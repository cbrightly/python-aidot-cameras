package org.apache.http.client.entity;

import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.d;
import org.apache.http.entity.g;
import org.apache.http.j;

/* compiled from: DecompressingEntity */
public class a extends g {
    private final c d;
    private InputStream f;

    public a(j wrapped, c inputStreamFactory) {
        super(wrapped);
        this.d = inputStreamFactory;
    }

    private InputStream f() {
        return new d(this.c.getContent(), this.d);
    }

    public InputStream getContent() {
        if (!this.c.isStreaming()) {
            return f();
        }
        if (this.f == null) {
            this.f = f();
        }
        return this.f;
    }

    public void writeTo(OutputStream outstream) {
        org.apache.http.util.a.i(outstream, "Output stream");
        InputStream instream = getContent();
        try {
            byte[] buffer = new byte[2048];
            while (true) {
                int read = instream.read(buffer);
                int l = read;
                if (read != -1) {
                    outstream.write(buffer, 0, l);
                } else {
                    return;
                }
            }
        } finally {
            instream.close();
        }
    }

    public d getContentEncoding() {
        return null;
    }

    public long getContentLength() {
        return -1;
    }
}
