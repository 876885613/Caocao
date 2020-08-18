package com.caocao.client.ui.login;

import android.text.Editable;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityLoginBinding;
import com.caocao.client.http.entity.respons.LoginResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.wrapper.TextWatcherWrapper;

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
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;
    private LoginViewModel loginVM;
    private String tel;
    private String password;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("登录")
                .builder();
    }

    @Override
    protected void initView() {

        binding.etTel.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                tel = s.toString();
            }
        });

        binding.etPassword.addTextChangedListener(new TextWatcherWrapper() {

            @Override
            public void afterTextChanged(Editable s) {
                password = s.toString();
            }
        });

        binding.tvRegister.setOnClickListener(this);
        binding.tvLogin.setOnClickListener(this);
        binding.tvRetrievePassword.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        loginVM = getViewModel(LoginViewModel.class);

        loginVM.loginLiveData.observe(this, loginRes -> {
            LoginResp login = loginRes.getData();
            SPStaticUtils.put("token", login.token);
            SPStaticUtils.put("nickname", login.nickname);
            SPStaticUtils.put("headimgurl", login.headimgurl);
            SPStaticUtils.put("phone", login.phone);
            finish();
        });
    }

    @Override
    public View initLayout() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                ActivityUtils.startActivity(RegisterActivity.class);
                break;
            case R.id.tv_login:
                if (!binding.cbAgreement.isChecked()) {
                    ToastUtils.showShort("您未同意当前协议");
                    return;
                }
                loginVM.login(tel, password);
                break;
            case R.id.tv_retrieve_password:
                ActivityUtils.startActivity(RetrievePasswordActivity.class);
                break;
        }
    }
}
