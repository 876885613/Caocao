package com.caocao.client.weight;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.ui.me.AgreementActivity;
import com.caocao.client.ui.splash.FirstStartActivity;

import java.util.List;

@SuppressLint({"StaticFieldLeak","InflateParams","SetTextI18n","CutPasteId"})
public class AgreementDialog {
    private BaseActivity activity;
    private Dialog dialog;
    public AgreementDialog(BaseActivity activity) {
        this.activity = activity;
        dialog = new Dialog(activity, R.style.custom_dialog);
    }

    // 显示软件更新对话框
    public void showDialog() {
        if (dialog.isShowing()) return;
        dialog.setCancelable(false);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_agreement, null);
        TextView tv_content = view.findViewById(R.id.tv_content);
        TextView tv_agree = view.findViewById(R.id.tv_agree);
        TextView tv_disagree = view.findViewById(R.id.tv_disagree);

        setTextInfo(activity,tv_content);

        tv_agree.setOnClickListener(v -> {
            ActivityUtils.startActivity(FirstStartActivity.class);
            dialog.dismiss();
        });

        tv_disagree.setOnClickListener(v -> {
            dialog.dismiss();
            getExitApp(activity);
        });

        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        dialog.setContentView(view, new ViewGroup.MarginLayoutParams(displayMetrics.widthPixels, ViewGroup.MarginLayoutParams.MATCH_PARENT));
        dialog.show();
    }


    /**
     * 设置TextView中间字体颜色与点击事见
     * start   开始文字的位置 坐标从 0开始
     * end     改变结束的位置 ，并不包括这个位置。
     * 使用BackgroundColorSpan设置背景颜色。
     *
     * @param activity
     * @param textView
     */
    public static void setTextInfo(BaseActivity activity, TextView textView) {
        //初始化可以更改内容和标记的文本类
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        //添加需要改变的文本
        spannableStringBuilder.append(activity.getResources().getString(R.string.agreement_content));
        //去掉点击后的背景色
        textView.setHighlightColor(activity.getResources().getColor(android.R.color.transparent));
        //设置用户协议点击
        ClickableSpan firstClickableSpan = new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setUnderlineText(false);//取消点击事见字体的背景颜色
                textPaint.setColor(activity.getResources().getColor(R.color.colorPrimaryDark));
            }
            //变色字体点击监听设置
            @Override
            public void onClick(@NonNull View widget) {
                Bundle bundle = new Bundle();
                bundle.putString("agreement","user_protocol");
                ActivityUtils.startActivity(bundle, AgreementActivity.class);
            }
        };

        //设置隐私协议点击
        ClickableSpan secondClickableSpan = new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setUnderlineText(false);//取消点击事见字体的背景颜色
                textPaint.setColor(activity.getResources().getColor(R.color.colorPrimaryDark));
            }
            //变色字体点击监听设置
            @Override
            public void onClick(@NonNull View widget) {
                Bundle bundle = new Bundle();
                bundle.putString("agreement","privacy");
                ActivityUtils.startActivity(bundle,AgreementActivity.class);
            }
        };
        spannableStringBuilder.setSpan(firstClickableSpan, 30, 36, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(secondClickableSpan,37,43, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //一定要记得设置，不然点击不生效
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        //最好设置文字
        textView.setText(spannableStringBuilder);
    }


    /**
     * 退出App
     *
     * @return
     */
    public static void getExitApp(BaseActivity activity) {
        // 1. 通过Context获取ActivityManager
        ActivityManager activityManager = (ActivityManager) activity.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        // 2. 通过ActivityManager获取任务栈
        List<ActivityManager.AppTask> appTaskList = activityManager.getAppTasks();
        // 3. 逐个关闭Activity
        for (ActivityManager.AppTask appTask : appTaskList) {
            appTask.finishAndRemoveTask();
        }
    }
}

