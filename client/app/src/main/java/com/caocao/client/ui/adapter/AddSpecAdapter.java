package com.caocao.client.ui.adapter;

import android.text.Editable;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caocao.client.R;
import com.caocao.client.http.entity.request.SettleApplyReq;
import com.caocao.client.ui.wrapper.TextWatcherWrapper;
import com.caocao.client.view.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class AddSpecAdapter extends BaseQuickAdapter<SettleApplyReq.Spec, BaseViewHolder> {
    private OnImageAdapterClickListener listener;

    public AddSpecAdapter(int layoutResId, @Nullable List<SettleApplyReq.Spec> data) {
        super(layoutResId, data);
    }


    public void setImageAdapterClickListener(OnImageAdapterClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void convert(BaseViewHolder helper, SettleApplyReq.Spec item) {

//        helper.<SuperTextView>getView(R.id.tv_title).getLeftTextView().setText("规格" + (helper.getAdapterPosition() + 1));
        helper.<SuperTextView>getView(R.id.tv_title).getLeftTextView().setText("添加规格");

        RecyclerView rvPhoto = helper.getView(R.id.rv_photo);

        rvPhoto.setLayoutManager(new GridLayoutManager(mContext, 3));

        GridImageAdapter addAdapter = new GridImageAdapter(null, 1);
        rvPhoto.setAdapter(addAdapter);

        addAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onTakePhotoClick(View view, int position) {
                listener.onTakePhotoClick(addAdapter, helper.getAdapterPosition());
            }

            @Override
            public void onItemClick(View view, int position) {
                listener.onItemClick(addAdapter, position);
            }

            @Override
            public void onItemDelClick(View view, int position) {
                listener.onItemDelClick(addAdapter, position);
            }
        });

        helper.<SuperTextView>getView(R.id.stv_spec_name).setRightEditTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                item.specName = s.toString();
            }
        });

        helper.<SuperTextView>getView(R.id.stv_price).setRightEditTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                item.specPrice = s.toString();
            }
        });

        helper.<SuperTextView>getView(R.id.stv_unit).setRightEditTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                item.specUnit = s.toString();
            }
        });


        helper.addOnClickListener(R.id.rv_photo, R.id.iv_right);
    }




    public interface OnImageAdapterClickListener {
        void onTakePhotoClick(GridImageAdapter adapter, int position);

        void onItemClick(GridImageAdapter adapter, int position);

        void onItemDelClick(GridImageAdapter adapter, int position);
    }
}
