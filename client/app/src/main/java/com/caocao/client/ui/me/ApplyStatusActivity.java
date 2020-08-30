package com.caocao.client.ui.me;

import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityApplyStatusBinding;
import com.caocao.client.http.entity.respons.ApplyStatusResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.MainActivity;
import com.caocao.client.ui.serve.release.ServeGenreActivity;

public class ApplyStatusActivity extends BaseActivity implements View.OnClickListener {

    private ActivityApplyStatusBinding binding;
    private int status;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("我的技能")
                .builder();
    }

    @Override
    protected void initView() {
        binding.tvRelease.setOnClickListener(this);
        binding.tvHome.setOnClickListener(this);
        binding.tvReleaseServe.setOnClickListener(this);
        binding.tvUpdate.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        status = getIntValue("status");

        MeViewModel meVM = getViewModel(MeViewModel.class);

        meVM.applyStatus();

        meVM.applyStatusLiveData.observe(this, applyStatusResp -> {

            ApplyStatusResp applyStatus = applyStatusResp.getData();

            if (applyStatus.status == -1) {
                binding.llNotApply.setVisibility(View.VISIBLE);
            } else if (applyStatus.status == 0) {
                binding.llWaitApply.setVisibility(View.VISIBLE);
            } else if (applyStatus.status == 1) {
                binding.llSucceedApply.setVisibility(View.VISIBLE);
            } else if (applyStatus.status == 2) {
                binding.llFailApply.setVisibility(View.VISIBLE);
                binding.tvRefuseMsg.setText("原因：" + applyStatus.refuseMsg);
            }
        });
    }

    @Override
    public View initLayout() {
        binding = ActivityApplyStatusBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_release:
            case R.id.tv_release_serve:
            case R.id.tv_update:
                ActivityUtils.startActivity(ServeGenreActivity.class);
                break;
            case R.id.tv_home:
                if (status == 100) {
                    ActivityUtils.startActivity(MainActivity.class);
                } else {
                    finish();
                }
                break;
        }
    }
}
