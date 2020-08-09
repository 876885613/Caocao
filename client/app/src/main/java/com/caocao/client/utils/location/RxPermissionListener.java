package com.caocao.client.utils.location;

public interface RxPermissionListener {

    /*权限同意*/
    void accept();

    /*权限拒绝*/
    void refuse();

    /*权限拒绝禁止提醒*/
    void noAsk(String permissionName);

}
