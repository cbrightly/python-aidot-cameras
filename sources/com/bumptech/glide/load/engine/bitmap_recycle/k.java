package com.bumptech.glide.load.engine.bitmap_recycle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* compiled from: LruBitmapPool */
public class k implements e {
    private static final Bitmap.Config a = Bitmap.Config.ARGB_8888;
    private final l b;
    private final Set<Bitmap.Config> c;
    private final long d;
    private final a e;
    private long f;
    private long g;
    private int h;
    private int i;
    private int j;
    private int k;

    /* compiled from: LruBitmapPool */
    public interface a {
        void a(Bitmap bitmap);

        void c(Bitmap bitmap);
    }

    k(long maxSize, l strategy, Set<Bitmap.Config> allowedConfigs) {
        this.d = maxSize;
        this.f = maxSize;
        this.b = strategy;
        this.c = allowedConfigs;
        this.e = new b();
    }

    public k(long maxSize) {
        this(maxSize, l(), k());
    }

    public long n() {
        return this.f;
    }

    public synchronized void b(Bitmap bitmap) {
        if (bitmap != null) {
            try {
                if (!bitmap.isRecycled()) {
                    if (bitmap.isMutable() && ((long) this.b.e(bitmap)) <= this.f) {
                        if (this.c.contains(bitmap.getConfig())) {
                            int size = this.b.e(bitmap);
                            this.b.b(bitmap);
                            this.e.a(bitmap);
                            this.j++;
                            this.g += (long) size;
                            if (Log.isLoggable("LruBitmapPool", 2)) {
                                Log.v("LruBitmapPool", "Put bitmap in pool=" + this.b.a(bitmap));
                            }
                            h();
                            j();
                            return;
                        }
                    }
                    if (Log.isLoggable("LruBitmapPool", 2)) {
                        Log.v("LruBitmapPool", "Reject bitmap from pool, bitmap: " + this.b.a(bitmap) + ", is mutable: " + bitmap.isMutable() + ", is allowed config: " + this.c.contains(bitmap.getConfig()));
                    }
                    bitmap.recycle();
                    return;
                }
                throw new IllegalStateException("Cannot pool recycled bitmap");
            } catch (Throwable th) {
                throw th;
            }
        } else {
            throw new NullPointerException("Bitmap must not be null");
        }
    }

    private void j() {
        q(this.f);
    }

    @NonNull
    public Bitmap c(int width, int height, Bitmap.Config config) {
        Bitmap result = m(width, height, config);
        if (result == null) {
            return g(width, height, config);
        }
        result.eraseColor(0);
        return result;
    }

    @NonNull
    public Bitmap e(int width, int height, Bitmap.Config config) {
        Bitmap result = m(width, height, config);
        if (result == null) {
            return g(width, height, config);
        }
        return result;
    }

    @NonNull
    private static Bitmap g(int width, int height, @Nullable Bitmap.Config config) {
        return Bitmap.createBitmap(width, height, config != null ? config : a);
    }

    @TargetApi(26)
    private static void f(Bitmap.Config config) {
        if (Build.VERSION.SDK_INT >= 26 && config == Bitmap.Config.HARDWARE) {
            throw new IllegalArgumentException("Cannot create a mutable Bitmap with config: " + config + ". Consider setting Downsampler#ALLOW_HARDWARE_CONFIG to false in your RequestOptions and/or in GlideBuilder.setDefaultRequestOptions");
        }
    }

    @Nullable
    private synchronized Bitmap m(int width, int height, @Nullable Bitmap.Config config) {
        Bitmap result;
        f(config);
        result = this.b.c(width, height, config != null ? config : a);
        if (result == null) {
            if (Log.isLoggable("LruBitmapPool", 3)) {
                Log.d("LruBitmapPool", "Missing bitmap=" + this.b.d(width, height, config));
            }
            this.i++;
        } else {
            this.h++;
            this.g -= (long) this.b.e(result);
            this.e.c(result);
            p(result);
        }
        if (Log.isLoggable("LruBitmapPool", 2)) {
            Log.v("LruBitmapPool", "Get bitmap=" + this.b.d(width, height, config));
        }
        h();
        return result;
    }

    private static void p(Bitmap bitmap) {
        bitmap.setHasAlpha(true);
        o(bitmap);
    }

    @TargetApi(19)
    private static void o(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 19) {
            bitmap.setPremultiplied(true);
        }
    }

    public void d() {
        if (Log.isLoggable("LruBitmapPool", 3)) {
            Log.d("LruBitmapPool", "clearMemory");
        }
        q(0);
    }

    @SuppressLint({"InlinedApi"})
    public void a(int level) {
        if (Log.isLoggable("LruBitmapPool", 3)) {
            Log.d("LruBitmapPool", "trimMemory, level=" + level);
        }
        if (level >= 40 || (Build.VERSION.SDK_INT >= 23 && level >= 20)) {
            d();
        } else if (level >= 20 || level == 15) {
            q(n() / 2);
        }
    }

    private synchronized void q(long size) {
        while (this.g > size) {
            Bitmap removed = this.b.removeLast();
            if (removed == null) {
                if (Log.isLoggable("LruBitmapPool", 5)) {
                    Log.w("LruBitmapPool", "Size mismatch, resetting");
                    i();
                }
                this.g = 0;
                return;
            }
            this.e.c(removed);
            this.g -= (long) this.b.e(removed);
            this.k++;
            if (Log.isLoggable("LruBitmapPool", 3)) {
                Log.d("LruBitmapPool", "Evicting bitmap=" + this.b.a(removed));
            }
            h();
            removed.recycle();
        }
    }

    private void h() {
        if (Log.isLoggable("LruBitmapPool", 2)) {
            i();
        }
    }

    private void i() {
        Log.v("LruBitmapPool", "Hits=" + this.h + ", misses=" + this.i + ", puts=" + this.j + ", evictions=" + this.k + ", currentSize=" + this.g + ", maxSize=" + this.f + "\nStrategy=" + this.b);
    }

    private static l l() {
        if (Build.VERSION.SDK_INT >= 19) {
            return new n();
        }
        return new c();
    }

    @TargetApi(26)
    private static Set<Bitmap.Config> k() {
        Set<Bitmap.Config> configs = new HashSet<>(Arrays.asList(Bitmap.Config.values()));
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 19) {
            configs.add((Object) null);
        }
        if (i2 >= 26) {
            configs.remove(Bitmap.Config.HARDWARE);
        }
        return Collections.unmodifiableSet(configs);
    }

    /* compiled from: LruBitmapPool */
    public static final class b implements a {
        b() {
        }

        public void a(Bitmap bitmap) {
        }

        public void c(Bitmap bitmap) {
        }
    }
}
