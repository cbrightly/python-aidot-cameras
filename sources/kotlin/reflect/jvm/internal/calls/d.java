package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Caller.kt */
public interface d<M extends Member> {
    @NotNull
    List<Type> a();

    M b();

    @Nullable
    Object call(@NotNull Object[] objArr);

    @NotNull
    Type getReturnType();

    /* compiled from: Caller.kt */
    public static final class a {
        public static <M extends Member> void a(d<? extends M> $this, @NotNull Object[] args) {
            k.f(args, "args");
            if (f.a($this) != args.length) {
                throw new IllegalArgumentException("Callable expects " + f.a($this) + " arguments, but " + args.length + " were provided.");
            }
        }
    }
}
