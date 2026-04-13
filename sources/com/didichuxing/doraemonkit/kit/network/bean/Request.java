package com.didichuxing.doraemonkit.kit.network.bean;

import android.text.TextUtils;
import java.io.Serializable;

public class Request implements Serializable {
    public String encode;
    public String headers;
    public String method;
    public String postData;
    public String url;

    public String toString() {
        return String.format("[%s %s %s %s]", new Object[]{this.url, this.method, this.headers.toString(), this.postData});
    }

    public boolean filter(String text) {
        return !TextUtils.isEmpty(this.url) && this.url.contains(text);
    }
}
