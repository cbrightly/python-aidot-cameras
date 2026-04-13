package org.apache.http.impl.entity;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import org.apache.http.d;
import org.apache.http.entity.b;
import org.apache.http.entity.e;
import org.apache.http.impl.io.g;
import org.apache.http.impl.io.p;
import org.apache.http.io.h;
import org.apache.http.j;
import org.apache.http.n;

@Deprecated
/* compiled from: EntityDeserializer */
public class a {
    private final e a;

    public a(e lenStrategy) {
        this.a = (e) org.apache.http.util.a.i(lenStrategy, "Content length strategy");
    }

    /* access modifiers changed from: protected */
    public b b(h inbuffer, n message) {
        b entity = new b();
        long len = this.a.a(message);
        if (len == -2) {
            entity.setChunked(true);
            entity.setContentLength(-1);
            entity.setContent(new org.apache.http.impl.io.e(inbuffer));
        } else if (len == -1) {
            entity.setChunked(false);
            entity.setContentLength(-1);
            entity.setContent(new p(inbuffer));
        } else {
            entity.setChunked(false);
            entity.setContentLength(len);
            entity.setContent(new g(inbuffer, len));
        }
        d contentTypeHeader = message.u("Content-Type");
        if (contentTypeHeader != null) {
            entity.setContentType(contentTypeHeader);
        }
        d contentEncodingHeader = message.u(HttpHeaders.HEAD_KEY_CONTENT_ENCODING);
        if (contentEncodingHeader != null) {
            entity.setContentEncoding(contentEncodingHeader);
        }
        return entity;
    }

    public j a(h inbuffer, n message) {
        org.apache.http.util.a.i(inbuffer, "Session input buffer");
        org.apache.http.util.a.i(message, "HTTP message");
        return b(inbuffer, message);
    }
}
