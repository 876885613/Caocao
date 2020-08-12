package com.caocao.client.ui.adapter;

import androidx.annotation.Nullable;

import com.caocao.client.R;
import com.caocao.client.http.entity.respons.AddressResp;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.adapter
 * @ClassName: AddressAdapter
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/12 15:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/12 15:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AddressAdapter extends BaseQuickAdapter<AddressResp, BaseViewHolder> {
    public AddressAdapter(int layoutResId, @Nullable List<AddressResp> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressResp item) {
        helper.setText(R.id.tv_name, item.name);
        helper.setText(R.id.tv_tel, item.phone);
        helper.setText(R.id.tv_address, item.province + item.city + item.area + item.detail);
        helper.addOnClickListener(R.id.tv_edit, R.id.tv_del);
    }
}
