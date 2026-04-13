package io.ktor.utils.io.core.internal;

import io.ktor.utils.io.core.a0;
import io.ktor.utils.io.pool.d;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ChunkBuffer.kt */
public class a extends io.ktor.utils.io.core.c {
    private static final AtomicReferenceFieldUpdater a1;
    /* access modifiers changed from: private */
    @NotNull
    public static final io.ktor.utils.io.pool.d<a> a2 = new e();
    private static final AtomicIntegerFieldUpdater p1;
    /* access modifiers changed from: private */
    @NotNull
    public static final io.ktor.utils.io.pool.d<a> p2 = new b();
    @NotNull
    private static final io.ktor.utils.io.pool.d<a> p3 = new c();
    @NotNull
    private static final io.ktor.utils.io.pool.d<a> p4 = new d();
    public static final f z4 = new f((DefaultConstructorMarker) null);
    @Nullable
    private a A4;
    private volatile Object nextRef;
    private volatile int refCount;

    /* compiled from: ChunkBuffer.kt */
    public static final class b implements io.ktor.utils.io.pool.d<a> {

        /* renamed from: io.ktor.utils.io.core.internal.a$b$a  reason: collision with other inner class name */
        /* compiled from: Require.kt */
        public static final class C0284a extends e {
            @NotNull
            public Void a() {
                throw new IllegalArgumentException("Only ChunkBuffer.Empty instance could be recycled.");
            }
        }

        b() {
        }

        public void close() {
            d.a.a(this);
        }

        @NotNull
        /* renamed from: a */
        public a p0() {
            return a.z4.a();
        }

        /* renamed from: c */
        public void N0(@NotNull a instance) {
            k.f(instance, "instance");
            if (!(instance == a.z4.a())) {
                new C0284a().a();
                throw null;
            }
        }

        public void dispose() {
        }
    }

    /* renamed from: io.ktor.utils.io.core.internal.a$a  reason: collision with other inner class name */
    /* compiled from: Require.kt */
    public static final class C0283a extends e {
        @NotNull
        public Void a() {
            throw new IllegalArgumentException("A chunk couldn't be a view of itself.");
        }
    }

    /* compiled from: Require.kt */
    public static final class g extends e {
        @NotNull
        public Void a() {
            throw new IllegalArgumentException("Unable to reset buffer with origin");
        }
    }

    private a(ByteBuffer memory, a origin) {
        super(memory, (DefaultConstructorMarker) null);
        if (origin != this) {
            this.nextRef = null;
            this.refCount = 1;
            this.A4 = origin;
            return;
        }
        new C0283a().a();
        throw null;
    }

    public /* synthetic */ a(ByteBuffer memory, a origin, DefaultConstructorMarker $constructor_marker) {
        this(memory, origin);
    }

    @Nullable
    public final a c1() {
        return this.A4;
    }

    @Nullable
    public final a b1() {
        return (a) this.nextRef;
    }

    public final void g1(@Nullable a newValue) {
        if (newValue == null) {
            P0();
        } else {
            F0(newValue);
        }
    }

    public final int d1() {
        return this.refCount;
    }

    private final void F0(a chunk) {
        if (!a1.compareAndSet(this, (Object) null, chunk)) {
            throw new IllegalStateException("This chunk has already a next chunk.");
        }
    }

    @Nullable
    public final a P0() {
        return (a) a1.getAndSet(this, (Object) null);
    }

    @NotNull
    public a a1() {
        a newOrigin = this.A4;
        if (newOrigin == null) {
            newOrigin = this;
        }
        newOrigin.u0();
        a copy = new a(n(), newOrigin, (DefaultConstructorMarker) null);
        j(copy);
        return copy;
    }

    public void e1(@NotNull io.ktor.utils.io.pool.d<a> pool) {
        k.f(pool, "pool");
        if (f1()) {
            a origin = this.A4;
            if (origin != null) {
                h1();
                origin.e1(pool);
                return;
            }
            pool.N0(this);
        }
    }

