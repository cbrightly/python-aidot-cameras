package androidx.core.graphics;

import android.graphics.ImageDecoder;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\n¢\u0006\u0004\b\u0007\u0010\b"}, d2 = {"Landroid/graphics/ImageDecoder;", "decoder", "Landroid/graphics/ImageDecoder$ImageInfo;", "info", "Landroid/graphics/ImageDecoder$Source;", "source", "Lkotlin/x;", "<anonymous>", "(Landroid/graphics/ImageDecoder;Landroid/graphics/ImageDecoder$ImageInfo;Landroid/graphics/ImageDecoder$Source;)V"}, k = 3, mv = {1, 5, 1})
/* compiled from: ImageDecoder.kt */
public final class ImageDecoderKt$decodeBitmap$1 implements ImageDecoder.OnHeaderDecodedListener {
    final /* synthetic */ q<ImageDecoder, ImageDecoder.ImageInfo, ImageDecoder.Source, x> $action;

    public ImageDecoderKt$decodeBitmap$1(q<? super ImageDecoder, ? super ImageDecoder.ImageInfo, ? super ImageDecoder.Source, x> qVar) {
        this.$action = qVar;
    }

    public final void onHeaderDecoded(@NotNull ImageDecoder decoder, @NotNull ImageDecoder.ImageInfo info, @NotNull ImageDecoder.Source source) {
        k.e(decoder, "decoder");
        k.e(info, "info");
        k.e(source, "source");
        this.$action.invoke(decoder, info, source);
    }
}
