package com.caocao.client.ui.splash;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivitySplashBinding;
import com.caocao.client.ui.MainActivity;

public class SplashActivity extends BaseActivity {

    private ActivitySplashBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        if (isFirstApp()) {
            ActivityUtils.startActivity(MainActivity.class);
        } else {
            ActivityUtils.startActivity(FirstStartActivity.class);
        }

        finish();

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initLayout() {
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    /**
     * 检测是否第一次打开app
     *
     * @return
     */
    public boolean isFirstApp() {
        int first = SPStaticUtils.getInt("first_app", -1);
        if (first == -1) {
            return false;
        }
        return true;
    }

}
