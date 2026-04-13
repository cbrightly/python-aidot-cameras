package org.apache.http.message;

import org.apache.http.c;
import org.apache.http.util.a;
import org.apache.http.util.d;
import org.apache.http.v;
import org.apache.http.x;
import org.apache.http.y;

/* compiled from: BasicLineFormatter */
public class j implements t {
    @Deprecated
    public static final j a = new j();
    public static final j b = new j();

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
        a.i(version, "Protocol version");
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

    public d a(d buffer, x reqline) {
        a.i(reqline, "Request line");
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

    public d h(d buffer, y statline) {
        a.i(statline, "Status line");
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

    public d b(d buffer, org.apache.http.d header) {
        a.i(header, "Header");
        if (header instanceof c) {
            return ((c) header).getBuffer();
        }
        d result = i(buffer);
        d(result, header);
        return result;
    }

    /* access modifiers changed from: protected */
    public void d(d buffer, org.apache.http.d header) {
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
            buffer.append(value);
        }
    }
}
