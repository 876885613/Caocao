package com.caocao.client.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.LogUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.base.BaseFragment;
import com.caocao.client.databinding.ActivityMainBinding;
import com.caocao.client.ui.home.HomeFragment;
import com.caocao.client.ui.me.MeFragment;
import com.coder.baselibrary.dialog.AlertDialog;
import com.coder.baselibrary.dialog.OnClickListenerWrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 轮播图 使用的是Banner 框架
 */
public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;


    List<BaseFragment> mFragments;
    private AlertDialog dialog;

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
        /*头部导航栏的基础使用  没出标题栏课不初始化
        AbsNavigationBar navigationBar = new DefaultNavigationBar.Builder(this)
                .setTitle("测试")
                .builder();
         */

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
                        switchFragment(mFragments.get(1), mFragments.get(0));
                        break;
                }
            }
        });


        binding.rbRelease.setOnClickListener(view -> {
            /*
            if (dialog == null) {
                dialog = new AlertDialog.Builder(this)
                        .setView(R.layout.dialog_release)
                        .setText(R.id.tv_title, R.string.end_practive)
                        .setOnClickListener(R.id.tv_cancel, null)
                        .setOnClickListener(R.id.tv_confirm, new OnClickListenerWrapper() {
                            @Override
                            public void onClickCall(View v) {
                            }
                        })
                        .create();
            } else {
                dialog.show();
            }
             */
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
        //实例方式 ： 在VM中做网络请求和逻辑处理
//        MainVM vm = getViewModel(MainVM.class);
    }

}
