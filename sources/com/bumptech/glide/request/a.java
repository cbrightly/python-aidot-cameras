package com.bumptech.glide.request;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.CheckResult;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.g;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.m;
import com.bumptech.glide.load.resource.bitmap.j;
import com.bumptech.glide.load.resource.bitmap.l;
import com.bumptech.glide.load.resource.bitmap.q;
import com.bumptech.glide.load.resource.gif.h;
import com.bumptech.glide.request.a;
import com.bumptech.glide.signature.c;
import com.bumptech.glide.util.CachedHashCodeArrayMap;
import java.util.Map;

/* compiled from: BaseRequestOptions */
public abstract class a<T extends a<T>> implements Cloneable {
    private int A4;
    @NonNull
    private i B4 = new i();
    @NonNull
    private Map<Class<?>, m<?>> C4 = new CachedHashCodeArrayMap();
    @NonNull
    private Class<?> D4 = Object.class;
    private boolean E4;
    @Nullable
    private Resources.Theme F4;
    private boolean G4;
    private boolean H4;
    private boolean I4;
    private boolean J4 = true;
    private boolean K4;
    private boolean a1 = true;
    private int a2 = -1;
    private int c;
    private float d = 1.0f;
    @NonNull
    private com.bumptech.glide.load.engine.i f = com.bumptech.glide.load.engine.i.e;
    private int p0;
    private int p1 = -1;
    @NonNull
    private f p2 = c.a();
    private boolean p3;
    private boolean p4 = true;
    @NonNull
    private g q = g.NORMAL;
    @Nullable
    private Drawable x;
    private int y;
    @Nullable
    private Drawable z;
    @Nullable
    private Drawable z4;

    private static boolean M(int fields, int flag) {
        return (fields & flag) != 0;
    }

    @CheckResult
    @NonNull
    public T l0(@FloatRange(from = 0.0d, to = 1.0d) float sizeMultiplier) {
        if (this.G4) {
            return clone().l0(sizeMultiplier);
        }
        if (sizeMultiplier < 0.0f || sizeMultiplier > 1.0f) {
            throw new IllegalArgumentException("sizeMultiplier must be between 0 and 1");
        }
        this.d = sizeMultiplier;
        this.c |= 2;
        return i0();
    }

    @CheckResult
    @NonNull
    public T u0(boolean flag) {
        if (this.G4) {
            return clone().u0(flag);
        }
        this.K4 = flag;
        this.c |= 1048576;
        return i0();
    }

    @CheckResult
    @NonNull
    public T f(@NonNull com.bumptech.glide.load.engine.i strategy) {
        if (this.G4) {
            return clone().f(strategy);
        }
        this.f = (com.bumptech.glide.load.engine.i) com.bumptech.glide.util.i.d(strategy);
        this.c |= 4;
        return i0();
    }

    @CheckResult
    @NonNull
    public T e0(@NonNull g priority) {
        if (this.G4) {
            return clone().e0(priority);
        }
        this.q = (g) com.bumptech.glide.util.i.d(priority);
        this.c |= 8;
        return i0();
    }

    @CheckResult
    @NonNull
    public T d0(@DrawableRes int resourceId) {
        if (this.G4) {
            return clone().d0(resourceId);
        }
        this.p0 = resourceId;
        int i = this.c | 128;
        this.c = i;
        this.z = null;
        this.c = i & -65;
        return i0();
    }

    @CheckResult
    @NonNull
    public T j(@DrawableRes int resourceId) {
        if (this.G4) {
            return clone().j(resourceId);
        }
        this.y = resourceId;
        int i = this.c | 32;
        this.c = i;
        this.x = null;
        this.c = i & -17;
        return i0();
    }

    @CheckResult
    @NonNull
    public T m0(boolean skip) {
        if (this.G4) {
            return clone().m0(true);
        }
        this.a1 = !skip;
        this.c |= 256;
        return i0();
    }

