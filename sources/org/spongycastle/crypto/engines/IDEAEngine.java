package org.spongycastle.crypto.engines;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.tutk.IOTC.AVIOCTRLDEFs;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class IDEAEngine implements BlockCipher {
    private int[] a = null;

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof KeyParameter) {
            this.a = h(forEncryption, ((KeyParameter) params).a());
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to IDEA init - " + params.getClass().getName());
    }

    public String b() {
        return "IDEA";
    }

    public int c() {
        return 8;
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        int[] iArr = this.a;
        if (iArr == null) {
            throw new IllegalStateException("IDEA engine not initialised");
        } else if (inOff + 8 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + 8 <= out.length) {
            i(iArr, in, inOff, out, outOff);
            return 8;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
    }

    private int e(byte[] in, int inOff) {
        return ((in[inOff] << 8) & 65280) + (in[inOff + 1] & 255);
    }

    private void m(int word, byte[] out, int outOff) {
        out[outOff] = (byte) (word >>> 8);
        out[outOff + 1] = (byte) word;
    }

    private int k(int x, int y) {
        int p;
        if (x == 0) {
            p = AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_VSAAS_PLAYBACK_INFO_RESP - y;
        } else if (y == 0) {
            p = AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_VSAAS_PLAYBACK_INFO_RESP - x;
        } else {
            int p2 = x * y;
            int y2 = p2 & 65535;
            int x2 = p2 >>> 16;
            p = (y2 - x2) + (y2 < x2 ? 1 : 0);
        }
        return p & 65535;
    }

    private void i(int[] workingKey, byte[] in, int inOff, byte[] out, int outOff) {
        byte[] bArr = in;
        int i = inOff;
        byte[] bArr2 = out;
        int i2 = outOff;
        int x0 = 0;
        int x2 = e(bArr, i);
        int x1 = e(bArr, i + 2);
        int keyOff = e(bArr, i + 4);
        int x3 = e(bArr, i + 6);
        int round = 0;
        while (round < 8) {
            int keyOff2 = x0 + 1;
            int x02 = k(x2, workingKey[x0]);
            int keyOff3 = keyOff2 + 1;
            int x12 = (x1 + workingKey[keyOff2]) & 65535;
            int keyOff4 = keyOff3 + 1;
            int x22 = (keyOff + workingKey[keyOff3]) & 65535;
            int keyOff5 = keyOff4 + 1;
            int x32 = k(x3, workingKey[keyOff4]);
            int t0 = x12;
            int t1 = x22;
            int keyOff6 = keyOff5 + 1;
            int x23 = k(x22 ^ x02, workingKey[keyOff5]);
            int x13 = k(((x12 ^ x32) + x23) & 65535, workingKey[keyOff6]);
            int x24 = (x23 + x13) & 65535;
            int x03 = x02 ^ x13;
            x3 = x32 ^ x24;
            x1 = x13 ^ t1;
            round++;
            int i3 = x24 ^ t0;
            x2 = x03;
            x0 = keyOff6 + 1;
            keyOff = i3;
        }
        int keyOff7 = x0 + 1;
        m(k(x2, workingKey[x0]), bArr2, i2);
        int keyOff8 = keyOff7 + 1;
        m(workingKey[keyOff7] + keyOff, bArr2, i2 + 2);
        m(workingKey[keyOff8] + x1, bArr2, i2 + 4);
        m(k(x3, workingKey[keyOff8 + 1]), bArr2, i2 + 6);
    }

    private int[] g(byte[] uKey) {
        int[] key = new int[52];
        if (uKey.length < 16) {
            byte[] tmp = new byte[16];
            System.arraycopy(uKey, 0, tmp, tmp.length - uKey.length, uKey.length);
            uKey = tmp;
        }
        for (int i = 0; i < 8; i++) {
            key[i] = e(uKey, i * 2);
        }
        for (int i2 = 8; i2 < 52; i2++) {
            if ((i2 & 7) < 6) {
                key[i2] = (((key[i2 - 7] & NeedPermissionEvent.PER_IPC_SPEAK_PERM) << 9) | (key[i2 - 6] >> 7)) & 65535;
            } else if ((i2 & 7) == 6) {
                key[i2] = (((key[i2 - 7] & NeedPermissionEvent.PER_IPC_SPEAK_PERM) << 9) | (key[i2 - 14] >> 7)) & 65535;
            } else {
                key[i2] = (((key[i2 - 15] & NeedPermissionEvent.PER_IPC_SPEAK_PERM) << 9) | (key[i2 - 14] >> 7)) & 65535;
            }
        }
        return key;
    }

    private int l(int x) {
        if (x < 2) {
            return x;
        }
        int t0 = 1;
        int t1 = AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_VSAAS_PLAYBACK_INFO_RESP / x;
        int y = AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_VSAAS_PLAYBACK_INFO_RESP % x;
        while (y != 1) {
            int q = x / y;
            x %= y;
            t0 = ((t1 * q) + t0) & 65535;
            if (x == 1) {
                return t0;
            }
            int q2 = y / x;
            y %= x;
            t1 = ((t0 * q2) + t1) & 65535;
        }
        return 65535 & (1 - t1);
    }

    /* access modifiers changed from: package-private */
    public int d(int x) {
        return (0 - x) & 65535;
    }

    private int[] j(int[] inKey) {
        int[] key = new int[52];
        int inOff = 0 + 1;
        int t1 = l(inKey[0]);
        int inOff2 = inOff + 1;
        int t2 = d(inKey[inOff]);
        int inOff3 = inOff2 + 1;
        int t3 = d(inKey[inOff2]);
        int inOff4 = inOff3 + 1;
        int p = 52 - 1;
        key[p] = l(inKey[inOff3]);
        int p2 = p - 1;
        key[p2] = t3;
        int p3 = p2 - 1;
        key[p3] = t2;
        int p4 = p3 - 1;
        key[p4] = t1;
        for (int round = 1; round < 8; round++) {
            int inOff5 = inOff4 + 1;
            int t12 = inKey[inOff4];
            int inOff6 = inOff5 + 1;
            int p5 = p4 - 1;
            key[p5] = inKey[inOff5];
            int p6 = p5 - 1;
            key[p6] = t12;
            int inOff7 = inOff6 + 1;
            int t13 = l(inKey[inOff6]);
            int inOff8 = inOff7 + 1;
            int t22 = d(inKey[inOff7]);
            int inOff9 = inOff8 + 1;
            int t32 = d(inKey[inOff8]);
            inOff4 = inOff9 + 1;
            int p7 = p6 - 1;
            key[p7] = l(inKey[inOff9]);
            int p8 = p7 - 1;
            key[p8] = t22;
            int p9 = p8 - 1;
            key[p9] = t32;
            p4 = p9 - 1;
            key[p4] = t13;
        }
        int inOff10 = inOff4 + 1;
        int t14 = inKey[inOff4];
        int inOff11 = inOff10 + 1;
        int p10 = p4 - 1;
        key[p10] = inKey[inOff10];
        int p11 = p10 - 1;
        key[p11] = t14;
        int inOff12 = inOff11 + 1;
        int t15 = l(inKey[inOff11]);
        int inOff13 = inOff12 + 1;
        int t23 = d(inKey[inOff12]);
        int t33 = d(inKey[inOff13]);
        int p12 = p11 - 1;
        key[p12] = l(inKey[inOff13 + 1]);
        int p13 = p12 - 1;
        key[p13] = t33;
        int p14 = p13 - 1;
        key[p14] = t23;
        key[p14 - 1] = t15;
        return key;
    }

    private int[] h(boolean forEncryption, byte[] userKey) {
        if (forEncryption) {
            return g(userKey);
        }
        return j(g(userKey));
    }
}
