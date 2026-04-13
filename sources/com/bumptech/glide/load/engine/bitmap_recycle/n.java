package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.util.j;
import com.meituan.robust.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

@RequiresApi(19)
/* compiled from: SizeConfigStrategy */
public class n implements l {
    private static final Bitmap.Config[] a;
    private static final Bitmap.Config[] b;
    private static final Bitmap.Config[] c = {Bitmap.Config.RGB_565};
    private static final Bitmap.Config[] d = {Bitmap.Config.ARGB_4444};
    private static final Bitmap.Config[] e = {Bitmap.Config.ALPHA_8};
    private final c f = new c();
    private final h<b, Bitmap> g = new h<>();
    private final Map<Bitmap.Config, NavigableMap<Integer, Integer>> h = new HashMap();

    /* JADX WARNING: type inference failed for: r1v8, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            r0 = 2
            android.graphics.Bitmap$Config[] r0 = new android.graphics.Bitmap.Config[r0]
            android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.ARGB_8888
            r2 = 0
            r0[r2] = r1
            r1 = 0
            r3 = 1
            r0[r3] = r1
            int r1 = android.os.Build.VERSION.SDK_INT
            r4 = 26
            if (r1 < r4) goto L_0x0021
            int r1 = r0.length
            int r1 = r1 + r3
            java.lang.Object[] r1 = java.util.Arrays.copyOf(r0, r1)
            r0 = r1
            android.graphics.Bitmap$Config[] r0 = (android.graphics.Bitmap.Config[]) r0
            int r1 = r0.length
            int r1 = r1 - r3
            android.graphics.Bitmap$Config r4 = android.graphics.Bitmap.Config.RGBA_F16
            r0[r1] = r4
        L_0x0021:
            a = r0
            b = r0
            android.graphics.Bitmap$Config[] r0 = new android.graphics.Bitmap.Config[r3]
            android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.RGB_565
            r0[r2] = r1
            c = r0
            android.graphics.Bitmap$Config[] r0 = new android.graphics.Bitmap.Config[r3]
            android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.ARGB_4444
            r0[r2] = r1
            d = r0
            android.graphics.Bitmap$Config[] r0 = new android.graphics.Bitmap.Config[r3]
            android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.ALPHA_8
            r0[r2] = r1
            e = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.engine.bitmap_recycle.n.<clinit>():void");
    }

    public void b(Bitmap bitmap) {
        b key = this.f.e(j.g(bitmap), bitmap.getConfig());
        this.g.d(key, bitmap);
        NavigableMap<Integer, Integer> sizes = j(bitmap.getConfig());
        Integer current = (Integer) sizes.get(Integer.valueOf(key.b));
        Integer valueOf = Integer.valueOf(key.b);
        int i = 1;
        if (current != null) {
            i = 1 + current.intValue();
        }
        sizes.put(valueOf, Integer.valueOf(i));
    }

    @Nullable
    public Bitmap c(int width, int height, Bitmap.Config config) {
        b bestKey = g(j.f(width, height, config), config);
        Bitmap result = this.g.a(bestKey);
        if (result != null) {
            f(Integer.valueOf(bestKey.b), result);
            result.reconfigure(width, height, config);
        }
        return result;
    }

    private b g(int size, Bitmap.Config config) {
        b result = this.f.e(size, config);
        Bitmap.Config[] i = i(config);
        int length = i.length;
        int i2 = 0;
        while (i2 < length) {
            Bitmap.Config possibleConfig = i[i2];
            Integer possibleSize = j(possibleConfig).ceilingKey(Integer.valueOf(size));
            if (possibleSize == null || possibleSize.intValue() > size * 8) {
                i2++;
            } else {
                if (possibleSize.intValue() == size) {
                    if (possibleConfig == null) {
                        if (config == null) {
                            return result;
                        }
                    } else if (possibleConfig.equals(config)) {
                        return result;
                    }
                }
                this.f.c(result);
                return this.f.e(possibleSize.intValue(), possibleConfig);
            }
        }
        return result;
    }

    @Nullable
    public Bitmap removeLast() {
        Bitmap removed = this.g.f();
        if (removed != null) {
            f(Integer.valueOf(j.g(removed)), removed);
        }
        return removed;
    }

    private void f(Integer size, Bitmap removed) {
        NavigableMap<Integer, Integer> sizes = j(removed.getConfig());
        Integer current = (Integer) sizes.get(size);
        if (current == null) {
            throw new NullPointerException("Tried to decrement empty size, size: " + size + ", removed: " + a(removed) + ", this: " + this);
        } else if (current.intValue() == 1) {
            sizes.remove(size);
        } else {
            sizes.put(size, Integer.valueOf(current.intValue() - 1));
        }
    }

    private NavigableMap<Integer, Integer> j(Bitmap.Config config) {
        NavigableMap<Integer, Integer> sizes = this.h.get(config);
        if (sizes != null) {
            return sizes;
        }
        NavigableMap<Integer, Integer> sizes2 = new TreeMap<>();
        this.h.put(config, sizes2);
        return sizes2;
    }

    public String a(Bitmap bitmap) {
        return h(j.g(bitmap), bitmap.getConfig());
    }

    public String d(int width, int height, Bitmap.Config config) {
        return h(j.f(width, height, config), config);
    }

    public int e(Bitmap bitmap) {
        return j.g(bitmap);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SizeConfigStrategy{groupedMap=");
        sb.append(this.g);
        StringBuilder sb2 = sb.append(", sortedSizes=(");
        for (Map.Entry<Bitmap.Config, NavigableMap<Integer, Integer>> entry : this.h.entrySet()) {
            sb2.append(entry.getKey());
            sb2.append('[');
            sb2.append(entry.getValue());
            sb2.append("], ");
        }
        if (!this.h.isEmpty()) {
            sb2.replace(sb2.length() - 2, sb2.length(), "");
        }
        sb2.append(")}");
        return sb2.toString();
    }

    @VisibleForTesting
    /* compiled from: SizeConfigStrategy */
    public static class c extends d<b> {
        c() {
        }

