package androidx.camera.core.internal.compat;

import android.media.ImageWriter;
import android.os.Build;
import android.view.Surface;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(26)
public final class ImageWriterCompat {
    @NonNull
    public static ImageWriter newInstance(@NonNull Surface surface, @IntRange(from = 1) int maxImages, int format) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 29) {
            return ImageWriterCompatApi29Impl.newInstance(surface, maxImages, format);
        }
        if (i >= 26) {
            return ImageWriterCompatApi26Impl.newInstance(surface, maxImages, format);
        }
        throw new RuntimeException("Unable to call newInstance(Surface, int, int) on API " + i + ". Version 26 or higher required.");
    }

    private ImageWriterCompat() {
    }
}
