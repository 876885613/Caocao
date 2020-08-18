package com.caocao.client.http.entity.respons;

import com.caocao.client.http.entity.BaseResp;
import com.google.gson.annotations.SerializedName;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.http.entity.respons
 * @ClassName: PayInfoResp
 * @Description: 支付信息
 * @CreateDate: 2020/8/18 11:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/18 11:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PayInfoResp extends BaseResp<PayInfoResp> {


    /**
     * appid : wxcd6e2b9e6d6da467
     * partnerid : 1600657735
     * prepayid : wx18112126020020b67d7dee88bba1af0000
     * create_time : 2020-08-18 11:21:26
     * order_sn : 2020081811212584846344
     * price : 0.01
     * timestamp : 1597720886
     * noncestr : xZR9o4MqdZlS2qgx
     * package : Sign=WXPay
     * sign : 76daca88e15431b637f92b9de5162086
     */

    public String appid;
    public String partnerid;
    public String prepayid;
    @SerializedName("create_time")
    public String createTime;
    @SerializedName("order_sn")
    public String orderSn;
    public String price;
    public int    timestamp;
    public String noncestr;
    @SerializedName("package")
    public String packageX;
    public String sign;


}
