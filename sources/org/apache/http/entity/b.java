package org.apache.http.entity;

import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.impl.io.m;
import org.apache.http.util.a;

/* compiled from: BasicHttpEntity */
public class b extends a {
    private InputStream content;
    private long length = -1;

    public long getContentLength() {
        return this.length;
    }

    public InputStream getContent() {
        org.apache.http.util.b.a(this.content != null, "Content has not been provided");
        return this.content;
    }

    public boolean isRepeatable() {
        return false;
    }

    public void setContentLength(long len) {
        this.length = len;
    }

    public void setContent(InputStream instream) {
        this.content = instream;
    }

    public void writeTo(OutputStream outstream) {
        a.i(outstream, "Output stream");
        InputStream instream = getContent();
        try {
            byte[] tmp = new byte[4096];
            while (true) {
                int read = instream.read(tmp);
                int l = read;
                if (read != -1) {
                    outstream.write(tmp, 0, l);
                } else {
                    return;
                }
            }
        } finally {
            instream.close();
        }
    }

    public boolean isStreaming() {
        InputStream inputStream = this.content;
        return (inputStream == null || inputStream == m.c) ? false : true;
    }
}
