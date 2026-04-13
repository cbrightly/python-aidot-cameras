package org.apache.http.entity;

import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.d;
import org.apache.http.j;
import org.apache.http.util.a;

/* compiled from: HttpEntityWrapper */
public class g implements j {
    protected j c;

    public g(j wrappedEntity) {
        this.c = (j) a.i(wrappedEntity, "Wrapped entity");
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
        this.c.writeTo(outstream);
    }

    public boolean isStreaming() {
        return this.c.isStreaming();
    }
}
