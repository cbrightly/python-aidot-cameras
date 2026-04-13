package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.modes.gcm.GCMExponentiator;
import org.spongycastle.crypto.modes.gcm.GCMMultiplier;
import org.spongycastle.crypto.modes.gcm.GCMUtil;
import org.spongycastle.crypto.modes.gcm.Tables1kGCMExponentiator;
import org.spongycastle.crypto.modes.gcm.Tables8kGCMMultiplier;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;

public class GCMBlockCipher implements AEADBlockCipher {
    private BlockCipher a;
    private GCMMultiplier b;
    private GCMExponentiator c;
    private boolean d;
    private boolean e;
    private int f;
    private byte[] g;
    private byte[] h;
    private byte[] i;
    private byte[] j;
    private byte[] k;
    private byte[] l;
    private byte[] m;
    private byte[] n;
    private byte[] o;
    private byte[] p;
    private byte[] q;
    private int r;
    private int s;
    private long t;
    private byte[] u;
    private int v;
    private long w;
    private long x;

    public GCMBlockCipher(BlockCipher c2) {
        this(c2, (GCMMultiplier) null);
    }

    public GCMBlockCipher(BlockCipher c2, GCMMultiplier m2) {
        if (c2.c() == 16) {
            m2 = m2 == null ? new Tables8kGCMMultiplier() : m2;
            this.a = c2;
            this.b = m2;
            return;
        }
        throw new IllegalArgumentException("cipher required with a block size of 16.");
    }

    public BlockCipher g() {
        return this.a;
    }

