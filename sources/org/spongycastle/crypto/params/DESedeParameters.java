package org.spongycastle.crypto.params;

public class DESedeParameters extends DESParameters {
    public static boolean g(byte[] key, int offset, int length) {
        for (int i = offset; i < length; i += 8) {
            if (DESParameters.b(key, i)) {
                return true;
            }
        }
        return false;
    }

    public static boolean f(byte[] key, int offset) {
        return key.length == 16 ? d(key, offset) : e(key, offset);
    }

    public static boolean d(byte[] key, int offset) {
        boolean isValid = false;
        for (int i = offset; i != offset + 8; i++) {
            if (key[i] != key[i + 8]) {
                isValid = true;
            }
        }
        return isValid;
    }

    public static boolean e(byte[] key, int offset) {
        boolean diff12 = false;
        boolean diff13 = false;
        boolean diff23 = false;
        int i = offset;
        while (true) {
            boolean z = false;
            if (i == offset + 8) {
                break;
            }
            diff12 |= key[i] != key[i + 8];
            diff13 |= key[i] != key[i + 16];
            if (key[i + 8] != key[i + 16]) {
                z = true;
            }
            diff23 |= z;
            i++;
        }
        if (!diff12 || !diff13 || !diff23) {
            return false;
        }
        return true;
    }
}
