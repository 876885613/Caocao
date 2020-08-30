package com.caocao.client.http.entity.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SettleApplyReq implements Parcelable {

    @SerializedName("cate_id")
    public int cateId;

    @SerializedName("goods_title")
    public String goodsTitle;

    @SerializedName("goods_detail")
    public String goodsDetail;

    @SerializedName("merchant_province")
    public String merchantProvince;

    @SerializedName("merchant_city")
    public String merchantCity;

    @SerializedName("merchant_district")
    public String merchantDistrict;

    @SerializedName("address_detail")
    public String addressDetail;

    @SerializedName("show_image")
    public String showImage;

    @SerializedName("banner_image")
    public String bannerImage;

    @SerializedName("detail_image")
    public String detailImage;

    @SerializedName("merchant_name")
    public String merchantName;

    public String username;

    @SerializedName("merchant_phone")
    public String merchantPhone;


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


//    public List<Spec> spec;
    public String spec;

    public static class Spec implements Parcelable {
        @SerializedName("spec_name")
        public String specName;
        @SerializedName("spec_price")
        public String specPrice;
        @SerializedName("spec_unit")
        public String specUnit;
        @SerializedName("spec_image")
        public String specImage;


        @Override
        public String toString() {
            return "Spec{" +
                    "specName='" + specName + '\'' +
                    ", specPrice='" + specPrice + '\'' +
                    ", specUnit='" + specUnit + '\'' +
                    ", specImage='" + specImage + '\'' +
                    '}';
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.specName);
            dest.writeString(this.specPrice);
            dest.writeString(this.specUnit);
            dest.writeString(this.specImage);
        }

        public Spec() {
        }

        protected Spec(Parcel in) {
            this.specName = in.readString();
            this.specPrice = in.readString();
            this.specUnit = in.readString();
            this.specImage = in.readString();
        }

        public static final Creator<Spec> CREATOR = new Creator<Spec>() {
            @Override
            public Spec createFromParcel(Parcel source) {
                return new Spec(source);
            }

            @Override
            public Spec[] newArray(int size) {
                return new Spec[size];
            }
        };
    }


    @Override
    public String toString() {
        return "SettleApplyReq{" +
                "merchantName='" + merchantName + '\'' +
                ", username='" + username + '\'' +
                ", merchantPhone='" + merchantPhone + '\'' +
                ", merchantProvince='" + merchantProvince + '\'' +
                ", merchantCity='" + merchantCity + '\'' +
                ", merchantDistrict='" + merchantDistrict + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", cateId=" + cateId +
                ", type=" + type +
                ", cardNumber='" + cardNumber + '\'' +
                ", merchantPassword='" + merchantPassword + '\'' +
                ", idcardImage='" + idcardImage + '\'' +
                ", merchantImage='" + merchantImage + '\'' +
                ", merchantPhoto='" + merchantPhoto + '\'' +
                ", businessLicense='" + businessLicense + '\'' +
                ", businessHours='" + businessHours + '\'' +
                ", consumerHotline='" + consumerHotline + '\'' +
                ", goodsTitle='" + goodsTitle + '\'' +
                ", goodsDetail='" + goodsDetail + '\'' +
                ", showImage='" + showImage + '\'' +
                ", bannerImage='" + bannerImage + '\'' +
                ", detailImage='" + detailImage + '\'' +
                ", spec=" + spec +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cateId);
        dest.writeString(this.goodsTitle);
        dest.writeString(this.goodsDetail);
        dest.writeString(this.merchantProvince);
        dest.writeString(this.merchantCity);
        dest.writeString(this.merchantDistrict);
        dest.writeString(this.addressDetail);
        dest.writeString(this.showImage);
        dest.writeString(this.bannerImage);
        dest.writeString(this.detailImage);
        dest.writeString(this.merchantName);
        dest.writeString(this.username);
        dest.writeString(this.merchantPhone);
        dest.writeInt(this.type);
        dest.writeString(this.cardNumber);
        dest.writeString(this.merchantPassword);
        dest.writeString(this.idcardImage);
        dest.writeString(this.merchantImage);
        dest.writeString(this.merchantPhoto);
        dest.writeString(this.businessLicense);
        dest.writeString(this.businessHours);
        dest.writeString(this.consumerHotline);
        dest.writeString(this.spec);
    }

    public SettleApplyReq() {
    }

    protected SettleApplyReq(Parcel in) {
        this.cateId = in.readInt();
        this.goodsTitle = in.readString();
        this.goodsDetail = in.readString();
        this.merchantProvince = in.readString();
        this.merchantCity = in.readString();
        this.merchantDistrict = in.readString();
        this.addressDetail = in.readString();
        this.showImage = in.readString();
        this.bannerImage = in.readString();
        this.detailImage = in.readString();
        this.merchantName = in.readString();
        this.username = in.readString();
        this.merchantPhone = in.readString();
        this.type = in.readInt();
        this.cardNumber = in.readString();
        this.merchantPassword = in.readString();
        this.idcardImage = in.readString();
        this.merchantImage = in.readString();
        this.merchantPhoto = in.readString();
        this.businessLicense = in.readString();
        this.businessHours = in.readString();
        this.consumerHotline = in.readString();
        this.spec = in.readString();
    }

    public static final Creator<SettleApplyReq> CREATOR = new Creator<SettleApplyReq>() {
        @Override
        public SettleApplyReq createFromParcel(Parcel source) {
            return new SettleApplyReq(source);
        }

        @Override
        public SettleApplyReq[] newArray(int size) {
            return new SettleApplyReq[size];
        }
    };
}
