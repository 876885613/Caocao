package com.caocao.client.ui.serve;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityAddSpecBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.AddPhotoAdapter;

public class AddSpecActivity extends BaseActivity {

    private ActivityAddSpecBinding binding;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle("添加规格").builder();
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
        binding = ActivityAddSpecBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
