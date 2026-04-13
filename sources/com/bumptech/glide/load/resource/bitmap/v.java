package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.os.ParcelFileDescriptor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;

@RequiresApi(21)
/* compiled from: ParcelFileDescriptorBitmapDecoder */
public final class v implements k<ParcelFileDescriptor, Bitmap> {
    private final m a;

    public v(m downsampler) {
        this.a = downsampler;
    }

    /* renamed from: d */
    public boolean a(@NonNull ParcelFileDescriptor source, @NonNull i options) {
        return this.a.o(source);
    }

    @Nullable
    /* renamed from: c */
    public t<Bitmap> b(@NonNull ParcelFileDescriptor source, int width, int height, @NonNull i options) {
        return this.a.d(source, width, height, options);
    }
}
