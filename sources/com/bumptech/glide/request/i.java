package com.bumptech.glide.request;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.d;

/* compiled from: ThumbnailRequestCoordinator */
public class i implements d, c {
    @Nullable
    private final d a;
    private final Object b;
    private volatile c c;
    private volatile c d;
    @GuardedBy("requestLock")
    private d.a e;
    @GuardedBy("requestLock")
    private d.a f;
    @GuardedBy("requestLock")
    private boolean g;

    public i(Object requestLock, @Nullable d parent) {
        d.a aVar = d.a.CLEARED;
        this.e = aVar;
        this.f = aVar;
        this.b = requestLock;
        this.a = parent;
    }

    public void m(c full, c thumb) {
        this.c = full;
        this.d = thumb;
    }

    public boolean c(c request) {
        boolean z;
        synchronized (this.b) {
            z = l() && (request.equals(this.c) || this.e != d.a.SUCCESS);
        }
        return z;
    }

    @GuardedBy("requestLock")
    private boolean l() {
        d dVar = this.a;
        return dVar == null || dVar.c(this);
    }

    public boolean b(c request) {
        boolean z;
        synchronized (this.b) {
            z = k() && request.equals(this.c) && !a();
        }
        return z;
    }

    public boolean i(c request) {
        boolean z;
        synchronized (this.b) {
            z = j() && request.equals(this.c) && this.e != d.a.PAUSED;
        }
        return z;
    }

    @GuardedBy("requestLock")
    private boolean j() {
        d dVar = this.a;
        return dVar == null || dVar.i(this);
    }

    @GuardedBy("requestLock")
    private boolean k() {
        d dVar = this.a;
        return dVar == null || dVar.b(this);
    }

