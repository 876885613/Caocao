package com.caocao.client.ui.serve;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivitySkillReleaseBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.AddPhotoAdapter;

public class SkillReleaseActivity extends BaseActivity {

    private ActivitySkillReleaseBinding binding;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle("发布技能").builder();
    }

    @Override
    protected void initView() {
        binding.rvPhoto.setLayoutManager(new GridLayoutManager(this, 3));
        AddPhotoAdapter addAdapter = new AddPhotoAdapter(this, null);
        binding.rvPhoto.setAdapter(addAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public View initLayout() {
        binding = ActivitySkillReleaseBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
