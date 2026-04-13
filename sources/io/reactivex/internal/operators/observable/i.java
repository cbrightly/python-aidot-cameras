package io.reactivex.internal.operators.observable;

import io.reactivex.functions.h;
import io.reactivex.o;
import io.reactivex.q;

/* compiled from: ObservableFilter */
public final class i<T> extends a<T, T> {
    final h<? super T> d;

    public i(o<T> source, h<? super T> predicate) {
        super(source);
        this.d = predicate;
    }

    public void a0(q<? super T> observer) {
        this.c.a(new a(observer, this.d));
    }

    /* compiled from: ObservableFilter */
    public static final class a<T> extends io.reactivex.internal.observers.a<T, T> {
        final h<? super T> y;

        a(q<? super T> actual, h<? super T> filter) {
            super(actual);
            this.y = filter;
        }

        public void onNext(T t) {
            if (this.x == 0) {
                try {
                    if (this.y.test(t)) {
                        this.c.onNext(t);
                    }
                } catch (Throwable e) {
                    c(e);
                }
            } else {
                this.c.onNext(null);
            }
        }

        public int requestFusion(int mode) {
            return d(mode);
        }

        /*  JADX ERROR: StackOverflow in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: 
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
            	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
            */
        public T poll() {
            /*
                r2 = this;
            L_0x0000:
                io.reactivex.internal.fuseable.b<T> r0 = r2.f
                java.lang.Object r0 = r0.poll()
                if (r0 == 0) goto L_0x0012
                io.reactivex.functions.h<? super T> r1 = r2.y
                boolean r1 = r1.test(r0)
                if (r1 == 0) goto L_0x0011
                goto L_0x0012
            L_0x0011:
                goto L_0x0000
            L_0x0012:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.i.a.poll():java.lang.Object");
        }
    }
}
