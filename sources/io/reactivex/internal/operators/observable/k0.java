package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.functions.f;
import io.reactivex.internal.disposables.d;
import io.reactivex.internal.operators.observable.ObservableZip;
import io.reactivex.internal.queue.c;
import io.reactivex.l;
import io.reactivex.o;
import io.reactivex.q;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ObservableZip */
public final class k0<T, R> extends l<R> {
    final o<? extends T>[] c;
    final Iterable<? extends o<? extends T>> d;
    final f<? super Object[], ? extends R> f;
    final int q;
    final boolean x;

    public k0(o<? extends T>[] sources, Iterable<? extends o<? extends T>> sourcesIterable, f<? super Object[], ? extends R> zipper, int bufferSize, boolean delayError) {
        this.c = sources;
        this.d = sourcesIterable;
        this.f = zipper;
        this.q = bufferSize;
        this.x = delayError;
    }

    public void a0(q<? super R> observer) {
        ObservableSource<? extends T>[] sources = this.c;
        int count = 0;
        if (sources == null) {
            sources = new l[8];
            Iterator<? extends o<? extends T>> it = this.d.iterator();
            while (it.hasNext()) {
                ObservableSource<? extends T> p = (o) it.next();
                if (count == sources.length) {
                    ObservableSource<? extends T>[] b2 = new o[((count >> 2) + count)];
                    System.arraycopy(sources, 0, b2, 0, count);
                    sources = b2;
                }
                sources[count] = p;
                count++;
            }
        } else {
            count = sources.length;
        }
        if (count == 0) {
            d.complete((q<?>) observer);
        } else {
            new a<>(observer, this.f, count, this.x).subscribe(sources, this.q);
        }
    }

    /* compiled from: ObservableZip */
    public static final class a<T, R> extends AtomicInteger implements io.reactivex.disposables.b {
        private static final long serialVersionUID = 2983708048395377667L;
        volatile boolean cancelled;
        final boolean delayError;
        final q<? super R> downstream;
        final b<T, R>[] observers;
        final T[] row;
        final f<? super Object[], ? extends R> zipper;

        a(q<? super R> actual, f<? super Object[], ? extends R> zipper2, int count, boolean delayError2) {
            this.downstream = actual;
            this.zipper = zipper2;
            this.observers = new b[count];
            this.row = new Object[count];
            this.delayError = delayError2;
        }

        public void subscribe(o<? extends T>[] sources, int bufferSize) {
            ObservableZip.ZipObserver<T, R>[] s = this.observers;
            int len = s.length;
            for (int i = 0; i < len; i++) {
                s[i] = new b<>(this, bufferSize);
            }
            lazySet(0);
            this.downstream.onSubscribe(this);
            for (int i2 = 0; i2 < len && !this.cancelled; i2++) {
                sources[i2].a(s[i2]);
            }
        }

        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                cancelSources();
                if (getAndIncrement() == 0) {
                    clear();
                }
            }
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        /* access modifiers changed from: package-private */
        public void cancel() {
            clear();
            cancelSources();
        }

        /* access modifiers changed from: package-private */
        public void cancelSources() {
            for (ObservableZip.ZipObserver<?, ?> zs : this.observers) {
                zs.a();
            }
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            for (ObservableZip.ZipObserver<?, ?> zs : this.observers) {
                zs.d.clear();
            }
        }

        public void drain() {
            Throwable ex;
            if (getAndIncrement() == 0) {
                b<T, R>[] bVarArr = this.observers;
                q<? super R> qVar = this.downstream;
                T[] os = this.row;
                boolean delayError2 = this.delayError;
                int missing = 1;
                while (true) {
                    int i = 0;
                    int emptyCount = 0;
                    for (b<T, R> bVar : bVarArr) {
                        boolean z = true;
                        if (os[i] == null) {
                            boolean d = bVar.f;
                            T v = bVar.d.poll();
                            if (v != null) {
                                z = false;
                            }
                            boolean empty = z;
                            boolean z2 = d;
                            b<T, R> bVar2 = bVar;
                            if (!checkTerminated(d, empty, qVar, delayError2, bVar)) {
                                if (!empty) {
                                    os[i] = v;
                                } else {
                                    emptyCount++;
                                }
                                b<T, R> bVar3 = bVar2;
                            } else {
                                return;
                            }
                        } else {
                            b<T, R> bVar4 = bVar;
                            if (bVar4.f && !delayError2 && (ex = bVar4.q) != null) {
                                this.cancelled = true;
                                cancel();
                                qVar.onError(ex);
                                return;
                            }
                        }
                        i++;
                    }
                    if (emptyCount != 0) {
                        missing = addAndGet(-missing);
                        if (missing == 0) {
                            return;
                        }
                    } else {
                        try {
                            qVar.onNext(io.reactivex.internal.functions.b.e(this.zipper.apply(os.clone()), "The zipper returned a null value"));
                            Arrays.fill(os, (Object) null);
                        } catch (Throwable ex2) {
                            io.reactivex.exceptions.a.b(ex2);
                            cancel();
                            qVar.onError(ex2);
                            return;
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public boolean checkTerminated(boolean d, boolean empty, q<? super R> a, boolean delayError2, b<?, ?> source) {
            if (this.cancelled) {
                cancel();
                return true;
            } else if (!d) {
                return false;
            } else {
                if (!delayError2) {
                    Throwable e = source.q;
                    if (e != null) {
                        this.cancelled = true;
                        cancel();
                        a.onError(e);
                        return true;
                    } else if (!empty) {
                        return false;
                    } else {
                        this.cancelled = true;
                        cancel();
                        a.onComplete();
                        return true;
                    }
                } else if (!empty) {
                    return false;
                } else {
                    Throwable e2 = source.q;
                    this.cancelled = true;
                    cancel();
                    if (e2 != null) {
                        a.onError(e2);
                    } else {
                        a.onComplete();
                    }
                    return true;
                }
            }
        }
    }

    /* compiled from: ObservableZip */
    public static final class b<T, R> implements q<T> {
        final a<T, R> c;
        final c<T> d;
        volatile boolean f;
        Throwable q;
        final AtomicReference<io.reactivex.disposables.b> x = new AtomicReference<>();

        b(a<T, R> parent, int bufferSize) {
            this.c = parent;
            this.d = new c<>(bufferSize);
        }

        public void onSubscribe(io.reactivex.disposables.b d2) {
            io.reactivex.internal.disposables.c.setOnce(this.x, d2);
        }

        public void onNext(T t) {
            this.d.offer(t);
            this.c.drain();
        }

        public void onError(Throwable t) {
            this.q = t;
            this.f = true;
            this.c.drain();
        }

        public void onComplete() {
            this.f = true;
            this.c.drain();
        }

        public void a() {
            io.reactivex.internal.disposables.c.dispose(this.x);
        }
    }
}
