package com.caocao.client.ui.serve;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityGoodsDetailsBinding;
import com.caocao.client.http.entity.respons.GoodsDetailResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.ADBannerAdapter;
import com.caocao.client.ui.adapter.GoodsBannerAdapter;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.BannerUtils;

public class GoodsDetailsActivity extends BaseActivity {

    private ActivityGoodsDetailsBinding binding;
    private ServeViewModel              serveVM;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("服务详情")
                .builder();
    }

    @Override
    protected void initView() {
        binding.table.addView(LayoutInflater.from(this).inflate(R.layout.layout_spec_item, null));
    }

    @Override
    protected void initData() {
        int goodsId = getIntent().getIntExtra("goodsId", 0);

        serveVM = getViewModel(ServeViewModel.class);
        serveVM.goodsDetail(goodsId);

        serveVM.goodsDetailLiveData.observe(this, goodsDetailRes -> {
            LogUtils.e(goodsDetailRes);
            GoodsDetailResp detailRes = goodsDetailRes.getData();
            bannerView(detailRes);
        });
    }

    private void bannerView(GoodsDetailResp detailInfo) {
        binding.banner.addBannerLifecycleObserver(this)//添加生命周期观察者
                .setAdapter(new GoodsBannerAdapter(this, detailInfo.bannerImage))//添加数据
                .setIndicator(new CircleIndicator(this), true)//设置轮播指示器
                .setIndicatorNormalColor(Color.parseColor("#aaffffff"))
                .setIndicatorSelectedColor(Color.parseColor("#ffffff"))
                .setIndicatorNormalWidth((int) BannerUtils.dp2px(6))
                .setIndicatorSelectedWidth((int) BannerUtils.dp2px(6))
                .start();
    }

    @Override
    public View initLayout() {
        binding = ActivityGoodsDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
