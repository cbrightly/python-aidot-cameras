package org.apache.http.message;

import io.netty.util.internal.StringUtil;
import org.apache.http.e;
import org.apache.http.u;
import org.apache.http.util.a;
import org.apache.http.util.d;

/* compiled from: BasicHeaderValueFormatter */
public class f {
    @Deprecated
    public static final f a = new f();
    public static final f b = new f();

    public d e(d charBuffer, e elem, boolean quote) {
        a.i(elem, "Header element");
        int len = b(elem);
        d buffer = charBuffer;
        if (buffer == null) {
            buffer = new d(len);
        } else {
            buffer.ensureCapacity(len);
        }
        buffer.append(elem.getName());
        String value = elem.getValue();
        if (value != null) {
            buffer.append('=');
            a(buffer, value, quote);
        }
        int parcnt = elem.a();
        if (parcnt > 0) {
            for (int i = 0; i < parcnt; i++) {
                buffer.append("; ");
                f(buffer, elem.b(i), quote);
            }
        }
        return buffer;
    }

    /* access modifiers changed from: protected */
    public int b(e elem) {
        if (elem == null) {
            return 0;
        }
        int result = elem.getName().length();
        String value = elem.getValue();
        if (value != null) {
            result += value.length() + 3;
        }
        int parcnt = elem.a();
        if (parcnt > 0) {
            for (int i = 0; i < parcnt; i++) {
                result += c(elem.b(i)) + 2;
            }
        }
        return result;
    }

    public d g(d charBuffer, u[] nvps, boolean quote) {
        a.i(nvps, "Header parameter array");
        int len = d(nvps);
        d buffer = charBuffer;
        if (buffer == null) {
            buffer = new d(len);
        } else {
            buffer.ensureCapacity(len);
        }
        for (int i = 0; i < nvps.length; i++) {
            if (i > 0) {
                buffer.append("; ");
            }
            f(buffer, nvps[i], quote);
        }
        return buffer;
    }

    /* access modifiers changed from: protected */
    public int d(u[] nvps) {
        if (nvps == null || nvps.length < 1) {
            return 0;
        }
        int result = (nvps.length - 1) * 2;
        for (u nvp : nvps) {
            result += c(nvp);
        }
        return result;
    }

    public d f(d charBuffer, u nvp, boolean quote) {
        a.i(nvp, "Name / value pair");
        int len = c(nvp);
        d buffer = charBuffer;
        if (buffer == null) {
            buffer = new d(len);
        } else {
            buffer.ensureCapacity(len);
        }
        buffer.append(nvp.getName());
        String value = nvp.getValue();
        if (value != null) {
            buffer.append('=');
            a(buffer, value, quote);
        }
        return buffer;
    }

    /* access modifiers changed from: protected */
    public int c(u nvp) {
        if (nvp == null) {
            return 0;
        }
        int result = nvp.getName().length();
        String value = nvp.getValue();
        if (value != null) {
            return result + value.length() + 3;
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public void a(d buffer, String value, boolean quote) {
        boolean quoteFlag = quote;
        if (!quoteFlag) {
            for (int i = 0; i < value.length() && !quoteFlag; i++) {
                quoteFlag = h(value.charAt(i));
            }
        }
        if (quoteFlag) {
            buffer.append((char) StringUtil.DOUBLE_QUOTE);
        }
        for (int i2 = 0; i2 < value.length(); i2++) {
            char ch = value.charAt(i2);
            if (i(ch)) {
                buffer.append('\\');
            }
            buffer.append(ch);
        }
        if (quoteFlag) {
            buffer.append((char) StringUtil.DOUBLE_QUOTE);
        }
    }

    /* access modifiers changed from: protected */
    public boolean h(char ch) {
        return " ;,:@()<>\\\"/[]?={}\t".indexOf(ch) >= 0;
    }

    /* access modifiers changed from: protected */
    public boolean i(char ch) {
        return "\"\\".indexOf(ch) >= 0;
    }
}
