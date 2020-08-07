package com.caocao.client.ui.demand;


import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.databinding.ActivityDemandBinding;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.coder.baselibrary.dialog.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class DemandActivity extends BaseActivity implements View.OnClickListener {

    private ActivityDemandBinding binding;
    private DemandViewModel demandVm;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("发布需求")
                .builder();
    }

    @Override
    protected void initView() {
        binding.stvSort.getRightTextView().setOnClickListener(view -> selectSortDialog());
        binding.stvMakeSum.getLeftTextView().setOnClickListener(view -> showHintDialog("关于预约金",
                "预约金是您预先支付的定金，您交付后，平台将暂时替您保管，在您确认完成需求后，在将钱付给服务人员，需求如果为完成，将退还给你。"));
        binding.stvServeTime.getRightTextView().setOnClickListener(view -> selectServeTimeDialog());

    }


    private void showHintDialog(String title, String content) {
        new AlertDialog.Builder(this, R.style.DialogAlter)
                .setView(R.layout.dailog_release_hint)
                .fullWidth()
                .setText(R.id.tv_title, title)
                .setText(R.id.tv_content, content)
                .setOnClickListener(R.id.iv_dismiss, null)
                .show();
    }

    @Override
    protected void initData() {
        demandVm = getViewModel(DemandViewModel.class);
    }

    @Override
    public View initLayout() {
        binding = ActivityDemandBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }


    //分类选择监听器
    OnOptionsSelectListener sortSelectListener = (options1, option2, options3, v) -> {
        //返回的分别是三个级别的选中位置
//                String tx = options1Items.get(options1).getPickerViewText()
//                        + options2Items.get(options1).get(option2)
//                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
////                tvOptions.setText(tx);
    };

    //服务时间选择监听器
    OnOptionsSelectListener serveTimeSelectListener = (options1, options2, options3, v) -> {

//            String str = "food:" + food.get(options1)
//                    + "\nclothes:" + clothes.get(options2)
//                    + "\ncomputer:" + computer.get(options3);
//
//            Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
    };

    private void selectSortDialog() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, sortSelectListener)
                .setSubmitColor(getResources().getColor(R.color.color_theme))
                .setCancelColor(getResources().getColor(R.color.color_theme))
                .setTitleText("请选择")
                .setTitleColor(getResources().getColor(R.color.t1))
                .setLineSpacingMultiplier(2f)
                .build();
        pvOptions.setPicker(getFirstSort(), getSecondSort(), getThreeSort());
        pvOptions.show();
    }


    private void selectServeTimeDialog() {
        OptionsPickerView pvNoOptions = new OptionsPickerBuilder(this, serveTimeSelectListener)
                .setSubmitColor(getResources().getColor(R.color.color_theme))
                .setCancelColor(getResources().getColor(R.color.color_theme))
                .setTitleText("请选择")
                .setTitleColor(getResources().getColor(R.color.t1))
                .setLineSpacingMultiplier(2f)
                .build();

        pvNoOptions.setNPicker(demandVm.getYMD(), demandVm.getHour(), demandVm.getMin());
        pvNoOptions.show();
    }


    private List<String> getFirstSort() {
        List<String> sort = new ArrayList<>();
        sort.add("消毒杀菌");
        sort.add("窗帘清洗");
        sort.add("洗衣机清洗");
        sort.add("油烟机清洗");
        sort.add("冰箱清洗");
        return sort;
    }

    private List<List<String>> getSecondSort() {
        List<List<String>> sort = new ArrayList<>();
        sort.add(getFirstSort());
        sort.add(getFirstSort());
        sort.add(getFirstSort());
        sort.add(getFirstSort());
        sort.add(getFirstSort());
        return sort;
    }

    private List<List<List<String>>> getThreeSort() {
        List<List<List<String>>> sort = new ArrayList<>();
        sort.add(getSecondSort());
        sort.add(getSecondSort());
        sort.add(getSecondSort());
        sort.add(getSecondSort());
        sort.add(getSecondSort());
        return sort;
    }
}
