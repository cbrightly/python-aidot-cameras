package io.reactivex.internal.functions;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import java.util.Comparator;
import java.util.concurrent.Callable;

/* compiled from: Functions */
public final class a {
    static final io.reactivex.functions.f<Object, Object> a = new h();
    public static final Runnable b = new e();
    public static final io.reactivex.functions.a c = new b();
    static final io.reactivex.functions.e<Object> d = new c();
    public static final io.reactivex.functions.e<Throwable> e = new f();
    public static final io.reactivex.functions.e<Throwable> f = new m();
    public static final io.reactivex.functions.g g = new d();
    static final io.reactivex.functions.h<Object> h = new n();
    static final io.reactivex.functions.h<Object> i = new g();
    static final Callable<Object> j = new l();
    static final Comparator<Object> k = new k();
    public static final io.reactivex.functions.e<org.reactivestreams.c> l = new j();

    public static <T1, T2, R> io.reactivex.functions.f<Object[], R> f(io.reactivex.functions.b<? super T1, ? super T2, ? extends R> f2) {
        b.e(f2, "f is null");
        return new C0296a(f2);
    }

    public static <T> io.reactivex.functions.f<T, T> c() {
        return a;
    }

    public static <T> io.reactivex.functions.e<T> b() {
        return d;
    }

    public static <T> io.reactivex.functions.h<T> a() {
        return h;
    }

    /* compiled from: Functions */
    public static final class i<T, U> implements Callable<U>, io.reactivex.functions.f<T, U> {
        final U c;

        i(U value) {
            this.c = value;
        }

        public U call() {
            return this.c;
        }

        public U apply(T t) {
            return this.c;
        }
    }

    public static <T> Callable<T> d(T value) {
        return new i(value);
    }

    public static <T, U> io.reactivex.functions.f<T, U> e(U value) {
        return new i(value);
    }

    /* renamed from: io.reactivex.internal.functions.a$a  reason: collision with other inner class name */
    /* compiled from: Functions */
    public static final class C0296a<T1, T2, R> implements io.reactivex.functions.f<Object[], R> {
        final io.reactivex.functions.b<? super T1, ? super T2, ? extends R> c;

        C0296a(io.reactivex.functions.b<? super T1, ? super T2, ? extends R> f) {
            this.c = f;
        }

        /* renamed from: a */
        public R apply(Object[] a) {
            if (a.length == 2) {
                return this.c.apply(a[0], a[1]);
            }
            throw new IllegalArgumentException("Array of size 2 expected but got " + a.length);
        }
    }

    /* compiled from: Functions */
    public static final class h implements io.reactivex.functions.f<Object, Object> {
        h() {
        }

        public Object apply(Object v) {
            return v;
        }

        public String toString() {
            return "IdentityFunction";
        }
    }

    /* compiled from: Functions */
    public static final class e implements Runnable {
        e() {
        }

        public void run() {
        }

        public String toString() {
            return "EmptyRunnable";
        }
    }

    /* compiled from: Functions */
    public static final class b implements io.reactivex.functions.a {
        b() {
        }

        public void run() {
        }

        public String toString() {
            return "EmptyAction";
        }
    }

    /* compiled from: Functions */
    public static final class c implements io.reactivex.functions.e<Object> {
        c() {
        }

        public void accept(Object v) {
        }

        public String toString() {
            return "EmptyConsumer";
        }
    }

    /* compiled from: Functions */
    public static final class f implements io.reactivex.functions.e<Throwable> {
        f() {
        }

        /* renamed from: a */
        public void accept(Throwable error) {
            io.reactivex.plugins.a.q(error);
        }
    }

    /* compiled from: Functions */
    public static final class m implements io.reactivex.functions.e<Throwable> {
        m() {
        }

        /* renamed from: a */
        public void accept(Throwable error) {
            io.reactivex.plugins.a.q(new OnErrorNotImplementedException(error));
        }
    }

    /* compiled from: Functions */
    public static final class d implements io.reactivex.functions.g {
        d() {
        }
    }

    /* compiled from: Functions */
    public static final class n implements io.reactivex.functions.h<Object> {
        n() {
        }

        public boolean test(Object o) {
            return true;
        }
    }

    /* compiled from: Functions */
    public static final class g implements io.reactivex.functions.h<Object> {
        g() {
        }

        public boolean test(Object o) {
            return false;
        }
    }

    /* compiled from: Functions */
    public static final class l implements Callable<Object> {
        l() {
        }

        public Object call() {
            return null;
        }
    }

    /* compiled from: Functions */
    public static final class k implements Comparator<Object> {
        k() {
        }

        public int compare(Object a, Object b) {
            return ((Comparable) a).compareTo(b);
        }
    }

    /* compiled from: Functions */
    public static final class j implements io.reactivex.functions.e<org.reactivestreams.c> {
        j() {
        }

        /* renamed from: a */
        public void accept(org.reactivestreams.c t) {
            t.request(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
        }
    }
}
