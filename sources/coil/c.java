package coil;

import android.graphics.Bitmap;
import androidx.annotation.AnyThread;
import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import coil.decode.e;
import coil.decode.m;
import coil.fetch.f;
import coil.fetch.g;
import coil.request.i;
import coil.request.j;
import coil.size.Size;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: EventListener.kt */
public interface c extends i.b {
    @NotNull
    public static final b a = b.a;
    @NotNull
    public static final c b = new a();

    @MainThread
    void a(@NotNull i iVar);

    @MainThread
    void b(@NotNull i iVar);

    @MainThread
    void c(@NotNull i iVar, @NotNull Throwable th);

    @MainThread
    void d(@NotNull i iVar, @NotNull j.a aVar);

    @AnyThread
    void e(@NotNull i iVar, @NotNull Object obj);

    @WorkerThread
    void f(@NotNull i iVar, @NotNull g<?> gVar, @NotNull m mVar);

    @MainThread
    void g(@NotNull i iVar);

    @AnyThread
    void h(@NotNull i iVar, @NotNull Object obj);

    @WorkerThread
    void i(@NotNull i iVar, @NotNull e eVar, @NotNull m mVar, @NotNull coil.decode.c cVar);

    @WorkerThread
    void j(@NotNull i iVar, @NotNull g<?> gVar, @NotNull m mVar, @NotNull f fVar);

    @WorkerThread
    void k(@NotNull i iVar, @NotNull Bitmap bitmap);

    @MainThread
    void l(@NotNull i iVar, @NotNull Size size);

    @WorkerThread
    void m(@NotNull i iVar, @NotNull Bitmap bitmap);

    @WorkerThread
    void n(@NotNull i iVar, @NotNull e eVar, @NotNull m mVar);

    @MainThread
    void o(@NotNull i iVar);

    @MainThread
    void p(@NotNull i iVar);

    /* renamed from: coil.c$c  reason: collision with other inner class name */
    /* compiled from: EventListener.kt */
    public static final class C0000c {
        @MainThread
        public static void i(@NotNull c cVar, @NotNull i request) {
            k.e(cVar, "this");
            k.e(request, Progress.REQUEST);
        }

        @MainThread
        public static void l(@NotNull c cVar, @NotNull i request) {
            k.e(cVar, "this");
            k.e(request, Progress.REQUEST);
        }

        @MainThread
        public static void k(@NotNull c cVar, @NotNull i request, @NotNull Size size) {
            k.e(cVar, "this");
            k.e(request, Progress.REQUEST);
            k.e(size, "size");
        }

        @AnyThread
        public static void f(@NotNull c cVar, @NotNull i request, @NotNull Object input) {
            k.e(cVar, "this");
            k.e(request, Progress.REQUEST);
            k.e(input, "input");
        }

        @AnyThread
        public static void e(@NotNull c cVar, @NotNull i request, @NotNull Object output) {
            k.e(cVar, "this");
            k.e(request, Progress.REQUEST);
            k.e(output, "output");
        }

        @WorkerThread
        public static void d(@NotNull c cVar, @NotNull i request, @NotNull g<?> fetcher, @NotNull m options) {
            k.e(cVar, "this");
            k.e(request, Progress.REQUEST);
            k.e(fetcher, "fetcher");
            k.e(options, "options");
        }

        @WorkerThread
        public static void c(@NotNull c cVar, @NotNull i request, @NotNull g<?> fetcher, @NotNull m options, @NotNull f result) {
            k.e(cVar, "this");
            k.e(request, Progress.REQUEST);
            k.e(fetcher, "fetcher");
            k.e(options, "options");
            k.e(result, "result");
        }

        @WorkerThread
        public static void b(@NotNull c cVar, @NotNull i request, @NotNull e decoder, @NotNull m options) {
            k.e(cVar, "this");
            k.e(request, Progress.REQUEST);
            k.e(decoder, "decoder");
            k.e(options, "options");
        }

        @WorkerThread
        public static void a(@NotNull c cVar, @NotNull i request, @NotNull e decoder, @NotNull m options, @NotNull coil.decode.c result) {
            k.e(cVar, "this");
            k.e(request, Progress.REQUEST);
            k.e(decoder, "decoder");
            k.e(options, "options");
            k.e(result, "result");
        }

        @WorkerThread
        public static void n(@NotNull c cVar, @NotNull i request, @NotNull Bitmap input) {
            k.e(cVar, "this");
            k.e(request, Progress.REQUEST);
            k.e(input, "input");
        }

