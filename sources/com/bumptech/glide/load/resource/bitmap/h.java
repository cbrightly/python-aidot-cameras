package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;
import java.nio.ByteBuffer;

@RequiresApi(api = 28)
/* compiled from: ByteBufferBitmapImageDecoderResourceDecoder */
public final class h implements k<ByteBuffer, Bitmap> {
    private final d a = new d();

    /* renamed from: d */
    public boolean a(@NonNull ByteBuffer source, @NonNull i options) {
        return true;
    }

    @Nullable
    /* renamed from: c */
    public t<Bitmap> b(@NonNull ByteBuffer buffer, int width, int height, @NonNull i options) {
        return this.a.b(ImageDecoder.createSource(buffer), width, height, options);
    }
}
