package com.caocao.client.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.request.RequestOptions;
import com.caocao.client.R;
import com.caocao.client.http.entity.BaseResp;
import com.caocao.client.http.entity.respons.GoodsResp;
import com.caocao.client.utils.DrawableUtils;
import com.caocao.client.weight.CornerTransform;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.adapter
 * @ClassName: ServeListAdapter
 * @Description: 服务列表Adapter
 * @Author: XuYu
 * @CreateDate: 2020/8/4 16:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/4 16:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ServeListAdapter extends BaseQuickAdapter<GoodsResp, BaseViewHolder> {
    public ServeListAdapter(int layoutResId, @Nullable List<GoodsResp> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsResp item) {
        Glide.with(mContext).load(item.showImage)
                . transform(new CornerTransform(mContext, 10))
                .<AppCompatImageView>into(helper.getView(R.id.iv_thumb));

        helper.setText(R.id.tv_source, item.merchantType == 1 ? "个人" : "商家");

        helper.setText(R.id.tv_title, item.goodsTitle);

        helper.setText(R.id.tv_cate_name, item.cateName);

        helper.setText(R.id.tv_serve_intro, item.goodsDetail);

        helper.setText(R.id.tv_price, mContext.getString(R.string.goods_price_qi, item.goodsPrice));
        helper.setText(R.id.tv_distance, mContext.getString(R.string.goods_distance, item.distance));


        GradientDrawable sourceDrawable = DrawableUtils.getDrawable(Color.parseColor("#49cbff"), ConvertUtils.dp2px(1));
        helper.getView(R.id.tv_source).setBackground(sourceDrawable);

        GradientDrawable serveDrawable = DrawableUtils.getDrawable(Color.parseColor("#ffede1"), ConvertUtils.dp2px(1));
        helper.getView(R.id.tv_cate_name).setBackground(serveDrawable);

        GradientDrawable introDrawable = DrawableUtils.getDrawable(Color.parseColor("#f5f5f5"), ConvertUtils.dp2px(1));
        helper.getView(R.id.tv_serve_intro).setBackground(introDrawable);
    }
}
