package com.caocao.client.ui.serve.order;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityPlaceOrderBinding;
import com.caocao.client.http.entity.request.OrderReq;
import com.caocao.client.http.entity.respons.AddressResp;
import com.caocao.client.http.entity.respons.GoodsDetailResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.OrderSpecAdapter;
import com.caocao.client.ui.login.LoginUtils;
import com.caocao.client.ui.me.address.AddressActivity;
import com.caocao.client.utils.LocalParseUtils;
import com.caocao.client.ui.serve.ServeViewModel;
import com.caocao.client.ui.wrapper.TextWatcherWrapper;
import com.caocao.client.utils.DateUtils;
import com.caocao.client.weight.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.Date;
import java.util.List;

public class PlaceOrderActivity extends BaseActivity implements View.OnClickListener, OnOptionsSelectListener {

    private ActivityPlaceOrderBinding binding;
    private OrderSpecAdapter specAdapter;
    private LocalParseUtils localParseUtils;

    private OrderReq orderReq;
    private ServeViewModel serveVM;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        orderReq = new OrderReq();
        super.onCreate(savedInstanceState);
        localParseUtils = LocalParseUtils.getInstance(getApplicationContext());
    }

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

        binding.tvServeTime.setOnClickListener(this);

        binding.tvSubmit.setOnClickListener(this);

        specView();

        binding.etNotes.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                orderReq.remark = s.toString();
            }
        });
    }


    //规格
    private void specView() {
        binding.rvSpec.setLayoutManager(new LinearLayoutManager(this));
        binding.rvSpec.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_10));
        specAdapter = new OrderSpecAdapter(R.layout.adapter_spec_item, null);
        binding.rvSpec.setAdapter(specAdapter);

        specAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsDetailResp.GoodsSpec goodsSpec = specAdapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.tv_minus:
                        int specNum = goodsSpec.specNum;
                        if (specNum == 0) {
                            return;
                        }
                        goodsSpec.specNum--;
                        break;
                    case R.id.tv_add:
                        goodsSpec.specNum++;
                        break;
                }
                orderReq.goodsSpecId = goodsSpec.id;
                orderReq.goodsSpecNum = goodsSpec.specNum;
                orderReq.goodsSpecPrice = goodsSpec.specPrice;


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
        orderReq.goodsId = goodsId;

        serveVM = getViewModel(ServeViewModel.class);

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
            case R.id.tv_serve_time:
                localParseUtils.showServeTimeDialog(this,this);
                break;
            case R.id.tv_submit:
                if (LoginUtils.isLogin()) {
                    createOrder();
                }
                break;
        }
    }

    private void createOrder() {

        serveVM.createOrder(orderReq);

        serveVM.baseLiveData.observe(this, createOrderRes -> {
            LogUtils.e(createOrderRes);
            ToastUtils.showShort(createOrderRes.getMsg());
        });

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

            orderReq.addressId = address.id;
        }
    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        try {
            String YMD = localParseUtils.getServeYMD().get(options1);
            String hour = localParseUtils.getServeHour().get(options2);
            String min = localParseUtils.getServeMin().get(options3);
            binding.tvServeTime.setText(getString(R.string.goods_serve_time, YMD, hour, min));
            Date serveDate = DateUtils.stringToDate(getString(R.string.goods_serve_time, YMD, hour, min),
                    "yyyy-MM-dd  HH:mm");
            orderReq.serviceTime = serveDate;
            LogUtils.e(orderReq.serviceTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
