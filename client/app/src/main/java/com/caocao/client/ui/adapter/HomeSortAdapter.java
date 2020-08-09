package com.caocao.client.ui.adapter;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.caocao.client.R;
import com.caocao.client.http.entity.respons.SortResp;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.adapter
 * @ClassName: HomeSortAdapter
 * @Description: 首页分类Adapter
 * @Author: XuYu
 * @CreateDate: 2020/8/4 11:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/4 11:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HomeSortAdapter extends BaseQuickAdapter<SortResp, BaseViewHolder> {
    public HomeSortAdapter(int layoutResId, @Nullable List<SortResp> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SortResp item) {
        if (!StringUtils.isEmpty(item.cateIcon)) {
            Glide.with(mContext).load(item.cateIcon).<AppCompatImageView>into(helper.getView(R.id.iv_sort));
        } else {
            helper.setImageResource(R.id.iv_sort, item.resId);
        }
        helper.setText(R.id.tv_sort, item.cateName);
    }
}
