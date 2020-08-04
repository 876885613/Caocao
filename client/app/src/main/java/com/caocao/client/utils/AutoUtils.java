package com.caocao.client.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.utils
 * @ClassName: AutoUtils
 * @Description: 基础的UI适配
 * @Author: XuYu
 * @CreateDate: 2020/8/3 10:42
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/3 10:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AutoUtils {
    private static float sNoCompatDensity;
    private static float sNoCompatScaleDensity;

    private static final float width = 375;

    public static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application) {

        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNoCompatDensity == 0) {
            sNoCompatDensity = appDisplayMetrics.density;
            sNoCompatScaleDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoCompatScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        final float targetDensity = appDisplayMetrics.widthPixels / width;
        final float targetScaleDensity = targetDensity * (sNoCompatScaleDensity / sNoCompatDensity);
        final int targetDensityDpi = (int) (targetDensity * 160);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
