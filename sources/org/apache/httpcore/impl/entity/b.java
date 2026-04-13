package org.apache.httpcore.impl.entity;

import org.apache.httpcore.ParseException;
import org.apache.httpcore.ProtocolException;
import org.apache.httpcore.e;
import org.apache.httpcore.entity.d;
import org.apache.httpcore.f;
import org.apache.httpcore.l;
import org.apache.httpcore.util.a;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: LaxContentLengthStrategy */
public class b implements d {
    public static final b a = new b();
    private final int b;

    public b(int implicitLen) {
        this.b = implicitLen;
    }

    public b() {
        this(-1);
    }

    public long a(l message) {
        a.g(message, "HTTP message");
        e transferEncodingHeader = message.u(Constants.TRANSFERENCODING);
        if (transferEncodingHeader != null) {
            try {
                f[] encodings = transferEncodingHeader.getElements();
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
            long contentLen = -1;
            e[] headers = message.c("Content-Length");
            int i = headers.length - 1;
            while (true) {
                if (i < 0) {
                    break;
                }
                try {
                    contentLen = Long.parseLong(headers[i].getValue());
                    break;
                } catch (NumberFormatException e) {
                    i--;
                }
            }
            if (contentLen >= 0) {
                return contentLen;
            }
            return -1;
        }
    }
}
