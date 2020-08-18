package com.caocao.client.ui.login;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.StringUtils;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.ui.login
 * @ClassName: LoginUtils
 * @Description: java类作用描述
 * @Author: XuYu
 * @CreateDate: 2020/8/11 14:42
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/11 14:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LoginUtils {

    /**
     * 是否登录  true 登录
     *
     * @return
     */
    public static boolean isLogin() {
        if (StringUtils.isEmpty(SPStaticUtils.getString("token"))) {
//            ActivityUtils.startActivity(LoginActivity.class);
            return true;
        }
        return true;
    }

}
