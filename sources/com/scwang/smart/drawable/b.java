package com.scwang.smart.drawable;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import androidx.annotation.NonNull;

/* compiled from: ProgressDrawable */
public class b extends a implements Animatable, ValueAnimator.AnimatorUpdateListener {
    protected int d = 0;
    protected int f = 0;
    protected int q = 0;
    protected ValueAnimator x;
    protected Path y = new Path();

    public b() {
        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{30, 3600});
        this.x = ofInt;
        ofInt.setDuration(10000);
        this.x.setInterpolator((TimeInterpolator) null);
        this.x.setRepeatCount(-1);
        this.x.setRepeatMode(1);
    }

    public void onAnimationUpdate(ValueAnimator animation) {
        this.q = (((Integer) animation.getAnimatedValue()).intValue() / 30) * 30;
        invalidateSelf();
    }

    public void draw(@NonNull Canvas canvas) {
        Canvas canvas2 = canvas;
        Rect bounds = getBounds();
        int width = bounds.width();
        int height = bounds.height();
        float r = Math.max(1.0f, ((float) width) / 22.0f);
        if (!(this.d == width && this.f == height)) {
            this.y.reset();
            this.y.addCircle(((float) width) - r, ((float) height) / 2.0f, r, Path.Direction.CW);
            this.y.addRect(((float) width) - (r * 5.0f), (((float) height) / 2.0f) - r, ((float) width) - r, (((float) height) / 2.0f) + r, Path.Direction.CW);
            this.y.addCircle(((float) width) - (5.0f * r), ((float) height) / 2.0f, r, Path.Direction.CW);
            this.d = width;
            this.f = height;
        }
        canvas.save();
        canvas2.rotate((float) this.q, ((float) width) / 2.0f, ((float) height) / 2.0f);
        for (int i = 0; i < 12; i++) {
            this.c.setAlpha((i + 5) * 17);
            canvas2.rotate(30.0f, ((float) width) / 2.0f, ((float) height) / 2.0f);
            canvas2.drawPath(this.y, this.c);
        }
        canvas.restore();
    }

    public void start() {
        if (!this.x.isRunning()) {
            this.x.addUpdateListener(this);
            this.x.start();
        }
    }

    public void stop() {
        if (this.x.isRunning()) {
            this.x.removeAllListeners();
            this.x.removeAllUpdateListeners();
            this.x.cancel();
        }
    }

    public boolean isRunning() {
        return this.x.isRunning();
    }
}
