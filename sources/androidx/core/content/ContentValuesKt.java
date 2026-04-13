package androidx.core.content;

import android.content.ContentValues;
import io.netty.util.internal.StringUtil;
import kotlin.jvm.internal.k;
import kotlin.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: ContentValues.kt */
public final class ContentValuesKt {
    @NotNull
    public static final ContentValues contentValuesOf(@NotNull n<String, ? extends Object>... pairs) {
        k.e(pairs, "pairs");
        ContentValues contentValues = new ContentValues(pairs.length);
        ContentValues $this$contentValuesOf_u24lambda_u2d0 = contentValues;
        int length = pairs.length;
        int i = 0;
        while (i < length) {
            n<String, ? extends Object> nVar = pairs[i];
            i++;
            String key = nVar.component1();
            Object value = nVar.component2();
            if (value == null) {
                $this$contentValuesOf_u24lambda_u2d0.putNull(key);
            } else if (value instanceof String) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (String) value);
            } else if (value instanceof Integer) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (Integer) value);
            } else if (value instanceof Long) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (Long) value);
            } else if (value instanceof Boolean) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (Boolean) value);
            } else if (value instanceof Float) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (Float) value);
            } else if (value instanceof Double) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (Double) value);
            } else if (value instanceof byte[]) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (byte[]) value);
            } else if (value instanceof Byte) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (Byte) value);
            } else if (value instanceof Short) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (Short) value);
            } else {
                String valueType = value.getClass().getCanonicalName();
                throw new IllegalArgumentException("Illegal value type " + valueType + " for key \"" + key + StringUtil.DOUBLE_QUOTE);
            }
        }
        return contentValues;
    }
}
