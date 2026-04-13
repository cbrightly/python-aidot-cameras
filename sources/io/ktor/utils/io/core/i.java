package io.ktor.utils.io.core;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.EOFException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.z;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: BufferPrimitives.kt */
public final class i {

    /* compiled from: Require.kt */
    public static final class a extends io.ktor.utils.io.core.internal.e {
        final /* synthetic */ int a;
        final /* synthetic */ String b;
        final /* synthetic */ z c;
        final /* synthetic */ byte[] d;
        final /* synthetic */ int e;
        final /* synthetic */ int f;

        public a(int i, String str, z zVar, byte[] bArr, int i2, int i3) {
            this.a = i;
            this.b = str;
            this.c = zVar;
            this.d = bArr;
            this.e = i2;
            this.f = i3;
        }

        @NotNull
        public Void a() {
            throw new EOFException("Not enough bytes to read a " + this.b + " of size " + this.a + '.');
        }
    }

    /* compiled from: Require.kt */
    public static final class b extends io.ktor.utils.io.core.internal.e {
        final /* synthetic */ int a;
        final /* synthetic */ String b;
        final /* synthetic */ z c;
        final /* synthetic */ c d;
        final /* synthetic */ int e;

        public b(int i, String str, z zVar, c cVar, int i2) {
            this.a = i;
            this.b = str;
            this.c = zVar;
            this.d = cVar;
            this.e = i2;
        }

        @NotNull
        public Void a() {
            throw new EOFException("Not enough bytes to read a " + this.b + " of size " + this.a + '.');
        }
    }

    /* compiled from: Require.kt */
    public static final class c extends io.ktor.utils.io.core.internal.e {
        final /* synthetic */ int a;

        public c(int i) {
            this.a = i;
        }

        @NotNull
        public Void a() {
            throw new IllegalArgumentException("length shouldn't be negative: " + this.a);
        }
    }

    /* compiled from: Require.kt */
    public static final class d extends io.ktor.utils.io.core.internal.e {
        final /* synthetic */ int a;
        final /* synthetic */ c b;

        public d(int i, c cVar) {
            this.a = i;
            this.b = cVar;
        }

        @NotNull
        public Void a() {
            StringBuilder sb = new StringBuilder();
            sb.append("length shouldn't be greater than the source read remaining: ");
            sb.append(this.a);
            sb.append(" > ");
            c this_$iv = this.b;
            sb.append(this_$iv.s() - this_$iv.o());
            throw new IllegalArgumentException(sb.toString());
        }
    }

    /* compiled from: Require.kt */
    public static final class e extends io.ktor.utils.io.core.internal.e {
        final /* synthetic */ c a;
        final /* synthetic */ int b;

        public e(c cVar, int i) {
            this.a = cVar;
            this.b = i;
        }

