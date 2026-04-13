package io.reactivex.internal.disposables;

import io.reactivex.disposables.b;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.a;
import io.reactivex.internal.util.f;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* compiled from: ListCompositeDisposable */
public final class e implements b, b {
    List<b> c;
    volatile boolean d;

    public void dispose() {
        if (!this.d) {
            synchronized (this) {
                if (!this.d) {
                    this.d = true;
                    List<b> list = this.c;
                    this.c = null;
                    d(list);
                }
            }
        }
    }

    public boolean isDisposed() {
        return this.d;
    }

    public boolean b(b d2) {
        io.reactivex.internal.functions.b.e(d2, "d is null");
        if (!this.d) {
            synchronized (this) {
                if (!this.d) {
                    List<b> list = this.c;
                    if (list == null) {
                        list = new LinkedList<>();
                        this.c = list;
                    }
                    list.add(d2);
                    return true;
                }
            }
        }
        d2.dispose();
        return false;
    }

    public boolean a(b d2) {
        if (!c(d2)) {
            return false;
        }
        d2.dispose();
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0021, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean c(io.reactivex.disposables.b r4) {
        /*
            r3 = this;
            java.lang.String r0 = "Disposable item is null"
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
            java.util.List<io.reactivex.disposables.b> r0 = r3.c     // Catch:{ all -> 0x0022 }
            if (r0 == 0) goto L_0x0020
            boolean r2 = r0.remove(r4)     // Catch:{ all -> 0x0022 }
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
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.disposables.e.c(io.reactivex.disposables.b):boolean");
    }

    /* access modifiers changed from: package-private */
    public void d(List<b> set) {
        if (set != null) {
            List<Throwable> errors = null;
            for (b o : set) {
                try {
                    o.dispose();
                } catch (Throwable ex) {
                    a.b(ex);
                    if (errors == null) {
                        errors = new ArrayList<>();
                    }
                    errors.add(ex);
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
