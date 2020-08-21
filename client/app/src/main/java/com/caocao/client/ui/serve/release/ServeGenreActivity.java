package com.caocao.client.ui.serve.release;

import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.base.app.BaseApplication;
import com.caocao.client.databinding.ActivityServeGenreBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.login.LoginUtils;
import com.caocao.client.ui.serve.AgentApplyActivity;
import com.caocao.client.ui.serve.ServeViewModel;
import com.caocao.client.ui.serve.authentication.IdentityAUTActivity;
import com.coder.baselibrary.dialog.AlertDialog;
import com.coder.baselibrary.dialog.OnClickListenerWrapper;

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
                if (LoginUtils.isLogin()) {
                    if (binding.rbMe.isChecked()) {
                        ActivityUtils.startActivity(SkillActivity.class);
                    } else {
                        ActivityUtils.startActivity(IdentityAUTActivity.class);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        ServeViewModel serveVM = getViewModel(ServeViewModel.class);

        serveVM.isAgentByAddress();

        serveVM.errorLiveData.observe(this, baseResp -> {
            new AlertDialog.Builder(ServeGenreActivity.this)
                    .setView(R.layout.dialog_general)
                    .setText(R.id.tv_title, "当前所在的" + SPStaticUtils.getString("district","")
                            + "没有运营商,您可以选择继续申请发布技能,也可以选择申请加盟")
                    .setText(R.id.tv_cancel, "加盟")
                    .setText(R.id.tv_affirm, "继续申请")
                    .setOnClickListener(R.id.tv_affirm, null)
                    .setOnClickListener(R.id.tv_cancel, new OnClickListenerWrapper() {
                        @Override
                        public void onClickCall(View v) {
                            if(LoginUtils.isLogin()){
                            ActivityUtils.startActivity(AgentApplyActivity.class);
                            }
                        }
                    })
                    .show();
        });
    }

    @Override
    public View initLayout() {
        binding = ActivityServeGenreBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
