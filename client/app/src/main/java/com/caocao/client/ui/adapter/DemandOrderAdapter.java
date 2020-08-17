package com.caocao.client.ui.adapter;

import androidx.annotation.Nullable;

import com.caocao.client.R;
import com.caocao.client.http.entity.respons.DemandOrderResp;
import com.caocao.client.view.SuperTextView;
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
public class DemandOrderAdapter extends BaseQuickAdapter<DemandOrderResp, BaseViewHolder> {

    public DemandOrderAdapter(int layoutResId, @Nullable List<DemandOrderResp> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DemandOrderResp item) {

        SuperTextView sortView = helper.getView(R.id.stv_sort);
        sortView.getLeftTextView().setText(item.parentCateName);

        helper.setText(R.id.tv_cate_name, item.cateName);
        helper.setText(R.id.tv_due_stats, mContext.getString(R.string.demand_due_stats, item.surplusTime, item.invitedCount));
        helper.setText(R.id.tv_address, mContext.getString(R.string.demand_address, item.province, item.city, item.area, item.address));
        helper.setText(R.id.tv_contact, mContext.getString(R.string.demand_contact, item.contactPerson));
        helper.setText(R.id.tv_tel, mContext.getString(R.string.demand_tel, item.mobile));
        helper.setText(R.id.serve_time, mContext.getString(R.string.demand_serve_time, item.serviceTime));


        switch (item.status) {
            case 1:
                sortView.getRightTextView().setText("已完成");
                helper.setBackgroundRes(R.id.tv_state, R.drawable.bg_round_5_b3b3b3);
                helper.setText(R.id.tv_state, "删除订单");
                btnGone(helper,true,false,false);
                break;
            case 2:
                sortView.getRightTextView().setText("待支付");
                btnGone(helper,false,true,true);
                break;
            case 3:
                sortView.getRightTextView().setText("待接单");
                helper.setText(R.id.tv_state, "申请退款");
                btnGone(helper,true,false,false);
                break;
            case 4:
                sortView.getRightTextView().setText("待服务");
                helper.setText(R.id.tv_state, "支付尾款");
                btnGone(helper,true,false,false);
                break;
            case 5:
                sortView.getRightTextView().setText("退款中");
                btnGone(helper,false,false,false);
                break;
            case 6:
                sortView.getRightTextView().setText("退款成功");
                helper.setBackgroundRes(R.id.tv_state, R.drawable.bg_round_5_b3b3b3);
                helper.setText(R.id.tv_state, "删除订单");
                btnGone(helper,true,false,false);
                break;
        }
    }

    private void btnGone(BaseViewHolder helper,boolean state,boolean cancel,boolean pay){
        helper.setGone(R.id.tv_state, state);
        helper.setGone(R.id.tv_cancel, cancel);
        helper.setGone(R.id.tv_pay, pay);
    }
}
