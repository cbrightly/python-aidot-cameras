package kotlin.text;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: StringBuilderJVM.kt */
public class s extends r {
    @NotNull
    public static final StringBuilder j(@NotNull StringBuilder $this$clear) {
        k.e($this$clear, "$this$clear");
        $this$clear.setLength(0);
        return $this$clear;
    }

    @NotNull
    public static final StringBuilder i(@NotNull StringBuilder $this$appendln) {
        k.e($this$appendln, "$this$appendln");
        $this$appendln.append(a0.a);
        k.d($this$appendln, "append(SystemProperties.LINE_SEPARATOR)");
        return $this$appendln;
    }
}
