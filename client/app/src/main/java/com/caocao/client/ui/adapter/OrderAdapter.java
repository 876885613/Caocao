package com.caocao.client.ui.adapter;

import androidx.annotation.Nullable;

import com.caocao.client.http.entity.BaseResp;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.adapter
 * @ClassName: OrderAdapter
 * @Description: 用户订单
 * @Author: XuYu
 * @CreateDate: 2020/8/7 15:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/7 15:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OrderAdapter extends BaseQuickAdapter<BaseResp, BaseViewHolder> {
    public OrderAdapter(int layoutResId, @Nullable List<BaseResp> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseResp item) {

    }
}
