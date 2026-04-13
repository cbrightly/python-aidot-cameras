package io.reactivex.internal.subscribers;

import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.d;
import io.reactivex.internal.subscriptions.f;
import org.reactivestreams.c;

/* compiled from: BasicFuseableConditionalSubscriber */
public abstract class a<T, R> implements io.reactivex.internal.fuseable.a<T>, d<R> {
    protected final io.reactivex.internal.fuseable.a<? super R> c;
    protected c d;
    protected d<T> f;
    protected boolean q;
    protected int x;

    public a(io.reactivex.internal.fuseable.a<? super R> downstream) {
        this.c = downstream;
    }

    public final void onSubscribe(c s) {
        if (f.validate(this.d, s)) {
            this.d = s;
            if (s instanceof d) {
                this.f = (d) s;
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
        this.d.cancel();
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
        QueueSubscription<T> qs = this.f;
        if (qs == null || (mode & 4) != 0) {
            return 0;
        }
        int m = qs.requestFusion(mode);
        if (m != 0) {
            this.x = m;
        }
        return m;
    }

    public void request(long n) {
        this.d.request(n);
    }

    public void cancel() {
        this.d.cancel();
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
