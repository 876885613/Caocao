package com.caocao.client.ui.adapter;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.caocao.client.R;
import com.caocao.client.http.entity.respons.GoodsResp;
import com.caocao.client.weight.CornerTransform;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class HomeSiftAdapter extends BaseQuickAdapter<GoodsResp, BaseViewHolder> {
    public HomeSiftAdapter(int layoutResId, @Nullable List<GoodsResp> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsResp item) {
        Glide.with(mContext).load(item.showImage)
                .transform(new CornerTransform(mContext, SizeUtils.dp2px(3)))
                .<AppCompatImageView>into(helper.getView(R.id.iv_image));
        helper.setText(R.id.tv_cate_name, item.goodsTitle);
    }
}
