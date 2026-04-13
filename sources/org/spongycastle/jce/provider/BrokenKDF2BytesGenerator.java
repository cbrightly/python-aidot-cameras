package org.spongycastle.jce.provider;

import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KDFParameters;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class BrokenKDF2BytesGenerator implements DerivationFunction {
    private Digest a;
    private byte[] b;
    private byte[] c;

    public void b(DerivationParameters param) {
        if (param instanceof KDFParameters) {
            KDFParameters p = (KDFParameters) param;
            this.b = p.b();
            this.c = p.a();
            return;
        }
        throw new IllegalArgumentException("KDF parameters required for generator");
    }

    public int a(byte[] out, int outOff, int len) {
        if (out.length - len >= outOff) {
            long oBits = ((long) len) * 8;
            if (oBits > ((long) this.a.e()) * 8 * IjkMediaMeta.AV_CH_WIDE_LEFT) {
                new IllegalArgumentException("Output length to large");
            }
            int cThreshold = (int) (oBits / ((long) this.a.e()));
            byte[] dig = new byte[this.a.e()];
            for (int counter = 1; counter <= cThreshold; counter++) {
                Digest digest = this.a;
                byte[] bArr = this.b;
                digest.update(bArr, 0, bArr.length);
                this.a.d((byte) (counter & 255));
                this.a.d((byte) ((counter >> 8) & 255));
                this.a.d((byte) ((counter >> 16) & 255));
                this.a.d((byte) ((counter >> 24) & 255));
                Digest digest2 = this.a;
                byte[] bArr2 = this.c;
                digest2.update(bArr2, 0, bArr2.length);
                this.a.c(dig, 0);
                if (len - outOff > dig.length) {
                    System.arraycopy(dig, 0, out, outOff, dig.length);
                    outOff += dig.length;
                } else {
                    System.arraycopy(dig, 0, out, outOff, len - outOff);
                }
            }
            this.a.reset();
            return len;
        }
        throw new OutputLengthException("output buffer too small");
    }
}
