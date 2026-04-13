package org.apache.httpcore.entity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.httpcore.util.a;

/* compiled from: ByteArrayEntity */
public class c extends a implements Cloneable {
    @Deprecated
    protected final byte[] q;
    private final byte[] x;
    private final int y;
    private final int z;

    public c(byte[] b, e contentType) {
        a.g(b, "Source byte array");
        this.q = b;
        this.x = b;
        this.y = 0;
        this.z = b.length;
        if (contentType != null) {
            c(contentType.toString());
        }
    }

    public c(byte[] b) {
        this(b, (e) null);
    }

    public long getContentLength() {
        return (long) this.z;
    }

    public InputStream getContent() {
        return new ByteArrayInputStream(this.x, this.y, this.z);
    }

    public void writeTo(OutputStream outStream) {
        a.g(outStream, "Output stream");
        outStream.write(this.x, this.y, this.z);
        outStream.flush();
    }

    public boolean isStreaming() {
        return false;
    }

    public Object clone() {
        return super.clone();
    }
}
