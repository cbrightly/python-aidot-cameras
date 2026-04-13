package io.reactivex.internal.operators.flowable;

import io.reactivex.e;
import io.reactivex.functions.f;

/* compiled from: FlowableMap */
public final class p<T, U> extends a<T, U> {
    final f<? super T, ? extends U> f;

    public p(e<T> source, f<? super T, ? extends U> mapper) {
        super(source);
        this.f = mapper;
    }

    /* access modifiers changed from: protected */
    public void L(org.reactivestreams.b<? super U> s) {
        if (s instanceof io.reactivex.internal.fuseable.a) {
            this.d.K(new a((io.reactivex.internal.fuseable.a) s, this.f));
        } else {
            this.d.K(new b(s, this.f));
        }
    }

    /* compiled from: FlowableMap */
    public static final class b<T, U> extends io.reactivex.internal.subscribers.b<T, U> {
        final f<? super T, ? extends U> y;

        b(org.reactivestreams.b<? super U> actual, f<? super T, ? extends U> mapper) {
            super(actual);
            this.y = mapper;
        }

        public void onNext(T t) {
            if (!this.q) {
                if (this.x != 0) {
                    this.c.onNext(null);
                    return;
                }
                try {
                    this.c.onNext(io.reactivex.internal.functions.b.e(this.y.apply(t), "The mapper function returned a null value."));
                } catch (Throwable ex) {
                    c(ex);
                }
            }
        }

        public int requestFusion(int mode) {
            return d(mode);
        }

        public U poll() {
            T t = this.f.poll();
            if (t != null) {
                return io.reactivex.internal.functions.b.e(this.y.apply(t), "The mapper function returned a null value.");
            }
            return null;
        }
    }

    /* compiled from: FlowableMap */
    public static final class a<T, U> extends io.reactivex.internal.subscribers.a<T, U> {
        final f<? super T, ? extends U> y;

        a(io.reactivex.internal.fuseable.a<? super U> actual, f<? super T, ? extends U> function) {
            super(actual);
            this.y = function;
        }

        public void onNext(T t) {
            if (!this.q) {
                if (this.x != 0) {
                    this.c.onNext(null);
                    return;
                }
                try {
                    this.c.onNext(io.reactivex.internal.functions.b.e(this.y.apply(t), "The mapper function returned a null value."));
                } catch (Throwable ex) {
                    c(ex);
                }
            }
        }

        public boolean tryOnNext(T t) {
            if (this.q) {
                return false;
            }
            try {
                return this.c.tryOnNext(io.reactivex.internal.functions.b.e(this.y.apply(t), "The mapper function returned a null value."));
            } catch (Throwable ex) {
                c(ex);
                return true;
            }
        }

        public int requestFusion(int mode) {
            return d(mode);
        }

        public U poll() {
            T t = this.f.poll();
            if (t != null) {
                return io.reactivex.internal.functions.b.e(this.y.apply(t), "The mapper function returned a null value.");
            }
            return null;
        }
    }
}
