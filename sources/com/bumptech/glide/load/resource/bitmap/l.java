package com.bumptech.glide.load.resource.bitmap;

import android.os.Build;
import com.bumptech.glide.load.h;

/* compiled from: DownsampleStrategy */
public abstract class l {
    public static final l a = new a();
    public static final l b = new b();
    public static final l c = new e();
    public static final l d = new c();
    public static final l e;
    public static final l f = new f();
    public static final l g;
    public static final h<l> h;
    static final boolean i = (Build.VERSION.SDK_INT >= 19);

    /* compiled from: DownsampleStrategy */
    public enum g {
        MEMORY,
        QUALITY
    }

    public abstract g a(int i2, int i3, int i4, int i5);

    public abstract float b(int i2, int i3, int i4, int i5);

    static {
        d dVar = new d();
        e = dVar;
        g = dVar;
        h = h.f("com.bumptech.glide.load.resource.bitmap.Downsampler.DownsampleStrategy", dVar);
    }

    /* compiled from: DownsampleStrategy */
    public static class e extends l {
        e() {
        }

        public float b(int sourceWidth, int sourceHeight, int requestedWidth, int requestedHeight) {
            if (l.i) {
                return Math.min(((float) requestedWidth) / ((float) sourceWidth), ((float) requestedHeight) / ((float) sourceHeight));
            }
            int maxIntegerFactor = Math.max(sourceHeight / requestedHeight, sourceWidth / requestedWidth);
            if (maxIntegerFactor == 0) {
                return 1.0f;
            }
            return 1.0f / ((float) Integer.highestOneBit(maxIntegerFactor));
        }

        public g a(int sourceWidth, int sourceHeight, int requestedWidth, int requestedHeight) {
            if (l.i) {
                return g.QUALITY;
            }
            return g.MEMORY;
        }
    }

    /* compiled from: DownsampleStrategy */
    public static class d extends l {
        d() {
        }

        public float b(int sourceWidth, int sourceHeight, int requestedWidth, int requestedHeight) {
            return Math.max(((float) requestedWidth) / ((float) sourceWidth), ((float) requestedHeight) / ((float) sourceHeight));
        }

        public g a(int sourceWidth, int sourceHeight, int requestedWidth, int requestedHeight) {
            return g.QUALITY;
        }
    }

    /* compiled from: DownsampleStrategy */
    public static class a extends l {
        a() {
        }

        public float b(int sourceWidth, int sourceHeight, int requestedWidth, int requestedHeight) {
            int minIntegerFactor = Math.min(sourceHeight / requestedHeight, sourceWidth / requestedWidth);
            if (minIntegerFactor == 0) {
                return 1.0f;
            }
            return 1.0f / ((float) Integer.highestOneBit(minIntegerFactor));
        }

        public g a(int sourceWidth, int sourceHeight, int requestedWidth, int requestedHeight) {
            return g.QUALITY;
        }
    }

    /* compiled from: DownsampleStrategy */
    public static class b extends l {
        b() {
        }

        public float b(int sourceWidth, int sourceHeight, int requestedWidth, int requestedHeight) {
            int maxIntegerFactor = (int) Math.ceil((double) Math.max(((float) sourceHeight) / ((float) requestedHeight), ((float) sourceWidth) / ((float) requestedWidth)));
            int i = 1;
            int lesserOrEqualSampleSize = Math.max(1, Integer.highestOneBit(maxIntegerFactor));
            if (lesserOrEqualSampleSize >= maxIntegerFactor) {
                i = 0;
            }
            return 1.0f / ((float) (lesserOrEqualSampleSize << i));
        }

        public g a(int sourceWidth, int sourceHeight, int requestedWidth, int requestedHeight) {
            return g.MEMORY;
        }
    }

    /* compiled from: DownsampleStrategy */
    public static class f extends l {
        f() {
        }

        public float b(int sourceWidth, int sourceHeight, int requestedWidth, int requestedHeight) {
            return 1.0f;
        }

        public g a(int sourceWidth, int sourceHeight, int requestedWidth, int requestedHeight) {
            return g.QUALITY;
        }
    }

    /* compiled from: DownsampleStrategy */
    public static class c extends l {
        c() {
        }

        public float b(int sourceWidth, int sourceHeight, int requestedWidth, int requestedHeight) {
            return Math.min(1.0f, l.c.b(sourceWidth, sourceHeight, requestedWidth, requestedHeight));
        }

        public g a(int sourceWidth, int sourceHeight, int requestedWidth, int requestedHeight) {
            if (b(sourceWidth, sourceHeight, requestedWidth, requestedHeight) == 1.0f) {
                return g.QUALITY;
            }
            return l.c.a(sourceWidth, sourceHeight, requestedWidth, requestedHeight);
        }
    }
}
