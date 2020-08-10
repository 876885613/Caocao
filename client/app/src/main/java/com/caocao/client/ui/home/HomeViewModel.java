package com.caocao.client.ui.home;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.caocao.client.base.app.BaseApplication;
import com.caocao.client.http.BaseViewModel;
import com.caocao.client.http.entity.respons.BannerResp;
import com.caocao.client.http.entity.respons.GoodsResp;
import com.caocao.client.http.entity.respons.SortResp;

public class HomeViewModel extends BaseViewModel {
    public MutableLiveData<BannerResp> homeBannerLiveData;

    public MutableLiveData<SortResp> homeSortLiveData;

    public MutableLiveData<GoodsResp> homeChoiceGoodsLiveData;


    public MutableLiveData<GoodsResp> homeIndexGoodsLiveData;

    private int page;

    public HomeViewModel() {
        homeBannerLiveData = new MutableLiveData<>();

        homeSortLiveData = new MutableLiveData<>();

        homeChoiceGoodsLiveData = new MutableLiveData<>();

        homeIndexGoodsLiveData = new MutableLiveData<>();
    }

    public void homeBanner() {
        request(api.homeBanner()).send(homeBannerLiveData);
    }


    public void homeSort() {
        request(api.sort(2, 0)).send(homeSortLiveData);
    }

    public void homeChoiceGoods() {
        request(api.homeChoiceGoods(BaseApplication.sRegion,
                String.valueOf(BaseApplication.sLongitude), String.valueOf(BaseApplication.sLatitude))).send(homeChoiceGoodsLiveData);
    }

    public void homeIndexGoods() {
        page = 1;
        request(api.homeIndexGoods(BaseApplication.sRegion,
                String.valueOf(BaseApplication.sLongitude), String.valueOf(BaseApplication.sLatitude),page)).send(homeIndexGoodsLiveData, page);
    }

    public void homeIndexGoodsMore() {
        page++;
        request(api.homeIndexGoods(BaseApplication.sRegion,
                String.valueOf(BaseApplication.sLongitude), String.valueOf(BaseApplication.sLatitude),page)).send(homeIndexGoodsLiveData, page);
    }


    public void homeSearchGoods(String keyword) {
        page = 1;
        request(api.homeSearchGoods(BaseApplication.sRegion, keyword,
                String.valueOf(BaseApplication.sLongitude), String.valueOf(BaseApplication.sLatitude), page)).send(homeIndexGoodsLiveData, page);
    }

    public void homeSearchGoodsMore(String keyword) {
        page++;
        request(api.homeSearchGoods(BaseApplication.sRegion, keyword,
                String.valueOf(BaseApplication.sLongitude), String.valueOf(BaseApplication.sLatitude), page)).send(homeIndexGoodsLiveData, page);
    }
}
