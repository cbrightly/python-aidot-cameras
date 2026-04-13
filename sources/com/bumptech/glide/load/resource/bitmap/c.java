package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.h;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.l;
import com.bumptech.glide.util.e;
import com.bumptech.glide.util.j;
import com.bumptech.glide.util.pool.a;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* compiled from: BitmapEncoder */
public class c implements l<Bitmap> {
    public static final h<Integer> a = h.f("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionQuality", 90);
    public static final h<Bitmap.CompressFormat> b = h.e("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionFormat");
    @Nullable
    private final b c;

    public c(@NonNull b arrayPool) {
        this.c = arrayPool;
    }

    /* renamed from: c */
    public boolean a(@NonNull t<Bitmap> resource, @NonNull File file, @NonNull i options) {
        OutputStream os;
        Bitmap bitmap = resource.get();
        Bitmap.CompressFormat format = d(bitmap, options);
        a.c("encode: [%dx%d] %s", Integer.valueOf(bitmap.getWidth()), Integer.valueOf(bitmap.getHeight()), format);
        try {
            long start = e.b();
            int quality = ((Integer) options.a(a)).intValue();
            boolean success = false;
            os = null;
            try {
                OutputStream os2 = new FileOutputStream(file);
                if (this.c != null) {
                    os2 = new com.bumptech.glide.load.data.c(os2, this.c);
                }
                bitmap.compress(format, quality, os2);
                os2.close();
                success = true;
                try {
                    os2.close();
                } catch (IOException e) {
                }
            } catch (IOException e2) {
                if (Log.isLoggable("BitmapEncoder", 3)) {
                    Log.d("BitmapEncoder", "Failed to encode Bitmap", e2);
                }
                if (os != null) {
                    os.close();
                }
            }
            if (Log.isLoggable("BitmapEncoder", 2)) {
                Log.v("BitmapEncoder", "Compressed with type: " + format + " of size " + j.g(bitmap) + " in " + e.a(start) + ", options format: " + options.a(b) + ", hasAlpha: " + bitmap.hasAlpha());
            }
            a.d();
            return success;
        } catch (Throwable th) {
            a.d();
            throw th;
        }
    }

    private Bitmap.CompressFormat d(Bitmap bitmap, i options) {
        Bitmap.CompressFormat format = (Bitmap.CompressFormat) options.a(b);
        if (format != null) {
            return format;
        }
        if (bitmap.hasAlpha()) {
            return Bitmap.CompressFormat.PNG;
        }
        return Bitmap.CompressFormat.JPEG;
    }

    @NonNull
    public com.bumptech.glide.load.c b(@NonNull i options) {
        return com.bumptech.glide.load.c.TRANSFORMED;
    }
}
