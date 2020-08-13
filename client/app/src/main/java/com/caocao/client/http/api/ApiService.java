package com.caocao.client.http.api;

import com.caocao.client.http.entity.BaseResp;
import com.caocao.client.http.entity.request.DemandReq;
import com.caocao.client.http.entity.request.OrderReq;
import com.caocao.client.http.entity.respons.AddressResp;
import com.caocao.client.http.entity.respons.BannerResp;
import com.caocao.client.http.entity.respons.GoodsDetailResp;
import com.caocao.client.http.entity.respons.GoodsResp;
import com.caocao.client.http.entity.respons.MerchantResp;
import com.caocao.client.http.entity.respons.RemarkResp;
import com.caocao.client.http.entity.respons.SortResp;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
    @POST("api/banner/getIndexBannerList")
    Flowable<BannerResp> homeBanner();


    /**
     * 分类
     */
    @FormUrlEncoded
    @POST("api/service_cate/getIndexCateList")
    Flowable<SortResp> sort(
            @Field("level") int level,
            @Field("pid") int pid
    );

    /**
     * 首页精选到家
     */
    @FormUrlEncoded
    @POST("api/goods/getChoiceGoodsList")
    Flowable<GoodsResp> homeChoiceGoods(
            @Field("region") String region,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude
    );

    /**
     * 首页附近服务
     */
    @FormUrlEncoded
    @POST("api/goods/getIndexGoodsList")
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
    @POST("api/goods/searchGoods")
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
    @POST("api/goods/getGoodsByCate")
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
    @POST("api/goods/getGoodsDetail")
    Flowable<GoodsDetailResp> goodsDetail(@Field("goods_id") int goodsId);


    /**
     * 评论列表
     *
     * @param goodsId
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("api/order_comment/getOrderCommentList")
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
    @POST("api/customer/collectionGoods")
    Flowable<BaseResp> collectionGoods(@Field("goods_id") int goodsId);

    /**
     * 商户详情
     *
     * @param merchantId
     * @return
     */
    @FormUrlEncoded
    @POST("api/merchant/merchantDetail")
    Flowable<MerchantResp> merchantDetail(@Field("merchant_id") int merchantId);

    /**
     * 一级分类
     *
     * @return
     */
    @POST("api/service_cate/getCateList")
    Flowable<SortResp> cateList();

    /**
     * 地址列表
     *
     * @return
     */
    @POST("api/customer_address/getAddressList")
    Flowable<AddressResp> addressList();

    /**
     * 编辑地址
     *
     * @param address
     * @return
     */
    @POST("api/customer_address/createAddress")
    Flowable<BaseResp> editAddress(@Body AddressResp address);

    /**
     * 删除地址
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("api/customer_address/delAddress")
    Flowable<BaseResp> deleteAddress(@Field("id") int id);

    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    @POST("api/order/createOrder")
    Flowable<BaseResp> createOrder(@Body OrderReq order);

    @POST("api/customer_demand/createDemand")
    Flowable<BaseResp> createDemand(@Body DemandReq demand);
}
