package com.caocao.client.http;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.http.entity.BaseResp;

import org.reactivestreams.Subscription;

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


    private int                page;
    private MutableLiveData<T> liveData;
    private MutableLiveData<T> errorLiveData;


    public BaseSubscriber(MutableLiveData<T> liveData) {
        this(liveData, 0);
    }

    public BaseSubscriber(MutableLiveData<T> liveData, int page) {
        this(liveData, null, page);
    }


    public BaseSubscriber(MutableLiveData<T> liveData, MutableLiveData<T> errorLiveData, int page) {
        this.page = page;
        this.liveData = liveData;
        this.errorLiveData = errorLiveData;
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
        if (t instanceof BaseResp) {
            BaseResp resp = (BaseResp) t;
            if (resp.getCode() == 100) {
                resp.setPage(page);
                liveData.setValue(t);
            } else {
                ToastUtils.showShort(resp.getMsg());
                if (errorLiveData != null) {
                    errorLiveData.setValue((T) resp);
                }
            }
        }
    }


    @Override
    public void onError(Throwable e) {
        LogUtils.e(e);
        if (errorLiveData != null) {
            errorLiveData.setValue(null);
        }
    }

    @Override
    public void onComplete() {
    }
}

