package com.yanzhenjie.andserver.http.multipart;

import androidx.annotation.NonNull;
import com.yanzhenjie.andserver.http.f;
import com.yanzhenjie.andserver.util.h;
import java.io.InputStream;
import org.apache.commons.fileupload.j;

/* compiled from: BodyContext */
public class a implements j {
    private final f a;

    public a(@NonNull f body) {
        this.a = body;
    }

    public String b() {
        return this.a.a();
    }

    public String getContentType() {
        h contentType = this.a.d();
        if (contentType == null) {
            return null;
        }
        return contentType.toString();
    }

    public int getContentLength() {
        long contentLength = a();
        if (contentLength > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) contentLength;
    }

    public long a() {
        return this.a.length();
    }

    public InputStream getInputStream() {
        return this.a.stream();
    }

    public String toString() {
        return String.format("ContentLength=%s, Mime=%s", new Object[]{Long.valueOf(a()), getContentType()});
    }
}
