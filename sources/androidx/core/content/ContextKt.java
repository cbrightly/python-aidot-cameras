package androidx.core.content;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.annotation.AttrRes;
import androidx.annotation.StyleRes;
import androidx.exifinterface.media.ExifInterface;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Context.kt */
public final class ContextKt {
    public static final /* synthetic */ <T> T getSystemService(Context $this$getSystemService) {
        k.e($this$getSystemService, "<this>");
        k.i(4, ExifInterface.GPS_DIRECTION_TRUE);
        return ContextCompat.getSystemService($this$getSystemService, Object.class);
    }

    public static /* synthetic */ void withStyledAttributes$default(Context $this$withStyledAttributes_u24default, AttributeSet set, int[] attrs, int defStyleAttr, int defStyleRes, l block, int i, Object obj) {
        if ((i & 1) != 0) {
            set = null;
        }
        if ((i & 4) != 0) {
            defStyleAttr = 0;
        }
        if ((i & 8) != 0) {
            defStyleRes = 0;
        }
        k.e($this$withStyledAttributes_u24default, "<this>");
        k.e(attrs, "attrs");
        k.e(block, "block");
        TypedArray obtainStyledAttributes = $this$withStyledAttributes_u24default.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes);
        k.d(obtainStyledAttributes, "obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes)");
        block.invoke(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
    }

    public static final void withStyledAttributes(@NotNull Context $this$withStyledAttributes, @Nullable AttributeSet set, @NotNull int[] attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes, @NotNull l<? super TypedArray, x> block) {
        k.e($this$withStyledAttributes, "<this>");
        k.e(attrs, "attrs");
        k.e(block, "block");
        TypedArray obtainStyledAttributes = $this$withStyledAttributes.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes);
        k.d(obtainStyledAttributes, "obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes)");
        block.invoke(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
    }

    public static final void withStyledAttributes(@NotNull Context $this$withStyledAttributes, @StyleRes int resourceId, @NotNull int[] attrs, @NotNull l<? super TypedArray, x> block) {
        k.e($this$withStyledAttributes, "<this>");
        k.e(attrs, "attrs");
        k.e(block, "block");
        TypedArray obtainStyledAttributes = $this$withStyledAttributes.obtainStyledAttributes(resourceId, attrs);
        k.d(obtainStyledAttributes, "obtainStyledAttributes(resourceId, attrs)");
        block.invoke(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
    }
}
