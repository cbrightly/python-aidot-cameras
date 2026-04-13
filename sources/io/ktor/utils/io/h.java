package io.ktor.utils.io;

import io.ktor.utils.io.core.a0;
import io.ktor.utils.io.core.e0;
import java.util.concurrent.CancellationException;
import kotlin.TypeCastException;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ByteChannelSequential.kt */
public abstract class h implements f, i, l, x {
    private boolean b;
    /* access modifiers changed from: private */
    @NotNull
    public final io.ktor.utils.io.core.n c;
    /* access modifiers changed from: private */
    @NotNull
    public final io.ktor.utils.io.core.q d;
    @NotNull
    private final p e;
    /* access modifiers changed from: private */
    public int f;
    /* access modifiers changed from: private */
    public final p g;
    private int h;
    private final p i;
    @Nullable
    private Throwable j;
    private int k;
    private io.ktor.utils.io.core.internal.a l;
    private final boolean m;

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteChannelSequentialBase", f = "ByteChannelSequential.kt", l = {972}, m = "awaitFreeSpace")
    /* compiled from: ByteChannelSequential.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(h hVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = hVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.H(this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteChannelSequentialBase", f = "ByteChannelSequential.kt", l = {964}, m = "awaitSuspend")
    /* compiled from: ByteChannelSequential.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(h hVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = hVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.J(0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteChannelSequentialBase", f = "ByteChannelSequential.kt", l = {584}, m = "discard$suspendImpl")
    /* compiled from: ByteChannelSequential.kt */
    public static final class f extends kotlin.coroutines.jvm.internal.d {
        long J$0;
        long J$1;
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(h hVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = hVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return h.M(this.this$0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteChannelSequentialBase", f = "ByteChannelSequential.kt", l = {591}, m = "discardSuspend")
    /* compiled from: ByteChannelSequential.kt */
    public static final class g extends kotlin.coroutines.jvm.internal.d {
        long J$0;
        long J$1;
        long J$2;
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(h hVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = hVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.N(0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteChannelSequentialBase", f = "ByteChannelSequential.kt", l = {430}, m = "readAvailable$ktor_io")
    /* renamed from: io.ktor.utils.io.h$h  reason: collision with other inner class name */
    /* compiled from: ByteChannelSequential.kt */
    public static final class C0286h extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0286h(h hVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = hVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.V((io.ktor.utils.io.core.c) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteChannelSequentialBase", f = "ByteChannelSequential.kt", l = {469}, m = "readAvailable$suspendImpl")
    /* compiled from: ByteChannelSequential.kt */
    public static final class i extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        i(h hVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = hVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return h.X(this.this$0, (byte[]) null, 0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteChannelSequentialBase", f = "ByteChannelSequential.kt", l = {435, 436}, m = "readAvailableSuspend")
    /* compiled from: ByteChannelSequential.kt */
    public static final class j extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        j(h hVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = hVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.Z((io.ktor.utils.io.core.c) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteChannelSequentialBase", f = "ByteChannelSequential.kt", l = {474, 475}, m = "readAvailableSuspend")
    /* compiled from: ByteChannelSequential.kt */
    public static final class k extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        k(h hVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = hVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.a0((byte[]) null, 0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteChannelSequentialBase", f = "ByteChannelSequential.kt", l = {389}, m = "readPacket$suspendImpl")
    /* compiled from: ByteChannelSequential.kt */
    public static final class l extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        int I$1;
        int I$2;
        int I$3;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        l(h hVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = hVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return h.b0(this.this$0, 0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteChannelSequentialBase", f = "ByteChannelSequential.kt", l = {402}, m = "readPacketSuspend")
    /* compiled from: ByteChannelSequential.kt */
    public static final class m extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        int I$1;
        int I$2;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        m(h hVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = hVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.c0((io.ktor.utils.io.core.n) null, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteChannelSequentialBase", f = "ByteChannelSequential.kt", l = {358}, m = "readRemaining$suspendImpl")
    /* compiled from: ByteChannelSequential.kt */
    public static final class n extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        long J$0;
        long J$1;
        long J$2;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        n(h hVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = hVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return h.d0(this.this$0, 0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteChannelSequentialBase", f = "ByteChannelSequential.kt", l = {373}, m = "readRemainingSuspend")
    /* compiled from: ByteChannelSequential.kt */
    public static final class o extends kotlin.coroutines.jvm.internal.d {
        long J$0;
        long J$1;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        o(h hVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = hVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.e0((io.ktor.utils.io.core.n) null, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteChannelSequentialBase", f = "ByteChannelSequential.kt", l = {620}, m = "readSuspendableSession$suspendImpl")
    /* compiled from: ByteChannelSequential.kt */
    public static final class p extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        p(h hVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = hVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return h.f0(this.this$0, (kotlin.jvm.functions.p) null, this);
        }
    }

    @Nullable
    public Object c(@NotNull kotlin.jvm.functions.p<? super x, ? super kotlin.coroutines.d<? super x>, ? extends Object> pVar, @NotNull kotlin.coroutines.d<? super x> dVar) {
        return f0(this, pVar, dVar);
    }

    @Nullable
    public Object g(long j2, @NotNull kotlin.coroutines.d<? super Long> dVar) {
        return M(this, j2, dVar);
    }

    @Nullable
    public <A extends Appendable> Object i(@NotNull A a2, int i2, @NotNull kotlin.coroutines.d<? super Boolean> dVar) {
        return g0(this, a2, i2, dVar);
    }

    @Nullable
    public Object j(@NotNull byte[] bArr, int i2, int i3, @NotNull kotlin.coroutines.d<? super Integer> dVar) {
        return X(this, bArr, i2, i3, dVar);
    }

    @Nullable
    public Object k(int i2, @NotNull kotlin.coroutines.d<? super Boolean> dVar) {
        return G(this, i2, dVar);
    }

    @Nullable
    public Object m(@NotNull a0 a0Var, @NotNull kotlin.coroutines.d<? super x> dVar) {
        return k0(this, a0Var, dVar);
    }

    @Nullable
    public Object n(@NotNull kotlin.jvm.functions.p<? super b0, ? super kotlin.coroutines.d<? super x>, ? extends Object> pVar, @NotNull kotlin.coroutines.d<? super x> dVar) {
        return n0(this, pVar, dVar);
    }

    @Nullable
    public Object o(int i2, int i3, @NotNull kotlin.coroutines.d<? super io.ktor.utils.io.core.q> dVar) {
        return b0(this, i2, i3, dVar);
    }

    @Nullable
    public Object p(long j2, int i2, @NotNull kotlin.coroutines.d<? super io.ktor.utils.io.core.q> dVar) {
        return d0(this, j2, i2, dVar);
    }

    @Nullable
    public Object q(@NotNull byte[] bArr, int i2, int i3, @NotNull kotlin.coroutines.d<? super x> dVar) {
        return l0(this, bArr, i2, i3, dVar);
    }

    @Nullable
    public Object r(@NotNull a0 a0Var, @NotNull kotlin.coroutines.d<? super Integer> dVar) {
        return W(this, a0Var, dVar);
    }

    @Nullable
    public Object x(@NotNull io.ktor.utils.io.core.q qVar, @NotNull kotlin.coroutines.d<? super x> dVar) {
        return m0(this, qVar, dVar);
    }

    /* compiled from: Require.kt */
    public static final class a extends io.ktor.utils.io.core.internal.e {
        final /* synthetic */ int a;

        public a(int i) {
            this.a = i;
        }

        @NotNull
        public Void a() {
            throw new IllegalArgumentException("atLeast parameter shouldn't be negative: " + this.a);
        }
    }

    /* compiled from: Require.kt */
    public static final class b extends io.ktor.utils.io.core.internal.e {
        final /* synthetic */ int a;

        public b(int i) {
            this.a = i;
        }

        @NotNull
        public Void a() {
            throw new IllegalArgumentException("atLeast parameter shouldn't be larger than max buffer size of 4088: " + this.a);
        }
    }

    public boolean A() {
        return this.m;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final io.ktor.utils.io.core.n U() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final io.ktor.utils.io.core.q T() {
        return this.d;
    }

    @NotNull
    public final p S() {
        return this.e;
    }

    public int e() {
        return (int) this.d.P0();
    }

    public int R() {
        return Math.max(0, 4088 - (((int) this.d.P0()) + this.c.f1()));
    }

    public boolean y() {
        return this.b && this.d.w0();
    }

    public boolean f() {
        return this.b;
    }

    public long t() {
        return 0;
    }

    public void flush() {
        if (this.c.g1()) {
            io.ktor.utils.io.core.internal.g.a(this.d, this.c);
            this.i.c();
        }
    }

    private final void O() {
        if (this.b) {
            Throwable th = this.j;
            if (th == null) {
                th = new ClosedWriteChannelException("Channel is already closed");
            }
            throw th;
        }
    }

    private final void P() {
        Throwable it = this.j;
        if (it != null) {
            throw it;
        }
    }

    private final void Q(io.ktor.utils.io.core.n closeable) {
        Throwable cause = this.j;
        if (cause != null) {
            closeable.release();
            throw cause;
        }
    }

    static /* synthetic */ Object m0(h hVar, io.ktor.utils.io.core.q packet, kotlin.coroutines.d $completion) {
        hVar.c.W(packet);
        Object H = hVar.H($completion);
        return H == kotlin.coroutines.intrinsics.c.d() ? H : x.a;
    }

    static /* synthetic */ Object k0(h hVar, a0 src, kotlin.coroutines.d $completion) {
        if (src != null) {
            Object j0 = hVar.j0(src, $completion);
            return j0 == kotlin.coroutines.intrinsics.c.d() ? j0 : x.a;
        }
        throw new TypeCastException("null cannot be cast to non-null type io.ktor.utils.io.core.Buffer");
    }

    @Nullable
    public final Object j0(@NotNull io.ktor.utils.io.core.c src, @NotNull kotlin.coroutines.d<? super x> $completion) {
        e0.c(this.c, src, 0, 2, (Object) null);
        Object H = H($completion);
        return H == kotlin.coroutines.intrinsics.c.d() ? H : x.a;
    }

    static /* synthetic */ Object l0(h hVar, byte[] src, int offset, int length, kotlin.coroutines.d $completion) {
        e0.b(hVar.c, src, offset, length);
        Object H = hVar.H($completion);
        if (H == kotlin.coroutines.intrinsics.c.d()) {
            return H;
        }
        return x.a;
    }

    static /* synthetic */ Object n0(h hVar, kotlin.jvm.functions.p visitor, kotlin.coroutines.d $completion) {
        Object invoke = visitor.invoke(hVar.K(), $completion);
        if (invoke == kotlin.coroutines.intrinsics.c.d()) {
            return invoke;
        }
        return x.a;
    }

    /* compiled from: ByteChannelSequential.kt */
    public static final class e implements b0 {
        final /* synthetic */ h a;

        @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteChannelSequentialBase$beginWriteSession$1", f = "ByteChannelSequential.kt", l = {754}, m = "tryAwait")
        /* compiled from: ByteChannelSequential.kt */
        public static final class a extends kotlin.coroutines.jvm.internal.d {
            int I$0;
            Object L$0;
            Object L$1;
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ e this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(e eVar, kotlin.coroutines.d dVar) {
                super(dVar);
                this.this$0 = eVar;
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return this.this$0.c(0, this);
            }
        }

        e(h $outer) {
            this.a = $outer;
        }

        @Nullable
        public a0 a(int min) {
            if (this.a.R() == 0) {
                return null;
            }
            io.ktor.utils.io.core.internal.a J = this.a.U().J(min);
            if (J != null) {
                return (a0) J;
            }
            throw new TypeCastException("null cannot be cast to non-null type io.ktor.utils.io.core.IoBuffer");
        }

        public void b(int n) {
            this.a.U().c();
            this.a.F();
        }

        /* Debug info: failed to restart local var, previous not found, register: 9 */
        /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
        /* JADX WARNING: Removed duplicated region for block: B:11:0x003e  */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object c(int r10, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r11) {
            /*
                r9 = this;
                boolean r0 = r11 instanceof io.ktor.utils.io.h.e.a
                if (r0 == 0) goto L_0x0013
                r0 = r11
                io.ktor.utils.io.h$e$a r0 = (io.ktor.utils.io.h.e.a) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L_0x0013
                int r1 = r1 - r2
                r0.label = r1
                goto L_0x0018
            L_0x0013:
                io.ktor.utils.io.h$e$a r0 = new io.ktor.utils.io.h$e$a
                r0.<init>(r9, r11)
            L_0x0018:
                java.lang.Object r1 = r0.result
                java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
                int r3 = r0.label
                r4 = 0
                switch(r3) {
                    case 0: goto L_0x003e;
                    case 1: goto L_0x002c;
                    default: goto L_0x0024;
                }
            L_0x0024:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x002c:
                r2 = r4
                r3 = 0
                java.lang.Object r4 = r0.L$1
                r2 = r4
                io.ktor.utils.io.p r2 = (io.ktor.utils.io.p) r2
                int r10 = r0.I$0
                java.lang.Object r4 = r0.L$0
                io.ktor.utils.io.h$e r4 = (io.ktor.utils.io.h.e) r4
                kotlin.p.b(r1)
                goto L_0x00ab
            L_0x003e:
                kotlin.p.b(r1)
                io.ktor.utils.io.h r3 = r9.a
                int r3 = r3.R()
                if (r3 >= r10) goto L_0x00b2
                io.ktor.utils.io.h r3 = r9.a
                r3.f = r10
                io.ktor.utils.io.h r3 = r9.a
                io.ktor.utils.io.p r3 = r3.g
                r5 = 0
                kotlin.jvm.functions.a r6 = r3.b()
                java.lang.Object r6 = r6.invoke()
                java.lang.Boolean r6 = (java.lang.Boolean) r6
                boolean r6 = r6.booleanValue()
                if (r6 == 0) goto L_0x0066
                goto L_0x00b2
            L_0x0066:
                r0.L$0 = r9
                r0.I$0 = r10
                r0.L$1 = r3
                r6 = 1
                r0.label = r6
                r6 = r0
                r7 = 0
                java.util.concurrent.atomic.AtomicReferenceFieldUpdater r8 = io.ktor.utils.io.p.a
                boolean r8 = r8.compareAndSet(r3, r4, r6)
                if (r8 == 0) goto L_0x00ac
                kotlin.jvm.functions.a r8 = r3.b()
                java.lang.Object r8 = r8.invoke()
                java.lang.Boolean r8 = (java.lang.Boolean) r8
                boolean r8 = r8.booleanValue()
                if (r8 == 0) goto L_0x0098
                java.util.concurrent.atomic.AtomicReferenceFieldUpdater r8 = io.ktor.utils.io.p.a
                boolean r4 = r8.compareAndSet(r3, r6, r4)
                if (r4 == 0) goto L_0x0098
                kotlin.x r4 = kotlin.x.a
                goto L_0x009c
            L_0x0098:
                java.lang.Object r4 = kotlin.coroutines.intrinsics.c.d()
            L_0x009c:
                java.lang.Object r6 = kotlin.coroutines.intrinsics.c.d()
                if (r4 != r6) goto L_0x00a5
                kotlin.coroutines.jvm.internal.h.c(r0)
            L_0x00a5:
                if (r4 != r2) goto L_0x00a8
                return r2
            L_0x00a8:
                r4 = r9
                r2 = r3
                r3 = r5
            L_0x00ab:
                goto L_0x00b3
            L_0x00ac:
                java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
                r2.<init>()
                throw r2
            L_0x00b2:
                r4 = r9
            L_0x00b3:
                kotlin.x r2 = kotlin.x.a
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.h.e.c(int, kotlin.coroutines.d):java.lang.Object");
        }
    }

    @NotNull
    public b0 K() {
        return new e(this);
    }

    /* access modifiers changed from: protected */
    public final void E() {
        this.g.c();
        this.e.c();
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object d0(io.ktor.utils.io.h r11, long r12, int r14, kotlin.coroutines.d r15) {
        /*
            boolean r0 = r15 instanceof io.ktor.utils.io.h.n
            if (r0 == 0) goto L_0x0013
            r0 = r15
            io.ktor.utils.io.h$n r0 = (io.ktor.utils.io.h.n) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.h$n r0 = new io.ktor.utils.io.h$n
            r0.<init>(r11, r15)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x0047;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002d:
            r2 = r4
            r6 = 0
            long r4 = r0.J$2
            long r2 = r0.J$1
            java.lang.Object r7 = r0.L$1
            r6 = r7
            io.ktor.utils.io.core.n r6 = (io.ktor.utils.io.core.n) r6
            int r14 = r0.I$0
            long r12 = r0.J$0
            java.lang.Object r7 = r0.L$0
            r11 = r7
            io.ktor.utils.io.h r11 = (io.ktor.utils.io.h) r11
            kotlin.p.b(r1)
            r9 = r4
            r4 = r1
            goto L_0x0091
        L_0x0047:
            kotlin.p.b(r1)
            r11.P()
            io.ktor.utils.io.core.n r6 = io.ktor.utils.io.core.f0.a(r14)
            io.ktor.utils.io.core.q r3 = r11.d
            long r7 = r3.P0()
            long r7 = java.lang.Math.min(r12, r7)
            io.ktor.utils.io.core.q r3 = r11.d
            r6.u0(r3, r7)
            int r3 = r6.f1()
            long r9 = (long) r3
            long r9 = r12 - r9
            int r3 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r3 == 0) goto L_0x0095
            io.ktor.utils.io.core.q r3 = r11.d
            r4 = 0
            boolean r3 = r3.w0()
            if (r3 == 0) goto L_0x0079
            boolean r3 = r11.b
            if (r3 == 0) goto L_0x0079
            goto L_0x0095
        L_0x0079:
            r0.L$0 = r11
            r0.J$0 = r12
            r0.I$0 = r14
            r0.L$1 = r6
            r0.J$1 = r7
            r0.J$2 = r9
            r3 = 1
            r0.label = r3
            java.lang.Object r3 = r11.e0(r6, r12, r0)
            if (r3 != r2) goto L_0x008f
            return r2
        L_0x008f:
            r4 = r3
            r2 = r7
        L_0x0091:
            io.ktor.utils.io.core.q r4 = (io.ktor.utils.io.core.q) r4
            r7 = r2
            goto L_0x009f
        L_0x0095:
            r11.E()
            r11.Q(r6)
            io.ktor.utils.io.core.q r4 = r6.e1()
        L_0x009f:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.h.d0(io.ktor.utils.io.h, long, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 10 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v2, resolved type: io.ktor.utils.io.core.n} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object e0(@org.jetbrains.annotations.NotNull io.ktor.utils.io.core.n r11, long r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super io.ktor.utils.io.core.q> r14) {
        /*
            r10 = this;
            boolean r0 = r14 instanceof io.ktor.utils.io.h.o
            if (r0 == 0) goto L_0x0013
            r0 = r14
            io.ktor.utils.io.h$o r0 = (io.ktor.utils.io.h.o) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.h$o r0 = new io.ktor.utils.io.h$o
            r0.<init>(r10, r14)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x003f;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002d:
            r6 = r4
            long r6 = r0.J$1
            long r12 = r0.J$0
            java.lang.Object r3 = r0.L$1
            r11 = r3
            io.ktor.utils.io.core.n r11 = (io.ktor.utils.io.core.n) r11
            java.lang.Object r3 = r0.L$0
            io.ktor.utils.io.h r3 = (io.ktor.utils.io.h) r3
            kotlin.p.b(r1)
            goto L_0x0091
        L_0x003f:
            kotlin.p.b(r1)
            r3 = r10
        L_0x0043:
            int r6 = r11.f1()
            long r6 = (long) r6
            int r6 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1))
            if (r6 >= 0) goto L_0x0092
            int r6 = r11.f1()
            long r6 = (long) r6
            long r6 = r12 - r6
            io.ktor.utils.io.core.q r8 = r3.d
            long r8 = r8.P0()
            long r6 = java.lang.Math.min(r6, r8)
            io.ktor.utils.io.core.q r8 = r3.d
            r11.u0(r8, r6)
            r3.E()
            r3.Q(r11)
            io.ktor.utils.io.core.q r8 = r3.d
            long r8 = r8.P0()
            int r8 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r8 != 0) goto L_0x007f
            io.ktor.utils.io.core.n r8 = r3.c
            int r8 = r8.f1()
            if (r8 != 0) goto L_0x007f
            boolean r8 = r3.b
            if (r8 == 0) goto L_0x007f
            goto L_0x0092
        L_0x007f:
            r0.L$0 = r3
            r0.L$1 = r11
            r0.J$0 = r12
            r0.J$1 = r6
            r8 = 1
            r0.label = r8
            java.lang.Object r8 = r3.J(r8, r0)
            if (r8 != r2) goto L_0x0091
            return r2
        L_0x0091:
            goto L_0x0043
        L_0x0092:
            r3.Q(r11)
            io.ktor.utils.io.core.q r2 = r11.e1()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.h.e0(io.ktor.utils.io.core.n, long, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object b0(io.ktor.utils.io.h r9, int r10, int r11, kotlin.coroutines.d r12) {
        /*
            boolean r0 = r12 instanceof io.ktor.utils.io.h.l
            if (r0 == 0) goto L_0x0013
            r0 = r12
            io.ktor.utils.io.h$l r0 = (io.ktor.utils.io.h.l) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.h$l r0 = new io.ktor.utils.io.h$l
            r0.<init>(r9, r12)
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
            int r3 = r0.I$3
            int r2 = r0.I$2
            java.lang.Object r5 = r0.L$1
            r4 = r5
            io.ktor.utils.io.core.n r4 = (io.ktor.utils.io.core.n) r4
            int r11 = r0.I$1
            int r10 = r0.I$0
            java.lang.Object r5 = r0.L$0
            r9 = r5
            io.ktor.utils.io.h r9 = (io.ktor.utils.io.h) r9
            kotlin.p.b(r1)
            r6 = r1
            goto L_0x007c
        L_0x0045:
            kotlin.p.b(r1)
            io.ktor.utils.io.core.n r4 = io.ktor.utils.io.core.f0.a(r11)
            r3 = r10
            long r5 = (long) r3
            io.ktor.utils.io.core.q r7 = r9.d
            long r7 = r7.P0()
            long r5 = java.lang.Math.min(r5, r7)
            int r5 = (int) r5
            int r3 = r3 - r5
            io.ktor.utils.io.core.q r6 = r9.d
            r4.o0(r6, r5)
            r9.E()
            if (r3 <= 0) goto L_0x0081
            r0.L$0 = r9
            r0.I$0 = r10
            r0.I$1 = r11
            r0.L$1 = r4
            r0.I$2 = r3
            r0.I$3 = r5
            r6 = 1
            r0.label = r6
            java.lang.Object r6 = r9.c0(r4, r3, r0)
            if (r6 != r2) goto L_0x007a
            return r2
        L_0x007a:
            r2 = r3
            r3 = r5
        L_0x007c:
            io.ktor.utils.io.core.q r6 = (io.ktor.utils.io.core.q) r6
            r5 = r3
            r3 = r2
            goto L_0x0085
        L_0x0081:
            io.ktor.utils.io.core.q r6 = r4.e1()
        L_0x0085:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.h.b0(io.ktor.utils.io.h, int, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 10 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v2, resolved type: io.ktor.utils.io.core.n} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object c0(@org.jetbrains.annotations.NotNull io.ktor.utils.io.core.n r11, int r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super io.ktor.utils.io.core.q> r13) {
        /*
            r10 = this;
            boolean r0 = r13 instanceof io.ktor.utils.io.h.m
            if (r0 == 0) goto L_0x0013
            r0 = r13
            io.ktor.utils.io.h$m r0 = (io.ktor.utils.io.h.m) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.h$m r0 = new io.ktor.utils.io.h$m
            r0.<init>(r10, r13)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0040;
                case 1: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            r3 = 0
            r4 = r3
            int r4 = r0.I$2
            int r3 = r0.I$1
            int r12 = r0.I$0
            java.lang.Object r5 = r0.L$1
            r11 = r5
            io.ktor.utils.io.core.n r11 = (io.ktor.utils.io.core.n) r11
            java.lang.Object r5 = r0.L$0
            io.ktor.utils.io.h r5 = (io.ktor.utils.io.h) r5
            kotlin.p.b(r1)
            goto L_0x0072
        L_0x0040:
            kotlin.p.b(r1)
            r3 = r12
            r5 = r10
        L_0x0045:
            if (r3 <= 0) goto L_0x0073
            long r6 = (long) r3
            io.ktor.utils.io.core.q r4 = r5.d
            long r8 = r4.P0()
            long r6 = java.lang.Math.min(r6, r8)
            int r4 = (int) r6
            int r3 = r3 - r4
            io.ktor.utils.io.core.q r6 = r5.d
            r11.o0(r6, r4)
            r5.E()
            if (r3 <= 0) goto L_0x0045
            r0.L$0 = r5
            r0.L$1 = r11
            r0.I$0 = r12
            r0.I$1 = r3
            r0.I$2 = r4
            r6 = 1
            r0.label = r6
            java.lang.Object r6 = r5.J(r6, r0)
            if (r6 != r2) goto L_0x0072
            return r2
        L_0x0072:
            goto L_0x0045
        L_0x0073:
            io.ktor.utils.io.core.q r2 = r11.e1()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.h.c0(io.ktor.utils.io.core.n, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: protected */
    public final int Y() {
        Throwable it = this.j;
        if (it == null) {
            return -1;
        }
        throw it;
    }

    static /* synthetic */ Object W(h hVar, a0 dst, kotlin.coroutines.d $completion) {
        if (dst != null) {
            return hVar.V(dst, $completion);
        }
        throw new TypeCastException("null cannot be cast to non-null type io.ktor.utils.io.core.Buffer");
    }

    /* Debug info: failed to restart local var, previous not found, register: 9 */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object V(@org.jetbrains.annotations.NotNull io.ktor.utils.io.core.c r10, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Integer> r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof io.ktor.utils.io.h.C0286h
            if (r0 == 0) goto L_0x0013
            r0 = r11
            io.ktor.utils.io.h$h r0 = (io.ktor.utils.io.h.C0286h) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.h$h r0 = new io.ktor.utils.io.h$h
            r0.<init>(r9, r11)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x003b;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002c:
            java.lang.Object r2 = r0.L$1
            r10 = r2
            io.ktor.utils.io.core.c r10 = (io.ktor.utils.io.core.c) r10
            java.lang.Object r2 = r0.L$0
            io.ktor.utils.io.h r2 = (io.ktor.utils.io.h) r2
            kotlin.p.b(r1)
            r3 = r1
            goto L_0x009e
        L_0x003b:
            kotlin.p.b(r1)
            java.lang.Throwable r3 = r9.j
            if (r3 == 0) goto L_0x0049
            if (r3 != 0) goto L_0x0048
            kotlin.jvm.internal.k.n()
        L_0x0048:
            throw r3
        L_0x0049:
            io.ktor.utils.io.core.q r3 = r9.d
            boolean r3 = r3.j()
            if (r3 == 0) goto L_0x0072
            r2 = r10
            r3 = 0
            int r4 = r2.m()
            int r5 = r2.s()
            int r4 = r4 - r5
            long r2 = (long) r4
            io.ktor.utils.io.core.q r4 = r9.d
            long r4 = r4.P0()
            long r2 = java.lang.Math.min(r2, r4)
            int r4 = (int) r2
            io.ktor.utils.io.core.q r2 = r9.d
            io.ktor.utils.io.core.x.b(r2, r10, r4)
            r9.E()
            r2 = r9
            goto L_0x00a4
        L_0x0072:
            boolean r3 = r9.b
            if (r3 == 0) goto L_0x007c
            int r4 = r9.Y()
            r2 = r9
            goto L_0x00a4
        L_0x007c:
            r3 = r10
            r5 = 0
            int r6 = r3.m()
            int r7 = r3.s()
            r8 = 1
            if (r6 <= r7) goto L_0x008b
            r3 = r8
            goto L_0x008c
        L_0x008b:
            r3 = r4
        L_0x008c:
            if (r3 != 0) goto L_0x0090
            r2 = r9
            goto L_0x00a4
        L_0x0090:
            r0.L$0 = r9
            r0.L$1 = r10
            r0.label = r8
            java.lang.Object r3 = r9.Z(r10, r0)
            if (r3 != r2) goto L_0x009d
            return r2
        L_0x009d:
            r2 = r9
        L_0x009e:
            java.lang.Number r3 = (java.lang.Number) r3
            int r4 = r3.intValue()
        L_0x00a4:
            java.lang.Integer r3 = kotlin.coroutines.jvm.internal.b.c(r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.h.V(io.ktor.utils.io.core.c, kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 5 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: io.ktor.utils.io.core.c} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0065 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object Z(@org.jetbrains.annotations.NotNull io.ktor.utils.io.core.c r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Integer> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof io.ktor.utils.io.h.j
            if (r0 == 0) goto L_0x0013
            r0 = r7
            io.ktor.utils.io.h$j r0 = (io.ktor.utils.io.h.j) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.h$j r0 = new io.ktor.utils.io.h$j
            r0.<init>(r5, r7)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0046;
                case 1: goto L_0x0039;
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
            r6 = r2
            io.ktor.utils.io.core.c r6 = (io.ktor.utils.io.core.c) r6
            java.lang.Object r2 = r0.L$0
            io.ktor.utils.io.h r2 = (io.ktor.utils.io.h) r2
            kotlin.p.b(r1)
            r4 = r1
            goto L_0x0067
        L_0x0039:
            java.lang.Object r3 = r0.L$1
            r6 = r3
            io.ktor.utils.io.core.c r6 = (io.ktor.utils.io.core.c) r6
            java.lang.Object r3 = r0.L$0
            io.ktor.utils.io.h r3 = (io.ktor.utils.io.h) r3
            kotlin.p.b(r1)
            goto L_0x0058
        L_0x0046:
            kotlin.p.b(r1)
            r0.L$0 = r5
            r0.L$1 = r6
            r3 = 1
            r0.label = r3
            java.lang.Object r3 = r5.J(r3, r0)
            if (r3 != r2) goto L_0x0057
            return r2
        L_0x0057:
            r3 = r5
        L_0x0058:
            r0.L$0 = r3
            r0.L$1 = r6
            r4 = 2
            r0.label = r4
            java.lang.Object r4 = r3.V(r6, r0)
            if (r4 != r2) goto L_0x0066
            return r2
        L_0x0066:
            r2 = r3
        L_0x0067:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.h.Z(io.ktor.utils.io.core.c, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object X(io.ktor.utils.io.h r6, byte[] r7, int r8, int r9, kotlin.coroutines.d r10) {
        /*
            boolean r0 = r10 instanceof io.ktor.utils.io.h.i
            if (r0 == 0) goto L_0x0013
            r0 = r10
            io.ktor.utils.io.h$i r0 = (io.ktor.utils.io.h.i) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.h$i r0 = new io.ktor.utils.io.h$i
            r0.<init>(r6, r10)
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
            int r9 = r0.I$1
            int r8 = r0.I$0
            java.lang.Object r2 = r0.L$1
            r7 = r2
            byte[] r7 = (byte[]) r7
            java.lang.Object r2 = r0.L$0
            r6 = r2
            io.ktor.utils.io.h r6 = (io.ktor.utils.io.h) r6
            kotlin.p.b(r1)
            r3 = r1
            goto L_0x007b
        L_0x003e:
            kotlin.p.b(r1)
            io.ktor.utils.io.core.q r3 = r6.d
            boolean r3 = r3.j()
            if (r3 == 0) goto L_0x0060
            long r2 = (long) r9
            io.ktor.utils.io.core.q r4 = r6.d
            long r4 = r4.P0()
            long r2 = java.lang.Math.min(r2, r4)
            int r2 = (int) r2
            io.ktor.utils.io.core.q r3 = r6.d
            io.ktor.utils.io.core.x.c(r3, r7, r8, r2)
            r6.E()
            goto L_0x0081
        L_0x0060:
            boolean r3 = r6.b
            if (r3 == 0) goto L_0x0069
            int r2 = r6.Y()
            goto L_0x0081
        L_0x0069:
            r0.L$0 = r6
            r0.L$1 = r7
            r0.I$0 = r8
            r0.I$1 = r9
            r3 = 1
            r0.label = r3
            java.lang.Object r3 = r6.a0(r7, r8, r9, r0)
            if (r3 != r2) goto L_0x007b
            return r2
        L_0x007b:
            java.lang.Number r3 = (java.lang.Number) r3
            int r2 = r3.intValue()
        L_0x0081:
            java.lang.Integer r2 = kotlin.coroutines.jvm.internal.b.c(r2)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.h.X(io.ktor.utils.io.h, byte[], int, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 5 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: byte[]} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0075 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object a0(@org.jetbrains.annotations.NotNull byte[] r6, int r7, int r8, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Integer> r9) {
        /*
            r5 = this;
            boolean r0 = r9 instanceof io.ktor.utils.io.h.k
            if (r0 == 0) goto L_0x0013
            r0 = r9
            io.ktor.utils.io.h$k r0 = (io.ktor.utils.io.h.k) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.h$k r0 = new io.ktor.utils.io.h$k
            r0.<init>(r5, r9)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x004e;
                case 1: goto L_0x003d;
                case 2: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            int r8 = r0.I$1
            int r7 = r0.I$0
            java.lang.Object r2 = r0.L$1
            r6 = r2
            byte[] r6 = (byte[]) r6
            java.lang.Object r2 = r0.L$0
            io.ktor.utils.io.h r2 = (io.ktor.utils.io.h) r2
            kotlin.p.b(r1)
            r4 = r1
            goto L_0x0077
        L_0x003d:
            int r8 = r0.I$1
            int r7 = r0.I$0
            java.lang.Object r3 = r0.L$1
            r6 = r3
            byte[] r6 = (byte[]) r6
            java.lang.Object r3 = r0.L$0
            io.ktor.utils.io.h r3 = (io.ktor.utils.io.h) r3
            kotlin.p.b(r1)
            goto L_0x0064
        L_0x004e:
            kotlin.p.b(r1)
            r0.L$0 = r5
            r0.L$1 = r6
            r0.I$0 = r7
            r0.I$1 = r8
            r3 = 1
            r0.label = r3
            java.lang.Object r3 = r5.J(r3, r0)
            if (r3 != r2) goto L_0x0063
            return r2
        L_0x0063:
            r3 = r5
        L_0x0064:
            r0.L$0 = r3
            r0.L$1 = r6
            r0.I$0 = r7
            r0.I$1 = r8
            r4 = 2
            r0.label = r4
            java.lang.Object r4 = r3.j(r6, r7, r8, r0)
            if (r4 != r2) goto L_0x0076
            return r2
        L_0x0076:
            r2 = r3
        L_0x0077:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.h.a0(byte[], int, int, kotlin.coroutines.d):java.lang.Object");
    }

    private final void L() {
        io.ktor.utils.io.core.c this_$iv = this.l;
        int delta = this.k - (this_$iv.s() - this_$iv.o());
        if (this.l != io.ktor.utils.io.core.c.c.a()) {
            io.ktor.utils.io.core.internal.g.d(this.d, this.l);
        }
        if (delta > 0) {
            E();
        }
        this.k = 0;
        this.l = io.ktor.utils.io.core.internal.a.z4.a();
    }

    static /* synthetic */ Object G(h hVar, int atLeast, kotlin.coroutines.d $completion) {
        boolean condition$iv = false;
        if (atLeast >= 0) {
            if (atLeast <= 4088) {
                condition$iv = true;
            }
            if (condition$iv) {
                hVar.L();
                if (atLeast == 0) {
                    return kotlin.coroutines.jvm.internal.b.a(!hVar.y());
                }
                if (hVar.e() >= atLeast) {
                    return kotlin.coroutines.jvm.internal.b.a(true);
                }
                return hVar.J(atLeast, $completion);
            }
            new b(atLeast).a();
            throw null;
        }
        new a(atLeast).a();
        throw null;
    }

    @Nullable
    public final Object I(@NotNull kotlin.coroutines.d<? super Boolean> $completion) {
        if (!this.d.w0()) {
            return kotlin.coroutines.jvm.internal.b.a(true);
        }
        return J(1, $completion);
    }

    /* Debug info: failed to restart local var, previous not found, register: 11 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00ae  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object J(int r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Boolean> r13) {
        /*
            r11 = this;
            boolean r0 = r13 instanceof io.ktor.utils.io.h.d
            if (r0 == 0) goto L_0x0013
            r0 = r13
            io.ktor.utils.io.h$d r0 = (io.ktor.utils.io.h.d) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.h$d r0 = new io.ktor.utils.io.h$d
            r0.<init>(r11, r13)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            r4 = 1
            r5 = 0
            r6 = 0
            switch(r3) {
                case 0: goto L_0x0040;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002e:
            r2 = r6
            r3 = r5
            java.lang.Object r6 = r0.L$1
            r2 = r6
            io.ktor.utils.io.p r2 = (io.ktor.utils.io.p) r2
            int r12 = r0.I$0
            java.lang.Object r6 = r0.L$0
            io.ktor.utils.io.h r6 = (io.ktor.utils.io.h) r6
            kotlin.p.b(r1)
            goto L_0x00a9
        L_0x0040:
            kotlin.p.b(r1)
            if (r12 < 0) goto L_0x0047
            r3 = r4
            goto L_0x0048
        L_0x0047:
            r3 = r5
        L_0x0048:
            if (r3 == 0) goto L_0x00c9
            r11.h = r12
            io.ktor.utils.io.p r3 = r11.i
            r7 = 0
            kotlin.jvm.functions.a r8 = r3.b()
            java.lang.Object r8 = r8.invoke()
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x0061
            r6 = r11
            goto L_0x00aa
        L_0x0061:
            r0.L$0 = r11
            r0.I$0 = r12
            r0.L$1 = r3
            r0.label = r4
            r8 = r0
            r9 = 0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r10 = io.ktor.utils.io.p.a
            boolean r10 = r10.compareAndSet(r3, r6, r8)
            if (r10 == 0) goto L_0x00c3
            kotlin.jvm.functions.a r10 = r3.b()
            java.lang.Object r10 = r10.invoke()
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            if (r10 == 0) goto L_0x0092
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r10 = io.ktor.utils.io.p.a
            boolean r6 = r10.compareAndSet(r3, r8, r6)
            if (r6 == 0) goto L_0x0092
            kotlin.x r6 = kotlin.x.a
            goto L_0x009a
        L_0x0092:
            r6 = 0
            r11.E()
            java.lang.Object r6 = kotlin.coroutines.intrinsics.c.d()
        L_0x009a:
            java.lang.Object r8 = kotlin.coroutines.intrinsics.c.d()
            if (r6 != r8) goto L_0x00a3
            kotlin.coroutines.jvm.internal.h.c(r0)
        L_0x00a3:
            if (r6 != r2) goto L_0x00a6
            return r2
        L_0x00a6:
            r6 = r11
            r2 = r3
            r3 = r7
        L_0x00a9:
        L_0x00aa:
            java.lang.Throwable r2 = r6.j
            if (r2 != 0) goto L_0x00c1
            boolean r2 = r6.y()
            if (r2 != 0) goto L_0x00bb
            int r2 = r6.e()
            if (r2 < r12) goto L_0x00bb
            goto L_0x00bc
        L_0x00bb:
            r4 = r5
        L_0x00bc:
            java.lang.Boolean r2 = kotlin.coroutines.jvm.internal.b.a(r4)
            return r2
        L_0x00c1:
            r3 = 0
            throw r2
        L_0x00c3:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            r2.<init>()
            throw r2
        L_0x00c9:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "Failed requirement."
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.h.J(int, kotlin.coroutines.d):java.lang.Object");
    }

    @Nullable
    public a0 a(int atLeast) {
        Throwable it = this.j;
        if (it == null) {
            L();
            return h0(atLeast);
        }
        throw it;
    }

    private final a0 h0(int atLeast) {
        a0 view = (a0) this.d.g1(atLeast);
        if (view == null) {
            this.l = io.ktor.utils.io.core.internal.a.z4.a();
            this.k = 0;
        } else {
            this.l = view;
            io.ktor.utils.io.core.c this_$iv = view;
            this.k = this_$iv.s() - this_$iv.o();
        }
        return view;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object M(io.ktor.utils.io.h r11, long r12, kotlin.coroutines.d r14) {
        /*
            boolean r0 = r14 instanceof io.ktor.utils.io.h.f
            if (r0 == 0) goto L_0x0013
            r0 = r14
            io.ktor.utils.io.h$f r0 = (io.ktor.utils.io.h.f) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.h$f r0 = new io.ktor.utils.io.h$f
            r0.<init>(r11, r14)
        L_0x0018:
            java.lang.Object r7 = r0.result
            java.lang.Object r8 = kotlin.coroutines.intrinsics.c.d()
            int r1 = r0.label
            switch(r1) {
                case 0: goto L_0x003c;
                case 1: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            r1 = 0
            long r1 = r0.J$1
            long r12 = r0.J$0
            java.lang.Object r3 = r0.L$0
            r11 = r3
            io.ktor.utils.io.h r11 = (io.ktor.utils.io.h) r11
            kotlin.p.b(r7)
            r9 = r1
            r1 = r7
            goto L_0x0064
        L_0x003c:
            kotlin.p.b(r7)
            io.ktor.utils.io.core.q r1 = r11.d
            long r9 = r1.C0(r12)
            int r1 = (r9 > r12 ? 1 : (r9 == r12 ? 0 : -1))
            if (r1 == 0) goto L_0x006f
            boolean r1 = r11.y()
            if (r1 == 0) goto L_0x0050
            goto L_0x006f
        L_0x0050:
            r0.L$0 = r11
            r0.J$0 = r12
            r0.J$1 = r9
            r1 = 1
            r0.label = r1
            r1 = r11
            r2 = r12
            r4 = r9
            r6 = r0
            java.lang.Object r1 = r1.N(r2, r4, r6)
            if (r1 != r8) goto L_0x0064
            return r8
        L_0x0064:
            java.lang.Number r1 = (java.lang.Number) r1
            long r1 = r1.longValue()
            java.lang.Long r1 = kotlin.coroutines.jvm.internal.b.d(r1)
            return r1
        L_0x006f:
            java.lang.Long r1 = kotlin.coroutines.jvm.internal.b.d(r9)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.h.M(io.ktor.utils.io.h, long, kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 10 */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0056 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object N(long r11, long r13, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Long> r15) {
        /*
            r10 = this;
            boolean r0 = r15 instanceof io.ktor.utils.io.h.g
            if (r0 == 0) goto L_0x0013
            r0 = r15
            io.ktor.utils.io.h$g r0 = (io.ktor.utils.io.h.g) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.h$g r0 = new io.ktor.utils.io.h$g
            r0.<init>(r10, r15)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x003f;
                case 1: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            r3 = 0
            long r3 = r0.J$2
            long r13 = r0.J$1
            long r11 = r0.J$0
            java.lang.Object r5 = r0.L$0
            io.ktor.utils.io.h r5 = (io.ktor.utils.io.h) r5
            kotlin.p.b(r1)
            r6 = r5
            r4 = r3
            r3 = r2
            r2 = r1
            goto L_0x005d
        L_0x003f:
            kotlin.p.b(r1)
            r3 = r13
            r5 = r10
        L_0x0044:
            r0.L$0 = r5
            r0.J$0 = r11
            r0.J$1 = r13
            r0.J$2 = r3
            r6 = 1
            r0.label = r6
            java.lang.Object r6 = r5.k(r6, r0)
            if (r6 != r2) goto L_0x0057
            return r2
        L_0x0057:
            r9 = r2
            r2 = r1
            r1 = r6
            r6 = r5
            r4 = r3
            r3 = r9
        L_0x005d:
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 != 0) goto L_0x0066
            goto L_0x007f
        L_0x0066:
            io.ktor.utils.io.core.q r1 = r6.d
            long r7 = r11 - r4
            long r7 = r1.C0(r7)
            long r4 = r4 + r7
            int r1 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1))
            if (r1 >= 0) goto L_0x007f
            boolean r1 = r6.y()
            if (r1 == 0) goto L_0x007a
            goto L_0x007f
        L_0x007a:
            r1 = r2
            r2 = r3
            r3 = r4
            r5 = r6
            goto L_0x0044
        L_0x007f:
            java.lang.Long r1 = kotlin.coroutines.jvm.internal.b.d(r4)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.h.N(long, long, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: io.ktor.utils.io.h} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object f0(io.ktor.utils.io.h r4, kotlin.jvm.functions.p r5, kotlin.coroutines.d r6) {
        /*
            boolean r0 = r6 instanceof io.ktor.utils.io.h.p
            if (r0 == 0) goto L_0x0013
            r0 = r6
            io.ktor.utils.io.h$p r0 = (io.ktor.utils.io.h.p) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.h$p r0 = new io.ktor.utils.io.h$p
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
            kotlin.jvm.functions.p r5 = (kotlin.jvm.functions.p) r5
            java.lang.Object r2 = r0.L$0
            r4 = r2
            io.ktor.utils.io.h r4 = (io.ktor.utils.io.h) r4
            kotlin.p.b(r1)     // Catch:{ all -> 0x0052 }
            goto L_0x004b
        L_0x0039:
            kotlin.p.b(r1)
            r0.L$0 = r4     // Catch:{ all -> 0x0052 }
            r0.L$1 = r5     // Catch:{ all -> 0x0052 }
            r3 = 1
            r0.label = r3     // Catch:{ all -> 0x0052 }
            java.lang.Object r3 = r5.invoke(r4, r0)     // Catch:{ all -> 0x0052 }
            if (r3 != r2) goto L_0x004b
            return r2
        L_0x004b:
            r4.L()
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x0052:
            r2 = move-exception
            r4.L()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.h.f0(io.ktor.utils.io.h, kotlin.jvm.functions.p, kotlin.coroutines.d):java.lang.Object");
    }

    static /* synthetic */ Object g0(h hVar, Appendable out, int limit, kotlin.coroutines.d $completion) {
        if (!hVar.y()) {
            return io.ktor.utils.io.core.internal.f.b(out, limit, new q(hVar, (kotlin.coroutines.d) null), $completion);
        }
        Throwable cause = hVar.j;
        if (cause == null) {
            return kotlin.coroutines.jvm.internal.b.a(false);
        }
        throw cause;
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteChannelSequentialBase$readUTF8LineTo$2", f = "ByteChannelSequential.kt", l = {638}, m = "invokeSuspend")
    /* compiled from: ByteChannelSequential.kt */
    public static final class q extends kotlin.coroutines.jvm.internal.l implements kotlin.jvm.functions.p<Integer, kotlin.coroutines.d<? super io.ktor.utils.io.core.a>, Object> {
        int I$0;
        int label;
        private int p$0;
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        q(h hVar, kotlin.coroutines.d dVar) {
            super(2, dVar);
            this.this$0 = hVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            kotlin.jvm.internal.k.f(dVar, "completion");
            q qVar = new q(this.this$0, dVar);
            ((Number) obj).intValue();
            qVar.p$0 = ((Number) obj).intValue();
            return qVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((q) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object obj;
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    int size = this.p$0;
                    this.this$0.E();
                    h hVar = this.this$0;
                    this.I$0 = size;
                    this.label = 1;
                    obj = hVar.k(size, this);
                    if (obj != d) {
                        int i = size;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    int size2 = this.I$0;
                    kotlin.p.b($result);
                    obj = $result;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            if (((Boolean) obj).booleanValue()) {
                return this.this$0.T();
            }
            return null;
        }
    }

    public boolean b(@Nullable Throwable cause) {
        if (this.j != null || this.b) {
            return false;
        }
        return d(cause != null ? cause : new CancellationException("Channel cancelled"));
    }

    public boolean d(@Nullable Throwable cause) {
        if (this.b || this.j != null) {
            return false;
        }
        this.j = cause;
        this.b = true;
        if (cause != null) {
            this.d.release();
            this.c.release();
        } else {
            flush();
        }
        this.i.c();
        this.g.c();
        this.e.c();
        return true;
    }

    public final long i0(@NotNull h dst, long limit) {
        kotlin.jvm.internal.k.f(dst, "dst");
        long size = this.d.P0();
        if (size > limit) {
            return 0;
        }
        dst.c.W(this.d);
        dst.F();
        E();
        return size;
    }

    /* access modifiers changed from: protected */
    public final void F() {
        if (this.b) {
            this.c.release();
            O();
        }
        if (A() || R() == 0) {
            flush();
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 9 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object H(@org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r10) {
        /*
            r9 = this;
            boolean r0 = r10 instanceof io.ktor.utils.io.h.c
            if (r0 == 0) goto L_0x0013
            r0 = r10
            io.ktor.utils.io.h$c r0 = (io.ktor.utils.io.h.c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.h$c r0 = new io.ktor.utils.io.h$c
            r0.<init>(r9, r10)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x003b;
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
            java.lang.Object r4 = r0.L$1
            r3 = r4
            io.ktor.utils.io.p r3 = (io.ktor.utils.io.p) r3
            java.lang.Object r4 = r0.L$0
            io.ktor.utils.io.h r4 = (io.ktor.utils.io.h) r4
            kotlin.p.b(r1)
            goto L_0x009d
        L_0x003b:
            kotlin.p.b(r1)
            r9.F()
            io.ktor.utils.io.p r3 = r9.e
            r5 = 0
            kotlin.jvm.functions.a r6 = r3.b()
            java.lang.Object r6 = r6.invoke()
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x0056
            r4 = r9
            goto L_0x009e
        L_0x0056:
            r0.L$0 = r9
            r0.L$1 = r3
            r6 = 1
            r0.label = r6
            r6 = r0
            r7 = 0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r8 = io.ktor.utils.io.p.a
            boolean r8 = r8.compareAndSet(r3, r4, r6)
            if (r8 == 0) goto L_0x00a4
            kotlin.jvm.functions.a r8 = r3.b()
            java.lang.Object r8 = r8.invoke()
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x0086
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r8 = io.ktor.utils.io.p.a
            boolean r4 = r8.compareAndSet(r3, r6, r4)
            if (r4 == 0) goto L_0x0086
            kotlin.x r4 = kotlin.x.a
            goto L_0x008f
        L_0x0086:
            r4 = 0
            r9.flush()
            java.lang.Object r4 = kotlin.coroutines.intrinsics.c.d()
        L_0x008f:
            java.lang.Object r6 = kotlin.coroutines.intrinsics.c.d()
            if (r4 != r6) goto L_0x0098
            kotlin.coroutines.jvm.internal.h.c(r0)
        L_0x0098:
            if (r4 != r2) goto L_0x009b
            return r2
        L_0x009b:
            r4 = r9
            r2 = r5
        L_0x009d:
        L_0x009e:
            r4.O()
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x00a4:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            r2.<init>()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.h.H(kotlin.coroutines.d):java.lang.Object");
    }
}
