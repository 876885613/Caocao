package com.caocao.client.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.caocao.client.R;
import com.caocao.client.base.app.BaseApplication;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.adapter
 * @ClassName: GridImageAdapter
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/14 15:15
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/14 15:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@SuppressLint("InflateParams")
public class GridImageAdapter extends RecyclerView.Adapter<GridImageAdapter.ImageViewHolder> {

    private List<String> mData;
    private int          mCountLimit = 6;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onTakePhotoClick(View view, int position);
//        void onItemClick(View view, int position);

//        void onItemDelClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public GridImageAdapter(List<String> data) {
        this.mData = data;
        this.mCountLimit = 3;
    }

    public GridImageAdapter(List<String> data, int count) {
        this.mData = data;
        this.mCountLimit = count;
    }

    public GridImageAdapter(List<String> data, int count, OnItemClickListener onItemClickListener) {
        this.mData = data;
        this.mCountLimit = count;
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_add_photo_item, null);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        if (mData == null || (position == getItemCount() - 1 && mData.size() < mCountLimit)) {
            Glide.with(BaseApplication.getInstance())
                    .load("")
                    .placeholder(R.mipmap.ic_add_photo)
                    .error(R.mipmap.ic_add_photo)
                    .into(holder.imageView);

            holder.imageView.setOnClickListener(v -> mOnItemClickListener.onTakePhotoClick(v, position));
        } else {
            // 网络图片
            Glide.with(BaseApplication.getInstance())
                    .load(mData.get(position))
                    .placeholder(R.mipmap.ic_default_image)
                    .error(R.mipmap.ic_default_image)
                    .into(holder.imageView);
        }
    }

    /**
     * 添加并更新数据
     */
    public void setNewData(List<String> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // 满 6张图就不让其添加新图
        if (mData != null && mData.size() >= mCountLimit) {
            return mCountLimit;
        } else {
            return mData == null ? 1 : mData.size() + 1;
        }
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        private ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);
        }
    }
}
