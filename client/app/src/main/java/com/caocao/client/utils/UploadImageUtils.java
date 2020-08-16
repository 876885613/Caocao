package com.caocao.client.utils;

import android.Manifest;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.base.app.BaseApplication;
import com.caocao.client.ui.adapter.GridImageAdapter;
import com.caocao.client.ui.image.UploadViewModel;
import com.caocao.client.utils.location.RxPermissionListener;
import com.caocao.client.weight.DividerItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.List;

public class UploadImageUtils {

    private BaseActivity mActivity;

    private RxPermissionListener mListener;

    private UploadViewModel mUploadVM;


    private int mSource;

    public UploadImageUtils(BaseActivity activity, RxPermissionListener listener, UploadViewModel uploadVM) {
        this.mActivity = activity;
        this.mListener = listener;
        this.mUploadVM = uploadVM;
        uploadVM.initPermission(activity, listener, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


    public GridImageAdapter showPhoto(RecyclerView recyclerView, int count) {
        recyclerView.setLayoutManager(new GridLayoutManager(BaseApplication.getInstance(), 3));
        recyclerView.addItemDecoration(new DividerItemDecoration(BaseApplication.getInstance(),
                DividerItemDecoration.VERTICAL_LIST, 0, 5));
        GridImageAdapter imageAdapter = new GridImageAdapter(null, count);

        recyclerView.setAdapter(imageAdapter);
        return imageAdapter;
    }


    public void photoSelect(int source) {
        boolean granted = mUploadVM.isCameraGranted();
        if (!granted) {
            mUploadVM.initPermission(mActivity, mListener, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return;
        }
        mUploadVM.pictureSelection(mActivity, new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
                String path = result.get(0).getPath();
                if (!StringUtils.isEmpty(path)) {
                    UploadImageUtils.this.mSource = source;
                    mUploadVM.uploadPhoto(path);
                }
            }

            @Override
            public void onCancel() {
            }
        });
    }


    public int getSource() {
        return mSource;
    }
}
