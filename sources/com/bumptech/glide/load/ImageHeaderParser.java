package com.bumptech.glide.load;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import java.io.InputStream;
import java.nio.ByteBuffer;

public interface ImageHeaderParser {
    @NonNull
    ImageType a(@NonNull ByteBuffer byteBuffer);

    @NonNull
    ImageType b(@NonNull InputStream inputStream);

    int c(@NonNull InputStream inputStream, @NonNull b bVar);

    public enum ImageType {
        GIF(true),
        JPEG(false),
        RAW(false),
        PNG_A(true),
        PNG(false),
        WEBP_A(true),
        WEBP(false),
        UNKNOWN(false);
        
        private final boolean hasAlpha;

        private ImageType(boolean hasAlpha2) {
            this.hasAlpha = hasAlpha2;
        }

        public boolean hasAlpha() {
            return this.hasAlpha;
        }
    }
}
