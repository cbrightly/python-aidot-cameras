package androidx.core.graphics;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Rect.kt */
public final class RectKt {
    public static final int component1(@NotNull Rect $this$component1) {
        k.e($this$component1, "<this>");
        return $this$component1.left;
    }

    public static final int component2(@NotNull Rect $this$component2) {
        k.e($this$component2, "<this>");
        return $this$component2.top;
    }

    public static final int component3(@NotNull Rect $this$component3) {
        k.e($this$component3, "<this>");
        return $this$component3.right;
    }

    public static final int component4(@NotNull Rect $this$component4) {
        k.e($this$component4, "<this>");
        return $this$component4.bottom;
    }

    public static final float component1(@NotNull RectF $this$component1) {
        k.e($this$component1, "<this>");
        return $this$component1.left;
    }

    public static final float component2(@NotNull RectF $this$component2) {
        k.e($this$component2, "<this>");
        return $this$component2.top;
    }

    public static final float component3(@NotNull RectF $this$component3) {
        k.e($this$component3, "<this>");
        return $this$component3.right;
    }

    public static final float component4(@NotNull RectF $this$component4) {
        k.e($this$component4, "<this>");
        return $this$component4.bottom;
    }

    @NotNull
    public static final Rect plus(@NotNull Rect $this$plus, @NotNull Rect r) {
        k.e($this$plus, "<this>");
        k.e(r, "r");
        Rect $this$plus_u24lambda_u2d0 = new Rect($this$plus);
        $this$plus_u24lambda_u2d0.union(r);
        return $this$plus_u24lambda_u2d0;
    }

    @NotNull
    public static final RectF plus(@NotNull RectF $this$plus, @NotNull RectF r) {
        k.e($this$plus, "<this>");
        k.e(r, "r");
        RectF $this$plus_u24lambda_u2d1 = new RectF($this$plus);
        $this$plus_u24lambda_u2d1.union(r);
        return $this$plus_u24lambda_u2d1;
    }

    @NotNull
    public static final Rect plus(@NotNull Rect $this$plus, int xy) {
        k.e($this$plus, "<this>");
        Rect $this$plus_u24lambda_u2d2 = new Rect($this$plus);
        $this$plus_u24lambda_u2d2.offset(xy, xy);
        return $this$plus_u24lambda_u2d2;
    }

    @NotNull
    public static final RectF plus(@NotNull RectF $this$plus, float xy) {
        k.e($this$plus, "<this>");
        RectF $this$plus_u24lambda_u2d3 = new RectF($this$plus);
        $this$plus_u24lambda_u2d3.offset(xy, xy);
        return $this$plus_u24lambda_u2d3;
    }

    @NotNull
    public static final Rect plus(@NotNull Rect $this$plus, @NotNull Point xy) {
        k.e($this$plus, "<this>");
        k.e(xy, "xy");
        Rect $this$plus_u24lambda_u2d4 = new Rect($this$plus);
        $this$plus_u24lambda_u2d4.offset(xy.x, xy.y);
        return $this$plus_u24lambda_u2d4;
    }

    @NotNull
    public static final RectF plus(@NotNull RectF $this$plus, @NotNull PointF xy) {
        k.e($this$plus, "<this>");
        k.e(xy, "xy");
        RectF $this$plus_u24lambda_u2d5 = new RectF($this$plus);
        $this$plus_u24lambda_u2d5.offset(xy.x, xy.y);
        return $this$plus_u24lambda_u2d5;
    }

    @NotNull
    public static final Region minus(@NotNull Rect $this$minus, @NotNull Rect r) {
        k.e($this$minus, "<this>");
        k.e(r, "r");
        Region $this$minus_u24lambda_u2d6 = new Region($this$minus);
        $this$minus_u24lambda_u2d6.op(r, Region.Op.DIFFERENCE);
        return $this$minus_u24lambda_u2d6;
    }

