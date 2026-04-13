package org.apache.httpcore.impl.entity;

import org.apache.httpcore.ProtocolException;
import org.apache.httpcore.e;
import org.apache.httpcore.entity.d;
import org.apache.httpcore.l;
import org.apache.httpcore.t;
import org.apache.httpcore.util.a;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: StrictContentLengthStrategy */
public class c implements d {
    public static final c a = new c();
    private final int b;

    public c(int implicitLen) {
        this.b = implicitLen;
    }

    public c() {
        this(-1);
    }

    public long a(l message) {
        a.g(message, "HTTP message");
        e transferEncodingHeader = message.u(Constants.TRANSFERENCODING);
        if (transferEncodingHeader != null) {
            String s = transferEncodingHeader.getValue();
            if ("chunked".equalsIgnoreCase(s)) {
                if (!message.getProtocolVersion().lessEquals(t.HTTP_1_0)) {
                    return -2;
                }
                throw new ProtocolException("Chunked transfer encoding not allowed for " + message.getProtocolVersion());
            } else if ("identity".equalsIgnoreCase(s)) {
                return -1;
            } else {
                throw new ProtocolException("Unsupported transfer encoding: " + s);
            }
        } else {
            e contentLengthHeader = message.u("Content-Length");
            if (contentLengthHeader == null) {
                return (long) this.b;
            }
            String s2 = contentLengthHeader.getValue();
            try {
                long len = Long.parseLong(s2);
                if (len >= 0) {
                    return len;
                }
                throw new ProtocolException("Negative content length: " + s2);
            } catch (NumberFormatException e) {
                throw new ProtocolException("Invalid content length: " + s2);
            }
        }
    }
}
