package com.caocao.client.ui.demand;

import androidx.lifecycle.MutableLiveData;

import com.caocao.client.http.BaseViewModel;
import com.caocao.client.http.entity.BaseResp;
import com.caocao.client.http.entity.request.DemandReq;

public class DemandViewModel extends BaseViewModel {

    public MutableLiveData<BaseResp> baseResp;

    public DemandViewModel() {
        baseResp = new MutableLiveData<>();
    }

    public void createDemand(DemandReq demand) {
        request(api.createDemand(demand)).send(baseResp);
    }

}