    @NotNull
    public static final Region minus(@NotNull RectF $this$minus, @NotNull RectF r) {
        k.e($this$minus, "<this>");
        k.e(r, "r");
        Rect r$iv = new Rect();
        $this$minus.roundOut(r$iv);
        Region $this$minus_u24lambda_u2d7 = new Region(r$iv);
        Rect r$iv2 = new Rect();
        r.roundOut(r$iv2);
        $this$minus_u24lambda_u2d7.op(r$iv2, Region.Op.DIFFERENCE);
        return $this$minus_u24lambda_u2d7;
    }

    @NotNull
    public static final Rect minus(@NotNull Rect $this$minus, int xy) {
        k.e($this$minus, "<this>");
        Rect $this$minus_u24lambda_u2d8 = new Rect($this$minus);
        $this$minus_u24lambda_u2d8.offset(-xy, -xy);
        return $this$minus_u24lambda_u2d8;
    }

    @NotNull
    public static final RectF minus(@NotNull RectF $this$minus, float xy) {
        k.e($this$minus, "<this>");
        RectF $this$minus_u24lambda_u2d9 = new RectF($this$minus);
        $this$minus_u24lambda_u2d9.offset(-xy, -xy);
        return $this$minus_u24lambda_u2d9;
    }

    @NotNull
    public static final Rect minus(@NotNull Rect $this$minus, @NotNull Point xy) {
        k.e($this$minus, "<this>");
        k.e(xy, "xy");
        Rect $this$minus_u24lambda_u2d10 = new Rect($this$minus);
        $this$minus_u24lambda_u2d10.offset(-xy.x, -xy.y);
        return $this$minus_u24lambda_u2d10;
    }

    @NotNull
    public static final RectF minus(@NotNull RectF $this$minus, @NotNull PointF xy) {
        k.e($this$minus, "<this>");
        k.e(xy, "xy");
        RectF $this$minus_u24lambda_u2d11 = new RectF($this$minus);
        $this$minus_u24lambda_u2d11.offset(-xy.x, -xy.y);
        return $this$minus_u24lambda_u2d11;
    }

    @NotNull
    public static final Rect times(@NotNull Rect $this$times, int factor) {
        k.e($this$times, "<this>");
        Rect rect = new Rect($this$times);
        Rect $this$times_u24lambda_u2d12 = rect;
        $this$times_u24lambda_u2d12.top *= factor;
        $this$times_u24lambda_u2d12.left *= factor;
        $this$times_u24lambda_u2d12.right *= factor;
        $this$times_u24lambda_u2d12.bottom *= factor;
        return rect;
    }

    @NotNull
    public static final RectF times(@NotNull RectF $this$times, int factor) {
        k.e($this$times, "<this>");
        float factor$iv = (float) factor;
        RectF rectF = new RectF($this$times);
        RectF $this$times_u24lambda_u2d13$iv = rectF;
        $this$times_u24lambda_u2d13$iv.top *= factor$iv;
        $this$times_u24lambda_u2d13$iv.left *= factor$iv;
        $this$times_u24lambda_u2d13$iv.right *= factor$iv;
        $this$times_u24lambda_u2d13$iv.bottom *= factor$iv;
        return rectF;
    }

    @NotNull
    public static final RectF times(@NotNull RectF $this$times, float factor) {
        k.e($this$times, "<this>");
        RectF rectF = new RectF($this$times);
        RectF $this$times_u24lambda_u2d13 = rectF;
        $this$times_u24lambda_u2d13.top *= factor;
        $this$times_u24lambda_u2d13.left *= factor;
        $this$times_u24lambda_u2d13.right *= factor;
        $this$times_u24lambda_u2d13.bottom *= factor;
        return rectF;
    }

    @NotNull
    public static final Rect or(@NotNull Rect $this$or, @NotNull Rect r) {
        k.e($this$or, "<this>");
        k.e(r, "r");
        Rect $this$plus_u24lambda_u2d0$iv = new Rect($this$or);
        $this$plus_u24lambda_u2d0$iv.union(r);
        return $this$plus_u24lambda_u2d0$iv;
    }

