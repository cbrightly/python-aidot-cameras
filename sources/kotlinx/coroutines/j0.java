package kotlinx.coroutines;

import kotlin.coroutines.g;
import kotlin.jvm.functions.p;
import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\bf\u0018\u0000 \b2\u00020\u0001:\u0001\bJ\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\t"}, d2 = {"Lkotlinx/coroutines/CoroutineExceptionHandler;", "Lkotlin/coroutines/CoroutineContext$Element;", "handleException", "", "context", "Lkotlin/coroutines/CoroutineContext;", "exception", "", "Key", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: CoroutineExceptionHandler.kt */
public interface j0 extends g.b {
    @NotNull
    public static final b e = b.c;

    void handleException(@NotNull g gVar, @NotNull Throwable th);

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: CoroutineExceptionHandler.kt */
    public static final class a {
        public static <R> R a(@NotNull j0 j0Var, R initial, @NotNull p<? super R, ? super g.b, ? extends R> operation) {
            return g.b.a.a(j0Var, initial, operation);
        }

        @Nullable
        public static <E extends g.b> E b(@NotNull j0 j0Var, @NotNull g.c<E> key) {
            return g.b.a.b(j0Var, key);
        }

        @NotNull
        public static g c(@NotNull j0 j0Var, @NotNull g.c<?> key) {
            return g.b.a.c(j0Var, key);
        }

        @NotNull
        public static g d(@NotNull j0 j0Var, @NotNull g context) {
            return g.b.a.d(j0Var, context);
        }
    }

    @l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lkotlinx/coroutines/CoroutineExceptionHandler$Key;", "Lkotlin/coroutines/CoroutineContext$Key;", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "()V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: CoroutineExceptionHandler.kt */
    public static final class b implements g.c<j0> {
        static final /* synthetic */ b c = new b();

        private b() {
        }
    }
}
