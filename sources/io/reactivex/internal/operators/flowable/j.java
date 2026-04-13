package io.reactivex.internal.operators.flowable;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.e;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.f;
import org.reactivestreams.Subscriber;

/* compiled from: FlowableFromArray */
public final class j<T> extends e<T> {
    final T[] d;

    public j(T[] array) {
        this.d = array;
    }

    public void L(org.reactivestreams.b<? super T> s) {
        if (s instanceof io.reactivex.internal.fuseable.a) {
            s.onSubscribe(new a((io.reactivex.internal.fuseable.a) s, this.d));
        } else {
            s.onSubscribe(new b(s, this.d));
        }
    }

    /* compiled from: FlowableFromArray */
    public static abstract class c<T> extends io.reactivex.internal.subscriptions.b<T> {
        private static final long serialVersionUID = -2252972430506210021L;
        final T[] array;
        volatile boolean cancelled;
        int index;

        /* access modifiers changed from: package-private */
        public abstract void fastPath();

        /* access modifiers changed from: package-private */
        public abstract void slowPath(long j);

        c(T[] array2) {
            this.array = array2;
        }

        public final int requestFusion(int mode) {
            return mode & 1;
        }

        public final T poll() {
            int i = this.index;
            T[] arr = this.array;
            if (i == arr.length) {
                return null;
            }
            this.index = i + 1;
            return io.reactivex.internal.functions.b.e(arr[i], "array element is null");
        }

        public final boolean isEmpty() {
            return this.index == this.array.length;
        }

        public final void clear() {
            this.index = this.array.length;
        }

        public final void request(long n) {
            if (f.validate(n) && io.reactivex.internal.util.c.a(this, n) == 0) {
                if (n == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                    fastPath();
                } else {
                    slowPath(n);
                }
            }
        }

        public final void cancel() {
            this.cancelled = true;
        }
    }

    /* compiled from: FlowableFromArray */
    public static final class b<T> extends c<T> {
        private static final long serialVersionUID = 2587302975077663557L;
        final org.reactivestreams.b<? super T> downstream;

        b(org.reactivestreams.b<? super T> actual, T[] array) {
            super(array);
            this.downstream = actual;
        }

        /* access modifiers changed from: package-private */
        public void fastPath() {
            T[] arr = this.array;
            int f = arr.length;
            Subscriber<? super T> a = this.downstream;
            int i = this.index;
            while (i != f) {
                if (!this.cancelled) {
                    T t = arr[i];
                    if (t == null) {
                        a.onError(new NullPointerException("The element at index " + i + " is null"));
                        return;
                    }
                    a.onNext(t);
                    i++;
                } else {
                    return;
                }
            }
            if (this.cancelled == 0) {
                a.onComplete();
            }
        }

        /* access modifiers changed from: package-private */
        public void slowPath(long r) {
            long e = 0;
            T[] arr = this.array;
            int f = arr.length;
            int i = this.index;
            Subscriber<? super T> a = this.downstream;
            while (true) {
                if (e == r || i == f) {
                    if (i != f) {
                        r = get();
                        if (e == r) {
                            this.index = i;
                            r = addAndGet(-e);
                            if (r != 0) {
                                e = 0;
                            } else {
                                return;
                            }
                        } else {
                            continue;
                        }
                    } else if (!this.cancelled) {
                        a.onComplete();
                        return;
                    } else {
                        return;
                    }
                } else if (!this.cancelled) {
                    T t = arr[i];
                    if (t == null) {
                        a.onError(new NullPointerException("The element at index " + i + " is null"));
                        return;
                    }
                    a.onNext(t);
                    e++;
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    /* compiled from: FlowableFromArray */
    public static final class a<T> extends c<T> {
        private static final long serialVersionUID = 2587302975077663557L;
        final io.reactivex.internal.fuseable.a<? super T> downstream;

        a(io.reactivex.internal.fuseable.a<? super T> actual, T[] array) {
            super(array);
            this.downstream = actual;
        }

        /* access modifiers changed from: package-private */
        public void fastPath() {
            T[] arr = this.array;
            int f = arr.length;
            ConditionalSubscriber<? super T> a = this.downstream;
            int i = this.index;
            while (i != f) {
                if (!this.cancelled) {
                    T t = arr[i];
                    if (t == null) {
                        a.onError(new NullPointerException("The element at index " + i + " is null"));
                        return;
                    }
                    a.tryOnNext(t);
                    i++;
                } else {
                    return;
                }
            }
            if (this.cancelled == 0) {
                a.onComplete();
            }
        }

        /* access modifiers changed from: package-private */
        public void slowPath(long r) {
            long e = 0;
            T[] arr = this.array;
            int f = arr.length;
            int i = this.index;
            ConditionalSubscriber<? super T> a = this.downstream;
            while (true) {
                if (e == r || i == f) {
                    if (i != f) {
                        r = get();
                        if (e == r) {
                            this.index = i;
                            r = addAndGet(-e);
                            if (r != 0) {
                                e = 0;
                            } else {
                                return;
                            }
                        } else {
                            continue;
                        }
                    } else if (!this.cancelled) {
                        a.onComplete();
                        return;
                    } else {
                        return;
                    }
                } else if (!this.cancelled) {
                    T t = arr[i];
                    if (t == null) {
                        a.onError(new NullPointerException("The element at index " + i + " is null"));
                        return;
                    }
                    if (a.tryOnNext(t)) {
                        e++;
                    }
                    i++;
                } else {
                    return;
                }
            }
        }
    }
}
