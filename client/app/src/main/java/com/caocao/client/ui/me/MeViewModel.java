package com.caocao.client.ui.me;

import androidx.lifecycle.MutableLiveData;

import com.caocao.client.http.BaseViewModel;
import com.caocao.client.http.entity.BaseResp;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.me
 * @ClassName: MeViewModel
 * @Description:
 * @Author: XuYu
 * @CreateDate: 2020/8/7 15:51
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/7 15:51
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MeViewModel extends BaseViewModel {

    public MutableLiveData<List<BaseResp>> orderLiveData;

    public MeViewModel() {
        orderLiveData = new MutableLiveData<>();
    }

    public void orderData() {
        List<BaseResp> serveList = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            serveList.add(new BaseResp());
        }
        orderLiveData.setValue(serveList);
    }
}
