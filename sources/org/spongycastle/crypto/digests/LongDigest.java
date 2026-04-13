package org.spongycastle.crypto.digests;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.util.Memoable;
import org.spongycastle.util.Pack;

public abstract class LongDigest implements ExtendedDigest, Memoable, EncodableDigest {
    static final long[] a = {4794697086780616226L, 8158064640168781261L, -5349999486874862801L, -1606136188198331460L, 4131703408338449720L, 6480981068601479193L, -7908458776815382629L, -6116909921290321640L, -2880145864133508542L, 1334009975649890238L, 2608012711638119052L, 6128411473006802146L, 8268148722764581231L, -9160688886553864527L, -7215885187991268811L, -4495734319001033068L, -1973867731355612462L, -1171420211273849373L, 1135362057144423861L, 2597628984639134821L, 3308224258029322869L, 5365058923640841347L, 6679025012923562964L, 8573033837759648693L, -7476448914759557205L, -6327057829258317296L, -5763719355590565569L, -4658551843659510044L, -4116276920077217854L, -3051310485924567259L, 489312712824947311L, 1452737877330783856L, 2861767655752347644L, 3322285676063803686L, 5560940570517711597L, 5996557281743188959L, 7280758554555802590L, 8532644243296465576L, -9096487096722542874L, -7894198246740708037L, -6719396339535248540L, -6333637450476146687L, -4446306890439682159L, -4076793802049405392L, -3345356375505022440L, -2983346525034927856L, -860691631967231958L, 1182934255886127544L, 1847814050463011016L, 2177327727835720531L, 2830643537854262169L, 3796741975233480872L, 4115178125766777443L, 5681478168544905931L, 6601373596472566643L, 7507060721942968483L, 8399075790359081724L, 8693463985226723168L, -8878714635349349518L, -8302665154208450068L, -8016688836872298968L, -6606660893046293015L, -4685533653050689259L, -4147400797238176981L, -3880063495543823972L, -3348786107499101689L, -1523767162380948706L, -757361751448694408L, 500013540394364858L, 748580250866718886L, 1242879168328830382L, 1977374033974150939L, 2944078676154940804L, 3659926193048069267L, 4368137639120453308L, 4836135668995329356L, 5532061633213252278L, 6448918945643986474L, 6902733635092675308L, 7801388544844847127L};
    private byte[] b;
    private int c;
    private long d;
    private long e;
    protected long f;
    protected long g;
    protected long h;
    protected long i;
    protected long j;
    protected long k;
    protected long l;
    protected long m;
    private long[] n;
    private int o;

    protected LongDigest() {
        this.b = new byte[8];
        this.n = new long[80];
        this.c = 0;
        reset();
    }

    protected LongDigest(LongDigest t) {
        this.b = new byte[8];
        this.n = new long[80];
        u(t);
    }

    /* access modifiers changed from: protected */
    public void u(LongDigest t) {
        byte[] bArr = t.b;
        System.arraycopy(bArr, 0, this.b, 0, bArr.length);
        this.c = t.c;
        this.d = t.d;
        this.e = t.e;
        this.f = t.f;
        this.g = t.g;
        this.h = t.h;
        this.i = t.i;
        this.j = t.j;
        this.k = t.k;
        this.l = t.l;
        this.m = t.m;
        long[] jArr = t.n;
        System.arraycopy(jArr, 0, this.n, 0, jArr.length);
        this.o = t.o;
    }

    public void d(byte in) {
        byte[] bArr = this.b;
        int i2 = this.c;
        int i3 = i2 + 1;
        this.c = i3;
        bArr[i2] = in;
        if (i3 == bArr.length) {
            y(bArr, 0);
            this.c = 0;
        }
        this.d++;
    }

    public void update(byte[] in, int inOff, int len) {
        while (this.c != 0 && len > 0) {
            d(in[inOff]);
            inOff++;
            len--;
        }
        while (len > this.b.length) {
            y(in, inOff);
            byte[] bArr = this.b;
            inOff += bArr.length;
            len -= bArr.length;
            this.d += (long) bArr.length;
        }
        while (len > 0) {
            d(in[inOff]);
            inOff++;
            len--;
        }
    }

    public void v() {
        t();
        long lowBitLength = this.d << 3;
        long hiBitLength = this.e;
        d(OTACommand.RESP_ID_VERSION);
        while (this.c != 0) {
            d((byte) 0);
        }
        x(lowBitLength, hiBitLength);
        w();
    }

