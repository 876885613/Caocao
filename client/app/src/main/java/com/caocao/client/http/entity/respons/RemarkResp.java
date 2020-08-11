package com.caocao.client.http.entity.respons;

import com.caocao.client.http.entity.BaseResp;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.http.entity.respons
 * @ClassName: RemarkResp
 * @Description: 评论
 * @Author: XuYu
 * @CreateDate: 2020/8/11 10:41
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/11 10:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RemarkResp extends BaseResp<List<RemarkResp>> {
    @SerializedName("order_comment_id")
    public int          orderCommentId;
    @SerializedName("order_comment_content")
    public String       orderCommentContent;
    @SerializedName("comment_img")
    public Object commentImg;
    @SerializedName("order_comment_fraction")
    public int       orderCommentFraction;
    @SerializedName("order_comment_time")
    public String       orderCommentTime;
    public String       nickname;
    public String       headimgurl;
}
