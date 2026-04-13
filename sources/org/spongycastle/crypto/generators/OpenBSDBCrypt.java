package org.spongycastle.crypto.generators;

import java.util.HashSet;
import java.util.Set;

public class OpenBSDBCrypt {
    private static final byte[] a = {46, 47, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57};
    private static final byte[] b = new byte[128];
    private static final Set<String> c;

    static {
        HashSet hashSet = new HashSet();
        c = hashSet;
        hashSet.add("2a");
        hashSet.add("2y");
        hashSet.add("2b");
        int i = 0;
        while (true) {
            byte[] bArr = b;
            if (i >= bArr.length) {
                break;
            }
            bArr[i] = -1;
            i++;
        }
        int i2 = 0;
        while (true) {
            byte[] bArr2 = a;
            if (i2 < bArr2.length) {
                b[bArr2[i2]] = (byte) i2;
                i2++;
            } else {
                return;
            }
        }
    }
}
