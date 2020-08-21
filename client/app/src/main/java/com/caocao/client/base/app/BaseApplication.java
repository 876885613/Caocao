package com.caocao.client.base.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.SPUtils;
import com.caocao.client.R;
import com.caocao.client.http.BaseRequest;
import com.caocao.client.http.api.ApiService;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * @ProjectName: EssayJoke
 * @Package: com.coder.essayjoke.base
 * @ClassName: BaseApplication
 * @Description: 基类App
 * @Author: XuYu
 * @CreateDate: 2020/2/1$ 1:51$
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/2/1 1:51
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */
public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";


    private static BaseApplication sInstance;

    //网络请求
    public static BaseRequest<ApiService> sHttpRequest;


    public static final String HOST_PATH = "http://ccdj.jiajiayong.com";

    //初始化刷新工具
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(android.R.color.transparent, R.color.color_theme);//全局设置主题颜色
            return new MaterialHeader(context).setColorSchemeResources(R.color.color_theme);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20).setAccentColorId(R.color.color_theme);
        });
    }


    public static Context getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        initRequest(HOST_PATH, ApiService.class);

        registerActivityLifecycleCallbacks(mCallbacks);

        SPStaticUtils.setDefaultSPUtils(SPUtils.getInstance("caocao_client"));

        //初始化微信
        initWX(sInstance);
    }


    /**
     * 初始化网络请求
     *
     * @param host
     * @param clazz
     */
    public void initRequest(String host, Class clazz) {
        if (sHttpRequest == null) {
            if (sHttpRequest == null) {
                sHttpRequest = new BaseRequest(host, clazz);
            }
        }
    }

    /**
     * 微信初始化配置
     */
    public static final String APP_ID = "wxcd6e2b9e6d6da467";
    public static       IWXAPI iwxapi;

    private void initWX(Context context) {
        iwxapi = WXAPIFactory.createWXAPI(context, APP_ID, true);
        iwxapi.registerApp(APP_ID);
    }

    //获取到主线程的handler
    private static Handler mMainThreadHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            mListener.handlerMessage(msg);
        }
    };

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    private static HandlerListener mListener;

    public static void setOnHandlerListener(HandlerListener listener) {
        mListener = listener;
    }

    public static HandlerListener getListener() {
        return mListener;
    }

    public interface HandlerListener {
        void handlerMessage(Message msg);
    }


    private ActivityLifecycleCallbacks mCallbacks = new ActivityLifecycleCallbacks() {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    };
}
