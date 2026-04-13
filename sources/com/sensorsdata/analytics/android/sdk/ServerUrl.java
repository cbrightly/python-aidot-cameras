package com.sensorsdata.analytics.android.sdk;

import android.net.Uri;
import android.text.TextUtils;

public class ServerUrl {
    private String baseUrl;
    private String host;
    private String project;
    private String token;
    private String url;

    private ServerUrl() {
    }

    public ServerUrl(String url2) {
        this.url = url2;
        if (!TextUtils.isEmpty(url2)) {
            this.baseUrl = getBaseUrl(url2);
            Uri uri = Uri.parse(url2);
            try {
                this.host = uri.getHost();
                this.token = uri.getQueryParameter("token");
                this.project = uri.getQueryParameter("project");
                if (TextUtils.isEmpty(this.host)) {
                    this.host = "";
                }
                if (TextUtils.isEmpty(this.project)) {
                    this.project = "default";
                }
                if (!TextUtils.isEmpty(this.token)) {
                    return;
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
                if (TextUtils.isEmpty(this.host)) {
                    this.host = "";
                }
                if (TextUtils.isEmpty(this.project)) {
                    this.project = "default";
                }
                if (!TextUtils.isEmpty(this.token)) {
                    return;
                }
            } catch (Throwable th) {
                if (TextUtils.isEmpty(this.host)) {
                    this.host = "";
                }
                if (TextUtils.isEmpty(this.project)) {
                    this.project = "default";
                }
                if (TextUtils.isEmpty(this.token)) {
                    this.token = "";
                }
                throw th;
            }
            this.token = "";
        }
    }

    public String getUrl() {
        return this.url;
    }

    public String getHost() {
        return this.host;
    }

    public String getProject() {
        return this.project;
    }

    public String getToken() {
        return this.token;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public String toString() {
        return "url=" + this.url + ",baseUrl" + this.baseUrl + ",host=" + this.host + ",project=" + this.project + ",token=" + this.token;
    }

    public boolean check(ServerUrl serverUrl) {
        if (serverUrl == null) {
            return false;
        }
        try {
            if (!getHost().equals(serverUrl.getHost()) || !getProject().equals(serverUrl.getProject())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    public String getBaseUrl(String url2) {
        int pathPrefix;
        if (TextUtils.isEmpty(url2) || (pathPrefix = url2.lastIndexOf("/")) == -1) {
            return "";
        }
        return url2.substring(0, pathPrefix);
    }
}
