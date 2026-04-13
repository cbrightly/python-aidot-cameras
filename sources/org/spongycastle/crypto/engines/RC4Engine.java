package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;

public class RC4Engine implements StreamCipher {
    private byte[] a = null;
    private int b = 0;
    private int c = 0;
    private byte[] d = null;

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof KeyParameter) {
            byte[] a2 = ((KeyParameter) params).a();
            this.d = a2;
            c(a2);
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to RC4 init - " + params.getClass().getName());
    }

    public String b() {
        return "RC4";
    }

    public byte e(byte in) {
        int i = (this.b + 1) & 255;
        this.b = i;
        byte[] bArr = this.a;
        int i2 = (bArr[i] + this.c) & 255;
        this.c = i2;
        byte tmp = bArr[i];
        bArr[i] = bArr[i2];
        bArr[i2] = tmp;
        return (byte) (bArr[(bArr[i] + bArr[i2]) & 255] ^ in);
    }

    public int d(byte[] in, int inOff, int len, byte[] out, int outOff) {
        if (inOff + len > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + len <= out.length) {
            for (int i = 0; i < len; i++) {
                int i2 = (this.b + 1) & 255;
                this.b = i2;
                byte[] bArr = this.a;
                int i3 = (bArr[i2] + this.c) & 255;
                this.c = i3;
                byte tmp = bArr[i2];
                bArr[i2] = bArr[i3];
                bArr[i3] = tmp;
                out[i + outOff] = (byte) (bArr[(bArr[i2] + bArr[i3]) & 255] ^ in[i + inOff]);
            }
            return len;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
        c(this.d);
    }

    private void c(byte[] keyBytes) {
        this.d = keyBytes;
        this.b = 0;
        this.c = 0;
        if (this.a == null) {
            this.a = new byte[256];
        }
        for (int i = 0; i < 256; i++) {
            this.a[i] = (byte) i;
        }
        int i1 = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < 256; i3++) {
            byte[] bArr = this.a;
            i2 = ((keyBytes[i1] & 255) + bArr[i3] + i2) & 255;
            byte tmp = bArr[i3];
            bArr[i3] = bArr[i2];
            bArr[i2] = tmp;
            i1 = (i1 + 1) % keyBytes.length;
        }
    }
}
