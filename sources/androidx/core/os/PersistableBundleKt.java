package androidx.core.os;

import android.os.Build;
import android.os.PersistableBundle;
import androidx.annotation.RequiresApi;
import io.netty.util.internal.StringUtil;
import kotlin.jvm.internal.k;
import kotlin.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: PersistableBundle.kt */
public final class PersistableBundleKt {
    @RequiresApi(21)
    @NotNull
    public static final PersistableBundle persistableBundleOf(@NotNull n<String, ? extends Object>... pairs) {
        k.e(pairs, "pairs");
        PersistableBundle persistableBundle = new PersistableBundle(pairs.length);
        PersistableBundle $this$persistableBundleOf_u24lambda_u2d0 = persistableBundle;
        int length = pairs.length;
        int i = 0;
        while (i < length) {
            n<String, ? extends Object> nVar = pairs[i];
            i++;
            String key = nVar.component1();
            Object value = nVar.component2();
            if (value == null) {
                $this$persistableBundleOf_u24lambda_u2d0.putString(key, (String) null);
            } else if (value instanceof Boolean) {
                if (Build.VERSION.SDK_INT >= 22) {
                    $this$persistableBundleOf_u24lambda_u2d0.putBoolean(key, ((Boolean) value).booleanValue());
                } else {
                    throw new IllegalArgumentException("Illegal value type boolean for key \"" + key + StringUtil.DOUBLE_QUOTE);
                }
            } else if (value instanceof Double) {
                $this$persistableBundleOf_u24lambda_u2d0.putDouble(key, ((Number) value).doubleValue());
            } else if (value instanceof Integer) {
                $this$persistableBundleOf_u24lambda_u2d0.putInt(key, ((Number) value).intValue());
            } else if (value instanceof Long) {
                $this$persistableBundleOf_u24lambda_u2d0.putLong(key, ((Number) value).longValue());
            } else if (value instanceof String) {
                $this$persistableBundleOf_u24lambda_u2d0.putString(key, (String) value);
            } else if (value instanceof boolean[]) {
                if (Build.VERSION.SDK_INT >= 22) {
                    $this$persistableBundleOf_u24lambda_u2d0.putBooleanArray(key, (boolean[]) value);
                } else {
                    throw new IllegalArgumentException("Illegal value type boolean[] for key \"" + key + StringUtil.DOUBLE_QUOTE);
                }
            } else if (value instanceof double[]) {
                $this$persistableBundleOf_u24lambda_u2d0.putDoubleArray(key, (double[]) value);
            } else if (value instanceof int[]) {
                $this$persistableBundleOf_u24lambda_u2d0.putIntArray(key, (int[]) value);
            } else if (value instanceof long[]) {
                $this$persistableBundleOf_u24lambda_u2d0.putLongArray(key, (long[]) value);
            } else if (value instanceof Object[]) {
                Class componentType = value.getClass().getComponentType();
                k.c(componentType);
                if (String.class.isAssignableFrom(componentType)) {
                    $this$persistableBundleOf_u24lambda_u2d0.putStringArray(key, (String[]) value);
                } else {
                    String valueType = componentType.getCanonicalName();
                    throw new IllegalArgumentException("Illegal value array type " + valueType + " for key \"" + key + StringUtil.DOUBLE_QUOTE);
                }
            } else {
                String valueType2 = value.getClass().getCanonicalName();
                throw new IllegalArgumentException("Illegal value type " + valueType2 + " for key \"" + key + StringUtil.DOUBLE_QUOTE);
            }
        }
        return persistableBundle;
    }
}
