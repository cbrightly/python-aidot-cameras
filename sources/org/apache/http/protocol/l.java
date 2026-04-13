package org.apache.http.protocol;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import org.apache.http.ProtocolException;
import org.apache.http.j;
import org.apache.http.k;
import org.apache.http.o;
import org.apache.http.p;
import org.apache.http.t;
import org.apache.http.util.a;
import org.apache.http.v;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: RequestContent */
public class l implements p {
    private final boolean c;

    public l() {
        this(false);
    }

    public l(boolean overwrite) {
        this.c = overwrite;
    }

    public void b(o request, f context) {
        a.i(request, "HTTP request");
        if (request instanceof k) {
            if (this.c) {
                request.s(Constants.TRANSFERENCODING);
                request.s("Content-Length");
            } else if (request.containsHeader(Constants.TRANSFERENCODING)) {
                throw new ProtocolException("Transfer-encoding header already present");
            } else if (request.containsHeader("Content-Length")) {
                throw new ProtocolException("Content-Length header already present");
            }
            v ver = request.r().getProtocolVersion();
            j entity = ((k) request).a();
            if (entity == null) {
                request.addHeader("Content-Length", "0");
                return;
            }
            if (!entity.isChunked() && entity.getContentLength() >= 0) {
                request.addHeader("Content-Length", Long.toString(entity.getContentLength()));
            } else if (!ver.lessEquals(t.HTTP_1_0)) {
                request.addHeader(Constants.TRANSFERENCODING, "chunked");
            } else {
                throw new ProtocolException("Chunked transfer encoding not allowed for " + ver);
            }
            if (entity.getContentType() != null && !request.containsHeader("Content-Type")) {
                request.I(entity.getContentType());
            }
            if (entity.getContentEncoding() != null && !request.containsHeader(HttpHeaders.HEAD_KEY_CONTENT_ENCODING)) {
                request.I(entity.getContentEncoding());
            }
        }
    }
}
