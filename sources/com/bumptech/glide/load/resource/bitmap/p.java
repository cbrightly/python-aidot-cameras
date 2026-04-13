package com.bumptech.glide.load.resource.bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.exifinterface.media.ExifInterface;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import java.io.InputStream;
import java.nio.ByteBuffer;

@RequiresApi(27)
/* compiled from: ExifInterfaceImageHeaderParser */
public final class p implements ImageHeaderParser {
    @NonNull
    public ImageHeaderParser.ImageType b(@NonNull InputStream is) {
        return ImageHeaderParser.ImageType.UNKNOWN;
    }

    @NonNull
    public ImageHeaderParser.ImageType a(@NonNull ByteBuffer byteBuffer) {
        return ImageHeaderParser.ImageType.UNKNOWN;
    }

    public int c(@NonNull InputStream is, @NonNull b byteArrayPool) {
        int result = new ExifInterface(is).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        if (result == 0) {
            return -1;
        }
        return result;
    }
}
