package androidx.core.os;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import io.netty.util.internal.StringUtil;
import java.io.Serializable;
import kotlin.jvm.internal.k;
import kotlin.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: Bundle.kt */
public final class BundleKt {
    @NotNull
    public static final Bundle bundleOf(@NotNull n<String, ? extends Object>... pairs) {
        k.e(pairs, "pairs");
        Bundle bundle = new Bundle(pairs.length);
        Bundle $this$bundleOf_u24lambda_u2d0 = bundle;
        int length = pairs.length;
        int i = 0;
        while (i < length) {
            n<String, ? extends Object> nVar = pairs[i];
            i++;
            String key = nVar.component1();
            Object value = nVar.component2();
            if (value == null) {
                $this$bundleOf_u24lambda_u2d0.putString(key, (String) null);
            } else if (value instanceof Boolean) {
                $this$bundleOf_u24lambda_u2d0.putBoolean(key, ((Boolean) value).booleanValue());
            } else if (value instanceof Byte) {
                $this$bundleOf_u24lambda_u2d0.putByte(key, ((Number) value).byteValue());
            } else if (value instanceof Character) {
                $this$bundleOf_u24lambda_u2d0.putChar(key, ((Character) value).charValue());
            } else if (value instanceof Double) {
                $this$bundleOf_u24lambda_u2d0.putDouble(key, ((Number) value).doubleValue());
            } else if (value instanceof Float) {
                $this$bundleOf_u24lambda_u2d0.putFloat(key, ((Number) value).floatValue());
            } else if (value instanceof Integer) {
                $this$bundleOf_u24lambda_u2d0.putInt(key, ((Number) value).intValue());
            } else if (value instanceof Long) {
                $this$bundleOf_u24lambda_u2d0.putLong(key, ((Number) value).longValue());
            } else if (value instanceof Short) {
                $this$bundleOf_u24lambda_u2d0.putShort(key, ((Number) value).shortValue());
            } else if (value instanceof Bundle) {
                $this$bundleOf_u24lambda_u2d0.putBundle(key, (Bundle) value);
            } else if (value instanceof CharSequence) {
                $this$bundleOf_u24lambda_u2d0.putCharSequence(key, (CharSequence) value);
            } else if (value instanceof Parcelable) {
                $this$bundleOf_u24lambda_u2d0.putParcelable(key, (Parcelable) value);
            } else if (value instanceof boolean[]) {
                $this$bundleOf_u24lambda_u2d0.putBooleanArray(key, (boolean[]) value);
            } else if (value instanceof byte[]) {
                $this$bundleOf_u24lambda_u2d0.putByteArray(key, (byte[]) value);
            } else if (value instanceof char[]) {
                $this$bundleOf_u24lambda_u2d0.putCharArray(key, (char[]) value);
            } else if (value instanceof double[]) {
                $this$bundleOf_u24lambda_u2d0.putDoubleArray(key, (double[]) value);
            } else if (value instanceof float[]) {
                $this$bundleOf_u24lambda_u2d0.putFloatArray(key, (float[]) value);
            } else if (value instanceof int[]) {
                $this$bundleOf_u24lambda_u2d0.putIntArray(key, (int[]) value);
            } else if (value instanceof long[]) {
                $this$bundleOf_u24lambda_u2d0.putLongArray(key, (long[]) value);
            } else if (value instanceof short[]) {
                $this$bundleOf_u24lambda_u2d0.putShortArray(key, (short[]) value);
            } else if (value instanceof Object[]) {
                Class componentType = value.getClass().getComponentType();
                k.c(componentType);
                if (Parcelable.class.isAssignableFrom(componentType)) {
                    $this$bundleOf_u24lambda_u2d0.putParcelableArray(key, (Parcelable[]) value);
                } else if (String.class.isAssignableFrom(componentType)) {
                    $this$bundleOf_u24lambda_u2d0.putStringArray(key, (String[]) value);
                } else if (CharSequence.class.isAssignableFrom(componentType)) {
                    $this$bundleOf_u24lambda_u2d0.putCharSequenceArray(key, (CharSequence[]) value);
                } else if (Serializable.class.isAssignableFrom(componentType)) {
                    $this$bundleOf_u24lambda_u2d0.putSerializable(key, (Serializable) value);
                } else {
                    String valueType = componentType.getCanonicalName();
                    throw new IllegalArgumentException("Illegal value array type " + valueType + " for key \"" + key + StringUtil.DOUBLE_QUOTE);
                }
            } else if (value instanceof Serializable) {
                $this$bundleOf_u24lambda_u2d0.putSerializable(key, (Serializable) value);
            } else {
                int i2 = Build.VERSION.SDK_INT;
                if (i2 >= 18 && (value instanceof IBinder)) {
                    $this$bundleOf_u24lambda_u2d0.putBinder(key, (IBinder) value);
                } else if (i2 >= 21 && (value instanceof Size)) {
                    $this$bundleOf_u24lambda_u2d0.putSize(key, (Size) value);
                } else if (i2 < 21 || !(value instanceof SizeF)) {
                    String valueType2 = value.getClass().getCanonicalName();
                    throw new IllegalArgumentException("Illegal value type " + valueType2 + " for key \"" + key + StringUtil.DOUBLE_QUOTE);
                } else {
                    $this$bundleOf_u24lambda_u2d0.putSizeF(key, (SizeF) value);
                }
            }
        }
        return bundle;
    }
}
