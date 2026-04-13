package org.spongycastle.util.io;

import java.io.OutputStream;
import org.spongycastle.util.Arrays;

public class BufferingOutputStream extends OutputStream {
    private final OutputStream c;
    private final byte[] d;
    private int f;

    public void write(byte[] bytes, int offset, int len) {
        byte[] bArr;
        byte[] bArr2 = this.d;
        int length = bArr2.length;
        int i = this.f;
        if (len < length - i) {
            System.arraycopy(bytes, offset, bArr2, i, len);
            this.f += len;
            return;
        }
        int gap = bArr2.length - i;
        System.arraycopy(bytes, offset, bArr2, i, gap);
        this.f += gap;
        flush();
        int offset2 = offset + gap;
        int len2 = len - gap;
        while (true) {
            bArr = this.d;
            if (len2 < bArr.length) {
                break;
            }
            this.c.write(bytes, offset2, bArr.length);
            byte[] bArr3 = this.d;
            offset2 += bArr3.length;
            len2 -= bArr3.length;
        }
        if (len2 > 0) {
            System.arraycopy(bytes, offset2, bArr, this.f, len2);
            this.f += len2;
        }
    }

    public void write(int b) {
        byte[] bArr = this.d;
        int i = this.f;
        int i2 = i + 1;
        this.f = i2;
        bArr[i] = (byte) b;
        if (i2 == bArr.length) {
            flush();
        }
    }

    public void flush() {
        this.c.write(this.d, 0, this.f);
        this.f = 0;
        Arrays.F(this.d, (byte) 0);
    }

    public void close() {
        flush();
        this.c.close();
    }
}
