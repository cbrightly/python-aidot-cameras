package com.bumptech.glide.load.resource.gif;

import android.util.Log;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.e;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.k;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

/* compiled from: StreamGifDecoder */
public class i implements k<InputStream, GifDrawable> {
    private final List<ImageHeaderParser> a;
    private final k<ByteBuffer, GifDrawable> b;
    private final b c;

    public i(List<ImageHeaderParser> parsers, k<ByteBuffer, GifDrawable> byteBufferDecoder, b byteArrayPool) {
        this.a = parsers;
        this.b = byteBufferDecoder;
        this.c = byteArrayPool;
    }

    /* renamed from: d */
    public boolean a(@NonNull InputStream source, @NonNull com.bumptech.glide.load.i options) {
        return !((Boolean) options.a(h.b)).booleanValue() && e.e(this.a, source, this.c) == ImageHeaderParser.ImageType.GIF;
    }

    /* renamed from: c */
    public t<GifDrawable> b(@NonNull InputStream source, int width, int height, @NonNull com.bumptech.glide.load.i options) {
        byte[] data = e(source);
        if (data == null) {
            return null;
        }
        return this.b.b(ByteBuffer.wrap(data), width, height, options);
    }

    private static byte[] e(InputStream is) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(16384);
        try {
            byte[] data = new byte[16384];
            while (true) {
                int read = is.read(data);
                int nRead = read;
                if (read != -1) {
                    buffer.write(data, 0, nRead);
                } else {
                    buffer.flush();
                    return buffer.toByteArray();
                }
            }
        } catch (IOException e) {
            if (!Log.isLoggable("StreamGifDecoder", 5)) {
                return null;
            }
            Log.w("StreamGifDecoder", "Error reading data from stream", e);
            return null;
        }
    }
}
