package com.caocao.client.ui.authentication;

import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityIdentityAutBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.authentication
 * @ClassName: IdentityActivity
 * @Description: 商家认证  身份信息
 * @Author: XuYu
 * @CreateDate: 2020/8/7 10:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/7 10:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class IdentityAUTActivity extends BaseActivity {

    private ActivityIdentityAutBinding binding;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("商家认证")
                .builder();
    }

    @Override
    protected void initView() {
        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(StoreAUTActivity.class);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public View initLayout() {
        binding = ActivityIdentityAutBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
