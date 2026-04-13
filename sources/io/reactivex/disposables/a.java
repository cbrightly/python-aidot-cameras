package io.reactivex.disposables;

import io.reactivex.exceptions.CompositeException;
import io.reactivex.internal.disposables.b;
import io.reactivex.internal.util.OpenHashSet;
import io.reactivex.internal.util.f;
import io.reactivex.internal.util.i;
import java.util.ArrayList;
import java.util.List;

/* compiled from: CompositeDisposable */
public final class a implements b, b {
    i<b> c;
    volatile boolean d;

    public void dispose() {
        if (!this.d) {
            synchronized (this) {
                if (!this.d) {
                    this.d = true;
                    OpenHashSet<Disposable> set = this.c;
                    this.c = null;
                    e(set);
                }
            }
        }
    }

    public boolean isDisposed() {
        return this.d;
    }

    public boolean b(b disposable) {
        io.reactivex.internal.functions.b.e(disposable, "disposable is null");
        if (!this.d) {
            synchronized (this) {
                if (!this.d) {
                    OpenHashSet<Disposable> set = this.c;
                    if (set == null) {
                        set = new i<>();
                        this.c = set;
                    }
                    set.a(disposable);
                    return true;
                }
            }
        }
        disposable.dispose();
        return false;
    }

    public boolean a(b disposable) {
        if (!c(disposable)) {
            return false;
        }
        disposable.dispose();
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0021, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean c(io.reactivex.disposables.b r4) {
        /*
            r3 = this;
            java.lang.String r0 = "disposables is null"
            io.reactivex.internal.functions.b.e(r4, r0)
            boolean r0 = r3.d
            r1 = 0
            if (r0 == 0) goto L_0x000b
            return r1
        L_0x000b:
            monitor-enter(r3)
            boolean r0 = r3.d     // Catch:{ all -> 0x0022 }
            if (r0 == 0) goto L_0x0012
            monitor-exit(r3)     // Catch:{ all -> 0x0022 }
            return r1
        L_0x0012:
            io.reactivex.internal.util.i<io.reactivex.disposables.b> r0 = r3.c     // Catch:{ all -> 0x0022 }
            if (r0 == 0) goto L_0x0020
            boolean r2 = r0.e(r4)     // Catch:{ all -> 0x0022 }
            if (r2 != 0) goto L_0x001d
            goto L_0x0020
        L_0x001d:
            monitor-exit(r3)     // Catch:{ all -> 0x0022 }
            r0 = 1
            return r0
        L_0x0020:
            monitor-exit(r3)     // Catch:{ all -> 0x0022 }
            return r1
        L_0x0022:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0022 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.disposables.a.c(io.reactivex.disposables.b):boolean");
    }

    public void d() {
        if (!this.d) {
            synchronized (this) {
                if (!this.d) {
                    OpenHashSet<Disposable> set = this.c;
                    this.c = null;
                    e(set);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void e(i<b> set) {
        if (set != null) {
            List<Throwable> errors = null;
            for (Object o : set.b()) {
                if (o instanceof b) {
                    try {
                        ((b) o).dispose();
                    } catch (Throwable ex) {
                        io.reactivex.exceptions.a.b(ex);
                        if (errors == null) {
                            errors = new ArrayList<>();
                        }
                        errors.add(ex);
                    }
                }
            }
            if (errors == null) {
                return;
            }
            if (errors.size() == 1) {
                throw f.d(errors.get(0));
            }
            throw new CompositeException((Iterable<? extends Throwable>) errors);
        }
    }
}
