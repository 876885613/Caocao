package com.caocao.client.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.caocao.client.R;
import com.caocao.client.base.app.BaseApplication;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
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

    private List<LocalMedia> mData;
    private int mCountLimit = 6;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onTakePhotoClick(View view, int position);

        void onItemClick(View view, int position);

        void onItemDelClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public GridImageAdapter(List<LocalMedia> data) {
        this(data, 3);
    }

    public GridImageAdapter(List<LocalMedia> data, int count) {
        this(data, count, null);
    }

    public GridImageAdapter(List<LocalMedia> data, int count, OnItemClickListener onItemClickListener) {
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
            holder.delView.setVisibility(View.INVISIBLE);
            holder.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            holder.imageView.setOnClickListener(v -> mOnItemClickListener.onTakePhotoClick(v, position));
        } else {
            holder.delView.setVisibility(View.VISIBLE);
            // 网络图片
            Glide.with(BaseApplication.getInstance())
                    .load(mData.get(position).getPath())
                    .error(R.mipmap.ic_default_image)
                    .into(holder.imageView);

            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            holder.imageView.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, position));

            holder.delView.setOnClickListener(v -> mOnItemClickListener.onItemDelClick(v, position));
        }
    }

    /**
     * 添加并更新数据
     */
    public void setNewData(List<LocalMedia> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void addData(LocalMedia data) {
        if (mData == null) {
            mData = new ArrayList<>();
            mData.add(data);
        } else {
            mData.add(data);
        }
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
        private AppCompatImageView imageView;
        private AppCompatImageView delView;

        private ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);

            delView = itemView.findViewById(R.id.iv_del);
        }
    }

    public List<LocalMedia> getData() {
        return mData;
    }
}
