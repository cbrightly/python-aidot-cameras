package org.spongycastle.crypto.prng;

public class ReversedWindowGenerator implements RandomGenerator {
    private final RandomGenerator a;
    private byte[] b;
    private int c;

    public void a(byte[] bytes) {
        c(bytes, 0, bytes.length);
    }

    public void b(byte[] bytes, int start, int len) {
        c(bytes, start, len);
    }

    private void c(byte[] bytes, int start, int len) {
        synchronized (this) {
            for (int done = 0; done < len; done++) {
                if (this.c < 1) {
                    RandomGenerator randomGenerator = this.a;
                    byte[] bArr = this.b;
                    randomGenerator.b(bArr, 0, bArr.length);
                    this.c = this.b.length;
                }
                byte[] bArr2 = this.b;
                int i = this.c - 1;
                this.c = i;
                bytes[done + start] = bArr2[i];
            }
        }
    }
}
