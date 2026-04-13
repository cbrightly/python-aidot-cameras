package com.yanzhenjie.andserver.http.cookie;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: StandardCookieProcessor */
public class c implements b {
    private static final ThreadLocal<DateFormat> a;
    private static final String b;
    private static final BitSet c = new BitSet(128);

    /* compiled from: StandardCookieProcessor */
    public static final class a extends ThreadLocal<DateFormat> {
        a() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public DateFormat initialValue() {
            DateFormat df = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss z", Locale.US);
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            return df;
        }
    }

    static {
        a aVar = new a();
        a = aVar;
        b = ((DateFormat) aVar.get()).format(new Date(10000));
        for (char c2 = '0'; c2 <= '9'; c2 = (char) (c2 + 1)) {
            c.set(c2);
        }
        for (char c3 = 'a'; c3 <= 'z'; c3 = (char) (c3 + 1)) {
            c.set(c3);
        }
        for (char c4 = 'A'; c4 <= 'Z'; c4 = (char) (c4 + 1)) {
            c.set(c4);
        }
        BitSet bitSet = c;
        bitSet.set(46);
        bitSet.set(45);
    }

    @NonNull
    public String a(@NonNull a cookie) {
        StringBuffer header = new StringBuffer();
        header.append(cookie.getName());
        header.append('=');
        String value = cookie.getValue();
        if (!TextUtils.isEmpty(value)) {
            b(value);
            header.append(value);
        }
        int maxAge = cookie.getMaxAge();
        if (maxAge > -1) {
            header.append("; Max-Age=");
            header.append(maxAge);
            header.append("; Expires=");
            if (maxAge == 0) {
                header.append(b);
            } else {
                a.get().format(new Date(System.currentTimeMillis() + (((long) maxAge) * 1000)), header, new FieldPosition(0));
            }
        }
        String domain = cookie.getDomain();
        if (domain != null && domain.length() > 0) {
            c(domain);
            header.append("; Domain=");
            header.append(domain);
        }
        String path = cookie.getPath();
        if (path != null && path.length() > 0) {
            d(path);
            header.append("; Path=");
            header.append(path);
        }
        if (cookie.getSecure()) {
            header.append("; Secure");
        }
        if (cookie.isHttpOnly()) {
            header.append("; HttpOnly");
        }
        return header.toString();
    }

    private void b(String value) {
        int start = 0;
        int end = value.length();
        if (end > 1 && value.charAt(0) == '\"' && value.charAt(end - 1) == '\"') {
            start = 1;
            end--;
        }
        char[] chars = value.toCharArray();
        for (int i = start; i < end; i++) {
            char c2 = chars[i];
            if (c2 < '!' || c2 == '\"' || c2 == ',' || c2 == ';' || c2 == '\\' || c2 == 127) {
                throw new IllegalArgumentException(String.format("The cookie's value [%1$s] is invalid.", new Object[]{value}));
            }
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r2v2, types: [int, char] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c(java.lang.String r11) {
        /*
            r10 = this;
            r0 = 0
            r1 = -1
            r2 = -1
            char[] r3 = r11.toCharArray()
        L_0x0007:
            int r4 = r3.length
            r5 = 45
            r6 = 0
            java.lang.String r7 = "The cookie's domain [%1$s] is invalid."
            r8 = 46
            r9 = 1
            if (r0 >= r4) goto L_0x0058
            r1 = r2
            char r2 = r3[r0]
            java.util.BitSet r4 = c
            boolean r4 = r4.get(r2)
            if (r4 == 0) goto L_0x004a
            if (r1 == r8) goto L_0x0022
            r4 = -1
            if (r1 != r4) goto L_0x0026
        L_0x0022:
            if (r2 == r8) goto L_0x003c
            if (r2 == r5) goto L_0x003c
        L_0x0026:
            if (r1 != r5) goto L_0x0039
            if (r2 == r8) goto L_0x002b
            goto L_0x0039
        L_0x002b:
            java.lang.Object[] r4 = new java.lang.Object[r9]
            r4[r6] = r11
            java.lang.String r4 = java.lang.String.format(r7, r4)
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            r5.<init>(r4)
            throw r5
        L_0x0039:
            int r0 = r0 + 1
            goto L_0x0007
        L_0x003c:
            java.lang.Object[] r4 = new java.lang.Object[r9]
            r4[r6] = r11
            java.lang.String r4 = java.lang.String.format(r7, r4)
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            r5.<init>(r4)
            throw r5
        L_0x004a:
            java.lang.Object[] r4 = new java.lang.Object[r9]
            r4[r6] = r11
            java.lang.String r4 = java.lang.String.format(r7, r4)
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            r5.<init>(r4)
            throw r5
        L_0x0058:
            if (r2 == r8) goto L_0x005d
            if (r2 == r5) goto L_0x005d
            return
        L_0x005d:
            java.lang.Object[] r4 = new java.lang.Object[r9]
            r4[r6] = r11
            java.lang.String r4 = java.lang.String.format(r7, r4)
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            r5.<init>(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yanzhenjie.andserver.http.cookie.c.c(java.lang.String):void");
    }

    private void d(String path) {
        char[] chars = path.toCharArray();
        for (char ch : chars) {
            if (ch < ' ' || ch > '~' || ch == ';') {
                throw new IllegalArgumentException(String.format("The cookie's path [%1$s] is invalid.", new Object[]{path}));
            }
        }
    }
}
