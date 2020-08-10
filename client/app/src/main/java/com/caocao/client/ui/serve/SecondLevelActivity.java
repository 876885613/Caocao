package com.caocao.client.ui.serve;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivitySecondLevelBinding;
import com.caocao.client.http.entity.respons.GoodsResp;
import com.caocao.client.http.entity.respons.SortResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.HomeSortAdapter;
import com.caocao.client.ui.adapter.ServeListAdapter;
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
    private int                        id;
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




        //刷新和加载
        binding.refresh.setEnableRefresh(false);
        binding.refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                serveVM.goodsByCateMore(id);
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

            }
        });

    }

    @Override
    protected void initData() {
        id = getIntent().getIntExtra("id", 0);
        serveVM = getViewModel(ServeViewModel.class);
        serveVM.secondSort(id);
        serveVM.sortLiveData.observe(this, sortResp -> {
            List<SortResp> sortList = sortResp.getData();
            if (sortList == null || sortList.size() == 0) {
                binding.rvSort.setVisibility(View.GONE);
                return;
            }
            sortAdapter.setNewData(sortList);
        });

        serveVM.goodsByCate(id);

        serveVM.indexGoodsLiveData.observe(this, indexGoods -> {
            List<GoodsResp> goodsRes = indexGoods.getData();
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