        @WorkerThread
        public static void m(@NotNull c cVar, @NotNull i request, @NotNull Bitmap output) {
            k.e(cVar, "this");
            k.e(request, Progress.REQUEST);
            k.e(output, "output");
        }

        @MainThread
        public static void p(@NotNull c cVar, @NotNull i request) {
            k.e(cVar, "this");
            k.e(request, Progress.REQUEST);
        }

        @MainThread
        public static void o(@NotNull c cVar, @NotNull i request) {
            k.e(cVar, "this");
            k.e(request, Progress.REQUEST);
        }

        @MainThread
        public static void g(@NotNull c cVar, @NotNull i request) {
            k.e(cVar, "this");
            k.e(request, Progress.REQUEST);
        }

        @MainThread
        public static void h(@NotNull c cVar, @NotNull i request, @NotNull Throwable throwable) {
            k.e(cVar, "this");
            k.e(request, Progress.REQUEST);
            k.e(throwable, "throwable");
        }

        @MainThread
        public static void j(@NotNull c cVar, @NotNull i request, @NotNull j.a metadata) {
            k.e(cVar, "this");
            k.e(request, Progress.REQUEST);
            k.e(metadata, "metadata");
        }
    }

    /* compiled from: EventListener.kt */
    public interface d {
        @NotNull
        public static final a a;
        @NotNull
        public static final d b;

        @NotNull
        c a(@NotNull i iVar);

        /* compiled from: EventListener.kt */
        public static final class a {
            static final /* synthetic */ a a = new a();

            private a() {
            }

            /* access modifiers changed from: private */
            public static final c b(c $listener, i it) {
                k.e($listener, "$listener");
                k.e(it, "it");
                return $listener;
            }

            @NotNull
            public final d a(@NotNull c listener) {
                k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
                return new a(listener);
            }
        }

        static {
            a aVar = a.a;
            a = aVar;
            b = aVar.a(c.b);
        }
    }

    /* compiled from: EventListener.kt */
    public static final class b {
        static final /* synthetic */ b a = new b();

        private b() {
        }
    }

    /* compiled from: EventListener.kt */
    public static final class a implements c {
        a() {
        }

        @MainThread
        public void a(@NotNull i request) {
            C0000c.g(this, request);
        }

        @MainThread
        public void b(@NotNull i request) {
            C0000c.i(this, request);
        }

        @MainThread
        public void c(@NotNull i request, @NotNull Throwable throwable) {
            C0000c.h(this, request, throwable);
        }

        @MainThread
        public void d(@NotNull i request, @NotNull j.a metadata) {
            C0000c.j(this, request, metadata);
        }

        @AnyThread
        public void e(@NotNull i request, @NotNull Object output) {
            C0000c.e(this, request, output);
        }

        @WorkerThread
        public void f(@NotNull i request, @NotNull g<?> fetcher, @NotNull m options) {
            C0000c.d(this, request, fetcher, options);
        }

        @MainThread
        public void g(@NotNull i request) {
            C0000c.o(this, request);
        }

        @AnyThread
        public void h(@NotNull i request, @NotNull Object input) {
            C0000c.f(this, request, input);
        }

        @WorkerThread
        public void i(@NotNull i request, @NotNull e decoder, @NotNull m options, @NotNull coil.decode.c result) {
            C0000c.a(this, request, decoder, options, result);
        }

        @WorkerThread
        public void j(@NotNull i request, @NotNull g<?> fetcher, @NotNull m options, @NotNull f result) {
            C0000c.c(this, request, fetcher, options, result);
        }

        @WorkerThread
        public void k(@NotNull i request, @NotNull Bitmap input) {
            C0000c.n(this, request, input);
        }

        @MainThread
        public void l(@NotNull i request, @NotNull Size size) {
            C0000c.k(this, request, size);
        }

        @WorkerThread
        public void m(@NotNull i request, @NotNull Bitmap output) {
            C0000c.m(this, request, output);
        }

        @WorkerThread
        public void n(@NotNull i request, @NotNull e decoder, @NotNull m options) {
            C0000c.b(this, request, decoder, options);
        }

        @MainThread
        public void o(@NotNull i request) {
            C0000c.l(this, request);
        }

        @MainThread
        public void p(@NotNull i request) {
            C0000c.p(this, request);
        }
    }
}
