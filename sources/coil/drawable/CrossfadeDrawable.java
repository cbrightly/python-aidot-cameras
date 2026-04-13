package coil.drawable;

import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import coil.decode.d;
import coil.size.e;
import com.leedarson.bean.Constants;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.math.b;
import kotlin.ranges.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CrossfadeDrawable.kt */
public final class CrossfadeDrawable extends Drawable implements Drawable.Callback, Animatable2Compat {
    @NotNull
    public static final a c = new a((DefaultConstructorMarker) null);
    @Nullable
    private Drawable a1;
    private long a2;
    @NotNull
    private final e d;
    private final int f;
    private final int p0;
    @Nullable
    private final Drawable p1;
    private int p2;
    private int p3;
    private final boolean q;
    private final boolean x;
    @NotNull
    private final List<Animatable2Compat.AnimationCallback> y = new ArrayList();
    private final int z;

    public CrossfadeDrawable(@Nullable Drawable start, @Nullable Drawable end, @NotNull e scale, int durationMillis, boolean fadeStart, boolean preferExactIntrinsicSize) {
        k.e(scale, "scale");
        this.d = scale;
        this.f = durationMillis;
        this.q = fadeStart;
        this.x = preferExactIntrinsicSize;
        Drawable drawable = null;
        this.z = a(start == null ? null : Integer.valueOf(start.getIntrinsicWidth()), end == null ? null : Integer.valueOf(end.getIntrinsicWidth()));
        this.p0 = a(start == null ? null : Integer.valueOf(start.getIntrinsicHeight()), end == null ? null : Integer.valueOf(end.getIntrinsicHeight()));
        this.a1 = start == null ? null : start.mutate();
        drawable = end != null ? end.mutate() : drawable;
        this.p1 = drawable;
        this.p2 = 255;
        if (durationMillis > 0) {
            Drawable drawable2 = this.a1;
            if (drawable2 != null) {
                drawable2.setCallback(this);
            }
            if (drawable != null) {
                drawable.setCallback(this);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("durationMillis must be > 0.".toString());
    }

    public void draw(@NotNull Canvas canvas) {
        Drawable $this$draw_u24lambda_u2d6;
        k.e(canvas, "canvas");
        int i = this.p3;
        if (i == 0) {
            Drawable $this$draw_u24lambda_u2d2 = this.a1;
            if ($this$draw_u24lambda_u2d2 != null) {
                $this$draw_u24lambda_u2d2.setAlpha(this.p2);
                Canvas $this$withSave$iv = canvas;
                int checkpoint$iv = $this$withSave$iv.save();
                Canvas canvas2 = $this$withSave$iv;
                try {
                    $this$draw_u24lambda_u2d2.draw(canvas);
                } finally {
                    $this$withSave$iv.restoreToCount(checkpoint$iv);
                }
            }
        } else if (i == 2) {
            Drawable $this$draw_u24lambda_u2d4 = this.p1;
            if ($this$draw_u24lambda_u2d4 != null) {
                $this$draw_u24lambda_u2d4.setAlpha(this.p2);
                Canvas $this$withSave$iv2 = canvas;
                int checkpoint$iv2 = $this$withSave$iv2.save();
                Canvas canvas3 = $this$withSave$iv2;
                try {
                    $this$draw_u24lambda_u2d4.draw(canvas);
                } finally {
                    $this$withSave$iv2.restoreToCount(checkpoint$iv2);
                }
            }
        } else {
            double percent = ((double) (SystemClock.uptimeMillis() - this.a2)) / ((double) this.f);
            double g = n.g(percent, 0.0d, 1.0d);
            int i2 = this.p2;
            int endAlpha = (int) (g * ((double) i2));
            if (this.q) {
                i2 -= endAlpha;
            }
            int startAlpha = i2;
            boolean isDone = percent >= 1.0d;
            if (!isDone && ($this$draw_u24lambda_u2d6 = this.a1) != null) {
                $this$draw_u24lambda_u2d6.setAlpha(startAlpha);
                Canvas $this$withSave$iv3 = canvas;
                int checkpoint$iv3 = $this$withSave$iv3.save();
                Canvas canvas4 = $this$withSave$iv3;
                try {
                    $this$draw_u24lambda_u2d6.draw(canvas);
                } finally {
                    $this$withSave$iv3.restoreToCount(checkpoint$iv3);
                }
            }
            Drawable $this$draw_u24lambda_u2d62 = this.p1;
            if ($this$draw_u24lambda_u2d62 != null) {
                $this$draw_u24lambda_u2d62.setAlpha(endAlpha);
                Canvas $this$withSave$iv4 = canvas;
                int checkpoint$iv4 = $this$withSave$iv4.save();
                Canvas canvas5 = $this$withSave$iv4;
                try {
                    $this$draw_u24lambda_u2d62.draw(canvas);
                } finally {
                    $this$withSave$iv4.restoreToCount(checkpoint$iv4);
                }
            }
            if (isDone) {
                b();
            } else {
                invalidateSelf();
            }
        }
    }

    @RequiresApi(19)
    public int getAlpha() {
        return this.p2;
    }

    public void setAlpha(int alpha) {
        boolean z2 = false;
        if (alpha >= 0 && alpha <= 255) {
            z2 = true;
        }
        if (z2) {
            this.p2 = alpha;
            return;
        }
        throw new IllegalArgumentException(k.l("Invalid alpha: ", Integer.valueOf(alpha)).toString());
    }

    public int getOpacity() {
        Drawable start = this.a1;
        Drawable end = this.p1;
        int i = this.p3;
        if (i == 0) {
            if (start == null) {
                return -2;
            }
            return start.getOpacity();
        } else if (i == 2) {
            if (end == null) {
                return -2;
            }
            return end.getOpacity();
        } else if (start != null && end != null) {
            return Drawable.resolveOpacity(start.getOpacity(), end.getOpacity());
        } else {
            if (start != null) {
                return start.getOpacity();
            }
            if (end != null) {
                return end.getOpacity();
            }
            return -2;
        }
    }

    @RequiresApi(21)
    @Nullable
    public ColorFilter getColorFilter() {
        switch (this.p3) {
            case 0:
                Drawable drawable = this.a1;
                if (drawable == null) {
                    return null;
                }
                return drawable.getColorFilter();
            case 1:
                Drawable drawable2 = this.p1;
                ColorFilter colorFilter = drawable2 == null ? null : drawable2.getColorFilter();
                if (colorFilter != null) {
                    return colorFilter;
                }
                Drawable drawable3 = this.a1;
                if (drawable3 == null) {
                    return null;
                }
                return drawable3.getColorFilter();
            case 2:
                Drawable drawable4 = this.p1;
                if (drawable4 == null) {
                    return null;
                }
                return drawable4.getColorFilter();
            default:
                return null;
        }
    }

    @RequiresApi(21)
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        Drawable drawable = this.a1;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        }
        Drawable drawable2 = this.p1;
        if (drawable2 != null) {
            drawable2.setColorFilter(colorFilter);
        }
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(@NotNull Rect bounds) {
        k.e(bounds, "bounds");
        Drawable it = this.a1;
        if (it != null) {
            c(it, bounds);
        }
        Drawable it2 = this.p1;
        if (it2 != null) {
            c(it2, bounds);
        }
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int level) {
        Drawable drawable = this.a1;
        boolean startChanged = drawable == null ? false : drawable.setLevel(level);
        Drawable drawable2 = this.p1;
        boolean endChanged = drawable2 == null ? false : drawable2.setLevel(level);
        if (startChanged || endChanged) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(@NotNull int[] state) {
        k.e(state, Constants.ACTION_STATE);
        Drawable drawable = this.a1;
        boolean startChanged = drawable == null ? false : drawable.setState(state);
        Drawable drawable2 = this.p1;
        boolean endChanged = drawable2 == null ? false : drawable2.setState(state);
        if (startChanged || endChanged) {
            return true;
        }
        return false;
    }

    public int getIntrinsicWidth() {
        return this.z;
    }

    public int getIntrinsicHeight() {
        return this.p0;
    }

    public void unscheduleDrawable(@NotNull Drawable who, @NotNull Runnable what) {
        k.e(who, "who");
        k.e(what, "what");
        unscheduleSelf(what);
    }

    public void invalidateDrawable(@NotNull Drawable who) {
        k.e(who, "who");
        invalidateSelf();
    }

    public void scheduleDrawable(@NotNull Drawable who, @NotNull Runnable what, long when) {
        k.e(who, "who");
        k.e(what, "what");
        scheduleSelf(what, when);
    }

    @RequiresApi(21)
    public void setTint(int tintColor) {
        Drawable drawable = this.a1;
        if (drawable != null) {
            drawable.setTint(tintColor);
        }
        Drawable drawable2 = this.p1;
        if (drawable2 != null) {
            drawable2.setTint(tintColor);
        }
    }

    @RequiresApi(21)
    public void setTintList(@Nullable ColorStateList tint) {
        Drawable drawable = this.a1;
        if (drawable != null) {
            drawable.setTintList(tint);
        }
        Drawable drawable2 = this.p1;
        if (drawable2 != null) {
            drawable2.setTintList(tint);
        }
    }

    @RequiresApi(21)
    public void setTintMode(@Nullable PorterDuff.Mode tintMode) {
        Drawable drawable = this.a1;
        if (drawable != null) {
            drawable.setTintMode(tintMode);
        }
        Drawable drawable2 = this.p1;
        if (drawable2 != null) {
            drawable2.setTintMode(tintMode);
        }
    }

    @RequiresApi(29)
    public void setTintBlendMode(@Nullable BlendMode blendMode) {
        Drawable drawable = this.a1;
        if (drawable != null) {
            drawable.setTintBlendMode(blendMode);
        }
        Drawable drawable2 = this.p1;
        if (drawable2 != null) {
            drawable2.setTintBlendMode(blendMode);
        }
    }

    public boolean isRunning() {
        return this.p3 == 1;
    }

    /* JADX WARNING: type inference failed for: r0v2, types: [android.graphics.drawable.Drawable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void start() {
        /*
            r8 = this;
            android.graphics.drawable.Drawable r0 = r8.a1
            boolean r1 = r0 instanceof android.graphics.drawable.Animatable
            r2 = 0
            if (r1 == 0) goto L_0x000a
            android.graphics.drawable.Animatable r0 = (android.graphics.drawable.Animatable) r0
            goto L_0x000b
        L_0x000a:
            r0 = r2
        L_0x000b:
            if (r0 != 0) goto L_0x000e
            goto L_0x0011
        L_0x000e:
            r0.start()
        L_0x0011:
            android.graphics.drawable.Drawable r0 = r8.p1
            boolean r1 = r0 instanceof android.graphics.drawable.Animatable
            if (r1 == 0) goto L_0x001a
            r2 = r0
            android.graphics.drawable.Animatable r2 = (android.graphics.drawable.Animatable) r2
        L_0x001a:
            if (r2 != 0) goto L_0x001d
            goto L_0x0020
        L_0x001d:
            r2.start()
        L_0x0020:
            int r0 = r8.p3
            if (r0 == 0) goto L_0x0025
            return
        L_0x0025:
            r0 = 1
            r8.p3 = r0
            long r1 = android.os.SystemClock.uptimeMillis()
            r8.a2 = r1
            java.util.List<androidx.vectordrawable.graphics.drawable.Animatable2Compat$AnimationCallback> r1 = r8.y
            r2 = 0
            r3 = 0
            int r4 = r1.size()
            int r4 = r4 + -1
            if (r4 < 0) goto L_0x0048
        L_0x003a:
            r5 = r3
            int r3 = r3 + r0
            java.lang.Object r6 = r1.get(r5)
            androidx.vectordrawable.graphics.drawable.Animatable2Compat$AnimationCallback r6 = (androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback) r6
            r7 = 0
            r6.onAnimationStart(r8)
            if (r3 <= r4) goto L_0x003a
        L_0x0048:
            r8.invalidateSelf()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.drawable.CrossfadeDrawable.start():void");
    }

    /* JADX WARNING: type inference failed for: r0v2, types: [android.graphics.drawable.Drawable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void stop() {
        /*
            r3 = this;
            android.graphics.drawable.Drawable r0 = r3.a1
            boolean r1 = r0 instanceof android.graphics.drawable.Animatable
            r2 = 0
            if (r1 == 0) goto L_0x000a
            android.graphics.drawable.Animatable r0 = (android.graphics.drawable.Animatable) r0
            goto L_0x000b
        L_0x000a:
            r0 = r2
        L_0x000b:
            if (r0 != 0) goto L_0x000e
            goto L_0x0011
        L_0x000e:
            r0.stop()
        L_0x0011:
            android.graphics.drawable.Drawable r0 = r3.p1
            boolean r1 = r0 instanceof android.graphics.drawable.Animatable
            if (r1 == 0) goto L_0x001a
            r2 = r0
            android.graphics.drawable.Animatable r2 = (android.graphics.drawable.Animatable) r2
        L_0x001a:
            if (r2 != 0) goto L_0x001d
            goto L_0x0020
        L_0x001d:
            r2.stop()
        L_0x0020:
            int r0 = r3.p3
            r1 = 2
            if (r0 == r1) goto L_0x0028
            r3.b()
        L_0x0028:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.drawable.CrossfadeDrawable.stop():void");
    }

    public void registerAnimationCallback(@NotNull Animatable2Compat.AnimationCallback callback) {
        k.e(callback, "callback");
        this.y.add(callback);
    }

    public boolean unregisterAnimationCallback(@NotNull Animatable2Compat.AnimationCallback callback) {
        k.e(callback, "callback");
        return this.y.remove(callback);
    }

    public void clearAnimationCallbacks() {
        this.y.clear();
    }

    @VisibleForTesting
    public final void c(@NotNull Drawable drawable, @NotNull Rect targetBounds) {
        Drawable drawable2 = drawable;
        Rect rect = targetBounds;
        k.e(drawable2, "drawable");
        k.e(rect, "targetBounds");
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        if (width > 0) {
            if (height > 0) {
                int targetWidth = targetBounds.width();
                int targetHeight = targetBounds.height();
                d dVar = d.a;
                double multiplier = d.d(width, height, targetWidth, targetHeight, this.d);
                double d2 = (double) 2;
                int dx = b.a((((double) targetWidth) - (((double) width) * multiplier)) / d2);
                int dy = b.a((((double) targetHeight) - (((double) height) * multiplier)) / d2);
                drawable2.setBounds(rect.left + dx, rect.top + dy, rect.right - dx, rect.bottom - dy);
                return;
            }
        }
        drawable.setBounds(targetBounds);
    }

    private final int a(Integer startSize, Integer endSize) {
        int i = -1;
        if (!this.x) {
            if (startSize != null && startSize.intValue() == -1) {
                return -1;
            }
            if (endSize != null && endSize.intValue() == -1) {
                return -1;
            }
        }
        int intValue = startSize == null ? -1 : startSize.intValue();
        if (endSize != null) {
            i = endSize.intValue();
        }
        return Math.max(intValue, i);
    }

    private final void b() {
        this.p3 = 2;
        this.a1 = null;
        List $this$forEachIndices$iv = this.y;
        int size = $this$forEachIndices$iv.size() - 1;
        if (size >= 0) {
            int i = 0;
            do {
                int i$iv = i;
                i++;
                $this$forEachIndices$iv.get(i$iv).onAnimationEnd(this);
            } while (i <= size);
        }
    }

    /* compiled from: CrossfadeDrawable.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
