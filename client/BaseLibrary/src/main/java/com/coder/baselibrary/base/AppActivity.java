package com.coder.baselibrary.base;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


/**
 * @ProjectName: Matata Students
 * @Package: com.matata.baselibrary.base
 * @ClassName: BaseFragment
 * @Description: Activity 的基类
 * @Author: XuYu
 * @CreateDate: 2020/5/18 11:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/18 11:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class AppActivity extends AppCompatActivity {

    /**
     * 初始化页面布局
     *
     * @return
     */
    public abstract View initLayout();

}
