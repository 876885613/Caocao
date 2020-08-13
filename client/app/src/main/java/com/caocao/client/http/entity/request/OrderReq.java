package com.caocao.client.http.entity.request;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class OrderReq {
    @SerializedName("goods_id")
    public int goodsId;
    @SerializedName("goods_spec_id")
    public int goodsSpecId;
    @SerializedName("goods_spec_price")
    public String goodsSpecPrice;
    @SerializedName("goods_spec_num")
    public int goodsSpecNum;

    @SerializedName("service_time")
    public Date serviceTime;
    @SerializedName("order_source")
    public int orderSource = 2;
    @SerializedName("address_id")
    public int addressId;
    @SerializedName("remark")
    public String remark;
}
