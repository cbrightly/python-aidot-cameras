package coil.bitmap;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.VisibleForTesting;
import androidx.collection.SparseArrayCompat;
import coil.memory.u;
import coil.util.m;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BitmapReferenceCounter.kt */
public final class i implements e {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private static final Handler b = new Handler(Looper.getMainLooper());
    @NotNull
    private final u c;
    @NotNull
    private final c d;
    @Nullable
    private final m e;
    @NotNull
    private final SparseArrayCompat<b> f = new SparseArrayCompat<>();
    private int g;

    public i(@NotNull u weakMemoryCache, @NotNull c bitmapPool, @Nullable m logger) {
        k.e(weakMemoryCache, "weakMemoryCache");
        k.e(bitmapPool, "bitmapPool");
        this.c = weakMemoryCache;
        this.d = bitmapPool;
        this.e = logger;
    }

    public synchronized void c(@NotNull Bitmap bitmap) {
        k.e(bitmap, "bitmap");
        int key = System.identityHashCode(bitmap);
        b value = g(key, bitmap);
        value.d(value.b() + 1);
        m $this$log$iv = this.e;
        if ($this$log$iv != null) {
            if ($this$log$iv.b() <= 2) {
                $this$log$iv.a("RealBitmapReferenceCounter", 2, "INCREMENT: [" + key + ", " + value.b() + ", " + value.c() + ']', (Throwable) null);
            }
        }
        e();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0042, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean b(@org.jetbrains.annotations.NotNull android.graphics.Bitmap r13) {
        /*
            r12 = this;
            monitor-enter(r12)
            java.lang.String r0 = "bitmap"
            kotlin.jvm.internal.k.e(r13, r0)     // Catch:{ all -> 0x00b7 }
            r0 = r13
            r1 = 0
            int r2 = java.lang.System.identityHashCode(r0)     // Catch:{ all -> 0x00b7 }
            r0 = r2
            coil.bitmap.i$b r1 = r12.h(r0, r13)     // Catch:{ all -> 0x00b7 }
            r2 = 0
            r3 = 0
            if (r1 != 0) goto L_0x0043
            r1 = r12
            r4 = 0
            coil.util.m r5 = r1.e     // Catch:{ all -> 0x00b7 }
            if (r5 != 0) goto L_0x001c
            goto L_0x0041
        L_0x001c:
            java.lang.String r6 = "RealBitmapReferenceCounter"
            r7 = 2
            r8 = 0
            int r9 = r5.b()     // Catch:{ all -> 0x00b7 }
            if (r9 > r7) goto L_0x0040
            r9 = 0
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b7 }
            r10.<init>()     // Catch:{ all -> 0x00b7 }
            java.lang.String r11 = "DECREMENT: ["
            r10.append(r11)     // Catch:{ all -> 0x00b7 }
            r10.append(r0)     // Catch:{ all -> 0x00b7 }
            java.lang.String r11 = ", UNKNOWN, UNKNOWN]"
            r10.append(r11)     // Catch:{ all -> 0x00b7 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x00b7 }
            r5.a(r6, r7, r10, r2)     // Catch:{ all -> 0x00b7 }
        L_0x0040:
        L_0x0041:
            monitor-exit(r12)
            return r3
        L_0x0043:
            int r4 = r1.b()     // Catch:{ all -> 0x00b7 }
            int r4 = r4 + -1
            r1.d(r4)     // Catch:{ all -> 0x00b7 }
            coil.util.m r4 = r12.e     // Catch:{ all -> 0x00b7 }
            if (r4 != 0) goto L_0x0051
            goto L_0x008e
        L_0x0051:
            java.lang.String r5 = "RealBitmapReferenceCounter"
            r6 = 2
            r7 = 0
            int r8 = r4.b()     // Catch:{ all -> 0x00b7 }
            if (r8 > r6) goto L_0x008d
            r8 = 0
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b7 }
            r9.<init>()     // Catch:{ all -> 0x00b7 }
            java.lang.String r10 = "DECREMENT: ["
            r9.append(r10)     // Catch:{ all -> 0x00b7 }
            r9.append(r0)     // Catch:{ all -> 0x00b7 }
            java.lang.String r10 = ", "
            r9.append(r10)     // Catch:{ all -> 0x00b7 }
            int r10 = r1.b()     // Catch:{ all -> 0x00b7 }
            r9.append(r10)     // Catch:{ all -> 0x00b7 }
            java.lang.String r10 = ", "
            r9.append(r10)     // Catch:{ all -> 0x00b7 }
            boolean r10 = r1.c()     // Catch:{ all -> 0x00b7 }
            r9.append(r10)     // Catch:{ all -> 0x00b7 }
            r10 = 93
            r9.append(r10)     // Catch:{ all -> 0x00b7 }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x00b7 }
            r4.a(r5, r6, r9, r2)     // Catch:{ all -> 0x00b7 }
        L_0x008d:
        L_0x008e:
            int r2 = r1.b()     // Catch:{ all -> 0x00b7 }
            if (r2 > 0) goto L_0x009b
            boolean r2 = r1.c()     // Catch:{ all -> 0x00b7 }
            if (r2 == 0) goto L_0x009b
            r3 = 1
        L_0x009b:
            r2 = r3
            if (r2 == 0) goto L_0x00b2
            androidx.collection.SparseArrayCompat<coil.bitmap.i$b> r3 = r12.f     // Catch:{ all -> 0x00b7 }
            r3.remove(r0)     // Catch:{ all -> 0x00b7 }
            coil.memory.u r3 = r12.c     // Catch:{ all -> 0x00b7 }
            r3.c(r13)     // Catch:{ all -> 0x00b7 }
            android.os.Handler r3 = b     // Catch:{ all -> 0x00b7 }
            coil.bitmap.a r4 = new coil.bitmap.a     // Catch:{ all -> 0x00b7 }
            r4.<init>(r12, r13)     // Catch:{ all -> 0x00b7 }
            r3.post(r4)     // Catch:{ all -> 0x00b7 }
        L_0x00b2:
            r12.e()     // Catch:{ all -> 0x00b7 }
            monitor-exit(r12)
            return r2
        L_0x00b7:
            r13 = move-exception
            monitor-exit(r12)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.bitmap.i.b(android.graphics.Bitmap):boolean");
    }

    /* access modifiers changed from: private */
    public static final void f(i this$0, Bitmap $bitmap) {
        k.e(this$0, "this$0");
        k.e($bitmap, "$bitmap");
        this$0.d.b($bitmap);
    }

    public synchronized void a(@NotNull Bitmap bitmap, boolean isValid) {
        k.e(bitmap, "bitmap");
        int key = System.identityHashCode(bitmap);
        if (!isValid) {
            g(key, bitmap).e(false);
        } else if (h(key, bitmap) == null) {
            this.f.put(key, new b(new WeakReference(bitmap), 0, true));
        }
        e();
    }

    private final void e() {
        int i = this.g;
        this.g = i + 1;
        if (i >= 50) {
            d();
        }
    }

    @VisibleForTesting
    public final void d() {
        List toRemove = new ArrayList();
        int size = this.f.size();
        int $i$f$getSize = 0;
        if (size > 0) {
            int i = 0;
            do {
                int index = i;
                i++;
                if (this.f.valueAt(index).a().get() == null) {
                    toRemove.add(Integer.valueOf(index));
                    continue;
                }
            } while (i < size);
        }
        List $this$forEachIndices$iv = toRemove;
        SparseArrayCompat<b> sparseArrayCompat = this.f;
        int size2 = $this$forEachIndices$iv.size() - 1;
        if (size2 >= 0) {
            do {
                int i$iv = $i$f$getSize;
                $i$f$getSize++;
                sparseArrayCompat.removeAt(((Number) $this$forEachIndices$iv.get(i$iv)).intValue());
            } while ($i$f$getSize <= size2);
        }
    }

    private final b g(int key, Bitmap bitmap) {
        b value = h(key, bitmap);
        if (value != null) {
            return value;
        }
        b value2 = new b(new WeakReference(bitmap), 0, false);
        this.f.put(key, value2);
        return value2;
    }

    private final b h(int key, Bitmap bitmap) {
        b it = this.f.get(key);
        if (it != null) {
            if (it.a().get() == bitmap) {
                return it;
            }
        }
        return null;
    }

    @VisibleForTesting
    /* compiled from: BitmapReferenceCounter.kt */
    public static final class b {
        @NotNull
        private final WeakReference<Bitmap> a;
        private int b;
        private boolean c;

        public b(@NotNull WeakReference<Bitmap> bitmap, int count, boolean isValid) {
            k.e(bitmap, "bitmap");
            this.a = bitmap;
            this.b = count;
            this.c = isValid;
        }

        @NotNull
        public final WeakReference<Bitmap> a() {
            return this.a;
        }

        public final int b() {
            return this.b;
        }

        public final void d(int i) {
            this.b = i;
        }

        public final boolean c() {
            return this.c;
        }

        public final void e(boolean z) {
            this.c = z;
        }
    }

    /* compiled from: BitmapReferenceCounter.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
