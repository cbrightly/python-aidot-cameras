package com.downloader.database;

/* compiled from: DownloadModel */
public class d {
    private int a;
    private String b;
    private String c;
    private String d;
    private String e;
    private long f;
    private long g;
    private long h;

    public int e() {
        return this.a;
    }

    public void m(int id) {
        this.a = id;
    }

    public String h() {
        return this.b;
    }

    public void p(String url) {
        this.b = url;
    }

    public String c() {
        return this.c;
    }

    public void k(String eTag) {
        this.c = eTag;
    }

    public String a() {
        return this.d;
    }

    public void i(String dirPath) {
        this.d = dirPath;
    }

    public String d() {
        return this.e;
    }

    public void l(String fileName) {
        this.e = fileName;
    }

    public long g() {
        return this.f;
    }

    public void o(long totalBytes) {
        this.f = totalBytes;
    }

    public long b() {
        return this.g;
    }

    public void j(long downloadedBytes) {
        this.g = downloadedBytes;
    }

    public long f() {
        return this.h;
    }

    public void n(long lastModifiedAt) {
        this.h = lastModifiedAt;
    }
}
