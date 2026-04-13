package com.trello.rxlifecycle3;

import io.reactivex.functions.f;
import io.reactivex.functions.h;
import java.util.concurrent.CancellationException;

/* compiled from: Functions */
public final class a {
    static final f<Throwable, Boolean> a = new C0225a();
    static final h<Boolean> b = new b();
    static final f<Object, io.reactivex.b> c = new c();

    /* renamed from: com.trello.rxlifecycle3.a$a  reason: collision with other inner class name */
    /* compiled from: Functions */
    public static final class C0225a implements f<Throwable, Boolean> {
        C0225a() {
        }

        /* renamed from: a */
        public Boolean apply(Throwable throwable) {
            if (throwable instanceof OutsideLifecycleException) {
                return true;
            }
            io.reactivex.exceptions.a.a(throwable);
            return false;
        }
    }

    /* compiled from: Functions */
    public static final class b implements h<Boolean> {
        b() {
        }

        /* renamed from: a */
        public boolean test(Boolean shouldComplete) {
            return shouldComplete.booleanValue();
        }
    }

    /* compiled from: Functions */
    public static final class c implements f<Object, io.reactivex.b> {
        c() {
        }

        /* renamed from: a */
        public io.reactivex.b apply(Object ignore) {
            return io.reactivex.b.a(new CancellationException());
        }
    }
}
