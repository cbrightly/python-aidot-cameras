package androidx.core.graphics;

import android.graphics.Point;
import android.graphics.PointF;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Point.kt */
public final class PointKt {
    public static final int component1(@NotNull Point $this$component1) {
        k.e($this$component1, "<this>");
        return $this$component1.x;
    }

    public static final int component2(@NotNull Point $this$component2) {
        k.e($this$component2, "<this>");
        return $this$component2.y;
    }

    public static final float component1(@NotNull PointF $this$component1) {
        k.e($this$component1, "<this>");
        return $this$component1.x;
    }

    public static final float component2(@NotNull PointF $this$component2) {
        k.e($this$component2, "<this>");
        return $this$component2.y;
    }

    @NotNull
    public static final Point plus(@NotNull Point $this$plus, @NotNull Point p) {
        k.e($this$plus, "<this>");
        k.e(p, "p");
        Point $this$plus_u24lambda_u2d0 = new Point($this$plus.x, $this$plus.y);
        $this$plus_u24lambda_u2d0.offset(p.x, p.y);
        return $this$plus_u24lambda_u2d0;
    }

    @NotNull
    public static final PointF plus(@NotNull PointF $this$plus, @NotNull PointF p) {
        k.e($this$plus, "<this>");
        k.e(p, "p");
        PointF $this$plus_u24lambda_u2d1 = new PointF($this$plus.x, $this$plus.y);
        $this$plus_u24lambda_u2d1.offset(p.x, p.y);
        return $this$plus_u24lambda_u2d1;
    }

    @NotNull
    public static final Point plus(@NotNull Point $this$plus, int xy) {
        k.e($this$plus, "<this>");
        Point $this$plus_u24lambda_u2d2 = new Point($this$plus.x, $this$plus.y);
        $this$plus_u24lambda_u2d2.offset(xy, xy);
        return $this$plus_u24lambda_u2d2;
    }

    @NotNull
    public static final PointF plus(@NotNull PointF $this$plus, float xy) {
        k.e($this$plus, "<this>");
        PointF $this$plus_u24lambda_u2d3 = new PointF($this$plus.x, $this$plus.y);
        $this$plus_u24lambda_u2d3.offset(xy, xy);
        return $this$plus_u24lambda_u2d3;
    }

    @NotNull
    public static final Point minus(@NotNull Point $this$minus, @NotNull Point p) {
        k.e($this$minus, "<this>");
        k.e(p, "p");
        Point $this$minus_u24lambda_u2d4 = new Point($this$minus.x, $this$minus.y);
        $this$minus_u24lambda_u2d4.offset(-p.x, -p.y);
        return $this$minus_u24lambda_u2d4;
    }

    @NotNull
    public static final PointF minus(@NotNull PointF $this$minus, @NotNull PointF p) {
        k.e($this$minus, "<this>");
        k.e(p, "p");
        PointF $this$minus_u24lambda_u2d5 = new PointF($this$minus.x, $this$minus.y);
        $this$minus_u24lambda_u2d5.offset(-p.x, -p.y);
        return $this$minus_u24lambda_u2d5;
    }

    @NotNull
    public static final Point minus(@NotNull Point $this$minus, int xy) {
        k.e($this$minus, "<this>");
        Point $this$minus_u24lambda_u2d6 = new Point($this$minus.x, $this$minus.y);
        $this$minus_u24lambda_u2d6.offset(-xy, -xy);
        return $this$minus_u24lambda_u2d6;
    }

    @NotNull
    public static final PointF minus(@NotNull PointF $this$minus, float xy) {
        k.e($this$minus, "<this>");
        PointF $this$minus_u24lambda_u2d7 = new PointF($this$minus.x, $this$minus.y);
        $this$minus_u24lambda_u2d7.offset(-xy, -xy);
        return $this$minus_u24lambda_u2d7;
    }

    @NotNull
    public static final Point unaryMinus(@NotNull Point $this$unaryMinus) {
        k.e($this$unaryMinus, "<this>");
        return new Point(-$this$unaryMinus.x, -$this$unaryMinus.y);
    }

    @NotNull
    public static final PointF unaryMinus(@NotNull PointF $this$unaryMinus) {
        k.e($this$unaryMinus, "<this>");
        return new PointF(-$this$unaryMinus.x, -$this$unaryMinus.y);
    }

    @NotNull
    public static final PointF toPointF(@NotNull Point $this$toPointF) {
        k.e($this$toPointF, "<this>");
        return new PointF($this$toPointF);
    }

    @NotNull
    public static final Point toPoint(@NotNull PointF $this$toPoint) {
        k.e($this$toPoint, "<this>");
        return new Point((int) $this$toPoint.x, (int) $this$toPoint.y);
    }
}
