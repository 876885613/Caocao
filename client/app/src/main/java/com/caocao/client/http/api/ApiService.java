package com.caocao.client.http.api;

import com.caocao.client.http.entity.respons.BannerResp;
import com.caocao.client.http.entity.respons.GoodsResp;
import com.caocao.client.http.entity.respons.SortResp;

import io.reactivex.Flowable;
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
     * 首页分类
     */
    @POST("api/service_cate/getIndexCateList")
    Flowable<SortResp> homeSort();

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
            @Field("latitude") String latitude
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
}
