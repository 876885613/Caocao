package com.caocao.client.ui.me.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityAddressBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.AddressAdapter;
import com.caocao.client.ui.me.MeViewModel;
import com.caocao.client.weight.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.baselibrary.dialog.AlertDialog;
import com.coder.baselibrary.dialog.OnClickListenerWrapper;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.me.address
 * @ClassName: AddressActivity
 * @Description: 地址列表
 * @Author: XuYu
 * @CreateDate: 2020/8/12 15:03
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/12 15:03
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AddressActivity extends BaseActivity {

    private ActivityAddressBinding binding;
    private MeViewModel meVM;
    private AddressAdapter addressAdapter;

    //1 ： 订单 0：个人信息
    private int source;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("地址列表")
                .builder();
    }

    @Override
    protected void initView() {

        binding.rvList.setLayoutManager(new LinearLayoutManager(this));

        binding.rvList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_f5_10));
        addressAdapter = new AddressAdapter(R.layout.adapter_address_item, null);
        binding.rvList.setAdapter(addressAdapter);

        addressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (source == 1) {
                    Intent intent = new Intent();
                    intent.putExtra("address", addressAdapter.getData().get(position));
                    setResult(200, intent);
                    finish();
                }
            }
        });


        addressAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_del:
                        int id = addressAdapter.getData().get(position).id;
                        showDelDialog(id);
                        break;
                    case R.id.tv_edit:
                        Bundle bundle = new Bundle();
                        bundle.putInt("source", 1);
                        bundle.putParcelable("address", addressAdapter.getData().get(position));
                        ActivityUtils.startActivityForResult(bundle, AddressActivity.this, EditAddressActivity.class, 200);
                        break;
                }
            }
        });
    }

    private void showDelDialog(int id) {
        new AlertDialog.Builder(this)
                .setView(R.layout.dialog_general)
                .setText(R.id.tv_title, "确认要删除吗？")
                .setOnClickListener(R.id.tv_affirm, new OnClickListenerWrapper() {

                    @Override
                    public void onClickCall(View v) {
                        meVM.deleteAddress(id);
                    }
                })
                .setOnClickListener(R.id.tv_cancel, null)
                .show();
    }

    @Override
    protected void initData() {
        source = getIntValue("source");

        meVM = getViewModel(MeViewModel.class);
        meVM.addressList();
        meVM.addressLiveData.observe(this, addressRes -> {
            addressAdapter.setNewData(addressRes.getData());
        });

        meVM.editAddressLiveData.observe(this, delRes -> {
            ToastUtils.showShort(delRes.getMsg());
            meVM.addressList();
        });
    }

    @Override
    public View initLayout() {
        binding = ActivityAddressBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 200) {
            meVM.addressList();
        }
    }
}
