package com.bumptech.glide.load;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.data.ParcelFileDescriptorRewinder;
import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

/* compiled from: ImageHeaderParserUtils */
public final class e {

    /* compiled from: ImageHeaderParserUtils */
    public interface f {
        int a(ImageHeaderParser imageHeaderParser);
    }

    /* compiled from: ImageHeaderParserUtils */
    public interface g {
        ImageHeaderParser.ImageType a(ImageHeaderParser imageHeaderParser);
    }

    @NonNull
    public static ImageHeaderParser.ImageType e(@NonNull List<ImageHeaderParser> parsers, @Nullable InputStream is, @NonNull com.bumptech.glide.load.engine.bitmap_recycle.b byteArrayPool) {
        if (is == null) {
            return ImageHeaderParser.ImageType.UNKNOWN;
        }
        if (!is.markSupported()) {
            is = new RecyclableBufferedInputStream(is, byteArrayPool);
        }
        is.mark(5242880);
        return g(parsers, new a(is));
    }

    /* compiled from: ImageHeaderParserUtils */
    public class a implements g {
        final /* synthetic */ InputStream a;

        a(InputStream inputStream) {
            this.a = inputStream;
        }

        public ImageHeaderParser.ImageType a(ImageHeaderParser parser) {
            try {
                return parser.b(this.a);
            } finally {
                this.a.reset();
            }
        }
    }

    @NonNull
    public static ImageHeaderParser.ImageType f(@NonNull List<ImageHeaderParser> parsers, @Nullable ByteBuffer buffer) {
        if (buffer == null) {
            return ImageHeaderParser.ImageType.UNKNOWN;
        }
        return g(parsers, new b(buffer));
    }

    /* compiled from: ImageHeaderParserUtils */
    public class b implements g {
        final /* synthetic */ ByteBuffer a;

        b(ByteBuffer byteBuffer) {
            this.a = byteBuffer;
        }

        public ImageHeaderParser.ImageType a(ImageHeaderParser parser) {
            return parser.a(this.a);
        }
    }

    @RequiresApi(21)
    @NonNull
    public static ImageHeaderParser.ImageType d(@NonNull List<ImageHeaderParser> parsers, @NonNull ParcelFileDescriptorRewinder parcelFileDescriptorRewinder, @NonNull com.bumptech.glide.load.engine.bitmap_recycle.b byteArrayPool) {
        return g(parsers, new c(parcelFileDescriptorRewinder, byteArrayPool));
    }

    /* compiled from: ImageHeaderParserUtils */
    public class c implements g {
        final /* synthetic */ ParcelFileDescriptorRewinder a;
        final /* synthetic */ com.bumptech.glide.load.engine.bitmap_recycle.b b;

        c(ParcelFileDescriptorRewinder parcelFileDescriptorRewinder, com.bumptech.glide.load.engine.bitmap_recycle.b bVar) {
            this.a = parcelFileDescriptorRewinder;
            this.b = bVar;
        }

        public ImageHeaderParser.ImageType a(ImageHeaderParser parser) {
            InputStream is = null;
            try {
                InputStream is2 = new RecyclableBufferedInputStream(new FileInputStream(this.a.a().getFileDescriptor()), this.b);
                ImageHeaderParser.ImageType b2 = parser.b(is2);
                try {
                    is2.close();
                } catch (IOException e) {
                }
                this.a.a();
                return b2;
            } catch (Throwable th) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e2) {
                    }
                }
                this.a.a();
                throw th;
            }
        }
    }

    @NonNull
    private static ImageHeaderParser.ImageType g(@NonNull List<ImageHeaderParser> parsers, g reader) {
        int size = parsers.size();
        for (int i = 0; i < size; i++) {
            ImageHeaderParser.ImageType type = reader.a(parsers.get(i));
            if (type != ImageHeaderParser.ImageType.UNKNOWN) {
                return type;
            }
        }
        return ImageHeaderParser.ImageType.UNKNOWN;
    }

    public static int b(@NonNull List<ImageHeaderParser> parsers, @Nullable InputStream is, @NonNull com.bumptech.glide.load.engine.bitmap_recycle.b byteArrayPool) {
        if (is == null) {
            return -1;
        }
        if (!is.markSupported()) {
            is = new RecyclableBufferedInputStream(is, byteArrayPool);
        }
        is.mark(5242880);
        return c(parsers, new d(is, byteArrayPool));
    }

    /* compiled from: ImageHeaderParserUtils */
    public class d implements f {
        final /* synthetic */ InputStream a;
        final /* synthetic */ com.bumptech.glide.load.engine.bitmap_recycle.b b;

        d(InputStream inputStream, com.bumptech.glide.load.engine.bitmap_recycle.b bVar) {
            this.a = inputStream;
            this.b = bVar;
        }

        public int a(ImageHeaderParser parser) {
            try {
                return parser.c(this.a, this.b);
            } finally {
                this.a.reset();
            }
        }
    }

    @RequiresApi(21)
    public static int a(@NonNull List<ImageHeaderParser> parsers, @NonNull ParcelFileDescriptorRewinder parcelFileDescriptorRewinder, @NonNull com.bumptech.glide.load.engine.bitmap_recycle.b byteArrayPool) {
        return c(parsers, new C0024e(parcelFileDescriptorRewinder, byteArrayPool));
    }

    /* renamed from: com.bumptech.glide.load.e$e  reason: collision with other inner class name */
    /* compiled from: ImageHeaderParserUtils */
    public class C0024e implements f {
        final /* synthetic */ ParcelFileDescriptorRewinder a;
        final /* synthetic */ com.bumptech.glide.load.engine.bitmap_recycle.b b;

        C0024e(ParcelFileDescriptorRewinder parcelFileDescriptorRewinder, com.bumptech.glide.load.engine.bitmap_recycle.b bVar) {
            this.a = parcelFileDescriptorRewinder;
            this.b = bVar;
        }

        public int a(ImageHeaderParser parser) {
            InputStream is = null;
            try {
                InputStream is2 = new RecyclableBufferedInputStream(new FileInputStream(this.a.a().getFileDescriptor()), this.b);
                int c = parser.c(is2, this.b);
                try {
                    is2.close();
                } catch (IOException e) {
                }
                this.a.a();
                return c;
            } catch (Throwable th) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e2) {
                    }
                }
                this.a.a();
                throw th;
            }
        }
    }

    private static int c(@NonNull List<ImageHeaderParser> parsers, f reader) {
        int size = parsers.size();
        for (int i = 0; i < size; i++) {
            int orientation = reader.a(parsers.get(i));
            if (orientation != -1) {
                return orientation;
            }
        }
        return -1;
    }
}
