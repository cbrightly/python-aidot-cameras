package org.apache.http.entity;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import io.netty.util.internal.StringUtil;
import org.apache.http.d;
import org.apache.http.j;
import org.apache.http.message.b;

/* compiled from: AbstractHttpEntity */
public abstract class a implements j {
    protected static final int OUTPUT_BUFFER_SIZE = 4096;
    protected boolean chunked;
    protected d contentEncoding;
    protected d contentType;

    protected a() {
    }

    public d getContentType() {
        return this.contentType;
    }

    public d getContentEncoding() {
        return this.contentEncoding;
    }

    public boolean isChunked() {
        return this.chunked;
    }

    public void setContentType(d contentType2) {
        this.contentType = contentType2;
    }

    public void setContentType(String ctString) {
        d h = null;
        if (ctString != null) {
            h = new b("Content-Type", ctString);
        }
        setContentType(h);
    }

    public void setContentEncoding(d contentEncoding2) {
        this.contentEncoding = contentEncoding2;
    }

    public void setContentEncoding(String ceString) {
        d h = null;
        if (ceString != null) {
            h = new b(HttpHeaders.HEAD_KEY_CONTENT_ENCODING, ceString);
        }
        setContentEncoding(h);
    }

    public void setChunked(boolean b) {
        this.chunked = b;
    }

    @Deprecated
    public void consumeContent() {
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        if (this.contentType != null) {
            sb.append("Content-Type: ");
            sb.append(this.contentType.getValue());
            sb.append(StringUtil.COMMA);
        }
        if (this.contentEncoding != null) {
            sb.append("Content-Encoding: ");
            sb.append(this.contentEncoding.getValue());
            sb.append(StringUtil.COMMA);
        }
        long len = getContentLength();
        if (len >= 0) {
            sb.append("Content-Length: ");
            sb.append(len);
            sb.append(StringUtil.COMMA);
        }
        sb.append("Chunked: ");
        sb.append(this.chunked);
        sb.append(']');
        return sb.toString();
    }
}
