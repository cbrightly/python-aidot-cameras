package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.keyframe.a;
import com.airbnb.lottie.animation.keyframe.q;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.j0;
import com.airbnb.lottie.value.c;

/* compiled from: SolidLayer */
public class h extends b {
    private final RectF D = new RectF();
    private final Paint E;
    private final float[] F;
    private final Path G;
    private final e H;
    @Nullable
    private a<ColorFilter, ColorFilter> I;

    h(e0 lottieDrawable, e layerModel) {
        super(lottieDrawable, layerModel);
        com.airbnb.lottie.animation.a aVar = new com.airbnb.lottie.animation.a();
        this.E = aVar;
        this.F = new float[8];
        this.G = new Path();
        this.H = layerModel;
        aVar.setAlpha(0);
        aVar.setStyle(Paint.Style.FILL);
        aVar.setColor(layerModel.o());
    }

    public void s(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        int backgroundAlpha = Color.alpha(this.H.o());
        if (backgroundAlpha != 0) {
            int alpha = (int) ((((float) parentAlpha) / 255.0f) * (((((float) backgroundAlpha) / 255.0f) * ((float) (this.x.h() == null ? 100 : this.x.h().h().intValue()))) / 100.0f) * 255.0f);
            this.E.setAlpha(alpha);
            a<ColorFilter, ColorFilter> aVar = this.I;
            if (aVar != null) {
                this.E.setColorFilter(aVar.h());
            }
            if (alpha > 0) {
                float[] fArr = this.F;
                fArr[0] = 0.0f;
                fArr[1] = 0.0f;
                fArr[2] = (float) this.H.q();
                float[] fArr2 = this.F;
                fArr2[3] = 0.0f;
                fArr2[4] = (float) this.H.q();
                this.F[5] = (float) this.H.p();
                float[] fArr3 = this.F;
                fArr3[6] = 0.0f;
                fArr3[7] = (float) this.H.p();
                parentMatrix.mapPoints(this.F);
                this.G.reset();
                Path path = this.G;
                float[] fArr4 = this.F;
                path.moveTo(fArr4[0], fArr4[1]);
                Path path2 = this.G;
                float[] fArr5 = this.F;
                path2.lineTo(fArr5[2], fArr5[3]);
                Path path3 = this.G;
                float[] fArr6 = this.F;
                path3.lineTo(fArr6[4], fArr6[5]);
                Path path4 = this.G;
                float[] fArr7 = this.F;
                path4.lineTo(fArr7[6], fArr7[7]);
                Path path5 = this.G;
                float[] fArr8 = this.F;
                path5.lineTo(fArr8[0], fArr8[1]);
                this.G.close();
                canvas.drawPath(this.G, this.E);
                return;
            }
            Canvas canvas2 = canvas;
            Matrix matrix = parentMatrix;
        }
    }

    public void f(RectF outBounds, Matrix parentMatrix, boolean applyParents) {
        super.f(outBounds, parentMatrix, applyParents);
        this.D.set(0.0f, 0.0f, (float) this.H.q(), (float) this.H.p());
        this.o.mapRect(this.D);
        outBounds.set(this.D);
    }

    public <T> void d(T property, @Nullable c<T> callback) {
        super.d(property, callback);
        if (property != j0.K) {
            return;
        }
        if (callback == null) {
            this.I = null;
        } else {
            this.I = new q(callback);
        }
    }
}
