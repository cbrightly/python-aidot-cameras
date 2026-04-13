package com.downloader;

import java.util.List;
import java.util.Map;

/* compiled from: Error */
public class a {
    private boolean a;
    private boolean b;
    private String c;
    private Map<String, List<String>> d;
    private Throwable e;
    private int f;

    public void e(boolean serverError) {
        this.a = serverError;
    }

    public void a(boolean connectionError) {
        this.b = connectionError;
    }

    public void f(String serverErrorMessage) {
        this.c = serverErrorMessage;
    }

    public void c(Map<String, List<String>> headerFields) {
        this.d = headerFields;
    }

    public void b(Throwable connectionException) {
        this.e = connectionException;
    }

    public void d(int responseCode) {
        this.f = responseCode;
    }
}
