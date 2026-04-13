package com.bumptech.glide.integration.webp.decoder;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import androidx.annotation.Nullable;
import com.bumptech.glide.integration.webp.WebpBitmapFactory;
import com.bumptech.glide.integration.webp.b;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.h;
import com.bumptech.glide.load.resource.bitmap.l;
import com.bumptech.glide.load.resource.bitmap.m;
import com.bumptech.glide.load.resource.bitmap.z;
import com.bumptech.glide.util.i;
import com.meituan.robust.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Queue;

/* compiled from: WebpDownsampler */
public final class j {
    public static final h<Boolean> a = h.f("com.bumptech.glide.integration.webp.decoder.WebpDownsampler.DisableDecoder", false);
    private static final m.b b = new a();
    private static final Queue<BitmapFactory.Options> c = com.bumptech.glide.util.j.e(0);
    private final e d;
    private final DisplayMetrics e;
    private final b f;
    private final List<ImageHeaderParser> g;

    /* compiled from: WebpDownsampler */
    public static final class a implements m.b {
        a() {
        }

        public void b() {
        }

        public void a(e bitmapPool, Bitmap downsampled) {
        }
    }

    public j(List<ImageHeaderParser> parsers, DisplayMetrics displayMetrics, e bitmapPool, b byteArrayPool) {
        this.g = parsers;
        this.e = (DisplayMetrics) i.d(displayMetrics);
        this.d = (e) i.d(bitmapPool);
        this.f = (b) i.d(byteArrayPool);
    }

    public boolean l(InputStream is, com.bumptech.glide.load.i options) {
        if (((Boolean) options.a(a)).booleanValue() || com.bumptech.glide.integration.webp.b.a) {
            return false;
        }
        b.e webpType = com.bumptech.glide.integration.webp.b.b(is, this.f);
        if (!com.bumptech.glide.integration.webp.b.h(webpType) || webpType == b.e.WEBP_SIMPLE) {
            return false;
        }
        return true;
    }

    public boolean m(ByteBuffer byteBuffer, com.bumptech.glide.load.i options) {
        if (((Boolean) options.a(a)).booleanValue() || com.bumptech.glide.integration.webp.b.a) {
            return false;
        }
        b.e webpType = com.bumptech.glide.integration.webp.b.c(byteBuffer);
        if (!com.bumptech.glide.integration.webp.b.h(webpType) || webpType == b.e.WEBP_SIMPLE) {
            return false;
        }
        return true;
    }

    public t<Bitmap> d(InputStream is, int outWidth, int outHeight, com.bumptech.glide.load.i options) {
        return e(is, outWidth, outHeight, options, b);
    }

    public t<Bitmap> e(InputStream is, int requestedWidth, int requestedHeight, com.bumptech.glide.load.i options, m.b callbacks) {
        com.bumptech.glide.load.i iVar = options;
        Class cls = byte[].class;
        i.a(is.markSupported(), "You must provide an InputStream that supports mark()");
        byte[] bytesForOptions = (byte[]) this.f.e(65536, cls);
        BitmapFactory.Options bitmapFactoryOptions = i();
        bitmapFactoryOptions.inTempStorage = bytesForOptions;
        com.bumptech.glide.load.b decodeFormat = (com.bumptech.glide.load.b) iVar.a(m.a);
        l downsampleStrategy = (l) iVar.a(m.c);
        boolean fixBitmapToRequestedDimensions = ((Boolean) iVar.a(m.d)).booleanValue();
        h hVar = m.e;
        try {
            return com.bumptech.glide.load.resource.bitmap.e.c(f(is, bitmapFactoryOptions, downsampleStrategy, decodeFormat, iVar.a(hVar) != null && ((Boolean) iVar.a(hVar)).booleanValue(), requestedWidth, requestedHeight, fixBitmapToRequestedDimensions, callbacks), this.d);
        } finally {
            q(bitmapFactoryOptions);
            this.f.g(bytesForOptions, cls);
        }
    }

