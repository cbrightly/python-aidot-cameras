package io.reactivex.schedulers;

import io.reactivex.internal.schedulers.n;
import io.reactivex.internal.schedulers.o;
import io.reactivex.r;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

/* compiled from: Schedulers */
public final class a {
    static final r a = io.reactivex.plugins.a.h(new h());
    static final r b = io.reactivex.plugins.a.e(new b());
    static final r c = io.reactivex.plugins.a.f(new c());
    static final r d = o.e();
    static final r e = io.reactivex.plugins.a.g(new f());

    /* renamed from: io.reactivex.schedulers.a$a  reason: collision with other inner class name */
    /* compiled from: Schedulers */
    public static final class C0311a {
        static final r a = new io.reactivex.internal.schedulers.b();
    }

    /* compiled from: Schedulers */
    public static final class d {
        static final r a = new io.reactivex.internal.schedulers.f();
    }

    /* compiled from: Schedulers */
    public static final class e {
        static final r a = new io.reactivex.internal.schedulers.g();
    }

    /* compiled from: Schedulers */
    public static final class g {
        static final r a = new n();
    }

    public static r a() {
        return io.reactivex.plugins.a.p(b);
    }

    public static r c() {
        return io.reactivex.plugins.a.r(c);
    }

    public static r d() {
        return io.reactivex.plugins.a.s(e);
    }

    public static r e() {
        return io.reactivex.plugins.a.u(a);
    }

    public static r b(Executor executor) {
        return new io.reactivex.internal.schedulers.d(executor, false);
    }

    /* compiled from: Schedulers */
    public static final class c implements Callable<r> {
        c() {
        }

        /* renamed from: a */
        public r call() {
            return d.a;
        }
    }

    /* compiled from: Schedulers */
    public static final class f implements Callable<r> {
        f() {
        }

        /* renamed from: a */
        public r call() {
            return e.a;
        }
    }

    /* compiled from: Schedulers */
    public static final class h implements Callable<r> {
        h() {
        }

        /* renamed from: a */
        public r call() {
            return g.a;
        }
    }

    /* compiled from: Schedulers */
    public static final class b implements Callable<r> {
        b() {
        }

        /* renamed from: a */
        public r call() {
            return C0311a.a;
        }
    }
}
