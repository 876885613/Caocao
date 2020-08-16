package com.caocao.client.ui.serve.authentication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityStoreAutBinding;
import com.caocao.client.http.entity.request.SettleApplyReq;
import com.caocao.client.http.entity.respons.SortResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.GridImageAdapter;
import com.caocao.client.ui.bean.CheckBean;
import com.caocao.client.ui.demand.OnSortCallBackListener;
import com.caocao.client.ui.image.GridImageOnItemClick;
import com.caocao.client.ui.image.UploadViewModel;
import com.caocao.client.ui.me.address.OnAddressCallBackListener;
import com.caocao.client.ui.serve.ServeViewModel;
import com.caocao.client.ui.wrapper.TextWatcherWrapper;
import com.caocao.client.utils.CheckNotNullUtils;
import com.caocao.client.utils.LocalParseUtils;
import com.caocao.client.utils.UploadImageUtils;
import com.caocao.client.utils.location.RxPermissionListener;
import com.caocao.client.utils.location.RxPermissionManager;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.caocao.client.ui.bean.CheckBean.CheckType.INTEGER;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.authentication
 * @ClassName: StoreAUTActivity
 * @Description: 商店信息 认证
 * @Author: XuYu
 * @CreateDate: 2020/8/7 10:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/7 10:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class StoreAUTActivity extends BaseActivity implements
        OnSortCallBackListener, OnAddressCallBackListener, RxPermissionListener {

    private ActivityStoreAutBinding binding;

    private LocalParseUtils localParseUtils;
    private SettleApplyReq applyReq;


    private ServeViewModel serveVM;


    private UploadImageUtils uploadImageUtils;
    private GridImageAdapter doorHeaderAdapter;
    private GridImageAdapter showPhotoAdapter;


    private UploadViewModel uploadVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        localParseUtils = LocalParseUtils.getInstance(getApplicationContext());
        localParseUtils.initAddressData();
        uploadVM = getViewModel(UploadViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("商家认证")
                .builder();
    }

    @Override
    protected void initView() {
        uploadImageUtils = new UploadImageUtils(this, this, uploadVM);

        doorHeaderView();

        showPhotoView();


        binding.stvStoreName.setRightEditTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                applyReq.merchantName = s.toString();
            }
        });

        binding.stvKefuTel.setRightEditTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                applyReq.consumerHotline = s.toString();
            }
        });


        binding.stvBusinessHours.setRightEditTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                applyReq.businessHours = s.toString();
            }
        });


        binding.stvServeSort.setRightTextOnClickListener(v ->
                localParseUtils.showSortDialog(StoreAUTActivity.this, StoreAUTActivity.this));


        binding.etServeContent.setContentTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                applyReq.goodsDetail = s.toString();
            }
        });


        binding.stvMakeAddress.setRightTextOnClickListener(v ->
                localParseUtils.showAddressDialog(StoreAUTActivity.this, StoreAUTActivity.this));


        binding.etAddressDetail.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                applyReq.addressDetail = s.toString();
            }
        });

        binding.tvNext.setOnClickListener(view -> ActivityUtils.startActivity(CertificateAUTActivity.class));


        binding.tvNext.setOnClickListener(v -> toNext());

    }

    private void showPhotoView() {
        showPhotoAdapter = uploadImageUtils.showPhoto(binding.rvShowPhoto, 3);

        showPhotoAdapter.setOnItemClickListener(new GridImageOnItemClick(showPhotoAdapter, uploadImageUtils,2));
    }


    private void doorHeaderView() {
        doorHeaderAdapter = uploadImageUtils.showPhoto(binding.rvDoorHeader, 1);

        doorHeaderAdapter.setOnItemClickListener(new GridImageOnItemClick(doorHeaderAdapter, uploadImageUtils, 1));
    }


    @SuppressLint("NewApi")
    private void toNext() {
        if (doorHeaderAdapter.getData() != null) {
            applyReq.merchantImage = doorHeaderAdapter.getData().get(0).getPath();
        }

        if (showPhotoAdapter.getData() != null) {
            List<String> merchantList = new ArrayList<>();
            for (LocalMedia localMedia : showPhotoAdapter.getData()) {
                merchantList.add(localMedia.getPath());
            }
            applyReq.merchantPhoto = String.join(",", merchantList);
        }


        Map<CheckBean, String> checkParam = new LinkedHashMap();

        checkParam.put(new CheckBean(applyReq.merchantImage), "请上传店铺门头照");
        checkParam.put(new CheckBean(applyReq.merchantPhoto), "请上传商家展示图");
        checkParam.put(new CheckBean(applyReq.merchantName), "请填写店铺名称");
        checkParam.put(new CheckBean(applyReq.consumerHotline), "请填写客服电话");
        checkParam.put(new CheckBean(applyReq.businessHours), "请填写营业时间");
        checkParam.put(new CheckBean(INTEGER, applyReq.cateId), "请选择服务品类");
        checkParam.put(new CheckBean(applyReq.goodsDetail), "请填写店铺介绍");
        checkParam.put(new CheckBean(applyReq.merchantProvince), "请选择服务地区");
        checkParam.put(new CheckBean(applyReq.merchantCity), "请选择服务地区");
        checkParam.put(new CheckBean(applyReq.merchantDistrict), "请选择服务地区");
        checkParam.put(new CheckBean(applyReq.addressDetail), "请填写详情地址");


        String msg = CheckNotNullUtils.checkNotNull(checkParam);
        if (!StringUtils.isEmpty(msg)) {
            ToastUtils.showShort(msg);
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable("apply", applyReq);
        ActivityUtils.startActivity(bundle, CertificateAUTActivity.class);
    }


    @Override
    protected void initData() {

        applyReq = getParcelableExtra("apply");

        serveVM = getViewModel(ServeViewModel.class);

        serveVM.cateList();
        serveVM.sortLiveData.observe(this, sortResp -> {
            localParseUtils.buildSortData(sortResp);
        });

        uploadVM.uploadLiveData.observe(this, uploadRes -> {
            LocalMedia localMedia = new LocalMedia();
            localMedia.setPath(uploadRes.getData().uploadUrl);
            if (uploadImageUtils.getSource() == 1) {
                doorHeaderAdapter.addData(localMedia);
            } else if (uploadImageUtils.getSource() == 2) {
                showPhotoAdapter.addData(localMedia);
            }
        });
    }

    @Override
    public View initLayout() {
        binding = ActivityStoreAutBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onSort(SortResp sort1, SortResp sort2, SortResp sort3) {
        applyReq.cateId = sort3.id;
        binding.stvServeSort.getRightTextView().setText(sort3.cateName);
    }

    @Override
    public void onAddress(String province, String city, String area) {
        applyReq.merchantProvince = province;
        applyReq.merchantCity = city;
        applyReq.merchantDistrict = area;
        binding.stvMakeAddress.getRightTextView().setText(province + city + area);
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
