package io.reactivex.processors;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.queue.c;
import io.reactivex.internal.subscriptions.f;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.b;

/* compiled from: UnicastProcessor */
public final class e<T> extends a<T> {
    final AtomicBoolean a1;
    final AtomicLong a2;
    final c<T> d;
    final AtomicReference<Runnable> f;
    volatile boolean p0;
    final io.reactivex.internal.subscriptions.a<T> p1;
    boolean p2;
    final boolean q;
    volatile boolean x;
    Throwable y;
    final AtomicReference<b<? super T>> z;

    public static <T> e<T> Y(int capacityHint) {
        return new e<>(capacityHint);
    }

    e(int capacityHint) {
        this(capacityHint, (Runnable) null, true);
    }

    e(int capacityHint, Runnable onTerminate, boolean delayError) {
        this.d = new c<>(io.reactivex.internal.functions.b.f(capacityHint, "capacityHint"));
        this.f = new AtomicReference<>(onTerminate);
        this.q = delayError;
        this.z = new AtomicReference<>();
        this.a1 = new AtomicBoolean();
        this.p1 = new a();
        this.a2 = new AtomicLong();
    }

    /* access modifiers changed from: package-private */
    public void Z() {
        Runnable r = this.f.getAndSet((Object) null);
        if (r != null) {
            r.run();
        }
    }

    /* access modifiers changed from: package-private */
    public void c0(b<? super T> a3) {
        c<T> cVar = this.d;
        boolean failFast = !this.q;
        int missed = 1;
        do {
            long r = this.a2.get();
            long e = 0;
            while (true) {
                if (r == e) {
                    b<? super T> bVar = a3;
                    break;
                }
                boolean d2 = this.x;
                T t = cVar.poll();
                boolean empty = t == null;
                if (!X(failFast, d2, empty, a3, cVar)) {
                    if (empty) {
                        b<? super T> bVar2 = a3;
                        break;
                    } else {
                        a3.onNext(t);
                        e++;
                    }
                } else {
                    return;
                }
            }
            if (r == e) {
                if (X(failFast, this.x, cVar.isEmpty(), a3, cVar)) {
                    return;
                }
            }
            if (!(e == 0 || r == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE)) {
                this.a2.addAndGet(-e);
            }
            missed = this.p1.addAndGet(-missed);
        } while (missed != 0);
    }

    /* access modifiers changed from: package-private */
    public void b0(b<? super T> a3) {
        int missed = 1;
        SpscLinkedArrayQueue<T> q2 = this.d;
        boolean failFast = !this.q;
        while (!this.p0) {
            boolean d2 = this.x;
            if (!failFast || !d2 || this.y == null) {
                a3.onNext(null);
                if (d2) {
                    this.z.lazySet((Object) null);
                    Throwable ex = this.y;
                    if (ex != null) {
                        a3.onError(ex);
                        return;
                    } else {
                        a3.onComplete();
                        return;
                    }
                } else {
                    missed = this.p1.addAndGet(-missed);
                    if (missed == 0) {
                        return;
                    }
                }
            } else {
                q2.clear();
                this.z.lazySet((Object) null);
                a3.onError(this.y);
                return;
            }
        }
        q2.clear();
        this.z.lazySet((Object) null);
    }

    /* access modifiers changed from: package-private */
    public void a0() {
        if (this.p1.getAndIncrement() == 0) {
            int missed = 1;
            Subscriber<? super T> a3 = (b) this.z.get();
            while (a3 == null) {
                missed = this.p1.addAndGet(-missed);
                if (missed != 0) {
                    a3 = (b) this.z.get();
                } else {
                    return;
                }
            }
            if (this.p2) {
                b0(a3);
            } else {
                c0(a3);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean X(boolean failFast, boolean d2, boolean empty, b<? super T> a3, c<T> q2) {
        if (this.p0) {
            q2.clear();
            this.z.lazySet((Object) null);
            return true;
        } else if (!d2) {
            return false;
        } else {
            if (failFast && this.y != null) {
                q2.clear();
                this.z.lazySet((Object) null);
                a3.onError(this.y);
                return true;
            } else if (!empty) {
                return false;
            } else {
                Throwable e = this.y;
                this.z.lazySet((Object) null);
                if (e != null) {
                    a3.onError(e);
                } else {
                    a3.onComplete();
                }
                return true;
            }
        }
    }

    public void onSubscribe(org.reactivestreams.c s) {
        if (this.x || this.p0) {
            s.cancel();
        } else {
            s.request(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
        }
    }

    public void onNext(T t) {
        io.reactivex.internal.functions.b.e(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (!this.x && !this.p0) {
            this.d.offer(t);
            a0();
        }
    }

    public void onError(Throwable t) {
        io.reactivex.internal.functions.b.e(t, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.x || this.p0) {
            io.reactivex.plugins.a.q(t);
            return;
        }
        this.y = t;
        this.x = true;
        Z();
        a0();
    }

    public void onComplete() {
        if (!this.x && !this.p0) {
            this.x = true;
            Z();
            a0();
        }
    }

    /* access modifiers changed from: protected */
    public void L(b<? super T> s) {
        if (this.a1.get() || !this.a1.compareAndSet(false, true)) {
            io.reactivex.internal.subscriptions.c.error(new IllegalStateException("This processor allows only a single Subscriber"), s);
            return;
        }
        s.onSubscribe(this.p1);
        this.z.set(s);
        if (this.p0) {
            this.z.lazySet((Object) null);
        } else {
            a0();
        }
    }

    /* compiled from: UnicastProcessor */
    public final class a extends io.reactivex.internal.subscriptions.a<T> {
        private static final long serialVersionUID = -4896760517184205454L;

        a() {
        }

        public T poll() {
            return e.this.d.poll();
        }

        public boolean isEmpty() {
            return e.this.d.isEmpty();
        }

        public void clear() {
            e.this.d.clear();
        }

        public int requestFusion(int requestedMode) {
            if ((requestedMode & 2) == 0) {
                return 0;
            }
            e.this.p2 = true;
            return 2;
        }

        public void request(long n) {
            if (f.validate(n)) {
                io.reactivex.internal.util.c.a(e.this.a2, n);
                e.this.a0();
            }
        }

        public void cancel() {
            if (!e.this.p0) {
                e.this.p0 = true;
                e.this.Z();
                e eVar = e.this;
                if (!eVar.p2 && eVar.p1.getAndIncrement() == 0) {
                    e.this.d.clear();
                    e.this.z.lazySet((Object) null);
                }
            }
        }
    }
}
