package com.caocao.client.ui.serve;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityRemarkMoreBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.RemarkAdapter;
import com.caocao.client.utils.RefreshUtils;
import com.caocao.client.weight.DividerItemDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.serve
 * @ClassName: RemarkMoreActivity
 * @Description: 更多评论
 * @Author: XuYu
 * @CreateDate: 2020/8/11 11:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/11 11:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RemarkMoreActivity extends BaseActivity {

    private ActivityRemarkMoreBinding binding;

    private ServeViewModel serveVM;
    private int            goodsId;
    private RemarkAdapter adapter;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("更多评论")
                .builder();
    }

    @Override
    protected void initView() {

        binding.rvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST,R.drawable.divider_10));
        adapter = new RemarkAdapter(R.layout.adapter_remrk_item, null);
        binding.rvList.setAdapter(adapter);

        //刷新和加载
        binding.refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                serveVM.orderRemarkList(8);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                serveVM.orderRemarkListMore(8);
            }
        });
    }

    @Override
    protected void initData() {
        goodsId = getIntent().getIntExtra("goodsId", 0);
        serveVM = getViewModel(ServeViewModel.class);
        serveVM.orderRemarkList(8);

        serveVM.remarkLiveData.observe(this, remarkRes -> {
            if (remarkRes == null) {
                return;
            }
            if (remarkRes.getPage() == 1) {
                adapter.setNewData(remarkRes.getData());
            } else {
                adapter.addData(remarkRes.getData());
            }
            RefreshUtils.setNoMore(binding.refresh, remarkRes.getPage(), remarkRes.getData().size());
        });
    }

    @Override
    public View initLayout() {
        binding = ActivityRemarkMoreBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
