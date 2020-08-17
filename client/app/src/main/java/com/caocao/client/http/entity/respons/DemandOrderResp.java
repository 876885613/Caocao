package com.caocao.client.http.entity.respons;

import com.caocao.client.http.entity.BaseResp;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.http.entity.respons
 * @ClassName: DemandOrderResp
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/17 11:56
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/17 11:56
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DemandOrderResp extends BaseResp<List<DemandOrderResp>> {


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
     * reserve_trade_sn : 4200000720202008107738627444
     * reserve_pay_price : 0.01
     * service_time : 2020-08-15 00:00:00
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
     * invited_count : 1
     * merchant_name : Api
     * surplus_time : -4
     */

    public int    id;
    @SerializedName("cate_id")
    public int    cateId;
    @SerializedName("merchant_id")
    public int    merchantId;
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
    @SerializedName("reserve_trade_sn")
    public String reserveTradeSn;
    @SerializedName("reserve_pay_price")
    public String reservePayPrice;
    @SerializedName("service_time")
    public String serviceTime;
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
    public String balancePayTime;
    @SerializedName("balance_pay_price")
    public String balancePayPrice;
    public int    status;
    @SerializedName("cate_name")
    public String cateName;
    @SerializedName("parent_cate_name")
    public String parentCateName;
    @SerializedName("invited_count")
    public int    invitedCount;
    @SerializedName("merchant_name")
    public String merchantName;
    @SerializedName("surplus_time")
    public int    surplusTime;

}
