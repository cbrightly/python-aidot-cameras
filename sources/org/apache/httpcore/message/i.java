package org.apache.httpcore.message;

import org.apache.httpcore.e;
import org.apache.httpcore.util.a;
import org.apache.httpcore.util.d;
import org.apache.httpcore.v;
import org.apache.httpcore.x;
import org.apache.httpcore.y;

/* compiled from: BasicLineFormatter */
public class i implements s {
    @Deprecated
    public static final i a = new i();
    public static final i b = new i();

    /* access modifiers changed from: protected */
    public d i(d charBuffer) {
        d buffer = charBuffer;
        if (buffer == null) {
            return new d(64);
        }
        buffer.clear();
        return buffer;
    }

    public d c(d buffer, v version) {
        a.g(version, "Protocol version");
        d result = buffer;
        int len = g(version);
        if (result == null) {
            result = new d(len);
        } else {
            result.ensureCapacity(len);
        }
        result.append(version.getProtocol());
        result.append('/');
        result.append(Integer.toString(version.getMajor()));
        result.append('.');
        result.append(Integer.toString(version.getMinor()));
        return result;
    }

    /* access modifiers changed from: protected */
    public int g(v version) {
        return version.getProtocol().length() + 4;
    }

    public d h(d buffer, x reqline) {
        a.g(reqline, "Request line");
        d result = i(buffer);
        e(result, reqline);
        return result;
    }

    /* access modifiers changed from: protected */
    public void e(d buffer, x reqline) {
        String method = reqline.getMethod();
        String uri = reqline.getUri();
        buffer.ensureCapacity(method.length() + 1 + uri.length() + 1 + g(reqline.getProtocolVersion()));
        buffer.append(method);
        buffer.append(' ');
        buffer.append(uri);
        buffer.append(' ');
        c(buffer, reqline.getProtocolVersion());
    }

    public d b(d buffer, y statline) {
        a.g(statline, "Status line");
        d result = i(buffer);
        f(result, statline);
        return result;
    }

    /* access modifiers changed from: protected */
    public void f(d buffer, y statline) {
        int len = g(statline.getProtocolVersion()) + 1 + 3 + 1;
        String reason = statline.getReasonPhrase();
        if (reason != null) {
            len += reason.length();
        }
        buffer.ensureCapacity(len);
        c(buffer, statline.getProtocolVersion());
        buffer.append(' ');
        buffer.append(Integer.toString(statline.getStatusCode()));
        buffer.append(' ');
        if (reason != null) {
            buffer.append(reason);
        }
    }

    public d a(d buffer, e header) {
        a.g(header, "Header");
        if (header instanceof org.apache.httpcore.d) {
            return ((org.apache.httpcore.d) header).getBuffer();
        }
        d result = i(buffer);
        d(result, header);
        return result;
    }

    /* access modifiers changed from: protected */
    public void d(d buffer, e header) {
        String name = header.getName();
        String value = header.getValue();
        int len = name.length() + 2;
        if (value != null) {
            len += value.length();
        }
        buffer.ensureCapacity(len);
        buffer.append(name);
        buffer.append(": ");
        if (value != null) {
            buffer.ensureCapacity(buffer.length() + value.length());
            for (int valueIndex = 0; valueIndex < value.length(); valueIndex++) {
                char valueChar = value.charAt(valueIndex);
                if (valueChar == 13 || valueChar == 10 || valueChar == 12 || valueChar == 11) {
                    valueChar = ' ';
                }
                buffer.append(valueChar);
            }
        }
    }
}
