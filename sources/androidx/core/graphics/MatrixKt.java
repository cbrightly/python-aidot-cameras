package androidx.core.graphics;

import android.graphics.Matrix;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Matrix.kt */
public final class MatrixKt {
    @NotNull
    public static final Matrix times(@NotNull Matrix $this$times, @NotNull Matrix m) {
        k.e($this$times, "<this>");
        k.e(m, "m");
        Matrix $this$times_u24lambda_u2d0 = new Matrix($this$times);
        $this$times_u24lambda_u2d0.preConcat(m);
        return $this$times_u24lambda_u2d0;
    }

    @NotNull
    public static final float[] values(@NotNull Matrix $this$values) {
        k.e($this$values, "<this>");
        float[] $this$values_u24lambda_u2d1 = new float[9];
        $this$values.getValues($this$values_u24lambda_u2d1);
        return $this$values_u24lambda_u2d1;
    }

    public static /* synthetic */ Matrix translationMatrix$default(float f, float f2, int i, Object obj) {
        if ((i & 1) != 0) {
            f = 0.0f;
        }
        if ((i & 2) != 0) {
            f2 = 0.0f;
        }
        return translationMatrix(f, f2);
    }

    @NotNull
    public static final Matrix translationMatrix(float tx, float ty) {
        Matrix $this$translationMatrix_u24lambda_u2d2 = new Matrix();
        $this$translationMatrix_u24lambda_u2d2.setTranslate(tx, ty);
        return $this$translationMatrix_u24lambda_u2d2;
    }

    public static /* synthetic */ Matrix scaleMatrix$default(float f, float f2, int i, Object obj) {
        if ((i & 1) != 0) {
            f = 1.0f;
        }
        if ((i & 2) != 0) {
            f2 = 1.0f;
        }
        return scaleMatrix(f, f2);
    }

    @NotNull
    public static final Matrix scaleMatrix(float sx, float sy) {
        Matrix $this$scaleMatrix_u24lambda_u2d3 = new Matrix();
        $this$scaleMatrix_u24lambda_u2d3.setScale(sx, sy);
        return $this$scaleMatrix_u24lambda_u2d3;
    }

    public static /* synthetic */ Matrix rotationMatrix$default(float f, float f2, float f3, int i, Object obj) {
        if ((i & 2) != 0) {
            f2 = 0.0f;
        }
        if ((i & 4) != 0) {
            f3 = 0.0f;
        }
        return rotationMatrix(f, f2, f3);
    }

    @NotNull
    public static final Matrix rotationMatrix(float degrees, float px, float py) {
        Matrix $this$rotationMatrix_u24lambda_u2d4 = new Matrix();
        $this$rotationMatrix_u24lambda_u2d4.setRotate(degrees, px, py);
        return $this$rotationMatrix_u24lambda_u2d4;
    }
}
