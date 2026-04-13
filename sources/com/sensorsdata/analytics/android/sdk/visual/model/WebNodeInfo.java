package com.sensorsdata.analytics.android.sdk.visual.model;

import java.util.List;

public class WebNodeInfo {
    private List<AlertInfo> alertInfos;
    private Status status;
    private String title;
    private String url;
    private List<WebNode> webNodes;

    public enum Status {
        SUCCESS,
        FAILURE
    }

    private WebNodeInfo(List<WebNode> webNodes2, List<AlertInfo> alertInfos2, String title2, String url2, Status status2) {
        this.webNodes = webNodes2;
        this.alertInfos = alertInfos2;
        this.title = title2;
        this.url = url2;
        this.status = status2;
    }

    public static WebNodeInfo createWebAlertInfo(List<AlertInfo> list) {
        return new Builder().setAlertInfo(list).setStatus(Status.FAILURE).create();
    }

    public static WebNodeInfo createWebNodesInfo(List<WebNode> webNodes2) {
        return new Builder().setWebNodes(webNodes2).setStatus(Status.SUCCESS).create();
    }

    public static WebNodeInfo createPageInfo(String title2, String url2) {
        return new Builder().setTitle(title2).setUrl(url2).create();
    }

    public static class Builder {
        private List<AlertInfo> alertInfos;
        private Status status;
        private String title;
        private String url;
        private List<WebNode> webNodes;

        /* access modifiers changed from: package-private */
        public Builder setWebNodes(List<WebNode> webNodes2) {
            this.webNodes = webNodes2;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setAlertInfo(List<AlertInfo> alertInfos2) {
            this.alertInfos = alertInfos2;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setTitle(String title2) {
            this.title = title2;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setUrl(String url2) {
            this.url = url2;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setStatus(Status status2) {
            this.status = status2;
            return this;
        }

        /* access modifiers changed from: package-private */
        public WebNodeInfo create() {
            return new WebNodeInfo(this.webNodes, this.alertInfos, this.title, this.url, this.status);
        }
    }

    public List<WebNode> getWebNodes() {
        return this.webNodes;
    }

    public Status getStatus() {
        return this.status;
    }

    public List<AlertInfo> getAlertInfos() {
        return this.alertInfos;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public static class AlertInfo {
        public String linkText;
        public String linkUrl;
        public String message;
        public String title;

        public AlertInfo(String title2, String message2, String linkText2, String linkUrl2) {
            this.title = title2;
            this.message = message2;
            this.linkText = linkText2;
            this.linkUrl = linkUrl2;
        }
    }
}
