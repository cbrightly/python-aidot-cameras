package com.caverock.androidsvg;

/* compiled from: IntegerParser */
public class c {
    private int a;
    private long b;

    c(long value, int pos) {
        this.b = value;
        this.a = pos;
    }

    /* access modifiers changed from: package-private */
    public int a() {
        return this.a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x004a A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x004b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.caverock.androidsvg.c c(java.lang.String r11, int r12, int r13, boolean r14) {
        /*
            r0 = r12
            r1 = 0
            r2 = 0
            r4 = 0
            if (r0 < r13) goto L_0x0008
            return r4
        L_0x0008:
            if (r14 == 0) goto L_0x0015
            char r5 = r11.charAt(r0)
            switch(r5) {
                case 43: goto L_0x0013;
                case 44: goto L_0x0011;
                case 45: goto L_0x0012;
                default: goto L_0x0011;
            }
        L_0x0011:
            goto L_0x0015
        L_0x0012:
            r1 = 1
        L_0x0013:
            int r0 = r0 + 1
        L_0x0015:
            r5 = r0
        L_0x0016:
            if (r0 >= r13) goto L_0x0048
            char r6 = r11.charAt(r0)
            r7 = 48
            if (r6 < r7) goto L_0x0048
            r7 = 57
            if (r6 > r7) goto L_0x0048
            r7 = 10
            if (r1 == 0) goto L_0x0037
            long r7 = r7 * r2
            int r9 = r6 + -48
            long r9 = (long) r9
            long r7 = r7 - r9
            r2 = -2147483648(0xffffffff80000000, double:NaN)
            int r2 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r2 >= 0) goto L_0x0035
            return r4
        L_0x0035:
            r2 = r7
            goto L_0x0045
        L_0x0037:
            long r7 = r7 * r2
            int r9 = r6 + -48
            long r9 = (long) r9
            long r7 = r7 + r9
            r2 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r2 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x0044
            return r4
        L_0x0044:
            r2 = r7
        L_0x0045:
            int r0 = r0 + 1
            goto L_0x0016
        L_0x0048:
            if (r0 != r5) goto L_0x004b
            return r4
        L_0x004b:
            com.caverock.androidsvg.c r4 = new com.caverock.androidsvg.c
            r4.<init>(r2, r0)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.c.c(java.lang.String, int, int, boolean):com.caverock.androidsvg.c");
    }

    public int d() {
        return (int) this.b;
    }

    static c b(String input, int startpos, int len) {
        int pos = startpos;
        long value = 0;
        if (pos >= len) {
            return null;
        }
        while (pos < len) {
            char ch = input.charAt(pos);
            if (ch >= '0' && ch <= '9') {
                value = (16 * value) + ((long) (ch - '0'));
            } else if (ch < 'A' || ch > 'F') {
                if (ch < 'a' || ch > 'f') {
                    break;
                }
                value = (16 * value) + ((long) (ch - 'a')) + 10;
            } else {
                value = (16 * value) + ((long) (ch - 'A')) + 10;
            }
            if (value > 4294967295L) {
                return null;
            }
            pos++;
        }
        if (pos == startpos) {
            return null;
        }
        return new c(value, pos);
    }
}
