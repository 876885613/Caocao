package com.caocao.client.ui.serve;

import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityServeGenreBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;

public class ServeGenreActivity extends BaseActivity {

    private ActivityServeGenreBinding binding;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("发布服务")
                .builder();
    }

    @Override
    protected void initView() {
        binding.tvNext.setOnClickListener(view -> ActivityUtils.startActivity(SkillActivity.class));
    }

    @Override
    protected void initData() {

    }

    @Override
    public View initLayout() {
        binding = ActivityServeGenreBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
