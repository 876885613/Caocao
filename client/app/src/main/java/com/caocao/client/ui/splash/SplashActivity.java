package com.caocao.client.ui.splash;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivitySplashBinding;
import com.caocao.client.ui.MainActivity;
import com.caocao.client.utils.location.LocationUtils;
import com.caocao.client.utils.location.RxPermissionListener;
import com.caocao.client.utils.location.RxPermissionManager;

import static com.caocao.client.base.app.BaseApplication.setOnHandlerListener;

public class SplashActivity extends BaseActivity implements RxPermissionListener {

    private ActivitySplashBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxPermissionManager.requestPermissions(this, this,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION);

        setOnHandlerListener(msg -> {
            if (isFirstApp()) {
                ActivityUtils.startActivity(MainActivity.class);
            } else {
                ActivityUtils.startActivity(FirstStartActivity.class);
            }
            finish();
        });
    }

    @Override
    protected void initTitle() {
    }

    @Override
    protected void initView() {
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

    @Override
    public void accept() {
        //定位
        LocationUtils.getInstance(getApplicationContext()).onStartLocation();
    }

    @Override
    public void refuse() {
        if (isFirstApp()) {
            ActivityUtils.startActivity(MainActivity.class);
        } else {
            ActivityUtils.startActivity(FirstStartActivity.class);
        }
        finish();
    }

    @Override
    public void noAsk(String permissionName) {
        RxPermissionManager.showPermissionDialog(this, permissionName);
    }
}
