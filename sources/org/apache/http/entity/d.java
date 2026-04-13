package org.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.util.a;

/* compiled from: ByteArrayEntity */
public class d extends a implements Cloneable {
    @Deprecated
    protected final byte[] c;
    private final byte[] d;
    private final int f;
    private final int q;

    public d(byte[] b, f contentType) {
        a.i(b, "Source byte array");
        this.c = b;
        this.d = b;
        this.f = 0;
        this.q = b.length;
        if (contentType != null) {
            setContentType(contentType.toString());
        }
    }

    public d(byte[] b) {
        this(b, (f) null);
    }

    public boolean isRepeatable() {
        return true;
    }

    public long getContentLength() {
        return (long) this.q;
    }

    public InputStream getContent() {
        return new ByteArrayInputStream(this.d, this.f, this.q);
    }

    public void writeTo(OutputStream outstream) {
        a.i(outstream, "Output stream");
        outstream.write(this.d, this.f, this.q);
        outstream.flush();
    }

    public boolean isStreaming() {
        return false;
    }

    public Object clone() {
        return super.clone();
    }
}
