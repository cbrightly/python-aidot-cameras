package coil.drawable;

import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import androidx.annotation.RequiresApi;
import coil.decode.d;
import coil.size.e;
import com.leedarson.bean.Constants;
import kotlin.jvm.internal.k;
import kotlin.math.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ScaleDrawable.kt */
public final class a extends Drawable implements Drawable.Callback, Animatable {
    @NotNull
    private final Drawable c;
    @NotNull
    private final e d;
    private float f;
    private float q;
    private float x = 1.0f;

    @NotNull
    public final Drawable a() {
        return this.c;
    }

    public a(@NotNull Drawable child, @NotNull e scale) {
        k.e(child, "child");
        k.e(scale, "scale");
        this.c = child;
        this.d = scale;
        child.setCallback(this);
    }

    public void draw(@NotNull Canvas canvas) {
        k.e(canvas, "canvas");
        Canvas $this$withSave$iv = canvas;
        int checkpoint$iv = $this$withSave$iv.save();
        Canvas $this$draw_u24lambda_u2d0 = $this$withSave$iv;
        try {
            $this$draw_u24lambda_u2d0.translate(this.f, this.q);
            float f2 = this.x;
            $this$draw_u24lambda_u2d0.scale(f2, f2);
            a().draw($this$draw_u24lambda_u2d0);
        } finally {
            $this$withSave$iv.restoreToCount(checkpoint$iv);
        }
    }

    @RequiresApi(19)
    public int getAlpha() {
        return this.c.getAlpha();
    }

    public void setAlpha(int alpha) {
        this.c.setAlpha(alpha);
    }

    public int getOpacity() {
        return this.c.getOpacity();
    }

    @RequiresApi(21)
    @Nullable
    public ColorFilter getColorFilter() {
        return this.c.getColorFilter();
    }

    @RequiresApi(21)
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.c.setColorFilter(colorFilter);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(@NotNull Rect bounds) {
        k.e(bounds, "bounds");
        int width = this.c.getIntrinsicWidth();
        int height = this.c.getIntrinsicHeight();
        if (width <= 0 || height <= 0) {
            this.c.setBounds(bounds);
            this.f = 0.0f;
            this.q = 0.0f;
            this.x = 1.0f;
            return;
        }
        int targetWidth = bounds.width();
        int targetHeight = bounds.height();
        d dVar = d.a;
        double multiplier = d.d(width, height, targetWidth, targetHeight, this.d);
        double d2 = (double) 2;
        int left = b.a((((double) targetWidth) - (((double) width) * multiplier)) / d2);
        int top = b.a((((double) targetHeight) - (((double) height) * multiplier)) / d2);
        this.c.setBounds(left, top, left + width, top + height);
        this.f = (float) bounds.left;
        this.q = (float) bounds.top;
        this.x = (float) multiplier;
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int level) {
        return this.c.setLevel(level);
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(@NotNull int[] state) {
        k.e(state, Constants.ACTION_STATE);
        return this.c.setState(state);
    }

    public int getIntrinsicWidth() {
        return this.c.getIntrinsicWidth();
    }

    public int getIntrinsicHeight() {
        return this.c.getIntrinsicHeight();
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
        this.c.setTint(tintColor);
    }

    @RequiresApi(21)
    public void setTintList(@Nullable ColorStateList tint) {
        this.c.setTintList(tint);
    }

    @RequiresApi(21)
    public void setTintMode(@Nullable PorterDuff.Mode tintMode) {
        this.c.setTintMode(tintMode);
    }

    @RequiresApi(29)
    public void setTintBlendMode(@Nullable BlendMode blendMode) {
        this.c.setTintBlendMode(blendMode);
    }

    public boolean isRunning() {
        Drawable drawable = this.c;
        return (drawable instanceof Animatable) && ((Animatable) drawable).isRunning();
    }

    public void start() {
        Drawable drawable = this.c;
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }

    public void stop() {
        Drawable drawable = this.c;
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).stop();
        }
    }
}
