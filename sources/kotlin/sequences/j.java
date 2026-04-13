package kotlin.sequences;

import java.util.Iterator;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.c;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SequenceBuilder.kt */
public abstract class j<T> {
    @Nullable
    public abstract Object g(T t, @NotNull d<? super x> dVar);

    @Nullable
    public abstract Object i(@NotNull Iterator<? extends T> it, @NotNull d<? super x> dVar);

    @Nullable
    public final Object j(@NotNull h<? extends T> sequence, @NotNull d<? super x> $completion) {
        Object i = i(sequence.iterator(), $completion);
        return i == c.d() ? i : x.a;
    }
}
