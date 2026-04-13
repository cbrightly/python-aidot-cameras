package zendesk.ui.android.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;
import androidx.annotation.Dimension;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: DimensionExt.kt */
public final class e {
    @Dimension
    public static final int a(@NotNull Context $this$resolveDimensionAttr, @NotNull int[] dimensionAttr) {
        k.e($this$resolveDimensionAttr, "<this>");
        k.e(dimensionAttr, "dimensionAttr");
        TypedArray typeArray = $this$resolveDimensionAttr.obtainStyledAttributes(new TypedValue().data, dimensionAttr);
        k.d(typeArray, "obtainStyledAttributes(t…alue.data, dimensionAttr)");
        int dimensionValue = typeArray.getDimensionPixelSize(0, 0);
        typeArray.recycle();
        return dimensionValue;
    }
}
