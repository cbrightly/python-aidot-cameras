package androidx.core.util;

import android.util.SparseBooleanArray;
import kotlin.collections.g0;
import kotlin.collections.n;
import kotlin.jvm.functions.a;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: SparseBooleanArray.kt */
public final class SparseBooleanArrayKt {
    public static final int getSize(@NotNull SparseBooleanArray $this$size) {
        k.e($this$size, "<this>");
        return $this$size.size();
    }

    public static final boolean contains(@NotNull SparseBooleanArray $this$contains, int key) {
        k.e($this$contains, "<this>");
        return $this$contains.indexOfKey(key) >= 0;
    }

    public static final void set(@NotNull SparseBooleanArray $this$set, int key, boolean value) {
        k.e($this$set, "<this>");
        $this$set.put(key, value);
    }

    @NotNull
    public static final SparseBooleanArray plus(@NotNull SparseBooleanArray $this$plus, @NotNull SparseBooleanArray other) {
        k.e($this$plus, "<this>");
        k.e(other, "other");
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray($this$plus.size() + other.size());
        putAll(sparseBooleanArray, $this$plus);
        putAll(sparseBooleanArray, other);
        return sparseBooleanArray;
    }

    public static final boolean containsKey(@NotNull SparseBooleanArray $this$containsKey, int key) {
        k.e($this$containsKey, "<this>");
        return $this$containsKey.indexOfKey(key) >= 0;
    }

    public static final boolean containsValue(@NotNull SparseBooleanArray $this$containsValue, boolean value) {
        k.e($this$containsValue, "<this>");
        return $this$containsValue.indexOfValue(value) >= 0;
    }

    public static final boolean getOrDefault(@NotNull SparseBooleanArray $this$getOrDefault, int key, boolean defaultValue) {
        k.e($this$getOrDefault, "<this>");
        return $this$getOrDefault.get(key, defaultValue);
    }

    public static final boolean getOrElse(@NotNull SparseBooleanArray $this$getOrElse, int key, @NotNull a<Boolean> defaultValue) {
        k.e($this$getOrElse, "<this>");
        k.e(defaultValue, "defaultValue");
        int it = $this$getOrElse.indexOfKey(key);
        return it >= 0 ? $this$getOrElse.valueAt(it) : defaultValue.invoke().booleanValue();
    }

    public static final boolean isEmpty(@NotNull SparseBooleanArray $this$isEmpty) {
        k.e($this$isEmpty, "<this>");
        return $this$isEmpty.size() == 0;
    }

    public static final boolean isNotEmpty(@NotNull SparseBooleanArray $this$isNotEmpty) {
        k.e($this$isNotEmpty, "<this>");
        return $this$isNotEmpty.size() != 0;
    }

    public static final boolean remove(@NotNull SparseBooleanArray $this$remove, int key, boolean value) {
        k.e($this$remove, "<this>");
        int index = $this$remove.indexOfKey(key);
        if (index < 0 || value != $this$remove.valueAt(index)) {
            return false;
        }
        $this$remove.delete(key);
        return true;
    }

    public static final void putAll(@NotNull SparseBooleanArray $this$putAll, @NotNull SparseBooleanArray other) {
        k.e($this$putAll, "<this>");
        k.e(other, "other");
        SparseBooleanArray $this$forEach$iv = other;
        int size = $this$forEach$iv.size();
        if (size > 0) {
            int i = 0;
            do {
                int index$iv = i;
                i++;
                $this$putAll.put($this$forEach$iv.keyAt(index$iv), $this$forEach$iv.valueAt(index$iv));
            } while (i < size);
        }
    }

    public static final void forEach(@NotNull SparseBooleanArray $this$forEach, @NotNull p<? super Integer, ? super Boolean, x> action) {
        k.e($this$forEach, "<this>");
        k.e(action, "action");
        int size = $this$forEach.size();
        if (size > 0) {
            int i = 0;
            do {
                int index = i;
                i++;
                action.invoke(Integer.valueOf($this$forEach.keyAt(index)), Boolean.valueOf($this$forEach.valueAt(index)));
            } while (i < size);
        }
    }

    @NotNull
    public static final g0 keyIterator(@NotNull SparseBooleanArray $this$keyIterator) {
        k.e($this$keyIterator, "<this>");
        return new SparseBooleanArrayKt$keyIterator$1($this$keyIterator);
    }

    @NotNull
    public static final n valueIterator(@NotNull SparseBooleanArray $this$valueIterator) {
        k.e($this$valueIterator, "<this>");
        return new SparseBooleanArrayKt$valueIterator$1($this$valueIterator);
    }
}
