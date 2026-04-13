package androidx.core.util;

import android.util.SparseArray;
import java.util.Iterator;
import kotlin.collections.g0;
import kotlin.jvm.functions.a;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: SparseArray.kt */
public final class SparseArrayKt {
    public static final <T> int getSize(@NotNull SparseArray<T> $this$size) {
        k.e($this$size, "<this>");
        return $this$size.size();
    }

    public static final <T> boolean contains(@NotNull SparseArray<T> $this$contains, int key) {
        k.e($this$contains, "<this>");
        return $this$contains.indexOfKey(key) >= 0;
    }

    public static final <T> void set(@NotNull SparseArray<T> $this$set, int key, T value) {
        k.e($this$set, "<this>");
        $this$set.put(key, value);
    }

    @NotNull
    public static final <T> SparseArray<T> plus(@NotNull SparseArray<T> $this$plus, @NotNull SparseArray<T> other) {
        k.e($this$plus, "<this>");
        k.e(other, "other");
        SparseArray sparseArray = new SparseArray($this$plus.size() + other.size());
        putAll(sparseArray, $this$plus);
        putAll(sparseArray, other);
        return sparseArray;
    }

    public static final <T> boolean containsKey(@NotNull SparseArray<T> $this$containsKey, int key) {
        k.e($this$containsKey, "<this>");
        return $this$containsKey.indexOfKey(key) >= 0;
    }

    public static final <T> boolean containsValue(@NotNull SparseArray<T> $this$containsValue, T value) {
        k.e($this$containsValue, "<this>");
        return $this$containsValue.indexOfValue(value) >= 0;
    }

    public static final <T> T getOrDefault(@NotNull SparseArray<T> $this$getOrDefault, int key, T defaultValue) {
        k.e($this$getOrDefault, "<this>");
        T t = $this$getOrDefault.get(key);
        return t == null ? defaultValue : t;
    }

    public static final <T> T getOrElse(@NotNull SparseArray<T> $this$getOrElse, int key, @NotNull a<? extends T> defaultValue) {
        k.e($this$getOrElse, "<this>");
        k.e(defaultValue, "defaultValue");
        T t = $this$getOrElse.get(key);
        return t == null ? defaultValue.invoke() : t;
    }

    public static final <T> boolean isEmpty(@NotNull SparseArray<T> $this$isEmpty) {
        k.e($this$isEmpty, "<this>");
        return $this$isEmpty.size() == 0;
    }

    public static final <T> boolean isNotEmpty(@NotNull SparseArray<T> $this$isNotEmpty) {
        k.e($this$isNotEmpty, "<this>");
        return $this$isNotEmpty.size() != 0;
    }

    public static final <T> boolean remove(@NotNull SparseArray<T> $this$remove, int key, T value) {
        k.e($this$remove, "<this>");
        int index = $this$remove.indexOfKey(key);
        if (index < 0 || !k.a(value, $this$remove.valueAt(index))) {
            return false;
        }
        $this$remove.removeAt(index);
        return true;
    }

    public static final <T> void putAll(@NotNull SparseArray<T> $this$putAll, @NotNull SparseArray<T> other) {
        k.e($this$putAll, "<this>");
        k.e(other, "other");
        SparseArray $this$forEach$iv = other;
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

    public static final <T> void forEach(@NotNull SparseArray<T> $this$forEach, @NotNull p<? super Integer, ? super T, x> action) {
        k.e($this$forEach, "<this>");
        k.e(action, "action");
        int size = $this$forEach.size();
        if (size > 0) {
            int i = 0;
            do {
                int index = i;
                i++;
                action.invoke(Integer.valueOf($this$forEach.keyAt(index)), $this$forEach.valueAt(index));
            } while (i < size);
        }
    }

    @NotNull
    public static final <T> g0 keyIterator(@NotNull SparseArray<T> $this$keyIterator) {
        k.e($this$keyIterator, "<this>");
        return new SparseArrayKt$keyIterator$1($this$keyIterator);
    }

    @NotNull
    public static final <T> Iterator<T> valueIterator(@NotNull SparseArray<T> $this$valueIterator) {
        k.e($this$valueIterator, "<this>");
        return new SparseArrayKt$valueIterator$1($this$valueIterator);
    }
}
