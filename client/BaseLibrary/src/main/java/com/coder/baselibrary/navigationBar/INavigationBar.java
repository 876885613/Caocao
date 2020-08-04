package com.coder.baselibrary.navigationBar;

/**
 * @ProjectName: EssayJoke
 * @Package: com.coder.baselibrary.navigationBar
 * @ClassName: INavigationBar
 * @Description: 头部导航规范
 * @Author: XuYu
 * @CreateDate: 2020/2/8$ 15:40$
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/2/8 15:40
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */
public interface INavigationBar {
    //头部资源ID
    int bindLayoutId();

    //绑定头部参数
    void applyView();
}
