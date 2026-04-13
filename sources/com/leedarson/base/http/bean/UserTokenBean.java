package com.leedarson.base.http.bean;

import com.google.gson.annotations.SerializedName;
import com.meituan.robust.ChangeQuickRedirect;

public class UserTokenBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    @SerializedName("accessToken")
    private String accessToken;
    @SerializedName("expireIn")
    private int expireIn;
    @SerializedName("refreshToken")
    private String refreshToken;

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken2) {
        this.accessToken = accessToken2;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken2) {
        this.refreshToken = refreshToken2;
    }

    public int getExpireIn() {
        return this.expireIn;
    }

    public void setExpireIn(int expireIn2) {
        this.expireIn = expireIn2;
    }
}
