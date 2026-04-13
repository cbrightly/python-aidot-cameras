package org.spongycastle.crypto.prng.drbg;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.prng.EntropySource;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.encoders.Hex;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class CTRSP800DRBG implements SP80090DRBG {
    private static final byte[] a = Hex.a("000102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F");
    private EntropySource b;
    private BlockCipher c;
    private int d;
    private int e;
    private int f;
    private byte[] g;
    private byte[] h;
    private long i = 0;
    private boolean j = false;

    public CTRSP800DRBG(BlockCipher engine, int keySizeInBits, int securityStrength, EntropySource entropySource, byte[] personalizationString, byte[] nonce) {
        this.b = entropySource;
        this.c = engine;
        this.d = keySizeInBits;
        this.f = securityStrength;
        this.e = (engine.c() * 8) + keySizeInBits;
        this.j = n(engine);
        if (securityStrength > 256) {
            throw new IllegalArgumentException("Requested security strength is not supported by the derivation function");
        } else if (m(engine, keySizeInBits) < securityStrength) {
            throw new IllegalArgumentException("Requested security strength is not supported by block cipher and key size");
        } else if (entropySource.b() >= securityStrength) {
            e(l(), nonce, personalizationString);
        } else {
            throw new IllegalArgumentException("Not enough entropy for security strength required");
        }
    }

    private void e(byte[] entropy, byte[] nonce, byte[] personalisationString) {
        byte[] seed = d(Arrays.s(entropy, nonce, personalisationString), this.e);
        int outlen = this.c.c();
        byte[] bArr = new byte[((this.d + 7) / 8)];
        this.g = bArr;
        byte[] bArr2 = new byte[outlen];
        this.h = bArr2;
        g(seed, bArr, bArr2);
        this.i = 1;
    }

    private void g(byte[] seed, byte[] key, byte[] v) {
        byte[] temp = new byte[seed.length];
        byte[] outputBlock = new byte[this.c.c()];
        int outLen = this.c.c();
        this.c.a(true, new KeyParameter(k(key)));
        for (int i2 = 0; i2 * outLen < seed.length; i2++) {
            i(v);
            this.c.f(v, 0, outputBlock, 0);
            System.arraycopy(outputBlock, 0, temp, i2 * outLen, temp.length - (i2 * outLen) > outLen ? outLen : temp.length - (i2 * outLen));
        }
        h(temp, seed, temp, 0);
        System.arraycopy(temp, 0, key, 0, key.length);
        System.arraycopy(temp, key.length, v, 0, v.length);
    }

    private void f(byte[] additionalInput) {
        g(d(Arrays.r(l(), additionalInput), this.e), this.g, this.h);
        this.i = 1;
    }

    private void h(byte[] out, byte[] a2, byte[] b2, int bOff) {
        for (int i2 = 0; i2 < out.length; i2++) {
            out[i2] = (byte) (a2[i2] ^ b2[i2 + bOff]);
        }
    }

    private void i(byte[] longer) {
        int carry = 1;
        for (int i2 = 1; i2 <= longer.length; i2++) {
            int res = (longer[longer.length - i2] & 255) + carry;
            carry = res > 255 ? 1 : 0;
            longer[longer.length - i2] = (byte) res;
        }
    }

    private byte[] l() {
        byte[] entropy = this.b.a();
        if (entropy.length >= (this.f + 7) / 8) {
            return entropy;
        }
        throw new IllegalStateException("Insufficient entropy provided by entropy source");
    }

    private byte[] d(byte[] inputString, int bitLength) {
        byte[] bArr = inputString;
        int outLen = this.c.c();
        int L = bArr.length;
        byte[] S = new byte[((((((L + 8) + 1) + outLen) - 1) / outLen) * outLen)];
        j(S, L, 0);
        j(S, bitLength / 8, 4);
        System.arraycopy(bArr, 0, S, 8, L);
        S[L + 8] = OTACommand.RESP_ID_VERSION;
        int i2 = this.d;
        byte[] temp = new byte[((i2 / 8) + outLen)];
        byte[] bccOut = new byte[outLen];
        byte[] IV = new byte[outLen];
        byte[] K = new byte[(i2 / 8)];
        System.arraycopy(a, 0, K, 0, K.length);
        for (int i3 = 0; i3 * outLen * 8 < this.d + (outLen * 8); i3++) {
            j(IV, i3, 0);
            c(bccOut, K, IV, S);
            System.arraycopy(bccOut, 0, temp, i3 * outLen, temp.length - (i3 * outLen) > outLen ? outLen : temp.length - (i3 * outLen));
        }
        byte[] X = new byte[outLen];
        System.arraycopy(temp, 0, K, 0, K.length);
        System.arraycopy(temp, K.length, X, 0, X.length);
        byte[] temp2 = new byte[(bitLength / 2)];
        this.c.a(true, new KeyParameter(k(K)));
        for (int i4 = 0; i4 * outLen < temp2.length; i4++) {
            this.c.f(X, 0, X, 0);
            System.arraycopy(X, 0, temp2, i4 * outLen, temp2.length - (i4 * outLen) > outLen ? outLen : temp2.length - (i4 * outLen));
        }
        return temp2;
    }

    private void c(byte[] bccOut, byte[] k, byte[] iV, byte[] data) {
        int outlen = this.c.c();
        byte[] chainingValue = new byte[outlen];
        int n = data.length / outlen;
        byte[] inputBlock = new byte[outlen];
        this.c.a(true, new KeyParameter(k(k)));
        this.c.f(iV, 0, chainingValue, 0);
        for (int i2 = 0; i2 < n; i2++) {
            h(inputBlock, chainingValue, data, i2 * outlen);
            this.c.f(inputBlock, 0, chainingValue, 0);
        }
        System.arraycopy(chainingValue, 0, bccOut, 0, bccOut.length);
    }

    private void j(byte[] buf, int value, int offSet) {
        buf[offSet + 0] = (byte) (value >> 24);
        buf[offSet + 1] = (byte) (value >> 16);
        buf[offSet + 2] = (byte) (value >> 8);
        buf[offSet + 3] = (byte) value;
    }

    public int a(byte[] output, byte[] additionalInput, boolean predictionResistant) {
        byte[] additionalInput2;
        if (this.j) {
            if (this.i > IjkMediaMeta.AV_CH_WIDE_LEFT) {
                return -1;
            }
            if (Utils.d(output, 512)) {
                throw new IllegalArgumentException("Number of bits per request limited to 4096");
            }
        } else if (this.i > 140737488355328L) {
            return -1;
        } else {
            if (Utils.d(output, 32768)) {
                throw new IllegalArgumentException("Number of bits per request limited to 262144");
            }
        }
        if (predictionResistant) {
            f(additionalInput);
            additionalInput = null;
        }
        if (additionalInput != null) {
            additionalInput2 = d(additionalInput, this.e);
            g(additionalInput2, this.g, this.h);
        } else {
            additionalInput2 = new byte[this.e];
        }
        byte[] out = new byte[this.h.length];
        this.c.a(true, new KeyParameter(k(this.g)));
        for (int i2 = 0; i2 <= output.length / out.length; i2++) {
            int bytesToCopy = output.length - (out.length * i2) > out.length ? out.length : output.length - (this.h.length * i2);
            if (bytesToCopy != 0) {
                i(this.h);
                this.c.f(this.h, 0, out, 0);
                System.arraycopy(out, 0, output, out.length * i2, bytesToCopy);
            }
        }
        g(additionalInput2, this.g, this.h);
        this.i++;
        return output.length * 8;
    }

    public void b(byte[] additionalInput) {
        f(additionalInput);
    }

    private boolean n(BlockCipher cipher) {
        return cipher.b().equals("DESede") || cipher.b().equals("TDEA");
    }

    private int m(BlockCipher cipher, int keySizeInBits) {
        if (n(cipher) && keySizeInBits == 168) {
            return 112;
        }
        if (cipher.b().equals("AES")) {
            return keySizeInBits;
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public byte[] k(byte[] key) {
        if (!this.j) {
            return key;
        }
        byte[] tmp = new byte[24];
        o(key, 0, tmp, 0);
        o(key, 7, tmp, 8);
        o(key, 14, tmp, 16);
        return tmp;
    }

    private void o(byte[] keyMaster, int keyOff, byte[] tmp, int tmpOff) {
        tmp[tmpOff + 0] = (byte) (keyMaster[keyOff + 0] & 254);
        tmp[tmpOff + 1] = (byte) ((keyMaster[keyOff + 0] << 7) | ((keyMaster[keyOff + 1] & 252) >>> 1));
        tmp[tmpOff + 2] = (byte) ((keyMaster[keyOff + 1] << 6) | ((keyMaster[keyOff + 2] & 248) >>> 2));
        tmp[tmpOff + 3] = (byte) ((keyMaster[keyOff + 2] << 5) | ((keyMaster[keyOff + 3] & 240) >>> 3));
        tmp[tmpOff + 4] = (byte) ((keyMaster[keyOff + 3] << 4) | ((keyMaster[keyOff + 4] & 224) >>> 4));
        tmp[tmpOff + 5] = (byte) ((keyMaster[keyOff + 4] << 3) | ((keyMaster[keyOff + 5] & 192) >>> 5));
        tmp[tmpOff + 6] = (byte) ((keyMaster[keyOff + 5] << 2) | ((keyMaster[keyOff + 6] & OTACommand.RESP_ID_VERSION) >>> 6));
        tmp[tmpOff + 7] = (byte) (keyMaster[keyOff + 6] << 1);
        for (int i2 = tmpOff; i2 <= tmpOff + 7; i2++) {
            byte b2 = tmp[i2];
            tmp[i2] = (byte) ((b2 & 254) | (((((((((b2 >> 1) ^ (b2 >> 2)) ^ (b2 >> 3)) ^ (b2 >> 4)) ^ (b2 >> 5)) ^ (b2 >> 6)) ^ (b2 >> 7)) ^ 1) & 1));
        }
    }
}
