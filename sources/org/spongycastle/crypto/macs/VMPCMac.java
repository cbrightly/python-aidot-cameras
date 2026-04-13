package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class VMPCMac implements Mac {
    private byte a;
    private byte b = 0;
    private byte[] c = null;
    private byte d = 0;
    private byte[] e;
    private byte[] f;
    private byte[] g;
    private byte h;
    private byte i;
    private byte j;
    private byte k;

    public int c(byte[] out, int outOff) {
        for (int r = 1; r < 25; r++) {
            byte[] bArr = this.c;
            byte b2 = this.d;
            byte b3 = this.b;
            byte b4 = bArr[(b2 + bArr[b3 & 255]) & 255];
            this.d = b4;
            byte b5 = this.k;
            byte b6 = this.j;
            byte b7 = bArr[(b5 + b6 + r) & 255];
            this.k = b7;
            byte b8 = this.i;
            byte b9 = bArr[(b6 + b8 + r) & 255];
            this.j = b9;
            byte b10 = this.h;
            byte b11 = bArr[(b8 + b10 + r) & 255];
            this.i = b11;
            byte b12 = bArr[(b10 + b4 + r) & 255];
            this.h = b12;
            byte[] bArr2 = this.e;
            byte b13 = this.a;
            bArr2[b13 & 31] = (byte) (b12 ^ bArr2[b13 & 31]);
            bArr2[(b13 + 1) & 31] = (byte) (b11 ^ bArr2[(b13 + 1) & 31]);
            bArr2[(b13 + 2) & 31] = (byte) (b9 ^ bArr2[(b13 + 2) & 31]);
            bArr2[(b13 + 3) & 31] = (byte) (b7 ^ bArr2[(b13 + 3) & 31]);
            this.a = (byte) ((b13 + 4) & 31);
            byte temp = bArr[b3 & 255];
            bArr[b3 & 255] = bArr[b4 & 255];
            bArr[b4 & 255] = temp;
            this.b = (byte) ((b3 + 1) & 255);
        }
        for (int m = 0; m < 768; m++) {
            byte[] bArr3 = this.c;
            byte b14 = bArr3[(this.d + bArr3[m & 255] + this.e[m & 31]) & 255];
            this.d = b14;
            byte temp2 = bArr3[m & 255];
            bArr3[m & 255] = bArr3[b14 & 255];
            bArr3[b14 & 255] = temp2;
        }
        byte[] M = new byte[20];
        for (int i2 = 0; i2 < 20; i2++) {
            byte[] bArr4 = this.c;
            byte b15 = bArr4[(this.d + bArr4[i2 & 255]) & 255];
            this.d = b15;
            M[i2] = bArr4[(bArr4[bArr4[b15 & 255] & 255] + 1) & 255];
            byte temp3 = bArr4[i2 & 255];
            bArr4[i2 & 255] = bArr4[b15 & 255];
            bArr4[b15 & 255] = temp3;
        }
        System.arraycopy(M, 0, out, outOff, M.length);
        reset();
        return M.length;
    }

    public String b() {
        return "VMPC-MAC";
    }

    public int e() {
        return 20;
    }

    public void a(CipherParameters params) {
        if (params instanceof ParametersWithIV) {
            ParametersWithIV ivParams = (ParametersWithIV) params;
            KeyParameter key = (KeyParameter) ivParams.b();
            if (ivParams.b() instanceof KeyParameter) {
                byte[] a2 = ivParams.a();
                this.f = a2;
                if (a2 == null || a2.length < 1 || a2.length > 768) {
                    throw new IllegalArgumentException("VMPC-MAC requires 1 to 768 bytes of IV");
                }
                this.g = key.a();
                reset();
                return;
            }
            throw new IllegalArgumentException("VMPC-MAC Init parameters must include a key");
        }
        throw new IllegalArgumentException("VMPC-MAC Init parameters must include an IV");
    }

    private void f(byte[] keyBytes, byte[] ivBytes) {
        this.d = 0;
        this.c = new byte[256];
        for (int i2 = 0; i2 < 256; i2++) {
            this.c[i2] = (byte) i2;
        }
        for (int m = 0; m < 768; m++) {
            byte[] bArr = this.c;
            byte b2 = bArr[(this.d + bArr[m & 255] + keyBytes[m % keyBytes.length]) & 255];
            this.d = b2;
            byte temp = bArr[m & 255];
            bArr[m & 255] = bArr[b2 & 255];
            bArr[b2 & 255] = temp;
        }
        for (int m2 = 0; m2 < 768; m2++) {
            byte[] bArr2 = this.c;
            byte b3 = bArr2[(this.d + bArr2[m2 & 255] + ivBytes[m2 % ivBytes.length]) & 255];
            this.d = b3;
            byte temp2 = bArr2[m2 & 255];
            bArr2[m2 & 255] = bArr2[b3 & 255];
            bArr2[b3 & 255] = temp2;
        }
        this.b = 0;
    }

    public void reset() {
        f(this.g, this.f);
        this.b = 0;
        this.k = 0;
        this.j = 0;
        this.i = 0;
        this.h = 0;
        this.a = 0;
        this.e = new byte[32];
        for (int i2 = 0; i2 < 32; i2++) {
            this.e[i2] = 0;
        }
    }

    public void d(byte in) {
        byte[] bArr = this.c;
        byte b2 = this.d;
        byte b3 = this.b;
        byte b4 = bArr[(b2 + bArr[b3 & 255]) & 255];
        this.d = b4;
        byte b5 = this.k;
        byte b6 = this.j;
        byte b7 = bArr[(b5 + b6) & 255];
        this.k = b7;
        byte b8 = this.i;
        byte b9 = bArr[(b6 + b8) & 255];
        this.j = b9;
        byte b10 = this.h;
        byte b11 = bArr[(b8 + b10) & 255];
        this.i = b11;
        byte b12 = bArr[(b10 + b4 + ((byte) (bArr[(bArr[bArr[b4 & 255] & 255] + 1) & 255] ^ in))) & 255];
        this.h = b12;
        byte[] bArr2 = this.e;
        byte b13 = this.a;
        bArr2[b13 & 31] = (byte) (b12 ^ bArr2[b13 & 31]);
        bArr2[(b13 + 1) & 31] = (byte) (b11 ^ bArr2[(b13 + 1) & 31]);
        bArr2[(b13 + 2) & 31] = (byte) (b9 ^ bArr2[(b13 + 2) & 31]);
        bArr2[(b13 + 3) & 31] = (byte) (b7 ^ bArr2[(b13 + 3) & 31]);
        this.a = (byte) ((b13 + 4) & 31);
        byte temp = bArr[b3 & 255];
        bArr[b3 & 255] = bArr[b4 & 255];
        bArr[b4 & 255] = temp;
        this.b = (byte) ((b3 + 1) & 255);
    }

    public void update(byte[] in, int inOff, int len) {
        if (inOff + len <= in.length) {
            for (int i2 = 0; i2 < len; i2++) {
                d(in[inOff + i2]);
            }
            return;
        }
        throw new DataLengthException("input buffer too short");
    }
}
