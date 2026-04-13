package io.reactivex.processors;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.internal.subscriptions.f;
import io.reactivex.processors.ReplayProcessor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;

/* compiled from: ReplayProcessor */
public final class c<T> extends a<T> {
    private static final Object[] d = new Object[0];
    static final b[] f = new b[0];
    static final b[] q = new b[0];
    final a<T> x;
    boolean y;
    final AtomicReference<b<T>[]> z = new AtomicReference<>(f);

    /* compiled from: ReplayProcessor */
    public interface a<T> {
        void a(Throwable th);

        void b(b<T> bVar);

        void c(T t);

        void complete();
    }

    public static <T> c<T> Y(int capacityHint) {
        return new c<>(new C0310c(capacityHint));
    }

    c(a<T> buffer) {
        this.x = buffer;
    }

    /* access modifiers changed from: protected */
    public void L(org.reactivestreams.b<? super T> s) {
        ReplayProcessor.ReplaySubscription<T> rs = new b<>(s, this);
        s.onSubscribe(rs);
        if (!X(rs) || !rs.cancelled) {
            this.x.b(rs);
        } else {
            Z(rs);
        }
    }

    public void onSubscribe(org.reactivestreams.c s) {
        if (this.y) {
            s.cancel();
        } else {
            s.request(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
        }
    }

    public void onNext(T t) {
        io.reactivex.internal.functions.b.e(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (!this.y) {
            ReplayProcessor.ReplayBuffer<T> b2 = this.x;
            b2.c(t);
            for (ReplayProcessor.ReplaySubscription<T> rs : (b[]) this.z.get()) {
                b2.b(rs);
            }
        }
    }

    public void onError(Throwable t) {
        io.reactivex.internal.functions.b.e(t, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.y) {
            io.reactivex.plugins.a.q(t);
            return;
        }
        this.y = true;
        ReplayProcessor.ReplayBuffer<T> b2 = this.x;
        b2.a(t);
        for (ReplayProcessor.ReplaySubscription<T> rs : (b[]) this.z.getAndSet(q)) {
            b2.b(rs);
        }
    }

    public void onComplete() {
        if (!this.y) {
            this.y = true;
            ReplayProcessor.ReplayBuffer<T> b2 = this.x;
            b2.complete();
            for (ReplayProcessor.ReplaySubscription<T> rs : (b[]) this.z.getAndSet(q)) {
                b2.b(rs);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean X(b<T> rs) {
        ReplayProcessor.ReplaySubscription<T>[] a2;
        ReplayProcessor.ReplaySubscription<T>[] b2;
        do {
            a2 = (b[]) this.z.get();
            if (a2 == q) {
                return false;
            }
            int len = a2.length;
            b2 = new b[(len + 1)];
            System.arraycopy(a2, 0, b2, 0, len);
            b2[len] = rs;
        } while (!this.z.compareAndSet(a2, b2));
        return true;
    }

    /* access modifiers changed from: package-private */
    public void Z(b<T> rs) {
        ReplayProcessor.ReplaySubscription<T>[] a2;
        ReplayProcessor.ReplaySubscription<T>[] b2;
        do {
            a2 = (b[]) this.z.get();
            if (a2 != q && a2 != f) {
                int len = a2.length;
                int j = -1;
                int i = 0;
                while (true) {
                    if (i >= len) {
                        break;
                    } else if (a2[i] == rs) {
                        j = i;
                        break;
                    } else {
                        i++;
                    }
                }
                if (j >= 0) {
                    if (len == 1) {
                        b2 = f;
                    } else {
                        ReplayProcessor.ReplaySubscription<T>[] b3 = new b[(len - 1)];
                        System.arraycopy(a2, 0, b3, 0, j);
                        System.arraycopy(a2, j + 1, b3, j, (len - j) - 1);
                        b2 = b3;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.z.compareAndSet(a2, b2));
    }

    /* compiled from: ReplayProcessor */
    public static final class b<T> extends AtomicInteger implements org.reactivestreams.c {
        private static final long serialVersionUID = 466549804534799122L;
        volatile boolean cancelled;
        final org.reactivestreams.b<? super T> downstream;
        long emitted;
        Object index;
        final AtomicLong requested = new AtomicLong();
        final c<T> state;

        b(org.reactivestreams.b<? super T> actual, c<T> state2) {
            this.downstream = actual;
            this.state = state2;
        }

        public void request(long n) {
            if (f.validate(n)) {
                io.reactivex.internal.util.c.a(this.requested, n);
                this.state.x.b(this);
            }
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.state.Z(this);
            }
        }
    }

    /* renamed from: io.reactivex.processors.c$c  reason: collision with other inner class name */
    /* compiled from: ReplayProcessor */
    public static final class C0310c<T> implements a<T> {
        final List<T> a;
        Throwable b;
        volatile boolean c;
        volatile int d;

        C0310c(int capacityHint) {
            this.a = new ArrayList(io.reactivex.internal.functions.b.f(capacityHint, "capacityHint"));
        }

        public void c(T value) {
            this.a.add(value);
            this.d++;
        }

        public void a(Throwable ex) {
            this.b = ex;
            this.c = true;
        }

        public void complete() {
            this.c = true;
        }

        public void b(b<T> rs) {
            int index;
            if (rs.getAndIncrement() == 0) {
                int missed = 1;
                List<T> b2 = this.a;
                Subscriber<? super T> a2 = rs.downstream;
                Integer indexObject = (Integer) rs.index;
                if (indexObject != null) {
                    index = indexObject.intValue();
                } else {
                    index = 0;
                    rs.index = 0;
                }
                long e = rs.emitted;
                do {
                    long r = rs.requested.get();
                    while (e != r) {
                        if (rs.cancelled) {
                            rs.index = null;
                            return;
                        }
                        boolean d2 = this.c;
                        int s = this.d;
                        if (d2 && index == s) {
                            rs.index = null;
                            rs.cancelled = true;
                            Throwable ex = this.b;
                            if (ex == null) {
                                a2.onComplete();
                                return;
                            } else {
                                a2.onError(ex);
                                return;
                            }
                        } else if (index == s) {
                            break;
                        } else {
                            a2.onNext(b2.get(index));
                            index++;
                            e++;
                        }
                    }
                    if (e == r) {
                        if (rs.cancelled) {
                            rs.index = null;
                            return;
                        }
                        boolean d3 = this.c;
                        int s2 = this.d;
                        if (d3 && index == s2) {
                            rs.index = null;
                            rs.cancelled = true;
                            Throwable ex2 = this.b;
                            if (ex2 == null) {
                                a2.onComplete();
                                return;
                            } else {
                                a2.onError(ex2);
                                return;
                            }
                        }
                    }
                    rs.index = Integer.valueOf(index);
                    rs.emitted = e;
                    missed = rs.addAndGet(-missed);
                } while (missed != 0);
            }
        }
    }
}
