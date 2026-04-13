package zendesk.ui.android.internal;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import kotlin.jvm.internal.k;
import kotlin.math.b;
import org.jetbrains.annotations.NotNull;

/* compiled from: ColorExt.kt */
public final class d {
    @ColorInt
    public static final int b(@NotNull Context $this$resolveColorAttr, @AttrRes int colorAttr) {
        k.e($this$resolveColorAttr, "<this>");
        TypedValue typedValue = new TypedValue();
        $this$resolveColorAttr.getTheme().resolveAttribute(colorAttr, typedValue, true);
        return typedValue.data;
    }

    @ColorInt
    public static final int a(@ColorInt int $this$adjustAlpha, float factor) {
        return Color.argb(b.b(((float) Color.alpha($this$adjustAlpha)) * factor), Color.red($this$adjustAlpha), Color.green($this$adjustAlpha), Color.blue($this$adjustAlpha));
    }
}
