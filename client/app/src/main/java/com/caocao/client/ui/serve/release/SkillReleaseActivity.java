package com.caocao.client.ui.serve.release;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.base.app.BaseApplication;
import com.caocao.client.databinding.ActivitySkillReleaseBinding;
import com.caocao.client.http.entity.request.SettleApplyReq;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.AddPhotoAdapter;
import com.caocao.client.ui.adapter.GridImageAdapter;
import com.caocao.client.ui.bean.CheckBean;
import com.caocao.client.ui.image.UploadViewModel;
import com.caocao.client.ui.serve.ServeViewModel;
import com.caocao.client.ui.serve.authentication.CommitAuditActivity;
import com.caocao.client.ui.wrapper.TextWatcherWrapper;
import com.caocao.client.utils.CheckNotNullUtils;
import com.caocao.client.utils.location.RxPermissionListener;
import com.caocao.client.utils.location.RxPermissionManager;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.caocao.client.ui.bean.CheckBean.CheckType.CARD_IMAGE;
import static com.caocao.client.ui.bean.CheckBean.CheckType.ID_CARD;
import static com.caocao.client.ui.bean.CheckBean.CheckType.PHONE;
import static com.caocao.client.ui.bean.CheckBean.CheckType.SERVE_PASSWORD;

public class SkillReleaseActivity extends BaseActivity implements View.OnClickListener, RxPermissionListener {

    private ActivitySkillReleaseBinding binding;


    private SettleApplyReq applyReq;
    private UploadViewModel uploadVM;



    private int IMAGE_SOURCE;

    private List<String> cardImgList = new ArrayList<>();
    private ServeViewModel serveVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uploadVM.initPermission(this, this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle("发布技能").builder();
    }

    @Override
    protected void initView() {
        binding.stvContacts.setRightEditTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                applyReq.username = s.toString();
            }
        });

        binding.stvTel.setRightEditTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                applyReq.merchantPhone = s.toString();
            }
        });

        binding.stvPassword.setRightEditTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                applyReq.merchantPassword = s.toString();
            }
        });

        binding.layoutIdCard.stvIdCard.setRightEditTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                applyReq.cardNumber = s.toString();
            }
        });

        binding.layoutIdCard.ivFront.setOnClickListener(this);
        binding.layoutIdCard.ivReverse.setOnClickListener(this);
        binding.tvRelease.setOnClickListener(this);

        binding.rvPhoto.setLayoutManager(new GridLayoutManager(this, 3));
        AddPhotoAdapter addAdapter = new AddPhotoAdapter(this, null);
        binding.rvPhoto.setAdapter(addAdapter);


        qualificationView();
    }

    private void qualificationView() {
        binding.rvPhoto.setLayoutManager(new GridLayoutManager(this, 3));
        GridImageAdapter addQualificationAdapter = new GridImageAdapter(null, 1);
        addQualificationAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onTakePhotoClick(View view, int position) {
                photoSelect(3);
            }

            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemDelClick(View view, int position) {
                addQualificationAdapter.setNewData(null);
            }
        });
        binding.rvPhoto.setAdapter(addQualificationAdapter);
    }

    @Override
    protected void initData() {
        applyReq = getParcelableExtra("apply");
        uploadVM = getViewModel(UploadViewModel.class);

        serveVM = getViewModel(ServeViewModel.class);

        uploadVM.uploadLiveData.observe(this, uploadRes -> {
            LogUtils.e(uploadRes.getData().uploadUrl);
            String url = uploadRes.getData().uploadUrl;
            if (IMAGE_SOURCE == 1) {
                Glide.with(this).load(BaseApplication.HOST_PATH + url).error(R.mipmap.ic_id_card_front).into(binding.layoutIdCard.ivFront);
                cardImgList.add(0, url);
            } else if (IMAGE_SOURCE == 2) {
                Glide.with(this).load(BaseApplication.HOST_PATH + url).error(R.mipmap.ic_id_card_reverse).into(binding.layoutIdCard.ivReverse);
                cardImgList.add(1, url);
            } else if (IMAGE_SOURCE == 3) {
                applyReq.businessLicense = url;
            }
        });

        serveVM.baseLiveData.observe(this, applyReq -> {
            ActivityUtils.startActivity(CommitAuditActivity.class);
        });
    }

    @Override
    public View initLayout() {
        binding = ActivitySkillReleaseBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_front:
                photoSelect(1);
                break;
            case R.id.iv_reverse:
                photoSelect(2);
                break;
            case R.id.tv_release:
                release();
                break;
        }
    }

    @SuppressLint("NewApi")
    private void release() {

        String cardImage = String.join(",", cardImgList);
        applyReq.idcardImage = cardImage;

        Map<CheckBean, String> checkParam = new LinkedHashMap();
        checkParam.put(new CheckBean(applyReq.username), "请填写联系人姓名");
        checkParam.put(new CheckBean(PHONE,applyReq.merchantPhone), "请填写正确的联系人电话");
        checkParam.put(new CheckBean(SERVE_PASSWORD, applyReq.merchantPassword), "请填写密码并且不能少于6位");
        checkParam.put(new CheckBean(ID_CARD, applyReq.cardNumber), "请填写正确的身份证号");
        checkParam.put(new CheckBean(CARD_IMAGE, applyReq.idcardImage), "请上传身份证正反面");

        String msg = CheckNotNullUtils.checkNotNull(checkParam);
        if (!StringUtils.isEmpty(msg)) {
            ToastUtils.showShort(msg);
            return;
        }

        serveVM.apply(applyReq);
    }


    private void photoSelect(int type) {
        boolean granted = uploadVM.isCameraGranted();
        if (!granted) {
            uploadVM.initPermission(this, this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return;
        }
        uploadVM.pictureSelection(this, new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
                SkillReleaseActivity.this.IMAGE_SOURCE = type;

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
