package com.caocao.client.http;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.caocao.client.base.app.BaseApplication;
import com.caocao.client.http.api.ApiService;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @ProjectName: matata-android-students
 * @Package: com.sjjy_android_student.http
 * @ClassName: BaseViewModel
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/5/21 9:45
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/21 9:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BaseViewModel<T> extends ViewModel {
    public static ApiService api;

    public static final int PAGE_SIZE = 10;

    /*解决背压*/
    private Flowable<T> sFlowable;

    static {
        api = BaseApplication.sHttpRequest.getApiService();
    }

    /**
     * 初始化flowable
     *
     * @param flowable
     * @return
     */
    protected BaseViewModel request(Flowable<T> flowable) {
        this.sFlowable = flowable;
        return this;
    }


    /**
     * 发送请求
     *
     * @return
     */
    public BaseViewModel<T> send(MutableLiveData<T> liveData) {
        sFlowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<T>(liveData));
        return this;
    }

    /**
     * 发送请求
     *
     * @return
     */
    public BaseViewModel send(MutableLiveData<T> liveData, boolean isLoading) {
        sFlowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber(liveData,isLoading));
        return this;
    }
}
