package androidx.core.graphics;

import android.graphics.Matrix;
import android.graphics.Shader;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: Shader.kt */
public final class ShaderKt {
    public static final void transform(@NotNull Shader $this$transform, @NotNull l<? super Matrix, x> block) {
        k.e($this$transform, "<this>");
        k.e(block, "block");
        Matrix matrix = new Matrix();
        $this$transform.getLocalMatrix(matrix);
        block.invoke(matrix);
        $this$transform.setLocalMatrix(matrix);
    }
}
