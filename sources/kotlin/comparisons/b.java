package kotlin.comparisons;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: _ComparisonsJvm.kt */
public class b extends a {
    @NotNull
    public static final <T extends Comparable<? super T>> T f(@NotNull T a, @NotNull T b) {
        k.e(a, "a");
        k.e(b, "b");
        return a.compareTo(b) >= 0 ? a : b;
    }
}
