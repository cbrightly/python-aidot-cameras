package org.spongycastle.asn1;

import java.io.OutputStream;

public class BEROctetStringGenerator extends BERGenerator {

    public class BufferedBEROctetStream extends OutputStream {
        private byte[] c;
        private int d;
        private DEROutputStream f;
        final /* synthetic */ BEROctetStringGenerator q;

        public void write(int b) {
            byte[] bArr = this.c;
            int i = this.d;
            int i2 = i + 1;
            this.d = i2;
            bArr[i] = (byte) b;
            if (i2 == bArr.length) {
                DEROctetString.r(this.f, bArr);
                this.d = 0;
            }
        }

        public void write(byte[] b, int off, int len) {
            while (len > 0) {
                int numToCopy = Math.min(len, this.c.length - this.d);
                System.arraycopy(b, off, this.c, this.d, numToCopy);
                int i = this.d + numToCopy;
                this.d = i;
                byte[] bArr = this.c;
                if (i >= bArr.length) {
                    DEROctetString.r(this.f, bArr);
                    this.d = 0;
                    off += numToCopy;
                    len -= numToCopy;
                } else {
                    return;
                }
            }
        }

        public void close() {
            int i = this.d;
            if (i != 0) {
                byte[] bytes = new byte[i];
                System.arraycopy(this.c, 0, bytes, 0, i);
                DEROctetString.r(this.f, bytes);
            }
            this.q.a();
        }
    }
}
