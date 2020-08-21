package com.caocao.client.ui.me.order;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.blankj.utilcode.util.FragmentUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.base.BaseFragment;
import com.caocao.client.databinding.ActivityOrderBinding;
import com.caocao.client.ui.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.me.order
 * @ClassName: OrderActivity
 * @Description: 订单列表
 * @Author: XuYu
 * @CreateDate: 2020/8/7 14:35
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/7 14:35
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OrderActivity extends BaseActivity {

    private ActivityOrderBinding binding;
    private OrderServeFragment   serveFragment;
    private OrderReleaseFragment releaseFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        binding.ivBack.setOnClickListener(view -> finish());

        serveFragment = new OrderServeFragment();
        releaseFragment = new OrderReleaseFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentUtils.add(manager, releaseFragment, R.id.fl_content);
        FragmentUtils.add(manager, serveFragment, R.id.fl_content);


        binding.tabGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_serve:
                        FragmentUtils.showHide(serveFragment, releaseFragment);
                        break;
                    case R.id.rb_release:
                        FragmentUtils.showHide(releaseFragment, serveFragment);
                        break;
                }
            }
        });

    }


    @Override
    protected void initData() {
    }

    @Override
    public View initLayout() {
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
