package com.caocao.client.http.entity.respons;

import com.caocao.client.http.entity.BaseResp;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GoodsResp extends BaseResp<List<GoodsResp>> {

    /*
  {
    "code": 100,
    "data": {
      "goods_title": "商品名称",
      "goods_price": "商品展示价格",
      "show_image": "商品展示图",
      "tags": "商品标签",
      "merchant_name": "商户名称",
      "username": "商户姓名",
      "merchant_tag": "商户标签",
      "merchant_image": "商户门头照",
      "distance":"距离",
      "cate_name":"分类名称"
      "merchant_type":"商家类型"
    }
  }
 */

    @SerializedName("goods_id")
    public int goodsId;

    @SerializedName("goods_title")
    public String goodsTitle;

    @SerializedName("goods_price")
    public String goodsPrice;

    @SerializedName("show_image")
    public String showImage;

    public String tags;

    @SerializedName("merchant_name")
    public String merchantName;

    public String username;
    @SerializedName("merchant_tag")
    public String merchantTag;

    @SerializedName("merchant_image")
    public String merchantImage;

    public String distance;

    @SerializedName("cate_name")
    public String cateName;

    @SerializedName("merchant_type")
    public int merchantType;

    @SerializedName("goods_detail")
    public String goodsDetail;
}
