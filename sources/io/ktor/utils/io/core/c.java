package io.ktor.utils.io.core;

import java.io.EOFException;
import java.nio.ByteBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Buffer.kt */
public class c {
    public static final a c = new a((DefaultConstructorMarker) null);
    private int d;
    private int f;
    @NotNull
    private final ByteBuffer p0;
    private int q;
    private int x;
    private final int y;
    @Nullable
    private Object z;

    private c(ByteBuffer memory) {
        this.p0 = memory;
        this.x = this.p0.limit();
        this.y = this.p0.limit();
    }

    public /* synthetic */ c(ByteBuffer memory, DefaultConstructorMarker $constructor_marker) {
        this(memory);
    }

    @NotNull
    public final ByteBuffer n() {
        return this.p0;
    }

    public final int o() {
        return this.d;
    }

    public final int s() {
        return this.f;
    }

    public final int r() {
        return this.q;
    }

    public final int m() {
        return this.x;
    }

    public final int l() {
        return this.y;
    }

    public final void T(@Nullable Object obj) {
        this.z = obj;
    }

    public final void g(int count) {
        if (count != 0) {
            int newReadPosition = this.d + count;
            if (count < 0 || newReadPosition > this.f) {
                g.b(count, s() - o());
                throw null;
            }
            this.d = newReadPosition;
        }
    }

    public final long C0(long count) {
        int size = (int) Math.min(count, (long) (s() - o()));
        g(size);
        return (long) size;
    }

    public final void a(int count) {
        int newWritePosition = this.f + count;
        if (count < 0 || newWritePosition > this.x) {
            g.a(count, m() - s());
            throw null;
        }
        this.f = newWritePosition;
    }

    public final boolean c(int position) {
        int limit = this.x;
        int i = this.f;
        if (position < i) {
            g.a(position - i, m() - s());
            throw null;
        } else if (position < limit) {
            this.f = position;
            return true;
        } else if (position == limit) {
            this.f = position;
            return false;
        } else {
            g.a(position - i, m() - s());
            throw null;
        }
    }

    public final void i(int position) {
        if (position < 0 || position > this.f) {
            g.b(position - this.d, s() - o());
            throw null;
        } else if (this.d != position) {
            this.d = position;
        }
    }

    public final void z(int startGap) {
        if (startGap >= 0) {
            int i = this.d;
            if (i >= startGap) {
                this.q = startGap;
            } else if (i != this.f) {
                g.g(this, startGap);
                throw null;
            } else if (startGap <= this.x) {
                this.f = startGap;
                this.d = startGap;
                this.q = startGap;
            } else {
                g.h(this, startGap);
                throw null;
            }
        } else {
            throw new IllegalArgumentException(("startGap shouldn't be negative: " + startGap).toString());
        }
    }

    public final void x(int endGap) {
        if (endGap >= 0) {
            int newLimit = this.y - endGap;
            if (newLimit >= this.f) {
                this.x = newLimit;
                return;
            }
            if (newLimit < 0) {
                g.c(this, endGap);
            }
            if (newLimit < this.q) {
                g.e(this, endGap);
            }
            if (this.d == this.f) {
                this.x = newLimit;
                this.d = newLimit;
                this.f = newLimit;
                return;
            }
            g.d(this, endGap);
            return;
        }
        throw new IllegalArgumentException(("endGap shouldn't be negative: " + endGap).toString());
    }

    public final void I() {
        this.q = 0;
        this.d = 0;
        this.f = this.y;
    }

    public final void J() {
        P(this.y - this.q);
    }

    public final void P(int limit) {
        int startGap = this.q;
        this.d = startGap;
        this.f = startGap;
        this.x = limit;
    }

    public final void u() {
        v(0);
        t();
    }

    public final void t() {
        this.x = this.y;
    }

    public final void v(int newReadPosition) {
        boolean z2 = true;
        if (newReadPosition >= 0) {
            if (newReadPosition > this.d) {
                z2 = false;
            }
            if (z2) {
                this.d = newReadPosition;
                if (this.q > newReadPosition) {
                    this.q = newReadPosition;
                    return;
                }
                return;
            }
            throw new IllegalArgumentException(("newReadPosition shouldn't be ahead of the read position: " + newReadPosition + " > " + this.d).toString());
        }
        throw new IllegalArgumentException(("newReadPosition shouldn't be negative: " + newReadPosition).toString());
    }

    /* access modifiers changed from: protected */
    public void j(@NotNull c copy) {
        k.f(copy, "copy");
        copy.x = this.x;
        copy.q = this.q;
        copy.d = this.d;
        copy.f = this.f;
    }

    public final byte readByte() {
        int readPosition = this.d;
        if (readPosition != this.f) {
            this.d = readPosition + 1;
            return this.p0.get(readPosition);
        }
        throw new EOFException("No readable bytes available.");
    }

    public final void writeByte(byte value) {
        int writePosition = this.f;
        if (writePosition != this.x) {
            this.p0.put(writePosition, value);
            this.f = writePosition + 1;
            return;
        }
        throw new InsufficientSpaceException("No free space in the buffer to write a byte");
    }

    public void E() {
        u();
        J();
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Buffer(");
        sb.append(s() - o());
        sb.append(" used, ");
        sb.append(m() - s());
        sb.append(" free, ");
        sb.append(this.q + (l() - m()));
        sb.append(" reserved of ");
        sb.append(this.y);
        sb.append(')');
        return sb.toString();
    }

    /* compiled from: Buffer.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final c a() {
            return a0.I4.a();
        }
    }
}
