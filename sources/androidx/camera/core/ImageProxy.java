package androidx.camera.core;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.media.Image;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.nio.ByteBuffer;

public interface ImageProxy extends AutoCloseable {

    public interface PlaneProxy {
        @NonNull
        ByteBuffer getBuffer();

        int getPixelStride();

        int getRowStride();
    }

    void close();

    @NonNull
    Rect getCropRect();

    int getFormat();

    int getHeight();

    @ExperimentalGetImage
    @Nullable
    Image getImage();

    @NonNull
    ImageInfo getImageInfo();

    @SuppressLint({"ArrayReturn"})
    @NonNull
    PlaneProxy[] getPlanes();

    int getWidth();

    void setCropRect(@Nullable Rect rect);
}
