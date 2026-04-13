package androidx.camera.core;

import android.media.ImageReader;
import androidx.annotation.NonNull;
import androidx.camera.core.impl.ImageReaderProxy;

public final class ImageReaderProxys {
    private ImageReaderProxys() {
    }

    @NonNull
    static ImageReaderProxy createIsolatedReader(int width, int height, int format, int maxImages) {
        return new AndroidImageReaderProxy(ImageReader.newInstance(width, height, format, maxImages));
    }
}
