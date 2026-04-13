package kotlin.comparisons;

import java.util.Comparator;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Comparisons.kt */
public class a {
    /* access modifiers changed from: private */
    public static final <T> int d(T a, T b, l<? super T, ? extends Comparable<?>>[] selectors) {
        for (l fn : selectors) {
            int diff = c((Comparable) fn.invoke(a), (Comparable) fn.invoke(b));
            if (diff != 0) {
                return diff;
            }
        }
        return 0;
    }

    public static final <T extends Comparable<?>> int c(@Nullable T a, @Nullable T b) {
        if (a == b) {
            return 0;
        }
        if (a == null) {
            return -1;
        }
        if (b == null) {
            return 1;
        }
        return a.compareTo(b);
    }

    /* renamed from: kotlin.comparisons.a$a  reason: collision with other inner class name */
    /* compiled from: Comparisons.kt */
    public static final class C0319a<T> implements Comparator<T> {
        final /* synthetic */ l[] c;

        C0319a(l[] lVarArr) {
            this.c = lVarArr;
        }

        public final int compare(T a, T b) {
            return a.d(a, b, this.c);
        }
    }

    @NotNull
    public static final <T> Comparator<T> b(@NotNull l<? super T, ? extends Comparable<?>>... selectors) {
        k.e(selectors, "selectors");
        if (selectors.length > 0) {
            return new C0319a(selectors);
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    @NotNull
    public static final <T extends Comparable<? super T>> Comparator<T> e() {
        c cVar = c.c;
        if (cVar != null) {
            return cVar;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
    }
}
