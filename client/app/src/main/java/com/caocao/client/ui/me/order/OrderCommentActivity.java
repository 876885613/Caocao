package com.caocao.client.ui.me.order;

import android.view.View;

import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityOrderCommentBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;

public class OrderCommentActivity extends BaseActivity {

    private ActivityOrderCommentBinding binding;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("发表评论")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        int goodsId = getIntValue("goodsId");
        int orderId = getIntValue("orderId");

    }

    @Override
    public View initLayout() {
        binding = ActivityOrderCommentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
