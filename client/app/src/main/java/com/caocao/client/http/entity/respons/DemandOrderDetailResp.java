package com.caocao.client.http.entity.respons;

import com.caocao.client.http.entity.BaseResp;
import com.google.gson.annotations.SerializedName;

public class DemandOrderDetailResp extends BaseResp<DemandOrderDetailResp> {

    /**
     * id : 7
     * cate_id : 50
     * merchant_id : 13
     * order_sn : 202008101408084841036
     * reserve_price : 0.01
     * expected_price : 100
     * start_time : 2020-08-10 14:08:08
     * end_time : 2020-08-13 00:00:00
     * demand_depict : 1111
     * service_time : 2020-08-15 00:00:00
     * reserve_trade_sn : 4200000720202008107738627444
     * reserve_pay_price : 0.01
     * contact_person : 徐文强
     * mobile : 13220699017
     * province : 山东省
     * city : 济南市
     * area : 历城区
     * address : 产学研基地
     * reserve_pay_time : 2020-08-10 14:08:40
     * balance_pay_time : null
     * balance_pay_price : null
     * status : 4
     * cate_name : 沙发保养
     * parent_cate_name : 曹操家政
     * invited_count : 2
     * merchant_name : Api
     * consumer_hotline : 2313
     * merchant_province : 山东省
     * merchant_city : 济南市
     * merchant_district : 历城区
     * address_detail : 汇源大厦
     * surplus_time : -4
     */

    public int id;
    @SerializedName("cate_id")
    public int cateId;
    @SerializedName("merchant_id")
    public int merchantId;
    @SerializedName("order_sn")
    public String orderSn;
    @SerializedName("reserve_price")
    public String reservePrice;
    @SerializedName("expected_price")
    public String expectedPrice;
    @SerializedName("start_time")
    public String startTime;
    @SerializedName("end_time")
    public String endTime;
    @SerializedName("demand_depict")
    public String demandDepict;
    @SerializedName("service_time")
    public String serviceTime;
    @SerializedName("reserve_trade_sn")
    public String reserveTradeSn;
    @SerializedName("reserve_pay_price")
    public String reservePayPrice;
    @SerializedName("contact_person")
    public String contactPerson;

    public String mobile;
    public String province;
    public String city;
    public String area;
    public String address;
    @SerializedName("reserve_pay_time")
    public String reservePayTime;
    @SerializedName("balance_pay_time")
    public Object balancePayTime;
    @SerializedName("balance_pay_price")
    public Object balancePayPrice;
    public int status;
    @SerializedName("cate_name")
    public String cateName;
    @SerializedName("parent_cate_name")
    public String parentCateName;
    @SerializedName("invited_count")
    public int invitedCount;
    @SerializedName("merchant_name")
    public String merchantName;
    @SerializedName("consumer_hotline")
    public String consumerHotline;
    @SerializedName("merchant_province")
    public String merchantProvince;
    @SerializedName("merchant_city")
    public String merchantCity;
    @SerializedName("merchant_district")
    public String merchantDistrict;
    @SerializedName("address_detail")
    public String addressDetail;
    @SerializedName("surplus_time")
    public int surplusTime;




}
