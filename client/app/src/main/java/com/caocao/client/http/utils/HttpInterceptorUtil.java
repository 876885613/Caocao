package com.caocao.client.http.utils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPStaticUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.http.utils
 * @ClassName: HttpInterceptorUtil
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/3 10:03
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/3 10:03
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HttpInterceptorUtil {
    public static final String TAG = "HttpInterceptorUtil";

    public static HttpLoggingInterceptor LogInterceptor() {     //日志拦截器,用于打印返回请求的结果
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.d(TAG, message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }


    public static Interceptor HeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //添加请求头
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("token", SPStaticUtils.getString("token"));
//                        .addHeader("token", "tIDMgHVgduct4SCEVGEHSc2O1z4zajOYmmyUOjOwaQvJlimTwlctZvCAO20-eQBbTJqBvJmV_wDnOJ1gFgjwzg==");
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }
}
