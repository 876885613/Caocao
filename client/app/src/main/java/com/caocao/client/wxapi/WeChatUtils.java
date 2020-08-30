package com.caocao.client.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.base.app.BaseApplication;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

    private static byte[] thumbData;


    public static Disposable disposable;

    public static void getNetworkBitmap(String image) {

        Observable.create((ObservableOnSubscribe<Bitmap>) e -> {
            e.onNext(returnBitmap(image));
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        thumbData = ImageUtils.compressByQuality(bitmap, 128L);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //释放订阅避免内存泄漏
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }

                    @Override
                    public void onComplete() {
                        //释放订阅避免内存泄漏
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }
                });
    }

    /**
     * 根据图片的url路径获得Bitmap对象
     *
     * @param url
     * @return
     */
    public static Bitmap returnBitmap(String url) {
        if (!StringUtils.isEmpty(url)) {
            URL fileUrl = null;
            Bitmap bitmap = null;
            try {
                fileUrl = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        return null;
    }


    public static byte[] getThumbData() {
        return thumbData;
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
