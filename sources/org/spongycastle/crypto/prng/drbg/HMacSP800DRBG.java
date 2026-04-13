package org.spongycastle.crypto.prng.drbg;

import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.prng.EntropySource;
import org.spongycastle.util.Arrays;

public class HMacSP800DRBG implements SP80090DRBG {
    private byte[] a;
    private byte[] b;
    private long c;
    private EntropySource d;
    private Mac e;
    private int f;

    public HMacSP800DRBG(Mac hMac, int securityStrength, EntropySource entropySource, byte[] personalizationString, byte[] nonce) {
        if (securityStrength > Utils.b(hMac)) {
            throw new IllegalArgumentException("Requested security strength is not supported by the derivation function");
        } else if (entropySource.b() >= securityStrength) {
            this.f = securityStrength;
            this.d = entropySource;
            this.e = hMac;
            byte[] seedMaterial = Arrays.s(c(), nonce, personalizationString);
            byte[] bArr = new byte[hMac.e()];
            this.a = bArr;
            byte[] bArr2 = new byte[bArr.length];
            this.b = bArr2;
            Arrays.F(bArr2, (byte) 1);
            d(seedMaterial);
            this.c = 1;
        } else {
            throw new IllegalArgumentException("Not enough entropy for security strength required");
        }
    }

    private void d(byte[] seedMaterial) {
        e(seedMaterial, (byte) 0);
        if (seedMaterial != null) {
            e(seedMaterial, (byte) 1);
        }
    }

    private void e(byte[] seedMaterial, byte vValue) {
        this.e.a(new KeyParameter(this.a));
        Mac mac = this.e;
        byte[] bArr = this.b;
        mac.update(bArr, 0, bArr.length);
        this.e.d(vValue);
        if (seedMaterial != null) {
            this.e.update(seedMaterial, 0, seedMaterial.length);
        }
        this.e.c(this.a, 0);
        this.e.a(new KeyParameter(this.a));
        Mac mac2 = this.e;
        byte[] bArr2 = this.b;
        mac2.update(bArr2, 0, bArr2.length);
        this.e.c(this.b, 0);
    }

    public int a(byte[] output, byte[] additionalInput, boolean predictionResistant) {
        int numberOfBits = output.length * 8;
        if (numberOfBits > 262144) {
            throw new IllegalArgumentException("Number of bits per request limited to 262144");
        } else if (this.c > 140737488355328L) {
            return -1;
        } else {
            if (predictionResistant) {
                b(additionalInput);
                additionalInput = null;
            }
            if (additionalInput != null) {
                d(additionalInput);
            }
            byte[] rv = new byte[output.length];
            int m = output.length / this.b.length;
            this.e.a(new KeyParameter(this.a));
            for (int i = 0; i < m; i++) {
                Mac mac = this.e;
                byte[] bArr = this.b;
                mac.update(bArr, 0, bArr.length);
                this.e.c(this.b, 0);
                byte[] bArr2 = this.b;
                System.arraycopy(bArr2, 0, rv, bArr2.length * i, bArr2.length);
            }
            byte[] bArr3 = this.b;
            if (bArr3.length * m < rv.length) {
                this.e.update(bArr3, 0, bArr3.length);
                this.e.c(this.b, 0);
                byte[] bArr4 = this.b;
                System.arraycopy(bArr4, 0, rv, bArr4.length * m, rv.length - (bArr4.length * m));
            }
            d(additionalInput);
            this.c++;
            System.arraycopy(rv, 0, output, 0, output.length);
            return numberOfBits;
        }
    }

    public void b(byte[] additionalInput) {
        d(Arrays.r(c(), additionalInput));
        this.c = 1;
    }

    private byte[] c() {
        byte[] entropy = this.d.a();
        if (entropy.length >= (this.f + 7) / 8) {
            return entropy;
        }
        throw new IllegalStateException("Insufficient entropy provided by entropy source");
    }
}
