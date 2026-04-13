package com.yanzhenjie.andserver.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.yanzhenjie.andserver.http.cookie.c;
import com.yanzhenjie.andserver.util.d;
import com.yanzhenjie.andserver.util.h;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.httpcore.e;
import org.apache.httpcore.j;
import org.apache.httpcore.p;

/* compiled from: StandardResponse */
public class l implements d {
    private static final com.yanzhenjie.andserver.http.cookie.b a = new c();
    private p b;

    public l(p response) {
        this.b = response;
    }

    public void e(int sc) {
        this.b.e(sc);
    }

    public void setHeader(@NonNull String name, @NonNull String value) {
        this.b.setHeader(name, value);
    }

    public void f(@NonNull String name, @NonNull String value) {
        this.b.addHeader(name, value);
    }

    @Nullable
    public String getHeader(@NonNull String name) {
        e header = this.b.u(name);
        if (header == null) {
            return null;
        }
        return header.getValue();
    }

    public void a(@NonNull String name, long date) {
        setHeader(name, d.a(date));
    }

    public void d(@NonNull com.yanzhenjie.andserver.http.cookie.a cookie) {
        f(HttpHeaders.HEAD_KEY_SET_COOKIE, a.a(cookie));
    }

    public void c(@NonNull String location) {
        e(302);
        setHeader("Location", location);
    }

    public void b(i body) {
        this.b.b(new b(body));
    }

    /* compiled from: StandardResponse */
    public static class b implements j {
        private i c;

        private b(i body) {
            this.c = body;
        }

        public boolean isChunked() {
            return false;
        }

        public long getContentLength() {
            return this.c.a();
        }

        public e getContentType() {
            h mimeType = this.c.d();
            if (mimeType == null) {
                return null;
            }
            return new org.apache.httpcore.message.b("Content-Type", mimeType.toString());
        }

        public e getContentEncoding() {
            return null;
        }

        public InputStream getContent() {
            return null;
        }

        public void writeTo(OutputStream out) {
            this.c.writeTo(out);
        }

        public boolean isStreaming() {
            return false;
        }
    }
}
