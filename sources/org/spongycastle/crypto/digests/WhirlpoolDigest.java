package org.spongycastle.crypto.digests;

import com.alibaba.fastjson.asm.Opcodes;
import com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem;
import com.github.druk.dnssd.NSType;
import com.leedarson.serviceimpl.business.bean.OTACommand;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Memoable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public final class WhirlpoolDigest implements ExtendedDigest, Memoable {
    private static final int[] a = {24, 35, Opcodes.IFNULL, 232, 135, Opcodes.INVOKESTATIC, 1, 79, 54, 166, 210, 245, 121, 111, 145, 82, 96, 188, 155, 142, Opcodes.IF_ICMPGT, 12, 123, 53, 29, 224, 215, 194, 46, 75, 254, 87, 21, 119, 55, 229, Opcodes.IF_ICMPEQ, 240, 74, 218, 88, 201, 41, 10, Opcodes.RETURN, Opcodes.IF_ICMPNE, 107, 133, 189, 93, 16, IjkMediaMeta.FF_PROFILE_H264_HIGH_444_PREDICTIVE, KitWrapItem.TYPE_EXIT, 62, 5, 103, 228, 39, 65, NeedPermissionEvent.PER_GET_LOCATION_BLE, Opcodes.GOTO, 125, Opcodes.FCMPL, 216, NSType.IXFR, 238, 124, 102, 221, 23, 71, Opcodes.IFLE, KitWrapItem.TYPE_MODE, 45, 191, 7, 173, 90, 131, 51, 99, 2, 170, 113, 200, 25, 73, 217, 242, 227, 91, 136, Opcodes.IFNE, 38, 50, Opcodes.ARETURN, 233, 15, 213, 128, 190, 205, 52, 72, 255, 122, IjkMediaMeta.FF_PROFILE_H264_HIGH_444, 95, 32, 104, 26, 174, 180, 84, 147, 34, 100, 241, 115, 18, 64, 8, 195, 236, 219, Opcodes.IF_ICMPLT, NeedPermissionEvent.PER_ANDROID_NOTIFICATION, 61, Opcodes.DCMPL, 0, 207, 43, 118, NeedPermissionEvent.PER_IPC_ALBUM_PERM, 214, 27, Opcodes.PUTFIELD, 175, 106, 80, 69, 243, 48, 239, 63, 85, Opcodes.IF_ICMPGE, 234, 101, 186, 47, Opcodes.CHECKCAST, 222, 28, NSType.MAILB, 77, 146, 117, 6, 138, Opcodes.GETSTATIC, 230, 14, 31, 98, 212, 168, 150, NSType.TKEY, 197, 37, 89, 132, 114, 57, 76, 94, 120, 56, NeedPermissionEvent.PER_ANDROID_S_BLE, 209, Opcodes.IF_ACMPEQ, 226, 97, 179, 33, 156, 30, 67, 199, NSType.AXFR, 4, 81, Opcodes.IFEQ, 109, 13, 250, 223, 126, 36, 59, 171, 206, 17, 143, 78, Opcodes.INVOKESPECIAL, 235, 60, NeedPermissionEvent.PER_IPC_GET_SPEAK_PERM, Opcodes.LCMP, 247, Opcodes.INVOKEINTERFACE, 19, 44, 211, 231, 110, 196, 3, 86, 68, NeedPermissionEvent.PER_IPC_SPEAK_PERM, Opcodes.RET, 42, Opcodes.NEW, Opcodes.INSTANCEOF, 83, 220, 11, 157, 108, 49, 116, 246, 70, 172, NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV, 20, 225, 22, 58, 105, 9, 112, Opcodes.INVOKEVIRTUAL, 208, 237, KitWrapItem.TYPE_VERSION, 66, 152, 164, 40, 92, 248, 134};
    private static final long[] b = new long[256];
    private static final long[] c = new long[256];
    private static final long[] d = new long[256];
    private static final long[] e = new long[256];
    private static final long[] f = new long[256];
    private static final long[] g = new long[256];
    private static final long[] h = new long[256];
    private static final long[] i = new long[256];
    private static final short[] j;
    private final long[] k = new long[11];
    private byte[] l = new byte[64];
    private int m = 0;
    private short[] n = new short[32];
    private long[] o = new long[8];
    private long[] p = new long[8];
    private long[] q = new long[8];
    private long[] r = new long[8];
    private long[] s = new long[8];

    static {
        short[] sArr = new short[32];
        j = sArr;
        sArr[31] = 8;
    }

    public WhirlpoolDigest() {
        for (int i2 = 0; i2 < 256; i2++) {
            int v1 = a[i2];
            int v2 = s(v1 << 1);
            int v4 = s(v2 << 1);
            int v5 = v4 ^ v1;
            int v8 = s(v4 << 1);
            int v9 = v8 ^ v1;
            int i3 = v1;
            b[i2] = t(v1, i3, v4, v1, v8, v5, v2, v9);
            int i4 = v1;
            c[i2] = t(v9, i3, i4, v4, v1, v8, v5, v2);
            int i5 = v1;
            d[i2] = t(v2, v9, i4, i5, v4, v1, v8, v5);
            int i6 = v1;
            e[i2] = t(v5, v2, v9, i5, i6, v4, v1, v8);
            int i7 = v1;
            f[i2] = t(v8, v5, v2, v9, i6, i7, v4, v1);
            int i8 = v1;
            g[i2] = t(v1, v8, v5, v2, v9, i7, i8, v4);
            int i9 = v1;
            h[i2] = t(v4, v1, v8, v5, v2, v9, i8, i9);
            i[i2] = t(v1, v4, v1, v8, v5, v2, v9, i9);
        }
        this.k[0] = 0;
        for (int r2 = 1; r2 <= 10; r2++) {
            int i10 = (r2 - 1) * 8;
            this.k[r2] = (((((((b[i10] & -72057594037927936L) ^ (c[i10 + 1] & 71776119061217280L)) ^ (d[i10 + 2] & 280375465082880L)) ^ (e[i10 + 3] & 1095216660480L)) ^ (f[i10 + 4] & 4278190080L)) ^ (g[i10 + 5] & 16711680)) ^ (h[i10 + 6] & 65280)) ^ (i[i10 + 7] & 255);
        }
    }

    private long t(int b7, int b6, int b5, int b4, int b3, int b2, int b1, int b0) {
        return (((((((((long) b7) << 56) ^ (((long) b6) << 48)) ^ (((long) b5) << 40)) ^ (((long) b4) << 32)) ^ (((long) b3) << 24)) ^ (((long) b2) << 16)) ^ (((long) b1) << 8)) ^ ((long) b0);
    }

    private int s(int input) {
        int rv = input;
        if (((long) rv) >= 256) {
            return rv ^ 285;
        }
        return rv;
    }

    public WhirlpoolDigest(WhirlpoolDigest originalDigest) {
        m(originalDigest);
    }

    public String b() {
        return "Whirlpool";
    }

    public int e() {
        return 64;
    }

    public int c(byte[] out, int outOff) {
        q();
        for (int i2 = 0; i2 < 8; i2++) {
            o(this.o[i2], out, (i2 * 8) + outOff);
        }
        reset();
        return e();
    }

    public void reset() {
        this.m = 0;
        Arrays.H(this.n, 0);
        Arrays.F(this.l, (byte) 0);
        Arrays.G(this.o, 0);
        Arrays.G(this.p, 0);
        Arrays.G(this.q, 0);
        Arrays.G(this.r, 0);
        Arrays.G(this.s, 0);
    }

    private void v(byte[] in, int inOff) {
        for (int i2 = 0; i2 < this.s.length; i2++) {
            this.r[i2] = n(this.l, i2 * 8);
        }
        u();
        this.m = 0;
        Arrays.F(this.l, (byte) 0);
    }

    private long n(byte[] buffer, int startPos) {
        return ((((long) buffer[startPos + 0]) & 255) << 56) | ((((long) buffer[startPos + 1]) & 255) << 48) | ((((long) buffer[startPos + 2]) & 255) << 40) | ((((long) buffer[startPos + 3]) & 255) << 32) | ((((long) buffer[startPos + 4]) & 255) << 24) | ((((long) buffer[startPos + 5]) & 255) << 16) | ((((long) buffer[startPos + 6]) & 255) << 8) | (255 & ((long) buffer[startPos + 7]));
    }

    private void o(long inputLong, byte[] outputArray, int offSet) {
        for (int i2 = 0; i2 < 8; i2++) {
            outputArray[offSet + i2] = (byte) ((int) ((inputLong >> (56 - (i2 * 8))) & 255));
        }
    }

    /* access modifiers changed from: protected */
    public void u() {
        for (int i2 = 0; i2 < 8; i2++) {
            long[] jArr = this.s;
            long j2 = this.r[i2];
            long[] jArr2 = this.p;
            long j3 = this.o[i2];
            jArr2[i2] = j3;
            jArr[i2] = j2 ^ j3;
        }
        for (int round = 1; round <= 10; round++) {
            for (int i3 = 0; i3 < 8; i3++) {
                long[] jArr3 = this.q;
                jArr3[i3] = 0;
                long j4 = jArr3[i3];
                long[] jArr4 = b;
                long[] jArr5 = this.p;
                jArr3[i3] = jArr4[((int) (jArr5[(i3 + 0) & 7] >>> 56)) & 255] ^ j4;
                jArr3[i3] = jArr3[i3] ^ c[((int) (jArr5[(i3 - 1) & 7] >>> 48)) & 255];
                jArr3[i3] = jArr3[i3] ^ d[((int) (jArr5[(i3 - 2) & 7] >>> 40)) & 255];
                jArr3[i3] = jArr3[i3] ^ e[((int) (jArr5[(i3 - 3) & 7] >>> 32)) & 255];
                jArr3[i3] = jArr3[i3] ^ f[((int) (jArr5[(i3 - 4) & 7] >>> 24)) & 255];
                jArr3[i3] = jArr3[i3] ^ g[((int) (jArr5[(i3 - 5) & 7] >>> 16)) & 255];
                jArr3[i3] = jArr3[i3] ^ h[((int) (jArr5[(i3 - 6) & 7] >>> 8)) & 255];
                jArr3[i3] = jArr3[i3] ^ i[((int) jArr5[(i3 - 7) & 7]) & 255];
            }
            long[] jArr6 = this.q;
            long[] jArr7 = this.p;
            System.arraycopy(jArr6, 0, jArr7, 0, jArr7.length);
            long[] jArr8 = this.p;
            jArr8[0] = jArr8[0] ^ this.k[round];
            for (int i4 = 0; i4 < 8; i4++) {
                long[] jArr9 = this.q;
                jArr9[i4] = this.p[i4];
                long j5 = jArr9[i4];
                long[] jArr10 = b;
                long[] jArr11 = this.s;
                jArr9[i4] = j5 ^ jArr10[((int) (jArr11[(i4 + 0) & 7] >>> 56)) & 255];
                jArr9[i4] = jArr9[i4] ^ c[((int) (jArr11[(i4 - 1) & 7] >>> 48)) & 255];
                jArr9[i4] = jArr9[i4] ^ d[((int) (jArr11[(i4 - 2) & 7] >>> 40)) & 255];
                jArr9[i4] = jArr9[i4] ^ e[((int) (jArr11[(i4 - 3) & 7] >>> 32)) & 255];
                jArr9[i4] = jArr9[i4] ^ f[((int) (jArr11[(i4 - 4) & 7] >>> 24)) & 255];
                jArr9[i4] = jArr9[i4] ^ g[((int) (jArr11[(i4 - 5) & 7] >>> 16)) & 255];
                jArr9[i4] = jArr9[i4] ^ h[((int) (jArr11[(i4 - 6) & 7] >>> 8)) & 255];
                jArr9[i4] = jArr9[i4] ^ i[((int) jArr11[(i4 - 7) & 7]) & 255];
            }
            long[] jArr12 = this.q;
            long[] jArr13 = this.s;
            System.arraycopy(jArr12, 0, jArr13, 0, jArr13.length);
        }
        for (int i5 = 0; i5 < 8; i5++) {
            long[] jArr14 = this.o;
            jArr14[i5] = jArr14[i5] ^ (this.s[i5] ^ this.r[i5]);
        }
    }

    public void d(byte in) {
        byte[] bArr = this.l;
        int i2 = this.m;
        bArr[i2] = in;
        int i3 = i2 + 1;
        this.m = i3;
        if (i3 == bArr.length) {
            v(bArr, 0);
        }
        r();
    }

    private void r() {
        int carry = 0;
        for (int i2 = this.n.length - 1; i2 >= 0; i2--) {
            short[] sArr = this.n;
            int sum = (sArr[i2] & 255) + j[i2] + carry;
            carry = sum >>> 8;
            sArr[i2] = (short) (sum & 255);
        }
    }

    public void update(byte[] in, int inOff, int len) {
        while (len > 0) {
            d(in[inOff]);
            inOff++;
            len--;
        }
    }

    private void q() {
        byte[] bitLength = p();
        byte[] bArr = this.l;
        int i2 = this.m;
        int i3 = i2 + 1;
        this.m = i3;
        bArr[i2] = (byte) (bArr[i2] | OTACommand.RESP_ID_VERSION);
        if (i3 == bArr.length) {
            v(bArr, 0);
        }
        if (this.m > 32) {
            while (this.m != 0) {
                d((byte) 0);
            }
        }
        while (this.m <= 32) {
            d((byte) 0);
        }
        System.arraycopy(bitLength, 0, this.l, 32, bitLength.length);
        v(this.l, 0);
    }

    private byte[] p() {
        byte[] rv = new byte[32];
        for (int i2 = 0; i2 < rv.length; i2++) {
            rv[i2] = (byte) (this.n[i2] & 255);
        }
        return rv;
    }

    public int k() {
        return 64;
    }

    public Memoable copy() {
        return new WhirlpoolDigest(this);
    }

    public void m(Memoable other) {
        WhirlpoolDigest originalDigest = (WhirlpoolDigest) other;
        long[] jArr = originalDigest.k;
        long[] jArr2 = this.k;
        System.arraycopy(jArr, 0, jArr2, 0, jArr2.length);
        byte[] bArr = originalDigest.l;
        byte[] bArr2 = this.l;
        System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
        this.m = originalDigest.m;
        short[] sArr = originalDigest.n;
        short[] sArr2 = this.n;
        System.arraycopy(sArr, 0, sArr2, 0, sArr2.length);
        long[] jArr3 = originalDigest.o;
        long[] jArr4 = this.o;
        System.arraycopy(jArr3, 0, jArr4, 0, jArr4.length);
        long[] jArr5 = originalDigest.p;
        long[] jArr6 = this.p;
        System.arraycopy(jArr5, 0, jArr6, 0, jArr6.length);
        long[] jArr7 = originalDigest.q;
        long[] jArr8 = this.q;
        System.arraycopy(jArr7, 0, jArr8, 0, jArr8.length);
        long[] jArr9 = originalDigest.r;
        long[] jArr10 = this.r;
        System.arraycopy(jArr9, 0, jArr10, 0, jArr10.length);
        long[] jArr11 = originalDigest.s;
        long[] jArr12 = this.s;
        System.arraycopy(jArr11, 0, jArr12, 0, jArr12.length);
    }
}
