package io.ktor.utils.io.core;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.ktor.utils.io.core.internal.e;
import io.ktor.utils.io.pool.d;
import java.nio.ByteBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: IoBufferJVM.kt */
public final class a0 extends io.ktor.utils.io.core.internal.a implements w, c0 {
    /* access modifiers changed from: private */
    public static final int B4 = io.ktor.utils.io.utils.a.a("buffer.size", 4096);
    private static final int C4;
    /* access modifiers changed from: private */
    public static final int D4 = io.ktor.utils.io.utils.a.a("buffer.pool.direct", 0);
    /* access modifiers changed from: private */
    @NotNull
    public static final a0 E4 = new a0(io.ktor.utils.io.bits.c.b.a(), (io.ktor.utils.io.core.internal.a) null, (DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @NotNull
    public static final d<a0> F4;
    @NotNull
    private static final d<a0> G4 = new a();
    @NotNull
    private static final d<a0> H4 = v.c;
    public static final c I4 = new c((DefaultConstructorMarker) null);

    /* compiled from: IoBufferJVM.kt */
    public static final class b extends io.ktor.utils.io.pool.b<a0> {

        /* compiled from: Require.kt */
        public static final class a extends e {
            @NotNull
            public Void a() {
                throw new IllegalArgumentException("Buffer is not yet released but tried to recycle");
            }
        }

        /* renamed from: io.ktor.utils.io.core.a0$b$b  reason: collision with other inner class name */
        /* compiled from: Require.kt */
        public static final class C0282b extends e {
            @NotNull
            public Void a() {
                throw new IllegalArgumentException("Unable to recycle buffer view, only origin buffers are applicable");
            }
        }

        b(int $super_call_param$0) {
            super($super_call_param$0);
        }

        /* access modifiers changed from: protected */
        @NotNull
        /* renamed from: u */
        public a0 l() {
            ByteBuffer buffer;
            switch (a0.D4) {
                case 0:
                    buffer = ByteBuffer.allocate(a0.B4);
                    break;
                default:
                    buffer = ByteBuffer.allocateDirect(a0.B4);
                    break;
            }
            k.b(buffer, "buffer");
            return new a0(buffer);
        }

        /* access modifiers changed from: protected */
        /* renamed from: t */
        public void i(@NotNull a0 instance) {
            k.f(instance, "instance");
            instance.h1();
        }

        /* access modifiers changed from: protected */
        @NotNull
        /* renamed from: s */
        public a0 g(@NotNull a0 instance) {
            k.f(instance, "instance");
            a0 $this$apply = instance;
            $this$apply.i1();
            $this$apply.E();
            return instance;
        }

        /* access modifiers changed from: protected */
        /* renamed from: v */
        public void r(@NotNull a0 instance) {
            k.f(instance, "instance");
            boolean condition$iv = true;
            if (instance.d1() == 0) {
                if (instance.c1() != null) {
                    condition$iv = false;
                }
                if (!condition$iv) {
                    new C0282b().a();
                    throw null;
                }
                return;
            }
            new a().a();
            throw null;
        }
    }

    private a0(ByteBuffer memory, io.ktor.utils.io.core.internal.a origin) {
        super(memory, origin, (DefaultConstructorMarker) null);
    }

    public /* synthetic */ a0(ByteBuffer memory, io.ktor.utils.io.core.internal.a origin, DefaultConstructorMarker $constructor_marker) {
        this(memory, origin);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public a0(@org.jetbrains.annotations.NotNull java.nio.ByteBuffer r5) {
        /*
            r4 = this;
            java.lang.String r0 = "external"
            kotlin.jvm.internal.k.f(r5, r0)
            io.ktor.utils.io.bits.c$a r0 = io.ktor.utils.io.bits.c.b
            r1 = 0
            java.nio.ByteBuffer r2 = r5.slice()
            java.nio.ByteOrder r3 = java.nio.ByteOrder.BIG_ENDIAN
            java.nio.ByteBuffer r2 = r2.order(r3)
            java.lang.String r3 = "buffer.slice().order(ByteOrder.BIG_ENDIAN)"
            kotlin.jvm.internal.k.b(r2, r3)
            java.nio.ByteBuffer r0 = io.ktor.utils.io.bits.c.b(r2)
            r1 = 0
            r4.<init>(r0, r1, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.core.a0.<init>(java.nio.ByteBuffer):void");
    }

    public boolean w0() {
        return !(s() > o());
    }

    public final long n0(@NotNull ByteBuffer destination, long destinationOffset, long offset, long min, long max) {
        k.f(destination, FirebaseAnalytics.Param.DESTINATION);
        return j.d(this, destination, destinationOffset, offset, min, max);
    }

    @NotNull
    public final /* synthetic */ Appendable append(@Nullable CharSequence csq, int start, int end) {
        e.c(this, csq, start, end);
        return this;
    }

    @NotNull
    public final /* synthetic */ Appendable append(@Nullable CharSequence csq) {
        e.b(this, csq);
        return this;
    }

    @NotNull
    public /* synthetic */ Appendable append(char c2) {
        e.a(this, c2);
        return this;
    }

    public final void n1(@NotNull ByteBuffer child) {
        k.f(child, "child");
        P(child.limit());
        c(child.position());
    }

    @NotNull
    /* renamed from: m1 */
    public a0 a1() {
        io.ktor.utils.io.core.internal.a newOrigin = c1();
        if (newOrigin == null) {
            newOrigin = this;
        }
        newOrigin.u0();
        a0 copy = new a0(n(), newOrigin, (DefaultConstructorMarker) null);
        j(copy);
        return copy;
    }

    public void close() {
        throw new UnsupportedOperationException("close for buffer view is not supported");
    }

    public final void e1(@NotNull d<a0> pool) {
        k.f(pool, "pool");
        j.f(this, pool);
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Buffer[readable = ");
        sb.append(s() - o());
        sb.append(", writable = ");
        sb.append(m() - s());
        sb.append(", startGap = ");
        sb.append(r());
        sb.append(", endGap = ");
        sb.append(l() - m());
        sb.append(']');
        return sb.toString();
    }

    /* compiled from: IoBufferJVM.kt */
    public static final class c {
        private c() {
        }

        public /* synthetic */ c(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final a0 a() {
            return a0.E4;
        }

        @NotNull
        public final d<a0> b() {
            return a0.F4;
        }
    }

    static {
        int a2 = io.ktor.utils.io.utils.a.a("buffer.pool.size", 100);
        C4 = a2;
        F4 = new b(a2);
    }

    /* compiled from: IoBufferJVM.kt */
    public static final class a extends io.ktor.utils.io.pool.c<a0> {
        a() {
        }

        @NotNull
        /* renamed from: a */
        public a0 p0() {
            ByteBuffer buffer;
            switch (a0.D4) {
                case 0:
                    buffer = ByteBuffer.allocate(a0.B4);
                    break;
                default:
                    buffer = ByteBuffer.allocateDirect(a0.B4);
                    break;
            }
            k.b(buffer, "buffer");
            return new a0(buffer);
        }
    }
}
