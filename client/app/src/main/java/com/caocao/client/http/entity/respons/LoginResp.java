package com.caocao.client.http.entity.respons;

import com.caocao.client.http.entity.BaseResp;

public class LoginResp extends BaseResp<LoginResp> {
    public String token;
    public String nickname;
    public String headimgurl;
    public String phone;
}
