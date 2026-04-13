package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.engine.bitmap_recycle.f;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.resource.a;

@RequiresApi(api = 28)
/* compiled from: BitmapImageDecoderResourceDecoder */
public final class d extends a<Bitmap> {
    private final e b = new f();

    /* access modifiers changed from: protected */
    public t<Bitmap> c(ImageDecoder.Source source, int requestedResourceWidth, int requestedResourceHeight, ImageDecoder.OnHeaderDecodedListener listener) {
        Bitmap result = ImageDecoder.decodeBitmap(source, listener);
        if (Log.isLoggable("BitmapImageDecoder", 2)) {
            Log.v("BitmapImageDecoder", "Decoded [" + result.getWidth() + "x" + result.getHeight() + "] for [" + requestedResourceWidth + "x" + requestedResourceHeight + "]");
        }
        return new e(result, this.b);
    }
}
