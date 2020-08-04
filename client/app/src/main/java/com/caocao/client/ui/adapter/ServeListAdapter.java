package com.caocao.client.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ConvertUtils;
import com.caocao.client.R;
import com.caocao.client.http.entity.BaseResp;
import com.caocao.client.utils.DrawableUtils;
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
public class ServeListAdapter extends BaseQuickAdapter<BaseResp, BaseViewHolder> {
    public ServeListAdapter(int layoutResId, @Nullable List<BaseResp> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseResp item) {
        GradientDrawable sourceDrawable = DrawableUtils.getDrawable(Color.parseColor("#49cbff"), ConvertUtils.dp2px(1));
        helper.getView(R.id.tv_source).setBackground(sourceDrawable);

        GradientDrawable serveDrawable = DrawableUtils.getDrawable(Color.parseColor("#ffede1"), ConvertUtils.dp2px(1));
        helper.getView(R.id.tv_serve_bg).setBackground(serveDrawable);

        GradientDrawable introDrawable = DrawableUtils.getDrawable(Color.parseColor("#f5f5f5"), ConvertUtils.dp2px(1));
        helper.getView(R.id.tv_serve_intro).setBackground(introDrawable);
    }
}
