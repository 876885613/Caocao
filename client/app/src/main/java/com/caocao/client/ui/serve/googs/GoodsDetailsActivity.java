package com.caocao.client.ui.serve.googs;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.base.app.BaseApplication;
import com.caocao.client.databinding.ActivityGoodsDetailsBinding;
import com.caocao.client.http.entity.respons.GoodsDetailResp;
import com.caocao.client.http.entity.respons.RemarkResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.GoodsBannerAdapter;
import com.caocao.client.ui.login.LoginUtils;
import com.caocao.client.ui.serve.RemarkMoreActivity;
import com.caocao.client.ui.serve.ServeViewModel;
import com.caocao.client.ui.serve.order.PlaceOrderActivity;
import com.caocao.client.weight.CornerTransform;
import com.caocao.client.wxapi.WeChatUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.BannerUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class GoodsDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ActivityGoodsDetailsBinding binding;
    private ServeViewModel serveVM;
    private int goodsId;
    private GoodsDetailResp goodsRes;


    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("服务详情")
                .setRightIcon(R.mipmap.ic_share_ico)
                .setRightIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        share();
                    }
                })
                .builder();
    }

    private void share() {
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = "https://ccdj.jiajiayong.com"; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = "gh_9f1c863248b9";     // 小程序原始id
        miniProgramObj.path = "/pagesA/product/product_info?goods_id=" + goodsId;            //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = goodsRes.goodsTitle;                    // 小程序消息title
        msg.description = goodsRes.cateName;               // 小程序消息desc
        msg.thumbData = WeChatUtils.getThumbData();                      // 小程序消息封面图片，小于128k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webPage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
        BaseApplication.iwxapi.sendReq(req);
    }


    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    protected void initView() {
        binding.rlCollect.setOnClickListener(this);
        binding.tvStore.setOnClickListener(this);
        binding.tvPlaceOrder.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        goodsId = getIntent().getIntExtra("goodsId", 0);

        serveVM = getViewModel(ServeViewModel.class);
        serveVM.goodsDetail(goodsId);

        serveVM.orderRemarkList(goodsId);


        remarkView();

        serveVM.baseLiveData.observe(this, collectionRes -> {
            ToastUtils.showShort(collectionRes.getMsg());
            binding.cbCollect.setChecked(!binding.cbCollect.isChecked());
        });

        serveVM.goodsDetailLiveData.observe(this, goodsDetailRes -> {

            goodsRes = goodsDetailRes.getData();

            WeChatUtils.getNetworkBitmap(goodsRes.showImage);

            bannerView(goodsRes);

            binding.tvTitle.setText(goodsRes.goodsTitle);
            binding.tvCateName.setText(goodsRes.cateName);
            binding.tvPrice.setText(getString(R.string.goods_price, goodsRes.goodsPrice));
            binding.cbCollect.setChecked(!(goodsRes.collectionStatus == 0));
            specView(goodsRes.goodsSpec);
            detailWebView(goodsRes.goodsDetailImage);

            if (goodsRes.merchantType == 1) {
                binding.tvStore.setVisibility(View.GONE);
            }

        });

        binding.tvRemarkAll.setOnClickListener(this);
    }

    private void remarkView() {
        serveVM.remarkLiveData.observe(this, remarkRes -> {
            if (remarkRes == null || remarkRes.getData().size() == 0) {
                return;
            }

            binding.remarkGroup.setVisibility(View.VISIBLE);

            RemarkResp remark = remarkRes.getData().get(0);
            binding.tvRemark.setText(getString(R.string.goods_remark_num, remarkRes.getData().size()));

            Glide.with(this)
                    .load(remark.headimgurl)
                    .placeholder(R.mipmap.ic_default_portrait)
                    .error(R.mipmap.ic_default_portrait)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(binding.ivPortrait);


            binding.tvName.setText(remark.nickname);
            binding.tvRemarkContent.setText(remark.orderCommentContent);
            binding.tvRemarkTime.setText(remark.orderCommentTime);
            if (remark.commentImg != null) {
                List<String> remarkImg = (List<String>) remark.commentImg;
                binding.ivRemarkImg.setVisibility(View.VISIBLE);
                Glide.with(this).load(remarkImg.get(0)).
                        transform(new CornerTransform(this, SizeUtils.dp2px(7))).into(binding.ivRemarkImg);
            }

            showRating(remark.orderCommentFraction);


        });
    }


    private void showRating(int fraction) {
        if (fraction == 1) {
            binding.ivRating.setImageResource(R.mipmap.ic_rating1);
        }

        if (fraction == 2) {
            binding.ivRating.setImageResource(R.mipmap.ic_rating2);
        }

        if (fraction == 3) {
            binding.ivRating.setImageResource(R.mipmap.ic_rating3);
        }

        if (fraction == 4) {
            binding.ivRating.setImageResource(R.mipmap.ic_rating4);
        }

        if (fraction == 5) {
            binding.ivRating.setImageResource(R.mipmap.ic_rating5);
        }
    }


    private void detailWebView(String goodsDetails) {
        binding.webView.loadData(getNewContent(goodsDetails), "text/html; charset=utf-8", "utf-8");
        // 设置支持Javascript
        binding.webView.getSettings().setJavaScriptEnabled(true);
        //设置背景颜色
        binding.webView.setBackgroundColor(0);
    }


    private static String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
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
            tvSpecPrice.setText(getString(R.string.goods_spec_price_unit, goodsSpecs.get(i).specPrice, goodsSpecs.get(i).specUnit));
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

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.tv_remark_all:
                bundle.putInt("goodsId", goodsId);
                ActivityUtils.startActivity(bundle, RemarkMoreActivity.class);
                break;
            case R.id.rl_collect:
                if (LoginUtils.isLogin()) {
                    serveVM.collectionGoods(goodsId);
                }
                break;
            case R.id.tv_store:
                bundle.putInt("merchantId", goodsRes.merchantId);
                ActivityUtils.startActivity(bundle, MerchantDetailsActivity.class);
                break;
            case R.id.tv_place_order:
                if (LoginUtils.isLogin()) {
                    bundle.putParcelableArrayList("goodsSpec", (ArrayList<? extends Parcelable>) goodsRes.goodsSpec);
                    bundle.putInt("goodsId", goodsRes.goodsId);
                    ActivityUtils.startActivity(bundle, PlaceOrderActivity.class);
                }
                break;
        }
    }
}
