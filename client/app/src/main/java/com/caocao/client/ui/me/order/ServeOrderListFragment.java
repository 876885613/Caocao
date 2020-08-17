package com.caocao.client.ui.me.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseFragment;
import com.caocao.client.databinding.LayoutRefreshListBinding;
import com.caocao.client.http.entity.respons.ServeOrderResp;
import com.caocao.client.ui.adapter.ServeOrderAdapter;
import com.caocao.client.ui.adapter.ViewPagerAdapter;
import com.caocao.client.ui.me.MeViewModel;
import com.caocao.client.utils.RefreshUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

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

    @Override
    protected void initVmData(Bundle savedInstanceState) {

        state = getArguments().getInt(ViewPagerAdapter.ARGUMENT, 0);

        meVM = getViewModel(MeViewModel.class);

        meVM.serveOrder(state);

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
                switch (view.getId()) {
                    case R.id.tv_cancel:
                        break;
                    case R.id.tv_pay:
                        break;
                    case R.id.tv_state:
                        ServeOrderResp order = orderAdapter.getData().get(position);

                        if (order.status == 1 && order.commentStatus == 0) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("goodsId",order.goodsId);
                            bundle.putInt("orderId",order.orderId);
                            ActivityUtils.startActivity(bundle,OrderCommentActivity.class);
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

    @Override
    protected View initViewBind(LayoutInflater inflater, ViewGroup container) {
        binding = LayoutRefreshListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
