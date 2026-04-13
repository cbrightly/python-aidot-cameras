package kotlin.comparisons;

import java.util.Comparator;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Comparisons.kt */
public final class d implements Comparator<Comparable<? super Object>> {
    @NotNull
    public static final d c = new d();

    private d() {
    }

    /* renamed from: a */
    public int compare(@NotNull Comparable<Object> a, @NotNull Comparable<Object> b) {
        k.e(a, "a");
        k.e(b, "b");
        return b.compareTo(a);
    }

    @NotNull
    public final Comparator<Comparable<Object>> reversed() {
        return c.c;
    }
}
