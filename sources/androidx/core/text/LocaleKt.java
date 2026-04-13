package androidx.core.text;

import android.text.TextUtils;
import androidx.annotation.RequiresApi;
import java.util.Locale;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Locale.kt */
public final class LocaleKt {
    @RequiresApi(17)
    public static final int getLayoutDirection(@NotNull Locale $this$layoutDirection) {
        k.e($this$layoutDirection, "<this>");
        return TextUtils.getLayoutDirectionFromLocale($this$layoutDirection);
    }
}
