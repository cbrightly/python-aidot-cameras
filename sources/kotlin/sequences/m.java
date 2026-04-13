package kotlin.sequences;

import java.util.Iterator;
import kotlin.collections.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Sequences.kt */
public class m extends l {

    /* compiled from: Sequences.kt */
    public static final class a implements h<T> {
        final /* synthetic */ Iterator a;

        public a(Iterator it) {
            this.a = it;
        }

        @NotNull
        public Iterator<T> iterator() {
            return this.a;
        }
    }

    @NotNull
    public static final <T> h<T> c(@NotNull Iterator<? extends T> $this$asSequence) {
        k.e($this$asSequence, "$this$asSequence");
        return d(new a($this$asSequence));
    }

    @NotNull
    public static final <T> h<T> k(@NotNull T... elements) {
        k.e(elements, "elements");
        return elements.length == 0 ? e() : l.p(elements);
    }

    @NotNull
    public static final <T> h<T> e() {
        return d.a;
    }

    /* compiled from: Sequences.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<h<? extends T>, Iterator<? extends T>> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        @NotNull
        public final Iterator<T> invoke(@NotNull h<? extends T> it) {
            k.e(it, "it");
            return it.iterator();
        }
    }

    @NotNull
    public static final <T> h<T> f(@NotNull h<? extends h<? extends T>> $this$flatten) {
        k.e($this$flatten, "$this$flatten");
        return g($this$flatten, b.INSTANCE);
    }

    private static final <T, R> h<R> g(h<? extends T> $this$flatten, kotlin.jvm.functions.l<? super T, ? extends Iterator<? extends R>> iterator) {
        if ($this$flatten instanceof q) {
            return ((q) $this$flatten).d(iterator);
        }
        return new f($this$flatten, c.INSTANCE, iterator);
    }

    /* compiled from: Sequences.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<T, T> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        public final T invoke(T it) {
            return it;
        }
    }

    @NotNull
    public static final <T> h<T> d(@NotNull h<? extends T> $this$constrainOnce) {
        k.e($this$constrainOnce, "$this$constrainOnce");
        return $this$constrainOnce instanceof a ? $this$constrainOnce : new a($this$constrainOnce);
    }

    /* compiled from: Sequences.kt */
    public static final class d extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<T, T> {
        final /* synthetic */ kotlin.jvm.functions.a $nextFunction;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(kotlin.jvm.functions.a aVar) {
            super(1);
            this.$nextFunction = aVar;
        }

        @Nullable
        public final T invoke(@NotNull T it) {
            k.e(it, "it");
            return this.$nextFunction.invoke();
        }
    }

    @NotNull
    public static final <T> h<T> i(@NotNull kotlin.jvm.functions.a<? extends T> nextFunction) {
        k.e(nextFunction, "nextFunction");
        return d(new g(nextFunction, new d(nextFunction)));
    }

    @NotNull
    public static final <T> h<T> h(@Nullable T seed, @NotNull kotlin.jvm.functions.l<? super T, ? extends T> nextFunction) {
        k.e(nextFunction, "nextFunction");
        if (seed == null) {
            return d.a;
        }
        return new g(new e(seed), nextFunction);
    }

    /* compiled from: Sequences.kt */
    public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<T> {
        final /* synthetic */ Object $seed;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(Object obj) {
            super(0);
            this.$seed = obj;
        }

        @Nullable
        public final T invoke() {
            return this.$seed;
        }
    }

    @NotNull
    public static final <T> h<T> j(@NotNull kotlin.jvm.functions.a<? extends T> seedFunction, @NotNull kotlin.jvm.functions.l<? super T, ? extends T> nextFunction) {
        k.e(seedFunction, "seedFunction");
        k.e(nextFunction, "nextFunction");
        return new g(seedFunction, nextFunction);
    }
}
