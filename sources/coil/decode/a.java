package coil.decode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Build;
import com.luck.picture.lib.compress.Checker;
import java.io.InputStream;
import kotlin.collections.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okio.e;
import okio.e0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BitmapFactoryDecoder.kt */
public final class a implements e {
    @NotNull
    public static final C0003a a = new C0003a((DefaultConstructorMarker) null);
    @NotNull
    private static final String[] b = {"image/jpeg", "image/webp", Checker.MIME_TYPE_HEIC, "image/heif"};
    @NotNull
    private final Context c;
    @NotNull
    private final Paint d = new Paint(3);

    public a(@NotNull Context context) {
        k.e(context, "context");
        this.c = context;
    }

    public boolean b(@NotNull e source, @Nullable String mimeType) {
        k.e(source, "source");
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x006d  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object a(@org.jetbrains.annotations.NotNull coil.bitmap.c r17, @org.jetbrains.annotations.NotNull okio.e r18, @org.jetbrains.annotations.NotNull coil.size.Size r19, @org.jetbrains.annotations.NotNull coil.decode.m r20, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super coil.decode.c> r21) {
        /*
            r16 = this;
            r1 = 0
            r2 = 0
            r3 = r21
            r4 = 0
            kotlinx.coroutines.o r0 = new kotlinx.coroutines.o
            kotlin.coroutines.d r5 = kotlin.coroutines.intrinsics.b.c(r3)
            r6 = 1
            r0.<init>(r5, r6)
            r5 = r0
            r5.w()
            r6 = r5
            r7 = 0
            coil.decode.k r0 = new coil.decode.k     // Catch:{ Exception -> 0x0058 }
            r8 = r18
            r0.<init>(r6, r8)     // Catch:{ Exception -> 0x0052 }
            r9 = r0
            r0 = r9
            r10 = 0
            r11 = r16
            r12 = r17
            r13 = r19
            r14 = r20
            coil.decode.c r15 = r11.f(r12, r0, r13, r14)     // Catch:{ all -> 0x004b }
            kotlin.o$a r0 = kotlin.o.Companion     // Catch:{ all -> 0x004b }
            java.lang.Object r0 = kotlin.o.m17constructorimpl(r15)     // Catch:{ all -> 0x004b }
            r6.resumeWith(r0)     // Catch:{ all -> 0x004b }
            r9.a()     // Catch:{ Exception -> 0x0050 }
            java.lang.Object r0 = r5.t()
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            if (r0 != r3) goto L_0x0048
            kotlin.coroutines.jvm.internal.h.c(r21)
        L_0x0048:
            return r0
        L_0x004b:
            r0 = move-exception
            r9.a()     // Catch:{ Exception -> 0x0050 }
            throw r0     // Catch:{ Exception -> 0x0050 }
        L_0x0050:
            r0 = move-exception
            goto L_0x0063
        L_0x0052:
            r0 = move-exception
            r11 = r16
            r12 = r17
            goto L_0x005f
        L_0x0058:
            r0 = move-exception
            r11 = r16
            r12 = r17
            r8 = r18
        L_0x005f:
            r13 = r19
            r14 = r20
        L_0x0063:
            boolean r9 = r0 instanceof java.lang.InterruptedException
            if (r9 != 0) goto L_0x006d
            boolean r9 = r0 instanceof java.io.InterruptedIOException
            if (r9 == 0) goto L_0x006c
            goto L_0x006d
        L_0x006c:
            throw r0
        L_0x006d:
            java.util.concurrent.CancellationException r9 = new java.util.concurrent.CancellationException
            java.lang.String r10 = "Blocking call was interrupted due to parent cancellation."
            r9.<init>(r10)
            java.lang.Throwable r9 = r9.initCause(r0)
            java.lang.String r10 = "CancellationException(\"Blocking call was interrupted due to parent cancellation.\").initCause(exception)"
            kotlin.jvm.internal.k.d(r9, r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.decode.a.a(coil.bitmap.c, okio.e, coil.size.Size, coil.decode.m, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x027f  */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x02af  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x01ed A[SYNTHETIC, Splitter:B:79:0x01ed] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x021d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final coil.decode.c f(coil.bitmap.c r33, okio.e0 r34, coil.size.Size r35, coil.decode.m r36) {
        /*
            r32 = this;
            r7 = r32
            r8 = r33
            r9 = r35
            android.graphics.BitmapFactory$Options r0 = new android.graphics.BitmapFactory$Options
            r0.<init>()
            r10 = r0
            r11 = 0
            coil.decode.a$b r0 = new coil.decode.a$b
            r12 = r34
            r0.<init>(r12)
            r13 = r0
            okio.e r14 = okio.p.d(r13)
            r0 = 1
            r10.inJustDecodeBounds = r0
            okio.e r1 = r14.peek()
            java.io.InputStream r1 = r1.Y0()
            r2 = 0
            android.graphics.BitmapFactory.decodeStream(r1, r2, r10)
            java.lang.Exception r1 = r13.a()
            if (r1 != 0) goto L_0x02bf
            r15 = 0
            r10.inJustDecodeBounds = r15
            r1 = 0
            r3 = 0
            java.lang.String r4 = r10.outMimeType
            boolean r4 = r7.g(r4)
            if (r4 == 0) goto L_0x0061
            androidx.exifinterface.media.ExifInterface r4 = new androidx.exifinterface.media.ExifInterface
            coil.decode.a$c r5 = new coil.decode.a$c
            okio.e r6 = r14.peek()
            java.io.InputStream r6 = r6.Y0()
            r5.<init>(r6)
            r4.<init>((java.io.InputStream) r5)
            java.lang.Exception r5 = r13.a()
            if (r5 != 0) goto L_0x005e
            boolean r1 = r4.isFlipped()
            int r3 = r4.getRotationDegrees()
            r6 = r1
            r5 = r3
            goto L_0x0065
        L_0x005e:
            r0 = r5
            r2 = 0
            throw r0
        L_0x0061:
            r1 = 0
            r3 = 0
            r6 = r1
            r5 = r3
        L_0x0065:
            r1 = 90
            if (r5 == r1) goto L_0x0070
            r1 = 270(0x10e, float:3.78E-43)
            if (r5 != r1) goto L_0x006e
            goto L_0x0070
        L_0x006e:
            r1 = r15
            goto L_0x0071
        L_0x0070:
            r1 = r0
        L_0x0071:
            r16 = r1
            if (r16 == 0) goto L_0x0078
            int r1 = r10.outHeight
            goto L_0x007a
        L_0x0078:
            int r1 = r10.outWidth
        L_0x007a:
            r4 = r1
            if (r16 == 0) goto L_0x0080
            int r1 = r10.outWidth
            goto L_0x0082
        L_0x0080:
            int r1 = r10.outHeight
        L_0x0082:
            r3 = r1
            r1 = r36
            android.graphics.Bitmap$Config r2 = r7.e(r10, r1, r6, r5)
            r10.inPreferredConfig = r2
            int r2 = android.os.Build.VERSION.SDK_INT
            r0 = 26
            if (r2 < r0) goto L_0x009d
            android.graphics.ColorSpace r0 = r36.c()
            if (r0 == 0) goto L_0x009d
            android.graphics.ColorSpace r0 = r36.c()
            r10.inPreferredColorSpace = r0
        L_0x009d:
            r0 = 19
            if (r2 < r0) goto L_0x00a7
            boolean r0 = r36.j()
            r10.inPremultiplied = r0
        L_0x00a7:
            r0 = 24
            if (r2 >= r0) goto L_0x00ad
            r0 = 1
            goto L_0x00ae
        L_0x00ad:
            r0 = r15
        L_0x00ae:
            r10.inMutable = r0
            r10.inScaled = r15
            int r15 = r10.outWidth
            java.lang.String r1 = "inPreferredConfig"
            if (r15 <= 0) goto L_0x01d0
            r20 = r5
            int r5 = r10.outHeight
            if (r5 > 0) goto L_0x00c9
            r27 = r4
            r15 = r6
            r21 = r11
            r22 = r13
            r13 = r3
            goto L_0x01da
        L_0x00c9:
            r21 = r11
            boolean r11 = r9 instanceof coil.size.PixelSize
            if (r11 != 0) goto L_0x00f2
            r11 = 1
            r10.inSampleSize = r11
            r11 = 0
            r10.inScaled = r11
            if (r0 == 0) goto L_0x00ea
            android.graphics.Bitmap$Config r0 = r10.inPreferredConfig
            kotlin.jvm.internal.k.d(r0, r1)
            android.graphics.Bitmap r0 = r8.e(r15, r5, r0)
            r10.inBitmap = r0
            r27 = r4
            r15 = r6
            r22 = r13
            r13 = r3
            goto L_0x01e3
        L_0x00ea:
            r27 = r4
            r15 = r6
            r22 = r13
            r13 = r3
            goto L_0x01e3
        L_0x00f2:
            r0 = r9
            coil.size.PixelSize r0 = (coil.size.PixelSize) r0
            int r0 = r0.a()
            r5 = r9
            coil.size.PixelSize r5 = (coil.size.PixelSize) r5
            int r5 = r5.b()
            coil.decode.d r11 = coil.decode.d.a
            coil.size.e r11 = r36.k()
            int r11 = coil.decode.d.a(r4, r3, r0, r5, r11)
            r10.inSampleSize = r11
            r15 = r6
            double r6 = (double) r4
            r22 = r13
            double r12 = (double) r11
            double r23 = r6 / r12
            double r6 = (double) r3
            double r11 = (double) r11
            double r25 = r6 / r11
            double r6 = (double) r0
            double r11 = (double) r5
            coil.size.e r31 = r36.k()
            r27 = r6
            r29 = r11
            double r6 = coil.decode.d.c(r23, r25, r27, r29, r31)
            boolean r11 = r36.a()
            r12 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            if (r11 == 0) goto L_0x0133
            double r23 = kotlin.ranges.n.d(r6, r12)
            goto L_0x0135
        L_0x0133:
            r23 = r6
        L_0x0135:
            int r11 = (r23 > r12 ? 1 : (r23 == r12 ? 0 : -1))
            if (r11 != 0) goto L_0x013b
            r11 = 1
            goto L_0x013c
        L_0x013b:
            r11 = 0
        L_0x013c:
            r18 = 1
            r11 = r11 ^ 1
            r10.inScaled = r11
            if (r11 == 0) goto L_0x0167
            int r11 = (r23 > r12 ? 1 : (r23 == r12 ? 0 : -1))
            r12 = 2147483647(0x7fffffff, float:NaN)
            if (r11 <= 0) goto L_0x0159
            r13 = r3
            r11 = r4
            double r3 = (double) r12
            double r3 = r3 / r23
            int r3 = kotlin.math.b.a(r3)
            r10.inDensity = r3
            r10.inTargetDensity = r12
            goto L_0x0169
        L_0x0159:
            r13 = r3
            r11 = r4
            r10.inDensity = r12
            double r3 = (double) r12
            double r3 = r3 * r23
            int r3 = kotlin.math.b.a(r3)
            r10.inTargetDensity = r3
            goto L_0x0169
        L_0x0167:
            r13 = r3
            r11 = r4
        L_0x0169:
            boolean r3 = r10.inMutable
            if (r3 == 0) goto L_0x01c9
            int r3 = r10.inSampleSize
            r4 = 1
            if (r3 != r4) goto L_0x018b
            boolean r4 = r10.inScaled
            if (r4 != 0) goto L_0x018b
            int r3 = r10.outWidth
            int r4 = r10.outHeight
            android.graphics.Bitmap$Config r12 = r10.inPreferredConfig
            kotlin.jvm.internal.k.d(r12, r1)
            android.graphics.Bitmap r3 = r8.e(r3, r4, r12)
            r28 = r5
            r25 = r6
            r27 = r11
            goto L_0x01c6
        L_0x018b:
            r4 = 19
            if (r2 < r4) goto L_0x01bf
            int r4 = r10.outWidth
            r12 = r5
            double r4 = (double) r4
            r25 = r6
            double r6 = (double) r3
            double r4 = r4 / r6
            int r6 = r10.outHeight
            double r6 = (double) r6
            r27 = r11
            r28 = r12
            double r11 = (double) r3
            double r6 = r6 / r11
            double r11 = r23 * r4
            r29 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            double r11 = r11 + r29
            double r11 = java.lang.Math.ceil(r11)
            int r3 = (int) r11
            double r11 = r23 * r6
            double r11 = r11 + r29
            double r11 = java.lang.Math.ceil(r11)
            int r11 = (int) r11
            android.graphics.Bitmap$Config r12 = r10.inPreferredConfig
            kotlin.jvm.internal.k.d(r12, r1)
            android.graphics.Bitmap r3 = r8.e(r3, r11, r12)
            goto L_0x01c6
        L_0x01bf:
            r28 = r5
            r25 = r6
            r27 = r11
            r3 = 0
        L_0x01c6:
            r10.inBitmap = r3
            goto L_0x01e3
        L_0x01c9:
            r28 = r5
            r25 = r6
            r27 = r11
            goto L_0x01e3
        L_0x01d0:
            r27 = r4
            r20 = r5
            r15 = r6
            r21 = r11
            r22 = r13
            r13 = r3
        L_0x01da:
            r0 = 1
            r10.inSampleSize = r0
            r0 = 0
            r10.inScaled = r0
            r0 = 0
            r10.inBitmap = r0
        L_0x01e3:
            android.graphics.Bitmap r7 = r10.inBitmap
            r3 = 0
            r0 = r14
            r4 = 0
            r5 = 19
            if (r2 >= r5) goto L_0x0208
            java.lang.String r2 = r10.outMimeType     // Catch:{ all -> 0x01fe }
            if (r2 != 0) goto L_0x0208
            byte[] r2 = r0.q0()     // Catch:{ all -> 0x01fe }
            int r5 = r2.length     // Catch:{ all -> 0x01fe }
            r11 = 0
            android.graphics.Bitmap r5 = android.graphics.BitmapFactory.decodeByteArray(r2, r11, r5, r10)     // Catch:{ all -> 0x01fe }
            r2 = r5
            r5 = 0
            goto L_0x0212
        L_0x01fe:
            r0 = move-exception
            r2 = r32
            r1 = r0
            r19 = r20
            r17 = r27
            goto L_0x02a4
        L_0x0208:
            r11 = 0
            java.io.InputStream r2 = r0.Y0()     // Catch:{ all -> 0x029c }
            r5 = 0
            android.graphics.Bitmap r2 = android.graphics.BitmapFactory.decodeStream(r2, r5, r10)     // Catch:{ all -> 0x029c }
        L_0x0212:
            kotlin.io.b.a(r14, r5)     // Catch:{ all -> 0x0294 }
            r12 = r2
            java.lang.Exception r0 = r22.a()     // Catch:{ all -> 0x028b }
            if (r0 != 0) goto L_0x027f
            if (r12 == 0) goto L_0x0271
            android.content.Context r0 = r36.e()
            android.content.res.Resources r0 = r0.getResources()
            android.util.DisplayMetrics r0 = r0.getDisplayMetrics()
            int r0 = r0.densityDpi
            r12.setDensity(r0)
            android.graphics.Bitmap$Config r4 = r10.inPreferredConfig
            kotlin.jvm.internal.k.d(r4, r1)
            r1 = r32
            r2 = r33
            r3 = r12
            r17 = r27
            r19 = r20
            r5 = r15
            r6 = r19
            android.graphics.Bitmap r0 = r1.d(r2, r3, r4, r5, r6)
            coil.decode.c r1 = new coil.decode.c
            r2 = r32
            android.content.Context r3 = r2.c
            r4 = r0
            r5 = 0
            android.content.res.Resources r6 = r3.getResources()
            java.lang.String r11 = "context.resources"
            kotlin.jvm.internal.k.d(r6, r11)
            r11 = r4
            r20 = 0
            r23 = r0
            android.graphics.drawable.BitmapDrawable r0 = new android.graphics.drawable.BitmapDrawable
            r0.<init>(r6, r11)
            int r3 = r10.inSampleSize
            r4 = 1
            if (r3 > r4) goto L_0x026c
            boolean r3 = r10.inScaled
            if (r3 == 0) goto L_0x026b
            goto L_0x026c
        L_0x026b:
            r4 = 0
        L_0x026c:
            r1.<init>(r0, r4)
            return r1
        L_0x0271:
            r0 = 0
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "BitmapFactory returned a null bitmap. Often this means BitmapFactory could not decode the image data read from the input source (e.g. network, disk, or memory) as it's not encoded as a valid image format."
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x027f:
            r2 = r32
            r19 = r20
            r17 = r27
            r1 = 0
            throw r0     // Catch:{ all -> 0x0288 }
        L_0x0288:
            r0 = move-exception
            r3 = r12
            goto L_0x02ac
        L_0x028b:
            r0 = move-exception
            r2 = r32
            r19 = r20
            r17 = r27
            r3 = r12
            goto L_0x02ac
        L_0x0294:
            r0 = move-exception
            r2 = r32
            r19 = r20
            r17 = r27
            goto L_0x02ac
        L_0x029c:
            r0 = move-exception
            r2 = r32
            r19 = r20
            r17 = r27
            r1 = r0
        L_0x02a4:
            throw r1     // Catch:{ all -> 0x02a5 }
        L_0x02a5:
            r0 = move-exception
            r4 = r0
            kotlin.io.b.a(r14, r1)     // Catch:{ all -> 0x02ab }
            throw r4     // Catch:{ all -> 0x02ab }
        L_0x02ab:
            r0 = move-exception
        L_0x02ac:
            if (r7 != 0) goto L_0x02af
            goto L_0x02b4
        L_0x02af:
            r1 = r7
            r4 = 0
            r8.b(r1)
        L_0x02b4:
            if (r3 == r7) goto L_0x02be
            if (r3 != 0) goto L_0x02b9
            goto L_0x02be
        L_0x02b9:
            r1 = r3
            r4 = 0
            r8.b(r1)
        L_0x02be:
            throw r0
        L_0x02bf:
            r0 = r1
            r1 = 0
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.decode.a.f(coil.bitmap.c, okio.e0, coil.size.Size, coil.decode.m):coil.decode.c");
    }

    private final boolean g(String mimeType) {
        return mimeType != null && l.r(b, mimeType);
    }

    private final Bitmap.Config e(BitmapFactory.Options $this$computeConfig, m options, boolean isFlipped, int rotationDegrees) {
        Bitmap.Config config = options.d();
        if (isFlipped || rotationDegrees > 0) {
            config = coil.util.c.e(config);
        }
        if (options.b() && config == Bitmap.Config.ARGB_8888 && k.a($this$computeConfig.outMimeType, "image/jpeg")) {
            config = Bitmap.Config.RGB_565;
        }
        if (Build.VERSION.SDK_INT < 26 || $this$computeConfig.outConfig != Bitmap.Config.RGBA_F16 || config == Bitmap.Config.HARDWARE) {
            return config;
        }
        return Bitmap.Config.RGBA_F16;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005c, code lost:
        if (r4 == false) goto L_0x005e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final android.graphics.Bitmap d(coil.bitmap.c r17, android.graphics.Bitmap r18, android.graphics.Bitmap.Config r19, boolean r20, int r21) {
        /*
            r16 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r21
            r4 = 1
            r5 = 0
            if (r3 <= 0) goto L_0x000e
            r6 = r4
            goto L_0x000f
        L_0x000e:
            r6 = r5
        L_0x000f:
            if (r20 != 0) goto L_0x0014
            if (r6 != 0) goto L_0x0014
            return r1
        L_0x0014:
            android.graphics.Matrix r7 = new android.graphics.Matrix
            r7.<init>()
            int r8 = r18.getWidth()
            float r8 = (float) r8
            r9 = 1073741824(0x40000000, float:2.0)
            float r8 = r8 / r9
            int r10 = r18.getHeight()
            float r10 = (float) r10
            float r10 = r10 / r9
            if (r20 == 0) goto L_0x0030
            r9 = -1082130432(0xffffffffbf800000, float:-1.0)
            r11 = 1065353216(0x3f800000, float:1.0)
            r7.postScale(r9, r11, r8, r10)
        L_0x0030:
            if (r6 == 0) goto L_0x0036
            float r9 = (float) r3
            r7.postRotate(r9, r8, r10)
        L_0x0036:
            android.graphics.RectF r9 = new android.graphics.RectF
            int r11 = r18.getWidth()
            float r11 = (float) r11
            int r12 = r18.getHeight()
            float r12 = (float) r12
            r13 = 0
            r9.<init>(r13, r13, r11, r12)
            r7.mapRect(r9)
            float r11 = r9.left
            int r12 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r12 != 0) goto L_0x0051
            r12 = r4
            goto L_0x0052
        L_0x0051:
            r12 = r5
        L_0x0052:
            if (r12 == 0) goto L_0x005e
            float r12 = r9.top
            int r12 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r12 != 0) goto L_0x005b
            goto L_0x005c
        L_0x005b:
            r4 = r5
        L_0x005c:
            if (r4 != 0) goto L_0x0065
        L_0x005e:
            float r4 = -r11
            float r5 = r9.top
            float r5 = -r5
            r7.postTranslate(r4, r5)
        L_0x0065:
            r4 = 90
            if (r3 == r4) goto L_0x007b
            r4 = 270(0x10e, float:3.78E-43)
            if (r3 != r4) goto L_0x006e
            goto L_0x007b
        L_0x006e:
            int r4 = r18.getWidth()
            int r5 = r18.getHeight()
            android.graphics.Bitmap r4 = r0.c(r4, r5, r2)
            goto L_0x0087
        L_0x007b:
            int r4 = r18.getHeight()
            int r5 = r18.getWidth()
            android.graphics.Bitmap r4 = r0.c(r4, r5, r2)
        L_0x0087:
            r5 = r4
            r11 = 0
            android.graphics.Canvas r12 = new android.graphics.Canvas
            r12.<init>(r5)
            r13 = r12
            r14 = 0
            r15 = r16
            android.graphics.Paint r0 = r15.d
            r13.drawBitmap(r1, r7, r0)
            r17.b(r18)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.decode.a.d(coil.bitmap.c, android.graphics.Bitmap, android.graphics.Bitmap$Config, boolean, int):android.graphics.Bitmap");
    }

    /* compiled from: BitmapFactoryDecoder.kt */
    public static final class b extends okio.k {
        @Nullable
        private Exception c;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull e0 delegate) {
            super(delegate);
            k.e(delegate, "delegate");
        }

        @Nullable
        public final Exception a() {
            return this.c;
        }

        public long read(@NotNull okio.c sink, long byteCount) {
            k.e(sink, "sink");
            try {
                return super.read(sink, byteCount);
            } catch (Exception e) {
                this.c = e;
                throw e;
            }
        }
    }

    /* compiled from: BitmapFactoryDecoder.kt */
    public static final class c extends InputStream {
        @NotNull
        private final InputStream c;
        private volatile int d = 1073741824;

        public c(@NotNull InputStream delegate) {
            k.e(delegate, "delegate");
            this.c = delegate;
        }

        public int read() {
            return a(this.c.read());
        }

        public int read(@NotNull byte[] b) {
            k.e(b, "b");
            return a(this.c.read(b));
        }

        public int read(@NotNull byte[] b, int off, int len) {
            k.e(b, "b");
            return a(this.c.read(b, off, len));
        }

        public long skip(long n) {
            return this.c.skip(n);
        }

        public int available() {
            return this.d;
        }

        public void close() {
            this.c.close();
        }

        private final int a(int bytesRead) {
            if (bytesRead == -1) {
                this.d = 0;
            }
            return bytesRead;
        }
    }

    /* renamed from: coil.decode.a$a  reason: collision with other inner class name */
    /* compiled from: BitmapFactoryDecoder.kt */
    public static final class C0003a {
        public /* synthetic */ C0003a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private C0003a() {
        }
    }
}
