package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;
import com.bumptech.glide.load.resource.bitmap.m;
import com.bumptech.glide.util.c;
import com.bumptech.glide.util.g;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: StreamBitmapDecoder */
public class y implements k<InputStream, Bitmap> {
    private final m a;
    private final b b;

    public y(m downsampler, b byteArrayPool) {
        this.a = downsampler;
        this.b = byteArrayPool;
    }

    /* renamed from: d */
    public boolean a(@NonNull InputStream source, @NonNull i options) {
        return this.a.p(source);
    }

    /* renamed from: c */
    public t<Bitmap> b(@NonNull InputStream source, int width, int height, @NonNull i options) {
        boolean ownsBufferedStream;
        RecyclableBufferedInputStream bufferedStream;
        if (source instanceof RecyclableBufferedInputStream) {
            bufferedStream = (RecyclableBufferedInputStream) source;
            ownsBufferedStream = false;
        } else {
            bufferedStream = new RecyclableBufferedInputStream(source, this.b);
            ownsBufferedStream = true;
        }
        c exceptionStream = c.c(bufferedStream);
        try {
            return this.a.g(new g(exceptionStream), width, height, options, new a(bufferedStream, exceptionStream));
        } finally {
            exceptionStream.release();
            if (ownsBufferedStream) {
                bufferedStream.release();
            }
        }
    }

    /* compiled from: StreamBitmapDecoder */
    public static class a implements m.b {
        private final RecyclableBufferedInputStream a;
        private final c b;

        a(RecyclableBufferedInputStream bufferedStream, c exceptionStream) {
            this.a = bufferedStream;
            this.b = exceptionStream;
        }

        public void b() {
            this.a.c();
        }

        public void a(e bitmapPool, Bitmap downsampled) {
            IOException streamException = this.b.a();
            if (streamException != null) {
                if (downsampled != null) {
                    bitmapPool.b(downsampled);
                }
                throw streamException;
            }
        }
    }
}
