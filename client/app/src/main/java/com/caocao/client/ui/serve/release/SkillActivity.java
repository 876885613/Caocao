package com.caocao.client.ui.serve.release;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivitySkillBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.AddPhotoAdapter;
import com.caocao.client.ui.adapter.EditToolAdapter;
import com.caocao.client.ui.serve.bean.EditToolEntity;
import com.caocao.client.ui.serve.bean.ToolType;
import com.caocao.client.ui.serve.release.AddSpecActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

public class SkillActivity extends BaseActivity {

    private ActivitySkillBinding binding;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle("发布技能").builder();
    }

    @Override
    protected void initView() {
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

    }

    @Override
    public View initLayout() {
        binding = ActivitySkillBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
