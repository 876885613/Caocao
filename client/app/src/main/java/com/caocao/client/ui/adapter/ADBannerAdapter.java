package com.caocao.client.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.caocao.client.R;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.adapter
 * @ClassName: ADBannerAdapter
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/4 16:13
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/4 16:13
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ADBannerAdapter extends BannerAdapter<Integer, ADBannerAdapter.BannerViewHolder> {
    public Context context;

    public ADBannerAdapter(Context context, List<Integer> beanList) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(beanList);
        this.context = context;
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, Integer item, int position, int size) {
        Glide.with(context)
                .load(item)
                .into(holder.imageView);
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }
}
