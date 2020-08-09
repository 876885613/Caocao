package com.caocao.client.ui.home;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.LogUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivitySearchBinding;
import com.caocao.client.http.entity.respons.GoodsResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.ServeListAdapter;
import com.caocao.client.utils.AdapterLoadMoreView;
import com.caocao.client.weight.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

public class SearchActivity extends BaseActivity {

    private ActivitySearchBinding binding;
    private HomeViewModel homeVM;
    private ServeListAdapter serveAdapter;
    private String keyword;

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

        serveAdapter.setLoadMoreView(new AdapterLoadMoreView());
        serveAdapter.setOnLoadMoreListener(() -> homeVM.homeSearchGoodsMore(keyword), binding.rvList);

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
