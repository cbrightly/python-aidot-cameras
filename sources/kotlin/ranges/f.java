package kotlin.ranges;

import java.lang.Comparable;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Range.kt */
public interface f<T extends Comparable<? super T>> {
    @NotNull
    T getEndInclusive();

    @NotNull
    T getStart();

    /* compiled from: Range.kt */
    public static final class a {
        public static <T extends Comparable<? super T>> boolean a(@NotNull f<T> $this, @NotNull T value) {
            k.e(value, "value");
            return value.compareTo($this.getStart()) >= 0 && value.compareTo($this.getEndInclusive()) <= 0;
        }

        public static <T extends Comparable<? super T>> boolean b(@NotNull f<T> $this) {
            return $this.getStart().compareTo($this.getEndInclusive()) > 0;
        }
    }
}
