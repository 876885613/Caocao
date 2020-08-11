package com.caocao.client.ui.serve.level;

import android.view.View;

import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityServeMoreBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.serve.level
 * @ClassName: ServeMoreActivity
 * @Description: 更多服务
 * @Author: XuYu
 * @CreateDate: 2020/8/11 16:53
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/11 16:53
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ServeMoreActivity extends BaseActivity {

    private ActivityServeMoreBinding binding;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("更多服务")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initLayout() {
        binding = ActivityServeMoreBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
