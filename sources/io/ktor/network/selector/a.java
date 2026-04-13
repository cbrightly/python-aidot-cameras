package io.ktor.network.selector;

import java.io.Closeable;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.o;
import kotlin.x;
import kotlinx.coroutines.j;
import kotlinx.coroutines.n0;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ActorSelectorManager.kt */
public final class a extends SelectorManagerSupport implements Closeable, o0 {
    /* access modifiers changed from: private */
    public volatile boolean closed;
    private volatile boolean inSelect;
    private final AtomicLong q = new AtomicLong();
    /* access modifiers changed from: private */
    public volatile Selector selectorRef;
    private final b<x, kotlin.coroutines.d<x>> x = new b<>();
    /* access modifiers changed from: private */
    public final h<k> y = new h<>();
    @NotNull
    private final g z;

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.network.selector.ActorSelectorManager", f = "ActorSelectorManager.kt", l = {72, 77, 87}, m = "process")
    /* compiled from: ActorSelectorManager.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.W((h<k>) null, (Selector) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.network.selector.ActorSelectorManager", f = "ActorSelectorManager.kt", l = {154}, m = "receiveOrNull")
    /* compiled from: ActorSelectorManager.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.u0((h<k>) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.network.selector.ActorSelectorManager", f = "ActorSelectorManager.kt", l = {164}, m = "receiveOrNullSuspend")
    /* compiled from: ActorSelectorManager.kt */
    public static final class e extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.F0((h<k>) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.network.selector.ActorSelectorManager", f = "ActorSelectorManager.kt", l = {209}, m = "select")
    /* compiled from: ActorSelectorManager.kt */
    public static final class f extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.P0((Selector) null, this);
        }
    }

    public a(@NotNull g context) {
        k.f(context, "context");
        this.z = context.plus(new n0("selector"));
        z1 unused = j.d(this, (g) null, (q0) null, new C0254a(this, (kotlin.coroutines.d) null), 3, (Object) null);
    }

    @NotNull
    public g getCoroutineContext() {
        return this.z;
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.network.selector.ActorSelectorManager$1", f = "ActorSelectorManager.kt", l = {45}, m = "invokeSuspend")
    /* renamed from: io.ktor.network.selector.a$a  reason: collision with other inner class name */
    /* compiled from: ActorSelectorManager.kt */
    public static final class C0254a extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        Object L$0;
        Object L$1;
        int label;
        private o0 p$;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0254a(a aVar, kotlin.coroutines.d dVar) {
            super(2, dVar);
            this.this$0 = aVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            k.f(dVar, "completion");
            C0254a aVar = new C0254a(this.this$0, dVar);
            o0 o0Var = (o0) obj;
            aVar.p$ = (o0) obj;
            return aVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((C0254a) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v9, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: java.nio.channels.spi.AbstractSelector} */
        /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
            io.ktor.network.selector.a.P(r8.this$0, true);
            io.ktor.network.selector.a.J(r8.this$0).b();
            io.ktor.network.selector.a.T(r8.this$0, (java.nio.channels.Selector) null);
            r2 = r8.this$0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x006a, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00be, code lost:
            r4 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
            io.ktor.network.selector.a.P(r8.this$0, true);
            io.ktor.network.selector.a.J(r8.this$0).b();
            io.ktor.network.selector.a.T(r8.this$0, (java.nio.channels.Selector) null);
            r8.this$0.n(r0, (java.lang.Throwable) null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x00d7, code lost:
            throw r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x00d8, code lost:
            r0.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x00db, code lost:
            throw r2;
         */
        /* JADX WARNING: Exception block dominator not found, dom blocks: [B:16:0x0051, B:22:0x0070] */
        /* JADX WARNING: Multi-variable type inference failed */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r9) {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r1 = r8.label
                r2 = 1
                r3 = 0
                switch(r1) {
                    case 0: goto L_0x0025;
                    case 1: goto L_0x0013;
                    default: goto L_0x000b;
                }
            L_0x000b:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0013:
                r0 = r3
                r1 = r3
                java.lang.Object r4 = r8.L$1
                r0 = r4
                java.nio.channels.spi.AbstractSelector r0 = (java.nio.channels.spi.AbstractSelector) r0
                java.lang.Object r4 = r8.L$0
                r1 = r4
                kotlinx.coroutines.o0 r1 = (kotlinx.coroutines.o0) r1
                kotlin.p.b(r9)     // Catch:{ all -> 0x0023 }
                goto L_0x0051
            L_0x0023:
                r4 = move-exception
                goto L_0x0070
            L_0x0025:
                kotlin.p.b(r9)
                kotlinx.coroutines.o0 r1 = r8.p$
                io.ktor.network.selector.a r4 = r8.this$0
                java.nio.channels.spi.SelectorProvider r4 = r4.a()
                java.nio.channels.spi.AbstractSelector r4 = r4.openSelector()
                if (r4 == 0) goto L_0x00dc
                io.ktor.network.selector.a r5 = r8.this$0
                r5.selectorRef = r4
                io.ktor.network.selector.a r5 = r8.this$0     // Catch:{ all -> 0x006c }
                io.ktor.network.selector.h r6 = r5.y     // Catch:{ all -> 0x006c }
                r8.L$0 = r1     // Catch:{ all -> 0x006c }
                r8.L$1 = r4     // Catch:{ all -> 0x006c }
                r8.label = r2     // Catch:{ all -> 0x006c }
                java.lang.Object r5 = r5.W(r6, r4, r8)     // Catch:{ all -> 0x006c }
                if (r5 != r0) goto L_0x0050
                return r0
            L_0x0050:
                r0 = r4
            L_0x0051:
                io.ktor.network.selector.a r4 = r8.this$0     // Catch:{ all -> 0x006a }
                r4.closed = r2     // Catch:{ all -> 0x006a }
                io.ktor.network.selector.a r2 = r8.this$0     // Catch:{ all -> 0x006a }
                io.ktor.network.selector.h r2 = r2.y     // Catch:{ all -> 0x006a }
                r2.b()     // Catch:{ all -> 0x006a }
                io.ktor.network.selector.a r2 = r8.this$0     // Catch:{ all -> 0x006a }
                r2.selectorRef = r3     // Catch:{ all -> 0x006a }
                io.ktor.network.selector.a r2 = r8.this$0     // Catch:{ all -> 0x006a }
            L_0x0066:
                r2.n(r0, r3)     // Catch:{ all -> 0x006a }
                goto L_0x0099
            L_0x006a:
                r2 = move-exception
                goto L_0x00d8
            L_0x006c:
                r0 = move-exception
                r7 = r4
                r4 = r0
                r0 = r7
            L_0x0070:
                io.ktor.network.selector.a r5 = r8.this$0     // Catch:{ all -> 0x00be }
                r5.closed = r2     // Catch:{ all -> 0x00be }
                io.ktor.network.selector.a r5 = r8.this$0     // Catch:{ all -> 0x00be }
                io.ktor.network.selector.h r5 = r5.y     // Catch:{ all -> 0x00be }
                r5.b()     // Catch:{ all -> 0x00be }
                io.ktor.network.selector.a r5 = r8.this$0     // Catch:{ all -> 0x00be }
                r5.n(r0, r4)     // Catch:{ all -> 0x00be }
                io.ktor.network.selector.a r4 = r8.this$0     // Catch:{ all -> 0x006a }
                r4.closed = r2     // Catch:{ all -> 0x006a }
                io.ktor.network.selector.a r2 = r8.this$0     // Catch:{ all -> 0x006a }
                io.ktor.network.selector.h r2 = r2.y     // Catch:{ all -> 0x006a }
                r2.b()     // Catch:{ all -> 0x006a }
                io.ktor.network.selector.a r2 = r8.this$0     // Catch:{ all -> 0x006a }
                r2.selectorRef = r3     // Catch:{ all -> 0x006a }
                io.ktor.network.selector.a r2 = r8.this$0     // Catch:{ all -> 0x006a }
                goto L_0x0066
            L_0x0099:
                io.ktor.network.selector.a r2 = r8.this$0     // Catch:{ all -> 0x006a }
                io.ktor.network.selector.h r2 = r2.y     // Catch:{ all -> 0x006a }
                java.lang.Object r2 = r2.d()     // Catch:{ all -> 0x006a }
                io.ktor.network.selector.k r2 = (io.ktor.network.selector.k) r2     // Catch:{ all -> 0x006a }
                if (r2 == 0) goto L_0x00b6
                io.ktor.network.selector.a r3 = r8.this$0     // Catch:{ all -> 0x006a }
                kotlinx.coroutines.channels.ClosedSendChannelException r4 = new kotlinx.coroutines.channels.ClosedSendChannelException     // Catch:{ all -> 0x006a }
                java.lang.String r5 = "Failed to apply interest: selector closed"
                r4.<init>(r5)     // Catch:{ all -> 0x006a }
                r3.m(r2, r4)     // Catch:{ all -> 0x006a }
                goto L_0x0099
            L_0x00b6:
                r0.close()
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x00be:
                r4 = move-exception
                io.ktor.network.selector.a r5 = r8.this$0     // Catch:{ all -> 0x006a }
                r5.closed = r2     // Catch:{ all -> 0x006a }
                io.ktor.network.selector.a r2 = r8.this$0     // Catch:{ all -> 0x006a }
                io.ktor.network.selector.h r2 = r2.y     // Catch:{ all -> 0x006a }
                r2.b()     // Catch:{ all -> 0x006a }
                io.ktor.network.selector.a r2 = r8.this$0     // Catch:{ all -> 0x006a }
                r2.selectorRef = r3     // Catch:{ all -> 0x006a }
                io.ktor.network.selector.a r2 = r8.this$0     // Catch:{ all -> 0x006a }
                r2.n(r0, r3)     // Catch:{ all -> 0x006a }
                throw r4     // Catch:{ all -> 0x006a }
            L_0x00d8:
                r0.close()
                throw r2
            L_0x00dc:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r2 = "openSelector() = null"
                java.lang.String r2 = r2.toString()
                r0.<init>(r2)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.network.selector.a.C0254a.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 9 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: java.nio.channels.Selector} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: io.ktor.network.selector.h<io.ktor.network.selector.k>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v17, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v7, resolved type: java.nio.channels.Selector} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v18, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v7, resolved type: io.ktor.network.selector.h<io.ktor.network.selector.k>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v20, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: java.nio.channels.Selector} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v9, resolved type: io.ktor.network.selector.h<io.ktor.network.selector.k>} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x007b, code lost:
        if (r3.closed != false) goto L_0x0127;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x007d, code lost:
        r3.o0(r10, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0084, code lost:
        if (r3.s() <= 0) goto L_0x00dd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0086, code lost:
        r0.L$0 = r3;
        r0.L$1 = r10;
        r0.L$2 = r11;
        r0.label = 1;
        r6 = r3.P0(r11, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0093, code lost:
        if (r6 != r2) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0095, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0096, code lost:
        r8 = r2;
        r2 = r1;
        r1 = r6;
        r6 = r3;
        r3 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00a1, code lost:
        if (((java.lang.Number) r1).intValue() <= 0) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00a3, code lost:
        r1 = r11.selectedKeys();
        kotlin.jvm.internal.k.b(r1, "selector.selectedKeys()");
        r7 = r11.keys();
        kotlin.jvm.internal.k.b(r7, "selector.keys()");
        r6.v(r1, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00b5, code lost:
        r1 = r10.d();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00bb, code lost:
        if (r1 == null) goto L_0x00c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00bd, code lost:
        r6.i(r11, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00c0, code lost:
        r1 = r2;
        r2 = r3;
        r3 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00c5, code lost:
        r0.L$0 = r6;
        r0.L$1 = r10;
        r0.L$2 = r11;
        r0.L$3 = r1;
        r0.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00d4, code lost:
        if (kotlinx.coroutines.e3.a(r0) != r3) goto L_0x00d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00d6, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00d7, code lost:
        r8 = r3;
        r3 = r1;
        r1 = r2;
        r2 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00db, code lost:
        r3 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00e1, code lost:
        if (r3.r() <= 0) goto L_0x0103;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00e3, code lost:
        r11.selectNow();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ea, code lost:
        if (r3.s() <= 0) goto L_0x00fe;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ec, code lost:
        r6 = r11.selectedKeys();
        kotlin.jvm.internal.k.b(r6, "selector.selectedKeys()");
        r7 = r11.keys();
        kotlin.jvm.internal.k.b(r7, "selector.keys()");
        r3.v(r6, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00fe, code lost:
        r3.z(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0103, code lost:
        r0.L$0 = r3;
        r0.L$1 = r10;
        r0.L$2 = r11;
        r0.label = 3;
        r6 = r3.u0(r10, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0110, code lost:
        if (r6 != r2) goto L_0x0113;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0112, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0113, code lost:
        r8 = r2;
        r2 = r1;
        r1 = r6;
        r6 = r3;
        r3 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0118, code lost:
        r1 = (io.ktor.network.selector.k) r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x011a, code lost:
        if (r1 == null) goto L_0x0125;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x011c, code lost:
        r6.i(r11, r1);
        r1 = r2;
        r2 = r3;
        r3 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0125, code lost:
        r1 = r2;
        r3 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0129, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object W(@org.jetbrains.annotations.NotNull io.ktor.network.selector.h<io.ktor.network.selector.k> r10, @org.jetbrains.annotations.NotNull java.nio.channels.Selector r11, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r12) {
        /*
            r9 = this;
            boolean r0 = r12 instanceof io.ktor.network.selector.a.c
            if (r0 == 0) goto L_0x0013
            r0 = r12
            io.ktor.network.selector.a$c r0 = (io.ktor.network.selector.a.c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.network.selector.a$c r0 = new io.ktor.network.selector.a$c
            r0.<init>(r9, r12)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            java.lang.String r4 = "selector.keys()"
            java.lang.String r5 = "selector.selectedKeys()"
            switch(r3) {
                case 0: goto L_0x0075;
                case 1: goto L_0x0060;
                case 2: goto L_0x0047;
                case 3: goto L_0x0031;
                default: goto L_0x0029;
            }
        L_0x0029:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0031:
            java.lang.Object r3 = r0.L$2
            r11 = r3
            java.nio.channels.Selector r11 = (java.nio.channels.Selector) r11
            java.lang.Object r3 = r0.L$1
            r10 = r3
            io.ktor.network.selector.h r10 = (io.ktor.network.selector.h) r10
            java.lang.Object r3 = r0.L$0
            io.ktor.network.selector.a r3 = (io.ktor.network.selector.a) r3
            kotlin.p.b(r1)
            r6 = r3
            r3 = r2
            r2 = r1
            goto L_0x0118
        L_0x0047:
            r3 = 0
            java.lang.Object r6 = r0.L$3
            r3 = r6
            io.ktor.network.selector.k r3 = (io.ktor.network.selector.k) r3
            java.lang.Object r6 = r0.L$2
            r11 = r6
            java.nio.channels.Selector r11 = (java.nio.channels.Selector) r11
            java.lang.Object r6 = r0.L$1
            r10 = r6
            io.ktor.network.selector.h r10 = (io.ktor.network.selector.h) r10
            java.lang.Object r6 = r0.L$0
            io.ktor.network.selector.a r6 = (io.ktor.network.selector.a) r6
            kotlin.p.b(r1)
            goto L_0x00db
        L_0x0060:
            java.lang.Object r3 = r0.L$2
            r11 = r3
            java.nio.channels.Selector r11 = (java.nio.channels.Selector) r11
            java.lang.Object r3 = r0.L$1
            r10 = r3
            io.ktor.network.selector.h r10 = (io.ktor.network.selector.h) r10
            java.lang.Object r3 = r0.L$0
            io.ktor.network.selector.a r3 = (io.ktor.network.selector.a) r3
            kotlin.p.b(r1)
            r6 = r3
            r3 = r2
            r2 = r1
            goto L_0x009b
        L_0x0075:
            kotlin.p.b(r1)
            r3 = r9
        L_0x0079:
            boolean r6 = r3.closed
            if (r6 != 0) goto L_0x0127
            r3.o0(r10, r11)
            int r6 = r3.s()
            if (r6 <= 0) goto L_0x00dd
            r0.L$0 = r3
            r0.L$1 = r10
            r0.L$2 = r11
            r6 = 1
            r0.label = r6
            java.lang.Object r6 = r3.P0(r11, r0)
            if (r6 != r2) goto L_0x0096
            return r2
        L_0x0096:
            r8 = r2
            r2 = r1
            r1 = r6
            r6 = r3
            r3 = r8
        L_0x009b:
            java.lang.Number r1 = (java.lang.Number) r1
            int r1 = r1.intValue()
            if (r1 <= 0) goto L_0x00b5
            java.util.Set r1 = r11.selectedKeys()
            kotlin.jvm.internal.k.b(r1, r5)
            java.util.Set r7 = r11.keys()
            kotlin.jvm.internal.k.b(r7, r4)
            r6.v(r1, r7)
            goto L_0x00c0
        L_0x00b5:
            java.lang.Object r1 = r10.d()
            io.ktor.network.selector.k r1 = (io.ktor.network.selector.k) r1
            if (r1 == 0) goto L_0x00c5
            r6.i(r11, r1)
        L_0x00c0:
            r1 = r2
            r2 = r3
            r3 = r6
            goto L_0x0122
        L_0x00c5:
            r0.L$0 = r6
            r0.L$1 = r10
            r0.L$2 = r11
            r0.L$3 = r1
            r7 = 2
            r0.label = r7
            java.lang.Object r7 = kotlinx.coroutines.e3.a(r0)
            if (r7 != r3) goto L_0x00d7
            return r3
        L_0x00d7:
            r8 = r3
            r3 = r1
            r1 = r2
            r2 = r8
        L_0x00db:
            r3 = r6
            goto L_0x0122
        L_0x00dd:
            int r6 = r3.r()
            if (r6 <= 0) goto L_0x0103
            r11.selectNow()
            int r6 = r3.s()
            if (r6 <= 0) goto L_0x00fe
            java.util.Set r6 = r11.selectedKeys()
            kotlin.jvm.internal.k.b(r6, r5)
            java.util.Set r7 = r11.keys()
            kotlin.jvm.internal.k.b(r7, r4)
            r3.v(r6, r7)
            goto L_0x0122
        L_0x00fe:
            r6 = 0
            r3.z(r6)
            goto L_0x0122
        L_0x0103:
            r0.L$0 = r3
            r0.L$1 = r10
            r0.L$2 = r11
            r6 = 3
            r0.label = r6
            java.lang.Object r6 = r3.u0(r10, r0)
            if (r6 != r2) goto L_0x0113
            return r2
        L_0x0113:
            r8 = r2
            r2 = r1
            r1 = r6
            r6 = r3
            r3 = r8
        L_0x0118:
            io.ktor.network.selector.k r1 = (io.ktor.network.selector.k) r1
            if (r1 == 0) goto L_0x0125
            r6.i(r11, r1)
            r1 = r2
            r2 = r3
            r3 = r6
        L_0x0122:
            goto L_0x0079
        L_0x0125:
            r1 = r2
            r3 = r6
        L_0x0127:
            kotlin.x r2 = kotlin.x.a
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.network.selector.a.W(io.ktor.network.selector.h, java.nio.channels.Selector, kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 8 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v2, resolved type: java.nio.channels.Selector} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0071  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object P0(@org.jetbrains.annotations.NotNull java.nio.channels.Selector r9, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Integer> r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof io.ktor.network.selector.a.f
            if (r0 == 0) goto L_0x0013
            r0 = r10
            io.ktor.network.selector.a$f r0 = (io.ktor.network.selector.a.f) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.network.selector.a$f r0 = new io.ktor.network.selector.a$f
            r0.<init>(r8, r10)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x0040;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002c:
            r2 = 0
            r3 = r4
            java.lang.Object r5 = r0.L$2
            r2 = r5
            io.ktor.network.selector.a r2 = (io.ktor.network.selector.a) r2
            java.lang.Object r5 = r0.L$1
            r9 = r5
            java.nio.channels.Selector r9 = (java.nio.channels.Selector) r9
            java.lang.Object r5 = r0.L$0
            io.ktor.network.selector.a r5 = (io.ktor.network.selector.a) r5
            kotlin.p.b(r1)
            goto L_0x005a
        L_0x0040:
            kotlin.p.b(r1)
            r3 = 1
            r8.inSelect = r3
            r5 = r8
            r6 = 0
            r0.L$0 = r8
            r0.L$1 = r9
            r0.L$2 = r5
            r0.label = r3
            java.lang.Object r3 = kotlinx.coroutines.e3.a(r0)
            if (r3 != r2) goto L_0x0057
            return r2
        L_0x0057:
            r2 = r5
            r3 = r6
            r5 = r8
        L_0x005a:
            java.util.concurrent.atomic.AtomicLong r2 = r5.q
            long r2 = r2.get()
            r6 = 0
            int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r2 != 0) goto L_0x0071
            r2 = 500(0x1f4, double:2.47E-321)
            int r2 = r9.select(r2)
            r5.inSelect = r4
            goto L_0x007c
        L_0x0071:
            r5.inSelect = r4
            java.util.concurrent.atomic.AtomicLong r2 = r5.q
            r2.set(r6)
            int r2 = r9.selectNow()
        L_0x007c:
            java.lang.Integer r2 = kotlin.coroutines.jvm.internal.b.c(r2)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.network.selector.a.P0(java.nio.channels.Selector, kotlin.coroutines.d):java.lang.Object");
    }

    private final void a1() {
        Selector selector;
        if (this.q.incrementAndGet() == 1 && this.inSelect && (selector = this.selectorRef) != null) {
            selector.wakeup();
        }
    }

    private final void o0(h<k> mb, Selector selector) {
        while (true) {
            k selectable = mb.d();
            if (selectable != null) {
                i(selector, selectable);
            } else {
                return;
            }
        }
    }

    public void c(@NotNull k s) {
        SelectionKey k;
        k.f(s, "s");
        m(s, new ClosedChannelException());
        Selector selector = this.selectorRef;
        if (selector != null && (k = s.getChannel().keyFor(selector)) != null) {
            k.cancel();
            a1();
        }
    }

    /* access modifiers changed from: protected */
    public void x(@NotNull k selectable) {
        k.f(selectable, "selectable");
        try {
            if (this.y.a(selectable)) {
                if (!this.x.b(x.a)) {
                    a1();
                }
            } else if (selectable.getChannel().isOpen()) {
                throw new ClosedSelectorException();
            } else {
                throw new ClosedChannelException();
            }
        } catch (Throwable t) {
            m(selectable, t);
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 4 */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object u0(@org.jetbrains.annotations.NotNull io.ktor.network.selector.h<io.ktor.network.selector.k> r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super io.ktor.network.selector.k> r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof io.ktor.network.selector.a.d
            if (r0 == 0) goto L_0x0013
            r0 = r6
            io.ktor.network.selector.a$d r0 = (io.ktor.network.selector.a.d) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.network.selector.a$d r0 = new io.ktor.network.selector.a$d
            r0.<init>(r4, r6)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0039;
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
            io.ktor.network.selector.h r5 = (io.ktor.network.selector.h) r5
            java.lang.Object r2 = r0.L$0
            io.ktor.network.selector.a r2 = (io.ktor.network.selector.a) r2
            kotlin.p.b(r1)
            r3 = r1
            goto L_0x0055
        L_0x0039:
            kotlin.p.b(r1)
            java.lang.Object r3 = r5.d()
            io.ktor.network.selector.k r3 = (io.ktor.network.selector.k) r3
            if (r3 == 0) goto L_0x0046
            r2 = r4
            goto L_0x0057
        L_0x0046:
            r0.L$0 = r4
            r0.L$1 = r5
            r3 = 1
            r0.label = r3
            java.lang.Object r3 = r4.F0(r5, r0)
            if (r3 != r2) goto L_0x0054
            return r2
        L_0x0054:
            r2 = r4
        L_0x0055:
            io.ktor.network.selector.k r3 = (io.ktor.network.selector.k) r3
        L_0x0057:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.network.selector.a.u0(io.ktor.network.selector.h, kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 18 */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00c5  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00cd  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00d3  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002a  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object F0(@org.jetbrains.annotations.NotNull io.ktor.network.selector.h<io.ktor.network.selector.k> r19, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super io.ktor.network.selector.k> r20) {
        /*
            r18 = this;
            r0 = r20
            boolean r1 = r0 instanceof io.ktor.network.selector.a.e
            if (r1 == 0) goto L_0x0017
            r1 = r0
            io.ktor.network.selector.a$e r1 = (io.ktor.network.selector.a.e) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0017
            int r2 = r2 - r3
            r1.label = r2
            r2 = r18
            goto L_0x001e
        L_0x0017:
            io.ktor.network.selector.a$e r1 = new io.ktor.network.selector.a$e
            r2 = r18
            r1.<init>(r2, r0)
        L_0x001e:
            java.lang.Object r3 = r1.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.c.d()
            int r5 = r1.label
            r6 = 0
            switch(r5) {
                case 0: goto L_0x004d;
                case 1: goto L_0x0032;
                default: goto L_0x002a;
            }
        L_0x002a:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r3 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r3)
            throw r1
        L_0x0032:
            r5 = r6
            java.lang.Object r7 = r1.L$2
            r5 = r7
            io.ktor.network.selector.k r5 = (io.ktor.network.selector.k) r5
            java.lang.Object r7 = r1.L$1
            io.ktor.network.selector.h r7 = (io.ktor.network.selector.h) r7
            java.lang.Object r8 = r1.L$0
            io.ktor.network.selector.a r8 = (io.ktor.network.selector.a) r8
            kotlin.p.b(r3)
            r17 = r1
            r1 = r0
            r0 = r7
            r7 = r4
            r4 = r3
            r3 = r17
            goto L_0x00d8
        L_0x004d:
            kotlin.p.b(r3)
            r8 = r2
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r0
            r0 = r19
        L_0x0057:
            java.lang.Object r7 = r0.d()
            io.ktor.network.selector.k r7 = (io.ktor.network.selector.k) r7
            if (r7 == 0) goto L_0x0061
            return r7
        L_0x0061:
            boolean r9 = r8.closed
            if (r9 == 0) goto L_0x0066
            return r6
        L_0x0066:
            r3.L$0 = r8
            r3.L$1 = r0
            r3.L$2 = r7
            r9 = 1
            r3.label = r9
            r10 = r3
            r11 = 0
            io.ktor.network.selector.a$b<kotlin.x, kotlin.coroutines.d<kotlin.x>> r12 = r8.x
            r13 = 0
            r14 = 0
            boolean r15 = r0.c()
            r16 = 0
            if (r15 == 0) goto L_0x0083
            boolean r15 = r8.closed
            if (r15 != 0) goto L_0x0083
            r14 = r9
            goto L_0x0085
        L_0x0083:
            r14 = r16
        L_0x0085:
            java.lang.Boolean r14 = kotlin.coroutines.jvm.internal.b.a(r14)
            boolean r14 = r14.booleanValue()
            if (r14 != 0) goto L_0x0091
        L_0x008f:
            r9 = r6
            goto L_0x00c2
        L_0x0091:
            java.util.concurrent.atomic.AtomicReference r14 = r12.a
            boolean r14 = r14.compareAndSet(r6, r10)
            if (r14 == 0) goto L_0x00db
            r14 = 0
            boolean r15 = r0.c()
            if (r15 == 0) goto L_0x00a7
            boolean r15 = r8.closed
            if (r15 != 0) goto L_0x00a7
            goto L_0x00a9
        L_0x00a7:
            r9 = r16
        L_0x00a9:
            java.lang.Boolean r9 = kotlin.coroutines.jvm.internal.b.a(r9)
            boolean r9 = r9.booleanValue()
            if (r9 != 0) goto L_0x00be
            java.util.concurrent.atomic.AtomicReference r9 = r12.a
            boolean r9 = r9.compareAndSet(r10, r6)
            if (r9 == 0) goto L_0x00be
            goto L_0x008f
        L_0x00be:
            java.lang.Object r9 = kotlin.coroutines.intrinsics.c.d()
        L_0x00c2:
            if (r9 == 0) goto L_0x00c5
            goto L_0x00c7
        L_0x00c5:
            kotlin.x r9 = kotlin.x.a
        L_0x00c7:
            java.lang.Object r10 = kotlin.coroutines.intrinsics.c.d()
            if (r9 != r10) goto L_0x00d0
            kotlin.coroutines.jvm.internal.h.c(r3)
        L_0x00d0:
            if (r9 != r5) goto L_0x00d3
            return r5
        L_0x00d3:
            r17 = r7
            r7 = r5
            r5 = r17
        L_0x00d8:
            r5 = r7
            goto L_0x0057
        L_0x00db:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "Continuation is already set"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.network.selector.a.F0(io.ktor.network.selector.h, kotlin.coroutines.d):java.lang.Object");
    }

    public void close() {
        this.closed = true;
        this.y.b();
        if (!this.x.b(x.a)) {
            a1();
        }
    }

    /* compiled from: ActorSelectorManager.kt */
    public static final class b<R, C extends kotlin.coroutines.d<? super R>> {
        /* access modifiers changed from: private */
        public final AtomicReference<C> a = new AtomicReference<>((Object) null);

        public final boolean b(R value) {
            kotlin.coroutines.d continuation = (kotlin.coroutines.d) this.a.getAndSet((Object) null);
            if (continuation == null) {
                return false;
            }
            o.a aVar = o.Companion;
            continuation.resumeWith(o.m17constructorimpl(value));
            return true;
        }
    }
}
