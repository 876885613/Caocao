package com.caocao.client.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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
        String extData = ((PayReq) baseReq).extData;
        String[] split = extData.split(",");
        LogUtils.i("onReq", extData, split[0], split[1]);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtils.i("onPayFinish, errCode = " + baseResp.errCode);
        LogUtils.i(baseResp.getType());
        LogUtils.i(baseResp.errStr);
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
                ToastUtils.showShort("支付成功");
                String extData = ((PayResp) baseResp).extData;
                String[] split = extData.split(",");
                LogUtils.i(extData, split[0], split[1]);
            } else if (baseResp.errCode == -1) {
                ToastUtils.showShort("支付失败");
            } else if (baseResp.errCode == -2) {
                ToastUtils.showShort("支付取消");
            }
        }
        finish();
    }

}
