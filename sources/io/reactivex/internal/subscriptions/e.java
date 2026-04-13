package io.reactivex.internal.subscriptions;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.internal.functions.b;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.c;

/* compiled from: SubscriptionArbiter */
public class e extends AtomicInteger implements c {
    private static final long serialVersionUID = -2189523197179400958L;
    c actual;
    final boolean cancelOnReplace;
    volatile boolean cancelled;
    final AtomicLong missedProduced = new AtomicLong();
    final AtomicLong missedRequested = new AtomicLong();
    final AtomicReference<c> missedSubscription = new AtomicReference<>();
    long requested;
    protected boolean unbounded;

    public e(boolean cancelOnReplace2) {
        this.cancelOnReplace = cancelOnReplace2;
    }

    public final void setSubscription(c s) {
        if (this.cancelled) {
            s.cancel();
            return;
        }
        b.e(s, "s is null");
        if (get() != 0 || !compareAndSet(0, 1)) {
            c a = this.missedSubscription.getAndSet(s);
            if (a != null && this.cancelOnReplace) {
                a.cancel();
            }
            drain();
            return;
        }
        c a2 = this.actual;
        if (a2 != null && this.cancelOnReplace) {
            a2.cancel();
        }
        this.actual = s;
        long r = this.requested;
        if (decrementAndGet() != 0) {
            drainLoop();
        }
        if (r != 0) {
            s.request(r);
        }
    }

    public final void request(long n) {
        if (f.validate(n) && !this.unbounded) {
            if (get() != 0 || !compareAndSet(0, 1)) {
                io.reactivex.internal.util.c.a(this.missedRequested, n);
                drain();
                return;
            }
            long r = this.requested;
            if (r != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                long r2 = io.reactivex.internal.util.c.c(r, n);
                this.requested = r2;
                if (r2 == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                    this.unbounded = true;
                }
            }
            c a = this.actual;
            if (decrementAndGet() != 0) {
                drainLoop();
            }
            if (a != null) {
                a.request(n);
            }
        }
    }

    public final void produced(long n) {
        if (!this.unbounded) {
            if (get() != 0 || !compareAndSet(0, 1)) {
                io.reactivex.internal.util.c.a(this.missedProduced, n);
                drain();
                return;
            }
            long r = this.requested;
            if (r != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                long u = r - n;
                if (u < 0) {
                    f.reportMoreProduced(u);
                    u = 0;
                }
                this.requested = u;
            }
            if (decrementAndGet() != 0) {
                drainLoop();
            }
        }
    }

    public void cancel() {
        if (!this.cancelled) {
            this.cancelled = true;
            drain();
        }
    }

    /* access modifiers changed from: package-private */
    public final void drain() {
        if (getAndIncrement() == 0) {
            drainLoop();
        }
    }

    /* access modifiers changed from: package-private */
    public final void drainLoop() {
        int missed = 1;
        long requestAmount = 0;
        c requestTarget = null;
        do {
            c ms = this.missedSubscription.get();
            if (ms != null) {
                ms = this.missedSubscription.getAndSet((Object) null);
            }
            long mr = this.missedRequested.get();
            if (mr != 0) {
                mr = this.missedRequested.getAndSet(0);
            }
            long mp = this.missedProduced.get();
            if (mp != 0) {
                mp = this.missedProduced.getAndSet(0);
            }
            c a = this.actual;
            if (this.cancelled) {
                if (a != null) {
                    a.cancel();
                    this.actual = null;
                }
                if (ms != null) {
                    ms.cancel();
                }
            } else {
                long r = this.requested;
                if (r != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                    long u = io.reactivex.internal.util.c.c(r, mr);
                    if (u != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                        long v = u - mp;
                        if (v < 0) {
                            f.reportMoreProduced(v);
                            v = 0;
                        }
                        r = v;
                    } else {
                        r = u;
                    }
                    this.requested = r;
                }
                if (ms != null) {
                    if (a != null && this.cancelOnReplace) {
                        a.cancel();
                    }
                    this.actual = ms;
                    if (r != 0) {
                        requestAmount = io.reactivex.internal.util.c.c(requestAmount, r);
                        requestTarget = ms;
                    }
                } else if (!(a == null || mr == 0)) {
                    requestAmount = io.reactivex.internal.util.c.c(requestAmount, mr);
                    requestTarget = a;
                }
            }
            missed = addAndGet(-missed);
        } while (missed != 0);
        if (requestAmount != 0) {
            requestTarget.request(requestAmount);
        }
    }

    public final boolean isUnbounded() {
        return this.unbounded;
    }

    public final boolean isCancelled() {
        return this.cancelled;
    }
}
