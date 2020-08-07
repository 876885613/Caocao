package com.caocao.client.ui.serve;

import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityServeGenreBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.authentication.IdentityAUTActivity;

public class ServeGenreActivity extends BaseActivity {

    private ActivityServeGenreBinding binding;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("发布服务")
                .builder();
    }

    @Override
    protected void initView() {
        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.rbMe.isChecked()) {
                    ActivityUtils.startActivity(SkillActivity.class);
                }else {
                    ActivityUtils.startActivity(IdentityAUTActivity.class);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public View initLayout() {
        binding = ActivityServeGenreBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
