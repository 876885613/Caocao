package com.caocao.client.utils.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.base.BaseFragment;
import com.caocao.client.ui.demand.DemandActivity;
import com.caocao.client.ui.serve.ServeGenreActivity;
import com.coder.baselibrary.dialog.AlertDialog;
import com.coder.baselibrary.dialog.OnClickListenerWrapper;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class RxPermissionManager {
    //存储不再询问权限
    private static ArrayList<Permission> noAskPermission = new ArrayList<>();
    //权限申请次数
    private static int requestCount;
    //拒绝权限的次数
    private static int refuseCount;


    /**
     * 申请多个权限，带拒绝权限并选择不再询问处理
     *
     * @param activity
     * @param permissions
     */
    @SuppressLint("CheckResult")
    public static void requestPermissions(BaseActivity activity, RxPermissionListener listener, String... permissions) {
        //每次清空一下
        noAskPermission.clear();
        requestCount = 0;
        refuseCount = 0;
        int length = permissions.length;
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions
                .requestEach(permissions)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(permissionNext -> {
                    if (permissionNext.granted) {
                        requestCount++;
                        //用户同意
                        handlePermission(length, listener);
                    } else if (permissionNext.shouldShowRequestPermissionRationale) {
                        requestCount++;
                        refuseCount++;
                        //用户拒绝
                        handlePermission(length, listener);
                    } else {
                        requestCount++;
                        //用户拒绝了权限，并选中了不再询问
                        noAskPermission.add(permissionNext);
                        handlePermission(length, listener);
                    }
                });
    }


    /**
     * 申请多个权限，带拒绝权限并选择不再询问处理
     *
     * @param fragment
     * @param permissions
     */
    @SuppressLint("CheckResult")
    public static void requestPermissions(Fragment fragment, RxPermissionListener listener, String... permissions) {
        //每次清空一下
        noAskPermission.clear();
        requestCount = 0;
        refuseCount = 0;
        int length = permissions.length;
        RxPermissions rxPermissions = new RxPermissions(fragment);
        rxPermissions
                .requestEach(permissions)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(permissionNext -> {
                    if (permissionNext.granted) {
                        requestCount++;
                        //用户同意
                        handlePermission(length, listener);
                    } else if (permissionNext.shouldShowRequestPermissionRationale) {
                        requestCount++;
                        refuseCount++;
                        //用户拒绝
                        handlePermission(length, listener);
                    } else {
                        requestCount++;
                        //用户拒绝了权限，并选中了不再询问
                        noAskPermission.add(permissionNext);
                        handlePermission(length, listener);
                    }
                });

    }

    /**
     * 处理多组权限问
     *
     * @param length
     * @param listener
     */
    private static void handlePermission(int length, RxPermissionListener listener) {
        //当权限个数等于申请次数时，证明所有权限都已申请完毕
        if (length == requestCount) {
            if (refuseCount == 0 && noAskPermission.size() == 0) {
                //权限全部申请通过
                listener.accept();
            } else if (noAskPermission.size() == 0) {
                //拒绝权限
                listener.refuse();
            } else {
                //拒绝权限，并勾选了不再提醒，弹窗提示去设置页
                String permissionName = getPermissionName();
                listener.noAsk(permissionName);
            }
        }
    }



    public static void showPermissionDialog(Activity activity, String permissionName) {
        new AlertDialog.Builder(activity, R.style.DialogAlter)
                .setView(R.layout.dialog_permission)
                .setText(R.id.tv_content, "请前往设置->应用权限中打开" + permissionName + "权限，否则功能无法正常运行.")
                .setOnClickListener(R.id.tv_cancel, null)
                .setOnClickListener(R.id.tv_open, new OnClickListenerWrapper() {
                    @Override
                    public void onClickCall(View v) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", AppUtils.getAppPackageName(), null);
                        intent.setData(uri);
                        activity.startActivityForResult(intent, 200);
                    }
                })
                .show();
    }

    public static void showPermissionDialog(Fragment fragment, String permissionName) {
        new AlertDialog.Builder(fragment.getActivity(), R.style.DialogAlter)
                .setView(R.layout.dialog_permission)
                .setText(R.id.tv_content, "请前往设置->应用权限中打开" + permissionName + "权限，否则功能无法正常运行.")
                .setOnClickListener(R.id.tv_cancel, null)
                .setOnClickListener(R.id.tv_open, new OnClickListenerWrapper() {
                    @Override
                    public void onClickCall(View v) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", AppUtils.getAppPackageName(), null);
                        intent.setData(uri);
                        fragment.startActivityForResult(intent, 200);
                    }
                })
                .show();
    }

    /**
     * 获取权限的名字
     *
     * @return
     */
    private static String getPermissionName() {
        if (noAskPermission != null && noAskPermission.size() != 0) {
            StringBuilder sb = new StringBuilder();
            for (Permission permission : noAskPermission) {
                Log.e("lwd", permission.name);
                switch (permission.name) {
                    case Manifest.permission.ACCESS_COARSE_LOCATION://定位权限
                        sb.append("定位");
                        sb.append("、");
                        break;
                    case Manifest.permission.ACCESS_FINE_LOCATION:
                        break;
                    case Manifest.permission.CAMERA://相机权限
                        sb.append("相机");
                        sb.append("、");
                        break;
                    case Manifest.permission.WRITE_EXTERNAL_STORAGE://读写
                        sb.append("存储");
                        sb.append("、");
                        break;
                    case Manifest.permission.REQUEST_INSTALL_PACKAGES://安装应用权限
                        sb.append("应用内安装其他应用");
                        sb.append("、");
                        break;
                    case Manifest.permission.CALL_PHONE://打电话
                        sb.append("电话");
                        sb.append("、");
                        break;
                }
            }
            return TextUtils.isEmpty(sb.toString()) ? "相关" : sb.toString().substring(0, sb.toString().length() - 1);
        }
        return "相关";
    }

}
