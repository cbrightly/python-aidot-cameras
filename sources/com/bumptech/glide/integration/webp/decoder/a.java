package com.bumptech.glide.integration.webp.decoder;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.integration.webp.WebpImage;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.h;
import com.bumptech.glide.load.i;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* compiled from: AnimatedWebpBitmapDecoder */
public class a {
    public static final h<Boolean> a = h.f("com.bumptech.glide.integration.webp.decoder.AnimatedWebpBitmapDecoder.DisableBitmap", false);
    private final b b;
    private final e c;
    private final com.bumptech.glide.load.resource.gif.b d;

    public a(b byteArrayPool, e bitmapPool) {
        this.b = byteArrayPool;
        this.c = bitmapPool;
        this.d = new com.bumptech.glide.load.resource.gif.b(bitmapPool, byteArrayPool);
    }

    public boolean c(InputStream source, @NonNull i options) {
        if (((Boolean) options.a(a)).booleanValue()) {
            return false;
        }
        return com.bumptech.glide.integration.webp.b.e(com.bumptech.glide.integration.webp.b.b(source, this.b));
    }

    public boolean d(ByteBuffer source, @NonNull i options) {
        if (((Boolean) options.a(a)).booleanValue()) {
            return false;
        }
        return com.bumptech.glide.integration.webp.b.e(com.bumptech.glide.integration.webp.b.c(source));
    }

    public t<Bitmap> a(InputStream source, int width, int height, i options) {
        byte[] data = h.b(source);
        if (data == null) {
            return null;
        }
        return b(ByteBuffer.wrap(data), width, height, options);
    }

    public t<Bitmap> b(ByteBuffer source, int width, int height, i options) {
        int length = source.remaining();
        byte[] data = new byte[length];
        source.get(data, 0, length);
        WebpImage webp = WebpImage.create(data);
        i webpDecoder = new i(this.d, webp, source, h.a(webp.getWidth(), webp.getHeight(), width, height));
        try {
            webpDecoder.a();
            return com.bumptech.glide.load.resource.bitmap.e.c(webpDecoder.getNextFrame(), this.c);
        } finally {
            webpDecoder.clear();
        }
    }
}
