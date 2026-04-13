package coil.request;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import coil.decode.l;
import coil.transition.b;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.i0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DefaultRequestOptions.kt */
public final class d {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    public static final d b = new d((i0) null, (b) null, (coil.size.b) null, (Bitmap.Config) null, false, false, (Drawable) null, (Drawable) null, (Drawable) null, (c) null, (c) null, (c) null, 4095, (DefaultConstructorMarker) null);
    @NotNull
    private final i0 c;
    @NotNull
    private final b d;
    @NotNull
    private final coil.size.b e;
    @NotNull
    private final Bitmap.Config f;
    private final boolean g;
    private final boolean h;
    @Nullable
    private final Drawable i;
    @Nullable
    private final Drawable j;
    @Nullable
    private final Drawable k;
    @NotNull
    private final c l;
    @NotNull
    private final c m;
    @NotNull
    private final c n;

    public d() {
        this((i0) null, (b) null, (coil.size.b) null, (Bitmap.Config) null, false, false, (Drawable) null, (Drawable) null, (Drawable) null, (c) null, (c) null, (c) null, 4095, (DefaultConstructorMarker) null);
    }

    public d(@NotNull i0 dispatcher, @NotNull b transition, @NotNull coil.size.b precision, @NotNull Bitmap.Config bitmapConfig, boolean allowHardware, boolean allowRgb565, @Nullable Drawable placeholder, @Nullable Drawable error, @Nullable Drawable fallback, @NotNull c memoryCachePolicy, @NotNull c diskCachePolicy, @NotNull c networkCachePolicy) {
        k.e(dispatcher, "dispatcher");
        k.e(transition, "transition");
        k.e(precision, "precision");
        k.e(bitmapConfig, "bitmapConfig");
        k.e(memoryCachePolicy, "memoryCachePolicy");
        k.e(diskCachePolicy, "diskCachePolicy");
        k.e(networkCachePolicy, "networkCachePolicy");
        this.c = dispatcher;
        this.d = transition;
        this.e = precision;
        this.f = bitmapConfig;
        this.g = allowHardware;
        this.h = allowRgb565;
        this.i = placeholder;
        this.j = error;
        this.k = fallback;
        this.l = memoryCachePolicy;
        this.m = diskCachePolicy;
        this.n = networkCachePolicy;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ d(kotlinx.coroutines.i0 r13, coil.transition.b r14, coil.size.b r15, android.graphics.Bitmap.Config r16, boolean r17, boolean r18, android.graphics.drawable.Drawable r19, android.graphics.drawable.Drawable r20, android.graphics.drawable.Drawable r21, coil.request.c r22, coil.request.c r23, coil.request.c r24, int r25, kotlin.jvm.internal.DefaultConstructorMarker r26) {
        /*
            r12 = this;
            r0 = r25
            r1 = r0 & 1
            if (r1 == 0) goto L_0x000d
            kotlinx.coroutines.d1 r1 = kotlinx.coroutines.d1.a
            kotlinx.coroutines.i0 r1 = kotlinx.coroutines.d1.b()
            goto L_0x000e
        L_0x000d:
            r1 = r13
        L_0x000e:
            r2 = r0 & 2
            if (r2 == 0) goto L_0x0015
            coil.transition.b r2 = coil.transition.b.b
            goto L_0x0016
        L_0x0015:
            r2 = r14
        L_0x0016:
            r3 = r0 & 4
            if (r3 == 0) goto L_0x001d
            coil.size.b r3 = coil.size.b.AUTOMATIC
            goto L_0x001e
        L_0x001d:
            r3 = r15
        L_0x001e:
            r4 = r0 & 8
            if (r4 == 0) goto L_0x0029
            coil.util.o r4 = coil.util.o.a
            android.graphics.Bitmap$Config r4 = r4.d()
            goto L_0x002b
        L_0x0029:
            r4 = r16
        L_0x002b:
            r5 = r0 & 16
            if (r5 == 0) goto L_0x0031
            r5 = 1
            goto L_0x0033
        L_0x0031:
            r5 = r17
        L_0x0033:
            r6 = r0 & 32
            if (r6 == 0) goto L_0x0039
            r6 = 0
            goto L_0x003b
        L_0x0039:
            r6 = r18
        L_0x003b:
            r7 = r0 & 64
            r8 = 0
            if (r7 == 0) goto L_0x0042
            r7 = r8
            goto L_0x0044
        L_0x0042:
            r7 = r19
        L_0x0044:
            r9 = r0 & 128(0x80, float:1.794E-43)
            if (r9 == 0) goto L_0x004a
            r9 = r8
            goto L_0x004c
        L_0x004a:
            r9 = r20
        L_0x004c:
            r10 = r0 & 256(0x100, float:3.59E-43)
            if (r10 == 0) goto L_0x0051
            goto L_0x0053
        L_0x0051:
            r8 = r21
        L_0x0053:
            r10 = r0 & 512(0x200, float:7.175E-43)
            if (r10 == 0) goto L_0x005a
            coil.request.c r10 = coil.request.c.ENABLED
            goto L_0x005c
        L_0x005a:
            r10 = r22
        L_0x005c:
            r11 = r0 & 1024(0x400, float:1.435E-42)
            if (r11 == 0) goto L_0x0063
            coil.request.c r11 = coil.request.c.ENABLED
            goto L_0x0065
        L_0x0063:
            r11 = r23
        L_0x0065:
            r0 = r0 & 2048(0x800, float:2.87E-42)
            if (r0 == 0) goto L_0x006c
            coil.request.c r0 = coil.request.c.ENABLED
            goto L_0x006e
        L_0x006c:
            r0 = r24
        L_0x006e:
            r13 = r12
            r14 = r1
            r15 = r2
            r16 = r3
            r17 = r4
            r18 = r5
            r19 = r6
            r20 = r7
            r21 = r9
            r22 = r8
            r23 = r10
            r24 = r11
            r25 = r0
            r13.<init>(r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.request.d.<init>(kotlinx.coroutines.i0, coil.transition.b, coil.size.b, android.graphics.Bitmap$Config, boolean, boolean, android.graphics.drawable.Drawable, android.graphics.drawable.Drawable, android.graphics.drawable.Drawable, coil.request.c, coil.request.c, coil.request.c, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public final i0 e() {
        return this.c;
    }

    @NotNull
    public final b l() {
        return this.d;
    }

    @NotNull
    public final coil.size.b k() {
        return this.e;
    }

    @NotNull
    public final Bitmap.Config c() {
        return this.f;
    }

    public final boolean a() {
        return this.g;
    }

    public final boolean b() {
        return this.h;
    }

    @Nullable
    public final Drawable j() {
        return this.i;
    }

    @Nullable
    public final Drawable f() {
        return this.j;
    }

    @Nullable
    public final Drawable g() {
        return this.k;
    }

    @NotNull
    public final c h() {
        return this.l;
    }

    @NotNull
    public final c d() {
        return this.m;
    }

    @NotNull
    public final c i() {
        return this.n;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if ((other instanceof d) && k.a(this.c, ((d) other).c) && k.a(this.d, ((d) other).d) && this.e == ((d) other).e && this.f == ((d) other).f && this.g == ((d) other).g && this.h == ((d) other).h && k.a(this.i, ((d) other).i) && k.a(this.j, ((d) other).j) && k.a(this.k, ((d) other).k) && this.l == ((d) other).l && this.m == ((d) other).m && this.n == ((d) other).n) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = ((((((((((this.c.hashCode() * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31) + this.f.hashCode()) * 31) + l.a(this.g)) * 31) + l.a(this.h)) * 31;
        Drawable drawable = this.i;
        int i2 = 0;
        int result2 = (result + (drawable == null ? 0 : drawable.hashCode())) * 31;
        Drawable drawable2 = this.j;
        int result3 = (result2 + (drawable2 == null ? 0 : drawable2.hashCode())) * 31;
        Drawable drawable3 = this.k;
        if (drawable3 != null) {
            i2 = drawable3.hashCode();
        }
        return ((((((result3 + i2) * 31) + this.l.hashCode()) * 31) + this.m.hashCode()) * 31) + this.n.hashCode();
    }

    @NotNull
    public String toString() {
        return "DefaultRequestOptions(dispatcher=" + this.c + ", transition=" + this.d + ", precision=" + this.e + ", bitmapConfig=" + this.f + ", allowHardware=" + this.g + ", allowRgb565=" + this.h + ", placeholder=" + this.i + ", error=" + this.j + ", fallback=" + this.k + ", memoryCachePolicy=" + this.l + ", diskCachePolicy=" + this.m + ", networkCachePolicy=" + this.n + ')';
    }

    /* compiled from: DefaultRequestOptions.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
