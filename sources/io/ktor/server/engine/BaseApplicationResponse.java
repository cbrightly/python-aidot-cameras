package io.ktor.server.engine;

import androidx.core.app.NotificationCompat;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.ktor.features.n;
import io.ktor.http.content.j;
import io.ktor.http.s;
import io.ktor.http.v;
import io.ktor.utils.io.l;
import io.ktor.utils.io.m;
import java.util.List;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.w;
import kotlin.x;
import kotlinx.coroutines.g0;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseApplicationResponse.kt */
public abstract class BaseApplicationResponse implements io.ktor.response.a {
    /* access modifiers changed from: private */
    @NotNull
    public static final io.ktor.util.a<BaseApplicationResponse> a = new io.ktor.util.a<>("EngineResponse");
    public static final a b = new a((DefaultConstructorMarker) null);
    private v c;
    @NotNull
    private final kotlin.g d = kotlin.i.b(new c(this));
    private boolean e;
    @NotNull
    private final io.ktor.response.d f;
    @NotNull
    private final io.ktor.application.b g;

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.server.engine.BaseApplicationResponse", f = "BaseApplicationResponse.kt", l = {182, 183}, m = "respondFromBytes$suspendImpl")
    /* compiled from: BaseApplicationResponse.kt */
    public static final class e extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ BaseApplicationResponse this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(BaseApplicationResponse baseApplicationResponse, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = baseApplicationResponse;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return BaseApplicationResponse.h(this.this$0, (byte[]) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.server.engine.BaseApplicationResponse", f = "BaseApplicationResponse.kt", l = {193, 195, 200}, m = "respondFromChannel$suspendImpl")
    /* compiled from: BaseApplicationResponse.kt */
    public static final class g extends kotlin.coroutines.jvm.internal.d {
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ BaseApplicationResponse this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(BaseApplicationResponse baseApplicationResponse, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = baseApplicationResponse;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return BaseApplicationResponse.j(this.this$0, (io.ktor.utils.io.i) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.server.engine.BaseApplicationResponse", f = "BaseApplicationResponse.kt", l = {101, 110, 118, 128, 137}, m = "respondOutgoingContent$suspendImpl")
    /* compiled from: BaseApplicationResponse.kt */
    public static final class h extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ BaseApplicationResponse this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(BaseApplicationResponse baseApplicationResponse, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = baseApplicationResponse;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return BaseApplicationResponse.n(this.this$0, (io.ktor.http.content.j) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.server.engine.BaseApplicationResponse", f = "BaseApplicationResponse.kt", l = {154, 158}, m = "respondWriteChannelContent$suspendImpl")
    /* compiled from: BaseApplicationResponse.kt */
    public static final class j extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ BaseApplicationResponse this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        j(BaseApplicationResponse baseApplicationResponse, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = baseApplicationResponse;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return BaseApplicationResponse.q(this.this$0, (j.e) null, this);
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Object g(@NotNull byte[] bArr, @NotNull kotlin.coroutines.d<? super x> dVar) {
        return h(this, bArr, dVar);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Object i(@NotNull io.ktor.utils.io.i iVar, @NotNull kotlin.coroutines.d<? super x> dVar) {
        return j(this, iVar, dVar);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Object k(@NotNull j.b bVar, @NotNull kotlin.coroutines.d<? super x> dVar) {
        return x.a;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Object m(@NotNull io.ktor.http.content.j jVar, @NotNull kotlin.coroutines.d<? super x> dVar) {
        return n(this, jVar, dVar);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public abstract Object o(@NotNull j.c cVar, @NotNull kotlin.coroutines.d<? super x> dVar);

    /* access modifiers changed from: protected */
    @Nullable
    public Object p(@NotNull j.e eVar, @NotNull kotlin.coroutines.d<? super x> dVar) {
        return q(this, eVar, dVar);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public abstract Object r(@NotNull kotlin.coroutines.d<? super l> dVar);

    /* access modifiers changed from: protected */
    public abstract void s(@NotNull v vVar);

    /* compiled from: BaseApplicationResponse.kt */
    public static final class b extends kotlin.jvm.internal.l implements p<String, List<? extends String>, x> {
        final /* synthetic */ io.ktor.http.content.j $content;
        final /* synthetic */ w $transferEncodingSet;
        final /* synthetic */ BaseApplicationResponse this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(BaseApplicationResponse baseApplicationResponse, w wVar, io.ktor.http.content.j jVar) {
            super(2);
            this.this$0 = baseApplicationResponse;
            this.$transferEncodingSet = wVar;
            this.$content = jVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            invoke((String) obj, (List<String>) (List) obj2);
            return x.a;
        }

        public final void invoke(@NotNull String name, @NotNull List<String> values) {
            k.f(name, "name");
            k.f(values, "values");
            s sVar = s.V0;
            if (k.a(name, sVar.A())) {
                this.$transferEncodingSet.element = true;
            } else if (k.a(name, sVar.B())) {
                if (this.$content instanceof j.c) {
                    for (String value : values) {
                        this.this$0.getHeaders().a(name, value, false);
                    }
                    return;
                }
                throw new InvalidHeaderForContent(sVar.B(), "non-upgrading response");
            }
            for (String value2 : values) {
                io.ktor.response.f.b(this.this$0.getHeaders(), name, value2, false, 4, (Object) null);
            }
        }
    }

    public BaseApplicationResponse(@NotNull io.ktor.application.b call) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        this.g = call;
        io.ktor.response.d $this$apply = new io.ktor.response.d();
        $this$apply.r(f().a().C());
        this.f = $this$apply;
    }

    @NotNull
    public io.ktor.application.b f() {
        return this.g;
    }

    /* compiled from: BaseApplicationResponse.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<io.ktor.response.e> {
        final /* synthetic */ BaseApplicationResponse this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(BaseApplicationResponse baseApplicationResponse) {
            super(0);
            this.this$0 = baseApplicationResponse;
        }

        @NotNull
        public final io.ktor.response.e invoke() {
            BaseApplicationResponse baseApplicationResponse = this.this$0;
            return new io.ktor.response.e(baseApplicationResponse, k.a(n.a(baseApplicationResponse.f().getRequest()).c(), "https"));
        }
    }

    @Nullable
    public v b() {
        return this.c;
    }

    public void t(@NotNull v value) {
        k.f(value, "value");
        this.c = value;
        s(value);
    }

    @NotNull
    public final io.ktor.response.d a() {
        return this.f;
    }

    /* access modifiers changed from: protected */
    public final void d(@NotNull io.ktor.http.content.j content) {
        Object obj;
        k.f(content, FirebaseAnalytics.Param.CONTENT);
        if (!this.e) {
            this.e = true;
            w transferEncodingSet = new w();
            transferEncodingSet.element = false;
            v it = content.e();
            if (it != null) {
                t(it);
                obj = x.a;
            } else {
                obj = b();
            }
            if (obj == null) {
                t(v.c0.A());
                x xVar = x.a;
            }
            content.c().a(new b(this, transferEncodingSet, content));
            Long contentLength = content.a();
            if (contentLength != null) {
                getHeaders().a(s.V0.r(), t.a(contentLength.longValue()), false);
            } else if (!transferEncodingSet.element && !(content instanceof j.c)) {
                if (content instanceof j.b) {
                    getHeaders().a(s.V0.r(), "0", false);
                } else {
                    getHeaders().a(s.V0.A(), "chunked", false);
                }
            }
            io.ktor.http.c it2 = content.b();
            if (it2 != null) {
                getHeaders().a(s.V0.s(), it2.toString(), false);
            }
            String connection = f().getRequest().getHeaders().get(s.V0.o());
            if (connection == null) {
                return;
            }
            if (kotlin.text.w.y(connection, "close", true)) {
                io.ktor.response.c.a(this, "Connection", "close");
            } else if (kotlin.text.w.y(connection, "keep-alive", true)) {
                io.ktor.response.c.a(this, "Connection", "keep-alive");
            }
        } else {
            throw new ResponseAlreadySentException();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v29, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v20, resolved type: io.ktor.utils.io.i} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00a3, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00c5, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00e0, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        r3 = kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0103, code lost:
        io.ktor.utils.io.k.a(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0106, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0129, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object n(io.ktor.server.engine.BaseApplicationResponse r6, io.ktor.http.content.j r7, kotlin.coroutines.d r8) {
        /*
            boolean r0 = r8 instanceof io.ktor.server.engine.BaseApplicationResponse.h
            if (r0 == 0) goto L_0x0013
            r0 = r8
            io.ktor.server.engine.BaseApplicationResponse$h r0 = (io.ktor.server.engine.BaseApplicationResponse.h) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.server.engine.BaseApplicationResponse$h r0 = new io.ktor.server.engine.BaseApplicationResponse$h
            r0.<init>(r6, r8)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x0085;
                case 1: goto L_0x0077;
                case 2: goto L_0x0063;
                case 3: goto L_0x0054;
                case 4: goto L_0x003c;
                case 5: goto L_0x002d;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002d:
            java.lang.Object r2 = r0.L$1
            r7 = r2
            io.ktor.http.content.j r7 = (io.ktor.http.content.j) r7
            java.lang.Object r2 = r0.L$0
            r6 = r2
            io.ktor.server.engine.BaseApplicationResponse r6 = (io.ktor.server.engine.BaseApplicationResponse) r6
            kotlin.p.b(r1)
            goto L_0x0127
        L_0x003c:
            r2 = r4
            java.lang.Object r3 = r0.L$2
            r2 = r3
            io.ktor.utils.io.i r2 = (io.ktor.utils.io.i) r2
            java.lang.Object r3 = r0.L$1
            r7 = r3
            io.ktor.http.content.j r7 = (io.ktor.http.content.j) r7
            java.lang.Object r3 = r0.L$0
            r6 = r3
            io.ktor.server.engine.BaseApplicationResponse r6 = (io.ktor.server.engine.BaseApplicationResponse) r6
            kotlin.p.b(r1)     // Catch:{ all -> 0x0051 }
            goto L_0x0101
        L_0x0051:
            r3 = move-exception
            goto L_0x010b
        L_0x0054:
            java.lang.Object r2 = r0.L$1
            r7 = r2
            io.ktor.http.content.j r7 = (io.ktor.http.content.j) r7
            java.lang.Object r2 = r0.L$0
            r6 = r2
            io.ktor.server.engine.BaseApplicationResponse r6 = (io.ktor.server.engine.BaseApplicationResponse) r6
            kotlin.p.b(r1)
            goto L_0x00de
        L_0x0063:
            r2 = r4
            java.lang.Object r3 = r0.L$2
            r2 = r3
            byte[] r2 = (byte[]) r2
            java.lang.Object r3 = r0.L$1
            r7 = r3
            io.ktor.http.content.j r7 = (io.ktor.http.content.j) r7
            java.lang.Object r3 = r0.L$0
            r6 = r3
            io.ktor.server.engine.BaseApplicationResponse r6 = (io.ktor.server.engine.BaseApplicationResponse) r6
            kotlin.p.b(r1)
            goto L_0x00c3
        L_0x0077:
            java.lang.Object r2 = r0.L$1
            r7 = r2
            io.ktor.http.content.j r7 = (io.ktor.http.content.j) r7
            java.lang.Object r2 = r0.L$0
            r6 = r2
            io.ktor.server.engine.BaseApplicationResponse r6 = (io.ktor.server.engine.BaseApplicationResponse) r6
            kotlin.p.b(r1)
            goto L_0x00a1
        L_0x0085:
            kotlin.p.b(r1)
            boolean r3 = r7 instanceof io.ktor.http.content.j.c
            if (r3 == 0) goto L_0x00a4
            r6.d(r7)
            r3 = r7
            io.ktor.http.content.j$c r3 = (io.ktor.http.content.j.c) r3
            r0.L$0 = r6
            r0.L$1 = r7
            r4 = 1
            r0.label = r4
            java.lang.Object r3 = r6.o(r3, r0)
            if (r3 != r2) goto L_0x00a1
            return r2
        L_0x00a1:
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x00a4:
            boolean r3 = r7 instanceof io.ktor.http.content.j.a
            if (r3 == 0) goto L_0x00c6
            r3 = r7
            io.ktor.http.content.j$a r3 = (io.ktor.http.content.j.a) r3
            byte[] r3 = r3.g()
            r6.d(r7)
            r0.L$0 = r6
            r0.L$1 = r7
            r0.L$2 = r3
            r4 = 2
            r0.label = r4
            java.lang.Object r4 = r6.g(r3, r0)
            if (r4 != r2) goto L_0x00c2
            return r2
        L_0x00c2:
            r2 = r3
        L_0x00c3:
            kotlin.x r3 = kotlin.x.a
            return r3
        L_0x00c6:
            boolean r3 = r7 instanceof io.ktor.http.content.j.e
            if (r3 == 0) goto L_0x00e1
            r6.d(r7)
            r3 = r7
            io.ktor.http.content.j$e r3 = (io.ktor.http.content.j.e) r3
            r0.L$0 = r6
            r0.L$1 = r7
            r4 = 3
            r0.label = r4
            java.lang.Object r3 = r6.p(r3, r0)
            if (r3 != r2) goto L_0x00de
            return r2
        L_0x00de:
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x00e1:
            boolean r3 = r7 instanceof io.ktor.http.content.j.d
            if (r3 == 0) goto L_0x010f
            r3 = r7
            io.ktor.http.content.j$d r3 = (io.ktor.http.content.j.d) r3
            io.ktor.utils.io.i r3 = r3.g()
            r6.d(r7)     // Catch:{ all -> 0x0107 }
            r0.L$0 = r6     // Catch:{ all -> 0x0107 }
            r0.L$1 = r7     // Catch:{ all -> 0x0107 }
            r0.L$2 = r3     // Catch:{ all -> 0x0107 }
            r4 = 4
            r0.label = r4     // Catch:{ all -> 0x0107 }
            java.lang.Object r4 = r6.i(r3, r0)     // Catch:{ all -> 0x0107 }
            if (r4 != r2) goto L_0x0100
            return r2
        L_0x0100:
            r2 = r3
        L_0x0101:
            kotlin.x r3 = kotlin.x.a     // Catch:{ all -> 0x0051 }
            io.ktor.utils.io.k.a(r2)
            return r3
        L_0x0107:
            r2 = move-exception
            r5 = r3
            r3 = r2
            r2 = r5
        L_0x010b:
            io.ktor.utils.io.k.a(r2)
            throw r3
        L_0x010f:
            boolean r3 = r7 instanceof io.ktor.http.content.j.b
            if (r3 == 0) goto L_0x012a
            r6.d(r7)
            r3 = r7
            io.ktor.http.content.j$b r3 = (io.ktor.http.content.j.b) r3
            r0.L$0 = r6
            r0.L$1 = r7
            r4 = 5
            r0.label = r4
            java.lang.Object r3 = r6.k(r3, r0)
            if (r3 != r2) goto L_0x0127
            return r2
        L_0x0127:
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x012a:
            kotlin.NoWhenBranchMatchedException r2 = new kotlin.NoWhenBranchMatchedException
            r2.<init>()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.engine.BaseApplicationResponse.n(io.ktor.server.engine.BaseApplicationResponse, io.ktor.http.content.j, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v2, resolved type: io.ktor.http.content.j$e} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v2, resolved type: io.ktor.server.engine.BaseApplicationResponse} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v11, resolved type: io.ktor.utils.io.l} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x008f A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0090  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object q(io.ktor.server.engine.BaseApplicationResponse r13, io.ktor.http.content.j.e r14, kotlin.coroutines.d r15) {
        /*
            boolean r0 = r15 instanceof io.ktor.server.engine.BaseApplicationResponse.j
            if (r0 == 0) goto L_0x0013
            r0 = r15
            io.ktor.server.engine.BaseApplicationResponse$j r0 = (io.ktor.server.engine.BaseApplicationResponse.j) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.server.engine.BaseApplicationResponse$j r0 = new io.ktor.server.engine.BaseApplicationResponse$j
            r0.<init>(r13, r15)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            r4 = 1
            r5 = 0
            switch(r3) {
                case 0: goto L_0x005d;
                case 1: goto L_0x004e;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002d:
            r2 = r5
            r3 = r5
            r6 = 0
            r7 = r6
            java.lang.Object r8 = r0.L$3
            r3 = r8
            io.ktor.utils.io.l r3 = (io.ktor.utils.io.l) r3
            java.lang.Object r8 = r0.L$2
            r2 = r8
            io.ktor.utils.io.l r2 = (io.ktor.utils.io.l) r2
            java.lang.Object r8 = r0.L$1
            r14 = r8
            io.ktor.http.content.j$e r14 = (io.ktor.http.content.j.e) r14
            java.lang.Object r8 = r0.L$0
            r13 = r8
            io.ktor.server.engine.BaseApplicationResponse r13 = (io.ktor.server.engine.BaseApplicationResponse) r13
            kotlin.p.b(r1)     // Catch:{ ClosedWriteChannelException -> 0x004c }
            goto L_0x0093
        L_0x0049:
            r3 = move-exception
            goto L_0x00ae
        L_0x004c:
            r8 = move-exception
            goto L_0x00a8
        L_0x004e:
            java.lang.Object r3 = r0.L$1
            r14 = r3
            io.ktor.http.content.j$e r14 = (io.ktor.http.content.j.e) r14
            java.lang.Object r3 = r0.L$0
            r13 = r3
            io.ktor.server.engine.BaseApplicationResponse r13 = (io.ktor.server.engine.BaseApplicationResponse) r13
            kotlin.p.b(r1)
            r3 = r1
            goto L_0x006d
        L_0x005d:
            kotlin.p.b(r1)
            r0.L$0 = r13
            r0.L$1 = r14
            r0.label = r4
            java.lang.Object r3 = r13.r(r0)
            if (r3 != r2) goto L_0x006d
            return r2
        L_0x006d:
            io.ktor.utils.io.l r3 = (io.ktor.utils.io.l) r3
            r6 = 0
            r7 = r3
            r8 = 0
            kotlinx.coroutines.i0 r9 = kotlinx.coroutines.d1.b()     // Catch:{ ClosedWriteChannelException -> 0x00a2, all -> 0x009d }
            io.ktor.server.engine.BaseApplicationResponse$i r10 = new io.ktor.server.engine.BaseApplicationResponse$i     // Catch:{ ClosedWriteChannelException -> 0x00a2, all -> 0x009d }
            r10.<init>(r7, r5, r0, r14)     // Catch:{ ClosedWriteChannelException -> 0x00a2, all -> 0x009d }
            r0.L$0 = r13     // Catch:{ ClosedWriteChannelException -> 0x00a2, all -> 0x009d }
            r0.L$1 = r14     // Catch:{ ClosedWriteChannelException -> 0x00a2, all -> 0x009d }
            r0.L$2 = r3     // Catch:{ ClosedWriteChannelException -> 0x00a2, all -> 0x009d }
            r0.L$3 = r7     // Catch:{ ClosedWriteChannelException -> 0x00a2, all -> 0x009d }
            r11 = 2
            r0.label = r11     // Catch:{ ClosedWriteChannelException -> 0x00a2, all -> 0x009d }
            java.lang.Object r4 = kotlinx.coroutines.h.g(r9, r10, r0)     // Catch:{ ClosedWriteChannelException -> 0x00a2, all -> 0x009d }
            if (r4 != r2) goto L_0x0090
            return r2
        L_0x0090:
            r2 = r3
            r3 = r7
            r7 = r8
        L_0x0093:
            io.ktor.utils.io.m.a(r2)
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x009d:
            r2 = move-exception
            r12 = r3
            r3 = r2
            r2 = r12
            goto L_0x00ae
        L_0x00a2:
            r2 = move-exception
            r12 = r8
            r8 = r2
            r2 = r3
            r3 = r7
            r7 = r12
        L_0x00a8:
            io.ktor.util.cio.ChannelWriteException r9 = new io.ktor.util.cio.ChannelWriteException     // Catch:{ all -> 0x0049 }
            r9.<init>(r5, r8, r4, r5)     // Catch:{ all -> 0x0049 }
            throw r9     // Catch:{ all -> 0x0049 }
        L_0x00ae:
            r2.d(r3)     // Catch:{ all -> 0x00b3 }
            throw r3     // Catch:{ all -> 0x00b3 }
        L_0x00b3:
            r3 = move-exception
            io.ktor.utils.io.m.a(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.engine.BaseApplicationResponse.q(io.ktor.server.engine.BaseApplicationResponse, io.ktor.http.content.j$e, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: BaseApplicationResponse.kt */
    public static final class i extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ j.e $content$inlined;
        final /* synthetic */ kotlin.coroutines.d $continuation$inlined;
        final /* synthetic */ l $this_use;
        Object L$0;
        int label;
        private o0 p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        i(l lVar, kotlin.coroutines.d dVar, kotlin.coroutines.d dVar2, j.e eVar) {
            super(2, dVar);
            this.$this_use = lVar;
            this.$continuation$inlined = dVar2;
            this.$content$inlined = eVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            k.f(dVar, "completion");
            i iVar = new i(this.$this_use, dVar, this.$continuation$inlined, this.$content$inlined);
            o0 o0Var = (o0) obj;
            iVar.p$ = (o0) obj;
            return iVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((i) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    o0 $this$withContext = this.p$;
                    j.e eVar = this.$content$inlined;
                    l lVar = this.$this_use;
                    this.L$0 = $this$withContext;
                    this.label = 1;
                    if (eVar.g(lVar, this) != d) {
                        o0 o0Var = $this$withContext;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    o0 $this$withContext2 = (o0) this.L$0;
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v2, resolved type: io.ktor.server.engine.BaseApplicationResponse} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: io.ktor.utils.io.l} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00b2 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object h(io.ktor.server.engine.BaseApplicationResponse r11, byte[] r12, kotlin.coroutines.d r13) {
        /*
            boolean r0 = r13 instanceof io.ktor.server.engine.BaseApplicationResponse.e
            if (r0 == 0) goto L_0x0013
            r0 = r13
            io.ktor.server.engine.BaseApplicationResponse$e r0 = (io.ktor.server.engine.BaseApplicationResponse.e) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.server.engine.BaseApplicationResponse$e r0 = new io.ktor.server.engine.BaseApplicationResponse$e
            r0.<init>(r11, r13)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x005b;
                case 1: goto L_0x004c;
                case 2: goto L_0x002c;
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
            r4 = 0
            r5 = r4
            java.lang.Object r6 = r0.L$3
            r3 = r6
            io.ktor.utils.io.l r3 = (io.ktor.utils.io.l) r3
            java.lang.Object r6 = r0.L$2
            r2 = r6
            io.ktor.utils.io.l r2 = (io.ktor.utils.io.l) r2
            java.lang.Object r6 = r0.L$1
            r12 = r6
            byte[] r12 = (byte[]) r12
            java.lang.Object r6 = r0.L$0
            r11 = r6
            io.ktor.server.engine.BaseApplicationResponse r11 = (io.ktor.server.engine.BaseApplicationResponse) r11
            kotlin.p.b(r1)     // Catch:{ all -> 0x0049 }
            goto L_0x00b7
        L_0x0049:
            r3 = move-exception
            goto L_0x00c5
        L_0x004c:
            java.lang.Object r3 = r0.L$1
            r12 = r3
            byte[] r12 = (byte[]) r12
            java.lang.Object r3 = r0.L$0
            r11 = r3
            io.ktor.server.engine.BaseApplicationResponse r11 = (io.ktor.server.engine.BaseApplicationResponse) r11
            kotlin.p.b(r1)
            r3 = r1
            goto L_0x0091
        L_0x005b:
            kotlin.p.b(r1)
            io.ktor.response.f r3 = r11.getHeaders()
            io.ktor.http.s r5 = io.ktor.http.s.V0
            java.lang.String r5 = r5.r()
            java.lang.String r3 = r3.d(r5)
            if (r3 == 0) goto L_0x0082
            long r5 = java.lang.Long.parseLong(r3)
            java.lang.Long r3 = kotlin.coroutines.jvm.internal.b.d(r5)
            if (r3 == 0) goto L_0x0082
            long r5 = r3.longValue()
            r3 = 0
            int r7 = r12.length
            long r7 = (long) r7
            r11.e(r5, r7)
        L_0x0082:
            r0.L$0 = r11
            r0.L$1 = r12
            r3 = 1
            r0.label = r3
            java.lang.Object r3 = r11.r(r0)
            if (r3 != r2) goto L_0x0091
            return r2
        L_0x0091:
            io.ktor.utils.io.l r3 = (io.ktor.utils.io.l) r3
            r5 = 0
            r6 = r3
            r7 = 0
            kotlinx.coroutines.i0 r8 = kotlinx.coroutines.d1.d()     // Catch:{ all -> 0x00c0 }
            io.ktor.server.engine.BaseApplicationResponse$d r9 = new io.ktor.server.engine.BaseApplicationResponse$d     // Catch:{ all -> 0x00c0 }
            r9.<init>(r6, r4, r0, r12)     // Catch:{ all -> 0x00c0 }
            r0.L$0 = r11     // Catch:{ all -> 0x00c0 }
            r0.L$1 = r12     // Catch:{ all -> 0x00c0 }
            r0.L$2 = r3     // Catch:{ all -> 0x00c0 }
            r0.L$3 = r6     // Catch:{ all -> 0x00c0 }
            r4 = 2
            r0.label = r4     // Catch:{ all -> 0x00c0 }
            java.lang.Object r4 = kotlinx.coroutines.h.g(r8, r9, r0)     // Catch:{ all -> 0x00c0 }
            if (r4 != r2) goto L_0x00b3
            return r2
        L_0x00b3:
            r2 = r3
            r4 = r5
            r3 = r6
            r5 = r7
        L_0x00b7:
            io.ktor.utils.io.m.a(r2)
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x00c0:
            r2 = move-exception
            r4 = r5
            r10 = r3
            r3 = r2
            r2 = r10
        L_0x00c5:
            r2.d(r3)     // Catch:{ all -> 0x00ca }
            throw r3     // Catch:{ all -> 0x00ca }
        L_0x00ca:
            r3 = move-exception
            io.ktor.utils.io.m.a(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.engine.BaseApplicationResponse.h(io.ktor.server.engine.BaseApplicationResponse, byte[], kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: BaseApplicationResponse.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ byte[] $bytes$inlined;
        final /* synthetic */ kotlin.coroutines.d $continuation$inlined;
        final /* synthetic */ l $this_use;
        Object L$0;
        int label;
        private o0 p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(l lVar, kotlin.coroutines.d dVar, kotlin.coroutines.d dVar2, byte[] bArr) {
            super(2, dVar);
            this.$this_use = lVar;
            this.$continuation$inlined = dVar2;
            this.$bytes$inlined = bArr;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            k.f(dVar, "completion");
            d dVar2 = new d(this.$this_use, dVar, this.$continuation$inlined, this.$bytes$inlined);
            o0 o0Var = (o0) obj;
            dVar2.p$ = (o0) obj;
            return dVar2;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((d) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    o0 $this$withContext = this.p$;
                    l lVar = this.$this_use;
                    byte[] bArr = this.$bytes$inlined;
                    this.L$0 = $this$withContext;
                    this.label = 1;
                    if (m.b(lVar, bArr, this) != d) {
                        o0 o0Var = $this$withContext;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    o0 $this$withContext2 = (o0) this.L$0;
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: io.ktor.server.engine.BaseApplicationResponse} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: java.lang.Long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: io.ktor.utils.io.l} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v22, resolved type: io.ktor.utils.io.l} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v5, resolved type: io.ktor.server.engine.BaseApplicationResponse} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v12, resolved type: java.lang.Long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v26, resolved type: io.ktor.utils.io.l} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00c5 A[SYNTHETIC, Splitter:B:31:0x00c5] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0103 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0104  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object j(io.ktor.server.engine.BaseApplicationResponse r19, io.ktor.utils.io.i r20, kotlin.coroutines.d r21) {
        /*
            r0 = r19
            r1 = r21
            boolean r2 = r1 instanceof io.ktor.server.engine.BaseApplicationResponse.g
            if (r2 == 0) goto L_0x0017
            r2 = r1
            io.ktor.server.engine.BaseApplicationResponse$g r2 = (io.ktor.server.engine.BaseApplicationResponse.g) r2
            int r3 = r2.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = r3 & r4
            if (r5 == 0) goto L_0x0017
            int r3 = r3 - r4
            r2.label = r3
            goto L_0x001c
        L_0x0017:
            io.ktor.server.engine.BaseApplicationResponse$g r2 = new io.ktor.server.engine.BaseApplicationResponse$g
            r2.<init>(r0, r1)
        L_0x001c:
            java.lang.Object r10 = r2.result
            java.lang.Object r11 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r2.label
            r4 = 0
            r5 = 0
            switch(r3) {
                case 0: goto L_0x0097;
                case 1: goto L_0x0087;
                case 2: goto L_0x005c;
                case 3: goto L_0x0033;
                default: goto L_0x0029;
            }
        L_0x0029:
            r16 = r2
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r1
        L_0x0033:
            r3 = r5
            r6 = 0
            r8 = r5
            r9 = r4
            long r6 = r2.J$0
            java.lang.Object r11 = r2.L$4
            r8 = r11
            java.lang.Long r8 = (java.lang.Long) r8
            java.lang.Object r11 = r2.L$3
            r5 = r11
            io.ktor.utils.io.l r5 = (io.ktor.utils.io.l) r5
            java.lang.Object r11 = r2.L$2
            r3 = r11
            io.ktor.utils.io.l r3 = (io.ktor.utils.io.l) r3
            java.lang.Object r11 = r2.L$1
            io.ktor.utils.io.i r11 = (io.ktor.utils.io.i) r11
            java.lang.Object r12 = r2.L$0
            io.ktor.server.engine.BaseApplicationResponse r12 = (io.ktor.server.engine.BaseApplicationResponse) r12
            kotlin.p.b(r10)     // Catch:{ all -> 0x0057 }
            r0 = r10
            goto L_0x012f
        L_0x0057:
            r0 = move-exception
            r16 = r2
            goto L_0x0166
        L_0x005c:
            r3 = r5
            r6 = r5
            r7 = r4
            java.lang.Object r8 = r2.L$4
            r6 = r8
            java.lang.Long r6 = (java.lang.Long) r6
            java.lang.Object r8 = r2.L$3
            r5 = r8
            io.ktor.utils.io.l r5 = (io.ktor.utils.io.l) r5
            java.lang.Object r8 = r2.L$2
            r3 = r8
            io.ktor.utils.io.l r3 = (io.ktor.utils.io.l) r3
            java.lang.Object r8 = r2.L$1
            io.ktor.utils.io.i r8 = (io.ktor.utils.io.i) r8
            java.lang.Object r9 = r2.L$0
            r12 = r9
            io.ktor.server.engine.BaseApplicationResponse r12 = (io.ktor.server.engine.BaseApplicationResponse) r12
            kotlin.p.b(r10)     // Catch:{ all -> 0x0081 }
            r14 = r3
            r9 = r7
            r13 = r8
            r3 = r10
            r8 = r6
            goto L_0x0109
        L_0x0081:
            r0 = move-exception
            r16 = r2
            r11 = r8
            goto L_0x0166
        L_0x0087:
            java.lang.Object r3 = r2.L$1
            io.ktor.utils.io.i r3 = (io.ktor.utils.io.i) r3
            java.lang.Object r4 = r2.L$0
            r0 = r4
            io.ktor.server.engine.BaseApplicationResponse r0 = (io.ktor.server.engine.BaseApplicationResponse) r0
            kotlin.p.b(r10)
            r12 = r0
            r13 = r3
            r4 = r10
            goto L_0x00ac
        L_0x0097:
            kotlin.p.b(r10)
            r2.L$0 = r0
            r3 = r20
            r2.L$1 = r3
            r4 = 1
            r2.label = r4
            java.lang.Object r4 = r0.r(r2)
            if (r4 != r11) goto L_0x00aa
            return r11
        L_0x00aa:
            r12 = r0
            r13 = r3
        L_0x00ac:
            r14 = r4
            io.ktor.utils.io.l r14 = (io.ktor.utils.io.l) r14
            r15 = 0
            r0 = r14
            r16 = 0
            io.ktor.response.f r3 = r12.getHeaders()     // Catch:{ all -> 0x0160 }
            io.ktor.http.s r4 = io.ktor.http.s.V0     // Catch:{ all -> 0x0160 }
            java.lang.String r4 = r4.r()     // Catch:{ all -> 0x0160 }
            java.lang.String r3 = r3.d(r4)     // Catch:{ all -> 0x0160 }
            if (r3 == 0) goto L_0x00d6
            long r3 = java.lang.Long.parseLong(r3)     // Catch:{ all -> 0x00ce }
            java.lang.Long r5 = kotlin.coroutines.jvm.internal.b.d(r3)     // Catch:{ all -> 0x00ce }
            goto L_0x00d6
        L_0x00ce:
            r0 = move-exception
            r16 = r2
            r11 = r13
            r3 = r14
            r4 = r15
            goto L_0x0166
        L_0x00d6:
            r9 = r5
            kotlinx.coroutines.i0 r8 = kotlinx.coroutines.d1.d()     // Catch:{ all -> 0x0160 }
            io.ktor.server.engine.BaseApplicationResponse$f r7 = new io.ktor.server.engine.BaseApplicationResponse$f     // Catch:{ all -> 0x0160 }
            r6 = 0
            r3 = r7
            r4 = r0
            r5 = r9
            r17 = r7
            r7 = r12
            r18 = r8
            r8 = r2
            r1 = r9
            r9 = r13
            r3.<init>(r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0160 }
            r2.L$0 = r12     // Catch:{ all -> 0x0160 }
            r2.L$1 = r13     // Catch:{ all -> 0x0160 }
            r2.L$2 = r14     // Catch:{ all -> 0x0160 }
            r2.L$3 = r0     // Catch:{ all -> 0x0160 }
            r2.L$4 = r1     // Catch:{ all -> 0x0160 }
            r3 = 2
            r2.label = r3     // Catch:{ all -> 0x0160 }
            r4 = r17
            r3 = r18
            java.lang.Object r3 = kotlinx.coroutines.h.g(r3, r4, r2)     // Catch:{ all -> 0x0160 }
            if (r3 != r11) goto L_0x0104
            return r11
        L_0x0104:
            r5 = r0
            r8 = r1
            r4 = r15
            r9 = r16
        L_0x0109:
            java.lang.Number r3 = (java.lang.Number) r3     // Catch:{ all -> 0x015a }
            long r0 = r3.longValue()     // Catch:{ all -> 0x015a }
            r6 = r0
            if (r8 == 0) goto L_0x0150
            r8.longValue()     // Catch:{ all -> 0x015a }
            r0 = 1
            r2.L$0 = r12     // Catch:{ all -> 0x015a }
            r2.L$1 = r13     // Catch:{ all -> 0x015a }
            r2.L$2 = r14     // Catch:{ all -> 0x015a }
            r2.L$3 = r5     // Catch:{ all -> 0x015a }
            r2.L$4 = r8     // Catch:{ all -> 0x015a }
            r2.J$0 = r6     // Catch:{ all -> 0x015a }
            r3 = 3
            r2.label = r3     // Catch:{ all -> 0x015a }
            java.lang.Object r0 = r13.g(r0, r2)     // Catch:{ all -> 0x015a }
            if (r0 != r11) goto L_0x012d
            return r11
        L_0x012d:
            r11 = r13
            r3 = r14
        L_0x012f:
            java.lang.Number r0 = (java.lang.Number) r0     // Catch:{ all -> 0x014a }
            long r0 = r0.longValue()     // Catch:{ all -> 0x014a }
            long r13 = r8.longValue()     // Catch:{ all -> 0x014a }
            r16 = r2
            r19 = r3
            long r2 = r6 + r0
            r12.e(r13, r2)     // Catch:{ all -> 0x0146 }
            r14 = r19
            r13 = r11
            goto L_0x0152
        L_0x0146:
            r0 = move-exception
            r3 = r19
            goto L_0x0166
        L_0x014a:
            r0 = move-exception
            r16 = r2
            r19 = r3
            goto L_0x0166
        L_0x0150:
            r16 = r2
        L_0x0152:
            io.ktor.utils.io.m.a(r14)
            kotlin.x r0 = kotlin.x.a
            return r0
        L_0x015a:
            r0 = move-exception
            r16 = r2
            r11 = r13
            r3 = r14
            goto L_0x0166
        L_0x0160:
            r0 = move-exception
            r16 = r2
            r11 = r13
            r3 = r14
            r4 = r15
        L_0x0166:
            r3.d(r0)     // Catch:{ all -> 0x016b }
            throw r0     // Catch:{ all -> 0x016b }
        L_0x016b:
            r0 = move-exception
            io.ktor.utils.io.m.a(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.engine.BaseApplicationResponse.j(io.ktor.server.engine.BaseApplicationResponse, io.ktor.utils.io.i, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: BaseApplicationResponse.kt */
    public static final class f extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super Long>, Object> {
        final /* synthetic */ kotlin.coroutines.d $continuation$inlined;
        final /* synthetic */ Long $length;
        final /* synthetic */ io.ktor.utils.io.i $readChannel$inlined;
        final /* synthetic */ l $this_use;
        Object L$0;
        int label;
        private o0 p$;
        final /* synthetic */ BaseApplicationResponse this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(l lVar, Long l, kotlin.coroutines.d dVar, BaseApplicationResponse baseApplicationResponse, kotlin.coroutines.d dVar2, io.ktor.utils.io.i iVar) {
            super(2, dVar);
            this.$this_use = lVar;
            this.$length = l;
            this.this$0 = baseApplicationResponse;
            this.$continuation$inlined = dVar2;
            this.$readChannel$inlined = iVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            k.f(dVar, "completion");
            f fVar = new f(this.$this_use, this.$length, dVar, this.this$0, this.$continuation$inlined, this.$readChannel$inlined);
            o0 o0Var = (o0) obj;
            fVar.p$ = (o0) obj;
            return fVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((f) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    o0 $this$withContext = this.p$;
                    io.ktor.utils.io.i iVar = this.$readChannel$inlined;
                    l lVar = this.$this_use;
                    Long l = this.$length;
                    long longValue = l != null ? l.longValue() : DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
                    this.L$0 = $this$withContext;
                    this.label = 1;
                    Object a = io.ktor.utils.io.j.a(iVar, lVar, longValue, this);
                    if (a == d) {
                        return d;
                    }
                    o0 o0Var = $this$withContext;
                    return a;
                case 1:
                    o0 $this$withContext2 = (o0) this.L$0;
                    kotlin.p.b($result);
                    return $result;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    private final void e(long expected, long actual) {
        if (expected < actual) {
            throw new BodyLengthIsTooLong(expected);
        } else if (expected > actual) {
            throw new BodyLengthIsTooSmall(expected, actual);
        }
    }

    /* compiled from: BaseApplicationResponse.kt */
    public static final class ResponseAlreadySentException extends IllegalStateException {
        public ResponseAlreadySentException() {
            super("Response has already been sent");
        }
    }

    /* compiled from: BaseApplicationResponse.kt */
    public static final class InvalidHeaderForContent extends IllegalStateException implements g0<InvalidHeaderForContent> {
        private final String content;
        private final String name;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public InvalidHeaderForContent(@NotNull String name2, @NotNull String content2) {
            super("Header " + name2 + " is not allowed for " + content2);
            k.f(name2, "name");
            k.f(content2, FirebaseAnalytics.Param.CONTENT);
            this.name = name2;
            this.content = content2;
        }

        @Nullable
        public InvalidHeaderForContent createCopy() {
            InvalidHeaderForContent it = new InvalidHeaderForContent(this.name, this.content);
            it.initCause(this);
            return it;
        }
    }

    /* compiled from: BaseApplicationResponse.kt */
    public static final class BodyLengthIsTooSmall extends IllegalStateException implements g0<BodyLengthIsTooSmall> {
        private final long actual;
        private final long expected;

        public BodyLengthIsTooSmall(long expected2, long actual2) {
            super("Body.size is too small. Body: " + actual2 + ", Content-Length: " + expected2);
            this.expected = expected2;
            this.actual = actual2;
        }

        @Nullable
        public BodyLengthIsTooSmall createCopy() {
            BodyLengthIsTooSmall it = new BodyLengthIsTooSmall(this.expected, this.actual);
            it.initCause(this);
            return it;
        }
    }

    /* compiled from: BaseApplicationResponse.kt */
    public static final class BodyLengthIsTooLong extends IllegalStateException implements g0<BodyLengthIsTooLong> {
        private final long expected;

        public BodyLengthIsTooLong(long expected2) {
            super("Body.size is too long. Expected " + expected2);
            this.expected = expected2;
        }

        @Nullable
        public BodyLengthIsTooLong createCopy() {
            BodyLengthIsTooLong it = new BodyLengthIsTooLong(this.expected);
            it.initCause(this);
            return it;
        }
    }

    /* compiled from: BaseApplicationResponse.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final io.ktor.util.a<BaseApplicationResponse> a() {
            return BaseApplicationResponse.a;
        }

        @kotlin.coroutines.jvm.internal.f(c = "io.ktor.server.engine.BaseApplicationResponse$Companion$setupSendPipeline$1", f = "BaseApplicationResponse.kt", l = {307}, m = "invokeSuspend")
        /* renamed from: io.ktor.server.engine.BaseApplicationResponse$a$a  reason: collision with other inner class name */
        /* compiled from: BaseApplicationResponse.kt */
        public static final class C0269a extends kotlin.coroutines.jvm.internal.l implements q<io.ktor.util.pipeline.d<Object, io.ktor.application.b>, Object, kotlin.coroutines.d<? super x>, Object> {
            Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            int label;
            private io.ktor.util.pipeline.d p$;
            private Object p$0;

            C0269a(kotlin.coroutines.d dVar) {
                super(3, dVar);
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@NotNull io.ktor.util.pipeline.d<Object, io.ktor.application.b> dVar, @NotNull Object obj, @NotNull kotlin.coroutines.d<? super x> dVar2) {
                k.f(dVar, "$this$create");
                k.f(obj, "response");
                k.f(dVar2, "continuation");
                C0269a aVar = new C0269a(dVar2);
                aVar.p$ = dVar;
                aVar.p$0 = obj;
                return aVar;
            }

            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return ((C0269a) create((io.ktor.util.pipeline.d) obj, obj2, (kotlin.coroutines.d) obj3)).invokeSuspend(x.a);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                Object d = kotlin.coroutines.intrinsics.c.d();
                BaseApplicationResponse baseApplicationResponse = null;
                switch (this.label) {
                    case 0:
                        kotlin.p.b($result);
                        io.ktor.util.pipeline.d $this$intercept = this.p$;
                        Object response = this.p$0;
                        if (response instanceof io.ktor.http.content.j) {
                            io.ktor.application.b call = (io.ktor.application.b) $this$intercept.getContext();
                            io.ktor.response.a b = call.b();
                            if (b instanceof BaseApplicationResponse) {
                                baseApplicationResponse = b;
                            }
                            BaseApplicationResponse callResponse = baseApplicationResponse;
                            if (callResponse == null) {
                                callResponse = (BaseApplicationResponse) call.getAttributes().a(BaseApplicationResponse.b.a());
                            }
                            this.L$0 = $this$intercept;
                            this.L$1 = response;
                            this.L$2 = call;
                            this.L$3 = callResponse;
                            this.label = 1;
                            if (callResponse.m((io.ktor.http.content.j) response, this) != d) {
                                Object obj = response;
                                Object response2 = call;
                                break;
                            } else {
                                return d;
                            }
                        } else {
                            throw new IllegalArgumentException("Response pipeline couldn't transform '" + response.getClass() + "' to the OutgoingContent");
                        }
                    case 1:
                        BaseApplicationResponse callResponse2 = (BaseApplicationResponse) this.L$3;
                        io.ktor.application.b call2 = (io.ktor.application.b) this.L$2;
                        Object response3 = this.L$1;
                        io.ktor.util.pipeline.d $this$intercept2 = (io.ktor.util.pipeline.d) this.L$0;
                        kotlin.p.b($result);
                        break;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                return x.a;
            }
        }

        public final void b(@NotNull io.ktor.response.d sendPipeline) {
            k.f(sendPipeline, "sendPipeline");
            sendPipeline.p(io.ktor.response.d.p3.a(), new C0269a((kotlin.coroutines.d) null));
        }
    }
}
