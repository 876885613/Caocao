package com.caocao.client.http.entity.respons;

import com.caocao.client.http.entity.BaseResp;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.http.entity.respons
 * @ClassName: OrderResp
 * @Description: 订单
 * @Author:
 * @CreateDate: 2020/8/17 10:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/17 10:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ServeOrderResp extends BaseResp<List<ServeOrderResp>> {

    /**
     * merchant_id : 13
     * goods_id : 1
     * goods_spec_id : 1
     * order_sn : 20200702174824606701
     * order_price : 15.99
     * goods_spec_num : 1
     * goods_spec_price : 15.99
     * order_create_time : 2020-07-02 17:48:24
     * service_time : 2020-07-02 19:00:00
     * merchant_name : 测试
     * goods_title : 测试商品1
     * goods_spec_name : 规格1
     */
    @SerializedName("order_id")
    public int orderId;
    @SerializedName("merchant_id")
    public int    merchantId;
    @SerializedName("goods_id")
    public int    goodsId;
    @SerializedName("goods_spec_id")
    public int    goodsSpecId;
    @SerializedName("order_sn")
    public String orderSn;
    @SerializedName("order_price")
    public String orderPrice;
    @SerializedName("goods_spec_num")
    public int    goodsSpecNum;
    @SerializedName("goods_spec_price")
    public String goodsSpecPrice;
    @SerializedName("order_create_time")
    public String orderCreateTime;
    @SerializedName("service_time")
    public String serviceTime;
    @SerializedName("merchant_name")
    public String merchantName;
    @SerializedName("goods_title")
    public String goodsTitle;
    @SerializedName("goods_spec_name")
    public String goodsSpecName;
    @SerializedName("goods_spec_unit")
    public String goodsSpecUnit;
    @SerializedName("show_image")
    public String showImage;
    @SerializedName("comment_status")
    public int commentStatus;
    public int status;
}
