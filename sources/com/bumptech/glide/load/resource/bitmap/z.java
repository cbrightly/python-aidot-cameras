package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.util.i;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: TransformationUtils */
public final class z {
    private static final Paint a = new Paint(6);
    private static final Paint b = new Paint(7);
    private static final Paint c;
    private static final Set<String> d;
    private static final Lock e;

    /* compiled from: TransformationUtils */
    public interface b {
        void a(Canvas canvas, Paint paint, RectF rectF);
    }

    static {
        HashSet hashSet = new HashSet(Arrays.asList(new String[]{"XT1085", "XT1092", "XT1093", "XT1094", "XT1095", "XT1096", "XT1097", "XT1098", "XT1031", "XT1028", "XT937C", "XT1032", "XT1008", "XT1033", "XT1035", "XT1034", "XT939G", "XT1039", "XT1040", "XT1042", "XT1045", "XT1063", "XT1064", "XT1068", "XT1069", "XT1072", "XT1077", "XT1078", "XT1079"}));
        d = hashSet;
        e = hashSet.contains(Build.MODEL) ? new ReentrantLock() : new c();
        Paint paint = new Paint(7);
        c = paint;
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    public static Lock i() {
        return e;
    }

    public static Bitmap b(@NonNull e pool, @NonNull Bitmap inBitmap, int width, int height) {
        float dy;
        float dx;
        float scale;
        if (inBitmap.getWidth() == width && inBitmap.getHeight() == height) {
            return inBitmap;
        }
        Matrix m = new Matrix();
        if (inBitmap.getWidth() * height > inBitmap.getHeight() * width) {
            scale = ((float) height) / ((float) inBitmap.getHeight());
            dx = (((float) width) - (((float) inBitmap.getWidth()) * scale)) * 0.5f;
            dy = 0.0f;
        } else {
            scale = ((float) width) / ((float) inBitmap.getWidth());
            dx = 0.0f;
            dy = (((float) height) - (((float) inBitmap.getHeight()) * scale)) * 0.5f;
        }
        m.setScale(scale, scale);
        m.postTranslate((float) ((int) (dx + 0.5f)), (float) ((int) (0.5f + dy)));
        Bitmap result = pool.c(width, height, k(inBitmap));
        q(inBitmap, result);
        a(inBitmap, result, m);
        return result;
    }

    public static Bitmap f(@NonNull e pool, @NonNull Bitmap inBitmap, int width, int height) {
        if (inBitmap.getWidth() == width && inBitmap.getHeight() == height) {
            if (Log.isLoggable("TransformationUtils", 2)) {
                Log.v("TransformationUtils", "requested target size matches input, returning input");
            }
            return inBitmap;
        }
        float minPercentage = Math.min(((float) width) / ((float) inBitmap.getWidth()), ((float) height) / ((float) inBitmap.getHeight()));
        int targetWidth = Math.round(((float) inBitmap.getWidth()) * minPercentage);
        int targetHeight = Math.round(((float) inBitmap.getHeight()) * minPercentage);
        if (inBitmap.getWidth() == targetWidth && inBitmap.getHeight() == targetHeight) {
            if (Log.isLoggable("TransformationUtils", 2)) {
                Log.v("TransformationUtils", "adjusted target size matches input, returning input");
            }
            return inBitmap;
        }
        Bitmap toReuse = pool.c((int) (((float) inBitmap.getWidth()) * minPercentage), (int) (((float) inBitmap.getHeight()) * minPercentage), k(inBitmap));
        q(inBitmap, toReuse);
        if (Log.isLoggable("TransformationUtils", 2)) {
            Log.v("TransformationUtils", "request: " + width + "x" + height);
            Log.v("TransformationUtils", "toFit:   " + inBitmap.getWidth() + "x" + inBitmap.getHeight());
            Log.v("TransformationUtils", "toReuse: " + toReuse.getWidth() + "x" + toReuse.getHeight());
            StringBuilder sb = new StringBuilder();
            sb.append("minPct:   ");
            sb.append(minPercentage);
            Log.v("TransformationUtils", sb.toString());
        }
        Matrix matrix = new Matrix();
        matrix.setScale(minPercentage, minPercentage);
        a(inBitmap, toReuse, matrix);
        return toReuse;
    }

    public static Bitmap c(@NonNull e pool, @NonNull Bitmap inBitmap, int width, int height) {
        if (inBitmap.getWidth() > width || inBitmap.getHeight() > height) {
            if (Log.isLoggable("TransformationUtils", 2)) {
                Log.v("TransformationUtils", "requested target size too big for input, fit centering instead");
            }
            return f(pool, inBitmap, width, height);
        }
        if (Log.isLoggable("TransformationUtils", 2)) {
            Log.v("TransformationUtils", "requested target size larger or equal to input, returning input");
        }
        return inBitmap;
    }

    public static void q(Bitmap inBitmap, Bitmap outBitmap) {
        outBitmap.setHasAlpha(inBitmap.hasAlpha());
    }

    public static int j(int exifOrientation) {
        switch (exifOrientation) {
            case 3:
            case 4:
                return 180;
            case 5:
            case 6:
                return 90;
            case 7:
            case 8:
                return SubsamplingScaleImageView.ORIENTATION_270;
            default:
                return 0;
        }
    }

    public static Bitmap n(@NonNull e pool, @NonNull Bitmap inBitmap, int exifOrientation) {
        if (!m(exifOrientation)) {
            return inBitmap;
        }
        Matrix matrix = new Matrix();
        l(exifOrientation, matrix);
        RectF newRect = new RectF(0.0f, 0.0f, (float) inBitmap.getWidth(), (float) inBitmap.getHeight());
        matrix.mapRect(newRect);
        Bitmap result = pool.c(Math.round(newRect.width()), Math.round(newRect.height()), k(inBitmap));
        matrix.postTranslate(-newRect.left, -newRect.top);
        result.setHasAlpha(inBitmap.hasAlpha());
        a(inBitmap, result, matrix);
        return result;
    }

    public static boolean m(int exifOrientation) {
        switch (exifOrientation) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return true;
            default:
                return false;
        }
    }

