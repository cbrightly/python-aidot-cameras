package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.util.DisplayMetrics;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.data.ParcelFileDescriptorRewinder;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.h;
import com.bumptech.glide.load.j;
import com.bumptech.glide.load.resource.bitmap.l;
import com.bumptech.glide.load.resource.bitmap.s;
import com.bumptech.glide.util.i;
import com.meituan.robust.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/* compiled from: Downsampler */
public final class m {
    public static final h<com.bumptech.glide.load.b> a = h.f("com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeFormat", com.bumptech.glide.load.b.DEFAULT);
    public static final h<j> b = h.f("com.bumptech.glide.load.resource.bitmap.Downsampler.PreferredColorSpace", j.SRGB);
    @Deprecated
    public static final h<l> c = l.h;
    public static final h<Boolean> d = h.f("com.bumptech.glide.load.resource.bitmap.Downsampler.FixBitmapSize", false);
    public static final h<Boolean> e = h.f("com.bumptech.glide.load.resource.bitmap.Downsampler.AllowHardwareDecode", false);
    private static final Set<String> f = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"image/vnd.wap.wbmp", "image/x-ico"})));
    private static final b g = new a();
    private static final Set<ImageHeaderParser.ImageType> h = Collections.unmodifiableSet(EnumSet.of(ImageHeaderParser.ImageType.JPEG, ImageHeaderParser.ImageType.PNG_A, ImageHeaderParser.ImageType.PNG));
    private static final Queue<BitmapFactory.Options> i = com.bumptech.glide.util.j.e(0);
    private final e j;
    private final DisplayMetrics k;
    private final com.bumptech.glide.load.engine.bitmap_recycle.b l;
    private final List<ImageHeaderParser> m;
    private final r n = r.b();

    /* compiled from: Downsampler */
    public interface b {
        void a(e eVar, Bitmap bitmap);

        void b();
    }

    /* compiled from: Downsampler */
    public class a implements b {
        a() {
        }

        public void b() {
        }

        public void a(e bitmapPool, Bitmap downsampled) {
        }
    }

    public m(List<ImageHeaderParser> parsers, DisplayMetrics displayMetrics, e bitmapPool, com.bumptech.glide.load.engine.bitmap_recycle.b byteArrayPool) {
        this.m = parsers;
        this.k = (DisplayMetrics) i.d(displayMetrics);
        this.j = (e) i.d(bitmapPool);
        this.l = (com.bumptech.glide.load.engine.bitmap_recycle.b) i.d(byteArrayPool);
    }

    public boolean p(InputStream is) {
        return true;
    }

    public boolean q(ByteBuffer byteBuffer) {
        return true;
    }

    public boolean o(ParcelFileDescriptor source) {
        return ParcelFileDescriptorRewinder.c();
    }

    public t<Bitmap> f(InputStream is, int outWidth, int outHeight, com.bumptech.glide.load.i options) {
        return g(is, outWidth, outHeight, options, g);
    }

    public t<Bitmap> g(InputStream is, int requestedWidth, int requestedHeight, com.bumptech.glide.load.i options, b callbacks) {
        return e(new s.a(is, this.m, this.l), requestedWidth, requestedHeight, options, callbacks);
    }

    @RequiresApi(21)
    public t<Bitmap> d(ParcelFileDescriptor parcelFileDescriptor, int outWidth, int outHeight, com.bumptech.glide.load.i options) {
        return e(new s.b(parcelFileDescriptor, this.m, this.l), outWidth, outHeight, options, g);
    }

    private t<Bitmap> e(s imageReader, int requestedWidth, int requestedHeight, com.bumptech.glide.load.i options, b callbacks) {
        com.bumptech.glide.load.i iVar = options;
        byte[] bytesForOptions = (byte[]) this.l.e(65536, byte[].class);
        BitmapFactory.Options bitmapFactoryOptions = k();
        bitmapFactoryOptions.inTempStorage = bytesForOptions;
        com.bumptech.glide.load.b decodeFormat = (com.bumptech.glide.load.b) iVar.a(a);
        j preferredColorSpace = (j) iVar.a(b);
        l downsampleStrategy = (l) iVar.a(l.h);
        boolean fixBitmapToRequestedDimensions = ((Boolean) iVar.a(d)).booleanValue();
        h hVar = e;
        try {
            return e.c(h(imageReader, bitmapFactoryOptions, downsampleStrategy, decodeFormat, preferredColorSpace, iVar.a(hVar) != null && ((Boolean) iVar.a(hVar)).booleanValue(), requestedWidth, requestedHeight, fixBitmapToRequestedDimensions, callbacks), this.j);
        } finally {
            v(bitmapFactoryOptions);
            this.l.put(bytesForOptions);
        }
    }

    private Bitmap h(s imageReader, BitmapFactory.Options options, l downsampleStrategy, com.bumptech.glide.load.b decodeFormat, j preferredColorSpace, boolean isHardwareConfigAllowed, int requestedWidth, int requestedHeight, boolean fixBitmapToRequestedDimensions, b callbacks) {
        boolean isHardwareConfigAllowed2;
        int targetWidth;
        int targetHeight;
        String str;
        m mVar;
        ColorSpace colorSpace;
        int expectedHeight;
        int expectedWidth;
        int expectedHeight2;
        BitmapFactory.Options options2 = options;
        b bVar = callbacks;
        long startTime = com.bumptech.glide.util.e.b();
        int[] sourceDimensions = m(imageReader, options2, bVar, this.j);
        boolean isP3Eligible = false;
        int sourceWidth = sourceDimensions[0];
        int sourceHeight = sourceDimensions[1];
        String sourceMimeType = options2.outMimeType;
        if (sourceWidth == -1 || sourceHeight == -1) {
            isHardwareConfigAllowed2 = false;
        } else {
            isHardwareConfigAllowed2 = isHardwareConfigAllowed;
        }
        int orientation = imageReader.c();
        int degreesToRotate = z.j(orientation);
        boolean isExifOrientationRequired = z.m(orientation);
        int i2 = requestedWidth;
        if (i2 == Integer.MIN_VALUE) {
            targetWidth = r(degreesToRotate) ? sourceHeight : sourceWidth;
        } else {
            targetWidth = i2;
        }
        int i3 = requestedHeight;
        if (i3 == Integer.MIN_VALUE) {
            targetHeight = r(degreesToRotate) ? sourceWidth : sourceHeight;
        } else {
            targetHeight = i3;
        }
        ImageHeaderParser.ImageType imageType = imageReader.d();
        e eVar = this.j;
        ImageHeaderParser.ImageType imageType2 = imageType;
        c(imageType, imageReader, callbacks, eVar, downsampleStrategy, degreesToRotate, sourceWidth, sourceHeight, targetWidth, targetHeight, options);
        int orientation2 = orientation;
        String sourceMimeType2 = sourceMimeType;
        int sourceHeight2 = sourceHeight;
        int sourceWidth2 = sourceWidth;
        b bVar2 = bVar;
        BitmapFactory.Options options3 = options2;
        b(imageReader, decodeFormat, isHardwareConfigAllowed2, isExifOrientationRequired, options, targetWidth, targetHeight);
        int i4 = Build.VERSION.SDK_INT;
        boolean isKitKatOrGreater = i4 >= 19;
        if (options3.inSampleSize == 1 || isKitKatOrGreater) {
            mVar = this;
            ImageHeaderParser.ImageType imageType3 = imageType2;
            if (mVar.z(imageType3)) {
                if (sourceWidth2 < 0 || sourceHeight2 < 0 || !fixBitmapToRequestedDimensions || !isKitKatOrGreater) {
                    float densityMultiplier = s(options) != 0 ? ((float) options3.inTargetDensity) / ((float) options3.inDensity) : 1.0f;
                    int sampleSize = options3.inSampleSize;
                    int downsampledWidth = (int) Math.ceil((double) (((float) sourceWidth2) / ((float) sampleSize)));
                    ImageHeaderParser.ImageType imageType4 = imageType3;
                    int downsampledHeight = (int) Math.ceil((double) (((float) sourceHeight2) / ((float) sampleSize)));
                    int expectedWidth2 = Math.round(((float) downsampledWidth) * densityMultiplier);
                    boolean z = isKitKatOrGreater;
                    int expectedHeight3 = Math.round(((float) downsampledHeight) * densityMultiplier);
                    int i5 = downsampledWidth;
                    str = "Downsampler";
                    if (Log.isLoggable(str, 2)) {
                        StringBuilder sb = new StringBuilder();
                        int i6 = downsampledHeight;
                        sb.append("Calculated target [");
                        sb.append(expectedWidth2);
                        sb.append("x");
                        sb.append(expectedHeight3);
                        expectedHeight2 = expectedHeight3;
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
                        expectedHeight2 = expectedHeight3;
                        int i7 = downsampledHeight;
                    }
                    expectedWidth = expectedWidth2;
                    expectedHeight = expectedHeight2;
                } else {
                    expectedWidth = targetWidth;
                    expectedHeight = targetHeight;
                    boolean z2 = isKitKatOrGreater;
                    str = "Downsampler";
                    ImageHeaderParser.ImageType imageType5 = imageType3;
                }
                if (expectedWidth > 0 && expectedHeight > 0) {
                    y(options3, mVar.j, expectedWidth, expectedHeight);
                }
            } else {
                str = "Downsampler";
                ImageHeaderParser.ImageType imageType6 = imageType3;
            }
        } else {
            mVar = this;
            boolean z3 = isKitKatOrGreater;
            str = "Downsampler";
        }
        if (i4 >= 28) {
            if (preferredColorSpace == j.DISPLAY_P3 && (colorSpace = options3.outColorSpace) != null && colorSpace.isWideGamut()) {
                isP3Eligible = true;
            }
            options3.inPreferredColorSpace = ColorSpace.get(isP3Eligible ? ColorSpace.Named.DISPLAY_P3 : ColorSpace.Named.SRGB);
        } else {
            j jVar = preferredColorSpace;
            if (i4 >= 26) {
                options3.inPreferredColorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
            }
        }
        Bitmap downsampled = i(imageReader, options3, bVar2, mVar.j);
        bVar2.a(mVar.j, downsampled);
        if (Log.isLoggable(str, 2)) {
            t(sourceWidth2, sourceHeight2, sourceMimeType2, options, downsampled, requestedWidth, requestedHeight, startTime);
        }
        Bitmap rotated = null;
        if (downsampled != null) {
            downsampled.setDensity(mVar.k.densityDpi);
            rotated = z.n(mVar.j, downsampled, orientation2);
            if (!downsampled.equals(rotated)) {
                mVar.j.b(downsampled);
            }
        }
        return rotated;
    }

    private static void c(ImageHeaderParser.ImageType imageType, s imageReader, b decodeCallbacks, e bitmapPool, l downsampleStrategy, int degreesToRotate, int sourceWidth, int sourceHeight, int targetWidth, int targetHeight, BitmapFactory.Options options) {
        String str;
        int scaleFactor;
        int powerOfTwoSampleSize;
        String str2;
        int powerOfTwoHeight;
        int powerOfTwoWidth;
        ImageHeaderParser.ImageType imageType2 = imageType;
        l lVar = downsampleStrategy;
        int i2 = sourceWidth;
        int powerOfTwoSampleSize2 = sourceHeight;
        int i3 = targetWidth;
        int i4 = targetHeight;
        BitmapFactory.Options options2 = options;
        if (i2 <= 0) {
            int i5 = powerOfTwoSampleSize2;
            str = "Downsampler";
        } else if (powerOfTwoSampleSize2 <= 0) {
            int i6 = powerOfTwoSampleSize2;
            str = "Downsampler";
        } else {
            int orientedSourceWidth = sourceWidth;
            int orientedSourceHeight = sourceHeight;
            if (r(degreesToRotate)) {
                orientedSourceWidth = sourceHeight;
                orientedSourceHeight = sourceWidth;
            }
            float exactScaleFactor = lVar.b(orientedSourceWidth, orientedSourceHeight, i3, i4);
            if (exactScaleFactor > 0.0f) {
                l.g rounding = lVar.a(orientedSourceWidth, orientedSourceHeight, i3, i4);
                if (rounding != null) {
                    int outWidth = x((double) (((float) orientedSourceWidth) * exactScaleFactor));
                    int outHeight = x((double) (((float) orientedSourceHeight) * exactScaleFactor));
                    int widthScaleFactor = orientedSourceWidth / outWidth;
                    int i7 = outWidth;
                    int heightScaleFactor = orientedSourceHeight / outHeight;
                    int i8 = outHeight;
                    l.g gVar = l.g.MEMORY;
                    if (rounding == gVar) {
                        scaleFactor = Math.max(widthScaleFactor, heightScaleFactor);
                    } else {
                        scaleFactor = Math.min(widthScaleFactor, heightScaleFactor);
                    }
                    int i9 = heightScaleFactor;
                    int heightScaleFactor2 = Build.VERSION.SDK_INT;
                    int i10 = widthScaleFactor;
                    if (heightScaleFactor2 > 23 || !f.contains(options2.outMimeType)) {
                        powerOfTwoSampleSize = Math.max(1, Integer.highestOneBit(scaleFactor));
                        if (rounding == gVar && ((float) powerOfTwoSampleSize) < 1.0f / exactScaleFactor) {
                            powerOfTwoSampleSize <<= 1;
                        }
                    } else {
                        powerOfTwoSampleSize = 1;
                    }
                    options2.inSampleSize = powerOfTwoSampleSize;
                    if (imageType2 == ImageHeaderParser.ImageType.JPEG) {
                        int nativeScaling = Math.min(powerOfTwoSampleSize, 8);
                        l.g gVar2 = rounding;
                        str2 = "Downsampler";
                        powerOfTwoWidth = (int) Math.ceil((double) (((float) orientedSourceWidth) / ((float) nativeScaling)));
                        int i11 = nativeScaling;
                        powerOfTwoHeight = (int) Math.ceil((double) (((float) orientedSourceHeight) / ((float) nativeScaling)));
                        int secondaryScaling = powerOfTwoSampleSize / 8;
                        if (secondaryScaling > 0) {
                            powerOfTwoWidth /= secondaryScaling;
                            powerOfTwoHeight /= secondaryScaling;
                        }
                        b bVar = decodeCallbacks;
                        e eVar = bitmapPool;
                    } else {
                        str2 = "Downsampler";
                        if (imageType2 == ImageHeaderParser.ImageType.PNG) {
                            b bVar2 = decodeCallbacks;
                            e eVar2 = bitmapPool;
                        } else if (imageType2 == ImageHeaderParser.ImageType.PNG_A) {
                            b bVar3 = decodeCallbacks;
                            e eVar3 = bitmapPool;
                        } else {
                            if (imageType2 == ImageHeaderParser.ImageType.WEBP) {
                                s sVar = imageReader;
                                b bVar4 = decodeCallbacks;
                                e eVar4 = bitmapPool;
                            } else if (imageType2 == ImageHeaderParser.ImageType.WEBP_A) {
                                s sVar2 = imageReader;
                                b bVar5 = decodeCallbacks;
                                e eVar5 = bitmapPool;
                            } else if (orientedSourceWidth % powerOfTwoSampleSize == 0 && orientedSourceHeight % powerOfTwoSampleSize == 0) {
                                powerOfTwoWidth = orientedSourceWidth / powerOfTwoSampleSize;
                                powerOfTwoHeight = orientedSourceHeight / powerOfTwoSampleSize;
                                b bVar6 = decodeCallbacks;
                                e eVar6 = bitmapPool;
                            } else {
                                int[] dimensions = m(imageReader, options2, decodeCallbacks, bitmapPool);
                                int powerOfTwoWidth2 = dimensions[0];
                                powerOfTwoHeight = dimensions[1];
                                powerOfTwoWidth = powerOfTwoWidth2;
                            }
                            if (heightScaleFactor2 >= 24) {
                                int powerOfTwoWidth3 = Math.round(((float) orientedSourceWidth) / ((float) powerOfTwoSampleSize));
                                powerOfTwoHeight = Math.round(((float) orientedSourceHeight) / ((float) powerOfTwoSampleSize));
                                powerOfTwoWidth = powerOfTwoWidth3;
                            } else {
                                int powerOfTwoWidth4 = (int) Math.floor((double) (((float) orientedSourceWidth) / ((float) powerOfTwoSampleSize)));
                                powerOfTwoHeight = (int) Math.floor((double) (((float) orientedSourceHeight) / ((float) powerOfTwoSampleSize)));
                                powerOfTwoWidth = powerOfTwoWidth4;
                            }
                        }
                        int powerOfTwoWidth5 = (int) Math.floor((double) (((float) orientedSourceWidth) / ((float) powerOfTwoSampleSize)));
                        powerOfTwoHeight = (int) Math.floor((double) (((float) orientedSourceHeight) / ((float) powerOfTwoSampleSize)));
                        powerOfTwoWidth = powerOfTwoWidth5;
                    }
                    int i12 = orientedSourceWidth;
                    int i13 = orientedSourceHeight;
                    double adjustedScaleFactor = (double) lVar.b(powerOfTwoWidth, powerOfTwoHeight, i3, i4);
                    if (heightScaleFactor2 >= 19) {
                        options2.inTargetDensity = a(adjustedScaleFactor);
                        options2.inDensity = l(adjustedScaleFactor);
                    }
                    if (s(options)) {
                        options2.inScaled = true;
                    } else {
                        options2.inTargetDensity = 0;
                        options2.inDensity = 0;
                    }
                    String str3 = str2;
                    if (Log.isLoggable(str3, 2)) {
                        Log.v(str3, "Calculate scaling, source: [" + i2 + "x" + sourceHeight + "], degreesToRotate: " + degreesToRotate + ", target: [" + i3 + "x" + i4 + "], power of two scaled: [" + powerOfTwoWidth + "x" + powerOfTwoHeight + "], exact scale factor: " + exactScaleFactor + ", power of 2 sample size: " + powerOfTwoSampleSize + ", adjusted scale factor: " + adjustedScaleFactor + ", target density: " + options2.inTargetDensity + ", density: " + options2.inDensity);
                        return;
                    }
                    int i14 = sourceHeight;
                    return;
                }
                int i15 = powerOfTwoSampleSize2;
                throw new IllegalArgumentException("Cannot round with null rounding");
            }
            int i16 = powerOfTwoSampleSize2;
            throw new IllegalArgumentException("Cannot scale with factor: " + exactScaleFactor + " from: " + lVar + ", source: [" + i2 + "x" + i16 + "], target: [" + i3 + "x" + i4 + "]");
        }
        if (Log.isLoggable(str, 3)) {
            Log.d(str, "Unable to determine dimensions for: " + imageType + " with target [" + i3 + "x" + i4 + "]");
            return;
        }
        ImageHeaderParser.ImageType imageType3 = imageType;
    }

    private static int a(double adjustedScaleFactor) {
        int densityMultiplier = l(adjustedScaleFactor);
        int targetDensity = x(((double) densityMultiplier) * adjustedScaleFactor);
        return x(((double) targetDensity) * (adjustedScaleFactor / ((double) (((float) targetDensity) / ((float) densityMultiplier)))));
    }

    private static int l(double adjustedScaleFactor) {
        return (int) Math.round((adjustedScaleFactor <= 1.0d ? adjustedScaleFactor : 1.0d / adjustedScaleFactor) * 2.147483647E9d);
    }

    private static int x(double value) {
        return (int) (0.5d + value);
    }

    private boolean z(ImageHeaderParser.ImageType imageType) {
        if (Build.VERSION.SDK_INT >= 19) {
            return true;
        }
        return h.contains(imageType);
    }

    private void b(s imageReader, com.bumptech.glide.load.b format, boolean isHardwareConfigAllowed, boolean isExifOrientationRequired, BitmapFactory.Options optionsWithScaling, int targetWidth, int targetHeight) {
        if (!this.n.i(targetWidth, targetHeight, optionsWithScaling, isHardwareConfigAllowed, isExifOrientationRequired)) {
            if (format == com.bumptech.glide.load.b.PREFER_ARGB_8888 || Build.VERSION.SDK_INT == 16) {
                optionsWithScaling.inPreferredConfig = Bitmap.Config.ARGB_8888;
                return;
            }
            boolean hasAlpha = false;
            try {
                hasAlpha = imageReader.d().hasAlpha();
            } catch (IOException e2) {
                if (Log.isLoggable("Downsampler", 3)) {
                    Log.d("Downsampler", "Cannot determine whether the image has alpha or not from header, format " + format, e2);
                }
            }
            Bitmap.Config config = hasAlpha ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            optionsWithScaling.inPreferredConfig = config;
            if (config == Bitmap.Config.RGB_565) {
                optionsWithScaling.inDither = true;
            }
        }
    }

    private static int[] m(s imageReader, BitmapFactory.Options options, b decodeCallbacks, e bitmapPool) {
        options.inJustDecodeBounds = true;
        i(imageReader, options, decodeCallbacks, bitmapPool);
        options.inJustDecodeBounds = false;
        return new int[]{options.outWidth, options.outHeight};
    }

    private static Bitmap i(s imageReader, BitmapFactory.Options options, b callbacks, e bitmapPool) {
        IOException bitmapAssertionException;
        if (!options.inJustDecodeBounds) {
            callbacks.b();
            imageReader.b();
        }
        int sourceWidth = options.outWidth;
        int sourceHeight = options.outHeight;
        String outMimeType = options.outMimeType;
        z.i().lock();
        try {
            Bitmap result = imageReader.a(options);
            z.i().unlock();
            return result;
        } catch (IOException e2) {
            throw bitmapAssertionException;
        } catch (IllegalArgumentException e3) {
            bitmapAssertionException = u(e3, sourceWidth, sourceHeight, outMimeType, options);
            if (Log.isLoggable("Downsampler", 3)) {
                Log.d("Downsampler", "Failed to decode with inBitmap, trying again without Bitmap re-use", bitmapAssertionException);
            }
            Bitmap bitmap = options.inBitmap;
            if (bitmap != null) {
                bitmapPool.b(bitmap);
                options.inBitmap = null;
                Bitmap i2 = i(imageReader, options, callbacks, bitmapPool);
                z.i().unlock();
                return i2;
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
    private static boolean s(android.graphics.BitmapFactory.Options r2) {
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
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.resource.bitmap.m.s(android.graphics.BitmapFactory$Options):boolean");
    }

    private static void t(int sourceWidth, int sourceHeight, String outMimeType, BitmapFactory.Options options, Bitmap result, int requestedWidth, int requestedHeight, long startTime) {
        Log.v("Downsampler", "Decoded " + j(result) + " from [" + sourceWidth + "x" + sourceHeight + "] " + outMimeType + " with inBitmap " + n(options) + " for [" + requestedWidth + "x" + requestedHeight + "], sample size: " + options.inSampleSize + ", density: " + options.inDensity + ", target density: " + options.inTargetDensity + ", thread: " + Thread.currentThread().getName() + ", duration: " + com.bumptech.glide.util.e.a(startTime));
    }

    private static String n(BitmapFactory.Options options) {
        return j(options.inBitmap);
    }

    @TargetApi(19)
    @Nullable
    private static String j(Bitmap bitmap) {
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

    private static IOException u(IllegalArgumentException e2, int outWidth, int outHeight, String outMimeType, BitmapFactory.Options options) {
        return new IOException("Exception decoding bitmap, outWidth: " + outWidth + ", outHeight: " + outHeight + ", outMimeType: " + outMimeType + ", inBitmap: " + n(options), e2);
    }

    @TargetApi(26)
    private static void y(BitmapFactory.Options options, e bitmapPool, int width, int height) {
        Bitmap.Config expectedConfig = null;
        if (Build.VERSION.SDK_INT >= 26) {
            if (options.inPreferredConfig != Bitmap.Config.HARDWARE) {
                expectedConfig = options.outConfig;
            } else {
                return;
            }
        }
        if (expectedConfig == null) {
            expectedConfig = options.inPreferredConfig;
        }
        options.inBitmap = bitmapPool.e(width, height, expectedConfig);
    }

    private static synchronized BitmapFactory.Options k() {
        BitmapFactory.Options decodeBitmapOptions;
        synchronized (m.class) {
            Queue<BitmapFactory.Options> queue = i;
            synchronized (queue) {
                decodeBitmapOptions = queue.poll();
            }
            if (decodeBitmapOptions == null) {
                decodeBitmapOptions = new BitmapFactory.Options();
                w(decodeBitmapOptions);
            }
        }
        return decodeBitmapOptions;
    }

    private static void v(BitmapFactory.Options decodeBitmapOptions) {
        w(decodeBitmapOptions);
        Queue<BitmapFactory.Options> queue = i;
        synchronized (queue) {
            queue.offer(decodeBitmapOptions);
        }
    }

    private static void w(BitmapFactory.Options decodeBitmapOptions) {
        decodeBitmapOptions.inTempStorage = null;
        decodeBitmapOptions.inDither = false;
        decodeBitmapOptions.inScaled = false;
        decodeBitmapOptions.inSampleSize = 1;
        decodeBitmapOptions.inPreferredConfig = null;
        decodeBitmapOptions.inJustDecodeBounds = false;
        decodeBitmapOptions.inDensity = 0;
        decodeBitmapOptions.inTargetDensity = 0;
        if (Build.VERSION.SDK_INT >= 26) {
            decodeBitmapOptions.inPreferredColorSpace = null;
            decodeBitmapOptions.outColorSpace = null;
            decodeBitmapOptions.outConfig = null;
        }
        decodeBitmapOptions.outWidth = 0;
        decodeBitmapOptions.outHeight = 0;
        decodeBitmapOptions.outMimeType = null;
        decodeBitmapOptions.inBitmap = null;
        decodeBitmapOptions.inMutable = true;
    }

    private static boolean r(int degreesToRotate) {
        return degreesToRotate == 90 || degreesToRotate == 270;
    }
}
