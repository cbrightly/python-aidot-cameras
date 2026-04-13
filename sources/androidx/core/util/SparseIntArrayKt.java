package androidx.core.util;

import android.util.SparseIntArray;
import kotlin.collections.g0;
import kotlin.jvm.functions.a;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: SparseIntArray.kt */
public final class SparseIntArrayKt {
    public static final int getSize(@NotNull SparseIntArray $this$size) {
        k.e($this$size, "<this>");
        return $this$size.size();
    }

    public static final boolean contains(@NotNull SparseIntArray $this$contains, int key) {
        k.e($this$contains, "<this>");
        return $this$contains.indexOfKey(key) >= 0;
    }

    public static final void set(@NotNull SparseIntArray $this$set, int key, int value) {
        k.e($this$set, "<this>");
        $this$set.put(key, value);
    }

    @NotNull
    public static final SparseIntArray plus(@NotNull SparseIntArray $this$plus, @NotNull SparseIntArray other) {
        k.e($this$plus, "<this>");
        k.e(other, "other");
        SparseIntArray sparseIntArray = new SparseIntArray($this$plus.size() + other.size());
        putAll(sparseIntArray, $this$plus);
        putAll(sparseIntArray, other);
        return sparseIntArray;
    }

    public static final boolean containsKey(@NotNull SparseIntArray $this$containsKey, int key) {
        k.e($this$containsKey, "<this>");
        return $this$containsKey.indexOfKey(key) >= 0;
    }

    public static final boolean containsValue(@NotNull SparseIntArray $this$containsValue, int value) {
        k.e($this$containsValue, "<this>");
        return $this$containsValue.indexOfValue(value) >= 0;
    }

    public static final int getOrDefault(@NotNull SparseIntArray $this$getOrDefault, int key, int defaultValue) {
        k.e($this$getOrDefault, "<this>");
        return $this$getOrDefault.get(key, defaultValue);
    }

    public static final int getOrElse(@NotNull SparseIntArray $this$getOrElse, int key, @NotNull a<Integer> defaultValue) {
        k.e($this$getOrElse, "<this>");
        k.e(defaultValue, "defaultValue");
        int it = $this$getOrElse.indexOfKey(key);
        return it >= 0 ? $this$getOrElse.valueAt(it) : defaultValue.invoke().intValue();
    }

    public static final boolean isEmpty(@NotNull SparseIntArray $this$isEmpty) {
        k.e($this$isEmpty, "<this>");
        return $this$isEmpty.size() == 0;
    }

    public static final boolean isNotEmpty(@NotNull SparseIntArray $this$isNotEmpty) {
        k.e($this$isNotEmpty, "<this>");
        return $this$isNotEmpty.size() != 0;
    }

    public static final boolean remove(@NotNull SparseIntArray $this$remove, int key, int value) {
        k.e($this$remove, "<this>");
        int index = $this$remove.indexOfKey(key);
        if (index < 0 || value != $this$remove.valueAt(index)) {
            return false;
        }
        $this$remove.removeAt(index);
        return true;
    }

    public static final void putAll(@NotNull SparseIntArray $this$putAll, @NotNull SparseIntArray other) {
        k.e($this$putAll, "<this>");
        k.e(other, "other");
        SparseIntArray $this$forEach$iv = other;
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

    public static final void forEach(@NotNull SparseIntArray $this$forEach, @NotNull p<? super Integer, ? super Integer, x> action) {
        k.e($this$forEach, "<this>");
        k.e(action, "action");
        int size = $this$forEach.size();
        if (size > 0) {
            int i = 0;
            do {
                int index = i;
                i++;
                action.invoke(Integer.valueOf($this$forEach.keyAt(index)), Integer.valueOf($this$forEach.valueAt(index)));
            } while (i < size);
        }
    }

    @NotNull
    public static final g0 keyIterator(@NotNull SparseIntArray $this$keyIterator) {
        k.e($this$keyIterator, "<this>");
        return new SparseIntArrayKt$keyIterator$1($this$keyIterator);
    }

    @NotNull
    public static final g0 valueIterator(@NotNull SparseIntArray $this$valueIterator) {
        k.e($this$valueIterator, "<this>");
        return new SparseIntArrayKt$valueIterator$1($this$valueIterator);
    }
}
