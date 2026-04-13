package com.bumptech.glide.integration.webp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import com.bumptech.glide.Registry;
import com.bumptech.glide.b;
import com.bumptech.glide.integration.webp.decoder.WebpDrawable;
import com.bumptech.glide.integration.webp.decoder.a;
import com.bumptech.glide.integration.webp.decoder.d;
import com.bumptech.glide.integration.webp.decoder.f;
import com.bumptech.glide.integration.webp.decoder.g;
import com.bumptech.glide.integration.webp.decoder.j;
import com.bumptech.glide.integration.webp.decoder.k;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.module.c;
import java.io.InputStream;
import java.nio.ByteBuffer;

@Deprecated
public class WebpGlideModule implements c {
    public void a(Context context, com.bumptech.glide.c builder) {
    }

    public void b(Context context, b glide, Registry registry) {
        Resources resources = context.getResources();
        e bitmapPool = glide.f();
        com.bumptech.glide.load.engine.bitmap_recycle.b arrayPool = glide.e();
        j webpDownsampler = new j(registry.g(), resources.getDisplayMetrics(), bitmapPool, arrayPool);
        a bitmapDecoder = new a(arrayPool, bitmapPool);
        com.bumptech.glide.integration.webp.decoder.c byteBufferBitmapDecoder = new com.bumptech.glide.integration.webp.decoder.c(webpDownsampler);
        f streamBitmapDecoder = new f(webpDownsampler, arrayPool);
        d byteBufferWebpDecoder = new d(context, arrayPool, bitmapPool);
        registry.q("Bitmap", ByteBuffer.class, Bitmap.class, byteBufferBitmapDecoder).q("Bitmap", InputStream.class, Bitmap.class, streamBitmapDecoder).q("BitmapDrawable", ByteBuffer.class, BitmapDrawable.class, new com.bumptech.glide.load.resource.bitmap.a(resources, byteBufferBitmapDecoder)).q("BitmapDrawable", InputStream.class, BitmapDrawable.class, new com.bumptech.glide.load.resource.bitmap.a(resources, streamBitmapDecoder)).q("Bitmap", ByteBuffer.class, Bitmap.class, new com.bumptech.glide.integration.webp.decoder.b(bitmapDecoder)).q("Bitmap", InputStream.class, Bitmap.class, new com.bumptech.glide.integration.webp.decoder.e(bitmapDecoder)).p(ByteBuffer.class, WebpDrawable.class, byteBufferWebpDecoder).p(InputStream.class, WebpDrawable.class, new g(byteBufferWebpDecoder, arrayPool)).o(WebpDrawable.class, new k());
    }
}
