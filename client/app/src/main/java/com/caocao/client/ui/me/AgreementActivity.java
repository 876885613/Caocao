package com.caocao.client.ui.me;

import android.view.View;

import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityAgreementBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;

public class AgreementActivity extends BaseActivity {

    private ActivityAgreementBinding binding;
    private String agreement;

    @Override
    protected void initTitle() {
        agreement = getStringValue("agreement");
        new DefaultNavigationBar.Builder(this)
                .setTitle(agreement.equals("user_protocol") ? "用户协议" : "隐私协议")
                .builder();
    }

    @Override
    protected void initView() {
        switch (agreement) {
            case "user_protocol":
                binding.webView.loadUrl("https://ccdj.jiajiayong.com/gravity/Protocol/privacy_agreement.html", 0);
                break;
            case "privacy":
                binding.webView.loadUrl("https://ccdj.jiajiayong.com/gravity/Protocol/user_privacy.html", 0);
                break;
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public View initLayout() {
        binding = ActivityAgreementBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
