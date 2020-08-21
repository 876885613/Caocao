package com.caocao.client.ui.me.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseFragment;
import com.caocao.client.base.app.BaseApplication;
import com.caocao.client.databinding.LayoutRefreshListBinding;
import com.caocao.client.http.entity.respons.DemandOrderResp;
import com.caocao.client.http.entity.respons.PayInfoResp;
import com.caocao.client.ui.adapter.DemandOrderAdapter;
import com.caocao.client.ui.adapter.ViewPagerAdapter;
import com.caocao.client.ui.me.MeViewModel;
import com.caocao.client.utils.RefreshUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.baselibrary.dialog.AlertDialog;
import com.coder.baselibrary.dialog.OnClickListenerWrapper;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.me.order
 * @ClassName: DemandOrderListActivity
 * @Description:
 * @CreateDate: 2020/8/17 10:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/17 10:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DemandOrderListFragment extends BaseFragment {
    private LayoutRefreshListBinding binding;
    private DemandOrderAdapter orderAdapter;
    int state;
    private MeViewModel meVM;

    private DemandOrderResp order;
    private int adapterPosition = -1;

    @Override
    protected void initVmData(Bundle savedInstanceState) {
        state = getArguments().getInt(ViewPagerAdapter.ARGUMENT, 0);

        meVM = getViewModel(MeViewModel.class);

        meVM.demandOrderLiveData.observe(this, orderRes -> {
            List<DemandOrderResp> orderList = orderRes.getData();
            if (orderRes.getPage() == 1) {
                orderAdapter.setNewData(orderList);
            } else {
                orderAdapter.addData(orderList);
            }
            RefreshUtils.setNoMore(binding.refresh, orderRes.getPage(), orderList.size());
        });


        meVM.payInfoLiveData.observe(this, payInfoRes -> {
            PayInfoResp payInfo = payInfoRes.getData();
            PayReq request = new PayReq();
            request.appId = payInfo.appid;
            request.partnerId = payInfo.partnerid;
            request.prepayId = payInfo.prepayid;
            request.packageValue = payInfo.packageX;
            request.nonceStr = payInfo.noncestr;
            request.timeStamp = String.valueOf(payInfo.timestamp);
            request.sign = payInfo.sign;
            request.extData = "reloadDemand";
            BaseApplication.iwxapi.sendReq(request);
        });

        meVM.cancelOrderLiveData.observe(this, cancelResp -> {
            if (orderAdapter.getData() != null && orderAdapter.getData().size() > 0) {
                orderAdapter.getData().remove(order);
                orderAdapter.notifyDataSetChanged();
            }
        });

        meVM.baseLiveData.observe(this, refundResp -> {
            if (adapterPosition != -1) {
                orderAdapter.getData().get(adapterPosition).status = 5;
                orderAdapter.notifyDataSetChanged();
            } else {
                meVM.demandOrder(state);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        meVM.demandOrder(state);
    }

    @Override
    protected void initView() {
        binding.rvList.setLayoutManager(new LinearLayoutManager(context));
        orderAdapter = new DemandOrderAdapter(R.layout.adapter_release_order_item, null);
        binding.rvList.setAdapter(orderAdapter);


        orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", orderAdapter.getData().get(position).id);
                ActivityUtils.startActivity(bundle, DemandOrderDetailsActivity.class);
            }
        });


        orderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                order = orderAdapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.tv_cancel:
                        cancelOrder(order);
                        break;
                    case R.id.tv_pay:
                        meVM.reloadDemand(order.id);
                        break;
                    case R.id.tv_state:
                        if (order.status == 4) {
                            reloadDemandDialog(order);
                        } else if (order.status == 3) {
                            adapterPosition = position;
                            refundOrder(order);
                        }
                        break;
                }
            }
        });


        //刷新和加载
        binding.refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                meVM.demandOrder(state);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                meVM.demandOrderMore(state);
            }
        });
    }


    private void refundOrder(DemandOrderResp order) {
        new AlertDialog.Builder(activity)
                .setView(R.layout.dialog_general)
                .setText(R.id.tv_title, "确认申请退款")
                .setOnClickListener(R.id.tv_cancel, null)
                .setOnClickListener(R.id.tv_affirm, new OnClickListenerWrapper() {
                    @Override
                    public void onClickCall(View v) {
                        meVM.refundDemand(order.id);
                    }
                })
                .show();
    }


    private void cancelOrder(DemandOrderResp order) {
        new AlertDialog.Builder(activity)
                .setView(R.layout.dialog_general)
                .setText(R.id.tv_title, "确认取消订单")
                .setOnClickListener(R.id.tv_cancel, null)
                .setOnClickListener(R.id.tv_affirm, new OnClickListenerWrapper() {
                    @Override
                    public void onClickCall(View v) {
                        meVM.cancelDemand(order.id);
                    }
                })
                .show();
    }


    private void reloadDemandDialog(DemandOrderResp order) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setView(R.layout.dailog_balance_pay)
                .setText(R.id.et_price, order.expectedPrice)
                .show();


        dialog.setOnClickListener(R.id.tv_pay, v -> {
            AppCompatEditText tvPrice = dialog.getView(R.id.et_price);
            String price = tvPrice.getText().toString();

            if (StringUtils.isEmpty(price) || Double.parseDouble(price) <= 0) {
                ToastUtils.showShort("尾款金额不能为空或者小于0");
                return;
            }

            meVM.balancePayDemand(order.id, price);
            dialog.dismiss();
        });

    }


    @Override
    protected View initViewBind(LayoutInflater inflater, ViewGroup container) {
        binding = LayoutRefreshListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
