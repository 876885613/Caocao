package com.caocao.client.ui.me.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseFragment;
import com.caocao.client.http.entity.respons.DemandOrderResp;
import com.caocao.client.ui.adapter.DemandOrderAdapter;
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
 * @ClassName: DemandOrderListActivity
 * @Description:
 * @Author: XuYu
 * @CreateDate: 2020/8/17 10:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/17 10:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DemandOrderListFragment extends BaseFragment {
    private com.caocao.client.databinding.LayoutRefreshListBinding binding;
    private DemandOrderAdapter                                     orderAdapter;
    int state;
    private MeViewModel meVM;

    @Override
    protected void initVmData(Bundle savedInstanceState) {
        state = getArguments().getInt(ViewPagerAdapter.ARGUMENT, 0);

        meVM = getViewModel(MeViewModel.class);
        meVM.demandOrder(state);
        meVM.demandOrderLiveData.observe(this, orderRes -> {
            List<DemandOrderResp> orderList = orderRes.getData();
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


    @Override
    protected View initViewBind(LayoutInflater inflater, ViewGroup container) {
        binding = com.caocao.client.databinding.LayoutRefreshListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
