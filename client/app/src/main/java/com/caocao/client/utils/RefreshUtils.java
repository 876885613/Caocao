package com.caocao.client.utils;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.utils
 * @ClassName: RefreshUtils
 * @Description: 上拉加载刷新数据工具
 * @Author: XuYu
 * @CreateDate: 2020/8/10 17:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/10 17:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RefreshUtils {
    //上拉加载刷新数据 分页工具
    public static void setNoMore(SmartRefreshLayout refresh, int page, int count) {
        if (page == 1) {
            if (count == 0) {
                if (refresh.getState() == RefreshState.None) {
                    refresh.setNoMoreData(true);
                } else {
                    refresh.finishRefreshWithNoMoreData();
                }
            } else {
                refresh.finishRefresh();
            }
        } else {
            if (count == 0) {
                refresh.finishLoadMoreWithNoMoreData();
            } else {
                refresh.finishLoadMore();
            }
        }
    }
}
