package com.bumptech.glide.load.resource.bitmap;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaDataSource;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.h;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class VideoDecoder<T> implements k<T, Bitmap> {
    public static final h<Long> a = h.a("com.bumptech.glide.load.resource.bitmap.VideoBitmapDecode.TargetFrame", -1L, new a());
    public static final h<Integer> b = h.a("com.bumptech.glide.load.resource.bitmap.VideoBitmapDecode.FrameOption", 2, new b());
    private static final e c = new e();
    private final f<T> d;
    private final com.bumptech.glide.load.engine.bitmap_recycle.e e;
    private final e f;

    @VisibleForTesting
    public interface f<T> {
        void a(MediaMetadataRetriever mediaMetadataRetriever, T t);
    }

    public class a implements h.b<Long> {
        private final ByteBuffer a = ByteBuffer.allocate(8);

        a() {
        }

        /* renamed from: b */
        public void a(@NonNull byte[] keyBytes, @NonNull Long value, @NonNull MessageDigest messageDigest) {
            messageDigest.update(keyBytes);
            synchronized (this.a) {
                this.a.position(0);
                messageDigest.update(this.a.putLong(value.longValue()).array());
            }
        }
    }

    public class b implements h.b<Integer> {
        private final ByteBuffer a = ByteBuffer.allocate(4);

        b() {
        }

        /* renamed from: b */
        public void a(@NonNull byte[] keyBytes, @NonNull Integer value, @NonNull MessageDigest messageDigest) {
            if (value != null) {
                messageDigest.update(keyBytes);
                synchronized (this.a) {
                    this.a.position(0);
                    messageDigest.update(this.a.putInt(value.intValue()).array());
                }
            }
        }
    }

    public static k<AssetFileDescriptor, Bitmap> c(com.bumptech.glide.load.engine.bitmap_recycle.e bitmapPool) {
        return new VideoDecoder(bitmapPool, new c((a) null));
    }

    public static k<ParcelFileDescriptor, Bitmap> h(com.bumptech.glide.load.engine.bitmap_recycle.e bitmapPool) {
        return new VideoDecoder(bitmapPool, new g());
    }

    @RequiresApi(api = 23)
    public static k<ByteBuffer, Bitmap> d(com.bumptech.glide.load.engine.bitmap_recycle.e bitmapPool) {
        return new VideoDecoder(bitmapPool, new d());
    }

    VideoDecoder(com.bumptech.glide.load.engine.bitmap_recycle.e bitmapPool, f<T> initializer) {
        this(bitmapPool, initializer, c);
    }

    @VisibleForTesting
    VideoDecoder(com.bumptech.glide.load.engine.bitmap_recycle.e bitmapPool, f<T> initializer, e factory) {
        this.e = bitmapPool;
        this.d = initializer;
        this.f = factory;
    }

    public boolean a(@NonNull T t, @NonNull i options) {
        return true;
    }

    public t<Bitmap> b(@NonNull T resource, int outWidth, int outHeight, @NonNull i options) {
        Integer frameOption;
        l downsampleStrategy;
        i iVar = options;
        long frameTimeMicros = ((Long) iVar.a(a)).longValue();
        if (frameTimeMicros >= 0 || frameTimeMicros == -1) {
            Integer frameOption2 = (Integer) iVar.a(b);
            if (frameOption2 == null) {
                frameOption = 2;
            } else {
                frameOption = frameOption2;
            }
            l downsampleStrategy2 = (l) iVar.a(l.h);
            if (downsampleStrategy2 == null) {
                downsampleStrategy = l.g;
            } else {
                downsampleStrategy = downsampleStrategy2;
            }
            MediaMetadataRetriever mediaMetadataRetriever = this.f.a();
            try {
                try {
                    this.d.a(mediaMetadataRetriever, resource);
                    Bitmap result = e(mediaMetadataRetriever, frameTimeMicros, frameOption.intValue(), outWidth, outHeight, downsampleStrategy);
                    mediaMetadataRetriever.release();
                    return e.c(result, this.e);
                } catch (Throwable th) {
                    th = th;
                    mediaMetadataRetriever.release();
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                T t = resource;
                mediaMetadataRetriever.release();
                throw th;
            }
        } else {
            throw new IllegalArgumentException("Requested frame must be non-negative, or DEFAULT_FRAME, given: " + frameTimeMicros);
        }
    }

    @Nullable
    private static Bitmap e(MediaMetadataRetriever mediaMetadataRetriever, long frameTimeMicros, int frameOption, int outWidth, int outHeight, l strategy) {
        Bitmap result = null;
        if (!(Build.VERSION.SDK_INT < 27 || outWidth == Integer.MIN_VALUE || outHeight == Integer.MIN_VALUE || strategy == l.f)) {
            result = g(mediaMetadataRetriever, frameTimeMicros, frameOption, outWidth, outHeight, strategy);
        }
        if (result == null) {
            result = f(mediaMetadataRetriever, frameTimeMicros, frameOption);
        }
        if (result != null) {
            return result;
        }
        throw new VideoDecoderException();
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    @android.annotation.TargetApi(27)
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.graphics.Bitmap g(android.media.MediaMetadataRetriever r14, long r15, int r17, int r18, int r19, com.bumptech.glide.load.resource.bitmap.l r20) {
        /*
            r7 = r14
            r0 = 18
            java.lang.String r0 = r14.extractMetadata(r0)     // Catch:{ all -> 0x0052 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ all -> 0x0052 }
            r1 = 19
            java.lang.String r1 = r14.extractMetadata(r1)     // Catch:{ all -> 0x0052 }
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ all -> 0x0052 }
            r2 = 24
            java.lang.String r2 = r14.extractMetadata(r2)     // Catch:{ all -> 0x0052 }
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ all -> 0x0052 }
            r8 = r2
            r2 = 90
            if (r8 == r2) goto L_0x002b
            r2 = 270(0x10e, float:3.78E-43)
            if (r8 != r2) goto L_0x0029
            goto L_0x002b
        L_0x0029:
            r9 = r1
            goto L_0x002f
        L_0x002b:
            r2 = r0
            r0 = r1
            r1 = r2
            r9 = r1
        L_0x002f:
            r10 = r18
            r11 = r19
            r12 = r20
            float r1 = r12.b(r0, r9, r10, r11)     // Catch:{ all -> 0x0050 }
            r13 = r1
            float r1 = (float) r0     // Catch:{ all -> 0x0050 }
            float r1 = r1 * r13
            int r5 = java.lang.Math.round(r1)     // Catch:{ all -> 0x0050 }
            float r1 = (float) r9     // Catch:{ all -> 0x0050 }
            float r1 = r1 * r13
            int r6 = java.lang.Math.round(r1)     // Catch:{ all -> 0x0050 }
            r1 = r14
            r2 = r15
            r4 = r17
            android.graphics.Bitmap r1 = r1.getScaledFrameAtTime(r2, r4, r5, r6)     // Catch:{ all -> 0x0050 }
            return r1
        L_0x0050:
            r0 = move-exception
            goto L_0x0059
        L_0x0052:
            r0 = move-exception
            r10 = r18
            r11 = r19
            r12 = r20
        L_0x0059:
            r1 = 3
            java.lang.String r2 = "VideoDecoder"
            boolean r1 = android.util.Log.isLoggable(r2, r1)
            if (r1 == 0) goto L_0x0067
            java.lang.String r1 = "Exception trying to decode a scaled frame on oreo+, falling back to a fullsize frame"
            android.util.Log.d(r2, r1, r0)
        L_0x0067:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.resource.bitmap.VideoDecoder.g(android.media.MediaMetadataRetriever, long, int, int, int, com.bumptech.glide.load.resource.bitmap.l):android.graphics.Bitmap");
    }

    private static Bitmap f(MediaMetadataRetriever mediaMetadataRetriever, long frameTimeMicros, int frameOption) {
        return mediaMetadataRetriever.getFrameAtTime(frameTimeMicros, frameOption);
    }

    @VisibleForTesting
    public static class e {
        e() {
        }

        public MediaMetadataRetriever a() {
            return new MediaMetadataRetriever();
        }
    }

    public static final class c implements f<AssetFileDescriptor> {
        private c() {
        }

        /* synthetic */ c(a x0) {
            this();
        }

        /* renamed from: b */
        public void a(MediaMetadataRetriever retriever, AssetFileDescriptor data) {
            retriever.setDataSource(data.getFileDescriptor(), data.getStartOffset(), data.getLength());
        }
    }

    public static final class g implements f<ParcelFileDescriptor> {
        g() {
        }

        /* renamed from: b */
        public void a(MediaMetadataRetriever retriever, ParcelFileDescriptor data) {
            retriever.setDataSource(data.getFileDescriptor());
        }
    }

    @RequiresApi(23)
    public static final class d implements f<ByteBuffer> {
        d() {
        }

        public class a extends MediaDataSource {
            final /* synthetic */ ByteBuffer c;

            a(ByteBuffer byteBuffer) {
                this.c = byteBuffer;
            }

            public int readAt(long position, byte[] buffer, int offset, int size) {
                if (position >= ((long) this.c.limit())) {
                    return -1;
                }
                this.c.position((int) position);
                int numBytesRead = Math.min(size, this.c.remaining());
                this.c.get(buffer, offset, numBytesRead);
                return numBytesRead;
            }

            public long getSize() {
                return (long) this.c.limit();
            }

            public void close() {
            }
        }

        /* renamed from: b */
        public void a(MediaMetadataRetriever retriever, ByteBuffer data) {
            retriever.setDataSource(new a(data));
        }
    }

    public static final class VideoDecoderException extends RuntimeException {
        private static final long serialVersionUID = -2556382523004027815L;

        VideoDecoderException() {
            super("MediaMetadataRetriever failed to retrieve a frame without throwing, check the adb logs for .*MetadataRetriever.* prior to this exception for details");
        }
    }
}
