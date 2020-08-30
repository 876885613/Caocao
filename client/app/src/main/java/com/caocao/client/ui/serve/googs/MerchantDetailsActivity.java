package com.caocao.client.ui.serve.googs;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.base.app.BaseApplication;
import com.caocao.client.databinding.ActivityMerchantDetailsBinding;
import com.caocao.client.http.entity.respons.GoodsResp;
import com.caocao.client.http.entity.respons.MerchantResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.GoodsBannerAdapter;
import com.caocao.client.ui.adapter.MerchantServeAdapter;
import com.caocao.client.ui.serve.ServeViewModel;
import com.caocao.client.weight.DividerItemDecoration;
import com.caocao.client.wxapi.WeChatUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.BannerUtils;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.serve
 * @ClassName: MerchantDetailsActivity
 * @Description: 商家详情
 * @Author: XuYu
 * @CreateDate: 2020/8/11 15:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/11 15:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MerchantDetailsActivity extends BaseActivity {

    private ActivityMerchantDetailsBinding binding;
    private ServeViewModel                 serveVM;
    private MerchantServeAdapter           serveAdapter;
    private int merchantId;
    private MerchantResp merchant;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("商户详情")
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
        miniProgramObj.path = "/pagesA/index/store_info?merchant_id=" + merchantId;            //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = merchant.merchant.merchantName;                    // 小程序消息title
        msg.description = merchant.merchant.merchantName;               // 小程序消息desc
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
        binding.rvList.setNestedScrollingEnabled(false);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_f5_5));
        serveAdapter = new MerchantServeAdapter(R.layout.adapter_search_item, null);

        binding.rvList.setAdapter(serveAdapter);

        serveAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("goodsId",serveAdapter.getData().get(position).goodsId);
            ActivityUtils.startActivity(bundle, GoodsDetailsActivity.class);
        });
    }

    @Override
    protected void initData() {
        merchantId = getIntValue("merchantId");
        serveVM = getViewModel(ServeViewModel.class);

        serveVM.merchantDetail(merchantId);

        serveVM.merchantLiveData.observe(this, merchantRes -> {
            merchant = merchantRes.getData();

            WeChatUtils.getNetworkBitmap(merchant.merchant.merchantPhoto.get(0));

            banner(merchant.merchant.merchantPhoto);
            binding.tvTitle.setText(merchant.merchant.merchantName);
            binding.tvIntro.setText(merchant.merchant.merchantDetail);
            binding.tvTimeValue.setText(merchant.merchant.businessHours);
            binding.tvAddressValue.setText(merchant.merchant.merchantProvince
                    + merchant.merchant.merchantCity
                    + merchant.merchant.merchantDistrict
                    + merchant.merchant.addressDetail);

            goodsView(merchant.goodsList, merchant.merchant.merchantType);
        });
    }

    private void goodsView(List<GoodsResp> goodsList, int merchantType) {
        serveAdapter.setMerchantType(merchantType);
        serveAdapter.setNewData(goodsList);
    }

    private void banner(List<String> bannerRes) {
        binding.banner.addBannerLifecycleObserver(this)//添加生命周期观察者
                .setAdapter(new GoodsBannerAdapter(this, bannerRes))//添加数据
                .setIndicator(new CircleIndicator(this), true)//设置轮播指示器
                .setIndicatorNormalColor(Color.parseColor("#ffffff"))
                .setIndicatorSelectedColor(Color.parseColor("#ff6203"))
                .setIndicatorNormalWidth((int) BannerUtils.dp2px(6))
                .setIndicatorSelectedWidth((int) BannerUtils.dp2px(6))
                .start();
    }

    @Override
    public View initLayout() {
        binding = ActivityMerchantDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
