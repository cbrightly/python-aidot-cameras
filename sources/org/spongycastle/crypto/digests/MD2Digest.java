package org.spongycastle.crypto.digests;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.Constants;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.util.Memoable;

public class MD2Digest implements ExtendedDigest, Memoable {
    private static final byte[] a = {41, 46, 67, -55, -94, -40, 124, 1, 61, 54, 84, -95, -20, -16, 6, 19, 98, -89, 5, -13, -64, -57, 115, -116, -104, -109, 43, -39, -68, 76, OTACommand.RESP_ID_SEND_OTA, -54, 30, -101, 87, 60, -3, -44, -32, 22, 103, 66, 111, 24, -118, 23, -27, 18, -66, 78, -60, -42, -38, -98, -34, 73, -96, -5, -11, -114, -69, 47, -18, 122, -87, 104, 121, -111, 21, -78, 7, 63, -108, -62, MappingData.PATH, -119, 11, 34, 95, 33, OTACommand.RESP_ID_VERSION, Byte.MAX_VALUE, 93, -102, 90, -112, 50, 39, 53, 62, -52, -25, -65, -9, -105, 3, -1, 25, 48, -77, 72, -91, -75, -47, -41, 94, -110, 42, -84, 86, -86, -58, 79, -72, 56, -46, -106, -92, 125, -74, 118, -4, 107, -30, -100, 116, 4, -15, 69, -99, 112, 89, 100, 113, -121, 32, -122, 91, -49, 101, -26, 45, -88, 2, 27, 96, 37, -83, -82, -80, -71, -10, 28, 70, 97, 105, 52, 64, 126, 15, 85, 71, -93, 35, -35, 81, -81, 58, -61, 92, -7, -50, -70, -59, -22, 38, Constants.COMMA, 83, 13, 110, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 40, OTACommand.RESP_ID_END_OTA_MD5, 9, -45, -33, -51, -12, 65, OTACommand.RESP_ID_START_OTA, 77, 82, 106, -36, 55, -56, 108, -63, -85, -6, 36, -31, 123, 8, 12, -67, -79, 74, 120, -120, -107, -117, -29, 99, -24, 109, -23, -53, -43, -2, Constants.SEMI_COLON, 0, 29, 57, -14, -17, -73, 14, 102, 88, -48, -28, -90, 119, 114, -8, -21, 117, 75, 10, 49, 68, 80, -76, -113, -19, 31, 26, -37, -103, -115, 51, -97, 17, OTACommand.RESP_ID_END_OTA, 20};
    private byte[] b = new byte[48];
    private int c;
    private byte[] d = new byte[16];
    private int e;
    private byte[] f = new byte[16];
    private int g;

    public MD2Digest() {
        reset();
    }

    public MD2Digest(MD2Digest t) {
        n(t);
    }

    private void n(MD2Digest t) {
        byte[] bArr = t.b;
        System.arraycopy(bArr, 0, this.b, 0, bArr.length);
        this.c = t.c;
        byte[] bArr2 = t.d;
        System.arraycopy(bArr2, 0, this.d, 0, bArr2.length);
        this.e = t.e;
        byte[] bArr3 = t.f;
        System.arraycopy(bArr3, 0, this.f, 0, bArr3.length);
        this.g = t.g;
    }

    public String b() {
        return "MD2";
    }

    public int e() {
        return 16;
    }

    public int c(byte[] out, int outOff) {
        byte paddingByte = (byte) (this.d.length - this.e);
        int i = this.e;
        while (true) {
            byte[] bArr = this.d;
            if (i < bArr.length) {
                bArr[i] = paddingByte;
                i++;
            } else {
                p(bArr);
                o(this.d);
                o(this.f);
                System.arraycopy(this.b, this.c, out, outOff, 16);
                reset();
                return 16;
            }
        }
    }

    public void reset() {
        this.c = 0;
        int i = 0;
        while (true) {
            byte[] bArr = this.b;
            if (i == bArr.length) {
                break;
            }
            bArr[i] = 0;
            i++;
        }
        this.e = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr2 = this.d;
            if (i2 == bArr2.length) {
                break;
            }
            bArr2[i2] = 0;
            i2++;
        }
        this.g = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr3 = this.f;
            if (i3 != bArr3.length) {
                bArr3[i3] = 0;
                i3++;
            } else {
                return;
            }
        }
    }

    public void d(byte in) {
        byte[] bArr = this.d;
        int i = this.e;
        int i2 = i + 1;
        this.e = i2;
        bArr[i] = in;
        if (i2 == 16) {
            p(bArr);
            o(this.d);
            this.e = 0;
        }
    }

    public void update(byte[] in, int inOff, int len) {
        while (this.e != 0 && len > 0) {
            d(in[inOff]);
            inOff++;
            len--;
        }
        while (len > 16) {
            System.arraycopy(in, inOff, this.d, 0, 16);
            p(this.d);
            o(this.d);
            len -= 16;
            inOff += 16;
        }
        while (len > 0) {
            d(in[inOff]);
            inOff++;
            len--;
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r0v1, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r0v3, types: [byte] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void p(byte[] r7) {
        /*
            r6 = this;
            byte[] r0 = r6.f
            r1 = 15
            byte r0 = r0[r1]
            r1 = 0
        L_0x0007:
            r2 = 16
            if (r1 >= r2) goto L_0x0021
            byte[] r2 = r6.f
            byte r3 = r2[r1]
            byte[] r4 = a
            byte r5 = r7[r1]
            r5 = r5 ^ r0
            r5 = r5 & 255(0xff, float:3.57E-43)
            byte r4 = r4[r5]
            r3 = r3 ^ r4
            byte r3 = (byte) r3
            r2[r1] = r3
            byte r0 = r2[r1]
            int r1 = r1 + 1
            goto L_0x0007
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.crypto.digests.MD2Digest.p(byte[]):void");
    }

    /* access modifiers changed from: protected */
    public void o(byte[] m) {
        for (int i = 0; i < 16; i++) {
            byte[] bArr = this.b;
            bArr[i + 16] = m[i];
            bArr[i + 32] = (byte) (m[i] ^ bArr[i]);
        }
        int t = 0;
        for (int j = 0; j < 18; j++) {
            for (int k = 0; k < 48; k++) {
                byte[] bArr2 = this.b;
                byte t2 = (byte) (bArr2[k] ^ a[t]);
                bArr2[k] = t2;
                t = t2 & 255;
            }
            t = (t + j) % 256;
        }
    }

    public int k() {
        return 16;
    }

    public Memoable copy() {
        return new MD2Digest(this);
    }

    public void m(Memoable other) {
        n((MD2Digest) other);
    }
}
