package com.caocao.client.ui.serve;

import androidx.lifecycle.MutableLiveData;

import com.caocao.client.base.app.BaseApplication;
import com.caocao.client.http.BaseViewModel;
import com.caocao.client.http.entity.BaseResp;
import com.caocao.client.http.entity.respons.GoodsDetailResp;
import com.caocao.client.http.entity.respons.GoodsResp;
import com.caocao.client.http.entity.respons.MerchantResp;
import com.caocao.client.http.entity.respons.RemarkResp;
import com.caocao.client.http.entity.respons.SortResp;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.serve
 * @ClassName: ServeViewModel
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/10 11:54
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/10 11:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ServeViewModel extends BaseViewModel {
    public MutableLiveData<SortResp>        sortLiveData;
    public MutableLiveData<GoodsResp>       indexGoodsLiveData;
    public MutableLiveData<GoodsDetailResp> goodsDetailLiveData;

    public MutableLiveData<RemarkResp> remarkLiveData;

    public MutableLiveData<BaseResp>     collectionLiveData;
    public MutableLiveData<MerchantResp> merchantLiveData;

    private int page;

    public ServeViewModel() {
        sortLiveData = new MutableLiveData<>();
        indexGoodsLiveData = new MutableLiveData<>();
        goodsDetailLiveData = new MutableLiveData<>();
        remarkLiveData = new MutableLiveData<>();

        collectionLiveData = new MutableLiveData<>();

        merchantLiveData = new MutableLiveData<>();
    }

    public void secondSort(int pid) {
        request(api.sort(3, pid)).send(sortLiveData);
    }


    public void goodsByCate(int pid) {
        page = 1;
        request(api.goodsByCate(BaseApplication.sRegion, pid, String.valueOf(BaseApplication.sLongitude),
                String.valueOf(BaseApplication.sLatitude), page)).send(indexGoodsLiveData, page);
    }

    public void goodsByCateMore(int pid) {
        page++;
        request(api.goodsByCate(BaseApplication.sRegion, pid, String.valueOf(BaseApplication.sLongitude),
                String.valueOf(BaseApplication.sLatitude), page)).send(indexGoodsLiveData, page);
    }

    public void goodsDetail(int goodsId) {
        request(api.goodsDetail(goodsId)).send(goodsDetailLiveData);
    }


    public void orderRemarkList(int goodId) {
        page = 1;
        request(api.orderRemarkList(goodId, page)).send(remarkLiveData, page);
    }

    public void orderRemarkListMore(int goodId) {
        page++;
        request(api.orderRemarkList(goodId, page)).send(remarkLiveData, page);
    }


    public void collectionGoods(int goodId) {
        request(api.collectionGoods(goodId)).send(collectionLiveData);
    }

    public void merchantDetail(int merchantId) {
        request(api.merchantDetail(merchantId)).send(merchantLiveData);
    }
}
