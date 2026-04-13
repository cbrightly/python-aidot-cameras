package com.yanzhenjie.andserver.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

/* compiled from: RequestWrapper */
public class h implements c {
    private c a;

    public h(c request) {
        this.a = request;
    }

    public c d() {
        return this.a;
    }

    @NonNull
    public b getMethod() {
        return this.a.getMethod();
    }

    @NonNull
    public String t() {
        return this.a.t();
    }

    @NonNull
    public String getPath() {
        return this.a.getPath();
    }

    @NonNull
    public List<String> x() {
        return this.a.x();
    }

    @Nullable
    public String getHeader(@NonNull String name) {
        return this.a.getHeader(name);
    }

    @NonNull
    public List<String> c(@NonNull String name) {
        return this.a.c(name);
    }

    public long A(@NonNull String name) {
        return this.a.A(name);
    }

    @NonNull
    public List<com.yanzhenjie.andserver.util.h> v() {
        return this.a.v();
    }

    @Nullable
    public com.yanzhenjie.andserver.util.h getContentType() {
        return this.a.getContentType();
    }

    @NonNull
    public List<String> w() {
        return this.a.w();
    }

    @NonNull
    public List<String> y(@NonNull String name) {
        return this.a.y(name);
    }

    @Nullable
    public f z() {
        return this.a.z();
    }

    @Nullable
    public g u(@NonNull String path) {
        return this.a.u(path);
    }

    @Nullable
    public Object getAttribute(@NonNull String id) {
        return this.a.getAttribute(id);
    }

    public void setAttribute(@NonNull String id, @NonNull Object obj) {
        this.a.setAttribute(id, obj);
    }
}
