package com.caocao.client.ui.me.order;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityServeOrderDetailsBinding;
import com.caocao.client.http.entity.respons.ServeOrderDetailResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.me.MeViewModel;
import com.caocao.client.weight.CornerTransform;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.me.order
 * @ClassName: OrderDetailsActivity
 * @Description: 订单详情
 * @Author:
 * @CreateDate: 2020/8/17 14:20
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/17 14:20
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ServeOrderDetailsActivity extends BaseActivity {


    private MeViewModel                      meVM;
    private ActivityServeOrderDetailsBinding binding;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("订单详情")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        int orderId = getIntValue("orderId");

        meVM = getViewModel(MeViewModel.class);

        meVM.orderDetail(orderId);
        meVM.orderDetailLiveData.observe(this, orderRes -> {
            ServeOrderDetailResp order = orderRes.getData();
            orderView(order);
        });
    }


    private void orderView(ServeOrderDetailResp order) {
        binding.tvName.setText(order.orderName);
        binding.tvTel.setText(order.orderPhone);
        binding.tvAddress.setText(getString(R.string.demand_address, order.orderProvince, order.orderCity, order.orderArea, order.orderDetail));

        Glide.with(this).load(order.showImage)
                .transform(new CornerTransform(this, SizeUtils.dp2px(2)))
                .<AppCompatImageView>into(binding.ivThumb);

        binding.tvSpecName.setText(order.specName);
        binding.tvSpecPriceUnit.setText(getString(R.string.goods_spec_price_unit_symbol, order.goodsSpecPrice, order.specUnit));
        binding.tvCount.setText("x" + order.goodsSpecNum);
        binding.tvPrice.setText(getString(R.string.goods_price, order.orderPrice));
        binding.tvMerchantName.setText(order.merchantName);
        binding.tvCateName.setText(order.cateName);
        binding.tvServiceTime.setText(order.serviceTime);
        binding.tvServeAddress.setText(getString(R.string.demand_address, order.orderProvince, order.orderCity, order.orderArea, order.orderDetail));
        binding.tvOrderRemark.setText(order.orderRemark);
        binding.tvOrderSn.setText(order.orderSn);
        binding.tvCreateTime.setText(order.orderCreateTime);

        switch (order.orderStatus) {
            case 1:
                binding.tvOrderStatus.setText("已完成");
                break;
            case 2:
                binding.tvOrderStatus.setText("待支付");
                break;
            case 3:
                binding.tvOrderStatus.setText("待接单");
                break;
            case 4:
                binding.tvOrderStatus.setText("待服务");
                break;
        }

    }

    @Override
    public View initLayout() {
        binding = ActivityServeOrderDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
