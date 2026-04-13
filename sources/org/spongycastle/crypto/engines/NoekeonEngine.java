package org.spongycastle.crypto.engines;

import com.alibaba.fastjson.asm.Opcodes;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class NoekeonEngine implements BlockCipher {
    private static final int[] a = {0, 0, 0, 0};
    private static final int[] b = {128, 27, 54, 108, 216, 171, 77, Opcodes.IFNE, 47, 94, 188, 99, Opcodes.IFNULL, Opcodes.DCMPL, 53, 106, 212};
    private int[] c = new int[4];
    private int[] d = new int[4];
    private int[] e = new int[4];
    private boolean f = false;
    private boolean g;

    public String b() {
        return "Noekeon";
    }

    public int c() {
        return 16;
    }

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof KeyParameter) {
            this.g = forEncryption;
            this.f = true;
            m(((KeyParameter) params).a());
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to Noekeon init - " + params.getClass().getName());
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        if (!this.f) {
            throw new IllegalStateException(b() + " not initialised");
        } else if (inOff + 16 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + 16 > out.length) {
            throw new OutputLengthException("output buffer too short");
        } else if (this.g) {
            return g(in, inOff, out, outOff);
        } else {
            return e(in, inOff, out, outOff);
        }
    }

    public void reset() {
    }

    private void m(byte[] key) {
        this.d[0] = d(key, 0);
        this.d[1] = d(key, 4);
        this.d[2] = d(key, 8);
        this.d[3] = d(key, 12);
    }

    private int g(byte[] in, int inOff, byte[] out, int outOff) {
        this.c[0] = d(in, inOff);
        this.c[1] = d(in, inOff + 4);
        this.c[2] = d(in, inOff + 8);
        this.c[3] = d(in, inOff + 12);
        int i = 0;
        while (i < 16) {
            int[] iArr = this.c;
            iArr[0] = iArr[0] ^ b[i];
            n(iArr, this.d);
            j(this.c);
            h(this.c);
            k(this.c);
            i++;
        }
        int[] iArr2 = this.c;
        iArr2[0] = iArr2[0] ^ b[i];
        n(iArr2, this.d);
        i(this.c[0], out, outOff);
        i(this.c[1], out, outOff + 4);
        i(this.c[2], out, outOff + 8);
        i(this.c[3], out, outOff + 12);
        return 16;
    }

    private int e(byte[] in, int inOff, byte[] out, int outOff) {
        this.c[0] = d(in, inOff);
        this.c[1] = d(in, inOff + 4);
        this.c[2] = d(in, inOff + 8);
        this.c[3] = d(in, inOff + 12);
        int[] iArr = this.d;
        System.arraycopy(iArr, 0, this.e, 0, iArr.length);
        n(this.e, a);
        int i = 16;
        while (i > 0) {
            n(this.c, this.e);
            int[] iArr2 = this.c;
            iArr2[0] = iArr2[0] ^ b[i];
            j(iArr2);
            h(this.c);
            k(this.c);
            i--;
        }
        n(this.c, this.e);
        int[] iArr3 = this.c;
        iArr3[0] = iArr3[0] ^ b[i];
        i(iArr3[0], out, outOff);
        i(this.c[1], out, outOff + 4);
        i(this.c[2], out, outOff + 8);
        i(this.c[3], out, outOff + 12);
        return 16;
    }

    private void h(int[] a2) {
        a2[1] = a2[1] ^ ((~a2[3]) & (~a2[2]));
        a2[0] = a2[0] ^ (a2[2] & a2[1]);
        int tmp = a2[3];
        a2[3] = a2[0];
        a2[0] = tmp;
        a2[2] = a2[2] ^ ((a2[0] ^ a2[1]) ^ a2[3]);
        a2[1] = ((~a2[3]) & (~a2[2])) ^ a2[1];
        a2[0] = (a2[1] & a2[2]) ^ a2[0];
    }

    private void n(int[] a2, int[] k) {
        int tmp = a2[0] ^ a2[2];
        int tmp2 = tmp ^ (l(tmp, 8) ^ l(tmp, 24));
        a2[1] = a2[1] ^ tmp2;
        a2[3] = a2[3] ^ tmp2;
        for (int i = 0; i < 4; i++) {
            a2[i] = a2[i] ^ k[i];
        }
        int tmp3 = a2[1] ^ a2[3];
        int tmp4 = tmp3 ^ (l(tmp3, 8) ^ l(tmp3, 24));
        a2[0] = a2[0] ^ tmp4;
        a2[2] = a2[2] ^ tmp4;
    }

    private void j(int[] a2) {
        a2[1] = l(a2[1], 1);
        a2[2] = l(a2[2], 5);
        a2[3] = l(a2[3], 2);
    }

    private void k(int[] a2) {
        a2[1] = l(a2[1], 31);
        a2[2] = l(a2[2], 27);
        a2[3] = l(a2[3], 30);
    }

    private int d(byte[] in, int off) {
        int off2 = off + 1;
        int off3 = off2 + 1;
        return (in[off] << 24) | ((in[off2] & 255) << MappingData.PATH) | ((in[off3] & 255) << 8) | (in[off3 + 1] & 255);
    }

    private void i(int x, byte[] out, int off) {
        int off2 = off + 1;
        out[off] = (byte) (x >>> 24);
        int off3 = off2 + 1;
        out[off2] = (byte) (x >>> 16);
        out[off3] = (byte) (x >>> 8);
        out[off3 + 1] = (byte) x;
    }

    private int l(int x, int y) {
        return (x << y) | (x >>> (32 - y));
    }
}
