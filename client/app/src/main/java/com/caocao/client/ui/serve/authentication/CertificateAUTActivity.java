package com.caocao.client.ui.serve.authentication;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityCertificateAutBinding;
import com.caocao.client.http.entity.request.SettleApplyReq;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.GridImageAdapter;
import com.caocao.client.ui.image.GridImageOnItemClick;
import com.caocao.client.ui.image.UploadViewModel;
import com.caocao.client.ui.serve.ServeViewModel;
import com.caocao.client.utils.UploadImageUtils;
import com.caocao.client.utils.location.RxPermissionListener;
import com.caocao.client.utils.location.RxPermissionManager;
import com.luck.picture.lib.entity.LocalMedia;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.authentication
 * @ClassName: CertificateAUTActivity
 * @Description: 商家营业执照 认证
 * @Author: XuYu
 * @CreateDate: 2020/8/7 11:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/7 11:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CertificateAUTActivity extends BaseActivity implements RxPermissionListener {

    private ActivityCertificateAutBinding binding;


    private SettleApplyReq applyReq;
    private UploadImageUtils uploadImageUtils;
    private UploadViewModel uploadVM;
    private GridImageAdapter certificateAdapter;
    private ServeViewModel serveVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        uploadVM = getViewModel(UploadViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("商家认证")
                .builder();
    }

    @Override
    protected void initView() {
        uploadImageUtils = new UploadImageUtils(this, this, uploadVM);
        certificateView();
        binding.tvSubmit.setOnClickListener(v -> {
            if (StringUtils.isEmpty(applyReq.businessLicense)) {
                ToastUtils.showShort("请上传营业执照");
                return;
            }
            serveVM.apply(applyReq);
        });
    }


    private void certificateView() {
        certificateAdapter = uploadImageUtils.showPhoto(binding.rvBusinessCertificate, 1);

        certificateAdapter.setOnItemClickListener(new GridImageOnItemClick(certificateAdapter, uploadImageUtils, 1));
    }


    @Override
    protected void initData() {
        applyReq = getParcelableExtra("apply");

        serveVM = getViewModel(ServeViewModel.class);

        uploadVM.uploadLiveData.observe(this, uploadRes -> {
            LocalMedia localMedia = new LocalMedia();
            localMedia.setPath(uploadRes.getData().uploadUrl);
            certificateAdapter.addData(localMedia);
            applyReq.businessLicense = uploadRes.getData().uploadUrl;
        });


        serveVM.baseLiveData.observe(this, applyReq -> {
            ActivityUtils.startActivity(CommitAuditActivity.class);
        });
    }

    @Override
    public View initLayout() {
        binding = ActivityCertificateAutBinding.inflate(getLayoutInflater());
        return binding.getRoot();
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
