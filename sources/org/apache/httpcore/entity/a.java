package org.apache.httpcore.entity;

import io.netty.util.internal.StringUtil;
import org.apache.httpcore.e;
import org.apache.httpcore.j;
import org.apache.httpcore.message.b;

/* compiled from: AbstractHttpEntity */
public abstract class a implements j {
    protected e c;
    protected e d;
    protected boolean f;

    protected a() {
    }

    public e getContentType() {
        return this.c;
    }

    public e getContentEncoding() {
        return this.d;
    }

    public boolean isChunked() {
        return this.f;
    }

    public void d(e contentType) {
        this.c = contentType;
    }

    public void c(String ctString) {
        e h = null;
        if (ctString != null) {
            h = new b("Content-Type", ctString);
        }
        d(h);
    }

    public void b(e contentEncoding) {
        this.d = contentEncoding;
    }

    public void a(boolean b) {
        this.f = b;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        if (this.c != null) {
            sb.append("Content-Type: ");
            sb.append(this.c.getValue());
            sb.append(StringUtil.COMMA);
        }
        if (this.d != null) {
            sb.append("Content-Encoding: ");
            sb.append(this.d.getValue());
            sb.append(StringUtil.COMMA);
        }
        long len = getContentLength();
        if (len >= 0) {
            sb.append("Content-Length: ");
            sb.append(len);
            sb.append(StringUtil.COMMA);
        }
        sb.append("Chunked: ");
        sb.append(this.f);
        sb.append(']');
        return sb.toString();
    }
}