    public static Bitmap d(@NonNull e pool, @NonNull Bitmap inBitmap, int destWidth, int destHeight) {
        e eVar = pool;
        int destMinEdge = Math.min(destWidth, destHeight);
        float radius = ((float) destMinEdge) / 2.0f;
        int srcWidth = inBitmap.getWidth();
        int srcHeight = inBitmap.getHeight();
        float maxScale = Math.max(((float) destMinEdge) / ((float) srcWidth), ((float) destMinEdge) / ((float) srcHeight));
        float scaledWidth = maxScale * ((float) srcWidth);
        float scaledHeight = maxScale * ((float) srcHeight);
        float left = (((float) destMinEdge) - scaledWidth) / 2.0f;
        float top = (((float) destMinEdge) - scaledHeight) / 2.0f;
        RectF destRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);
        Bitmap toTransform = g(pool, inBitmap);
        float f = top;
        Bitmap result = eVar.c(destMinEdge, destMinEdge, h(inBitmap));
        result.setHasAlpha(true);
        Lock lock = e;
        lock.lock();
        int i = destMinEdge;
        try {
            Canvas canvas = new Canvas(result);
            int i2 = srcWidth;
            try {
                canvas.drawCircle(radius, radius, radius, b);
                float f2 = radius;
            } catch (Throwable th) {
                th = th;
                Bitmap bitmap = inBitmap;
                float f3 = radius;
                e.unlock();
                throw th;
            }
            try {
                canvas.drawBitmap(toTransform, (Rect) null, destRect, c);
                e(canvas);
                lock.unlock();
                if (!toTransform.equals(inBitmap)) {
                    eVar.b(toTransform);
                }
                return result;
            } catch (Throwable th2) {
                th = th2;
                Bitmap bitmap2 = inBitmap;
                e.unlock();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            Bitmap bitmap3 = inBitmap;
            float f4 = radius;
            int i3 = srcWidth;
            e.unlock();
            throw th;
        }
    }

