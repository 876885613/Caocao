package com.caocao.client.ui.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivitySearchBinding;
import com.caocao.client.http.entity.respons.GoodsResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.ServeListAdapter;
import com.caocao.client.ui.serve.googs.GoodsDetailsActivity;
import com.caocao.client.weight.DividerItemDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

public class SearchActivity extends BaseActivity {

    private ActivitySearchBinding binding;
    private HomeViewModel         homeVM;
    private ServeListAdapter      serveAdapter;
    private String                keyword;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("搜索")
                .builder();
    }

    @Override
    protected void initView() {
        binding.rvList.setLayoutManager(new LinearLayoutManager(this));

        binding.rvList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_f5_5));
        serveAdapter = new ServeListAdapter(R.layout.adapter_search_item, null);
        binding.rvList.setAdapter(serveAdapter);


        serveAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("goodsId", serveAdapter.getData().get(position).goodsId);
            ActivityUtils.startActivity(bundle, GoodsDetailsActivity.class);
        });


        //刷新和加载
        binding.refresh.setEnableRefresh(false);
        binding.refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                homeVM.homeSearchGoodsMore(keyword);
            }
        });
    }

    @Override
    protected void initData() {
        keyword = getIntent().getStringExtra("keyword");
        LogUtils.e(keyword);

        homeVM = getViewModel(HomeViewModel.class);
        homeVM.homeSearchGoods(keyword);

        homeVM.homeIndexGoodsLiveData.observe(this, searchGoods -> {
            List<GoodsResp> goodsRes = searchGoods.getData();
            if (searchGoods.getPage() == 1) {
                serveAdapter.setNewData(goodsRes);
            } else {
                if (goodsRes == null || goodsRes.size() == 0) {
                    serveAdapter.loadMoreEnd();
                    return;
                }
                serveAdapter.addData(goodsRes);
            }

        });
    }

    @Override
    public View initLayout() {
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
