package org.apache.commons.fileupload.util.mime;

import java.io.IOException;
import java.io.OutputStream;

/* compiled from: Base64Decoder */
public final class a {
    private static final byte[] a = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] b = new byte[256];

    static {
        int i = 0;
        while (true) {
            byte[] bArr = b;
            if (i >= bArr.length) {
                break;
            }
            bArr[i] = -1;
            i++;
        }
        int i2 = 0;
        while (true) {
            byte[] bArr2 = a;
            if (i2 < bArr2.length) {
                b[bArr2[i2]] = (byte) i2;
                i2++;
            } else {
                b[61] = -2;
                return;
            }
        }
    }

    public static int a(byte[] data, OutputStream out) {
        byte[] bArr = data;
        OutputStream outputStream = out;
        int outLen = 0;
        int i = 4;
        byte[] cache = new byte[4];
        int cachedBytes = 0;
        int length = bArr.length;
        int i2 = 0;
        while (i2 < length) {
            byte d = b[bArr[i2] & 255];
            if (d != -1) {
                int cachedBytes2 = cachedBytes + 1;
                cache[cachedBytes] = d;
                if (cachedBytes2 == i) {
                    byte b1 = cache[0];
                    byte b2 = cache[1];
                    byte b3 = cache[2];
                    byte b4 = cache[3];
                    if (b1 == -2 || b2 == -2) {
                        throw new IOException("Invalid Base64 input: incorrect padding, first two bytes cannot be padding");
                    }
                    outputStream.write((b1 << 2) | (b2 >> 4));
                    outLen++;
                    if (b3 != -2) {
                        outputStream.write((b2 << 4) | (b3 >> 2));
                        outLen++;
                        if (b4 != -2) {
                            outputStream.write((b3 << 6) | b4);
                            outLen++;
                        }
                    } else if (b4 != -2) {
                        throw new IOException("Invalid Base64 input: incorrect padding, 4th byte must be padding if 3rd byte is");
                    }
                    cachedBytes = 0;
                } else {
                    cachedBytes = cachedBytes2;
                }
            }
            i2++;
            i = 4;
        }
        if (cachedBytes == 0) {
            return outLen;
        }
        throw new IOException("Invalid Base64 input: truncated");
    }
}
