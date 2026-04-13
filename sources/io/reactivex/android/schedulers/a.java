package io.reactivex.android.schedulers;

import android.os.Handler;
import android.os.Looper;
import io.reactivex.r;
import java.util.concurrent.Callable;

/* compiled from: AndroidSchedulers */
public final class a {
    private static final r a = io.reactivex.android.plugins.a.d(new C0294a());

    /* compiled from: AndroidSchedulers */
    public static final class b {
        static final r a = new b(new Handler(Looper.getMainLooper()), false);
    }

    /* renamed from: io.reactivex.android.schedulers.a$a  reason: collision with other inner class name */
    /* compiled from: AndroidSchedulers */
    public static final class C0294a implements Callable<r> {
        C0294a() {
        }

        /* renamed from: a */
        public r call() {
            return b.a;
        }
    }

    public static r a() {
        return io.reactivex.android.plugins.a.e(a);
    }
}
