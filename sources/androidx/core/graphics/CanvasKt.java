package androidx.core.graphics;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.j;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: Canvas.kt */
public final class CanvasKt {
    public static final void withSave(@NotNull Canvas $this$withSave, @NotNull l<? super Canvas, x> block) {
        k.e($this$withSave, "<this>");
        k.e(block, "block");
        int checkpoint = $this$withSave.save();
        try {
            block.invoke($this$withSave);
        } finally {
            j.b(1);
            $this$withSave.restoreToCount(checkpoint);
            j.a(1);
        }
    }

    public static /* synthetic */ void withTranslation$default(Canvas $this$withTranslation_u24default, float x, float y, l block, int i, Object obj) {
        if ((i & 1) != 0) {
            x = 0.0f;
        }
        if ((i & 2) != 0) {
            y = 0.0f;
        }
        k.e($this$withTranslation_u24default, "<this>");
        k.e(block, "block");
        int checkpoint = $this$withTranslation_u24default.save();
        $this$withTranslation_u24default.translate(x, y);
        try {
            block.invoke($this$withTranslation_u24default);
        } finally {
            j.b(1);
            $this$withTranslation_u24default.restoreToCount(checkpoint);
            j.a(1);
        }
    }

    public static final void withTranslation(@NotNull Canvas $this$withTranslation, float x, float y, @NotNull l<? super Canvas, x> block) {
        k.e($this$withTranslation, "<this>");
        k.e(block, "block");
        int checkpoint = $this$withTranslation.save();
        $this$withTranslation.translate(x, y);
        try {
            block.invoke($this$withTranslation);
        } finally {
            j.b(1);
            $this$withTranslation.restoreToCount(checkpoint);
            j.a(1);
        }
    }

    public static /* synthetic */ void withRotation$default(Canvas $this$withRotation_u24default, float degrees, float pivotX, float pivotY, l block, int i, Object obj) {
        if ((i & 1) != 0) {
            degrees = 0.0f;
        }
        if ((i & 2) != 0) {
            pivotX = 0.0f;
        }
        if ((i & 4) != 0) {
            pivotY = 0.0f;
        }
        k.e($this$withRotation_u24default, "<this>");
        k.e(block, "block");
        int checkpoint = $this$withRotation_u24default.save();
        $this$withRotation_u24default.rotate(degrees, pivotX, pivotY);
        try {
            block.invoke($this$withRotation_u24default);
        } finally {
            j.b(1);
            $this$withRotation_u24default.restoreToCount(checkpoint);
            j.a(1);
        }
    }

    public static final void withRotation(@NotNull Canvas $this$withRotation, float degrees, float pivotX, float pivotY, @NotNull l<? super Canvas, x> block) {
        k.e($this$withRotation, "<this>");
        k.e(block, "block");
        int checkpoint = $this$withRotation.save();
        $this$withRotation.rotate(degrees, pivotX, pivotY);
        try {
            block.invoke($this$withRotation);
        } finally {
            j.b(1);
            $this$withRotation.restoreToCount(checkpoint);
            j.a(1);
        }
    }

    public static /* synthetic */ void withScale$default(Canvas $this$withScale_u24default, float x, float y, float pivotX, float pivotY, l block, int i, Object obj) {
        if ((i & 1) != 0) {
            x = 1.0f;
        }
        if ((i & 2) != 0) {
            y = 1.0f;
        }
        if ((i & 4) != 0) {
            pivotX = 0.0f;
        }
        if ((i & 8) != 0) {
            pivotY = 0.0f;
        }
        k.e($this$withScale_u24default, "<this>");
        k.e(block, "block");
        int checkpoint = $this$withScale_u24default.save();
        $this$withScale_u24default.scale(x, y, pivotX, pivotY);
        try {
            block.invoke($this$withScale_u24default);
        } finally {
            j.b(1);
            $this$withScale_u24default.restoreToCount(checkpoint);
            j.a(1);
        }
    }

    public static final void withScale(@NotNull Canvas $this$withScale, float x, float y, float pivotX, float pivotY, @NotNull l<? super Canvas, x> block) {
        k.e($this$withScale, "<this>");
        k.e(block, "block");
        int checkpoint = $this$withScale.save();
        $this$withScale.scale(x, y, pivotX, pivotY);
        try {
            block.invoke($this$withScale);
        } finally {
            j.b(1);
            $this$withScale.restoreToCount(checkpoint);
            j.a(1);
        }
    }