    public final void h1() {
        if (p1.compareAndSet(this, 0, -1)) {
            P0();
            this.A4 = null;
            return;
        }
        throw new IllegalStateException("Unable to unlink: buffer is in use.");
    }

    public final void u0() {
        int cur$iv;
        int old;
        do {
            cur$iv = this.refCount;
            old = cur$iv;
            if (old > 0) {
            } else {
                throw new IllegalStateException("Unable to acquire chunk: it is already released.");
            }
        } while (!p1.compareAndSet(this, cur$iv, old + 1));
    }

    public final void i1() {
        int cur$iv;
        do {
            cur$iv = this.refCount;
            int old = cur$iv;
            if (old < 0) {
                throw new IllegalStateException("This instance is already disposed and couldn't be borrowed.");
            } else if (old > 0) {
                throw new IllegalStateException("This instance is already in use but somehow appeared in the pool.");
            }
        } while (!p1.compareAndSet(this, cur$iv, 1));
    }

    public final boolean f1() {
        int cur$iv;
        int upd$iv;
        do {
            cur$iv = this.refCount;
            int old = cur$iv;
            if (old > 0) {
                upd$iv = old - 1;
            } else {
                throw new IllegalStateException("Unable to release: it is already released.");
            }
        } while (!p1.compareAndSet(this, cur$iv, upd$iv));
        return upd$iv == 0;
    }

    public final void E() {
        if (this.A4 == null) {
            super.E();
            T((Object) null);
            this.nextRef = null;
            return;
        }
        new g().a();
        throw null;
    }

    /* compiled from: ChunkBuffer.kt */
    public static final class f {
        private f() {
        }

        public /* synthetic */ f(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final io.ktor.utils.io.pool.d<a> c() {
            return a.a2;
        }

        @NotNull
        public final a a() {
            return a0.I4.a();
        }

        @NotNull
        public final io.ktor.utils.io.pool.d<a> b() {
            return a.p2;
        }
    }

    /* compiled from: ChunkBuffer.kt */
    public static final class e implements io.ktor.utils.io.pool.d<a> {
        e() {
        }

        public void close() {
            d.a.a(this);
        }

        @NotNull
        /* renamed from: a */
        public a p0() {
            return io.ktor.utils.io.core.f.a().p0();
        }

        /* renamed from: c */
        public void N0(@NotNull a instance) {
            k.f(instance, "instance");
            if (instance instanceof a0) {
                io.ktor.utils.io.core.f.a().N0(instance);
                return;
            }
            throw new IllegalArgumentException("Only IoBuffer instances can be recycled.");
        }

        public void dispose() {
            io.ktor.utils.io.core.f.a().dispose();
        }
    }

    static {
        Class<a> cls = a.class;
        a1 = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "nextRef");
        p1 = AtomicIntegerFieldUpdater.newUpdater(cls, "refCount");
    }

    /* compiled from: ChunkBuffer.kt */
    public static final class c extends io.ktor.utils.io.pool.c<a> {
        c() {
        }

        @NotNull
        /* renamed from: a */
        public a p0() {
            return new a0(io.ktor.utils.io.bits.b.a.b(4096), (a) null, (DefaultConstructorMarker) null);
        }

        /* renamed from: c */
        public void N0(@NotNull a instance) {
            k.f(instance, "instance");
            if (instance instanceof a0) {
                io.ktor.utils.io.bits.b.a.a(instance.n());
                return;
            }
            throw new IllegalArgumentException("Only IoBuffer instances can be recycled.");
        }
    }

    /* compiled from: ChunkBuffer.kt */
    public static final class d extends io.ktor.utils.io.pool.c<a> {
        d() {
        }

        @NotNull
        /* renamed from: a */
        public a p0() {
            throw new UnsupportedOperationException("This pool doesn't support borrow");
        }

        /* renamed from: c */
        public void N0(@NotNull a instance) {
            k.f(instance, "instance");
        }
    }
}
