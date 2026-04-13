package io.ktor.utils.io;

import io.ktor.utils.io.core.f0;
import io.ktor.utils.io.internal.f;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.o;
import kotlinx.coroutines.e2;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ByteBufferChannel.kt */
public class a implements f, i, l, t {
    /* access modifiers changed from: private */
    public static final AtomicReferenceFieldUpdater<a, io.ktor.utils.io.internal.f> b;
    /* access modifiers changed from: private */
    public static final AtomicReferenceFieldUpdater<a, kotlin.coroutines.d<kotlin.x>> c;
    /* access modifiers changed from: private */
    public static final AtomicReferenceFieldUpdater<a, kotlin.coroutines.d<Boolean>> d;
    private static final AtomicReferenceFieldUpdater<a, C0279a> e;
    public static final b f = new b((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public volatile z1 attachedJob;
    /* access modifiers changed from: private */
    public volatile C0279a closed;
    private int g;
    private int h;
    @NotNull
    private io.ktor.utils.io.core.l i;
    @NotNull
    private io.ktor.utils.io.core.l j;
    /* access modifiers changed from: private */
    public volatile c joining;
    /* access modifiers changed from: private */
    public final io.ktor.utils.io.internal.e k;
    private final io.ktor.utils.io.internal.o l;
    private final io.ktor.utils.io.internal.a<Boolean> m;
    private final io.ktor.utils.io.internal.a<kotlin.x> n;
    private final kotlin.jvm.functions.l<kotlin.coroutines.d<? super kotlin.x>, Object> o;
    private final boolean p;
    private final io.ktor.utils.io.pool.d<f.c> q;
    private final int r;
    /* access modifiers changed from: private */
    public volatile kotlin.coroutines.d<? super Boolean> readOp;
    /* access modifiers changed from: private */
    public volatile io.ktor.utils.io.internal.f state;
    private volatile long totalBytesRead;
    private volatile long totalBytesWritten;
    /* access modifiers changed from: private */
    public volatile kotlin.coroutines.d<? super kotlin.x> writeOp;
    /* access modifiers changed from: private */
    public volatile int writeSuspensionSize;

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {3058}, m = "writeSuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class a0 extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a0(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.B1(0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {1909}, m = "writeSuspendSession$suspendImpl")
    /* compiled from: ByteBufferChannel.kt */
    public static final class b0 extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b0(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return a.E1(this.this$0, (kotlin.jvm.functions.p) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {1623, 1635}, m = "writeWhileSuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class d0 extends kotlin.coroutines.jvm.internal.d {
        long J$0;
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
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d0(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.I1((kotlin.jvm.functions.l<? super ByteBuffer, Boolean>) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {1941}, m = "awaitAtLeastSuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class f extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        Object L$0;
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
            return this.this$0.Y(0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {1326, 1386, 1392}, m = "copyDirect$ktor_io")
    /* compiled from: ByteBufferChannel.kt */
    public static final class g extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        long J$0;
        long J$1;
        Object L$0;
        Object L$1;
        Object L$10;
        Object L$11;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        Object L$8;
        Object L$9;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.e0((a) null, 0, (c) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {1778}, m = "discardSuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class h extends kotlin.coroutines.jvm.internal.d {
        long J$0;
        long J$1;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.h0(0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {1293, 1299}, m = "joinFromSuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class i extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        i(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.q0((a) null, false, (c) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {1872, 1877, 1882, 1884}, m = "lookAheadSuspend$suspendImpl")
    /* compiled from: ByteBufferChannel.kt */
    public static final class j extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        Object L$8;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        j(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return a.r0(this.this$0, (kotlin.jvm.functions.p) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {648, 649}, m = "readAvailableSuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class k extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        k(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.E0((byte[]) null, 0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {653, 654}, m = "readAvailableSuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class l extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        l(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.D0((ByteBuffer) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {658, 659}, m = "readAvailableSuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class m extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        m(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.C0((io.ktor.utils.io.core.a0) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {573}, m = "readFullySuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class n extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        n(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.G0((ByteBuffer) null, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {710}, m = "readPacketSuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class o extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        o(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.I0(0, (io.ktor.utils.io.core.n) null, (ByteBuffer) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {2184}, m = "readRemainingSuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class p extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        int I$1;
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        p(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.K0(0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {2260}, m = "readSuspendLoop")
    /* compiled from: ByteBufferChannel.kt */
    public static final class q extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        q(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.N0(0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {2116}, m = "readUTF8LineToUtf8Suspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class t extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        t(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.R0((Appendable) null, 0, (char[]) null, (CharBuffer) null, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {1245, 1247}, m = "writeFullySuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class v extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        v(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.x1((ByteBuffer) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {1255, 1257}, m = "writeFullySuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class w extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        w(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.w1((io.ktor.utils.io.core.a0) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {1532}, m = "writeFullySuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class x extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        x(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.y1((byte[]) null, 0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {1816, 1818}, m = "writePacketSuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class y extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        y(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.A1((io.ktor.utils.io.core.q) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel", f = "ByteBufferChannel.kt", l = {1546, 1548}, m = "writeSuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class z extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        z(a aVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.C1((byte[]) null, 0, 0, this);
        }
    }

    @Nullable
    public Object c(@NotNull kotlin.jvm.functions.p<? super x, ? super kotlin.coroutines.d<? super kotlin.x>, ? extends Object> pVar, @NotNull kotlin.coroutines.d<? super kotlin.x> dVar) {
        return O0(this, pVar, dVar);
    }

    @Nullable
    public Object g(long j2, @NotNull kotlin.coroutines.d<? super Long> dVar) {
        return g0(this, j2, dVar);
    }

    @Nullable
    public Object h(@NotNull ByteBuffer byteBuffer, @NotNull kotlin.coroutines.d<? super kotlin.x> dVar) {
        return u1(this, byteBuffer, dVar);
    }

    @Nullable
    public <A extends Appendable> Object i(@NotNull A a, int i2, @NotNull kotlin.coroutines.d<? super Boolean> dVar) {
        return Q0(a, i2, dVar);
    }

    @Nullable
    public Object j(@NotNull byte[] bArr, int i2, int i3, @NotNull kotlin.coroutines.d<? super Integer> dVar) {
        return B0(this, bArr, i2, i3, dVar);
    }

    @Nullable
    public <R> Object l(@NotNull kotlin.jvm.functions.p<? super t, ? super kotlin.coroutines.d<? super R>, ? extends Object> pVar, @NotNull kotlin.coroutines.d<? super R> dVar) {
        return r0(this, pVar, dVar);
    }

    @Nullable
    public Object m(@NotNull io.ktor.utils.io.core.a0 a0Var, @NotNull kotlin.coroutines.d<? super kotlin.x> dVar) {
        return t1(this, a0Var, dVar);
    }

    @Nullable
    public Object n(@NotNull kotlin.jvm.functions.p<? super b0, ? super kotlin.coroutines.d<? super kotlin.x>, ? extends Object> pVar, @NotNull kotlin.coroutines.d<? super kotlin.x> dVar) {
        return E1(this, pVar, dVar);
    }

    @Nullable
    public Object o(int i2, int i3, @NotNull kotlin.coroutines.d<? super io.ktor.utils.io.core.q> dVar) {
        return H0(this, i2, i3, dVar);
    }

    @Nullable
    public Object p(long j2, int i2, @NotNull kotlin.coroutines.d<? super io.ktor.utils.io.core.q> dVar) {
        return J0(this, j2, i2, dVar);
    }

    @Nullable
    public Object q(@NotNull byte[] bArr, int i2, int i3, @NotNull kotlin.coroutines.d<? super kotlin.x> dVar) {
        return v1(this, bArr, i2, i3, dVar);
    }

    @Nullable
    public Object r(@NotNull io.ktor.utils.io.core.a0 a0Var, @NotNull kotlin.coroutines.d<? super Integer> dVar) {
        return z0(this, a0Var, dVar);
    }

    @Nullable
    public Object r1(@NotNull byte[] bArr, int i2, int i3, @NotNull kotlin.coroutines.d<? super Integer> dVar) {
        return s1(this, bArr, i2, i3, dVar);
    }

    @Nullable
    public Object v(@NotNull ByteBuffer byteBuffer, @NotNull kotlin.coroutines.d<? super Integer> dVar) {
        return A0(this, byteBuffer, dVar);
    }

    @Nullable
    public Object x(@NotNull io.ktor.utils.io.core.q qVar, @NotNull kotlin.coroutines.d<? super kotlin.x> dVar) {
        return z1(this, qVar, dVar);
    }

    @Nullable
    public Object z(@NotNull kotlin.jvm.functions.l<? super ByteBuffer, Boolean> lVar, @NotNull kotlin.coroutines.d<? super kotlin.x> dVar) {
        return F1(this, lVar, dVar);
    }

    /* compiled from: ByteBufferChannel.kt */
    public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, kotlin.x> {
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(a aVar) {
            super(1);
            this.this$0 = aVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return kotlin.x.a;
        }

        public final void invoke(@Nullable Throwable cause) {
            this.this$0.attachedJob = null;
            if (cause != null) {
                this.this$0.b(cause);
            }
        }
    }

    /* compiled from: ByteBufferChannel.kt */
    public static final class s extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<s, kotlin.x> {
        final /* synthetic */ char[] $ca;
        final /* synthetic */ CharBuffer $cb;
        final /* synthetic */ kotlin.jvm.internal.x $consumed;
        final /* synthetic */ kotlin.jvm.internal.w $eol;
        final /* synthetic */ int $limit;
        final /* synthetic */ Appendable $out;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        s(a aVar, kotlin.jvm.internal.w wVar, Appendable appendable, char[] cArr, CharBuffer charBuffer, kotlin.jvm.internal.x xVar, int i) {
            super(1);
            this.this$0 = aVar;
            this.$eol = wVar;
            this.$out = appendable;
            this.$ca = cArr;
            this.$cb = charBuffer;
            this.$consumed = xVar;
            this.$limit = i;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((s) obj);
            return kotlin.x.a;
        }

        public final void invoke(@NotNull s $this$lookAhead) {
            boolean z;
            boolean z2;
            s sVar = this;
            kotlin.jvm.internal.k.f($this$lookAhead, "$receiver");
            kotlin.jvm.internal.w wVar = sVar.$eol;
            a this_$iv = sVar.this$0;
            Appendable out$iv = sVar.$out;
            char[] ca$iv = sVar.$ca;
            CharBuffer cb$iv = sVar.$cb;
            s $this$readLineLoop$iv = $this$lookAhead;
            int required$iv = 1;
            while (true) {
                if ((sVar.this$0.e() >= required$iv ? 1 : 0) != 0) {
                    ByteBuffer buffer$iv = $this$readLineLoop$iv.a(0, 1);
                    if (buffer$iv == null) {
                        z = false;
                        break;
                    }
                    int before$iv = buffer$iv.position();
                    if (buffer$iv.remaining() < required$iv) {
                        this_$iv.a1(buffer$iv, required$iv);
                    }
                    char[] cArr = sVar.$ca;
                    a this_$iv2 = this_$iv;
                    long rc$iv = io.ktor.utils.io.internal.m.a(buffer$iv, cArr, 0, Math.min(cArr.length, sVar.$limit - sVar.$consumed.element));
                    $this$readLineLoop$iv.w(buffer$iv.position() - before$iv);
                    int decoded$iv = (int) (rc$iv >> 32);
                    int rcRequired$iv = (int) (4294967295L & rc$iv);
                    if (rcRequired$iv == -1) {
                        required$iv = 0;
                    } else if (rcRequired$iv != 0 || !buffer$iv.hasRemaining()) {
                        required$iv = Math.max(1, rcRequired$iv);
                    } else {
                        required$iv = -1;
                    }
                    sVar.$consumed.element += decoded$iv;
                    if (out$iv instanceof StringBuilder) {
                        z = false;
                        ((StringBuilder) out$iv).append(ca$iv, 0, decoded$iv);
                    } else {
                        z = false;
                        out$iv.append(cb$iv, 0, decoded$iv);
                    }
                    if (required$iv <= 0) {
                        break;
                    }
                    sVar = this;
                    s sVar2 = $this$lookAhead;
                    this_$iv = this_$iv2;
                } else {
                    a aVar = this_$iv;
                    z = false;
                    break;
                }
            }
            switch (required$iv) {
                case 0:
                    z2 = true;
                    break;
                default:
                    z2 = z;
                    break;
            }
            wVar.element = z2;
        }
    }

    public a(boolean autoFlush, @NotNull io.ktor.utils.io.pool.d<f.c> pool, int reservedSize) {
        kotlin.jvm.internal.k.f(pool, "pool");
        this.p = autoFlush;
        this.q = pool;
        this.r = reservedSize;
        this.state = f.a.c;
        io.ktor.utils.io.core.l lVar = io.ktor.utils.io.core.l.BIG_ENDIAN;
        this.i = lVar;
        this.j = lVar;
        this.k = new io.ktor.utils.io.internal.e(this);
        this.l = new io.ktor.utils.io.internal.o(this);
        this.m = new io.ktor.utils.io.internal.a<>();
        this.n = new io.ktor.utils.io.internal.a<>();
        this.o = new c0(this);
    }

    public boolean A() {
        return this.p;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ a(boolean z2, io.ktor.utils.io.pool.d<f.c> dVar, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(z2, (i3 & 2) != 0 ? io.ktor.utils.io.internal.d.b() : dVar, (i3 & 4) != 0 ? 8 : i2);
    }

    @NotNull
    public final io.ktor.utils.io.internal.f f0() {
        return this.state;
    }

    @Nullable
    public final c l0() {
        return this.joining;
    }

    public void s(@NotNull z1 job) {
        kotlin.jvm.internal.k.f(job, "job");
        z1 z1Var = this.attachedJob;
        if (z1Var != null) {
            z1.a.a(z1Var, (CancellationException) null, 1, (Object) null);
        }
        this.attachedJob = job;
        z1.a.d(job, true, false, new e(this), 2, (Object) null);
    }

    @NotNull
    public io.ktor.utils.io.core.l m0() {
        return this.i;
    }

    @NotNull
    public io.ktor.utils.io.core.l o0() {
        return this.j;
    }

    public void d1(@NotNull io.ktor.utils.io.core.l newOrder) {
        a e2;
        kotlin.jvm.internal.k.f(newOrder, "newOrder");
        if (this.j != newOrder) {
            this.j = newOrder;
            c cVar = this.joining;
            if (cVar != null && (e2 = cVar.e()) != null) {
                e2.d1(newOrder);
            }
        }
    }

    public int e() {
        return this.state.b.availableForRead;
    }

    public boolean y() {
        return this.state == f.C0290f.c && this.closed != null;
    }

    public boolean f() {
        return this.closed != null;
    }

    public void b1(long j2) {
        this.totalBytesRead = j2;
    }

    public long t() {
        return this.totalBytesRead;
    }

    public void c1(long j2) {
        this.totalBytesWritten = j2;
    }

    public long n0() {
        return this.totalBytesWritten;
    }

    @Nullable
    public Throwable k0() {
        C0279a aVar = this.closed;
        if (aVar != null) {
            return aVar.b();
        }
        return null;
    }

    public boolean d(@Nullable Throwable cause) {
        c it;
        if (this.closed != null) {
            return false;
        }
        C0279a newClosed = cause == null ? C0279a.b.a() : new C0279a(cause);
        this.state.b.e();
        if (!e.compareAndSet(this, (Object) null, newClosed)) {
            return false;
        }
        this.state.b.e();
        if (this.state.b.g() || cause != null) {
            l1();
        }
        X0(cause);
        if (this.state == f.C0290f.c && (it = this.joining) != null) {
            i0(it);
        }
        if (cause != null) {
            z1 z1Var = this.attachedJob;
            if (z1Var != null) {
                z1.a.a(z1Var, (CancellationException) null, 1, (Object) null);
            }
            this.m.g(cause);
            this.n.g(cause);
        } else {
            this.n.g(new ClosedWriteChannelException("Byte channel was closed"));
            this.m.d(Boolean.valueOf(this.state.b.e()));
        }
        return true;
    }

    public boolean b(@Nullable Throwable cause) {
        return d(cause != null ? cause : new CancellationException("Channel has been cancelled"));
    }

    /* access modifiers changed from: private */
    public final void j0(int minReadSize, int minWriteSize) {
        io.ktor.utils.io.internal.f s2;
        f.C0290f fVar;
        a e2;
        c cVar = this.joining;
        if (!(cVar == null || (e2 = cVar.e()) == null)) {
            e2.flush();
        }
        do {
            s2 = this.state;
            fVar = f.C0290f.c;
            if (s2 != fVar) {
                s2.b.e();
            } else {
                return;
            }
        } while (s2 != this.state);
        int avw = s2.b.availableForWrite;
        if (s2.b.availableForRead >= minReadSize) {
            Y0();
        }
        c joining2 = this.joining;
        if (avw < minWriteSize) {
            return;
        }
        if (joining2 == null || this.state == fVar) {
            Z0();
        }
    }

    public void flush() {
        j0(1, 1);
    }

    public final void u0(@NotNull ByteBuffer buffer, int lockedSpace) {
        kotlin.jvm.internal.k.f(buffer, "buffer");
        t0(buffer, o0(), this.h, lockedSpace);
    }

    private final void t0(@NotNull ByteBuffer $this$prepareBuffer, io.ktor.utils.io.core.l order, int position, int available) {
        boolean z2 = true;
        if (position >= 0) {
            if (available < 0) {
                z2 = false;
            }
            if (z2) {
                int bufferLimit = $this$prepareBuffer.capacity() - this.r;
                $this$prepareBuffer.order(order.getNioOrder());
                $this$prepareBuffer.limit(kotlin.ranges.n.e(position + available, bufferLimit));
                $this$prepareBuffer.position(position);
                return;
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    /*  JADX ERROR: JadxRuntimeException in pass: CodeShrinkVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Don't wrap MOVE or CONST insns: 0x0075: MOVE  (r8v1 'newValue$iv$iv' java.lang.Object) = (r12v0 java.lang.Object)
        	at jadx.core.dex.instructions.args.InsnArg.wrapArg(InsnArg.java:164)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.assignInline(CodeShrinkVisitor.java:133)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.checkInline(CodeShrinkVisitor.java:118)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:65)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:43)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.visit(CodeShrinkVisitor.java:35)
        */
    @org.jetbrains.annotations.Nullable
    public final java.nio.ByteBuffer g1() {
        /*
            r15 = this;
            kotlin.coroutines.d<? super kotlin.x> r0 = r15.writeOp
            if (r0 != 0) goto L_0x00cb
            r1 = 0
            r0 = r1
            r2 = r15
            r3 = 0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r4 = b
            r5 = r2
            r6 = 0
        L_0x000e:
            r7 = 0
            io.ktor.utils.io.internal.f r7 = r2.state
            r8 = r7
            r9 = 0
            io.ktor.utils.io.a$c r10 = r15.joining
            if (r10 == 0) goto L_0x0023
            if (r0 == 0) goto L_0x0022
            r10 = r0
            r11 = 0
            r15.S0(r10)
        L_0x0022:
            return r1
        L_0x0023:
            io.ktor.utils.io.a$a r10 = r15.closed
            if (r10 == 0) goto L_0x003a
            if (r0 == 0) goto L_0x002e
            r1 = r0
            r10 = 0
            r15.S0(r1)
        L_0x002e:
            io.ktor.utils.io.a$a r1 = r15.closed
            if (r1 != 0) goto L_0x0035
            kotlin.jvm.internal.k.n()
        L_0x0035:
            java.lang.Throwable r1 = r1.c()
            throw r1
        L_0x003a:
            io.ktor.utils.io.internal.f$a r10 = io.ktor.utils.io.internal.f.a.c
            if (r8 != r10) goto L_0x0052
            if (r0 == 0) goto L_0x0042
            r11 = r0
            goto L_0x004c
        L_0x0042:
            io.ktor.utils.io.internal.f$c r11 = r15.s0()
            r12 = r11
            r13 = 0
            r0 = r12
            r14 = r11
            r11 = r0
            r0 = r14
        L_0x004c:
            io.ktor.utils.io.internal.f$g r12 = r0.e()
            r0 = r11
            goto L_0x0072
        L_0x0052:
            io.ktor.utils.io.internal.f$f r11 = io.ktor.utils.io.internal.f.C0290f.c
            if (r8 != r11) goto L_0x006e
            if (r0 == 0) goto L_0x005d
            r10 = r0
            r11 = 0
            r15.S0(r10)
        L_0x005d:
            io.ktor.utils.io.a$c r10 = r15.joining
            if (r10 == 0) goto L_0x0062
            return r1
        L_0x0062:
            io.ktor.utils.io.a$a r1 = r15.closed
            if (r1 != 0) goto L_0x0069
            kotlin.jvm.internal.k.n()
        L_0x0069:
            java.lang.Throwable r1 = r1.c()
            throw r1
        L_0x006e:
            io.ktor.utils.io.internal.f r12 = r8.e()
        L_0x0072:
            if (r12 == 0) goto L_0x00c9
            r8 = r12
            if (r7 == r8) goto L_0x0080
            boolean r9 = r4.compareAndSet(r5, r7, r8)
            if (r9 == 0) goto L_0x007f
            goto L_0x0080
        L_0x007f:
            goto L_0x000e
        L_0x0080:
            kotlin.n r1 = new kotlin.n
            r1.<init>(r7, r8)
            java.lang.Object r2 = r1.component1()
            io.ktor.utils.io.internal.f r2 = (io.ktor.utils.io.internal.f) r2
            java.lang.Object r1 = r1.component2()
            io.ktor.utils.io.internal.f r1 = (io.ktor.utils.io.internal.f) r1
            io.ktor.utils.io.a$a r3 = r15.closed
            if (r3 == 0) goto L_0x00a8
            r15.W0()
            r15.l1()
            io.ktor.utils.io.a$a r3 = r15.closed
            if (r3 != 0) goto L_0x00a3
            kotlin.jvm.internal.k.n()
        L_0x00a3:
            java.lang.Throwable r3 = r3.c()
            throw r3
        L_0x00a8:
            java.nio.ByteBuffer r3 = r1.c()
            if (r0 == 0) goto L_0x00b7
            r4 = r0
            r5 = 0
            if (r2 == r10) goto L_0x00b5
            r15.S0(r4)
        L_0x00b5:
        L_0x00b7:
            r4 = r3
            r5 = 0
            io.ktor.utils.io.core.l r6 = r15.o0()
            int r7 = r15.h
            io.ktor.utils.io.internal.k r8 = r1.b
            int r8 = r8.availableForWrite
            r15.t0(r4, r6, r7, r8)
            return r3
        L_0x00c9:
            goto L_0x000e
        L_0x00cb:
            r1 = 0
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Write operation is already in progress: "
            r3.append(r4)
            r3.append(r0)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.g1():java.nio.ByteBuffer");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: CodeShrinkVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Don't wrap MOVE or CONST insns: 0x002d: MOVE  (r7v1 'newValue$iv$iv' java.lang.Object) = (r9v1 'writeStopped' io.ktor.utils.io.internal.f)
        	at jadx.core.dex.instructions.args.InsnArg.wrapArg(InsnArg.java:164)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.assignInline(CodeShrinkVisitor.java:133)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.checkInline(CodeShrinkVisitor.java:118)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:65)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:43)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.visit(CodeShrinkVisitor.java:35)
        */
    public final void W0() {
        /*
            r11 = this;
            r0 = 0
            r1 = r11
            r2 = 0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r3 = b
            r4 = r1
            r5 = 0
        L_0x0009:
            r6 = 0
            io.ktor.utils.io.internal.f r6 = r1.state
            r7 = r6
            r8 = 0
            io.ktor.utils.io.internal.f r9 = r7.g()
            boolean r10 = r9 instanceof io.ktor.utils.io.internal.f.b
            if (r10 == 0) goto L_0x0028
            io.ktor.utils.io.internal.k r10 = r9.b
            boolean r10 = r10.g()
            if (r10 == 0) goto L_0x0028
            r0 = r9
            io.ktor.utils.io.internal.f$b r0 = (io.ktor.utils.io.internal.f.b) r0
            io.ktor.utils.io.internal.f$a r10 = io.ktor.utils.io.internal.f.a.c
            r9 = r10
            goto L_0x0029
        L_0x0028:
        L_0x0029:
            if (r9 == 0) goto L_0x0054
            r7 = r9
            if (r6 == r7) goto L_0x0038
            boolean r8 = r3.compareAndSet(r4, r6, r7)
            if (r8 == 0) goto L_0x0037
            goto L_0x0038
        L_0x0037:
            goto L_0x0009
        L_0x0038:
            kotlin.n r8 = new kotlin.n
            r8.<init>(r6, r7)
            java.lang.Object r1 = r8.component2()
            io.ktor.utils.io.internal.f r1 = (io.ktor.utils.io.internal.f) r1
            io.ktor.utils.io.internal.f$a r2 = io.ktor.utils.io.internal.f.a.c
            if (r1 != r2) goto L_0x0053
            if (r0 == 0) goto L_0x0053
            r2 = r0
            r3 = 0
            io.ktor.utils.io.internal.f$c r4 = r2.h()
            r11.S0(r4)
        L_0x0053:
            return
        L_0x0054:
            goto L_0x0009
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.W0():void");
    }

    /* access modifiers changed from: private */
    public final ByteBuffer f1() {
        Throwable it;
        Throwable it2;
        io.ktor.utils.io.internal.f newState$iv;
        AtomicReferenceFieldUpdater updater$iv = b;
        while (true) {
            io.ktor.utils.io.internal.f old$iv = this.state;
            io.ktor.utils.io.internal.f state2 = old$iv;
            if (kotlin.jvm.internal.k.a(state2, f.C0290f.c)) {
                C0279a aVar = this.closed;
                if (aVar == null || (it = aVar.b()) == null) {
                    return null;
                }
                throw it;
            } else if (kotlin.jvm.internal.k.a(state2, f.a.c)) {
                C0279a aVar2 = this.closed;
                if (aVar2 == null || (it2 = aVar2.b()) == null) {
                    return null;
                }
                throw it2;
            } else if (state2.b.availableForRead == 0) {
                return null;
            } else {
                newState$iv = state2.d();
                if (newState$iv == null || (old$iv != newState$iv && !updater$iv.compareAndSet(this, old$iv, newState$iv))) {
                }
            }
        }
        io.ktor.utils.io.internal.f newState = newState$iv;
        ByteBuffer $this$apply = newState.b();
        t0($this$apply, m0(), this.g, newState.b.availableForRead);
        return $this$apply;
    }

    /* access modifiers changed from: private */
    /*  JADX ERROR: JadxRuntimeException in pass: CodeShrinkVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Don't wrap MOVE or CONST insns: 0x0040: MOVE  (r6v1 'newState$iv' io.ktor.utils.io.internal.f) = (r8v1 'readStopped' io.ktor.utils.io.internal.f)
        	at jadx.core.dex.instructions.args.InsnArg.wrapArg(InsnArg.java:164)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.assignInline(CodeShrinkVisitor.java:133)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.checkInline(CodeShrinkVisitor.java:118)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:65)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:43)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.visit(CodeShrinkVisitor.java:35)
        */
    public final void V0() {
        /*
            r11 = this;
            r0 = 0
            r1 = r0
            r2 = r11
            r3 = 0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r4 = b
        L_0x0008:
            io.ktor.utils.io.internal.f r5 = r2.state
            r6 = r5
            r7 = 0
            if (r1 == 0) goto L_0x001e
            r8 = r1
            r9 = 0
            io.ktor.utils.io.internal.k r10 = r8.b
            r10.i()
            r11.Z0()
            r1 = r0
        L_0x001e:
            io.ktor.utils.io.internal.f r8 = r6.f()
            boolean r9 = r8 instanceof io.ktor.utils.io.internal.f.b
            if (r9 == 0) goto L_0x003b
            io.ktor.utils.io.internal.f r9 = r11.state
            if (r9 != r6) goto L_0x0039
            io.ktor.utils.io.internal.k r9 = r8.b
            boolean r9 = r9.j()
            if (r9 == 0) goto L_0x0039
            r1 = r8
            io.ktor.utils.io.internal.f$b r1 = (io.ktor.utils.io.internal.f.b) r1
            io.ktor.utils.io.internal.f$a r9 = io.ktor.utils.io.internal.f.a.c
            r8 = r9
            goto L_0x003c
        L_0x0039:
            goto L_0x003c
        L_0x003b:
        L_0x003c:
            if (r8 == 0) goto L_0x0092
            r6 = r8
            if (r5 == r6) goto L_0x004b
            boolean r7 = r4.compareAndSet(r2, r5, r6)
            if (r7 == 0) goto L_0x004a
            goto L_0x004b
        L_0x004a:
            goto L_0x0008
        L_0x004b:
            r0 = r6
            io.ktor.utils.io.internal.f$a r2 = io.ktor.utils.io.internal.f.a.c
            if (r0 != r2) goto L_0x005f
            if (r1 == 0) goto L_0x005b
            r2 = r1
            r3 = 0
            io.ktor.utils.io.internal.f$c r4 = r2.h()
            r11.S0(r4)
        L_0x005b:
            r11.Z0()
            goto L_0x0090
        L_0x005f:
            boolean r3 = r0 instanceof io.ktor.utils.io.internal.f.b
            if (r3 == 0) goto L_0x0090
            io.ktor.utils.io.internal.k r3 = r0.b
            boolean r3 = r3.g()
            if (r3 == 0) goto L_0x0090
            io.ktor.utils.io.internal.k r3 = r0.b
            boolean r3 = r3.j()
            if (r3 == 0) goto L_0x0090
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater<io.ktor.utils.io.a, io.ktor.utils.io.internal.f> r3 = b
            boolean r2 = r3.compareAndSet(r11, r0, r2)
            if (r2 == 0) goto L_0x0090
            io.ktor.utils.io.internal.k r2 = r0.b
            r2.i()
            r2 = r0
            io.ktor.utils.io.internal.f$b r2 = (io.ktor.utils.io.internal.f.b) r2
            io.ktor.utils.io.internal.f$c r2 = r2.h()
            r11.S0(r2)
            r11.Z0()
        L_0x0090:
            return
        L_0x0092:
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.V0():void");
    }

    private final c e1(a delegate, boolean delegateClose) {
        if (this != delegate) {
            c joined = new c(delegate, delegateClose);
            delegate.d1(o0());
            this.joining = joined;
            C0279a alreadyClosed = this.closed;
            if (alreadyClosed == null) {
                flush();
            } else if (alreadyClosed.b() != null) {
                delegate.d(alreadyClosed.b());
            } else if (!delegateClose || this.state != f.C0290f.c) {
                delegate.flush();
            } else {
                m.a(delegate);
            }
            return joined;
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    private final boolean j1(c joined) {
        if (!k1(true)) {
            return false;
        }
        i0(joined);
        kotlin.coroutines.d dVar = (kotlin.coroutines.d) d.getAndSet(this, (Object) null);
        if (dVar != null) {
            IllegalStateException illegalStateException = new IllegalStateException("Joining is in progress");
            o.a aVar = kotlin.o.Companion;
            dVar.resumeWith(kotlin.o.m17constructorimpl(kotlin.p.a(illegalStateException)));
        }
        Z0();
        return true;
    }

    public final boolean l1() {
        if (this.closed == null || !k1(false)) {
            return false;
        }
        c it = this.joining;
        if (it != null) {
            i0(it);
        }
        Y0();
        Z0();
        return true;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: CodeShrinkVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Don't wrap MOVE or CONST insns: 0x006f: MOVE  (r8v1 'newValue$iv$iv' java.lang.Object) = (r11v0 java.lang.Object)
        	at jadx.core.dex.instructions.args.InsnArg.wrapArg(InsnArg.java:164)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.assignInline(CodeShrinkVisitor.java:133)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.checkInline(CodeShrinkVisitor.java:118)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:65)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:43)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.visit(CodeShrinkVisitor.java:35)
        */
    private final boolean k1(boolean r15) {
        /*
            r14 = this;
            r0 = 0
            r1 = r0
            r2 = r14
            r3 = 0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r4 = b
            r5 = r2
            r6 = 0
        L_0x000a:
            r7 = 0
            io.ktor.utils.io.internal.f r7 = r2.state
            r8 = r7
            r9 = 0
            if (r1 == 0) goto L_0x0021
            r10 = r1
            r11 = 0
            r1 = r0
            io.ktor.utils.io.internal.k r12 = r10.b
            r12.i()
            r14.Z0()
        L_0x0021:
            io.ktor.utils.io.a$a r10 = r14.closed
            io.ktor.utils.io.internal.f$f r11 = io.ktor.utils.io.internal.f.C0290f.c
            r12 = 1
            if (r8 != r11) goto L_0x002a
            return r12
        L_0x002a:
            io.ktor.utils.io.internal.f$a r13 = io.ktor.utils.io.internal.f.a.c
            if (r8 != r13) goto L_0x002f
            goto L_0x006c
        L_0x002f:
            if (r10 == 0) goto L_0x0056
            boolean r13 = r8 instanceof io.ktor.utils.io.internal.f.b
            if (r13 == 0) goto L_0x0056
            io.ktor.utils.io.internal.k r13 = r8.b
            boolean r13 = r13.j()
            if (r13 != 0) goto L_0x0043
            java.lang.Throwable r13 = r10.b()
            if (r13 == 0) goto L_0x0056
        L_0x0043:
            java.lang.Throwable r13 = r10.b()
            if (r13 == 0) goto L_0x004e
            io.ktor.utils.io.internal.k r13 = r8.b
            r13.f()
        L_0x004e:
            r13 = r8
            io.ktor.utils.io.internal.f$b r13 = (io.ktor.utils.io.internal.f.b) r13
            io.ktor.utils.io.internal.f$c r1 = r13.h()
            goto L_0x006c
        L_0x0056:
            if (r15 == 0) goto L_0x0090
            boolean r13 = r8 instanceof io.ktor.utils.io.internal.f.b
            if (r13 == 0) goto L_0x0090
            io.ktor.utils.io.internal.k r13 = r8.b
            boolean r13 = r13.j()
            if (r13 == 0) goto L_0x0090
            r13 = r8
            io.ktor.utils.io.internal.f$b r13 = (io.ktor.utils.io.internal.f.b) r13
            io.ktor.utils.io.internal.f$c r1 = r13.h()
        L_0x006c:
            if (r11 == 0) goto L_0x008e
            r8 = r11
            if (r7 == r8) goto L_0x007a
            boolean r9 = r4.compareAndSet(r5, r7, r8)
            if (r9 == 0) goto L_0x0079
            goto L_0x007a
        L_0x0079:
            goto L_0x000a
        L_0x007a:
            kotlin.n r0 = new kotlin.n
            r0.<init>(r7, r8)
            if (r1 == 0) goto L_0x008d
            r0 = r1
            r2 = 0
            io.ktor.utils.io.internal.f r3 = r14.state
            if (r3 != r11) goto L_0x008b
            r14.S0(r0)
        L_0x008b:
        L_0x008d:
            return r12
        L_0x008e:
            goto L_0x000a
        L_0x0090:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.k1(boolean):boolean");
    }

    private final int d0(@NotNull ByteBuffer $this$carryIndex, int idx) {
        return idx >= $this$carryIndex.capacity() - this.r ? idx - ($this$carryIndex.capacity() - this.r) : idx;
    }

    private final int w0(ByteBuffer dst) {
        int consumed = 0;
        ByteBuffer V = f1();
        if (V != null) {
            ByteBuffer buffer$iv = V;
            io.ktor.utils.io.internal.k capacity$iv = this.state.b;
            try {
                if (capacity$iv.availableForRead != 0) {
                    ByteBuffer $this$reading = buffer$iv;
                    io.ktor.utils.io.internal.k state2 = capacity$iv;
                    ByteBuffer buffer = $this$reading;
                    int bufferLimit = buffer.capacity() - this.r;
                    while (true) {
                        int dstRemaining = dst.remaining();
                        if (dstRemaining == 0) {
                            break;
                        }
                        int position = this.g;
                        int part = state2.k(Math.min(bufferLimit - position, dstRemaining));
                        if (part == 0) {
                            break;
                        }
                        buffer.limit(position + part);
                        buffer.position(position);
                        try {
                            dst.put(buffer);
                            a0($this$reading, state2, part);
                            consumed += part;
                        } catch (Throwable th) {
                            th = th;
                            V0();
                            l1();
                            throw th;
                        }
                    }
                }
                V0();
                l1();
                ByteBuffer byteBuffer = dst;
            } catch (Throwable th2) {
                th = th2;
                ByteBuffer byteBuffer2 = dst;
                V0();
                l1();
                throw th;
            }
        } else {
            ByteBuffer byteBuffer3 = dst;
        }
        return consumed;
    }

    static /* synthetic */ int y0(a aVar, io.ktor.utils.io.core.c cVar, int i2, int i3, int i4, Object obj) {
        if (obj == null) {
            if ((i4 & 2) != 0) {
                i2 = 0;
            }
            if ((i4 & 4) != 0) {
                io.ktor.utils.io.core.c this_$iv = cVar;
                i3 = this_$iv.m() - this_$iv.s();
            }
            return aVar.v0(cVar, i2, i3);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: readAsMuchAsPossible");
    }

    private final int v0(io.ktor.utils.io.core.c dst, int consumed0, int max) {
        int consumed;
        int dstSize;
        int consumed02 = consumed0;
        int max2 = max;
        while (true) {
            consumed = 0;
            ByteBuffer V = f1();
            boolean z2 = true;
            if (V != null) {
                ByteBuffer buffer$iv = V;
                io.ktor.utils.io.internal.k capacity$iv = this.state.b;
                try {
                    if (capacity$iv.availableForRead == 0) {
                        V0();
                        l1();
                        io.ktor.utils.io.core.c cVar = dst;
                        dstSize = 0;
                    } else {
                        ByteBuffer $this$reading = buffer$iv;
                        io.ktor.utils.io.internal.k it = capacity$iv;
                        io.ktor.utils.io.core.c this_$iv = dst;
                        int dstSize2 = this_$iv.m() - this_$iv.s();
                        int part = it.k(Math.min($this$reading.remaining(), Math.min(dstSize2, max2)));
                        if (part > 0) {
                            consumed = 0 + part;
                            if (dstSize2 < $this$reading.remaining()) {
                                $this$reading.limit($this$reading.position() + dstSize2);
                            }
                            try {
                                io.ktor.utils.io.core.h.a(dst, $this$reading);
                                a0($this$reading, it, part);
                                dstSize = 1;
                            } catch (Throwable th) {
                                th = th;
                                V0();
                                l1();
                                throw th;
                            }
                        } else {
                            io.ktor.utils.io.core.c cVar2 = dst;
                            dstSize = 0;
                        }
                        V0();
                        l1();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    io.ktor.utils.io.core.c cVar3 = dst;
                    V0();
                    l1();
                    throw th;
                }
            } else {
                io.ktor.utils.io.core.c cVar4 = dst;
                dstSize = 0;
            }
            if (dstSize == 0) {
                break;
            }
            io.ktor.utils.io.core.c $this$canWrite$iv = dst;
            if ($this$canWrite$iv.m() <= $this$canWrite$iv.s()) {
                z2 = false;
            }
            if (!z2 || this.state.b.availableForRead <= 0) {
                break;
            }
            consumed02 += consumed;
            max2 -= consumed;
        }
        return consumed + consumed02;
    }

    private final int x0(byte[] dst, int offset, int length) {
        int consumed = 0;
        boolean z2 = false;
        ByteBuffer V = f1();
        if (V != null) {
            ByteBuffer buffer$iv = V;
            io.ktor.utils.io.internal.k capacity$iv = this.state.b;
            try {
                if (capacity$iv.availableForRead != 0) {
                    ByteBuffer $this$reading = buffer$iv;
                    io.ktor.utils.io.internal.k state2 = capacity$iv;
                    ByteBuffer buffer = $this$reading;
                    int bufferLimit = buffer.capacity() - this.r;
                    while (true) {
                        int lengthRemaining = length - consumed;
                        if (lengthRemaining == 0) {
                            break;
                        }
                        int position = this.g;
                        int part = state2.k(Math.min(bufferLimit - position, lengthRemaining));
                        if (part == 0) {
                            break;
                        }
                        buffer.limit(position + part);
                        buffer.position(position);
                        boolean z3 = z2;
                        try {
                            buffer.get(dst, offset + consumed, part);
                            a0($this$reading, state2, part);
                            consumed += part;
                            z2 = z3;
                        } catch (Throwable th) {
                            th = th;
                            V0();
                            l1();
                            throw th;
                        }
                    }
                }
                V0();
                l1();
                byte[] bArr = dst;
            } catch (Throwable th2) {
                th = th2;
                boolean z4 = z2;
                byte[] bArr2 = dst;
                V0();
                l1();
                throw th;
            }
        } else {
            byte[] bArr3 = dst;
        }
        return consumed;
    }

    @Nullable
    public final Object F0(@NotNull ByteBuffer dst, @NotNull kotlin.coroutines.d<? super Integer> $completion) {
        int rc = w0(dst);
        if (!dst.hasRemaining()) {
            return kotlin.coroutines.jvm.internal.b.c(rc);
        }
        return G0(dst, rc, $completion);
    }

    /* Debug info: failed to restart local var, previous not found, register: 8 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v3, resolved type: java.nio.ByteBuffer} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object G0(@org.jetbrains.annotations.NotNull java.nio.ByteBuffer r9, int r10, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Integer> r11) {
        /*
            r8 = this;
            boolean r0 = r11 instanceof io.ktor.utils.io.a.n
            if (r0 == 0) goto L_0x0013
            r0 = r11
            io.ktor.utils.io.a$n r0 = (io.ktor.utils.io.a.n) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.a$n r0 = new io.ktor.utils.io.a$n
            r0.<init>(r8, r11)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0041;
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
            int r3 = r0.I$1
            int r10 = r0.I$0
            java.lang.Object r4 = r0.L$1
            r9 = r4
            java.nio.ByteBuffer r9 = (java.nio.ByteBuffer) r9
            java.lang.Object r4 = r0.L$0
            io.ktor.utils.io.a r4 = (io.ktor.utils.io.a) r4
            kotlin.p.b(r1)
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            goto L_0x0064
        L_0x0041:
            kotlin.p.b(r1)
            r3 = r10
            r4 = r8
        L_0x0046:
            boolean r5 = r9.hasRemaining()
            if (r5 == 0) goto L_0x0097
            r0.L$0 = r4
            r0.L$1 = r9
            r0.I$0 = r10
            r0.I$1 = r3
            r5 = 1
            r0.label = r5
            java.lang.Object r5 = r4.L0(r5, r0)
            if (r5 != r2) goto L_0x005e
            return r2
        L_0x005e:
            r7 = r2
            r2 = r1
            r1 = r5
            r5 = r4
            r4 = r3
            r3 = r7
        L_0x0064:
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L_0x0077
            int r1 = r5.w0(r9)
            int r1 = r1 + r4
            r4 = r5
            r7 = r3
            r3 = r1
            r1 = r2
            r2 = r7
            goto L_0x0046
        L_0x0077:
            kotlinx.coroutines.channels.ClosedReceiveChannelException r1 = new kotlinx.coroutines.channels.ClosedReceiveChannelException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r6 = "Unexpected EOF: expected "
            r3.append(r6)
            int r6 = r9.remaining()
            r3.append(r6)
            java.lang.String r6 = " more bytes"
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            r1.<init>(r3)
            throw r1
        L_0x0097:
            java.lang.Integer r2 = kotlin.coroutines.jvm.internal.b.c(r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.G0(java.nio.ByteBuffer, int, kotlin.coroutines.d):java.lang.Object");
    }

    static /* synthetic */ Object B0(a aVar, byte[] dst, int offset, int length, kotlin.coroutines.d $completion) {
        int consumed = aVar.x0(dst, offset, length);
        if (consumed != 0 || aVar.closed == null) {
            if (consumed > 0 || length == 0) {
                return kotlin.coroutines.jvm.internal.b.c(consumed);
            }
            return aVar.E0(dst, offset, length, $completion);
        } else if (aVar.state.b.e()) {
            return kotlin.coroutines.jvm.internal.b.c(aVar.x0(dst, offset, length));
        } else {
            return kotlin.coroutines.jvm.internal.b.c(-1);
        }
    }

    static /* synthetic */ Object A0(a aVar, ByteBuffer dst, kotlin.coroutines.d $completion) {
        int consumed = aVar.w0(dst);
        if (consumed != 0 || aVar.closed == null) {
            if (consumed > 0 || !dst.hasRemaining()) {
                return kotlin.coroutines.jvm.internal.b.c(consumed);
            }
            return aVar.D0(dst, $completion);
        } else if (aVar.state.b.e()) {
            return kotlin.coroutines.jvm.internal.b.c(aVar.w0(dst));
        } else {
            return kotlin.coroutines.jvm.internal.b.c(-1);
        }
    }

    static /* synthetic */ Object z0(a aVar, io.ktor.utils.io.core.a0 dst, kotlin.coroutines.d $completion) {
        int consumed = y0(aVar, dst, 0, 0, 6, (Object) null);
        if (consumed != 0 || aVar.closed == null) {
            if (consumed <= 0) {
                io.ktor.utils.io.core.c $this$canWrite$iv = dst;
                if ($this$canWrite$iv.m() > $this$canWrite$iv.s()) {
                    return aVar.C0(dst, $completion);
                }
            }
            return kotlin.coroutines.jvm.internal.b.c(consumed);
        } else if (aVar.state.b.e()) {
            return kotlin.coroutines.jvm.internal.b.c(y0(aVar, dst, 0, 0, 6, (Object) null));
        } else {
            return kotlin.coroutines.jvm.internal.b.c(-1);
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 5 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: byte[]} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x006e  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object E0(@org.jetbrains.annotations.NotNull byte[] r6, int r7, int r8, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Integer> r9) {
        /*
            r5 = this;
            boolean r0 = r9 instanceof io.ktor.utils.io.a.k
            if (r0 == 0) goto L_0x0013
            r0 = r9
            io.ktor.utils.io.a$k r0 = (io.ktor.utils.io.a.k) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.a$k r0 = new io.ktor.utils.io.a$k
            r0.<init>(r5, r9)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x004f;
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
            io.ktor.utils.io.a r2 = (io.ktor.utils.io.a) r2
            kotlin.p.b(r1)
            r4 = r1
            goto L_0x0087
        L_0x003d:
            int r8 = r0.I$1
            int r7 = r0.I$0
            java.lang.Object r3 = r0.L$1
            r6 = r3
            byte[] r6 = (byte[]) r6
            java.lang.Object r3 = r0.L$0
            io.ktor.utils.io.a r3 = (io.ktor.utils.io.a) r3
            kotlin.p.b(r1)
            r4 = r1
            goto L_0x0066
        L_0x004f:
            kotlin.p.b(r1)
            r0.L$0 = r5
            r0.L$1 = r6
            r0.I$0 = r7
            r0.I$1 = r8
            r3 = 1
            r0.label = r3
            java.lang.Object r3 = r5.L0(r3, r0)
            if (r3 != r2) goto L_0x0064
            return r2
        L_0x0064:
            r4 = r3
            r3 = r5
        L_0x0066:
            java.lang.Boolean r4 = (java.lang.Boolean) r4
            boolean r4 = r4.booleanValue()
            if (r4 != 0) goto L_0x0074
            r2 = -1
            java.lang.Integer r2 = kotlin.coroutines.jvm.internal.b.c(r2)
            return r2
        L_0x0074:
            r0.L$0 = r3
            r0.L$1 = r6
            r0.I$0 = r7
            r0.I$1 = r8
            r4 = 2
            r0.label = r4
            java.lang.Object r4 = r3.j(r6, r7, r8, r0)
            if (r4 != r2) goto L_0x0086
            return r2
        L_0x0086:
            r2 = r3
        L_0x0087:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.E0(byte[], int, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 5 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: java.nio.ByteBuffer} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object D0(@org.jetbrains.annotations.NotNull java.nio.ByteBuffer r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Integer> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof io.ktor.utils.io.a.l
            if (r0 == 0) goto L_0x0013
            r0 = r7
            io.ktor.utils.io.a$l r0 = (io.ktor.utils.io.a.l) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.a$l r0 = new io.ktor.utils.io.a$l
            r0.<init>(r5, r7)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0047;
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
            java.nio.ByteBuffer r6 = (java.nio.ByteBuffer) r6
            java.lang.Object r2 = r0.L$0
            io.ktor.utils.io.a r2 = (io.ktor.utils.io.a) r2
            kotlin.p.b(r1)
            r4 = r1
            goto L_0x0077
        L_0x0039:
            java.lang.Object r3 = r0.L$1
            r6 = r3
            java.nio.ByteBuffer r6 = (java.nio.ByteBuffer) r6
            java.lang.Object r3 = r0.L$0
            io.ktor.utils.io.a r3 = (io.ktor.utils.io.a) r3
            kotlin.p.b(r1)
            r4 = r1
            goto L_0x005a
        L_0x0047:
            kotlin.p.b(r1)
            r0.L$0 = r5
            r0.L$1 = r6
            r3 = 1
            r0.label = r3
            java.lang.Object r3 = r5.L0(r3, r0)
            if (r3 != r2) goto L_0x0058
            return r2
        L_0x0058:
            r4 = r3
            r3 = r5
        L_0x005a:
            java.lang.Boolean r4 = (java.lang.Boolean) r4
            boolean r4 = r4.booleanValue()
            if (r4 != 0) goto L_0x0068
            r2 = -1
            java.lang.Integer r2 = kotlin.coroutines.jvm.internal.b.c(r2)
            return r2
        L_0x0068:
            r0.L$0 = r3
            r0.L$1 = r6
            r4 = 2
            r0.label = r4
            java.lang.Object r4 = r3.v(r6, r0)
            if (r4 != r2) goto L_0x0076
            return r2
        L_0x0076:
            r2 = r3
        L_0x0077:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.D0(java.nio.ByteBuffer, kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 5 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: io.ktor.utils.io.core.a0} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object C0(@org.jetbrains.annotations.NotNull io.ktor.utils.io.core.a0 r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Integer> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof io.ktor.utils.io.a.m
            if (r0 == 0) goto L_0x0013
            r0 = r7
            io.ktor.utils.io.a$m r0 = (io.ktor.utils.io.a.m) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.a$m r0 = new io.ktor.utils.io.a$m
            r0.<init>(r5, r7)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0047;
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
            io.ktor.utils.io.core.a0 r6 = (io.ktor.utils.io.core.a0) r6
            java.lang.Object r2 = r0.L$0
            io.ktor.utils.io.a r2 = (io.ktor.utils.io.a) r2
            kotlin.p.b(r1)
            r4 = r1
            goto L_0x0077
        L_0x0039:
            java.lang.Object r3 = r0.L$1
            r6 = r3
            io.ktor.utils.io.core.a0 r6 = (io.ktor.utils.io.core.a0) r6
            java.lang.Object r3 = r0.L$0
            io.ktor.utils.io.a r3 = (io.ktor.utils.io.a) r3
            kotlin.p.b(r1)
            r4 = r1
            goto L_0x005a
        L_0x0047:
            kotlin.p.b(r1)
            r0.L$0 = r5
            r0.L$1 = r6
            r3 = 1
            r0.label = r3
            java.lang.Object r3 = r5.L0(r3, r0)
            if (r3 != r2) goto L_0x0058
            return r2
        L_0x0058:
            r4 = r3
            r3 = r5
        L_0x005a:
            java.lang.Boolean r4 = (java.lang.Boolean) r4
            boolean r4 = r4.booleanValue()
            if (r4 != 0) goto L_0x0068
            r2 = -1
            java.lang.Integer r2 = kotlin.coroutines.jvm.internal.b.c(r2)
            return r2
        L_0x0068:
            r0.L$0 = r3
            r0.L$1 = r6
            r4 = 2
            r0.label = r4
            java.lang.Object r4 = r3.r(r6, r0)
            if (r4 != r2) goto L_0x0076
            return r2
        L_0x0076:
            r2 = r3
        L_0x0077:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.C0(io.ktor.utils.io.core.a0, kotlin.coroutines.d):java.lang.Object");
    }

    static /* synthetic */ Object H0(a aVar, int size, int headerSizeHint, kotlin.coroutines.d $completion) {
        Throwable it;
        C0279a aVar2 = aVar.closed;
        if (aVar2 != null && (it = aVar2.b()) != null) {
            throw it;
        } else if (size == 0) {
            return io.ktor.utils.io.core.q.p1.a();
        } else {
            io.ktor.utils.io.core.n builder = f0.a(headerSizeHint);
            ByteBuffer buffer = io.ktor.utils.io.internal.d.c().p0();
            int remaining = size;
            while (true) {
                if (remaining <= 0) {
                    break;
                }
                try {
                    buffer.clear();
                    if (buffer.remaining() > remaining) {
                        buffer.limit(remaining);
                    }
                    int rc = aVar.w0(buffer);
                    if (rc == 0) {
                        break;
                    }
                    buffer.flip();
                    io.ktor.utils.io.core.d0.a(builder, buffer);
                    remaining -= rc;
                } catch (Throwable t2) {
                    io.ktor.utils.io.internal.d.c().N0(buffer);
                    builder.release();
                    throw t2;
                }
            }
            if (remaining != 0) {
                return aVar.I0(remaining, builder, buffer, $completion);
            }
            io.ktor.utils.io.internal.d.c().N0(buffer);
            return builder.e1();
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 7 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: java.nio.ByteBuffer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v5, resolved type: io.ktor.utils.io.core.n} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0051 A[SYNTHETIC, Splitter:B:17:0x0051] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object I0(int r8, @org.jetbrains.annotations.NotNull io.ktor.utils.io.core.n r9, @org.jetbrains.annotations.NotNull java.nio.ByteBuffer r10, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super io.ktor.utils.io.core.q> r11) {
        /*
            r7 = this;
            boolean r0 = r11 instanceof io.ktor.utils.io.a.o
            if (r0 == 0) goto L_0x0013
            r0 = r11
            io.ktor.utils.io.a$o r0 = (io.ktor.utils.io.a.o) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.a$o r0 = new io.ktor.utils.io.a$o
            r0.<init>(r7, r11)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x004a;
                case 1: goto L_0x002c;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002c:
            r3 = 0
            int r3 = r0.I$1
            java.lang.Object r4 = r0.L$2
            r10 = r4
            java.nio.ByteBuffer r10 = (java.nio.ByteBuffer) r10
            java.lang.Object r4 = r0.L$1
            r9 = r4
            io.ktor.utils.io.core.n r9 = (io.ktor.utils.io.core.n) r9
            int r8 = r0.I$0
            java.lang.Object r4 = r0.L$0
            io.ktor.utils.io.a r4 = (io.ktor.utils.io.a) r4
            kotlin.p.b(r1)     // Catch:{ all -> 0x0047 }
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            goto L_0x0077
        L_0x0047:
            r2 = move-exception
            goto L_0x009e
        L_0x004a:
            kotlin.p.b(r1)
            r3 = r8
            r4 = r7
        L_0x004f:
            if (r3 <= 0) goto L_0x0092
            r10.clear()     // Catch:{ all -> 0x0047 }
            int r5 = r10.remaining()     // Catch:{ all -> 0x0047 }
            if (r5 <= r3) goto L_0x005d
            r10.limit(r3)     // Catch:{ all -> 0x0047 }
        L_0x005d:
            r0.L$0 = r4     // Catch:{ all -> 0x0047 }
            r0.I$0 = r8     // Catch:{ all -> 0x0047 }
            r0.L$1 = r9     // Catch:{ all -> 0x0047 }
            r0.L$2 = r10     // Catch:{ all -> 0x0047 }
            r0.I$1 = r3     // Catch:{ all -> 0x0047 }
            r5 = 1
            r0.label = r5     // Catch:{ all -> 0x0047 }
            java.lang.Object r5 = r4.F0(r10, r0)     // Catch:{ all -> 0x0047 }
            if (r5 != r2) goto L_0x0071
            return r2
        L_0x0071:
            r6 = r2
            r2 = r1
            r1 = r5
            r5 = r4
            r4 = r3
            r3 = r6
        L_0x0077:
            java.lang.Number r1 = (java.lang.Number) r1     // Catch:{ all -> 0x008b }
            int r1 = r1.intValue()     // Catch:{ all -> 0x008b }
            r10.flip()     // Catch:{ all -> 0x008b }
            io.ktor.utils.io.core.d0.a(r9, r10)     // Catch:{ all -> 0x008b }
            int r1 = r4 - r1
            r4 = r5
            r6 = r3
            r3 = r1
            r1 = r2
            r2 = r6
            goto L_0x004f
        L_0x008b:
            r1 = move-exception
            r3 = r4
            r4 = r5
            r6 = r2
            r2 = r1
            r1 = r6
            goto L_0x009e
        L_0x0092:
            io.ktor.utils.io.core.q r2 = r9.e1()     // Catch:{ all -> 0x0047 }
            io.ktor.utils.io.pool.d r5 = io.ktor.utils.io.internal.d.c()
            r5.N0(r10)
            return r2
        L_0x009e:
            r9.release()     // Catch:{ all -> 0x00a3 }
            throw r2     // Catch:{ all -> 0x00a3 }
        L_0x00a3:
            r2 = move-exception
            io.ktor.utils.io.pool.d r5 = io.ktor.utils.io.internal.d.c()
            r5.N0(r10)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.I0(int, io.ktor.utils.io.core.n, java.nio.ByteBuffer, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    public final void a1(@NotNull ByteBuffer $this$rollBytes, int n2) {
        int rem = $this$rollBytes.remaining();
        $this$rollBytes.limit($this$rollBytes.position() + n2);
        int i2 = n2 - rem;
        for (int i3 = 0; i3 < i2; i3++) {
            $this$rollBytes.put(($this$rollBytes.capacity() - 8) + i3, $this$rollBytes.get(i3));
        }
    }

    public final void c0(@NotNull ByteBuffer buffer, @NotNull io.ktor.utils.io.internal.k c2, int n2) {
        kotlin.jvm.internal.k.f(buffer, "buffer");
        kotlin.jvm.internal.k.f(c2, "c");
        b0(buffer, c2, n2);
    }

    private final void b0(@NotNull ByteBuffer $this$bytesWritten, io.ktor.utils.io.internal.k c2, int n2) {
        if (n2 >= 0) {
            this.h = d0($this$bytesWritten, this.h + n2);
            c2.d(n2);
            c1(n0() + ((long) n2));
            return;
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    private final void a0(@NotNull ByteBuffer $this$bytesRead, io.ktor.utils.io.internal.k c2, int n2) {
        if (n2 >= 0) {
            this.g = d0($this$bytesRead, this.g + n2);
            c2.a(n2);
            b1(t() + ((long) n2));
            Z0();
            return;
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = U0(r2, r0);
     */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final io.ktor.utils.io.a T0() {
        /*
            r2 = this;
            io.ktor.utils.io.a$c r0 = r2.joining
            if (r0 == 0) goto L_0x000c
            r1 = 0
            io.ktor.utils.io.a r0 = r2.U0(r2, r0)
            if (r0 == 0) goto L_0x000c
            goto L_0x000d
        L_0x000c:
            r0 = r2
        L_0x000d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.T0():io.ktor.utils.io.a");
    }

    /* access modifiers changed from: private */
    public final a U0(a current, c joining2) {
        while (current.state == f.C0290f.c) {
            a joinedTo = joining2.e();
            c nextJoining = joinedTo.joining;
            if (nextJoining == null) {
                return joinedTo;
            }
            joining2 = nextJoining;
            current = joinedTo;
        }
        return null;
    }

    static /* synthetic */ Object u1(a aVar, ByteBuffer src, kotlin.coroutines.d $completion) {
        a it;
        c it2 = aVar.joining;
        if (it2 == null || (it = aVar.U0(aVar, it2)) == null) {
            aVar.p1(src);
            if (!src.hasRemaining()) {
                return kotlin.x.a;
            }
            Object x1 = aVar.x1(src, $completion);
            return x1 == kotlin.coroutines.intrinsics.c.d() ? x1 : kotlin.x.a;
        }
        Object h2 = it.h(src, $completion);
        return h2 == kotlin.coroutines.intrinsics.c.d() ? h2 : kotlin.x.a;
    }

    static /* synthetic */ Object t1(a aVar, io.ktor.utils.io.core.a0 src, kotlin.coroutines.d $completion) {
        aVar.o1(src);
        io.ktor.utils.io.core.c $this$canRead$iv = src;
        if (!($this$canRead$iv.s() > $this$canRead$iv.o())) {
            return kotlin.x.a;
        }
        Object w1 = aVar.w1(src, $completion);
        return w1 == kotlin.coroutines.intrinsics.c.d() ? w1 : kotlin.x.a;
    }

    /* Debug info: failed to restart local var, previous not found, register: 9 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v3, resolved type: java.nio.ByteBuffer} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x005b, code lost:
        if (r10.hasRemaining() == false) goto L_0x0094;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x005d, code lost:
        r0.L$0 = r3;
        r0.L$1 = r10;
        r0.label = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0068, code lost:
        if (r3.n1(1, r0) != r2) goto L_0x006b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x006a, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006b, code lost:
        r4 = r3.joining;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006d, code lost:
        if (r4 == null) goto L_0x0090;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x006f, code lost:
        r6 = r3.U0(r3, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0074, code lost:
        if (r6 == null) goto L_0x0090;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0076, code lost:
        r0.L$0 = r3;
        r0.L$1 = r10;
        r0.L$2 = r4;
        r0.L$3 = r6;
        r0.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0086, code lost:
        if (r6.h(r10, r0) != r2) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0088, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0089, code lost:
        r5 = r6;
        r6 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x008f, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0090, code lost:
        r3.p1(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0096, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object x1(@org.jetbrains.annotations.NotNull java.nio.ByteBuffer r10, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof io.ktor.utils.io.a.v
            if (r0 == 0) goto L_0x0013
            r0 = r11
            io.ktor.utils.io.a$v r0 = (io.ktor.utils.io.a.v) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.a$v r0 = new io.ktor.utils.io.a$v
            r0.<init>(r9, r11)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0053;
                case 1: goto L_0x0046;
                case 2: goto L_0x002b;
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
            r5 = r4
            java.lang.Object r6 = r0.L$3
            r5 = r6
            io.ktor.utils.io.a r5 = (io.ktor.utils.io.a) r5
            java.lang.Object r6 = r0.L$2
            r4 = r6
            io.ktor.utils.io.a$c r4 = (io.ktor.utils.io.a.c) r4
            java.lang.Object r6 = r0.L$1
            r10 = r6
            java.nio.ByteBuffer r10 = (java.nio.ByteBuffer) r10
            java.lang.Object r6 = r0.L$0
            io.ktor.utils.io.a r6 = (io.ktor.utils.io.a) r6
            kotlin.p.b(r1)
            goto L_0x008d
        L_0x0046:
            java.lang.Object r3 = r0.L$1
            r10 = r3
            java.nio.ByteBuffer r10 = (java.nio.ByteBuffer) r10
            java.lang.Object r3 = r0.L$0
            io.ktor.utils.io.a r3 = (io.ktor.utils.io.a) r3
            kotlin.p.b(r1)
            goto L_0x006b
        L_0x0053:
            kotlin.p.b(r1)
            r3 = r9
        L_0x0057:
            boolean r4 = r10.hasRemaining()
            if (r4 == 0) goto L_0x0094
            r0.L$0 = r3
            r0.L$1 = r10
            r4 = 1
            r0.label = r4
            java.lang.Object r4 = r3.n1(r4, r0)
            if (r4 != r2) goto L_0x006b
            return r2
        L_0x006b:
            io.ktor.utils.io.a$c r4 = r3.joining
            if (r4 == 0) goto L_0x0090
            r5 = 0
            io.ktor.utils.io.a r6 = r3.U0(r3, r4)
            if (r6 == 0) goto L_0x0090
            r7 = 0
            r0.L$0 = r3
            r0.L$1 = r10
            r0.L$2 = r4
            r0.L$3 = r6
            r8 = 2
            r0.label = r8
            java.lang.Object r8 = r6.h(r10, r0)
            if (r8 != r2) goto L_0x0089
            return r2
        L_0x0089:
            r2 = r5
            r5 = r6
            r6 = r3
            r3 = r7
        L_0x008d:
            kotlin.x r7 = kotlin.x.a
            return r7
        L_0x0090:
            r3.p1(r10)
            goto L_0x0057
        L_0x0094:
            kotlin.x r2 = kotlin.x.a
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.x1(java.nio.ByteBuffer, kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 11 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v3, resolved type: io.ktor.utils.io.core.a0} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0057, code lost:
        r5 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0062, code lost:
        if (r5.s() <= r5.o()) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0064, code lost:
        r5 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0066, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0067, code lost:
        if (r5 == null) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0069, code lost:
        r0.L$0 = r3;
        r0.L$1 = r12;
        r0.label = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0073, code lost:
        if (r3.n1(1, r0) != r2) goto L_0x0076;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0075, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0076, code lost:
        r5 = r3.joining;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0078, code lost:
        if (r5 == null) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x007a, code lost:
        r7 = r3.U0(r3, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x007f, code lost:
        if (r7 == null) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0081, code lost:
        r4 = r7;
        r0.L$0 = r3;
        r0.L$1 = r12;
        r0.L$2 = r5;
        r0.L$3 = r4;
        r0.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0092, code lost:
        if (r4.m(r12, r0) != r2) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0094, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0095, code lost:
        r6 = r3;
        r3 = r5;
        r5 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x009d, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009e, code lost:
        r3.o1(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a4, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object w1(@org.jetbrains.annotations.NotNull io.ktor.utils.io.core.a0 r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r13) {
        /*
            r11 = this;
            boolean r0 = r13 instanceof io.ktor.utils.io.a.w
            if (r0 == 0) goto L_0x0013
            r0 = r13
            io.ktor.utils.io.a$w r0 = (io.ktor.utils.io.a.w) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.a$w r0 = new io.ktor.utils.io.a$w
            r0.<init>(r11, r13)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x0053;
                case 1: goto L_0x0046;
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
            r3 = 0
            r5 = r3
            java.lang.Object r6 = r0.L$3
            r5 = r6
            io.ktor.utils.io.a r5 = (io.ktor.utils.io.a) r5
            java.lang.Object r6 = r0.L$2
            r3 = r6
            io.ktor.utils.io.a$c r3 = (io.ktor.utils.io.a.c) r3
            java.lang.Object r6 = r0.L$1
            r12 = r6
            io.ktor.utils.io.core.a0 r12 = (io.ktor.utils.io.core.a0) r12
            java.lang.Object r6 = r0.L$0
            io.ktor.utils.io.a r6 = (io.ktor.utils.io.a) r6
            kotlin.p.b(r1)
            goto L_0x009b
        L_0x0046:
            java.lang.Object r3 = r0.L$1
            r12 = r3
            io.ktor.utils.io.core.a0 r12 = (io.ktor.utils.io.core.a0) r12
            java.lang.Object r3 = r0.L$0
            io.ktor.utils.io.a r3 = (io.ktor.utils.io.a) r3
            kotlin.p.b(r1)
            goto L_0x0076
        L_0x0053:
            kotlin.p.b(r1)
            r3 = r11
        L_0x0057:
            r5 = r12
            r6 = 0
            int r7 = r5.s()
            int r8 = r5.o()
            r9 = 1
            if (r7 <= r8) goto L_0x0066
            r5 = r9
            goto L_0x0067
        L_0x0066:
            r5 = r4
        L_0x0067:
            if (r5 == 0) goto L_0x00a2
            r0.L$0 = r3
            r0.L$1 = r12
            r0.label = r9
            java.lang.Object r5 = r3.n1(r9, r0)
            if (r5 != r2) goto L_0x0076
            return r2
        L_0x0076:
            io.ktor.utils.io.a$c r5 = r3.joining
            if (r5 == 0) goto L_0x009e
            r6 = 0
            io.ktor.utils.io.a r7 = r3.U0(r3, r5)
            if (r7 == 0) goto L_0x009e
            r4 = r7
            r7 = 0
            r0.L$0 = r3
            r0.L$1 = r12
            r0.L$2 = r5
            r0.L$3 = r4
            r8 = 2
            r0.label = r8
            java.lang.Object r8 = r4.m(r12, r0)
            if (r8 != r2) goto L_0x0095
            return r2
        L_0x0095:
            r2 = r7
            r10 = r6
            r6 = r3
            r3 = r5
            r5 = r4
            r4 = r10
        L_0x009b:
            kotlin.x r7 = kotlin.x.a
            return r7
        L_0x009e:
            r3.o1(r12)
            goto L_0x0057
        L_0x00a2:
            kotlin.x r2 = kotlin.x.a
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.w1(io.ktor.utils.io.core.a0, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final /* synthetic */ Object Z(@NotNull kotlin.coroutines.d<? super kotlin.x> $completion) {
        if (this.closed != null) {
            return kotlin.x.a;
        }
        c joined = this.joining;
        if (joined != null) {
            Object a = joined.a($completion);
            return a == kotlin.coroutines.intrinsics.c.d() ? a : kotlin.x.a;
        } else if (this.closed != null) {
            return kotlin.x.a;
        } else {
            throw new IllegalStateException("Only works for joined".toString());
        }
    }

    @Nullable
    public final Object p0(@NotNull a src, boolean delegateClose, @NotNull kotlin.coroutines.d<? super kotlin.x> $completion) {
        if (src.closed == null || src.state != f.C0290f.c) {
            C0279a closed2 = this.closed;
            if (closed2 == null) {
                c joined = src.e1(this, delegateClose);
                if (src.j1(joined)) {
                    Object Z = src.Z($completion);
                    return Z == kotlin.coroutines.intrinsics.c.d() ? Z : kotlin.x.a;
                }
                Object q0 = q0(src, delegateClose, joined, $completion);
                return q0 == kotlin.coroutines.intrinsics.c.d() ? q0 : kotlin.x.a;
            } else if (src.closed != null) {
                return kotlin.x.a;
            } else {
                throw closed2.c();
            }
        } else {
            if (delegateClose) {
                C0279a aVar = src.closed;
                if (aVar == null) {
                    kotlin.jvm.internal.k.n();
                }
                d(aVar.b());
            }
            return kotlin.x.a;
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 9 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: io.ktor.utils.io.a$c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v2, resolved type: io.ktor.utils.io.a} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object q0(@org.jetbrains.annotations.NotNull io.ktor.utils.io.a r10, boolean r11, @org.jetbrains.annotations.NotNull io.ktor.utils.io.a.c r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r13) {
        /*
            r9 = this;
            boolean r0 = r13 instanceof io.ktor.utils.io.a.i
            if (r0 == 0) goto L_0x0013
            r0 = r13
            io.ktor.utils.io.a$i r0 = (io.ktor.utils.io.a.i) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.a$i r0 = new io.ktor.utils.io.a$i
            r0.<init>(r9, r13)
        L_0x0018:
            java.lang.Object r7 = r0.result
            java.lang.Object r8 = kotlin.coroutines.intrinsics.c.d()
            int r1 = r0.label
            switch(r1) {
                case 0: goto L_0x0053;
                case 1: goto L_0x003f;
                case 2: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            java.lang.Object r1 = r0.L$2
            r12 = r1
            io.ktor.utils.io.a$c r12 = (io.ktor.utils.io.a.c) r12
            boolean r11 = r0.Z$0
            java.lang.Object r1 = r0.L$1
            r10 = r1
            io.ktor.utils.io.a r10 = (io.ktor.utils.io.a) r10
            java.lang.Object r1 = r0.L$0
            io.ktor.utils.io.a r1 = (io.ktor.utils.io.a) r1
            kotlin.p.b(r7)
            goto L_0x0093
        L_0x003f:
            java.lang.Object r1 = r0.L$2
            r12 = r1
            io.ktor.utils.io.a$c r12 = (io.ktor.utils.io.a.c) r12
            boolean r11 = r0.Z$0
            java.lang.Object r1 = r0.L$1
            r10 = r1
            io.ktor.utils.io.a r10 = (io.ktor.utils.io.a) r10
            java.lang.Object r1 = r0.L$0
            io.ktor.utils.io.a r1 = (io.ktor.utils.io.a) r1
            kotlin.p.b(r7)
            goto L_0x0072
        L_0x0053:
            kotlin.p.b(r7)
            r3 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r0.L$0 = r9
            r0.L$1 = r10
            r0.Z$0 = r11
            r0.L$2 = r12
            r1 = 1
            r0.label = r1
            r1 = r9
            r2 = r10
            r5 = r12
            r6 = r0
            java.lang.Object r1 = r1.e0(r2, r3, r5, r6)
            if (r1 != r8) goto L_0x0071
            return r8
        L_0x0071:
            r1 = r9
        L_0x0072:
            if (r11 == 0) goto L_0x007e
            boolean r2 = r10.y()
            if (r2 == 0) goto L_0x007e
            io.ktor.utils.io.m.a(r1)
            goto L_0x0094
        L_0x007e:
            r1.flush()
            r0.L$0 = r1
            r0.L$1 = r10
            r0.Z$0 = r11
            r0.L$2 = r12
            r2 = 2
            r0.label = r2
            java.lang.Object r2 = r10.Z(r0)
            if (r2 != r8) goto L_0x0093
            return r8
        L_0x0093:
        L_0x0094:
            kotlin.x r2 = kotlin.x.a
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.q0(io.ktor.utils.io.a, boolean, io.ktor.utils.io.a$c, kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 40 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v90, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v28, resolved type: io.ktor.utils.io.a} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v91, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v29, resolved type: io.ktor.utils.io.internal.k} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v92, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v21, resolved type: java.nio.ByteBuffer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v93, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v12, resolved type: io.ktor.utils.io.a} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v94, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v22, resolved type: kotlin.jvm.internal.y} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v96, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v42, resolved type: io.ktor.utils.io.a$c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v97, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v39, resolved type: io.ktor.utils.io.a} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v98, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r24v12, resolved type: io.ktor.utils.io.a} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v29, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v18, resolved type: kotlin.jvm.internal.y} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v31, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v49, resolved type: io.ktor.utils.io.a$c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v34, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v43, resolved type: io.ktor.utils.io.a} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v33, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v22, resolved type: kotlin.jvm.internal.y} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v35, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v51, resolved type: io.ktor.utils.io.a$c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v37, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v45, resolved type: io.ktor.utils.io.a} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x02f6, code lost:
        r43 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:?, code lost:
        r5.t0(r2, r11, r5.h, r13);
        r18 = 0;
        r24 = r21;
        r0 = V(r24);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0307, code lost:
        if (r0 == null) goto L_0x03db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0309, code lost:
        r26 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x0311, code lost:
        r45 = r6;
        r6 = I(r24).b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x031a, code lost:
        if (r6.availableForRead != 0) goto L_0x0346;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:?, code lost:
        N(r24);
        r24.l1();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x0322, code lost:
        r30 = r9;
        r31 = r10;
        r32 = r11;
        r33 = r12;
        r34 = r14;
        r35 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x0330, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x0331, code lost:
        r6 = r41;
        r5 = r42;
        r1 = r45;
        r2 = r0;
        r0 = r10;
        r32 = r11;
        r13 = r12;
        r12 = r15;
        r15 = r16;
        r10 = r19;
        r19 = r3;
        r3 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0346, code lost:
        r0 = r26;
        r44 = r6;
        r28 = r0;
        r29 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0357, code lost:
        r30 = r9;
        r31 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x0360, code lost:
        r32 = r11;
        r33 = r12;
        r34 = r14;
        r35 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:?, code lost:
        r9 = r1.n((int) java.lang.Math.min((long) r28.remaining(), java.lang.Math.min((long) r2.remaining(), r7 - r3.element)));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x037b, code lost:
        if (r9 <= 0) goto L_0x03a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x037d, code lost:
        r10 = r44;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x0383, code lost:
        if (r10.l(r9) == false) goto L_0x039d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x0385, code lost:
        r12 = r28;
        r12.limit(r28.position() + r9);
        r2.put(r12);
        r18 = r9;
        r21.a0(r12, r10, r9);
        r11 = kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x039d, code lost:
        r12 = r28;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x03a4, code lost:
        throw new java.lang.AssertionError();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x03a5, code lost:
        r10 = r44;
        r12 = r28;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x03a9, code lost:
        kotlin.coroutines.jvm.internal.b.a(true).booleanValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:?, code lost:
        N(r24);
        r24.l1();
        r0 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x03bb, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x03bd, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x03be, code lost:
        r32 = r11;
        r33 = r12;
        r34 = r14;
        r35 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x03c7, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x03c8, code lost:
        r30 = r9;
        r31 = r10;
        r32 = r11;
        r33 = r12;
        r34 = r14;
        r35 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x03db, code lost:
        r45 = r6;
        r30 = r9;
        r31 = r10;
        r32 = r11;
        r33 = r12;
        r34 = r14;
        r35 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x03e9, code lost:
        r0 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x03eb, code lost:
        if (r0 <= 0) goto L_0x0438;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x03ed, code lost:
        r5.b0(r2, r1, r0);
        r3.element += (long) r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x03f8, code lost:
        if ((r13 - r0) == 0) goto L_0x03fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x03fa, code lost:
        if (r4 == false) goto L_0x03ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x03fc, code lost:
        r5.flush();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x03ff, code lost:
        r15 = r1;
        r0 = r2;
        r1 = r3;
        r3 = r4;
        r18 = r5;
        r6 = r16;
        r2 = r21;
        r9 = r30;
        r10 = r31;
        r11 = r32;
        r12 = r33;
        r4 = r34;
        r14 = r35;
        r5 = r42;
        r16 = r43;
        r42 = r45;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x041d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x041e, code lost:
        r6 = r41;
        r5 = r42;
        r1 = r45;
        r2 = r0;
        r15 = r16;
        r10 = r19;
        r9 = r30;
        r0 = r31;
        r13 = r33;
        r14 = r34;
        r12 = r35;
        r19 = r3;
        r3 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x0438, code lost:
        r1 = r41;
        r5 = r42;
        r0 = r43;
        r2 = r45;
        r6 = r16;
        r12 = r19;
        r9 = r30;
        r10 = r31;
        r11 = r32;
        r14 = r35;
        r19 = r3;
        r3 = r4;
        r4 = r34;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x0452, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x0453, code lost:
        r45 = r6;
        r30 = r9;
        r31 = r10;
        r32 = r11;
        r34 = r14;
        r35 = r15;
        r6 = r41;
        r5 = r42;
        r1 = r45;
        r2 = r0;
        r15 = r16;
        r10 = r19;
        r0 = r31;
        r13 = r12;
        r12 = r35;
        r19 = r3;
        r3 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x0477, code lost:
        r43 = r19;
        r14 = r21;
        r19 = r0;
        r21 = r2;
        r0 = r18;
        r2 = r42;
        r0 = r16;
        r33 = r12;
        r19 = r1;
        r1 = r41;
        r12 = r43;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x0495, code lost:
        if (r4.h() != false) goto L_0x04ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x049b, code lost:
        if (r14.A() == false) goto L_0x04b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x049e, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x049f, code lost:
        r6 = r1;
        r10 = r11;
        r1 = r21;
        r13 = r33;
        r36 = r7;
        r8 = r2;
        r7 = r9;
        r9 = r3;
        r2 = r36;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:?, code lost:
        r14.flush();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x04b1, code lost:
        if (r14 == r10) goto L_0x04c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x04bc, code lost:
        r42 = r0;
        r41 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:?, code lost:
        r10.c1(r10.n0() + (r14.n0() - r12));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x04c8, code lost:
        r42 = r0;
        r41 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x04cc, code lost:
        r14.W0();
        r14.l1();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x04d2, code lost:
        r6 = r41;
        r0 = r42;
        r10 = r11;
        r11 = r19;
        r1 = r21;
        r13 = r33;
        r36 = r7;
        r8 = r2;
        r7 = r9;
        r9 = r3;
        r2 = r36;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x04e7, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x04e8, code lost:
        r6 = r41;
        r10 = r11;
        r1 = r21;
        r13 = r33;
        r36 = r7;
        r8 = r2;
        r7 = r9;
        r9 = r3;
        r2 = r36;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x04f8, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x04f9, code lost:
        r6 = r1;
        r10 = r11;
        r1 = r21;
        r13 = r33;
        r36 = r7;
        r8 = r2;
        r7 = r9;
        r9 = r3;
        r2 = r36;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x050b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x050c, code lost:
        r24 = r1;
        r43 = r19;
        r14 = r21;
        r21 = r2;
        r1 = r42;
        r2 = r0;
        r15 = r6;
        r0 = r10;
        r32 = r11;
        r13 = r12;
        r12 = r14;
        r19 = r24;
        r6 = r41;
        r10 = r43;
        r14 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x052a, code lost:
        throw r0.c();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x052b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x052c, code lost:
        r21 = r1;
        r1 = r8;
        r32 = r10;
        r36 = r2;
        r2 = r0;
        r3 = r9;
        r0 = r12;
        r12 = r14;
        r14 = r16;
        r9 = r7;
        r7 = r36;
        r19 = r11;
        r10 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:190:0x054c, code lost:
        if (r12.A() != false) goto L_0x055d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:191:0x054f, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:192:0x0550, code lost:
        r10 = r32;
        r36 = r7;
        r8 = r1;
        r7 = r9;
        r1 = r21;
        r9 = r3;
        r2 = r36;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:199:?, code lost:
        r0.c1(r0.n0() + (r12.n0() - r10));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:200:0x0575, code lost:
        r41 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:203:0x057e, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:204:0x057f, code lost:
        r2 = r7;
        r7 = r9;
        r10 = r32;
        r9 = r3;
        r8 = r1;
        r1 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:207:0x0598, code lost:
        r41 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:208:0x059a, code lost:
        if (r5 == null) goto L_0x05b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:211:0x05a0, code lost:
        if (r1.j1(r5) == false) goto L_0x05a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:214:0x05ac, code lost:
        if (r1.state.b.e() == false) goto L_0x05b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:215:0x05ae, code lost:
        r1.Z0();
        r4 = r40;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:217:0x05ba, code lost:
        if (r11.element < r2) goto L_0x05be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:219:0x05be, code lost:
        r13.flush();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:220:0x05c5, code lost:
        if (r1.e() != 0) goto L_0x0619;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:221:0x05c7, code lost:
        r7.L$0 = r13;
        r7.L$1 = r1;
        r7.J$0 = r2;
        r7.L$2 = r5;
        r7.Z$0 = r9;
        r7.L$3 = r10;
        r7.L$4 = r11;
        r7.label = 2;
        r12 = r1.M0(1, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:222:0x05dd, code lost:
        if (r12 != r0) goto L_0x05e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:223:0x05df, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:224:0x05e0, code lost:
        r4 = r11;
        r36 = r2;
        r2 = r5;
        r3 = r6;
        r5 = r7;
        r7 = r9;
        r6 = r12;
        r11 = r36;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:0x05f0, code lost:
        if (((java.lang.Boolean) r6).booleanValue() == false) goto L_0x05fb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:228:0x05f2, code lost:
        if (r2 == null) goto L_0x0604;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:230:0x05f8, code lost:
        if (r1.j1(r2) == false) goto L_0x0604;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:232:0x05fb, code lost:
        if (r2 == null) goto L_0x060b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:234:0x0601, code lost:
        if (r1.j1(r2) == false) goto L_0x0604;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:235:0x0604, code lost:
        r6 = r3;
        r9 = r7;
        r7 = r5;
        r5 = r2;
        r2 = r11;
        r11 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:236:0x060b, code lost:
        r6 = r3;
        r9 = r7;
        r7 = r5;
        r5 = r2;
        r2 = r11;
        r11 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:237:0x0612, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:238:0x0613, code lost:
        r6 = r3;
        r9 = r7;
        r7 = r5;
        r5 = r2;
        r2 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:241:0x061b, code lost:
        if (r13.joining == null) goto L_0x063a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:242:0x061d, code lost:
        r7.L$0 = r13;
        r7.L$1 = r1;
        r7.J$0 = r2;
        r7.L$2 = r5;
        r7.Z$0 = r9;
        r7.L$3 = r10;
        r7.L$4 = r11;
        r7.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:243:0x0633, code lost:
        if (r13.n1(1, r7) != r0) goto L_0x0636;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:244:0x0635, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:245:0x0636, code lost:
        r4 = r40;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:246:0x063a, code lost:
        r4 = r40;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:247:0x063f, code lost:
        if (r9 == false) goto L_0x0644;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:248:0x0641, code lost:
        r13.flush();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:250:0x064a, code lost:
        return kotlin.coroutines.jvm.internal.b.d(r11.element);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:251:0x064b, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x018a, code lost:
        if (r11.element >= r2) goto L_0x063f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x018c, code lost:
        r12 = r13;
        r17 = false;
        r14 = D(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0193, code lost:
        if (r14 == null) goto L_0x019d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0195, code lost:
        r16 = M(r12, r12, r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x019a, code lost:
        if (r16 == null) goto L_0x019d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x019d, code lost:
        r16 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x019f, code lost:
        r14 = r16;
        r15 = r14.g1();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x01a5, code lost:
        if (r15 == null) goto L_0x0598;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x01a7, code lost:
        r41 = r0;
        r16 = I(r14).b;
        r18 = r14.n0();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
        r0 = C(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x01ba, code lost:
        if (r0 != null) goto L_0x0525;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x01bc, code lost:
        r20 = r15;
        r21 = r16;
        r42 = r8;
        r4 = r16;
        r16 = r41;
        r41 = r6;
        r6 = r15;
        r15 = r21;
        r36 = r18;
        r18 = r14;
        r0 = r20;
        r19 = r36;
        r38 = r2;
        r2 = r1;
        r3 = r9;
        r1 = r11;
        r9 = r7;
        r11 = r10;
        r10 = r12;
        r12 = r13;
        r7 = r38;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x01e1, code lost:
        r21 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x01e7, code lost:
        if (r1.element >= r7) goto L_0x0477;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x01e9, code lost:
        r13 = r15.availableForWrite;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x01eb, code lost:
        if (r13 != 0) goto L_0x02d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x01ed, code lost:
        r9.L$0 = r12;
        r9.L$1 = r2;
        r9.J$0 = r7;
        r9.L$2 = r5;
        r9.Z$0 = r3;
        r9.L$3 = r11;
        r9.L$4 = r1;
        r9.L$5 = r10;
        r9.L$6 = r6;
        r9.L$7 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0201, code lost:
        r14 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:?, code lost:
        r9.L$8 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0205, code lost:
        r24 = r1;
        r21 = r2;
        r1 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:?, code lost:
        r9.J$1 = r1;
        r9.L$9 = r15;
        r9.L$10 = r0;
        r19 = r0;
        r0 = r18;
        r9.L$11 = r0;
        r9.I$0 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0219, code lost:
        r43 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:?, code lost:
        r9.label = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0222, code lost:
        r1 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0224, code lost:
        if (r0.n1(1, r9) != r1) goto L_0x0227;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0226, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0227, code lost:
        r16 = r6;
        r20 = r11;
        r18 = r17;
        r2 = r21;
        r6 = r42;
        r21 = r1;
        r17 = r10;
        r1 = r41;
        r10 = r43;
        r36 = r4;
        r4 = r0;
        r0 = r19;
        r19 = r24;
        r24 = r12;
        r12 = r14;
        r14 = r36;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0245, code lost:
        r41 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0249, code lost:
        if (r4.joining == null) goto L_0x025f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x024b, code lost:
        r4 = r14;
        r0 = r21;
        r33 = r24;
        r21 = r2;
        r2 = r6;
        r14 = r12;
        r6 = r16;
        r12 = r10;
        r10 = r17;
        r17 = r18;
        r11 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x0261, code lost:
        r13 = r15.availableForWrite;
        r42 = r5;
        r0 = r21;
        r21 = r2;
        r5 = r4;
        r2 = r41;
        r41 = r1;
        r4 = r3;
        r1 = r15;
        r3 = r19;
        r15 = r12;
        r12 = r24;
        r36 = r10;
        r10 = r17;
        r17 = r18;
        r11 = r20;
        r19 = r36;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0280, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0281, code lost:
        r21 = r2;
        r15 = r16;
        r32 = r20;
        r13 = r24;
        r2 = r0;
        r0 = r17;
        r17 = r18;
        r36 = r6;
        r6 = r1;
        r1 = r36;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0295, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0296, code lost:
        r1 = r42;
        r2 = r0;
        r15 = r6;
        r0 = r10;
        r32 = r11;
        r13 = r12;
        r12 = r14;
        r19 = r24;
        r6 = r41;
        r10 = r43;
        r14 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x02a8, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x02a9, code lost:
        r43 = r1;
        r1 = r42;
        r2 = r0;
        r15 = r6;
        r0 = r10;
        r32 = r11;
        r13 = r12;
        r12 = r14;
        r19 = r24;
        r6 = r41;
        r10 = r43;
        r14 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x02bd, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x02be, code lost:
        r24 = r1;
        r21 = r2;
        r43 = r19;
        r1 = r42;
        r2 = r0;
        r15 = r6;
        r0 = r10;
        r32 = r11;
        r13 = r12;
        r12 = r14;
        r19 = r24;
        r6 = r41;
        r10 = r43;
        r14 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x02d6, code lost:
        r24 = r1;
        r1 = r16;
        r14 = r21;
        r21 = r2;
        r16 = r6;
        r2 = r0;
        r6 = r42;
        r19 = r19;
        r42 = r5;
        r5 = r18;
        r0 = r1;
        r1 = r15;
        r15 = r14;
        r14 = r4;
        r4 = r3;
        r3 = r24;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:188:0x0548 A[SYNTHETIC, Splitter:B:188:0x0548] */
    /* JADX WARNING: Removed duplicated region for block: B:196:0x0562 A[Catch:{ all -> 0x058a }] */
    /* JADX WARNING: Removed duplicated region for block: B:200:0x0575 A[Catch:{ all -> 0x057e }] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x012e  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0032  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object e0(@org.jetbrains.annotations.NotNull io.ktor.utils.io.a r41, long r42, @org.jetbrains.annotations.Nullable io.ktor.utils.io.a.c r44, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Long> r45) {
        /*
            r40 = this;
            r1 = r41
            r2 = r44
            r3 = r45
            boolean r0 = r3 instanceof io.ktor.utils.io.a.g
            if (r0 == 0) goto L_0x001b
            r0 = r3
            io.ktor.utils.io.a$g r0 = (io.ktor.utils.io.a.g) r0
            int r4 = r0.label
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            r6 = r4 & r5
            if (r6 == 0) goto L_0x001b
            int r4 = r4 - r5
            r0.label = r4
            r4 = r40
            goto L_0x0022
        L_0x001b:
            io.ktor.utils.io.a$g r0 = new io.ktor.utils.io.a$g
            r4 = r40
            r0.<init>(r4, r3)
        L_0x0022:
            r5 = r0
            java.lang.Object r6 = r5.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r7 = r5.label
            r8 = 0
            r10 = 0
            r12 = 0
            switch(r7) {
                case 0: goto L_0x012e;
                case 1: goto L_0x0099;
                case 2: goto L_0x006a;
                case 3: goto L_0x003b;
                default: goto L_0x0032;
            }
        L_0x0032:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r3 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r3)
            throw r0
        L_0x003b:
            r7 = r8
            r8 = r12
            r10 = r12
            java.lang.Object r11 = r5.L$4
            r8 = r11
            kotlin.jvm.internal.y r8 = (kotlin.jvm.internal.y) r8
            java.lang.Object r11 = r5.L$3
            r10 = r11
            io.ktor.utils.io.core.l r10 = (io.ktor.utils.io.core.l) r10
            boolean r7 = r5.Z$0
            java.lang.Object r11 = r5.L$2
            r2 = r11
            io.ktor.utils.io.a$c r2 = (io.ktor.utils.io.a.c) r2
            long r11 = r5.J$0
            java.lang.Object r13 = r5.L$1
            r1 = r13
            io.ktor.utils.io.a r1 = (io.ktor.utils.io.a) r1
            java.lang.Object r13 = r5.L$0
            io.ktor.utils.io.a r13 = (io.ktor.utils.io.a) r13
            kotlin.p.b(r6)     // Catch:{ all -> 0x0090 }
            r9 = r7
            r4 = 1
            r7 = r5
            r5 = r2
            r36 = r6
            r6 = r3
            r2 = r11
            r11 = r8
            r8 = r36
            goto L_0x0636
        L_0x006a:
            r7 = r8
            r8 = r12
            r10 = r12
            java.lang.Object r11 = r5.L$4
            r8 = r11
            kotlin.jvm.internal.y r8 = (kotlin.jvm.internal.y) r8
            java.lang.Object r11 = r5.L$3
            r10 = r11
            io.ktor.utils.io.core.l r10 = (io.ktor.utils.io.core.l) r10
            boolean r7 = r5.Z$0
            java.lang.Object r11 = r5.L$2
            r2 = r11
            io.ktor.utils.io.a$c r2 = (io.ktor.utils.io.a.c) r2
            long r11 = r5.J$0
            java.lang.Object r13 = r5.L$1
            r1 = r13
            io.ktor.utils.io.a r1 = (io.ktor.utils.io.a) r1
            java.lang.Object r13 = r5.L$0
            io.ktor.utils.io.a r13 = (io.ktor.utils.io.a) r13
            kotlin.p.b(r6)     // Catch:{ all -> 0x0090 }
            r4 = r8
            r8 = r6
            goto L_0x05ea
        L_0x0090:
            r0 = move-exception
            r8 = r6
            r9 = r7
            r6 = r3
            r7 = r5
            r5 = r2
            r2 = r11
            goto L_0x0659
        L_0x0099:
            r7 = r8
            r13 = r12
            r14 = r12
            r15 = r12
            r16 = r12
            r17 = r8
            r18 = r8
            r19 = r12
            r20 = r12
            r21 = r12
            r22 = r12
            int r8 = r5.I$0
            java.lang.Object r9 = r5.L$11
            io.ktor.utils.io.a r9 = (io.ktor.utils.io.a) r9
            r21 = r0
            java.lang.Object r0 = r5.L$10
            java.nio.ByteBuffer r0 = (java.nio.ByteBuffer) r0
            java.lang.Object r13 = r5.L$9
            io.ktor.utils.io.internal.k r13 = (io.ktor.utils.io.internal.k) r13
            long r10 = r5.J$1
            r22 = r0
            java.lang.Object r0 = r5.L$8
            r12 = r0
            io.ktor.utils.io.a r12 = (io.ktor.utils.io.a) r12
            java.lang.Object r0 = r5.L$7
            r14 = r0
            io.ktor.utils.io.internal.k r14 = (io.ktor.utils.io.internal.k) r14
            java.lang.Object r0 = r5.L$6
            r15 = r0
            java.nio.ByteBuffer r15 = (java.nio.ByteBuffer) r15
            java.lang.Object r0 = r5.L$5
            r16 = r0
            io.ktor.utils.io.a r16 = (io.ktor.utils.io.a) r16
            java.lang.Object r0 = r5.L$4
            r19 = r0
            kotlin.jvm.internal.y r19 = (kotlin.jvm.internal.y) r19
            java.lang.Object r0 = r5.L$3
            r20 = r0
            io.ktor.utils.io.core.l r20 = (io.ktor.utils.io.core.l) r20
            boolean r3 = r5.Z$0
            java.lang.Object r0 = r5.L$2
            r2 = r0
            io.ktor.utils.io.a$c r2 = (io.ktor.utils.io.a.c) r2
            r44 = r2
            r18 = r3
            long r2 = r5.J$0
            java.lang.Object r0 = r5.L$1
            r1 = r0
            io.ktor.utils.io.a r1 = (io.ktor.utils.io.a) r1
            java.lang.Object r0 = r5.L$0
            r24 = r0
            io.ktor.utils.io.a r24 = (io.ktor.utils.io.a) r24
            kotlin.p.b(r6)     // Catch:{ all -> 0x0119 }
            r4 = r9
            r0 = r22
            r9 = r5
            r22 = r7
            r5 = r44
            r36 = r1
            r1 = r45
            r37 = r2
            r2 = r36
            r3 = r18
            r18 = r17
            r17 = r16
            r16 = r15
            r15 = r13
            r13 = r8
            r7 = r37
            goto L_0x0245
        L_0x0119:
            r0 = move-exception
            r21 = r1
            r7 = r2
            r9 = r5
            r1 = r6
            r3 = r18
            r32 = r20
            r13 = r24
            r5 = r44
            r6 = r45
            r2 = r0
            r0 = r16
            goto L_0x0542
        L_0x012e:
            r21 = r0
            kotlin.p.b(r6)
            int r0 = (r42 > r10 ? 1 : (r42 == r10 ? 0 : -1))
            if (r0 != 0) goto L_0x013c
            java.lang.Long r0 = kotlin.coroutines.jvm.internal.b.d(r10)
            return r0
        L_0x013c:
            boolean r0 = r41.y()
            if (r0 == 0) goto L_0x015c
            if (r2 == 0) goto L_0x0157
            boolean r0 = r1.j1(r2)
            if (r0 == 0) goto L_0x014b
            goto L_0x0157
        L_0x014b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r3 = "Check failed."
            java.lang.String r3 = r3.toString()
            r0.<init>(r3)
            throw r0
        L_0x0157:
            java.lang.Long r0 = kotlin.coroutines.jvm.internal.b.d(r10)
            return r0
        L_0x015c:
            if (r2 == 0) goto L_0x0169
            boolean r0 = r1.j1(r2)
            if (r0 == 0) goto L_0x0169
            java.lang.Long r0 = kotlin.coroutines.jvm.internal.b.d(r10)
            return r0
        L_0x0169:
            boolean r7 = r40.A()
            io.ktor.utils.io.core.l r3 = r40.o0()
            kotlin.jvm.internal.y r0 = new kotlin.jvm.internal.y     // Catch:{ all -> 0x064d }
            r0.<init>()     // Catch:{ all -> 0x064d }
            r0.element = r10     // Catch:{ all -> 0x064d }
            r11 = r0
            r10 = r3
            r13 = r4
            r8 = r6
            r9 = r7
            r0 = r21
            r6 = r45
            r7 = r5
            r5 = r2
            r2 = r42
        L_0x0186:
            long r14 = r11.element     // Catch:{ all -> 0x064b }
            int r12 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1))
            if (r12 >= 0) goto L_0x063f
            r12 = r13
            r17 = 0
            io.ktor.utils.io.a$c r14 = r12.joining     // Catch:{ all -> 0x064b }
            if (r14 == 0) goto L_0x019d
            r15 = 0
            io.ktor.utils.io.a r16 = r12.U0(r12, r14)     // Catch:{ all -> 0x064b }
            if (r16 == 0) goto L_0x019d
            goto L_0x019f
        L_0x019d:
            r16 = r12
        L_0x019f:
            r14 = r16
            java.nio.ByteBuffer r15 = r14.g1()     // Catch:{ all -> 0x064b }
            if (r15 == 0) goto L_0x0598
            r41 = r0
            io.ktor.utils.io.internal.f r0 = r14.state     // Catch:{ all -> 0x064b }
            io.ktor.utils.io.internal.k r0 = r0.b     // Catch:{ all -> 0x064b }
            r16 = r0
            long r18 = r14.n0()     // Catch:{ all -> 0x064b }
            io.ktor.utils.io.a$a r0 = r14.closed     // Catch:{ all -> 0x052b }
            if (r0 != 0) goto L_0x0525
            r0 = r14
            r20 = r15
            r21 = r16
            r22 = 0
            r42 = r8
            r4 = r16
            r16 = r41
            r41 = r6
            r6 = r15
            r15 = r21
            r36 = r18
            r18 = r0
            r0 = r20
            r19 = r36
            r38 = r2
            r2 = r1
            r3 = r9
            r1 = r11
            r9 = r7
            r11 = r10
            r10 = r12
            r12 = r13
            r7 = r38
        L_0x01e1:
            r21 = r14
            long r13 = r1.element     // Catch:{ all -> 0x050b }
            int r13 = (r13 > r7 ? 1 : (r13 == r7 ? 0 : -1))
            if (r13 >= 0) goto L_0x0477
            int r13 = r15.availableForWrite     // Catch:{ all -> 0x050b }
            if (r13 != 0) goto L_0x02d6
            r9.L$0 = r12     // Catch:{ all -> 0x050b }
            r9.L$1 = r2     // Catch:{ all -> 0x050b }
            r9.J$0 = r7     // Catch:{ all -> 0x050b }
            r9.L$2 = r5     // Catch:{ all -> 0x050b }
            r9.Z$0 = r3     // Catch:{ all -> 0x050b }
            r9.L$3 = r11     // Catch:{ all -> 0x050b }
            r9.L$4 = r1     // Catch:{ all -> 0x050b }
            r9.L$5 = r10     // Catch:{ all -> 0x050b }
            r9.L$6 = r6     // Catch:{ all -> 0x050b }
            r9.L$7 = r4     // Catch:{ all -> 0x050b }
            r14 = r21
            r9.L$8 = r14     // Catch:{ all -> 0x02bd }
            r24 = r1
            r21 = r2
            r1 = r19
            r9.J$1 = r1     // Catch:{ all -> 0x02a8 }
            r9.L$9 = r15     // Catch:{ all -> 0x02a8 }
            r9.L$10 = r0     // Catch:{ all -> 0x02a8 }
            r19 = r0
            r0 = r18
            r9.L$11 = r0     // Catch:{ all -> 0x02a8 }
            r9.I$0 = r13     // Catch:{ all -> 0x02a8 }
            r43 = r1
            r1 = 1
            r9.label = r1     // Catch:{ all -> 0x0295 }
            java.lang.Object r2 = r0.n1(r1, r9)     // Catch:{ all -> 0x0295 }
            r1 = r16
            if (r2 != r1) goto L_0x0227
            return r1
        L_0x0227:
            r16 = r6
            r20 = r11
            r18 = r17
            r2 = r21
            r6 = r42
            r21 = r1
            r17 = r10
            r1 = r41
            r10 = r43
            r36 = r4
            r4 = r0
            r0 = r19
            r19 = r24
            r24 = r12
            r12 = r14
            r14 = r36
        L_0x0245:
            r41 = r0
            io.ktor.utils.io.a$c r0 = r4.joining     // Catch:{ all -> 0x0280 }
            if (r0 == 0) goto L_0x025f
            r4 = r14
            r0 = r21
            r33 = r24
            r21 = r2
            r2 = r6
            r14 = r12
            r6 = r16
            r12 = r10
            r10 = r17
            r17 = r18
            r11 = r20
            goto L_0x0490
        L_0x025f:
            int r0 = r15.availableForWrite     // Catch:{ all -> 0x0280 }
            r13 = r0
            r42 = r5
            r0 = r21
            r21 = r2
            r5 = r4
            r2 = r41
            r41 = r1
            r4 = r3
            r1 = r15
            r3 = r19
            r15 = r12
            r12 = r24
            r36 = r10
            r10 = r17
            r17 = r18
            r11 = r20
            r19 = r36
            goto L_0x02f6
        L_0x0280:
            r0 = move-exception
            r21 = r2
            r15 = r16
            r32 = r20
            r13 = r24
            r2 = r0
            r0 = r17
            r17 = r18
            r36 = r6
            r6 = r1
            r1 = r36
            goto L_0x0542
        L_0x0295:
            r0 = move-exception
            r1 = r42
            r2 = r0
            r15 = r6
            r0 = r10
            r32 = r11
            r13 = r12
            r12 = r14
            r19 = r24
            r6 = r41
            r10 = r43
            r14 = r4
            goto L_0x0542
        L_0x02a8:
            r0 = move-exception
            r43 = r1
            r1 = r42
            r2 = r0
            r15 = r6
            r0 = r10
            r32 = r11
            r13 = r12
            r12 = r14
            r19 = r24
            r6 = r41
            r10 = r43
            r14 = r4
            goto L_0x0542
        L_0x02bd:
            r0 = move-exception
            r24 = r1
            r21 = r2
            r43 = r19
            r1 = r42
            r2 = r0
            r15 = r6
            r0 = r10
            r32 = r11
            r13 = r12
            r12 = r14
            r19 = r24
            r6 = r41
            r10 = r43
            r14 = r4
            goto L_0x0542
        L_0x02d6:
            r24 = r1
            r1 = r16
            r43 = r19
            r14 = r21
            r19 = r0
            r21 = r2
            r0 = r18
            r16 = r6
            r2 = r19
            r6 = r42
            r19 = r43
            r42 = r5
            r5 = r0
            r0 = r1
            r1 = r15
            r15 = r14
            r14 = r4
            r4 = r3
            r3 = r24
        L_0x02f6:
            r43 = r0
            int r0 = r5.h     // Catch:{ all -> 0x0452 }
            r5.t0(r2, r11, r0, r13)     // Catch:{ all -> 0x0452 }
            r18 = 0
            r24 = r21
            r25 = 0
            java.nio.ByteBuffer r0 = r24.f1()     // Catch:{ all -> 0x0452 }
            if (r0 == 0) goto L_0x03db
            r26 = r0
            io.ktor.utils.io.internal.f r0 = r24.state     // Catch:{ all -> 0x0452 }
            io.ktor.utils.io.internal.k r0 = r0.b     // Catch:{ all -> 0x0452 }
            r44 = r0
            r45 = r6
            r6 = r44
            int r0 = r6.availableForRead     // Catch:{ all -> 0x03c7 }
            if (r0 != 0) goto L_0x0346
            r24.V0()     // Catch:{ all -> 0x0330 }
            r24.l1()     // Catch:{ all -> 0x0330 }
            r30 = r9
            r31 = r10
            r32 = r11
            r33 = r12
            r34 = r14
            r35 = r15
            goto L_0x03e9
        L_0x0330:
            r0 = move-exception
            r6 = r41
            r5 = r42
            r1 = r45
            r2 = r0
            r0 = r10
            r32 = r11
            r13 = r12
            r12 = r15
            r15 = r16
            r10 = r19
            r19 = r3
            r3 = r4
            goto L_0x0542
        L_0x0346:
            r0 = r26
            r44 = r6
            r27 = 0
            r28 = r0
            r29 = r0
            int r0 = r28.remaining()     // Catch:{ all -> 0x03c7 }
            r30 = r9
            r31 = r10
            long r9 = (long) r0
            int r0 = r2.remaining()     // Catch:{ all -> 0x03bd }
            r32 = r11
            r33 = r12
            long r11 = (long) r0
            r34 = r14
            r35 = r15
            long r14 = r3.element     // Catch:{ all -> 0x03bb }
            long r14 = r7 - r14
            long r11 = java.lang.Math.min(r11, r14)     // Catch:{ all -> 0x03bb }
            long r9 = java.lang.Math.min(r9, r11)     // Catch:{ all -> 0x03bb }
            int r0 = (int) r9     // Catch:{ all -> 0x03bb }
            int r9 = r1.n(r0)     // Catch:{ all -> 0x03bb }
            if (r9 <= 0) goto L_0x03a5
            r10 = r44
            boolean r11 = r10.l(r9)     // Catch:{ all -> 0x03bb }
            if (r11 == 0) goto L_0x039d
            int r11 = r28.position()     // Catch:{ all -> 0x03bb }
            int r11 = r11 + r9
            r12 = r28
            r12.limit(r11)     // Catch:{ all -> 0x03bb }
            r2.put(r12)     // Catch:{ all -> 0x03bb }
            r18 = r9
            r11 = r21
            r14 = 0
            r11.a0(r12, r10, r9)     // Catch:{ all -> 0x03bb }
            kotlin.x r11 = kotlin.x.a     // Catch:{ all -> 0x03bb }
            goto L_0x03a9
        L_0x039d:
            r12 = r28
            java.lang.AssertionError r11 = new java.lang.AssertionError     // Catch:{ all -> 0x03bb }
            r11.<init>()     // Catch:{ all -> 0x03bb }
            throw r11     // Catch:{ all -> 0x03bb }
        L_0x03a5:
            r10 = r44
            r12 = r28
        L_0x03a9:
            r0 = 1
            java.lang.Boolean r9 = kotlin.coroutines.jvm.internal.b.a(r0)     // Catch:{ all -> 0x03bb }
            r9.booleanValue()     // Catch:{ all -> 0x03bb }
            r24.V0()     // Catch:{ all -> 0x041d }
            r24.l1()     // Catch:{ all -> 0x041d }
            r0 = r18
            goto L_0x03eb
        L_0x03bb:
            r0 = move-exception
            goto L_0x03d4
        L_0x03bd:
            r0 = move-exception
            r32 = r11
            r33 = r12
            r34 = r14
            r35 = r15
            goto L_0x03d4
        L_0x03c7:
            r0 = move-exception
            r30 = r9
            r31 = r10
            r32 = r11
            r33 = r12
            r34 = r14
            r35 = r15
        L_0x03d4:
            r24.V0()     // Catch:{ all -> 0x041d }
            r24.l1()     // Catch:{ all -> 0x041d }
            throw r0     // Catch:{ all -> 0x041d }
        L_0x03db:
            r45 = r6
            r30 = r9
            r31 = r10
            r32 = r11
            r33 = r12
            r34 = r14
            r35 = r15
        L_0x03e9:
            r0 = r18
        L_0x03eb:
            if (r0 <= 0) goto L_0x0438
            r5.b0(r2, r1, r0)     // Catch:{ all -> 0x041d }
            long r9 = r3.element     // Catch:{ all -> 0x041d }
            long r11 = (long) r0     // Catch:{ all -> 0x041d }
            long r9 = r9 + r11
            r3.element = r9     // Catch:{ all -> 0x041d }
            int r6 = r13 - r0
            if (r6 == 0) goto L_0x03fc
            if (r4 == 0) goto L_0x03ff
        L_0x03fc:
            r5.flush()     // Catch:{ all -> 0x041d }
        L_0x03ff:
            r15 = r1
            r0 = r2
            r1 = r3
            r3 = r4
            r18 = r5
            r6 = r16
            r2 = r21
            r9 = r30
            r10 = r31
            r11 = r32
            r12 = r33
            r4 = r34
            r14 = r35
            r5 = r42
            r16 = r43
            r42 = r45
            goto L_0x01e1
        L_0x041d:
            r0 = move-exception
            r6 = r41
            r5 = r42
            r1 = r45
            r2 = r0
            r15 = r16
            r10 = r19
            r9 = r30
            r0 = r31
            r13 = r33
            r14 = r34
            r12 = r35
            r19 = r3
            r3 = r4
            goto L_0x0542
        L_0x0438:
            r1 = r41
            r5 = r42
            r0 = r43
            r2 = r45
            r6 = r16
            r12 = r19
            r9 = r30
            r10 = r31
            r11 = r32
            r14 = r35
            r19 = r3
            r3 = r4
            r4 = r34
            goto L_0x0490
        L_0x0452:
            r0 = move-exception
            r45 = r6
            r30 = r9
            r31 = r10
            r32 = r11
            r33 = r12
            r34 = r14
            r35 = r15
            r6 = r41
            r5 = r42
            r1 = r45
            r2 = r0
            r15 = r16
            r10 = r19
            r0 = r31
            r13 = r33
            r12 = r35
            r19 = r3
            r3 = r4
            goto L_0x0542
        L_0x0477:
            r24 = r1
            r1 = r16
            r43 = r19
            r14 = r21
            r19 = r0
            r21 = r2
            r0 = r18
            r2 = r42
            r0 = r1
            r33 = r12
            r19 = r24
            r1 = r41
            r12 = r43
        L_0x0490:
            boolean r15 = r4.h()     // Catch:{ all -> 0x04f8 }
            if (r15 != 0) goto L_0x04ae
            boolean r15 = r14.A()     // Catch:{ all -> 0x049e }
            if (r15 == 0) goto L_0x04b1
            goto L_0x04ae
        L_0x049e:
            r0 = move-exception
            r6 = r1
            r10 = r11
            r1 = r21
            r13 = r33
            r36 = r7
            r8 = r2
            r7 = r9
            r9 = r3
            r2 = r36
            goto L_0x0659
        L_0x04ae:
            r14.flush()     // Catch:{ all -> 0x04f8 }
        L_0x04b1:
            if (r14 == r10) goto L_0x04c8
            long r15 = r10.n0()     // Catch:{ all -> 0x04f8 }
            long r24 = r14.n0()     // Catch:{ all -> 0x04f8 }
            long r24 = r24 - r12
            r42 = r0
            r41 = r1
            long r0 = r15 + r24
            r10.c1(r0)     // Catch:{ all -> 0x04e7 }
            goto L_0x04cc
        L_0x04c8:
            r42 = r0
            r41 = r1
        L_0x04cc:
            r14.W0()     // Catch:{ all -> 0x04e7 }
            r14.l1()     // Catch:{ all -> 0x04e7 }
            r6 = r41
            r0 = r42
            r10 = r11
            r11 = r19
            r1 = r21
            r13 = r33
            r36 = r7
            r8 = r2
            r7 = r9
            r9 = r3
            r2 = r36
            goto L_0x059a
        L_0x04e7:
            r0 = move-exception
            r6 = r41
            r10 = r11
            r1 = r21
            r13 = r33
            r36 = r7
            r8 = r2
            r7 = r9
            r9 = r3
            r2 = r36
            goto L_0x0659
        L_0x04f8:
            r0 = move-exception
            r41 = r1
            r6 = r41
            r10 = r11
            r1 = r21
            r13 = r33
            r36 = r7
            r8 = r2
            r7 = r9
            r9 = r3
            r2 = r36
            goto L_0x0659
        L_0x050b:
            r0 = move-exception
            r24 = r1
            r43 = r19
            r14 = r21
            r21 = r2
            r1 = r42
            r2 = r0
            r15 = r6
            r0 = r10
            r32 = r11
            r13 = r12
            r12 = r14
            r19 = r24
            r6 = r41
            r10 = r43
            r14 = r4
            goto L_0x0542
        L_0x0525:
            r4 = 0
            java.lang.Throwable r20 = r0.c()     // Catch:{ all -> 0x052b }
            throw r20     // Catch:{ all -> 0x052b }
        L_0x052b:
            r0 = move-exception
            r21 = r1
            r1 = r8
            r32 = r10
            r36 = r2
            r2 = r0
            r3 = r9
            r0 = r12
            r12 = r14
            r14 = r16
            r9 = r7
            r7 = r36
            r38 = r18
            r19 = r11
            r10 = r38
        L_0x0542:
            boolean r4 = r14.h()     // Catch:{ all -> 0x058a }
            if (r4 != 0) goto L_0x055d
            boolean r4 = r12.A()     // Catch:{ all -> 0x054f }
            if (r4 == 0) goto L_0x0560
            goto L_0x055d
        L_0x054f:
            r0 = move-exception
            r10 = r32
            r36 = r7
            r8 = r1
            r7 = r9
            r1 = r21
            r9 = r3
            r2 = r36
            goto L_0x0659
        L_0x055d:
            r12.flush()     // Catch:{ all -> 0x058a }
        L_0x0560:
            if (r12 == r0) goto L_0x0575
            long r22 = r0.n0()     // Catch:{ all -> 0x058a }
            long r24 = r12.n0()     // Catch:{ all -> 0x058a }
            long r24 = r24 - r10
            r41 = r3
            long r3 = r22 + r24
            r0.c1(r3)     // Catch:{ all -> 0x057e }
            goto L_0x0577
        L_0x0575:
            r41 = r3
        L_0x0577:
            r12.W0()     // Catch:{ all -> 0x057e }
            r12.l1()     // Catch:{ all -> 0x057e }
            throw r2     // Catch:{ all -> 0x057e }
        L_0x057e:
            r0 = move-exception
            r2 = r7
            r7 = r9
            r10 = r32
            r9 = r41
            r8 = r1
            r1 = r21
            goto L_0x0659
        L_0x058a:
            r0 = move-exception
            r41 = r3
            r2 = r7
            r7 = r9
            r10 = r32
            r9 = r41
            r8 = r1
            r1 = r21
            goto L_0x0659
        L_0x0598:
            r41 = r0
        L_0x059a:
            if (r5 == 0) goto L_0x05b5
            boolean r4 = r1.j1(r5)     // Catch:{ all -> 0x064b }
            if (r4 == 0) goto L_0x05a4
            goto L_0x063f
        L_0x05a4:
            io.ktor.utils.io.internal.f r4 = r1.state     // Catch:{ all -> 0x064b }
            io.ktor.utils.io.internal.k r4 = r4.b     // Catch:{ all -> 0x064b }
            boolean r4 = r4.e()     // Catch:{ all -> 0x064b }
            if (r4 == 0) goto L_0x05b5
            r1.Z0()     // Catch:{ all -> 0x064b }
            r4 = r40
            goto L_0x0186
        L_0x05b5:
            long r14 = r11.element     // Catch:{ all -> 0x064b }
            int r4 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1))
            if (r4 < 0) goto L_0x05be
            goto L_0x063f
        L_0x05be:
            r13.flush()     // Catch:{ all -> 0x064b }
            int r4 = r1.e()     // Catch:{ all -> 0x064b }
            if (r4 != 0) goto L_0x0619
            r7.L$0 = r13     // Catch:{ all -> 0x064b }
            r7.L$1 = r1     // Catch:{ all -> 0x064b }
            r7.J$0 = r2     // Catch:{ all -> 0x064b }
            r7.L$2 = r5     // Catch:{ all -> 0x064b }
            r7.Z$0 = r9     // Catch:{ all -> 0x064b }
            r7.L$3 = r10     // Catch:{ all -> 0x064b }
            r7.L$4 = r11     // Catch:{ all -> 0x064b }
            r4 = 2
            r7.label = r4     // Catch:{ all -> 0x064b }
            r4 = 1
            java.lang.Object r12 = r1.M0(r4, r7)     // Catch:{ all -> 0x064b }
            if (r12 != r0) goto L_0x05e0
            return r0
        L_0x05e0:
            r4 = r11
            r36 = r2
            r2 = r5
            r3 = r6
            r5 = r7
            r7 = r9
            r6 = r12
            r11 = r36
        L_0x05ea:
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch:{ all -> 0x0612 }
            boolean r6 = r6.booleanValue()     // Catch:{ all -> 0x0612 }
            if (r6 == 0) goto L_0x05fb
            if (r2 == 0) goto L_0x0604
            boolean r6 = r1.j1(r2)     // Catch:{ all -> 0x0612 }
            if (r6 == 0) goto L_0x0604
            goto L_0x060b
        L_0x05fb:
            if (r2 == 0) goto L_0x060b
            boolean r6 = r1.j1(r2)     // Catch:{ all -> 0x0612 }
            if (r6 == 0) goto L_0x0604
            goto L_0x060b
        L_0x0604:
            r6 = r3
            r9 = r7
            r7 = r5
            r5 = r2
            r2 = r11
            r11 = r4
            goto L_0x0619
        L_0x060b:
            r6 = r3
            r9 = r7
            r7 = r5
            r5 = r2
            r2 = r11
            r11 = r4
            goto L_0x063f
        L_0x0612:
            r0 = move-exception
            r6 = r3
            r9 = r7
            r7 = r5
            r5 = r2
            r2 = r11
            goto L_0x0659
        L_0x0619:
            io.ktor.utils.io.a$c r4 = r13.joining     // Catch:{ all -> 0x064b }
            if (r4 == 0) goto L_0x063a
            r7.L$0 = r13     // Catch:{ all -> 0x064b }
            r7.L$1 = r1     // Catch:{ all -> 0x064b }
            r7.J$0 = r2     // Catch:{ all -> 0x064b }
            r7.L$2 = r5     // Catch:{ all -> 0x064b }
            r7.Z$0 = r9     // Catch:{ all -> 0x064b }
            r7.L$3 = r10     // Catch:{ all -> 0x064b }
            r7.L$4 = r11     // Catch:{ all -> 0x064b }
            r4 = 3
            r7.label = r4     // Catch:{ all -> 0x064b }
            r4 = 1
            java.lang.Object r12 = r13.n1(r4, r7)     // Catch:{ all -> 0x064b }
            if (r12 != r0) goto L_0x0636
            return r0
        L_0x0636:
            r4 = r40
            goto L_0x0186
        L_0x063a:
            r4 = 1
            r4 = r40
            goto L_0x0186
        L_0x063f:
            if (r9 == 0) goto L_0x0644
            r13.flush()     // Catch:{ all -> 0x064b }
        L_0x0644:
            long r14 = r11.element     // Catch:{ all -> 0x064b }
            java.lang.Long r0 = kotlin.coroutines.jvm.internal.b.d(r14)     // Catch:{ all -> 0x064b }
            return r0
        L_0x064b:
            r0 = move-exception
            goto L_0x0659
        L_0x064d:
            r0 = move-exception
            r13 = r40
            r10 = r3
            r8 = r6
            r9 = r7
            r6 = r45
            r7 = r5
            r5 = r2
            r2 = r42
        L_0x0659:
            r13.d(r0)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.e0(io.ktor.utils.io.a, long, io.ktor.utils.io.a$c, kotlin.coroutines.d):java.lang.Object");
    }

    private final void i0(c joined) {
        C0279a closed2 = this.closed;
        if (closed2 != null) {
            this.joining = null;
            if (joined.d()) {
                io.ktor.utils.io.internal.f it = joined.e().state;
                boolean writing = (it instanceof f.g) || (it instanceof f.e);
                if (closed2.b() != null || !writing) {
                    joined.e().d(closed2.b());
                } else {
                    joined.e().flush();
                }
            } else {
                joined.e().flush();
            }
            joined.b();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x00de  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int p1(java.nio.ByteBuffer r19) {
        /*
            r18 = this;
            r1 = r19
            r2 = r18
            r3 = 0
            io.ktor.utils.io.a$c r0 = r2.joining
            if (r0 == 0) goto L_0x0013
            r4 = 0
            io.ktor.utils.io.a r0 = r2.U0(r2, r0)
            if (r0 == 0) goto L_0x0013
            goto L_0x0014
        L_0x0013:
            r0 = r2
        L_0x0014:
            r4 = r0
            java.nio.ByteBuffer r0 = r4.g1()
            if (r0 == 0) goto L_0x00f3
            r6 = r0
            io.ktor.utils.io.internal.f r0 = r4.state
            io.ktor.utils.io.internal.k r7 = r0.b
            long r8 = r4.n0()
            io.ktor.utils.io.a$a r0 = r4.closed     // Catch:{ all -> 0x00c9 }
            if (r0 != 0) goto L_0x00bf
            r0 = r4
            r10 = r7
            r11 = r6
            r12 = 0
            r13 = 0
            int r14 = r19.limit()     // Catch:{ all -> 0x00c9 }
        L_0x0036:
            int r15 = r19.position()     // Catch:{ all -> 0x00c9 }
            int r15 = r14 - r15
            if (r15 != 0) goto L_0x0040
            goto L_0x004e
        L_0x0040:
            int r5 = r11.remaining()     // Catch:{ all -> 0x00c9 }
            int r5 = java.lang.Math.min(r15, r5)     // Catch:{ all -> 0x00c9 }
            int r5 = r10.n(r5)     // Catch:{ all -> 0x00c9 }
            if (r5 != 0) goto L_0x007f
        L_0x004e:
            r1.limit(r14)     // Catch:{ all -> 0x007b }
            r0.b0(r11, r10, r13)     // Catch:{ all -> 0x007b }
            boolean r0 = r7.h()
            if (r0 != 0) goto L_0x0061
            boolean r0 = r4.A()
            if (r0 == 0) goto L_0x0064
        L_0x0061:
            r4.flush()
        L_0x0064:
            if (r4 == r2) goto L_0x0074
            long r5 = r2.n0()
            long r10 = r4.n0()
            long r10 = r10 - r8
            long r5 = r5 + r10
            r2.c1(r5)
        L_0x0074:
            r4.W0()
            r4.l1()
            return r13
        L_0x007b:
            r0 = move-exception
            r17 = r3
            goto L_0x00cc
        L_0x007f:
            if (r5 <= 0) goto L_0x0084
            r16 = 1
            goto L_0x0086
        L_0x0084:
            r16 = 0
        L_0x0086:
            if (r16 == 0) goto L_0x00af
            int r16 = r19.position()     // Catch:{ all -> 0x00c9 }
            r17 = r3
            int r3 = r16 + r5
            r1.limit(r3)     // Catch:{ all -> 0x00c7 }
            r11.put(r1)     // Catch:{ all -> 0x00c7 }
            int r13 = r13 + r5
            io.ktor.utils.io.core.l r3 = r0.o0()     // Catch:{ all -> 0x00c7 }
            int r1 = r0.h     // Catch:{ all -> 0x00c7 }
            int r1 = r1 + r13
            int r1 = r0.d0(r11, r1)     // Catch:{ all -> 0x00c7 }
            r16 = r5
            int r5 = r10.availableForWrite     // Catch:{ all -> 0x00c7 }
            r0.t0(r11, r3, r1, r5)     // Catch:{ all -> 0x00c7 }
            r1 = r19
            r3 = r17
            goto L_0x0036
        L_0x00af:
            r17 = r3
            r16 = r5
            java.lang.String r1 = "Failed requirement."
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x00c7 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00c7 }
            r3.<init>(r1)     // Catch:{ all -> 0x00c7 }
            throw r3     // Catch:{ all -> 0x00c7 }
        L_0x00bf:
            r17 = r3
            r1 = 0
            java.lang.Throwable r3 = r0.c()     // Catch:{ all -> 0x00c7 }
            throw r3     // Catch:{ all -> 0x00c7 }
        L_0x00c7:
            r0 = move-exception
            goto L_0x00cc
        L_0x00c9:
            r0 = move-exception
            r17 = r3
        L_0x00cc:
            boolean r1 = r7.h()
            if (r1 != 0) goto L_0x00d9
            boolean r1 = r4.A()
            if (r1 == 0) goto L_0x00dc
        L_0x00d9:
            r4.flush()
        L_0x00dc:
            if (r4 == r2) goto L_0x00ec
            long r10 = r2.n0()
            long r12 = r4.n0()
            long r12 = r12 - r8
            long r10 = r10 + r12
            r2.c1(r10)
        L_0x00ec:
            r4.W0()
            r4.l1()
            throw r0
        L_0x00f3:
            r17 = r3
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.p1(java.nio.ByteBuffer):int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x00cb  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int o1(io.ktor.utils.io.core.c r19) {
        /*
            r18 = this;
            r1 = r18
            r2 = 0
            io.ktor.utils.io.a$c r0 = r1.joining
            if (r0 == 0) goto L_0x0011
            r3 = 0
            io.ktor.utils.io.a r0 = r1.U0(r1, r0)
            if (r0 == 0) goto L_0x0011
            goto L_0x0012
        L_0x0011:
            r0 = r1
        L_0x0012:
            r3 = r0
            java.nio.ByteBuffer r0 = r3.g1()
            if (r0 == 0) goto L_0x00e0
            r4 = r0
            io.ktor.utils.io.internal.f r0 = r3.state
            io.ktor.utils.io.internal.k r5 = r0.b
            long r6 = r3.n0()
            io.ktor.utils.io.a$a r0 = r3.closed     // Catch:{ all -> 0x00b2 }
            if (r0 != 0) goto L_0x00a4
            r0 = r3
            r8 = r5
            r9 = r4
            r10 = 0
            r11 = 0
        L_0x0030:
            r12 = r19
            r13 = 0
            int r14 = r12.s()     // Catch:{ all -> 0x00b2 }
            int r15 = r12.o()     // Catch:{ all -> 0x00b2 }
            int r14 = r14 - r15
            r12 = r14
            int r13 = r9.remaining()     // Catch:{ all -> 0x00b2 }
            int r13 = java.lang.Math.min(r12, r13)     // Catch:{ all -> 0x00b2 }
            int r13 = r8.n(r13)     // Catch:{ all -> 0x00b2 }
            if (r13 != 0) goto L_0x007e
            r0.b0(r9, r8, r11)     // Catch:{ all -> 0x0076 }
            boolean r0 = r5.h()
            if (r0 != 0) goto L_0x005c
            boolean r0 = r3.A()
            if (r0 == 0) goto L_0x005f
        L_0x005c:
            r3.flush()
        L_0x005f:
            if (r3 == r1) goto L_0x006f
            long r4 = r1.n0()
            long r8 = r3.n0()
            long r8 = r8 - r6
            long r4 = r4 + r8
            r1.c1(r4)
        L_0x006f:
            r3.W0()
            r3.l1()
            return r11
        L_0x0076:
            r0 = move-exception
            r14 = r19
            r16 = r2
            r17 = r4
            goto L_0x00b9
        L_0x007e:
            r14 = r19
            io.ktor.utils.io.core.b0.a(r14, r9, r13)     // Catch:{ all -> 0x00a2 }
            int r11 = r11 + r13
            io.ktor.utils.io.core.l r15 = r0.o0()     // Catch:{ all -> 0x00a2 }
            r16 = r2
            int r2 = r0.h     // Catch:{ all -> 0x009e }
            int r2 = r2 + r11
            int r2 = r0.d0(r9, r2)     // Catch:{ all -> 0x009e }
            r17 = r4
            int r4 = r8.availableForWrite     // Catch:{ all -> 0x00b0 }
            r0.t0(r9, r15, r2, r4)     // Catch:{ all -> 0x00b0 }
            r2 = r16
            r4 = r17
            goto L_0x0030
        L_0x009e:
            r0 = move-exception
            r17 = r4
            goto L_0x00b9
        L_0x00a2:
            r0 = move-exception
            goto L_0x00b5
        L_0x00a4:
            r14 = r19
            r16 = r2
            r17 = r4
            r2 = 0
            java.lang.Throwable r4 = r0.c()     // Catch:{ all -> 0x00b0 }
            throw r4     // Catch:{ all -> 0x00b0 }
        L_0x00b0:
            r0 = move-exception
            goto L_0x00b9
        L_0x00b2:
            r0 = move-exception
            r14 = r19
        L_0x00b5:
            r16 = r2
            r17 = r4
        L_0x00b9:
            boolean r2 = r5.h()
            if (r2 != 0) goto L_0x00c6
            boolean r2 = r3.A()
            if (r2 == 0) goto L_0x00c9
        L_0x00c6:
            r3.flush()
        L_0x00c9:
            if (r3 == r1) goto L_0x00d9
            long r8 = r1.n0()
            long r10 = r3.n0()
            long r10 = r10 - r6
            long r8 = r8 + r10
            r1.c1(r8)
        L_0x00d9:
            r3.W0()
            r3.l1()
            throw r0
        L_0x00e0:
            r14 = r19
            r16 = r2
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.o1(io.ktor.utils.io.core.c):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r0.b0(r9, r10, r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0049, code lost:
        if (r6.h() != false) goto L_0x0051;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004f, code lost:
        if (r3.A() == false) goto L_0x0054;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0051, code lost:
        r3.flush();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0054, code lost:
        if (r3 == r1) goto L_0x0064;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0056, code lost:
        c1(n0() + (r3.n0() - r7));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0064, code lost:
        r3.W0();
        r3.l1();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x006a, code lost:
        return r12;
     */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00cb  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int q1(byte[] r18, int r19, int r20) {
        /*
            r17 = this;
            r1 = r17
            r2 = 0
            io.ktor.utils.io.a$c r0 = r1.joining
            if (r0 == 0) goto L_0x0011
            r3 = 0
            io.ktor.utils.io.a r0 = r1.U0(r1, r0)
            if (r0 == 0) goto L_0x0011
            goto L_0x0012
        L_0x0011:
            r0 = r1
        L_0x0012:
            r3 = r0
            java.nio.ByteBuffer r0 = r3.g1()
            if (r0 == 0) goto L_0x00e0
            r5 = r0
            io.ktor.utils.io.internal.f r0 = r3.state
            io.ktor.utils.io.internal.k r6 = r0.b
            long r7 = r3.n0()
            io.ktor.utils.io.a$a r0 = r3.closed     // Catch:{ all -> 0x00b4 }
            if (r0 != 0) goto L_0x00a8
            r0 = r3
            r9 = r5
            r10 = r6
            r11 = 0
            r12 = 0
        L_0x0030:
            int r13 = r20 - r12
            int r14 = r9.remaining()     // Catch:{ all -> 0x00b4 }
            int r13 = java.lang.Math.min(r13, r14)     // Catch:{ all -> 0x00b4 }
            int r13 = r10.n(r13)     // Catch:{ all -> 0x00b4 }
            if (r13 != 0) goto L_0x0071
            r0.b0(r9, r10, r12)     // Catch:{ all -> 0x006b }
            boolean r0 = r6.h()
            if (r0 != 0) goto L_0x0051
            boolean r0 = r3.A()
            if (r0 == 0) goto L_0x0054
        L_0x0051:
            r3.flush()
        L_0x0054:
            if (r3 == r1) goto L_0x0064
            long r4 = r1.n0()
            long r9 = r3.n0()
            long r9 = r9 - r7
            long r4 = r4 + r9
            r1.c1(r4)
        L_0x0064:
            r3.W0()
            r3.l1()
            return r12
        L_0x006b:
            r0 = move-exception
            r15 = r18
            r16 = r2
            goto L_0x00b9
        L_0x0071:
            if (r13 <= 0) goto L_0x0075
            r14 = 1
            goto L_0x0076
        L_0x0075:
            r14 = 0
        L_0x0076:
            if (r14 == 0) goto L_0x0098
            int r14 = r19 + r12
            r15 = r18
            r9.put(r15, r14, r13)     // Catch:{ all -> 0x0096 }
            int r12 = r12 + r13
            io.ktor.utils.io.core.l r14 = r0.o0()     // Catch:{ all -> 0x0096 }
            int r4 = r0.h     // Catch:{ all -> 0x0096 }
            int r4 = r4 + r12
            int r4 = r0.d0(r9, r4)     // Catch:{ all -> 0x0096 }
            r16 = r2
            int r2 = r10.availableForWrite     // Catch:{ all -> 0x00b2 }
            r0.t0(r9, r14, r4, r2)     // Catch:{ all -> 0x00b2 }
            r2 = r16
            goto L_0x0030
        L_0x0096:
            r0 = move-exception
            goto L_0x00b7
        L_0x0098:
            r15 = r18
            r16 = r2
            java.lang.String r2 = "Failed requirement."
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x00b2 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00b2 }
            r4.<init>(r2)     // Catch:{ all -> 0x00b2 }
            throw r4     // Catch:{ all -> 0x00b2 }
        L_0x00a8:
            r15 = r18
            r16 = r2
            r2 = 0
            java.lang.Throwable r4 = r0.c()     // Catch:{ all -> 0x00b2 }
            throw r4     // Catch:{ all -> 0x00b2 }
        L_0x00b2:
            r0 = move-exception
            goto L_0x00b9
        L_0x00b4:
            r0 = move-exception
            r15 = r18
        L_0x00b7:
            r16 = r2
        L_0x00b9:
            boolean r2 = r6.h()
            if (r2 != 0) goto L_0x00c6
            boolean r2 = r3.A()
            if (r2 == 0) goto L_0x00c9
        L_0x00c6:
            r3.flush()
        L_0x00c9:
            if (r3 == r1) goto L_0x00d9
            long r9 = r1.n0()
            long r11 = r3.n0()
            long r11 = r11 - r7
            long r9 = r9 + r11
            r1.c1(r9)
        L_0x00d9:
            r3.W0()
            r3.l1()
            throw r0
        L_0x00e0:
            r15 = r18
            r16 = r2
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.q1(byte[], int, int):int");
    }

    static /* synthetic */ Object v1(a aVar, byte[] src, int offset, int length, kotlin.coroutines.d $completion) {
        a it;
        c it2 = aVar.joining;
        if (it2 == null || (it = aVar.U0(aVar, it2)) == null) {
            int rem = length;
            int off = offset;
            while (rem > 0) {
                int s2 = aVar.q1(src, off, rem);
                if (s2 == 0) {
                    break;
                }
                off += s2;
                rem -= s2;
            }
            if (rem == 0) {
                return kotlin.x.a;
            }
            Object y1 = aVar.y1(src, off, rem, $completion);
            return y1 == kotlin.coroutines.intrinsics.c.d() ? y1 : kotlin.x.a;
        }
        Object q2 = it.q(src, offset, length, $completion);
        return q2 == kotlin.coroutines.intrinsics.c.d() ? q2 : kotlin.x.a;
    }

    /* Debug info: failed to restart local var, previous not found, register: 6 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v3, resolved type: byte[]} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object y1(@org.jetbrains.annotations.NotNull byte[] r7, int r8, int r9, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r10) {
        /*
            r6 = this;
            boolean r0 = r10 instanceof io.ktor.utils.io.a.x
            if (r0 == 0) goto L_0x0013
            r0 = r10
            io.ktor.utils.io.a$x r0 = (io.ktor.utils.io.a.x) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.a$x r0 = new io.ktor.utils.io.a$x
            r0.<init>(r6, r10)
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
            int r9 = r0.I$1
            int r8 = r0.I$0
            java.lang.Object r3 = r0.L$1
            r7 = r3
            byte[] r7 = (byte[]) r7
            java.lang.Object r3 = r0.L$0
            io.ktor.utils.io.a r3 = (io.ktor.utils.io.a) r3
            kotlin.p.b(r1)
            r4 = r3
            r3 = r2
            r2 = r1
            goto L_0x005f
        L_0x003f:
            kotlin.p.b(r1)
            r3 = r6
        L_0x0043:
            if (r9 != 0) goto L_0x0048
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x0048:
            r0.L$0 = r3
            r0.L$1 = r7
            r0.I$0 = r8
            r0.I$1 = r9
            r4 = 1
            r0.label = r4
            java.lang.Object r4 = r3.r1(r7, r8, r9, r0)
            if (r4 != r2) goto L_0x005a
            return r2
        L_0x005a:
            r5 = r2
            r2 = r1
            r1 = r4
            r4 = r3
            r3 = r5
        L_0x005f:
            java.lang.Number r1 = (java.lang.Number) r1
            int r1 = r1.intValue()
            int r8 = r8 + r1
            int r9 = r9 - r1
            r1 = r2
            r2 = r3
            r3 = r4
            goto L_0x0043
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.y1(byte[], int, int, kotlin.coroutines.d):java.lang.Object");
    }

    static /* synthetic */ Object s1(a aVar, byte[] src, int offset, int length, kotlin.coroutines.d $completion) {
        a it;
        c it2 = aVar.joining;
        if (it2 != null && (it = aVar.U0(aVar, it2)) != null) {
            return it.r1(src, offset, length, $completion);
        }
        int size = aVar.q1(src, offset, length);
        if (size > 0) {
            return kotlin.coroutines.jvm.internal.b.c(size);
        }
        return aVar.C1(src, offset, length, $completion);
    }

    /* Debug info: failed to restart local var, previous not found, register: 10 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v3, resolved type: byte[]} */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0072 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00a2  */
    @org.jetbrains.annotations.Nullable
    final /* synthetic */ java.lang.Object C1(@org.jetbrains.annotations.NotNull byte[] r11, int r12, int r13, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Integer> r14) {
        /*
            r10 = this;
            boolean r0 = r14 instanceof io.ktor.utils.io.a.z
            if (r0 == 0) goto L_0x0013
            r0 = r14
            io.ktor.utils.io.a$z r0 = (io.ktor.utils.io.a.z) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.a$z r0 = new io.ktor.utils.io.a$z
            r0.<init>(r10, r14)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x005c;
                case 1: goto L_0x004b;
                case 2: goto L_0x002b;
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
            r5 = r4
            java.lang.Object r6 = r0.L$3
            r4 = r6
            io.ktor.utils.io.a r4 = (io.ktor.utils.io.a) r4
            java.lang.Object r6 = r0.L$2
            r5 = r6
            io.ktor.utils.io.a$c r5 = (io.ktor.utils.io.a.c) r5
            int r13 = r0.I$1
            int r12 = r0.I$0
            java.lang.Object r6 = r0.L$1
            r11 = r6
            byte[] r11 = (byte[]) r11
            java.lang.Object r6 = r0.L$0
            io.ktor.utils.io.a r6 = (io.ktor.utils.io.a) r6
            kotlin.p.b(r1)
            r8 = r1
            goto L_0x009b
        L_0x004b:
            int r13 = r0.I$1
            int r12 = r0.I$0
            java.lang.Object r3 = r0.L$1
            r11 = r3
            byte[] r11 = (byte[]) r11
            java.lang.Object r3 = r0.L$0
            io.ktor.utils.io.a r3 = (io.ktor.utils.io.a) r3
            kotlin.p.b(r1)
            goto L_0x0073
        L_0x005c:
            kotlin.p.b(r1)
            r3 = r10
        L_0x0060:
            r0.L$0 = r3
            r0.L$1 = r11
            r0.I$0 = r12
            r0.I$1 = r13
            r4 = 1
            r0.label = r4
            java.lang.Object r4 = r3.n1(r4, r0)
            if (r4 != r2) goto L_0x0073
            return r2
        L_0x0073:
            io.ktor.utils.io.a$c r4 = r3.joining
            if (r4 == 0) goto L_0x009c
            r5 = r4
            r4 = 0
            io.ktor.utils.io.a r6 = r3.U0(r3, r5)
            if (r6 == 0) goto L_0x009c
            r7 = 0
            r0.L$0 = r3
            r0.L$1 = r11
            r0.I$0 = r12
            r0.I$1 = r13
            r0.L$2 = r5
            r0.L$3 = r6
            r8 = 2
            r0.label = r8
            java.lang.Object r8 = r6.C1(r11, r12, r13, r0)
            if (r8 != r2) goto L_0x0096
            return r2
        L_0x0096:
            r2 = r7
            r9 = r6
            r6 = r3
            r3 = r4
            r4 = r9
        L_0x009b:
            return r8
        L_0x009c:
            int r4 = r3.q1(r11, r12, r13)
            if (r4 <= 0) goto L_0x00a7
            java.lang.Integer r2 = kotlin.coroutines.jvm.internal.b.c(r4)
            return r2
        L_0x00a7:
            goto L_0x0060
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.C1(byte[], int, int, kotlin.coroutines.d):java.lang.Object");
    }

    static /* synthetic */ Object F1(a aVar, kotlin.jvm.functions.l block, kotlin.coroutines.d $completion) {
        if (!aVar.H1(block)) {
            return kotlin.x.a;
        }
        C0279a it = aVar.closed;
        if (it == null) {
            Object I1 = aVar.I1(block, $completion);
            return I1 == kotlin.coroutines.intrinsics.c.d() ? I1 : kotlin.x.a;
        }
        throw it.c();
    }

    private final boolean H1(kotlin.jvm.functions.l<? super ByteBuffer, Boolean> block) {
        a current$iv;
        boolean continueWriting = true;
        c it$iv = this.joining;
        if (it$iv == null || (current$iv = U0(this, it$iv)) == null) {
            current$iv = this;
        }
        ByteBuffer buffer$iv = current$iv.g1();
        if (buffer$iv != null) {
            io.ktor.utils.io.internal.k capacity$iv = current$iv.state.b;
            long before$iv = current$iv.n0();
            try {
                C0279a it$iv2 = current$iv.closed;
                if (it$iv2 == null) {
                    continueWriting = current$iv.G1(buffer$iv, capacity$iv, block);
                } else {
                    throw it$iv2.c();
                }
            } finally {
                if (capacity$iv.h() || current$iv.A()) {
                    current$iv.flush();
                }
                if (current$iv != this) {
                    c1(n0() + (current$iv.n0() - before$iv));
                }
                current$iv.W0();
                current$iv.l1();
            }
        }
        return continueWriting;
    }

    /* Debug info: failed to restart local var, previous not found, register: 25 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v8, resolved type: java.nio.ByteBuffer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v12, resolved type: kotlin.jvm.internal.w} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v33, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v4, resolved type: io.ktor.utils.io.a} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r5.L$0 = r3;
        r5.L$1 = r1;
        r5.L$2 = r7;
        r5.L$3 = r2;
        r5.L$4 = r15;
        r5.L$5 = r13;
        r5.L$6 = r12;
        r5.J$0 = r10;
        r5.L$7 = r0;
        r5.L$8 = r8;
        r5.L$9 = r9;
        r18 = r0;
        r5.label = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0126, code lost:
        r17 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x012c, code lost:
        if (r9.B1(1, r5) != r4) goto L_0x012f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x012e, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x012f, code lost:
        r1 = r26;
        r22 = r14;
        r14 = r2;
        r2 = r18;
        r18 = r16;
        r16 = r22;
        r23 = r17;
        r17 = r15;
        r15 = r23;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0142, code lost:
        if (r9.joining == null) goto L_0x0145;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0149, code lost:
        if (r9.G1(r8, r2, r15) != false) goto L_0x014f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x014b, code lost:
        r7.element = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0152, code lost:
        if (r9.closed == null) goto L_0x0182;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0159, code lost:
        if (r13.h() != false) goto L_0x0161;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x015f, code lost:
        if (r12.A() == false) goto L_0x0164;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0161, code lost:
        r12.flush();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0164, code lost:
        if (r12 == r14) goto L_0x0176;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0166, code lost:
        r14.c1(r14.n0() + (r12.n0() - r10));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0176, code lost:
        r12.W0();
        r12.l1();
        r8 = r3;
        r3 = r5;
        r0 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0182, code lost:
        r26 = r1;
        r0 = r2;
        r2 = r14;
        r1 = r15;
        r14 = r16;
        r15 = r17;
        r16 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x018f, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0190, code lost:
        r21 = r3;
        r3 = r5;
        r4 = r6;
        r5 = r7;
        r8 = r10;
        r10 = r12;
        r11 = r13;
        r12 = r16;
        r13 = r17;
        r17 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x019f, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x01a0, code lost:
        r1 = r26;
        r21 = r3;
        r3 = r5;
        r4 = r6;
        r5 = r7;
        r8 = r10;
        r10 = r12;
        r11 = r13;
        r12 = r14;
        r13 = r15;
        r14 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x01ae, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x01af, code lost:
        r17 = r1;
        r1 = r26;
        r21 = r3;
        r3 = r5;
        r4 = r6;
        r5 = r7;
        r8 = r10;
        r10 = r12;
        r11 = r13;
        r12 = r14;
        r13 = r15;
        r14 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x01fa, code lost:
        if (r0.element != false) goto L_0x01ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01fe, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01ff, code lost:
        r2 = r8.closed;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0201, code lost:
        if (r2 != null) goto L_0x0223;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0203, code lost:
        r2 = r8.joining;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0205, code lost:
        if (r2 == null) goto L_0x0220;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0207, code lost:
        r3.L$0 = r8;
        r3.L$1 = r15;
        r3.L$2 = r0;
        r3.L$3 = r2;
        r3.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0217, code lost:
        if (r8.z(r15, r3) != r4) goto L_0x021a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0219, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x021a, code lost:
        r4 = r6;
        r7 = r15;
        r6 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x021f, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0222, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0228, code lost:
        throw r2.c();
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x01db  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002c  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object I1(@org.jetbrains.annotations.NotNull kotlin.jvm.functions.l<? super java.nio.ByteBuffer, java.lang.Boolean> r26, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r27) {
        /*
            r25 = this;
            r1 = r27
            boolean r0 = r1 instanceof io.ktor.utils.io.a.d0
            if (r0 == 0) goto L_0x0017
            r0 = r1
            io.ktor.utils.io.a$d0 r0 = (io.ktor.utils.io.a.d0) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0017
            int r2 = r2 - r3
            r0.label = r2
            r2 = r25
            goto L_0x001e
        L_0x0017:
            io.ktor.utils.io.a$d0 r0 = new io.ktor.utils.io.a$d0
            r2 = r25
            r0.<init>(r2, r1)
        L_0x001e:
            r3 = r0
            java.lang.Object r4 = r3.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r5 = r3.label
            r7 = 0
            r8 = 0
            switch(r5) {
                case 0: goto L_0x00b3;
                case 1: goto L_0x004e;
                case 2: goto L_0x0034;
                default: goto L_0x002c;
            }
        L_0x002c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0034:
            r0 = r8
            r5 = r7
            r6 = r8
            java.lang.Object r7 = r3.L$3
            r6 = r7
            io.ktor.utils.io.a$c r6 = (io.ktor.utils.io.a.c) r6
            java.lang.Object r7 = r3.L$2
            r0 = r7
            kotlin.jvm.internal.w r0 = (kotlin.jvm.internal.w) r0
            java.lang.Object r7 = r3.L$1
            kotlin.jvm.functions.l r7 = (kotlin.jvm.functions.l) r7
            java.lang.Object r8 = r3.L$0
            io.ktor.utils.io.a r8 = (io.ktor.utils.io.a) r8
            kotlin.p.b(r4)
            goto L_0x021d
        L_0x004e:
            r5 = r8
            r9 = 0
            r11 = r8
            r12 = r7
            r13 = r8
            r14 = r8
            r15 = r8
            r16 = r7
            r17 = r8
            r18 = r8
            java.lang.Object r7 = r3.L$9
            io.ktor.utils.io.a r7 = (io.ktor.utils.io.a) r7
            java.lang.Object r6 = r3.L$8
            java.nio.ByteBuffer r6 = (java.nio.ByteBuffer) r6
            r18 = r0
            java.lang.Object r0 = r3.L$7
            io.ktor.utils.io.internal.k r0 = (io.ktor.utils.io.internal.k) r0
            long r8 = r3.J$0
            java.lang.Object r10 = r3.L$6
            io.ktor.utils.io.a r10 = (io.ktor.utils.io.a) r10
            java.lang.Object r11 = r3.L$5
            io.ktor.utils.io.internal.k r11 = (io.ktor.utils.io.internal.k) r11
            java.lang.Object r14 = r3.L$4
            r13 = r14
            java.nio.ByteBuffer r13 = (java.nio.ByteBuffer) r13
            java.lang.Object r14 = r3.L$3
            io.ktor.utils.io.a r14 = (io.ktor.utils.io.a) r14
            java.lang.Object r15 = r3.L$2
            r5 = r15
            kotlin.jvm.internal.w r5 = (kotlin.jvm.internal.w) r5
            java.lang.Object r15 = r3.L$1
            kotlin.jvm.functions.l r15 = (kotlin.jvm.functions.l) r15
            r20 = r0
            java.lang.Object r0 = r3.L$0
            r21 = r0
            io.ktor.utils.io.a r21 = (io.ktor.utils.io.a) r21
            kotlin.p.b(r4)     // Catch:{ all -> 0x00ae }
            r17 = r13
            r2 = r20
            r0 = 1
            r13 = r11
            r22 = r5
            r5 = r3
            r3 = r21
            r23 = r6
            r6 = r4
            r4 = r18
            r18 = r16
            r16 = r12
            r12 = r10
            r10 = r8
            r8 = r23
            r9 = r7
            r7 = r22
            goto L_0x0140
        L_0x00ae:
            r0 = move-exception
            r17 = r15
            goto L_0x01ca
        L_0x00b3:
            r18 = r0
            kotlin.p.b(r4)
            kotlin.jvm.internal.w r0 = new kotlin.jvm.internal.w
            r0.<init>()
            r5 = 1
            r0.element = r5
            r5 = r0
            r14 = r25
            r12 = 0
            io.ktor.utils.io.a$c r0 = r14.joining
            if (r0 == 0) goto L_0x00d2
            r6 = 0
            io.ktor.utils.io.a r0 = r14.U0(r14, r0)
            if (r0 == 0) goto L_0x00d2
            goto L_0x00d3
        L_0x00d2:
            r0 = r14
        L_0x00d3:
            r10 = r0
            java.nio.ByteBuffer r0 = r10.g1()
            if (r0 == 0) goto L_0x01f0
            r13 = r0
            io.ktor.utils.io.internal.f r0 = r10.state
            io.ktor.utils.io.internal.k r11 = r0.b
            long r8 = r10.n0()
            io.ktor.utils.io.a$a r0 = r10.closed     // Catch:{ all -> 0x01c5 }
            if (r0 != 0) goto L_0x01bf
            r0 = r10
            r6 = r13
            r7 = r11
            r15 = 0
            r16 = r15
            r15 = r13
            r13 = r11
            r22 = r1
            r1 = r26
            r26 = r22
            r23 = r8
            r9 = r0
            r8 = r6
            r0 = r7
            r6 = r4
            r7 = r5
            r4 = r18
            r5 = r3
            r3 = r2
            r2 = r14
            r14 = r12
            r12 = r10
            r10 = r23
        L_0x010a:
            r5.L$0 = r3     // Catch:{ all -> 0x01ae }
            r5.L$1 = r1     // Catch:{ all -> 0x01ae }
            r5.L$2 = r7     // Catch:{ all -> 0x01ae }
            r5.L$3 = r2     // Catch:{ all -> 0x01ae }
            r5.L$4 = r15     // Catch:{ all -> 0x01ae }
            r5.L$5 = r13     // Catch:{ all -> 0x01ae }
            r5.L$6 = r12     // Catch:{ all -> 0x01ae }
            r5.J$0 = r10     // Catch:{ all -> 0x01ae }
            r5.L$7 = r0     // Catch:{ all -> 0x01ae }
            r5.L$8 = r8     // Catch:{ all -> 0x01ae }
            r5.L$9 = r9     // Catch:{ all -> 0x01ae }
            r18 = r0
            r0 = 1
            r5.label = r0     // Catch:{ all -> 0x01ae }
            r17 = r1
            java.lang.Object r1 = r9.B1(r0, r5)     // Catch:{ all -> 0x019f }
            if (r1 != r4) goto L_0x012f
            return r4
        L_0x012f:
            r1 = r26
            r22 = r14
            r14 = r2
            r2 = r18
            r18 = r16
            r16 = r22
            r23 = r17
            r17 = r15
            r15 = r23
        L_0x0140:
            io.ktor.utils.io.a$c r0 = r9.joining     // Catch:{ all -> 0x018f }
            if (r0 == 0) goto L_0x0145
            goto L_0x0154
        L_0x0145:
            boolean r0 = r9.G1(r8, r2, r15)     // Catch:{ all -> 0x018f }
            if (r0 != 0) goto L_0x014f
            r0 = 0
            r7.element = r0     // Catch:{ all -> 0x018f }
            goto L_0x0154
        L_0x014f:
            r0 = 0
            io.ktor.utils.io.a$a r0 = r9.closed     // Catch:{ all -> 0x018f }
            if (r0 == 0) goto L_0x0182
        L_0x0154:
            boolean r0 = r13.h()
            if (r0 != 0) goto L_0x0161
            boolean r0 = r12.A()
            if (r0 == 0) goto L_0x0164
        L_0x0161:
            r12.flush()
        L_0x0164:
            if (r12 == r14) goto L_0x0176
            long r8 = r14.n0()
            long r18 = r12.n0()
            long r18 = r18 - r10
            long r8 = r8 + r18
            r14.c1(r8)
        L_0x0176:
            r12.W0()
            r12.l1()
            r8 = r3
            r3 = r5
            r0 = r7
            goto L_0x01f8
        L_0x0182:
            r26 = r1
            r0 = r2
            r2 = r14
            r1 = r15
            r14 = r16
            r15 = r17
            r16 = r18
            goto L_0x010a
        L_0x018f:
            r0 = move-exception
            r21 = r3
            r3 = r5
            r4 = r6
            r5 = r7
            r8 = r10
            r10 = r12
            r11 = r13
            r12 = r16
            r13 = r17
            r17 = r15
            goto L_0x01ca
        L_0x019f:
            r0 = move-exception
            r1 = r26
            r21 = r3
            r3 = r5
            r4 = r6
            r5 = r7
            r8 = r10
            r10 = r12
            r11 = r13
            r12 = r14
            r13 = r15
            r14 = r2
            goto L_0x01ca
        L_0x01ae:
            r0 = move-exception
            r17 = r1
            r1 = r26
            r21 = r3
            r3 = r5
            r4 = r6
            r5 = r7
            r8 = r10
            r10 = r12
            r11 = r13
            r12 = r14
            r13 = r15
            r14 = r2
            goto L_0x01ca
        L_0x01bf:
            r2 = 0
            java.lang.Throwable r6 = r0.c()     // Catch:{ all -> 0x01c5 }
            throw r6     // Catch:{ all -> 0x01c5 }
        L_0x01c5:
            r0 = move-exception
            r21 = r25
            r17 = r26
        L_0x01ca:
            boolean r2 = r11.h()
            if (r2 != 0) goto L_0x01d6
            boolean r2 = r10.A()
            if (r2 == 0) goto L_0x01d9
        L_0x01d6:
            r10.flush()
        L_0x01d9:
            if (r10 == r14) goto L_0x01e9
            long r6 = r14.n0()
            long r15 = r10.n0()
            long r15 = r15 - r8
            long r6 = r6 + r15
            r14.c1(r6)
        L_0x01e9:
            r10.W0()
            r10.l1()
            throw r0
        L_0x01f0:
            r8 = r25
            r15 = r26
            r6 = r4
            r0 = r5
            r4 = r18
        L_0x01f8:
            boolean r2 = r0.element
            if (r2 != 0) goto L_0x01ff
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x01ff:
            io.ktor.utils.io.a$a r2 = r8.closed
            if (r2 != 0) goto L_0x0223
            io.ktor.utils.io.a$c r2 = r8.joining
            if (r2 == 0) goto L_0x0220
            r5 = 0
            r3.L$0 = r8
            r3.L$1 = r15
            r3.L$2 = r0
            r3.L$3 = r2
            r7 = 2
            r3.label = r7
            java.lang.Object r7 = r8.z(r15, r3)
            if (r7 != r4) goto L_0x021a
            return r4
        L_0x021a:
            r4 = r6
            r7 = r15
            r6 = r2
        L_0x021d:
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x0220:
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x0223:
            r4 = 0
            java.lang.Throwable r5 = r2.c()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.I1(kotlin.jvm.functions.l, kotlin.coroutines.d):java.lang.Object");
    }

    private final boolean G1(ByteBuffer dst, io.ktor.utils.io.internal.k capacity, kotlin.jvm.functions.l<? super ByteBuffer, Boolean> block) {
        boolean continueWriting = true;
        int bufferLimit = dst.capacity() - this.r;
        while (continueWriting) {
            int locked = capacity.m(1);
            if (locked == 0) {
                break;
            }
            int position = this.h;
            int l2 = kotlin.ranges.n.e(position + locked, bufferLimit);
            dst.limit(l2);
            dst.position(position);
            try {
                continueWriting = block.invoke(dst).booleanValue();
                if (dst.limit() == l2) {
                    int actuallyWritten = dst.position() - position;
                    if (actuallyWritten >= 0) {
                        b0(dst, capacity, actuallyWritten);
                        if (actuallyWritten < locked) {
                            locked -= actuallyWritten;
                        }
                    } else {
                        throw new IllegalStateException("position has been moved backward: pushback is not supported");
                    }
                } else {
                    throw new IllegalStateException("buffer limit modified");
                }
            } finally {
                capacity.a(locked);
            }
        }
        return continueWriting;
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel$readSuspendableSession$2", f = "ByteBufferChannel.kt", l = {1716}, m = "invokeSuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class r extends kotlin.coroutines.jvm.internal.l implements kotlin.jvm.functions.p<t, kotlin.coroutines.d<? super kotlin.x>, Object> {
        final /* synthetic */ kotlin.jvm.functions.p $consumer;
        Object L$0;
        int label;
        private t p$;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        r(a aVar, kotlin.jvm.functions.p pVar, kotlin.coroutines.d dVar) {
            super(2, dVar);
            this.this$0 = aVar;
            this.$consumer = pVar;
        }

        @NotNull
        public final kotlin.coroutines.d<kotlin.x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            kotlin.jvm.internal.k.f(dVar, "completion");
            r rVar = new r(this.this$0, this.$consumer, dVar);
            t tVar = (t) obj;
            rVar.p$ = (t) obj;
            return rVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((r) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(kotlin.x.a);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0038, code lost:
            io.ktor.utils.io.a.G(r6.this$0).b();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0044, code lost:
            return kotlin.x.a;
         */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r7) {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r1 = r6.label
                switch(r1) {
                    case 0: goto L_0x001d;
                    case 1: goto L_0x0011;
                    default: goto L_0x0009;
                }
            L_0x0009:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0011:
                r0 = 0
                java.lang.Object r1 = r6.L$0
                r0 = r1
                io.ktor.utils.io.t r0 = (io.ktor.utils.io.t) r0
                kotlin.p.b(r7)     // Catch:{ all -> 0x001b }
                goto L_0x0038
            L_0x001b:
                r1 = move-exception
                goto L_0x0049
            L_0x001d:
                kotlin.p.b(r7)
                io.ktor.utils.io.t r1 = r6.p$
                kotlin.jvm.functions.p r2 = r6.$consumer     // Catch:{ all -> 0x0045 }
                io.ktor.utils.io.a r3 = r6.this$0     // Catch:{ all -> 0x0045 }
                io.ktor.utils.io.internal.e r3 = r3.k     // Catch:{ all -> 0x0045 }
                r6.L$0 = r1     // Catch:{ all -> 0x0045 }
                r4 = 1
                r6.label = r4     // Catch:{ all -> 0x0045 }
                java.lang.Object r2 = r2.invoke(r3, r6)     // Catch:{ all -> 0x0045 }
                if (r2 != r0) goto L_0x0037
                return r0
            L_0x0037:
                r0 = r1
            L_0x0038:
                io.ktor.utils.io.a r1 = r6.this$0
                io.ktor.utils.io.internal.e r1 = r1.k
                r1.b()
                kotlin.x r1 = kotlin.x.a
                return r1
            L_0x0045:
                r0 = move-exception
                r5 = r1
                r1 = r0
                r0 = r5
            L_0x0049:
                io.ktor.utils.io.a r2 = r6.this$0
                io.ktor.utils.io.internal.e r2 = r2.k
                r2.b()
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.r.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    static /* synthetic */ Object O0(a aVar, kotlin.jvm.functions.p consumer, kotlin.coroutines.d $completion) {
        Object l2 = aVar.l(new r(aVar, consumer, (kotlin.coroutines.d) null), $completion);
        if (l2 == kotlin.coroutines.intrinsics.c.d()) {
            return l2;
        }
        return kotlin.x.a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x006f  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x007b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object g0(io.ktor.utils.io.a r16, long r17, kotlin.coroutines.d r19) {
        /*
            r7 = r17
            r0 = 0
            int r0 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            r1 = 1
            if (r0 < 0) goto L_0x000b
            r0 = r1
            goto L_0x000c
        L_0x000b:
            r0 = 0
        L_0x000c:
            if (r0 == 0) goto L_0x0080
            r2 = 0
            r4 = r16
            r5 = 0
            java.nio.ByteBuffer r0 = r4.f1()
            if (r0 == 0) goto L_0x0061
            r6 = r0
            io.ktor.utils.io.internal.f r0 = r4.state
            io.ktor.utils.io.internal.k r9 = r0.b
            int r0 = r9.availableForRead     // Catch:{ all -> 0x0057 }
            if (r0 != 0) goto L_0x002e
            r4.V0()
            r4.l1()
            r13 = r16
            goto L_0x0063
        L_0x002e:
            r0 = r6
            r10 = r9
            r11 = 0
            r12 = 2147483647(0x7fffffff, float:NaN)
            long r12 = (long) r12
            long r12 = java.lang.Math.min(r12, r7)     // Catch:{ all -> 0x0057 }
            int r12 = (int) r12     // Catch:{ all -> 0x0057 }
            int r12 = r10.k(r12)     // Catch:{ all -> 0x0057 }
            r13 = r16
            r13.a0(r0, r10, r12)     // Catch:{ all -> 0x0055 }
            long r14 = (long) r12     // Catch:{ all -> 0x0055 }
            long r2 = r2 + r14
            java.lang.Boolean r0 = kotlin.coroutines.jvm.internal.b.a(r1)     // Catch:{ all -> 0x0055 }
            r0.booleanValue()     // Catch:{ all -> 0x0055 }
            r4.V0()
            r4.l1()
            r9 = r2
            goto L_0x0064
        L_0x0055:
            r0 = move-exception
            goto L_0x005a
        L_0x0057:
            r0 = move-exception
            r13 = r16
        L_0x005a:
            r4.V0()
            r4.l1()
            throw r0
        L_0x0061:
            r13 = r16
        L_0x0063:
            r9 = r2
        L_0x0064:
            int r0 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r0 == 0) goto L_0x007b
            boolean r0 = r16.y()
            if (r0 == 0) goto L_0x006f
            goto L_0x007b
        L_0x006f:
            r1 = r16
            r2 = r9
            r4 = r17
            r6 = r19
            java.lang.Object r0 = r1.h0(r2, r4, r6)
            return r0
        L_0x007b:
            java.lang.Long r0 = kotlin.coroutines.jvm.internal.b.d(r9)
            return r0
        L_0x0080:
            r13 = r16
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "max shouldn't be negative: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r0 = r1.toString()
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.g0(io.ktor.utils.io.a, long, kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 24 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v0, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v8, resolved type: kotlin.jvm.internal.y} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0133  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002a  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object h0(long r25, long r27, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Long> r29) {
        /*
            r24 = this;
            r0 = r29
            boolean r1 = r0 instanceof io.ktor.utils.io.a.h
            if (r1 == 0) goto L_0x0017
            r1 = r0
            io.ktor.utils.io.a$h r1 = (io.ktor.utils.io.a.h) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0017
            int r2 = r2 - r3
            r1.label = r2
            r2 = r24
            goto L_0x001e
        L_0x0017:
            io.ktor.utils.io.a$h r1 = new io.ktor.utils.io.a$h
            r2 = r24
            r1.<init>(r2, r0)
        L_0x001e:
            java.lang.Object r3 = r1.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.c.d()
            int r5 = r1.label
            r6 = 0
            switch(r5) {
                case 0: goto L_0x004c;
                case 1: goto L_0x0034;
                default: goto L_0x002a;
            }
        L_0x002a:
            r7 = r25
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r1
        L_0x0034:
            r5 = r6
            r7 = 0
            java.lang.Object r8 = r1.L$1
            r7 = r8
            kotlin.jvm.internal.y r7 = (kotlin.jvm.internal.y) r7
            long r8 = r1.J$1
            long r10 = r1.J$0
            java.lang.Object r12 = r1.L$0
            io.ktor.utils.io.a r12 = (io.ktor.utils.io.a) r12
            kotlin.p.b(r3)
            r2 = r12
            r12 = r10
            r10 = r8
            r9 = r3
            goto L_0x0110
        L_0x004c:
            kotlin.p.b(r3)
            kotlin.jvm.internal.y r5 = new kotlin.jvm.internal.y
            r5.<init>()
            r7 = r25
            r5.element = r7
            r12 = r2
            r9 = r3
            r10 = r5
            r5 = r1
            r1 = r0
            r0 = r4
            r3 = r27
        L_0x0060:
            long r13 = r10.element
            int r11 = (r13 > r3 ? 1 : (r13 == r3 ? 0 : -1))
            if (r11 >= 0) goto L_0x0133
            r11 = r12
            r13 = 0
            java.nio.ByteBuffer r14 = r11.f1()
            if (r14 == 0) goto L_0x00d6
            io.ktor.utils.io.internal.f r6 = r11.state
            io.ktor.utils.io.internal.k r6 = r6.b
            int r15 = r6.availableForRead     // Catch:{ all -> 0x00c6 }
            if (r15 != 0) goto L_0x0085
            r11.V0()
            r11.l1()
            r27 = r1
            r17 = r3
            r1 = 0
            goto L_0x00dd
        L_0x0085:
            r15 = r14
            r26 = r6
            r16 = 0
            r27 = r1
            r1 = 2147483647(0x7fffffff, float:NaN)
            long r1 = (long) r1
            r28 = r13
            r29 = r14
            long r13 = r10.element     // Catch:{ all -> 0x00c2 }
            long r13 = r3 - r13
            long r1 = java.lang.Math.min(r1, r13)     // Catch:{ all -> 0x00c2 }
            int r1 = (int) r1     // Catch:{ all -> 0x00c2 }
            r2 = r26
            int r1 = r2.k(r1)     // Catch:{ all -> 0x00c2 }
            r12.a0(r15, r2, r1)     // Catch:{ all -> 0x00c2 }
            long r13 = r10.element     // Catch:{ all -> 0x00c2 }
            r17 = r3
            r4 = r2
            long r2 = (long) r1
            long r13 = r13 + r2
            r10.element = r13     // Catch:{ all -> 0x00c0 }
            r1 = 1
            java.lang.Boolean r2 = kotlin.coroutines.jvm.internal.b.a(r1)     // Catch:{ all -> 0x00c0 }
            boolean r1 = r2.booleanValue()     // Catch:{ all -> 0x00c0 }
            r11.V0()
            r11.l1()
            goto L_0x00dd
        L_0x00c0:
            r0 = move-exception
            goto L_0x00cf
        L_0x00c2:
            r0 = move-exception
            r17 = r3
            goto L_0x00cf
        L_0x00c6:
            r0 = move-exception
            r27 = r1
            r17 = r3
            r28 = r13
            r29 = r14
        L_0x00cf:
            r11.V0()
            r11.l1()
            throw r0
        L_0x00d6:
            r27 = r1
            r17 = r3
            r28 = r13
            r1 = 0
        L_0x00dd:
            if (r1 != 0) goto L_0x0129
            boolean r2 = r12.y()
            if (r2 != 0) goto L_0x0126
            r5.L$0 = r12
            r5.J$0 = r7
            r2 = r17
            r5.J$1 = r2
            r5.L$1 = r10
            r4 = 1
            r5.label = r4
            java.lang.Object r4 = r12.L0(r4, r5)
            if (r4 != r0) goto L_0x00fa
            return r0
        L_0x00fa:
            r19 = r0
            r0 = r27
            r20 = r4
            r4 = r19
            r21 = r5
            r5 = r1
            r1 = r21
            r22 = r2
            r3 = r20
            r2 = r12
            r12 = r7
            r7 = r10
            r10 = r22
        L_0x0110:
            java.lang.Boolean r3 = (java.lang.Boolean) r3
            boolean r3 = r3.booleanValue()
            r5 = r1
            if (r3 != 0) goto L_0x011f
            r3 = r10
            r1 = r0
            r10 = r7
            r7 = r12
            r12 = r2
            goto L_0x0139
        L_0x011f:
            r1 = r0
            r0 = r4
            r3 = r10
            r10 = r7
            r7 = r12
            r12 = r2
            goto L_0x012e
        L_0x0126:
            r2 = r17
            goto L_0x0136
        L_0x0129:
            r2 = r17
            r1 = r27
            r3 = r2
        L_0x012e:
            r2 = r24
            r6 = 0
            goto L_0x0060
        L_0x0133:
            r27 = r1
            r2 = r3
        L_0x0136:
            r1 = r27
            r3 = r2
        L_0x0139:
            long r13 = r10.element
            java.lang.Long r0 = kotlin.coroutines.jvm.internal.b.d(r13)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.h0(long, long, kotlin.coroutines.d):java.lang.Object");
    }

    static /* synthetic */ Object z1(a aVar, io.ktor.utils.io.core.q packet, kotlin.coroutines.d $completion) {
        a it;
        a it2;
        c it3 = aVar.joining;
        if (it3 == null || (it2 = aVar.U0(aVar, it3)) == null) {
            do {
                try {
                    if (!(!packet.w0()) || aVar.m1(packet) == 0) {
                    }
                    break;
                } catch (Throwable t2) {
                    packet.release();
                    throw t2;
                }
            } while (aVar.m1(packet) == 0);
            if (packet.P0() <= 0) {
                return kotlin.x.a;
            }
            c it4 = aVar.joining;
            if (it4 == null || (it = aVar.U0(aVar, it4)) == null) {
                Object A1 = aVar.A1(packet, $completion);
                return A1 == kotlin.coroutines.intrinsics.c.d() ? A1 : kotlin.x.a;
            }
            Object x2 = it.x(packet, $completion);
            return x2 == kotlin.coroutines.intrinsics.c.d() ? x2 : kotlin.x.a;
        }
        Object x3 = it2.x(packet, $completion);
        return x3 == kotlin.coroutines.intrinsics.c.d() ? x3 : kotlin.x.a;
    }

    /* Debug info: failed to restart local var, previous not found, register: 9 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: io.ktor.utils.io.core.q} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: io.ktor.utils.io.a} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v7, resolved type: io.ktor.utils.io.core.q} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0063, code lost:
        if ((!r10.w0()) == false) goto L_0x009f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0065, code lost:
        r0.L$0 = r6;
        r0.L$1 = r10;
        r0.label = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006f, code lost:
        if (r6.B1(1, r0) != r2) goto L_0x0072;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0071, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0072, code lost:
        r3 = r6.joining;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0074, code lost:
        if (r3 == null) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0076, code lost:
        r4 = r3;
        r5 = r6.U0(r6, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x007c, code lost:
        if (r5 == null) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x007e, code lost:
        r0.L$0 = r6;
        r0.L$1 = r10;
        r0.L$2 = r4;
        r0.L$3 = r5;
        r0.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x008e, code lost:
        if (r5.x(r10, r0) != r2) goto L_0x0091;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0090, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0093, code lost:
        r7 = kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0095, code lost:
        r10.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0098, code lost:
        return r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        r6.m1(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x009f, code lost:
        r10.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a5, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object A1(@org.jetbrains.annotations.NotNull io.ktor.utils.io.core.q r10, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof io.ktor.utils.io.a.y
            if (r0 == 0) goto L_0x0013
            r0 = r11
            io.ktor.utils.io.a$y r0 = (io.ktor.utils.io.a.y) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.a$y r0 = new io.ktor.utils.io.a$y
            r0.<init>(r9, r11)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0056;
                case 1: goto L_0x0046;
                case 2: goto L_0x002b;
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
            r5 = r4
            java.lang.Object r6 = r0.L$3
            r5 = r6
            io.ktor.utils.io.a r5 = (io.ktor.utils.io.a) r5
            java.lang.Object r6 = r0.L$2
            r4 = r6
            io.ktor.utils.io.a$c r4 = (io.ktor.utils.io.a.c) r4
            java.lang.Object r6 = r0.L$1
            r10 = r6
            io.ktor.utils.io.core.q r10 = (io.ktor.utils.io.core.q) r10
            java.lang.Object r6 = r0.L$0
            io.ktor.utils.io.a r6 = (io.ktor.utils.io.a) r6
            kotlin.p.b(r1)     // Catch:{ all -> 0x0054 }
            goto L_0x0093
        L_0x0046:
            java.lang.Object r3 = r0.L$1
            r10 = r3
            io.ktor.utils.io.core.q r10 = (io.ktor.utils.io.core.q) r10
            java.lang.Object r3 = r0.L$0
            r6 = r3
            io.ktor.utils.io.a r6 = (io.ktor.utils.io.a) r6
            kotlin.p.b(r1)     // Catch:{ all -> 0x0054 }
            goto L_0x0072
        L_0x0054:
            r2 = move-exception
            goto L_0x00a6
        L_0x0056:
            kotlin.p.b(r1)
            r6 = r9
        L_0x005a:
            r3 = r10
            r4 = 0
            boolean r5 = r3.w0()     // Catch:{ all -> 0x0054 }
            r3 = 1
            r4 = r5 ^ 1
            if (r4 == 0) goto L_0x009f
            r0.L$0 = r6     // Catch:{ all -> 0x0054 }
            r0.L$1 = r10     // Catch:{ all -> 0x0054 }
            r0.label = r3     // Catch:{ all -> 0x0054 }
            java.lang.Object r3 = r6.B1(r3, r0)     // Catch:{ all -> 0x0054 }
            if (r3 != r2) goto L_0x0072
            return r2
        L_0x0072:
            io.ktor.utils.io.a$c r3 = r6.joining     // Catch:{ all -> 0x0054 }
            if (r3 == 0) goto L_0x009a
            r4 = r3
            r3 = 0
            io.ktor.utils.io.a r5 = r6.U0(r6, r4)     // Catch:{ all -> 0x0054 }
            if (r5 == 0) goto L_0x0099
            r7 = 0
            r0.L$0 = r6     // Catch:{ all -> 0x0054 }
            r0.L$1 = r10     // Catch:{ all -> 0x0054 }
            r0.L$2 = r4     // Catch:{ all -> 0x0054 }
            r0.L$3 = r5     // Catch:{ all -> 0x0054 }
            r8 = 2
            r0.label = r8     // Catch:{ all -> 0x0054 }
            java.lang.Object r8 = r5.x(r10, r0)     // Catch:{ all -> 0x0054 }
            if (r8 != r2) goto L_0x0091
            return r2
        L_0x0091:
            r2 = r3
            r3 = r7
        L_0x0093:
            kotlin.x r7 = kotlin.x.a     // Catch:{ all -> 0x0054 }
            r10.release()
            return r7
        L_0x0099:
        L_0x009a:
            r6.m1(r10)     // Catch:{ all -> 0x0054 }
            goto L_0x005a
        L_0x009f:
            r10.release()
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x00a6:
            r10.release()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.A1(io.ktor.utils.io.core.q, kotlin.coroutines.d):java.lang.Object");
    }

    private final int m1(io.ktor.utils.io.core.q packet) {
        a aVar;
        int copied = 0;
        c it$iv = this.joining;
        if (it$iv == null || (aVar = U0(this, it$iv)) == null) {
            aVar = this;
        }
        a current$iv = aVar;
        ByteBuffer g1 = current$iv.g1();
        if (g1 != null) {
            ByteBuffer buffer$iv = g1;
            io.ktor.utils.io.internal.k capacity$iv = current$iv.state.b;
            long before$iv = current$iv.n0();
            try {
                C0279a it$iv2 = current$iv.closed;
                if (it$iv2 == null) {
                    a $this$writing = current$iv;
                    io.ktor.utils.io.internal.k state2 = capacity$iv;
                    ByteBuffer dst = buffer$iv;
                    int size = state2.n((int) Math.min(packet.P0(), (long) dst.remaining()));
                    if (size > 0) {
                        dst.limit(dst.position() + size);
                        try {
                            io.ktor.utils.io.core.k.b(packet, dst);
                            $this$writing.b0(dst, state2, size);
                        } catch (Throwable th) {
                            th = th;
                        }
                    } else {
                        io.ktor.utils.io.core.q qVar = packet;
                    }
                    copied = size;
                    if (capacity$iv.h() || current$iv.A()) {
                        current$iv.flush();
                    }
                    if (current$iv != this) {
                        c1(n0() + (current$iv.n0() - before$iv));
                    }
                    current$iv.W0();
                    current$iv.l1();
                } else {
                    io.ktor.utils.io.core.q qVar2 = packet;
                    throw it$iv2.c();
                }
            } catch (Throwable th2) {
                th = th2;
                io.ktor.utils.io.core.q qVar3 = packet;
                if (capacity$iv.h() || current$iv.A()) {
                    current$iv.flush();
                }
                if (current$iv != this) {
                    c1(n0() + (current$iv.n0() - before$iv));
                }
                current$iv.W0();
                current$iv.l1();
                throw th;
            }
        } else {
            io.ktor.utils.io.core.q qVar4 = packet;
        }
        return copied;
    }

    public <R> R u(@NotNull kotlin.jvm.functions.l<? super s, ? extends R> visitor) {
        kotlin.jvm.internal.k.f(visitor, "visitor");
        if (this.state == f.C0290f.c) {
            return visitor.invoke(d.b);
        }
        Object result = null;
        ByteBuffer buffer$iv = f1();
        boolean rc = false;
        if (buffer$iv != null) {
            io.ktor.utils.io.internal.k capacity$iv = this.state.b;
            try {
                if (capacity$iv.availableForRead != 0) {
                    ByteBuffer byteBuffer = buffer$iv;
                    io.ktor.utils.io.internal.k kVar = capacity$iv;
                    result = visitor.invoke(this);
                    rc = true;
                }
            } finally {
                V0();
                l1();
            }
        }
        if (!rc) {
            return visitor.invoke(d.b);
        }
        if (result == null) {
            kotlin.jvm.internal.k.n();
        }
        return result;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v5, resolved type: java.nio.ByteBuffer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v14, resolved type: io.ktor.utils.io.a} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v12, resolved type: kotlin.jvm.internal.z} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: kotlin.jvm.functions.p} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: io.ktor.utils.io.a} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v11, resolved type: io.ktor.utils.io.a} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        r15.element = r8;
        r8 = kotlin.coroutines.jvm.internal.b.a(true).booleanValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0127, code lost:
        N(r10);
        r10.l1();
        r7 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x012f, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0130, code lost:
        r8 = r9;
        r9 = r10;
        r12 = r13;
        r13 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0141, code lost:
        if (r8 != false) goto L_0x01b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0145, code lost:
        if (r1.closed != null) goto L_0x019f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x014b, code lost:
        if (r1.state != io.ktor.utils.io.internal.f.C0290f.c) goto L_0x014e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        r4.L$0 = r1;
        r4.L$1 = r2;
        r4.L$2 = r7;
        r4.L$3 = r7;
        r4.label = 4;
        r8 = r2.invoke(r1, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x015d, code lost:
        if (r8 != r0) goto L_0x0160;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x015f, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0160, code lost:
        r9 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
        r7.element = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0163, code lost:
        r0 = r1.state;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0169, code lost:
        if (r0.a() != false) goto L_0x017d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x016d, code lost:
        if (r0 == io.ktor.utils.io.internal.f.C0290f.c) goto L_0x017d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0171, code lost:
        if ((r0 instanceof io.ktor.utils.io.internal.f.d) != false) goto L_0x0177;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0175, code lost:
        if ((r0 instanceof io.ktor.utils.io.internal.f.e) == false) goto L_0x017a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0177, code lost:
        r1.V0();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x017a, code lost:
        r1.l1();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x017d, code lost:
        r7 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x017f, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0180, code lost:
        r8 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0182, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0183, code lost:
        r8 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x019f, code lost:
        r8 = io.ktor.utils.io.a.d.b;
        r4.L$0 = r1;
        r4.L$1 = r2;
        r4.L$2 = r7;
        r4.label = 3;
        r8 = r2.invoke(r8, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x01ae, code lost:
        if (r8 != r0) goto L_0x01b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x01b0, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x01b1, code lost:
        r0 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x01b7, code lost:
        return r7.element;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:?, code lost:
        return r8;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00a8  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object r0(io.ktor.utils.io.a r16, kotlin.jvm.functions.p r17, kotlin.coroutines.d r18) {
        /*
            r1 = r16
            r2 = r17
            r3 = r18
            boolean r0 = r3 instanceof io.ktor.utils.io.a.j
            if (r0 == 0) goto L_0x0019
            r0 = r3
            io.ktor.utils.io.a$j r0 = (io.ktor.utils.io.a.j) r0
            int r4 = r0.label
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            r6 = r4 & r5
            if (r6 == 0) goto L_0x0019
            int r4 = r4 - r5
            r0.label = r4
            goto L_0x001e
        L_0x0019:
            io.ktor.utils.io.a$j r0 = new io.ktor.utils.io.a$j
            r0.<init>(r1, r3)
        L_0x001e:
            r4 = r0
            java.lang.Object r5 = r4.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r6 = r4.label
            r8 = 0
            r9 = 0
            switch(r6) {
                case 0: goto L_0x00b6;
                case 1: goto L_0x00a8;
                case 2: goto L_0x0069;
                case 3: goto L_0x0052;
                case 4: goto L_0x0034;
                default: goto L_0x002c;
            }
        L_0x002c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r4)
            throw r0
        L_0x0034:
            r0 = r9
            r6 = r8
            java.lang.Object r7 = r4.L$3
            kotlin.jvm.internal.z r7 = (kotlin.jvm.internal.z) r7
            java.lang.Object r8 = r4.L$2
            kotlin.jvm.internal.z r8 = (kotlin.jvm.internal.z) r8
            java.lang.Object r0 = r4.L$1
            r2 = r0
            kotlin.jvm.functions.p r2 = (kotlin.jvm.functions.p) r2
            java.lang.Object r0 = r4.L$0
            r1 = r0
            io.ktor.utils.io.a r1 = (io.ktor.utils.io.a) r1
            kotlin.p.b(r5)     // Catch:{ all -> 0x004f }
            r9 = r8
            r8 = r5
            goto L_0x0161
        L_0x004f:
            r0 = move-exception
            goto L_0x0184
        L_0x0052:
            r0 = r9
            r6 = r8
            java.lang.Object r7 = r4.L$2
            r0 = r7
            kotlin.jvm.internal.z r0 = (kotlin.jvm.internal.z) r0
            java.lang.Object r7 = r4.L$1
            r2 = r7
            kotlin.jvm.functions.p r2 = (kotlin.jvm.functions.p) r2
            java.lang.Object r7 = r4.L$0
            r1 = r7
            io.ktor.utils.io.a r1 = (io.ktor.utils.io.a) r1
            kotlin.p.b(r5)
            r8 = r5
            goto L_0x01b2
        L_0x0069:
            r6 = r9
            r10 = r8
            r11 = r9
            r12 = r9
            r13 = r9
            r14 = r9
            java.lang.Object r15 = r4.L$8
            kotlin.jvm.internal.z r15 = (kotlin.jvm.internal.z) r15
            java.lang.Object r7 = r4.L$7
            java.nio.ByteBuffer r7 = (java.nio.ByteBuffer) r7
            java.lang.Object r12 = r4.L$6
            r11 = r12
            io.ktor.utils.io.internal.k r11 = (io.ktor.utils.io.internal.k) r11
            java.lang.Object r12 = r4.L$5
            io.ktor.utils.io.internal.k r12 = (io.ktor.utils.io.internal.k) r12
            java.lang.Object r14 = r4.L$4
            r13 = r14
            java.nio.ByteBuffer r13 = (java.nio.ByteBuffer) r13
            java.lang.Object r14 = r4.L$3
            r9 = r14
            io.ktor.utils.io.a r9 = (io.ktor.utils.io.a) r9
            java.lang.Object r14 = r4.L$2
            r6 = r14
            kotlin.jvm.internal.z r6 = (kotlin.jvm.internal.z) r6
            java.lang.Object r14 = r4.L$1
            r2 = r14
            kotlin.jvm.functions.p r2 = (kotlin.jvm.functions.p) r2
            java.lang.Object r14 = r4.L$0
            r1 = r14
            io.ktor.utils.io.a r1 = (io.ktor.utils.io.a) r1
            kotlin.p.b(r5)     // Catch:{ all -> 0x00a5 }
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r5
            goto L_0x011c
        L_0x00a5:
            r0 = move-exception
            goto L_0x0138
        L_0x00a8:
            java.lang.Object r0 = r4.L$1
            kotlin.jvm.functions.p r0 = (kotlin.jvm.functions.p) r0
            java.lang.Object r2 = r4.L$0
            r1 = r2
            io.ktor.utils.io.a r1 = (io.ktor.utils.io.a) r1
            kotlin.p.b(r5)
            r6 = r5
            goto L_0x00d0
        L_0x00b6:
            kotlin.p.b(r5)
            io.ktor.utils.io.internal.f r6 = r1.state
            io.ktor.utils.io.internal.f$f r7 = io.ktor.utils.io.internal.f.C0290f.c
            if (r6 != r7) goto L_0x00d1
            io.ktor.utils.io.a$d r6 = io.ktor.utils.io.a.d.b
            r4.L$0 = r1
            r4.L$1 = r2
            r7 = 1
            r4.label = r7
            java.lang.Object r6 = r2.invoke(r6, r4)
            if (r6 != r0) goto L_0x00cf
            return r0
        L_0x00cf:
            r0 = r2
        L_0x00d0:
            return r6
        L_0x00d1:
            kotlin.jvm.internal.z r6 = new kotlin.jvm.internal.z
            r6.<init>()
            r6.element = r9
            r15 = r6
            r9 = r16
            r6 = 0
            java.nio.ByteBuffer r7 = r9.f1()
            if (r7 == 0) goto L_0x013f
            r13 = r7
            io.ktor.utils.io.internal.f r7 = r9.state
            io.ktor.utils.io.internal.k r12 = r7.b
            int r7 = r12.availableForRead     // Catch:{ all -> 0x0135 }
            if (r7 != 0) goto L_0x00f6
            r9.V0()
            r9.l1()
            r7 = r15
            goto L_0x0140
        L_0x00f6:
            r7 = r13
            r11 = r12
            r10 = 0
            r4.L$0 = r1     // Catch:{ all -> 0x0135 }
            r4.L$1 = r2     // Catch:{ all -> 0x0135 }
            r4.L$2 = r15     // Catch:{ all -> 0x0135 }
            r4.L$3 = r9     // Catch:{ all -> 0x0135 }
            r4.L$4 = r13     // Catch:{ all -> 0x0135 }
            r4.L$5 = r12     // Catch:{ all -> 0x0135 }
            r4.L$6 = r11     // Catch:{ all -> 0x0135 }
            r4.L$7 = r7     // Catch:{ all -> 0x0135 }
            r4.L$8 = r15     // Catch:{ all -> 0x0135 }
            r8 = 2
            r4.label = r8     // Catch:{ all -> 0x0135 }
            java.lang.Object r8 = r2.invoke(r1, r4)     // Catch:{ all -> 0x0135 }
            if (r8 != r0) goto L_0x0115
            return r0
        L_0x0115:
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r6
            r6 = r15
        L_0x011c:
            r15.element = r8     // Catch:{ all -> 0x012f }
            r7 = 1
            java.lang.Boolean r7 = kotlin.coroutines.jvm.internal.b.a(r7)     // Catch:{ all -> 0x012f }
            boolean r8 = r7.booleanValue()     // Catch:{ all -> 0x012f }
            r10.V0()
            r10.l1()
            r7 = r6
            goto L_0x0140
        L_0x012f:
            r0 = move-exception
            r8 = r9
            r9 = r10
            r12 = r13
            r13 = r14
            goto L_0x0138
        L_0x0135:
            r0 = move-exception
            r8 = r6
            r6 = r15
        L_0x0138:
            r9.V0()
            r9.l1()
            throw r0
        L_0x013f:
            r7 = r15
        L_0x0140:
            r6 = r8
            if (r6 != 0) goto L_0x01b3
            io.ktor.utils.io.a$a r8 = r1.closed
            if (r8 != 0) goto L_0x019f
            io.ktor.utils.io.internal.f r8 = r1.state
            io.ktor.utils.io.internal.f$f r9 = io.ktor.utils.io.internal.f.C0290f.c
            if (r8 != r9) goto L_0x014e
            goto L_0x019f
        L_0x014e:
            r4.L$0 = r1     // Catch:{ all -> 0x0182 }
            r4.L$1 = r2     // Catch:{ all -> 0x0182 }
            r4.L$2 = r7     // Catch:{ all -> 0x0182 }
            r4.L$3 = r7     // Catch:{ all -> 0x0182 }
            r8 = 4
            r4.label = r8     // Catch:{ all -> 0x0182 }
            java.lang.Object r8 = r2.invoke(r1, r4)     // Catch:{ all -> 0x0182 }
            if (r8 != r0) goto L_0x0160
            return r0
        L_0x0160:
            r9 = r7
        L_0x0161:
            r7.element = r8     // Catch:{ all -> 0x017f }
            io.ktor.utils.io.internal.f r0 = r1.state
            boolean r7 = r0.a()
            if (r7 != 0) goto L_0x017d
            io.ktor.utils.io.internal.f$f r7 = io.ktor.utils.io.internal.f.C0290f.c
            if (r0 == r7) goto L_0x017d
            boolean r7 = r0 instanceof io.ktor.utils.io.internal.f.d
            if (r7 != 0) goto L_0x0177
            boolean r7 = r0 instanceof io.ktor.utils.io.internal.f.e
            if (r7 == 0) goto L_0x017a
        L_0x0177:
            r1.V0()
        L_0x017a:
            r1.l1()
        L_0x017d:
            r7 = r9
            goto L_0x01b3
        L_0x017f:
            r0 = move-exception
            r8 = r9
            goto L_0x0184
        L_0x0182:
            r0 = move-exception
            r8 = r7
        L_0x0184:
            io.ktor.utils.io.internal.f r7 = r1.state
            boolean r9 = r7.a()
            if (r9 != 0) goto L_0x019e
            io.ktor.utils.io.internal.f$f r9 = io.ktor.utils.io.internal.f.C0290f.c
            if (r7 == r9) goto L_0x019e
            boolean r9 = r7 instanceof io.ktor.utils.io.internal.f.d
            if (r9 != 0) goto L_0x0198
            boolean r9 = r7 instanceof io.ktor.utils.io.internal.f.e
            if (r9 == 0) goto L_0x019b
        L_0x0198:
            r1.V0()
        L_0x019b:
            r1.l1()
        L_0x019e:
            throw r0
        L_0x019f:
            io.ktor.utils.io.a$d r8 = io.ktor.utils.io.a.d.b
            r4.L$0 = r1
            r4.L$1 = r2
            r4.L$2 = r7
            r9 = 3
            r4.label = r9
            java.lang.Object r8 = r2.invoke(r8, r4)
            if (r8 != r0) goto L_0x01b1
            return r0
        L_0x01b1:
            r0 = r7
        L_0x01b2:
            return r8
        L_0x01b3:
            T r0 = r7.element
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.r0(io.ktor.utils.io.a, kotlin.jvm.functions.p, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: io.ktor.utils.io.internal.o} */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005b, code lost:
        r2.e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0061, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object E1(io.ktor.utils.io.a r6, kotlin.jvm.functions.p r7, kotlin.coroutines.d r8) {
        /*
            boolean r0 = r8 instanceof io.ktor.utils.io.a.b0
            if (r0 == 0) goto L_0x0013
            r0 = r8
            io.ktor.utils.io.a$b0 r0 = (io.ktor.utils.io.a.b0) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.a$b0 r0 = new io.ktor.utils.io.a$b0
            r0.<init>(r6, r8)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0041;
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
            java.lang.Object r3 = r0.L$2
            r2 = r3
            io.ktor.utils.io.internal.o r2 = (io.ktor.utils.io.internal.o) r2
            java.lang.Object r3 = r0.L$1
            r7 = r3
            kotlin.jvm.functions.p r7 = (kotlin.jvm.functions.p) r7
            java.lang.Object r3 = r0.L$0
            r6 = r3
            io.ktor.utils.io.a r6 = (io.ktor.utils.io.a) r6
            kotlin.p.b(r1)     // Catch:{ all -> 0x003f }
            goto L_0x005b
        L_0x003f:
            r3 = move-exception
            goto L_0x0066
        L_0x0041:
            kotlin.p.b(r1)
            io.ktor.utils.io.internal.o r3 = r6.l
            r3.d()
            r0.L$0 = r6     // Catch:{ all -> 0x0062 }
            r0.L$1 = r7     // Catch:{ all -> 0x0062 }
            r0.L$2 = r3     // Catch:{ all -> 0x0062 }
            r4 = 1
            r0.label = r4     // Catch:{ all -> 0x0062 }
            java.lang.Object r4 = r7.invoke(r3, r0)     // Catch:{ all -> 0x0062 }
            if (r4 != r2) goto L_0x005a
            return r2
        L_0x005a:
            r2 = r3
        L_0x005b:
            r2.e()
            kotlin.x r3 = kotlin.x.a
            return r3
        L_0x0062:
            r2 = move-exception
            r5 = r3
            r3 = r2
            r2 = r5
        L_0x0066:
            r2.e()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.E1(io.ktor.utils.io.a, kotlin.jvm.functions.p, kotlin.coroutines.d):java.lang.Object");
    }

    public void w(int n2) {
        if (n2 >= 0) {
            io.ktor.utils.io.internal.f s2 = this.state;
            if (!s2.b.l(n2)) {
                throw new IllegalStateException("Unable to consume " + n2 + " bytes: not enough available bytes");
            } else if (n2 > 0) {
                a0(s2.b(), s2.b, n2);
            }
        } else {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    @Nullable
    public final Object k(int n2, @NotNull kotlin.coroutines.d<? super Boolean> $completion) {
        boolean z2 = false;
        if (n2 >= 0) {
            if (n2 <= 4088) {
                z2 = true;
            }
            if (!z2) {
                throw new IllegalArgumentException(("atLeast parameter shouldn't be larger than max buffer size of 4088: " + n2).toString());
            } else if (this.state.b.availableForRead >= n2) {
                if (this.state.a() || (this.state instanceof f.g)) {
                    f1();
                }
                return kotlin.coroutines.jvm.internal.b.a(true);
            } else if (this.state.a() || (this.state instanceof f.g)) {
                return Y(n2, $completion);
            } else {
                if (n2 == 1) {
                    return M0(1, $completion);
                }
                return L0(n2, $completion);
            }
        } else {
            throw new IllegalArgumentException(("atLeast parameter shouldn't be negative: " + n2).toString());
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 5 */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object Y(int r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Boolean> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof io.ktor.utils.io.a.f
            if (r0 == 0) goto L_0x0013
            r0 = r7
            io.ktor.utils.io.a$f r0 = (io.ktor.utils.io.a.f) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.a$f r0 = new io.ktor.utils.io.a$f
            r0.<init>(r5, r7)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0036;
                case 1: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            int r6 = r0.I$0
            java.lang.Object r2 = r0.L$0
            io.ktor.utils.io.a r2 = (io.ktor.utils.io.a) r2
            kotlin.p.b(r1)
            r3 = r1
            goto L_0x0048
        L_0x0036:
            kotlin.p.b(r1)
            r0.L$0 = r5
            r0.I$0 = r6
            r3 = 1
            r0.label = r3
            java.lang.Object r3 = r5.L0(r6, r0)
            if (r3 != r2) goto L_0x0047
            return r2
        L_0x0047:
            r2 = r5
        L_0x0048:
            java.lang.Boolean r3 = (java.lang.Boolean) r3
            boolean r3 = r3.booleanValue()
            if (r3 == 0) goto L_0x005b
            io.ktor.utils.io.internal.f r4 = r2.state
            boolean r4 = r4.a()
            if (r4 == 0) goto L_0x005b
            r2.f1()
        L_0x005b:
            java.lang.Boolean r4 = kotlin.coroutines.jvm.internal.b.a(r3)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.Y(int, kotlin.coroutines.d):java.lang.Object");
    }

    @Nullable
    public ByteBuffer a(int skip, int atLeast) {
        io.ktor.utils.io.internal.f s2 = this.state;
        int available = s2.b.availableForRead;
        int rp = this.g;
        if (available < atLeast + skip) {
            return null;
        }
        if (!s2.a() && ((s2 instanceof f.d) || (s2 instanceof f.e))) {
            ByteBuffer buffer = s2.b();
            t0(buffer, m0(), d0(buffer, rp + skip), available - skip);
            if (buffer.remaining() >= atLeast) {
                return buffer;
            }
            return null;
        } else if (f1() != null) {
            return a(skip, atLeast);
        } else {
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final /* synthetic */ Object Q0(@NotNull Appendable out, int limit, @NotNull kotlin.coroutines.d<? super Boolean> $completion) {
        if (this.state == f.C0290f.c) {
            Throwable cause = k0();
            if (cause == null) {
                return kotlin.coroutines.jvm.internal.b.a(false);
            }
            throw cause;
        }
        kotlin.jvm.internal.x xVar = new kotlin.jvm.internal.x();
        xVar.element = 0;
        kotlin.jvm.internal.x consumed = xVar;
        char[] ca = new char[8192];
        CharBuffer cb = CharBuffer.wrap(ca);
        if (cb == null) {
            kotlin.jvm.internal.k.n();
        }
        kotlin.jvm.internal.w wVar = new kotlin.jvm.internal.w();
        wVar.element = false;
        kotlin.jvm.internal.w eol = wVar;
        u(new s(this, eol, out, ca, cb, consumed, limit));
        if (eol.element) {
            return kotlin.coroutines.jvm.internal.b.a(true);
        }
        if (consumed.element == 0 && y()) {
            return kotlin.coroutines.jvm.internal.b.a(false);
        }
        int i2 = consumed.element;
        kotlin.jvm.internal.w wVar2 = eol;
        return R0(out, limit - i2, ca, cb, i2, $completion);
    }

    /* Debug info: failed to restart local var, previous not found, register: 21 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: kotlin.jvm.internal.w} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object R0(@org.jetbrains.annotations.NotNull java.lang.Appendable r22, int r23, @org.jetbrains.annotations.NotNull char[] r24, @org.jetbrains.annotations.NotNull java.nio.CharBuffer r25, int r26, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Boolean> r27) {
        /*
            r21 = this;
            r10 = r21
            r11 = r27
            boolean r0 = r11 instanceof io.ktor.utils.io.a.t
            if (r0 == 0) goto L_0x0017
            r0 = r11
            io.ktor.utils.io.a$t r0 = (io.ktor.utils.io.a.t) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0017
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x001c
        L_0x0017:
            io.ktor.utils.io.a$t r0 = new io.ktor.utils.io.a$t
            r0.<init>(r10, r11)
        L_0x001c:
            r12 = r0
            java.lang.Object r13 = r12.result
            java.lang.Object r14 = kotlin.coroutines.intrinsics.c.d()
            int r0 = r12.label
            switch(r0) {
                case 0: goto L_0x006a;
                case 1: goto L_0x003a;
                default: goto L_0x0028;
            }
        L_0x0028:
            r0 = r22
            r1 = r23
            r2 = r24
            r3 = r25
            r4 = r26
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L_0x003a:
            r0 = 0
            r1 = r0
            java.lang.Object r2 = r12.L$5
            r1 = r2
            kotlin.jvm.internal.w r1 = (kotlin.jvm.internal.w) r1
            java.lang.Object r2 = r12.L$4
            r0 = r2
            kotlin.jvm.internal.x r0 = (kotlin.jvm.internal.x) r0
            int r2 = r12.I$1
            java.lang.Object r3 = r12.L$3
            java.nio.CharBuffer r3 = (java.nio.CharBuffer) r3
            java.lang.Object r4 = r12.L$2
            char[] r4 = (char[]) r4
            int r5 = r12.I$0
            java.lang.Object r6 = r12.L$1
            java.lang.Appendable r6 = (java.lang.Appendable) r6
            java.lang.Object r7 = r12.L$0
            io.ktor.utils.io.a r7 = (io.ktor.utils.io.a) r7
            kotlin.p.b(r13)
            r15 = r0
            r0 = r6
            r19 = r5
            r5 = r1
            r1 = r19
            r20 = r4
            r4 = r2
            r2 = r20
            goto L_0x00c3
        L_0x006a:
            kotlin.p.b(r13)
            kotlin.jvm.internal.x r0 = new kotlin.jvm.internal.x
            r0.<init>()
            r1 = 0
            r0.element = r1
            r15 = r0
            kotlin.jvm.internal.w r0 = new kotlin.jvm.internal.w
            r0.<init>()
            r9 = 1
            r0.element = r9
            r8 = r0
            io.ktor.utils.io.a$u r7 = new io.ktor.utils.io.a$u
            r16 = 0
            r0 = r7
            r1 = r21
            r2 = r22
            r3 = r24
            r4 = r25
            r5 = r15
            r6 = r23
            r17 = r7
            r7 = r26
            r18 = r8
            r11 = r9
            r9 = r16
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            r12.L$0 = r10
            r0 = r22
            r12.L$1 = r0
            r1 = r23
            r12.I$0 = r1
            r2 = r24
            r12.L$2 = r2
            r3 = r25
            r12.L$3 = r3
            r4 = r26
            r12.I$1 = r4
            r12.L$4 = r15
            r5 = r18
            r12.L$5 = r5
            r12.label = r11
            r6 = r17
            java.lang.Object r6 = r10.l(r6, r12)
            if (r6 != r14) goto L_0x00c2
            return r14
        L_0x00c2:
            r7 = r10
        L_0x00c3:
            boolean r6 = r5.element
            java.lang.Boolean r6 = kotlin.coroutines.jvm.internal.b.a(r6)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.R0(java.lang.Appendable, int, char[], java.nio.CharBuffer, int, kotlin.coroutines.d):java.lang.Object");
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.ByteBufferChannel$readUTF8LineToUtf8Suspend$2", f = "ByteBufferChannel.kt", l = {2118}, m = "invokeSuspend")
    /* compiled from: ByteBufferChannel.kt */
    public static final class u extends kotlin.coroutines.jvm.internal.l implements kotlin.jvm.functions.p<t, kotlin.coroutines.d<? super kotlin.x>, Object> {
        final /* synthetic */ char[] $ca;
        final /* synthetic */ CharBuffer $cb;
        final /* synthetic */ int $consumed0;
        final /* synthetic */ kotlin.jvm.internal.x $consumed1;
        final /* synthetic */ int $limit;
        final /* synthetic */ Appendable $out;
        final /* synthetic */ kotlin.jvm.internal.w $result;
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        int label;
        private t p$;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        u(a aVar, Appendable appendable, char[] cArr, CharBuffer charBuffer, kotlin.jvm.internal.x xVar, int i, int i2, kotlin.jvm.internal.w wVar, kotlin.coroutines.d dVar) {
            super(2, dVar);
            this.this$0 = aVar;
            this.$out = appendable;
            this.$ca = cArr;
            this.$cb = charBuffer;
            this.$consumed1 = xVar;
            this.$limit = i;
            this.$consumed0 = i2;
            this.$result = wVar;
        }

        @NotNull
        public final kotlin.coroutines.d<kotlin.x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            kotlin.jvm.internal.k.f(dVar, "completion");
            u uVar = new u(this.this$0, this.$out, this.$ca, this.$cb, this.$consumed1, this.$limit, this.$consumed0, this.$result, dVar);
            t tVar = (t) obj;
            uVar.p$ = (t) obj;
            return uVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((u) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(kotlin.x.a);
        }

        /* Debug info: failed to restart local var, previous not found, register: 22 */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v7, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v7, resolved type: java.nio.CharBuffer} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v8, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v6, resolved type: char[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v9, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: java.lang.Appendable} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v10, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v12, resolved type: io.ktor.utils.io.s} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v11, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v17, resolved type: io.ktor.utils.io.a} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v12, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v11, resolved type: io.ktor.utils.io.t} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Removed duplicated region for block: B:9:0x008d  */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r23) {
            /*
                r22 = this;
                r0 = r22
                java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
                int r2 = r0.label
                r3 = 1
                r4 = 0
                switch(r2) {
                    case 0: goto L_0x0052;
                    case 1: goto L_0x0015;
                    default: goto L_0x000d;
                }
            L_0x000d:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0015:
                r2 = 0
                r5 = r2
                r6 = r2
                r7 = r4
                r8 = r2
                r9 = r4
                r10 = r4
                r11 = r4
                r12 = r2
                r13 = r2
                int r10 = r0.I$1
                int r11 = r0.I$0
                java.lang.Object r14 = r0.L$5
                r8 = r14
                java.nio.CharBuffer r8 = (java.nio.CharBuffer) r8
                java.lang.Object r14 = r0.L$4
                r13 = r14
                char[] r13 = (char[]) r13
                java.lang.Object r14 = r0.L$3
                r5 = r14
                java.lang.Appendable r5 = (java.lang.Appendable) r5
                java.lang.Object r14 = r0.L$2
                r6 = r14
                io.ktor.utils.io.s r6 = (io.ktor.utils.io.s) r6
                java.lang.Object r14 = r0.L$1
                r2 = r14
                io.ktor.utils.io.a r2 = (io.ktor.utils.io.a) r2
                java.lang.Object r14 = r0.L$0
                r12 = r14
                io.ktor.utils.io.t r12 = (io.ktor.utils.io.t) r12
                kotlin.p.b(r23)
                r3 = r23
                r14 = r10
                r15 = r13
                r10 = r9
                r13 = r12
                r9 = r8
                r12 = r11
                r11 = r0
                r8 = r5
                r5 = r2
                r2 = r1
                r1 = r3
                goto L_0x0098
            L_0x0052:
                kotlin.p.b(r23)
                io.ktor.utils.io.t r2 = r0.p$
                io.ktor.utils.io.a r5 = r0.this$0
                r6 = r2
                java.lang.Appendable r7 = r0.$out
                char[] r8 = r0.$ca
                java.nio.CharBuffer r9 = r0.$cb
                r10 = 0
                r11 = 1
                r12 = r2
                r13 = r8
                r8 = r9
                r9 = r10
                r10 = r0
                r2 = r1
                r1 = r23
            L_0x006a:
                java.lang.Integer r14 = kotlin.coroutines.jvm.internal.b.c(r11)
                int r14 = r14.intValue()
                r15 = 0
                r10.L$0 = r12
                r10.L$1 = r5
                r10.L$2 = r6
                r10.L$3 = r7
                r10.L$4 = r13
                r10.L$5 = r8
                r10.I$0 = r11
                r10.I$1 = r14
                r10.label = r3
                java.lang.Object r3 = r12.k(r14, r10)
                if (r3 != r2) goto L_0x008d
                return r2
            L_0x008d:
                r21 = r8
                r8 = r7
                r7 = r15
                r15 = r13
                r13 = r12
                r12 = r11
                r11 = r10
                r10 = r9
                r9 = r21
            L_0x0098:
                java.lang.Boolean r3 = (java.lang.Boolean) r3
                boolean r3 = r3.booleanValue()
                java.lang.Boolean r3 = kotlin.coroutines.jvm.internal.b.a(r3)
                boolean r3 = r3.booleanValue()
                if (r3 != 0) goto L_0x00af
                r23 = r1
                r14 = r5
                r16 = r6
                goto L_0x0156
            L_0x00af:
                r3 = 1
                java.nio.ByteBuffer r7 = r6.a(r4, r3)
                if (r7 == 0) goto L_0x0151
                r3 = r7
                int r7 = r3.position()
                int r14 = r3.remaining()
                if (r14 >= r12) goto L_0x00c4
                r5.a1(r3, r12)
            L_0x00c4:
                r14 = r3
                r16 = 0
                char[] r4 = r11.$ca
                int r0 = r4.length
                r23 = r1
                int r1 = r11.$limit
                r17 = r2
                kotlin.jvm.internal.x r2 = r11.$consumed1
                int r2 = r2.element
                int r1 = r1 - r2
                int r0 = java.lang.Math.min(r0, r1)
                r1 = 0
                long r18 = io.ktor.utils.io.charsets.c.h(r14, r4, r1, r0)
                java.lang.Long r0 = kotlin.coroutines.jvm.internal.b.d(r18)
                long r0 = r0.longValue()
                int r2 = r3.position()
                int r4 = r2 - r7
                r6.w(r4)
                r4 = 32
                r14 = r5
                long r4 = r0 >> r4
                int r4 = (int) r4
                r18 = 4294967295(0xffffffff, double:2.1219957905E-314)
                r16 = r6
                long r5 = r0 & r18
                int r5 = (int) r5
                r6 = -1
                if (r5 != r6) goto L_0x0104
                r6 = 0
                goto L_0x0114
            L_0x0104:
                if (r5 != 0) goto L_0x010e
                boolean r6 = r3.hasRemaining()
                if (r6 == 0) goto L_0x010e
                r6 = -1
                goto L_0x0114
            L_0x010e:
                r6 = 1
                int r12 = java.lang.Math.max(r6, r5)
                r6 = r12
            L_0x0114:
                java.lang.Integer r12 = kotlin.coroutines.jvm.internal.b.c(r4)
                int r12 = r12.intValue()
                r18 = 0
                r19 = r0
                kotlin.jvm.internal.x r0 = r11.$consumed1
                int r1 = r0.element
                int r1 = r1 + r12
                r0.element = r1
                boolean r0 = r8 instanceof java.lang.StringBuilder
                if (r0 == 0) goto L_0x0134
                r0 = r8
                java.lang.StringBuilder r0 = (java.lang.StringBuilder) r0
                r1 = 0
                r0.append(r15, r1, r4)
                goto L_0x0138
            L_0x0134:
                r1 = 0
                r8.append(r9, r1, r4)
            L_0x0138:
                if (r6 > 0) goto L_0x013d
                r12 = r6
                goto L_0x0156
            L_0x013d:
                r0 = r22
                r1 = r23
                r7 = r8
                r8 = r9
                r9 = r10
                r10 = r11
                r12 = r13
                r5 = r14
                r13 = r15
                r2 = r17
                r3 = 1
                r4 = 0
                r11 = r6
                r6 = r16
                goto L_0x006a
            L_0x0151:
                r23 = r1
                r14 = r5
                r16 = r6
            L_0x0156:
                switch(r12) {
                    case 0: goto L_0x015c;
                    default: goto L_0x0159;
                }
            L_0x0159:
                r0 = 0
                goto L_0x015d
            L_0x015c:
                r0 = 1
            L_0x015d:
                if (r0 != 0) goto L_0x01be
                io.ktor.utils.io.a r1 = r11.this$0
                boolean r1 = r1.f()
                if (r1 == 0) goto L_0x01be
                r1 = 1
                r2 = 0
                java.nio.ByteBuffer r3 = r13.a(r2, r1)
                if (r3 == 0) goto L_0x01af
                byte r2 = r3.get()
                r4 = 13
                byte r4 = (byte) r4
                if (r2 != r4) goto L_0x019e
                r13.w(r1)
                boolean r1 = r3.hasRemaining()
                if (r1 != 0) goto L_0x0183
                goto L_0x01be
            L_0x0183:
                io.ktor.utils.io.charsets.MalformedInputException r1 = new io.ktor.utils.io.charsets.MalformedInputException
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r4 = "Illegal trailing bytes: "
                r2.append(r4)
                int r4 = r3.remaining()
                r2.append(r4)
                java.lang.String r2 = r2.toString()
                r1.<init>(r2)
                throw r1
            L_0x019e:
                int r1 = r3.position()
                r2 = 1
                int r1 = r1 - r2
                r3.position(r1)
                io.ktor.utils.io.charsets.MalformedInputException r1 = new io.ktor.utils.io.charsets.MalformedInputException
                java.lang.String r2 = "Illegal trailing byte"
                r1.<init>(r2)
                throw r1
            L_0x01af:
                kotlin.jvm.internal.x r1 = r11.$consumed1
                int r1 = r1.element
                if (r1 != 0) goto L_0x01be
                int r1 = r11.$consumed0
                if (r1 != 0) goto L_0x01be
                kotlin.jvm.internal.w r1 = r11.$result
                r2 = 0
                r1.element = r2
            L_0x01be:
                kotlin.x r0 = kotlin.x.a
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.u.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    static /* synthetic */ Object J0(a aVar, long limit, int headerSizeHint, kotlin.coroutines.d $completion) {
        io.ktor.utils.io.core.internal.a tail$iv;
        if (!aVar.f()) {
            return aVar.K0(limit, headerSizeHint, $completion);
        }
        io.ktor.utils.io.core.n builder$iv = f0.a(headerSizeHint);
        long remaining = limit;
        io.ktor.utils.io.core.c0 $this$writeWhile$iv = builder$iv;
        try {
            io.ktor.utils.io.core.internal.a tail$iv2 = io.ktor.utils.io.core.internal.g.j($this$writeWhile$iv, 1, (io.ktor.utils.io.core.internal.a) null);
            long remaining2 = remaining;
            while (true) {
                tail$iv = tail$iv2;
                io.ktor.utils.io.core.c cVar = tail$iv;
                io.ktor.utils.io.core.c this_$iv = cVar;
                if (((long) (this_$iv.m() - this_$iv.s())) > remaining2) {
                    cVar.P((int) remaining2);
                }
                remaining2 -= (long) y0(aVar, cVar, 0, 0, 6, (Object) null);
                if (!kotlin.coroutines.jvm.internal.b.a(remaining2 > 0 && !aVar.y()).booleanValue()) {
                    io.ktor.utils.io.core.internal.g.b($this$writeWhile$iv, tail$iv);
                    return builder$iv.e1();
                }
                tail$iv2 = io.ktor.utils.io.core.internal.g.j($this$writeWhile$iv, 1, tail$iv);
            }
        } catch (Throwable t$iv) {
            builder$iv.release();
            throw t$iv;
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 25 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v21, resolved type: io.ktor.utils.io.core.internal.a} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v26, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v1, resolved type: kotlin.jvm.internal.y} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v15, resolved type: io.ktor.utils.io.core.n} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v26, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v15, resolved type: io.ktor.utils.io.a} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00ba, code lost:
        r26 = r5;
        r18 = r26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00cb, code lost:
        r27 = r2;
        r28 = r11;
        r29 = r12;
        r11 = (long) (r18.m() - r18.s());
        r2 = r4;
        r24 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r4 = r6.element;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00db, code lost:
        if (r11 <= r4) goto L_0x00f4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00dd, code lost:
        r5 = r26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        r5.P((int) r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00e4, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00e5, code lost:
        r11 = r28;
        r12 = r29;
        r18 = r1;
        r4 = r2;
        r19 = r15;
        r5 = r24;
        r2 = r27;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00f4, code lost:
        r5 = r26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r4 = y0(r15, r5, 0, 0, 6, (java.lang.Object) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0108, code lost:
        r19 = r7;
        r18 = r8;
        r11 = r6.element - ((long) r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        r6.element = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0114, code lost:
        if (r11 <= 0) goto L_0x01aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0116, code lost:
        r3.L$0 = r15;
        r3.J$0 = r9;
        r3.I$0 = r1;
        r3.L$1 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x011e, code lost:
        r7 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        r3.L$2 = r7;
        r3.L$3 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0124, code lost:
        r8 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        r3.L$4 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0128, code lost:
        r11 = r24;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        r3.L$5 = r11;
        r3.L$6 = r5;
        r3.I$1 = r4;
        r3.label = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0133, code lost:
        r18 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        r1 = r15.L0(1, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0139, code lost:
        if (r1 != r0) goto L_0x013c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x013b, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x013c, code lost:
        r12 = r6;
        r20 = r15;
        r6 = r5;
        r5 = r11;
        r15 = r13;
        r16 = r14;
        r11 = r28;
        r14 = r29;
        r13 = r7;
        r7 = r4;
        r4 = r1;
        r1 = r27;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0155, code lost:
        if (((java.lang.Boolean) r4).booleanValue() == false) goto L_0x0163;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0157, code lost:
        r4 = r2;
        r6 = r12;
        r7 = r13;
        r13 = r15;
        r15 = r20;
        r12 = true;
        r2 = r1;
        r1 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0163, code lost:
        r4 = r7;
        r7 = r13;
        r13 = r11;
        r11 = r5;
        r5 = r6;
        r6 = r2;
        r2 = r1;
        r1 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x016e, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x016f, code lost:
        r4 = r2;
        r6 = r12;
        r7 = r13;
        r12 = r14;
        r13 = r15;
        r14 = r16;
        r19 = r20;
        r2 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x017b, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x017c, code lost:
        r12 = r29;
        r4 = r2;
        r5 = r11;
        r19 = r15;
        r2 = r27;
        r11 = r28;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0188, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0189, code lost:
        r18 = r1;
        r12 = r29;
        r4 = r2;
        r5 = r11;
        r19 = r15;
        r2 = r27;
        r11 = r28;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0197, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0198, code lost:
        r8 = r18;
        r18 = r1;
        r12 = r29;
        r4 = r2;
        r5 = r24;
        r19 = r15;
        r2 = r27;
        r11 = r28;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01aa, code lost:
        r8 = r18;
        r7 = r19;
        r11 = r24;
        r18 = r1;
        r12 = r6;
        r20 = r15;
        r6 = r2;
        r15 = r13;
        r16 = r14;
        r2 = r27;
        r13 = r28;
        r14 = r29;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01c1, code lost:
        r4 = r6;
        r5 = r11;
        r6 = r12;
        r11 = r13;
        r13 = r15;
        r15 = r20;
        r12 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01d1, code lost:
        if (kotlin.coroutines.jvm.internal.b.a(r12).booleanValue() != false) goto L_0x01e2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:?, code lost:
        io.ktor.utils.io.core.internal.g.b(r8, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01dd, code lost:
        return r13.e1();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01de, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01df, code lost:
        r12 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01e7, code lost:
        r5 = io.ktor.utils.io.core.internal.g.j(r8, 1, r5);
        r12 = r14;
        r14 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01ee, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01ef, code lost:
        r18 = r1;
        r12 = r14;
        r19 = r15;
        r14 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x01f7, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x01f8, code lost:
        r8 = r18;
        r7 = r19;
        r18 = r1;
        r12 = r29;
        r4 = r2;
        r5 = r24;
        r19 = r15;
        r2 = r27;
        r11 = r28;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x020b, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x020c, code lost:
        r18 = r1;
        r12 = r29;
        r4 = r2;
        r5 = r24;
        r19 = r15;
        r2 = r27;
        r11 = r28;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x021b, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x021c, code lost:
        r18 = r1;
        r27 = r2;
        r2 = r4;
        r28 = r11;
        r29 = r12;
        r11 = r5;
        r19 = r15;
        r2 = r27;
        r11 = r28;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002c  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object K0(long r26, int r28, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super io.ktor.utils.io.core.q> r29) {
        /*
            r25 = this;
            r1 = r29
            boolean r0 = r1 instanceof io.ktor.utils.io.a.p
            if (r0 == 0) goto L_0x0017
            r0 = r1
            io.ktor.utils.io.a$p r0 = (io.ktor.utils.io.a.p) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0017
            int r2 = r2 - r3
            r0.label = r2
            r2 = r25
            goto L_0x001e
        L_0x0017:
            io.ktor.utils.io.a$p r0 = new io.ktor.utils.io.a$p
            r2 = r25
            r0.<init>(r2, r1)
        L_0x001e:
            r3 = r0
            java.lang.Object r4 = r3.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r5 = r3.label
            r7 = 0
            r8 = 0
            switch(r5) {
                case 0: goto L_0x008e;
                case 1: goto L_0x0036;
                default: goto L_0x002c;
            }
        L_0x002c:
            r9 = r26
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0036:
            r5 = r8
            r9 = r7
            r10 = r8
            r11 = r8
            r12 = r7
            r13 = r8
            r14 = r7
            r15 = r8
            r16 = r7
            r17 = r7
            int r7 = r3.I$1
            java.lang.Object r6 = r3.L$6
            io.ktor.utils.io.core.c r6 = (io.ktor.utils.io.core.c) r6
            java.lang.Object r8 = r3.L$5
            r5 = r8
            io.ktor.utils.io.core.internal.a r5 = (io.ktor.utils.io.core.internal.a) r5
            java.lang.Object r8 = r3.L$4
            io.ktor.utils.io.core.c0 r8 = (io.ktor.utils.io.core.c0) r8
            java.lang.Object r11 = r3.L$3
            r10 = r11
            kotlin.jvm.internal.y r10 = (kotlin.jvm.internal.y) r10
            java.lang.Object r11 = r3.L$2
            io.ktor.utils.io.core.n r11 = (io.ktor.utils.io.core.n) r11
            java.lang.Object r15 = r3.L$1
            r13 = r15
            io.ktor.utils.io.core.n r13 = (io.ktor.utils.io.core.n) r13
            int r15 = r3.I$0
            long r1 = r3.J$0
            r18 = r0
            java.lang.Object r0 = r3.L$0
            r19 = r0
            io.ktor.utils.io.a r19 = (io.ktor.utils.io.a) r19
            kotlin.p.b(r4)     // Catch:{ all -> 0x0083 }
            r0 = r18
            r20 = r19
            r18 = r15
            r19 = r16
            r15 = r13
            r16 = r14
            r13 = r11
            r14 = r12
            r11 = r9
            r12 = r10
            r9 = r1
            r2 = r4
            r1 = r29
            goto L_0x014f
        L_0x0083:
            r0 = move-exception
            r6 = r10
            r7 = r11
            r18 = r15
            r11 = r9
            r9 = r1
            r2 = r29
            goto L_0x022c
        L_0x008e:
            r18 = r0
            kotlin.p.b(r4)
            r1 = 0
            io.ktor.utils.io.core.n r2 = io.ktor.utils.io.core.f0.a(r28)
            r0 = r2
            r5 = 0
            kotlin.jvm.internal.y r6 = new kotlin.jvm.internal.y     // Catch:{ all -> 0x0238 }
            r6.<init>()     // Catch:{ all -> 0x0238 }
            r9 = r26
            r6.element = r9     // Catch:{ all -> 0x0236 }
            r7 = r0
            r11 = 0
            r12 = 1
            io.ktor.utils.io.core.internal.a r8 = io.ktor.utils.io.core.internal.g.j(r7, r12, r8)     // Catch:{ all -> 0x0236 }
            r15 = r25
            r12 = r1
            r13 = r2
            r14 = r5
            r5 = r8
            r1 = r28
            r2 = r29
            r8 = r7
            r7 = r0
            r0 = r18
        L_0x00ba:
            r26 = r5
            r16 = 0
            r18 = r26
            r19 = 0
            int r20 = r18.m()     // Catch:{ all -> 0x021b }
            int r21 = r18.s()     // Catch:{ all -> 0x021b }
            r27 = r2
            int r2 = r20 - r21
            r28 = r11
            r29 = r12
            long r11 = (long) r2
            r2 = r4
            r24 = r5
            long r4 = r6.element     // Catch:{ all -> 0x020b }
            int r11 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r11 <= 0) goto L_0x00f4
            int r4 = (int) r4
            r5 = r26
            r5.P(r4)     // Catch:{ all -> 0x00e4 }
            goto L_0x00f6
        L_0x00e4:
            r0 = move-exception
            r11 = r28
            r12 = r29
            r18 = r1
            r4 = r2
            r19 = r15
            r5 = r24
            r2 = r27
            goto L_0x022c
        L_0x00f4:
            r5 = r26
        L_0x00f6:
            r20 = 0
            r21 = 0
            r22 = 6
            r23 = 0
            r18 = r15
            r19 = r5
            int r4 = y0(r18, r19, r20, r21, r22, r23)     // Catch:{ all -> 0x020b }
            long r11 = r6.element     // Catch:{ all -> 0x020b }
            r19 = r7
            r18 = r8
            long r7 = (long) r4
            long r11 = r11 - r7
            r6.element = r11     // Catch:{ all -> 0x01f7 }
            r7 = 0
            int r7 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
            if (r7 <= 0) goto L_0x01aa
            r3.L$0 = r15     // Catch:{ all -> 0x01f7 }
            r3.J$0 = r9     // Catch:{ all -> 0x01f7 }
            r3.I$0 = r1     // Catch:{ all -> 0x01f7 }
            r3.L$1 = r13     // Catch:{ all -> 0x01f7 }
            r7 = r19
            r3.L$2 = r7     // Catch:{ all -> 0x0197 }
            r3.L$3 = r6     // Catch:{ all -> 0x0197 }
            r8 = r18
            r3.L$4 = r8     // Catch:{ all -> 0x020b }
            r11 = r24
            r3.L$5 = r11     // Catch:{ all -> 0x0188 }
            r3.L$6 = r5     // Catch:{ all -> 0x0188 }
            r3.I$1 = r4     // Catch:{ all -> 0x0188 }
            r12 = 1
            r3.label = r12     // Catch:{ all -> 0x0188 }
            r18 = r1
            java.lang.Object r1 = r15.L0(r12, r3)     // Catch:{ all -> 0x017b }
            if (r1 != r0) goto L_0x013c
            return r0
        L_0x013c:
            r12 = r6
            r20 = r15
            r19 = r16
            r6 = r5
            r5 = r11
            r15 = r13
            r16 = r14
            r11 = r28
            r14 = r29
            r13 = r7
            r7 = r4
            r4 = r1
            r1 = r27
        L_0x014f:
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ all -> 0x016e }
            boolean r4 = r4.booleanValue()     // Catch:{ all -> 0x016e }
            if (r4 == 0) goto L_0x0163
            r4 = r2
            r6 = r12
            r7 = r13
            r13 = r15
            r15 = r20
            r12 = 1
            r2 = r1
            r1 = r18
            goto L_0x01c9
        L_0x0163:
            r4 = r7
            r7 = r13
            r13 = r11
            r11 = r5
            r5 = r6
            r6 = r2
            r2 = r1
            r1 = r18
            goto L_0x01c1
        L_0x016e:
            r0 = move-exception
            r4 = r2
            r6 = r12
            r7 = r13
            r12 = r14
            r13 = r15
            r14 = r16
            r19 = r20
            r2 = r1
            goto L_0x022c
        L_0x017b:
            r0 = move-exception
            r12 = r29
            r4 = r2
            r5 = r11
            r19 = r15
            r2 = r27
            r11 = r28
            goto L_0x022c
        L_0x0188:
            r0 = move-exception
            r18 = r1
            r12 = r29
            r4 = r2
            r5 = r11
            r19 = r15
            r2 = r27
            r11 = r28
            goto L_0x022c
        L_0x0197:
            r0 = move-exception
            r8 = r18
            r11 = r24
            r18 = r1
            r12 = r29
            r4 = r2
            r5 = r11
            r19 = r15
            r2 = r27
            r11 = r28
            goto L_0x022c
        L_0x01aa:
            r8 = r18
            r7 = r19
            r11 = r24
            r18 = r1
            r12 = r6
            r20 = r15
            r19 = r16
            r6 = r2
            r15 = r13
            r16 = r14
            r2 = r27
            r13 = r28
            r14 = r29
        L_0x01c1:
            r4 = r6
            r5 = r11
            r6 = r12
            r11 = r13
            r13 = r15
            r15 = r20
            r12 = 0
        L_0x01c9:
            java.lang.Boolean r12 = kotlin.coroutines.jvm.internal.b.a(r12)     // Catch:{ all -> 0x01ee }
            boolean r12 = r12.booleanValue()     // Catch:{ all -> 0x01ee }
            if (r12 != 0) goto L_0x01e2
            io.ktor.utils.io.core.internal.g.b(r8, r5)     // Catch:{ all -> 0x01de }
            io.ktor.utils.io.core.q r0 = r13.e1()     // Catch:{ all -> 0x01de }
            return r0
        L_0x01de:
            r0 = move-exception
            r12 = r14
            goto L_0x0243
        L_0x01e2:
            r12 = 1
            io.ktor.utils.io.core.internal.a r17 = io.ktor.utils.io.core.internal.g.j(r8, r12, r5)     // Catch:{ all -> 0x01ee }
            r5 = r17
            r12 = r14
            r14 = r16
            goto L_0x00ba
        L_0x01ee:
            r0 = move-exception
            r18 = r1
            r12 = r14
            r19 = r15
            r14 = r16
            goto L_0x022c
        L_0x01f7:
            r0 = move-exception
            r8 = r18
            r7 = r19
            r11 = r24
            r18 = r1
            r12 = r29
            r4 = r2
            r5 = r11
            r19 = r15
            r2 = r27
            r11 = r28
            goto L_0x022c
        L_0x020b:
            r0 = move-exception
            r18 = r1
            r11 = r24
            r12 = r29
            r4 = r2
            r5 = r11
            r19 = r15
            r2 = r27
            r11 = r28
            goto L_0x022c
        L_0x021b:
            r0 = move-exception
            r18 = r1
            r27 = r2
            r2 = r4
            r28 = r11
            r29 = r12
            r11 = r5
            r19 = r15
            r2 = r27
            r11 = r28
        L_0x022c:
            io.ktor.utils.io.core.internal.g.b(r8, r5)     // Catch:{ all -> 0x0230 }
            throw r0     // Catch:{ all -> 0x0230 }
        L_0x0230:
            r0 = move-exception
            r1 = r18
            r15 = r19
            goto L_0x0243
        L_0x0236:
            r0 = move-exception
            goto L_0x023b
        L_0x0238:
            r0 = move-exception
            r9 = r26
        L_0x023b:
            r15 = r25
            r12 = r1
            r13 = r2
            r1 = r28
            r2 = r29
        L_0x0243:
            r13.release()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.K0(long, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    public final void Y0() {
        Throwable closedCause = null;
        kotlin.coroutines.d $this$apply = d.getAndSet(this, (Object) null);
        if ($this$apply != null) {
            C0279a aVar = this.closed;
            if (aVar != null) {
                closedCause = aVar.b();
            }
            if (closedCause != null) {
                o.a aVar2 = kotlin.o.Companion;
                $this$apply.resumeWith(kotlin.o.m17constructorimpl(kotlin.p.a(closedCause)));
                return;
            }
            o.a aVar3 = kotlin.o.Companion;
            $this$apply.resumeWith(kotlin.o.m17constructorimpl(true));
        }
    }

    private final void Z0() {
        kotlin.coroutines.d writeOp2;
        C0279a closed2;
        Object obj;
        do {
            writeOp2 = this.writeOp;
            if (writeOp2 != null) {
                closed2 = this.closed;
                if (closed2 == null && this.joining != null) {
                    io.ktor.utils.io.internal.f state2 = this.state;
                    if (!(state2 instanceof f.g) && !(state2 instanceof f.e) && state2 != f.C0290f.c) {
                        return;
                    }
                }
            } else {
                return;
            }
        } while (!c.compareAndSet(this, writeOp2, (Object) null));
        if (closed2 == null) {
            obj = kotlin.x.a;
            o.a aVar = kotlin.o.Companion;
        } else {
            Throwable c2 = closed2.c();
            o.a aVar2 = kotlin.o.Companion;
            obj = kotlin.p.a(c2);
        }
        writeOp2.resumeWith(kotlin.o.m17constructorimpl(obj));
    }

    private final void X0(Throwable cause) {
        kotlin.coroutines.d c2 = d.getAndSet(this, (Object) null);
        if (c2 != null) {
            if (cause != null) {
                o.a aVar = kotlin.o.Companion;
                c2.resumeWith(kotlin.o.m17constructorimpl(kotlin.p.a(cause)));
            } else {
                Boolean valueOf = Boolean.valueOf(this.state.b.availableForRead > 0);
                o.a aVar2 = kotlin.o.Companion;
                c2.resumeWith(kotlin.o.m17constructorimpl(valueOf));
            }
        }
        kotlin.coroutines.d andSet = c.getAndSet(this, (Object) null);
        if (andSet != null) {
            Throwable closedWriteChannelException = cause != null ? cause : new ClosedWriteChannelException("Byte channel was closed");
            o.a aVar3 = kotlin.o.Companion;
            andSet.resumeWith(kotlin.o.m17constructorimpl(kotlin.p.a(closedWriteChannelException)));
        }
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final /* synthetic */ Object L0(int size, @NotNull kotlin.coroutines.d<? super Boolean> $completion) {
        boolean result = true;
        if (this.state.b.availableForRead >= size) {
            return kotlin.coroutines.jvm.internal.b.a(true);
        }
        C0279a c2 = this.closed;
        if (c2 != null) {
            if (c2.b() == null) {
                io.ktor.utils.io.internal.k afterCapacity = this.state.b;
                if (!afterCapacity.e() || afterCapacity.availableForRead < size) {
                    result = false;
                }
                if (this.readOp == null) {
                    return kotlin.coroutines.jvm.internal.b.a(result);
                }
                throw new IllegalStateException("Read operation is already in progress");
            }
            throw c2.b();
        } else if (size == 1) {
            return M0(1, $completion);
        } else {
            return N0(size, $completion);
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 11 */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object N0(int r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Boolean> r13) {
        /*
            r11 = this;
            boolean r0 = r13 instanceof io.ktor.utils.io.a.q
            if (r0 == 0) goto L_0x0013
            r0 = r13
            io.ktor.utils.io.a$q r0 = (io.ktor.utils.io.a.q) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.a$q r0 = new io.ktor.utils.io.a$q
            r0.<init>(r11, r13)
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
            r3 = 0
            java.lang.Object r5 = r0.L$1
            r3 = r5
            io.ktor.utils.io.internal.k r3 = (io.ktor.utils.io.internal.k) r3
            int r12 = r0.I$0
            java.lang.Object r5 = r0.L$0
            io.ktor.utils.io.a r5 = (io.ktor.utils.io.a) r5
            kotlin.p.b(r1)
            r6 = r5
            r5 = r3
            r3 = r2
            r2 = r1
            goto L_0x0098
        L_0x0040:
            kotlin.p.b(r1)
            r5 = r11
        L_0x0044:
            io.ktor.utils.io.internal.f r3 = r5.state
            io.ktor.utils.io.internal.k r3 = r3.b
            int r6 = r3.availableForRead
            r7 = 1
            if (r6 < r12) goto L_0x0052
            java.lang.Boolean r2 = kotlin.coroutines.jvm.internal.b.a(r7)
            return r2
        L_0x0052:
            io.ktor.utils.io.a$a r6 = r5.closed
            if (r6 == 0) goto L_0x0083
            r2 = r6
            r6 = 0
            java.lang.Throwable r8 = r2.b()
            if (r8 != 0) goto L_0x007e
            io.ktor.utils.io.internal.f r8 = r5.state
            io.ktor.utils.io.internal.k r8 = r8.b
            boolean r9 = r8.e()
            if (r9 == 0) goto L_0x006d
            int r9 = r8.availableForRead
            if (r9 < r12) goto L_0x006d
            r4 = r7
        L_0x006d:
            kotlin.coroutines.d<? super java.lang.Boolean> r7 = r5.readOp
            if (r7 != 0) goto L_0x0076
            java.lang.Boolean r7 = kotlin.coroutines.jvm.internal.b.a(r4)
            return r7
        L_0x0076:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r9 = "Read operation is already in progress"
            r7.<init>(r9)
            throw r7
        L_0x007e:
            java.lang.Throwable r4 = r2.b()
            throw r4
        L_0x0083:
            r0.L$0 = r5
            r0.I$0 = r12
            r0.L$1 = r3
            r0.label = r7
            java.lang.Object r6 = r5.M0(r12, r0)
            if (r6 != r2) goto L_0x0092
            return r2
        L_0x0092:
            r10 = r2
            r2 = r1
            r1 = r6
            r6 = r5
            r5 = r3
            r3 = r10
        L_0x0098:
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 != 0) goto L_0x00a5
            java.lang.Boolean r1 = kotlin.coroutines.jvm.internal.b.a(r4)
            return r1
        L_0x00a5:
            r1 = r2
            r2 = r3
            r5 = r6
            goto L_0x0044
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.N0(int, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START, MTH_ENTER_BLOCK] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0103 A[EDGE_INSN: B:80:0x0103->B:76:0x0103 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x00bb A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.Object i1(int r14, kotlin.coroutines.d<? super java.lang.Boolean> r15) {
        /*
            r13 = this;
        L_0x0000:
            r0 = r13
            r1 = 0
            io.ktor.utils.io.internal.f r2 = r0.state
            io.ktor.utils.io.internal.k r3 = r2.b
            int r3 = r3.availableForRead
            r4 = 1
            r5 = 0
            if (r3 < r14) goto L_0x0011
            r0 = r5
            goto L_0x0029
        L_0x0011:
            io.ktor.utils.io.a$c r3 = r0.joining
            if (r3 == 0) goto L_0x0028
            kotlin.coroutines.d r3 = r0.writeOp
            if (r3 == 0) goto L_0x0028
            io.ktor.utils.io.internal.f$a r3 = io.ktor.utils.io.internal.f.a.c
            if (r2 == r3) goto L_0x0026
            boolean r3 = r2 instanceof io.ktor.utils.io.internal.f.b
            if (r3 == 0) goto L_0x0028
        L_0x0026:
            r0 = r5
            goto L_0x0029
        L_0x0028:
            r0 = r4
        L_0x0029:
            if (r0 != 0) goto L_0x003a
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r4)
            kotlin.o$a r1 = kotlin.o.Companion
            java.lang.Object r0 = kotlin.o.m17constructorimpl(r0)
            r15.resumeWith(r0)
            goto L_0x0103
        L_0x003a:
            io.ktor.utils.io.a$a r0 = r13.closed
            if (r0 == 0) goto L_0x007e
            r1 = 0
            java.lang.Throwable r2 = r0.b()
            if (r2 == 0) goto L_0x0057
            java.lang.Throwable r2 = r0.b()
            kotlin.o$a r3 = kotlin.o.Companion
            java.lang.Object r2 = kotlin.p.a(r2)
            java.lang.Object r2 = kotlin.o.m17constructorimpl(r2)
            r15.resumeWith(r2)
            goto L_0x0078
        L_0x0057:
            io.ktor.utils.io.internal.f r2 = r13.state
            io.ktor.utils.io.internal.k r2 = r2.b
            boolean r2 = r2.e()
            if (r2 == 0) goto L_0x006a
            io.ktor.utils.io.internal.f r2 = r13.state
            io.ktor.utils.io.internal.k r2 = r2.b
            int r2 = r2.availableForRead
            if (r2 < r14) goto L_0x006a
            goto L_0x006b
        L_0x006a:
            r4 = r5
        L_0x006b:
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r4)
            kotlin.o$a r3 = kotlin.o.Companion
            java.lang.Object r2 = kotlin.o.m17constructorimpl(r2)
            r15.resumeWith(r2)
        L_0x0078:
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            return r2
        L_0x007e:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater<io.ktor.utils.io.a, kotlin.coroutines.d<java.lang.Boolean>> r0 = d
            r1 = r13
            r2 = 0
        L_0x0082:
            r3 = 0
            kotlin.coroutines.d<? super java.lang.Boolean> r3 = r13.readOp
            if (r3 != 0) goto L_0x010a
            r6 = 0
            io.ktor.utils.io.a$a r7 = r13.closed
            if (r7 != 0) goto L_0x00b8
            r7 = r14
            r8 = r13
            r9 = 0
            io.ktor.utils.io.internal.f r10 = r8.state
            io.ktor.utils.io.internal.k r11 = r10.b
            int r11 = r11.availableForRead
            if (r11 < r7) goto L_0x009c
            r7 = r5
            goto L_0x00b4
        L_0x009c:
            io.ktor.utils.io.a$c r11 = r8.joining
            if (r11 == 0) goto L_0x00b3
            kotlin.coroutines.d r11 = r8.writeOp
            if (r11 == 0) goto L_0x00b3
            io.ktor.utils.io.internal.f$a r11 = io.ktor.utils.io.internal.f.a.c
            if (r10 == r11) goto L_0x00b1
            boolean r11 = r10 instanceof io.ktor.utils.io.internal.f.b
            if (r11 == 0) goto L_0x00b3
        L_0x00b1:
            r7 = r5
            goto L_0x00b4
        L_0x00b3:
            r7 = r4
        L_0x00b4:
            if (r7 == 0) goto L_0x00b8
            r6 = r4
            goto L_0x00b9
        L_0x00b8:
            r6 = r5
        L_0x00b9:
            if (r6 != 0) goto L_0x00bd
            r4 = r5
            goto L_0x0101
        L_0x00bd:
            r6 = 0
            boolean r7 = r0.compareAndSet(r1, r6, r15)
            if (r7 == 0) goto L_0x0108
            r7 = 0
            io.ktor.utils.io.a$a r8 = r13.closed
            if (r8 != 0) goto L_0x00f4
            r8 = r14
            r9 = r13
            r10 = 0
            io.ktor.utils.io.internal.f r11 = r9.state
            io.ktor.utils.io.internal.k r12 = r11.b
            int r12 = r12.availableForRead
            if (r12 < r8) goto L_0x00d8
            r8 = r5
            goto L_0x00f0
        L_0x00d8:
            io.ktor.utils.io.a$c r12 = r9.joining
            if (r12 == 0) goto L_0x00ef
            kotlin.coroutines.d r12 = r9.writeOp
            if (r12 == 0) goto L_0x00ef
            io.ktor.utils.io.internal.f$a r12 = io.ktor.utils.io.internal.f.a.c
            if (r11 == r12) goto L_0x00ed
            boolean r12 = r11 instanceof io.ktor.utils.io.internal.f.b
            if (r12 == 0) goto L_0x00ef
        L_0x00ed:
            r8 = r5
            goto L_0x00f0
        L_0x00ef:
            r8 = r4
        L_0x00f0:
            if (r8 == 0) goto L_0x00f4
            r7 = r4
            goto L_0x00f5
        L_0x00f4:
            r7 = r5
        L_0x00f5:
            if (r7 != 0) goto L_0x0100
            boolean r6 = r0.compareAndSet(r1, r15, r6)
            if (r6 != 0) goto L_0x00fe
            goto L_0x0100
        L_0x00fe:
            r4 = r5
            goto L_0x0101
        L_0x0100:
        L_0x0101:
            if (r4 == 0) goto L_0x0000
        L_0x0103:
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            return r0
        L_0x0108:
            goto L_0x0082
        L_0x010a:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "Operation is already in progress"
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.i1(int, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final /* synthetic */ Object M0(int size, @NotNull kotlin.coroutines.d<? super Boolean> $completion) {
        io.ktor.utils.io.internal.f state$iv = this.state;
        boolean z2 = false;
        if (state$iv.b.availableForRead < size && (this.joining == null || this.writeOp == null || (state$iv != f.a.c && !(state$iv instanceof f.b)))) {
            z2 = true;
        }
        if (!z2) {
            return kotlin.coroutines.jvm.internal.b.a(true);
        }
        io.ktor.utils.io.internal.a c2 = this.m;
        i1(size, c2);
        Object i2 = c2.i(kotlin.coroutines.intrinsics.b.c($completion));
        if (i2 == kotlin.coroutines.intrinsics.c.d()) {
            kotlin.coroutines.jvm.internal.h.c($completion);
        }
        return i2;
    }

    /* access modifiers changed from: private */
    public final boolean h1() {
        return this.joining != null && (this.state == f.a.c || (this.state instanceof f.b));
    }

    /* access modifiers changed from: private */
    public final boolean D1(int size) {
        c joined = this.joining;
        io.ktor.utils.io.internal.f state2 = this.state;
        if (this.closed != null) {
            return false;
        }
        if (joined == null) {
            if (state2.b.availableForWrite >= size || state2 == f.a.c) {
                return false;
            }
            return true;
        } else if (state2 == f.C0290f.c || (state2 instanceof f.g) || (state2 instanceof f.e)) {
            return false;
        } else {
            return true;
        }
    }

    /* compiled from: ByteBufferChannel.kt */
    public static final class c0 extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<kotlin.coroutines.d<? super kotlin.x>, Object> {
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c0(a aVar) {
            super(1);
            this.this$0 = aVar;
        }

        @NotNull
        public final Object invoke(@NotNull kotlin.coroutines.d<? super kotlin.x> ucont) {
            Throwable it;
            kotlin.jvm.internal.k.f(ucont, "ucont");
            int size = this.this$0.writeSuspensionSize;
            while (true) {
                C0279a C = this.this$0.closed;
                if (C != null && (it = C.c()) != null) {
                    throw it;
                } else if (!this.this$0.D1(size)) {
                    kotlin.x xVar = kotlin.x.a;
                    o.a aVar = kotlin.o.Companion;
                    ucont.resumeWith(kotlin.o.m17constructorimpl(xVar));
                    break;
                } else {
                    a this_$iv = this.this$0;
                    AtomicReferenceFieldUpdater updater$iv = a.c;
                    kotlin.coroutines.d continuation$iv = kotlin.coroutines.intrinsics.b.c(ucont);
                    while (this.this$0.writeOp == null) {
                        boolean z = false;
                        if (this.this$0.D1(size) != 0) {
                            if (updater$iv.compareAndSet(this_$iv, (Object) null, continuation$iv)) {
                                if (this.this$0.D1(size) != 0 || !updater$iv.compareAndSet(this_$iv, continuation$iv, (Object) null)) {
                                    z = true;
                                    continue;
                                }
                            }
                        }
                        if (z) {
                            break;
                        }
                    }
                    throw new IllegalStateException("Operation is already in progress");
                }
            }
            this.this$0.j0(1, size);
            if (this.this$0.h1()) {
                this.this$0.Y0();
            }
            return kotlin.coroutines.intrinsics.c.d();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: io.ktor.utils.io.a$a} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v13, resolved type: io.ktor.utils.io.a$a} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v14, resolved type: io.ktor.utils.io.a$a} */
    /* JADX WARNING: Multi-variable type inference failed */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object n1(int r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r6) {
        /*
            r4 = this;
            boolean r0 = r4.D1(r5)
            if (r0 != 0) goto L_0x001d
            io.ktor.utils.io.a$a r0 = r4.closed
            if (r0 == 0) goto L_0x0013
            java.lang.Throwable r0 = r0.c()
            if (r0 != 0) goto L_0x0011
            goto L_0x0013
        L_0x0011:
            r1 = 0
            throw r0
        L_0x0013:
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            if (r0 != r1) goto L_0x001a
            return r0
        L_0x001a:
            kotlin.x r0 = kotlin.x.a
            return r0
        L_0x001d:
            r4.writeSuspensionSize = r5
            kotlinx.coroutines.z1 r0 = r4.attachedJob
            if (r0 == 0) goto L_0x003c
            kotlin.jvm.functions.l<kotlin.coroutines.d<? super kotlin.x>, java.lang.Object> r0 = r4.o
            java.lang.Object r0 = r0.invoke(r6)
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            if (r0 != r1) goto L_0x0032
            kotlin.coroutines.jvm.internal.h.c(r6)
        L_0x0032:
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            if (r0 != r1) goto L_0x0039
            return r0
        L_0x0039:
            kotlin.x r0 = kotlin.x.a
            return r0
        L_0x003c:
            r0 = r6
            r1 = 0
            io.ktor.utils.io.internal.a<kotlin.x> r2 = r4.n
            kotlin.jvm.functions.l<kotlin.coroutines.d<? super kotlin.x>, java.lang.Object> r3 = r4.o
            r3.invoke(r2)
            kotlin.coroutines.d r3 = kotlin.coroutines.intrinsics.b.c(r0)
            java.lang.Object r2 = r2.i(r3)
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            if (r2 != r0) goto L_0x0056
            kotlin.coroutines.jvm.internal.h.c(r6)
        L_0x0056:
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            if (r2 != r0) goto L_0x005d
            return r2
        L_0x005d:
            kotlin.x r0 = kotlin.x.a
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.n1(int, kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 20 */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0112  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0114  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x007f A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002a  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object B1(int r21, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r22) {
        /*
            r20 = this;
            r0 = r22
            boolean r1 = r0 instanceof io.ktor.utils.io.a.a0
            if (r1 == 0) goto L_0x0017
            r1 = r0
            io.ktor.utils.io.a$a0 r1 = (io.ktor.utils.io.a.a0) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0017
            int r2 = r2 - r3
            r1.label = r2
            r2 = r20
            goto L_0x001e
        L_0x0017:
            io.ktor.utils.io.a$a0 r1 = new io.ktor.utils.io.a$a0
            r2 = r20
            r1.<init>(r2, r0)
        L_0x001e:
            java.lang.Object r3 = r1.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.c.d()
            int r5 = r1.label
            r6 = 0
            switch(r5) {
                case 0: goto L_0x0043;
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
            int r7 = r1.I$0
            java.lang.Object r8 = r1.L$0
            io.ktor.utils.io.a r8 = (io.ktor.utils.io.a) r8
            kotlin.p.b(r3)
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r0
            r0 = r7
            goto L_0x00f0
        L_0x0043:
            kotlin.p.b(r3)
            r8 = r2
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r0
            r0 = r21
        L_0x004d:
            boolean r7 = r8.D1(r0)
            if (r7 == 0) goto L_0x0107
            r7 = 0
            r3.L$0 = r8
            r3.I$0 = r0
            r9 = 1
            r3.label = r9
            r10 = r3
            r11 = 0
            kotlinx.coroutines.o r12 = new kotlinx.coroutines.o
            kotlin.coroutines.d r13 = kotlin.coroutines.intrinsics.b.c(r10)
            r12.<init>(r13, r9)
            r13 = r12
            r14 = 0
        L_0x0068:
            io.ktor.utils.io.a$a r15 = r8.closed
            if (r15 == 0) goto L_0x0079
            java.lang.Throwable r15 = r15.c()
            if (r15 != 0) goto L_0x0076
            goto L_0x0079
        L_0x0076:
            r5 = r15
            r6 = 0
            throw r5
        L_0x0079:
            boolean r15 = r8.D1(r0)
            if (r15 != 0) goto L_0x008b
            kotlin.x r15 = kotlin.x.a
            kotlin.o$a r16 = kotlin.o.Companion
            java.lang.Object r15 = kotlin.o.m17constructorimpl(r15)
            r13.resumeWith(r15)
            goto L_0x00d2
        L_0x008b:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r15 = c
            r21 = r8
            r16 = 0
        L_0x0093:
            r17 = 0
            kotlin.coroutines.d r17 = r8.writeOp
            if (r17 != 0) goto L_0x00ff
            r18 = 0
            boolean r18 = r8.D1(r0)
            java.lang.Boolean r18 = kotlin.coroutines.jvm.internal.b.a(r18)
            boolean r18 = r18.booleanValue()
            if (r18 != 0) goto L_0x00ad
            goto L_0x00d0
        L_0x00ad:
            r6 = 0
            r9 = r21
            boolean r19 = r15.compareAndSet(r9, r6, r13)
            if (r19 == 0) goto L_0x00f9
            r19 = 0
            boolean r19 = r8.D1(r0)
            java.lang.Boolean r19 = kotlin.coroutines.jvm.internal.b.a(r19)
            boolean r19 = r19.booleanValue()
            if (r19 != 0) goto L_0x00cf
            boolean r6 = r15.compareAndSet(r9, r13, r6)
            if (r6 != 0) goto L_0x00cd
            goto L_0x00cf
        L_0x00cd:
            r6 = 0
            goto L_0x00d0
        L_0x00cf:
            r6 = 1
        L_0x00d0:
            if (r6 == 0) goto L_0x00f4
        L_0x00d2:
            r6 = 1
            r8.j0(r6, r0)
            boolean r6 = r8.h1()
            if (r6 == 0) goto L_0x00df
            r8.Y0()
        L_0x00df:
            java.lang.Object r6 = r12.t()
            java.lang.Object r9 = kotlin.coroutines.intrinsics.c.d()
            if (r6 != r9) goto L_0x00ed
            kotlin.coroutines.jvm.internal.h.c(r3)
        L_0x00ed:
            if (r6 != r5) goto L_0x00f0
            return r5
        L_0x00f0:
            r6 = 0
            goto L_0x004d
        L_0x00f4:
            r6 = 1
            r9 = r6
            r6 = 0
            goto L_0x0068
        L_0x00f9:
            r6 = 1
            r21 = r9
            r9 = r6
            r6 = 0
            goto L_0x0093
        L_0x00ff:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "Operation is already in progress"
            r5.<init>(r6)
            throw r5
        L_0x0107:
            io.ktor.utils.io.a$a r5 = r8.closed
            if (r5 == 0) goto L_0x0114
            java.lang.Throwable r5 = r5.c()
            if (r5 != 0) goto L_0x0112
            goto L_0x0114
        L_0x0112:
            r6 = 0
            throw r5
        L_0x0114:
            kotlin.x r5 = kotlin.x.a
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.a.B1(int, kotlin.coroutines.d):java.lang.Object");
    }

    private final f.c s0() {
        f.c result = this.q.p0();
        result.b().order(m0().getNioOrder());
        result.c().order(o0().getNioOrder());
        result.b.i();
        return result;
    }

    private final void S0(f.c buffer) {
        this.q.N0(buffer);
    }

    /* compiled from: ByteBufferChannel.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    static {
        Class<kotlin.coroutines.d> cls = kotlin.coroutines.d.class;
        Class<a> cls2 = a.class;
        AtomicReferenceFieldUpdater<a, io.ktor.utils.io.internal.f> newUpdater = AtomicReferenceFieldUpdater.newUpdater(cls2, io.ktor.utils.io.internal.f.class, d.INSTANCE.getName());
        kotlin.jvm.internal.k.b(newUpdater, "AtomicReferenceFieldUpda…a, T::class.java, p.name)");
        b = newUpdater;
        AtomicReferenceFieldUpdater<a, kotlin.coroutines.d<kotlin.x>> newUpdater2 = AtomicReferenceFieldUpdater.newUpdater(cls2, cls, e.INSTANCE.getName());
        kotlin.jvm.internal.k.b(newUpdater2, "AtomicReferenceFieldUpda…a, T::class.java, p.name)");
        c = newUpdater2;
        AtomicReferenceFieldUpdater<a, kotlin.coroutines.d<Boolean>> newUpdater3 = AtomicReferenceFieldUpdater.newUpdater(cls2, cls, c.INSTANCE.getName());
        kotlin.jvm.internal.k.b(newUpdater3, "AtomicReferenceFieldUpda…a, T::class.java, p.name)");
        d = newUpdater3;
        AtomicReferenceFieldUpdater<a, C0279a> newUpdater4 = AtomicReferenceFieldUpdater.newUpdater(cls2, C0279a.class, b.INSTANCE.getName());
        kotlin.jvm.internal.k.b(newUpdater4, "AtomicReferenceFieldUpda…a, T::class.java, p.name)");
        e = newUpdater4;
    }

    /* compiled from: ByteBufferChannel.kt */
    public static final class d implements t {
        public static final d b = new d();

        private d() {
        }

        public void w(int n) {
            if (n > 0) {
                throw new IllegalStateException("Unable to mark " + n + " bytes consumed for already terminated channel");
            }
        }

        @Nullable
        public ByteBuffer a(int skip, int atLeast) {
            return null;
        }

        @Nullable
        public Object k(int n, @NotNull kotlin.coroutines.d<? super Boolean> $completion) {
            boolean z = true;
            if (n >= 0) {
                if (n > 4088) {
                    z = false;
                }
                if (z) {
                    return kotlin.coroutines.jvm.internal.b.a(false);
                }
                throw new IllegalArgumentException(("atLeast parameter shouldn't be larger than max buffer size of 4088: " + n).toString());
            }
            throw new IllegalArgumentException(("atLeast parameter shouldn't be negative: " + n).toString());
        }
    }

    /* renamed from: io.ktor.utils.io.a$a  reason: collision with other inner class name */
    /* compiled from: ByteBufferChannel.kt */
    public static final class C0279a {
        /* access modifiers changed from: private */
        @NotNull
        public static final C0279a a = new C0279a((Throwable) null);
        public static final C0280a b = new C0280a((DefaultConstructorMarker) null);
        @Nullable
        private final Throwable c;

        public C0279a(@Nullable Throwable cause) {
            this.c = cause;
        }

        @Nullable
        public final Throwable b() {
            return this.c;
        }

        @NotNull
        public final Throwable c() {
            Throwable th = this.c;
            return th != null ? th : new ClosedWriteChannelException("The channel was closed");
        }

        /* renamed from: io.ktor.utils.io.a$a$a  reason: collision with other inner class name */
        /* compiled from: ByteBufferChannel.kt */
        public static final class C0280a {
            private C0280a() {
            }

            public /* synthetic */ C0280a(DefaultConstructorMarker $constructor_marker) {
                this();
            }

            @NotNull
            public final C0279a a() {
                return C0279a.a;
            }
        }
    }

    /* compiled from: ByteBufferChannel.kt */
    public static final class c {
        private static final AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(c.class, Object.class, "_closeWaitJob");
        private volatile Object _closeWaitJob = null;
        @NotNull
        private final a b;
        private final boolean c;
        private volatile int closed = 0;

        public c(@NotNull a delegatedTo, boolean delegateClose) {
            kotlin.jvm.internal.k.f(delegatedTo, "delegatedTo");
            this.b = delegatedTo;
            this.c = delegateClose;
        }

        public final boolean d() {
            return this.c;
        }

        @NotNull
        public final a e() {
            return this.b;
        }

        private final z1 c() {
            kotlinx.coroutines.z newJob;
            do {
                z1 current = (z1) this._closeWaitJob;
                if (current != null) {
                    return current;
                }
                newJob = e2.b((z1) null, 1, (Object) null);
            } while (!a.compareAndSet(this, (Object) null, newJob));
            if (this.closed == 1) {
                z1.a.a(newJob, (CancellationException) null, 1, (Object) null);
            }
            return newJob;
        }

        public final void b() {
            this.closed = 1;
            z1 z1Var = (z1) a.getAndSet(this, (Object) null);
            if (z1Var != null) {
                z1.a.a(z1Var, (CancellationException) null, 1, (Object) null);
            }
        }

        @Nullable
        public final Object a(@NotNull kotlin.coroutines.d<? super kotlin.x> $completion) {
            if (this.closed == 1) {
                return kotlin.x.a;
            }
            Object J = c().J($completion);
            return J == kotlin.coroutines.intrinsics.c.d() ? J : kotlin.x.a;
        }
    }
}
