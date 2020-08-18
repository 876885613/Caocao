package com.caocao.client.ui.me.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentPagerAdapter;

import com.caocao.client.base.BaseFragment;
import com.caocao.client.databinding.FragmentOrderBinding;
import com.caocao.client.ui.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.me.order
 * @ClassName: OrderReleaseFragment
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/17 8:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/17 8:59
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OrderReleaseFragment extends BaseFragment {

    private FragmentOrderBinding binding;

    @Override
    protected void initVmData(Bundle savedInstanceState) {
    }

    protected void initView() {
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.setFragments(getFragments());
        adapter.setArguments(new int[]{0, 2, 3, 4, 1});
        adapter.setTitles(new String[]{"全部", "待支付", "抢单中", "待服务", "已完成"});
        binding.viewPager.setAdapter(adapter);
    }

    public List<BaseFragment> getFragments() {
        List<BaseFragment> fragments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fragments.add(new DemandOrderListFragment());
        }
        return fragments;
    }

    @Override
    protected View initViewBind(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentOrderBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