    @CheckResult
    @NonNull
    public T c0(int width, int height) {
        if (this.G4) {
            return clone().c0(width, height);
        }
        this.a2 = width;
        this.p1 = height;
        this.c |= 512;
        return i0();
    }

    @CheckResult
    @NonNull
    public T k0(@NonNull f signature) {
        if (this.G4) {
            return clone().k0(signature);
        }
        this.p2 = (f) com.bumptech.glide.util.i.d(signature);
        this.c |= 1024;
        return i0();
    }

    @CheckResult
    /* renamed from: d */
    public T clone() {
        try {
            BaseRequestOptions<?> result = (a) super.clone();
            i iVar = new i();
            result.B4 = iVar;
            iVar.b(this.B4);
            CachedHashCodeArrayMap cachedHashCodeArrayMap = new CachedHashCodeArrayMap();
            result.C4 = cachedHashCodeArrayMap;
            cachedHashCodeArrayMap.putAll(this.C4);
            result.E4 = false;
            result.G4 = false;
            return result;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.bumptech.glide.load.h<Y>, java.lang.Object, com.bumptech.glide.load.h] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @androidx.annotation.CheckResult
    @androidx.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <Y> T j0(@androidx.annotation.NonNull com.bumptech.glide.load.h<Y> r2, @androidx.annotation.NonNull Y r3) {
        /*
            r1 = this;
            boolean r0 = r1.G4
            if (r0 == 0) goto L_0x000d
            com.bumptech.glide.request.a r0 = r1.clone()
            com.bumptech.glide.request.a r0 = r0.j0(r2, r3)
            return r0
        L_0x000d:
            com.bumptech.glide.util.i.d(r2)
            com.bumptech.glide.util.i.d(r3)
            com.bumptech.glide.load.i r0 = r1.B4
            r0.c(r2, r3)
            com.bumptech.glide.request.a r0 = r1.i0()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.a.j0(com.bumptech.glide.load.h, java.lang.Object):com.bumptech.glide.request.a");
    }

    @CheckResult
    @NonNull
    public T e(@NonNull Class<?> resourceClass) {
        if (this.G4) {
            return clone().e(resourceClass);
        }
        this.D4 = (Class) com.bumptech.glide.util.i.d(resourceClass);
        this.c |= 4096;
        return i0();
    }

    public final boolean N() {
        return this.p4;
    }

    public final boolean Q() {
        return L(2048);
    }

    @CheckResult
    @NonNull
    public T i(@NonNull l strategy) {
        return j0(l.h, com.bumptech.glide.util.i.d(strategy));
    }

    @CheckResult
    @NonNull
    public T n0(@IntRange(from = 0) int timeoutMs) {
        return j0(com.bumptech.glide.load.model.stream.a.a, Integer.valueOf(timeoutMs));
    }

    @CheckResult
    @NonNull
    public T U() {
        return a0(l.e, new com.bumptech.glide.load.resource.bitmap.i());
    }

    @CheckResult
    @NonNull
    public T c() {
        return r0(l.e, new com.bumptech.glide.load.resource.bitmap.i());
    }

    @CheckResult
    @NonNull
    public T X() {
        return Y(l.c, new q());
    }

    @CheckResult
    @NonNull
    public T k() {
        return f0(l.c, new q());
    }

    @CheckResult
    @NonNull
    public T V() {
        return Y(l.d, new j());
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [com.bumptech.glide.load.m, com.bumptech.glide.load.m<android.graphics.Bitmap>] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Unknown variable types count: 1 */
    @androidx.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final T a0(@androidx.annotation.NonNull com.bumptech.glide.load.resource.bitmap.l r2, @androidx.annotation.NonNull com.bumptech.glide.load.m<android.graphics.Bitmap> r3) {
        /*
            r1 = this;
            boolean r0 = r1.G4
            if (r0 == 0) goto L_0x000d
            com.bumptech.glide.request.a r0 = r1.clone()
            com.bumptech.glide.request.a r0 = r0.a0(r2, r3)
            return r0
        L_0x000d:
            r1.i(r2)
            r0 = 0
            com.bumptech.glide.request.a r0 = r1.q0(r3, r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.a.a0(com.bumptech.glide.load.resource.bitmap.l, com.bumptech.glide.load.m):com.bumptech.glide.request.a");
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [com.bumptech.glide.load.m, com.bumptech.glide.load.m<android.graphics.Bitmap>] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Unknown variable types count: 1 */
    @androidx.annotation.CheckResult
    @androidx.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final T r0(@androidx.annotation.NonNull com.bumptech.glide.load.resource.bitmap.l r2, @androidx.annotation.NonNull com.bumptech.glide.load.m<android.graphics.Bitmap> r3) {
        /*
            r1 = this;
            boolean r0 = r1.G4
            if (r0 == 0) goto L_0x000d
            com.bumptech.glide.request.a r0 = r1.clone()
            com.bumptech.glide.request.a r0 = r0.r0(r2, r3)
            return r0
        L_0x000d:
            r1.i(r2)
            com.bumptech.glide.request.a r0 = r1.p0(r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.a.r0(com.bumptech.glide.load.resource.bitmap.l, com.bumptech.glide.load.m):com.bumptech.glide.request.a");
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [com.bumptech.glide.load.m, com.bumptech.glide.load.m<android.graphics.Bitmap>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @androidx.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private T f0(@androidx.annotation.NonNull com.bumptech.glide.load.resource.bitmap.l r2, @androidx.annotation.NonNull com.bumptech.glide.load.m<android.graphics.Bitmap> r3) {
        /*
            r1 = this;
            r0 = 1
            com.bumptech.glide.request.a r0 = r1.g0(r2, r3, r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.a.f0(com.bumptech.glide.load.resource.bitmap.l, com.bumptech.glide.load.m):com.bumptech.glide.request.a");
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [com.bumptech.glide.load.m, com.bumptech.glide.load.m<android.graphics.Bitmap>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @androidx.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private T Y(@androidx.annotation.NonNull com.bumptech.glide.load.resource.bitmap.l r2, @androidx.annotation.NonNull com.bumptech.glide.load.m<android.graphics.Bitmap> r3) {
        /*
            r1 = this;
            r0 = 0
            com.bumptech.glide.request.a r0 = r1.g0(r2, r3, r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.a.Y(com.bumptech.glide.load.resource.bitmap.l, com.bumptech.glide.load.m):com.bumptech.glide.request.a");
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [com.bumptech.glide.load.m, com.bumptech.glide.load.m<android.graphics.Bitmap>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @androidx.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private T g0(@androidx.annotation.NonNull com.bumptech.glide.load.resource.bitmap.l r3, @androidx.annotation.NonNull com.bumptech.glide.load.m<android.graphics.Bitmap> r4, boolean r5) {
        /*
            r2 = this;
            if (r5 == 0) goto L_0x0007
            com.bumptech.glide.request.a r0 = r2.r0(r3, r4)
            goto L_0x000b
        L_0x0007:
            com.bumptech.glide.request.a r0 = r2.a0(r3, r4)
        L_0x000b:
            r1 = 1
            r0.J4 = r1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.a.g0(com.bumptech.glide.load.resource.bitmap.l, com.bumptech.glide.load.m, boolean):com.bumptech.glide.request.a");
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.bumptech.glide.load.m, com.bumptech.glide.load.m<android.graphics.Bitmap>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @androidx.annotation.CheckResult
    @androidx.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public T p0(@androidx.annotation.NonNull com.bumptech.glide.load.m<android.graphics.Bitmap> r2) {
        /*
            r1 = this;
            r0 = 1
            com.bumptech.glide.request.a r0 = r1.q0(r2, r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.a.p0(com.bumptech.glide.load.m):com.bumptech.glide.request.a");
    }

    @CheckResult
    @NonNull
    public T t0(@NonNull m<Bitmap>... transformations) {
        if (transformations.length > 1) {
            return q0(new com.bumptech.glide.load.g(transformations), true);
        }
        if (transformations.length == 1) {
            return p0(transformations[0]);
        }
        return i0();
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.bumptech.glide.load.m, com.bumptech.glide.load.m<android.graphics.Bitmap>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @androidx.annotation.CheckResult
    @androidx.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public T Z(@androidx.annotation.NonNull com.bumptech.glide.load.m<android.graphics.Bitmap> r2) {
        /*
            r1 = this;
            r0 = 0
            com.bumptech.glide.request.a r0 = r1.q0(r2, r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.a.Z(com.bumptech.glide.load.m):com.bumptech.glide.request.a");
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [com.bumptech.glide.load.m, com.bumptech.glide.load.m<android.graphics.Bitmap>] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Unknown variable types count: 1 */
    @androidx.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public T q0(@androidx.annotation.NonNull com.bumptech.glide.load.m<android.graphics.Bitmap> r4, boolean r5) {
        /*
            r3 = this;
            boolean r0 = r3.G4
            if (r0 == 0) goto L_0x000d
            com.bumptech.glide.request.a r0 = r3.clone()
            com.bumptech.glide.request.a r0 = r0.q0(r4, r5)
            return r0
        L_0x000d:
            com.bumptech.glide.load.resource.bitmap.o r0 = new com.bumptech.glide.load.resource.bitmap.o
            r0.<init>(r4, r5)
            java.lang.Class<android.graphics.Bitmap> r1 = android.graphics.Bitmap.class
            r3.s0(r1, r4, r5)
            java.lang.Class<android.graphics.drawable.Drawable> r1 = android.graphics.drawable.Drawable.class
            r3.s0(r1, r0, r5)
            java.lang.Class<android.graphics.drawable.BitmapDrawable> r1 = android.graphics.drawable.BitmapDrawable.class
            com.bumptech.glide.load.m r2 = r0.a()
            r3.s0(r1, r2, r5)
            java.lang.Class<com.bumptech.glide.load.resource.gif.GifDrawable> r1 = com.bumptech.glide.load.resource.gif.GifDrawable.class
            com.bumptech.glide.load.resource.gif.e r2 = new com.bumptech.glide.load.resource.gif.e
            r2.<init>(r4)
            r3.s0(r1, r2, r5)
            com.bumptech.glide.request.a r1 = r3.i0()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.a.q0(com.bumptech.glide.load.m, boolean):com.bumptech.glide.request.a");
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [java.lang.Class<Y>, java.lang.Class] */
    /* JADX WARNING: type inference failed for: r3v0, types: [com.bumptech.glide.load.m, com.bumptech.glide.load.m<Y>] */
    /* JADX WARNING: Unknown variable types count: 2 */
    @androidx.annotation.CheckResult
    @androidx.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <Y> T b0(@androidx.annotation.NonNull java.lang.Class<Y> r2, @androidx.annotation.NonNull com.bumptech.glide.load.m<Y> r3) {
        /*
            r1 = this;
            r0 = 0
            com.bumptech.glide.request.a r0 = r1.s0(r2, r3, r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.a.b0(java.lang.Class, com.bumptech.glide.load.m):com.bumptech.glide.request.a");
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [java.lang.Class<Y>, java.lang.Object, java.lang.Class] */
    /* JADX WARNING: type inference failed for: r5v0, types: [com.bumptech.glide.load.m, com.bumptech.glide.load.m<Y>, java.lang.Object] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Unknown variable types count: 2 */
    @androidx.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <Y> T s0(@androidx.annotation.NonNull java.lang.Class<Y> r4, @androidx.annotation.NonNull com.bumptech.glide.load.m<Y> r5, boolean r6) {
        /*
            r3 = this;
            boolean r0 = r3.G4
            if (r0 == 0) goto L_0x000d
            com.bumptech.glide.request.a r0 = r3.clone()
            com.bumptech.glide.request.a r0 = r0.s0(r4, r5, r6)
            return r0
        L_0x000d:
            com.bumptech.glide.util.i.d(r4)
            com.bumptech.glide.util.i.d(r5)
            java.util.Map<java.lang.Class<?>, com.bumptech.glide.load.m<?>> r0 = r3.C4
            r0.put(r4, r5)
            int r0 = r3.c
            r0 = r0 | 2048(0x800, float:2.87E-42)
            r3.c = r0
            r1 = 1
            r3.p4 = r1
            r2 = 65536(0x10000, float:9.18355E-41)
            r0 = r0 | r2
            r3.c = r0
            r2 = 0
            r3.J4 = r2
            if (r6 == 0) goto L_0x0032
            r2 = 131072(0x20000, float:1.83671E-40)
            r0 = r0 | r2
            r3.c = r0
            r3.p3 = r1
        L_0x0032:
            com.bumptech.glide.request.a r0 = r3.i0()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.a.s0(java.lang.Class, com.bumptech.glide.load.m, boolean):com.bumptech.glide.request.a");
    }

    @CheckResult
    @NonNull
    public T h() {
        return j0(h.b, true);
    }

    @CheckResult
    @NonNull
    public T a(@NonNull a<?> o) {
        if (this.G4) {
            return clone().a(o);
        }
        a<?> aVar = o;
        if (M(aVar.c, 2)) {
            this.d = aVar.d;
        }
        if (M(aVar.c, 262144)) {
            this.H4 = aVar.H4;
        }
        if (M(aVar.c, 1048576)) {
            this.K4 = aVar.K4;
        }
        if (M(aVar.c, 4)) {
            this.f = aVar.f;
        }
        if (M(aVar.c, 8)) {
            this.q = aVar.q;
        }
        if (M(aVar.c, 16)) {
            this.x = aVar.x;
            this.y = 0;
            this.c &= -33;
        }
        if (M(aVar.c, 32)) {
            this.y = aVar.y;
            this.x = null;
            this.c &= -17;
        }
        if (M(aVar.c, 64)) {
            this.z = aVar.z;
            this.p0 = 0;
            this.c &= -129;
        }
        if (M(aVar.c, 128)) {
            this.p0 = aVar.p0;
            this.z = null;
            this.c &= -65;
        }
        if (M(aVar.c, 256)) {
            this.a1 = aVar.a1;
        }
        if (M(aVar.c, 512)) {
            this.a2 = aVar.a2;
            this.p1 = aVar.p1;
        }
        if (M(aVar.c, 1024)) {
            this.p2 = aVar.p2;
        }
        if (M(aVar.c, 4096)) {
            this.D4 = aVar.D4;
        }
        if (M(aVar.c, 8192)) {
            this.z4 = aVar.z4;
            this.A4 = 0;
            this.c &= -16385;
        }
        if (M(aVar.c, 16384)) {
            this.A4 = aVar.A4;
            this.z4 = null;
            this.c &= -8193;
        }
        if (M(aVar.c, 32768)) {
            this.F4 = aVar.F4;
        }
        if (M(aVar.c, 65536)) {
            this.p4 = aVar.p4;
        }
        if (M(aVar.c, 131072)) {
            this.p3 = aVar.p3;
        }
        if (M(aVar.c, 2048)) {
            this.C4.putAll(aVar.C4);
            this.J4 = aVar.J4;
        }
        if (M(aVar.c, 524288)) {
            this.I4 = aVar.I4;
        }
        if (!this.p4) {
            this.C4.clear();
            int i = this.c & -2049;
            this.c = i;
            this.p3 = false;
            this.c = i & -131073;
            this.J4 = true;
        }
        this.c |= aVar.c;
        this.B4.b(aVar.B4);
        return i0();
    }

    public boolean equals(Object o) {
        if (!(o instanceof a)) {
            return false;
        }
        BaseRequestOptions<?> other = (a) o;
        if (Float.compare(other.d, this.d) == 0 && this.y == other.y && com.bumptech.glide.util.j.c(this.x, other.x) && this.p0 == other.p0 && com.bumptech.glide.util.j.c(this.z, other.z) && this.A4 == other.A4 && com.bumptech.glide.util.j.c(this.z4, other.z4) && this.a1 == other.a1 && this.p1 == other.p1 && this.a2 == other.a2 && this.p3 == other.p3 && this.p4 == other.p4 && this.H4 == other.H4 && this.I4 == other.I4 && this.f.equals(other.f) && this.q == other.q && this.B4.equals(other.B4) && this.C4.equals(other.C4) && this.D4.equals(other.D4) && com.bumptech.glide.util.j.c(this.p2, other.p2) && com.bumptech.glide.util.j.c(this.F4, other.F4)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return com.bumptech.glide.util.j.o(this.F4, com.bumptech.glide.util.j.o(this.p2, com.bumptech.glide.util.j.o(this.D4, com.bumptech.glide.util.j.o(this.C4, com.bumptech.glide.util.j.o(this.B4, com.bumptech.glide.util.j.o(this.q, com.bumptech.glide.util.j.o(this.f, com.bumptech.glide.util.j.p(this.I4, com.bumptech.glide.util.j.p(this.H4, com.bumptech.glide.util.j.p(this.p4, com.bumptech.glide.util.j.p(this.p3, com.bumptech.glide.util.j.n(this.a2, com.bumptech.glide.util.j.n(this.p1, com.bumptech.glide.util.j.p(this.a1, com.bumptech.glide.util.j.o(this.z4, com.bumptech.glide.util.j.n(this.A4, com.bumptech.glide.util.j.o(this.z, com.bumptech.glide.util.j.n(this.p0, com.bumptech.glide.util.j.o(this.x, com.bumptech.glide.util.j.n(this.y, com.bumptech.glide.util.j.k(this.d)))))))))))))))))))));
    }

    @NonNull
    public T S() {
        this.E4 = true;
        return h0();
    }

    @NonNull
    public T b() {
        if (!this.E4 || this.G4) {
            this.G4 = true;
            return S();
        }
        throw new IllegalStateException("You cannot auto lock an already locked options object, try clone() first");
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final T i0() {
        if (!this.E4) {
            return h0();
        }
        throw new IllegalStateException("You cannot modify locked T, consider clone()");
    }

    /* access modifiers changed from: protected */
    public final boolean G() {
        return this.G4;
    }

    @NonNull
    public final Map<Class<?>, m<?>> C() {
        return this.C4;
    }

    public final boolean O() {
        return this.p3;
    }

    @NonNull
    public final i r() {
        return this.B4;
    }

    @NonNull
    public final Class<?> y() {
        return this.D4;
    }

    @NonNull
    public final com.bumptech.glide.load.engine.i l() {
        return this.f;
    }

    @Nullable
    public final Drawable n() {
        return this.x;
    }

    public final int m() {
        return this.y;
    }

    public final int v() {
        return this.p0;
    }

    @Nullable
    public final Drawable u() {
        return this.z;
    }

    public final int p() {
        return this.A4;
    }

    @Nullable
    public final Drawable o() {
        return this.z4;
    }

    @Nullable
    public final Resources.Theme B() {
        return this.F4;
    }

    public final boolean H() {
        return this.a1;
    }

    @NonNull
    public final f z() {
        return this.p2;
    }

    public final boolean I() {
        return L(8);
    }

    @NonNull
    public final g w() {
        return this.q;
    }

    public final int t() {
        return this.a2;
    }

    public final boolean R() {
        return com.bumptech.glide.util.j.t(this.a2, this.p1);
    }

    public final int s() {
        return this.p1;
    }

    public final float A() {
        return this.d;
    }

    /* access modifiers changed from: package-private */
    public boolean K() {
        return this.J4;
    }

    private boolean L(int flag) {
        return M(this.c, flag);
    }

    public final boolean F() {
        return this.H4;
    }

    public final boolean D() {
        return this.K4;
    }

    public final boolean q() {
        return this.I4;
    }

    private T h0() {
        return this;
    }
}
