package com.caocao.client.http.entity.respons;

import com.caocao.client.http.entity.BaseResp;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.http.entity.respons
 * @ClassName: MerchantResp
 * @Description: 商户信息
 * @Author: XuYu
 * @CreateDate: 2020/8/11 15:51
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/11 15:51
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MerchantResp extends BaseResp<MerchantResp> {


    /**
     * merchant : {"merchant_name":"冰粉水果捞","merchant_image":"http://ccdj.jiajiayong.com/uploads/thumb20200802173926549.jpeg","merchant_photo":[],"merchant_detail":"10元自选冰粉","business_hours":"19:00~23:00","consumer_hotline":"15216400112","merchant_province":"山东省","merchant_type":1,"merchant_city":"济南市","merchant_district":"历下区","address_detail":"工业北路81号"}
     * goods_list : [{"goods_id":66,"goods_title":"健康小吃","show_image":"http://ccdj.jiajiayong.com/merchant_uploads/20200803/e5094fb17ddd91109ef0bda7dc592a51.jpg","goods_price":"0.10","goods_detail":"健康小吃","goods_tags":"餐饮","cate_name":"生活配送"}]
     */
    public MerchantDetailsResp merchant;
    @SerializedName("goods_list")
    public List<GoodsResp> goodsList;

    public static class MerchantDetailsResp {
        /**
         * merchant_name : 冰粉水果捞
         * merchant_image : http://ccdj.jiajiayong.com/uploads/thumb20200802173926549.jpeg
         * merchant_photo : []
         * merchant_detail : 10元自选冰粉
         * business_hours : 19:00~23:00
         * consumer_hotline : 15216400112
         * merchant_province : 山东省
         * merchant_type : 1
         * merchant_city : 济南市
         * merchant_district : 历下区
         * address_detail : 工业北路81号
         */
        @SerializedName("merchant_name")
        public String merchantName;
        @SerializedName("merchant_image")
        public String  merchantImage;
        @SerializedName("merchant_detail")
        public String  merchantDetail;
        @SerializedName("business_hours")
        public String  businessHours;
        @SerializedName("consumer_hotline")
        public String  consumerHotline;
        @SerializedName("merchant_province")
        public String  merchantProvince;
        @SerializedName("merchant_type")
        public int     merchantType;
        @SerializedName("merchant_city")
        public String  merchantCity;
        @SerializedName("merchant_district")
        public String  merchantDistrict;
        @SerializedName("address_detail")
        public String  addressDetail;
        @SerializedName("merchant_photo")
        public List<String> merchantPhoto;
    }


}
