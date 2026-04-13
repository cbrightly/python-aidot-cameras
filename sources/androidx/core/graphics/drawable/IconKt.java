package androidx.core.graphics.drawable;

import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import androidx.annotation.RequiresApi;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Icon.kt */
public final class IconKt {
    @RequiresApi(26)
    @NotNull
    public static final Icon toAdaptiveIcon(@NotNull Bitmap $this$toAdaptiveIcon) {
        k.e($this$toAdaptiveIcon, "<this>");
        Icon createWithAdaptiveBitmap = Icon.createWithAdaptiveBitmap($this$toAdaptiveIcon);
        k.d(createWithAdaptiveBitmap, "createWithAdaptiveBitmap(this)");
        return createWithAdaptiveBitmap;
    }

    @RequiresApi(26)
    @NotNull
    public static final Icon toIcon(@NotNull Bitmap $this$toIcon) {
        k.e($this$toIcon, "<this>");
        Icon createWithBitmap = Icon.createWithBitmap($this$toIcon);
        k.d(createWithBitmap, "createWithBitmap(this)");
        return createWithBitmap;
    }

    @RequiresApi(26)
    @NotNull
    public static final Icon toIcon(@NotNull Uri $this$toIcon) {
        k.e($this$toIcon, "<this>");
        Icon createWithContentUri = Icon.createWithContentUri($this$toIcon);
        k.d(createWithContentUri, "createWithContentUri(this)");
        return createWithContentUri;
    }

    @RequiresApi(26)
    @NotNull
    public static final Icon toIcon(@NotNull byte[] $this$toIcon) {
        k.e($this$toIcon, "<this>");
        Icon createWithData = Icon.createWithData($this$toIcon, 0, $this$toIcon.length);
        k.d(createWithData, "createWithData(this, 0, size)");
        return createWithData;
    }
}
