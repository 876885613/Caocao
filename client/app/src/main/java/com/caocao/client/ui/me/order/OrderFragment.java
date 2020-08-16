package com.caocao.client.ui.me.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.caocao.client.R;
import com.caocao.client.base.BaseFragment;
import com.caocao.client.databinding.LayoutRefreshListBinding;
import com.caocao.client.ui.adapter.OrderAdapter;
import com.caocao.client.ui.me.MeViewModel;

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
public class OrderFragment extends BaseFragment {

    private LayoutRefreshListBinding binding;
    private OrderAdapter             adapter;

    @Override
    protected void initVmData(Bundle savedInstanceState) {

        MeViewModel meVM = getViewModel(MeViewModel.class);

        meVM.orderData();

        meVM.orderLiveData.observe(this, orderList -> {
            adapter.setNewData(orderList);
        });
    }

    @Override
    protected void initView() {
        binding.rvList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new OrderAdapter(R.layout.adapter_release_order_item, null);
        binding.rvList.setAdapter(adapter);
    }

    @Override
    protected View initViewBind(LayoutInflater inflater, ViewGroup container) {
        binding = LayoutRefreshListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
