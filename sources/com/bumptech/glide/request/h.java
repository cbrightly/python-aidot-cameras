package com.bumptech.glide.request;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import androidx.annotation.DrawableRes;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.c;
import com.bumptech.glide.d;
import com.bumptech.glide.g;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.j;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.request.target.i;
import com.bumptech.glide.request.target.j;
import com.bumptech.glide.request.transition.c;
import com.bumptech.glide.util.e;
import com.bumptech.glide.util.pool.b;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: SingleRequest */
public final class h<R> implements c, i, g {
    private static final boolean a = Log.isLoggable("Request", 2);
    @GuardedBy("requestLock")
    private int A;
    @GuardedBy("requestLock")
    private int B;
    @GuardedBy("requestLock")
    private boolean C;
    @Nullable
    private RuntimeException D;
    @Nullable
    private final String b;
    private final b c;
    private final Object d;
    @Nullable
    private final e<R> e;
    private final d f;
    private final Context g;
    private final d h;
    @Nullable
    private final Object i;
    private final Class<R> j;
    private final a<?> k;
    private final int l;
    private final int m;
    private final g n;
    private final j<R> o;
    @Nullable
    private final List<e<R>> p;
    private final c<? super R> q;
    private final Executor r;
    @GuardedBy("requestLock")
    private t<R> s;
    @GuardedBy("requestLock")
    private j.d t;
    @GuardedBy("requestLock")
    private long u;
    private volatile com.bumptech.glide.load.engine.j v;
    @GuardedBy("requestLock")
    private a w;
    @GuardedBy("requestLock")
    @Nullable
    private Drawable x;
    @GuardedBy("requestLock")
    @Nullable
    private Drawable y;
    @GuardedBy("requestLock")
    @Nullable
    private Drawable z;

    /* compiled from: SingleRequest */
    public enum a {
        PENDING,
        RUNNING,
        WAITING_FOR_SIZE,
        COMPLETE,
        FAILED,
        CLEARED
    }

    public static <R> h<R> w(Context context, d glideContext, Object requestLock, Object model, Class<R> transcodeClass, a<?> requestOptions, int overrideWidth, int overrideHeight, g priority, com.bumptech.glide.request.target.j<R> target, e<R> targetListener, @Nullable List<e<R>> requestListeners, d requestCoordinator, com.bumptech.glide.load.engine.j engine, c<? super R> animationFactory, Executor callbackExecutor) {
        return new h(context, glideContext, requestLock, model, transcodeClass, requestOptions, overrideWidth, overrideHeight, priority, target, targetListener, requestListeners, requestCoordinator, engine, animationFactory, callbackExecutor);
    }

