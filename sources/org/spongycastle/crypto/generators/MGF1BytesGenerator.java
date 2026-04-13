package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.MGFParameters;

public class MGF1BytesGenerator implements DerivationFunction {
    private Digest a;
    private byte[] b;
    private int c;

    public void b(DerivationParameters param) {
        if (param instanceof MGFParameters) {
            this.b = ((MGFParameters) param).a();
            return;
        }
        throw new IllegalArgumentException("MGF parameters required for MGF1Generator");
    }

    private void c(int i, byte[] sp) {
        sp[0] = (byte) (i >>> 24);
        sp[1] = (byte) (i >>> 16);
        sp[2] = (byte) (i >>> 8);
        sp[3] = (byte) (i >>> 0);
    }

    public int a(byte[] out, int outOff, int len) {
        if (out.length - len >= outOff) {
            byte[] hashBuf = new byte[this.c];
            byte[] C = new byte[4];
            int counter = 0;
            this.a.reset();
            if (len > this.c) {
                do {
                    c(counter, C);
                    Digest digest = this.a;
                    byte[] bArr = this.b;
                    digest.update(bArr, 0, bArr.length);
                    this.a.update(C, 0, C.length);
                    this.a.c(hashBuf, 0);
                    int i = this.c;
                    System.arraycopy(hashBuf, 0, out, (counter * i) + outOff, i);
                    counter++;
                } while (counter < len / this.c);
            }
            if (this.c * counter < len) {
                c(counter, C);
                Digest digest2 = this.a;
                byte[] bArr2 = this.b;
                digest2.update(bArr2, 0, bArr2.length);
                this.a.update(C, 0, C.length);
                this.a.c(hashBuf, 0);
                int i2 = this.c;
                System.arraycopy(hashBuf, 0, out, (counter * i2) + outOff, len - (i2 * counter));
            }
            return len;
        }
        throw new OutputLengthException("output buffer too small");
    }
}
