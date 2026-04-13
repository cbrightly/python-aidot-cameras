package com.bumptech.glide.integration.webp.decoder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.h;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* compiled from: StreamWebpDecoder */
public class g implements k<InputStream, WebpDrawable> {
    public static final h<Boolean> a = h.f("com.bumptech.glide.integration.webp.decoder.StreamWebpDecoder.DisableAnimation", false);
    private final k<ByteBuffer, WebpDrawable> b;
    private final b c;

    public g(k<ByteBuffer, WebpDrawable> byteBufferDecoder, b byteArrayPool) {
        this.b = byteBufferDecoder;
        this.c = byteArrayPool;
    }

    /* renamed from: d */
    public boolean a(@NonNull InputStream inputStream, @NonNull i options) {
        if (((Boolean) options.a(a)).booleanValue()) {
            return false;
        }
        return com.bumptech.glide.integration.webp.b.e(com.bumptech.glide.integration.webp.b.b(inputStream, this.c));
    }

    @Nullable
    /* renamed from: c */
    public t<WebpDrawable> b(@NonNull InputStream inputStream, int width, int height, @NonNull i options) {
        byte[] data = h.b(inputStream);
        if (data == null) {
            return null;
        }
        return this.b.b(ByteBuffer.wrap(data), width, height, options);
    }
}
