package com.caocao.client.ui.serve;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;

import androidx.appcompat.widget.AppCompatTextView;

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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.util.List;

public class GoodsDetailsActivity extends BaseActivity {

    private ActivityGoodsDetailsBinding binding;
    private ServeViewModel serveVM;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("服务详情")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        int goodsId = getIntent().getIntExtra("goodsId", 0);

        serveVM = getViewModel(ServeViewModel.class);
        serveVM.goodsDetail(goodsId);

        serveVM.goodsDetailLiveData.observe(this, goodsDetailRes -> {
            LogUtils.e(goodsDetailRes);
            GoodsDetailResp goodsRes = goodsDetailRes.getData();
            bannerView(goodsRes);

            binding.tvTitle.setText(goodsRes.goodsTitle);

            binding.tvCateName.setText(goodsRes.cateName);

            binding.tvPrice.setText(getString(R.string.goods_price, goodsRes.goodsPrice));

            binding.cbCollect.setChecked(!(goodsRes.collectionStatus == 0));

            specView(goodsRes.goodsSpec);

            detailView(goodsRes.goodsDetailImage);

        });
    }

    private void detailView(String goodsDetails) {
        binding.webView.loadData(getNewContent(goodsDetails) ,"text/html; charset=utf-8", "utf-8");
        // 设置支持Javascript
        binding.webView.getSettings().setJavaScriptEnabled(true);
        //设置背景颜色
        binding.webView.setBackgroundColor(0);
    }


    private static String getNewContent(String htmltext){
        Document doc= Jsoup.parse(htmltext);
        Elements elements=doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width","100%").attr("height","auto");
        }
        return doc.toString();
    }

    private void specView(List<GoodsDetailResp.GoodsSpec> goodsSpecs) {
        for (int i = 0; i < goodsSpecs.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_spec_item, null);
            AppCompatTextView tvSpecName = view.findViewById(R.id.tv_spec_name);
            AppCompatTextView tvSpecPrice = view.findViewById(R.id.tv_spec_price);
            if (i == goodsSpecs.size()) {
                tvSpecName.setBackgroundResource(R.drawable.bg_table_row_l);
                tvSpecPrice.setBackgroundResource(R.drawable.bg_table_row_r);
            }

            tvSpecName.setText(goodsSpecs.get(i).specName);
            tvSpecPrice.setText(getString(R.string.good_spec_price_unit, goodsSpecs.get(i).specPrice, goodsSpecs.get(i).specUnit));
            binding.table.addView(view);
        }
    }

    private void bannerView(GoodsDetailResp goodsRes) {
        binding.banner.addBannerLifecycleObserver(this)//添加生命周期观察者
                .setAdapter(new GoodsBannerAdapter(this, goodsRes.bannerImage))//添加数据
                .setIndicator(new CircleIndicator(this), true)//设置轮播指示器
                .setIndicatorNormalColor(Color.parseColor("#aaffffff"))
                .setIndicatorSelectedColor(Color.parseColor("#ff6203"))
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
