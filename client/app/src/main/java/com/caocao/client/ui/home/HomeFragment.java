package com.caocao.client.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseFragment;
import com.caocao.client.databinding.FragmentHomeBinding;
import com.caocao.client.http.entity.respons.BannerResp;
import com.caocao.client.http.entity.respons.GoodsResp;
import com.caocao.client.http.entity.respons.SortResp;
import com.caocao.client.ui.adapter.ADBannerAdapter;
import com.caocao.client.ui.adapter.HomeBannerAdapter;
import com.caocao.client.ui.adapter.HomeSortAdapter;
import com.caocao.client.ui.adapter.ServeListAdapter;
import com.caocao.client.ui.serve.GoodsDetailsActivity;
import com.caocao.client.ui.serve.SecondLevelActivity;
import com.caocao.client.utils.location.LocationUtils;
import com.caocao.client.utils.location.RxPermissionListener;
import com.caocao.client.utils.location.RxPermissionManager;
import com.caocao.client.weight.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.BannerUtils;

import java.util.ArrayList;
import java.util.List;

import static com.caocao.client.base.app.BaseApplication.setOnHandlerListener;

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
public class HomeFragment extends BaseFragment implements RxPermissionListener {

    private FragmentHomeBinding binding;

    private HomeViewModel   homeVM;
    private HomeSortAdapter sortAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RxPermissionManager.requestPermissions(this, this,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION);


        setOnHandlerListener(msg -> {
            String district = (String) msg.obj;
            binding.homeTop.tvAddress.setText(district);
            binding.tvAddress.setText(district);

            homeVM.homeChoiceGoods();

            homeVM.homeIndexGoods();

        });
    }


    @Override
    protected void initVmData(Bundle savedInstanceState) {


        homeVM = getViewModel(HomeViewModel.class);


        homeBanner();

        homeSort();

        homeSift();
    }

    private void homeSift() {
        homeVM.homeChoiceGoodsLiveData.observe(this, choiceGoods -> {
            LogUtils.e(choiceGoods);
        });
    }


    private void homeSort() {
        homeVM.homeSort();
        homeVM.homeSortLiveData.observe(this, homeSort -> {
            List<SortResp> sortRes = homeSort.getData();
            sortRes.add(new SortResp(0, "更多", R.mipmap.ic_more));
            sortAdapter.setNewData(sortRes);
        });
    }

    private void homeBanner() {
        homeVM.homeBanner();
        homeVM.homeBannerLiveData.observe(this, homeBanner -> {
            List<BannerResp> bannerRes = homeBanner.getData();

            binding.homeTop.banner.addBannerLifecycleObserver(this)//添加生命周期观察者
                    .setAdapter(new HomeBannerAdapter(context, bannerRes))//添加数据
                    .setIndicator(new CircleIndicator(context), true)//设置轮播指示器
                    .setIndicatorNormalColor(Color.parseColor("#aaffffff"))
                    .setIndicatorSelectedColor(Color.parseColor("#ffffff"))
                    .setIndicatorNormalWidth((int) BannerUtils.dp2px(6))
                    .setIndicatorSelectedWidth((int) BannerUtils.dp2px(6))
                    .start();
        });
    }

    @Override
    protected void initView() {
        scrollView();

        initSearch();

        sortView();

        adView();

        serveView();
    }


    //搜索功能
    private void initSearch() {
        binding.homeTop.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Bundle bundle = new Bundle();
                    bundle.putString("keyword", textView.getText().toString());
                    ActivityUtils.startActivity(bundle, SearchActivity.class);
                    KeyboardUtils.hideSoftInput(binding.homeTop.etSearch);
                    return true;
                }
                return false;
            }
        });

        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Bundle bundle = new Bundle();
                    bundle.putString("keyword", textView.getText().toString());
                    ActivityUtils.startActivity(bundle, SearchActivity.class);
                    KeyboardUtils.hideSoftInput(binding.homeTop.etSearch);
                    return true;
                }
                return false;
            }
        });
    }


    //分类
    private void sortView() {
        binding.rvSort.setLayoutManager(new GridLayoutManager(context, 5));
        sortAdapter = new HomeSortAdapter(R.layout.adapter_home_sort_item, null);
        binding.rvSort.setAdapter(sortAdapter);

        sortAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("title", sortAdapter.getData().get(position).cateName);
                bundle.putInt("id", sortAdapter.getData().get(position).id);
                ActivityUtils.startActivity(bundle, SecondLevelActivity.class);
            }
        });
    }

    //服务列表
    private void serveView() {
        binding.homeServe.rvList.setLayoutManager(new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        binding.homeServe.rvList.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL_LIST));
        ServeListAdapter serveAdapter = new ServeListAdapter(R.layout.adapter_serve_item, null);
        binding.homeServe.rvList.setAdapter(serveAdapter);


        homeVM.homeIndexGoodsLiveData.observe(this, homeIndexGoods -> {
            List<GoodsResp> goodsRes = homeIndexGoods.getData();

            if (homeIndexGoods.getPage() == 1) {
                serveAdapter.setNewData(goodsRes);
            } else {
                if (goodsRes == null || goodsRes.size() == 0) {
                    serveAdapter.loadMoreEnd();
                    return;
                }
                serveAdapter.addData(goodsRes);
                serveAdapter.loadMoreComplete();
            }
        });

        serveAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int goodsId = serveAdapter.getData().get(position).goodsId;
                Bundle bundle = new Bundle();
                bundle.putInt("goodsId", goodsId);
                ActivityUtils.startActivity(bundle, GoodsDetailsActivity.class);
            }
        });



        //刷新和加载
        binding.homeServe.refresh.setHeaderHeight(0);
        binding.homeServe.refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                serveVM.goodsByCateMore(id);
            }
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

        binding.scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView view, int i, int i1, int i2, int i3) {

                if (i3 > height && i3 < i1) {
                    binding.rlSearch.setVisibility(View.VISIBLE);
                    binding.homeTop.rlSearch.setVisibility(View.GONE);
                    binding.rlLogo.setVisibility(View.GONE);
                } else if (i1 == 0) {
                    binding.rlSearch.setVisibility(View.GONE);
                    binding.homeTop.rlSearch.setVisibility(View.VISIBLE);
                    binding.rlLogo.setVisibility(View.VISIBLE);
                }

                if (i1 == (view.getChildAt(0).getMeasuredHeight() - view.getMeasuredHeight())) {
                    homeVM.homeIndexGoodsMore();
                }
            }
        });

    }

    @Override
    protected View initViewBind(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }



    @Override
    public void accept() {
        //定位
        LocationUtils.getInstance(context.getApplicationContext()).onStartLocation();
    }

    @Override
    public void refuse() {
        LogUtils.e("权限refuse");
    }

    @Override
    public void noAsk(String permissionName) {
        LogUtils.e("权限noAsk");
        RxPermissionManager.showPermissionDialog(this, permissionName);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LocationUtils.getInstance(context.getApplicationContext()).onStartLocation();
    }
}
