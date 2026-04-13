package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.util.j;
import com.meituan.robust.Constants;

/* compiled from: AttributeStrategy */
public class c implements l {
    private final b a = new b();
    private final h<a, Bitmap> b = new h<>();

    c() {
    }

    public void b(Bitmap bitmap) {
        this.b.d(this.a.e(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig()), bitmap);
    }

    public Bitmap c(int width, int height, Bitmap.Config config) {
        return this.b.a(this.a.e(width, height, config));
    }

    public Bitmap removeLast() {
        return this.b.f();
    }

    public String a(Bitmap bitmap) {
        return g(bitmap);
    }

    public String d(int width, int height, Bitmap.Config config) {
        return f(width, height, config);
    }

    public int e(Bitmap bitmap) {
        return j.g(bitmap);
    }

    public String toString() {
        return "AttributeStrategy:\n  " + this.b;
    }

    private static String g(Bitmap bitmap) {
        return f(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
    }

    static String f(int width, int height, Bitmap.Config config) {
        return Constants.ARRAY_TYPE + width + "x" + height + "], " + config;
    }

    @VisibleForTesting
    /* compiled from: AttributeStrategy */
    public static class b extends d<a> {
        b() {
        }

        /* access modifiers changed from: package-private */
        public a e(int width, int height, Bitmap.Config config) {
            a result = (a) b();
            result.b(width, height, config);
            return result;
        }

        /* access modifiers changed from: protected */
        /* renamed from: d */
        public a a() {
            return new a(this);
        }
    }

    @VisibleForTesting
    /* compiled from: AttributeStrategy */
    public static class a implements m {
        private final b a;
        private int b;
        private int c;
        private Bitmap.Config d;

        public a(b pool) {
            this.a = pool;
        }

        public void b(int width, int height, Bitmap.Config config) {
            this.b = width;
            this.c = height;
            this.d = config;
        }

        public boolean equals(Object o) {
            if (!(o instanceof a)) {
                return false;
            }
            a other = (a) o;
            if (this.b == other.b && this.c == other.c && this.d == other.d) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int result = ((this.b * 31) + this.c) * 31;
            Bitmap.Config config = this.d;
            return result + (config != null ? config.hashCode() : 0);
        }

        public String toString() {
            return c.f(this.b, this.c, this.d);
        }

        public void a() {
            this.a.c(this);
        }
    }
}
