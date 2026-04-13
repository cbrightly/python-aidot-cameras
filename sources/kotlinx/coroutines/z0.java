package kotlinx.coroutines;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import kotlin.coroutines.d;
import kotlin.coroutines.e;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.b;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.h;
import kotlin.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0011\u0010\u0005\u001a\u00020\u0006H@ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a\u0019\u0010\u0000\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a!\u0010\u0000\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH@ø\u0001\u0000ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u000e\u0010\u000b\u001a\u0019\u0010\u000f\u001a\u00020\n*\u00020\rH\u0000ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0010\u0010\u0011\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u0002\u000b\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001¨\u0006\u0012"}, d2 = {"delay", "Lkotlinx/coroutines/Delay;", "Lkotlin/coroutines/CoroutineContext;", "getDelay", "(Lkotlin/coroutines/CoroutineContext;)Lkotlinx/coroutines/Delay;", "awaitCancellation", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "", "timeMillis", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "duration", "Lkotlin/time/Duration;", "delay-VtjQ1oo", "toDelayMillis", "toDelayMillis-LRDsOJo", "(J)J", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: Delay.kt */
public final class z0 {
    @Nullable
    public static final Object a(long timeMillis, @NotNull d<? super x> $completion) {
        if (timeMillis <= 0) {
            return x.a;
        }
        o cancellable$iv = new o(b.c($completion), 1);
        cancellable$iv.w();
        n cont = cancellable$iv;
        if (timeMillis < DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
            b(cont.getContext()).j(timeMillis, cont);
        }
        Object t = cancellable$iv.t();
        if (t == c.d()) {
            h.c($completion);
        }
        return t == c.d() ? t : x.a;
    }

    @NotNull
    public static final y0 b(@NotNull g $this$delay) {
        g.b bVar = $this$delay.get(e.a);
        y0 y0Var = bVar instanceof y0 ? (y0) bVar : null;
        return y0Var == null ? v0.a() : y0Var;
    }
}
