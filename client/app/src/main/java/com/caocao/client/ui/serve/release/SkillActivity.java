package com.caocao.client.ui.serve.release;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivitySkillBinding;
import com.caocao.client.http.entity.request.SettleApplyReq;
import com.caocao.client.http.entity.respons.SortResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.AddPhotoAdapter;
import com.caocao.client.ui.adapter.EditToolAdapter;
import com.caocao.client.ui.demand.OnSortCallBackListener;
import com.caocao.client.ui.me.address.OnAddressCallBackListener;
import com.caocao.client.ui.serve.ServeViewModel;
import com.caocao.client.ui.serve.bean.EditToolEntity;
import com.caocao.client.ui.serve.bean.ToolType;
import com.caocao.client.ui.wrapper.TextWatcherWrapper;
import com.caocao.client.utils.LocalParseUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

public class SkillActivity extends BaseActivity implements OnSortCallBackListener, OnAddressCallBackListener {

    private ActivitySkillBinding binding;
    private LocalParseUtils localParseUtils;
    private SettleApplyReq applyReq;
    private ServeViewModel serveVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        localParseUtils = LocalParseUtils.getInstance(getApplicationContext());
        localParseUtils.initAddressData();
        super.onCreate(savedInstanceState);

        applyReq = new SettleApplyReq();
        applyReq.type = 1;
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle("发布技能").builder();
    }

    @Override
    protected void initView() {

        binding.stvSort.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localParseUtils.showSortDialog(SkillActivity.this, SkillActivity.this);
            }
        });

        binding.etServeIntro.setContentTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                applyReq.goodsTitle = s.toString();
            }
        });

        binding.etServeContent.setContentTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                applyReq.goodsDetail = s.toString();
            }
        });

        binding.stvMakeAddress.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localParseUtils.showAddressDialog(SkillActivity.this, SkillActivity.this);
            }
        });

        binding.etDetailAddress.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                applyReq.addressDetail = s.toString();
            }
        });

        //添加图片
        binding.rvBannerPhoto.setLayoutManager(new GridLayoutManager(this, 3));
        AddPhotoAdapter addBannerAdapter = new AddPhotoAdapter(this, null);
        binding.rvBannerPhoto.setAdapter(addBannerAdapter);

        binding.rvServePhoto.setLayoutManager(new GridLayoutManager(this, 3));
        AddPhotoAdapter addServeAdapter = new AddPhotoAdapter(this, null);
        binding.rvServePhoto.setAdapter(addServeAdapter);

        binding.tvNext.setOnClickListener(view -> ActivityUtils.startActivity(AddSpecActivity.class));

        editToolView();
    }

    //文本编辑器工具
    private void editToolView() {
        binding.rvEtTool.setLayoutManager(new GridLayoutManager(this, 7));
        AddPhotoAdapter addAdapter = new AddPhotoAdapter(this, null);
        binding.rvEtTool.setAdapter(addAdapter);

        EditToolAdapter editToolAdapter = new EditToolAdapter(R.layout.adapter_edit_tool_item);
        binding.rvEtTool.setAdapter(editToolAdapter);

        editToolAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EditToolEntity toolEntity = editToolAdapter.getItem(position);
                switch (ToolType.valueOf(toolEntity.des)) {
                    case Image:
                        ToastUtils.showShort("添加图片");
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        serveVM = getViewModel(ServeViewModel.class);
        serveVM.cateList();
        serveVM.sortLiveData.observe(this, sortResp -> {
            localParseUtils.buildSortData(sortResp);
        });
    }

    @Override
    public View initLayout() {
        binding = ActivitySkillBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onSort(SortResp sort1, SortResp sort2, SortResp sort3) {
        applyReq.cateId = sort3.id;
        binding.stvSort.getRightTextView().setText(sort3.cateName);
    }

    @Override
    public void onAddress(String province, String city, String area) {
        applyReq.merchantProvince = province;
        applyReq.merchantCity = city;
        applyReq.merchantDistrict = area;
        binding.stvMakeAddress.getRightTextView().setText(province + city + area);
    }
}
