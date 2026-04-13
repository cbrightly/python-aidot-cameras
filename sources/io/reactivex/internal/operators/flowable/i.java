package io.reactivex.internal.operators.flowable;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.e;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.f;
import io.reactivex.h;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.fuseable.d;
import io.reactivex.internal.fuseable.g;
import io.reactivex.internal.operators.flowable.FlowableFlatMap;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.c;

/* compiled from: FlowableFlatMap */
public final class i<T, U> extends a<T, U> {
    final f<? super T, ? extends org.reactivestreams.a<? extends U>> f;
    final boolean q;
    final int x;
    final int y;

    public i(e<T> source, f<? super T, ? extends org.reactivestreams.a<? extends U>> mapper, boolean delayErrors, int maxConcurrency, int bufferSize) {
        super(source);
        this.f = mapper;
        this.q = delayErrors;
        this.x = maxConcurrency;
        this.y = bufferSize;
    }

    /* access modifiers changed from: protected */
    public void L(org.reactivestreams.b<? super U> s) {
        if (!y.b(this.d, s, this.f)) {
            this.d.K(W(s, this.f, this.q, this.x, this.y));
        }
    }

    public static <T, U> h<T> W(org.reactivestreams.b<? super U> s, f<? super T, ? extends org.reactivestreams.a<? extends U>> mapper, boolean delayErrors, int maxConcurrency, int bufferSize) {
        return new b(s, mapper, delayErrors, maxConcurrency, bufferSize);
    }

    /* compiled from: FlowableFlatMap */
    public static final class b<T, U> extends AtomicInteger implements h<T>, c {
        static final a<?, ?>[] CANCELLED = new a[0];
        static final a<?, ?>[] EMPTY = new a[0];
        private static final long serialVersionUID = -2117620485640801370L;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final org.reactivestreams.b<? super U> downstream;
        final io.reactivex.internal.util.b errs = new io.reactivex.internal.util.b();
        long lastId;
        int lastIndex;
        final f<? super T, ? extends org.reactivestreams.a<? extends U>> mapper;
        final int maxConcurrency;
        volatile io.reactivex.internal.fuseable.f<U> queue;
        final AtomicLong requested;
        int scalarEmitted;
        final int scalarLimit;
        final AtomicReference<a<?, ?>[]> subscribers;
        long uniqueId;
        c upstream;

        b(org.reactivestreams.b<? super U> actual, f<? super T, ? extends org.reactivestreams.a<? extends U>> mapper2, boolean delayErrors2, int maxConcurrency2, int bufferSize2) {
            AtomicReference<a<?, ?>[]> atomicReference = new AtomicReference<>();
            this.subscribers = atomicReference;
            this.requested = new AtomicLong();
            this.downstream = actual;
            this.mapper = mapper2;
            this.delayErrors = delayErrors2;
            this.maxConcurrency = maxConcurrency2;
            this.bufferSize = bufferSize2;
            this.scalarLimit = Math.max(1, maxConcurrency2 >> 1);
            atomicReference.lazySet(EMPTY);
        }

