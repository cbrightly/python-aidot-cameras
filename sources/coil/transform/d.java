package coil.transform;

import android.graphics.Bitmap;
import coil.bitmap.c;
import coil.size.Size;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Transformation.kt */
public interface d {
    @Nullable
    Object a(@NotNull c cVar, @NotNull Bitmap bitmap, @NotNull Size size, @NotNull kotlin.coroutines.d<? super Bitmap> dVar);

    @NotNull
    String key();
}
