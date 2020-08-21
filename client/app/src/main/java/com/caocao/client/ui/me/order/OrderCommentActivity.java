package com.caocao.client.ui.me.order;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityOrderCommentBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.GridImageAdapter;
import com.caocao.client.ui.image.UploadViewModel;
import com.caocao.client.ui.me.MeViewModel;
import com.caocao.client.ui.wrapper.TextWatcherWrapper;
import com.caocao.client.utils.location.RxPermissionListener;
import com.caocao.client.utils.location.RxPermissionManager;
import com.coder.baselibrary.dialog.OnClickListenerWrapper;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.ArrayList;
import java.util.List;

public class OrderCommentActivity extends BaseActivity implements RxPermissionListener {

    private ActivityOrderCommentBinding binding;

    private UploadViewModel  uploadVM;
    private GridImageAdapter imageAdapter;
    private MeViewModel      meVM;

    private String content;
    private int    goodsId;
    private int    orderId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uploadVM.initPermission(this, this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("发表评论")
                .builder();
    }

    @Override
    protected void initView() {


        binding.rvImage.setLayoutManager(new GridLayoutManager(this, 3));
        imageAdapter = new GridImageAdapter(null, 3);
        imageAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onTakePhotoClick(View view, int position) {
                photoSelect();
            }

            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemDelClick(View view, int position) {
                imageAdapter.getData().remove(position);
                imageAdapter.notifyDataSetChanged();
            }
        });
        binding.rvImage.setAdapter(imageAdapter);

        binding.etContent.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                content = s.toString();
            }
        });

        binding.tvRelease.setOnClickListener(new OnClickListenerWrapper() {
            String commentImg;

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClickCall(View v) {
                int fraction = (int) binding.rating.getRating();
                if (fraction == 0) {
                    ToastUtils.showShort("请选择服务质量");
                    return;
                }
                if (imageAdapter.getData() != null) {
                    List<String> imageList = new ArrayList<>();
                    for (LocalMedia localMedia : imageAdapter.getData()) {
                        imageList.add(localMedia.getPath());
                    }
                    commentImg = String.join(",", imageList);
                }
                meVM.createOrderComment(orderId, goodsId, content, fraction, commentImg);
            }
        });
    }

    @Override
    protected void initData() {
        goodsId = getIntValue("goodsId");
        orderId = getIntValue("orderId");

        uploadVM = getViewModel(UploadViewModel.class);

        meVM = getViewModel(MeViewModel.class);


        uploadVM.uploadLiveData.observe(this, uploadRes -> {
            LocalMedia localMedia = new LocalMedia();
            localMedia.setPath(uploadRes.getData().uploadUrl);
            imageAdapter.addData(localMedia);
        });

        meVM.baseLiveData.observe(this, commentResp -> {
            finish();
        });

    }

    @Override
    public View initLayout() {
        binding = ActivityOrderCommentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    private void photoSelect() {
        boolean granted = uploadVM.isCameraGranted();
        if (!granted) {
            uploadVM.initPermission(this, this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return;
        }
        uploadVM.pictureSelection(this, new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
                String path = null;
                int skdCode = DeviceUtils.getSDKVersionCode();

                if (skdCode > 28) {
                    path = result.get(0).getAndroidQToPath();
                } else {
                    path = result.get(0).getPath();
                }

                uploadVM.uploadPhoto(path);
            }

            @Override
            public void onCancel() {
            }
        });
    }

    @Override
    public void accept() {

    }

    @Override
    public void refuse() {

    }

    @Override
    public void noAsk(String permissionName) {
        RxPermissionManager.showPermissionDialog(this, permissionName);
    }
}
