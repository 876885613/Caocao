package com.caocao.client.ui.demand;

import com.caocao.client.http.entity.respons.SortResp;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.me.address
 * @ClassName: OnAddressCallBackListener
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/12 16:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/12 16:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface OnSortCallBackListener {
    void onSort(SortResp sort1, SortResp sort2, SortResp sort3);
}
