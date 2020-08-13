package com.caocao.client.ui.adapter;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.caocao.client.R;
import com.caocao.client.http.entity.respons.SortResp;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.adapter
 * @ClassName: LevelAdapter
 * @Description: 更多服务 第一级类型
 * @Author: XuYu
 * @CreateDate: 2020/8/11 17:29
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/11 17:29
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ServeLevelAdapter extends BaseQuickAdapter<SortResp, BaseViewHolder> {
    private int position;

    public ServeLevelAdapter(int layoutResId, @Nullable List<SortResp> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, SortResp item) {
        helper.setText(R.id.cb_level, item.cateName);
        helper.setChecked(R.id.cb_level, position == helper.getAdapterPosition());

        LogUtils.e(helper.getAdapterPosition() );
    }

    public void setPosition(int position) {
        this.position = position;
        notifyDataSetChanged();
    }
}
