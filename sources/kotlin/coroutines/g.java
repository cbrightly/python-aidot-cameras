package kotlin.coroutines;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.coroutines.e;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CoroutineContext.kt */
public interface g {

    /* compiled from: CoroutineContext.kt */
    public interface c<E extends b> {
    }

    <R> R fold(R r, @NotNull p<? super R, ? super b, ? extends R> pVar);

    @Nullable
    <E extends b> E get(@NotNull c<E> cVar);

    @NotNull
    g minusKey(@NotNull c<?> cVar);

    @NotNull
    g plus(@NotNull g gVar);

    /* compiled from: CoroutineContext.kt */
    public static final class a {
        @NotNull
        public static g a(@NotNull g $this, @NotNull g context) {
            k.e(context, "context");
            if (context == h.INSTANCE) {
                return $this;
            }
            return (g) context.fold($this, C0322a.INSTANCE);
        }

        /* renamed from: kotlin.coroutines.g$a$a  reason: collision with other inner class name */
        /* compiled from: CoroutineContext.kt */
        public static final class C0322a extends l implements p<g, b, g> {
            public static final C0322a INSTANCE = new C0322a();

            C0322a() {
                super(2);
            }

            @NotNull
            public final g invoke(@NotNull g acc, @NotNull b element) {
                k.e(acc, "acc");
                k.e(element, "element");
                g removed = acc.minusKey(element.getKey());
                h hVar = h.INSTANCE;
                if (removed == hVar) {
                    return element;
                }
                e.b bVar = e.a;
                e interceptor = (e) removed.get(bVar);
                if (interceptor == null) {
                    return new c(removed, element);
                }
                g left = removed.minusKey(bVar);
                if (left == hVar) {
                    return new c(element, interceptor);
                }
                return new c(new c(left, element), interceptor);
            }
        }
    }

    /* compiled from: CoroutineContext.kt */
    public interface b extends g {
        <R> R fold(R r, @NotNull p<? super R, ? super b, ? extends R> pVar);

        @Nullable
        <E extends b> E get(@NotNull c<E> cVar);

        @NotNull
        c<?> getKey();

        @NotNull
        g minusKey(@NotNull c<?> cVar);

        /* compiled from: CoroutineContext.kt */
        public static final class a {
            @NotNull
            public static g d(@NotNull b bVar, @NotNull g gVar) {
                k.e(gVar, "context");
                return a.a(bVar, gVar);
            }

            @Nullable
            public static <E extends b> E b(@NotNull b $this, @NotNull c<E> key) {
                k.e(key, CacheEntity.KEY);
                if (k.a($this.getKey(), key)) {
                    return $this;
                }
                return null;
            }

            public static <R> R a(@NotNull b $this, R initial, @NotNull p<? super R, ? super b, ? extends R> operation) {
                k.e(operation, "operation");
                return operation.invoke(initial, $this);
            }

            @NotNull
            public static g c(@NotNull b $this, @NotNull c<?> key) {
                k.e(key, CacheEntity.KEY);
                return k.a($this.getKey(), key) ? h.INSTANCE : $this;
            }
        }
    }
}
