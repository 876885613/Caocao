package com.caocao.client.ui.splash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityFirstStartBinding;
import com.caocao.client.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FirstStartActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ActivityFirstStartBinding binding;

    private List<View> viewList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        isTransparency = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        LayoutInflater inflater = getLayoutInflater();


        AppCompatImageView guide1 = new AppCompatImageView(this);
        guide1.setScaleType(ImageView.ScaleType.FIT_XY);
        AppCompatImageView guide2 = new AppCompatImageView(this);
        guide2.setScaleType(ImageView.ScaleType.FIT_XY);
        AppCompatImageView guide3 = new AppCompatImageView(this);
        guide3.setScaleType(ImageView.ScaleType.FIT_XY);

        View guide4 = inflater.inflate(R.layout.layout_first_start, null);

        guide1.setImageResource(R.mipmap.ic_guide_1);
        guide2.setImageResource(R.mipmap.ic_guide_2);
        guide3.setImageResource(R.mipmap.ic_guide_3);

        viewList.add(guide1);
        viewList.add(guide2);
        viewList.add(guide3);
        viewList.add(guide4);
        binding.viewPager.setAdapter(new MyPagerAdapter());
        binding.viewPager.addOnPageChangeListener(binding.materialIndicator);
        binding.materialIndicator.setAdapter(Objects.requireNonNull(binding.viewPager.getAdapter()));
        binding.viewPager.setOnPageChangeListener(this);

        guide4.findViewById(R.id.tv_start).setOnClickListener(v -> {
            SPStaticUtils.put("first_app", 1);
            ActivityUtils.startActivity(MainActivity.class);
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initLayout() {
        binding = ActivityFirstStartBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
            case 1:
            case 2:
                binding.materialIndicator.setVisibility(View.VISIBLE);
                break;
            case 3:
                binding.materialIndicator.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //这个方法是返回总共有几个滑动的页面（）
            return viewList == null ? 0 : viewList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            //该方法判断是否由该对象生成界面。
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //这个方法返回一个对象，该对象表明PagerAapter选择哪个对象放在当前的ViewPager中。这里我们返回当前的页面
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            //这个方法从viewPager中移动当前的view。（划过的时候）
            container.removeView(viewList.get(position));
        }
    }
}
