package com.caocao.client.ui.serve.release;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.base.app.BaseApplication;
import com.caocao.client.databinding.ActivityAddSpecBinding;
import com.caocao.client.http.entity.request.SettleApplyReq;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.AddSpecAdapter;
import com.caocao.client.ui.adapter.GridImageAdapter;
import com.caocao.client.ui.image.UploadViewModel;
import com.caocao.client.utils.CheckNotNullUtils;
import com.caocao.client.utils.location.RxPermissionListener;
import com.caocao.client.utils.location.RxPermissionManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddSpecActivity extends BaseActivity implements RxPermissionListener {

    private ActivityAddSpecBinding binding;

    private SettleApplyReq applyReq;


    private List<SettleApplyReq.Spec> applyList;
    private UploadViewModel uploadVM;

    private PictureSelectionModel pictureSelectionModel;


    //当前规格Item
    private int position;
    private AddSpecAdapter specAdapter;

    private GridImageAdapter gridImageAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        applyList = new ArrayList<>();
        applyList.add(new SettleApplyReq.Spec());
        super.onCreate(savedInstanceState);

        uploadVM.initPermission(this, this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle("添加规格").builder();
    }

    @Override
    protected void initView() {

        binding.rvSpec.setLayoutManager(new LinearLayoutManager(this));

        specAdapter = new AddSpecAdapter(R.layout.adapter_spec_add, applyList);

        specAdapter.bindToRecyclerView(binding.rvSpec);


        specAdapter.setImageAdapterClickListener(new AddSpecAdapter.OnImageAdapterClickListener() {

            @Override
            public void onTakePhotoClick(GridImageAdapter adapter, int position) {
                AddSpecActivity.this.gridImageAdapter = adapter;
                photoSelect(position);
            }

            @Override
            public void onItemClick(GridImageAdapter adapter, int position) {
                pictureSelectionModel.openExternalPreview(position, adapter.getData());
            }

            @Override
            public void onItemDelClick(GridImageAdapter adapter, int position) {
                adapter.setNewData(null);
            }
        });

        specAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_right) {
                    if (position == 0) {
                        ToastUtils.showShort("规格必须保留一个");
                        return;
                    }
                    specAdapter.getData().remove(position);
                    specAdapter.notifyDataSetChanged();
                }
            }
        });


        binding.rvSpec.setAdapter(specAdapter);

        binding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specAdapter.addData(specAdapter.getData().size(), new SettleApplyReq.Spec());
            }
        });

        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyReq.spec = specAdapter.getData();

                LogUtils.e(applyReq.spec);

                Map<String, String> fieldMap = new HashMap();

                for (int i = 1; i <= applyReq.spec.size(); i++) {
                    try {
                        fieldMap.clear();
                        fieldMap.put("specName", "请补全第" + i + "个规格");
                        fieldMap.put("specPrice", "请补全第" + i + "个规格");
                        fieldMap.put("specUnit", "请补全第" + i + "个规格");
                        fieldMap.put("specImage", "请补全第" + i + "个规格");
                        String msg = CheckNotNullUtils.checkNotNull(applyReq.spec.get(i - 1), fieldMap);
//                        if (!StringUtils.isEmpty(msg)) {
//                            ToastUtils.showShort(msg);
//                            return;
//                        }
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("apply", applyReq);
                        ActivityUtils.startActivity(bundle, SkillReleaseActivity.class);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private void photoSelect(int position) {
        boolean granted = uploadVM.isCameraGranted();
        if (!granted) {
            uploadVM.initPermission(this, this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return;
        }

        pictureSelectionModel = uploadVM.pictureSelection(this, new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
                AddSpecActivity.this.position = position;
                String path = result.get(0).getPath();
                uploadVM.uploadPhoto(path);
            }

            @Override
            public void onCancel() {
            }
        });
    }


    @Override
    protected void initData() {
        applyReq = getParcelableExtra("apply");

        uploadVM = getViewModel(UploadViewModel.class);

        uploadVM.uploadLiveData.observe(this, uploadRes -> {
            LogUtils.e(uploadRes.getData().uploadUrl);
            LocalMedia localMedia = new LocalMedia();
            localMedia.setPath(BaseApplication.HOST_PATH + uploadRes.getData().uploadUrl);

            gridImageAdapter.addData(localMedia);
            specAdapter.getData().get(position).specImage = gridImageAdapter.getData().get(0).getPath();
        });
    }

    @Override
    public View initLayout() {
        binding = ActivityAddSpecBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void accept() {

    }

    @Override
    public void refuse() {

    }

    @Override
    public void noAsk(String permissionName) {
        RxPermissionManager.showPermissionDialog(this, permissionName);
    }
}
