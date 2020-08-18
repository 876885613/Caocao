package com.caocao.client.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.base.app.BaseApplication;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.mine
 * @ClassName: WXPayEntryActivity
 * @Description: 微信支付返回结果页
 * @Author: 徐玉
 * @CreateDate: 2019/12/15 15:14
 * @UpdateUser: 徐玉
 * @UpdateDate: 2019/12/15 15:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        BaseApplication.iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
                ToastUtils.showShort("支付成功");
            } else if (baseResp.errCode == -1) {
                ToastUtils.showShort("支付失败");
            } else if (baseResp.errCode == -2) {
                ToastUtils.showShort("支付取消");
            }

            finish();
            Message msg = new Message();
            msg.what = 10000;
            BaseApplication.getMainThreadHandler().sendMessage(msg);
        }
    }

}
