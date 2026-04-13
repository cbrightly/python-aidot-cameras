package io.ktor.http.cio;

import io.ktor.utils.io.core.q;
import io.ktor.utils.io.i;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.w0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Multipart.kt */
public abstract class j {
    public abstract void a();

    private j() {
    }

    public /* synthetic */ j(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    /* compiled from: Multipart.kt */
    public static final class c extends j {
        @NotNull
        private final q a;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public c(@NotNull q body) {
            super((DefaultConstructorMarker) null);
            k.f(body, "body");
            this.a = body;
        }

        public void a() {
            this.a.release();
        }
    }

    /* compiled from: Multipart.kt */
    public static final class b extends j {
        @NotNull
        private final w0<f> a;
        @NotNull
        private final i b;

        /* compiled from: Multipart.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<Throwable, x> {
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(b bVar) {
                super(1);
                this.this$0 = bVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((Throwable) obj);
                return x.a;
            }

            public final void invoke(@Nullable Throwable t) {
                if (t != null) {
                    this.this$0.c().g().j();
                }
            }
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull w0<f> headers, @NotNull i body) {
            super((DefaultConstructorMarker) null);
            k.f(headers, "headers");
            k.f(body, "body");
            this.a = headers;
            this.b = body;
        }

        @NotNull
        public final i b() {
            return this.b;
        }

        @NotNull
        public final w0<f> c() {
            return this.a;
        }

        public void a() {
            this.a.t(new a(this));
            Object unused = kotlinx.coroutines.i.b((g) null, new C0253b(this, (d) null), 1, (Object) null);
        }

        @f(c = "io.ktor.http.cio.MultipartEvent$MultipartPart$release$2", f = "Multipart.kt", l = {56}, m = "invokeSuspend")
        /* renamed from: io.ktor.http.cio.j$b$b  reason: collision with other inner class name */
        /* compiled from: Multipart.kt */
        public static final class C0253b extends kotlin.coroutines.jvm.internal.l implements p<o0, d<? super Long>, Object> {
            Object L$0;
            int label;
            private o0 p$;
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0253b(b bVar, d dVar) {
                super(2, dVar);
                this.this$0 = bVar;
            }

            @NotNull
            public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
                k.f(dVar, "completion");
                C0253b bVar = new C0253b(this.this$0, dVar);
                o0 o0Var = (o0) obj;
                bVar.p$ = (o0) obj;
                return bVar;
            }

            public final Object invoke(Object obj, Object obj2) {
                return ((C0253b) create(obj, (d) obj2)).invokeSuspend(x.a);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                Object d = kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b($result);
                        o0 $this$runBlocking = this.p$;
                        i b = this.this$0.b();
                        this.L$0 = $this$runBlocking;
                        this.label = 1;
                        Object d2 = io.ktor.utils.io.k.d(b, this);
                        if (d2 == d) {
                            return d;
                        }
                        o0 o0Var = $this$runBlocking;
                        return d2;
                    case 1:
                        o0 $this$runBlocking2 = (o0) this.L$0;
                        kotlin.p.b($result);
                        return $result;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }
    }

    /* compiled from: Multipart.kt */
    public static final class a extends j {
        @NotNull
        private final q a;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull q body) {
            super((DefaultConstructorMarker) null);
            k.f(body, "body");
            this.a = body;
        }

        public void a() {
            this.a.release();
        }
    }
}