    @NotNull
    public static final RectF or(@NotNull RectF $this$or, @NotNull RectF r) {
        k.e($this$or, "<this>");
        k.e(r, "r");
        RectF $this$plus_u24lambda_u2d1$iv = new RectF($this$or);
        $this$plus_u24lambda_u2d1$iv.union(r);
        return $this$plus_u24lambda_u2d1$iv;
    }

    @NotNull
    @SuppressLint({"CheckResult"})
    public static final Rect and(@NotNull Rect $this$and, @NotNull Rect r) {
        k.e($this$and, "<this>");
        k.e(r, "r");
        Rect $this$and_u24lambda_u2d14 = new Rect($this$and);
        $this$and_u24lambda_u2d14.intersect(r);
        return $this$and_u24lambda_u2d14;
    }

    @NotNull
    @SuppressLint({"CheckResult"})
    public static final RectF and(@NotNull RectF $this$and, @NotNull RectF r) {
        k.e($this$and, "<this>");
        k.e(r, "r");
        RectF $this$and_u24lambda_u2d15 = new RectF($this$and);
        $this$and_u24lambda_u2d15.intersect(r);
        return $this$and_u24lambda_u2d15;
    }

    @NotNull
    public static final Region xor(@NotNull Rect $this$xor, @NotNull Rect r) {
        k.e($this$xor, "<this>");
        k.e(r, "r");
        Region $this$xor_u24lambda_u2d16 = new Region($this$xor);
        $this$xor_u24lambda_u2d16.op(r, Region.Op.XOR);
        return $this$xor_u24lambda_u2d16;
    }

    @NotNull
    public static final Region xor(@NotNull RectF $this$xor, @NotNull RectF r) {
        k.e($this$xor, "<this>");
        k.e(r, "r");
        Rect r$iv = new Rect();
        $this$xor.roundOut(r$iv);
        Region $this$xor_u24lambda_u2d17 = new Region(r$iv);
        Rect r$iv2 = new Rect();
        r.roundOut(r$iv2);
        $this$xor_u24lambda_u2d17.op(r$iv2, Region.Op.XOR);
        return $this$xor_u24lambda_u2d17;
    }

    public static final boolean contains(@NotNull Rect $this$contains, @NotNull Point p) {
        k.e($this$contains, "<this>");
        k.e(p, "p");
        return $this$contains.contains(p.x, p.y);
    }

    public static final boolean contains(@NotNull RectF $this$contains, @NotNull PointF p) {
        k.e($this$contains, "<this>");
        k.e(p, "p");
        return $this$contains.contains(p.x, p.y);
    }

    @NotNull
    public static final RectF toRectF(@NotNull Rect $this$toRectF) {
        k.e($this$toRectF, "<this>");
        return new RectF($this$toRectF);
    }

    @NotNull
    public static final Rect toRect(@NotNull RectF $this$toRect) {
        k.e($this$toRect, "<this>");
        Rect r = new Rect();
        $this$toRect.roundOut(r);
        return r;
    }

    @NotNull
    public static final Region toRegion(@NotNull Rect $this$toRegion) {
        k.e($this$toRegion, "<this>");
        return new Region($this$toRegion);
    }

    @NotNull
    public static final Region toRegion(@NotNull RectF $this$toRegion) {
        k.e($this$toRegion, "<this>");
        Rect r$iv = new Rect();
        $this$toRegion.roundOut(r$iv);
        return new Region(r$iv);
    }

    @NotNull
    public static final RectF transform(@NotNull RectF $this$transform, @NotNull Matrix m) {
        k.e($this$transform, "<this>");
        k.e(m, "m");
        RectF rectF = $this$transform;
        m.mapRect($this$transform);
        return $this$transform;
    }
}
