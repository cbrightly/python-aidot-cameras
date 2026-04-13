package io.ktor.utils.io.core;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.ktor.utils.io.bits.c;
import io.ktor.utils.io.core.internal.a;
import io.ktor.utils.io.pool.d;
import java.nio.ByteBuffer;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Buffers.kt */
public final class j {
    public static final void f(@NotNull a0 $this$releaseImpl, @NotNull d<a0> pool) {
        k.f($this$releaseImpl, "$this$releaseImpl");
        k.f(pool, "pool");
        if ($this$releaseImpl.f1()) {
            a origin = $this$releaseImpl.c1();
            if (origin instanceof a0) {
                $this$releaseImpl.h1();
                ((a0) origin).e1(pool);
                return;
            }
            pool.N0($this$releaseImpl);
        }
    }

    public static final void e(@Nullable a $this$releaseAll, @NotNull d<a> pool) {
        while (true) {
            k.f(pool, "pool");
            if ($this$releaseAll != null) {
                a next = $this$releaseAll.P0();
                $this$releaseAll.e1(pool);
                $this$releaseAll = next;
            } else {
                return;
            }
        }
    }

    @NotNull
    public static final a a(@NotNull a $this$copyAll) {
        k.f($this$copyAll, "$this$copyAll");
        a copied = $this$copyAll.a1();
        a next = $this$copyAll.b1();
        if (next != null) {
            return b(next, copied, copied);
        }
        return copied;
    }

    private static final a b(@NotNull a $this$copyAll, a head, a prev) {
        while (true) {
            a copied = $this$copyAll.a1();
            prev.g1(copied);
            a next = $this$copyAll.b1();
            if (next == null) {
                return head;
            }
            prev = copied;
            $this$copyAll = next;
        }
    }

    @NotNull
    public static final a c(@NotNull a $this$findTail) {
        while (true) {
            k.f($this$findTail, "$this$findTail");
            a next = $this$findTail.b1();
            if (next == null) {
                return $this$findTail;
            }
            $this$findTail = next;
        }
    }

    public static final long g(@NotNull a $this$remainingAll) {
        k.f($this$remainingAll, "$this$remainingAll");
        return h($this$remainingAll, 0);
    }

    private static final long h(@NotNull a $this$remainingAll, long n) {
        while (true) {
            c this_$iv = $this$remainingAll;
            long rem = ((long) (this_$iv.s() - this_$iv.o())) + n;
            a next = $this$remainingAll.b1();
            if (next == null) {
                return rem;
            }
            n = rem;
            $this$remainingAll = next;
        }
    }

    public static final long d(@NotNull c $this$peekTo, @NotNull ByteBuffer destination, long destinationOffset, long offset, long min, long max) {
        c cVar = $this$peekTo;
        k.f($this$peekTo, "$this$peekTo");
        k.f(destination, FirebaseAnalytics.Param.DESTINATION);
        c this_$iv = $this$peekTo;
        long size = Math.min(((long) destination.limit()) - destinationOffset, Math.min(max, (long) (this_$iv.s() - this_$iv.o())));
        c.d($this$peekTo.n(), destination, ((long) $this$peekTo.o()) + offset, size, destinationOffset);
        return size;
    }
}
