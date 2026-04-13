package com.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.bumptech.glide.b;
import com.bumptech.glide.load.m;
import com.bumptech.glide.load.resource.gif.f;
import com.bumptech.glide.util.i;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class GifDrawable extends Drawable implements f.b, Animatable, Animatable2Compat {
    private Paint a1;
    private List<Animatable2Compat.AnimationCallback> a2;
    private final a c;
    private boolean d;
    private boolean f;
    private boolean p0;
    private Rect p1;
    private boolean q;
    private boolean x;
    private int y;
    private int z;

    public GifDrawable(Context context, com.bumptech.glide.gifdecoder.a gifDecoder, m<Bitmap> frameTransformation, int targetFrameWidth, int targetFrameHeight, Bitmap firstFrame) {
        this(new a(new f(b.c(context), gifDecoder, targetFrameWidth, targetFrameHeight, frameTransformation, firstFrame)));
    }

    GifDrawable(a state) {
        this.x = true;
        this.z = -1;
        this.c = (a) i.d(state);
    }

    public int i() {
        return this.c.a.j();
    }

    public Bitmap e() {
        return this.c.a.e();
    }

    public void m(m<Bitmap> frameTransformation, Bitmap firstFrame) {
        this.c.a.o(frameTransformation, firstFrame);
    }

    public ByteBuffer c() {
        return this.c.a.b();
    }

    public int f() {
        return this.c.a.f();
    }

    public int g() {
        return this.c.a.d();
    }

    private void l() {
        this.y = 0;
    }

    public void start() {
        this.f = true;
        l();
        if (this.x) {
            n();
        }
    }

    public void stop() {
        this.f = false;
        o();
    }

    private void n() {
        i.a(!this.q, "You cannot start a recycled Drawable. Ensure thatyou clear any references to the Drawable when clearing the corresponding request.");
        if (this.c.a.f() == 1) {
            invalidateSelf();
        } else if (!this.d) {
            this.d = true;
            this.c.a.r(this);
            invalidateSelf();
        }
    }

    private void o() {
        this.d = false;
        this.c.a.s(this);
    }

    public boolean setVisible(boolean visible, boolean restart) {
        i.a(!this.q, "Cannot change the visibility of a recycled resource. Ensure that you unset the Drawable from your View before changing the View's visibility.");
        this.x = visible;
        if (!visible) {
            o();
        } else if (this.f) {
            n();
        }
        return super.setVisible(visible, restart);
    }

    public int getIntrinsicWidth() {
        return this.c.a.k();
    }

    public int getIntrinsicHeight() {
        return this.c.a.h();
    }

    public boolean isRunning() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.p0 = true;
    }

    public void draw(@NonNull Canvas canvas) {
        if (!this.q) {
            if (this.p0) {
                Gravity.apply(119, getIntrinsicWidth(), getIntrinsicHeight(), getBounds(), d());
                this.p0 = false;
            }
            canvas.drawBitmap(this.c.a.c(), (Rect) null, d(), h());
        }
    }

    public void setAlpha(int i) {
        h().setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        h().setColorFilter(colorFilter);
    }

    private Rect d() {
        if (this.p1 == null) {
            this.p1 = new Rect();
        }
        return this.p1;
    }

    private Paint h() {
        if (this.a1 == null) {
            this.a1 = new Paint(2);
        }
        return this.a1;
    }

    public int getOpacity() {
        return -2;
    }

    private Drawable.Callback b() {
        Drawable.Callback callback = getCallback();
        while (callback instanceof Drawable) {
            callback = ((Drawable) callback).getCallback();
        }
        return callback;
    }

    public void a() {
        if (b() == null) {
            stop();
            invalidateSelf();
            return;
        }
        invalidateSelf();
        if (g() == f() - 1) {
            this.y++;
        }
        int i = this.z;
        if (i != -1 && this.y >= i) {
            j();
            stop();
        }
    }

    private void j() {
        List<Animatable2Compat.AnimationCallback> list = this.a2;
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                this.a2.get(i).onAnimationEnd(this);
            }
        }
    }

    public Drawable.ConstantState getConstantState() {
        return this.c;
    }

    public void k() {
        this.q = true;
        this.c.a.a();
    }

    public void registerAnimationCallback(@NonNull Animatable2Compat.AnimationCallback animationCallback) {
        if (animationCallback != null) {
            if (this.a2 == null) {
                this.a2 = new ArrayList();
            }
            this.a2.add(animationCallback);
        }
    }

    public boolean unregisterAnimationCallback(@NonNull Animatable2Compat.AnimationCallback animationCallback) {
        List<Animatable2Compat.AnimationCallback> list = this.a2;
        if (list == null || animationCallback == null) {
            return false;
        }
        return list.remove(animationCallback);
    }

    public void clearAnimationCallbacks() {
        List<Animatable2Compat.AnimationCallback> list = this.a2;
        if (list != null) {
            list.clear();
        }
    }

    public static final class a extends Drawable.ConstantState {
        @VisibleForTesting
        final f a;

        a(f frameLoader) {
            this.a = frameLoader;
        }

        @NonNull
        public Drawable newDrawable(Resources res) {
            return newDrawable();
        }

        @NonNull
        public Drawable newDrawable() {
            return new GifDrawable(this);
        }

        public int getChangingConfigurations() {
            return 0;
        }
    }
}
