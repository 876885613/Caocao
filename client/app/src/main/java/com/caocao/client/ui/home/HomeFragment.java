package com.caocao.client.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.LogUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseFragment;
import com.caocao.client.databinding.FragmentHomeBinding;
import com.caocao.client.http.entity.respons.SortResp;
import com.caocao.client.ui.MainViewModel;
import com.caocao.client.ui.adapter.ADBannerAdapter;
import com.caocao.client.ui.adapter.HomeSortAdapter;
import com.caocao.client.ui.adapter.ServeListAdapter;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.transformer.AlphaPageTransformer;
import com.youth.banner.util.BannerUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui
 * @ClassName: HomeFragment
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/3 16:54
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/3 16:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private FragmentHomeBinding binding;
    private MainViewModel       mainVM;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //初始化首页分类数据
    private List<SortResp> getSortData() {
        List<SortResp> sortList = new ArrayList<>();
        sortList.add(new SortResp(R.mipmap.ic_tongcheng, "曹操同城"));
        sortList.add(new SortResp(R.mipmap.ic_jiazheng, "曹操家政"));
        sortList.add(new SortResp(R.mipmap.ic_zhuangxiu, "曹操装修"));
        sortList.add(new SortResp(R.mipmap.ic_baomu, "曹操保姆"));
        sortList.add(new SortResp(R.mipmap.ic_baojie, "曹操保洁"));
        sortList.add(new SortResp(R.mipmap.ic_banjia, "曹操搬家"));
        sortList.add(new SortResp(R.mipmap.ic_weixiu, "曹操维修"));
        sortList.add(new SortResp(R.mipmap.ic_huishou, "曹操回收"));
        sortList.add(new SortResp(R.mipmap.ic_paotui, "曹操跑腿"));
        sortList.add(new SortResp(R.mipmap.ic_more, "更多"));
        return sortList;
    }

    @Override
    protected void initVmData(Bundle savedInstanceState) {
        mainVM = getViewModel(MainViewModel.class);
        mainVM.serveData();
    }

    @Override
    protected void initView() {
        scrollView();

        binding.rvSort.setLayoutManager(new GridLayoutManager(context, 5));
        HomeSortAdapter sortAdapter = new HomeSortAdapter(R.layout.adapter_home_sort_item, getSortData());
        binding.rvSort.setAdapter(sortAdapter);

        adView();

        serveView();
    }

    //服务列表
    private void serveView() {
        binding.homeServe.rvServe.setLayoutManager(new LinearLayoutManager(context));
        ServeListAdapter serveAdapter = new ServeListAdapter(R.layout.adapter_serve_item, null);
        binding.homeServe.rvServe.setAdapter(serveAdapter);

        mainVM.serveLiveData.observe(this, serveLiveData -> {
            serveAdapter.setNewData(serveLiveData);
        });
    }

    //咨询广告
    private void adView() {
        binding.homeConsult.adBanner.addBannerLifecycleObserver(this)//添加生命周期观察者
                .setAdapter(new ADBannerAdapter(context, getAdBanner()))//添加数据
                .setIndicator(new CircleIndicator(context), true)//设置轮播指示器
                .setIndicatorNormalColor(Color.parseColor("#aaffffff"))
                .setIndicatorSelectedColor(Color.parseColor("#ffffff"))
                .setIndicatorNormalWidth((int) BannerUtils.dp2px(6))
                .setIndicatorSelectedWidth((int) BannerUtils.dp2px(6))
                .start();
    }


    private List<Integer> getAdBanner() {
        List<Integer> adList = new ArrayList<>();
        adList.add(R.mipmap.ic_ad1);
        adList.add(R.mipmap.ic_ad2);
        return adList;
    }


    @SuppressLint("NewApi")
    private void scrollView() {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        binding.homeTop.rlSearch.measure(w, h);
        int height = binding.homeTop.rlSearch.getMeasuredHeight();

        binding.scroll.setOnScrollChangeListener((View.OnScrollChangeListener) (view, i, i1, i2, i3) -> {
            if (i3 > height && i3 < i1) {
                binding.rlSearch.setVisibility(View.VISIBLE);
                binding.homeTop.rlSearch.setVisibility(View.GONE);
                binding.rlLogo.setVisibility(View.GONE);
            } else if (i1 == 0) {
                binding.rlSearch.setVisibility(View.GONE);
                binding.homeTop.rlSearch.setVisibility(View.VISIBLE);
                binding.rlLogo.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected View initViewBind(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {

    }
}
