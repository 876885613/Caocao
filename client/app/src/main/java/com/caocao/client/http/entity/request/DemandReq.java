package com.caocao.client.http.entity.request;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DemandReq {
    @SerializedName("cate_id")
    public int cateId;
    @SerializedName("reserve_price")
    public String reservePrice;
    @SerializedName("expected_price")
    public String expectedPrice;
    @SerializedName("demand_depict")
    public String demandDepict;
    @SerializedName("end_time")
    public Date endTime;
    @SerializedName("service_time")
    public Date serviceTime;
    @SerializedName("contact_person")
    public String contactPerson;
    public String mobile;
    public String province;
    public String city;
    public String area;
    public String address;
    @SerializedName("order_source")
    public int orderSource = 2;
}
