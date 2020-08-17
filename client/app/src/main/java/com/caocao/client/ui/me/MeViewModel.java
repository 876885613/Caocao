package com.caocao.client.ui.me;

import androidx.lifecycle.MutableLiveData;

import com.caocao.client.http.BaseViewModel;
import com.caocao.client.http.entity.BaseResp;
import com.caocao.client.http.entity.respons.AddressResp;
import com.caocao.client.http.entity.respons.DemandOrderDetailResp;
import com.caocao.client.http.entity.respons.DemandOrderResp;
import com.caocao.client.http.entity.respons.ServeOrderDetailResp;
import com.caocao.client.http.entity.respons.ServeOrderResp;

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


    public MutableLiveData<ServeOrderResp>       serveOrderLiveData;
    public MutableLiveData<DemandOrderResp>      demandOrderLiveData;
    public MutableLiveData<ServeOrderDetailResp> serveOrderDetailLiveData;
    public MutableLiveData<DemandOrderDetailResp> demandOrderDetailLiveData;

    public MutableLiveData<AddressResp> addressLiveData;

    public MutableLiveData<BaseResp> baseLiveData;

    private int page;

    public MeViewModel() {
        serveOrderLiveData = new MutableLiveData<>();
        demandOrderLiveData = new MutableLiveData<>();
        serveOrderDetailLiveData = new MutableLiveData<>();
        demandOrderDetailLiveData = new MutableLiveData<>();
        addressLiveData = new MutableLiveData<>();

        baseLiveData = new MutableLiveData<>();
    }

    public void addressList() {
        request(api.addressList()).send(addressLiveData);
    }

    public void editAddress(AddressResp address) {
        request(api.editAddress(address)).send(baseLiveData);
    }

    public void deleteAddress(int id) {
        request(api.deleteAddress(id)).send(baseLiveData);
    }

    public void serveOrder(int state) {
        page = 1;
        request(api.orderList(state, page)).send(serveOrderLiveData, page);
    }

    public void serveOrderMore(int state) {
        page++;
        request(api.orderList(state, page)).send(serveOrderLiveData, page);
    }

    public void demandOrder(int state) {
        page = 1;
        request(api.demandList(state, page)).send(demandOrderLiveData, page);
    }

    public void demandOrderMore(int state) {
        page++;
        request(api.demandList(state, page)).send(demandOrderLiveData, page);
    }

    public void orderDetail(int orderId){
        request(api.orderDetail(orderId)).send(serveOrderDetailLiveData);
    }

    public void demandDetail(int id){
        request(api.demandDetail(id)).send(demandOrderDetailLiveData);
    }

}