    private h(Context context, d glideContext, @NonNull Object requestLock, @Nullable Object model, Class<R> transcodeClass, a<?> requestOptions, int overrideWidth, int overrideHeight, g priority, com.bumptech.glide.request.target.j<R> target, @Nullable e<R> targetListener, @Nullable List<e<R>> requestListeners, d requestCoordinator, com.bumptech.glide.load.engine.j engine, c<? super R> animationFactory, Executor callbackExecutor) {
        this.b = a ? String.valueOf(super.hashCode()) : null;
        this.c = b.a();
        this.d = requestLock;
        this.g = context;
        this.h = glideContext;
        this.i = model;
        this.j = transcodeClass;
        this.k = requestOptions;
        this.l = overrideWidth;
        this.m = overrideHeight;
        this.n = priority;
        this.o = target;
        this.e = targetListener;
        this.p = requestListeners;
        this.f = requestCoordinator;
        this.v = engine;
        this.q = animationFactory;
        this.r = callbackExecutor;
        this.w = a.PENDING;
        if (this.D == null && glideContext.g().a(c.C0020c.class)) {
            this.D = new RuntimeException("Glide request origin trace");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x009f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void h() {
        /*
            r5 = this;
            java.lang.Object r0 = r5.d
            monitor-enter(r0)
            r5.i()     // Catch:{ all -> 0x00a8 }
            com.bumptech.glide.util.pool.b r1 = r5.c     // Catch:{ all -> 0x00a8 }
            r1.c()     // Catch:{ all -> 0x00a8 }
            long r1 = com.bumptech.glide.util.e.b()     // Catch:{ all -> 0x00a8 }
            r5.u = r1     // Catch:{ all -> 0x00a8 }
            java.lang.Object r1 = r5.i     // Catch:{ all -> 0x00a8 }
            if (r1 != 0) goto L_0x003c
            int r1 = r5.l     // Catch:{ all -> 0x00a8 }
            int r2 = r5.m     // Catch:{ all -> 0x00a8 }
            boolean r1 = com.bumptech.glide.util.j.t(r1, r2)     // Catch:{ all -> 0x00a8 }
            if (r1 == 0) goto L_0x0027
            int r1 = r5.l     // Catch:{ all -> 0x00a8 }
            r5.A = r1     // Catch:{ all -> 0x00a8 }
            int r1 = r5.m     // Catch:{ all -> 0x00a8 }
            r5.B = r1     // Catch:{ all -> 0x00a8 }
        L_0x0027:
            android.graphics.drawable.Drawable r1 = r5.o()     // Catch:{ all -> 0x00a8 }
            if (r1 != 0) goto L_0x002f
            r1 = 5
            goto L_0x0030
        L_0x002f:
            r1 = 3
        L_0x0030:
            com.bumptech.glide.load.engine.GlideException r2 = new com.bumptech.glide.load.engine.GlideException     // Catch:{ all -> 0x00a8 }
            java.lang.String r3 = "Received null model"
            r2.<init>(r3)     // Catch:{ all -> 0x00a8 }
            r5.x(r2, r1)     // Catch:{ all -> 0x00a8 }
            monitor-exit(r0)     // Catch:{ all -> 0x00a8 }
            return
        L_0x003c:
            com.bumptech.glide.request.h$a r1 = r5.w     // Catch:{ all -> 0x00a8 }
            com.bumptech.glide.request.h$a r2 = com.bumptech.glide.request.h.a.RUNNING     // Catch:{ all -> 0x00a8 }
            if (r1 == r2) goto L_0x00a0
            com.bumptech.glide.request.h$a r3 = com.bumptech.glide.request.h.a.COMPLETE     // Catch:{ all -> 0x00a8 }
            if (r1 != r3) goto L_0x0050
            com.bumptech.glide.load.engine.t<R> r1 = r5.s     // Catch:{ all -> 0x00a8 }
            com.bumptech.glide.load.a r2 = com.bumptech.glide.load.a.MEMORY_CACHE     // Catch:{ all -> 0x00a8 }
            r3 = 0
            r5.b(r1, r2, r3)     // Catch:{ all -> 0x00a8 }
            monitor-exit(r0)     // Catch:{ all -> 0x00a8 }
            return
        L_0x0050:
            com.bumptech.glide.request.h$a r1 = com.bumptech.glide.request.h.a.WAITING_FOR_SIZE     // Catch:{ all -> 0x00a8 }
            r5.w = r1     // Catch:{ all -> 0x00a8 }
            int r3 = r5.l     // Catch:{ all -> 0x00a8 }
            int r4 = r5.m     // Catch:{ all -> 0x00a8 }
            boolean r3 = com.bumptech.glide.util.j.t(r3, r4)     // Catch:{ all -> 0x00a8 }
            if (r3 == 0) goto L_0x0066
            int r3 = r5.l     // Catch:{ all -> 0x00a8 }
            int r4 = r5.m     // Catch:{ all -> 0x00a8 }
            r5.d(r3, r4)     // Catch:{ all -> 0x00a8 }
            goto L_0x006b
        L_0x0066:
            com.bumptech.glide.request.target.j<R> r3 = r5.o     // Catch:{ all -> 0x00a8 }
            r3.g(r5)     // Catch:{ all -> 0x00a8 }
        L_0x006b:
            com.bumptech.glide.request.h$a r3 = r5.w     // Catch:{ all -> 0x00a8 }
            if (r3 == r2) goto L_0x0071
            if (r3 != r1) goto L_0x0080
        L_0x0071:
            boolean r1 = r5.k()     // Catch:{ all -> 0x00a8 }
            if (r1 == 0) goto L_0x0080
            com.bumptech.glide.request.target.j<R> r1 = r5.o     // Catch:{ all -> 0x00a8 }
            android.graphics.drawable.Drawable r2 = r5.p()     // Catch:{ all -> 0x00a8 }
            r1.b(r2)     // Catch:{ all -> 0x00a8 }
        L_0x0080:
            boolean r1 = a     // Catch:{ all -> 0x00a8 }
            if (r1 == 0) goto L_0x009e
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a8 }
            r1.<init>()     // Catch:{ all -> 0x00a8 }
            java.lang.String r2 = "finished run method in "
            r1.append(r2)     // Catch:{ all -> 0x00a8 }
            long r2 = r5.u     // Catch:{ all -> 0x00a8 }
            double r2 = com.bumptech.glide.util.e.a(r2)     // Catch:{ all -> 0x00a8 }
            r1.append(r2)     // Catch:{ all -> 0x00a8 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00a8 }
            r5.s(r1)     // Catch:{ all -> 0x00a8 }
        L_0x009e:
            monitor-exit(r0)     // Catch:{ all -> 0x00a8 }
            return
        L_0x00a0:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x00a8 }
            java.lang.String r2 = "Cannot restart a running request"
            r1.<init>(r2)     // Catch:{ all -> 0x00a8 }
            throw r1     // Catch:{ all -> 0x00a8 }
        L_0x00a8:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00a8 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.h.h():void");
    }

    @GuardedBy("requestLock")
    private void m() {
        i();
        this.c.c();
        this.o.a(this);
        j.d dVar = this.t;
        if (dVar != null) {
            dVar.a();
            this.t = null;
        }
    }

    @GuardedBy("requestLock")
    private void i() {
        if (this.C) {
            throw new IllegalStateException("You can't start or clear loads in RequestListener or Target callbacks. If you're trying to start a fallback request when a load fails, use RequestBuilder#error(RequestBuilder). Otherwise consider posting your into() or clear() calls to the main thread using a Handler instead.");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0031, code lost:
        if (r0 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0033, code lost:
        r5.v.k(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void clear() {
        /*
            r5 = this;
            r0 = 0
            java.lang.Object r1 = r5.d
            monitor-enter(r1)
            r5.i()     // Catch:{ all -> 0x0039 }
            com.bumptech.glide.util.pool.b r2 = r5.c     // Catch:{ all -> 0x0039 }
            r2.c()     // Catch:{ all -> 0x0039 }
            com.bumptech.glide.request.h$a r2 = r5.w     // Catch:{ all -> 0x0039 }
            com.bumptech.glide.request.h$a r3 = com.bumptech.glide.request.h.a.CLEARED     // Catch:{ all -> 0x0039 }
            if (r2 != r3) goto L_0x0014
            monitor-exit(r1)     // Catch:{ all -> 0x0039 }
            return
        L_0x0014:
            r5.m()     // Catch:{ all -> 0x0039 }
            com.bumptech.glide.load.engine.t<R> r2 = r5.s     // Catch:{ all -> 0x0039 }
            if (r2 == 0) goto L_0x001f
            r0 = r2
            r2 = 0
            r5.s = r2     // Catch:{ all -> 0x0039 }
        L_0x001f:
            boolean r2 = r5.j()     // Catch:{ all -> 0x0039 }
            if (r2 == 0) goto L_0x002e
            com.bumptech.glide.request.target.j<R> r2 = r5.o     // Catch:{ all -> 0x0039 }
            android.graphics.drawable.Drawable r4 = r5.p()     // Catch:{ all -> 0x0039 }
            r2.c(r4)     // Catch:{ all -> 0x0039 }
        L_0x002e:
            r5.w = r3     // Catch:{ all -> 0x0039 }
            monitor-exit(r1)     // Catch:{ all -> 0x0039 }
            if (r0 == 0) goto L_0x0038
            com.bumptech.glide.load.engine.j r1 = r5.v
            r1.k(r0)
        L_0x0038:
            return
        L_0x0039:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0039 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.h.clear():void");
    }

    public void pause() {
        synchronized (this.d) {
            if (isRunning()) {
                clear();
            }
        }
    }

    public boolean isRunning() {
        boolean z2;
        synchronized (this.d) {
            a aVar = this.w;
            if (aVar != a.RUNNING) {
                if (aVar != a.WAITING_FOR_SIZE) {
                    z2 = false;
                }
            }
            z2 = true;
        }
        return z2;
    }

    public boolean isComplete() {
        boolean z2;
        synchronized (this.d) {
            z2 = this.w == a.COMPLETE;
        }
        return z2;
    }

    public boolean e() {
        boolean z2;
        synchronized (this.d) {
            z2 = this.w == a.CLEARED;
        }
        return z2;
    }

    public boolean a() {
        boolean z2;
        synchronized (this.d) {
            z2 = this.w == a.COMPLETE;
        }
        return z2;
    }

    @GuardedBy("requestLock")
    private Drawable n() {
        if (this.x == null) {
            Drawable n2 = this.k.n();
            this.x = n2;
            if (n2 == null && this.k.m() > 0) {
                this.x = r(this.k.m());
            }
        }
        return this.x;
    }

    @GuardedBy("requestLock")
    private Drawable p() {
        if (this.y == null) {
            Drawable u2 = this.k.u();
            this.y = u2;
            if (u2 == null && this.k.v() > 0) {
                this.y = r(this.k.v());
            }
        }
        return this.y;
    }

    @GuardedBy("requestLock")
    private Drawable o() {
        if (this.z == null) {
            Drawable o2 = this.k.o();
            this.z = o2;
            if (o2 == null && this.k.p() > 0) {
                this.z = r(this.k.p());
            }
        }
        return this.z;
    }

    @GuardedBy("requestLock")
    private Drawable r(@DrawableRes int resourceId) {
        return com.bumptech.glide.load.resource.drawable.a.a(this.h, resourceId, this.k.B() != null ? this.k.B() : this.g.getTheme());
    }

    @GuardedBy("requestLock")
    private void z() {
        if (k()) {
            Drawable error = null;
            if (this.i == null) {
                error = o();
            }
            if (error == null) {
                error = n();
            }
            if (error == null) {
                error = p();
            }
            this.o.f(error);
        }
    }

    public void d(int width, int height) {
        Object obj;
        this.c.c();
        Object obj2 = this.d;
        synchronized (obj2) {
            try {
                boolean z2 = a;
                if (z2) {
                    s("Got onSizeReady in " + e.a(this.u));
                }
                if (this.w == a.WAITING_FOR_SIZE) {
                    a aVar = a.RUNNING;
                    this.w = aVar;
                    float sizeMultiplier = this.k.A();
                    this.A = t(width, sizeMultiplier);
                    this.B = t(height, sizeMultiplier);
                    if (z2) {
                        s("finished setup for calling load in " + e.a(this.u));
                    }
                    float sizeMultiplier2 = sizeMultiplier;
                    a aVar2 = aVar;
                    boolean z3 = z2;
                    float f2 = sizeMultiplier2;
                    a aVar3 = aVar2;
                    obj = obj2;
                    try {
                        this.t = this.v.f(this.h, this.i, this.k.z(), this.A, this.B, this.k.y(), this.j, this.n, this.k.l(), this.k.C(), this.k.O(), this.k.K(), this.k.r(), this.k.H(), this.k.F(), this.k.D(), this.k.q(), this, this.r);
                        if (this.w != aVar3) {
                            this.t = null;
                        }
                        if (z3) {
                            s("finished onSizeReady in " + e.a(this.u));
                        }
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                obj = obj2;
                throw th;
            }
        }
    }

    private static int t(int size, float sizeMultiplier) {
        return size == Integer.MIN_VALUE ? size : Math.round(((float) size) * sizeMultiplier);
    }

    @GuardedBy("requestLock")
    private boolean l() {
        d dVar = this.f;
        return dVar == null || dVar.c(this);
    }

    @GuardedBy("requestLock")
    private boolean j() {
        d dVar = this.f;
        return dVar == null || dVar.i(this);
    }

    @GuardedBy("requestLock")
    private boolean k() {
        d dVar = this.f;
        return dVar == null || dVar.b(this);
    }

    @GuardedBy("requestLock")
    private boolean q() {
        d dVar = this.f;
        return dVar == null || !dVar.getRoot().a();
    }

    @GuardedBy("requestLock")
    private void v() {
        d dVar = this.f;
        if (dVar != null) {
            dVar.f(this);
        }
    }

    @GuardedBy("requestLock")
    private void u() {
        d dVar = this.f;
        if (dVar != null) {
            dVar.d(this);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002f, code lost:
        if (0 == 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0031, code lost:
        r6.v.k((com.bumptech.glide.load.engine.t<?>) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0063, code lost:
        if (0 == 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0065, code lost:
        r6.v.k((com.bumptech.glide.load.engine.t<?>) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(com.bumptech.glide.load.engine.t<?> r7, com.bumptech.glide.load.a r8, boolean r9) {
        /*
            r6 = this;
            com.bumptech.glide.util.pool.b r0 = r6.c
            r0.c()
            r0 = 0
            java.lang.Object r1 = r6.d     // Catch:{ all -> 0x00c7 }
            monitor-enter(r1)     // Catch:{ all -> 0x00c7 }
            r2 = 0
            r6.t = r2     // Catch:{ all -> 0x00c4 }
            if (r7 != 0) goto L_0x0037
            com.bumptech.glide.load.engine.GlideException r2 = new com.bumptech.glide.load.engine.GlideException     // Catch:{ all -> 0x00c4 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c4 }
            r3.<init>()     // Catch:{ all -> 0x00c4 }
            java.lang.String r4 = "Expected to receive a Resource<R> with an object of "
            r3.append(r4)     // Catch:{ all -> 0x00c4 }
            java.lang.Class<R> r4 = r6.j     // Catch:{ all -> 0x00c4 }
            r3.append(r4)     // Catch:{ all -> 0x00c4 }
            java.lang.String r4 = " inside, but instead got null."
            r3.append(r4)     // Catch:{ all -> 0x00c4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00c4 }
            r2.<init>(r3)     // Catch:{ all -> 0x00c4 }
            r6.c(r2)     // Catch:{ all -> 0x00c4 }
            monitor-exit(r1)     // Catch:{ all -> 0x00c4 }
            if (r0 == 0) goto L_0x0036
            com.bumptech.glide.load.engine.j r1 = r6.v
            r1.k(r0)
        L_0x0036:
            return
        L_0x0037:
            java.lang.Object r3 = r7.get()     // Catch:{ all -> 0x00c4 }
            if (r3 == 0) goto L_0x006b
            java.lang.Class<R> r4 = r6.j     // Catch:{ all -> 0x00c4 }
            java.lang.Class r5 = r3.getClass()     // Catch:{ all -> 0x00c4 }
            boolean r4 = r4.isAssignableFrom(r5)     // Catch:{ all -> 0x00c4 }
            if (r4 != 0) goto L_0x004a
            goto L_0x006b
        L_0x004a:
            boolean r4 = r6.l()     // Catch:{ all -> 0x00c4 }
            if (r4 != 0) goto L_0x005f
            r0 = r7
            r6.s = r2     // Catch:{ all -> 0x00c4 }
            com.bumptech.glide.request.h$a r2 = com.bumptech.glide.request.h.a.COMPLETE     // Catch:{ all -> 0x00c4 }
            r6.w = r2     // Catch:{ all -> 0x00c4 }
            monitor-exit(r1)     // Catch:{ all -> 0x00c4 }
            com.bumptech.glide.load.engine.j r1 = r6.v
            r1.k(r0)
            return
        L_0x005f:
            r6.y(r7, r3, r8, r9)     // Catch:{ all -> 0x00c4 }
            monitor-exit(r1)     // Catch:{ all -> 0x00c4 }
            if (r0 == 0) goto L_0x006a
            com.bumptech.glide.load.engine.j r1 = r6.v
            r1.k(r0)
        L_0x006a:
            return
        L_0x006b:
            r0 = r7
            r6.s = r2     // Catch:{ all -> 0x00c4 }
            com.bumptech.glide.load.engine.GlideException r2 = new com.bumptech.glide.load.engine.GlideException     // Catch:{ all -> 0x00c4 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c4 }
            r4.<init>()     // Catch:{ all -> 0x00c4 }
            java.lang.String r5 = "Expected to receive an object of "
            r4.append(r5)     // Catch:{ all -> 0x00c4 }
            java.lang.Class<R> r5 = r6.j     // Catch:{ all -> 0x00c4 }
            r4.append(r5)     // Catch:{ all -> 0x00c4 }
            java.lang.String r5 = " but instead got "
            r4.append(r5)     // Catch:{ all -> 0x00c4 }
            if (r3 == 0) goto L_0x008b
            java.lang.Class r5 = r3.getClass()     // Catch:{ all -> 0x00c4 }
            goto L_0x008d
        L_0x008b:
            java.lang.String r5 = ""
        L_0x008d:
            r4.append(r5)     // Catch:{ all -> 0x00c4 }
            java.lang.String r5 = "{"
            r4.append(r5)     // Catch:{ all -> 0x00c4 }
            r4.append(r3)     // Catch:{ all -> 0x00c4 }
            java.lang.String r5 = "} inside Resource{"
            r4.append(r5)     // Catch:{ all -> 0x00c4 }
            r4.append(r7)     // Catch:{ all -> 0x00c4 }
            java.lang.String r5 = "}."
            r4.append(r5)     // Catch:{ all -> 0x00c4 }
            if (r3 == 0) goto L_0x00ad
            java.lang.String r5 = ""
            goto L_0x00af
        L_0x00ad:
            java.lang.String r5 = " To indicate failure return a null Resource object, rather than a Resource object containing null data."
        L_0x00af:
            r4.append(r5)     // Catch:{ all -> 0x00c4 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00c4 }
            r2.<init>(r4)     // Catch:{ all -> 0x00c4 }
            r6.c(r2)     // Catch:{ all -> 0x00c4 }
            monitor-exit(r1)     // Catch:{ all -> 0x00c4 }
            com.bumptech.glide.load.engine.j r1 = r6.v
            r1.k(r0)
            return
        L_0x00c4:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00c4 }
            throw r2     // Catch:{ all -> 0x00c7 }
        L_0x00c7:
            r1 = move-exception
            if (r0 == 0) goto L_0x00cf
            com.bumptech.glide.load.engine.j r2 = r6.v
            r2.k(r0)
        L_0x00cf:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.h.b(com.bumptech.glide.load.engine.t, com.bumptech.glide.load.a, boolean):void");
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00aa A[Catch:{ all -> 0x00bc }] */
    @androidx.annotation.GuardedBy("requestLock")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void y(com.bumptech.glide.load.engine.t<R> r12, R r13, com.bumptech.glide.load.a r14, boolean r15) {
        /*
            r11 = this;
            boolean r6 = r11.q()
            com.bumptech.glide.request.h$a r0 = com.bumptech.glide.request.h.a.COMPLETE
            r11.w = r0
            r11.s = r12
            com.bumptech.glide.d r0 = r11.h
            int r0 = r0.h()
            r1 = 3
            if (r0 > r1) goto L_0x006b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Finished loading "
            r0.append(r1)
            java.lang.Class r1 = r13.getClass()
            java.lang.String r1 = r1.getSimpleName()
            r0.append(r1)
            java.lang.String r1 = " from "
            r0.append(r1)
            r0.append(r14)
            java.lang.String r1 = " for "
            r0.append(r1)
            java.lang.Object r1 = r11.i
            r0.append(r1)
            java.lang.String r1 = " with size ["
            r0.append(r1)
            int r1 = r11.A
            r0.append(r1)
            java.lang.String r1 = "x"
            r0.append(r1)
            int r1 = r11.B
            r0.append(r1)
            java.lang.String r1 = "] in "
            r0.append(r1)
            long r1 = r11.u
            double r1 = com.bumptech.glide.util.e.a(r1)
            r0.append(r1)
            java.lang.String r1 = " ms"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "Glide"
            android.util.Log.d(r1, r0)
        L_0x006b:
            r7 = 1
            r11.C = r7
            r0 = 0
            r8 = 0
            java.util.List<com.bumptech.glide.request.e<R>> r1 = r11.p     // Catch:{ all -> 0x00bc }
            if (r1 == 0) goto L_0x0092
            java.util.Iterator r9 = r1.iterator()     // Catch:{ all -> 0x00bc }
            r10 = r0
        L_0x0079:
            boolean r0 = r9.hasNext()     // Catch:{ all -> 0x00bc }
            if (r0 == 0) goto L_0x0093
            java.lang.Object r0 = r9.next()     // Catch:{ all -> 0x00bc }
            com.bumptech.glide.request.e r0 = (com.bumptech.glide.request.e) r0     // Catch:{ all -> 0x00bc }
            java.lang.Object r2 = r11.i     // Catch:{ all -> 0x00bc }
            com.bumptech.glide.request.target.j<R> r3 = r11.o     // Catch:{ all -> 0x00bc }
            r1 = r13
            r4 = r14
            r5 = r6
            boolean r1 = r0.onResourceReady(r1, r2, r3, r4, r5)     // Catch:{ all -> 0x00bc }
            r10 = r10 | r1
            goto L_0x0079
        L_0x0092:
            r10 = r0
        L_0x0093:
            com.bumptech.glide.request.e<R> r0 = r11.e     // Catch:{ all -> 0x00bc }
            if (r0 == 0) goto L_0x00a5
            java.lang.Object r2 = r11.i     // Catch:{ all -> 0x00bc }
            com.bumptech.glide.request.target.j<R> r3 = r11.o     // Catch:{ all -> 0x00bc }
            r1 = r13
            r4 = r14
            r5 = r6
            boolean r0 = r0.onResourceReady(r1, r2, r3, r4, r5)     // Catch:{ all -> 0x00bc }
            if (r0 == 0) goto L_0x00a5
            goto L_0x00a6
        L_0x00a5:
            r7 = r8
        L_0x00a6:
            r0 = r10 | r7
            if (r0 != 0) goto L_0x00b5
            com.bumptech.glide.request.transition.c<? super R> r1 = r11.q     // Catch:{ all -> 0x00bc }
            com.bumptech.glide.request.transition.b r1 = r1.a(r14, r6)     // Catch:{ all -> 0x00bc }
            com.bumptech.glide.request.target.j<R> r2 = r11.o     // Catch:{ all -> 0x00bc }
            r2.d(r13, r1)     // Catch:{ all -> 0x00bc }
        L_0x00b5:
            r11.C = r8
            r11.v()
            return
        L_0x00bc:
            r0 = move-exception
            r11.C = r8
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.h.y(com.bumptech.glide.load.engine.t, java.lang.Object, com.bumptech.glide.load.a, boolean):void");
    }

    public void c(GlideException e2) {
        x(e2, 5);
    }

    public Object f() {
        this.c.c();
        return this.d;
    }

    /* JADX INFO: finally extract failed */
    private void x(GlideException e2, int maxLogLevel) {
        this.c.c();
        synchronized (this.d) {
            e2.setOrigin(this.D);
            int logLevel = this.h.h();
            if (logLevel <= maxLogLevel) {
                Log.w("Glide", "Load failed for " + this.i + " with size [" + this.A + "x" + this.B + "]", e2);
                if (logLevel <= 4) {
                    e2.logRootCauses("Glide");
                }
            }
            this.t = null;
            this.w = a.FAILED;
            boolean z2 = true;
            this.C = true;
            boolean anyListenerHandledUpdatingTarget = false;
            try {
                List<e<R>> list = this.p;
                if (list != null) {
                    Iterator<e<R>> it = list.iterator();
                    while (it.hasNext()) {
                        anyListenerHandledUpdatingTarget |= ((e) it.next()).onLoadFailed(e2, this.i, this.o, q());
                    }
                }
                e<R> eVar = this.e;
                if (eVar == null || !eVar.onLoadFailed(e2, this.i, this.o, q())) {
                    z2 = false;
                }
                if (!z2 && !anyListenerHandledUpdatingTarget) {
                    z();
                }
                this.C = false;
                u();
            } catch (Throwable th) {
                this.C = false;
                throw th;
            }
        }
    }

    public boolean g(c o2) {
        int localOverrideWidth;
        int localOverrideHeight;
        Object localModel;
        Class<R> cls;
        a<?> aVar;
        g localPriority;
        int localListenerCount;
        int otherLocalOverrideWidth;
        int otherLocalOverrideHeight;
        Object otherLocalModel;
        Class<R> cls2;
        BaseRequestOptions<?> otherLocalRequestOptions;
        g otherLocalPriority;
        int otherLocalListenerCount;
        c cVar = o2;
        if (!(cVar instanceof h)) {
            return false;
        }
        synchronized (this.d) {
            localOverrideWidth = this.l;
            localOverrideHeight = this.m;
            localModel = this.i;
            cls = this.j;
            aVar = this.k;
            localPriority = this.n;
            List<e<R>> list = this.p;
            localListenerCount = list != null ? list.size() : 0;
        }
        SingleRequest<?> other = (h) cVar;
        synchronized (other.d) {
            otherLocalOverrideWidth = other.l;
            otherLocalOverrideHeight = other.m;
            otherLocalModel = other.i;
            cls2 = other.j;
            otherLocalRequestOptions = other.k;
            otherLocalPriority = other.n;
            List<e<R>> list2 = other.p;
            otherLocalListenerCount = list2 != null ? list2.size() : 0;
        }
        return localOverrideWidth == otherLocalOverrideWidth && localOverrideHeight == otherLocalOverrideHeight && com.bumptech.glide.util.j.b(localModel, otherLocalModel) && cls.equals(cls2) && aVar.equals(otherLocalRequestOptions) && localPriority == otherLocalPriority && localListenerCount == otherLocalListenerCount;
    }

    private void s(String message) {
        Log.v("Request", message + " this: " + this.b);
    }
}
