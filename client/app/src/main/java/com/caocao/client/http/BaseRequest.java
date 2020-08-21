package com.caocao.client.http;

import com.caocao.client.http.utils.HttpInterceptorUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.http
 * @ClassName: BaseRequest
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/3 10:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/3 10:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BaseRequest<T> {

    private static OkHttpClient client;

    static {
        //初始化OkHttp,绑定拦截器事件
        client = new OkHttpClient.Builder()
                .connectTimeout(6000, TimeUnit.MILLISECONDS)
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .writeTimeout(10000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .hostnameVerifier((hostname, session) -> true)
                .dns(new ApiDns())
                .addInterceptor(HttpInterceptorUtil.LogInterceptor())             //绑定日志拦截器
                .addInterceptor(HttpInterceptorUtil.NullResponseInterceptor())             //空数据拦截器
                .addNetworkInterceptor(HttpInterceptorUtil.HeaderInterceptor())       //绑定header拦截器
                .build();


        //http 证书问题
        /*
        TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        try {
            SSLContext ssl = SSLContext.getInstance("SSL");
            ssl.init(null, trustManagers, new SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(ssl.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
         */
    }

    private T api;


    public BaseRequest(String host, Class clazz) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson)) //设置gson转换器, 将返回的json数据转为实体
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //设置CallAdapter
                .baseUrl(host)
                .client(client)                                               //设置客户端okhttp相关参数
                .build();
        api = (T) retrofit.create(clazz);
    }


    public T getApiService() {
        return api;
    }

}
