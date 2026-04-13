package org.apache.http.message;

import org.apache.http.ParseException;
import org.apache.http.protocol.e;
import org.apache.http.t;
import org.apache.http.util.a;
import org.apache.http.util.d;
import org.apache.http.v;
import org.apache.http.y;

/* compiled from: BasicLineParser */
public class k implements u {
    @Deprecated
    public static final k a = new k();
    public static final k b = new k();
    protected final v c;

    public k(v proto) {
        this.c = proto != null ? proto : t.HTTP_1_1;
    }

    public k() {
        this((v) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x004c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.v f(org.apache.http.util.d r18, org.apache.http.message.v r19) {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            r3 = r19
            java.lang.String r0 = "Char array buffer"
            org.apache.http.util.a.i(r2, r0)
            java.lang.String r0 = "Parser cursor"
            org.apache.http.util.a.i(r3, r0)
            org.apache.http.v r0 = r1.c
            java.lang.String r4 = r0.getProtocol()
            int r5 = r4.length()
            int r6 = r19.b()
            int r7 = r19.c()
            r17.g(r18, r19)
            int r0 = r19.b()
            int r8 = r0 + r5
            int r8 = r8 + 4
            java.lang.String r9 = "Not a valid protocol version: "
            if (r8 > r7) goto L_0x00fa
            r8 = 1
            r10 = 0
        L_0x0033:
            r11 = 0
            r12 = 1
            if (r8 == 0) goto L_0x004a
            if (r10 >= r5) goto L_0x004a
            int r13 = r0 + r10
            char r13 = r2.charAt(r13)
            char r14 = r4.charAt(r10)
            if (r13 != r14) goto L_0x0046
            r11 = r12
        L_0x0046:
            r8 = r11
            int r10 = r10 + 1
            goto L_0x0033
        L_0x004a:
            if (r8 == 0) goto L_0x0059
            int r10 = r0 + r5
            char r10 = r2.charAt(r10)
            r13 = 47
            if (r10 != r13) goto L_0x0057
            goto L_0x0058
        L_0x0057:
            r12 = r11
        L_0x0058:
            r8 = r12
        L_0x0059:
            if (r8 == 0) goto L_0x00e1
            int r9 = r5 + 1
            int r9 = r9 + r0
            r0 = 46
            int r10 = r2.indexOf(r0, r9, r7)
            r0 = -1
            if (r10 == r0) goto L_0x00c6
            java.lang.String r12 = r2.substringTrimmed(r9, r10)     // Catch:{ NumberFormatException -> 0x00aa }
            int r12 = java.lang.Integer.parseInt(r12)     // Catch:{ NumberFormatException -> 0x00aa }
            int r9 = r10 + 1
            r13 = 32
            int r13 = r2.indexOf(r13, r9, r7)
            if (r13 != r0) goto L_0x007b
            r13 = r7
        L_0x007b:
            java.lang.String r0 = r2.substringTrimmed(r9, r13)     // Catch:{ NumberFormatException -> 0x008c }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x008c }
            r3.d(r13)
            org.apache.http.v r11 = r1.d(r12, r0)
            return r11
        L_0x008c:
            r0 = move-exception
            org.apache.http.ParseException r14 = new org.apache.http.ParseException
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r16 = r0
            java.lang.String r0 = "Invalid protocol minor version number: "
            r15.append(r0)
            java.lang.String r0 = r2.substring(r6, r7)
            r15.append(r0)
            java.lang.String r0 = r15.toString()
            r14.<init>(r0)
            throw r14
        L_0x00aa:
            r0 = move-exception
            org.apache.http.ParseException r12 = new org.apache.http.ParseException
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = "Invalid protocol major version number: "
            r13.append(r14)
            java.lang.String r14 = r2.substring(r6, r7)
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r12.<init>(r13)
            throw r12
        L_0x00c6:
            org.apache.http.ParseException r0 = new org.apache.http.ParseException
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "Invalid protocol version number: "
            r11.append(r12)
            java.lang.String r12 = r2.substring(r6, r7)
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            r0.<init>(r11)
            throw r0
        L_0x00e1:
            org.apache.http.ParseException r10 = new org.apache.http.ParseException
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r9)
            java.lang.String r9 = r2.substring(r6, r7)
            r11.append(r9)
            java.lang.String r9 = r11.toString()
            r10.<init>(r9)
            throw r10
        L_0x00fa:
            org.apache.http.ParseException r8 = new org.apache.http.ParseException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r9)
            java.lang.String r9 = r2.substring(r6, r7)
            r10.append(r9)
            java.lang.String r9 = r10.toString()
            r8.<init>(r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.message.k.f(org.apache.http.util.d, org.apache.http.message.v):org.apache.http.v");
    }

    /* access modifiers changed from: protected */
    public v d(int major, int minor) {
        return this.c.forVersion(major, minor);
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(org.apache.http.util.d r10, org.apache.http.message.v r11) {
        /*
            r9 = this;
            java.lang.String r0 = "Char array buffer"
            org.apache.http.util.a.i(r10, r0)
            java.lang.String r0 = "Parser cursor"
            org.apache.http.util.a.i(r11, r0)
            int r0 = r11.b()
            org.apache.http.v r1 = r9.c
            java.lang.String r1 = r1.getProtocol()
            int r2 = r1.length()
            int r3 = r10.length()
            int r4 = r2 + 4
            r5 = 0
            if (r3 >= r4) goto L_0x0022
            return r5
        L_0x0022:
            if (r0 >= 0) goto L_0x002d
            int r3 = r10.length()
            int r3 = r3 + -4
            int r0 = r3 - r2
            goto L_0x0042
        L_0x002d:
            if (r0 != 0) goto L_0x0042
        L_0x002f:
            int r3 = r10.length()
            if (r0 >= r3) goto L_0x0042
            char r3 = r10.charAt(r0)
            boolean r3 = org.apache.http.protocol.e.a(r3)
            if (r3 == 0) goto L_0x0042
            int r0 = r0 + 1
            goto L_0x002f
        L_0x0042:
            int r3 = r0 + r2
            int r3 = r3 + 4
            int r4 = r10.length()
            if (r3 <= r4) goto L_0x004d
            return r5
        L_0x004d:
            r3 = 1
            r4 = 0
        L_0x004f:
            r6 = 1
            if (r3 == 0) goto L_0x0066
            if (r4 >= r2) goto L_0x0066
            int r7 = r0 + r4
            char r7 = r10.charAt(r7)
            char r8 = r1.charAt(r4)
            if (r7 != r8) goto L_0x0061
            goto L_0x0062
        L_0x0061:
            r6 = r5
        L_0x0062:
            r3 = r6
            int r4 = r4 + 1
            goto L_0x004f
        L_0x0066:
            if (r3 == 0) goto L_0x0074
            int r4 = r0 + r2
            char r4 = r10.charAt(r4)
            r7 = 47
            if (r4 != r7) goto L_0x0073
            r5 = r6
        L_0x0073:
            r3 = r5
        L_0x0074:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.message.k.a(org.apache.http.util.d, org.apache.http.message.v):boolean");
    }

    public y c(d buffer, v cursor) {
        String reasonPhrase;
        a.i(buffer, "Char array buffer");
        a.i(cursor, "Parser cursor");
        int indexFrom = cursor.b();
        int indexTo = cursor.c();
        try {
            v ver = f(buffer, cursor);
            g(buffer, cursor);
            int i = cursor.b();
            int blank = buffer.indexOf(32, i, indexTo);
            if (blank < 0) {
                blank = indexTo;
            }
            String s = buffer.substringTrimmed(i, blank);
            int j = 0;
            while (j < s.length()) {
                if (Character.isDigit(s.charAt(j))) {
                    j++;
                } else {
                    throw new ParseException("Status line contains invalid status code: " + buffer.substring(indexFrom, indexTo));
                }
            }
            int j2 = Integer.parseInt(s);
            int i2 = blank;
            if (i2 < indexTo) {
                reasonPhrase = buffer.substringTrimmed(i2, indexTo);
            } else {
                reasonPhrase = "";
            }
            return e(ver, j2, reasonPhrase);
        } catch (NumberFormatException e) {
            throw new ParseException("Status line contains invalid status code: " + buffer.substring(indexFrom, indexTo));
        } catch (IndexOutOfBoundsException e2) {
            throw new ParseException("Invalid status line: " + buffer.substring(indexFrom, indexTo));
        }
    }

    /* access modifiers changed from: protected */
    public y e(v ver, int status, String reason) {
        return new o(ver, status, reason);
    }

    public org.apache.http.d b(d buffer) {
        return new q(buffer);
    }

    /* access modifiers changed from: protected */
    public void g(d buffer, v cursor) {
        int pos = cursor.b();
        int indexTo = cursor.c();
        while (pos < indexTo && e.a(buffer.charAt(pos))) {
            pos++;
        }
        cursor.d(pos);
    }
}
