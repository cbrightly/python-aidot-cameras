package org.spongycastle.crypto.agreement.kdf;

import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KDFParameters;

public class ConcatenationKDFGenerator implements DerivationFunction {
    private Digest a;
    private byte[] b;
    private byte[] c;
    private int d;

    public ConcatenationKDFGenerator(Digest digest) {
        this.a = digest;
        this.d = digest.e();
    }

    public void b(DerivationParameters param) {
        if (param instanceof KDFParameters) {
            KDFParameters p = (KDFParameters) param;
            this.b = p.b();
            this.c = p.a();
            return;
        }
        throw new IllegalArgumentException("KDF parameters required for generator");
    }

    private void c(int i, byte[] sp) {
        sp[0] = (byte) (i >>> 24);
        sp[1] = (byte) (i >>> 16);
        sp[2] = (byte) (i >>> 8);
        sp[3] = (byte) (i >>> 0);
    }

    public int a(byte[] out, int outOff, int len) {
        int counter;
        if (out.length - len >= outOff) {
            byte[] hashBuf = new byte[this.d];
            byte[] C = new byte[4];
            int counter2 = 1;
            int outputLen = 0;
            this.a.reset();
            if (len > this.d) {
                while (true) {
                    c(counter2, C);
                    this.a.update(C, 0, C.length);
                    Digest digest = this.a;
                    byte[] bArr = this.b;
                    digest.update(bArr, 0, bArr.length);
                    Digest digest2 = this.a;
                    byte[] bArr2 = this.c;
                    digest2.update(bArr2, 0, bArr2.length);
                    this.a.c(hashBuf, 0);
                    System.arraycopy(hashBuf, 0, out, outOff + outputLen, this.d);
                    int i = this.d;
                    outputLen += i;
                    counter = counter2 + 1;
                    if (counter2 >= len / i) {
                        break;
                    }
                    counter2 = counter;
                }
                counter2 = counter;
            }
            if (outputLen < len) {
                c(counter2, C);
                this.a.update(C, 0, C.length);
                Digest digest3 = this.a;
                byte[] bArr3 = this.b;
                digest3.update(bArr3, 0, bArr3.length);
                Digest digest4 = this.a;
                byte[] bArr4 = this.c;
                digest4.update(bArr4, 0, bArr4.length);
                this.a.c(hashBuf, 0);
                System.arraycopy(hashBuf, 0, out, outOff + outputLen, len - outputLen);
            }
            return len;
        }
        throw new OutputLengthException("output buffer too small");
    }
}
