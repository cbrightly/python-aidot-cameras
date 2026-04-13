package com.bumptech.glide.load.engine.cache;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;

/* compiled from: MemorySizeCalculator */
public final class i {
    private final int a;
    private final int b;
    private final Context c;
    private final int d;

    /* compiled from: MemorySizeCalculator */
    public interface c {
        int a();

        int b();
    }

    i(a builder) {
        int i;
        this.c = builder.b;
        if (e(builder.c)) {
            i = builder.i / 2;
        } else {
            i = builder.i;
        }
        this.d = i;
        int maxSize = c(builder.c, builder.g, builder.h);
        int screenSize = builder.d.b() * builder.d.a() * 4;
        int targetBitmapPoolSize = Math.round(((float) screenSize) * builder.f);
        int targetMemoryCacheSize = Math.round(((float) screenSize) * builder.e);
        int availableSize = maxSize - i;
        if (targetMemoryCacheSize + targetBitmapPoolSize <= availableSize) {
            this.b = targetMemoryCacheSize;
            this.a = targetBitmapPoolSize;
        } else {
            float f = builder.f;
            float f2 = builder.e;
            float part = ((float) availableSize) / (f + f2);
            this.b = Math.round(f2 * part);
            this.a = Math.round(builder.f * part);
        }
        if (Log.isLoggable("MemorySizeCalculator", 3)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Calculation complete, Calculated memory cache size: ");
            sb.append(f(this.b));
            sb.append(", pool size: ");
            sb.append(f(this.a));
            sb.append(", byte array size: ");
            sb.append(f(i));
            sb.append(", memory class limited? ");
            sb.append(targetMemoryCacheSize + targetBitmapPoolSize > maxSize);
            sb.append(", max size: ");
            sb.append(f(maxSize));
            sb.append(", memoryClass: ");
            sb.append(builder.c.getMemoryClass());
            sb.append(", isLowMemoryDevice: ");
            sb.append(e(builder.c));
            Log.d("MemorySizeCalculator", sb.toString());
        }
    }

    public int d() {
        return this.b;
    }

    public int b() {
        return this.a;
    }

    public int a() {
        return this.d;
    }

    private static int c(ActivityManager activityManager, float maxSizeMultiplier, float lowMemoryMaxSizeMultiplier) {
        return Math.round(((float) (activityManager.getMemoryClass() * 1024 * 1024)) * (e(activityManager) ? lowMemoryMaxSizeMultiplier : maxSizeMultiplier));
    }

    private String f(int bytes) {
        return Formatter.formatFileSize(this.c, (long) bytes);
    }

    @TargetApi(19)
    static boolean e(ActivityManager activityManager) {
        if (Build.VERSION.SDK_INT >= 19) {
            return activityManager.isLowRamDevice();
        }
        return true;
    }

    /* compiled from: MemorySizeCalculator */
    public static final class a {
        static final int a = (Build.VERSION.SDK_INT < 26 ? 4 : 1);
        final Context b;
        ActivityManager c;
        c d;
        float e = 2.0f;
        float f = ((float) a);
        float g = 0.4f;
        float h = 0.33f;
        int i = 4194304;

        public a(Context context) {
            this.b = context;
            this.c = (ActivityManager) context.getSystemService("activity");
            this.d = new b(context.getResources().getDisplayMetrics());
            if (Build.VERSION.SDK_INT >= 26 && i.e(this.c)) {
                this.f = 0.0f;
            }
        }

        public i a() {
            return new i(this);
        }
    }

    /* compiled from: MemorySizeCalculator */
    public static final class b implements c {
        private final DisplayMetrics a;

        b(DisplayMetrics displayMetrics) {
            this.a = displayMetrics;
        }

        public int b() {
            return this.a.widthPixels;
        }

        public int a() {
            return this.a.heightPixels;
        }
    }
}
