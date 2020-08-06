package com.caocao.client.ui.demand;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.TimeUtils;
import com.caocao.client.http.BaseViewModel;
import com.caocao.client.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DemandViewModel extends BaseViewModel {

    public DemandViewModel() {
    }

    public List<String> getYMD() {
        List<String> yearList = new ArrayList<>();
        yearList.add(TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd")));
        yearList.add(DateUtils.beforeAfterDate(1));
        yearList.add(DateUtils.beforeAfterDate(2));
        yearList.add(DateUtils.beforeAfterDate(3));
        return yearList;
    }

    public List<String> getHour() {
        List<String> hourList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            hourList.add(i + "时");
        }
        return hourList;
    }


    public List<String> getMin() {
        List<String> minList = new ArrayList<>();
        minList.add("00分钟");
        minList.add("30分钟");
        return minList;
    }
}
