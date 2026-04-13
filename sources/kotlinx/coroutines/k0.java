package kotlinx.coroutines;

import java.util.List;
import kotlin.b;
import kotlin.coroutines.g;
import kotlin.l;
import kotlin.o;
import kotlin.p;
import kotlin.sequences.m;
import kotlin.sequences.o;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\u001a\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0000\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"handlers", "", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "handleCoroutineExceptionImpl", "", "context", "Lkotlin/coroutines/CoroutineContext;", "exception", "", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: CoroutineExceptionHandlerImpl.kt */
public final class k0 {
    @NotNull
    private static final List<j0> a = o.C(m.c(a.a()));

    public static final void a(@NotNull g context, @NotNull Throwable exception) {
        for (j0 handler : a) {
            try {
                handler.handleException(context, exception);
            } catch (Throwable t) {
                Thread currentThread = Thread.currentThread();
                currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, l0.b(exception, t));
            }
        }
        Thread currentThread2 = Thread.currentThread();
        try {
            o.a aVar = kotlin.o.Companion;
            b.a(exception, new DiagnosticCoroutineContextException(context));
            kotlin.o.m17constructorimpl(x.a);
        } catch (Throwable th) {
            o.a aVar2 = kotlin.o.Companion;
            kotlin.o.m17constructorimpl(p.a(th));
        }
        currentThread2.getUncaughtExceptionHandler().uncaughtException(currentThread2, exception);
    }
}
