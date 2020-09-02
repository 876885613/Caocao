package com.caocao.client.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.caocao.client.R;
import com.caocao.client.base.BaseFragment;
import com.caocao.client.databinding.FragmentMeBinding;
import com.caocao.client.ui.login.LoginActivity;
import com.caocao.client.ui.me.address.AddressActivity;
import com.caocao.client.ui.me.order.OrderActivity;
import com.coder.baselibrary.dialog.AlertDialog;
import com.coder.baselibrary.dialog.OnClickListenerWrapper;

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
public class MeFragment extends BaseFragment implements View.OnClickListener {

    private FragmentMeBinding binding;

    @Override
    protected void initVmData(Bundle savedInstanceState) {
    }

    @Override
    protected void initView() {
        binding.tvOrder.setOnClickListener(view -> {
            ActivityUtils.startActivity(OrderActivity.class);
        });

        String headimgurl = SPStaticUtils.getString("headimgurl", "");
        Glide.with(this).load(headimgurl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .error(R.mipmap.ic_default_portrait).into(binding.ivPortrait);
        binding.tvName.setText(SPStaticUtils.getString("nickname", ""));
        binding.tvTel.setText(SPStaticUtils.getString("phone", ""));

        binding.tvSkill.setOnClickListener(this);
        binding.tvAddress.setOnClickListener(this);
        binding.tvCollect.setOnClickListener(this);
        binding.tvUserProtocol.setOnClickListener(this);
        binding.tvPrivacy.setOnClickListener(this);
        binding.tvKefu.setOnClickListener(this);
        binding.tvAlterPassword.setOnClickListener(this);
        binding.tvExit.setOnClickListener(this);
    }


    @Override
    protected View initViewBind(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentMeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.tv_skill:
                ActivityUtils.startActivity(ApplyStatusActivity.class);
                break;
            case R.id.tv_address:
                ActivityUtils.startActivity(AddressActivity.class);
                break;
            case R.id.tv_collect:
                ActivityUtils.startActivity(CollectActivity.class);
                break;
            case R.id.tv_user_protocol:
                bundle.putString("agreement", "user_protocol");
                ActivityUtils.startActivity(bundle, AgreementActivity.class);
                break;
            case R.id.tv_privacy:
                bundle.putString("agreement", "privacy");
                ActivityUtils.startActivity(bundle, AgreementActivity.class);
                break;
            case R.id.tv_kefu:
                PhoneUtils.dial("0531-82380271");
                break;
            case R.id.tv_alter_password:
                ActivityUtils.startActivity(AlterPasswordActivity.class);
                break;
            case R.id.tv_exit:

                new AlertDialog.Builder(activity)
                        .setView(R.layout.dialog_general)
                        .setText(R.id.tv_title, "您确定退出当前账号")
                        .setText(R.id.tv_affirm, "确定")
                        .setOnClickListener(R.id.tv_cancel, null)
                        .setOnClickListener(R.id.tv_affirm, new OnClickListenerWrapper() {
                            @Override
                            public void onClickCall(View v) {
                                SPStaticUtils.remove("token", true);
                                SPStaticUtils.remove("nickname", true);
                                SPStaticUtils.remove("headimgurl", true);
                                SPStaticUtils.remove("phone", true);
                                activity.finish();
                                bundle.putInt("flag", 200);
                                ActivityUtils.startActivity(bundle, LoginActivity.class);
                            }
                        })
                        .show();
                break;
        }
    }
}
