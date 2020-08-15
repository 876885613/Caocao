package com.caocao.client.http.entity.respons;

import com.caocao.client.http.entity.BaseResp;
import com.google.gson.annotations.SerializedName;

public class UploadResp extends BaseResp<UploadResp> {
    @SerializedName("upload_url")
    public String uploadUrl;
}