    public boolean a() {
        boolean z;
        synchronized (this.b) {
            if (!this.d.a()) {
                if (!this.c.a()) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void f(com.bumptech.glide.request.c r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.b
            monitor-enter(r0)
            com.bumptech.glide.request.c r1 = r2.d     // Catch:{ all -> 0x002b }
            boolean r1 = r3.equals(r1)     // Catch:{ all -> 0x002b }
            if (r1 == 0) goto L_0x0011
            com.bumptech.glide.request.d$a r1 = com.bumptech.glide.request.d.a.SUCCESS     // Catch:{ all -> 0x002b }
            r2.f = r1     // Catch:{ all -> 0x002b }
            monitor-exit(r0)     // Catch:{ all -> 0x002b }
            return
        L_0x0011:
            com.bumptech.glide.request.d$a r1 = com.bumptech.glide.request.d.a.SUCCESS     // Catch:{ all -> 0x002b }
            r2.e = r1     // Catch:{ all -> 0x002b }
            com.bumptech.glide.request.d r1 = r2.a     // Catch:{ all -> 0x002b }
            if (r1 == 0) goto L_0x001c
            r1.f(r2)     // Catch:{ all -> 0x002b }
        L_0x001c:
            com.bumptech.glide.request.d$a r1 = r2.f     // Catch:{ all -> 0x002b }
            boolean r1 = r1.isComplete()     // Catch:{ all -> 0x002b }
            if (r1 != 0) goto L_0x0029
            com.bumptech.glide.request.c r1 = r2.d     // Catch:{ all -> 0x002b }
            r1.clear()     // Catch:{ all -> 0x002b }
        L_0x0029:
            monitor-exit(r0)     // Catch:{ all -> 0x002b }
            return
        L_0x002b:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002b }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.i.f(com.bumptech.glide.request.c):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001d, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void d(com.bumptech.glide.request.c r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.b
            monitor-enter(r0)
            com.bumptech.glide.request.c r1 = r2.c     // Catch:{ all -> 0x001e }
            boolean r1 = r3.equals(r1)     // Catch:{ all -> 0x001e }
            if (r1 != 0) goto L_0x0011
            com.bumptech.glide.request.d$a r1 = com.bumptech.glide.request.d.a.FAILED     // Catch:{ all -> 0x001e }
            r2.f = r1     // Catch:{ all -> 0x001e }
            monitor-exit(r0)     // Catch:{ all -> 0x001e }
            return
        L_0x0011:
            com.bumptech.glide.request.d$a r1 = com.bumptech.glide.request.d.a.FAILED     // Catch:{ all -> 0x001e }
            r2.e = r1     // Catch:{ all -> 0x001e }
            com.bumptech.glide.request.d r1 = r2.a     // Catch:{ all -> 0x001e }
            if (r1 == 0) goto L_0x001c
            r1.d(r2)     // Catch:{ all -> 0x001e }
        L_0x001c:
            monitor-exit(r0)     // Catch:{ all -> 0x001e }
            return
        L_0x001e:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x001e }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.i.d(com.bumptech.glide.request.c):void");
    }

    public d getRoot() {
        d root;
        synchronized (this.b) {
            d dVar = this.a;
            root = dVar != null ? dVar.getRoot() : this;
        }
        return root;
    }

    public void h() {
        d.a aVar;
        d.a aVar2;
        synchronized (this.b) {
            this.g = true;
            try {
                if (!(this.e == d.a.SUCCESS || this.f == (aVar2 = d.a.RUNNING))) {
                    this.f = aVar2;
                    this.d.h();
                }
                if (this.g && this.e != (aVar = d.a.RUNNING)) {
                    this.e = aVar;
                    this.c.h();
                }
            } finally {
                this.g = false;
            }
        }
    }

    public void clear() {
        synchronized (this.b) {
            this.g = false;
            d.a aVar = d.a.CLEARED;
            this.e = aVar;
            this.f = aVar;
            this.d.clear();
            this.c.clear();
        }
    }

    public void pause() {
        synchronized (this.b) {
            if (!this.f.isComplete()) {
                this.f = d.a.PAUSED;
                this.d.pause();
            }
            if (!this.e.isComplete()) {
                this.e = d.a.PAUSED;
                this.c.pause();
            }
        }
    }

    public boolean isRunning() {
        boolean z;
        synchronized (this.b) {
            z = this.e == d.a.RUNNING;
        }
        return z;
    }

    public boolean isComplete() {
        boolean z;
        synchronized (this.b) {
            z = this.e == d.a.SUCCESS;
        }
        return z;
    }

    public boolean e() {
        boolean z;
        synchronized (this.b) {
            z = this.e == d.a.CLEARED;
        }
        return z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x002e A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean g(com.bumptech.glide.request.c r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.bumptech.glide.request.i
            r1 = 0
            if (r0 == 0) goto L_0x0032
            r0 = r5
            com.bumptech.glide.request.i r0 = (com.bumptech.glide.request.i) r0
            com.bumptech.glide.request.c r2 = r4.c
            if (r2 != 0) goto L_0x0011
            com.bumptech.glide.request.c r2 = r0.c
            if (r2 != 0) goto L_0x0030
            goto L_0x001b
        L_0x0011:
            com.bumptech.glide.request.c r2 = r4.c
            com.bumptech.glide.request.c r3 = r0.c
            boolean r2 = r2.g(r3)
            if (r2 == 0) goto L_0x0030
        L_0x001b:
            com.bumptech.glide.request.c r2 = r4.d
            if (r2 != 0) goto L_0x0024
            com.bumptech.glide.request.c r2 = r0.d
            if (r2 != 0) goto L_0x0030
            goto L_0x002e
        L_0x0024:
            com.bumptech.glide.request.c r2 = r4.d
            com.bumptech.glide.request.c r3 = r0.d
            boolean r2 = r2.g(r3)
            if (r2 == 0) goto L_0x0030
        L_0x002e:
            r1 = 1
            goto L_0x0031
        L_0x0030:
        L_0x0031:
            return r1
        L_0x0032:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.i.g(com.bumptech.glide.request.c):boolean");
    }
}
