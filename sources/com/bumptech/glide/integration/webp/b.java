package com.bumptech.glide.integration.webp;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.MotionEventCompat;
import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;
import com.bumptech.glide.util.i;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* compiled from: WebpHeaderParser */
public class b {
    public static final boolean a = f();

    /* compiled from: WebpHeaderParser */
    public interface c {
        int a();

        int getUInt16();

        long skip(long j);
    }

    public static boolean f() {
        int i = Build.VERSION.SDK_INT;
        if (i < 17) {
            return false;
        }
        if (i == 17) {
            byte[] decodedBytes = Base64.decode("UklGRkoAAABXRUJQVlA4WAoAAAAQAAAAAAAAAAAAQUxQSAwAAAARBxAR/Q9ERP8DAABWUDggGAAAABQBAJ0BKgEAAQAAAP4AAA3AAP7mtQAAAA==", 0);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length, opts);
            if (!(opts.outHeight == 1 && opts.outWidth == 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean h(e imageType) {
        return imageType == e.WEBP_SIMPLE || imageType == e.WEBP_LOSSLESS || imageType == e.WEBP_LOSSLESS_WITH_ALPHA || imageType == e.WEBP_EXTENDED || imageType == e.WEBP_EXTENDED_WITH_ALPHA;
    }

    public static boolean g(e imageType) {
        return (imageType == e.NONE_WEBP || imageType == e.WEBP_SIMPLE) ? false : true;
    }

    public static boolean e(e imageType) {
        return imageType == e.WEBP_EXTENDED_ANIMATED;
    }

    public static e b(InputStream is, com.bumptech.glide.load.engine.bitmap_recycle.b byteArrayPool) {
        if (is == null) {
            return e.NONE_WEBP;
        }
        if (!is.markSupported()) {
            is = new RecyclableBufferedInputStream(is, byteArrayPool);
        }
        is.mark(21);
        try {
            return a(new d((InputStream) i.d(is)));
        } finally {
            is.reset();
        }
    }

    public static e c(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return e.NONE_WEBP;
        }
        return a(new C0023b((ByteBuffer) i.d(byteBuffer)));
    }

    public static e d(byte[] headers, int offset, int headerSize) {
        return a(new a(headers, offset, headerSize));
    }

    private static e a(c reader) {
        if ((((reader.getUInt16() << 16) & SupportMenu.CATEGORY_MASK) | (reader.getUInt16() & 65535)) != 1380533830) {
            return e.NONE_WEBP;
        }
        reader.skip(4);
        if ((((reader.getUInt16() << 16) & SupportMenu.CATEGORY_MASK) | (reader.getUInt16() & 65535)) != 1464156752) {
            return e.NONE_WEBP;
        }
        int fourthFourBytes = (-65536 & (reader.getUInt16() << 16)) | (65535 & reader.getUInt16());
        if (fourthFourBytes == 1448097824) {
            return e.WEBP_SIMPLE;
        }
        if (fourthFourBytes == 1448097868) {
            reader.skip(4);
            return (reader.a() & 8) != 0 ? e.WEBP_LOSSLESS_WITH_ALPHA : e.WEBP_LOSSLESS;
        } else if (fourthFourBytes != 1448097880) {
            return e.NONE_WEBP;
        } else {
            reader.skip(4);
            int meta = reader.a();
            if ((meta & 2) != 0) {
                return e.WEBP_EXTENDED_ANIMATED;
            }
            if ((meta & 16) != 0) {
                return e.WEBP_EXTENDED_WITH_ALPHA;
            }
            return e.WEBP_EXTENDED;
        }
    }

    /* compiled from: WebpHeaderParser */
    public enum e {
        WEBP_SIMPLE(false, false),
        WEBP_LOSSLESS(false, false),
        WEBP_LOSSLESS_WITH_ALPHA(true, false),
        WEBP_EXTENDED(false, false),
        WEBP_EXTENDED_WITH_ALPHA(true, false),
        WEBP_EXTENDED_ANIMATED(false, true),
        NONE_WEBP(false, false);
        
        private final boolean hasAlpha;
        private final boolean hasAnimation;

        private e(boolean hasAlpha2, boolean hasAnimation2) {
            this.hasAlpha = hasAlpha2;
            this.hasAnimation = hasAnimation2;
        }

        public boolean hasAlpha() {
            return this.hasAlpha;
        }

        public boolean hasAnimation() {
            return this.hasAnimation;
        }
    }

    /* compiled from: WebpHeaderParser */
    public static final class a implements c {
        private final byte[] a;
        private final int b;
        private final int c;
        private int d;

        a(byte[] data, int offset, int size) {
            this.a = data;
            this.b = offset;
            this.c = size;
            this.d = offset;
        }

        public int getUInt16() {
            return ((a() << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (a() & 255);
        }

        public long skip(long total) {
            int toSkip = (int) Math.min((long) ((this.b + this.c) - this.d), total);
            this.d += toSkip;
            return (long) toSkip;
        }

        public int a() {
            int i = this.d;
            if (i >= this.b + this.c) {
                return -1;
            }
            byte[] bArr = this.a;
            this.d = i + 1;
            return bArr[i];
        }
    }

    /* renamed from: com.bumptech.glide.integration.webp.b$b  reason: collision with other inner class name */
    /* compiled from: WebpHeaderParser */
    public static final class C0023b implements c {
        private final ByteBuffer a;

        C0023b(ByteBuffer byteBuffer) {
            this.a = byteBuffer;
            byteBuffer.order(ByteOrder.BIG_ENDIAN);
        }

        public int getUInt16() {
            return ((a() << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (a() & 255);
        }

        public long skip(long total) {
            int toSkip = (int) Math.min((long) this.a.remaining(), total);
            ByteBuffer byteBuffer = this.a;
            byteBuffer.position(byteBuffer.position() + toSkip);
            return (long) toSkip;
        }

        public int a() {
            if (this.a.remaining() < 1) {
                return -1;
            }
            return this.a.get();
        }
    }

    /* compiled from: WebpHeaderParser */
    public static final class d implements c {
        private final InputStream a;

        d(InputStream is) {
            this.a = is;
        }

        public int getUInt16() {
            return ((this.a.read() << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (this.a.read() & 255);
        }

        public long skip(long total) {
            if (total < 0) {
                return 0;
            }
            long toSkip = total;
            while (toSkip > 0) {
                long skipped = this.a.skip(toSkip);
                if (skipped > 0) {
                    toSkip -= skipped;
                } else if (this.a.read() == -1) {
                    break;
                } else {
                    toSkip--;
                }
            }
            return total - toSkip;
        }

        public int a() {
            return this.a.read();
        }
    }
}
