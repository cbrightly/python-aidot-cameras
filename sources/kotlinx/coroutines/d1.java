package kotlinx.coroutines;

import kotlin.l;
import kotlinx.coroutines.internal.w;
import kotlinx.coroutines.scheduling.b;
import kotlinx.coroutines.scheduling.c;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0007R\u001c\u0010\u0003\u001a\u00020\u00048\u0006X\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007R\u001c\u0010\b\u001a\u00020\u00048\u0006X\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\t\u0010\u0002\u001a\u0004\b\n\u0010\u0007R\u001a\u0010\u000b\u001a\u00020\f8FX\u0004¢\u0006\f\u0012\u0004\b\r\u0010\u0002\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u00020\u00048\u0006X\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0011\u0010\u0002\u001a\u0004\b\u0012\u0010\u0007¨\u0006\u0015"}, d2 = {"Lkotlinx/coroutines/Dispatchers;", "", "()V", "Default", "Lkotlinx/coroutines/CoroutineDispatcher;", "getDefault$annotations", "getDefault", "()Lkotlinx/coroutines/CoroutineDispatcher;", "IO", "getIO$annotations", "getIO", "Main", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "getMain$annotations", "getMain", "()Lkotlinx/coroutines/MainCoroutineDispatcher;", "Unconfined", "getUnconfined$annotations", "getUnconfined", "shutdown", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: Dispatchers.kt */
public final class d1 {
    @NotNull
    public static final d1 a = new d1();
    @NotNull
    private static final i0 b = c.z;
    @NotNull
    private static final i0 c = a3.c;
    @NotNull
    private static final i0 d = b.d;

    private d1() {
    }

    @NotNull
    public static final i0 a() {
        return b;
    }

    @NotNull
    public static final k2 c() {
        return w.c;
    }

    @NotNull
    public static final i0 d() {
        return c;
    }

    @NotNull
    public static final i0 b() {
        return d;
    }
}
