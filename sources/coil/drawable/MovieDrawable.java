package coil.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import coil.decode.d;
import coil.size.e;
import coil.transform.c;
import coil.util.GifExtensions;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MovieDrawable.kt */
public final class MovieDrawable extends Drawable implements Animatable2Compat {
    @NotNull
    public static final a c = new a((DefaultConstructorMarker) null);
    private boolean A4;
    private long B4;
    private long C4;
    private int D4 = -1;
    private int E4;
    @Nullable
    private coil.transform.a F4;
    @Nullable
    private Picture G4;
    @NotNull
    private c H4 = c.UNCHANGED;
    private boolean I4;
    @NotNull
    private final Rect a1 = new Rect();
    @Nullable
    private Bitmap a2;
    @NotNull
    private final Movie d;
    @NotNull
    private final coil.bitmap.c f;
    @NotNull
    private final Rect p0 = new Rect();
    @Nullable
    private Canvas p1;
    private float p2 = 1.0f;
    private float p3 = 1.0f;
    private float p4;
    @NotNull
    private final Bitmap.Config q;
    @NotNull
    private final e x;
    @NotNull
    private final Paint y = new Paint(3);
    @NotNull
    private final List<Animatable2Compat.AnimationCallback> z = new ArrayList();
    private float z4;

    public MovieDrawable(@NotNull Movie movie, @NotNull coil.bitmap.c pool, @NotNull Bitmap.Config config, @NotNull e scale) {
        k.e(movie, "movie");
        k.e(pool, "pool");
        k.e(config, "config");
        k.e(scale, "scale");
        this.d = movie;
        this.f = pool;
        this.q = config;
        this.x = scale;
        if (!(!GifExtensions.f(config))) {
            throw new IllegalArgumentException("Bitmap config must not be hardware.".toString());
        }
    }

    public void draw(@NotNull Canvas canvas) {
        k.e(canvas, "canvas");
        boolean invalidate = f();
        if (this.I4) {
            e(b(canvas));
            Canvas $this$withSave$iv = canvas;
            int checkpoint$iv = $this$withSave$iv.save();
            Canvas $this$draw_u24lambda_u2d1 = $this$withSave$iv;
            try {
                float scale = ((float) 1) / this.p2;
                $this$draw_u24lambda_u2d1.scale(scale, scale);
                a(canvas);
            } finally {
                $this$withSave$iv.restoreToCount(checkpoint$iv);
            }
        } else {
            Rect bounds = getBounds();
            k.d(bounds, "bounds");
            e(bounds);
            a(canvas);
        }
        if (!this.A4 || !invalidate) {
            stop();
        } else {
            invalidateSelf();
        }
    }

    private final boolean f() {
        int time;
        boolean invalidate;
        int duration = this.d.duration();
        if (duration == 0) {
            invalidate = false;
            time = 0;
        } else {
            if (this.A4) {
                this.C4 = SystemClock.uptimeMillis();
            }
            int elapsedTime = (int) (this.C4 - this.B4);
            int i = elapsedTime / duration;
            this.E4 = i;
            int i2 = this.D4;
            invalidate = i2 == -1 || i <= i2;
            time = invalidate ? elapsedTime - (i * duration) : duration;
        }
        this.d.setTime(time);
        return invalidate;
    }

    /* JADX INFO: finally extract failed */
    private final void a(Canvas canvas) {
        Canvas softwareCanvas = this.p1;
        Bitmap softwareBitmap = this.a2;
        if (softwareCanvas != null && softwareBitmap != null) {
            softwareCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
            Canvas $this$withSave$iv = softwareCanvas;
            int checkpoint$iv = $this$withSave$iv.save();
            Canvas $this$drawFrame_u24lambda_u2d2 = $this$withSave$iv;
            try {
                float f2 = this.p2;
                $this$drawFrame_u24lambda_u2d2.scale(f2, f2);
                this.d.draw($this$drawFrame_u24lambda_u2d2, 0.0f, 0.0f, this.y);
                Picture picture = this.G4;
                if (picture != null) {
                    picture.draw($this$drawFrame_u24lambda_u2d2);
                }
                $this$withSave$iv.restoreToCount(checkpoint$iv);
                Canvas $this$withSave$iv2 = canvas;
                int checkpoint$iv2 = $this$withSave$iv2.save();
                Canvas $this$drawFrame_u24lambda_u2d3 = $this$withSave$iv2;
                try {
                    $this$drawFrame_u24lambda_u2d3.translate(this.p4, this.z4);
                    float f3 = this.p3;
                    $this$drawFrame_u24lambda_u2d3.scale(f3, f3);
                    $this$drawFrame_u24lambda_u2d3.drawBitmap(softwareBitmap, 0.0f, 0.0f, this.y);
                } finally {
                    $this$withSave$iv2.restoreToCount(checkpoint$iv2);
                }
            } catch (Throwable th) {
                $this$withSave$iv.restoreToCount(checkpoint$iv);
                throw th;
            }
        }
    }

    public final void d(int repeatCount) {
        if (repeatCount >= -1) {
            this.D4 = repeatCount;
            return;
        }
        throw new IllegalArgumentException(k.l("Invalid repeatCount: ", Integer.valueOf(repeatCount)).toString());
    }

