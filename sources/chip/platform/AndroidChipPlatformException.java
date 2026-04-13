package chip.platform;

public class AndroidChipPlatformException extends Exception {
    private static final long serialVersionUID = 1;
    public int errorCode;

    public AndroidChipPlatformException() {
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public AndroidChipPlatformException(int r4, java.lang.String r5) {
        /*
            r3 = this;
            if (r5 == 0) goto L_0x0004
            r0 = r5
            goto L_0x0014
        L_0x0004:
            r0 = 1
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r1 = 0
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            r0[r1] = r2
            java.lang.String r1 = "Error Code %d"
            java.lang.String r0 = java.lang.String.format(r1, r0)
        L_0x0014:
            r3.<init>(r0)
            r3.errorCode = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: chip.platform.AndroidChipPlatformException.<init>(int, java.lang.String):void");
    }
}
