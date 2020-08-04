package com.coder.baselibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @ProjectName: Matata Students
 * @Package: com.matata.baselibrary.base
 * @ClassName: BaseFragment
 * @Description: Fragment 的基类
 * @Author: XuYu
 * @CreateDate: 2020/5/18 11:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/18 11:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class AppFragment extends Fragment {

    public Context context;

    public Bundle arguments;

    private View rootView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arguments = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = initViewBind(inflater, container);
        return rootView;
    }

    protected abstract View initViewBind(LayoutInflater inflater, ViewGroup container);


    public View getRootView() {
        return rootView;
    }
}