    private Bitmap f(InputStream is, BitmapFactory.Options options, l downsampleStrategy, com.bumptech.glide.load.b decodeFormat, boolean isHardwareConfigAllowed, int requestedWidth, int requestedHeight, boolean fixBitmapToRequestedDimensions, m.b callbacks) {
        boolean isHardwareConfigAllowed2;
        j jVar;
        String str;
        int expectedHeight;
        int expectedWidth;
        int expectedWidth2;
        InputStream inputStream = is;
        BitmapFactory.Options options2 = options;
        m.b bVar = callbacks;
        long startTime = com.bumptech.glide.util.e.b();
        int[] sourceDimensions = j(inputStream, options2, bVar, this.d);
        boolean z = false;
        int sourceWidth = sourceDimensions[0];
        int sourceHeight = sourceDimensions[1];
        String sourceMimeType = options2.outMimeType;
        if (sourceWidth == -1 || sourceHeight == -1) {
            isHardwareConfigAllowed2 = false;
        } else {
            isHardwareConfigAllowed2 = isHardwareConfigAllowed;
        }
        int orientation = com.bumptech.glide.load.e.b(this.g, inputStream, this.f);
        int degreesToRotate = z.j(orientation);
        boolean isExifOrientationRequired = z.m(orientation);
        int i = requestedWidth;
        int targetWidth = i == Integer.MIN_VALUE ? sourceWidth : i;
        int i2 = requestedHeight;
        int targetHeight = i2 == Integer.MIN_VALUE ? sourceHeight : i2;
        ImageHeaderParser.ImageType imageType = com.bumptech.glide.load.e.e(this.g, inputStream, this.f);
        e eVar = this.d;
        ImageHeaderParser.ImageType imageType2 = imageType;
        c(imageType, is, callbacks, eVar, downsampleStrategy, degreesToRotate, sourceWidth, sourceHeight, targetWidth, targetHeight, options);
        int orientation2 = orientation;
        String sourceMimeType2 = sourceMimeType;
        int sourceHeight2 = sourceHeight;
        int sourceWidth2 = sourceWidth;
        m.b bVar2 = bVar;
        BitmapFactory.Options options3 = options2;
        b(is, decodeFormat, isHardwareConfigAllowed2, isExifOrientationRequired, options, targetWidth, targetHeight);
        if (Build.VERSION.SDK_INT >= 19) {
            z = true;
        }
        boolean isKitKatOrGreater = z;
        if (options3.inSampleSize == 1 || isKitKatOrGreater) {
            jVar = this;
            if (jVar.u(imageType2)) {
                if (!fixBitmapToRequestedDimensions || !isKitKatOrGreater) {
                    float densityMultiplier = n(options) != 0 ? ((float) options3.inTargetDensity) / ((float) options3.inDensity) : 1.0f;
                    int sampleSize = options3.inSampleSize;
                    int downsampledHeight = (int) Math.ceil((double) (((float) sourceHeight2) / ((float) sampleSize)));
                    int expectedWidth3 = Math.round(((float) ((int) Math.ceil((double) (((float) sourceWidth2) / ((float) sampleSize))))) * densityMultiplier);
                    int expectedHeight2 = Math.round(((float) downsampledHeight) * densityMultiplier);
                    boolean z2 = isKitKatOrGreater;
                    str = "WebpDownsampler";
                    if (Log.isLoggable(str, 2)) {
                        StringBuilder sb = new StringBuilder();
                        int i3 = downsampledHeight;
                        sb.append("Calculated target [");
                        sb.append(expectedWidth3);
                        sb.append("x");
                        sb.append(expectedHeight2);
                        expectedWidth2 = expectedWidth3;
                        sb.append("] for source [");
                        sb.append(sourceWidth2);
                        sb.append("x");
                        sb.append(sourceHeight2);
                        sb.append("], sampleSize: ");
                        sb.append(sampleSize);
                        sb.append(", targetDensity: ");
                        sb.append(options3.inTargetDensity);
                        sb.append(", density: ");
                        sb.append(options3.inDensity);
                        sb.append(", density multiplier: ");
                        sb.append(densityMultiplier);
                        Log.v(str, sb.toString());
                    } else {
                        expectedWidth2 = expectedWidth3;
                    }
                    expectedHeight = expectedHeight2;
                    expectedWidth = expectedWidth2;
                } else {
                    expectedWidth = targetWidth;
                    expectedHeight = targetHeight;
                    boolean z3 = isKitKatOrGreater;
                    str = "WebpDownsampler";
                }
                if (expectedWidth > 0 && expectedHeight > 0) {
                    t(options3, jVar.d, expectedWidth, expectedHeight);
                }
            } else {
                str = "WebpDownsampler";
            }
        } else {
            jVar = this;
            boolean z4 = isKitKatOrGreater;
            str = "WebpDownsampler";
            ImageHeaderParser.ImageType imageType3 = imageType2;
        }
        Bitmap downsampled = g(is, options3, bVar2, jVar.d);
        bVar2.a(jVar.d, downsampled);
        if (Log.isLoggable(str, 2)) {
            o(sourceWidth2, sourceHeight2, sourceMimeType2, options, downsampled, requestedWidth, requestedHeight, startTime);
        }
        Bitmap rotated = null;
        if (downsampled != null) {
            downsampled.setDensity(jVar.e.densityDpi);
            rotated = z.n(jVar.d, downsampled, orientation2);
            if (!downsampled.equals(rotated)) {
                jVar.d.b(downsampled);
            }
        }
        return rotated;
    }

