package com.caocao.client.ui.serve.level;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivitySecondLevelBinding;
import com.caocao.client.http.entity.respons.GoodsResp;
import com.caocao.client.http.entity.respons.SortResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.HomeSortAdapter;
import com.caocao.client.ui.adapter.ServeListAdapter;
import com.caocao.client.ui.serve.ServeListActivity;
import com.caocao.client.ui.serve.ServeViewModel;
import com.caocao.client.ui.serve.googs.GoodsDetailsActivity;
import com.caocao.client.utils.RefreshUtils;
import com.caocao.client.weight.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.goods
 * @ClassName: SecondLevelActivity
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/10 11:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/10 11:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SecondLevelActivity extends BaseActivity {

    private ActivitySecondLevelBinding binding;
    private HomeSortAdapter            sortAdapter;
    private ServeViewModel             serveVM;
    private int                        cateId;
    private ServeListAdapter           serveAdapter;

    @Override
    protected void initTitle() {
        String title = getIntent().getStringExtra("title");
        new DefaultNavigationBar.Builder(this)
                .setTitle(title)
                .builder();
    }

    @Override
    protected void initView() {
        sortView();
        serveView();
    }

    private void serveView() {
        binding.secondServe.tvServeTitle.setText("热门服务");

        binding.secondServe.rvList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        binding.secondServe.rvList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        serveAdapter = new ServeListAdapter(R.layout.adapter_serve_item, null);
        binding.secondServe.rvList.setAdapter(serveAdapter);


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
                serveVM.goodsByCateMore(cateId);
            }
        });
    }

    private void sortView() {


        binding.rvSort.setLayoutManager(new GridLayoutManager(this, 5));
        sortAdapter = new HomeSortAdapter(R.layout.adapter_second_sort_item, null);
        binding.rvSort.setAdapter(sortAdapter);

        sortAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                cateId = sortAdapter.getData().get(position).id;
                String cateName = sortAdapter.getData().get(position).cateName;

                binding.secondServe.tvServeTitle.setText(cateName);

                serveVM.goodsByCate(cateId);

//                Bundle bundle = new Bundle();
//                bundle.putInt("id", cateId);
//                ActivityUtils.startActivity(bundle, ServeListActivity.class);
            }
        });

    }

    @Override
    protected void initData() {
        cateId = getIntent().getIntExtra("id", 0);
        serveVM = getViewModel(ServeViewModel.class);
        serveVM.secondSort(cateId);
        serveVM.sortLiveData.observe(this, sortResp -> {
            List<SortResp> sortList = sortResp.getData();
            if (sortList == null || sortList.size() == 0) {
                binding.rvSort.setVisibility(View.GONE);
                return;
            }
            sortAdapter.setNewData(sortList);
        });

        serveVM.goodsByCate(cateId);

        serveVM.indexGoodsLiveData.observe(this, indexGoods -> {

            List<GoodsResp> goodsRes = indexGoods.getData();

            if (goodsRes == null || goodsRes.size() == 0) {
                binding.secondServe.rvList.setVisibility(View.GONE);
                return;
            }

            binding.secondServe.rvList.setVisibility(View.VISIBLE);
            if (indexGoods.getPage() == 1) {
                serveAdapter.setNewData(goodsRes);
            } else {
                serveAdapter.addData(goodsRes);
            }
            RefreshUtils.setNoMore(binding.refresh, indexGoods.getPage(), goodsRes.size());
        });
    }

    @Override
    public View initLayout() {
        binding = ActivitySecondLevelBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
