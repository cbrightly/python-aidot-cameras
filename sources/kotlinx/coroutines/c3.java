package kotlinx.coroutines;

import kotlin.coroutines.g;
import kotlin.jvm.functions.p;
import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bÂ\u0002\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00000\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003R\u0018\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u00028VX\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lkotlinx/coroutines/UndispatchedMarker;", "Lkotlin/coroutines/CoroutineContext$Element;", "Lkotlin/coroutines/CoroutineContext$Key;", "()V", "key", "getKey", "()Lkotlin/coroutines/CoroutineContext$Key;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: CoroutineContext.kt */
public final class c3 implements g.b, g.c<c3> {
    @NotNull
    public static final c3 c = new c3();

    private c3() {
    }

    public <R> R fold(R initial, @NotNull p<? super R, ? super g.b, ? extends R> operation) {
        return g.b.a.a(this, initial, operation);
    }

    @Nullable
    public <E extends g.b> E get(@NotNull g.c<E> key) {
        return g.b.a.b(this, key);
    }

    @NotNull
    public g minusKey(@NotNull g.c<?> key) {
        return g.b.a.c(this, key);
    }

    @NotNull
    public g plus(@NotNull g context) {
        return g.b.a.d(this, context);
    }

    @NotNull
    public g.c<?> getKey() {
        return this;
    }
}
