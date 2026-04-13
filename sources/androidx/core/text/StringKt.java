package androidx.core.text;

import android.text.TextUtils;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: String.kt */
public final class StringKt {
    @NotNull
    public static final String htmlEncode(@NotNull String $this$htmlEncode) {
        k.e($this$htmlEncode, "<this>");
        String htmlEncode = TextUtils.htmlEncode($this$htmlEncode);
        k.d(htmlEncode, "htmlEncode(this)");
        return htmlEncode;
    }
}
