package org.apache.commons.fileupload;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.fileupload.util.mime.b;

/* compiled from: ParameterParser */
public class g {
    private char[] a = null;
    private int b = 0;
    private int c = 0;
    private int d = 0;
    private int e = 0;
    private boolean f = false;

    private boolean b() {
        return this.b < this.c;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0032  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String a(boolean r6) {
        /*
            r5 = this;
        L_0x0000:
            int r0 = r5.d
            int r1 = r5.e
            if (r0 >= r1) goto L_0x0017
            char[] r1 = r5.a
            char r0 = r1[r0]
            boolean r0 = java.lang.Character.isWhitespace(r0)
            if (r0 == 0) goto L_0x0017
            int r0 = r5.d
            int r0 = r0 + 1
            r5.d = r0
            goto L_0x0000
        L_0x0017:
            int r0 = r5.e
            int r1 = r5.d
            if (r0 <= r1) goto L_0x0030
            char[] r1 = r5.a
            int r0 = r0 + -1
            char r0 = r1[r0]
            boolean r0 = java.lang.Character.isWhitespace(r0)
            if (r0 == 0) goto L_0x0030
            int r0 = r5.e
            int r0 = r0 + -1
            r5.e = r0
            goto L_0x0017
        L_0x0030:
            if (r6 == 0) goto L_0x0051
            int r0 = r5.e
            int r1 = r5.d
            int r2 = r0 - r1
            r3 = 2
            if (r2 < r3) goto L_0x0051
            char[] r2 = r5.a
            char r3 = r2[r1]
            r4 = 34
            if (r3 != r4) goto L_0x0051
            int r3 = r0 + -1
            char r2 = r2[r3]
            if (r2 != r4) goto L_0x0051
            int r1 = r1 + 1
            r5.d = r1
            int r0 = r0 + -1
            r5.e = r0
        L_0x0051:
            r0 = 0
            int r1 = r5.e
            int r2 = r5.d
            if (r1 <= r2) goto L_0x0065
            java.lang.String r1 = new java.lang.String
            char[] r2 = r5.a
            int r3 = r5.d
            int r4 = r5.e
            int r4 = r4 - r3
            r1.<init>(r2, r3, r4)
            r0 = r1
        L_0x0065:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.fileupload.g.a(boolean):java.lang.String");
    }

    private boolean c(char ch, char[] charray) {
        for (char element : charray) {
            if (ch == element) {
                return true;
            }
        }
        return false;
    }

    private String i(char[] terminators) {
        int i = this.b;
        this.d = i;
        this.e = i;
        while (b() && !c(this.a[this.b], terminators)) {
            this.e++;
            this.b++;
        }
        return a(false);
    }

    private String h(char[] terminators) {
        int i = this.b;
        this.d = i;
        this.e = i;
        boolean quoted = false;
        boolean charEscaped = false;
        while (b()) {
            char ch = this.a[this.b];
            if (!quoted && c(ch, terminators)) {
                break;
            }
            boolean z = false;
            if (!charEscaped && ch == '\"') {
                quoted = !quoted;
            }
            if (!charEscaped && ch == '\\') {
                z = true;
            }
            charEscaped = z;
            this.e++;
            this.b++;
        }
        return a(true);
    }

    public void j(boolean b2) {
        this.f = b2;
    }

    public Map<String, String> e(String str, char[] separators) {
        if (separators == null || separators.length == 0) {
            return new HashMap();
        }
        char separator = separators[0];
        if (str != null) {
            int idx = str.length();
            for (char separator2 : separators) {
                int tmp = str.indexOf(separator2);
                if (tmp != -1 && tmp < idx) {
                    idx = tmp;
                    separator = separator2;
                }
            }
        }
        return d(str, separator);
    }

    public Map<String, String> d(String str, char separator) {
        if (str == null) {
            return new HashMap();
        }
        return f(str.toCharArray(), separator);
    }

    public Map<String, String> f(char[] charArray, char separator) {
        if (charArray == null) {
            return new HashMap();
        }
        return g(charArray, 0, charArray.length, separator);
    }

    public Map<String, String> g(char[] charArray, int offset, int length, char separator) {
        if (charArray == null) {
            return new HashMap();
        }
        HashMap<String, String> params = new HashMap<>();
        this.a = charArray;
        this.b = offset;
        this.c = length;
        while (b()) {
            String paramName = i(new char[]{'=', separator});
            String paramValue = null;
            if (b()) {
                int i = this.b;
                if (charArray[i] == '=') {
                    this.b = i + 1;
                    paramValue = h(new char[]{separator});
                    if (paramValue != null) {
                        try {
                            paramValue = b.a(paramValue);
                        } catch (UnsupportedEncodingException e2) {
                        }
                    }
                }
            }
            if (b()) {
                int i2 = this.b;
                if (charArray[i2] == separator) {
                    this.b = i2 + 1;
                }
            }
            if (paramName != null && paramName.length() > 0) {
                if (this.f) {
                    paramName = paramName.toLowerCase(Locale.ENGLISH);
                }
                params.put(paramName, paramValue);
            }
        }
        return params;
    }
}
