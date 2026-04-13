package io.ktor.utils.io.internal;

import io.ktor.utils.io.a;
import io.ktor.utils.io.core.a0;
import io.ktor.utils.io.core.c;
import io.ktor.utils.io.x;
import java.nio.ByteBuffer;
import kotlin.coroutines.d;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReadSessionImpl.kt */
public final class e implements x {
    private int b;
    private a0 c = a0.I4.a();
    private final a d;

    public e(@NotNull a channel) {
        k.f(channel, "channel");
        this.d = channel;
    }

    public final void b() {
        c(a0.I4.a());
    }

    private final void c(a0 newView) {
        int i = this.b;
        c this_$iv = this.c;
        int delta = i - (this_$iv.s() - this_$iv.o());
        if (delta > 0) {
            this.d.w(delta);
        }
        this.c = newView;
        c this_$iv2 = newView;
        this.b = this_$iv2.s() - this_$iv2.o();
    }

    @Nullable
    public a0 a(int atLeast) {
        ByteBuffer it = this.d.a(0, atLeast);
        if (it == null) {
            return null;
        }
        a0 a0Var = new a0(it);
        a0 it2 = a0Var;
        it2.I();
        c(it2);
        return a0Var;
    }

    @Nullable
    public Object k(int atLeast, @NotNull d<? super Boolean> $completion) {
        b();
        return this.d.k(atLeast, $completion);
    }
}
