package com.caocao.client.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.base.app.BaseApplication;
import com.tencent.mm.opensdk.modelmsg.SendAuth;


import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * @ProjectName: sjjy-android-students
 * @Package: com.sjjy_android_student.wxapi
 * @ClassName: WeChatUtils
 * @Description:
 * @Author:
 * @CreateDate: 2020/8/12 11:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/12 11:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WeChatUtils {
    //Bitmap图片转换
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //微信登陆
    public static void getWeChatLogin(Activity activity, String state) {
        if (isWeiXinInstall(activity)) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = state;
            BaseApplication.iwxapi.sendReq(req);
        } else {
            ToastUtils.showShort("您还没有安装微信，请先安装微信客户端");
        }
    }


    /**
     * 判断是否安装微信
     *
     * @return
     */
    public static boolean isWeiXinInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packageManager
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        for (int i = 0; i < packageInfoList.size(); i++) {
            String pn = packageInfoList.get(i).packageName;
            if (pn.equals("com.tencent.mm")) {
                return true;
            }
        }
        return false;
    }
}
