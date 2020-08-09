package com.caocao.client.http.entity.respons;

import com.caocao.client.http.entity.BaseResp;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BannerResp extends BaseResp<List<BannerResp>> {
    @SerializedName("banner_name")
    public String bannerName;
    @SerializedName("banner_img")
    public String bannerImg;
}
