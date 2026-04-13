package kotlin.sequences;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.i;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: _Sequences.kt */
public class o extends n {

    /* compiled from: Iterables.kt */
    public static final class a implements Iterable<T>, kotlin.jvm.internal.markers.a {
        final /* synthetic */ h c;

        public a(h hVar) {
            this.c = hVar;
        }

        @NotNull
        public Iterator<T> iterator() {
            return this.c.iterator();
        }
    }

    @Nullable
    public static final <T> T r(@NotNull h<? extends T> $this$firstOrNull) {
        k.e($this$firstOrNull, "$this$firstOrNull");
        Iterator iterator = $this$firstOrNull.iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        return iterator.next();
    }

    @NotNull
    public static final <T> h<T> n(@NotNull h<? extends T> $this$drop, int n) {
        k.e($this$drop, "$this$drop");
        if (!(n >= 0)) {
            throw new IllegalArgumentException(("Requested element count " + n + " is less than zero.").toString());
        } else if (n == 0) {
            return $this$drop;
        } else {
            if ($this$drop instanceof c) {
                return ((c) $this$drop).a(n);
            }
            return new b($this$drop, n);
        }
    }

    @NotNull
    public static final <T> h<T> o(@NotNull h<? extends T> $this$filter, @NotNull l<? super T, Boolean> predicate) {
        k.e($this$filter, "$this$filter");
        k.e(predicate, "predicate");
        return new e($this$filter, true, predicate);
    }

    @NotNull
    public static final <T> h<T> p(@NotNull h<? extends T> $this$filterNot, @NotNull l<? super T, Boolean> predicate) {
        k.e($this$filterNot, "$this$filterNot");
        k.e(predicate, "predicate");
        return new e($this$filterNot, false, predicate);
    }

