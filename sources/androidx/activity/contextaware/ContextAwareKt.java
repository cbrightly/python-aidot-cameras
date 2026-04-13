package androidx.activity.contextaware;

import android.content.Context;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.b;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.j;
import kotlinx.coroutines.n;
import kotlinx.coroutines.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ContextAware.kt */
public final class ContextAwareKt {
    @Nullable
    public static final <R> Object withContextAvailable(@NotNull ContextAware $this$withContextAvailable, @NotNull l<? super Context, ? extends R> onContextAvailable, @NotNull d<? super R> $completion) {
        Context availableContext = $this$withContextAvailable.peekAvailableContext();
        if (availableContext != null) {
            return onContextAvailable.invoke(availableContext);
        }
        o cancellable$iv = new o(b.c($completion), 1);
        cancellable$iv.w();
        n co = cancellable$iv;
        ContextAwareKt$withContextAvailable$2$listener$1 listener = new ContextAwareKt$withContextAvailable$2$listener$1(co, onContextAvailable);
        $this$withContextAvailable.addOnContextAvailableListener(listener);
        co.f(new ContextAwareKt$withContextAvailable$2$1($this$withContextAvailable, listener));
        Object t = cancellable$iv.t();
        if (t == c.d()) {
            h.c($completion);
        }
        return t;
    }

    private static final <R> Object withContextAvailable$$forInline(ContextAware $this$withContextAvailable, l<? super Context, ? extends R> onContextAvailable, d<? super R> $completion) {
        Context availableContext = $this$withContextAvailable.peekAvailableContext();
        if (availableContext != null) {
            return onContextAvailable.invoke(availableContext);
        }
        j.c(0);
        o cancellable$iv = new o(b.c($completion), 1);
        cancellable$iv.w();
        n co = cancellable$iv;
        ContextAwareKt$withContextAvailable$2$listener$1 listener = new ContextAwareKt$withContextAvailable$2$listener$1(co, onContextAvailable);
        $this$withContextAvailable.addOnContextAvailableListener(listener);
        co.f(new ContextAwareKt$withContextAvailable$2$1($this$withContextAvailable, listener));
        Object t = cancellable$iv.t();
        if (t == c.d()) {
            h.c($completion);
        }
        j.c(1);
        return t;
    }
}
