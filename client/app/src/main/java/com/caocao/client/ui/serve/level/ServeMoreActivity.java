package com.caocao.client.ui.serve.level;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityServeMoreBinding;
import com.caocao.client.http.entity.respons.SortResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.HomeSortAdapter;
import com.caocao.client.ui.adapter.ServeLevelAdapter;
import com.caocao.client.ui.serve.ServeListActivity;
import com.caocao.client.ui.serve.ServeViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.serve.level
 * @ClassName: ServeMoreActivity
 * @Description: 更多服务
 * @Author: XuYu
 * @CreateDate: 2020/8/11 16:53
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/11 16:53
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ServeMoreActivity extends BaseActivity {

    private ActivityServeMoreBinding binding;


    private ServeLevelAdapter levelAdapter;
    private HomeSortAdapter sortAdapter;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("更多服务")
                .builder();
    }

    @Override
    protected void initView() {
        binding.rvOne.setLayoutManager(new LinearLayoutManager(this));
        levelAdapter = new ServeLevelAdapter(R.layout.adapter_level_item, null);
        binding.rvOne.setAdapter(levelAdapter);


        binding.rvSecond.setLayoutManager(new GridLayoutManager(ServeMoreActivity.this, 3));
        sortAdapter = new HomeSortAdapter(R.layout.adapter_home_sort_item, null);
        binding.rvSecond.setAdapter(sortAdapter);

        levelAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                levelAdapter.setPosition(position);
                binding.tvTitle.setText(levelAdapter.getData().get(position).cateName);
                List<SortResp> children = levelAdapter.getData().get(position).children;
                if (children != null && children.size() > 0) {
                    binding.rlSecond.setVisibility(View.VISIBLE);
                    binding.tvChildrenTitle.setText(levelAdapter.getData().get(position).children.get(0).cateName);
                    List<SortResp> childrenChildren = levelAdapter.getData().get(position).children.get(0).children;
                    if (childrenChildren != null && childrenChildren.size() > 0) {
                        sortAdapter.setNewData(childrenChildren);
                    }else {
                        sortAdapter.setNewData(null);
                    }
                } else {
                    binding.rlSecond.setVisibility(View.INVISIBLE);
                }
            }
        });


        sortAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int cateId = sortAdapter.getData().get(position).id;
                Bundle bundle = new Bundle();
                bundle.putInt("id",cateId);
                ActivityUtils.startActivity(bundle, ServeListActivity.class);
            }
        });
    }

    @Override
    protected void initData() {
        ServeViewModel serveVM = getViewModel(ServeViewModel.class);

        serveVM.cateList();

        serveVM.sortLiveData.observe(this, sortRes -> {
            levelAdapter.setNewData(sortRes.getData());
            binding.tvChildrenTitle.setText(levelAdapter.getData().get(0).children.get(0).cateName);
            List<SortResp> children = levelAdapter.getData().get(0).children.get(0).children;
            sortAdapter.setNewData(children);
        });
    }

    @Override
    public View initLayout() {
        binding = ActivityServeMoreBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
