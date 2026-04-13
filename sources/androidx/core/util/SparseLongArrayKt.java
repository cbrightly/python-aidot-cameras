package androidx.core.util;

import android.util.SparseLongArray;
import androidx.annotation.RequiresApi;
import kotlin.collections.g0;
import kotlin.collections.h0;
import kotlin.jvm.functions.a;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: SparseLongArray.kt */
public final class SparseLongArrayKt {
    @RequiresApi(18)
    public static final int getSize(@NotNull SparseLongArray $this$size) {
        k.e($this$size, "<this>");
        return $this$size.size();
    }

    @RequiresApi(18)
    public static final boolean contains(@NotNull SparseLongArray $this$contains, int key) {
        k.e($this$contains, "<this>");
        return $this$contains.indexOfKey(key) >= 0;
    }

    @RequiresApi(18)
    public static final void set(@NotNull SparseLongArray $this$set, int key, long value) {
        k.e($this$set, "<this>");
        $this$set.put(key, value);
    }

    @RequiresApi(18)
    @NotNull
    public static final SparseLongArray plus(@NotNull SparseLongArray $this$plus, @NotNull SparseLongArray other) {
        k.e($this$plus, "<this>");
        k.e(other, "other");
        SparseLongArray sparseLongArray = new SparseLongArray($this$plus.size() + other.size());
        putAll(sparseLongArray, $this$plus);
        putAll(sparseLongArray, other);
        return sparseLongArray;
    }

    @RequiresApi(18)
    public static final boolean containsKey(@NotNull SparseLongArray $this$containsKey, int key) {
        k.e($this$containsKey, "<this>");
        return $this$containsKey.indexOfKey(key) >= 0;
    }

    @RequiresApi(18)
    public static final boolean containsValue(@NotNull SparseLongArray $this$containsValue, long value) {
        k.e($this$containsValue, "<this>");
        return $this$containsValue.indexOfValue(value) >= 0;
    }

    @RequiresApi(18)
    public static final long getOrDefault(@NotNull SparseLongArray $this$getOrDefault, int key, long defaultValue) {
        k.e($this$getOrDefault, "<this>");
        return $this$getOrDefault.get(key, defaultValue);
    }

    @RequiresApi(18)
    public static final long getOrElse(@NotNull SparseLongArray $this$getOrElse, int key, @NotNull a<Long> defaultValue) {
        k.e($this$getOrElse, "<this>");
        k.e(defaultValue, "defaultValue");
        int it = $this$getOrElse.indexOfKey(key);
        return it >= 0 ? $this$getOrElse.valueAt(it) : defaultValue.invoke().longValue();
    }

    @RequiresApi(18)
    public static final boolean isEmpty(@NotNull SparseLongArray $this$isEmpty) {
        k.e($this$isEmpty, "<this>");
        return $this$isEmpty.size() == 0;
    }

    @RequiresApi(18)
    public static final boolean isNotEmpty(@NotNull SparseLongArray $this$isNotEmpty) {
        k.e($this$isNotEmpty, "<this>");
        return $this$isNotEmpty.size() != 0;
    }

    @RequiresApi(18)
    public static final boolean remove(@NotNull SparseLongArray $this$remove, int key, long value) {
        k.e($this$remove, "<this>");
        int index = $this$remove.indexOfKey(key);
        if (index < 0 || value != $this$remove.valueAt(index)) {
            return false;
        }
        $this$remove.removeAt(index);
        return true;
    }

    @RequiresApi(18)
    public static final void putAll(@NotNull SparseLongArray $this$putAll, @NotNull SparseLongArray other) {
        k.e($this$putAll, "<this>");
        k.e(other, "other");
        SparseLongArray $this$forEach$iv = other;
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

    @RequiresApi(18)
    public static final void forEach(@NotNull SparseLongArray $this$forEach, @NotNull p<? super Integer, ? super Long, x> action) {
        k.e($this$forEach, "<this>");
        k.e(action, "action");
        int size = $this$forEach.size();
        if (size > 0) {
            int i = 0;
            do {
                int index = i;
                i++;
                action.invoke(Integer.valueOf($this$forEach.keyAt(index)), Long.valueOf($this$forEach.valueAt(index)));
            } while (i < size);
        }
    }

    @RequiresApi(18)
    @NotNull
    public static final g0 keyIterator(@NotNull SparseLongArray $this$keyIterator) {
        k.e($this$keyIterator, "<this>");
        return new SparseLongArrayKt$keyIterator$1($this$keyIterator);
    }

    @RequiresApi(18)
    @NotNull
    public static final h0 valueIterator(@NotNull SparseLongArray $this$valueIterator) {
        k.e($this$valueIterator, "<this>");
        return new SparseLongArrayKt$valueIterator$1($this$valueIterator);
    }
}
