package org.apache.http.entity;

import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.util.a;

/* compiled from: InputStreamEntity */
public class h extends a {
    private final InputStream content;
    private final long length;

    public h(InputStream instream) {
        this(instream, -1);
    }

    public h(InputStream instream, long length2) {
        this(instream, length2, (f) null);
    }

    public h(InputStream instream, f contentType) {
        this(instream, -1, contentType);
    }

    public h(InputStream instream, long length2, f contentType) {
        this.content = (InputStream) a.i(instream, "Source input stream");
        this.length = length2;
        if (contentType != null) {
            setContentType(contentType.toString());
        }
    }

    public boolean isRepeatable() {
        return false;
    }

    public long getContentLength() {
        return this.length;
    }

    public InputStream getContent() {
        return this.content;
    }

    public void writeTo(OutputStream outstream) {
        a.i(outstream, "Output stream");
        InputStream instream = this.content;
        try {
            byte[] buffer = new byte[4096];
            long remaining = this.length;
            if (remaining < 0) {
                while (true) {
                    int read = instream.read(buffer);
                    int l = read;
                    if (read == -1) {
                        break;
                    }
                    outstream.write(buffer, 0, l);
                }
            } else {
                while (true) {
                    if (remaining <= 0) {
                        break;
                    }
                    int l2 = instream.read(buffer, 0, (int) Math.min(4096, remaining));
                    if (l2 == -1) {
                        break;
                    }
                    outstream.write(buffer, 0, l2);
                    remaining -= (long) l2;
                }
            }
        } finally {
            instream.close();
        }
    }

    public boolean isStreaming() {
        return true;
    }
}
