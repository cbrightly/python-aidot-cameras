package androidx.core.graphics;

import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import androidx.annotation.RequiresApi;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: ImageDecoder.kt */
public final class ImageDecoderKt {
    @RequiresApi(28)
    @NotNull
    public static final Bitmap decodeBitmap(@NotNull ImageDecoder.Source $this$decodeBitmap, @NotNull q<? super ImageDecoder, ? super ImageDecoder.ImageInfo, ? super ImageDecoder.Source, x> action) {
        k.e($this$decodeBitmap, "<this>");
        k.e(action, "action");
        Bitmap decodeBitmap = ImageDecoder.decodeBitmap($this$decodeBitmap, new ImageDecoderKt$decodeBitmap$1(action));
        k.d(decodeBitmap, "crossinline action: ImageDecoder.(info: ImageInfo, source: Source) -> Unit\n): Bitmap {\n    return ImageDecoder.decodeBitmap(this) { decoder, info, source ->\n        decoder.action(info, source)\n    }");
        return decodeBitmap;
    }

    @RequiresApi(28)
    @NotNull
    public static final Drawable decodeDrawable(@NotNull ImageDecoder.Source $this$decodeDrawable, @NotNull q<? super ImageDecoder, ? super ImageDecoder.ImageInfo, ? super ImageDecoder.Source, x> action) {
        k.e($this$decodeDrawable, "<this>");
        k.e(action, "action");
        Drawable decodeDrawable = ImageDecoder.decodeDrawable($this$decodeDrawable, new ImageDecoderKt$decodeDrawable$1(action));
        k.d(decodeDrawable, "crossinline action: ImageDecoder.(info: ImageInfo, source: Source) -> Unit\n): Drawable {\n    return ImageDecoder.decodeDrawable(this) { decoder, info, source ->\n        decoder.action(info, source)\n    }");
        return decodeDrawable;
    }
}
