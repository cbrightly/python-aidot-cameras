package com.bumptech.glide.load.resource.transcode;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.resource.bytes.b;
import java.io.ByteArrayOutputStream;

/* compiled from: BitmapBytesTranscoder */
public class a implements e<Bitmap, byte[]> {
    private final Bitmap.CompressFormat a;
    private final int b;

    public a() {
        this(Bitmap.CompressFormat.JPEG, 100);
    }

    public a(@NonNull Bitmap.CompressFormat compressFormat, int quality) {
        this.a = compressFormat;
        this.b = quality;
    }

    @Nullable
    public t<byte[]> a(@NonNull t<Bitmap> toTranscode, @NonNull i options) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        toTranscode.get().compress(this.a, this.b, os);
        toTranscode.recycle();
        return new b(os.toByteArray());
    }
}
