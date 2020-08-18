package com.caocao.client.ui.me;

import android.text.Editable;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityAlterPasswordBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.login.LoginActivity;
import com.caocao.client.ui.wrapper.TextWatcherWrapper;

public class AlterPasswordActivity extends BaseActivity {

    private ActivityAlterPasswordBinding binding;
    private String password;
    private String newPassword;
    private String confirmPassword;
    private MeViewModel meVM;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("修改密码")
                .builder();
    }

    @Override
    protected void initView() {
        binding.tvTel.setText(SPStaticUtils.getString("phone", ""));
        binding.etPassword.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                password = s.toString();
            }
        });
        binding.etNewPassword.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                newPassword = s.toString();
            }
        });
        binding.etConfirmPassword.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                confirmPassword = s.toString();
            }
        });

        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meVM.updatePassword(password, newPassword, confirmPassword);
            }
        });
    }

    @Override
    protected void initData() {
        meVM = getViewModel(MeViewModel.class);

        meVM.baseLiveData.observe(this, updatePasswordResp -> {
            ToastUtils.showShort(updatePasswordResp.getMsg());
            SPStaticUtils.clear(true);
            ActivityUtils.startActivity(LoginActivity.class);
            finish();
        });
    }

    @Override
    public View initLayout() {
        binding = ActivityAlterPasswordBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