        @NotNull
        public Void a() {
            StringBuilder sb = new StringBuilder();
            sb.append("length shouldn't be greater than the destination write remaining space: ");
            sb.append(this.b);
            sb.append(" > ");
            c this_$iv = this.a;
            sb.append(this_$iv.m() - this_$iv.s());
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static final void e(@NotNull c $this$writeShort, short value) {
        k.f($this$writeShort, "$this$writeShort");
        c $this$write$iv$iv = $this$writeShort;
        ByteBuffer memory$iv = $this$write$iv$iv.n();
        int start$iv = $this$write$iv$iv.s();
        int writeRemaining$iv = $this$write$iv$iv.m() - start$iv;
        if (writeRemaining$iv >= 2) {
            memory$iv.putShort(start$iv, value);
            $this$write$iv$iv.a(2);
            return;
        }
        throw new InsufficientSpaceException("short integer", 2, writeRemaining$iv);
    }

    public static final void b(@NotNull c $this$readFully, @NotNull byte[] destination, int offset, int length) {
        k.f($this$readFully, "$this$readFully");
        k.f(destination, FirebaseAnalytics.Param.DESTINATION);
        c $this$readExact$iv = $this$readFully;
        z value$iv = new z();
        c $this$read$iv$iv = $this$readExact$iv;
        ByteBuffer memory$iv = $this$read$iv$iv.n();
        int start$iv = $this$read$iv$iv.o();
        ByteBuffer memory$iv2 = memory$iv;
        if ($this$read$iv$iv.s() - start$iv >= length) {
            c cVar = $this$readExact$iv;
            io.ktor.utils.io.bits.d.a(memory$iv2, destination, start$iv, length, offset);
            value$iv.element = x.a;
            $this$read$iv$iv.g(length);
            return;
        }
        c cVar2 = $this$readExact$iv;
        new a(length, "byte array", value$iv, destination, offset, length).a();
        throw null;
    }

    public static final void d(@NotNull c $this$writeFully, @NotNull byte[] source, int offset, int length) {
        int i = length;
        k.f($this$writeFully, "$this$writeFully");
        k.f(source, "source");
        c $this$writeExact$iv = $this$writeFully;
        c $this$write$iv$iv = $this$writeExact$iv;
        ByteBuffer memory$iv = $this$write$iv$iv.n();
        int start$iv = $this$write$iv$iv.s();
        int writeRemaining$iv = $this$write$iv$iv.m() - start$iv;
        if (writeRemaining$iv >= i) {
            int sourceOffset$iv = offset;
            int count$iv = length;
            c cVar = $this$writeExact$iv;
            byte[] $this$useMemory$iv$iv = source;
            int i2 = sourceOffset$iv;
            byte[] bArr = $this$useMemory$iv$iv;
            ByteBuffer order = ByteBuffer.wrap($this$useMemory$iv$iv, sourceOffset$iv, count$iv).slice().order(ByteOrder.BIG_ENDIAN);
            k.b(order, "ByteBuffer.wrap(this, of…der(ByteOrder.BIG_ENDIAN)");
            io.ktor.utils.io.bits.c.c(io.ktor.utils.io.bits.c.b(order), memory$iv, 0, count$iv, start$iv);
            $this$write$iv$iv.a(length);
            return;
        }
        throw new InsufficientSpaceException("byte array", i, writeRemaining$iv);
    }

    public static final int a(@NotNull c $this$readFully, @NotNull c dst, int length) {
        int i = length;
        k.f($this$readFully, "$this$readFully");
        k.f(dst, "dst");
        boolean condition$iv$iv = true;
        if (i >= 0) {
            c this_$iv = dst;
            if (i <= this_$iv.m() - this_$iv.s()) {
                z value$iv = new z();
                c $this$read$iv$iv = $this$readFully;
                ByteBuffer memory$iv = $this$read$iv$iv.n();
                int start$iv = $this$read$iv$iv.o();
                if ($this$read$iv$iv.s() - start$iv < i) {
                    condition$iv$iv = false;
                }
                if (condition$iv$iv) {
                    io.ktor.utils.io.bits.c.c(memory$iv, dst.n(), start$iv, i, dst.s());
                    dst.a(length);
                    value$iv.element = x.a;
                    $this$read$iv$iv.g(length);
                    return i;
                }
                new b(length, "buffer content", value$iv, dst, length).a();
                throw null;
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    public static final void c(@NotNull c $this$writeFully, @NotNull c src, int length) {
        c cVar = $this$writeFully;
        c cVar2 = src;
        int i = length;
        k.f(cVar, "$this$writeFully");
        k.f(cVar2, "src");
        boolean condition$iv = true;
        if (i >= 0) {
            c this_$iv = src;
            if (i <= this_$iv.s() - this_$iv.o()) {
                c this_$iv2 = $this$writeFully;
                if (i > this_$iv2.m() - this_$iv2.s()) {
                    condition$iv = false;
                }
                if (condition$iv) {
                    c $this$writeExact$iv = $this$writeFully;
                    c $this$write$iv$iv = $this$writeExact$iv;
                    ByteBuffer memory$iv = $this$write$iv$iv.n();
                    int start$iv = $this$write$iv$iv.s();
                    int writeRemaining$iv = $this$write$iv$iv.m() - start$iv;
                    if (writeRemaining$iv >= i) {
                        c cVar3 = $this$writeExact$iv;
                        io.ktor.utils.io.bits.c.c(src.n(), memory$iv, src.o(), i, start$iv);
                        src.g(length);
                        $this$write$iv$iv.a(length);
                        return;
                    }
                    c cVar4 = $this$writeExact$iv;
                    throw new InsufficientSpaceException("buffer readable content", i, writeRemaining$iv);
                }
                new e(cVar, i).a();
                throw null;
            }
            new d(i, cVar2).a();
            throw null;
        }
        new c(i).a();
        throw null;
    }
}
