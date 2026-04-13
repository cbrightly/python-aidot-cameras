package coil.transition;

import coil.request.g;
import coil.request.j;
import coil.request.m;
import kotlin.coroutines.d;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NoneTransition.kt */
public final class a implements b {
    @NotNull
    public static final a c = new a();

    private a() {
    }

    @Nullable
    public Object a(@NotNull c target, @NotNull j result, @NotNull d<? super x> $completion) {
        if (result instanceof m) {
            target.a(((m) result).a());
        } else if (result instanceof g) {
            target.c(result.a());
        }
        return x.a;
    }

    @NotNull
    public String toString() {
        return "coil.transition.NoneTransition";
    }
}
