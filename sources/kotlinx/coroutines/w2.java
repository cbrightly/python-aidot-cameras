package kotlinx.coroutines;

import kotlin.coroutines.g;
import kotlin.jvm.functions.p;
import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u001d\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000H&¢\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u0006H&¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"Lkotlinx/coroutines/ThreadContextElement;", "S", "Lkotlin/coroutines/CoroutineContext$Element;", "restoreThreadContext", "", "context", "Lkotlin/coroutines/CoroutineContext;", "oldState", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V", "updateThreadContext", "(Lkotlin/coroutines/CoroutineContext;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: ThreadContextElement.kt */
public interface w2<S> extends g.b {
    S P(@NotNull g gVar);

    void v(@NotNull g gVar, S s);

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: ThreadContextElement.kt */
    public static final class a {
        public static <S, R> R a(@NotNull w2<S> w2Var, R initial, @NotNull p<? super R, ? super g.b, ? extends R> operation) {
            return g.b.a.a(w2Var, initial, operation);
        }

        @Nullable
        public static <S, E extends g.b> E b(@NotNull w2<S> w2Var, @NotNull g.c<E> key) {
            return g.b.a.b(w2Var, key);
        }

        @NotNull
        public static <S> g c(@NotNull w2<S> w2Var, @NotNull g.c<?> key) {
            return g.b.a.c(w2Var, key);
        }

        @NotNull
        public static <S> g d(@NotNull w2<S> w2Var, @NotNull g context) {
            return g.b.a.d(w2Var, context);
        }
    }
}
