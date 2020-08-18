package com.caocao.client.ui.login;

import android.text.Editable;
import android.view.View;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityRetrievePasswordBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.wrapper.TextWatcherWrapper;

public class RetrievePasswordActivity extends BaseActivity implements View.OnClickListener {

    private ActivityRetrievePasswordBinding binding;
    private String tel;
    private String code;
    private String newPassword;
    private String confirmPassword;
    private LoginViewModel loginVM;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("忘记密码")
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

        binding.etCode.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                code = s.toString();
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

        binding.tvSendCode.setOnClickListener(this);
        binding.tvSubmit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        loginVM = getViewModel(LoginViewModel.class);

        loginVM.codeLiveData.observe(this, codeRes -> {
            loginVM.countdown();
        });

        loginVM.timeLiveData.observe(this, aLong -> getCode(aLong));

        loginVM.baseLiveData.observe(this, retrieveResp -> {
            ToastUtils.showShort(retrieveResp.getMsg());
            finish();
        });
    }

    @Override
    public View initLayout() {
        binding = ActivityRetrievePasswordBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send_code:
                sendCode();
                break;
            case R.id.tv_submit:
                loginVM.retrievePassword(tel, code, newPassword, confirmPassword);
                break;
        }
    }

    private void sendCode() {
        if (!RegexUtils.isMobileExact(tel)) {
            ToastUtils.showShort("请填写正确的手机号");
            return;
        }

        loginVM.sendRetrievePasswordSms(tel);
    }


    //验证码操作
    private void getCode(Long aLong) {
        if (aLong == -1) {
            //重新获取
            binding.tvSendCode.setText("重新获取");
            binding.tvSendCode.setClickable(true);
            binding.tvSendCode.setTextColor(getResources().getColor(R.color.color_theme));
        } else {
            binding.tvSendCode.setText(String.format("重新获取 (%1$ds)", aLong));
            binding.tvSendCode.setClickable(false);
            binding.tvSendCode.setTextColor(getResources().getColor(R.color.t3));
        }
    }
}
