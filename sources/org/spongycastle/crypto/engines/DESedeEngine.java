package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class DESedeEngine extends DESEngine {
    private int[] o = null;
    private int[] p = null;
    private int[] q = null;
    private boolean r;

    public void a(boolean encrypting, CipherParameters params) {
        if (params instanceof KeyParameter) {
            byte[] keyMaster = ((KeyParameter) params).a();
            if (keyMaster.length == 24 || keyMaster.length == 16) {
                this.r = encrypting;
                byte[] key1 = new byte[8];
                System.arraycopy(keyMaster, 0, key1, 0, key1.length);
                this.o = e(encrypting, key1);
                byte[] key2 = new byte[8];
                System.arraycopy(keyMaster, 8, key2, 0, key2.length);
                this.p = e(!encrypting, key2);
                if (keyMaster.length == 24) {
                    byte[] key3 = new byte[8];
                    System.arraycopy(keyMaster, 16, key3, 0, key3.length);
                    this.q = e(encrypting, key3);
                    return;
                }
                this.q = this.o;
                return;
            }
            throw new IllegalArgumentException("key size must be 16 or 24 bytes.");
        }
        throw new IllegalArgumentException("invalid parameter passed to DESede init - " + params.getClass().getName());
    }

    public String b() {
        return "DESede";
    }

    public int c() {
        return 8;
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        int[] iArr = this.o;
        if (iArr == null) {
            throw new IllegalStateException("DESede engine not initialised");
        } else if (inOff + 8 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + 8 <= out.length) {
            byte[] temp = new byte[8];
            if (this.r) {
                byte[] bArr = temp;
                d(iArr, in, inOff, bArr, 0);
                byte[] bArr2 = temp;
                d(this.p, bArr2, 0, bArr, 0);
                d(this.q, bArr2, 0, out, outOff);
            } else {
                byte[] bArr3 = temp;
                d(this.q, in, inOff, bArr3, 0);
                byte[] bArr4 = temp;
                d(this.p, bArr4, 0, bArr3, 0);
                d(this.o, bArr4, 0, out, outOff);
            }
            return 8;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
    }
}
