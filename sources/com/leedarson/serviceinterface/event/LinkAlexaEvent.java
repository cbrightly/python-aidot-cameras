package com.leedarson.serviceinterface.event;

import com.google.gson.annotations.SerializedName;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.meituan.robust.ChangeQuickRedirect;
import org.json.JSONObject;

public class LinkAlexaEvent extends BaseEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    @SerializedName("code")
    private String code;
    @SerializedName("data")
    private JSONObject data;
    @SerializedName("event")
    private String event;
    public boolean flagOnlyNotifyCurrentWebView = false;
    @SerializedName("module")
    private String module;
    @SerializedName("scope")
    private String scope;
    @SerializedName("state")
    private String state;
    @SerializedName("url")
    private String url;

    public LinkAlexaEvent(String module2, JSONObject data2, String event2, WVJBWebView wvjbWebView) {
        this.module = module2;
        this.event = event2;
        this.data = data2;
        this.target = wvjbWebView;
    }

    public LinkAlexaEvent(String code2, String state2, String scope2, String url2) {
        this.code = code2;
        this.state = state2;
        this.scope = scope2;
        this.url = url2;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code2) {
        this.code = code2;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state2) {
        this.state = state2;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope2) {
        this.scope = scope2;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String module2) {
        this.module = module2;
    }

    public JSONObject getData() {
        return this.data;
    }

    public void setData(JSONObject data2) {
        this.data = data2;
    }

    public String getEvent() {
        return this.event;
    }

    public void setEvent(String event2) {
        this.event = event2;
    }
}
