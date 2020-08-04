package com.caocao.client.http.entity.respons;

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
public class SortResp {

    public SortResp(int resId, String sort) {
        this.resId = resId;
        this.sort = sort;
    }

    public int resId;

    public String sort;

}
