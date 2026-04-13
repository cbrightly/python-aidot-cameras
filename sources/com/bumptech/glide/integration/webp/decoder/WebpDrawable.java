package com.bumptech.glide.integration.webp.decoder;

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
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.bumptech.glide.b;
import com.bumptech.glide.integration.webp.decoder.o;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.m;
import com.bumptech.glide.util.i;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class WebpDrawable extends Drawable implements o.b, Animatable, Animatable2Compat {
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

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public WebpDrawable(Context context, i webDecoder, e bitmapPool, m<Bitmap> frameTransformation, int targetFrameWidth, int targetFrameHeight, Bitmap firstFrame) {
        this(new a(bitmapPool, new o(b.c(context), webDecoder, targetFrameWidth, targetFrameHeight, frameTransformation, firstFrame)));
        e eVar = bitmapPool;
    }

    WebpDrawable(a state) {
        this.x = true;
        this.z = -1;
        this.x = true;
        this.z = -1;
        this.c = (a) i.d(state);
    }

    public int i() {
        return this.c.b.j();
    }

    public Bitmap e() {
        return this.c.b.e();
    }

    public void n(m<Bitmap> frameTransformation, Bitmap firstFrame) {
        this.c.b.o(frameTransformation, firstFrame);
    }

    public ByteBuffer c() {
        return this.c.b.b();
    }

    public int f() {
        return this.c.b.f();
    }

    public int g() {
        return this.c.b.d();
    }

    private void m() {
        this.y = 0;
    }

    public void start() {
        this.f = true;
        m();
        if (this.x) {
            o();
        }
    }

    public void stop() {
        this.f = false;
        p();
    }

    private void o() {
        i.a(!this.q, "You cannot start a recycled Drawable. Ensure thatyou clear any references to the Drawable when clearing the corresponding request.");
        if (this.c.b.f() == 1) {
            invalidateSelf();
        } else if (!this.d) {
            this.d = true;
            this.c.b.r(this);
            invalidateSelf();
        }
    }

    private void p() {
        this.d = false;
        this.c.b.s(this);
    }

    public boolean setVisible(boolean visible, boolean restart) {
        i.a(!this.q, "Cannot change the visibility of a recycled resource. Ensure that you unset the Drawable from your View before changing the View's visibility.");
        this.x = visible;
        if (!visible) {
            p();
        } else if (this.f) {
            o();
        }
        return super.setVisible(visible, restart);
    }

    public int getIntrinsicWidth() {
        return this.c.b.k();
    }

    public int getIntrinsicHeight() {
        return this.c.b.h();
    }

    public boolean isRunning() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.p0 = true;
    }

    public void draw(Canvas canvas) {
        if (!j()) {
            if (this.p0) {
                Gravity.apply(119, getIntrinsicWidth(), getIntrinsicHeight(), getBounds(), d());
                this.p0 = false;
            }
            canvas.drawBitmap(this.c.b.c(), (Rect) null, d(), h());
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

    @Deprecated
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
            stop();
            k();
        }
    }

    private void k() {
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

    public void l() {
        this.q = true;
        this.c.b.a();
    }

    /* access modifiers changed from: package-private */
    public boolean j() {
        return this.q;
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

    public static class a extends Drawable.ConstantState {
        final e a;
        final o b;

        public a(e bitmapPool, o frameLoader) {
            this.a = bitmapPool;
            this.b = frameLoader;
        }

        public Drawable newDrawable(Resources res) {
            return newDrawable();
        }

        public Drawable newDrawable() {
            return new WebpDrawable(this);
        }

        public int getChangingConfigurations() {
            return 0;
        }
    }
}
