package com.didichuxing.doraemonkit.kit.network.bean;

import java.io.Serializable;

public class Response implements Serializable {
    public String headers;
    public String mimeType;
    public int status;
    public String url;

    public String toString() {
        return String.format("[%s %d  %s %s]", new Object[]{this.url, Integer.valueOf(this.status), this.headers.toString(), this.mimeType});
    }
}