    /* compiled from: _Sequences.kt */
    public static final class b extends kotlin.jvm.internal.l implements l<T, Boolean> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        public final boolean invoke(@Nullable T it) {
            return it == null;
        }
    }

    @NotNull
    public static final <T> h<T> q(@NotNull h<? extends T> $this$filterNotNull) {
        k.e($this$filterNotNull, "$this$filterNotNull");
        h<T> p = p($this$filterNotNull, b.INSTANCE);
        if (p != null) {
            return p;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.sequences.Sequence<T>");
    }

    @NotNull
    public static final <T> h<T> A(@NotNull h<? extends T> $this$takeWhile, @NotNull l<? super T, Boolean> predicate) {
        k.e($this$takeWhile, "$this$takeWhile");
        k.e(predicate, "predicate");
        return new p($this$takeWhile, predicate);
    }

    @NotNull
    public static final <T, C extends Collection<? super T>> C B(@NotNull h<? extends T> $this$toCollection, @NotNull C destination) {
        k.e($this$toCollection, "$this$toCollection");
        k.e(destination, FirebaseAnalytics.Param.DESTINATION);
        for (Object item : $this$toCollection) {
            destination.add(item);
        }
        return destination;
    }

    @NotNull
    public static final <T> List<T> C(@NotNull h<? extends T> $this$toList) {
        k.e($this$toList, "$this$toList");
        return q.n(D($this$toList));
    }

    @NotNull
    public static final <T> List<T> D(@NotNull h<? extends T> $this$toMutableList) {
        k.e($this$toMutableList, "$this$toMutableList");
        return (List) B($this$toMutableList, new ArrayList());
    }

    /* compiled from: _Sequences.kt */
    public static final /* synthetic */ class c extends i implements l<h<? extends R>, Iterator<? extends R>> {
        public static final c INSTANCE = new c();

        c() {
            super(1, h.class, "iterator", "iterator()Ljava/util/Iterator;", 0);
        }

        @NotNull
        public final Iterator<R> invoke(@NotNull h<? extends R> p1) {
            k.e(p1, "p1");
            return p1.iterator();
        }
    }

    @NotNull
    public static final <T, R> h<R> s(@NotNull h<? extends T> $this$flatMap, @NotNull l<? super T, ? extends h<? extends R>> transform) {
        k.e($this$flatMap, "$this$flatMap");
        k.e(transform, "transform");
        return new f($this$flatMap, transform, c.INSTANCE);
    }

    @NotNull
    public static final <T, R> h<R> w(@NotNull h<? extends T> $this$map, @NotNull l<? super T, ? extends R> transform) {
        k.e($this$map, "$this$map");
        k.e(transform, "transform");
        return new q($this$map, transform);
    }

    @NotNull
    public static final <T, R> h<R> x(@NotNull h<? extends T> $this$mapNotNull, @NotNull l<? super T, ? extends R> transform) {
        k.e($this$mapNotNull, "$this$mapNotNull");
        k.e(transform, "transform");
        return q(new q($this$mapNotNull, transform));
    }

    public static final <T> int m(@NotNull h<? extends T> $this$count) {
        k.e($this$count, "$this$count");
        int count = 0;
        for (Object next : $this$count) {
            count++;
            if (count < 0) {
                q.p();
            }
        }
        return count;
    }

    @NotNull
    public static final <T> h<T> z(@NotNull h<? extends T> $this$plus, T element) {
        k.e($this$plus, "$this$plus");
        return m.f(m.k($this$plus, m.k(element)));
    }

    @NotNull
    public static final <T> h<T> y(@NotNull h<? extends T> $this$plus, @NotNull Iterable<? extends T> elements) {
        k.e($this$plus, "$this$plus");
        k.e(elements, "elements");
        return m.f(m.k($this$plus, y.L(elements)));
    }

    @NotNull
    public static final <T, A extends Appendable> A t(@NotNull h<? extends T> $this$joinTo, @NotNull A buffer, @NotNull CharSequence separator, @NotNull CharSequence prefix, @NotNull CharSequence postfix, int limit, @NotNull CharSequence truncated, @Nullable l<? super T, ? extends CharSequence> transform) {
        k.e($this$joinTo, "$this$joinTo");
        k.e(buffer, "buffer");
        k.e(separator, "separator");
        k.e(prefix, "prefix");
        k.e(postfix, "postfix");
        k.e(truncated, "truncated");
        buffer.append(prefix);
        int count = 0;
        for (Object element : $this$joinTo) {
            count++;
            if (count > 1) {
                buffer.append(separator);
            }
            if (limit >= 0 && count > limit) {
                break;
            }
            kotlin.text.o.a(buffer, element, transform);
        }
        if (limit >= 0 && count > limit) {
            buffer.append(truncated);
        }
        buffer.append(postfix);
        return buffer;
    }

    public static /* synthetic */ String v(h hVar, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, CharSequence charSequence4, l lVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            charSequence = ", ";
        }
        CharSequence charSequence5 = "";
        CharSequence charSequence6 = (i2 & 2) != 0 ? charSequence5 : charSequence2;
        if ((i2 & 4) == 0) {
            charSequence5 = charSequence3;
        }
        if ((i2 & 8) != 0) {
            i = -1;
        }
        int i3 = i;
        if ((i2 & 16) != 0) {
            charSequence4 = "...";
        }
        CharSequence charSequence7 = charSequence4;
        if ((i2 & 32) != 0) {
            lVar = null;
        }
        return u(hVar, charSequence, charSequence6, charSequence5, i3, charSequence7, lVar);
    }

    @NotNull
    public static final <T> String u(@NotNull h<? extends T> $this$joinToString, @NotNull CharSequence separator, @NotNull CharSequence prefix, @NotNull CharSequence postfix, int limit, @NotNull CharSequence truncated, @Nullable l<? super T, ? extends CharSequence> transform) {
        k.e($this$joinToString, "$this$joinToString");
        k.e(separator, "separator");
        k.e(prefix, "prefix");
        k.e(postfix, "postfix");
        k.e(truncated, "truncated");
        String sb = ((StringBuilder) t($this$joinToString, new StringBuilder(), separator, prefix, postfix, limit, truncated, transform)).toString();
        k.d(sb, "joinTo(StringBuilder(), …ed, transform).toString()");
        return sb;
    }

    @NotNull
    public static final <T> Iterable<T> l(@NotNull h<? extends T> $this$asIterable) {
        k.e($this$asIterable, "$this$asIterable");
        return new a($this$asIterable);
    }
}
