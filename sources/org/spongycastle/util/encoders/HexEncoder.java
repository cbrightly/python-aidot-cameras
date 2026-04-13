package org.spongycastle.util.encoders;

import java.io.IOException;
import java.io.OutputStream;

public class HexEncoder implements Encoder {
    protected final byte[] a = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    protected final byte[] b = new byte[128];

    /* access modifiers changed from: protected */
    public void d() {
        int i = 0;
        while (true) {
            byte[] bArr = this.b;
            if (i >= bArr.length) {
                break;
            }
            bArr[i] = -1;
            i++;
        }
        int i2 = 0;
        while (true) {
            byte[] bArr2 = this.a;
            if (i2 < bArr2.length) {
                this.b[bArr2[i2]] = (byte) i2;
                i2++;
            } else {
                byte[] bArr3 = this.b;
                bArr3[65] = bArr3[97];
                bArr3[66] = bArr3[98];
                bArr3[67] = bArr3[99];
                bArr3[68] = bArr3[100];
                bArr3[69] = bArr3[101];
                bArr3[70] = bArr3[102];
                return;
            }
        }
    }

    public HexEncoder() {
        d();
    }

    public int a(byte[] data, int off, int length, OutputStream out) {
        for (int i = off; i < off + length; i++) {
            int v = data[i] & 255;
            out.write(this.a[v >>> 4]);
            out.write(this.a[v & 15]);
        }
        return length * 2;
    }

    private static boolean c(char c) {
        return c == 10 || c == 13 || c == 9 || c == ' ';
    }

    public int b(String data, OutputStream out) {
        int length = 0;
        int end = data.length();
        while (end > 0 && c(data.charAt(end - 1))) {
            end--;
        }
        int i = 0;
        while (i < end) {
            while (i < end && c(data.charAt(i))) {
                i++;
            }
            int i2 = i + 1;
            byte b1 = this.b[data.charAt(i)];
            while (i2 < end && c(data.charAt(i2))) {
                i2++;
            }
            int i3 = i2 + 1;
            byte b2 = this.b[data.charAt(i2)];
            if ((b1 | b2) >= 0) {
                out.write((b1 << 4) | b2);
                length++;
                i = i3;
            } else {
                throw new IOException("invalid characters encountered in Hex string");
            }
        }
        return length;
    }
}
