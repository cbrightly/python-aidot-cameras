package org.spongycastle.util.encoders;

import java.io.IOException;
import java.io.OutputStream;

public class Base64Encoder implements Encoder {
    protected final byte[] a = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    protected byte b = 61;
    protected final byte[] c = new byte[128];

    /* access modifiers changed from: protected */
    public void e() {
        int i = 0;
        while (true) {
            byte[] bArr = this.c;
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
                this.c[bArr2[i2]] = (byte) i2;
                i2++;
            } else {
                return;
            }
        }
    }

    public Base64Encoder() {
        e();
    }

    public int a(byte[] data, int off, int length, OutputStream out) {
        int modulus = length % 3;
        int dataLength = length - modulus;
        for (int i = off; i < off + dataLength; i += 3) {
            int a1 = data[i] & 255;
            int a2 = data[i + 1] & 255;
            int a3 = data[i + 2] & 255;
            out.write(this.a[(a1 >>> 2) & 63]);
            out.write(this.a[((a1 << 4) | (a2 >>> 4)) & 63]);
            out.write(this.a[((a2 << 2) | (a3 >>> 6)) & 63]);
            out.write(this.a[a3 & 63]);
        }
        switch (modulus) {
            case 1:
                int d1 = data[off + dataLength] & 255;
                out.write(this.a[(d1 >>> 2) & 63]);
                out.write(this.a[(d1 << 4) & 63]);
                out.write(this.b);
                out.write(this.b);
                break;
            case 2:
                int d12 = data[off + dataLength] & 255;
                int d2 = data[off + dataLength + 1] & 255;
                out.write(this.a[(d12 >>> 2) & 63]);
                out.write(this.a[((d12 << 4) | (d2 >>> 4)) & 63]);
                out.write(this.a[(d2 << 2) & 63]);
                out.write(this.b);
                break;
        }
        int i2 = 4;
        int i3 = (dataLength / 3) * 4;
        if (modulus == 0) {
            i2 = 0;
        }
        return i3 + i2;
    }

    private boolean d(char c2) {
        return c2 == 10 || c2 == 13 || c2 == 9 || c2 == ' ';
    }

    public int b(String data, OutputStream out) {
        int length = 0;
        int end = data.length();
        while (end > 0 && d(data.charAt(end - 1))) {
            end--;
        }
        int finish = end - 4;
        int i = f(data, 0, finish);
        while (i < finish) {
            int i2 = i + 1;
            byte b1 = this.c[data.charAt(i)];
            int i3 = f(data, i2, finish);
            int i4 = i3 + 1;
            byte b2 = this.c[data.charAt(i3)];
            int i5 = f(data, i4, finish);
            int i6 = i5 + 1;
            byte b3 = this.c[data.charAt(i5)];
            int i7 = f(data, i6, finish);
            int i8 = i7 + 1;
            byte b4 = this.c[data.charAt(i7)];
            if ((b1 | b2 | b3 | b4) >= 0) {
                out.write((b1 << 2) | (b2 >> 4));
                out.write((b2 << 4) | (b3 >> 2));
                out.write((b3 << 6) | b4);
                length += 3;
                i = f(data, i8, finish);
            } else {
                throw new IOException("invalid characters encountered in base64 data");
            }
        }
        return length + c(out, data.charAt(end - 4), data.charAt(end - 3), data.charAt(end - 2), data.charAt(end - 1));
    }

    private int c(OutputStream out, char c1, char c2, char c3, char c4) {
        byte b2 = this.b;
        if (c3 == b2) {
            if (c4 == b2) {
                byte[] bArr = this.c;
                byte b1 = bArr[c1];
                byte b22 = bArr[c2];
                if ((b1 | b22) >= 0) {
                    out.write((b1 << 2) | (b22 >> 4));
                    return 1;
                }
                throw new IOException("invalid characters encountered at end of base64 data");
            }
            throw new IOException("invalid characters encountered at end of base64 data");
        } else if (c4 == b2) {
            byte[] bArr2 = this.c;
            byte b12 = bArr2[c1];
            byte b23 = bArr2[c2];
            byte b3 = bArr2[c3];
            if ((b12 | b23 | b3) >= 0) {
                out.write((b12 << 2) | (b23 >> 4));
                out.write((b23 << 4) | (b3 >> 2));
                return 2;
            }
            throw new IOException("invalid characters encountered at end of base64 data");
        } else {
            byte[] bArr3 = this.c;
            byte b13 = bArr3[c1];
            byte b24 = bArr3[c2];
            byte b32 = bArr3[c3];
            byte b4 = bArr3[c4];
            if ((b13 | b24 | b32 | b4) >= 0) {
                out.write((b13 << 2) | (b24 >> 4));
                out.write((b24 << 4) | (b32 >> 2));
                out.write((b32 << 6) | b4);
                return 3;
            }
            throw new IOException("invalid characters encountered at end of base64 data");
        }
    }

    private int f(String data, int i, int finish) {
        while (i < finish && d(data.charAt(i))) {
            i++;
        }
        return i;
    }
}
