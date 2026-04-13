package org.apache.httpcore.message;

import org.apache.httpcore.ParseException;
import org.apache.httpcore.e;
import org.apache.httpcore.protocol.c;
import org.apache.httpcore.t;
import org.apache.httpcore.util.a;
import org.apache.httpcore.util.d;
import org.apache.httpcore.v;
import org.apache.httpcore.x;

/* compiled from: BasicLineParser */
public class j implements t {
    @Deprecated
    public static final j a = new j();
    public static final j b = new j();
    protected final v c;

    public j(v proto) {
        this.c = proto != null ? proto : t.HTTP_1_1;
    }

    public j() {
        this((v) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0046  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.httpcore.v e(org.apache.httpcore.util.d r14, org.apache.httpcore.message.u r15) {
        /*
            r13 = this;
            java.lang.String r0 = "Char array buffer"
            org.apache.httpcore.util.a.g(r14, r0)
            java.lang.String r0 = "Parser cursor"
            org.apache.httpcore.util.a.g(r15, r0)
            org.apache.httpcore.v r0 = r13.c
            java.lang.String r0 = r0.getProtocol()
            int r1 = r0.length()
            int r2 = r15.b()
            int r3 = r15.c()
            r13.f(r14, r15)
            int r4 = r15.b()
            int r5 = r4 + r1
            int r5 = r5 + 4
            java.lang.String r6 = "Not a valid protocol version: "
            if (r5 > r3) goto L_0x00f1
            r5 = 1
            r7 = 0
        L_0x002d:
            r8 = 0
            r9 = 1
            if (r5 == 0) goto L_0x0044
            if (r7 >= r1) goto L_0x0044
            int r10 = r4 + r7
            char r10 = r14.charAt(r10)
            char r11 = r0.charAt(r7)
            if (r10 != r11) goto L_0x0040
            r8 = r9
        L_0x0040:
            r5 = r8
            int r7 = r7 + 1
            goto L_0x002d
        L_0x0044:
            if (r5 == 0) goto L_0x0052
            int r7 = r4 + r1
            char r7 = r14.charAt(r7)
            r10 = 47
            if (r7 != r10) goto L_0x0051
            r8 = r9
        L_0x0051:
            r5 = r8
        L_0x0052:
            if (r5 == 0) goto L_0x00d8
            int r6 = r1 + 1
            int r4 = r4 + r6
            r6 = 46
            int r6 = r14.indexOf(r6, r4, r3)
            r7 = -1
            if (r6 == r7) goto L_0x00bd
            java.lang.String r8 = r14.substringTrimmed(r4, r6)     // Catch:{ NumberFormatException -> 0x00a1 }
            int r8 = java.lang.Integer.parseInt(r8)     // Catch:{ NumberFormatException -> 0x00a1 }
            int r4 = r6 + 1
            r9 = 32
            int r9 = r14.indexOf(r9, r4, r3)
            if (r9 != r7) goto L_0x0074
            r9 = r3
        L_0x0074:
            java.lang.String r7 = r14.substringTrimmed(r4, r9)     // Catch:{ NumberFormatException -> 0x0085 }
            int r7 = java.lang.Integer.parseInt(r7)     // Catch:{ NumberFormatException -> 0x0085 }
            r15.d(r9)
            org.apache.httpcore.v r10 = r13.c(r8, r7)
            return r10
        L_0x0085:
            r7 = move-exception
            org.apache.httpcore.ParseException r10 = new org.apache.httpcore.ParseException
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "Invalid protocol minor version number: "
            r11.append(r12)
            java.lang.String r12 = r14.substring(r2, r3)
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            r10.<init>(r11)
            throw r10
        L_0x00a1:
            r7 = move-exception
            org.apache.httpcore.ParseException r8 = new org.apache.httpcore.ParseException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Invalid protocol major version number: "
            r9.append(r10)
            java.lang.String r10 = r14.substring(r2, r3)
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x00bd:
            org.apache.httpcore.ParseException r7 = new org.apache.httpcore.ParseException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Invalid protocol version number: "
            r8.append(r9)
            java.lang.String r9 = r14.substring(r2, r3)
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r7.<init>(r8)
            throw r7
        L_0x00d8:
            org.apache.httpcore.ParseException r7 = new org.apache.httpcore.ParseException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r6)
            java.lang.String r6 = r14.substring(r2, r3)
            r8.append(r6)
            java.lang.String r6 = r8.toString()
            r7.<init>(r6)
            throw r7
        L_0x00f1:
            org.apache.httpcore.ParseException r5 = new org.apache.httpcore.ParseException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r6)
            java.lang.String r6 = r14.substring(r2, r3)
            r7.append(r6)
            java.lang.String r6 = r7.toString()
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.httpcore.message.j.e(org.apache.httpcore.util.d, org.apache.httpcore.message.u):org.apache.httpcore.v");
    }

    /* access modifiers changed from: protected */
    public v c(int major, int minor) {
        return this.c.forVersion(major, minor);
    }

    public x b(d buffer, u cursor) {
        a.g(buffer, "Char array buffer");
        a.g(cursor, "Parser cursor");
        int indexFrom = cursor.b();
        int indexTo = cursor.c();
        try {
            f(buffer, cursor);
            int i = cursor.b();
            int blank = buffer.indexOf(32, i, indexTo);
            if (blank >= 0) {
                String method = buffer.substringTrimmed(i, blank);
                cursor.d(blank);
                f(buffer, cursor);
                int i2 = cursor.b();
                int blank2 = buffer.indexOf(32, i2, indexTo);
                if (blank2 >= 0) {
                    String uri = buffer.substringTrimmed(i2, blank2);
                    cursor.d(blank2);
                    v ver = e(buffer, cursor);
                    f(buffer, cursor);
                    if (cursor.a()) {
                        return d(method, uri, ver);
                    }
                    throw new ParseException("Invalid request line: " + buffer.substring(indexFrom, indexTo));
                }
                throw new ParseException("Invalid request line: " + buffer.substring(indexFrom, indexTo));
            }
            throw new ParseException("Invalid request line: " + buffer.substring(indexFrom, indexTo));
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException("Invalid request line: " + buffer.substring(indexFrom, indexTo));
        }
    }

    /* access modifiers changed from: protected */
    public x d(String method, String uri, v ver) {
        return new m(method, uri, ver);
    }

    public e a(d buffer) {
        return new p(buffer);
    }

    /* access modifiers changed from: protected */
    public void f(d buffer, u cursor) {
        int pos = cursor.b();
        int indexTo = cursor.c();
        while (pos < indexTo && c.a(buffer.charAt(pos))) {
            pos++;
        }
        cursor.d(pos);
    }
}
