package com.caocao.client.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.R;
import com.caocao.client.http.entity.respons.SortResp;
import com.caocao.client.ui.demand.OnSortCallBackListener;
import com.caocao.client.ui.me.address.OnAddressCallBackListener;
import com.caocao.client.ui.me.address.bean.AddressBean;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.me.address
 * @ClassName: LocalParseUtils
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/12 16:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/12 16:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LocalParseUtils {

    public static LocalParseUtils sInstance;
    private final Context         mContent;


    //地址
    private List<AddressBean>                       address1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>>            address2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> address3Items = new ArrayList<>();

    //分类
    private List<SortResp>             sort1Items = new ArrayList<>();
    private List<List<SortResp>>       sort2Items = new ArrayList<>();
    private List<List<List<SortResp>>> sort3Items = new ArrayList<>();

    public static LocalParseUtils getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LocalParseUtils.class) {
                if (sInstance == null) {
                    sInstance = new LocalParseUtils(context);
                }
            }
        }
        return sInstance;
    }

    private LocalParseUtils(Context context) {
        this.mContent = context;
    }


    /**
     * 地址选择
     *
     * @param listener
     */
    public void showAddressDialog(Context context, OnAddressCallBackListener listener) {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = address1Items.size() > 0 ?
                        address1Items.get(options1).getPickerViewText() : "";

                String opt2tx = address2Items.size() > 0
                        && address2Items.get(options1).size() > 0 ?
                        address2Items.get(options1).get(options2) : "";

                String opt3tx = address2Items.size() > 0
                        && address3Items.get(options1).size() > 0
                        && address3Items.get(options1).get(options2).size() > 0 ?
                        address3Items.get(options1).get(options2).get(options3) : "";

                listener.onAddress(opt1tx, opt2tx, opt3tx);
            }
        })
                .setSubmitColor(context.getResources().getColor(R.color.color_theme))
                .setCancelColor(context.getResources().getColor(R.color.color_theme))
                .setTitleText("请选择")
                .setTitleColor(context.getResources().getColor(R.color.t1))
                .setLineSpacingMultiplier(2f)
                .build();

        pvOptions.setPicker(address1Items, address2Items, address3Items);//三级选择器
        pvOptions.show();
    }


    public void initAddressData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = getJson(mContent, "province.json");//获取assets目录下的json文件数据
        ArrayList<AddressBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        address1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }
            /**
             * 添加城市数据
             */
            address2Items.add(cityList);
            /**
             * 添加地区数据
             */
            address3Items.add(province_AreaList);
        }
    }


    private String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private ArrayList<AddressBean> parseData(String result) {//Gson 解析
        ArrayList<AddressBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                AddressBean entity = gson.fromJson(data.optJSONObject(i).toString(), AddressBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }


    /**
     * 时间弹窗
     */
    public void showServeTimeDialog(Context context, OnOptionsSelectListener listener) {
        OptionsPickerView pvNoOptions = new OptionsPickerBuilder(context, listener)
                .setSubmitColor(context.getResources().getColor(R.color.color_theme))
                .setCancelColor(context.getResources().getColor(R.color.color_theme))
                .setLabels("", "时", "分钟")
                .setTitleText("请选择")
                .setTitleColor(context.getResources().getColor(R.color.t1))
                .setLineSpacingMultiplier(2f)
                .build();

        pvNoOptions.setNPicker(getServeYMD(), getServeHour(), getServeMin());
        pvNoOptions.show();
    }

    public List<String> getServeYMD() {
        List<String> yearList = new ArrayList<>();
        yearList.add(TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd")));
        yearList.add(DateUtils.beforeAfterDate(1));
        yearList.add(DateUtils.beforeAfterDate(2));
        yearList.add(DateUtils.beforeAfterDate(3));
        return yearList;
    }

    public List<String> getServeHour() {
        List<String> hourList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            DecimalFormat decimalFormat = new DecimalFormat("00");
            String hour = decimalFormat.format(i);
            hourList.add(hour);
        }
        return hourList;
    }


    public List<String> getServeMin() {
        List<String> minList = new ArrayList<>();
        minList.add("00");
        minList.add("30");
        return minList;
    }


    /**
     * 分类弹出
     *
     * @param listener
     */
    public void showSortDialog(Context context, OnSortCallBackListener listener) {
        if (sort1Items.size() == 0 || sort2Items.size() == 0 || sort3Items.size() == 0) {
            ToastUtils.showShort("分类数据为空");
            return;
        }

        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                //返回的分别是三个级别的选中位置
                SortResp opt1tx = sort1Items.size() > 0 ?
                        sort1Items.get(options1) : new SortResp();

                SortResp opt2tx = sort2Items.size() > 0
                        && sort2Items.get(options1).size() > 0 ?
                        sort2Items.get(options1).get(options2) : new SortResp();

                SortResp opt3tx = sort3Items.size() > 0
                        && sort3Items.get(options1).size() > 0
                        && sort3Items.get(options1).get(options2).size() > 0 ?
                        sort3Items.get(options1).get(options2).get(options3) : new SortResp();

                listener.onSort(opt1tx, opt2tx, opt3tx);

            }
        })
                .setSubmitColor(context.getResources().getColor(R.color.color_theme))
                .setCancelColor(context.getResources().getColor(R.color.color_theme))
                .setTitleText("请选择")
                .setTitleColor(context.getResources().getColor(R.color.t1))
                .setLineSpacingMultiplier(2f)
                .build();
        pvOptions.setPicker(sort1Items, sort2Items, sort3Items);
        pvOptions.show();
    }


    public void buildSortData(SortResp sortRes) {
        sort1Items = sortRes.getData();

        for (int i = 0; i < sort1Items.size(); i++) {
            ArrayList<SortResp> secondSortList = new ArrayList<>();
            ArrayList<List<SortResp>> threeSortList = new ArrayList<>();

            if (sort1Items.get(i).children == null || sort1Items.get(i).children.size() == 0) {
                secondSortList.add(new SortResp(0, "", 0));
                threeSortList.add(new ArrayList<>());
            }

            for (int c = 0; c < sort1Items.get(i).children.size(); c++) {//遍历一级分类下的所有二级分类
                SortResp secondSort = sort1Items.get(i).children.get(c);
                secondSortList.add(secondSort);//添加二级分类

                ArrayList<SortResp> threeSort = new ArrayList<>();//二级分类下的所有分类

                //如果无数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (sort1Items.get(i).children.get(c).children == null
                        || sort1Items.get(i).children.get(c).children.size() == 0) {
                    threeSort.addAll(new ArrayList<>());
                } else {
                    threeSort.addAll(sort1Items.get(i).children.get(c).children);
                }
                threeSortList.add(threeSort);//添加该省所有地区数据
            }
            /**
             * 添加二级分类
             */
            sort2Items.add(secondSortList);
            /**
             * 添加三级分类
             */
            sort3Items.add(threeSortList);
        }
    }
}
