package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class Grainv1Engine implements StreamCipher {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private int[] d;
    private int[] e;
    private int f;
    private int g = 2;
    private boolean h = false;

    public String b() {
        return "Grain v1";
    }

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof ParametersWithIV) {
            ParametersWithIV ivParams = (ParametersWithIV) params;
            byte[] iv = ivParams.a();
            if (iv == null || iv.length != 8) {
                throw new IllegalArgumentException("Grain v1 requires exactly 8 bytes of IV");
            } else if (ivParams.b() instanceof KeyParameter) {
                KeyParameter key = (KeyParameter) ivParams.b();
                this.b = new byte[key.a().length];
                this.a = new byte[key.a().length];
                this.d = new int[5];
                this.e = new int[5];
                this.c = new byte[2];
                System.arraycopy(iv, 0, this.b, 0, iv.length);
                System.arraycopy(key.a(), 0, this.a, 0, key.a().length);
                reset();
            } else {
                throw new IllegalArgumentException("Grain v1 Init parameters must include a key");
            }
        } else {
            throw new IllegalArgumentException("Grain v1 Init parameters must include an IV");
        }
    }

    private void i() {
        for (int i = 0; i < 10; i++) {
            this.f = f();
            this.e = l(this.e, (h() ^ this.d[0]) ^ this.f);
            this.d = l(this.d, g() ^ this.f);
        }
        this.h = true;
    }

    private int h() {
        int[] iArr = this.e;
        int b0 = iArr[0];
        int b9 = (iArr[0] >>> 9) | (iArr[1] << 7);
        int b14 = (iArr[0] >>> 14) | (iArr[1] << 2);
        int b15 = (iArr[0] >>> 15) | (iArr[1] << 1);
        int b21 = (iArr[1] >>> 5) | (iArr[2] << 11);
        int b28 = (iArr[1] >>> 12) | (iArr[2] << 4);
        int b33 = (iArr[2] >>> 1) | (iArr[3] << 15);
        int b37 = (iArr[2] >>> 5) | (iArr[3] << 11);
        int b45 = (iArr[2] >>> 13) | (iArr[3] << 3);
        int b52 = (iArr[3] >>> 4) | (iArr[4] << 12);
        int b60 = (iArr[3] >>> 12) | (iArr[4] << 4);
        int b63 = (iArr[4] << 1) | (iArr[3] >>> 15);
        return (((((((((((((((((((((((iArr[3] >>> 14) | (iArr[4] << 2)) ^ b60) ^ b52) ^ b45) ^ b37) ^ b33) ^ b28) ^ b21) ^ b14) ^ b9) ^ b0) ^ (b63 & b60)) ^ (b37 & b33)) ^ (b15 & b9)) ^ ((b60 & b52) & b45)) ^ ((b33 & b28) & b21)) ^ (((b63 & b45) & b28) & b9)) ^ (((b60 & b52) & b37) & b33)) ^ (((b63 & b60) & b21) & b15)) ^ ((((b63 & b60) & b52) & b45) & b37)) ^ ((((b33 & b28) & b21) & b15) & b9)) ^ (((((b52 & b45) & b37) & b33) & b28) & b21)) & 65535;
    }

    private int g() {
        int[] iArr = this.d;
        int s23 = (iArr[1] >>> 7) | (iArr[2] << 9);
        int s38 = (iArr[2] >>> 6) | (iArr[3] << 10);
        int i = iArr[4] << 2;
        return (((((iArr[0] ^ ((iArr[0] >>> 13) | (iArr[1] << 3))) ^ s23) ^ s38) ^ ((iArr[3] >>> 3) | (iArr[4] << 13))) ^ (i | (iArr[3] >>> 14))) & 65535;
    }

    private int f() {
        int[] iArr = this.e;
        int b1 = (iArr[0] >>> 1) | (iArr[1] << 15);
        int b2 = (iArr[0] >>> 2) | (iArr[1] << 14);
        int b4 = (iArr[0] >>> 4) | (iArr[1] << 12);
        int b10 = (iArr[0] >>> 10) | (iArr[1] << 6);
        int b31 = (iArr[1] >>> 15) | (iArr[2] << 1);
        int b43 = (iArr[2] >>> 11) | (iArr[3] << 5);
        int b56 = (iArr[3] >>> 8) | (iArr[4] << 8);
        int b63 = (iArr[4] << 1) | (iArr[3] >>> 15);
        int[] iArr2 = this.d;
        int s3 = (iArr2[0] >>> 3) | (iArr2[1] << 13);
        int s25 = (iArr2[1] >>> 9) | (iArr2[2] << 7);
        int s46 = (iArr2[3] << 2) | (iArr2[2] >>> 14);
        int s64 = iArr2[4];
        return ((((((((((((((((s25 ^ b63) ^ (s3 & s64)) ^ (s46 & s64)) ^ (s64 & b63)) ^ ((s3 & s25) & s46)) ^ ((s3 & s46) & s64)) ^ ((s3 & s46) & b63)) ^ ((s25 & s46) & b63)) ^ ((s46 & s64) & b63)) ^ b1) ^ b2) ^ b4) ^ b10) ^ b31) ^ b43) ^ b56) & 65535;
    }

    private int[] l(int[] array, int val) {
        array[0] = array[1];
        array[1] = array[2];
        array[2] = array[3];
        array[3] = array[4];
        array[4] = val;
        return array;
    }

    private void k(byte[] keyBytes, byte[] ivBytes) {
        ivBytes[8] = -1;
        ivBytes[9] = -1;
        this.a = keyBytes;
        this.b = ivBytes;
        int j = 0;
        int i = 0;
        while (true) {
            int[] iArr = this.e;
            if (i < iArr.length) {
                byte[] bArr = this.a;
                iArr[i] = ((bArr[j] & 255) | (bArr[j + 1] << 8)) & 65535;
                int[] iArr2 = this.d;
                byte[] bArr2 = this.b;
                iArr2[i] = ((bArr2[j] & 255) | (bArr2[j + 1] << 8)) & 65535;
                j += 2;
                i++;
            } else {
                return;
            }
        }
    }

    public int d(byte[] in, int inOff, int len, byte[] out, int outOff) {
        if (!this.h) {
            throw new IllegalStateException(b() + " not initialised");
        } else if (inOff + len > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + len <= out.length) {
            for (int i = 0; i < len; i++) {
                out[outOff + i] = (byte) (in[inOff + i] ^ c());
            }
            return len;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
        this.g = 2;
        k(this.a, this.b);
        i();
    }

    private void j() {
        int f2 = f();
        this.f = f2;
        byte[] bArr = this.c;
        bArr[0] = (byte) f2;
        bArr[1] = (byte) (f2 >> 8);
        this.e = l(this.e, h() ^ this.d[0]);
        this.d = l(this.d, g());
    }

    public byte e(byte in) {
        if (this.h) {
            return (byte) (c() ^ in);
        }
        throw new IllegalStateException(b() + " not initialised");
    }

    private byte c() {
        if (this.g > 1) {
            j();
            this.g = 0;
        }
        byte[] bArr = this.c;
        int i = this.g;
        this.g = i + 1;
        return bArr[i];
    }
}
