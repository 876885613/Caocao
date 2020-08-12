package com.caocao.client.http.entity.respons;

import android.os.Parcel;
import android.os.Parcelable;

import com.caocao.client.http.entity.BaseResp;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.http.entity.respons
 * @ClassName: GoodsDetailResp
 * @Description: 商品详情
 * @Author: XuYu
 * @CreateDate: 2020/8/10 16:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/10 16:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class GoodsDetailResp extends BaseResp<GoodsDetailResp> implements Parcelable{

    /**
     * goods_id : 66
     * goods_title : 健康小吃
     * goods_price : 0.10
     * show_image : http://ccdj.jiajiayong.com/merchant_uploads/20200803/e5094fb17ddd91109ef0bda7dc592a51.jpg
     * goods_tags : 餐饮
     * goods_detail : 健康小吃
     * merchant_id : 18
     * banner_image : []
     * goods_detail_image : <p>好吃不贵</p>
     * merchant_name : 冰粉水果捞
     * merchant_type : 1
     * username : 刘瑶瑶
     * merchant_detail : 10元自选冰粉
     * merchant_tag : 餐饮
     * merchant_image : http://ccdj.jiajiayong.com/uploads/thumb20200802173926549.jpeg
     * merchant_province : 山东省
     * merchant_city : 济南市
     * merchant_district : 历下区
     * address_detail : 工业北路81号
     * cate_name : 生活配送
     * goods_spec : []
     * collection_status : 0
     */

    @SerializedName("goods_id")
    public int             goodsId;
    @SerializedName("goods_title")
    public String          goodsTitle;
    @SerializedName("goods_price")
    public String          goodsPrice;
    @SerializedName("show_image")
    public String          showImage;
    @SerializedName("goods_tags")
    public String          goodsTags;
    @SerializedName("goods_detail")
    public String          goodsDetail;
    @SerializedName("merchant_id")
    public int             merchantId;
    @SerializedName("goods_detail_image")
    public String          goodsDetailImage;
    @SerializedName("merchant_name")
    public String          merchantName;
    @SerializedName("merchant_type")
    public int             merchantType;
    public String          username;
    @SerializedName("merchant_detail")
    public String          merchantDetail;
    @SerializedName("merchant_tag")
    public String          merchantTag;
    @SerializedName("merchant_image")
    public String          merchantImage;
    @SerializedName("merchant_province")
    public String          merchantProvince;
    @SerializedName("merchant_city")
    public String          merchantCity;
    @SerializedName("merchant_district")
    public String          merchantDistrict;
    @SerializedName("address_detail")
    public String          addressDetail;
    @SerializedName("cate_name")
    public String          cateName;
    @SerializedName("collection_status")
    public int             collectionStatus;
    @SerializedName("banner_image")
    public List<String>    bannerImage;
    @SerializedName("goods_spec")
    public List<GoodsSpec> goodsSpec;

    public static class GoodsSpec implements Parcelable {
        public int    id;
        @SerializedName("goods_id")
        public int    goodsId;
        @SerializedName("spec_name")
        public String specName;
        @SerializedName("spec_price")
        public String specPrice;
        @SerializedName("spec_unit")
        public String specUnit;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.goodsId);
            dest.writeString(this.specName);
            dest.writeString(this.specPrice);
            dest.writeString(this.specUnit);
        }

        public GoodsSpec() {
        }

        protected GoodsSpec(Parcel in) {
            this.id = in.readInt();
            this.goodsId = in.readInt();
            this.specName = in.readString();
            this.specPrice = in.readString();
            this.specUnit = in.readString();
        }

        public static final Creator<GoodsSpec> CREATOR = new Creator<GoodsSpec>() {
            @Override
            public GoodsSpec createFromParcel(Parcel source) {
                return new GoodsSpec(source);
            }

            @Override
            public GoodsSpec[] newArray(int size) {
                return new GoodsSpec[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.goodsId);
        dest.writeString(this.goodsTitle);
        dest.writeString(this.goodsPrice);
        dest.writeString(this.showImage);
        dest.writeString(this.goodsTags);
        dest.writeString(this.goodsDetail);
        dest.writeInt(this.merchantId);
        dest.writeString(this.goodsDetailImage);
        dest.writeString(this.merchantName);
        dest.writeInt(this.merchantType);
        dest.writeString(this.username);
        dest.writeString(this.merchantDetail);
        dest.writeString(this.merchantTag);
        dest.writeString(this.merchantImage);
        dest.writeString(this.merchantProvince);
        dest.writeString(this.merchantCity);
        dest.writeString(this.merchantDistrict);
        dest.writeString(this.addressDetail);
        dest.writeString(this.cateName);
        dest.writeInt(this.collectionStatus);
        dest.writeStringList(this.bannerImage);
        dest.writeList(this.goodsSpec);
    }

    public GoodsDetailResp() {
    }

    protected GoodsDetailResp(Parcel in) {
        this.goodsId = in.readInt();
        this.goodsTitle = in.readString();
        this.goodsPrice = in.readString();
        this.showImage = in.readString();
        this.goodsTags = in.readString();
        this.goodsDetail = in.readString();
        this.merchantId = in.readInt();
        this.goodsDetailImage = in.readString();
        this.merchantName = in.readString();
        this.merchantType = in.readInt();
        this.username = in.readString();
        this.merchantDetail = in.readString();
        this.merchantTag = in.readString();
        this.merchantImage = in.readString();
        this.merchantProvince = in.readString();
        this.merchantCity = in.readString();
        this.merchantDistrict = in.readString();
        this.addressDetail = in.readString();
        this.cateName = in.readString();
        this.collectionStatus = in.readInt();
        this.bannerImage = in.createStringArrayList();
        this.goodsSpec = new ArrayList<GoodsSpec>();
        in.readList(this.goodsSpec, GoodsSpec.class.getClassLoader());
    }

    public static final Creator<GoodsDetailResp> CREATOR = new Creator<GoodsDetailResp>() {
        @Override
        public GoodsDetailResp createFromParcel(Parcel source) {
            return new GoodsDetailResp(source);
        }

        @Override
        public GoodsDetailResp[] newArray(int size) {
            return new GoodsDetailResp[size];
        }
    };
}