    static void c(ImageHeaderParser.ImageType imageType, InputStream is, m.b decodeCallbacks, e bitmapPool, l downsampleStrategy, int degreesToRotate, int sourceWidth, int sourceHeight, int targetWidth, int targetHeight, BitmapFactory.Options options) {
        float exactScaleFactor;
        int scaleFactor;
        int powerOfTwoHeight;
        int powerOfTwoWidth;
        int heightScaleFactor;
        ImageHeaderParser.ImageType imageType2 = imageType;
        l lVar = downsampleStrategy;
        int i = degreesToRotate;
        int i2 = sourceWidth;
        int i3 = sourceHeight;
        int i4 = targetWidth;
        int i5 = targetHeight;
        BitmapFactory.Options options2 = options;
        if (i2 <= 0) {
            l lVar2 = lVar;
        } else if (i3 <= 0) {
            l lVar3 = lVar;
        } else {
            if (i == 90 || i == 270) {
                exactScaleFactor = lVar.b(i3, i2, i4, i5);
            } else {
                exactScaleFactor = lVar.b(i2, i3, i4, i5);
            }
            if (exactScaleFactor > 0.0f) {
                l.g rounding = lVar.a(i2, i3, i4, i5);
                if (rounding != null) {
                    int outWidth = s((double) (((float) i2) * exactScaleFactor));
                    int outHeight = s((double) (((float) i3) * exactScaleFactor));
                    int widthScaleFactor = i2 / outWidth;
                    int heightScaleFactor2 = i3 / outHeight;
                    l.g gVar = l.g.MEMORY;
                    if (rounding == gVar) {
                        scaleFactor = Math.max(widthScaleFactor, heightScaleFactor2);
                    } else {
                        scaleFactor = Math.min(widthScaleFactor, heightScaleFactor2);
                    }
                    int i6 = outWidth;
                    int i7 = outHeight;
                    int powerOfTwoSampleSize = Math.max(1, Integer.highestOneBit(scaleFactor));
                    if (rounding == gVar && ((float) powerOfTwoSampleSize) < 1.0f / exactScaleFactor) {
                        powerOfTwoSampleSize <<= 1;
                    }
                    options2.inSampleSize = powerOfTwoSampleSize;
                    if (imageType2 == ImageHeaderParser.ImageType.JPEG) {
                        int nativeScaling = Math.min(powerOfTwoSampleSize, 8);
                        l.g gVar2 = rounding;
                        powerOfTwoWidth = (int) Math.ceil((double) (((float) i2) / ((float) nativeScaling)));
                        int i8 = widthScaleFactor;
                        heightScaleFactor = heightScaleFactor2;
                        powerOfTwoHeight = (int) Math.ceil((double) (((float) i3) / ((float) nativeScaling)));
                        int secondaryScaling = powerOfTwoSampleSize / 8;
                        if (secondaryScaling > 0) {
                            powerOfTwoWidth /= secondaryScaling;
                            powerOfTwoHeight /= secondaryScaling;
                        }
                        InputStream inputStream = is;
                    } else {
                        int i9 = widthScaleFactor;
                        heightScaleFactor = heightScaleFactor2;
                        if (imageType2 == ImageHeaderParser.ImageType.PNG) {
                            InputStream inputStream2 = is;
                        } else if (imageType2 == ImageHeaderParser.ImageType.PNG_A) {
                            InputStream inputStream3 = is;
                        } else {
                            if (imageType2 == ImageHeaderParser.ImageType.WEBP) {
                                InputStream inputStream4 = is;
                                m.b bVar = decodeCallbacks;
                                e eVar = bitmapPool;
                            } else if (imageType2 == ImageHeaderParser.ImageType.WEBP_A) {
                                InputStream inputStream5 = is;
                                m.b bVar2 = decodeCallbacks;
                                e eVar2 = bitmapPool;
                            } else if (i2 % powerOfTwoSampleSize == 0 && i3 % powerOfTwoSampleSize == 0) {
                                powerOfTwoWidth = i2 / powerOfTwoSampleSize;
                                powerOfTwoHeight = i3 / powerOfTwoSampleSize;
                                InputStream inputStream6 = is;
                            } else {
                                int[] dimensions = j(is, options2, decodeCallbacks, bitmapPool);
                                int powerOfTwoWidth2 = dimensions[0];
                                powerOfTwoHeight = dimensions[1];
                                powerOfTwoWidth = powerOfTwoWidth2;
                            }
                            if (Build.VERSION.SDK_INT >= 24) {
                                int powerOfTwoWidth3 = Math.round(((float) i2) / ((float) powerOfTwoSampleSize));
                                powerOfTwoHeight = Math.round(((float) i3) / ((float) powerOfTwoSampleSize));
                                powerOfTwoWidth = powerOfTwoWidth3;
                            } else {
                                powerOfTwoWidth = (int) Math.floor((double) (((float) i2) / ((float) powerOfTwoSampleSize)));
                                powerOfTwoHeight = (int) Math.floor((double) (((float) i3) / ((float) powerOfTwoSampleSize)));
                            }
                        }
                        powerOfTwoWidth = (int) Math.floor((double) (((float) i2) / ((float) powerOfTwoSampleSize)));
                        powerOfTwoHeight = (int) Math.floor((double) (((float) i3) / ((float) powerOfTwoSampleSize)));
                    }
                    double adjustedScaleFactor = (double) lVar.b(powerOfTwoWidth, powerOfTwoHeight, i4, i5);
                    if (Build.VERSION.SDK_INT >= 19) {
                        options2.inTargetDensity = a(adjustedScaleFactor);
                        options2.inDensity = 1000000000;
                    }
                    if (n(options)) {
                        options2.inScaled = true;
                    } else {
                        options2.inTargetDensity = 0;
                        options2.inDensity = 0;
                    }
                    if (Log.isLoggable("WebpDownsampler", 2)) {
                        StringBuilder sb = new StringBuilder();
                        int i10 = heightScaleFactor;
                        sb.append("Calculate scaling, source: [");
                        sb.append(i2);
                        sb.append("x");
                        sb.append(i3);
                        sb.append("], target: [");
                        sb.append(i4);
                        sb.append("x");
                        sb.append(i5);
                        sb.append("], power of two scaled: [");
                        sb.append(powerOfTwoWidth);
                        sb.append("x");
                        sb.append(powerOfTwoHeight);
                        sb.append("], exact scale factor: ");
                        sb.append(exactScaleFactor);
                        sb.append(", power of 2 sample size: ");
                        sb.append(powerOfTwoSampleSize);
                        sb.append(", adjusted scale factor: ");
                        sb.append(adjustedScaleFactor);
                        sb.append(", target density: ");
                        sb.append(options2.inTargetDensity);
                        sb.append(", density: ");
                        sb.append(options2.inDensity);
                        Log.v("WebpDownsampler", sb.toString());
                        return;
                    }
                    return;
                }
                throw new IllegalArgumentException("Cannot round with null rounding");
            }
            throw new IllegalArgumentException("Cannot scale with factor: " + exactScaleFactor + " from: " + downsampleStrategy + ", source: [" + i2 + "x" + i3 + "], target: [" + i4 + "x" + i5 + "]");
        }
    }

