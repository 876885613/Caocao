package com.caocao.client.http.entity.respons;

import com.caocao.client.http.entity.BaseResp;
import com.google.gson.annotations.SerializedName;

public class ApplyStatusResp extends BaseResp<ApplyStatusResp> {
    public int status;

    @SerializedName("refuse_msg")
    public String refuseMsg;
}
