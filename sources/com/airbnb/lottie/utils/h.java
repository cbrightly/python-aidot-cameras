package com.airbnb.lottie.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.content.u;
import com.airbnb.lottie.b0;
import java.io.Closeable;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.nio.channels.ClosedChannelException;
import javax.net.ssl.SSLException;

/* compiled from: Utils */
public final class h {
    private static final ThreadLocal<PathMeasure> a = new a();
    private static final ThreadLocal<Path> b = new b();
    private static final ThreadLocal<Path> c = new c();
    private static final ThreadLocal<float[]> d = new d();
    private static final float e = ((float) (Math.sqrt(2.0d) / 2.0d));

    /* compiled from: Utils */
    public class a extends ThreadLocal<PathMeasure> {
        a() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public PathMeasure initialValue() {
            return new PathMeasure();
        }
    }

    /* compiled from: Utils */
    public class b extends ThreadLocal<Path> {
        b() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Path initialValue() {
            return new Path();
        }
    }

    /* compiled from: Utils */
    public class c extends ThreadLocal<Path> {
        c() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Path initialValue() {
            return new Path();
        }
    }

    /* compiled from: Utils */
    public class d extends ThreadLocal<float[]> {
        d() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public float[] initialValue() {
            return new float[4];
        }
    }

    public static Path d(PointF startPoint, PointF endPoint, PointF cp1, PointF cp2) {
        Path path = new Path();
        path.moveTo(startPoint.x, startPoint.y);
        if (cp1 == null || cp2 == null || (cp1.length() == 0.0f && cp2.length() == 0.0f)) {
            path.lineTo(endPoint.x, endPoint.y);
        } else {
            float f = cp1.x + startPoint.x;
            float f2 = cp1.y + startPoint.y;
            float f3 = endPoint.x;
            float f4 = f3 + cp2.x;
            float f5 = endPoint.y;
            path.cubicTo(f, f2, f4, f5 + cp2.y, f3, f5);
        }
        return path;
    }

    public static void c(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception e2) {
            }
        }
    }

    public static float g(Matrix matrix) {
        float[] points = d.get();
        points[0] = 0.0f;
        points[1] = 0.0f;
        float f = e;
        points[2] = f;
        points[3] = f;
        matrix.mapPoints(points);
        return (float) Math.hypot((double) (points[2] - points[0]), (double) (points[3] - points[1]));
    }

    public static boolean h(Matrix matrix) {
        float[] points = d.get();
        points[0] = 0.0f;
        points[1] = 0.0f;
        points[2] = 37394.73f;
        points[3] = 39575.234f;
        matrix.mapPoints(points);
        if (points[0] == points[2] || points[1] == points[3]) {
            return true;
        }
        return false;
    }

    public static void b(Path path, @Nullable u trimPath) {
        if (trimPath != null && !trimPath.k()) {
            a(path, ((com.airbnb.lottie.animation.keyframe.d) trimPath.i()).p() / 100.0f, ((com.airbnb.lottie.animation.keyframe.d) trimPath.e()).p() / 100.0f, ((com.airbnb.lottie.animation.keyframe.d) trimPath.g()).p() / 360.0f);
        }
    }

    public static void a(Path path, float startValue, float endValue, float offsetValue) {
        Path path2 = path;
        b0.a("applyTrimPathIfNeeded");
        PathMeasure pathMeasure = a.get();
        Path tempPath = b.get();
        Path tempPath2 = c.get();
        pathMeasure.setPath(path, false);
        float length = pathMeasure.getLength();
        if (startValue == 1.0f && endValue == 0.0f) {
            b0.b("applyTrimPathIfNeeded");
        } else if (length < 1.0f || ((double) Math.abs((endValue - startValue) - 1.0f)) < 0.01d) {
            b0.b("applyTrimPathIfNeeded");
        } else {
            float start = length * startValue;
            float end = length * endValue;
            float offset = offsetValue * length;
            float newStart = Math.min(start, end) + offset;
            float newEnd = Math.max(start, end) + offset;
            if (newStart >= length && newEnd >= length) {
                newStart = (float) g.f(newStart, length);
                newEnd = (float) g.f(newEnd, length);
            }
            if (newStart < 0.0f) {
                newStart = (float) g.f(newStart, length);
            }
            if (newEnd < 0.0f) {
                newEnd = (float) g.f(newEnd, length);
            }
            if (newStart == newEnd) {
                path.reset();
                b0.b("applyTrimPathIfNeeded");
                return;
            }
            if (newStart >= newEnd) {
                newStart -= length;
            }
            tempPath.reset();
            pathMeasure.getSegment(newStart, newEnd, tempPath, true);
            if (newEnd > length) {
                tempPath2.reset();
                pathMeasure.getSegment(0.0f, newEnd % length, tempPath2, true);
                tempPath.addPath(tempPath2);
            } else if (newStart < 0.0f) {
                tempPath2.reset();
                pathMeasure.getSegment(length + newStart, length, tempPath2, true);
                tempPath.addPath(tempPath2);
            }
            path.set(tempPath);
            b0.b("applyTrimPathIfNeeded");
        }
    }

    public static boolean j(int major, int minor, int patch, int minMajor, int minMinor, int minPatch) {
        if (major < minMajor) {
            return false;
        }
        if (major > minMajor) {
            return true;
        }
        if (minor < minMinor) {
            return false;
        }
        if (minor > minMinor) {
            return true;
        }
        if (patch >= minPatch) {
            return true;
        }
        return false;
    }

    public static int i(float a2, float b2, float c2, float d2) {
        int result = 17;
        if (a2 != 0.0f) {
            result = (int) (((float) (17 * 31)) * a2);
        }
        if (b2 != 0.0f) {
            result = (int) (((float) (result * 31)) * b2);
        }
        if (c2 != 0.0f) {
            result = (int) (((float) (result * 31)) * c2);
        }
        if (d2 != 0.0f) {
            return (int) (((float) (result * 31)) * d2);
        }
        return result;
    }

    public static float e() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    public static float f(Context context) {
        if (Build.VERSION.SDK_INT >= 17) {
            return Settings.Global.getFloat(context.getContentResolver(), "animator_duration_scale", 1.0f);
        }
        return Settings.System.getFloat(context.getContentResolver(), "animator_duration_scale", 1.0f);
    }

    public static Bitmap l(Bitmap bitmap, int width, int height) {
        if (bitmap.getWidth() == width && bitmap.getHeight() == height) {
            return bitmap;
        }
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        bitmap.recycle();
        return resizedBitmap;
    }

    public static boolean k(Throwable e2) {
        return (e2 instanceof SocketException) || (e2 instanceof ClosedChannelException) || (e2 instanceof InterruptedIOException) || (e2 instanceof ProtocolException) || (e2 instanceof SSLException) || (e2 instanceof UnknownHostException) || (e2 instanceof UnknownServiceException);
    }

    public static void m(Canvas canvas, RectF rect, Paint paint) {
        n(canvas, rect, paint, 31);
    }

    public static void n(Canvas canvas, RectF rect, Paint paint, int flag) {
        b0.a("Utils#saveLayer");
        if (Build.VERSION.SDK_INT < 23) {
            canvas.saveLayer(rect, paint, flag);
        } else {
            canvas.saveLayer(rect, paint);
        }
        b0.b("Utils#saveLayer");
    }
}
