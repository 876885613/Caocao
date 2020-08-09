package com.caocao.client.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.coder.baselibrary.base.AppFragment;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.base
 * @ClassName: BaseFragment
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/3 9:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/3 9:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class BaseFragment extends AppFragment {

    public BaseActivity activity;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVmData(savedInstanceState);

        initView();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity = (BaseActivity) context;
    }

    /**
     * 初始化Vm
     *
     * @param savedInstanceState 数据状态
     */
    protected abstract void initVmData(Bundle savedInstanceState);

    protected abstract void initView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public <V extends ViewModel> V getViewModel(Class<V> clazz) {
        return new ViewModelProvider(this).get(clazz);
    }


}
