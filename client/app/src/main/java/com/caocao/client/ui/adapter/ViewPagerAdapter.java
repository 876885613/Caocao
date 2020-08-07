package com.caocao.client.ui.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.caocao.client.base.BaseFragment;

import java.util.HashMap;
import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.adapter
 * @ClassName: ViewPagerAdapter
 * @Description: ViewPager
 * @Author: XuYu
 * @CreateDate: 2020/8/7 15:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/7 15:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragments;

    private String[] mTitles;

    private String[] mArgument;


    public static final String ARGUMENT = "argument";

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }

    public void setFragments(List<BaseFragment> fragments) {
        this.mFragments = fragments;
    }

    public void setTitles(String[] titles) {
        this.mTitles = titles;
    }

    public void setArguments(String[] arguments) {
        this.mArgument = arguments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = mFragments.get(position);
        //传递参数信息
        if (mArgument != null && mArgument.length != 0 && mArgument.length == mFragments.size()) {
            Bundle bundle = new Bundle();
            bundle.putString(ARGUMENT, mArgument[position]);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
