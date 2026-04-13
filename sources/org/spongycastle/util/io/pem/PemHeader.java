package org.spongycastle.util.io.pem;

public class PemHeader {
    private String a;
    private String b;

    public String b() {
        return this.a;
    }

    public String c() {
        return this.b;
    }

    public int hashCode() {
        return a(this.a) + (a(this.b) * 31);
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof org.spongycastle.util.io.pem.PemHeader
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            r0 = r5
            org.spongycastle.util.io.pem.PemHeader r0 = (org.spongycastle.util.io.pem.PemHeader) r0
            if (r0 == r4) goto L_0x001f
            java.lang.String r2 = r4.a
            java.lang.String r3 = r0.a
            boolean r2 = r4.d(r2, r3)
            if (r2 == 0) goto L_0x0020
            java.lang.String r2 = r4.b
            java.lang.String r3 = r0.b
            boolean r2 = r4.d(r2, r3)
            if (r2 == 0) goto L_0x0020
        L_0x001f:
            r1 = 1
        L_0x0020:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.util.io.pem.PemHeader.equals(java.lang.Object):boolean");
    }

    private int a(String s) {
        if (s == null) {
            return 1;
        }
        return s.hashCode();
    }

    private boolean d(String s1, String s2) {
        if (s1 == s2) {
            return true;
        }
        if (s1 == null || s2 == null) {
            return false;
        }
        return s1.equals(s2);
    }
}
