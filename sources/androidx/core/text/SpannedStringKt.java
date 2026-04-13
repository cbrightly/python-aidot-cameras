package androidx.core.text;

import android.text.Spanned;
import android.text.SpannedString;
import androidx.exifinterface.media.ExifInterface;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: SpannedString.kt */
public final class SpannedStringKt {
    @NotNull
    public static final Spanned toSpanned(@NotNull CharSequence $this$toSpanned) {
        k.e($this$toSpanned, "<this>");
        SpannedString valueOf = SpannedString.valueOf($this$toSpanned);
        k.d(valueOf, "valueOf(this)");
        return valueOf;
    }

    public static /* synthetic */ Object[] getSpans$default(Spanned $this$getSpans_u24default, int start, int end, int i, Object obj) {
        if ((i & 1) != 0) {
            start = 0;
        }
        if ((i & 2) != 0) {
            end = $this$getSpans_u24default.length();
        }
        k.e($this$getSpans_u24default, "<this>");
        k.i(4, ExifInterface.GPS_DIRECTION_TRUE);
        Object[] spans = $this$getSpans_u24default.getSpans(start, end, Object.class);
        k.d(spans, "getSpans(start, end, T::class.java)");
        return spans;
    }

    public static final /* synthetic */ <T> T[] getSpans(Spanned $this$getSpans, int start, int end) {
        k.e($this$getSpans, "<this>");
        k.i(4, ExifInterface.GPS_DIRECTION_TRUE);
        T[] spans = $this$getSpans.getSpans(start, end, Object.class);
        k.d(spans, "getSpans(start, end, T::class.java)");
        return spans;
    }
}
