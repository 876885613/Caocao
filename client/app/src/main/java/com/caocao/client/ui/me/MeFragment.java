package com.caocao.client.ui.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caocao.client.base.BaseFragment;
import com.caocao.client.databinding.FragmentMeBinding;

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

    }


    @Override
    protected View initViewBind(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentMeBinding.inflate(inflater);
        return binding.getRoot();
    }
}
