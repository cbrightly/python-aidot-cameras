package org.spongycastle.crypto.engines;

import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class Grain128Engine implements StreamCipher {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private int[] d;
    private int[] e;
    private int f;
    private int g = 4;
    private boolean h = false;

    public String b() {
        return "Grain-128";
    }

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof ParametersWithIV) {
            ParametersWithIV ivParams = (ParametersWithIV) params;
            byte[] iv = ivParams.a();
            if (iv == null || iv.length != 12) {
                throw new IllegalArgumentException("Grain-128  requires exactly 12 bytes of IV");
            } else if (ivParams.b() instanceof KeyParameter) {
                KeyParameter key = (KeyParameter) ivParams.b();
                this.b = new byte[key.a().length];
                this.a = new byte[key.a().length];
                this.d = new int[4];
                this.e = new int[4];
                this.c = new byte[4];
                System.arraycopy(iv, 0, this.b, 0, iv.length);
                System.arraycopy(key.a(), 0, this.a, 0, key.a().length);
                reset();
            } else {
                throw new IllegalArgumentException("Grain-128 Init parameters must include a key");
            }
        } else {
            throw new IllegalArgumentException("Grain-128 Init parameters must include an IV");
        }
    }

    private void i() {
        for (int i = 0; i < 8; i++) {
            this.f = f();
            this.e = l(this.e, (h() ^ this.d[0]) ^ this.f);
            this.d = l(this.d, g() ^ this.f);
        }
        this.h = true;
    }

    private int h() {
        int[] iArr = this.e;
        int b0 = iArr[0];
        int b3 = (iArr[0] >>> 3) | (iArr[1] << 29);
        int b11 = (iArr[0] >>> 11) | (iArr[1] << 21);
        int b13 = (iArr[0] >>> 13) | (iArr[1] << 19);
        int b17 = (iArr[0] >>> 17) | (iArr[1] << 15);
        int b18 = (iArr[0] >>> 18) | (iArr[1] << 14);
        int b26 = (iArr[0] >>> 26) | (iArr[1] << 6);
        int b27 = (iArr[0] >>> 27) | (iArr[1] << 5);
        int b40 = (iArr[1] >>> 8) | (iArr[2] << 24);
        int b48 = (iArr[1] >>> 16) | (iArr[2] << 16);
        int b56 = (iArr[1] >>> 24) | (iArr[2] << 8);
        int b59 = (iArr[1] >>> 27) | (iArr[2] << 5);
        int b61 = (iArr[1] >>> 29) | (iArr[2] << 3);
        int b65 = (iArr[2] >>> 1) | (iArr[3] << 31);
        int b67 = (iArr[2] >>> 3) | (iArr[3] << 29);
        int b68 = (iArr[2] >>> 4) | (iArr[3] << 28);
        int b84 = (iArr[2] >>> 20) | (iArr[3] << 12);
        return ((((((((((b0 ^ b26) ^ b56) ^ ((iArr[2] >>> 27) | (iArr[3] << 5))) ^ iArr[3]) ^ (b3 & b67)) ^ (b11 & b13)) ^ (b17 & b18)) ^ (b27 & b59)) ^ (b40 & b48)) ^ (b61 & b65)) ^ (b68 & b84);
    }

    private int g() {
        int[] iArr = this.d;
        int s0 = iArr[0];
        int s7 = (iArr[0] >>> 7) | (iArr[1] << 25);
        int s38 = (iArr[1] >>> 6) | (iArr[2] << 26);
        return ((((s0 ^ s7) ^ s38) ^ ((iArr[2] >>> 6) | (iArr[3] << 26))) ^ ((iArr[2] >>> 17) | (iArr[3] << 15))) ^ iArr[3];
    }

    private int f() {
        int[] iArr = this.e;
        int b2 = (iArr[0] >>> 2) | (iArr[1] << 30);
        int b12 = (iArr[0] >>> 12) | (iArr[1] << 20);
        int b15 = (iArr[0] >>> 15) | (iArr[1] << 17);
        int b36 = (iArr[1] >>> 4) | (iArr[2] << 28);
        int b45 = (iArr[1] >>> 13) | (iArr[2] << 19);
        int b64 = iArr[2];
        int b73 = (iArr[2] >>> 9) | (iArr[3] << 23);
        int b89 = (iArr[2] >>> 25) | (iArr[3] << 7);
        int b95 = (iArr[3] << 1) | (iArr[2] >>> 31);
        int[] iArr2 = this.d;
        return ((((((((((((b12 & ((iArr2[0] >>> 8) | (iArr2[1] << 24))) ^ (((iArr2[0] >>> 13) | (iArr2[1] << 19)) & ((iArr2[0] >>> 20) | (iArr2[1] << 12)))) ^ (b95 & ((iArr2[1] >>> 10) | (iArr2[2] << 22)))) ^ (((iArr2[1] >>> 28) | (iArr2[2] << 4)) & ((iArr2[2] >>> 15) | (iArr2[3] << 17)))) ^ ((b12 & b95) & ((iArr2[2] >>> 31) | (iArr2[3] << 1)))) ^ ((iArr2[2] >>> 29) | (iArr2[3] << 3))) ^ b2) ^ b15) ^ b36) ^ b45) ^ b64) ^ b73) ^ b89;
    }

    private int[] l(int[] array, int val) {
        array[0] = array[1];
        array[1] = array[2];
        array[2] = array[3];
        array[3] = val;
        return array;
    }

    private void k(byte[] keyBytes, byte[] ivBytes) {
        ivBytes[12] = -1;
        ivBytes[13] = -1;
        ivBytes[14] = -1;
        ivBytes[15] = -1;
        this.a = keyBytes;
        this.b = ivBytes;
        int j = 0;
        int i = 0;
        while (true) {
            int[] iArr = this.e;
            if (i < iArr.length) {
                byte[] bArr = this.a;
                iArr[i] = (bArr[j] & 255) | (bArr[j + 3] << 24) | ((bArr[j + 2] << MappingData.PATH) & 16711680) | ((bArr[j + 1] << 8) & 65280);
                int[] iArr2 = this.d;
                byte[] bArr2 = this.b;
                iArr2[i] = (bArr2[j] & 255) | (bArr2[j + 3] << 24) | ((bArr2[j + 2] << MappingData.PATH) & 16711680) | ((bArr2[j + 1] << 8) & 65280);
                j += 4;
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
        this.g = 4;
        k(this.a, this.b);
        i();
    }

    private void j() {
        int f2 = f();
        this.f = f2;
        byte[] bArr = this.c;
        bArr[0] = (byte) f2;
        bArr[1] = (byte) (f2 >> 8);
        bArr[2] = (byte) (f2 >> 16);
        bArr[3] = (byte) (f2 >> 24);
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
        if (this.g > 3) {
            j();
            this.g = 0;
        }
        byte[] bArr = this.c;
        int i = this.g;
        this.g = i + 1;
        return bArr[i];
    }
}
