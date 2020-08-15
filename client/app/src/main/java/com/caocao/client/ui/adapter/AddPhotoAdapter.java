package com.caocao.client.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.caocao.client.R;

import java.util.List;

public class AddPhotoAdapter extends RecyclerView.Adapter<AddPhotoAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mList;
    private View mInflater;

    public AddPhotoAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(mContext).inflate(R.layout.adapter_add_photo_item, parent, false);
        ViewHolder holder = new ViewHolder(mInflater);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //将数据和控件绑定

    }

    @Override
    public int getItemCount() {
        return mList == null ? 1 : mList.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imageItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageItem = itemView.findViewById(R.id.iv_image);
        }


    }
}
