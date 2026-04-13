package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class VMPCEngine implements StreamCipher {
    protected byte a = 0;
    protected byte[] b = null;
    protected byte c = 0;
    protected byte[] d;
    protected byte[] e;

    public String b() {
        return "VMPC";
    }

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof ParametersWithIV) {
            ParametersWithIV ivParams = (ParametersWithIV) params;
            if (ivParams.b() instanceof KeyParameter) {
                KeyParameter key = (KeyParameter) ivParams.b();
                byte[] a2 = ivParams.a();
                this.d = a2;
                if (a2 == null || a2.length < 1 || a2.length > 768) {
                    throw new IllegalArgumentException("VMPC requires 1 to 768 bytes of IV");
                }
                byte[] a3 = key.a();
                this.e = a3;
                c(a3, this.d);
                return;
            }
            throw new IllegalArgumentException("VMPC init parameters must include a key");
        }
        throw new IllegalArgumentException("VMPC init parameters must include an IV");
    }

    /* access modifiers changed from: protected */
    public void c(byte[] keyBytes, byte[] ivBytes) {
        this.c = 0;
        this.b = new byte[256];
        for (int i = 0; i < 256; i++) {
            this.b[i] = (byte) i;
        }
        for (int m = 0; m < 768; m++) {
            byte[] bArr = this.b;
            byte b2 = bArr[(this.c + bArr[m & 255] + keyBytes[m % keyBytes.length]) & 255];
            this.c = b2;
            byte temp = bArr[m & 255];
            bArr[m & 255] = bArr[b2 & 255];
            bArr[b2 & 255] = temp;
        }
        for (int m2 = 0; m2 < 768; m2++) {
            byte[] bArr2 = this.b;
            byte b3 = bArr2[(this.c + bArr2[m2 & 255] + ivBytes[m2 % ivBytes.length]) & 255];
            this.c = b3;
            byte temp2 = bArr2[m2 & 255];
            bArr2[m2 & 255] = bArr2[b3 & 255];
            bArr2[b3 & 255] = temp2;
        }
        this.a = 0;
    }

    public int d(byte[] in, int inOff, int len, byte[] out, int outOff) {
        if (inOff + len > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + len <= out.length) {
            for (int i = 0; i < len; i++) {
                byte[] bArr = this.b;
                byte b2 = this.c;
                byte b3 = this.a;
                byte b4 = bArr[(b2 + bArr[b3 & 255]) & 255];
                this.c = b4;
                byte z = bArr[(bArr[bArr[b4 & 255] & 255] + 1) & 255];
                byte temp = bArr[b3 & 255];
                bArr[b3 & 255] = bArr[b4 & 255];
                bArr[b4 & 255] = temp;
                this.a = (byte) ((b3 + 1) & 255);
                out[i + outOff] = (byte) (in[i + inOff] ^ z);
            }
            return len;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
        c(this.e, this.d);
    }

    public byte e(byte in) {
        byte[] bArr = this.b;
        byte b2 = this.c;
        byte b3 = this.a;
        byte b4 = bArr[(b2 + bArr[b3 & 255]) & 255];
        this.c = b4;
        byte z = bArr[(bArr[bArr[b4 & 255] & 255] + 1) & 255];
        byte temp = bArr[b3 & 255];
        bArr[b3 & 255] = bArr[b4 & 255];
        bArr[b4 & 255] = temp;
        this.a = (byte) ((b3 + 1) & 255);
        return (byte) (in ^ z);
    }
}
