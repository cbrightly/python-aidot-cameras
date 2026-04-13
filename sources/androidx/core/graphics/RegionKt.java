package androidx.core.graphics;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import java.util.Iterator;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: Region.kt */
public final class RegionKt {
    public static final boolean contains(@NotNull Region $this$contains, @NotNull Point p) {
        k.e($this$contains, "<this>");
        k.e(p, "p");
        return $this$contains.contains(p.x, p.y);
    }

    @NotNull
    public static final Region plus(@NotNull Region $this$plus, @NotNull Rect r) {
        k.e($this$plus, "<this>");
        k.e(r, "r");
        Region $this$plus_u24lambda_u2d0 = new Region($this$plus);
        $this$plus_u24lambda_u2d0.union(r);
        return $this$plus_u24lambda_u2d0;
    }

    @NotNull
    public static final Region plus(@NotNull Region $this$plus, @NotNull Region r) {
        k.e($this$plus, "<this>");
        k.e(r, "r");
        Region $this$plus_u24lambda_u2d1 = new Region($this$plus);
        $this$plus_u24lambda_u2d1.op(r, Region.Op.UNION);
        return $this$plus_u24lambda_u2d1;
    }

    @NotNull
    public static final Region minus(@NotNull Region $this$minus, @NotNull Rect r) {
        k.e($this$minus, "<this>");
        k.e(r, "r");
        Region $this$minus_u24lambda_u2d2 = new Region($this$minus);
        $this$minus_u24lambda_u2d2.op(r, Region.Op.DIFFERENCE);
        return $this$minus_u24lambda_u2d2;
    }

    @NotNull
    public static final Region minus(@NotNull Region $this$minus, @NotNull Region r) {
        k.e($this$minus, "<this>");
        k.e(r, "r");
        Region $this$minus_u24lambda_u2d3 = new Region($this$minus);
        $this$minus_u24lambda_u2d3.op(r, Region.Op.DIFFERENCE);
        return $this$minus_u24lambda_u2d3;
    }

    @NotNull
    public static final Region unaryMinus(@NotNull Region $this$unaryMinus) {
        k.e($this$unaryMinus, "<this>");
        Region $this$unaryMinus_u24lambda_u2d4 = new Region($this$unaryMinus.getBounds());
        $this$unaryMinus_u24lambda_u2d4.op($this$unaryMinus, Region.Op.DIFFERENCE);
        return $this$unaryMinus_u24lambda_u2d4;
    }

    @NotNull
    public static final Region not(@NotNull Region $this$not) {
        k.e($this$not, "<this>");
        Region $this$unaryMinus$iv = $this$not;
        Region $this$unaryMinus_u24lambda_u2d4$iv = new Region($this$unaryMinus$iv.getBounds());
        $this$unaryMinus_u24lambda_u2d4$iv.op($this$unaryMinus$iv, Region.Op.DIFFERENCE);
        return $this$unaryMinus_u24lambda_u2d4$iv;
    }

    @NotNull
    public static final Region or(@NotNull Region $this$or, @NotNull Rect r) {
        k.e($this$or, "<this>");
        k.e(r, "r");
        Region $this$plus_u24lambda_u2d0$iv = new Region($this$or);
        $this$plus_u24lambda_u2d0$iv.union(r);
        return $this$plus_u24lambda_u2d0$iv;
    }

    @NotNull
    public static final Region or(@NotNull Region $this$or, @NotNull Region r) {
        k.e($this$or, "<this>");
        k.e(r, "r");
        Region $this$plus_u24lambda_u2d1$iv = new Region($this$or);
        $this$plus_u24lambda_u2d1$iv.op(r, Region.Op.UNION);
        return $this$plus_u24lambda_u2d1$iv;
    }

    @NotNull
    public static final Region and(@NotNull Region $this$and, @NotNull Rect r) {
        k.e($this$and, "<this>");
        k.e(r, "r");
        Region $this$and_u24lambda_u2d5 = new Region($this$and);
        $this$and_u24lambda_u2d5.op(r, Region.Op.INTERSECT);
        return $this$and_u24lambda_u2d5;
    }

    @NotNull
    public static final Region and(@NotNull Region $this$and, @NotNull Region r) {
        k.e($this$and, "<this>");
        k.e(r, "r");
        Region $this$and_u24lambda_u2d6 = new Region($this$and);
        $this$and_u24lambda_u2d6.op(r, Region.Op.INTERSECT);
        return $this$and_u24lambda_u2d6;
    }

    @NotNull
    public static final Region xor(@NotNull Region $this$xor, @NotNull Rect r) {
        k.e($this$xor, "<this>");
        k.e(r, "r");
        Region $this$xor_u24lambda_u2d7 = new Region($this$xor);
        $this$xor_u24lambda_u2d7.op(r, Region.Op.XOR);
        return $this$xor_u24lambda_u2d7;
    }

    @NotNull
    public static final Region xor(@NotNull Region $this$xor, @NotNull Region r) {
        k.e($this$xor, "<this>");
        k.e(r, "r");
        Region $this$xor_u24lambda_u2d8 = new Region($this$xor);
        $this$xor_u24lambda_u2d8.op(r, Region.Op.XOR);
        return $this$xor_u24lambda_u2d8;
    }

    public static final void forEach(@NotNull Region $this$forEach, @NotNull l<? super Rect, x> action) {
        k.e($this$forEach, "<this>");
        k.e(action, "action");
        RegionIterator iterator = new RegionIterator($this$forEach);
        while (true) {
            Rect r = new Rect();
            if (iterator.next(r)) {
                action.invoke(r);
            } else {
                return;
            }
        }
    }

    @NotNull
    public static final Iterator<Rect> iterator(@NotNull Region $this$iterator) {
        k.e($this$iterator, "<this>");
        return new RegionKt$iterator$1($this$iterator);
    }
}
