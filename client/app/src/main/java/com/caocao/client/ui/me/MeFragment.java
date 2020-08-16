package com.caocao.client.ui.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.bumptech.glide.Glide;
import com.caocao.client.R;
import com.caocao.client.base.BaseFragment;
import com.caocao.client.databinding.FragmentMeBinding;
import com.caocao.client.ui.me.order.OrderActivity;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.me
 * @ClassName: MeFragment
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/3 16:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/3 16:58
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MeFragment extends BaseFragment {

    private FragmentMeBinding binding;

    @Override
    protected void initVmData(Bundle savedInstanceState) {
    }

    @Override
    protected void initView() {
        binding.tvOrder.setOnClickListener(view -> {
            ActivityUtils.startActivity(OrderActivity.class);
        });

        String headimgurl = SPStaticUtils.getString("headimgurl","");
        Glide.with(this).load(headimgurl).error(R.mipmap.ic_default_portrait).into(binding.ivPortrait);
        binding.tvName.setText(SPStaticUtils.getString("nickname",""));
        binding.tvTel.setText(SPStaticUtils.getString("phone",""));
    }


    @Override
    protected View initViewBind(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentMeBinding.inflate(inflater);
        return binding.getRoot();
    }
}
