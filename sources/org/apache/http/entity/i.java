package org.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import org.apache.http.protocol.e;
import org.apache.http.util.a;

/* compiled from: StringEntity */
public class i extends a implements Cloneable {
    protected final byte[] c;

    public i(String string, f contentType) {
        a.i(string, "Source string");
        Charset charset = contentType != null ? contentType.getCharset() : null;
        this.c = string.getBytes(charset == null ? e.a : charset);
        if (contentType != null) {
            setContentType(contentType.toString());
        }
    }

    public i(String string, String charset) {
        this(string, f.create(f.TEXT_PLAIN.getMimeType(), charset));
    }

    public boolean isRepeatable() {
        return true;
    }

    public long getContentLength() {
        return (long) this.c.length;
    }

    public InputStream getContent() {
        return new ByteArrayInputStream(this.c);
    }

    public void writeTo(OutputStream outstream) {
        a.i(outstream, "Output stream");
        outstream.write(this.c);
        outstream.flush();
    }

    public boolean isStreaming() {
        return false;
    }

    public Object clone() {
        return super.clone();
    }
}
