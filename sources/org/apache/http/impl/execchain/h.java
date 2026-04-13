package org.apache.http.impl.execchain;

import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.d;
import org.apache.http.j;
import org.apache.http.k;
import org.apache.http.o;

/* compiled from: RequestEntityProxy */
public class h implements j {
    private final j c;
    private boolean d = false;

    static void a(k request) {
        j entity = request.a();
        if (entity != null && !entity.isRepeatable() && !c(entity)) {
            request.l(new h(entity));
        }
    }

    static boolean c(j entity) {
        return entity instanceof h;
    }

    static boolean d(o request) {
        j entity;
        if (!(request instanceof k) || (entity = ((k) request).a()) == null) {
            return true;
        }
        if (!c(entity) || ((h) entity).b()) {
            return entity.isRepeatable();
        }
        return true;
    }

    h(j original) {
        this.c = original;
    }

    public boolean b() {
        return this.d;
    }

    public boolean isRepeatable() {
        return this.c.isRepeatable();
    }

    public boolean isChunked() {
        return this.c.isChunked();
    }

    public long getContentLength() {
        return this.c.getContentLength();
    }

    public d getContentType() {
        return this.c.getContentType();
    }

    public d getContentEncoding() {
        return this.c.getContentEncoding();
    }

    public InputStream getContent() {
        return this.c.getContent();
    }

    public void writeTo(OutputStream outstream) {
        this.d = true;
        this.c.writeTo(outstream);
    }

    public boolean isStreaming() {
        return this.c.isStreaming();
    }

    public String toString() {
        return "RequestEntityProxy{" + this.c + '}';
    }
}