    private static Bitmap g(@NonNull e pool, @NonNull Bitmap maybeAlphaSafe) {
        Bitmap.Config safeConfig = h(maybeAlphaSafe);
        if (safeConfig.equals(maybeAlphaSafe.getConfig())) {
            return maybeAlphaSafe;
        }
        Bitmap argbBitmap = pool.c(maybeAlphaSafe.getWidth(), maybeAlphaSafe.getHeight(), safeConfig);
        new Canvas(argbBitmap).drawBitmap(maybeAlphaSafe, 0.0f, 0.0f, (Paint) null);
        return argbBitmap;
    }

    @NonNull
    private static Bitmap.Config h(@NonNull Bitmap inBitmap) {
        if (Build.VERSION.SDK_INT < 26 || !Bitmap.Config.RGBA_F16.equals(inBitmap.getConfig())) {
            return Bitmap.Config.ARGB_8888;
        }
        return Bitmap.Config.RGBA_F16;
    }

    public static Bitmap o(@NonNull e pool, @NonNull Bitmap inBitmap, int roundingRadius) {
        i.a(roundingRadius > 0, "roundingRadius must be greater than 0.");
        return p(pool, inBitmap, new a(roundingRadius));
    }

    /* compiled from: TransformationUtils */
    public class a implements b {
        final /* synthetic */ int a;

        a(int i) {
            this.a = i;
        }

        public void a(Canvas canvas, Paint paint, RectF rect) {
            int i = this.a;
            canvas.drawRoundRect(rect, (float) i, (float) i, paint);
        }
    }

    private static Bitmap p(@NonNull e pool, @NonNull Bitmap inBitmap, b drawRoundedCornerFn) {
        Bitmap.Config safeConfig = h(inBitmap);
        Bitmap toTransform = g(pool, inBitmap);
        Bitmap result = pool.c(toTransform.getWidth(), toTransform.getHeight(), safeConfig);
        result.setHasAlpha(true);
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        BitmapShader shader = new BitmapShader(toTransform, tileMode, tileMode);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        RectF rect = new RectF(0.0f, 0.0f, (float) result.getWidth(), (float) result.getHeight());
        Lock lock = e;
        lock.lock();
        try {
            Canvas canvas = new Canvas(result);
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            drawRoundedCornerFn.a(canvas, paint, rect);
            e(canvas);
            lock.unlock();
            if (!toTransform.equals(inBitmap)) {
                pool.b(toTransform);
            }
            return result;
        } catch (Throwable th) {
            e.unlock();
            throw th;
        }
    }

    private static void e(Canvas canvas) {
        canvas.setBitmap((Bitmap) null);
    }

    @NonNull
    private static Bitmap.Config k(@NonNull Bitmap bitmap) {
        return bitmap.getConfig() != null ? bitmap.getConfig() : Bitmap.Config.ARGB_8888;
    }

    private static void a(@NonNull Bitmap inBitmap, @NonNull Bitmap targetBitmap, Matrix matrix) {
        Lock lock = e;
        lock.lock();
        try {
            Canvas canvas = new Canvas(targetBitmap);
            canvas.drawBitmap(inBitmap, matrix, a);
            e(canvas);
            lock.unlock();
        } catch (Throwable th) {
            e.unlock();
            throw th;
        }
    }

    @VisibleForTesting
    static void l(int exifOrientation, Matrix matrix) {
        switch (exifOrientation) {
            case 2:
                matrix.setScale(-1.0f, 1.0f);
                return;
            case 3:
                matrix.setRotate(180.0f);
                return;
            case 4:
                matrix.setRotate(180.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            case 5:
                matrix.setRotate(90.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            case 6:
                matrix.setRotate(90.0f);
                return;
            case 7:
                matrix.setRotate(-90.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            case 8:
                matrix.setRotate(-90.0f);
                return;
            default:
                return;
        }
    }

    /* compiled from: TransformationUtils */
    public static final class c implements Lock {
        c() {
        }

        public void lock() {
        }

        public void lockInterruptibly() {
        }

        public boolean tryLock() {
            return true;
        }

        public boolean tryLock(long time, @NonNull TimeUnit unit) {
            return true;
        }

        public void unlock() {
        }

        @NonNull
        public Condition newCondition() {
            throw new UnsupportedOperationException("Should not be called");
        }
    }
}
