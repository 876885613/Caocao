package com.caocao.client.http.api;

import com.caocao.client.http.entity.BaseResp;
import com.caocao.client.http.entity.request.DemandReq;
import com.caocao.client.http.entity.request.OrderReq;
import com.caocao.client.http.entity.request.SettleApplyReq;
import com.caocao.client.http.entity.respons.AddressResp;
import com.caocao.client.http.entity.respons.AppUpgradeResp;
import com.caocao.client.http.entity.respons.ApplyStatusResp;
import com.caocao.client.http.entity.respons.BannerResp;
import com.caocao.client.http.entity.respons.DemandOrderDetailResp;
import com.caocao.client.http.entity.respons.DemandOrderResp;
import com.caocao.client.http.entity.respons.GoodsDetailResp;
import com.caocao.client.http.entity.respons.GoodsResp;
import com.caocao.client.http.entity.respons.LoginResp;
import com.caocao.client.http.entity.respons.MerchantResp;
import com.caocao.client.http.entity.respons.PayInfoResp;
import com.caocao.client.http.entity.respons.RemarkResp;
import com.caocao.client.http.entity.respons.ServeOrderDetailResp;
import com.caocao.client.http.entity.respons.ServeOrderResp;
import com.caocao.client.http.entity.respons.SiteInfoResp;
import com.caocao.client.http.entity.respons.SortResp;
import com.caocao.client.http.entity.respons.UploadResp;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @ProjectName: matata-android-students
 * @Package: com.sjjy_android_student.http.api
 * @ClassName: ApiServise
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/5/19 16:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/19 16:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface ApiService {

    /**
     * 使用示例
     * <p>
     * //登录 OR 注册接口
     *
     * @FormUrlEncoded
     * @POST("auth/register") Flowable<AppResponseBody < LoginBean>> register(
     * @Field("mobile") String mobile,
     * @Field("code") String code
     * );
     * <p>
     * //刷新Token
     * @POST("auth/refresh") Flowable<AppResponseBody < LoginBean>> refreshToken();
     */

    /**
     * 首页轮播图
     */
    @POST("/api/banner/getIndexBannerList")
    Flowable<BannerResp> homeBanner();


    /**
     * 分类
     */
    @FormUrlEncoded
    @POST("/api/service_cate/getIndexCateList")
    Flowable<SortResp> sort(
            @Field("level") int level,
            @Field("pid") int pid
    );

    /**
     * 首页精选到家
     */
    @FormUrlEncoded
    @POST("/api/goods/getChoiceGoodsList")
    Flowable<GoodsResp> homeChoiceGoods(
            @Field("region") String region,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude
    );

    /**
     * 首页附近服务
     */
    @FormUrlEncoded
    @POST("/api/goods/getIndexGoodsList")
    Flowable<GoodsResp> homeIndexGoods(
            @Field("region") String region,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("page") int page
    );

    /**
     * 首页搜索
     */
    @FormUrlEncoded
    @POST("/api/goods/searchGoods")
    Flowable<GoodsResp> homeSearchGoods(
            @Field("region") String region,
            @Field("keyword") String keyword,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("page") int page
    );

    /**
     * 二级分类服务列表
     */
    @FormUrlEncoded
    @POST("/api/goods/getGoodsByCate")
    Flowable<GoodsResp> goodsByCate(
            @Field("region") String region,
            @Field("cate_id") int cateId,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("page") int page
    );

    /**
     * 商品详情
     *
     * @param goodsId
     * @return
     */
    @FormUrlEncoded
    @POST("/api/goods/getGoodsDetail")
    Flowable<GoodsDetailResp> goodsDetail(@Field("goods_id") int goodsId);


    /**
     * 评论列表
     *
     * @param goodsId
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("/api/order_comment/getOrderCommentList")
    Flowable<RemarkResp> orderRemarkList(
            @Field("goods_id") int goodsId,
            @Field("page") int page
    );

    /**
     * 收藏服务 或 取消收藏服务
     *
     * @param goodsId
     * @return
     */
    @FormUrlEncoded
    @POST("/api/customer/collectionGoods")
    Flowable<BaseResp> collectionGoods(@Field("goods_id") int goodsId);

    /**
     * 商户详情
     *
     * @param merchantId
     * @return
     */
    @FormUrlEncoded
    @POST("/api/merchant/merchantDetail")
    Flowable<MerchantResp> merchantDetail(@Field("merchant_id") int merchantId);

    /**
     * 一级分类
     *
     * @return
     */
    @POST("/api/service_cate/getCateList")
    Flowable<SortResp> cateList();

    /**
     * 地址列表
     *
     * @return
     */
    @POST("/api/customer_address/getAddressList")
    Flowable<AddressResp> addressList();

    /**
     * 编辑地址
     *
     * @param address
     * @return
     */
    @POST("/api/customer_address/createAddress")
    Flowable<BaseResp> editAddress(@Body AddressResp address);

    /**
     * 删除地址
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("/api/customer_address/delAddress")
    Flowable<BaseResp> deleteAddress(@Field("id") int id);

    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    @POST("/api/order/createOrder")
    Flowable<PayInfoResp> createOrder(@Body OrderReq order);

    /**
     * 发布需求
     *
     * @param demand
     * @return
     */
    @POST("/api/customer_demand/createDemand")
    Flowable<PayInfoResp> createDemand(@Body DemandReq demand);

    /**
     * 判断当前区域是否有加盟商
     *
     * @param region
     * @return
     */
    @FormUrlEncoded
    @POST("/api/agent/isAgentByAddress")
    Flowable<BaseResp> isAgentByAddress(@Field("region") String region);


    /**
     * 上传附件
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("/api/uploads/picture")
    Flowable<UploadResp> upload(@Part MultipartBody.Part file);

    /**
     * 申请入驻
     *
     * @param demand
     * @return
     */
    @POST("/api/merchant/apply")
    Flowable<BaseResp> apply(@Body SettleApplyReq demand);

    /**
     * 注册
     *
     * @param phone
     * @param code
     * @param password
     * @param repeatPassword
     * @return
     */
    @FormUrlEncoded
    @POST("/api/login/register")
    Flowable<BaseResp> register(
            @Field("phone") String phone,
            @Field("code") String code,
            @Field("password") String password,
            @Field("repeat_password") String repeatPassword
    );

    /**
     * 验证码
     *
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("/api/login/sendRegisterSms")
    Flowable<BaseResp> sendCode(@Field("phone") String phone);

    /**
     * 登录
     *
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("/api/login/login")
    Flowable<LoginResp> login(@Field("phone") String phone, @Field("password") String password);

    /**
     * 总部信息
     *
     * @return
     */
    @POST("/api/agent/siteInfo")
    Flowable<SiteInfoResp> siteInfo();

    /**
     * 加盟
     *
     * @param region
     * @param name
     * @param identity
     * @param mobile
     * @param comment
     * @return
     */
    @FormUrlEncoded
    @POST("/api/agent/agentApply")
    Flowable<BaseResp> agentApply(
            @Field("region") String region,
            @Field("name") String name,
            @Field("identity") int identity,
            @Field("mobile") String mobile,
            @Field("comment") String comment
    );


    /**
     * 订单列表
     *
     * @param status
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("/api/order/getOrderList")
    Flowable<ServeOrderResp> orderList(
            @Field("status") int status,
            @Field("page") int page
    );

    /**
     * 需求列表
     *
     * @param status
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("/api/customer_demand/getDemandList")
    Flowable<DemandOrderResp> demandList(
            @Field("status") int status,
            @Field("page") int page
    );

    /**
     * 订单详情
     *
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST("/api/order/orderDetail")
    Flowable<ServeOrderDetailResp> orderDetail(@Field("order_id") int orderId);

    /**
     * 需求详情
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("/api/customer_demand/demandDetail")
    Flowable<DemandOrderDetailResp> demandDetail(@Field("id") int id);

    /**
     * 取消订单
     *
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST("/api/order/cancelOrder")
    Flowable<BaseResp> cancelOrder(@Field("order_id") int orderId);

    /**
     * 重新支付
     *
     * @param orderId
     * @param orderSource
     * @return
     */
    @FormUrlEncoded
    @POST("/api/order/reloadOrder")
    Flowable<PayInfoResp> reloadOrder(@Field("order_id") int orderId, @Field("order_source") int orderSource);

    /**
     * 完成订单
     *
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST("/api/order/finishOrder")
    Flowable<BaseResp> finishOrder(@Field("order_id") int orderId);


    /**
     * 订单评价
     *
     * @param orderId
     * @param goodsId
     * @param content
     * @param fraction
     * @param commentImg
     * @return
     */
    @FormUrlEncoded
    @POST("/api/order_comment/createOrderComment")
    Flowable<BaseResp> createOrderComment(
            @Field("order_id") int orderId,
            @Field("goods_id") int goodsId,
            @Field("content") String content,
            @Field("fraction") int fraction,
            @Field("comment_img") String commentImg
    );

    /**
     * 重新支付
     *
     * @param id
     * @param orderSource
     * @return
     */
    @FormUrlEncoded
    @POST("/api/customer_demand/reloadDemand")
    Flowable<PayInfoResp> reloadDemand(@Field("id") int id, @Field("order_source") int orderSource);

    /**
     * 支付尾款
     *
     * @param id
     * @param expectedPrice
     * @param orderSource
     * @return
     */
    @FormUrlEncoded
    @POST("/api/customer_demand/balancePayDemand")
    Flowable<PayInfoResp> balancePayDemand(
            @Field("id") int id,
            @Field("expected_price") String expectedPrice,
            @Field("order_source") int orderSource
    );

    /**
     * 取消需求
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("/api/customer_demand/cancelDemand")
    Flowable<BaseResp> cancelDemand(@Field("id") int id);

    /**
     * 需求退款
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("/api/customer_demand/refundDemand")
    Flowable<BaseResp> refundDemand(@Field("id") int id);


    /**
     * 订单退款
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST("/api/order/orderRefund")
    Flowable<BaseResp> orderRefund(@Field("order_id") int orderId);

    /**
     * 申请状态
     *
     * @return
     */
    @POST("/api/merchant/applyStatus")
    Flowable<ApplyStatusResp> applyStatus();

    /**
     * 收藏列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/api/customer/getCollectionGoodsList")
    Flowable<GoodsResp> collectionGoodsList(
            @Field("page") int page,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude
    );

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param password
     * @param repeatPassword
     * @return
     */
    @FormUrlEncoded
    @POST("/api/customer/updatePassword")
    Flowable<BaseResp> updatePassword(
            @Field("old_password") String oldPassword,
            @Field("password") String password,
            @Field("repeat_password") String repeatPassword
    );

    /**
     * 忘记密码验证码
     *
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("/api/login/sendRetrievePasswordSms")
    Flowable<BaseResp> sendRetrievePasswordSms(@Field("phone") String phone);

    /**
     * 忘记密码
     *
     * @param phone
     * @param code
     * @param password
     * @param repeatPassword
     * @return
     */
    @FormUrlEncoded
    @POST("/api/login/retrievePassword")
    Flowable<BaseResp> retrievePassword(
            @Field("phone") String phone,
            @Field("code") String code,
            @Field("password") String password,
            @Field("repeat_password") String repeatPassword
    );

    /**
     * APP版本升级
     */
    @POST("/api/package/upgrade")
    Flowable<AppUpgradeResp> upgrade();
}
