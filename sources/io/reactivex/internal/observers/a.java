package io.reactivex.internal.observers;

import io.reactivex.internal.disposables.c;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.b;
import io.reactivex.q;

/* compiled from: BasicFuseableObserver */
public abstract class a<T, R> implements q<T>, b<R> {
    protected final q<? super R> c;
    protected io.reactivex.disposables.b d;
    protected b<T> f;
    protected boolean q;
    protected int x;

    public a(q<? super R> downstream) {
        this.c = downstream;
    }

    public final void onSubscribe(io.reactivex.disposables.b d2) {
        if (c.validate(this.d, d2)) {
            this.d = d2;
            if (d2 instanceof b) {
                this.f = (b) d2;
            }
            if (b()) {
                this.c.onSubscribe(this);
                a();
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean b() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void a() {
    }

    public void onError(Throwable t) {
        if (this.q) {
            io.reactivex.plugins.a.q(t);
            return;
        }
        this.q = true;
        this.c.onError(t);
    }

    /* access modifiers changed from: protected */
    public final void c(Throwable t) {
        io.reactivex.exceptions.a.b(t);
        this.d.dispose();
        onError(t);
    }

    public void onComplete() {
        if (!this.q) {
            this.q = true;
            this.c.onComplete();
        }
    }

    /* access modifiers changed from: protected */
    public final int d(int mode) {
        QueueDisposable<T> qd = this.f;
        if (qd == null || (mode & 4) != 0) {
            return 0;
        }
        int m = qd.requestFusion(mode);
        if (m != 0) {
            this.x = m;
        }
        return m;
    }

    public void dispose() {
        this.d.dispose();
    }

    public boolean isDisposed() {
        return this.d.isDisposed();
    }

    public boolean isEmpty() {
        return this.f.isEmpty();
    }

    public void clear() {
        this.f.clear();
    }

    public final boolean offer(R r) {
        throw new UnsupportedOperationException("Should not be called!");
    }
}
