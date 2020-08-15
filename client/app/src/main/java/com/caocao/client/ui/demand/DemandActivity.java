package com.caocao.client.ui.demand;


import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.blankj.utilcode.util.LogUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityDemandBinding;
import com.caocao.client.http.entity.request.DemandReq;
import com.caocao.client.http.entity.respons.SortResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.me.address.OnAddressCallBackListener;
import com.caocao.client.ui.serve.ServeViewModel;
import com.caocao.client.ui.wrapper.TextWatcherWrapper;
import com.caocao.client.utils.DateUtils;
import com.caocao.client.utils.LocalParseUtils;
import com.coder.baselibrary.dialog.AlertDialog;

import java.util.Date;

public class DemandActivity extends BaseActivity implements View.OnClickListener, OnSortCallBackListener, OnAddressCallBackListener {

    private ActivityDemandBinding binding;
    private DemandViewModel demandVm;
    private ServeViewModel serveVM;
    private LocalParseUtils localParseUtils;
    private DemandReq demandReq;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        localParseUtils = LocalParseUtils.getInstance(getApplicationContext());
        localParseUtils.initAddressData();
        super.onCreate(savedInstanceState);
        demandReq = new DemandReq();
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("发布需求")
                .builder();
    }

    @Override
    protected void initView() {
        binding.stvMakeSum.getLeftTextView().setOnClickListener(view -> showHintDialog("关于预约金",
                "预约金是您预先支付的定金，您交付后，平台将暂时替您保管，在您确认完成需求后，在将钱付给服务人员，需求如果为完成，将退还给你。"));

        binding.stvSort.setOnClickListener(this);


        binding.stvMakeSum.setRightEditTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                demandReq.reservePrice = s.toString();
            }
        });

        binding.stvExpectSum.setRightEditTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                demandReq.expectedPrice = s.toString();
            }
        });

        binding.stvIndate.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localParseUtils.showServeTimeDialog(DemandActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        try {
                            String YMD = localParseUtils.getServeYMD().get(options1);
                            String hour = localParseUtils.getServeHour().get(options2);
                            String min = localParseUtils.getServeMin().get(options3);
                            binding.stvIndate.getRightTextView().setText(getString(R.string.goods_serve_time, YMD, hour, min));
                            Date endDate = DateUtils.stringToDate(getString(R.string.goods_serve_time, YMD, hour, min),
                                    "yyyy-MM-dd  HH:mm");
                            demandReq.endTime = endDate;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        binding.etDemandContent.setContentTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                demandReq.demandDepict = s.toString();
            }
        });

        binding.stvServeTime.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localParseUtils.showServeTimeDialog(DemandActivity.this,new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        try {
                            String YMD = localParseUtils.getServeYMD().get(options1);
                            String hour = localParseUtils.getServeHour().get(options2);
                            String min = localParseUtils.getServeMin().get(options3);
                            binding.stvServeTime.getRightTextView().setText(getString(R.string.goods_serve_time, YMD, hour, min));
                            Date endDate = DateUtils.stringToDate(getString(R.string.goods_serve_time, YMD, hour, min),
                                    "yyyy-MM-dd  HH:mm");
                            demandReq.serviceTime = endDate;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        binding.stvContacts.setRightEditTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                demandReq.contactPerson = s.toString();
            }
        });

        binding.stvTel.setRightEditTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                demandReq.mobile = s.toString();
            }
        });

        binding.stvMakeAddress.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localParseUtils.showAddressDialog(DemandActivity.this, DemandActivity.this);
            }
        });

        binding.etAddress.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                demandReq.address = s.toString();
            }
        });

        binding.tvRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demandVm.createDemand(demandReq);
            }
        });
    }


    private void showHintDialog(String title, String content) {
        new AlertDialog.Builder(this, R.style.DialogAlter)
                .setView(R.layout.dialog_release_hint)
                .fullWidth()
                .setText(R.id.tv_title, title)
                .setText(R.id.tv_content, content)
                .setOnClickListener(R.id.iv_dismiss, null)
                .show();
    }

    @Override
    protected void initData() {
        serveVM = getViewModel(ServeViewModel.class);
        demandVm = getViewModel(DemandViewModel.class);

        serveVM.cateList();

        serveVM.sortLiveData.observe(this, sortResp -> {
            localParseUtils.buildSortData(sortResp);
        });

        demandVm.baseResp.observe(this, demandReq -> {
            LogUtils.e(demandReq);

        });

    }


    @Override
    public View initLayout() {
        binding = ActivityDemandBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stv_sort:
                localParseUtils.showSortDialog(this,this);
                break;
        }
    }


    @Override
    public void onSort(SortResp sort1, SortResp sort2, SortResp sort3) {
        demandReq.cateId = sort3.id;
        binding.stvSort.getRightTextView().setText(sort3.cateName);
    }

    @Override
    public void onAddress(String province, String city, String area) {
        demandReq.province = province;
        demandReq.city = city;
        demandReq.area = area;
        binding.stvMakeAddress.getRightTextView().setText(province + city + area);
    }
}
