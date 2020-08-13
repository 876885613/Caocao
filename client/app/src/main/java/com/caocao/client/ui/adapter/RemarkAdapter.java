package com.caocao.client.ui.adapter;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.caocao.client.R;
import com.caocao.client.http.entity.respons.RemarkResp;
import com.caocao.client.weight.CornerTransform;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.adapter
 * @ClassName: RemarkAdapter
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/11 11:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/11 11:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RemarkAdapter extends BaseQuickAdapter<RemarkResp, BaseViewHolder> {
    public RemarkAdapter(int layoutResId, @Nullable List<RemarkResp> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RemarkResp item) {

        Glide.with(mContext).load(item.headimgurl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .error(R.mipmap.ic_default_portrait)
                .placeholder(R.mipmap.ic_default_portrait)
                .<AppCompatImageView>into(helper.getView(R.id.iv_portrait));

        helper.setText(R.id.tv_name, item.nickname);

        showRating(helper.getView(R.id.iv_rating), item.orderCommentFraction);

        helper.setText(R.id.tv_remark_content, item.orderCommentContent);
        helper.setText(R.id.tv_remark_time, item.orderCommentTime);

        if (item.commentImg != null) {
           try {
               List<String> remarkImg = (List<String>) item.commentImg;
               helper.setVisible(R.id.iv_remark_img, true);
               Glide.with(mContext)
                       .load(remarkImg.get(0))
                       .placeholder(R.mipmap.ic_default_portrait)
                       .error(R.mipmap.ic_default_portrait)
                       .transform(new CornerTransform(mContext, SizeUtils.dp2px(7)))
                       .<AppCompatImageView>into(helper.getView(R.id.iv_remark_img));
           }catch (Exception e){
               e.printStackTrace();
           }
        }
    }

    private void showRating(AppCompatImageView view, int fraction) {
        if (fraction == 1) {
            view.setImageResource(R.mipmap.ic_rating1);
        }
        if (fraction == 2) {
            view.setImageResource(R.mipmap.ic_rating2);
        }
        if (fraction == 3) {
            view.setImageResource(R.mipmap.ic_rating3);
        }
        if (fraction == 4) {
            view.setImageResource(R.mipmap.ic_rating4);
        }
        if (fraction == 5) {
            view.setImageResource(R.mipmap.ic_rating5);
        }
    }

}
