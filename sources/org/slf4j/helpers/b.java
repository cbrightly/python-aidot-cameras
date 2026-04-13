package org.slf4j.helpers;

/* compiled from: MessageFormatter */
public final class b {
    public static Throwable a(Object[] argArray) {
        if (argArray == null || argArray.length == 0) {
            return null;
        }
        Throwable th = argArray[argArray.length - 1];
        if (th instanceof Throwable) {
            return th;
        }
        return null;
    }

    public static Object[] b(Object[] argArray) {
        if (argArray == null || argArray.length == 0) {
            throw new IllegalStateException("non-sensical empty or null argument array");
        }
        int trimmedLen = argArray.length - 1;
        Object[] trimmed = new Object[trimmedLen];
        if (trimmedLen > 0) {
            System.arraycopy(argArray, 0, trimmed, 0, trimmedLen);
        }
        return trimmed;
    }
}
