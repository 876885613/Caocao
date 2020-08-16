package com.caocao.client.http.entity.respons;

import com.caocao.client.http.entity.BaseResp;
import com.google.gson.annotations.SerializedName;

public class SiteInfoResp extends BaseResp<SiteInfoResp> {
    @SerializedName("company_name")
    public String companyName;
    @SerializedName("company_address")
    public String companyAddress;
    public String phone;
    @SerializedName("company_description")
    public String companyDescription;
    @SerializedName("company_logo")
    public String companyLogo;
}
