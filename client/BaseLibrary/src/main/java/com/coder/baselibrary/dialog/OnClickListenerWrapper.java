package com.coder.baselibrary.dialog;

import android.view.View;

import androidx.appcompat.app.AppCompatDialog;

/**
 * @ProjectName: Caocao
 * @Package: com.coder.baselibrary.dialog
 * @ClassName: AlertDialog
 * @Description: 自定义全局可配置的Dialog
 * @Author: XuYu
 * @CreateDate: 2020/2/16 11:06
 * @UpdateUser: 更新者
 * @UpdateDate:
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class OnClickListenerWrapper implements View.OnClickListener {


    private AppCompatDialog mDialog;

    public abstract void onClickCall(View v);


    @Override
    public void onClick(View v) {
        onClickCall(v);
        if (mDialog != null)
            mDialog.dismiss();
    }

    public View.OnClickListener setDialog(AlertDialog dialog) {
        this.mDialog = dialog;
        return this;
    }
}
