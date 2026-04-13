package com.bumptech.glide.util;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.model.l;
import com.meituan.robust.Constants;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

/* compiled from: Util */
public final class j {
    private static final char[] a = "0123456789abcdef".toCharArray();
    private static final char[] b = new char[64];
    @Nullable
    private static volatile Handler c;

    private j() {
    }

    @NonNull
    public static String w(@NonNull byte[] bytes) {
        String d;
        char[] cArr = b;
        synchronized (cArr) {
            d = d(bytes, cArr);
        }
        return d;
    }

    @NonNull
    private static String d(@NonNull byte[] bytes, @NonNull char[] hexChars) {
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 255;
            char[] cArr = a;
            hexChars[j * 2] = cArr[v >>> 4];
            hexChars[(j * 2) + 1] = cArr[v & 15];
        }
        return new String(hexChars);
    }

    @TargetApi(19)
    public static int g(@NonNull Bitmap bitmap) {
        if (!bitmap.isRecycled()) {
            if (Build.VERSION.SDK_INT >= 19) {
                try {
                    return bitmap.getAllocationByteCount();
                } catch (NullPointerException e) {
                }
            }
            return bitmap.getHeight() * bitmap.getRowBytes();
        }
        throw new IllegalStateException("Cannot obtain size for recycled Bitmap: " + bitmap + Constants.ARRAY_TYPE + bitmap.getWidth() + "x" + bitmap.getHeight() + "] " + bitmap.getConfig());
    }

    public static int f(int width, int height, @Nullable Bitmap.Config config) {
        return width * height * h(config);
    }

    private static int h(@Nullable Bitmap.Config config) {
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }
        switch (a.a[config.ordinal()]) {
            case 1:
                return 1;
            case 2:
            case 3:
                return 2;
            case 4:
                return 8;
            default:
                return 4;
        }
    }

    /* compiled from: Util */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[Bitmap.Config.values().length];
            a = iArr;
            try {
                iArr[Bitmap.Config.ALPHA_8.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[Bitmap.Config.RGB_565.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[Bitmap.Config.ARGB_4444.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[Bitmap.Config.RGBA_F16.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[Bitmap.Config.ARGB_8888.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public static boolean t(int width, int height) {
        return s(width) && s(height);
    }

    private static boolean s(int dimen) {
        return dimen > 0 || dimen == Integer.MIN_VALUE;
    }

    public static void u(Runnable runnable) {
        j().post(runnable);
    }

    public static void v(Runnable runnable) {
        j().removeCallbacks(runnable);
    }

    private static Handler j() {
        if (c == null) {
            synchronized (j.class) {
                if (c == null) {
                    c = new Handler(Looper.getMainLooper());
                }
            }
        }
        return c;
    }

    public static void a() {
        if (!r()) {
            throw new IllegalArgumentException("You must call this method on the main thread");
        }
    }

    public static boolean r() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static boolean q() {
        return !r();
    }

    @NonNull
    public static <T> Queue<T> e(int size) {
        return new ArrayDeque(size);
    }

    @NonNull
    public static <T> List<T> i(@NonNull Collection<T> other) {
        List<T> result = new ArrayList<>(other.size());
        for (T item : other) {
            if (item != null) {
                result.add(item);
            }
        }
        return result;
    }

    public static boolean c(@Nullable Object a2, @Nullable Object b2) {
        if (a2 == null) {
            return b2 == null;
        }
        return a2.equals(b2);
    }

    public static boolean b(@Nullable Object a2, @Nullable Object b2) {
        if (a2 == null) {
            return b2 == null;
        }
        if (a2 instanceof l) {
            return ((l) a2).a(b2);
        }
        return a2.equals(b2);
    }

    public static int m(int value) {
        return n(value, 17);
    }

    public static int n(int value, int accumulator) {
        return (accumulator * 31) + value;
    }

    public static int k(float value) {
        return l(value, 17);
    }

    public static int l(float value, int accumulator) {
        return n(Float.floatToIntBits(value), accumulator);
    }

    public static int o(@Nullable Object object, int accumulator) {
        return n(object == null ? 0 : object.hashCode(), accumulator);
    }

    public static int p(boolean value, int accumulator) {
        return n(value, accumulator);
    }
}
