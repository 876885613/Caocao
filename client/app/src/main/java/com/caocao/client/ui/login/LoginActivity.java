package com.caocao.client.ui.login;

import android.view.View;

import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityLoginBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.login
 * @ClassName: LoginActivity
 * @Description: 登录
 * @Author: XuYu
 * @CreateDate: 2020/8/11 14:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/11 14:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("登录")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initLayout() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
