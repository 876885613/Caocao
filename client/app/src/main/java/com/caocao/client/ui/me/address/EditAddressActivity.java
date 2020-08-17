package com.caocao.client.ui.me.address;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityEditAddressBinding;
import com.caocao.client.http.entity.respons.AddressResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.me.MeViewModel;
import com.caocao.client.ui.wrapper.TextWatcherWrapper;
import com.caocao.client.utils.LocalParseUtils;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.me.address
 * @ClassName: EditAddressActivity
 * @Description: 编辑地址
 * @Author: XuYu
 * @CreateDate: 2020/8/12 16:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/12 16:16
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EditAddressActivity extends BaseActivity implements View.OnClickListener, OnAddressCallBackListener {

    //1：编辑 0：添加
    private int source;
    private ActivityEditAddressBinding binding;
    private LocalParseUtils localParseUtils;
    private MeViewModel meVM;
    private AddressResp address;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localParseUtils = LocalParseUtils.getInstance(getApplicationContext());
        localParseUtils.initAddressData();

    }

    @Override
    protected void initTitle() {
        source = getIntValue("source");
        new DefaultNavigationBar.Builder(this)
                .setTitle(source == 0 ? "添加地址" : "编辑地址")
                .builder();
    }

    @Override
    protected void initView() {
        binding.tvAddress.setOnClickListener(this);
        binding.tvSaveAddress.setOnClickListener(this);

        binding.etName.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                address.name = s.toString();
            }
        });

        binding.etTel.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                address.phone = s.toString();
            }
        });

        binding.etDetailAddress.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                address.detail = s.toString();
            }
        });

    }

    @Override
    protected void initData() {


        if (source == 1) {
            address = getParcelableExtra("address");
            binding.etName.setText(address.name);
            binding.etTel.setText(address.phone);
            binding.tvAddress.setText(address.province + address.city + address.area);
            binding.etDetailAddress.setText(address.detail);
        } else {
            address = new AddressResp();
        }

        meVM = getViewModel(MeViewModel.class);


        meVM.baseLiveData.observe(this, baseRes -> {
            ToastUtils.showShort(baseRes.getMsg());
            setResult(200);
            finish();
        });
    }

    @Override
    public View initLayout() {
        binding = ActivityEditAddressBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                localParseUtils.showAddressDialog(this,this);
                break;
            case R.id.tv_save_address:
                editAddress();
                break;
        }
    }


    private void editAddress() {
        meVM.editAddress(address);
    }

    @Override
    public void onAddress(String province, String city, String area) {
        address.province = province;
        address.city = city;
        address.area = area;
        binding.tvAddress.setText(province + city + area);
    }
}