    private static int a(double adjustedScaleFactor) {
        int targetDensity = s(1.0E9d * adjustedScaleFactor);
        return s(((double) targetDensity) * (adjustedScaleFactor / ((double) (((float) targetDensity) / 1.0E9f))));
    }

    private static int s(double value) {
        return (int) (0.5d + value);
    }

    private boolean u(ImageHeaderParser.ImageType imageType) {
        if (Build.VERSION.SDK_INT >= 19) {
            return true;
        }
        return false;
    }

    private void b(InputStream is, com.bumptech.glide.load.b format, boolean isHardwareConfigAllowed, boolean isExifOrientationRequired, BitmapFactory.Options optionsWithScaling, int targetWidth, int targetHeight) {
        if (format == com.bumptech.glide.load.b.PREFER_ARGB_8888 || Build.VERSION.SDK_INT == 16) {
            optionsWithScaling.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return;
        }
        boolean hasAlpha = false;
        try {
            hasAlpha = com.bumptech.glide.load.e.e(this.g, is, this.f).hasAlpha();
        } catch (IOException e2) {
            if (Log.isLoggable("WebpDownsampler", 3)) {
                Log.d("WebpDownsampler", "Cannot determine whether the image has alpha or not from header, format " + format, e2);
            }
        }
        Bitmap.Config config = hasAlpha ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        optionsWithScaling.inPreferredConfig = config;
        if (config == Bitmap.Config.RGB_565 || config == Bitmap.Config.ARGB_4444 || config == Bitmap.Config.ALPHA_8) {
            optionsWithScaling.inDither = true;
        }
    }

