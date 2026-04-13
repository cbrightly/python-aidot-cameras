package com.bumptech.glide.integration.webp.decoder;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.integration.webp.WebpImage;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.h;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;
import com.bumptech.glide.load.resource.c;
import com.bumptech.glide.load.resource.gif.b;
import java.nio.ByteBuffer;

/* compiled from: ByteBufferWebpDecoder */
public class d implements k<ByteBuffer, WebpDrawable> {
    public static final h<Boolean> a = h.f("com.bumptech.glide.integration.webp.decoder.ByteBufferWebpDecoder.DisableAnimation", false);
    private final Context b;
    private final e c;
    private final b d;

    public d(Context context, com.bumptech.glide.load.engine.bitmap_recycle.b byteArrayPool, e bitmapPool) {
        this.b = context.getApplicationContext();
        this.c = bitmapPool;
        this.d = new b(bitmapPool, byteArrayPool);
    }

    /* renamed from: d */
    public boolean a(@NonNull ByteBuffer source, @NonNull i options) {
        if (((Boolean) options.a(a)).booleanValue()) {
            return false;
        }
        return com.bumptech.glide.integration.webp.b.e(com.bumptech.glide.integration.webp.b.c(source));
    }

    @Nullable
    /* renamed from: c */
    public t<WebpDrawable> b(@NonNull ByteBuffer source, int width, int height, @NonNull i options) {
        int length = source.remaining();
        byte[] data = new byte[length];
        source.get(data, 0, length);
        WebpImage webp = WebpImage.create(data);
        i iVar = new i(this.d, webp, source, h.a(webp.getWidth(), webp.getHeight(), width, height), (n) options.a(o.a));
        iVar.a();
        Bitmap firstFrame = iVar.getNextFrame();
        if (firstFrame == null) {
            return null;
        }
        Transformation<Bitmap> unitTransformation = c.a();
        return new l(new WebpDrawable(this.b, iVar, this.c, unitTransformation, width, height, firstFrame));
    }
}
