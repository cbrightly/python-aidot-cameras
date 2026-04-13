package com.downloader.request;

import com.downloader.i;
import java.util.HashMap;
import java.util.List;

/* compiled from: DownloadRequestBuilder */
public class b {
    String a;
    String b;
    String c;
    i d = i.MEDIUM;
    Object e;
    int f;
    int g;
    String h;
    HashMap<String, List<String>> i;

    public b(String url, String dirPath, String fileName) {
        this.a = url;
        this.b = dirPath;
        this.c = fileName;
    }

    public a a() {
        return new a(this);
    }
}
