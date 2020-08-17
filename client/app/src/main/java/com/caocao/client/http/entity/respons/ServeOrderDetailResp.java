package com.caocao.client.http.entity.respons;

import com.caocao.client.http.entity.BaseResp;
import com.google.gson.annotations.SerializedName;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.http.entity.respons
 * @ClassName: OrderDetailResp
 * @Description: 订单详情
 * @Author:
 * @CreateDate: 2020/8/17 15:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/17 15:58
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ServeOrderDetailResp extends BaseResp<ServeOrderDetailResp> {

    /**
     * order_id : 111
     * merchant_id : 13
     * goods_id : 17
     * goods_spec_num : 1
     * goods_spec_price : 0.01
     * order_sn : 202008091624134210736
     * order_price : 0.01
     * order_create_time : 2020-08-09 16:24:13
     * service_time : 2020-08-09 18:00:00
     * pay_time : 2020-08-09 16:24:26
     * pay_price : 0.01
     * order_finish_time : 2020-08-14 19:31:31
     * order_remark : 
     * order_status : 4
     * order_name : 测试
     * order_phone : 13222222222
     * order_province : 北京市
     * order_city : 北京市
     * order_area : 东城区
     * order_detail : 天安门广场
     * goods_title : 沙发清洗
     * show_image : http://ccdj.jiajiayong.com/merchant_uploads/20200716/b3cd69a80cff91e38d0198c7774bac5f.png
     * merchant_name : Api
     * spec_name : 新人
     * spec_unit : 次
     * cate_name : 沙发保养
     * comment_status : 0
     */
    @SerializedName("order_id")
    public int orderId;
    @SerializedName("merchant_id")
    public int    merchantId;
    @SerializedName("goods_id")
    public int    goodsId;
    @SerializedName("goods_spec_num")
    public int    goodsSpecNum;
    @SerializedName("goods_spec_price")
    public String goodsSpecPrice;
    @SerializedName("order_sn")
    public String orderSn;
    @SerializedName("order_price")
    public String orderPrice;
    @SerializedName("order_create_time")
    public String orderCreateTime;
    @SerializedName("service_time")
    public String serviceTime;
    @SerializedName("pay_time")
    public String payTime;
    @SerializedName("pay_price")
    public String payPrice;
    @SerializedName("order_finish_time")
    public String orderFinishTime;
    @SerializedName("order_remark")
    public String orderRemark;
    @SerializedName("order_status")
    public int    orderStatus;
    @SerializedName("order_name")
    public String orderName;
    @SerializedName("order_phone")
    public String orderPhone;
    @SerializedName("order_province")
    public String orderProvince;
    @SerializedName("order_city")
    public String orderCity;
    @SerializedName("order_area")
    public String orderArea;
    @SerializedName("order_detail")
    public String orderDetail;
    @SerializedName("goods_title")
    public String goodsTitle;
    @SerializedName("show_image")
    public String showImage;
    @SerializedName("merchant_name")
    public String merchantName;
    @SerializedName("spec_name")
    public String specName;
    @SerializedName("spec_unit")
    public String specUnit;
    @SerializedName("cate_name")
    public String cateName;
    @SerializedName("comment_status")
    public int    commentStatus;

}
