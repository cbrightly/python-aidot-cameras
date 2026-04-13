package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.u;
import java.net.ProtocolException;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: StatusLine */
public final class p {
    public final u a;
    public final int b;
    public final String c;

    public p(u protocol, int code, String message) {
        this.a = protocol;
        this.b = code;
        this.c = message;
    }

    public static p a(String statusLine) {
        u protocol;
        int codeStart;
        if (statusLine.startsWith("HTTP/1.")) {
            if (statusLine.length() < 9 || statusLine.charAt(8) != ' ') {
                throw new ProtocolException("Unexpected status line: " + statusLine);
            }
            int httpMinorVersion = statusLine.charAt(7) - '0';
            codeStart = 9;
            if (httpMinorVersion == 0) {
                protocol = u.HTTP_1_0;
            } else if (httpMinorVersion == 1) {
                protocol = u.HTTP_1_1;
            } else {
                throw new ProtocolException("Unexpected status line: " + statusLine);
            }
        } else if (statusLine.startsWith("ICY ")) {
            protocol = u.HTTP_1_0;
            codeStart = 4;
        } else {
            throw new ProtocolException("Unexpected status line: " + statusLine);
        }
        if (statusLine.length() >= codeStart + 3) {
            try {
                int code = Integer.parseInt(statusLine.substring(codeStart, codeStart + 3));
                String message = "";
                if (statusLine.length() > codeStart + 3) {
                    if (statusLine.charAt(codeStart + 3) == ' ') {
                        message = statusLine.substring(codeStart + 4);
                    } else {
                        throw new ProtocolException("Unexpected status line: " + statusLine);
                    }
                }
                return new p(protocol, code, message);
            } catch (NumberFormatException e) {
                throw new ProtocolException("Unexpected status line: " + statusLine);
            }
        } else {
            throw new ProtocolException("Unexpected status line: " + statusLine);
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.a == u.HTTP_1_0 ? Constants.HTTP_10 : Constants.HTTP_11);
        result.append(' ');
        result.append(this.b);
        if (this.c != null) {
            result.append(' ');
            result.append(this.c);
        }
        return result.toString();
    }
}
