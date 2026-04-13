package androidx.core.text;

import android.text.TextUtils;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: CharSequence.kt */
public final class CharSequenceKt {
    public static final boolean isDigitsOnly(@NotNull CharSequence $this$isDigitsOnly) {
        k.e($this$isDigitsOnly, "<this>");
        return TextUtils.isDigitsOnly($this$isDigitsOnly);
    }

    public static final int trimmedLength(@NotNull CharSequence $this$trimmedLength) {
        k.e($this$trimmedLength, "<this>");
        return TextUtils.getTrimmedLength($this$trimmedLength);
    }
}
