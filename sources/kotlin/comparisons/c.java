package kotlin.comparisons;

import java.util.Comparator;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Comparisons.kt */
public final class c implements Comparator<Comparable<? super Object>> {
    @NotNull
    public static final c c = new c();

    private c() {
    }

    /* renamed from: a */
    public int compare(@NotNull Comparable<Object> a, @NotNull Comparable<Object> b) {
        k.e(a, "a");
        k.e(b, "b");
        return a.compareTo(b);
    }

    @NotNull
    public final Comparator<Comparable<Object>> reversed() {
        return d.c;
    }
}
