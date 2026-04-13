package org.spongycastle.util;

import com.alibaba.fastjson.asm.Opcodes;
import org.spongycastle.crypto.digests.SHA512tDigest;

public class Fingerprint {
    private static char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private final byte[] b;

    public Fingerprint(byte[] source) {
        this.b = a(source);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i != this.b.length; i++) {
            if (i > 0) {
                sb.append(":");
            }
            sb.append(a[(this.b[i] >>> 4) & 15]);
            sb.append(a[this.b[i] & 15]);
        }
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Fingerprint) {
            return Arrays.b(((Fingerprint) o).b, this.b);
        }
        return false;
    }

    public int hashCode() {
        return Arrays.J(this.b);
    }

    public static byte[] a(byte[] input) {
        SHA512tDigest digest = new SHA512tDigest((int) Opcodes.IF_ICMPNE);
        digest.update(input, 0, input.length);
        byte[] rv = new byte[digest.e()];
        digest.c(rv, 0);
        return rv;
    }
}
