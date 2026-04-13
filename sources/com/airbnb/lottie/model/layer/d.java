package com.airbnb.lottie.model.layer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.a;
import com.airbnb.lottie.animation.keyframe.q;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.f0;
import com.airbnb.lottie.j0;
import com.airbnb.lottie.utils.h;
import com.airbnb.lottie.value.c;

/* compiled from: ImageLayer */
public class d extends b {
    private final Paint D = new a(3);
    private final Rect E = new Rect();
    private final Rect F = new Rect();
    @Nullable
    private final f0 G;
    @Nullable
    private com.airbnb.lottie.animation.keyframe.a<ColorFilter, ColorFilter> H;
    @Nullable
    private com.airbnb.lottie.animation.keyframe.a<Bitmap, Bitmap> I;

    d(e0 lottieDrawable, e layerModel) {
        super(lottieDrawable, layerModel);
        this.G = lottieDrawable.z(layerModel.m());
    }

    public void s(@NonNull Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        Bitmap bitmap = O();
        if (bitmap != null && !bitmap.isRecycled() && this.G != null) {
            float density = h.e();
            this.D.setAlpha(parentAlpha);
            com.airbnb.lottie.animation.keyframe.a<ColorFilter, ColorFilter> aVar = this.H;
            if (aVar != null) {
                this.D.setColorFilter(aVar.h());
            }
            canvas.save();
            canvas.concat(parentMatrix);
            this.E.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
            if (this.p.A()) {
                this.F.set(0, 0, (int) (((float) this.G.e()) * density), (int) (((float) this.G.c()) * density));
            } else {
                this.F.set(0, 0, (int) (((float) bitmap.getWidth()) * density), (int) (((float) bitmap.getHeight()) * density));
            }
            canvas.drawBitmap(bitmap, this.E, this.F, this.D);
            canvas.restore();
        }
    }

    public void f(RectF outBounds, Matrix parentMatrix, boolean applyParents) {
        super.f(outBounds, parentMatrix, applyParents);
        if (this.G != null) {
            float scale = h.e();
            outBounds.set(0.0f, 0.0f, ((float) this.G.e()) * scale, ((float) this.G.c()) * scale);
            this.o.mapRect(outBounds);
        }
    }

    @Nullable
    private Bitmap O() {
        Bitmap callbackBitmap;
        com.airbnb.lottie.animation.keyframe.a<Bitmap, Bitmap> aVar = this.I;
        if (aVar != null && (callbackBitmap = aVar.h()) != null) {
            return callbackBitmap;
        }
        Bitmap bitmapFromDrawable = this.p.r(this.q.m());
        if (bitmapFromDrawable != null) {
            return bitmapFromDrawable;
        }
        f0 asset = this.G;
        if (asset != null) {
            return asset.a();
        }
        return null;
    }

    public <T> void d(T property, @Nullable c<T> callback) {
        super.d(property, callback);
        if (property == j0.K) {
            if (callback == null) {
                this.H = null;
            } else {
                this.H = new q(callback);
            }
        } else if (property != j0.N) {
        } else {
            if (callback == null) {
                this.I = null;
            } else {
                this.I = new q(callback);
            }
        }
    }
}
