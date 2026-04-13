package io.ktor.features;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.maps.android.BuildConfig;
import io.ktor.http.c;
import io.ktor.http.s;
import io.ktor.http.u;
import io.ktor.http.v;
import io.ktor.util.a0;
import io.ktor.util.g;
import io.ktor.util.i;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kotlin.TypeCastException;
import kotlin.collections.o0;
import kotlin.collections.p0;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.p;
import kotlin.text.j;
import kotlin.text.w;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.e;

/* compiled from: CORS.kt */
public final class b {
    /* access modifiers changed from: private */
    @NotNull
    public static final io.ktor.util.a<b> a = new io.ktor.util.a<>("CORS");
    public static final C0241b b = new C0241b((DefaultConstructorMarker) null);
    private final j c = new j("[0-9]+");
    private final boolean d;
    private final boolean e;
    private final boolean f;
    @NotNull
    private final Set<String> g;
    @NotNull
    private final Set<u> h;
    @NotNull
    private final Set<String> i;
    private final boolean j;
    private final String k;
    private final String l;
    private final String m;
    private final String n;
    private final HashSet<String> o;

    @f(c = "io.ktor.features.CORS", f = "CORS.kt", l = {94, 99, 107}, m = "intercept")
    /* compiled from: CORS.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ b this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(b bVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = bVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.j((io.ktor.util.pipeline.d<x, io.ktor.application.b>) null, this);
        }
    }

    @f(c = "io.ktor.features.CORS", f = "CORS.kt", l = {510}, m = "respondCorsFailed")
    /* compiled from: CORS.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ b this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(b bVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = bVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.n((io.ktor.util.pipeline.d<x, io.ktor.application.b>) null, this);
        }
    }

    public b(@NotNull a configuration) {
        k.f(configuration, "configuration");
        this.d = configuration.f();
        this.e = configuration.i().contains(e.ANY_MARKER);
        this.f = configuration.d();
        Set<String> h2 = configuration.h();
        a.C0240a aVar = a.f;
        Set<String> headers = p0.i(h2, aVar.b());
        headers = !configuration.e() ? p0.h(headers, s.V0.s()) : headers;
        this.g = headers;
        this.h = new HashSet(p0.i(configuration.k(), aVar.a()));
        Collection destination$iv$iv = new ArrayList(r.r(headers, 10));
        for (String it : headers) {
            destination$iv$iv.add(a0.c(it));
        }
        this.i = y.H0(destination$iv$iv);
        this.j = configuration.e();
        Iterable $this$filterNotTo$iv$iv = configuration.h();
        List destination$iv$iv2 = new ArrayList();
        for (T next : $this$filterNotTo$iv$iv) {
            if (!a.f.b().contains((String) next)) {
                destination$iv$iv2.add(next);
            }
        }
        List it2 = destination$iv$iv2;
        this.k = y.b0(y.t0(this.j ? y.o0(it2, s.V0.s()) : it2), ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (l) null, 62, (Object) null);
        Iterable $this$filterNotTo$iv$iv2 = this.h;
        ArrayList arrayList = new ArrayList();
        for (T next2 : $this$filterNotTo$iv$iv2) {
            if (!a.f.a().contains((u) next2)) {
                arrayList.add(next2);
            }
        }
        Iterable<u> $this$mapTo$iv$iv = arrayList;
        Collection destination$iv$iv3 = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (u it3 : $this$mapTo$iv$iv) {
            destination$iv$iv3.add(it3.i());
        }
        this.l = y.b0(y.t0(destination$iv$iv3), ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (l) null, 62, (Object) null);
        long it4 = configuration.j();
        String str = null;
        this.m = it4 > 0 ? String.valueOf(it4) : null;
        this.n = configuration.g().isEmpty() ^ true ? y.b0(y.t0(configuration.g()), ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (l) null, 62, (Object) null) : str;
        Iterable<String> $this$mapTo$iv$iv2 = configuration.i();
        Collection destination$iv$iv4 = new ArrayList(r.r($this$mapTo$iv$iv2, 10));
        for (String it5 : $this$mapTo$iv$iv2) {
            destination$iv$iv4.add(m(it5));
        }
        this.o = new HashSet<>(destination$iv$iv4);
    }

    /* Debug info: failed to restart local var, previous not found, register: 9 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v4, resolved type: io.ktor.util.pipeline.d<kotlin.x, io.ktor.application.b>} */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ec, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0115, code lost:
        r10.o();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x011a, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0137, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object j(@org.jetbrains.annotations.NotNull io.ktor.util.pipeline.d<kotlin.x, io.ktor.application.b> r10, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof io.ktor.features.b.c
            if (r0 == 0) goto L_0x0013
            r0 = r11
            io.ktor.features.b$c r0 = (io.ktor.features.b.c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.features.b$c r0 = new io.ktor.features.b$c
            r0.<init>(r9, r11)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x007a;
                case 1: goto L_0x0060;
                case 2: goto L_0x0046;
                case 3: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002c:
            r2 = r4
            r3 = r4
            java.lang.Object r4 = r0.L$3
            r2 = r4
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r4 = r0.L$2
            r3 = r4
            io.ktor.application.b r3 = (io.ktor.application.b) r3
            java.lang.Object r4 = r0.L$1
            r10 = r4
            io.ktor.util.pipeline.d r10 = (io.ktor.util.pipeline.d) r10
            java.lang.Object r4 = r0.L$0
            io.ktor.features.b r4 = (io.ktor.features.b) r4
            kotlin.p.b(r1)
            goto L_0x0135
        L_0x0046:
            r2 = r4
            r3 = r4
            java.lang.Object r4 = r0.L$3
            r2 = r4
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r4 = r0.L$2
            r3 = r4
            io.ktor.application.b r3 = (io.ktor.application.b) r3
            java.lang.Object r4 = r0.L$1
            r10 = r4
            io.ktor.util.pipeline.d r10 = (io.ktor.util.pipeline.d) r10
            java.lang.Object r4 = r0.L$0
            io.ktor.features.b r4 = (io.ktor.features.b) r4
            kotlin.p.b(r1)
            goto L_0x0115
        L_0x0060:
            r2 = r4
            r3 = r4
            java.lang.Object r4 = r0.L$3
            r2 = r4
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r4 = r0.L$2
            r3 = r4
            io.ktor.application.b r3 = (io.ktor.application.b) r3
            java.lang.Object r4 = r0.L$1
            r10 = r4
            io.ktor.util.pipeline.d r10 = (io.ktor.util.pipeline.d) r10
            java.lang.Object r4 = r0.L$0
            io.ktor.features.b r4 = (io.ktor.features.b) r4
            kotlin.p.b(r1)
            goto L_0x00ea
        L_0x007a:
            kotlin.p.b(r1)
            r3 = r10
            r5 = 0
            java.lang.Object r6 = r3.getContext()
            io.ktor.application.b r6 = (io.ktor.application.b) r6
            r3 = r6
            boolean r5 = r9.e
            if (r5 == 0) goto L_0x008e
            boolean r5 = r9.f
            if (r5 == 0) goto L_0x0091
        L_0x008e:
            r9.i(r3)
        L_0x0091:
            io.ktor.request.d r5 = r3.getRequest()
            io.ktor.http.o r5 = r5.getHeaders()
            io.ktor.http.s r6 = io.ktor.http.s.V0
            java.lang.String r7 = r6.x()
            java.util.List r5 = r5.c(r7)
            if (r5 == 0) goto L_0x0152
            java.lang.Object r5 = kotlin.collections.y.s0(r5)
            java.lang.String r5 = (java.lang.String) r5
            if (r5 == 0) goto L_0x0152
            r7 = r5
            r8 = 0
            boolean r7 = r9.l(r7)
            java.lang.Boolean r7 = kotlin.coroutines.jvm.internal.b.a(r7)
            boolean r7 = r7.booleanValue()
            if (r7 == 0) goto L_0x00c0
            r4 = r5
        L_0x00c0:
            if (r4 == 0) goto L_0x0152
            boolean r5 = r9.d
            if (r5 == 0) goto L_0x00d0
            boolean r5 = r9.k(r3, r4)
            if (r5 == 0) goto L_0x00d0
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x00d0:
            boolean r5 = r9.f(r4)
            if (r5 != 0) goto L_0x00ed
            r0.L$0 = r9
            r0.L$1 = r10
            r0.L$2 = r3
            r0.L$3 = r4
            r5 = 1
            r0.label = r5
            java.lang.Object r5 = r9.n(r10, r0)
            if (r5 != r2) goto L_0x00e8
            return r2
        L_0x00e8:
            r2 = r4
            r4 = r9
        L_0x00ea:
            kotlin.x r5 = kotlin.x.a
            return r5
        L_0x00ed:
            io.ktor.request.d r5 = r3.getRequest()
            io.ktor.http.u r5 = io.ktor.request.e.d(r5)
            io.ktor.http.u$a r7 = io.ktor.http.u.i
            io.ktor.http.u r7 = r7.e()
            boolean r5 = kotlin.jvm.internal.k.a(r5, r7)
            if (r5 == 0) goto L_0x011b
            r0.L$0 = r9
            r0.L$1 = r10
            r0.L$2 = r3
            r0.L$3 = r4
            r5 = 2
            r0.label = r5
            java.lang.Object r5 = r9.o(r3, r4, r0)
            if (r5 != r2) goto L_0x0113
            return r2
        L_0x0113:
            r2 = r4
            r4 = r9
        L_0x0115:
            r10.o()
            kotlin.x r5 = kotlin.x.a
            return r5
        L_0x011b:
            boolean r5 = r9.e(r3)
            if (r5 != 0) goto L_0x0138
            r0.L$0 = r9
            r0.L$1 = r10
            r0.L$2 = r3
            r0.L$3 = r4
            r5 = 3
            r0.label = r5
            java.lang.Object r5 = r9.n(r10, r0)
            if (r5 != r2) goto L_0x0133
            return r2
        L_0x0133:
            r2 = r4
            r4 = r9
        L_0x0135:
            kotlin.x r5 = kotlin.x.a
            return r5
        L_0x0138:
            r9.c(r3, r4)
            r9.b(r3)
            java.lang.String r2 = r9.n
            if (r2 == 0) goto L_0x014f
            io.ktor.response.a r2 = r3.b()
            java.lang.String r5 = r6.j()
            java.lang.String r6 = r9.n
            io.ktor.response.c.a(r2, r5, r6)
        L_0x014f:
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x0152:
            kotlin.x r2 = kotlin.x.a
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.features.b.j(io.ktor.util.pipeline.d, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final /* synthetic */ Object o(@NotNull io.ktor.application.b $this$respondPreflight, @NotNull String origin, @NotNull kotlin.coroutines.d<? super x> $completion) {
        if (!h($this$respondPreflight) || !g($this$respondPreflight)) {
            io.ktor.application.b $this$respond$iv = $this$respondPreflight;
            Object e2 = $this$respond$iv.b().a().e($this$respond$iv, v.c0.i(), $completion);
            if (e2 == kotlin.coroutines.intrinsics.c.d()) {
                return e2;
            }
            return x.a;
        }
        c($this$respondPreflight, origin);
        b($this$respondPreflight);
        boolean z = true;
        if (this.l.length() > 0) {
            io.ktor.response.c.a($this$respondPreflight.b(), s.V0.h(), this.l);
        }
        if (this.k.length() <= 0) {
            z = false;
        }
        if (z) {
            io.ktor.response.c.a($this$respondPreflight.b(), s.V0.g(), this.k);
        }
        d($this$respondPreflight);
        io.ktor.application.b $this$respond$iv2 = $this$respondPreflight;
        Object e3 = $this$respond$iv2.b().a().e($this$respond$iv2, v.c0.A(), $completion);
        if (e3 == kotlin.coroutines.intrinsics.c.d()) {
            return e3;
        }
        return x.a;
    }

    private final void c(@NotNull io.ktor.application.b $this$accessControlAllowOrigin, String origin) {
        if (!this.e || this.f) {
            io.ktor.response.c.a($this$accessControlAllowOrigin.b(), s.V0.i(), origin);
        } else {
            io.ktor.response.c.a($this$accessControlAllowOrigin.b(), s.V0.i(), e.ANY_MARKER);
        }
    }

    private final void i(@NotNull io.ktor.application.b $this$corsVary) {
        io.ktor.response.f headers = $this$corsVary.b().getHeaders();
        s sVar = s.V0;
        String vary = headers.d(sVar.C());
        if (vary == null) {
            io.ktor.response.c.a($this$corsVary.b(), sVar.C(), sVar.x());
            return;
        }
        io.ktor.response.a b2 = $this$corsVary.b();
        String C = sVar.C();
        io.ktor.response.c.a(b2, C, vary + ", " + sVar.x());
    }

    private final void b(@NotNull io.ktor.application.b $this$accessControlAllowCredentials) {
        if (this.f) {
            io.ktor.response.c.a($this$accessControlAllowCredentials.b(), s.V0.f(), "true");
        }
    }

    private final void d(@NotNull io.ktor.application.b $this$accessControlMaxAge) {
        if (this.m != null) {
            io.ktor.response.c.a($this$accessControlMaxAge.b(), s.V0.k(), this.m);
        }
    }

    private final boolean k(@NotNull io.ktor.application.b $this$isSameOrigin, String origin) {
        return k.a(m(n.a($this$isSameOrigin.getRequest()).c() + "://" + n.a($this$isSameOrigin.getRequest()).b() + ':' + n.a($this$isSameOrigin.getRequest()).a()), m(origin));
    }

    private final boolean f(String origin) {
        return this.e || this.o.contains(m(origin));
    }

    private final boolean g(@NotNull io.ktor.application.b $this$corsCheckRequestHeaders) {
        Collection destination$iv$iv;
        Iterable<String> $this$flatMap$iv = $this$corsCheckRequestHeaders.getRequest().getHeaders().c(s.V0.l());
        if ($this$flatMap$iv != null) {
            ArrayList arrayList = new ArrayList();
            for (String F0 : $this$flatMap$iv) {
                kotlin.collections.v.x(arrayList, kotlin.text.x.F0(F0, new String[]{","}, false, 0, 6, (Object) null));
            }
            Iterable<String> $this$mapTo$iv$iv = arrayList;
            destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (String it : $this$mapTo$iv$iv) {
                if (it != null) {
                    destination$iv$iv.add(a0.c(kotlin.text.x.e1(it).toString()));
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                }
            }
        } else {
            destination$iv$iv = q.g();
        }
        Collection<String> collection = destination$iv$iv;
        if (!(collection instanceof Collection) || !collection.isEmpty()) {
            for (String it2 : collection) {
                if (!this.i.contains(it2)) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    private final boolean e(@NotNull io.ktor.application.b $this$corsCheckCurrentMethod) {
        return this.h.contains(io.ktor.request.e.d($this$corsCheckCurrentMethod.getRequest()));
    }

    private final boolean h(@NotNull io.ktor.application.b $this$corsCheckRequestMethod) {
        String it = io.ktor.request.e.f($this$corsCheckRequestMethod.getRequest(), s.V0.m());
        u requestMethod = it != null ? new u(it) : null;
        return requestMethod != null && this.h.contains(requestMethod);
    }

    /* Debug info: failed to restart local var, previous not found, register: 8 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v2, resolved type: io.ktor.util.pipeline.d<kotlin.x, io.ktor.application.b>} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object n(@org.jetbrains.annotations.NotNull io.ktor.util.pipeline.d<kotlin.x, io.ktor.application.b> r9, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof io.ktor.features.b.d
            if (r0 == 0) goto L_0x0013
            r0 = r10
            io.ktor.features.b$d r0 = (io.ktor.features.b.d) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.features.b$d r0 = new io.ktor.features.b$d
            r0.<init>(r8, r10)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0045;
                case 1: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            r2 = 0
            r3 = r2
            r4 = 0
            java.lang.Object r5 = r0.L$3
            r2 = r5
            io.ktor.http.v r2 = (io.ktor.http.v) r2
            java.lang.Object r5 = r0.L$2
            r3 = r5
            io.ktor.application.b r3 = (io.ktor.application.b) r3
            java.lang.Object r5 = r0.L$1
            r9 = r5
            io.ktor.util.pipeline.d r9 = (io.ktor.util.pipeline.d) r9
            java.lang.Object r5 = r0.L$0
            io.ktor.features.b r5 = (io.ktor.features.b) r5
            kotlin.p.b(r1)
            goto L_0x0076
        L_0x0045:
            kotlin.p.b(r1)
            r3 = r9
            r4 = 0
            java.lang.Object r5 = r3.getContext()
            io.ktor.application.b r5 = (io.ktor.application.b) r5
            io.ktor.http.v$a r3 = io.ktor.http.v.c0
            io.ktor.http.v r3 = r3.i()
            r4 = r5
            r5 = 0
            io.ktor.response.a r6 = r4.b()
            io.ktor.response.d r6 = r6.a()
            r0.L$0 = r8
            r0.L$1 = r9
            r0.L$2 = r4
            r0.L$3 = r3
            r7 = 1
            r0.label = r7
            java.lang.Object r6 = r6.e(r4, r3, r0)
            if (r6 != r2) goto L_0x0072
            return r2
        L_0x0072:
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r8
        L_0x0076:
            r9.o()
            kotlin.x r2 = kotlin.x.a
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.features.b.n(io.ktor.util.pipeline.d, kotlin.coroutines.d):java.lang.Object");
    }

    private final boolean l(String origin) {
        int protoDelimiter;
        if (origin.length() == 0) {
            return false;
        }
        if (k.a(origin, BuildConfig.TRAVIS)) {
            return true;
        }
        if (kotlin.text.x.S(origin, "%", false, 2, (Object) null) || (protoDelimiter = kotlin.text.x.f0(origin, "://", 0, false, 6, (Object) null)) <= 0) {
            return false;
        }
        for (int index = 0; index < protoDelimiter; index++) {
            if (!Character.isLetter(origin.charAt(index))) {
                return false;
            }
        }
        int portIndex = origin.length();
        int index2 = protoDelimiter + 3;
        int length = origin.length();
        while (true) {
            if (index2 >= length) {
                break;
            }
            char ch = origin.charAt(index2);
            if (ch == ':' || ch == '/') {
                portIndex = index2 + 1;
            } else if (ch == '?') {
                return false;
            } else {
                index2++;
            }
        }
        int index3 = origin.length();
        for (int index4 = portIndex; index4 < index3; index4++) {
            if (!Character.isDigit(origin.charAt(index4))) {
                return false;
            }
        }
        return true;
    }

    private final String m(String origin) {
        if (k.a(origin, BuildConfig.TRAVIS) || k.a(origin, e.ANY_MARKER)) {
            return origin;
        }
        StringBuilder sb = new StringBuilder(origin.length());
        StringBuilder $this$apply = sb;
        $this$apply.append(origin);
        if (!this.c.matches(kotlin.text.x.T0(origin, ":", ""))) {
            String str = null;
            String Y0 = kotlin.text.x.Y0(origin, ':', (String) null, 2, (Object) null);
            switch (Y0.hashCode()) {
                case 3213448:
                    if (Y0.equals(org.apache.http.l.DEFAULT_SCHEME_NAME)) {
                        str = "80";
                        break;
                    }
                    break;
                case 99617003:
                    if (Y0.equals("https")) {
                        str = "443";
                        break;
                    }
                    break;
            }
            String port = str;
            if (port != null) {
                $this$apply.append(':');
                $this$apply.append(port);
            }
        }
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder(origin.len…   }\n        }.toString()");
        return sb2;
    }

    /* compiled from: CORS.kt */
    public static final class a {
        /* access modifiers changed from: private */
        @NotNull
        public static final Set<u> a;
        @NotNull
        private static final Set<String> b;
        /* access modifiers changed from: private */
        @NotNull
        public static final Set<String> c;
        @NotNull
        private static final Set<String> d;
        @NotNull
        private static final Set<io.ktor.http.c> e = i.a(o0.g(c.a.t.a(), c.C0247c.i.a(), c.d.j.a()));
        public static final C0240a f = new C0240a((DefaultConstructorMarker) null);
        @NotNull
        private final Set<String> g = new HashSet();
        @NotNull
        private final Set<String> h = new g();
        @NotNull
        private final Set<u> i = new HashSet();
        @NotNull
        private final Set<String> j = new g();
        private boolean k;
        private long l = 86400;
        private boolean m = true;
        private boolean n;

        /* renamed from: io.ktor.features.b$a$a  reason: collision with other inner class name */
        /* compiled from: CORS.kt */
        public static final class C0240a {
            private C0240a() {
            }

            public /* synthetic */ C0240a(DefaultConstructorMarker $constructor_marker) {
                this();
            }

            @NotNull
            public final Set<u> a() {
                return a.a;
            }

            @NotNull
            public final Set<String> b() {
                return a.c;
            }
        }

        static {
            u.a aVar = u.i;
            a = o0.g(aVar.c(), aVar.g(), aVar.d());
            C0241b bVar = b.b;
            s sVar = s.V0;
            b = bVar.c(sVar.n(), sVar.q(), sVar.s(), sVar.u(), sVar.v(), sVar.y());
            c = bVar.c(sVar.c(), sVar.e(), sVar.q(), sVar.s());
            d = bVar.c(sVar.n(), sVar.q(), sVar.s(), sVar.u(), sVar.v(), sVar.y());
        }

        @NotNull
        public final Set<String> i() {
            return this.g;
        }

        @NotNull
        public final Set<String> h() {
            return this.h;
        }

        @NotNull
        public final Set<u> k() {
            return this.i;
        }

        @NotNull
        public final Set<String> g() {
            return this.j;
        }

        public final boolean d() {
            return this.k;
        }

        public final void n(boolean z) {
            this.k = z;
        }

        public final long j() {
            return this.l;
        }

        public final void o(long newMaxAge) {
            if (newMaxAge >= 0) {
                this.l = newMaxAge;
                return;
            }
            throw new IllegalStateException(("maxAgeInSeconds shouldn't be negative: " + newMaxAge).toString());
        }

        public final boolean f() {
            return this.m;
        }

        public final boolean e() {
            return this.n;
        }

        public final void c() {
            this.g.add(e.ANY_MARKER);
        }

        public final void l(@NotNull String header) {
            k.f(header, "header");
            if (w.y(header, s.V0.s(), true)) {
                this.n = true;
            } else if (!c.contains(header)) {
                this.h.add(header);
            }
        }

        public final void m(@NotNull u method) {
            k.f(method, FirebaseAnalytics.Param.METHOD);
            if (!a.contains(method)) {
                this.i.add(method);
            }
        }
    }

    /* renamed from: io.ktor.features.b$b  reason: collision with other inner class name */
    /* compiled from: CORS.kt */
    public static final class C0241b implements io.ktor.application.f<io.ktor.application.c, a, b> {
        private C0241b() {
        }

        public /* synthetic */ C0241b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public io.ktor.util.a<b> getKey() {
            return b.a;
        }

        @f(c = "io.ktor.features.CORS$Feature$install$1", f = "CORS.kt", l = {476}, m = "invokeSuspend")
        /* renamed from: io.ktor.features.b$b$a */
        /* compiled from: CORS.kt */
        public static final class a extends kotlin.coroutines.jvm.internal.l implements kotlin.jvm.functions.q<io.ktor.util.pipeline.d<x, io.ktor.application.b>, x, kotlin.coroutines.d<? super x>, Object> {
            final /* synthetic */ b $cors;
            Object L$0;
            Object L$1;
            int label;
            private io.ktor.util.pipeline.d p$;
            private x p$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(b bVar, kotlin.coroutines.d dVar) {
                super(3, dVar);
                this.$cors = bVar;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@NotNull io.ktor.util.pipeline.d<x, io.ktor.application.b> dVar, @NotNull x xVar, @NotNull kotlin.coroutines.d<? super x> dVar2) {
                k.f(dVar, "$this$create");
                k.f(xVar, "it");
                k.f(dVar2, "continuation");
                a aVar = new a(this.$cors, dVar2);
                aVar.p$ = dVar;
                aVar.p$0 = xVar;
                return aVar;
            }

            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return ((a) create((io.ktor.util.pipeline.d) obj, (x) obj2, (kotlin.coroutines.d) obj3)).invokeSuspend(x.a);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                Object d = kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        p.b($result);
                        io.ktor.util.pipeline.d $this$intercept = this.p$;
                        x it = this.p$0;
                        b bVar = this.$cors;
                        this.L$0 = $this$intercept;
                        this.L$1 = it;
                        this.label = 1;
                        if (bVar.j($this$intercept, this) != d) {
                            io.ktor.util.pipeline.d dVar = $this$intercept;
                            x xVar = it;
                            break;
                        } else {
                            return d;
                        }
                    case 1:
                        x it2 = (x) this.L$1;
                        io.ktor.util.pipeline.d $this$intercept2 = (io.ktor.util.pipeline.d) this.L$0;
                        p.b($result);
                        break;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                return x.a;
            }
        }

        @NotNull
        /* renamed from: d */
        public b a(@NotNull io.ktor.application.c pipeline, @NotNull l<? super a, x> configure) {
            k.f(pipeline, "pipeline");
            k.f(configure, "configure");
            a aVar = new a();
            configure.invoke(aVar);
            b cors = new b(aVar);
            pipeline.p(io.ktor.application.c.a2.b(), new a(cors, (kotlin.coroutines.d) null));
            return cors;
        }

        /* access modifiers changed from: private */
        public final Set<String> c(String... elements) {
            return new g(kotlin.collections.k.c(elements));
        }
    }
}
