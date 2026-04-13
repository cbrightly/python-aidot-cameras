package org.spongycastle.crypto.prng.drbg;

import java.util.Hashtable;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.prng.EntropySource;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Integers;

public class HashSP800DRBG implements SP80090DRBG {
    private static final byte[] a = {1};
    private static final Hashtable b;
    private Digest c;
    private byte[] d;
    private byte[] e;
    private long f;
    private EntropySource g;
    private int h;
    private int i;

    static {
        Hashtable hashtable = new Hashtable();
        b = hashtable;
        hashtable.put("SHA-1", Integers.b(440));
        hashtable.put("SHA-224", Integers.b(440));
        hashtable.put("SHA-256", Integers.b(440));
        hashtable.put("SHA-512/256", Integers.b(440));
        hashtable.put("SHA-512/224", Integers.b(440));
        hashtable.put("SHA-384", Integers.b(888));
        hashtable.put("SHA-512", Integers.b(888));
    }

    public HashSP800DRBG(Digest digest, int securityStrength, EntropySource entropySource, byte[] personalizationString, byte[] nonce) {
        if (securityStrength > Utils.a(digest)) {
            throw new IllegalArgumentException("Requested security strength is not supported by the derivation function");
        } else if (entropySource.b() >= securityStrength) {
            this.c = digest;
            this.g = entropySource;
            this.h = securityStrength;
            this.i = ((Integer) b.get(digest.b())).intValue();
            byte[] seed = Utils.c(this.c, Arrays.s(e(), nonce, personalizationString), this.i);
            this.d = seed;
            byte[] subV = new byte[(seed.length + 1)];
            System.arraycopy(seed, 0, subV, 1, seed.length);
            this.e = Utils.c(this.c, subV, this.i);
            this.f = 1;
        } else {
            throw new IllegalArgumentException("Not enough entropy for security strength required");
        }
    }

    public int a(byte[] output, byte[] additionalInput, boolean predictionResistant) {
        byte[] additionalInput2;
        byte[] bArr = output;
        int numberOfBits = bArr.length * 8;
        if (numberOfBits > 262144) {
            throw new IllegalArgumentException("Number of bits per request limited to 262144");
        } else if (this.f > 140737488355328L) {
            return -1;
        } else {
            if (predictionResistant) {
                b(additionalInput);
                additionalInput2 = null;
            } else {
                additionalInput2 = additionalInput;
            }
            if (additionalInput2 != null) {
                byte[] bArr2 = this.d;
                byte[] newInput = new byte[(bArr2.length + 1 + additionalInput2.length)];
                newInput[0] = 2;
                System.arraycopy(bArr2, 0, newInput, 1, bArr2.length);
                System.arraycopy(additionalInput2, 0, newInput, this.d.length + 1, additionalInput2.length);
                c(this.d, f(newInput));
            }
            byte[] rv = g(this.d, numberOfBits);
            byte[] bArr3 = this.d;
            byte[] subH = new byte[(bArr3.length + 1)];
            System.arraycopy(bArr3, 0, subH, 1, bArr3.length);
            subH[0] = 3;
            c(this.d, f(subH));
            c(this.d, this.e);
            long j = this.f;
            byte[] bArr4 = subH;
            c(this.d, new byte[]{(byte) ((int) (j >> 24)), (byte) ((int) (j >> 16)), (byte) ((int) (j >> 8)), (byte) ((int) j)});
            this.f++;
            System.arraycopy(rv, 0, bArr, 0, bArr.length);
            return numberOfBits;
        }
    }

    private byte[] e() {
        byte[] entropy = this.g.a();
        if (entropy.length >= (this.h + 7) / 8) {
            return entropy;
        }
        throw new IllegalStateException("Insufficient entropy provided by entropy source");
    }

    private void c(byte[] longer, byte[] shorter) {
        int carry = 0;
        int i2 = 1;
        while (true) {
            int i3 = 0;
            if (i2 > shorter.length) {
                break;
            }
            int res = (longer[longer.length - i2] & 255) + (shorter[shorter.length - i2] & 255) + carry;
            if (res > 255) {
                i3 = 1;
            }
            carry = i3;
            longer[longer.length - i2] = (byte) res;
            i2++;
        }
        for (int i4 = shorter.length + 1; i4 <= longer.length; i4++) {
            int res2 = (longer[longer.length - i4] & 255) + carry;
            carry = res2 > 255 ? 1 : 0;
            longer[longer.length - i4] = (byte) res2;
        }
    }

    public void b(byte[] additionalInput) {
        byte[] seed = Utils.c(this.c, Arrays.t(a, this.d, e(), additionalInput), this.i);
        this.d = seed;
        byte[] subV = new byte[(seed.length + 1)];
        subV[0] = 0;
        System.arraycopy(seed, 0, subV, 1, seed.length);
        this.e = Utils.c(this.c, subV, this.i);
        this.f = 1;
    }

    private byte[] f(byte[] input) {
        byte[] hash = new byte[this.c.e()];
        d(input, hash);
        return hash;
    }

    private void d(byte[] input, byte[] output) {
        this.c.update(input, 0, input.length);
        this.c.c(output, 0);
    }

    private byte[] g(byte[] input, int lengthInBits) {
        int m = (lengthInBits / 8) / this.c.e();
        byte[] data = new byte[input.length];
        System.arraycopy(input, 0, data, 0, input.length);
        byte[] W = new byte[(lengthInBits / 8)];
        byte[] dig = new byte[this.c.e()];
        for (int i2 = 0; i2 <= m; i2++) {
            d(data, dig);
            System.arraycopy(dig, 0, W, dig.length * i2, W.length - (dig.length * i2) > dig.length ? dig.length : W.length - (dig.length * i2));
            c(data, a);
        }
        return W;
    }
}
