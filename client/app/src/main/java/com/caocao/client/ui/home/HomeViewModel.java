package com.caocao.client.ui.home;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPStaticUtils;
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
        request(api.homeChoiceGoods(SPStaticUtils.getString("region",""),
                SPStaticUtils.getString("longitude",""), SPStaticUtils.getString("latitude", ""))).send(homeChoiceGoodsLiveData);
    }

    public void homeIndexGoods() {
        page = 1;
        request(api.homeIndexGoods(SPStaticUtils.getString("region",""),
                SPStaticUtils.getString("longitude",""), SPStaticUtils.getString("latitude", ""),page)).send(homeIndexGoodsLiveData, page);
    }

    public void homeIndexGoodsMore() {
        page++;
        request(api.homeIndexGoods(SPStaticUtils.getString("region",""),
                SPStaticUtils.getString("longitude",""), SPStaticUtils.getString("latitude", ""),page)).send(homeIndexGoodsLiveData, page);
    }


    public void homeSearchGoods(String keyword) {
        page = 1;
        request(api.homeSearchGoods(SPStaticUtils.getString("region",""), keyword,
                SPStaticUtils.getString("longitude",""), SPStaticUtils.getString("latitude", ""), page)).send(homeIndexGoodsLiveData, page);
    }

    public void homeSearchGoodsMore(String keyword) {
        page++;
        request(api.homeSearchGoods(SPStaticUtils.getString("region",""), keyword,
                SPStaticUtils.getString("longitude",""), SPStaticUtils.getString("latitude", ""), page)).send(homeIndexGoodsLiveData, page);
    }
}
