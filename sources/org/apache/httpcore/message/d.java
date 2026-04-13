package org.apache.httpcore.message;

import io.netty.util.internal.StringUtil;
import org.apache.httpcore.u;
import org.apache.httpcore.util.a;

/* compiled from: BasicHeaderValueFormatter */
public class d {
    @Deprecated
    public static final d a = new d();
    public static final d b = new d();

    public org.apache.httpcore.util.d e(org.apache.httpcore.util.d charBuffer, u[] nvps, boolean quote) {
        a.g(nvps, "Header parameter array");
        int len = c(nvps);
        org.apache.httpcore.util.d buffer = charBuffer;
        if (buffer == null) {
            buffer = new org.apache.httpcore.util.d(len);
        } else {
            buffer.ensureCapacity(len);
        }
        for (int i = 0; i < nvps.length; i++) {
            if (i > 0) {
                buffer.append("; ");
            }
            d(buffer, nvps[i], quote);
        }
        return buffer;
    }

    /* access modifiers changed from: protected */
    public int c(u[] nvps) {
        if (nvps == null || nvps.length < 1) {
            return 0;
        }
        int result = (nvps.length - 1) * 2;
        for (u nvp : nvps) {
            result += b(nvp);
        }
        return result;
    }

    public org.apache.httpcore.util.d d(org.apache.httpcore.util.d charBuffer, u nvp, boolean quote) {
        a.g(nvp, "Name / value pair");
        int len = b(nvp);
        org.apache.httpcore.util.d buffer = charBuffer;
        if (buffer == null) {
            buffer = new org.apache.httpcore.util.d(len);
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
    public int b(u nvp) {
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
    public void a(org.apache.httpcore.util.d buffer, String value, boolean quote) {
        boolean quoteFlag = quote;
        if (!quoteFlag) {
            for (int i = 0; i < value.length() && !quoteFlag; i++) {
                quoteFlag = f(value.charAt(i));
            }
        }
        if (quoteFlag) {
            buffer.append((char) StringUtil.DOUBLE_QUOTE);
        }
        for (int i2 = 0; i2 < value.length(); i2++) {
            char ch = value.charAt(i2);
            if (g(ch)) {
                buffer.append('\\');
            }
            buffer.append(ch);
        }
        if (quoteFlag) {
            buffer.append((char) StringUtil.DOUBLE_QUOTE);
        }
    }

    /* access modifiers changed from: protected */
    public boolean f(char ch) {
        return " ;,:@()<>\\\"/[]?={}\t".indexOf(ch) >= 0;
    }

    /* access modifiers changed from: protected */
    public boolean g(char ch) {
        return "\"\\".indexOf(ch) >= 0;
    }
}
