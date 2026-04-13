package io.ktor.utils.io.core.internal;

import io.ktor.utils.io.core.a;
import io.ktor.utils.io.core.b;
import io.ktor.utils.io.core.c;
import io.ktor.utils.io.core.c0;
import io.ktor.utils.io.core.e0;
import io.ktor.utils.io.core.f0;
import io.ktor.utils.io.core.g0;
import io.ktor.utils.io.core.n;
import io.ktor.utils.io.core.q;
import io.ktor.utils.io.core.w;
import io.ktor.utils.io.core.y;
import io.ktor.utils.io.core.z;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Unsafe.kt */
public final class g {
    @NotNull
    public static final byte[] a = new byte[0];

    public static final void a(@NotNull q $this$_u24unsafeAppend_u24, @NotNull n builder) {
        k.f($this$_u24unsafeAppend_u24, "$this$_u24unsafeAppend_u24");
        k.f(builder, "builder");
        a builderHead = builder.P();
        if (builderHead == null) {
            return;
        }
        if (builder.f1() > f0.c() || builderHead.b1() != null || !$this$_u24unsafeAppend_u24.t1(builderHead)) {
            $this$_u24unsafeAppend_u24.c(builderHead);
        } else {
            builder.a();
        }
    }

    @Nullable
    public static final a g(@NotNull w $this$prepareReadFirstHead, int minSize) {
        k.f($this$prepareReadFirstHead, "$this$prepareReadFirstHead");
        if ($this$prepareReadFirstHead instanceof a) {
            return ((a) $this$prepareReadFirstHead).g1(minSize);
        }
        if (!($this$prepareReadFirstHead instanceof a)) {
            return h($this$prepareReadFirstHead, minSize);
        }
        c $this$canRead$iv = (c) $this$prepareReadFirstHead;
        if ($this$canRead$iv.s() > $this$canRead$iv.o()) {
            return (a) $this$prepareReadFirstHead;
        }
        return null;
    }

    private static final a h(@NotNull w $this$prepareReadHeadFallback, int minSize) {
        if ($this$prepareReadHeadFallback.w0()) {
            return null;
        }
        a buffer = a.z4.c().p0();
        c this_$iv = buffer;
        int copied = (int) $this$prepareReadHeadFallback.n0(buffer.n(), (long) buffer.s(), 0, (long) minSize, (long) (this_$iv.m() - this_$iv.s()));
        buffer.a(copied);
        if (copied >= minSize) {
            return buffer;
        }
        g0.a(minSize);
        throw null;
    }

    public static final void d(@NotNull w $this$completeReadHead, @NotNull a current) {
        k.f($this$completeReadHead, "$this$completeReadHead");
        k.f(current, "current");
        if (current != $this$completeReadHead) {
            if ($this$completeReadHead instanceof a) {
                c $this$canRead$iv = current;
                if (!($this$canRead$iv.s() > $this$canRead$iv.o())) {
                    ((a) $this$completeReadHead).u(current);
                    return;
                }
                c this_$iv = current;
                if (this_$iv.l() - this_$iv.m() < 8) {
                    ((a) $this$completeReadHead).I(current);
                } else {
                    ((a) $this$completeReadHead).o1(current.o());
                }
            } else {
                e($this$completeReadHead, current);
            }
        }
    }

    private static final void e(@NotNull w $this$completeReadHeadFallback, a current) {
        c this_$iv = current;
        y.a($this$completeReadHeadFallback, (current.l() - (this_$iv.m() - this_$iv.s())) - (this_$iv.s() - this_$iv.o()));
        current.e1(a.z4.c());
    }

    @Nullable
    public static final a i(@NotNull w $this$prepareReadNextHead, @NotNull a current) {
        k.f($this$prepareReadNextHead, "$this$prepareReadNextHead");
        k.f(current, "current");
        if (current == $this$prepareReadNextHead) {
            c $this$canRead$iv = (c) $this$prepareReadNextHead;
            if ($this$canRead$iv.s() > $this$canRead$iv.o()) {
                return (a) $this$prepareReadNextHead;
            }
            return null;
        } else if ($this$prepareReadNextHead instanceof a) {
            return ((a) $this$prepareReadNextHead).x(current);
        } else {
            return f($this$prepareReadNextHead, current);
        }
    }

    private static final a f(@NotNull w $this$prepareNextReadHeadFallback, a current) {
        c this_$iv = current;
        y.a($this$prepareNextReadHeadFallback, (current.l() - (this_$iv.m() - this_$iv.s())) - (this_$iv.s() - this_$iv.o()));
        current.J();
        if (!$this$prepareNextReadHeadFallback.w0() && z.b($this$prepareNextReadHeadFallback, current, 0, 0, 0, 14, (Object) null) > 0) {
            return current;
        }
        current.e1(a.z4.c());
        return null;
    }

    @NotNull
    public static final a j(@NotNull c0 $this$prepareWriteHead, int capacity, @Nullable a current) {
        k.f($this$prepareWriteHead, "$this$prepareWriteHead");
        if (!($this$prepareWriteHead instanceof b)) {
            return k($this$prepareWriteHead, current);
        }
        if (current != null) {
            ((b) $this$prepareWriteHead).c();
        }
        return ((b) $this$prepareWriteHead).J(capacity);
    }

    private static final a k(@NotNull c0 $this$prepareWriteHeadFallback, a current) {
        if (current == null) {
            return a.z4.c().p0();
        }
        e0.c($this$prepareWriteHeadFallback, current, 0, 2, (Object) null);
        current.J();
        return current;
    }

    public static final void b(@NotNull c0 $this$afterHeadWrite, @NotNull a current) {
        k.f($this$afterHeadWrite, "$this$afterHeadWrite");
        k.f(current, "current");
        if ($this$afterHeadWrite instanceof b) {
            ((b) $this$afterHeadWrite).c();
        } else {
            c($this$afterHeadWrite, current);
        }
    }

    private static final void c(@NotNull c0 $this$afterWriteHeadFallback, a current) {
        e0.c($this$afterWriteHeadFallback, current, 0, 2, (Object) null);
        current.e1(a.z4.c());
    }
}
