package kotlin.coroutines;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.coroutines.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ContinuationInterceptor.kt */
public interface e extends g.b {
    @NotNull
    public static final b a = b.c;

    @NotNull
    <T> d<T> interceptContinuation(@NotNull d<? super T> dVar);

    void releaseInterceptedContinuation(@NotNull d<?> dVar);

    /* compiled from: ContinuationInterceptor.kt */
    public static final class b implements g.c<e> {
        static final /* synthetic */ b c = new b();

        private b() {
        }
    }

    /* compiled from: ContinuationInterceptor.kt */
    public static final class a {
        @Nullable
        public static <E extends g.b> E a(@NotNull e $this, @NotNull g.c<E> key) {
            k.e(key, CacheEntity.KEY);
            if (key instanceof b) {
                if (!((b) key).a($this.getKey())) {
                    return null;
                }
                E b = ((b) key).b($this);
                if (!(b instanceof g.b)) {
                    return null;
                }
                return b;
            } else if (e.a != key) {
                return null;
            } else {
                if ($this != null) {
                    return $this;
                }
                throw new NullPointerException("null cannot be cast to non-null type E");
            }
        }

        @NotNull
        public static g b(@NotNull e $this, @NotNull g.c<?> key) {
            k.e(key, CacheEntity.KEY);
            return key instanceof b ? (!((b) key).a($this.getKey()) || ((b) key).b($this) == null) ? $this : h.INSTANCE : e.a == key ? h.INSTANCE : $this;
        }
    }
}
