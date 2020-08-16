package com.caocao.client.ui.serve;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityAgentApplyBinding;
import com.caocao.client.http.entity.respons.SiteInfoResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.wrapper.TextWatcherWrapper;

public class AgentApplyActivity extends BaseActivity implements View.OnClickListener {

    private ActivityAgentApplyBinding binding;
    private ServeViewModel serveVM;
    private SiteInfoResp siteInfo;

    private int identity;
    private String address;
    private String name;
    private String tel;
    private String comment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        identity = 1;
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("我要加盟")
                .builder();
    }

    @Override
    protected void initView() {

        binding.rgAgent.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_single:
                    identity = 1;
                    binding.tvName.setText("姓名");
                    break;
                case R.id.rb_company:
                    identity = 2;
                    binding.tvName.setText("公司名称");
                    break;
            }
        });

        binding.etAddress.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                address = s.toString();
            }
        });

        binding.etName.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                name = s.toString();
            }
        });

        binding.etTel.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                tel = s.toString();
            }
        });

        binding.etComment.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                comment = s.toString();
            }
        });

        binding.ivCall.setOnClickListener(this);

        binding.tvSubmit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        serveVM = getViewModel(ServeViewModel.class);

        serveVM.siteInfo();

        serveVM.siteInfoLiveData.observe(this, siteInfoRes -> {
            siteInfo = siteInfoRes.getData();
            binding.tvCompanyName.setText(siteInfo.companyName);
            binding.tvCompanyAddress.setText(siteInfo.companyAddress);
        });

        serveVM.baseLiveData.observe(this, agentResp -> {
            ToastUtils.showShort(agentResp.getMsg());
            finish();
        });
    }

    @Override
    public View initLayout() {
        binding = ActivityAgentApplyBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_call:
                PhoneUtils.dial(siteInfo.phone);
                break;
            case R.id.tv_submit:
                serveVM.agentApply(address, name, identity, tel, comment);
                break;
        }
    }
}
