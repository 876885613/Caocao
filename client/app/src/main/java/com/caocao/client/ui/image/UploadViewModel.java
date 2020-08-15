package com.caocao.client.ui.image;

import android.Manifest;
import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.base.app.BaseApplication;
import com.caocao.client.http.BaseViewModel;
import com.caocao.client.http.entity.respons.UploadResp;
import com.caocao.client.utils.image.GlideEngine;
import com.caocao.client.utils.location.RxPermissionListener;
import com.caocao.client.utils.location.RxPermissionManager;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class UploadViewModel extends BaseViewModel {

    public MutableLiveData<UploadResp> uploadLiveData;

    public UploadViewModel() {
        uploadLiveData = new MutableLiveData<>();
    }


    public PictureSelectionModel pictureSelection(BaseActivity activity, OnResultCallbackListener listener) {
        PictureSelectionModel pictureSelectionModel = PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(1);
        pictureSelectionModel.forResult(listener);
        return pictureSelectionModel;
    }


    public PictureSelectionModel pictureSelection(Fragment fragment, OnResultCallbackListener listener) {
        PictureSelectionModel pictureSelectionModel = PictureSelector.create(fragment)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(1);

        pictureSelectionModel.forResult(listener);

        return pictureSelectionModel;
    }


    public void initPermission(BaseActivity activity, RxPermissionListener listener, String... permissions) {
        RxPermissionManager.requestPermissions(activity, listener, permissions);
    }


    public void initPermission(Fragment fragment, RxPermissionListener listener, String... permissions) {
        RxPermissionManager.requestPermissions(fragment, listener, permissions);
    }


    public boolean isCameraGranted() {
        return PermissionUtils.isGranted(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @SuppressLint("CheckResult")
    public void uploadPhoto(String path) {
        Luban.with(BaseApplication.getInstance())
                .load(path)                                   // 传人要压缩的图片列表
                .ignoreBy(1024)                                  // 忽略不压缩图片的大小
                .setTargetDir(getPath())                        // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        upload(file);
                    }


                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e);
                    }
                }).launch();    //启动压缩

    }

    private String getPath() {
        String path = BaseApplication.getInstance().getFilesDir() + "/upload/image";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    private void upload(File file) {
        // 创建 RequestBody，用于封装 请求RequestBody
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        request(api.upload(body)).send(uploadLiveData);
    }
}
