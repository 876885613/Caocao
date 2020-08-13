package com.caocao.client.ui.adapter;

import androidx.annotation.Nullable;

import com.caocao.client.R;
import com.caocao.client.http.entity.respons.GoodsDetailResp;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class OrderSpecAdapter extends BaseQuickAdapter<GoodsDetailResp.GoodsSpec, BaseViewHolder> {
    private int position;

    public OrderSpecAdapter(int layoutResId, @Nullable List<GoodsDetailResp.GoodsSpec> data) {
        super(layoutResId, data);
    }


    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailResp.GoodsSpec item) {
        helper.setText(R.id.tv_time, item.specName);
        helper.setText(R.id.tv_price, mContext.getString(R.string.goods_spec_price_unit_symbol, item.specPrice, item.specUnit));

        if (!(position == helper.getAdapterPosition())) {
            item.specNum = 0;
        }

        helper.setText(R.id.tv_spec_num, String.valueOf(item.specNum));

        helper.addOnClickListener(R.id.tv_minus, R.id.tv_add);
    }
}
