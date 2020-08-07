package com.caocao.client.ui.authentication;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityCertificateAutBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.AddPhotoAdapter;

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
public class CertificateAUTActivity extends BaseActivity {

    private ActivityCertificateAutBinding binding;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("商家认证")
                .builder();
    }

    @Override
    protected void initView() {
        binding.rvBusinessCertificate.setLayoutManager(new GridLayoutManager(this, 3));
        AddPhotoAdapter certificateAdapter = new AddPhotoAdapter(this, null);
        binding.rvBusinessCertificate.setAdapter(certificateAdapter);

        binding.rvBusinessPermit.setLayoutManager(new GridLayoutManager(this, 3));
        AddPhotoAdapter permitAdapter = new AddPhotoAdapter(this, null);
        binding.rvBusinessPermit.setAdapter(permitAdapter);

        binding.tvSubmit.setOnClickListener(view -> ActivityUtils.startActivity(CommitAuditActivity.class));
    }

    @Override
    protected void initData() {

    }

    @Override
    public View initLayout() {
        binding = ActivityCertificateAutBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