        public b e(int size, Bitmap.Config config) {
            b result = (b) b();
            result.b(size, config);
            return result;
        }

        /* access modifiers changed from: protected */
        /* renamed from: d */
        public b a() {
            return new b(this);
        }
    }

    @VisibleForTesting
    /* compiled from: SizeConfigStrategy */
    public static final class b implements m {
        private final c a;
        int b;
        private Bitmap.Config c;

        public b(c pool) {
            this.a = pool;
        }

        public void b(int size, Bitmap.Config config) {
            this.b = size;
            this.c = config;
        }

        public void a() {
            this.a.c(this);
        }

        public String toString() {
            return n.h(this.b, this.c);
        }

        public boolean equals(Object o) {
            if (!(o instanceof b)) {
                return false;
            }
            b other = (b) o;
            if (this.b != other.b || !j.c(this.c, other.c)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int i = this.b * 31;
            Bitmap.Config config = this.c;
            return i + (config != null ? config.hashCode() : 0);
        }
    }

    static String h(int size, Bitmap.Config config) {
        return Constants.ARRAY_TYPE + size + "](" + config + ")";
    }

    private static Bitmap.Config[] i(Bitmap.Config requested) {
        if (Build.VERSION.SDK_INT >= 26 && Bitmap.Config.RGBA_F16.equals(requested)) {
            return b;
        }
        switch (a.a[requested.ordinal()]) {
            case 1:
                return a;
            case 2:
                return c;
            case 3:
                return d;
            case 4:
                return e;
            default:
                return new Bitmap.Config[]{requested};
        }
    }

    /* compiled from: SizeConfigStrategy */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[Bitmap.Config.values().length];
            a = iArr;
            try {
                iArr[Bitmap.Config.ARGB_8888.ordinal()] = 1;
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
                a[Bitmap.Config.ALPHA_8.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }
}
