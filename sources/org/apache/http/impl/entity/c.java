package org.apache.http.impl.entity;

import org.apache.http.ParseException;
import org.apache.http.ProtocolException;
import org.apache.http.d;
import org.apache.http.entity.e;
import org.apache.http.n;
import org.apache.http.util.a;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: LaxContentLengthStrategy */
public class c implements e {
    public static final c a = new c();
    private final int b;

    public c(int implicitLen) {
        this.b = implicitLen;
    }

    public c() {
        this(-1);
    }

    public long a(n message) {
        a.i(message, "HTTP message");
        d transferEncodingHeader = message.u(Constants.TRANSFERENCODING);
        if (transferEncodingHeader != null) {
            try {
                org.apache.http.e[] encodings = transferEncodingHeader.getElements();
                int len = encodings.length;
                if (!"identity".equalsIgnoreCase(transferEncodingHeader.getValue()) && len > 0 && "chunked".equalsIgnoreCase(encodings[len - 1].getName())) {
                    return -2;
                }
                return -1;
            } catch (ParseException px) {
                throw new ProtocolException("Invalid Transfer-Encoding header value: " + transferEncodingHeader, px);
            }
        } else if (message.u("Content-Length") == null) {
            return (long) this.b;
        } else {
            long contentlen = -1;
            d[] headers = message.c("Content-Length");
            int i = headers.length - 1;
            while (true) {
                if (i < 0) {
                    break;
                }
                try {
                    contentlen = Long.parseLong(headers[i].getValue());
                    break;
                } catch (NumberFormatException e) {
                    i--;
                }
            }
            if (contentlen >= 0) {
                return contentlen;
            }
            return -1;
        }
    }
}
