package com.caocao.client.ui.me.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseFragment;
import com.caocao.client.base.app.BaseApplication;
import com.caocao.client.databinding.LayoutRefreshListBinding;
import com.caocao.client.http.entity.respons.PayInfoResp;
import com.caocao.client.http.entity.respons.ServeOrderResp;
import com.caocao.client.ui.adapter.ServeOrderAdapter;
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
 * @ClassName: OrderFragment
 * @Description: 订单
 * @Author: XuYu
 * @CreateDate: 2020/8/7 15:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/7 15:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ServeOrderListFragment extends BaseFragment {

    private LayoutRefreshListBinding binding;
    private ServeOrderAdapter orderAdapter;
    private MeViewModel meVM;
    private int state;
    private ServeOrderResp order;

    private int adapterPosition = -1;

    @Override
    protected void initVmData(Bundle savedInstanceState) {

        state = getArguments().getInt(ViewPagerAdapter.ARGUMENT, 0);

        meVM = getViewModel(MeViewModel.class);



        meVM.serveOrderLiveData.observe(this, orderRes -> {
            LogUtils.e(orderRes);

            List<ServeOrderResp> orderList = orderRes.getData();

            if (orderRes.getPage() == 1) {
                orderAdapter.setNewData(orderList);
            } else {
                orderAdapter.addData(orderList);
            }

            RefreshUtils.setNoMore(binding.refresh, orderRes.getPage(), orderList.size());
        });


        meVM.cancelOrderLiveData.observe(this, cancelResp -> {
            ToastUtils.showShort(cancelResp.getMsg());
            orderAdapter.getData().remove(order);
            orderAdapter.notifyDataSetChanged();
        });


        meVM.finishOrderLiveData.observe(this, finishOrderRes -> {
            ToastUtils.showShort(finishOrderRes.getMsg());
            if (orderAdapter.getData() != null && orderAdapter.getData().size() > 0) {
                if (state == 1 && adapterPosition != -1) {
                    orderAdapter.getData().get(adapterPosition).status = 1;
                    orderAdapter.notifyDataSetChanged();
                } else {
                    orderAdapter.getData().remove(order);
                    orderAdapter.notifyDataSetChanged();
                }
            }
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
            request.extData = "reloadOrder";
            BaseApplication.iwxapi.sendReq(request);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        meVM.serveOrder(state);
    }

    @Override
    protected void initView() {
        binding.rvList.setLayoutManager(new LinearLayoutManager(context));
        orderAdapter = new ServeOrderAdapter(R.layout.adapter_serve_order_item, null);
        binding.rvList.setAdapter(orderAdapter);


        orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("orderId", orderAdapter.getData().get(position).orderId);
                ActivityUtils.startActivity(bundle, ServeOrderDetailsActivity.class);
            }
        });


        orderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                adapterPosition = position;
                order = orderAdapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.tv_cancel:
                        cancelOrder(order);
                        break;
                    case R.id.tv_pay:
                        meVM.reloadOrder(order.orderId);
                        break;
                    case R.id.tv_state:
                        if (order.status == 1 && order.commentStatus == 0) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("goodsId", order.goodsId);
                            bundle.putInt("orderId", order.orderId);
                            ActivityUtils.startActivity(bundle, OrderCommentActivity.class);
                        } else if (order.status == 4) {
                            meVM.finishOrder(order.orderId);
                        }
                        break;
                }
            }
        });

        //刷新和加载
        binding.refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                meVM.serveOrder(state);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                meVM.serveOrderMore(state);
            }
        });
    }


    private void cancelOrder(ServeOrderResp order) {
        new AlertDialog.Builder(activity)
                .setView(R.layout.dialog_general)
                .setText(R.id.tv_title, "是否取消订单")
                .setOnClickListener(R.id.tv_cancel, null)
                .setOnClickListener(R.id.tv_affirm, new OnClickListenerWrapper() {
                    @Override
                    public void onClickCall(View v) {
                        meVM.cancelOrder(order.orderId);
                    }
                })
                .show();
    }

    @Override
    protected View initViewBind(LayoutInflater inflater, ViewGroup container) {
        binding = LayoutRefreshListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        meVM.serveOrder(state);
    }
}
