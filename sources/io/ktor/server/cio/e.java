package io.ktor.server.cio;

import androidx.core.app.NotificationCompat;
import io.ktor.http.content.j;
import io.ktor.http.v;
import io.ktor.server.engine.BaseApplicationResponse;
import io.ktor.utils.io.k;
import io.ktor.utils.io.l;
import io.ktor.utils.io.m;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.p;
import kotlin.collections.q;
import kotlin.x;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.w;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CIOApplicationResponse.kt */
public final class e extends BaseApplicationResponse {
    private volatile l chunkedChannel;
    private volatile z1 chunkedJob;
    private v h = v.c0.A();
    /* access modifiers changed from: private */
    public final ArrayList<String> i = new ArrayList<>();
    /* access modifiers changed from: private */
    public final ArrayList<String> j = new ArrayList<>();
    @NotNull
    private final io.ktor.response.f k = new a(this);
    /* access modifiers changed from: private */
    public final l l;
    /* access modifiers changed from: private */
    public final io.ktor.utils.io.i m;
    private final kotlin.coroutines.g n;
    private final kotlin.coroutines.g o;
    private final w<Boolean> p;

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.server.cio.CIOApplicationResponse", f = "CIOApplicationResponse.kt", l = {142}, m = "preparedBodyChannel")
    /* compiled from: CIOApplicationResponse.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(e eVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = eVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.y(this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.server.cio.CIOApplicationResponse", f = "CIOApplicationResponse.kt", l = {97, 98, 99}, m = "respondFromBytes")
    /* compiled from: CIOApplicationResponse.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(e eVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = eVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.g((byte[]) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.server.cio.CIOApplicationResponse", f = "CIOApplicationResponse.kt", l = {106}, m = "respondNoContent")
    /* renamed from: io.ktor.server.cio.e$e  reason: collision with other inner class name */
    /* compiled from: CIOApplicationResponse.kt */
    public static final class C0268e extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0268e(e eVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = eVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.k((j.b) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.server.cio.CIOApplicationResponse", f = "CIOApplicationResponse.kt", l = {111, 113}, m = "respondOutgoingContent")
    /* compiled from: CIOApplicationResponse.kt */
    public static final class f extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(e eVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = eVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.m((io.ktor.http.content.j) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.server.cio.CIOApplicationResponse", f = "CIOApplicationResponse.kt", l = {89, 91, 93}, m = "respondUpgrade")
    /* compiled from: CIOApplicationResponse.kt */
    public static final class g extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(e eVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = eVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.o((j.c) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.server.cio.CIOApplicationResponse", f = "CIOApplicationResponse.kt", l = {82, 83}, m = "responseChannel")
    /* compiled from: CIOApplicationResponse.kt */
    public static final class i extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        i(e eVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = eVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.r(this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.server.cio.CIOApplicationResponse", f = "CIOApplicationResponse.kt", l = {128}, m = "sendResponseMessage")
    /* compiled from: CIOApplicationResponse.kt */
    public static final class j extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        j(e eVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = eVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.z(false, this);
        }
    }

    /* compiled from: CIOApplicationResponse.kt */
    public static final class h extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, x> {
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(e eVar) {
            super(1);
            this.this$0 = eVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return x.a;
        }

        public final void invoke(@Nullable Throwable it) {
            m.a(this.this$0.l);
            k.a(this.this$0.m);
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public e(@NotNull b call, @NotNull l output, @NotNull io.ktor.utils.io.i input, @NotNull kotlin.coroutines.g engineDispatcher, @NotNull kotlin.coroutines.g userDispatcher, @Nullable w<Boolean> upgraded) {
        super(call);
        kotlin.jvm.internal.k.f(call, NotificationCompat.CATEGORY_CALL);
        kotlin.jvm.internal.k.f(output, "output");
        kotlin.jvm.internal.k.f(input, "input");
        kotlin.jvm.internal.k.f(engineDispatcher, "engineDispatcher");
        kotlin.jvm.internal.k.f(userDispatcher, "userDispatcher");
        this.l = output;
        this.m = input;
        this.n = engineDispatcher;
        this.o = userDispatcher;
        this.p = upgraded;
    }

    /* compiled from: CIOApplicationResponse.kt */
    public static final class a extends io.ktor.response.f {
        final /* synthetic */ e a;

        a(e $outer) {
            this.a = $outer;
        }

        /* access modifiers changed from: protected */
        public void c(@NotNull String name, @NotNull String value) {
            kotlin.jvm.internal.k.f(name, "name");
            kotlin.jvm.internal.k.f(value, "value");
            this.a.i.add(name);
            this.a.j.add(value);
        }

        /* access modifiers changed from: protected */
        @NotNull
        public List<String> e(@NotNull String name) {
            kotlin.jvm.internal.k.f(name, "name");
            ArrayList names = this.a.i;
            ArrayList values = this.a.j;
            int size = this.a.i.size();
            int firstIndex = -1;
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                } else if (kotlin.text.w.y((String) names.get(i), name, true)) {
                    firstIndex = i;
                    break;
                } else {
                    i++;
                }
            }
            if (firstIndex == -1) {
                return q.g();
            }
            int secondIndex = -1;
            int i2 = firstIndex;
            while (true) {
                if (i2 >= size) {
                    break;
                } else if (kotlin.text.w.y((String) names.get(i2), name, true)) {
                    secondIndex = i2;
                    break;
                } else {
                    i2++;
                }
            }
            if (secondIndex == -1) {
                return p.b(values.get(firstIndex));
            }
            ArrayList result = new ArrayList((size - secondIndex) + 1);
            result.add(values.get(firstIndex));
            result.add(values.get(secondIndex));
            for (int i3 = secondIndex; i3 < size; i3++) {
                if (kotlin.text.w.y((String) names.get(i3), name, true)) {
                    result.add(values.get(i3));
                }
            }
            return result;
        }
    }

    @NotNull
    public io.ktor.response.f getHeaders() {
        return this.k;
    }

    /* Debug info: failed to restart local var, previous not found, register: 5 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0058 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object r(@org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super io.ktor.utils.io.l> r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof io.ktor.server.cio.e.i
            if (r0 == 0) goto L_0x0013
            r0 = r6
            io.ktor.server.cio.e$i r0 = (io.ktor.server.cio.e.i) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.server.cio.e$i r0 = new io.ktor.server.cio.e$i
            r0.<init>(r5, r6)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x003c;
                case 1: goto L_0x0034;
                case 2: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            java.lang.Object r2 = r0.L$0
            io.ktor.server.cio.e r2 = (io.ktor.server.cio.e) r2
            kotlin.p.b(r1)
            r4 = r1
            goto L_0x005a
        L_0x0034:
            java.lang.Object r3 = r0.L$0
            io.ktor.server.cio.e r3 = (io.ktor.server.cio.e) r3
            kotlin.p.b(r1)
            goto L_0x004d
        L_0x003c:
            kotlin.p.b(r1)
            r3 = 0
            r0.L$0 = r5
            r4 = 1
            r0.label = r4
            java.lang.Object r3 = r5.z(r3, r0)
            if (r3 != r2) goto L_0x004c
            return r2
        L_0x004c:
            r3 = r5
        L_0x004d:
            r0.L$0 = r3
            r4 = 2
            r0.label = r4
            java.lang.Object r4 = r3.y(r0)
            if (r4 != r2) goto L_0x0059
            return r2
        L_0x0059:
            r2 = r3
        L_0x005a:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.cio.e.r(kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 10 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v3, resolved type: io.ktor.http.content.j$c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: io.ktor.http.content.j$c} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0084, code lost:
        r2 = r9.m;
        r3 = r9.l;
        r4 = r9.n;
        r5 = r9.o;
        r0.L$0 = r9;
        r0.L$1 = r11;
        r0.label = 2;
        r1 = r11.g(r2, r3, r4, r5, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0099, code lost:
        if (r1 != r8) goto L_0x009c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x009b, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x009c, code lost:
        r2 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x009d, code lost:
        r1 = (kotlinx.coroutines.z1) r1;
        r1.t(new io.ktor.server.cio.e.h(r2));
        r0.L$0 = r2;
        r0.L$1 = r11;
        r0.L$2 = r1;
        r0.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00b4, code lost:
        if (r1.J(r0) != r8) goto L_0x00b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00b6, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b9, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object o(@org.jetbrains.annotations.NotNull io.ktor.http.content.j.c r11, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r12) {
        /*
            r10 = this;
            boolean r0 = r12 instanceof io.ktor.server.cio.e.g
            if (r0 == 0) goto L_0x0013
            r0 = r12
            io.ktor.server.cio.e$g r0 = (io.ktor.server.cio.e.g) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.server.cio.e$g r0 = new io.ktor.server.cio.e$g
            r0.<init>(r10, r12)
        L_0x0018:
            java.lang.Object r7 = r0.result
            java.lang.Object r8 = kotlin.coroutines.intrinsics.c.d()
            int r1 = r0.label
            switch(r1) {
                case 0: goto L_0x005c;
                case 1: goto L_0x004e;
                case 2: goto L_0x003f;
                case 3: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            r1 = 0
            java.lang.Object r2 = r0.L$2
            r1 = r2
            kotlinx.coroutines.z1 r1 = (kotlinx.coroutines.z1) r1
            java.lang.Object r2 = r0.L$1
            r11 = r2
            io.ktor.http.content.j$c r11 = (io.ktor.http.content.j.c) r11
            java.lang.Object r2 = r0.L$0
            io.ktor.server.cio.e r2 = (io.ktor.server.cio.e) r2
            kotlin.p.b(r7)
            goto L_0x00b7
        L_0x003f:
            java.lang.Object r1 = r0.L$1
            r11 = r1
            io.ktor.http.content.j$c r11 = (io.ktor.http.content.j.c) r11
            java.lang.Object r1 = r0.L$0
            io.ktor.server.cio.e r1 = (io.ktor.server.cio.e) r1
            kotlin.p.b(r7)
            r2 = r1
            r1 = r7
            goto L_0x009d
        L_0x004e:
            java.lang.Object r1 = r0.L$1
            r11 = r1
            io.ktor.http.content.j$c r11 = (io.ktor.http.content.j.c) r11
            java.lang.Object r1 = r0.L$0
            io.ktor.server.cio.e r1 = (io.ktor.server.cio.e) r1
            kotlin.p.b(r7)
            r9 = r1
            goto L_0x0084
        L_0x005c:
            kotlin.p.b(r7)
            kotlinx.coroutines.w<java.lang.Boolean> r1 = r10.p
            if (r1 == 0) goto L_0x00ba
            r2 = 1
            java.lang.Boolean r3 = kotlin.coroutines.jvm.internal.b.a(r2)
            boolean r1 = r1.x(r3)
            java.lang.Boolean r1 = kotlin.coroutines.jvm.internal.b.a(r1)
            if (r1 == 0) goto L_0x00ba
            r1.booleanValue()
            r1 = 0
            r0.L$0 = r10
            r0.L$1 = r11
            r0.label = r2
            java.lang.Object r1 = r10.z(r1, r0)
            if (r1 != r8) goto L_0x0083
            return r8
        L_0x0083:
            r9 = r10
        L_0x0084:
            io.ktor.utils.io.i r2 = r9.m
            io.ktor.utils.io.l r3 = r9.l
            kotlin.coroutines.g r4 = r9.n
            kotlin.coroutines.g r5 = r9.o
            r0.L$0 = r9
            r0.L$1 = r11
            r1 = 2
            r0.label = r1
            r1 = r11
            r6 = r0
            java.lang.Object r1 = r1.g(r2, r3, r4, r5, r6)
            if (r1 != r8) goto L_0x009c
            return r8
        L_0x009c:
            r2 = r9
        L_0x009d:
            kotlinx.coroutines.z1 r1 = (kotlinx.coroutines.z1) r1
            io.ktor.server.cio.e$h r3 = new io.ktor.server.cio.e$h
            r3.<init>(r2)
            r1.t(r3)
            r0.L$0 = r2
            r0.L$1 = r11
            r0.L$2 = r1
            r3 = 3
            r0.label = r3
            java.lang.Object r3 = r1.J(r0)
            if (r3 != r8) goto L_0x00b7
            return r8
        L_0x00b7:
            kotlin.x r3 = kotlin.x.a
            return r3
        L_0x00ba:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "Unable to perform upgrade as it is not requested by the client: request should have Upgrade and Connection headers filled properly"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.cio.e.o(io.ktor.http.content.j$c, kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 8 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v3, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v5, resolved type: byte[]} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x006c, code lost:
        r0.L$0 = r3;
        r0.L$1 = r9;
        r0.label = 2;
        r5 = r3.y(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0077, code lost:
        if (r5 != r2) goto L_0x007a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0079, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x007a, code lost:
        r5 = (io.ktor.utils.io.l) r5;
        r6 = kotlinx.coroutines.d1.d();
        r7 = new io.ktor.server.cio.e.d(r5, r9, (kotlin.coroutines.d) null);
        r0.L$0 = r3;
        r0.L$1 = r9;
        r0.L$2 = r5;
        r0.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0092, code lost:
        if (kotlinx.coroutines.h.g(r6, r7, r0) != r2) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0094, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0095, code lost:
        r2 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0098, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object g(@org.jetbrains.annotations.NotNull byte[] r9, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof io.ktor.server.cio.e.c
            if (r0 == 0) goto L_0x0013
            r0 = r10
            io.ktor.server.cio.e$c r0 = (io.ktor.server.cio.e.c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.server.cio.e$c r0 = new io.ktor.server.cio.e$c
            r0.<init>(r8, r10)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x005a;
                case 1: goto L_0x004d;
                case 2: goto L_0x003f;
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
            java.lang.Object r3 = r0.L$2
            r2 = r3
            io.ktor.utils.io.l r2 = (io.ktor.utils.io.l) r2
            java.lang.Object r3 = r0.L$1
            r9 = r3
            byte[] r9 = (byte[]) r9
            java.lang.Object r3 = r0.L$0
            io.ktor.server.cio.e r3 = (io.ktor.server.cio.e) r3
            kotlin.p.b(r1)
            goto L_0x0096
        L_0x003f:
            java.lang.Object r3 = r0.L$1
            r9 = r3
            byte[] r9 = (byte[]) r9
            java.lang.Object r3 = r0.L$0
            io.ktor.server.cio.e r3 = (io.ktor.server.cio.e) r3
            kotlin.p.b(r1)
            r5 = r1
            goto L_0x007a
        L_0x004d:
            java.lang.Object r3 = r0.L$1
            r9 = r3
            byte[] r9 = (byte[]) r9
            java.lang.Object r3 = r0.L$0
            io.ktor.server.cio.e r3 = (io.ktor.server.cio.e) r3
            kotlin.p.b(r1)
            goto L_0x006c
        L_0x005a:
            kotlin.p.b(r1)
            r0.L$0 = r8
            r0.L$1 = r9
            r3 = 1
            r0.label = r3
            java.lang.Object r3 = r8.z(r3, r0)
            if (r3 != r2) goto L_0x006b
            return r2
        L_0x006b:
            r3 = r8
        L_0x006c:
            r0.L$0 = r3
            r0.L$1 = r9
            r5 = 2
            r0.label = r5
            java.lang.Object r5 = r3.y(r0)
            if (r5 != r2) goto L_0x007a
            return r2
        L_0x007a:
            io.ktor.utils.io.l r5 = (io.ktor.utils.io.l) r5
            kotlinx.coroutines.i0 r6 = kotlinx.coroutines.d1.d()
            io.ktor.server.cio.e$d r7 = new io.ktor.server.cio.e$d
            r7.<init>(r5, r9, r4)
            r0.L$0 = r3
            r0.L$1 = r9
            r0.L$2 = r5
            r4 = 3
            r0.label = r4
            java.lang.Object r4 = kotlinx.coroutines.h.g(r6, r7, r0)
            if (r4 != r2) goto L_0x0095
            return r2
        L_0x0095:
            r2 = r5
        L_0x0096:
            kotlin.x r4 = kotlin.x.a
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.cio.e.g(byte[], kotlin.coroutines.d):java.lang.Object");
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.server.cio.CIOApplicationResponse$respondFromBytes$2", f = "CIOApplicationResponse.kt", l = {100}, m = "invokeSuspend")
    /* compiled from: CIOApplicationResponse.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.l implements kotlin.jvm.functions.p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ byte[] $bytes;
        final /* synthetic */ l $channel;
        Object L$0;
        int label;
        private o0 p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(l lVar, byte[] bArr, kotlin.coroutines.d dVar) {
            super(2, dVar);
            this.$channel = lVar;
            this.$bytes = bArr;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            kotlin.jvm.internal.k.f(dVar, "completion");
            d dVar2 = new d(this.$channel, this.$bytes, dVar);
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
                    l lVar = this.$channel;
                    byte[] bArr = this.$bytes;
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
            m.a(this.$channel);
            return x.a;
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 4 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object k(@org.jetbrains.annotations.NotNull io.ktor.http.content.j.b r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof io.ktor.server.cio.e.C0268e
            if (r0 == 0) goto L_0x0013
            r0 = r6
            io.ktor.server.cio.e$e r0 = (io.ktor.server.cio.e.C0268e) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.server.cio.e$e r0 = new io.ktor.server.cio.e$e
            r0.<init>(r4, r6)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0038;
                case 1: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            java.lang.Object r2 = r0.L$1
            r5 = r2
            io.ktor.http.content.j$b r5 = (io.ktor.http.content.j.b) r5
            java.lang.Object r2 = r0.L$0
            io.ktor.server.cio.e r2 = (io.ktor.server.cio.e) r2
            kotlin.p.b(r1)
            goto L_0x004a
        L_0x0038:
            kotlin.p.b(r1)
            r0.L$0 = r4
            r0.L$1 = r5
            r3 = 1
            r0.label = r3
            java.lang.Object r3 = r4.z(r3, r0)
            if (r3 != r2) goto L_0x0049
            return r2
        L_0x0049:
            r2 = r4
        L_0x004a:
            io.ktor.utils.io.l r3 = r2.l
            io.ktor.utils.io.m.a(r3)
            kotlin.x r3 = kotlin.x.a
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.cio.e.k(io.ktor.http.content.j$b, kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 6 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: io.ktor.http.content.j} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object m(@org.jetbrains.annotations.NotNull io.ktor.http.content.j r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof io.ktor.server.cio.e.f
            if (r0 == 0) goto L_0x0013
            r0 = r8
            io.ktor.server.cio.e$f r0 = (io.ktor.server.cio.e.f) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.server.cio.e$f r0 = new io.ktor.server.cio.e$f
            r0.<init>(r6, r8)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0045;
                case 1: goto L_0x0038;
                case 2: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            java.lang.Object r2 = r0.L$1
            r7 = r2
            io.ktor.http.content.j r7 = (io.ktor.http.content.j) r7
            java.lang.Object r2 = r0.L$0
            io.ktor.server.cio.e r2 = (io.ktor.server.cio.e) r2
            kotlin.p.b(r1)
            goto L_0x0075
        L_0x0038:
            java.lang.Object r3 = r0.L$1
            r7 = r3
            io.ktor.http.content.j r7 = (io.ktor.http.content.j) r7
            java.lang.Object r3 = r0.L$0
            io.ktor.server.cio.e r3 = (io.ktor.server.cio.e) r3
            kotlin.p.b(r1)
            goto L_0x0057
        L_0x0045:
            kotlin.p.b(r1)
            r0.L$0 = r6
            r0.L$1 = r7
            r3 = 1
            r0.label = r3
            java.lang.Object r3 = super.m(r7, r0)
            if (r3 != r2) goto L_0x0056
            return r2
        L_0x0056:
            r3 = r6
        L_0x0057:
            io.ktor.utils.io.l r4 = r3.chunkedChannel
            if (r4 == 0) goto L_0x0062
            boolean r4 = io.ktor.utils.io.m.a(r4)
            kotlin.coroutines.jvm.internal.b.a(r4)
        L_0x0062:
            kotlinx.coroutines.z1 r4 = r3.chunkedJob
            if (r4 == 0) goto L_0x0076
            r0.L$0 = r3
            r0.L$1 = r7
            r5 = 2
            r0.label = r5
            java.lang.Object r4 = r4.J(r0)
            if (r4 != r2) goto L_0x0074
            return r2
        L_0x0074:
            r2 = r3
        L_0x0075:
            r3 = r2
        L_0x0076:
            kotlin.x r2 = kotlin.x.a
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.cio.e.m(io.ktor.http.content.j, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: protected */
    public void s(@NotNull v statusCode) {
        kotlin.jvm.internal.k.f(statusCode, "statusCode");
        this.h = statusCode;
    }

    /* Debug info: failed to restart local var, previous not found, register: 9 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: io.ktor.http.cio.n} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x009d, code lost:
        if (r10 != false) goto L_0x00a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r3.l.flush();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a4, code lost:
        r2.d();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00aa, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object z(boolean r10, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof io.ktor.server.cio.e.j
            if (r0 == 0) goto L_0x0013
            r0 = r11
            io.ktor.server.cio.e$j r0 = (io.ktor.server.cio.e.j) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.server.cio.e$j r0 = new io.ktor.server.cio.e$j
            r0.<init>(r9, r11)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x003e;
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
            java.lang.Object r3 = r0.L$1
            r2 = r3
            io.ktor.http.cio.n r2 = (io.ktor.http.cio.n) r2
            boolean r10 = r0.Z$0
            java.lang.Object r3 = r0.L$0
            io.ktor.server.cio.e r3 = (io.ktor.server.cio.e) r3
            kotlin.p.b(r1)     // Catch:{ all -> 0x003b }
            goto L_0x009d
        L_0x003b:
            r4 = move-exception
            goto L_0x00ae
        L_0x003e:
            kotlin.p.b(r1)
            io.ktor.http.cio.n r3 = new io.ktor.http.cio.n
            r3.<init>()
            java.lang.String r4 = "HTTP/1.1"
            io.ktor.http.v r5 = r9.h     // Catch:{ all -> 0x00ab }
            int r5 = r5.b0()     // Catch:{ all -> 0x00ab }
            io.ktor.http.v r6 = r9.h     // Catch:{ all -> 0x00ab }
            java.lang.String r6 = r6.a0()     // Catch:{ all -> 0x00ab }
            r3.e(r4, r5, r6)     // Catch:{ all -> 0x00ab }
            r4 = 0
            java.util.ArrayList<java.lang.String> r5 = r9.i     // Catch:{ all -> 0x00ab }
            int r5 = r5.size()     // Catch:{ all -> 0x00ab }
        L_0x005f:
            if (r4 >= r5) goto L_0x0082
            java.util.ArrayList<java.lang.String> r6 = r9.i     // Catch:{ all -> 0x00ab }
            java.lang.Object r6 = r6.get(r4)     // Catch:{ all -> 0x00ab }
            java.lang.String r7 = "headersNames[i]"
            kotlin.jvm.internal.k.b(r6, r7)     // Catch:{ all -> 0x00ab }
            java.lang.CharSequence r6 = (java.lang.CharSequence) r6     // Catch:{ all -> 0x00ab }
            java.util.ArrayList<java.lang.String> r7 = r9.j     // Catch:{ all -> 0x00ab }
            java.lang.Object r7 = r7.get(r4)     // Catch:{ all -> 0x00ab }
            java.lang.String r8 = "headerValues[i]"
            kotlin.jvm.internal.k.b(r7, r8)     // Catch:{ all -> 0x00ab }
            java.lang.CharSequence r7 = (java.lang.CharSequence) r7     // Catch:{ all -> 0x00ab }
            r3.c(r6, r7)     // Catch:{ all -> 0x00ab }
            int r4 = r4 + 1
            goto L_0x005f
        L_0x0082:
            r3.b()     // Catch:{ all -> 0x00ab }
            io.ktor.utils.io.l r4 = r9.l     // Catch:{ all -> 0x00ab }
            io.ktor.utils.io.core.q r5 = r3.a()     // Catch:{ all -> 0x00ab }
            r0.L$0 = r9     // Catch:{ all -> 0x00ab }
            r0.Z$0 = r10     // Catch:{ all -> 0x00ab }
            r0.L$1 = r3     // Catch:{ all -> 0x00ab }
            r6 = 1
            r0.label = r6     // Catch:{ all -> 0x00ab }
            java.lang.Object r4 = r4.x(r5, r0)     // Catch:{ all -> 0x00ab }
            if (r4 != r2) goto L_0x009b
            return r2
        L_0x009b:
            r2 = r3
            r3 = r9
        L_0x009d:
            if (r10 != 0) goto L_0x00a4
            io.ktor.utils.io.l r4 = r3.l     // Catch:{ all -> 0x003b }
            r4.flush()     // Catch:{ all -> 0x003b }
        L_0x00a4:
            r2.d()
            kotlin.x r4 = kotlin.x.a
            return r4
        L_0x00ab:
            r4 = move-exception
            r2 = r3
            r3 = r9
        L_0x00ae:
            r2.d()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.cio.e.z(boolean, kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 7 */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object y(@org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super io.ktor.utils.io.l> r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof io.ktor.server.cio.e.b
            if (r0 == 0) goto L_0x0013
            r0 = r8
            io.ktor.server.cio.e$b r0 = (io.ktor.server.cio.e.b) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.server.cio.e$b r0 = new io.ktor.server.cio.e$b
            r0.<init>(r7, r8)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0037;
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
            boolean r2 = r0.Z$0
            java.lang.Object r3 = r0.L$0
            io.ktor.server.cio.e r3 = (io.ktor.server.cio.e) r3
            kotlin.p.b(r1)
            r4 = r1
            goto L_0x0069
        L_0x0037:
            kotlin.p.b(r1)
            io.ktor.response.f r3 = r7.getHeaders()
            io.ktor.http.s r4 = io.ktor.http.s.V0
            java.lang.String r4 = r4.A()
            java.lang.String r3 = r3.d(r4)
            java.lang.String r4 = "chunked"
            boolean r3 = kotlin.jvm.internal.k.a(r3, r4)
            if (r3 != 0) goto L_0x0053
            io.ktor.utils.io.l r2 = r7.l
            return r2
        L_0x0053:
            io.ktor.utils.io.l r4 = r7.l
            kotlinx.coroutines.i0 r5 = kotlinx.coroutines.d1.d()
            r0.L$0 = r7
            r0.Z$0 = r3
            r6 = 1
            r0.label = r6
            java.lang.Object r4 = io.ktor.http.cio.c.d(r4, r5, r0)
            if (r4 != r2) goto L_0x0067
            return r2
        L_0x0067:
            r2 = r3
            r3 = r7
        L_0x0069:
            io.ktor.utils.io.v r4 = (io.ktor.utils.io.v) r4
            io.ktor.utils.io.l r5 = r4.getChannel()
            r3.chunkedChannel = r5
            r3.chunkedJob = r4
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.cio.e.y(kotlin.coroutines.d):java.lang.Object");
    }
}
