package com.caocao.client.ui.login;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.caocao.client.http.BaseViewModel;
import com.caocao.client.http.entity.BaseResp;
import com.caocao.client.http.entity.respons.LoginResp;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel {
    public MutableLiveData<Long> timeLiveData;

    MutableLiveData<LoginResp> loginLiveData;
    MutableLiveData<BaseResp> codeLiveData;
    MutableLiveData<BaseResp> baseLiveData;

    public LoginViewModel() {
        timeLiveData = new MutableLiveData<>();
        loginLiveData = new MutableLiveData<>();
        codeLiveData = new MutableLiveData<>();
        baseLiveData = new MutableLiveData<>();
    }


    public void sendCode(String phone) {
        request(api.sendCode(phone)).send(codeLiveData);
    }

    public void register(String phone, String code, String password, String repeatPassword) {
        request(api.register(phone, code, password, repeatPassword)).send(loginLiveData);
    }


    public void login(String phone, String password) {
        request(api.login(phone, password)).send(loginLiveData);
    }


    public void sendRetrievePasswordSms(String phone) {
        request(api.sendRetrievePasswordSms(phone)).send(codeLiveData);
    }

    public void retrievePassword(String phone, String code, String password, String repeatPassword) {
        request(api.retrievePassword(phone, code, password, repeatPassword)).send(baseLiveData);
    }

    @SuppressLint("CheckResult")
    public void countdown() {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(60)
                .map(aLong -> 59 - aLong)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> timeLiveData.postValue(aLong), throwable -> {

                }, () -> {
                    //倒计时结束，重置按钮，并停止获取请求
                    timeLiveData.postValue((long) -1);
                });
    }
}
