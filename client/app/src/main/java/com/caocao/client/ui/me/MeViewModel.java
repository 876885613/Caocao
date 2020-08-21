package com.caocao.client.ui.me;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPStaticUtils;
import com.caocao.client.base.app.BaseApplication;
import com.caocao.client.http.BaseViewModel;
import com.caocao.client.http.entity.BaseResp;
import com.caocao.client.http.entity.respons.AddressResp;
import com.caocao.client.http.entity.respons.ApplyStatusResp;
import com.caocao.client.http.entity.respons.DemandOrderDetailResp;
import com.caocao.client.http.entity.respons.DemandOrderResp;
import com.caocao.client.http.entity.respons.GoodsResp;
import com.caocao.client.http.entity.respons.PayInfoResp;
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


    public MutableLiveData<ServeOrderResp> serveOrderLiveData;
    public MutableLiveData<DemandOrderResp> demandOrderLiveData;
    public MutableLiveData<ServeOrderDetailResp> serveOrderDetailLiveData;
    public MutableLiveData<DemandOrderDetailResp> demandOrderDetailLiveData;
    public MutableLiveData<PayInfoResp> payInfoLiveData;

    public MutableLiveData<AddressResp> addressLiveData;

    public MutableLiveData<BaseResp> cancelOrderLiveData;


    public MutableLiveData<BaseResp> finishOrderLiveData;


    public MutableLiveData<ApplyStatusResp> applyStatusLiveData;

    public MutableLiveData<GoodsResp> goodsLiveData;

    public MutableLiveData<BaseResp> baseLiveData;


    private int page;

    public MeViewModel() {
        serveOrderLiveData = new MutableLiveData<>();
        demandOrderLiveData = new MutableLiveData<>();
        serveOrderDetailLiveData = new MutableLiveData<>();
        demandOrderDetailLiveData = new MutableLiveData<>();
        addressLiveData = new MutableLiveData<>();
        payInfoLiveData = new MutableLiveData<>();

        cancelOrderLiveData = new MutableLiveData<>();

        finishOrderLiveData = new MutableLiveData<>();

        applyStatusLiveData = new MutableLiveData<>();

        goodsLiveData = new MutableLiveData<>();

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

    public void orderDetail(int orderId) {
        request(api.orderDetail(orderId)).send(serveOrderDetailLiveData);
    }

    public void demandDetail(int id) {
        request(api.demandDetail(id)).send(demandOrderDetailLiveData);
    }


    public void cancelOrder(int orderId) {
        request(api.cancelOrder(orderId)).send(cancelOrderLiveData);
    }


    public void reloadOrder(int orderId) {
        request(api.reloadOrder(orderId, 2)).send(payInfoLiveData);
    }


    public void finishOrder(int orderId) {
        request(api.finishOrder(orderId)).send(finishOrderLiveData);
    }


    public void createOrderComment(int orderId, int goodsId, String content, int fraction, String commentImg) {
        request(api.createOrderComment(orderId, goodsId, content, fraction, commentImg)).send(baseLiveData);
    }

    public void reloadDemand(int id) {
        request(api.reloadDemand(id, 2)).send(payInfoLiveData);
    }

    public void balancePayDemand(int id, String expectedPrice) {
        request(api.balancePayDemand(id, expectedPrice, 2)).send(payInfoLiveData);
    }

    public void cancelDemand(int id) {
        request(api.cancelDemand(id)).send(cancelOrderLiveData);
    }

    public void refundDemand(int id) {
        request(api.refundDemand(id)).send(baseLiveData);
    }

    public void applyStatus() {
        request(api.applyStatus()).send(applyStatusLiveData);
    }

    public void collectionGoodsList() {
        page = 1;
        request(api.collectionGoodsList(page,
                SPStaticUtils.getString("longitude",""), SPStaticUtils.getString("latitude", ""))).send(goodsLiveData, page);
    }

    public void collectionGoodsListMore() {
        page++;
        request(api.collectionGoodsList(page,
                SPStaticUtils.getString("longitude",""), SPStaticUtils.getString("latitude", ""))).send(goodsLiveData, page);
    }


    public void updatePassword(String oldPassword, String password, String repeatPassword) {
        request(api.updatePassword(oldPassword, password, repeatPassword)).send(baseLiveData);
    }
}
