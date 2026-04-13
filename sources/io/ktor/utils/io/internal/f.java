package io.ktor.utils.io.internal;

import java.nio.ByteBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReadWriteBufferState.kt */
public abstract class f {
    @NotNull
    public final ByteBuffer a;
    @NotNull
    public final k b;

    private f(ByteBuffer backingBuffer, k capacity) {
        this.a = backingBuffer;
        this.b = capacity;
    }

    public /* synthetic */ f(ByteBuffer backingBuffer, k capacity, DefaultConstructorMarker $constructor_marker) {
        this(backingBuffer, capacity);
    }

    public boolean a() {
        return false;
    }

    @NotNull
    public ByteBuffer b() {
        throw new IllegalStateException(("read buffer is not available in state " + this).toString());
    }

    @NotNull
    public ByteBuffer c() {
        throw new IllegalStateException(("write buffer is not available in state " + this).toString());
    }

    @NotNull
    public f d() {
        throw new IllegalStateException(("Reading is not available in state " + this).toString());
    }

    @NotNull
    public f e() {
        throw new IllegalStateException(("Writing is not available in state " + this).toString());
    }

    @NotNull
    public f f() {
        throw new IllegalStateException(("Unable to stop reading in state " + this).toString());
    }

    @NotNull
    public f g() {
        throw new IllegalStateException(("Unable to stop writing in state " + this).toString());
    }

    /* compiled from: ReadWriteBufferState.kt */
    public static final class a extends f {
        public static final a c = new a();

        private a() {
            super(g.a(), g.b(), (DefaultConstructorMarker) null);
        }

        public boolean a() {
            return true;
        }

        @NotNull
        public String toString() {
            return "IDLE(empty)";
        }
    }

    /* compiled from: ReadWriteBufferState.kt */
    public static final class c extends f {
        @NotNull
        private final ByteBuffer c;
        @NotNull
        private final ByteBuffer d;
        @NotNull
        private final b e;
        @NotNull
        private final d f;
        @NotNull
        private final g g;
        @NotNull
        private final e h;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public c(@NotNull ByteBuffer backingBuffer, int reservedSize) {
            super(backingBuffer, new k(backingBuffer.capacity() - reservedSize), (DefaultConstructorMarker) null);
            k.f(backingBuffer, "backingBuffer");
            boolean z = true;
            if (backingBuffer.position() == 0) {
                if (backingBuffer.limit() != backingBuffer.capacity() ? false : z) {
                    ByteBuffer duplicate = backingBuffer.duplicate();
                    k.b(duplicate, "backingBuffer.duplicate()");
                    this.c = duplicate;
                    ByteBuffer duplicate2 = backingBuffer.duplicate();
                    k.b(duplicate2, "backingBuffer.duplicate()");
                    this.d = duplicate2;
                    this.e = new b(this);
                    this.f = new d(this);
                    this.g = new g(this);
                    this.h = new e(this);
                    return;
                }
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ c(ByteBuffer byteBuffer, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(byteBuffer, (i2 & 2) != 0 ? 8 : i);
        }

        @NotNull
        public ByteBuffer c() {
            return this.c;
        }

        @NotNull
        public ByteBuffer b() {
            return this.d;
        }

        @NotNull
        public final b h() {
            return this.e;
        }

        @NotNull
        public final d i() {
            return this.f;
        }

        @NotNull
        public final g k() {
            return this.g;
        }

        @NotNull
        public final e j() {
            return this.h;
        }

        @NotNull
        /* renamed from: l */
        public d d() {
            return this.f;
        }

        @NotNull
        /* renamed from: m */
        public g e() {
            return this.g;
        }

        public boolean a() {
            throw new IllegalStateException("Not available for initial state".toString());
        }

        @NotNull
        public String toString() {
            return "Initial";
        }
    }

    /* compiled from: ReadWriteBufferState.kt */
    public static final class b extends f {
        @NotNull
        private final c c;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull c initial) {
            super(initial.a, initial.b, (DefaultConstructorMarker) null);
            k.f(initial, "initial");
            this.c = initial;
        }

        @NotNull
        public final c h() {
            return this.c;
        }

        @NotNull
        /* renamed from: i */
        public d d() {
            return this.c.i();
        }

        @NotNull
        /* renamed from: j */
        public g e() {
            return this.c.k();
        }

        public boolean a() {
            return true;
        }

        @NotNull
        public String toString() {
            return "IDLE(with buffer)";
        }
    }

    /* compiled from: ReadWriteBufferState.kt */
    public static final class d extends f {
        private final c c;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public d(@NotNull c initial) {
            super(initial.a, initial.b, (DefaultConstructorMarker) null);
            k.f(initial, "initial");
            this.c = initial;
        }

        @NotNull
        public ByteBuffer b() {
            return this.c.b();
        }

        @NotNull
        /* renamed from: h */
        public e e() {
            return this.c.j();
        }

        @NotNull
        /* renamed from: i */
        public b f() {
            return this.c.h();
        }

        @NotNull
        public String toString() {
            return "Reading";
        }
    }

    /* compiled from: ReadWriteBufferState.kt */
    public static final class g extends f {
        private final c c;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public g(@NotNull c initial) {
            super(initial.a, initial.b, (DefaultConstructorMarker) null);
            k.f(initial, "initial");
            this.c = initial;
        }

        @NotNull
        public ByteBuffer c() {
            return this.c.c();
        }

        @NotNull
        /* renamed from: h */
        public e d() {
            return this.c.j();
        }

        @NotNull
        /* renamed from: i */
        public b g() {
            return this.c.h();
        }

        @NotNull
        public String toString() {
            return "Writing";
        }
    }

    /* compiled from: ReadWriteBufferState.kt */
    public static final class e extends f {
        private final c c;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public e(@NotNull c initial) {
            super(initial.a, initial.b, (DefaultConstructorMarker) null);
            k.f(initial, "initial");
            this.c = initial;
        }

        @NotNull
        public ByteBuffer b() {
            return this.c.b();
        }

        @NotNull
        public ByteBuffer c() {
            return this.c.c();
        }

        @NotNull
        /* renamed from: h */
        public g f() {
            return this.c.k();
        }

        @NotNull
        /* renamed from: i */
        public d g() {
            return this.c.i();
        }

        @NotNull
        public String toString() {
            return "Reading+Writing";
        }
    }

    /* renamed from: io.ktor.utils.io.internal.f$f  reason: collision with other inner class name */
    /* compiled from: ReadWriteBufferState.kt */
    public static final class C0290f extends f {
        public static final C0290f c = new C0290f();

        private C0290f() {
            super(g.a(), g.b(), (DefaultConstructorMarker) null);
        }

        @NotNull
        public String toString() {
            return "Terminated";
        }
    }
}
