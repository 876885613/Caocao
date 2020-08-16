package com.caocao.client.ui.me.order;

import android.view.View;
import android.widget.RadioGroup;

import androidx.fragment.app.FragmentPagerAdapter;

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

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        binding.ivBack.setOnClickListener(view -> finish());

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.setFragments(getFragments());
        adapter.setTitles(new String[]{"全部", "签单中", "进行中", "已结束", "待评价"});
        binding.viewPager.setAdapter(adapter);


        binding.tabGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_serve:
                        adapter.setFragments(getFragments());
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.rb_release:
                        adapter.setFragments(getOrder());
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        });


    }


    public List<BaseFragment> getFragments() {
        List<BaseFragment> fragments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fragments.add(new OrderFragment());
        }
        return fragments;
    }

    public List<BaseFragment> getOrder() {
        List<BaseFragment> fragments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fragments.add(new OrderFragment());
        }
        return fragments;
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
