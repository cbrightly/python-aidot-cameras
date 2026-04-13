package coil.bitmap;

import android.graphics.Bitmap;
import android.os.Build;
import androidx.annotation.Px;
import coil.util.c;
import coil.util.m;
import java.util.HashSet;
import java.util.Set;
import kotlin.collections.n0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RealBitmapPool.kt */
public final class h implements c {
    @NotNull
    public static final a b = new a((DefaultConstructorMarker) null);
    @NotNull
    private static final Set<Bitmap.Config> c;
    private final int d;
    @NotNull
    private final Set<Bitmap.Config> e;
    @NotNull
    private final d f;
    @Nullable
    private final m g;
    @NotNull
    private final HashSet<Bitmap> h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;

    public h(int maxSize, @NotNull Set<? extends Bitmap.Config> allowedConfigs, @NotNull d strategy, @Nullable m logger) {
        k.e(allowedConfigs, "allowedConfigs");
        k.e(strategy, "strategy");
        this.d = maxSize;
        this.e = allowedConfigs;
        this.f = strategy;
        this.g = logger;
        this.h = new HashSet<>();
        if (!(maxSize >= 0)) {
            throw new IllegalArgumentException("maxSize must be >= 0.".toString());
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ h(int i2, Set<Bitmap.Config> set, d dVar, m mVar, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, (i3 & 2) != 0 ? c : set, (i3 & 4) != 0 ? d.a.a() : dVar, (i3 & 8) != 0 ? null : mVar);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0028, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x006f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void b(@org.jetbrains.annotations.NotNull android.graphics.Bitmap r11) {
        /*
            r10 = this;
            monitor-enter(r10)
            java.lang.String r0 = "bitmap"
            kotlin.jvm.internal.k.e(r11, r0)     // Catch:{ all -> 0x011e }
            boolean r0 = r11.isRecycled()     // Catch:{ all -> 0x011e }
            r1 = 0
            if (r0 == 0) goto L_0x0029
            coil.util.m r0 = r10.g     // Catch:{ all -> 0x011e }
            if (r0 != 0) goto L_0x0012
            goto L_0x0027
        L_0x0012:
            java.lang.String r2 = "RealBitmapPool"
            r3 = 6
            r4 = 0
            int r5 = r0.b()     // Catch:{ all -> 0x011e }
            if (r5 > r3) goto L_0x0026
            r5 = 0
            java.lang.String r6 = "Rejecting recycled bitmap from pool; bitmap: "
            java.lang.String r6 = kotlin.jvm.internal.k.l(r6, r11)     // Catch:{ all -> 0x011e }
            r0.a(r2, r3, r6, r1)     // Catch:{ all -> 0x011e }
        L_0x0026:
        L_0x0027:
            monitor-exit(r10)
            return
        L_0x0029:
            int r0 = coil.util.c.a(r11)     // Catch:{ all -> 0x011e }
            boolean r2 = r11.isMutable()     // Catch:{ all -> 0x011e }
            r3 = 1
            if (r2 == 0) goto L_0x00c2
            int r2 = r10.d     // Catch:{ all -> 0x011e }
            if (r0 > r2) goto L_0x00c2
            java.util.Set<android.graphics.Bitmap$Config> r2 = r10.e     // Catch:{ all -> 0x011e }
            android.graphics.Bitmap$Config r4 = r11.getConfig()     // Catch:{ all -> 0x011e }
            boolean r2 = r2.contains(r4)     // Catch:{ all -> 0x011e }
            if (r2 != 0) goto L_0x0046
            goto L_0x00c2
        L_0x0046:
            java.util.HashSet<android.graphics.Bitmap> r2 = r10.h     // Catch:{ all -> 0x011e }
            boolean r2 = r2.contains(r11)     // Catch:{ all -> 0x011e }
            if (r2 == 0) goto L_0x0070
            coil.util.m r2 = r10.g     // Catch:{ all -> 0x011e }
            if (r2 != 0) goto L_0x0053
            goto L_0x006e
        L_0x0053:
            java.lang.String r3 = "RealBitmapPool"
            r4 = 6
            r5 = 0
            int r6 = r2.b()     // Catch:{ all -> 0x011e }
            if (r6 > r4) goto L_0x006d
            r6 = 0
            java.lang.String r7 = "Rejecting duplicate bitmap from pool; bitmap: "
            coil.bitmap.d r8 = r10.f     // Catch:{ all -> 0x011e }
            java.lang.String r8 = r8.d(r11)     // Catch:{ all -> 0x011e }
            java.lang.String r7 = kotlin.jvm.internal.k.l(r7, r8)     // Catch:{ all -> 0x011e }
            r2.a(r3, r4, r7, r1)     // Catch:{ all -> 0x011e }
        L_0x006d:
        L_0x006e:
            monitor-exit(r10)
            return
        L_0x0070:
            coil.bitmap.d r2 = r10.f     // Catch:{ all -> 0x011e }
            r2.b(r11)     // Catch:{ all -> 0x011e }
            java.util.HashSet<android.graphics.Bitmap> r2 = r10.h     // Catch:{ all -> 0x011e }
            r2.add(r11)     // Catch:{ all -> 0x011e }
            int r2 = r10.i     // Catch:{ all -> 0x011e }
            int r2 = r2 + r0
            r10.i = r2     // Catch:{ all -> 0x011e }
            int r2 = r10.l     // Catch:{ all -> 0x011e }
            int r2 = r2 + r3
            r10.l = r2     // Catch:{ all -> 0x011e }
            coil.util.m r2 = r10.g     // Catch:{ all -> 0x011e }
            if (r2 != 0) goto L_0x0089
            goto L_0x00bb
        L_0x0089:
            java.lang.String r3 = "RealBitmapPool"
            r4 = 2
            r5 = 0
            int r6 = r2.b()     // Catch:{ all -> 0x011e }
            if (r6 > r4) goto L_0x00ba
            r6 = 0
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x011e }
            r7.<init>()     // Catch:{ all -> 0x011e }
            java.lang.String r8 = "Put bitmap="
            r7.append(r8)     // Catch:{ all -> 0x011e }
            coil.bitmap.d r8 = r10.f     // Catch:{ all -> 0x011e }
            java.lang.String r8 = r8.d(r11)     // Catch:{ all -> 0x011e }
            r7.append(r8)     // Catch:{ all -> 0x011e }
            r8 = 10
            r7.append(r8)     // Catch:{ all -> 0x011e }
            java.lang.String r8 = r10.h()     // Catch:{ all -> 0x011e }
            r7.append(r8)     // Catch:{ all -> 0x011e }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x011e }
            r2.a(r3, r4, r7, r1)     // Catch:{ all -> 0x011e }
        L_0x00ba:
        L_0x00bb:
            int r1 = r10.d     // Catch:{ all -> 0x011e }
            r10.j(r1)     // Catch:{ all -> 0x011e }
            monitor-exit(r10)
            return
        L_0x00c2:
            coil.util.m r2 = r10.g     // Catch:{ all -> 0x011e }
            if (r2 != 0) goto L_0x00c7
            goto L_0x0119
        L_0x00c7:
            java.lang.String r4 = "RealBitmapPool"
            r5 = 2
            r6 = 0
            int r7 = r2.b()     // Catch:{ all -> 0x011e }
            if (r7 > r5) goto L_0x0118
            r7 = 0
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x011e }
            r8.<init>()     // Catch:{ all -> 0x011e }
            java.lang.String r9 = "Rejecting bitmap from pool; bitmap: "
            r8.append(r9)     // Catch:{ all -> 0x011e }
            coil.bitmap.d r9 = r10.f     // Catch:{ all -> 0x011e }
            java.lang.String r9 = r9.d(r11)     // Catch:{ all -> 0x011e }
            r8.append(r9)     // Catch:{ all -> 0x011e }
            java.lang.String r9 = ", is mutable: "
            r8.append(r9)     // Catch:{ all -> 0x011e }
            boolean r9 = r11.isMutable()     // Catch:{ all -> 0x011e }
            r8.append(r9)     // Catch:{ all -> 0x011e }
            java.lang.String r9 = ", is greater than max size: "
            r8.append(r9)     // Catch:{ all -> 0x011e }
            int r9 = r10.d     // Catch:{ all -> 0x011e }
            if (r0 <= r9) goto L_0x00fb
            goto L_0x00fc
        L_0x00fb:
            r3 = 0
        L_0x00fc:
            r8.append(r3)     // Catch:{ all -> 0x011e }
            java.lang.String r3 = ", is allowed config: "
            r8.append(r3)     // Catch:{ all -> 0x011e }
            java.util.Set<android.graphics.Bitmap$Config> r3 = r10.e     // Catch:{ all -> 0x011e }
            android.graphics.Bitmap$Config r9 = r11.getConfig()     // Catch:{ all -> 0x011e }
            boolean r3 = r3.contains(r9)     // Catch:{ all -> 0x011e }
            r8.append(r3)     // Catch:{ all -> 0x011e }
            java.lang.String r3 = r8.toString()     // Catch:{ all -> 0x011e }
            r2.a(r4, r5, r3, r1)     // Catch:{ all -> 0x011e }
        L_0x0118:
        L_0x0119:
            r11.recycle()     // Catch:{ all -> 0x011e }
            monitor-exit(r10)
            return
        L_0x011e:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.bitmap.h.b(android.graphics.Bitmap):void");
    }

    @NotNull
    public Bitmap c(@Px int width, @Px int height, @NotNull Bitmap.Config config) {
        k.e(config, "config");
        Bitmap g2 = g(width, height, config);
        if (g2 != null) {
            return g2;
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, config);
        k.d(createBitmap, "createBitmap(width, height, config)");
        return createBitmap;
    }

    @Nullable
    public Bitmap g(@Px int width, @Px int height, @NotNull Bitmap.Config config) {
        k.e(config, "config");
        Bitmap $this$getOrNull_u24lambda_u2d5 = f(width, height, config);
        if ($this$getOrNull_u24lambda_u2d5 == null) {
            return null;
        }
        $this$getOrNull_u24lambda_u2d5.eraseColor(0);
        return $this$getOrNull_u24lambda_u2d5;
    }

    @NotNull
    public Bitmap e(@Px int width, @Px int height, @NotNull Bitmap.Config config) {
        k.e(config, "config");
        Bitmap f2 = f(width, height, config);
        if (f2 != null) {
            return f2;
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, config);
        k.d(createBitmap, "createBitmap(width, height, config)");
        return createBitmap;
    }

    @Nullable
    public synchronized Bitmap f(@Px int width, @Px int height, @NotNull Bitmap.Config config) {
        Bitmap result;
        k.e(config, "config");
        if (!c.d(config)) {
            result = this.f.c(width, height, config);
            if (result == null) {
                m $this$log$iv = this.g;
                if ($this$log$iv != null) {
                    if ($this$log$iv.b() <= 2) {
                        $this$log$iv.a("RealBitmapPool", 2, k.l("Missing bitmap=", this.f.a(width, height, config)), (Throwable) null);
                    }
                }
                this.k++;
            } else {
                this.h.remove(result);
                this.i -= c.a(result);
                this.j++;
                i(result);
            }
            m $this$log$iv2 = this.g;
            if ($this$log$iv2 != null) {
                if ($this$log$iv2.b() <= 2) {
                    $this$log$iv2.a("RealBitmapPool", 2, "Get bitmap=" + this.f.a(width, height, config) + 10 + h(), (Throwable) null);
                }
            }
        } else {
            throw new IllegalArgumentException("Cannot create a mutable hardware bitmap.".toString());
        }
        return result;
    }

    public final void d() {
        m $this$log$iv = this.g;
        if ($this$log$iv != null && $this$log$iv.b() <= 2) {
            $this$log$iv.a("RealBitmapPool", 2, "clearMemory", (Throwable) null);
        }
        j(-1);
    }

    public synchronized void a(int level) {
        m $this$log$iv = this.g;
        if ($this$log$iv != null) {
            if ($this$log$iv.b() <= 2) {
                $this$log$iv.a("RealBitmapPool", 2, k.l("trimMemory, level=", Integer.valueOf(level)), (Throwable) null);
            }
        }
        if (level >= 40) {
            d();
        } else {
            boolean z = false;
            if (10 <= level && level < 20) {
                z = true;
            }
            if (z) {
                j(this.i / 2);
            }
        }
    }

    private final void i(Bitmap bitmap) {
        bitmap.setDensity(0);
        bitmap.setHasAlpha(true);
        if (Build.VERSION.SDK_INT >= 19) {
            bitmap.setPremultiplied(true);
        }
    }

    private final synchronized void j(int size) {
        while (this.i > size) {
            Bitmap removed = this.f.removeLast();
            if (removed == null) {
                m $this$log$iv = this.g;
                if ($this$log$iv != null) {
                    if ($this$log$iv.b() <= 5) {
                        $this$log$iv.a("RealBitmapPool", 5, k.l("Size mismatch, resetting.\n", h()), (Throwable) null);
                    }
                }
                this.i = 0;
                return;
            }
            this.h.remove(removed);
            this.i -= c.a(removed);
            this.m++;
            m $this$log$iv2 = this.g;
            if ($this$log$iv2 != null) {
                if ($this$log$iv2.b() <= 2) {
                    $this$log$iv2.a("RealBitmapPool", 2, "Evicting bitmap=" + this.f.d(removed) + 10 + h(), (Throwable) null);
                }
            }
            removed.recycle();
        }
    }

    private final String h() {
        return "Hits=" + this.j + ", misses=" + this.k + ", puts=" + this.l + ", evictions=" + this.m + ", currentSize=" + this.i + ", maxSize=" + this.d + ", strategy=" + this.f;
    }

    /* compiled from: RealBitmapPool.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }

    static {
        Set b2 = n0.b();
        Set $this$ALLOWED_CONFIGS_u24lambda_u2d13 = b2;
        $this$ALLOWED_CONFIGS_u24lambda_u2d13.add(Bitmap.Config.ALPHA_8);
        $this$ALLOWED_CONFIGS_u24lambda_u2d13.add(Bitmap.Config.RGB_565);
        $this$ALLOWED_CONFIGS_u24lambda_u2d13.add(Bitmap.Config.ARGB_4444);
        $this$ALLOWED_CONFIGS_u24lambda_u2d13.add(Bitmap.Config.ARGB_8888);
        if (Build.VERSION.SDK_INT >= 26) {
            $this$ALLOWED_CONFIGS_u24lambda_u2d13.add(Bitmap.Config.RGBA_F16);
        }
        c = n0.a(b2);
    }
}
