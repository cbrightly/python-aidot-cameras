package kotlinx.coroutines;

import kotlin.l;
import kotlinx.coroutines.internal.g0;
import kotlinx.coroutines.internal.x;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\b\u0010\u0006\u001a\u00020\u0001H\u0002\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"DefaultDelay", "Lkotlinx/coroutines/Delay;", "getDefaultDelay", "()Lkotlinx/coroutines/Delay;", "defaultMainDelayOptIn", "", "initializeDefaultDelay", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: DefaultExecutor.kt */
public final class v0 {
    private static final boolean a = g0.e("kotlinx.coroutines.main.delay", false);
    @NotNull
    private static final y0 b = b();

    @NotNull
    public static final y0 a() {
        return b;
    }

    private static final y0 b() {
        if (!a) {
            return u0.y;
        }
        k2 main = d1.c();
        return (x.c(main) || !(main instanceof y0)) ? u0.y : (y0) main;
    }
}
