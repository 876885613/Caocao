package com.caocao.client.ui.goods;

import android.view.LayoutInflater;
import android.view.View;

import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityGoodsDetailsBinding;

public class GoodsDetailsActivity extends BaseActivity {

    private ActivityGoodsDetailsBinding binding;

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        binding.table.addView(LayoutInflater.from(this).inflate(R.layout.layout_spec_item,null));
    }

    @Override
    protected void initData() {

    }

    @Override
    public View initLayout() {
        binding = ActivityGoodsDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
