package com.bumptech.glide.request;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.d;

/* compiled from: ErrorRequestCoordinator */
public final class b implements d, c {
    private final Object a;
    @Nullable
    private final d b;
    private volatile c c;
    private volatile c d;
    @GuardedBy("requestLock")
    private d.a e;
    @GuardedBy("requestLock")
    private d.a f;

    public b(Object requestLock, @Nullable d parent) {
        d.a aVar = d.a.CLEARED;
        this.e = aVar;
        this.f = aVar;
        this.a = requestLock;
        this.b = parent;
    }

    public void n(c primary, c error) {
        this.c = primary;
        this.d = error;
    }

    public void h() {
        synchronized (this.a) {
            d.a aVar = this.e;
            d.a aVar2 = d.a.RUNNING;
            if (aVar != aVar2) {
                this.e = aVar2;
                this.c.h();
            }
        }
    }

    public void clear() {
        synchronized (this.a) {
            d.a aVar = d.a.CLEARED;
            this.e = aVar;
            this.c.clear();
            if (this.f != aVar) {
                this.f = aVar;
                this.d.clear();
            }
        }
    }

    public void pause() {
        synchronized (this.a) {
            d.a aVar = this.e;
            d.a aVar2 = d.a.RUNNING;
            if (aVar == aVar2) {
                this.e = d.a.PAUSED;
                this.c.pause();
            }
            if (this.f == aVar2) {
                this.f = d.a.PAUSED;
                this.d.pause();
            }
        }
    }

    public boolean isRunning() {
        boolean z;
        synchronized (this.a) {
            d.a aVar = this.e;
            d.a aVar2 = d.a.RUNNING;
            if (aVar != aVar2) {
                if (this.f != aVar2) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    public boolean isComplete() {
        boolean z;
        synchronized (this.a) {
            d.a aVar = this.e;
            d.a aVar2 = d.a.SUCCESS;
            if (aVar != aVar2) {
                if (this.f != aVar2) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    public boolean e() {
        boolean z;
        synchronized (this.a) {
            d.a aVar = this.e;
            d.a aVar2 = d.a.CLEARED;
            z = aVar == aVar2 && this.f == aVar2;
        }
        return z;
    }

    public boolean g(c o) {
        if (!(o instanceof b)) {
            return false;
        }
        b other = (b) o;
        if (!this.c.g(other.c) || !this.d.g(other.d)) {
            return false;
        }
        return true;
    }

    public boolean c(c request) {
        boolean z;
        synchronized (this.a) {
            z = m() && j(request);
        }
        return z;
    }

    @GuardedBy("requestLock")
    private boolean m() {
        d dVar = this.b;
        return dVar == null || dVar.c(this);
    }

    public boolean b(c request) {
        boolean z;
        synchronized (this.a) {
            z = l() && j(request);
        }
        return z;
    }

    public boolean i(c request) {
        boolean z;
        synchronized (this.a) {
            z = k() && j(request);
        }
        return z;
    }

    @GuardedBy("requestLock")
    private boolean k() {
        d dVar = this.b;
        return dVar == null || dVar.i(this);
    }

    @GuardedBy("requestLock")
    private boolean l() {
        d dVar = this.b;
        return dVar == null || dVar.b(this);
    }

    @GuardedBy("requestLock")
    private boolean j(c request) {
        return request.equals(this.c) || (this.e == d.a.FAILED && request.equals(this.d));
    }

    public boolean a() {
        boolean z;
        synchronized (this.a) {
            if (!this.c.a()) {
                if (!this.d.a()) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    public void f(c request) {
        synchronized (this.a) {
            if (request.equals(this.c)) {
                this.e = d.a.SUCCESS;
            } else if (request.equals(this.d)) {
                this.f = d.a.SUCCESS;
            }
            d dVar = this.b;
            if (dVar != null) {
                dVar.f(this);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002a, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001d, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void d(com.bumptech.glide.request.c r4) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.a
            monitor-enter(r0)
            com.bumptech.glide.request.c r1 = r3.d     // Catch:{ all -> 0x002b }
            boolean r1 = r4.equals(r1)     // Catch:{ all -> 0x002b }
            if (r1 != 0) goto L_0x001e
            com.bumptech.glide.request.d$a r1 = com.bumptech.glide.request.d.a.FAILED     // Catch:{ all -> 0x002b }
            r3.e = r1     // Catch:{ all -> 0x002b }
            com.bumptech.glide.request.d$a r1 = r3.f     // Catch:{ all -> 0x002b }
            com.bumptech.glide.request.d$a r2 = com.bumptech.glide.request.d.a.RUNNING     // Catch:{ all -> 0x002b }
            if (r1 == r2) goto L_0x001c
            r3.f = r2     // Catch:{ all -> 0x002b }
            com.bumptech.glide.request.c r1 = r3.d     // Catch:{ all -> 0x002b }
            r1.h()     // Catch:{ all -> 0x002b }
        L_0x001c:
            monitor-exit(r0)     // Catch:{ all -> 0x002b }
            return
        L_0x001e:
            com.bumptech.glide.request.d$a r1 = com.bumptech.glide.request.d.a.FAILED     // Catch:{ all -> 0x002b }
            r3.f = r1     // Catch:{ all -> 0x002b }
            com.bumptech.glide.request.d r1 = r3.b     // Catch:{ all -> 0x002b }
            if (r1 == 0) goto L_0x0029
            r1.d(r3)     // Catch:{ all -> 0x002b }
        L_0x0029:
            monitor-exit(r0)     // Catch:{ all -> 0x002b }
            return
        L_0x002b:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002b }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.b.d(com.bumptech.glide.request.c):void");
    }

    public d getRoot() {
        d root;
        synchronized (this.a) {
            d dVar = this.b;
            root = dVar != null ? dVar.getRoot() : this;
        }
        return root;
    }
}
