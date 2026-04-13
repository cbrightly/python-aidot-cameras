package kotlin.text;

import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Appendable.kt */
public class o {
    public static final <T> void a(@NotNull Appendable $this$appendElement, T element, @Nullable l<? super T, ? extends CharSequence> transform) {
        k.e($this$appendElement, "$this$appendElement");
        if (transform != null) {
            $this$appendElement.append((CharSequence) transform.invoke(element));
            return;
        }
        if (element != null ? element instanceof CharSequence : true) {
            $this$appendElement.append((CharSequence) element);
        } else if (element instanceof Character) {
            $this$appendElement.append(((Character) element).charValue());
        } else {
            $this$appendElement.append(String.valueOf(element));
        }
    }
}
