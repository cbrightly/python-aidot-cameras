package kotlinx.coroutines;

import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.jvm.functions.p;
import kotlin.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"kotlinx/coroutines/BuildersKt__BuildersKt", "kotlinx/coroutines/BuildersKt__Builders_commonKt"}, k = 4, mv = {1, 6, 0}, xi = 48)
public final class h {
    @NotNull
    public static final <T> w0<T> a(@NotNull o0 $this$async, @NotNull g context, @NotNull q0 start, @NotNull p<? super o0, ? super d<? super T>, ? extends Object> block) {
        return j.a($this$async, context, start, block);
    }

    @NotNull
    public static final z1 c(@NotNull o0 $this$launch, @NotNull g context, @NotNull q0 start, @NotNull p<? super o0, ? super d<? super x>, ? extends Object> block) {
        return j.c($this$launch, context, start, block);
    }

    public static final <T> T e(@NotNull g context, @NotNull p<? super o0, ? super d<? super T>, ? extends Object> block) {
        return i.a(context, block);
    }

    @Nullable
    public static final <T> Object g(@NotNull g context, @NotNull p<? super o0, ? super d<? super T>, ? extends Object> block, @NotNull d<? super T> $completion) {
        return j.e(context, block, $completion);
    }
}
