package com.caocao.client.ui.image;

import android.view.View;

import com.caocao.client.ui.adapter.GridImageAdapter;
import com.caocao.client.utils.UploadImageUtils;

public class GridImageOnItemClick implements GridImageAdapter.OnItemClickListener {
    private final UploadImageUtils uploadImageUtils;
    private GridImageAdapter adapter;
    private int source;

    public GridImageOnItemClick(GridImageAdapter adapter, UploadImageUtils uploadImageUtils, int source) {
        this.adapter = adapter;
        this.source = source;
        this.uploadImageUtils = uploadImageUtils;
    }

    @Override
    public void onTakePhotoClick(View view, int position) {
        uploadImageUtils.photoSelect(source);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemDelClick(View view, int position) {
        adapter.getData().remove(position);
        adapter.notifyDataSetChanged();
    }
}

