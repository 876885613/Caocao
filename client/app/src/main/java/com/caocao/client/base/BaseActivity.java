package com.caocao.client.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.caocao.client.R;
import com.caocao.client.utils.AutoUtils;
import com.coder.baselibrary.base.AppActivity;
import com.coder.baselibrary.statusView.MultipleStatusView;
import com.githang.statusbar.StatusBarCompat;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.base
 * @ClassName: BaseActivity
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/3 9:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/3 9:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class BaseActivity extends AppActivity {
    static final int DELAY = 2000;



    public BaseActivity mActivity;

    public MultipleStatusView mMultipleStatusView;


    public boolean isTransparency;

    private final Runnable callback = new Runnable() {
        @Override
        public void run() {
            if (!isFinishing() && null != mMultipleStatusView) {
                mMultipleStatusView.showContent();
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isTransparency){
            statusLan(Color.parseColor("#00000000"));
        }else {
            statusLan(Color.parseColor("#FFFFFFFF"));
        }

        mActivity = BaseActivity.this;
        //适配初始化UI
        AutoUtils.setCustomDensity(this, getApplication());
        setContentLayout(initLayout());

        mMultipleStatusView.setOnRetryClickListener(mRetryClickListener);
        mMultipleStatusView.setOnViewStatusChangeListener(mViewStatusChangeListener);

        initTitle();
        initView();
        initData();
    }


    final View.OnClickListener mRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "您点击了重试视图", Toast.LENGTH_SHORT).show();
            loading();
        }
    };


    final MultipleStatusView.OnViewStatusChangeListener mViewStatusChangeListener =
            new MultipleStatusView.OnViewStatusChangeListener() {

                /**
                 * 视图状态改变时回调
                 *
                 * @param oldViewStatus 之前的视图状态
                 * @param newViewStatus 新的视图状态
                 */
                @Override
                public void onChange(int oldViewStatus, int newViewStatus) {
                    LogUtils.d("MultipleStatusView", "oldViewStatus=" + oldViewStatus
                            + ", newViewStatus=" + newViewStatus);
                }
            };

    void loading() {
        mMultipleStatusView.showLoading();
        mMultipleStatusView.postDelayed(callback, DELAY);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //获取当前焦点控件，如果是EditText,则失去焦点并隐藏软键盘
        View view = getCurrentFocus();
        if (view != null && view instanceof EditText && isShouldHideInput(view, ev)) {
            view.clearFocus();
            KeyboardUtils.hideSoftInput(view);
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) { //如果点击的view是EditText
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    //初始化头部
    protected abstract void initTitle();

    //初始化页面控件
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();


    public void setContentLayout(View contentLayout) {
        View baseLayout = LayoutInflater.from(this).inflate(R.layout.layout_base, null);

        FrameLayout container = baseLayout.findViewById(R.id.container);

        mMultipleStatusView = baseLayout.findViewById(R.id.multiply_status_view);

        container.addView(contentLayout, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(baseLayout);
    }


    /**
     * 实例 VM
     *
     * @param clazz
     * @param <V>
     * @return
     */
    public <V extends ViewModel> V getViewModel(Class clazz) {
        return (V) new ViewModelProvider(this).get(clazz);
    }


    /**
     * 沉侵式通知栏
     */
    public void statusLan(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            //4.4到5.0
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        //android6.0以后可以对状态栏文字颜色和图标进行修改
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        StatusBarCompat.setStatusBarColor(getWindow(),color, true);
    }
}
