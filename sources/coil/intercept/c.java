package coil.intercept;

import android.graphics.Bitmap;
import coil.intercept.b;
import coil.request.i;
import coil.size.Size;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.util.List;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RealInterceptorChain.kt */
public final class c implements b.a {
    @NotNull
    private final i a;
    private final int b;
    @NotNull
    private final List<b> c;
    private final int d;
    @NotNull
    private final i e;
    @NotNull
    private final Size f;
    @Nullable
    private final Bitmap g;
    @NotNull
    private final coil.c h;

    @f(c = "coil.intercept.RealInterceptorChain", f = "RealInterceptorChain.kt", l = {27}, m = "proceed")
    /* compiled from: RealInterceptorChain.kt */
    public static final class a extends d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(c cVar, kotlin.coroutines.d<? super a> dVar) {
            super(dVar);
            this.this$0 = cVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.i((i) null, this);
        }
    }

    public c(@NotNull i initialRequest, int requestType, @NotNull List<? extends b> interceptors, int index, @NotNull i request, @NotNull Size size, @Nullable Bitmap cached, @NotNull coil.c eventListener) {
        k.e(initialRequest, "initialRequest");
        k.e(interceptors, "interceptors");
        k.e(request, Progress.REQUEST);
        k.e(size, "size");
        k.e(eventListener, "eventListener");
        this.a = initialRequest;
        this.b = requestType;
        this.c = interceptors;
        this.d = index;
        this.e = request;
        this.f = size;
        this.g = cached;
        this.h = eventListener;
    }

    public final int h() {
        return this.b;
    }

    @NotNull
    public final List<b> g() {
        return this.c;
    }

    public final int f() {
        return this.d;
    }

    @NotNull
    public i getRequest() {
        return this.e;
    }

    @NotNull
    public Size getSize() {
        return this.f;
    }

    @Nullable
    public final Bitmap d() {
        return this.g;
    }

    @NotNull
    public final coil.c e() {
        return this.h;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: coil.intercept.b} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v5, resolved type: coil.intercept.c} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object i(@org.jetbrains.annotations.NotNull coil.request.i r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super coil.request.j> r13) {
        /*
            r11 = this;
            boolean r0 = r13 instanceof coil.intercept.c.a
            if (r0 == 0) goto L_0x0013
            r0 = r13
            coil.intercept.c$a r0 = (coil.intercept.c.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            coil.intercept.c$a r0 = new coil.intercept.c$a
            r0.<init>(r11, r13)
        L_0x0018:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x003e;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L_0x002c:
            r12 = r11
            r1 = 0
            java.lang.Object r2 = r13.L$1
            r1 = r2
            coil.intercept.b r1 = (coil.intercept.b) r1
            java.lang.Object r2 = r13.L$0
            r12 = r2
            coil.intercept.c r12 = (coil.intercept.c) r12
            kotlin.p.b(r0)
            r8 = r12
            r12 = r0
            goto L_0x0087
        L_0x003e:
            kotlin.p.b(r0)
            r8 = r11
            int r2 = r8.f()
            r9 = 1
            if (r2 <= 0) goto L_0x005b
            java.util.List r2 = r8.g()
            int r3 = r8.f()
            int r3 = r3 - r9
            java.lang.Object r2 = r2.get(r3)
            coil.intercept.b r2 = (coil.intercept.b) r2
            r8.a(r12, r2)
        L_0x005b:
            java.util.List r2 = r8.g()
            int r3 = r8.f()
            java.lang.Object r2 = r2.get(r3)
            r10 = r2
            coil.intercept.b r10 = (coil.intercept.b) r10
            int r2 = r8.f()
            int r3 = r2 + 1
            r5 = 0
            r6 = 4
            r7 = 0
            r2 = r8
            r4 = r12
            coil.intercept.c r2 = c(r2, r3, r4, r5, r6, r7)
            r13.L$0 = r8
            r13.L$1 = r10
            r13.label = r9
            java.lang.Object r12 = r10.a(r2, r13)
            if (r12 != r1) goto L_0x0086
            return r1
        L_0x0086:
            r1 = r10
        L_0x0087:
            coil.request.j r12 = (coil.request.j) r12
            coil.request.i r2 = r12.b()
            r8.a(r2, r1)
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.intercept.c.i(coil.request.i, kotlin.coroutines.d):java.lang.Object");
    }

    private final void a(i request, b interceptor) {
        boolean z = true;
        if (request.l() == this.a.l()) {
            if (request.m() != coil.request.k.a) {
                if (request.I() == this.a.I()) {
                    if (request.w() == this.a.w()) {
                        if (request.H() != this.a.H()) {
                            z = false;
                        }
                        if (!z) {
                            throw new IllegalStateException(("Interceptor '" + interceptor + "' cannot modify the request's size resolver. Use `Interceptor.Chain.withSize` instead.").toString());
                        }
                        return;
                    }
                    throw new IllegalStateException(("Interceptor '" + interceptor + "' cannot modify the request's lifecycle.").toString());
                }
                throw new IllegalStateException(("Interceptor '" + interceptor + "' cannot modify the request's target.").toString());
            }
            throw new IllegalStateException(("Interceptor '" + interceptor + "' cannot set the request's data to null.").toString());
        }
        throw new IllegalStateException(("Interceptor '" + interceptor + "' cannot modify the request's context.").toString());
    }

    static /* synthetic */ c c(c cVar, int i, i iVar, Size size, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = cVar.d;
        }
        if ((i2 & 2) != 0) {
            iVar = cVar.getRequest();
        }
        if ((i2 & 4) != 0) {
            size = cVar.getSize();
        }
        return cVar.b(i, iVar, size);
    }

    private final c b(int index, i request, Size size) {
        return new c(this.a, this.b, this.c, index, request, size, this.g, this.h);
    }
}
