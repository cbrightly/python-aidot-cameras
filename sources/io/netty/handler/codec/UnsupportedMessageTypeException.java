package io.netty.handler.codec;

public class UnsupportedMessageTypeException extends CodecException {
    private static final long serialVersionUID = 2799598826487038726L;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public UnsupportedMessageTypeException(java.lang.Object r2, java.lang.Class<?>... r3) {
        /*
            r1 = this;
            if (r2 != 0) goto L_0x0005
            java.lang.String r0 = "null"
            goto L_0x000d
        L_0x0005:
            java.lang.Class r0 = r2.getClass()
            java.lang.String r0 = r0.getName()
        L_0x000d:
            java.lang.String r0 = message(r0, r3)
            r1.<init>((java.lang.String) r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.UnsupportedMessageTypeException.<init>(java.lang.Object, java.lang.Class[]):void");
    }

    public UnsupportedMessageTypeException() {
    }

    public UnsupportedMessageTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedMessageTypeException(String s) {
        super(s);
    }

    public UnsupportedMessageTypeException(Throwable cause) {
        super(cause);
    }

    private static String message(String actualType, Class<?>... expectedTypes) {
        Class<?> t;
        StringBuilder buf = new StringBuilder(actualType);
        if (expectedTypes != null && expectedTypes.length > 0) {
            buf.append(" (expected: ");
            buf.append(expectedTypes[0].getName());
            int i = 1;
            while (i < expectedTypes.length && (t = expectedTypes[i]) != null) {
                buf.append(", ");
                buf.append(t.getName());
                i++;
            }
            buf.append(')');
        }
        return buf.toString();
    }
}
