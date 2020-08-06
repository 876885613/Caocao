package com.caocao.client.ui.serve;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivitySkillBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.AddPhotoAdapter;

public class SkillActivity extends BaseActivity {

    private ActivitySkillBinding binding;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle("发布技能").builder();
    }

    @Override
    protected void initView() {
        binding.rvPhoto.setLayoutManager(new GridLayoutManager(this, 3));
        AddPhotoAdapter addAdapter = new AddPhotoAdapter(this, null);
        binding.rvPhoto.setAdapter(addAdapter);

        binding.tvNext.setOnClickListener(view -> ActivityUtils.startActivity(AddSpecActivity.class));
    }

    @Override
    protected void initData() {

    }

    @Override
    public View initLayout() {
        binding = ActivitySkillBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
