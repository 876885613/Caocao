package com.caocao.client.ui;

import androidx.lifecycle.MutableLiveData;

import com.caocao.client.http.BaseViewModel;
import com.caocao.client.http.entity.BaseResp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.PUT;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui
 * @ClassName: MainViewModel
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/4 16:56
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/4 16:56
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MainViewModel extends BaseViewModel {

    public MutableLiveData<List<BaseResp>> serveLiveData;

    public MainViewModel() {
        serveLiveData = new MutableLiveData<>();
    }


    public void serveData() {
        List<BaseResp> serveList = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            serveList.add(new BaseResp());
        }
        serveLiveData.setValue(serveList);
    }
}
