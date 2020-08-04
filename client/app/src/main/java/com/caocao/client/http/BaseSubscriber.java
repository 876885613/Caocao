package com.caocao.client.http;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.JsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.R;

import org.reactivestreams.Subscription;

import java.lang.reflect.Type;

import io.reactivex.FlowableSubscriber;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.http
 * @ClassName: BaseSubscriber
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/3 10:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/3 10:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BaseSubscriber<T> implements FlowableSubscriber<T> {



    private MutableLiveData<T> liveData;

    public BaseSubscriber(MutableLiveData<T> liveData) {
        this(liveData, false);
    }


    public BaseSubscriber(MutableLiveData<T> liveData, boolean loading) {
        this.liveData = liveData;
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(1);
        //网络判断，无网络停止请求
        if (!NetworkUtils.isConnected()) {
            ToastUtils.showShort("请检查是否联网");
            s.cancel();
            return;
        }
    }

    @Override
    public void onNext(T t) {
        liveData.setValue(t);
    }


    @Override
    public void onError(Throwable e) {
        LogUtils.e(e);
    }

    @Override
    public void onComplete() {
    }
}

