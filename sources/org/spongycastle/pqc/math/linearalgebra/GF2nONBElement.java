package org.spongycastle.pqc.math.linearalgebra;

import android.support.v4.media.session.PlaybackStateCompat;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class GF2nONBElement extends GF2nElement {
    private static final long[] c = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, PlaybackStateCompat.ACTION_SET_REPEAT_MODE, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED, 1048576, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, 268435456, IjkMediaMeta.AV_CH_STEREO_LEFT, IjkMediaMeta.AV_CH_STEREO_RIGHT, IjkMediaMeta.AV_CH_WIDE_LEFT, IjkMediaMeta.AV_CH_WIDE_RIGHT, IjkMediaMeta.AV_CH_SURROUND_DIRECT_LEFT, IjkMediaMeta.AV_CH_SURROUND_DIRECT_RIGHT, IjkMediaMeta.AV_CH_LOW_FREQUENCY_2, 68719476736L, 137438953472L, 274877906944L, 549755813888L, 1099511627776L, 2199023255552L, 4398046511104L, 8796093022208L, 17592186044416L, 35184372088832L, 70368744177664L, 140737488355328L, 281474976710656L, 562949953421312L, 1125899906842624L, 2251799813685248L, 4503599627370496L, 9007199254740992L, 18014398509481984L, 36028797018963968L, 72057594037927936L, 144115188075855872L, 288230376151711744L, 576460752303423488L, 1152921504606846976L, 2305843009213693952L, 4611686018427387904L, Long.MIN_VALUE};
    private static final long[] d = {1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 131071, 262143, 524287, 1048575, 2097151, 4194303, 8388607, 16777215, 33554431, 67108863, 134217727, 268435455, 536870911, 1073741823, 2147483647L, 4294967295L, 8589934591L, 17179869183L, 34359738367L, 68719476735L, 137438953471L, 274877906943L, 549755813887L, 1099511627775L, 2199023255551L, 4398046511103L, 8796093022207L, 17592186044415L, 35184372088831L, 70368744177663L, 140737488355327L, 281474976710655L, 562949953421311L, 1125899906842623L, 2251799813685247L, 4503599627370495L, 9007199254740991L, 18014398509481983L, 36028797018963967L, 72057594037927935L, 144115188075855871L, 288230376151711743L, 576460752303423487L, 1152921504606846975L, 2305843009213693951L, 4611686018427387903L, DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE, -1};
    private static final int[] e = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
    private int f = ((GF2nONBField) this.a).e();
    private int g = ((GF2nONBField) this.a).d();
    private long[] h = new long[this.f];

    public GF2nONBElement(GF2nONBElement gf2n) {
        GF2nField gF2nField = gf2n.a;
        this.a = gF2nField;
        this.b = gF2nField.b();
        b(gf2n.c());
    }

    public Object clone() {
        return new GF2nONBElement(this);
    }

    private void b(long[] val) {
        System.arraycopy(val, 0, this.h, 0, this.f);
    }

    public boolean a() {
        boolean result = true;
        for (int i = 0; i < this.f && result; i++) {
            result = result && (this.h[i] & -1) == 0;
        }
        return result;
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof GF2nONBElement)) {
            return false;
        }
        GF2nONBElement otherElem = (GF2nONBElement) other;
        for (int i = 0; i < this.f; i++) {
            if (this.h[i] != otherElem.h[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return this.h.hashCode();
    }

    private long[] c() {
        long[] jArr = this.h;
        long[] result = new long[jArr.length];
        System.arraycopy(jArr, 0, result, 0, jArr.length);
        return result;
    }

    public String toString() {
        return d(16);
    }

    public String d(int radix) {
        String s;
        String s2 = "";
        long[] a = c();
        int b = this.g;
        if (radix == 2) {
            for (int j = b - 1; j >= 0; j--) {
                if ((a[a.length - 1] & (1 << j)) == 0) {
                    s2 = s2 + "0";
                } else {
                    s2 = s2 + "1";
                }
            }
            for (int i = a.length - 2; i >= 0; i--) {
                for (int j2 = 63; j2 >= 0; j2--) {
                    if ((a[i] & c[j2]) == 0) {
                        s = s2 + "0";
                    } else {
                        s = s2 + "1";
                    }
                }
            }
        } else if (radix == 16) {
            char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            for (int i2 = a.length - 1; i2 >= 0; i2--) {
                s2 = ((((((((((((((((s2 + HEX_CHARS[((int) (a[i2] >>> 60)) & 15]) + HEX_CHARS[((int) (a[i2] >>> 56)) & 15]) + HEX_CHARS[((int) (a[i2] >>> 52)) & 15]) + HEX_CHARS[((int) (a[i2] >>> 48)) & 15]) + HEX_CHARS[((int) (a[i2] >>> 44)) & 15]) + HEX_CHARS[((int) (a[i2] >>> 40)) & 15]) + HEX_CHARS[((int) (a[i2] >>> 36)) & 15]) + HEX_CHARS[((int) (a[i2] >>> 32)) & 15]) + HEX_CHARS[((int) (a[i2] >>> 28)) & 15]) + HEX_CHARS[((int) (a[i2] >>> 24)) & 15]) + HEX_CHARS[((int) (a[i2] >>> 20)) & 15]) + HEX_CHARS[((int) (a[i2] >>> 16)) & 15]) + HEX_CHARS[((int) (a[i2] >>> 12)) & 15]) + HEX_CHARS[((int) (a[i2] >>> 8)) & 15]) + HEX_CHARS[((int) (a[i2] >>> 4)) & 15]) + HEX_CHARS[((int) a[i2]) & 15]) + " ";
            }
        }
        return s2;
    }
}