    /* JADX WARNING: type inference failed for: r5v4, types: [org.spongycastle.crypto.CipherParameters] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(boolean r12, org.spongycastle.crypto.CipherParameters r13) {
        /*
            r11 = this;
            r11.d = r12
            r0 = 0
            r11.m = r0
            r1 = 1
            r11.e = r1
            r2 = 0
            boolean r3 = r13 instanceof org.spongycastle.crypto.params.AEADParameters
            r4 = 16
            if (r3 == 0) goto L_0x004c
            r3 = r13
            org.spongycastle.crypto.params.AEADParameters r3 = (org.spongycastle.crypto.params.AEADParameters) r3
            byte[] r2 = r3.d()
            byte[] r5 = r3.a()
            r11.i = r5
            int r5 = r3.c()
            r6 = 32
            if (r5 < r6) goto L_0x0035
            r6 = 128(0x80, float:1.794E-43)
            if (r5 > r6) goto L_0x0035
            int r6 = r5 % 8
            if (r6 != 0) goto L_0x0035
            int r6 = r5 / 8
            r11.f = r6
            org.spongycastle.crypto.params.KeyParameter r3 = r3.b()
            goto L_0x0063
        L_0x0035:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = "Invalid value for MAC size: "
            r1.append(r4)
            r1.append(r5)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x004c:
            boolean r3 = r13 instanceof org.spongycastle.crypto.params.ParametersWithIV
            if (r3 == 0) goto L_0x0134
            r3 = r13
            org.spongycastle.crypto.params.ParametersWithIV r3 = (org.spongycastle.crypto.params.ParametersWithIV) r3
            byte[] r2 = r3.a()
            r11.i = r0
            r11.f = r4
            org.spongycastle.crypto.CipherParameters r5 = r3.b()
            r3 = r5
            org.spongycastle.crypto.params.KeyParameter r3 = (org.spongycastle.crypto.params.KeyParameter) r3
        L_0x0063:
            if (r12 == 0) goto L_0x0067
            r5 = r4
            goto L_0x006a
        L_0x0067:
            int r5 = r11.f
            int r5 = r5 + r4
        L_0x006a:
            byte[] r6 = new byte[r5]
            r11.l = r6
            if (r2 == 0) goto L_0x012c
            int r6 = r2.length
            if (r6 < r1) goto L_0x012c
            if (r12 == 0) goto L_0x009e
            byte[] r6 = r11.h
            if (r6 == 0) goto L_0x009e
            boolean r6 = org.spongycastle.util.Arrays.b(r6, r2)
            if (r6 == 0) goto L_0x009e
            java.lang.String r6 = "cannot reuse nonce for GCM encryption"
            if (r3 == 0) goto L_0x0098
            byte[] r7 = r11.g
            if (r7 == 0) goto L_0x009e
            byte[] r8 = r3.a()
            boolean r7 = org.spongycastle.util.Arrays.b(r7, r8)
            if (r7 != 0) goto L_0x0092
            goto L_0x009e
        L_0x0092:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>(r6)
            throw r0
        L_0x0098:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>(r6)
            throw r0
        L_0x009e:
            r11.h = r2
            if (r3 == 0) goto L_0x00a8
            byte[] r6 = r3.a()
            r11.g = r6
        L_0x00a8:
            r6 = 0
            if (r3 == 0) goto L_0x00c3
            org.spongycastle.crypto.BlockCipher r7 = r11.a
            r7.a(r1, r3)
            byte[] r7 = new byte[r4]
            r11.j = r7
            org.spongycastle.crypto.BlockCipher r8 = r11.a
            r8.f(r7, r6, r7, r6)
            org.spongycastle.crypto.modes.gcm.GCMMultiplier r7 = r11.b
            byte[] r8 = r11.j
            r7.a(r8)
            r11.c = r0
            goto L_0x00c7
        L_0x00c3:
            byte[] r0 = r11.j
            if (r0 == 0) goto L_0x0124
        L_0x00c7:
            byte[] r0 = new byte[r4]
            r11.k = r0
            byte[] r7 = r11.h
            int r8 = r7.length
            r9 = 12
            if (r8 != r9) goto L_0x00dd
            int r8 = r7.length
            java.lang.System.arraycopy(r7, r6, r0, r6, r8)
            byte[] r0 = r11.k
            r7 = 15
            r0[r7] = r1
            goto L_0x00f4
        L_0x00dd:
            int r1 = r7.length
            r11.l(r0, r7, r1)
            byte[] r0 = new byte[r4]
            byte[] r1 = r11.h
            int r1 = r1.length
            long r7 = (long) r1
            r9 = 8
            long r7 = r7 * r9
            r1 = 8
            org.spongycastle.util.Pack.p(r7, r0, r1)
            byte[] r1 = r11.k
            r11.m(r1, r0)
        L_0x00f4:
            byte[] r0 = new byte[r4]
            r11.n = r0
            byte[] r0 = new byte[r4]
            r11.o = r0
            byte[] r0 = new byte[r4]
            r11.p = r0
            byte[] r0 = new byte[r4]
            r11.u = r0
            r11.v = r6
            r0 = 0
            r11.w = r0
            r11.x = r0
            byte[] r4 = r11.k
            byte[] r4 = org.spongycastle.util.Arrays.h(r4)
            r11.q = r4
            r4 = -2
            r11.r = r4
            r11.s = r6
            r11.t = r0
            byte[] r0 = r11.i
            if (r0 == 0) goto L_0x0123
            int r1 = r0.length
            r11.i(r0, r6, r1)
        L_0x0123:
            return
        L_0x0124:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Key must be specified in initial init"
            r0.<init>(r1)
            throw r0
        L_0x012c:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "IV must be at least 1 byte"
            r0.<init>(r1)
            throw r0
        L_0x0134:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "invalid parameters passed to GCM"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.crypto.modes.GCMBlockCipher.a(boolean, org.spongycastle.crypto.CipherParameters):void");
    }

    public byte[] h() {
        byte[] bArr = this.m;
        if (bArr == null) {
            return new byte[this.f];
        }
        return Arrays.h(bArr);
    }

    public int f(int len) {
        int totalData = this.s + len;
        if (this.d) {
            return this.f + totalData;
        }
        int i2 = this.f;
        if (totalData < i2) {
            return 0;
        }
        return totalData - i2;
    }

    public int e(int len) {
        int totalData = this.s + len;
        if (!this.d) {
            int i2 = this.f;
            if (totalData < i2) {
                return 0;
            }
            totalData -= i2;
        }
        return totalData - (totalData % 16);
    }

    public void r(byte in) {
        b();
        byte[] bArr = this.u;
        int i2 = this.v;
        bArr[i2] = in;
        int i3 = i2 + 1;
        this.v = i3;
        if (i3 == 16) {
            m(this.o, bArr);
            this.v = 0;
            this.w += 16;
        }
    }

    public void i(byte[] in, int inOff, int len) {
        b();
        for (int i2 = 0; i2 < len; i2++) {
            byte[] bArr = this.u;
            int i3 = this.v;
            bArr[i3] = in[inOff + i2];
            int i4 = i3 + 1;
            this.v = i4;
            if (i4 == 16) {
                m(this.o, bArr);
                this.v = 0;
                this.w += 16;
            }
        }
    }

    private void p() {
        if (this.w > 0) {
            System.arraycopy(this.o, 0, this.p, 0, 16);
            this.x = this.w;
        }
        int i2 = this.v;
        if (i2 > 0) {
            n(this.p, this.u, 0, i2);
            this.x += (long) this.v;
        }
        if (this.x > 0) {
            System.arraycopy(this.p, 0, this.n, 0, 16);
        }
    }

    public int d(byte[] in, int inOff, int len, byte[] out, int outOff) {
        b();
        if (in.length >= inOff + len) {
            int resultLen = 0;
            for (int i2 = 0; i2 < len; i2++) {
                byte[] bArr = this.l;
                int i3 = this.s;
                bArr[i3] = in[inOff + i2];
                int i4 = i3 + 1;
                this.s = i4;
                if (i4 == bArr.length) {
                    q(out, outOff + resultLen);
                    resultLen += 16;
                }
            }
            return resultLen;
        }
        throw new DataLengthException("Input buffer too short");
    }

    private void q(byte[] output, int offset) {
        if (output.length >= offset + 16) {
            if (this.t == 0) {
                p();
            }
            j(this.l, output, offset);
            if (this.d) {
                this.s = 0;
                return;
            }
            byte[] bArr = this.l;
            System.arraycopy(bArr, 16, bArr, 0, this.f);
            this.s = this.f;
            return;
        }
        throw new OutputLengthException("Output buffer too short");
    }

    public int c(byte[] out, int outOff) {
        b();
        if (this.t == 0) {
            p();
        }
        int extra = this.s;
        if (!this.d) {
            int i2 = this.f;
            if (extra >= i2) {
                extra -= i2;
                if (out.length < outOff + extra) {
                    throw new OutputLengthException("Output buffer too short");
                }
            } else {
                throw new InvalidCipherTextException("data too short");
            }
        } else if (out.length < outOff + extra + this.f) {
            throw new OutputLengthException("Output buffer too short");
        }
        if (extra > 0) {
            k(this.l, 0, extra, out, outOff);
        }
        long j2 = this.w;
        int i3 = this.v;
        long j3 = j2 + ((long) i3);
        this.w = j3;
        if (j3 > this.x) {
            if (i3 > 0) {
                n(this.o, this.u, 0, i3);
            }
            if (this.x > 0) {
                GCMUtil.l(this.o, this.p);
            }
            long c2 = ((this.t * 8) + 127) >>> 7;
            byte[] H_c = new byte[16];
            if (this.c == null) {
                Tables1kGCMExponentiator tables1kGCMExponentiator = new Tables1kGCMExponentiator();
                this.c = tables1kGCMExponentiator;
                tables1kGCMExponentiator.a(this.j);
            }
            this.c.b(c2, H_c);
            GCMUtil.e(this.o, H_c);
            GCMUtil.l(this.n, this.o);
        }
        byte[] X = new byte[16];
        Pack.p(this.w * 8, X, 0);
        Pack.p(this.t * 8, X, 8);
        m(this.n, X);
        byte[] tag = new byte[16];
        this.a.f(this.k, 0, tag, 0);
        GCMUtil.l(tag, this.n);
        int resultLen = extra;
        int i4 = this.f;
        byte[] bArr = new byte[i4];
        this.m = bArr;
        System.arraycopy(tag, 0, bArr, 0, i4);
        if (this.d) {
            System.arraycopy(this.m, 0, out, this.s + outOff, this.f);
            resultLen += this.f;
        } else {
            int i5 = this.f;
            byte[] msgMac = new byte[i5];
            System.arraycopy(this.l, extra, msgMac, 0, i5);
            if (!Arrays.u(this.m, msgMac)) {
                throw new InvalidCipherTextException("mac check in GCM failed");
            }
        }
        t(false);
        return resultLen;
    }

    public void s() {
        t(true);
    }

    private void t(boolean clearMac) {
        this.a.reset();
        this.n = new byte[16];
        this.o = new byte[16];
        this.p = new byte[16];
        this.u = new byte[16];
        this.v = 0;
        this.w = 0;
        this.x = 0;
        this.q = Arrays.h(this.k);
        this.r = -2;
        this.s = 0;
        this.t = 0;
        byte[] bArr = this.l;
        if (bArr != null) {
            Arrays.F(bArr, (byte) 0);
        }
        if (clearMac) {
            this.m = null;
        }
        if (this.d) {
            this.e = false;
            return;
        }
        byte[] bArr2 = this.i;
        if (bArr2 != null) {
            i(bArr2, 0, bArr2.length);
        }
    }

    private void j(byte[] block, byte[] out, int outOff) {
        byte[] tmp = o();
        GCMUtil.l(tmp, block);
        System.arraycopy(tmp, 0, out, outOff, 16);
        m(this.n, this.d ? tmp : block);
        this.t += 16;
    }

    private void k(byte[] buf, int off, int len, byte[] out, int outOff) {
        byte[] tmp = o();
        GCMUtil.m(tmp, buf, off, len);
        System.arraycopy(tmp, 0, out, outOff, len);
        n(this.n, this.d ? tmp : buf, 0, len);
        this.t += (long) len;
    }

    private void l(byte[] Y, byte[] b2, int len) {
        for (int pos = 0; pos < len; pos += 16) {
            n(Y, b2, pos, Math.min(len - pos, 16));
        }
    }

    private void m(byte[] Y, byte[] b2) {
        GCMUtil.l(Y, b2);
        this.b.b(Y);
    }

    private void n(byte[] Y, byte[] b2, int off, int len) {
        GCMUtil.m(Y, b2, off, len);
        this.b.b(Y);
    }

    private byte[] o() {
        int i2 = this.r;
        if (i2 != 0) {
            this.r = i2 - 1;
            byte[] bArr = this.q;
            int c2 = 1 + (bArr[15] & 255);
            bArr[15] = (byte) c2;
            int c3 = (c2 >>> 8) + (bArr[14] & 255);
            bArr[14] = (byte) c3;
            int c4 = (c3 >>> 8) + (bArr[13] & 255);
            bArr[13] = (byte) c4;
            bArr[12] = (byte) ((c4 >>> 8) + (bArr[12] & 255));
            byte[] tmp = new byte[16];
            this.a.f(bArr, 0, tmp, 0);
            return tmp;
        }
        throw new IllegalStateException("Attempt to process too many blocks");
    }

    private void b() {
        if (this.e) {
            return;
        }
        if (this.d) {
            throw new IllegalStateException("GCM cipher cannot be reused for encryption");
        }
        throw new IllegalStateException("GCM cipher needs to be initialised");
    }
}
