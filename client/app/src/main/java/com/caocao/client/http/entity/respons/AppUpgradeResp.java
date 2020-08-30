package com.caocao.client.http.entity.respons;

import com.caocao.client.http.entity.BaseResp;
import com.google.gson.annotations.SerializedName;

public class AppUpgradeResp extends BaseResp<AppUpgradeResp> {
    @SerializedName("version_code")
    public int versionCode;
    @SerializedName("version_name")
    public String versionName;
    public String apkurl;
    public String desc;
    @SerializedName("is_force")
    public int isForce;
}
