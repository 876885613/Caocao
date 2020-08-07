package com.caocao.client.ui.authentication;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityStoreAutBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.AddPhotoAdapter;
import com.caocao.client.ui.serve.AddSpecActivity;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.authentication
 * @ClassName: StoreAUTActivity
 * @Description: 商店信息 认证
 * @Author: XuYu
 * @CreateDate: 2020/8/7 10:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/7 10:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class StoreAUTActivity extends BaseActivity {

    private ActivityStoreAutBinding binding;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("商家认证")
                .builder();
    }

    @Override
    protected void initView() {
        binding.rvPhoto.setLayoutManager(new GridLayoutManager(this, 3));
        AddPhotoAdapter addAdapter = new AddPhotoAdapter(this, null);
        binding.rvPhoto.setAdapter(addAdapter);
        binding.tvNext.setOnClickListener(view -> ActivityUtils.startActivity(CertificateAUTActivity.class));
    }

    @Override
    protected void initData() {

    }

    @Override
    public View initLayout() {
        binding = ActivityStoreAutBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
