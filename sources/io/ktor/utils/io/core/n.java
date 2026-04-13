package io.ktor.utils.io.core;

import io.ktor.utils.io.core.internal.e;
import io.ktor.utils.io.pool.d;
import java.nio.ByteBuffer;
import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BytePacketBuilder.kt */
public final class n extends p {
    /* access modifiers changed from: private */
    public int a2;

    /* compiled from: Require.kt */
    public static final class a extends e {
        final /* synthetic */ n a;

        public a(n nVar) {
            this.a = nVar;
        }

        @NotNull
        public Void a() {
            throw new IllegalArgumentException("shouldn't be negative: headerSizeHint = " + this.a.a2);
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public n(int headerSizeHint, @NotNull d<io.ktor.utils.io.core.internal.a> pool) {
        super(pool);
        k.f(pool, "pool");
        this.a2 = headerSizeHint;
        if (!(headerSizeHint >= 0)) {
            new a(this).a();
            throw null;
        }
    }

    public final int f1() {
        return I();
    }

    public final boolean g1() {
        return I() > 0;
    }

    /* access modifiers changed from: protected */
    public final void s() {
    }

    /* access modifiers changed from: protected */
    public final void t(@NotNull ByteBuffer source, int offset, int length) {
        k.f(source, "source");
    }

    @NotNull
    /* renamed from: b1 */
    public n g(char c) {
        b g = super.append(c);
        if (g != null) {
            return (n) g;
        }
        throw new TypeCastException("null cannot be cast to non-null type io.ktor.utils.io.core.BytePacketBuilder");
    }

    @NotNull
    /* renamed from: c1 */
    public n i(@Nullable CharSequence csq) {
        b i = super.append(csq);
        if (i != null) {
            return (n) i;
        }
        throw new TypeCastException("null cannot be cast to non-null type io.ktor.utils.io.core.BytePacketBuilder");
    }

    @NotNull
    /* renamed from: d1 */
    public n j(@Nullable CharSequence csq, int start, int end) {
        b j = super.append(csq, start, end);
        if (j != null) {
            return (n) j;
        }
        throw new TypeCastException("null cannot be cast to non-null type io.ktor.utils.io.core.BytePacketBuilder");
    }

    @NotNull
    public final q e1() {
        int size = f1();
        io.ktor.utils.io.core.internal.a head = P();
        if (head == null) {
            return q.p1.a();
        }
        return new q(head, (long) size, x());
    }

    @NotNull
    public String toString() {
        return "BytePacketBuilder(" + f1() + " bytes written)";
    }
}
