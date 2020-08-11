package com.caocao.client.ui.serve;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.LayoutRefreshListBinding;
import com.caocao.client.http.entity.respons.GoodsResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.ServeListAdapter;
import com.caocao.client.ui.serve.googs.GoodsDetailsActivity;
import com.caocao.client.utils.RefreshUtils;
import com.caocao.client.weight.DividerItemDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

public class ServeListActivity extends BaseActivity {

    private LayoutRefreshListBinding binding;

    private ServeListAdapter serveAdapter;
    private ServeViewModel serveVM;
    private int id;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("服务列表")
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
                serveVM.goodsByCate(id);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                serveVM.goodsByCateMore(id);
            }
        });
    }

    @Override
    protected void initData() {
        id = getIntent().getIntExtra("id", 0);
        serveVM = getViewModel(ServeViewModel.class);
        serveVM.goodsByCate(id);

        serveVM.indexGoodsLiveData.observe(this, goodsRes -> {
            List<GoodsResp> goods = goodsRes.getData();
            if (goodsRes.getPage() == 1) {
                serveAdapter.setNewData(goods);
            } else {
                serveAdapter.addData(goodsRes);
            }
            RefreshUtils.setNoMore(binding.refresh, goodsRes.getPage(), goods.size());
        });
    }

    @Override
    public View initLayout() {
        binding = LayoutRefreshListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
