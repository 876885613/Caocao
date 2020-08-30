package com.caocao.client.ui.serve.release;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.StringUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityServeGenreBinding;
import com.caocao.client.http.entity.respons.ApplyStatusResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.login.LoginUtils;
import com.caocao.client.ui.me.ApplyStatusActivity;
import com.caocao.client.ui.me.MeViewModel;
import com.caocao.client.ui.serve.AgentApplyActivity;
import com.caocao.client.ui.serve.ServeViewModel;
import com.caocao.client.ui.serve.authentication.IdentityAUTActivity;
import com.coder.baselibrary.dialog.AlertDialog;
import com.coder.baselibrary.dialog.OnClickListenerWrapper;

public class ServeGenreActivity extends BaseActivity {

    private ActivityServeGenreBinding binding;
    private MeViewModel meVM;

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
                    meVM.applyStatus();
                }
            }
        });
    }

    @Override
    protected void initData() {
        ServeViewModel serveVM = getViewModel(ServeViewModel.class);

        meVM = getViewModel(MeViewModel.class);


        if (!StringUtils.isEmpty(SPStaticUtils.getString("token"))) {
            serveVM.isAgentByAddress();
        }

        serveVM.errorLiveData.observe(this, baseResp -> {
            new AlertDialog.Builder(ServeGenreActivity.this)
                    .setView(R.layout.dialog_general)
                    .setText(R.id.tv_title, "当前所在的" + SPStaticUtils.getString("region", "").split(",")[2]
                            + "没有运营商,您可以选择继续申请发布技能,也可以选择申请加盟")
                    .setText(R.id.tv_cancel, "加盟")
                    .setText(R.id.tv_affirm, "继续申请")
                    .setOnClickListener(R.id.tv_affirm, null)
                    .setOnClickListener(R.id.tv_cancel, new OnClickListenerWrapper() {
                        @Override
                        public void onClickCall(View v) {
                            if (LoginUtils.isLogin()) {
                                ActivityUtils.startActivity(AgentApplyActivity.class);
                            }
                        }
                    })
                    .show();
        });


        meVM.applyStatusLiveData.observe(this, applyStatusResp -> {

            ApplyStatusResp applyStatus = applyStatusResp.getData();


            if (applyStatus.status == -1 || applyStatus.status == 2) {
                if (binding.rbMe.isChecked()) {
                    ActivityUtils.startActivity(SkillActivity.class);
                } else {
                    ActivityUtils.startActivity(IdentityAUTActivity.class);
                }
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt("status", 100);
                ActivityUtils.startActivity(bundle,ApplyStatusActivity.class);
            }

        });
    }

    @Override
    public View initLayout() {
        binding = ActivityServeGenreBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
