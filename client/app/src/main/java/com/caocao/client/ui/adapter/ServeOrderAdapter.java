package com.caocao.client.ui.adapter;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.caocao.client.R;
import com.caocao.client.http.entity.respons.ServeOrderResp;
import com.caocao.client.view.SuperTextView;
import com.caocao.client.weight.CornerTransform;
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
public class ServeOrderAdapter extends BaseQuickAdapter<ServeOrderResp, BaseViewHolder> {


    public ServeOrderAdapter(int layoutResId, @Nullable List<ServeOrderResp> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, ServeOrderResp item) {

        SuperTextView sortView = helper.getView(R.id.stv_sort);
        sortView.getLeftTextView().setText(item.goodsTitle);

        Glide.with(mContext).load(item.showImage)
                .transform(new CornerTransform(mContext, SizeUtils.dp2px(2)))
                .<AppCompatImageView>into(helper.getView(R.id.iv_thumb));

        helper.setText(R.id.tv_spec_name, item.goodsSpecName);
        helper.setText(R.id.tv_spec_price_unit, mContext.getString(R.string.goods_spec_price_unit_symbol,
                item.goodsSpecPrice, item.goodsSpecUnit));
        helper.setText(R.id.tv_order_sn, mContext.getString(R.string.order_sn, item.orderSn));
        helper.setText(R.id.tv_order_time, mContext.getString(R.string.order_time, item.orderCreateTime));
        helper.setText(R.id.tv_price, mContext.getString(R.string.goods_price, item.orderPrice));
        switch (item.status) {
            case 1:
                sortView.getRightTextView().setText("已完成");
                if (item.commentStatus == 0) {
                    helper.setText(R.id.tv_state, "待评价");
                    helper.setGone(R.id.tv_state, true);
                }
                break;
            case 2:
                sortView.getRightTextView().setText("待支付");
                helper.setText(R.id.tv_cancel, "取消订单");
                helper.setGone(R.id.tv_cancel, true);
                helper.setGone(R.id.tv_pay, true);
                break;
            case 3:
                sortView.getRightTextView().setText("待接单");
                break;
            case 4:
                sortView.getRightTextView().setText("待服务");
                break;
            case 5:
                sortView.getRightTextView().setText("待退款");
                break;
            case 6:
                sortView.getRightTextView().setText("已退款");
                break;
        }
    }
}
