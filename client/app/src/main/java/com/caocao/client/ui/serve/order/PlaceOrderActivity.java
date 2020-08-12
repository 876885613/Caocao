package com.caocao.client.ui.serve.order;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityPlaceOrderBinding;
import com.caocao.client.http.entity.respons.GoodsDetailResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.me.address.AddressActivity;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrderActivity extends BaseActivity implements View.OnClickListener {

    private ActivityPlaceOrderBinding binding;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("提交订单")
                .builder();
    }

    @Override
    protected void initView() {
        binding.rlSelectAddress.setOnClickListener(this);
        binding.rlAddress.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        int goodsId = getIntValue("goodsId");
        List<GoodsDetailResp.GoodsSpec> goodsSpecList = getParcelableArrayListExtra("goodsSpec");
    }

    @Override
    public View initLayout() {
        binding = ActivityPlaceOrderBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_select_address:
                Bundle bundle = new Bundle();
                bundle.putInt("source", 1);
                ActivityUtils.startActivityForResult(bundle, this, AddressActivity.class, 200);
                break;
            case R.id.rl_address:

                break;
        }
    }
}
