package io.netty.util;

public class IllegalReferenceCountException extends IllegalStateException {
    private static final long serialVersionUID = -2507492394288153468L;

    public IllegalReferenceCountException() {
    }

    public IllegalReferenceCountException(int refCnt) {
        this("refCnt: " + refCnt);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public IllegalReferenceCountException(int r4, int r5) {
        /*
            r3 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "refCnt: "
            r0.append(r1)
            r0.append(r4)
            java.lang.String r1 = ", "
            r0.append(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            if (r5 <= 0) goto L_0x0023
            r1.<init>()
            java.lang.String r2 = "increment: "
            r1.append(r2)
            r1.append(r5)
            goto L_0x002f
        L_0x0023:
            r1.<init>()
            java.lang.String r2 = "decrement: "
            r1.append(r2)
            int r2 = -r5
            r1.append(r2)
        L_0x002f:
            java.lang.String r1 = r1.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r3.<init>((java.lang.String) r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.IllegalReferenceCountException.<init>(int, int):void");
    }

    public IllegalReferenceCountException(String message) {
        super(message);
    }

    public IllegalReferenceCountException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalReferenceCountException(Throwable cause) {
        super(cause);
    }
}
