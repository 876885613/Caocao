package com.caocao.client.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.base.BaseFragment;
import com.caocao.client.databinding.ActivityMainBinding;
import com.caocao.client.http.entity.respons.AppUpgradeResp;
import com.caocao.client.ui.demand.DemandActivity;
import com.caocao.client.ui.home.HomeFragment;
import com.caocao.client.ui.login.LoginUtils;
import com.caocao.client.ui.me.MeFragment;
import com.caocao.client.ui.me.MeViewModel;
import com.caocao.client.ui.serve.release.ServeGenreActivity;
import com.coder.baselibrary.dialog.AlertDialog;
import com.coder.baselibrary.dialog.OnClickListenerWrapper;
import com.king.app.updater.AppUpdater;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图 使用的是Banner 框架
 */
public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;


    List<BaseFragment> mFragments;
    private AlertDialog dialog;
    private MeViewModel meVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        isTransparency = true;
        initFragment();
        super.onCreate(savedInstanceState);
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new MeFragment());
    }

    @Override
    public View initLayout() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    protected void initTitle() {
    }

    @Override
    protected void initView() {
        switchFragment(mFragments.get(0), mFragments.get(1));
        binding.tabGroup.check(R.id.rb_home);
        binding.tabGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.rb_home:
                        switchFragment(mFragments.get(0), mFragments.get(1));
                        break;
                    case R.id.rb_me:
                        if (LoginUtils.isLogin()) {
                            switchFragment(mFragments.get(1), mFragments.get(0));
                        } else {
                            binding.tabGroup.check(R.id.rb_home);
                        }
                        break;
                }
            }
        });


        binding.rbRelease.setOnClickListener(view -> {
            if (dialog == null) {
                dialog = new AlertDialog.Builder(this, R.style.DialogAlter)
                        .setView(R.layout.dialog_release)
                        .setGravity(Gravity.BOTTOM)
                        .setOnClickListener(R.id.iv_serve, new OnClickListenerWrapper() {
                            @Override
                            public void onClickCall(View v) {
                                ActivityUtils.startActivity(ServeGenreActivity.class);
                            }
                        })
                        .setOnClickListener(R.id.iv_demand, new OnClickListenerWrapper() {
                            @Override
                            public void onClickCall(View v) {
                                if (LoginUtils.isLogin()) {
                                    ActivityUtils.startActivity(DemandActivity.class);
                                }
                            }
                        })
                        .create();
            }
            dialog.show();
        });
    }


    //切换Fragment
    private void switchFragment(BaseFragment showFragment, BaseFragment hideFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(hideFragment);//隐藏上个Fragment
        if (!showFragment.isAdded()) {
            transaction.add(R.id.fl_content, showFragment);
        }
        transaction.show(showFragment).commitAllowingStateLoss();
    }

    @Override
    protected void initData() {
        meVM = getViewModel(MeViewModel.class);

        meVM.upgrade();

        meVM.appUpgradeLiveData.observe(this, upgradeResp -> {
            AppUpgradeResp upgrade = upgradeResp.getData();
            if (upgrade.versionCode > AppUtils.getAppVersionCode()) {
                upgradeDialog(upgrade);
            }
        });
    }


    private void upgradeDialog(AppUpgradeResp upgrade) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(R.layout.dialog_app_upgrade)
                .setText(R.id.tv_content, upgrade.desc)
                .setCancelable(upgrade.isForce != 1)
                .setOnClickListener(R.id.tv_affirm, new OnClickListenerWrapper() {

                    @Override
                    public void onClickCall(View v) {
                        new AppUpdater(MainActivity.this, upgrade.apkurl).start();
                    }
                })
                .setOnClickListener(R.id.tv_cancel, null)
                .show();

        if (upgrade.isForce == 1) {
            dialog.getView(R.id.tv_cancel).setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }

        if (keyCode == KeyEvent.KEYCODE_MOVE_HOME) {
            LogUtils.e("执行力");
        }


        return super.onKeyDown(keyCode, event);
    }

    private long exitTime = 0;

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {

            finish();
            System.exit(0);
        }
    }


}