    private static int[] j(InputStream is, BitmapFactory.Options options, m.b decodeCallbacks, e bitmapPool) {
        options.inJustDecodeBounds = true;
        g(is, options, decodeCallbacks, bitmapPool);
        options.inJustDecodeBounds = false;
        return new int[]{options.outWidth, options.outHeight};
    }

    private static Bitmap g(InputStream is, BitmapFactory.Options options, m.b callbacks, e bitmapPool) {
        IOException bitmapAssertionException;
        if (options.inJustDecodeBounds) {
            is.mark(10485760);
        } else {
            callbacks.b();
        }
        int sourceWidth = options.outWidth;
        int sourceHeight = options.outHeight;
        String outMimeType = options.outMimeType;
        z.i().lock();
        try {
            Bitmap result = WebpBitmapFactory.decodeStream(is, (Rect) null, options);
            z.i().unlock();
            if (options.inJustDecodeBounds) {
                is.reset();
            }
            return result;
        } catch (IOException e2) {
            throw bitmapAssertionException;
        } catch (IllegalArgumentException e3) {
            bitmapAssertionException = p(e3, sourceWidth, sourceHeight, outMimeType, options);
            if (Log.isLoggable("WebpDownsampler", 3)) {
                Log.d("WebpDownsampler", "Failed to decode with inBitmap, trying again without Bitmap re-use", bitmapAssertionException);
            }
            if (options.inBitmap != null) {
                is.reset();
                bitmapPool.b(options.inBitmap);
                options.inBitmap = null;
                Bitmap g2 = g(is, options, callbacks, bitmapPool);
                z.i().unlock();
                return g2;
            }
            throw bitmapAssertionException;
        } catch (Throwable th) {
            z.i().unlock();
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r1 = r2.inDensity;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean n(android.graphics.BitmapFactory.Options r2) {
        /*
            int r0 = r2.inTargetDensity
            if (r0 <= 0) goto L_0x000c
            int r1 = r2.inDensity
            if (r1 <= 0) goto L_0x000c
            if (r0 == r1) goto L_0x000c
            r0 = 1
            goto L_0x000d
        L_0x000c:
            r0 = 0
        L_0x000d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.integration.webp.decoder.j.n(android.graphics.BitmapFactory$Options):boolean");
    }

    private static void o(int sourceWidth, int sourceHeight, String outMimeType, BitmapFactory.Options options, Bitmap result, int requestedWidth, int requestedHeight, long startTime) {
        Log.v("WebpDownsampler", "Decoded " + h(result) + " from [" + sourceWidth + "x" + sourceHeight + "] " + outMimeType + " with inBitmap " + k(options) + " for [" + requestedWidth + "x" + requestedHeight + "], sample size: " + options.inSampleSize + ", density: " + options.inDensity + ", target density: " + options.inTargetDensity + ", thread: " + Thread.currentThread().getName() + ", duration: " + com.bumptech.glide.util.e.a(startTime));
    }

    private static String k(BitmapFactory.Options options) {
        return h(options.inBitmap);
    }

    @TargetApi(19)
    @Nullable
    private static String h(Bitmap bitmap) {
        String sizeString;
        if (bitmap == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            sizeString = " (" + bitmap.getAllocationByteCount() + ")";
        } else {
            sizeString = "";
        }
        return Constants.ARRAY_TYPE + bitmap.getWidth() + "x" + bitmap.getHeight() + "] " + bitmap.getConfig() + sizeString;
    }

    private static IOException p(IllegalArgumentException e2, int outWidth, int outHeight, String outMimeType, BitmapFactory.Options options) {
        return new IOException("Exception decoding bitmap, outWidth: " + outWidth + ", outHeight: " + outHeight + ", outMimeType: " + outMimeType + ", inBitmap: " + k(options), e2);
    }

    @TargetApi(26)
    private static void t(BitmapFactory.Options options, e bitmapPool, int width, int height) {
        if (Build.VERSION.SDK_INT < 26 || options.inPreferredConfig != Bitmap.Config.HARDWARE) {
            options.inBitmap = bitmapPool.e(width, height, options.inPreferredConfig);
        }
    }

    private static synchronized BitmapFactory.Options i() {
        BitmapFactory.Options decodeBitmapOptions;
        synchronized (j.class) {
            Queue<BitmapFactory.Options> queue = c;
            synchronized (queue) {
                decodeBitmapOptions = queue.poll();
            }
            if (decodeBitmapOptions == null) {
                decodeBitmapOptions = new BitmapFactory.Options();
                r(decodeBitmapOptions);
            }
        }
        return decodeBitmapOptions;
    }

    private static void q(BitmapFactory.Options decodeBitmapOptions) {
        r(decodeBitmapOptions);
        Queue<BitmapFactory.Options> queue = c;
        synchronized (queue) {
            queue.offer(decodeBitmapOptions);
        }
    }

    private static void r(BitmapFactory.Options decodeBitmapOptions) {
        decodeBitmapOptions.inTempStorage = null;
        decodeBitmapOptions.inDither = false;
        decodeBitmapOptions.inScaled = false;
        decodeBitmapOptions.inSampleSize = 1;
        decodeBitmapOptions.inPreferredConfig = null;
        decodeBitmapOptions.inJustDecodeBounds = false;
        decodeBitmapOptions.inDensity = 0;
        decodeBitmapOptions.inTargetDensity = 0;
        decodeBitmapOptions.outWidth = 0;
        decodeBitmapOptions.outHeight = 0;
        decodeBitmapOptions.outMimeType = null;
        decodeBitmapOptions.inBitmap = null;
        decodeBitmapOptions.inMutable = true;
    }
}
