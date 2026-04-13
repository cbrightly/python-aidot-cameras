package com.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.gifdecoder.a;
import com.bumptech.glide.gifdecoder.c;
import com.bumptech.glide.gifdecoder.d;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;
import com.bumptech.glide.util.j;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Queue;

/* compiled from: ByteBufferGifDecoder */
public class a implements k<ByteBuffer, GifDrawable> {
    private static final C0043a a = new C0043a();
    private static final b b = new b();
    private final Context c;
    private final List<ImageHeaderParser> d;
    private final b e;
    private final C0043a f;
    private final b g;

    public a(Context context, List<ImageHeaderParser> parsers, e bitmapPool, com.bumptech.glide.load.engine.bitmap_recycle.b arrayPool) {
        this(context, parsers, bitmapPool, arrayPool, b, a);
    }

    @VisibleForTesting
    a(Context context, List<ImageHeaderParser> parsers, e bitmapPool, com.bumptech.glide.load.engine.bitmap_recycle.b arrayPool, b parserPool, C0043a gifDecoderFactory) {
        this.c = context.getApplicationContext();
        this.d = parsers;
        this.f = gifDecoderFactory;
        this.g = new b(bitmapPool, arrayPool);
        this.e = parserPool;
    }

    /* renamed from: f */
    public boolean a(@NonNull ByteBuffer source, @NonNull i options) {
        return !((Boolean) options.a(h.b)).booleanValue() && com.bumptech.glide.load.e.f(this.d, source) == ImageHeaderParser.ImageType.GIF;
    }

    /* renamed from: d */
    public d b(@NonNull ByteBuffer source, int width, int height, @NonNull i options) {
        d parser = this.e.a(source);
        try {
            return c(source, width, height, parser, options);
        } finally {
            this.e.b(parser);
        }
    }

    @Nullable
    private d c(ByteBuffer byteBuffer, int width, int height, d parser, i options) {
        Bitmap.Config config;
        long startTime = com.bumptech.glide.util.e.b();
        try {
            c header = parser.c();
            if (header.b() > 0) {
                if (header.c() == 0) {
                    if (options.a(h.a) == com.bumptech.glide.load.b.PREFER_RGB_565) {
                        config = Bitmap.Config.RGB_565;
                    } else {
                        config = Bitmap.Config.ARGB_8888;
                    }
                    int sampleSize = e(header, width, height);
                    com.bumptech.glide.gifdecoder.a gifDecoder = this.f.a(this.g, header, byteBuffer, sampleSize);
                    gifDecoder.c(config);
                    gifDecoder.a();
                    Bitmap firstFrame = gifDecoder.getNextFrame();
                    if (firstFrame != null) {
                        int i = sampleSize;
                        d dVar = new d(new GifDrawable(this.c, gifDecoder, com.bumptech.glide.load.resource.c.a(), width, height, firstFrame));
                        if (Log.isLoggable("BufferGifDecoder", 2)) {
                            Log.v("BufferGifDecoder", "Decoded GIF from stream in " + com.bumptech.glide.util.e.a(startTime));
                        }
                        return dVar;
                    } else if (!Log.isLoggable("BufferGifDecoder", 2)) {
                        return null;
                    } else {
                        Log.v("BufferGifDecoder", "Decoded GIF from stream in " + com.bumptech.glide.util.e.a(startTime));
                        return null;
                    }
                }
            }
        } finally {
            if (Log.isLoggable("BufferGifDecoder", 2)) {
                Log.v("BufferGifDecoder", "Decoded GIF from stream in " + com.bumptech.glide.util.e.a(startTime));
            }
        }
    }

    private static int e(c gifHeader, int targetWidth, int targetHeight) {
        int exactSampleSize = Math.min(gifHeader.a() / targetHeight, gifHeader.d() / targetWidth);
        int sampleSize = Math.max(1, exactSampleSize == 0 ? 0 : Integer.highestOneBit(exactSampleSize));
        if (Log.isLoggable("BufferGifDecoder", 2) && sampleSize > 1) {
            Log.v("BufferGifDecoder", "Downsampling GIF, sampleSize: " + sampleSize + ", target dimens: [" + targetWidth + "x" + targetHeight + "], actual dimens: [" + gifHeader.d() + "x" + gifHeader.a() + "]");
        }
        return sampleSize;
    }

    @VisibleForTesting
    /* renamed from: com.bumptech.glide.load.resource.gif.a$a  reason: collision with other inner class name */
    /* compiled from: ByteBufferGifDecoder */
    public static class C0043a {
        C0043a() {
        }

        /* access modifiers changed from: package-private */
        public com.bumptech.glide.gifdecoder.a a(a.C0022a provider, c header, ByteBuffer data, int sampleSize) {
            return new com.bumptech.glide.gifdecoder.e(provider, header, data, sampleSize);
        }
    }

    @VisibleForTesting
    /* compiled from: ByteBufferGifDecoder */
    public static class b {
        private final Queue<d> a = j.e(0);

        b() {
        }

        /* access modifiers changed from: package-private */
        public synchronized d a(ByteBuffer buffer) {
            d result;
            result = this.a.poll();
            if (result == null) {
                result = new d();
            }
            return result.p(buffer);
        }

        /* access modifiers changed from: package-private */
        public synchronized void b(d parser) {
            parser.a();
            this.a.offer(parser);
        }
    }
}