    public final void c(@Nullable coil.transform.a animatedTransformation) {
        this.F4 = animatedTransformation;
        if (animatedTransformation == null || this.d.width() <= 0 || this.d.height() <= 0) {
            this.G4 = null;
            this.H4 = c.UNCHANGED;
            this.I4 = false;
        } else {
            Picture picture = new Picture();
            Canvas canvas = picture.beginRecording(this.d.width(), this.d.height());
            k.d(canvas, "picture.beginRecording(movie.width(), movie.height())");
            this.H4 = animatedTransformation.a(canvas);
            picture.endRecording();
            this.G4 = picture;
            this.I4 = true;
        }
        invalidateSelf();
    }

    public void setAlpha(int alpha) {
        boolean z2 = false;
        if (alpha >= 0 && alpha <= 255) {
            z2 = true;
        }
        if (z2) {
            this.y.setAlpha(alpha);
            return;
        }
        throw new IllegalArgumentException(k.l("Invalid alpha: ", Integer.valueOf(alpha)).toString());
    }

    public int getOpacity() {
        c cVar;
        if (this.y.getAlpha() != 255 || ((cVar = this.H4) != c.OPAQUE && (cVar != c.UNCHANGED || !this.d.isOpaque()))) {
            return -3;
        }
        return -1;
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.y.setColorFilter(colorFilter);
    }

    private final void e(Rect bounds) {
        if (!k.a(this.p0, bounds)) {
            this.p0.set(bounds);
            int boundsWidth = bounds.width();
            int boundsHeight = bounds.height();
            int movieWidth = this.d.width();
            int movieHeight = this.d.height();
            if (movieWidth > 0 && movieHeight > 0) {
                d dVar = d.a;
                double $this$updateBounds_u24lambda_u2d6 = d.d(movieWidth, movieHeight, boundsWidth, boundsHeight, this.x);
                if (!this.I4) {
                    $this$updateBounds_u24lambda_u2d6 = n.d($this$updateBounds_u24lambda_u2d6, 1.0d);
                }
                float f2 = (float) $this$updateBounds_u24lambda_u2d6;
                this.p2 = f2;
                int bitmapWidth = (int) (((float) movieWidth) * f2);
                int bitmapHeight = (int) (f2 * ((float) movieHeight));
                Bitmap bitmap = this.f.c(bitmapWidth, bitmapHeight, this.q);
                Bitmap p02 = this.a2;
                if (p02 != null) {
                    this.f.b(p02);
                }
                this.a2 = bitmap;
                this.p1 = new Canvas(bitmap);
                if (this.I4) {
                    this.p3 = 1.0f;
                    this.p4 = 0.0f;
                    this.z4 = 0.0f;
                    return;
                }
                float d2 = (float) d.d(bitmapWidth, bitmapHeight, boundsWidth, boundsHeight, this.x);
                this.p3 = d2;
                float f3 = (float) 2;
                this.p4 = ((float) bounds.left) + ((((float) boundsWidth) - (((float) bitmapWidth) * d2)) / f3);
                this.z4 = ((float) bounds.top) + ((((float) boundsHeight) - (d2 * ((float) bitmapHeight))) / f3);
            }
        }
    }

    public int getIntrinsicWidth() {
        return this.d.width();
    }

    public int getIntrinsicHeight() {
        return this.d.height();
    }

    public boolean isRunning() {
        return this.A4;
    }

    public void start() {
        if (!this.A4) {
            this.A4 = true;
            int i = 0;
            this.E4 = 0;
            this.B4 = SystemClock.uptimeMillis();
            List $this$forEachIndices$iv = this.z;
            int size = $this$forEachIndices$iv.size() - 1;
            if (size >= 0) {
                do {
                    int i$iv = i;
                    i++;
                    $this$forEachIndices$iv.get(i$iv).onAnimationStart(this);
                } while (i <= size);
            }
            invalidateSelf();
        }
    }

    public void stop() {
        if (this.A4) {
            int i = 0;
            this.A4 = false;
            List $this$forEachIndices$iv = this.z;
            int size = $this$forEachIndices$iv.size() - 1;
            if (size >= 0) {
                do {
                    int i$iv = i;
                    i++;
                    $this$forEachIndices$iv.get(i$iv).onAnimationEnd(this);
                } while (i <= size);
            }
        }
    }

    public void registerAnimationCallback(@NotNull Animatable2Compat.AnimationCallback callback) {
        k.e(callback, "callback");
        this.z.add(callback);
    }

    public boolean unregisterAnimationCallback(@NotNull Animatable2Compat.AnimationCallback callback) {
        k.e(callback, "callback");
        return this.z.remove(callback);
    }

    public void clearAnimationCallbacks() {
        this.z.clear();
    }

    private final Rect b(Canvas $this$bounds) {
        Rect $this$_get_bounds__u24lambda_u2d9 = this.a1;
        $this$_get_bounds__u24lambda_u2d9.set(0, 0, $this$bounds.getWidth(), $this$bounds.getHeight());
        return $this$_get_bounds__u24lambda_u2d9;
    }

    /* compiled from: MovieDrawable.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
