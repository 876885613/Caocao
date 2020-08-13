package com.caocao.client.http.entity.request;

import com.google.gson.annotations.SerializedName;

public class SettleApplyReq {
    @SerializedName("merchant_name")
    public String merchantName;

    public String username;

    @SerializedName("merchant_phone")
    public String merchantPhone;

    @SerializedName("merchant_province")
    public String merchantProvince;

    @SerializedName("merchant_city")
    public String merchantCity;

    @SerializedName("merchant_district")
    public String merchantDistrict;

    @SerializedName("address_detail")
    public String addressDetail;

    @SerializedName("cate_id")
    public int cateId;

    @SerializedName("type")
    public int type;

    @SerializedName("card_number")
    public String cardNumber;

    @SerializedName("merchant_password")
    public String merchantPassword;

    @SerializedName("idcard_image")
    public String idcardImage;

    @SerializedName("merchant_image")
    public String merchantImage;

    @SerializedName("merchant_photo")
    public String merchantPhoto;

    @SerializedName("business_license")
    public String businessLicense;

    @SerializedName("business_hours")
    public String businessHours;

    @SerializedName("consumer_hotline")
    public String consumerHotline;

    @SerializedName("goods_title")
    public String goodsTitle;

    @SerializedName("goods_detail")
    public String goodsDetail;

    @SerializedName("show_image")
    public String showImage;

    @SerializedName("banner_image")
    public String bannerImage;

    @SerializedName("detail_image")
    public String detailImage;

    public Spec spec;

    public class Spec {
        @SerializedName("spec_name")
        public String specName;
        @SerializedName("spec_price")
        public String specPrice;
        @SerializedName("spec_unit")
        public String specUnit;
        @SerializedName("spec_image")
        public String specImage;
    }
}
