package com.caocao.client.http.api;

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

/**  使用示例

 //登录 OR 注册接口
 @FormUrlEncoded
 @POST("auth/register") Flowable<AppResponseBody < LoginBean>> register(
 @Field("mobile") String mobile,
 @Field("code") String code
 );

 //刷新Token
 @POST("auth/refresh") Flowable<AppResponseBody < LoginBean>> refreshToken();

 */
}
