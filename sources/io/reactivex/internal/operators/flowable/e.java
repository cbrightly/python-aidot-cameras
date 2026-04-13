package io.reactivex.internal.operators.flowable;

import io.reactivex.functions.c;
import io.reactivex.functions.f;

/* compiled from: FlowableDistinctUntilChanged */
public final class e<T, K> extends a<T, T> {
    final f<? super T, K> f;
    final c<? super K, ? super K> q;

    public e(io.reactivex.e<T> source, f<? super T, K> keySelector, c<? super K, ? super K> comparer) {
        super(source);
        this.f = keySelector;
        this.q = comparer;
    }

    /* access modifiers changed from: protected */
    public void L(org.reactivestreams.b<? super T> s) {
        if (s instanceof io.reactivex.internal.fuseable.a) {
            this.d.K(new a((io.reactivex.internal.fuseable.a) s, this.f, this.q));
        } else {
            this.d.K(new b(s, this.f, this.q));
        }
    }

    /* compiled from: FlowableDistinctUntilChanged */
    public static final class b<T, K> extends io.reactivex.internal.subscribers.b<T, T> implements io.reactivex.internal.fuseable.a<T> {
        boolean a1;
        K p0;
        final f<? super T, K> y;
        final c<? super K, ? super K> z;

        b(org.reactivestreams.b<? super T> actual, f<? super T, K> keySelector, c<? super K, ? super K> comparer) {
            super(actual);
            this.y = keySelector;
            this.z = comparer;
        }

        public void onNext(T t) {
            if (!tryOnNext(t)) {
                this.d.request(1);
            }
        }

        public boolean tryOnNext(T t) {
            if (this.q) {
                return false;
            }
            if (this.x != 0) {
                this.c.onNext(t);
                return true;
            }
            try {
                K key = this.y.apply(t);
                if (this.a1) {
                    boolean equal = this.z.a(this.p0, key);
                    this.p0 = key;
                    if (equal) {
                        return false;
                    }
                } else {
                    this.a1 = true;
                    this.p0 = key;
                }
                this.c.onNext(t);
                return true;
            } catch (Throwable ex) {
                c(ex);
                return true;
            }
        }

        public int requestFusion(int mode) {
            return d(mode);
        }

        public T poll() {
            while (true) {
                T v = this.f.poll();
                if (v == null) {
                    return null;
                }
                K key = this.y.apply(v);
                if (!this.a1) {
                    this.a1 = true;
                    this.p0 = key;
                    return v;
                } else if (!this.z.a(this.p0, key)) {
                    this.p0 = key;
                    return v;
                } else {
                    this.p0 = key;
                    if (this.x != 1) {
                        this.d.request(1);
                    }
                }
            }
        }
    }

    /* compiled from: FlowableDistinctUntilChanged */
    public static final class a<T, K> extends io.reactivex.internal.subscribers.a<T, T> {
        boolean a1;
        K p0;
        final f<? super T, K> y;
        final c<? super K, ? super K> z;

        a(io.reactivex.internal.fuseable.a<? super T> actual, f<? super T, K> keySelector, c<? super K, ? super K> comparer) {
            super(actual);
            this.y = keySelector;
            this.z = comparer;
        }

        public void onNext(T t) {
            if (!tryOnNext(t)) {
                this.d.request(1);
            }
        }

        public boolean tryOnNext(T t) {
            if (this.q) {
                return false;
            }
            if (this.x != 0) {
                return this.c.tryOnNext(t);
            }
            try {
                K key = this.y.apply(t);
                if (this.a1) {
                    boolean equal = this.z.a(this.p0, key);
                    this.p0 = key;
                    if (equal) {
                        return false;
                    }
                } else {
                    this.a1 = true;
                    this.p0 = key;
                }
                this.c.onNext(t);
                return true;
            } catch (Throwable ex) {
                c(ex);
                return true;
            }
        }

        public int requestFusion(int mode) {
            return d(mode);
        }

        public T poll() {
            while (true) {
                T v = this.f.poll();
                if (v == null) {
                    return null;
                }
                K key = this.y.apply(v);
                if (!this.a1) {
                    this.a1 = true;
                    this.p0 = key;
                    return v;
                } else if (!this.z.a(this.p0, key)) {
                    this.p0 = key;
                    return v;
                } else {
                    this.p0 = key;
                    if (this.x != 1) {
                        this.d.request(1);
                    }
                }
            }
        }
    }
}
