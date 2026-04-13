package com.downloader;

import meshsdk.ctrl.GroupCtrlAdapter;

/* compiled from: PRDownloaderConfig */
public class h {
    private int a;
    private int b;
    private String c;
    private com.downloader.httpclient.b d;
    private boolean e;

    private h(b builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = builder.e;
    }

    public int c() {
        return this.a;
    }

    public int a() {
        return this.b;
    }

    public String d() {
        return this.c;
    }

    public com.downloader.httpclient.b b() {
        return this.d;
    }

    public boolean e() {
        return this.e;
    }

    public static b f() {
        return new b();
    }

    /* compiled from: PRDownloaderConfig */
    public static class b {
        int a = GroupCtrlAdapter.TIMEOUT_WAIT_MESH_ONLINE;
        int b = GroupCtrlAdapter.TIMEOUT_WAIT_MESH_ONLINE;
        String c = "PRDownloader";
        com.downloader.httpclient.b d = new com.downloader.httpclient.a();
        boolean e = false;

        public b d(int readTimeout) {
            this.a = readTimeout;
            return this;
        }

        public b b(int connectTimeout) {
            this.b = connectTimeout;
            return this;
        }

        public b c(boolean databaseEnabled) {
            this.e = databaseEnabled;
            return this;
        }

        public h a() {
            return new h(this);
        }
    }
}
