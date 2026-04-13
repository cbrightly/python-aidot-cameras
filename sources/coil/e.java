package coil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import coil.c;
import coil.decode.f;
import coil.fetch.h;
import coil.fetch.j;
import coil.memory.o;
import coil.memory.q;
import coil.memory.t;
import coil.request.i;
import coil.util.l;
import coil.util.m;
import coil.util.n;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.collections.y;
import kotlin.coroutines.g;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.j0;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.p0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.v2;
import kotlinx.coroutines.z;
import kotlinx.coroutines.z1;
import okhttp3.e;
import okhttp3.v;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RealImageLoader.kt */
public final class e implements d {
    @NotNull
    public static final a b = new a((DefaultConstructorMarker) null);
    @NotNull
    private final Context c;
    @NotNull
    private final coil.request.d d;
    @NotNull
    private final coil.bitmap.c e;
    @NotNull
    private final o f;
    @NotNull
    private final e.a g;
    @NotNull
    private final c.d h;
    @NotNull
    private final b i;
    @NotNull
    private final l j;
    @Nullable
    private final m k;
    @NotNull
    private final o0 l;
    @NotNull
    private final coil.memory.b m;
    @NotNull
    private final coil.memory.m n;
    @NotNull
    private final q o;
    @NotNull
    private final f p;
    @NotNull
    private final n q;
    @NotNull
    private final b r;
    /* access modifiers changed from: private */
    @NotNull
    public final List<coil.intercept.b> s;
    @NotNull
    private final AtomicBoolean t = new AtomicBoolean(false);

    @kotlin.coroutines.jvm.internal.f(c = "coil.RealImageLoader", f = "RealImageLoader.kt", l = {286, 175, 294, 296, 311, 328, 339}, m = "executeMain")
    /* compiled from: RealImageLoader.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        Object L$8;
        Object L$9;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(e eVar, kotlin.coroutines.d<? super d> dVar) {
            super(dVar);
            this.this$0 = eVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.f((i) null, 0, this);
        }
    }

    /* renamed from: coil.e$e  reason: collision with other inner class name */
    /* compiled from: CoroutineExceptionHandler.kt */
    public static final class C0004e extends kotlin.coroutines.a implements j0 {
        final /* synthetic */ e c;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public C0004e(j0.b $super_call_param$1, e eVar) {
            super($super_call_param$1);
            this.c = eVar;
        }

        public void handleException(@NotNull g context, @NotNull Throwable exception) {
            g gVar = context;
            Throwable throwable = exception;
            m j = this.c.j();
            if (j != null) {
                coil.util.g.a(j, "RealImageLoader", throwable);
            }
        }
    }

    public e(@NotNull Context context, @NotNull coil.request.d defaults, @NotNull coil.bitmap.c bitmapPool, @NotNull o memoryCache, @NotNull e.a callFactory, @NotNull c.d eventListenerFactory, @NotNull b componentRegistry, @NotNull l options, @Nullable m logger) {
        Context context2 = context;
        coil.request.d dVar = defaults;
        coil.bitmap.c cVar = bitmapPool;
        o oVar = memoryCache;
        e.a aVar = callFactory;
        c.d dVar2 = eventListenerFactory;
        b bVar = componentRegistry;
        l lVar = options;
        m mVar = logger;
        k.e(context2, "context");
        k.e(dVar, "defaults");
        k.e(cVar, "bitmapPool");
        k.e(oVar, "memoryCache");
        k.e(aVar, "callFactory");
        k.e(dVar2, "eventListenerFactory");
        k.e(bVar, "componentRegistry");
        k.e(lVar, "options");
        this.c = context2;
        this.d = dVar;
        this.e = cVar;
        this.f = oVar;
        this.g = aVar;
        this.h = dVar2;
        this.i = bVar;
        this.j = lVar;
        this.k = mVar;
        z b2 = v2.b((z1) null, 1, (Object) null);
        d1 d1Var = d1.a;
        this.l = p0.a(b2.plus(d1.c().W()).plus(new C0004e(j0.e, this)));
        this.m = new coil.memory.b(this, c().c(), mVar);
        coil.memory.m mVar2 = new coil.memory.m(c().c(), c().d(), c().e());
        this.n = mVar2;
        q qVar = new q(mVar);
        this.o = qVar;
        f fVar = new f(g());
        this.p = fVar;
        n nVar = new n(this, context2, options.c());
        this.q = nVar;
        b d2 = componentRegistry.e().c(new coil.map.e(), String.class).c(new coil.map.a(), Uri.class).c(new coil.map.d(context2), Uri.class).c(new coil.map.c(context2), Integer.class).b(new j(aVar), Uri.class).b(new coil.fetch.k(aVar), v.class).b(new h(options.a()), File.class).b(new coil.fetch.a(context2), Uri.class).b(new coil.fetch.c(context2), Uri.class).b(new coil.fetch.l(context2, fVar), Uri.class).b(new coil.fetch.d(fVar), Drawable.class).b(new coil.fetch.b(), Bitmap.class).a(new coil.decode.a(context2)).d();
        this.r = d2;
        q qVar2 = qVar;
        this.s = y.o0(d2.c(), new coil.intercept.a(d2, g(), c().c(), c().d(), mVar2, qVar2, nVar, fVar, logger));
    }

    @NotNull
    public coil.request.d h() {
        return this.d;
    }

    @NotNull
    public coil.bitmap.c g() {
        return this.e;
    }

    @NotNull
    /* renamed from: k */
    public o c() {
        return this.f;
    }

    @NotNull
    public final c.d i() {
        return this.h;
    }

    @NotNull
    public final l l() {
        return this.j;
    }

    @Nullable
    public final m j() {
        return this.k;
    }

