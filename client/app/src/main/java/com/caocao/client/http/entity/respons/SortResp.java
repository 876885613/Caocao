package com.caocao.client.http.entity.respons;

import com.caocao.client.http.entity.BaseResp;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.http.entity.respons
 * @ClassName: SortResp
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/4 11:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/4 11:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SortResp extends BaseResp<List<SortResp>> {

    public SortResp(int id, String cateName, int resId) {
        this.id = id;
        this.cateName = cateName;
        this.resId = resId;
    }

    public int resId;


    public int id;

    @SerializedName("cate_name")
    public String cateName;

    @SerializedName("cate_icon")
    public String cateIcon;
}
