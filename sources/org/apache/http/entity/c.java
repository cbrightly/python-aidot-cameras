package org.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.j;
import org.apache.http.util.a;

/* compiled from: BufferedHttpEntity */
public class c extends g {
    private final byte[] d;

    public c(j entity) {
        super(entity);
        if (!entity.isRepeatable() || entity.getContentLength() < 0) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            entity.writeTo(out);
            out.flush();
            this.d = out.toByteArray();
            return;
        }
        this.d = null;
    }

    public long getContentLength() {
        byte[] bArr = this.d;
        if (bArr != null) {
            return (long) bArr.length;
        }
        return super.getContentLength();
    }

    public InputStream getContent() {
        if (this.d != null) {
            return new ByteArrayInputStream(this.d);
        }
        return super.getContent();
    }

    public boolean isChunked() {
        return this.d == null && super.isChunked();
    }

    public boolean isRepeatable() {
        return true;
    }

    public void writeTo(OutputStream outstream) {
        a.i(outstream, "Output stream");
        byte[] bArr = this.d;
        if (bArr != null) {
            outstream.write(bArr);
        } else {
            super.writeTo(outstream);
        }
    }

    public boolean isStreaming() {
        return this.d == null && super.isStreaming();
    }
}