    public void reset() {
        this.d = 0;
        this.e = 0;
        this.c = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.b;
            if (i2 >= bArr.length) {
                break;
            }
            bArr[i2] = 0;
            i2++;
        }
        this.o = 0;
        int i3 = 0;
        while (true) {
            long[] jArr = this.n;
            if (i3 != jArr.length) {
                jArr[i3] = 0;
                i3++;
            } else {
                return;
            }
        }
    }

    public int k() {
        return 128;
    }

    /* access modifiers changed from: protected */
    public void y(byte[] in, int inOff) {
        this.n[this.o] = Pack.c(in, inOff);
        int i2 = this.o + 1;
        this.o = i2;
        if (i2 == 16) {
            w();
        }
    }

    private void t() {
        long j2 = this.d;
        if (j2 > 2305843009213693951L) {
            this.e += j2 >>> 61;
            this.d = j2 & 2305843009213693951L;
        }
    }

    /* access modifiers changed from: protected */
    public void x(long lowW, long hiW) {
        if (this.o > 14) {
            w();
        }
        long[] jArr = this.n;
        jArr[14] = hiW;
        jArr[15] = lowW;
    }

    /* access modifiers changed from: protected */
    public void w() {
        t();
        for (int t = 16; t <= 79; t++) {
            long[] jArr = this.n;
            long q = q(jArr[t - 2]);
            long[] jArr2 = this.n;
            jArr[t] = q + jArr2[t - 7] + p(jArr2[t - 15]) + this.n[t - 16];
        }
        long a2 = this.f;
        long b2 = this.g;
        long c2 = this.h;
        long d2 = this.i;
        long e2 = this.j;
        long f2 = this.k;
        long g2 = this.l;
        long g3 = b2;
        long h2 = c2;
        int i2 = 0;
        int t2 = 0;
        long d3 = d2;
        long e3 = e2;
        long b3 = a2;
        long e4 = this.m;
        while (i2 < 10) {
            int i3 = i2;
            long e5 = e3;
            long[] jArr3 = a;
            int t3 = t2 + 1;
            long h3 = e4 + s(e3) + n(e3, f2, g2) + jArr3[t2] + this.n[t2];
            long j2 = g3;
            long b4 = g3;
            long b5 = d3 + h3;
            long h4 = h3 + r(b3) + o(b3, j2, h2);
            int t4 = t3 + 1;
            long g4 = g2 + s(b5) + n(b5, e5, f2) + jArr3[t3] + this.n[t3];
            long c3 = h2 + g4;
            long j3 = b5;
            long d4 = b5;
            long d5 = g4 + r(h4) + o(h4, b3, b4);
            int t5 = t4 + 1;
            long f3 = f2 + s(c3) + n(c3, j3, e5) + jArr3[t4] + this.n[t4];
            long j4 = h4;
            long h5 = h4;
            long h6 = b4 + f3;
            long f4 = f3 + r(d5) + o(d5, j4, b3);
            long j5 = c3;
            long c4 = c3;
            long c5 = f4;
            int t6 = t5 + 1;
            long e6 = e5 + s(h6) + n(h6, j5, d4) + jArr3[t5] + this.n[t5];
            long a3 = b3 + e6;
            long j6 = h6;
            long b6 = h6;
            long b7 = e6 + r(c5) + o(c5, d5, h5);
            int t7 = t6 + 1;
            long d6 = d4 + s(a3) + n(a3, j6, c4) + jArr3[t6] + this.n[t6];
            long j7 = c5;
            long f5 = c5;
            long h7 = h5 + d6;
            long d7 = d6 + r(b7) + o(b7, j7, d5);
            long j8 = a3;
            long a4 = a3;
            long a5 = d7;
            int t8 = t7 + 1;
            long c6 = c4 + s(h7) + n(h7, j8, b6) + jArr3[t7] + this.n[t7];
            long g5 = d5 + c6;
            long j9 = h7;
            long h8 = h7;
            h2 = c6 + r(a5) + o(a5, b7, f5);
            int t9 = t8 + 1;
            long b8 = b6 + s(g5) + n(g5, j9, a4) + jArr3[t8] + this.n[t8];
            long f6 = f5 + b8;
            long j10 = a5;
            long d8 = a5;
            long d9 = f6;
            long b9 = b8 + r(h2) + o(h2, j10, b7);
            long b10 = s(d9);
            long f7 = d9;
            long b11 = b9;
            int t10 = t9 + 1;
            long a6 = a4 + b10 + n(d9, g5, h8) + jArr3[t9] + this.n[t9];
            long a7 = a6 + r(b11) + o(b11, h2, d8);
            i2 = i3 + 1;
            e3 = b7 + a6;
            g2 = g5;
            t2 = t10;
            e4 = h8;
            g3 = b11;
            f2 = f7;
            d3 = d8;
            b3 = a7;
        }
        int i4 = i2;
        this.f += b3;
        this.g += g3;
        this.h += h2;
        this.i += d3;
        this.j += e3;
        this.k += f2;
        this.l += g2;
        this.m += e4;
        this.o = 0;
        for (int i5 = 0; i5 < 16; i5++) {
            this.n[i5] = 0;
        }
    }

    private long n(long x, long y, long z) {
        return (x & y) ^ ((~x) & z);
    }

    private long o(long x, long y, long z) {
        return ((x & y) ^ (x & z)) ^ (y & z);
    }

    private long r(long x) {
        return (((x << 36) | (x >>> 28)) ^ ((x << 30) | (x >>> 34))) ^ ((x << 25) | (x >>> 39));
    }

    private long s(long x) {
        return (((x << 50) | (x >>> 14)) ^ ((x << 46) | (x >>> 18))) ^ ((x << 23) | (x >>> 41));
    }

    private long p(long x) {
        return (((x << 63) | (x >>> 1)) ^ ((x << 56) | (x >>> 8))) ^ (x >>> 7);
    }

    private long q(long x) {
        return (((x << 45) | (x >>> 19)) ^ ((x << 3) | (x >>> 61))) ^ (x >>> 6);
    }
}
