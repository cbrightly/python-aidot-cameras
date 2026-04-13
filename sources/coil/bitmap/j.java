package coil.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import coil.util.c;
import coil.util.o;
import java.util.TreeMap;
import kotlin.collections.l0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiresApi(19)
@VisibleForTesting
/* compiled from: BitmapPoolStrategy.kt */
public final class j implements d {
    @NotNull
    public static final a b = new a((DefaultConstructorMarker) null);
    @NotNull
    private final coil.collection.a<Integer, Bitmap> c = new coil.collection.a<>();
    @NotNull
    private final TreeMap<Integer, Integer> d = new TreeMap<>();

    public void b(@NotNull Bitmap bitmap) {
        k.e(bitmap, "bitmap");
        int size = c.a(bitmap);
        this.c.d(Integer.valueOf(size), bitmap);
        Integer count = this.d.get(Integer.valueOf(size));
        TreeMap<Integer, Integer> treeMap = this.d;
        Integer valueOf = Integer.valueOf(size);
        int i = 1;
        if (count != null) {
            i = 1 + count.intValue();
        }
        treeMap.put(valueOf, Integer.valueOf(i));
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0041  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.Bitmap c(@androidx.annotation.Px int r6, @androidx.annotation.Px int r7, @org.jetbrains.annotations.NotNull android.graphics.Bitmap.Config r8) {
        /*
            r5 = this;
            java.lang.String r0 = "config"
            kotlin.jvm.internal.k.e(r8, r0)
            coil.util.o r0 = coil.util.o.a
            int r0 = r0.a(r6, r7, r8)
            java.util.TreeMap<java.lang.Integer, java.lang.Integer> r1 = r5.d
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
            java.lang.Object r1 = r1.ceilingKey(r2)
            java.lang.Integer r1 = (java.lang.Integer) r1
            if (r1 != 0) goto L_0x001a
        L_0x0019:
            goto L_0x002d
        L_0x001a:
            int r2 = r1.intValue()
            r3 = 0
            int r4 = r0 * 4
            if (r2 > r4) goto L_0x0025
            r4 = 1
            goto L_0x0026
        L_0x0025:
            r4 = 0
        L_0x0026:
            if (r4 == 0) goto L_0x0029
            goto L_0x002a
        L_0x0029:
            r1 = 0
        L_0x002a:
            if (r1 != 0) goto L_0x002f
            goto L_0x0019
        L_0x002d:
            r1 = r0
            goto L_0x0033
        L_0x002f:
            int r1 = r1.intValue()
        L_0x0033:
            coil.collection.a<java.lang.Integer, android.graphics.Bitmap> r2 = r5.c
            java.lang.Integer r3 = java.lang.Integer.valueOf(r1)
            java.lang.Object r2 = r2.g(r3)
            android.graphics.Bitmap r2 = (android.graphics.Bitmap) r2
            if (r2 == 0) goto L_0x0047
            r5.e(r1)
            r2.reconfigure(r6, r7, r8)
        L_0x0047:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.bitmap.j.c(int, int, android.graphics.Bitmap$Config):android.graphics.Bitmap");
    }

    @Nullable
    public Bitmap removeLast() {
        Bitmap bitmap = this.c.f();
        if (bitmap != null) {
            e(bitmap.getAllocationByteCount());
        }
        return bitmap;
    }

    private final void e(int size) {
        int count = ((Number) l0.g(this.d, Integer.valueOf(size))).intValue();
        if (count == 1) {
            this.d.remove(Integer.valueOf(size));
        } else {
            this.d.put(Integer.valueOf(size), Integer.valueOf(count - 1));
        }
    }

    @NotNull
    public String d(@NotNull Bitmap bitmap) {
        k.e(bitmap, "bitmap");
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(c.a(bitmap));
        sb.append(']');
        return sb.toString();
    }

    @NotNull
    public String a(@Px int width, @Px int height, @NotNull Bitmap.Config config) {
        k.e(config, "config");
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(o.a.a(width, height, config));
        sb.append(']');
        return sb.toString();
    }

    @NotNull
    public String toString() {
        return "SizeStrategy: entries=" + this.c + ", sizes=" + this.d;
    }

    /* compiled from: BitmapPoolStrategy.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
