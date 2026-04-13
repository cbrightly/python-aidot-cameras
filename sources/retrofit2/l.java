package retrofit2;

import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.reflect.Method;
import kotlin.KotlinNullPointerException;
import kotlin.jvm.internal.k;
import kotlin.o;
import kotlin.p;
import kotlin.x;
import kotlinx.coroutines.n;
import kotlinx.coroutines.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KotlinExtensions.kt */
public final class l {

    @kotlin.coroutines.jvm.internal.f(c = "retrofit2.KotlinExtensions", f = "KotlinExtensions.kt", l = {113}, m = "suspendAndThrow")
    /* compiled from: KotlinExtensions.kt */
    public static final class h extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        h(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return l.d((Exception) null, this);
        }
    }

    @Nullable
    public static final <T> Object a(@NotNull d<T> $this$await, @NotNull kotlin.coroutines.d<? super T> $completion) {
        o cancellable$iv = new o(kotlin.coroutines.intrinsics.b.c($completion), 1);
        n continuation = cancellable$iv;
        continuation.f(new a($this$await));
        $this$await.T(new c(continuation));
        Object t = cancellable$iv.t();
        if (t == kotlin.coroutines.intrinsics.c.d()) {
            kotlin.coroutines.jvm.internal.h.c($completion);
        }
        return t;
    }

    /* compiled from: KotlinExtensions.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, x> {
        final /* synthetic */ d $this_await$inlined;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(d dVar) {
            super(1);
            this.$this_await$inlined = dVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return x.a;
        }

        public final void invoke(@Nullable Throwable it) {
            this.$this_await$inlined.cancel();
        }
    }

    /* compiled from: KotlinExtensions.kt */
    public static final class c implements f<T> {
        final /* synthetic */ n c;

        c(n $captured_local_variable$0) {
            this.c = $captured_local_variable$0;
        }

        public void b(@NotNull d<T> call, @NotNull s<T> response) {
            k.f(call, NotificationCompat.CATEGORY_CALL);
            k.f(response, "response");
            if (response.e()) {
                Object body = response.a();
                if (body == null) {
                    Object k = call.g().k(k.class);
                    if (k == null) {
                        k.n();
                    }
                    k.b(k, "call.request().tag(Invocation::class.java)!!");
                    Method method = ((k) k).a();
                    StringBuilder sb = new StringBuilder();
                    sb.append("Response from ");
                    k.b(method, FirebaseAnalytics.Param.METHOD);
                    Class<?> declaringClass = method.getDeclaringClass();
                    k.b(declaringClass, "method.declaringClass");
                    sb.append(declaringClass.getName());
                    sb.append('.');
                    sb.append(method.getName());
                    sb.append(" was null but response body type was declared as non-null");
                    KotlinNullPointerException e = new KotlinNullPointerException(sb.toString());
                    n nVar = this.c;
                    o.a aVar = kotlin.o.Companion;
                    nVar.resumeWith(kotlin.o.m17constructorimpl(p.a(e)));
                    return;
                }
                n nVar2 = this.c;
                o.a aVar2 = kotlin.o.Companion;
                nVar2.resumeWith(kotlin.o.m17constructorimpl(body));
                return;
            }
            n nVar3 = this.c;
            HttpException httpException = new HttpException(response);
            o.a aVar3 = kotlin.o.Companion;
            nVar3.resumeWith(kotlin.o.m17constructorimpl(p.a(httpException)));
        }

        public void a(@NotNull d<T> call, @NotNull Throwable t) {
            k.f(call, NotificationCompat.CATEGORY_CALL);
            k.f(t, "t");
            n nVar = this.c;
            o.a aVar = kotlin.o.Companion;
            nVar.resumeWith(kotlin.o.m17constructorimpl(p.a(t)));
        }
    }

    @Nullable
    public static final <T> Object b(@NotNull d<T> $this$await, @NotNull kotlin.coroutines.d<? super T> $completion) {
        kotlinx.coroutines.o cancellable$iv = new kotlinx.coroutines.o(kotlin.coroutines.intrinsics.b.c($completion), 1);
        n continuation = cancellable$iv;
        continuation.f(new b($this$await));
        $this$await.T(new d(continuation));
        Object t = cancellable$iv.t();
        if (t == kotlin.coroutines.intrinsics.c.d()) {
            kotlin.coroutines.jvm.internal.h.c($completion);
        }
        return t;
    }

    /* compiled from: KotlinExtensions.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, x> {
        final /* synthetic */ d $this_await$inlined;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(d dVar) {
            super(1);
            this.$this_await$inlined = dVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return x.a;
        }

        public final void invoke(@Nullable Throwable it) {
            this.$this_await$inlined.cancel();
        }
    }

    /* compiled from: KotlinExtensions.kt */
    public static final class d implements f<T> {
        final /* synthetic */ n c;

        d(n $captured_local_variable$0) {
            this.c = $captured_local_variable$0;
        }

        public void b(@NotNull d<T> call, @NotNull s<T> response) {
            k.f(call, NotificationCompat.CATEGORY_CALL);
            k.f(response, "response");
            if (response.e()) {
                n nVar = this.c;
                T a = response.a();
                o.a aVar = kotlin.o.Companion;
                nVar.resumeWith(kotlin.o.m17constructorimpl(a));
                return;
            }
            n nVar2 = this.c;
            HttpException httpException = new HttpException(response);
            o.a aVar2 = kotlin.o.Companion;
            nVar2.resumeWith(kotlin.o.m17constructorimpl(p.a(httpException)));
        }

        public void a(@NotNull d<T> call, @NotNull Throwable t) {
            k.f(call, NotificationCompat.CATEGORY_CALL);
            k.f(t, "t");
            n nVar = this.c;
            o.a aVar = kotlin.o.Companion;
            nVar.resumeWith(kotlin.o.m17constructorimpl(p.a(t)));
        }
    }

    @Nullable
    public static final <T> Object c(@NotNull d<T> $this$awaitResponse, @NotNull kotlin.coroutines.d<? super s<T>> $completion) {
        kotlinx.coroutines.o cancellable$iv = new kotlinx.coroutines.o(kotlin.coroutines.intrinsics.b.c($completion), 1);
        n continuation = cancellable$iv;
        continuation.f(new e($this$awaitResponse));
        $this$awaitResponse.T(new f(continuation));
        Object t = cancellable$iv.t();
        if (t == kotlin.coroutines.intrinsics.c.d()) {
            kotlin.coroutines.jvm.internal.h.c($completion);
        }
        return t;
    }

    /* compiled from: KotlinExtensions.kt */
    public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, x> {
        final /* synthetic */ d $this_awaitResponse$inlined;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(d dVar) {
            super(1);
            this.$this_awaitResponse$inlined = dVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return x.a;
        }

        public final void invoke(@Nullable Throwable it) {
            this.$this_awaitResponse$inlined.cancel();
        }
    }

    /* compiled from: KotlinExtensions.kt */
    public static final class f implements f<T> {
        final /* synthetic */ n c;

        f(n $captured_local_variable$0) {
            this.c = $captured_local_variable$0;
        }

        public void b(@NotNull d<T> call, @NotNull s<T> response) {
            k.f(call, NotificationCompat.CATEGORY_CALL);
            k.f(response, "response");
            n nVar = this.c;
            o.a aVar = kotlin.o.Companion;
            nVar.resumeWith(kotlin.o.m17constructorimpl(response));
        }

        public void a(@NotNull d<T> call, @NotNull Throwable t) {
            k.f(call, NotificationCompat.CATEGORY_CALL);
            k.f(t, "t");
            n nVar = this.c;
            o.a aVar = kotlin.o.Companion;
            nVar.resumeWith(kotlin.o.m17constructorimpl(p.a(t)));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object d(@org.jetbrains.annotations.NotNull java.lang.Exception r8, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<?> r9) {
        /*
            boolean r0 = r9 instanceof retrofit2.l.h
            if (r0 == 0) goto L_0x0013
            r0 = r9
            retrofit2.l$h r0 = (retrofit2.l.h) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            retrofit2.l$h r0 = new retrofit2.l$h
            r0.<init>(r9)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0034;
                case 1: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            java.lang.Object r2 = r0.L$0
            r8 = r2
            java.lang.Exception r8 = (java.lang.Exception) r8
            kotlin.p.b(r1)
            goto L_0x005e
        L_0x0034:
            kotlin.p.b(r1)
            r0.L$0 = r8
            r3 = 1
            r0.label = r3
            r3 = r0
            r4 = 0
            kotlinx.coroutines.i0 r5 = kotlinx.coroutines.d1.a()
            kotlin.coroutines.g r6 = r3.getContext()
            retrofit2.l$g r7 = new retrofit2.l$g
            r7.<init>(r3, r8)
            r5.dispatch(r6, r7)
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            java.lang.Object r4 = kotlin.coroutines.intrinsics.c.d()
            if (r3 != r4) goto L_0x005b
            kotlin.coroutines.jvm.internal.h.c(r0)
        L_0x005b:
            if (r3 != r2) goto L_0x005e
            return r2
        L_0x005e:
            kotlin.x r2 = kotlin.x.a
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: retrofit2.l.d(java.lang.Exception, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: KotlinExtensions.kt */
    public static final class g implements Runnable {
        final /* synthetic */ kotlin.coroutines.d c;
        final /* synthetic */ Exception d;

        g(kotlin.coroutines.d dVar, Exception exc) {
            this.c = dVar;
            this.d = exc;
        }

        public final void run() {
            kotlin.coroutines.d c2 = kotlin.coroutines.intrinsics.b.c(this.c);
            Exception exc = this.d;
            o.a aVar = kotlin.o.Companion;
            c2.resumeWith(kotlin.o.m17constructorimpl(p.a(exc)));
        }
    }
}
