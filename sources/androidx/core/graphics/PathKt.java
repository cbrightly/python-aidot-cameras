package androidx.core.graphics;

import android.graphics.Path;
import androidx.annotation.RequiresApi;
import java.util.Collection;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Path.kt */
public final class PathKt {
    public static /* synthetic */ Iterable flatten$default(Path path, float f, int i, Object obj) {
        if ((i & 1) != 0) {
            f = 0.5f;
        }
        return flatten(path, f);
    }

    @RequiresApi(26)
    @NotNull
    public static final Iterable<PathSegment> flatten(@NotNull Path $this$flatten, float error) {
        k.e($this$flatten, "<this>");
        Collection<PathSegment> flatten = PathUtils.flatten($this$flatten, error);
        k.d(flatten, "flatten(this, error)");
        return flatten;
    }

    @RequiresApi(19)
    @NotNull
    public static final Path plus(@NotNull Path $this$plus, @NotNull Path p) {
        k.e($this$plus, "<this>");
        k.e(p, "p");
        Path $this$plus_u24lambda_u2d0 = new Path($this$plus);
        $this$plus_u24lambda_u2d0.op(p, Path.Op.UNION);
        return $this$plus_u24lambda_u2d0;
    }

    @RequiresApi(19)
    @NotNull
    public static final Path minus(@NotNull Path $this$minus, @NotNull Path p) {
        k.e($this$minus, "<this>");
        k.e(p, "p");
        Path $this$minus_u24lambda_u2d1 = new Path($this$minus);
        $this$minus_u24lambda_u2d1.op(p, Path.Op.DIFFERENCE);
        return $this$minus_u24lambda_u2d1;
    }

    @RequiresApi(19)
    @NotNull
    public static final Path or(@NotNull Path $this$or, @NotNull Path p) {
        k.e($this$or, "<this>");
        k.e(p, "p");
        Path $this$plus_u24lambda_u2d0$iv = new Path($this$or);
        $this$plus_u24lambda_u2d0$iv.op(p, Path.Op.UNION);
        return $this$plus_u24lambda_u2d0$iv;
    }

    @RequiresApi(19)
    @NotNull
    public static final Path and(@NotNull Path $this$and, @NotNull Path p) {
        k.e($this$and, "<this>");
        k.e(p, "p");
        Path $this$and_u24lambda_u2d2 = new Path();
        $this$and_u24lambda_u2d2.op($this$and, p, Path.Op.INTERSECT);
        return $this$and_u24lambda_u2d2;
    }

    @RequiresApi(19)
    @NotNull
    public static final Path xor(@NotNull Path $this$xor, @NotNull Path p) {
        k.e($this$xor, "<this>");
        k.e(p, "p");
        Path $this$xor_u24lambda_u2d3 = new Path($this$xor);
        $this$xor_u24lambda_u2d3.op(p, Path.Op.XOR);
        return $this$xor_u24lambda_u2d3;
    }
}
