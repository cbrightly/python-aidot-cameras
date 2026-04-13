package kotlin.text;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: StringBuilder.kt */
public class t extends s {
    @NotNull
    public static final StringBuilder k(@NotNull StringBuilder $this$append, @NotNull String... value) {
        k.e($this$append, "$this$append");
        k.e(value, "value");
        for (String item : value) {
            $this$append.append(item);
        }
        return $this$append;
    }
}
