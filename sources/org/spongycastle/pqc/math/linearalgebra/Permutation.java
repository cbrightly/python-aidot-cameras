package org.spongycastle.pqc.math.linearalgebra;

import com.meituan.robust.Constants;
import java.security.SecureRandom;

public class Permutation {
    private int[] a;

    public Permutation(int n) {
        if (n > 0) {
            this.a = new int[n];
            for (int i = n - 1; i >= 0; i--) {
                this.a[i] = i;
            }
            return;
        }
        throw new IllegalArgumentException("invalid length");
    }

    public Permutation(byte[] enc) {
        if (enc.length > 4) {
            int n = LittleEndianConversions.e(enc, 0);
            int size = IntegerFunctions.b(n - 1);
            if (enc.length == (n * size) + 4) {
                this.a = new int[n];
                for (int i = 0; i < n; i++) {
                    this.a[i] = LittleEndianConversions.f(enc, (i * size) + 4, size);
                }
                if (!d(this.a)) {
                    throw new IllegalArgumentException("invalid encoding");
                }
                return;
            }
            throw new IllegalArgumentException("invalid encoding");
        }
        throw new IllegalArgumentException("invalid encoding");
    }

    public Permutation(int n, SecureRandom sr) {
        if (n > 0) {
            this.a = new int[n];
            int[] help = new int[n];
            for (int i = 0; i < n; i++) {
                help[i] = i;
            }
            int k = n;
            for (int j = 0; j < n; j++) {
                int i2 = RandUtils.a(sr, k);
                k--;
                this.a[j] = help[i2];
                help[i2] = help[k];
            }
            return;
        }
        throw new IllegalArgumentException("invalid length");
    }

    public byte[] b() {
        int n = this.a.length;
        int size = IntegerFunctions.b(n - 1);
        byte[] result = new byte[((n * size) + 4)];
        LittleEndianConversions.a(n, result, 0);
        for (int i = 0; i < n; i++) {
            LittleEndianConversions.b(this.a[i], result, (i * size) + 4, size);
        }
        return result;
    }

    public int[] c() {
        return IntUtils.a(this.a);
    }

    public Permutation a() {
        Permutation result = new Permutation(this.a.length);
        for (int i = this.a.length - 1; i >= 0; i--) {
            result.a[this.a[i]] = i;
        }
        return result;
    }

    public Permutation e(Permutation p) {
        int length = p.a.length;
        int[] iArr = this.a;
        if (length == iArr.length) {
            Permutation result = new Permutation(iArr.length);
            for (int i = this.a.length - 1; i >= 0; i--) {
                result.a[i] = this.a[p.a[i]];
            }
            return result;
        }
        throw new IllegalArgumentException("length mismatch");
    }

    public boolean equals(Object other) {
        if (!(other instanceof Permutation)) {
            return false;
        }
        return IntUtils.b(this.a, ((Permutation) other).a);
    }

    public String toString() {
        String result = Constants.ARRAY_TYPE + this.a[0];
        for (int i = 1; i < this.a.length; i++) {
            result = result + ", " + this.a[i];
        }
        return result + "]";
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    private boolean d(int[] perm) {
        int n = perm.length;
        boolean[] onlyOnce = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (perm[i] < 0 || perm[i] >= n || onlyOnce[perm[i]]) {
                return false;
            }
            onlyOnce[perm[i]] = true;
        }
        return true;
    }
}
