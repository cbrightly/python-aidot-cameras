package org.spongycastle.crypto.generators;

import androidx.core.view.InputDeviceCompat;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.DigestDerivationFunction;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.ISO18033KDFParameters;
import org.spongycastle.crypto.params.KDFParameters;
import org.spongycastle.util.Pack;

public class BaseKDFBytesGenerator implements DigestDerivationFunction {
    private int a;
    private Digest b;
    private byte[] c;
    private byte[] d;

    protected BaseKDFBytesGenerator(int counterStart, Digest digest) {
        this.a = counterStart;
        this.b = digest;
    }

    public void b(DerivationParameters param) {
        if (param instanceof KDFParameters) {
            KDFParameters p = (KDFParameters) param;
            this.c = p.b();
            this.d = p.a();
        } else if (param instanceof ISO18033KDFParameters) {
            this.c = ((ISO18033KDFParameters) param).a();
            this.d = null;
        } else {
            throw new IllegalArgumentException("KDF parameters required for generator");
        }
    }

    public int a(byte[] out, int outOff, int len) {
        if (out.length - len >= outOff) {
            long oBytes = (long) len;
            int outLen = this.b.e();
            if (oBytes <= 8589934591L) {
                int cThreshold = (int) (((((long) outLen) + oBytes) - 1) / ((long) outLen));
                byte[] dig = new byte[this.b.e()];
                byte[] C = new byte[4];
                Pack.d(this.a, C, 0);
                int counterBase = this.a & InputDeviceCompat.SOURCE_ANY;
                for (int i = 0; i < cThreshold; i++) {
                    Digest digest = this.b;
                    byte[] bArr = this.c;
                    digest.update(bArr, 0, bArr.length);
                    this.b.update(C, 0, C.length);
                    byte[] bArr2 = this.d;
                    if (bArr2 != null) {
                        this.b.update(bArr2, 0, bArr2.length);
                    }
                    this.b.c(dig, 0);
                    if (len > outLen) {
                        System.arraycopy(dig, 0, out, outOff, outLen);
                        outOff += outLen;
                        len -= outLen;
                    } else {
                        System.arraycopy(dig, 0, out, outOff, len);
                    }
                    byte b2 = (byte) (C[3] + 1);
                    C[3] = b2;
                    if (b2 == 0) {
                        counterBase += 256;
                        Pack.d(counterBase, C, 0);
                    }
                }
                this.b.reset();
                return (int) oBytes;
            }
            throw new IllegalArgumentException("Output length too large");
        }
        throw new OutputLengthException("output buffer too small");
    }
}
