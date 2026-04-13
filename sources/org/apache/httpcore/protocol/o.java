package org.apache.httpcore.protocol;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import org.apache.httpcore.ProtocolException;
import org.apache.httpcore.j;
import org.apache.httpcore.p;
import org.apache.httpcore.r;
import org.apache.httpcore.t;
import org.apache.httpcore.util.a;
import org.apache.httpcore.v;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: ResponseContent */
public class o implements r {
    private final boolean a;

    public o() {
        this(false);
    }

    public o(boolean overwrite) {
        this.a = overwrite;
    }

    public void a(p response, d context) {
        a.g(response, "HTTP response");
        if (this.a) {
            response.s(Constants.TRANSFERENCODING);
            response.s("Content-Length");
        } else if (response.containsHeader(Constants.TRANSFERENCODING)) {
            throw new ProtocolException("Transfer-encoding header already present");
        } else if (response.containsHeader("Content-Length")) {
            throw new ProtocolException("Content-Length header already present");
        }
        v ver = response.j().getProtocolVersion();
        j entity = response.a();
        if (entity != null) {
            long len = entity.getContentLength();
            if (entity.isChunked() && !ver.lessEquals(t.HTTP_1_0)) {
                response.addHeader(Constants.TRANSFERENCODING, "chunked");
            } else if (len >= 0) {
                response.addHeader("Content-Length", Long.toString(entity.getContentLength()));
            }
            if (entity.getContentType() != null && !response.containsHeader("Content-Type")) {
                response.f(entity.getContentType());
            }
            if (entity.getContentEncoding() != null && !response.containsHeader(HttpHeaders.HEAD_KEY_CONTENT_ENCODING)) {
                response.f(entity.getContentEncoding());
                return;
            }
            return;
        }
        int status = response.j().getStatusCode();
        if (status != 204 && status != 304 && status != 205) {
            response.addHeader("Content-Length", "0");
        }
    }
}
