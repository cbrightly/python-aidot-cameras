package org.apache.httpcore.entity;

import java.io.InputStream;
import java.io.OutputStream;
import org.apache.httpcore.impl.io.k;
import org.apache.httpcore.util.a;

/* compiled from: BasicHttpEntity */
public class b extends a {
    private InputStream q;
    private long x = -1;

    public long getContentLength() {
        return this.x;
    }

    public InputStream getContent() {
        org.apache.httpcore.util.b.a(this.q != null, "Content has not been provided");
        return this.q;
    }

    public void f(long len) {
        this.x = len;
    }

    public void e(InputStream inStream) {
        this.q = inStream;
    }

    public void writeTo(OutputStream outStream) {
        a.g(outStream, "Output stream");
        InputStream inStream = getContent();
        try {
            byte[] tmp = new byte[4096];
            while (true) {
                int read = inStream.read(tmp);
                int l = read;
                if (read != -1) {
                    outStream.write(tmp, 0, l);
                } else {
                    return;
                }
            }
        } finally {
            inStream.close();
        }
    }

    public boolean isStreaming() {
        InputStream inputStream = this.q;
        return (inputStream == null || inputStream == k.c) ? false : true;
    }
}
