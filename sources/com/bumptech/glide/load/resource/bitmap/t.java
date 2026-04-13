package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;
import com.bumptech.glide.util.a;
import java.io.InputStream;

@RequiresApi(api = 28)
/* compiled from: InputStreamBitmapImageDecoderResourceDecoder */
public final class t implements k<InputStream, Bitmap> {
    private final d a = new d();

    /* renamed from: d */
    public boolean a(@NonNull InputStream source, @NonNull i options) {
        return true;
    }

    @Nullable
    /* renamed from: c */
    public com.bumptech.glide.load.engine.t<Bitmap> b(@NonNull InputStream stream, int width, int height, @NonNull i options) {
        return this.a.b(ImageDecoder.createSource(a.b(stream)), width, height, options);
    }
}
