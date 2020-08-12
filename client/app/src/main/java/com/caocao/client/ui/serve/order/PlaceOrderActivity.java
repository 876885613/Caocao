package com.caocao.client.ui.serve.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityPlaceOrderBinding;
import com.caocao.client.http.entity.respons.AddressResp;
import com.caocao.client.http.entity.respons.GoodsDetailResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.OrderSpecAdapter;
import com.caocao.client.ui.adapter.ServeListAdapter;
import com.caocao.client.ui.me.address.AddressActivity;
import com.caocao.client.weight.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

public class PlaceOrderActivity extends BaseActivity implements View.OnClickListener {

    private ActivityPlaceOrderBinding binding;
    private OrderSpecAdapter specAdapter;

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

        binding.rvSpec.setLayoutManager(new LinearLayoutManager(this));
        binding.rvSpec.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_10));
        specAdapter = new OrderSpecAdapter(R.layout.adapter_spec_item, null);
        binding.rvSpec.setAdapter(specAdapter);

        specAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_minus:
                        int specNum = specAdapter.getData().get(position).specNum;
                        if (specNum == 0) {
                            return;
                        }
                        specAdapter.getData().get(position).specNum--;
                        break;
                    case R.id.tv_add:
                        specAdapter.getData().get(position).specNum++;
                        break;
                }
                specAdapter.setPosition(position);
                specAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initData() {
        int goodsId = getIntValue("goodsId");
        List<GoodsDetailResp.GoodsSpec> goodsSpecList = getParcelableArrayListExtra("goodsSpec");

        specAdapter.setNewData(goodsSpecList);
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
            case R.id.rl_address:
                Bundle bundle = new Bundle();
                bundle.putInt("source", 1);
                ActivityUtils.startActivityForResult(bundle, this, AddressActivity.class, 200);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 200) {
            AddressResp address = data.getParcelableExtra("address");
            binding.rlSelectAddress.setVisibility(View.INVISIBLE);
            binding.rlAddress.setVisibility(View.VISIBLE);
            binding.tvName.setText(address.name);
            binding.tvTel.setText(address.phone);
            binding.tvAddress.setText(address.province + address.city + address.area + address.detail);
        }
    }
}
