package io.reactivex.internal.operators.observable;

import io.reactivex.functions.f;
import io.reactivex.internal.functions.b;
import io.reactivex.o;
import io.reactivex.q;

/* compiled from: ObservableMap */
public final class r<T, U> extends a<T, U> {
    final f<? super T, ? extends U> d;

    public r(o<T> source, f<? super T, ? extends U> function) {
        super(source);
        this.d = function;
    }

    public void a0(q<? super U> t) {
        this.c.a(new a(t, this.d));
    }

    /* compiled from: ObservableMap */
    public static final class a<T, U> extends io.reactivex.internal.observers.a<T, U> {
        final f<? super T, ? extends U> y;

        a(q<? super U> actual, f<? super T, ? extends U> mapper) {
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
                    this.c.onNext(b.e(this.y.apply(t), "The mapper function returned a null value."));
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
                return b.e(this.y.apply(t), "The mapper function returned a null value.");
            }
            return null;
        }
    }
}
