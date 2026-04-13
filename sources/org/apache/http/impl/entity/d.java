package org.apache.http.impl.entity;

import org.apache.http.ProtocolException;
import org.apache.http.entity.e;
import org.apache.http.n;
import org.apache.http.t;
import org.apache.http.util.a;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: StrictContentLengthStrategy */
public class d implements e {
    public static final d a = new d();
    private final int b;

    public d(int implicitLen) {
        this.b = implicitLen;
    }

    public d() {
        this(-1);
    }

    public long a(n message) {
        a.i(message, "HTTP message");
        org.apache.http.d transferEncodingHeader = message.u(Constants.TRANSFERENCODING);
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
            org.apache.http.d contentLengthHeader = message.u("Content-Length");
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
