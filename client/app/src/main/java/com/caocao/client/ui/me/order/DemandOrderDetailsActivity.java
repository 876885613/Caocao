package com.caocao.client.ui.me.order;

import android.view.View;

import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityDemandOrderDetailsBinding;
import com.caocao.client.http.entity.respons.DemandOrderDetailResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.me.MeViewModel;

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
public class DemandOrderDetailsActivity extends BaseActivity {

    private ActivityDemandOrderDetailsBinding binding;
    private MeViewModel                       meVM;

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

        int id = getIntValue("id");

        meVM = getViewModel(MeViewModel.class);

        meVM.demandDetail(id);
        meVM.demandOrderDetailLiveData.observe(this, demandResp -> {
            DemandOrderDetailResp demand = demandResp.getData();
            demandView(demand);
        });
    }


    private void demandView(DemandOrderDetailResp demand) {
        binding.tvCateName.setText(demand.cateName);
        binding.tvDueStats.setText(getString(R.string.demand_due_stats, demand.surplusTime, demand.invitedCount));
        binding.tvOrderRemark.setText(demand.demandDepict);
        binding.tvOrderSn.setText(demand.orderSn);
        binding.tvCreateTime.setText(demand.startTime);
        binding.tvReservePrice.setText(demand.reservePrice);
        binding.tvBalancePrice.setText(demand.expectedPrice);

        binding.tvMerchantName.setText(demand.merchantName);
        binding.tvMerchantAddress.setText(getString(R.string.demand_address, demand.merchantProvince,
                demand.merchantCity, demand.merchantDistrict, demand.addressDetail));
        binding.tvMerchantTel.setText(demand.consumerHotline);

        switch (demand.status) {
            case 1:
                binding.tvOrderState.setText("已完成");
                binding.tvTime.setText(demand.serviceTime);
                binding.cbRelease.setChecked(true);
                binding.releaseLine.setChecked(true);
                binding.tvRelease.setChecked(true);
                binding.cbReceiving.setChecked(true);
                binding.receivingLine.setChecked(true);
                binding.tvReceiving.setChecked(true);
                binding.cbServe.setChecked(true);
                binding.serveLine.setChecked(true);
                binding.tvServe.setChecked(true);
                binding.tvFinish.setChecked(true);
                binding.cbFinish.setChecked(true);
                binding.llMerchant.setVisibility(View.VISIBLE);
                break;
            case 2:
                binding.tvOrderState.setText("等待支付中");
                binding.tvTime.setVisibility(View.GONE);
                binding.cbRelease.setChecked(true);
                binding.releaseLine.setChecked(true);
                binding.tvRelease.setChecked(true);
                break;
            case 3:
                binding.tvOrderState.setText("服务抢单中");
                binding.tvTime.setText(demand.serviceTime);
                binding.cbRelease.setChecked(true);
                binding.releaseLine.setChecked(true);
                binding.tvRelease.setChecked(true);
                binding.cbReceiving.setChecked(true);
                binding.receivingLine.setChecked(true);
                binding.tvReceiving.setChecked(true);
                break;
            case 4:
                binding.llMerchant.setVisibility(View.VISIBLE);
                binding.tvOrderState.setText("等待服务中");
                binding.tvTime.setText(demand.serviceTime);
                binding.cbRelease.setChecked(true);
                binding.releaseLine.setChecked(true);
                binding.tvRelease.setChecked(true);
                binding.cbReceiving.setChecked(true);
                binding.receivingLine.setChecked(true);
                binding.tvReceiving.setChecked(true);
                binding.cbServe.setChecked(true);
                binding.serveLine.setChecked(true);
                binding.tvServe.setChecked(true);
                break;
            case 5:
                binding.tvOrderState.setText("退款中");
                binding.tvTime.setText(demand.serviceTime);
            case 6:
                binding.tvOrderState.setText("退款成功");
                binding.tvTime.setText(demand.serviceTime);
                break;
        }

    }

    @Override
    public View initLayout() {
        binding = ActivityDemandOrderDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