    public static /* synthetic */ void withSkew$default(Canvas $this$withSkew_u24default, float x, float y, l block, int i, Object obj) {
        if ((i & 1) != 0) {
            x = 0.0f;
        }
        if ((i & 2) != 0) {
            y = 0.0f;
        }
        k.e($this$withSkew_u24default, "<this>");
        k.e(block, "block");
        int checkpoint = $this$withSkew_u24default.save();
        $this$withSkew_u24default.skew(x, y);
        try {
            block.invoke($this$withSkew_u24default);
        } finally {
            j.b(1);
            $this$withSkew_u24default.restoreToCount(checkpoint);
            j.a(1);
        }
    }

    public static final void withSkew(@NotNull Canvas $this$withSkew, float x, float y, @NotNull l<? super Canvas, x> block) {
        k.e($this$withSkew, "<this>");
        k.e(block, "block");
        int checkpoint = $this$withSkew.save();
        $this$withSkew.skew(x, y);
        try {
            block.invoke($this$withSkew);
        } finally {
            j.b(1);
            $this$withSkew.restoreToCount(checkpoint);
            j.a(1);
        }
    }

    public static /* synthetic */ void withMatrix$default(Canvas $this$withMatrix_u24default, Matrix matrix, l block, int i, Object obj) {
        if ((i & 1) != 0) {
            matrix = new Matrix();
        }
        k.e($this$withMatrix_u24default, "<this>");
        k.e(matrix, "matrix");
        k.e(block, "block");
        int checkpoint = $this$withMatrix_u24default.save();
        $this$withMatrix_u24default.concat(matrix);
        try {
            block.invoke($this$withMatrix_u24default);
        } finally {
            j.b(1);
            $this$withMatrix_u24default.restoreToCount(checkpoint);
            j.a(1);
        }
    }

    public static final void withMatrix(@NotNull Canvas $this$withMatrix, @NotNull Matrix matrix, @NotNull l<? super Canvas, x> block) {
        k.e($this$withMatrix, "<this>");
        k.e(matrix, "matrix");
        k.e(block, "block");
        int checkpoint = $this$withMatrix.save();
        $this$withMatrix.concat(matrix);
        try {
            block.invoke($this$withMatrix);
        } finally {
            j.b(1);
            $this$withMatrix.restoreToCount(checkpoint);
            j.a(1);
        }
    }

    public static final void withClip(@NotNull Canvas $this$withClip, @NotNull Rect clipRect, @NotNull l<? super Canvas, x> block) {
        k.e($this$withClip, "<this>");
        k.e(clipRect, "clipRect");
        k.e(block, "block");
        int checkpoint = $this$withClip.save();
        $this$withClip.clipRect(clipRect);
        try {
            block.invoke($this$withClip);
        } finally {
            j.b(1);
            $this$withClip.restoreToCount(checkpoint);
            j.a(1);
        }
    }

    public static final void withClip(@NotNull Canvas $this$withClip, @NotNull RectF clipRect, @NotNull l<? super Canvas, x> block) {
        k.e($this$withClip, "<this>");
        k.e(clipRect, "clipRect");
        k.e(block, "block");
        int checkpoint = $this$withClip.save();
        $this$withClip.clipRect(clipRect);
        try {
            block.invoke($this$withClip);
        } finally {
            j.b(1);
            $this$withClip.restoreToCount(checkpoint);
            j.a(1);
        }
    }

    public static final void withClip(@NotNull Canvas $this$withClip, int left, int top, int right, int bottom, @NotNull l<? super Canvas, x> block) {
        k.e($this$withClip, "<this>");
        k.e(block, "block");
        int checkpoint = $this$withClip.save();
        $this$withClip.clipRect(left, top, right, bottom);
        try {
            block.invoke($this$withClip);
        } finally {
            j.b(1);
            $this$withClip.restoreToCount(checkpoint);
            j.a(1);
        }
    }

    public static final void withClip(@NotNull Canvas $this$withClip, float left, float top, float right, float bottom, @NotNull l<? super Canvas, x> block) {
        k.e($this$withClip, "<this>");
        k.e(block, "block");
        int checkpoint = $this$withClip.save();
        $this$withClip.clipRect(left, top, right, bottom);
        try {
            block.invoke($this$withClip);
        } finally {
            j.b(1);
            $this$withClip.restoreToCount(checkpoint);
            j.a(1);
        }
    }

    public static final void withClip(@NotNull Canvas $this$withClip, @NotNull Path clipPath, @NotNull l<? super Canvas, x> block) {
        k.e($this$withClip, "<this>");
        k.e(clipPath, "clipPath");
        k.e(block, "block");
        int checkpoint = $this$withClip.save();
        $this$withClip.clipPath(clipPath);
        try {
            block.invoke($this$withClip);
        } finally {
            j.b(1);
            $this$withClip.restoreToCount(checkpoint);
            j.a(1);
        }
    }
}
