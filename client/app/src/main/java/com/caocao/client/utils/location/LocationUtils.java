package com.caocao.client.utils.location;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.caocao.client.base.app.BaseApplication;


public class LocationUtils {

    private static LocationUtils sInstance;

    private static LocationClient sLocationClient;

    private static MyLocationListener myListener = new MyLocationListener();
    private static Handler            sHandler;


    public static LocationUtils getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LocationUtils.class) {
                if (sInstance == null) {
                    sInstance = new LocationUtils(context);
                }
            }
        }
        return sInstance;
    }


    public LocationUtils(Context context) {
        //声明LocationClient类
        sLocationClient = new LocationClient(context);
        //注册监听函数
        sLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        option.setIsNeedAddress(true);
        //可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
        option.setNeedNewVersionRgc(true);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        sLocationClient.setLocOption(option);
    }

    //开启定位
    public static void onStartLocation() {
        sLocationClient.start();
    }


    public static class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            double latitude = location.getLatitude();  //获取纬度信息
            double longitude = location.getLongitude(); //获取经度信息
            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            String adcode = location.getAdCode();    //获取adcode
            String town = location.getTown();    //获取乡镇信息

            BaseApplication.sLatitude = latitude;
            BaseApplication.sLongitude = longitude;
            BaseApplication.sRegion = province + "," + city + "," + district;
//            BaseApplication.sRegion = "山东省,济南市,市中区";

            Message msg = new Message();
            msg.what = 200;
            msg.obj = district;
            BaseApplication.getMainThreadHandler().sendMessage(msg);
        }
    }
}
