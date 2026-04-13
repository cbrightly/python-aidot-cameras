package androidx.camera.core.internal.compat;

import android.media.ImageWriter;
import android.view.Surface;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(29)
public final class ImageWriterCompatApi29Impl {
    @NonNull
    static ImageWriter newInstance(@NonNull Surface surface, @IntRange(from = 1) int maxImages, int format) {
        return ImageWriter.newInstance(surface, maxImages, format);
    }

    private ImageWriterCompatApi29Impl() {
    }
}