        public void onSubscribe(c s) {
            if (io.reactivex.internal.subscriptions.f.validate(this.upstream, s)) {
                this.upstream = s;
                this.downstream.onSubscribe(this);
                if (!this.cancelled) {
                    int i = this.maxConcurrency;
                    if (i == Integer.MAX_VALUE) {
                        s.request(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
                    } else {
                        s.request((long) i);
                    }
                }
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                try {
                    org.reactivestreams.a aVar = (org.reactivestreams.a) io.reactivex.internal.functions.b.e(this.mapper.apply(t), "The mapper returned a null Publisher");
                    if (aVar instanceof Callable) {
                        try {
                            U u = ((Callable) aVar).call();
                            if (u != null) {
                                tryEmitScalar(u);
                            } else if (this.maxConcurrency != Integer.MAX_VALUE && !this.cancelled) {
                                int i = this.scalarEmitted + 1;
                                this.scalarEmitted = i;
                                int i2 = this.scalarLimit;
                                if (i == i2) {
                                    this.scalarEmitted = 0;
                                    this.upstream.request((long) i2);
                                }
                            }
                        } catch (Throwable ex) {
                            io.reactivex.exceptions.a.b(ex);
                            this.errs.addThrowable(ex);
                            drain();
                        }
                    } else {
                        long j = this.uniqueId;
                        this.uniqueId = 1 + j;
                        FlowableFlatMap.InnerSubscriber<T, U> inner = new a<>(this, j);
                        if (addInner(inner)) {
                            aVar.a(inner);
                        }
                    }
                } catch (Throwable e) {
                    io.reactivex.exceptions.a.b(e);
                    this.upstream.cancel();
                    onError(e);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public boolean addInner(a<T, U> inner) {
            FlowableFlatMap.InnerSubscriber<?, ?>[] a;
            FlowableFlatMap.InnerSubscriber<?, ?>[] b;
            do {
                a = (a[]) this.subscribers.get();
                if (a == CANCELLED) {
                    inner.dispose();
                    return false;
                }
                int n = a.length;
                b = new a[(n + 1)];
                System.arraycopy(a, 0, b, 0, n);
                b[n] = inner;
            } while (!this.subscribers.compareAndSet(a, b));
            return true;
        }

        /* access modifiers changed from: package-private */
        public void removeInner(a<T, U> inner) {
            FlowableFlatMap.InnerSubscriber<?, ?>[] a;
            FlowableFlatMap.InnerSubscriber<?, ?>[] b;
            do {
                a = (a[]) this.subscribers.get();
                int n = a.length;
                if (n != 0) {
                    int j = -1;
                    int i = 0;
                    while (true) {
                        if (i >= n) {
                            break;
                        } else if (a[i] == inner) {
                            j = i;
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (j >= 0) {
                        if (n == 1) {
                            b = EMPTY;
                        } else {
                            FlowableFlatMap.InnerSubscriber<?, ?>[] b2 = new a[(n - 1)];
                            System.arraycopy(a, 0, b2, 0, j);
                            System.arraycopy(a, j + 1, b2, j, (n - j) - 1);
                            b = b2;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.subscribers.compareAndSet(a, b));
        }

        /* access modifiers changed from: package-private */
        public g<U> getMainQueue() {
            SimplePlainQueue<U> q = this.queue;
            if (q == null) {
                if (this.maxConcurrency == Integer.MAX_VALUE) {
                    q = new io.reactivex.internal.queue.c<>(this.bufferSize);
                } else {
                    q = new io.reactivex.internal.queue.b<>(this.maxConcurrency);
                }
                this.queue = q;
            }
            return q;
        }

        /* access modifiers changed from: package-private */
        public void tryEmitScalar(U value) {
            if (get() == 0 && compareAndSet(0, 1)) {
                long r = this.requested.get();
                SimpleQueue<U> q = this.queue;
                if (r == 0 || (q != null && !q.isEmpty())) {
                    if (q == null) {
                        q = getMainQueue();
                    }
                    if (!q.offer(value)) {
                        onError(new IllegalStateException("Scalar queue full?!"));
                        return;
                    }
                } else {
                    this.downstream.onNext(value);
                    if (r != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                        this.requested.decrementAndGet();
                    }
                    if (this.maxConcurrency != Integer.MAX_VALUE && !this.cancelled) {
                        int i = this.scalarEmitted + 1;
                        this.scalarEmitted = i;
                        int i2 = this.scalarLimit;
                        if (i == i2) {
                            this.scalarEmitted = 0;
                            this.upstream.request((long) i2);
                        }
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else if (!getMainQueue().offer(value)) {
                onError(new IllegalStateException("Scalar queue full?!"));
                return;
            } else if (getAndIncrement() != 0) {
                return;
            }
            drainLoop();
        }

        /* access modifiers changed from: package-private */
        public g<U> getInnerQueue(a<T, U> inner) {
            SimpleQueue<U> q = inner.queue;
            if (q != null) {
                return q;
            }
            SimpleQueue<U> q2 = new io.reactivex.internal.queue.b<>(this.bufferSize);
            inner.queue = q2;
            return q2;
        }

        /* access modifiers changed from: package-private */
        public void tryEmit(U value, a<T, U> inner) {
            if (get() != 0 || !compareAndSet(0, 1)) {
                SimpleQueue<U> q = inner.queue;
                if (q == null) {
                    q = new io.reactivex.internal.queue.b<>(this.bufferSize);
                    inner.queue = q;
                }
                if (!q.offer(value)) {
                    onError(new MissingBackpressureException("Inner queue full?!"));
                    return;
                } else if (getAndIncrement() != 0) {
                    return;
                }
            } else {
                long r = this.requested.get();
                SimpleQueue<U> q2 = inner.queue;
                if (r == 0 || (q2 != null && !q2.isEmpty())) {
                    if (q2 == null) {
                        q2 = getInnerQueue(inner);
                    }
                    if (!q2.offer(value)) {
                        onError(new MissingBackpressureException("Inner queue full?!"));
                        return;
                    }
                } else {
                    this.downstream.onNext(value);
                    if (r != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                        this.requested.decrementAndGet();
                    }
                    inner.requestMore(1);
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
            drainLoop();
        }

        public void onError(Throwable t) {
            if (this.done) {
                io.reactivex.plugins.a.q(t);
            } else if (this.errs.addThrowable(t)) {
                this.done = true;
                drain();
            } else {
                io.reactivex.plugins.a.q(t);
            }
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                drain();
            }
        }

        public void request(long n) {
            if (io.reactivex.internal.subscriptions.f.validate(n)) {
                io.reactivex.internal.util.c.a(this.requested, n);
                drain();
            }
        }

        public void cancel() {
            SimpleQueue<U> q;
            if (!this.cancelled) {
                this.cancelled = true;
                this.upstream.cancel();
                disposeAll();
                if (getAndIncrement() == 0 && (q = this.queue) != null) {
                    q.clear();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Removed duplicated region for block: B:153:0x01e7 A[SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:61:0x00e5  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void drainLoop() {
            /*
                r32 = this;
                r1 = r32
                org.reactivestreams.b<? super U> r2 = r1.downstream
                r0 = 1
                r3 = r0
            L_0x0006:
                boolean r0 = r32.checkTerminate()
                if (r0 == 0) goto L_0x000d
                return
            L_0x000d:
                io.reactivex.internal.fuseable.f<U> r0 = r1.queue
                java.util.concurrent.atomic.AtomicLong r4 = r1.requested
                long r4 = r4.get()
                r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r6 != 0) goto L_0x0020
                r6 = 1
                goto L_0x0021
            L_0x0020:
                r6 = 0
            L_0x0021:
                r8 = 0
                r10 = 1
                r12 = 0
                if (r0 == 0) goto L_0x0067
            L_0x0029:
                r14 = 0
                r16 = 0
            L_0x002d:
                int r17 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
                if (r17 == 0) goto L_0x004a
                java.lang.Object r7 = r0.poll()
                boolean r16 = r32.checkTerminate()
                if (r16 == 0) goto L_0x003c
                return
            L_0x003c:
                if (r7 != 0) goto L_0x0041
                r16 = r7
                goto L_0x004a
            L_0x0041:
                r2.onNext(r7)
                long r8 = r8 + r10
                long r14 = r14 + r10
                long r4 = r4 - r10
                r16 = r7
                goto L_0x002d
            L_0x004a:
                int r7 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
                if (r7 == 0) goto L_0x005d
                if (r6 == 0) goto L_0x0056
                r4 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                goto L_0x005d
            L_0x0056:
                java.util.concurrent.atomic.AtomicLong r7 = r1.requested
                long r10 = -r14
                long r4 = r7.addAndGet(r10)
            L_0x005d:
                int r7 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
                if (r7 == 0) goto L_0x0067
                if (r16 != 0) goto L_0x0064
                goto L_0x0067
            L_0x0064:
                r10 = 1
                goto L_0x0029
            L_0x0067:
                boolean r7 = r1.done
                io.reactivex.internal.fuseable.f<U> r10 = r1.queue
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.i$a<?, ?>[]> r0 = r1.subscribers
                java.lang.Object r0 = r0.get()
                r11 = r0
                io.reactivex.internal.operators.flowable.i$a[] r11 = (io.reactivex.internal.operators.flowable.i.a[]) r11
                int r14 = r11.length
                if (r7 == 0) goto L_0x0095
                if (r10 == 0) goto L_0x007f
                boolean r0 = r10.isEmpty()
                if (r0 == 0) goto L_0x0095
            L_0x007f:
                if (r14 != 0) goto L_0x0095
                io.reactivex.internal.util.b r0 = r1.errs
                java.lang.Throwable r0 = r0.terminate()
                java.lang.Throwable r12 = io.reactivex.internal.util.f.a
                if (r0 == r12) goto L_0x0094
                if (r0 != 0) goto L_0x0091
                r2.onComplete()
                goto L_0x0094
            L_0x0091:
                r2.onError(r0)
            L_0x0094:
                return
            L_0x0095:
                r0 = 0
                if (r14 == 0) goto L_0x01fe
                long r12 = r1.lastId
                int r15 = r1.lastIndex
                if (r14 <= r15) goto L_0x00b0
                r22 = r0
                r0 = r11[r15]
                r23 = r4
                long r4 = r0.id
                int r0 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
                if (r0 == 0) goto L_0x00ab
                goto L_0x00b4
            L_0x00ab:
                r25 = r8
                r4 = r15
                r9 = r7
                goto L_0x00df
            L_0x00b0:
                r22 = r0
                r23 = r4
            L_0x00b4:
                if (r14 > r15) goto L_0x00b7
                r15 = 0
            L_0x00b7:
                r0 = r15
                r4 = 0
            L_0x00b9:
                if (r4 >= r14) goto L_0x00d2
                r5 = r11[r0]
                r25 = r8
                r9 = r7
                long r7 = r5.id
                int r5 = (r7 > r12 ? 1 : (r7 == r12 ? 0 : -1))
                if (r5 != 0) goto L_0x00c7
                goto L_0x00d5
            L_0x00c7:
                int r0 = r0 + 1
                if (r0 != r14) goto L_0x00cc
                r0 = 0
            L_0x00cc:
                int r4 = r4 + 1
                r7 = r9
                r8 = r25
                goto L_0x00b9
            L_0x00d2:
                r25 = r8
                r9 = r7
            L_0x00d5:
                r15 = r0
                r1.lastIndex = r0
                r4 = r11[r0]
                long r4 = r4.id
                r1.lastId = r4
                r4 = r15
            L_0x00df:
                r0 = r4
                r5 = 0
                r7 = r5
                r5 = r0
            L_0x00e3:
                if (r7 >= r14) goto L_0x01e7
                boolean r0 = r32.checkTerminate()
                if (r0 == 0) goto L_0x00ec
                return
            L_0x00ec:
                r8 = r11[r5]
                r0 = 0
            L_0x00ef:
                boolean r15 = r32.checkTerminate()
                if (r15 == 0) goto L_0x00f6
                return
            L_0x00f6:
                io.reactivex.internal.fuseable.g<U> r15 = r8.queue
                if (r15 != 0) goto L_0x0104
                r21 = r6
                r31 = r7
                r30 = r9
                r29 = r10
                goto L_0x01a3
            L_0x0104:
                r27 = 0
                r30 = r9
                r29 = r10
                r9 = r27
                r27 = r23
                r23 = r0
            L_0x0110:
                r20 = 0
                int r0 = (r27 > r20 ? 1 : (r27 == r20 ? 0 : -1))
                r20 = r15
                if (r0 == 0) goto L_0x0165
                java.lang.Object r0 = r20.poll()     // Catch:{ all -> 0x0135 }
                if (r0 != 0) goto L_0x0120
                goto L_0x0167
            L_0x0120:
                r2.onNext(r0)
                boolean r21 = r32.checkTerminate()
                if (r21 == 0) goto L_0x012a
                return
            L_0x012a:
                r18 = 1
                long r27 = r27 - r18
                long r9 = r9 + r18
                r23 = r0
                r15 = r20
                goto L_0x0110
            L_0x0135:
                r0 = move-exception
                r21 = r0
                r0 = r21
                io.reactivex.exceptions.a.b(r0)
                r8.dispose()
                io.reactivex.internal.util.b r15 = r1.errs
                r15.addThrowable(r0)
                boolean r15 = r1.delayErrors
                if (r15 != 0) goto L_0x014e
                org.reactivestreams.c r15 = r1.upstream
                r15.cancel()
            L_0x014e:
                boolean r15 = r32.checkTerminate()
                if (r15 == 0) goto L_0x0155
                return
            L_0x0155:
                r1.removeInner(r8)
                r15 = 1
                int r7 = r7 + 1
                r21 = r6
                r22 = r15
                r23 = r27
                r9 = 1
                goto L_0x01dd
            L_0x0165:
                r0 = r23
            L_0x0167:
                r15 = 0
                int r21 = (r9 > r15 ? 1 : (r9 == r15 ? 0 : -1))
                if (r21 == 0) goto L_0x018a
                if (r6 != 0) goto L_0x017b
                java.util.concurrent.atomic.AtomicLong r15 = r1.requested
                r21 = r6
                r31 = r7
                long r6 = -r9
                long r6 = r15.addAndGet(r6)
                goto L_0x0184
            L_0x017b:
                r21 = r6
                r31 = r7
                r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            L_0x0184:
                r8.requestMore(r9)
                r23 = r6
                goto L_0x0190
            L_0x018a:
                r21 = r6
                r31 = r7
                r23 = r27
            L_0x0190:
                r6 = 0
                int r27 = (r23 > r6 ? 1 : (r23 == r6 ? 0 : -1))
                if (r27 == 0) goto L_0x01a3
                if (r0 != 0) goto L_0x0199
                goto L_0x01a3
            L_0x0199:
                r6 = r21
                r10 = r29
                r9 = r30
                r7 = r31
                goto L_0x00ef
            L_0x01a3:
                boolean r6 = r8.done
                io.reactivex.internal.fuseable.g<U> r7 = r8.queue
                if (r6 == 0) goto L_0x01c6
                if (r7 == 0) goto L_0x01b5
                boolean r9 = r7.isEmpty()
                if (r9 == 0) goto L_0x01b2
                goto L_0x01b5
            L_0x01b2:
                r9 = 1
                goto L_0x01c8
            L_0x01b5:
                r1.removeInner(r8)
                boolean r9 = r32.checkTerminate()
                if (r9 == 0) goto L_0x01bf
                return
            L_0x01bf:
                r9 = 1
                long r25 = r25 + r9
                r22 = 1
                goto L_0x01c8
            L_0x01c6:
                r9 = 1
            L_0x01c8:
                r15 = 0
                int r18 = (r23 > r15 ? 1 : (r23 == r15 ? 0 : -1))
                if (r18 != 0) goto L_0x01d3
                r0 = r22
                r8 = r25
                goto L_0x01f3
            L_0x01d3:
                int r5 = r5 + 1
                if (r5 != r14) goto L_0x01db
                r5 = 0
                r7 = r31
                goto L_0x01dd
            L_0x01db:
                r7 = r31
            L_0x01dd:
                r6 = 1
                int r7 = r7 + r6
                r6 = r21
                r10 = r29
                r9 = r30
                goto L_0x00e3
            L_0x01e7:
                r21 = r6
                r31 = r7
                r30 = r9
                r29 = r10
                r0 = r22
                r8 = r25
            L_0x01f3:
                r1.lastIndex = r5
                r6 = r11[r5]
                long r6 = r6.id
                r1.lastId = r6
                r4 = r23
                goto L_0x020a
            L_0x01fe:
                r22 = r0
                r23 = r4
                r21 = r6
                r30 = r7
                r25 = r8
                r29 = r10
            L_0x020a:
                r6 = 0
                int r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
                if (r6 == 0) goto L_0x0219
                boolean r6 = r1.cancelled
                if (r6 != 0) goto L_0x0219
                org.reactivestreams.c r6 = r1.upstream
                r6.request(r8)
            L_0x0219:
                if (r0 == 0) goto L_0x021d
                goto L_0x0006
            L_0x021d:
                int r6 = -r3
                int r3 = r1.addAndGet(r6)
                if (r3 != 0) goto L_0x0226
                return
            L_0x0226:
                goto L_0x0006
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.i.b.drainLoop():void");
        }

        /* access modifiers changed from: package-private */
        public boolean checkTerminate() {
            if (this.cancelled) {
                clearScalarQueue();
                return true;
            } else if (this.delayErrors || this.errs.get() == null) {
                return false;
            } else {
                clearScalarQueue();
                Throwable ex = this.errs.terminate();
                if (ex != io.reactivex.internal.util.f.a) {
                    this.downstream.onError(ex);
                }
                return true;
            }
        }

        /* access modifiers changed from: package-private */
        public void clearScalarQueue() {
            SimpleQueue<U> q = this.queue;
            if (q != null) {
                q.clear();
            }
        }

        /* access modifiers changed from: package-private */
        public void disposeAll() {
            FlowableFlatMap.InnerSubscriber<?, ?>[] a;
            FlowableFlatMap.InnerSubscriber<?, ?>[] a2 = (a[]) this.subscribers.get();
            FlowableFlatMap.InnerSubscriber<?, ?>[] innerSubscriberArr = CANCELLED;
            if (a2 != innerSubscriberArr && (a = (a[]) this.subscribers.getAndSet(innerSubscriberArr)) != innerSubscriberArr) {
                for (FlowableFlatMap.InnerSubscriber<?, ?> inner : a) {
                    inner.dispose();
                }
                Throwable ex = this.errs.terminate();
                if (ex != null && ex != io.reactivex.internal.util.f.a) {
                    io.reactivex.plugins.a.q(ex);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void innerError(a<T, U> inner, Throwable t) {
            if (this.errs.addThrowable(t)) {
                inner.done = true;
                if (!this.delayErrors) {
                    this.upstream.cancel();
                    for (FlowableFlatMap.InnerSubscriber<?, ?> a : (a[]) this.subscribers.getAndSet(CANCELLED)) {
                        a.dispose();
                    }
                }
                drain();
                return;
            }
            io.reactivex.plugins.a.q(t);
        }
    }

    /* compiled from: FlowableFlatMap */
    public static final class a<T, U> extends AtomicReference<c> implements h<U>, io.reactivex.disposables.b {
        private static final long serialVersionUID = -4606175640614850599L;
        final int bufferSize;
        volatile boolean done;
        int fusionMode;
        final long id;
        final int limit;
        final b<T, U> parent;
        long produced;
        volatile g<U> queue;

        a(b<T, U> parent2, long id2) {
            this.id = id2;
            this.parent = parent2;
            int i = parent2.bufferSize;
            this.bufferSize = i;
            this.limit = i >> 2;
        }

        public void onSubscribe(c s) {
            if (io.reactivex.internal.subscriptions.f.setOnce(this, s)) {
                if (s instanceof d) {
                    QueueSubscription<U> qs = (d) s;
                    int m = qs.requestFusion(7);
                    if (m == 1) {
                        this.fusionMode = m;
                        this.queue = qs;
                        this.done = true;
                        this.parent.drain();
                        return;
                    } else if (m == 2) {
                        this.fusionMode = m;
                        this.queue = qs;
                    }
                }
                s.request((long) this.bufferSize);
            }
        }

        public void onNext(U t) {
            if (this.fusionMode != 2) {
                this.parent.tryEmit(t, this);
            } else {
                this.parent.drain();
            }
        }

        public void onError(Throwable t) {
            lazySet(io.reactivex.internal.subscriptions.f.CANCELLED);
            this.parent.innerError(this, t);
        }

        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        /* access modifiers changed from: package-private */
        public void requestMore(long n) {
            if (this.fusionMode != 1) {
                long p = this.produced + n;
                if (p >= ((long) this.limit)) {
                    this.produced = 0;
                    ((c) get()).request(p);
                    return;
                }
                this.produced = p;
            }
        }

        public void dispose() {
            io.reactivex.internal.subscriptions.f.cancel(this);
        }

        public boolean isDisposed() {
            return get() == io.reactivex.internal.subscriptions.f.CANCELLED;
        }
    }
}
