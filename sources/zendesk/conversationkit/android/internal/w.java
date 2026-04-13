package zendesk.conversationkit.android.internal;

import com.luck.picture.lib.compress.Checker;
import com.yanzhenjie.andserver.util.h;
import java.util.Locale;
import kotlin.collections.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: FileExt.kt */
public final class w {
    @NotNull
    private static final String[] a = {"image/jpeg", "image/png", h.IMAGE_GIF_VALUE, Checker.MIME_TYPE_JPG, "image/webp", "image/svg+xml"};

    public static final boolean a(@NotNull String $this$isImageMimeType) {
        k.e($this$isImageMimeType, "<this>");
        String[] strArr = a;
        Locale locale = Locale.US;
        k.d(locale, "US");
        String lowerCase = $this$isImageMimeType.toLowerCase(locale);
        k.d(lowerCase, "(this as java.lang.String).toLowerCase(locale)");
        return l.r(strArr, lowerCase);
    }
}
