package com.caocao.client.ui.me.order;

import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityDemandOrderDetailsBinding;
import com.caocao.client.http.entity.respons.ServeOrderDetailResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.me.MeViewModel;

import java.util.ArrayList;
import java.util.List;

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
        List<String> titles = new ArrayList<String>();
        titles.add("下订单");
        titles.add("支付完成");
        titles.add("已经发货");
        titles.add("交易完成");
        binding.stepView.setStepTitles(titles);
    }

    @Override
    protected void initData() {

        int id = getIntValue("id");

        meVM = getViewModel(MeViewModel.class);


    }

    private void serveOrder(int orderId) {

    }

    private void orderView(ServeOrderDetailResp order) {


    }

    @Override
    public View initLayout() {
        binding = ActivityDemandOrderDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
