package com.caocao.client.ui.serve.authentication;

import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityCommitAuditBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.MainActivity;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.authentication
 * @ClassName: AUTAuditActivity
 * @Description: 提交商家认证审核
 * @Author: XuYu
 * @CreateDate: 2020/8/7 11:15
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/7 11:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CommitAuditActivity extends BaseActivity {


    private ActivityCommitAuditBinding binding;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("认证提交")
                .builder();
    }

    @Override
    protected void initView() {
        binding.tvBackHome.setOnClickListener(view -> {
            ActivityUtils.startActivity(MainActivity.class);
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    public View initLayout() {
        binding = ActivityCommitAuditBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
