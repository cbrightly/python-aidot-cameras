package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.ParcelFileDescriptor;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.data.ParcelFileDescriptorRewinder;
import com.bumptech.glide.load.data.k;
import com.bumptech.glide.load.e;
import com.bumptech.glide.util.i;
import java.io.InputStream;
import java.util.List;

/* compiled from: ImageReader */
public interface s {
    @Nullable
    Bitmap a(BitmapFactory.Options options);

    void b();

    int c();

    ImageHeaderParser.ImageType d();

    /* compiled from: ImageReader */
    public static final class a implements s {
        private final k a;
        private final com.bumptech.glide.load.engine.bitmap_recycle.b b;
        private final List<ImageHeaderParser> c;

        a(InputStream is, List<ImageHeaderParser> parsers, com.bumptech.glide.load.engine.bitmap_recycle.b byteArrayPool) {
            this.b = (com.bumptech.glide.load.engine.bitmap_recycle.b) i.d(byteArrayPool);
            this.c = (List) i.d(parsers);
            this.a = new k(is, byteArrayPool);
        }

        @Nullable
        public Bitmap a(BitmapFactory.Options options) {
            return BitmapFactory.decodeStream(this.a.a(), (Rect) null, options);
        }

        public ImageHeaderParser.ImageType d() {
            return e.e(this.c, this.a.a(), this.b);
        }

        public int c() {
            return e.b(this.c, this.a.a(), this.b);
        }

        public void b() {
            this.a.c();
        }
    }

    @RequiresApi(21)
    /* compiled from: ImageReader */
    public static final class b implements s {
        private final com.bumptech.glide.load.engine.bitmap_recycle.b a;
        private final List<ImageHeaderParser> b;
        private final ParcelFileDescriptorRewinder c;

        b(ParcelFileDescriptor parcelFileDescriptor, List<ImageHeaderParser> parsers, com.bumptech.glide.load.engine.bitmap_recycle.b byteArrayPool) {
            this.a = (com.bumptech.glide.load.engine.bitmap_recycle.b) i.d(byteArrayPool);
            this.b = (List) i.d(parsers);
            this.c = new ParcelFileDescriptorRewinder(parcelFileDescriptor);
        }

        @Nullable
        public Bitmap a(BitmapFactory.Options options) {
            return BitmapFactory.decodeFileDescriptor(this.c.a().getFileDescriptor(), (Rect) null, options);
        }

        public ImageHeaderParser.ImageType d() {
            return e.d(this.b, this.c, this.a);
        }

        public int c() {
            return e.a(this.b, this.c, this.a);
        }

        public void b() {
        }
    }
}