    @kotlin.coroutines.jvm.internal.f(c = "coil.RealImageLoader$enqueue$job$1", f = "RealImageLoader.kt", l = {113}, m = "invokeSuspend")
    /* compiled from: RealImageLoader.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ i $request;
        int label;
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(e eVar, i iVar, kotlin.coroutines.d<? super b> dVar) {
            super(2, dVar);
            this.this$0 = eVar;
            this.$request = iVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new b(this.this$0, this.$request, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((b) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    e eVar = this.this$0;
                    i iVar = this.$request;
                    this.label = 1;
                    Object d2 = eVar.f(iVar, 0, this);
                    if (d2 != d) {
                        Object obj = $result;
                        $result = d2;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    Object obj2 = $result;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            coil.request.j result = (coil.request.j) $result;
            if (!(result instanceof coil.request.g)) {
                return x.a;
            }
            throw ((coil.request.g) result).c();
        }
    }

    @NotNull
    public coil.request.f a(@NotNull i request) {
        k.e(request, Progress.REQUEST);
        z1 job = kotlinx.coroutines.j.d(this.l, (g) null, (q0) null, new b(this, request, (kotlin.coroutines.d<? super b>) null), 3, (Object) null);
        if (request.I() instanceof coil.target.c) {
            return new coil.request.n(coil.util.f.g(((coil.target.c) request.I()).getView()).f(job), (coil.target.c) request.I());
        }
        return new coil.request.b(job);
    }

    @Nullable
    public Object b(@NotNull i request, @NotNull kotlin.coroutines.d<? super coil.request.j> $completion) {
        if (request.I() instanceof coil.target.c) {
            t g2 = coil.util.f.g(((coil.target.c) request.I()).getView());
            z1 z1Var = (z1) $completion.getContext().get(z1.g);
            k.c(z1Var);
            g2.f(z1Var);
        }
        d1 d1Var = d1.a;
        return kotlinx.coroutines.h.g(d1.c().W(), new c(this, request, (kotlin.coroutines.d<? super c>) null), $completion);
    }

    @kotlin.coroutines.jvm.internal.f(c = "coil.RealImageLoader$execute$2", f = "RealImageLoader.kt", l = {134}, m = "invokeSuspend")
    /* compiled from: RealImageLoader.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super coil.request.j>, Object> {
        final /* synthetic */ i $request;
        int label;
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(e eVar, i iVar, kotlin.coroutines.d<? super c> dVar) {
            super(2, dVar);
            this.this$0 = eVar;
            this.$request = iVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new c(this.this$0, this.$request, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super coil.request.j> dVar) {
            return ((c) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    e eVar = this.this$0;
                    i iVar = this.$request;
                    this.label = 1;
                    Object d2 = eVar.f(iVar, 1, this);
                    if (d2 == d) {
                        return d;
                    }
                    return d2;
                case 1:
                    kotlin.p.b($result);
                    return $result;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v27, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v21, resolved type: coil.memory.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v24, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v22, resolved type: coil.request.i} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v30, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v23, resolved type: coil.memory.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v27, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v24, resolved type: coil.request.i} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v33, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v25, resolved type: coil.memory.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v30, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v26, resolved type: coil.request.i} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v36, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v27, resolved type: coil.memory.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v36, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v28, resolved type: coil.request.i} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v30, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v34, resolved type: coil.request.m} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v82, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v15, resolved type: coil.memory.RequestDelegate} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v83, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v30, resolved type: coil.memory.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v84, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v13, resolved type: coil.c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v85, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v15, resolved type: coil.request.i} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v86, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v9, resolved type: coil.e} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v36, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v43, resolved type: coil.request.i} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v37, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v89, resolved type: coil.request.g} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v33, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v93, resolved type: coil.request.i} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v34, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v44, resolved type: coil.request.g} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x02e2, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x02e3, code lost:
        r19 = r0;
        r24 = r8;
        r8 = r11;
        r11 = r9;
        r9 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:?, code lost:
        r0 = (coil.size.Size) r2;
        r9.l(r15, r0);
        r2 = r11;
        r17 = new coil.intercept.c(r15, r19, e(r2), 0, r15, r0, r24, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x030f, code lost:
        if (r2.l().b() == false) goto L_0x033d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:?, code lost:
        r1.L$0 = r11;
        r1.L$1 = r15;
        r1.L$2 = r9;
        r1.L$3 = r13;
        r1.L$4 = r8;
        r1.L$5 = null;
        r1.label = 3;
        r12 = r17.i(r15, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x0325, code lost:
        if (r12 != r4) goto L_0x0328;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x0327, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x0328, code lost:
        r10 = r9;
        r9 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x032e, code lost:
        r14 = r10;
        r9 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x0331, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x0332, code lost:
        r20 = " - ";
        r3 = "🚨 Failed - ";
        r19 = "RealImageLoader";
        r6 = r8;
        r7 = r9;
        r9 = r11;
        r5 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:?, code lost:
        r0 = r15.r();
        r2 = new coil.f(r17, r15, (kotlin.coroutines.d<? super coil.f>) null);
        r1.L$0 = r11;
        r1.L$1 = r15;
        r1.L$2 = r9;
        r1.L$3 = r13;
        r1.L$4 = r8;
        r1.L$5 = null;
        r1.label = 4;
        r0 = kotlinx.coroutines.h.g(r0, r2, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x035b, code lost:
        if (r0 != r4) goto L_0x035e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x035d, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x035e, code lost:
        r12 = r0;
        r10 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x0361, code lost:
        r14 = r9;
        r9 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:?, code lost:
        r11 = (coil.request.j) r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x0369, code lost:
        if ((r11 instanceof coil.request.m) == false) goto L_0x04dd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x036e, code lost:
        r2 = r9;
        r3 = (coil.request.m) r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:?, code lost:
        r12 = r3.b();
        r0 = r3.c();
        r17 = r0.a();
        r18 = r2.j();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x0383, code lost:
        if (r18 != null) goto L_0x0391;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x0385, code lost:
        r21 = r4;
        r20 = " - ";
        r22 = "🚨 Failed - ";
        r19 = "RealImageLoader";
        r28 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x0391, code lost:
        r28 = false;
        r29 = "RealImageLoader";
        r19 = r18;
        r20 = " - ";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x03a0, code lost:
        if (r19.b() > 4) goto L_0x03f0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:?, code lost:
        r5 = new java.lang.StringBuilder();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x03aa, code lost:
        r22 = "🚨 Failed - ";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:?, code lost:
        r5.append(coil.util.f.b(r17));
        r5.append(" Successful (");
        r5.append(r17.name());
        r5.append(") - ");
        r5.append(r12.m());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x03cf, code lost:
        r21 = r4;
        r26 = r19;
        r19 = "RealImageLoader";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:?, code lost:
        r26.a(r29, 4, r5.toString(), (java.lang.Throwable) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x03de, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x03df, code lost:
        r10 = r11;
        r17 = r15;
        r5 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x03e6, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x03e8, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x03e9, code lost:
        r22 = "🚨 Failed - ";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x03eb, code lost:
        r19 = "RealImageLoader";
        r5 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x03f0, code lost:
        r21 = r4;
        r22 = "🚨 Failed - ";
        r6 = r29;
        r26 = r19;
        r19 = "RealImageLoader";
        r7 = r26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:?, code lost:
        r4 = kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x03ff, code lost:
        coil.util.f.q(r13, r0);
        r1.L$0 = r9;
        r1.L$1 = r15;
        r1.L$2 = r14;
        r1.L$3 = r13;
        r1.L$4 = r8;
        r1.L$5 = r11;
        r1.L$6 = r2;
        r1.L$7 = r3;
        r1.L$8 = r12;
        r1.L$9 = r0;
        r1.label = 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x041d, code lost:
        r5 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x041f, code lost:
        if (r13.f(r3, r1) != r5) goto L_0x0422;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x0421, code lost:
        return r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x0422, code lost:
        r18 = r9;
        r10 = r11;
        r17 = r15;
        r9 = r0;
        r11 = r3;
        r15 = r13;
        r13 = r2;
        r2 = r14;
        r14 = r8;
        r8 = r28;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:?, code lost:
        r2.d(r12, r9);
        r0 = r12.x();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x0436, code lost:
        if (r0 != null) goto L_0x0439;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x0439, code lost:
        r0.d(r12, r9);
        r0 = kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:?, code lost:
        r0 = r13.k().c();
        r3 = r11.a();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x044c, code lost:
        if (r3 == null) goto L_0x0465;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x0450, code lost:
        if ((r3 instanceof android.graphics.drawable.BitmapDrawable) == false) goto L_0x0465;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x0452, code lost:
        r6 = ((android.graphics.drawable.BitmapDrawable) r3).getBitmap();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x0459, code lost:
        if (r6 != null) goto L_0x045c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x045c, code lost:
        kotlin.coroutines.jvm.internal.b.a(r0.b(r6));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x0465, code lost:
        r11 = r10;
        r8 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x046b, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x046c, code lost:
        r7 = r2;
        r6 = r14;
        r13 = r15;
        r15 = r17;
        r9 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x0475, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x0476, code lost:
        r9 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x0479, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x047a, code lost:
        r5 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x047d, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x047e, code lost:
        r5 = r4;
        r22 = "🚨 Failed - ";
        r19 = "RealImageLoader";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x0483, code lost:
        r10 = r11;
        r17 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x0486, code lost:
        r11 = r3;
        r15 = r13;
        r13 = r2;
        r2 = r14;
        r14 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x048e, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x048f, code lost:
        r20 = " - ";
        r22 = "🚨 Failed - ";
        r19 = "RealImageLoader";
        r5 = r4;
        r10 = r11;
        r17 = r15;
        r11 = r3;
        r15 = r13;
        r13 = r2;
        r2 = r14;
        r14 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:197:0x04d4, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:198:0x04d5, code lost:
        r20 = " - ";
        r19 = "RealImageLoader";
        r5 = r4;
        r3 = "🚨 Failed - ";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:199:0x04dd, code lost:
        r20 = " - ";
        r22 = "🚨 Failed - ";
        r19 = "RealImageLoader";
        r5 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:202:0x04e6, code lost:
        if ((r11 instanceof coil.request.g) == false) goto L_0x0589;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:203:0x04e8, code lost:
        r0 = (coil.request.g) r11;
        r2 = r9;
        r10 = r0.b();
        r4 = r2.j();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:204:0x04f6, code lost:
        if (r4 != null) goto L_0x04ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:205:0x04f8, code lost:
        r28 = r2;
        r17 = false;
        r3 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:206:0x04ff, code lost:
        r7 = r19;
        r28 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:207:0x0509, code lost:
        if (r4.b() > 4) goto L_0x053e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:208:0x050b, code lost:
        r2 = new java.lang.StringBuilder();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:209:0x0513, code lost:
        r17 = false;
        r3 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:211:?, code lost:
        r2.append(r3);
        r2.append(r10.m());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:212:0x0523, code lost:
        r12 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:214:?, code lost:
        r2.append(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:215:0x0528, code lost:
        r20 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:217:?, code lost:
        r2.append(r0.c());
        r4.a(r7, 4, r2.toString(), (java.lang.Throwable) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:218:0x053a, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:219:0x053b, code lost:
        r20 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:220:0x053e, code lost:
        r17 = false;
        r3 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:221:0x0544, code lost:
        r2 = kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:222:0x0547, code lost:
        coil.util.f.q(r13, (coil.request.j.a) null);
        r1.L$0 = r9;
        r1.L$1 = r15;
        r1.L$2 = r14;
        r1.L$3 = r13;
        r1.L$4 = r8;
        r1.L$5 = r11;
        r1.L$6 = r0;
        r1.L$7 = r10;
        r1.label = 6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:223:0x0562, code lost:
        if (r13.b(r0, r1) != r5) goto L_0x0565;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:224:0x0564, code lost:
        return r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:225:0x0565, code lost:
        r12 = r8;
        r8 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:?, code lost:
        r14.c(r10, r0.c());
        r2 = r10.x();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:228:0x0573, code lost:
        if (r2 != null) goto L_0x0576;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:230:0x0576, code lost:
        r2.c(r10, r0.c());
        r2 = kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:231:0x0580, code lost:
        r8 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:232:0x0582, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:233:0x0583, code lost:
        r6 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:234:0x0584, code lost:
        r7 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:235:0x0587, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:236:0x0589, code lost:
        r8.a();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:237:0x058d, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:238:0x058e, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:239:0x058f, code lost:
        r3 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:240:0x0592, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:241:0x0593, code lost:
        r20 = " - ";
        r3 = "🚨 Failed - ";
        r19 = "RealImageLoader";
        r5 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:242:0x0599, code lost:
        r6 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:243:0x059b, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:244:0x059c, code lost:
        r20 = " - ";
        r3 = "🚨 Failed - ";
        r19 = "RealImageLoader";
        r5 = r4;
        r6 = r8;
        r7 = r9;
        r9 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:245:0x05a6, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:246:0x05a7, code lost:
        r20 = " - ";
        r3 = "🚨 Failed - ";
        r19 = "RealImageLoader";
        r5 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:247:0x05ae, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:248:0x05af, code lost:
        r20 = " - ";
        r3 = "🚨 Failed - ";
        r19 = "RealImageLoader";
        r5 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:249:0x05b6, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:250:0x05b7, code lost:
        r16 = r3;
        r20 = " - ";
        r3 = "🚨 Failed - ";
        r19 = "RealImageLoader";
        r5 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:252:?, code lost:
        r2 = r9.k().c();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:253:0x05c8, code lost:
        if (r8 != null) goto L_0x05ca;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:254:0x05ca, code lost:
        r2.b(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:255:0x05ce, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:258:0x05df, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:259:0x05e0, code lost:
        r7 = r10;
        r6 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0237, code lost:
        r0 = r8;
        r26 = r11;
        r11 = r9;
        r9 = r26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
        r2 = r9.n.a(r15.D());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0258, code lost:
        if (r2 != null) goto L_0x025c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x025a, code lost:
        r2 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x025c, code lost:
        r2 = r2.b();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0260, code lost:
        r8 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:?, code lost:
        coil.util.f.q(r13, (coil.request.j.a) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0266, code lost:
        if (r8 != null) goto L_0x026c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0268, code lost:
        r16 = r3;
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x026c, code lost:
        r2 = r15.l();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0270, code lost:
        r12 = r8;
        r16 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:?, code lost:
        r3 = r2.getResources();
        r29 = r2;
        kotlin.jvm.internal.k.d(r3, "context.resources");
        r18 = r12;
        r3 = new android.graphics.drawable.BitmapDrawable(r3, r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x028f, code lost:
        if (r3 != null) goto L_0x0295;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0291, code lost:
        r3 = r15.C();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0295, code lost:
        r13.e(r3, r8);
        r10.b(r15);
        r2 = r15.x();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x029f, code lost:
        if (r2 != null) goto L_0x02a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x02a2, code lost:
        r2.b(r15);
        r2 = kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:?, code lost:
        r2 = r9.k().c();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x02b1, code lost:
        if (r8 == null) goto L_0x02c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:?, code lost:
        r2.b(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x02b7, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x02b8, code lost:
        r20 = " - ";
        r3 = "🚨 Failed - ";
        r19 = "RealImageLoader";
        r7 = r10;
        r6 = r11;
        r5 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:?, code lost:
        r10.o(r15);
        r2 = r15.H();
        r1.L$0 = r9;
        r1.L$1 = r15;
        r1.L$2 = r10;
        r1.L$3 = r13;
        r1.L$4 = r11;
        r1.L$5 = r8;
        r1.I$0 = r0;
        r1.label = 2;
        r2 = r2.b(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x02e0, code lost:
        if (r2 != r4) goto L_0x02e3;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:192:0x04bf A[Catch:{ all -> 0x04ca }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x009d  */
    /* JADX WARNING: Removed duplicated region for block: B:254:0x05ca A[Catch:{ all -> 0x05df }] */
    /* JADX WARNING: Removed duplicated region for block: B:265:0x05f2 A[Catch:{ all -> 0x0686 }] */
    /* JADX WARNING: Removed duplicated region for block: B:283:0x0681 A[SYNTHETIC, Splitter:B:283:0x0681] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0124  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0154  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x017e  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x01ab  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0034  */
    @androidx.annotation.MainThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object f(coil.request.i r28, int r29, kotlin.coroutines.d<? super coil.request.j> r30) {
        /*
            r27 = this;
            r0 = r30
            boolean r1 = r0 instanceof coil.e.d
            if (r1 == 0) goto L_0x0018
            r1 = r0
            coil.e$d r1 = (coil.e.d) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r27
            goto L_0x0020
        L_0x0018:
            coil.e$d r1 = new coil.e$d
            r2 = r27
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r3 = r1.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.c.d()
            int r0 = r1.label
            java.lang.String r5 = " - "
            java.lang.String r6 = "🚨 Failed - "
            java.lang.String r7 = "RealImageLoader"
            r8 = 0
            r9 = 0
            switch(r0) {
                case 0: goto L_0x01ab;
                case 1: goto L_0x017e;
                case 2: goto L_0x0154;
                case 3: goto L_0x0124;
                case 4: goto L_0x00f6;
                case 5: goto L_0x009d;
                case 6: goto L_0x005e;
                case 7: goto L_0x003c;
                default: goto L_0x0034;
            }
        L_0x0034:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x003c:
            r0 = r8
            r4 = r8
            r5 = r8
            r4 = 0
            java.lang.Object r6 = r1.L$3
            r0 = r6
            coil.request.i r0 = (coil.request.i) r0
            java.lang.Object r6 = r1.L$2
            r5 = r6
            coil.request.g r5 = (coil.request.g) r5
            java.lang.Object r6 = r1.L$1
            coil.memory.RequestDelegate r6 = (coil.memory.RequestDelegate) r6
            java.lang.Object r7 = r1.L$0
            coil.c r7 = (coil.c) r7
            kotlin.p.b(r3)     // Catch:{ all -> 0x0059 }
            r16 = r3
            goto L_0x0663
        L_0x0059:
            r0 = move-exception
            r16 = r3
            goto L_0x0687
        L_0x005e:
            r0 = r9
            r10 = r9
            r8 = 0
            java.lang.Object r11 = r1.L$7
            r10 = r11
            coil.request.i r10 = (coil.request.i) r10
            java.lang.Object r11 = r1.L$6
            r0 = r11
            coil.request.g r0 = (coil.request.g) r0
            java.lang.Object r11 = r1.L$5
            coil.request.j r11 = (coil.request.j) r11
            java.lang.Object r12 = r1.L$4
            coil.memory.RequestDelegate r12 = (coil.memory.RequestDelegate) r12
            java.lang.Object r13 = r1.L$3
            coil.memory.s r13 = (coil.memory.s) r13
            java.lang.Object r14 = r1.L$2
            coil.c r14 = (coil.c) r14
            java.lang.Object r15 = r1.L$1
            coil.request.i r15 = (coil.request.i) r15
            java.lang.Object r9 = r1.L$0
            coil.e r9 = (coil.e) r9
            kotlin.p.b(r3)     // Catch:{ all -> 0x0090 }
            r16 = r3
            r20 = r5
            r3 = r6
            r19 = r7
            r5 = r4
            goto L_0x0568
        L_0x0090:
            r0 = move-exception
            r16 = r3
            r20 = r5
            r3 = r6
            r19 = r7
            r6 = r12
            r7 = r14
            r5 = r4
            goto L_0x05ee
        L_0x009d:
            r0 = r8
            r9 = 0
            r10 = r9
            r11 = r9
            r12 = r9
            r13 = r9
            r8 = 0
            java.lang.Object r9 = r1.L$9
            coil.request.j$a r9 = (coil.request.j.a) r9
            java.lang.Object r12 = r1.L$8
            coil.request.i r12 = (coil.request.i) r12
            java.lang.Object r13 = r1.L$7
            r11 = r13
            coil.request.m r11 = (coil.request.m) r11
            java.lang.Object r13 = r1.L$6
            coil.e r13 = (coil.e) r13
            java.lang.Object r0 = r1.L$5
            r10 = r0
            coil.request.j r10 = (coil.request.j) r10
            java.lang.Object r0 = r1.L$4
            r14 = r0
            coil.memory.RequestDelegate r14 = (coil.memory.RequestDelegate) r14
            java.lang.Object r0 = r1.L$3
            r15 = r0
            coil.memory.s r15 = (coil.memory.s) r15
            java.lang.Object r0 = r1.L$2
            r16 = r0
            coil.c r16 = (coil.c) r16
            java.lang.Object r0 = r1.L$1
            r17 = r0
            coil.request.i r17 = (coil.request.i) r17
            java.lang.Object r0 = r1.L$0
            r18 = r0
            coil.e r18 = (coil.e) r18
            kotlin.p.b(r3)     // Catch:{ all -> 0x00e6 }
            r20 = r5
            r22 = r6
            r19 = r7
            r2 = r16
            r16 = r3
            r5 = r4
            goto L_0x042f
        L_0x00e6:
            r0 = move-exception
            r20 = r5
            r22 = r6
            r19 = r7
            r2 = r16
            r9 = r18
            r16 = r3
            r5 = r4
            goto L_0x04a2
        L_0x00f6:
            r0 = r8
            r0 = 0
            java.lang.Object r8 = r1.L$4
            coil.memory.RequestDelegate r8 = (coil.memory.RequestDelegate) r8
            java.lang.Object r9 = r1.L$3
            r13 = r9
            coil.memory.s r13 = (coil.memory.s) r13
            java.lang.Object r9 = r1.L$2
            coil.c r9 = (coil.c) r9
            java.lang.Object r10 = r1.L$1
            r15 = r10
            coil.request.i r15 = (coil.request.i) r15
            java.lang.Object r10 = r1.L$0
            coil.e r10 = (coil.e) r10
            kotlin.p.b(r3)     // Catch:{ all -> 0x0116 }
            r12 = r3
            r16 = r12
            goto L_0x0361
        L_0x0116:
            r0 = move-exception
            r16 = r3
            r20 = r5
            r3 = r6
            r19 = r7
            r6 = r8
            r7 = r9
            r9 = r10
            r5 = r4
            goto L_0x05ee
        L_0x0124:
            r0 = r8
            r8 = 0
            r9 = r8
            r0 = 0
            java.lang.Object r8 = r1.L$4
            coil.memory.RequestDelegate r8 = (coil.memory.RequestDelegate) r8
            java.lang.Object r10 = r1.L$3
            r13 = r10
            coil.memory.s r13 = (coil.memory.s) r13
            java.lang.Object r10 = r1.L$2
            coil.c r10 = (coil.c) r10
            java.lang.Object r11 = r1.L$1
            r15 = r11
            coil.request.i r15 = (coil.request.i) r15
            java.lang.Object r11 = r1.L$0
            coil.e r11 = (coil.e) r11
            kotlin.p.b(r3)     // Catch:{ all -> 0x0146 }
            r12 = r3
            r16 = r12
            goto L_0x032e
        L_0x0146:
            r0 = move-exception
            r16 = r3
            r20 = r5
            r3 = r6
            r19 = r7
            r6 = r8
            r7 = r10
            r9 = r11
            r5 = r4
            goto L_0x05ee
        L_0x0154:
            int r0 = r1.I$0
            java.lang.Object r8 = r1.L$5
            android.graphics.Bitmap r8 = (android.graphics.Bitmap) r8
            java.lang.Object r9 = r1.L$4
            coil.memory.RequestDelegate r9 = (coil.memory.RequestDelegate) r9
            java.lang.Object r10 = r1.L$3
            r13 = r10
            coil.memory.s r13 = (coil.memory.s) r13
            java.lang.Object r10 = r1.L$2
            coil.c r10 = (coil.c) r10
            java.lang.Object r11 = r1.L$1
            r15 = r11
            coil.request.i r15 = (coil.request.i) r15
            java.lang.Object r11 = r1.L$0
            coil.e r11 = (coil.e) r11
            kotlin.p.b(r3)     // Catch:{ all -> 0x019d }
            r19 = r0
            r2 = r3
            r16 = r2
            r24 = r8
            r8 = r9
            r9 = r10
            goto L_0x02ea
        L_0x017e:
            r0 = r8
            r0 = 0
            int r8 = r1.I$0
            java.lang.Object r9 = r1.L$4
            coil.memory.RequestDelegate r9 = (coil.memory.RequestDelegate) r9
            java.lang.Object r10 = r1.L$3
            r13 = r10
            coil.memory.s r13 = (coil.memory.s) r13
            java.lang.Object r10 = r1.L$2
            coil.c r10 = (coil.c) r10
            java.lang.Object r11 = r1.L$1
            r15 = r11
            coil.request.i r15 = (coil.request.i) r15
            java.lang.Object r11 = r1.L$0
            coil.e r11 = (coil.e) r11
            kotlin.p.b(r3)     // Catch:{ all -> 0x019d }
            goto L_0x0237
        L_0x019d:
            r0 = move-exception
            r16 = r3
            r20 = r5
            r3 = r6
            r19 = r7
            r6 = r9
            r7 = r10
            r9 = r11
            r5 = r4
            goto L_0x05ee
        L_0x01ab:
            kotlin.p.b(r3)
            r9 = r27
            r8 = r29
            r0 = r28
            java.util.concurrent.atomic.AtomicBoolean r10 = r9.t
            boolean r10 = r10.get()
            r11 = 1
            r10 = r10 ^ r11
            if (r10 == 0) goto L_0x068b
            r10 = 0
            coil.request.i$a r12 = coil.request.i.M(r0, r10, r11, r10)
            coil.request.d r10 = r9.h()
            coil.request.i$a r10 = r12.f(r10)
            coil.request.i r15 = r10.b()
            coil.c$d r10 = r9.i()
            coil.c r10 = r10.a(r15)
            coil.memory.b r12 = r9.m
            coil.target.b r13 = r15.I()
            coil.memory.s r13 = r12.b(r13, r8, r10)
            coil.memory.b r12 = r9.m
            kotlin.coroutines.g r14 = r1.getContext()
            r16 = 0
            kotlinx.coroutines.z1$b r11 = kotlinx.coroutines.z1.g
            kotlin.coroutines.g$b r11 = r14.get(r11)
            kotlinx.coroutines.z1 r11 = (kotlinx.coroutines.z1) r11
            kotlin.jvm.internal.k.c(r11)
            coil.memory.RequestDelegate r11 = r12.a(r15, r13, r11)
            java.lang.Object r12 = r15.m()     // Catch:{ all -> 0x05e3 }
            coil.request.k r14 = coil.request.k.a     // Catch:{ all -> 0x05e3 }
            boolean r12 = kotlin.jvm.internal.k.a(r12, r14)     // Catch:{ all -> 0x05e3 }
            if (r12 != 0) goto L_0x05cf
            if (r8 != 0) goto L_0x024b
            androidx.lifecycle.Lifecycle r12 = r15.w()     // Catch:{ all -> 0x023e }
            r14 = 0
            r29 = r0
            androidx.lifecycle.Lifecycle$State r0 = r12.getCurrentState()     // Catch:{ all -> 0x023e }
            androidx.lifecycle.Lifecycle$State r2 = androidx.lifecycle.Lifecycle.State.STARTED     // Catch:{ all -> 0x023e }
            boolean r0 = r0.isAtLeast(r2)     // Catch:{ all -> 0x023e }
            if (r0 == 0) goto L_0x021b
            goto L_0x024d
        L_0x021b:
            r1.L$0 = r9     // Catch:{ all -> 0x023e }
            r1.L$1 = r15     // Catch:{ all -> 0x023e }
            r1.L$2 = r10     // Catch:{ all -> 0x023e }
            r1.L$3 = r13     // Catch:{ all -> 0x023e }
            r1.L$4 = r11     // Catch:{ all -> 0x023e }
            r1.I$0 = r8     // Catch:{ all -> 0x023e }
            r0 = 1
            r1.label = r0     // Catch:{ all -> 0x023e }
            java.lang.Object r0 = coil.util.Lifecycles.a(r12, r1)     // Catch:{ all -> 0x023e }
            if (r0 != r4) goto L_0x0231
            return r4
        L_0x0231:
            r0 = r14
            r26 = r11
            r11 = r9
            r9 = r26
        L_0x0237:
            r0 = r8
            r26 = r11
            r11 = r9
            r9 = r26
            goto L_0x024e
        L_0x023e:
            r0 = move-exception
            r16 = r3
            r20 = r5
            r3 = r6
            r19 = r7
            r7 = r10
            r6 = r11
            r5 = r4
            goto L_0x05ee
        L_0x024b:
            r29 = r0
        L_0x024d:
            r0 = r8
        L_0x024e:
            coil.memory.m r2 = r9.n     // Catch:{ all -> 0x05e3 }
            coil.memory.MemoryCache$Key r8 = r15.D()     // Catch:{ all -> 0x05e3 }
            coil.memory.o$a r2 = r2.a(r8)     // Catch:{ all -> 0x05e3 }
            if (r2 != 0) goto L_0x025c
            r2 = 0
            goto L_0x0260
        L_0x025c:
            android.graphics.Bitmap r2 = r2.b()     // Catch:{ all -> 0x05e3 }
        L_0x0260:
            r8 = r2
            r2 = 0
            coil.util.f.q(r13, r2)     // Catch:{ all -> 0x05b6 }
            if (r8 != 0) goto L_0x026c
            r16 = r3
            r3 = 0
            goto L_0x028f
        L_0x026c:
            android.content.Context r2 = r15.l()     // Catch:{ all -> 0x05b6 }
            r12 = r8
            r14 = 0
            r16 = r3
            android.content.res.Resources r3 = r2.getResources()     // Catch:{ all -> 0x05ae }
            r28 = r12
            r29 = r2
            java.lang.String r2 = "context.resources"
            kotlin.jvm.internal.k.d(r3, r2)     // Catch:{ all -> 0x05ae }
            r2 = r3
            r3 = 0
            r17 = r3
            android.graphics.drawable.BitmapDrawable r3 = new android.graphics.drawable.BitmapDrawable     // Catch:{ all -> 0x05ae }
            r18 = r12
            r12 = r28
            r3.<init>(r2, r12)     // Catch:{ all -> 0x05ae }
        L_0x028f:
            if (r3 != 0) goto L_0x0295
            android.graphics.drawable.Drawable r3 = r15.C()     // Catch:{ all -> 0x05ae }
        L_0x0295:
            r13.e(r3, r8)     // Catch:{ all -> 0x05ae }
            r10.b(r15)     // Catch:{ all -> 0x05ae }
            coil.request.i$b r2 = r15.x()     // Catch:{ all -> 0x05ae }
            if (r2 != 0) goto L_0x02a2
        L_0x02a1:
            goto L_0x02a8
        L_0x02a2:
            r2.b(r15)     // Catch:{ all -> 0x05ae }
            kotlin.x r2 = kotlin.x.a     // Catch:{ all -> 0x05ae }
            goto L_0x02a1
        L_0x02a8:
            coil.memory.o r2 = r9.c()     // Catch:{ all -> 0x05a6 }
            coil.bitmap.e r2 = r2.c()     // Catch:{ all -> 0x05a6 }
            r3 = 0
            if (r8 == 0) goto L_0x02c2
            r2.b(r8)     // Catch:{ all -> 0x02b7 }
            goto L_0x02c2
        L_0x02b7:
            r0 = move-exception
            r20 = r5
            r3 = r6
            r19 = r7
            r7 = r10
            r6 = r11
            r5 = r4
            goto L_0x05ee
        L_0x02c2:
            r10.o(r15)     // Catch:{ all -> 0x05a6 }
            coil.size.f r2 = r15.H()     // Catch:{ all -> 0x05a6 }
            r1.L$0 = r9     // Catch:{ all -> 0x05a6 }
            r1.L$1 = r15     // Catch:{ all -> 0x05a6 }
            r1.L$2 = r10     // Catch:{ all -> 0x05a6 }
            r1.L$3 = r13     // Catch:{ all -> 0x05a6 }
            r1.L$4 = r11     // Catch:{ all -> 0x05a6 }
            r1.L$5 = r8     // Catch:{ all -> 0x05a6 }
            r1.I$0 = r0     // Catch:{ all -> 0x05a6 }
            r3 = 2
            r1.label = r3     // Catch:{ all -> 0x05a6 }
            java.lang.Object r2 = r2.b(r1)     // Catch:{ all -> 0x05a6 }
            if (r2 != r4) goto L_0x02e3
            return r4
        L_0x02e3:
            r19 = r0
            r24 = r8
            r8 = r11
            r11 = r9
            r9 = r10
        L_0x02ea:
            coil.size.Size r2 = (coil.size.Size) r2     // Catch:{ all -> 0x059b }
            r0 = r2
            r9.l(r15, r0)     // Catch:{ all -> 0x059b }
            r2 = r11
            r3 = 0
            coil.intercept.c r10 = new coil.intercept.c     // Catch:{ all -> 0x059b }
            java.util.List r20 = r2.s     // Catch:{ all -> 0x059b }
            r21 = 0
            r17 = r10
            r18 = r15
            r22 = r15
            r23 = r0
            r25 = r9
            r17.<init>(r18, r19, r20, r21, r22, r23, r24, r25)     // Catch:{ all -> 0x059b }
            coil.util.l r12 = r2.l()     // Catch:{ all -> 0x059b }
            boolean r12 = r12.b()     // Catch:{ all -> 0x059b }
            if (r12 == 0) goto L_0x033d
            r1.L$0 = r11     // Catch:{ all -> 0x0331 }
            r1.L$1 = r15     // Catch:{ all -> 0x0331 }
            r1.L$2 = r9     // Catch:{ all -> 0x0331 }
            r1.L$3 = r13     // Catch:{ all -> 0x0331 }
            r1.L$4 = r8     // Catch:{ all -> 0x0331 }
            r12 = 0
            r1.L$5 = r12     // Catch:{ all -> 0x0331 }
            r12 = 3
            r1.label = r12     // Catch:{ all -> 0x0331 }
            java.lang.Object r12 = r10.i(r15, r1)     // Catch:{ all -> 0x0331 }
            if (r12 != r4) goto L_0x0328
            return r4
        L_0x0328:
            r0 = r3
            r26 = r10
            r10 = r9
            r9 = r26
        L_0x032e:
            r14 = r10
            r9 = r11
            goto L_0x0363
        L_0x0331:
            r0 = move-exception
            r20 = r5
            r3 = r6
            r19 = r7
            r6 = r8
            r7 = r9
            r9 = r11
            r5 = r4
            goto L_0x05ee
        L_0x033d:
            kotlinx.coroutines.i0 r0 = r15.r()     // Catch:{ all -> 0x059b }
            coil.f r2 = new coil.f     // Catch:{ all -> 0x059b }
            r12 = 0
            r2.<init>(r10, r15, r12)     // Catch:{ all -> 0x059b }
            r1.L$0 = r11     // Catch:{ all -> 0x059b }
            r1.L$1 = r15     // Catch:{ all -> 0x059b }
            r1.L$2 = r9     // Catch:{ all -> 0x059b }
            r1.L$3 = r13     // Catch:{ all -> 0x059b }
            r1.L$4 = r8     // Catch:{ all -> 0x059b }
            r12 = 0
            r1.L$5 = r12     // Catch:{ all -> 0x059b }
            r12 = 4
            r1.label = r12     // Catch:{ all -> 0x059b }
            java.lang.Object r0 = kotlinx.coroutines.h.g(r0, r2, r1)     // Catch:{ all -> 0x059b }
            if (r0 != r4) goto L_0x035e
            return r4
        L_0x035e:
            r12 = r0
            r0 = r3
            r10 = r11
        L_0x0361:
            r14 = r9
            r9 = r10
        L_0x0363:
            coil.request.j r12 = (coil.request.j) r12     // Catch:{ all -> 0x0592 }
            r11 = r12
            boolean r0 = r11 instanceof coil.request.m     // Catch:{ all -> 0x0592 }
            if (r0 == 0) goto L_0x04dd
            r0 = r11
            coil.request.m r0 = (coil.request.m) r0     // Catch:{ all -> 0x04d4 }
            r2 = r9
            r3 = r0
            r10 = 0
            coil.request.i r0 = r3.b()     // Catch:{ all -> 0x048e }
            r12 = r0
            coil.request.j$a r0 = r3.c()     // Catch:{ all -> 0x048e }
            coil.decode.b r17 = r0.a()     // Catch:{ all -> 0x048e }
            coil.util.m r18 = r2.j()     // Catch:{ all -> 0x048e }
            if (r18 != 0) goto L_0x0391
            r21 = r4
            r20 = r5
            r22 = r6
            r19 = r7
            r28 = r10
            goto L_0x03ff
        L_0x0391:
            r28 = r10
            r10 = 4
            r29 = r7
            r19 = r18
            r18 = 0
            r20 = r5
            int r5 = r19.b()     // Catch:{ all -> 0x047d }
            if (r5 > r10) goto L_0x03f0
            r5 = 0
            r21 = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x03e8 }
            r5.<init>()     // Catch:{ all -> 0x03e8 }
            r22 = r6
            java.lang.String r6 = coil.util.f.b(r17)     // Catch:{ all -> 0x03e6 }
            r5.append(r6)     // Catch:{ all -> 0x03e6 }
            java.lang.String r6 = " Successful ("
            r5.append(r6)     // Catch:{ all -> 0x03e6 }
            java.lang.String r6 = r17.name()     // Catch:{ all -> 0x03e6 }
            r5.append(r6)     // Catch:{ all -> 0x03e6 }
            java.lang.String r6 = ") - "
            r5.append(r6)     // Catch:{ all -> 0x03e6 }
            java.lang.Object r6 = r12.m()     // Catch:{ all -> 0x03e6 }
            r5.append(r6)     // Catch:{ all -> 0x03e6 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x03e6 }
            r6 = r29
            r21 = r4
            r4 = 0
            r26 = r19
            r19 = r7
            r7 = r26
            r7.a(r6, r10, r5, r4)     // Catch:{ all -> 0x03de }
            goto L_0x03fc
        L_0x03de:
            r0 = move-exception
            r10 = r11
            r17 = r15
            r5 = r21
            goto L_0x0486
        L_0x03e6:
            r0 = move-exception
            goto L_0x03eb
        L_0x03e8:
            r0 = move-exception
            r22 = r6
        L_0x03eb:
            r19 = r7
            r5 = r4
            goto L_0x0483
        L_0x03f0:
            r21 = r4
            r22 = r6
            r6 = r29
            r26 = r19
            r19 = r7
            r7 = r26
        L_0x03fc:
            kotlin.x r4 = kotlin.x.a     // Catch:{ all -> 0x0479 }
        L_0x03ff:
            coil.util.f.q(r13, r0)     // Catch:{ all -> 0x0479 }
            r1.L$0 = r9     // Catch:{ all -> 0x0479 }
            r1.L$1 = r15     // Catch:{ all -> 0x0479 }
            r1.L$2 = r14     // Catch:{ all -> 0x0479 }
            r1.L$3 = r13     // Catch:{ all -> 0x0479 }
            r1.L$4 = r8     // Catch:{ all -> 0x0479 }
            r1.L$5 = r11     // Catch:{ all -> 0x0479 }
            r1.L$6 = r2     // Catch:{ all -> 0x0479 }
            r1.L$7 = r3     // Catch:{ all -> 0x0479 }
            r1.L$8 = r12     // Catch:{ all -> 0x0479 }
            r1.L$9 = r0     // Catch:{ all -> 0x0479 }
            r4 = 5
            r1.label = r4     // Catch:{ all -> 0x0479 }
            java.lang.Object r4 = r13.f(r3, r1)     // Catch:{ all -> 0x0479 }
            r5 = r21
            if (r4 != r5) goto L_0x0422
            return r5
        L_0x0422:
            r18 = r9
            r10 = r11
            r17 = r15
            r9 = r0
            r11 = r3
            r15 = r13
            r13 = r2
            r2 = r14
            r14 = r8
            r8 = r28
        L_0x042f:
            r2.d(r12, r9)     // Catch:{ all -> 0x0475 }
            coil.request.i$b r0 = r12.x()     // Catch:{ all -> 0x0475 }
            if (r0 != 0) goto L_0x0439
        L_0x0438:
            goto L_0x043f
        L_0x0439:
            r0.d(r12, r9)     // Catch:{ all -> 0x0475 }
            kotlin.x r0 = kotlin.x.a     // Catch:{ all -> 0x0475 }
            goto L_0x0438
        L_0x043f:
            coil.memory.o r0 = r13.c()     // Catch:{ all -> 0x046b }
            coil.bitmap.e r0 = r0.c()     // Catch:{ all -> 0x046b }
            android.graphics.drawable.Drawable r3 = r11.a()     // Catch:{ all -> 0x046b }
            r4 = 0
            if (r3 == 0) goto L_0x0465
            boolean r6 = r3 instanceof android.graphics.drawable.BitmapDrawable     // Catch:{ all -> 0x046b }
            if (r6 == 0) goto L_0x0465
            r6 = r3
            android.graphics.drawable.BitmapDrawable r6 = (android.graphics.drawable.BitmapDrawable) r6     // Catch:{ all -> 0x046b }
            android.graphics.Bitmap r6 = r6.getBitmap()     // Catch:{ all -> 0x046b }
            if (r6 != 0) goto L_0x045c
        L_0x045b:
            goto L_0x0465
        L_0x045c:
            r7 = 0
            boolean r9 = r0.b(r6)     // Catch:{ all -> 0x046b }
            kotlin.coroutines.jvm.internal.b.a(r9)     // Catch:{ all -> 0x046b }
            goto L_0x045b
        L_0x0465:
            r11 = r10
            r8 = r14
            goto L_0x0589
        L_0x046b:
            r0 = move-exception
            r7 = r2
            r6 = r14
            r13 = r15
            r15 = r17
            r9 = r18
            goto L_0x04d0
        L_0x0475:
            r0 = move-exception
            r9 = r18
            goto L_0x04a2
        L_0x0479:
            r0 = move-exception
            r5 = r21
            goto L_0x0483
        L_0x047d:
            r0 = move-exception
            r5 = r4
            r22 = r6
            r19 = r7
        L_0x0483:
            r10 = r11
            r17 = r15
        L_0x0486:
            r11 = r3
            r15 = r13
            r13 = r2
            r2 = r14
            r14 = r8
            r8 = r28
            goto L_0x04a2
        L_0x048e:
            r0 = move-exception
            r20 = r5
            r22 = r6
            r19 = r7
            r28 = r10
            r5 = r4
            r10 = r11
            r17 = r15
            r11 = r3
            r15 = r13
            r13 = r2
            r2 = r14
            r14 = r8
            r8 = r28
        L_0x04a2:
            coil.memory.o r3 = r13.c()     // Catch:{ all -> 0x04ca }
            coil.bitmap.e r3 = r3.c()     // Catch:{ all -> 0x04ca }
            android.graphics.drawable.Drawable r4 = r11.a()     // Catch:{ all -> 0x04ca }
            r6 = 0
            if (r4 == 0) goto L_0x04c8
            boolean r7 = r4 instanceof android.graphics.drawable.BitmapDrawable     // Catch:{ all -> 0x04ca }
            if (r7 == 0) goto L_0x04c8
            r7 = r4
            android.graphics.drawable.BitmapDrawable r7 = (android.graphics.drawable.BitmapDrawable) r7     // Catch:{ all -> 0x04ca }
            android.graphics.Bitmap r7 = r7.getBitmap()     // Catch:{ all -> 0x04ca }
            if (r7 != 0) goto L_0x04bf
        L_0x04be:
            goto L_0x04c8
        L_0x04bf:
            r12 = 0
            boolean r18 = r3.b(r7)     // Catch:{ all -> 0x04ca }
            kotlin.coroutines.jvm.internal.b.a(r18)     // Catch:{ all -> 0x04ca }
            goto L_0x04be
        L_0x04c8:
            throw r0     // Catch:{ all -> 0x04ca }
        L_0x04ca:
            r0 = move-exception
            r7 = r2
            r6 = r14
            r13 = r15
            r15 = r17
        L_0x04d0:
            r3 = r22
            goto L_0x05ee
        L_0x04d4:
            r0 = move-exception
            r20 = r5
            r19 = r7
            r5 = r4
            r3 = r6
            goto L_0x0599
        L_0x04dd:
            r20 = r5
            r22 = r6
            r19 = r7
            r5 = r4
            boolean r0 = r11 instanceof coil.request.g     // Catch:{ all -> 0x058e }
            if (r0 == 0) goto L_0x0589
            r0 = r11
            coil.request.g r0 = (coil.request.g) r0     // Catch:{ all -> 0x058e }
            r2 = r9
            r3 = 0
            coil.request.i r4 = r0.b()     // Catch:{ all -> 0x058e }
            r10 = r4
            coil.util.m r4 = r2.j()     // Catch:{ all -> 0x058e }
            if (r4 != 0) goto L_0x04ff
            r28 = r2
            r17 = r3
            r3 = r22
            goto L_0x0547
        L_0x04ff:
            r6 = 4
            r7 = r19
            r12 = 0
            r28 = r2
            int r2 = r4.b()     // Catch:{ all -> 0x058e }
            if (r2 > r6) goto L_0x053e
            r2 = 0
            r29 = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x058e }
            r2.<init>()     // Catch:{ all -> 0x058e }
            r17 = r3
            r3 = r22
            r2.append(r3)     // Catch:{ all -> 0x0587 }
            r18 = r12
            java.lang.Object r12 = r10.m()     // Catch:{ all -> 0x0587 }
            r2.append(r12)     // Catch:{ all -> 0x0587 }
            r12 = r20
            r2.append(r12)     // Catch:{ all -> 0x053a }
            r20 = r12
            java.lang.Throwable r12 = r0.c()     // Catch:{ all -> 0x0587 }
            r2.append(r12)     // Catch:{ all -> 0x0587 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0587 }
            r12 = 0
            r4.a(r7, r6, r2, r12)     // Catch:{ all -> 0x0587 }
            goto L_0x0544
        L_0x053a:
            r0 = move-exception
            r20 = r12
            goto L_0x0599
        L_0x053e:
            r17 = r3
            r18 = r12
            r3 = r22
        L_0x0544:
            kotlin.x r2 = kotlin.x.a     // Catch:{ all -> 0x0587 }
        L_0x0547:
            r2 = 0
            coil.util.f.q(r13, r2)     // Catch:{ all -> 0x0587 }
            r1.L$0 = r9     // Catch:{ all -> 0x0587 }
            r1.L$1 = r15     // Catch:{ all -> 0x0587 }
            r1.L$2 = r14     // Catch:{ all -> 0x0587 }
            r1.L$3 = r13     // Catch:{ all -> 0x0587 }
            r1.L$4 = r8     // Catch:{ all -> 0x0587 }
            r1.L$5 = r11     // Catch:{ all -> 0x0587 }
            r1.L$6 = r0     // Catch:{ all -> 0x0587 }
            r1.L$7 = r10     // Catch:{ all -> 0x0587 }
            r2 = 6
            r1.label = r2     // Catch:{ all -> 0x0587 }
            java.lang.Object r2 = r13.b(r0, r1)     // Catch:{ all -> 0x0587 }
            if (r2 != r5) goto L_0x0565
            return r5
        L_0x0565:
            r12 = r8
            r8 = r17
        L_0x0568:
            java.lang.Throwable r2 = r0.c()     // Catch:{ all -> 0x0582 }
            r14.c(r10, r2)     // Catch:{ all -> 0x0582 }
            coil.request.i$b r2 = r10.x()     // Catch:{ all -> 0x0582 }
            if (r2 != 0) goto L_0x0576
        L_0x0575:
            goto L_0x0580
        L_0x0576:
            java.lang.Throwable r4 = r0.c()     // Catch:{ all -> 0x0582 }
            r2.c(r10, r4)     // Catch:{ all -> 0x0582 }
            kotlin.x r2 = kotlin.x.a     // Catch:{ all -> 0x0582 }
            goto L_0x0575
        L_0x0580:
            r8 = r12
            goto L_0x0589
        L_0x0582:
            r0 = move-exception
            r6 = r12
        L_0x0584:
            r7 = r14
            goto L_0x05ee
        L_0x0587:
            r0 = move-exception
            goto L_0x0599
        L_0x0589:
            r8.a()
            return r11
        L_0x058e:
            r0 = move-exception
            r3 = r22
            goto L_0x0599
        L_0x0592:
            r0 = move-exception
            r20 = r5
            r3 = r6
            r19 = r7
            r5 = r4
        L_0x0599:
            r6 = r8
            goto L_0x0584
        L_0x059b:
            r0 = move-exception
            r20 = r5
            r3 = r6
            r19 = r7
            r5 = r4
            r6 = r8
            r7 = r9
            r9 = r11
            goto L_0x05ee
        L_0x05a6:
            r0 = move-exception
            r20 = r5
            r3 = r6
            r19 = r7
            r5 = r4
            goto L_0x05e0
        L_0x05ae:
            r0 = move-exception
            r20 = r5
            r3 = r6
            r19 = r7
            r5 = r4
            goto L_0x05bf
        L_0x05b6:
            r0 = move-exception
            r16 = r3
            r20 = r5
            r3 = r6
            r19 = r7
            r5 = r4
        L_0x05bf:
            coil.memory.o r2 = r9.c()     // Catch:{ all -> 0x05df }
            coil.bitmap.e r2 = r2.c()     // Catch:{ all -> 0x05df }
            r4 = 0
            if (r8 == 0) goto L_0x05cd
            r2.b(r8)     // Catch:{ all -> 0x05df }
        L_0x05cd:
            throw r0     // Catch:{ all -> 0x05df }
        L_0x05cf:
            r29 = r0
            r16 = r3
            r20 = r5
            r3 = r6
            r19 = r7
            r5 = r4
            coil.request.NullRequestDataException r0 = new coil.request.NullRequestDataException     // Catch:{ all -> 0x05df }
            r0.<init>()     // Catch:{ all -> 0x05df }
            throw r0     // Catch:{ all -> 0x05df }
        L_0x05df:
            r0 = move-exception
        L_0x05e0:
            r7 = r10
            r6 = r11
            goto L_0x05ee
        L_0x05e3:
            r0 = move-exception
            r16 = r3
            r20 = r5
            r3 = r6
            r19 = r7
            r5 = r4
            r7 = r10
            r6 = r11
        L_0x05ee:
            boolean r2 = r0 instanceof java.util.concurrent.CancellationException     // Catch:{ all -> 0x0686 }
            if (r2 != 0) goto L_0x0681
            coil.memory.q r2 = r9.o     // Catch:{ all -> 0x0686 }
            coil.request.g r2 = r2.a(r15, r0)     // Catch:{ all -> 0x0686 }
            r4 = r9
            r8 = 0
            coil.request.i r9 = r2.b()     // Catch:{ all -> 0x0686 }
            coil.util.m r10 = r4.j()     // Catch:{ all -> 0x0686 }
            if (r10 != 0) goto L_0x0607
            r28 = r4
            goto L_0x063d
        L_0x0607:
            r11 = 4
            r12 = r19
            r14 = 0
            int r15 = r10.b()     // Catch:{ all -> 0x0686 }
            if (r15 > r11) goto L_0x0638
            r15 = 0
            r28 = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0686 }
            r4.<init>()     // Catch:{ all -> 0x0686 }
            r4.append(r3)     // Catch:{ all -> 0x0686 }
            java.lang.Object r3 = r9.m()     // Catch:{ all -> 0x0686 }
            r4.append(r3)     // Catch:{ all -> 0x0686 }
            r3 = r20
            r4.append(r3)     // Catch:{ all -> 0x0686 }
            java.lang.Throwable r3 = r2.c()     // Catch:{ all -> 0x0686 }
            r4.append(r3)     // Catch:{ all -> 0x0686 }
            java.lang.String r3 = r4.toString()     // Catch:{ all -> 0x0686 }
            r4 = 0
            r10.a(r12, r11, r3, r4)     // Catch:{ all -> 0x0686 }
            goto L_0x063a
        L_0x0638:
            r28 = r4
        L_0x063a:
            kotlin.x r3 = kotlin.x.a     // Catch:{ all -> 0x0686 }
        L_0x063d:
            r3 = 0
            coil.util.f.q(r13, r3)     // Catch:{ all -> 0x0686 }
            r1.L$0 = r7     // Catch:{ all -> 0x0686 }
            r1.L$1 = r6     // Catch:{ all -> 0x0686 }
            r1.L$2 = r2     // Catch:{ all -> 0x0686 }
            r1.L$3 = r9     // Catch:{ all -> 0x0686 }
            r3 = 0
            r1.L$4 = r3     // Catch:{ all -> 0x0686 }
            r1.L$5 = r3     // Catch:{ all -> 0x0686 }
            r1.L$6 = r3     // Catch:{ all -> 0x0686 }
            r1.L$7 = r3     // Catch:{ all -> 0x0686 }
            r1.L$8 = r3     // Catch:{ all -> 0x0686 }
            r1.L$9 = r3     // Catch:{ all -> 0x0686 }
            r3 = 7
            r1.label = r3     // Catch:{ all -> 0x0686 }
            java.lang.Object r3 = r13.b(r2, r1)     // Catch:{ all -> 0x0686 }
            if (r3 != r5) goto L_0x0660
            return r5
        L_0x0660:
            r5 = r2
            r4 = r8
            r0 = r9
        L_0x0663:
            java.lang.Throwable r2 = r5.c()     // Catch:{ all -> 0x0686 }
            r7.c(r0, r2)     // Catch:{ all -> 0x0686 }
            coil.request.i$b r2 = r0.x()     // Catch:{ all -> 0x0686 }
            if (r2 != 0) goto L_0x0671
        L_0x0670:
            goto L_0x067b
        L_0x0671:
            java.lang.Throwable r3 = r5.c()     // Catch:{ all -> 0x0686 }
            r2.c(r0, r3)     // Catch:{ all -> 0x0686 }
            kotlin.x r2 = kotlin.x.a     // Catch:{ all -> 0x0686 }
            goto L_0x0670
        L_0x067b:
            r6.a()
            return r5
        L_0x0681:
            r9.m(r15, r7)     // Catch:{ all -> 0x0686 }
            throw r0     // Catch:{ all -> 0x0686 }
        L_0x0686:
            r0 = move-exception
        L_0x0687:
            r6.a()
            throw r0
        L_0x068b:
            r29 = r0
            r0 = 0
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "The image loader is shutdown."
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.e.f(coil.request.i, int, kotlin.coroutines.d):java.lang.Object");
    }

    public final void n(int level) {
        c().d().a(level);
        c().e().a(level);
        g().a(level);
    }

    private final void m(i request, c eventListener) {
        m $this$log$iv = this.k;
        if ($this$log$iv != null && $this$log$iv.b() <= 4) {
            $this$log$iv.a("RealImageLoader", 4, k.l("🏗  Cancelled - ", request.m()), (Throwable) null);
        }
        eventListener.a(request);
        i.b x = request.x();
        if (x != null) {
            x.a(request);
        }
    }

    /* compiled from: RealImageLoader.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
