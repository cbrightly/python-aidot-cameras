package androidx.core.util;

import android.util.LongSparseArray;
import androidx.annotation.RequiresApi;
import java.util.Iterator;
import kotlin.collections.h0;
import kotlin.jvm.functions.a;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: LongSparseArray.kt */
public final class LongSparseArrayKt {
    @RequiresApi(16)
    public static final <T> int getSize(@NotNull LongSparseArray<T> $this$size) {
        k.e($this$size, "<this>");
        return $this$size.size();
    }

    @RequiresApi(16)
    public static final <T> boolean contains(@NotNull LongSparseArray<T> $this$contains, long key) {
        k.e($this$contains, "<this>");
        return $this$contains.indexOfKey(key) >= 0;
    }

    @RequiresApi(16)
    public static final <T> void set(@NotNull LongSparseArray<T> $this$set, long key, T value) {
        k.e($this$set, "<this>");
        $this$set.put(key, value);
    }

    @RequiresApi(16)
    @NotNull
    public static final <T> LongSparseArray<T> plus(@NotNull LongSparseArray<T> $this$plus, @NotNull LongSparseArray<T> other) {
        k.e($this$plus, "<this>");
        k.e(other, "other");
        LongSparseArray longSparseArray = new LongSparseArray($this$plus.size() + other.size());
        putAll(longSparseArray, $this$plus);
        putAll(longSparseArray, other);
        return longSparseArray;
    }

    @RequiresApi(16)
    public static final <T> boolean containsKey(@NotNull LongSparseArray<T> $this$containsKey, long key) {
        k.e($this$containsKey, "<this>");
        return $this$containsKey.indexOfKey(key) >= 0;
    }

    @RequiresApi(16)
    public static final <T> boolean containsValue(@NotNull LongSparseArray<T> $this$containsValue, T value) {
        k.e($this$containsValue, "<this>");
        return $this$containsValue.indexOfValue(value) >= 0;
    }

    @RequiresApi(16)
    public static final <T> T getOrDefault(@NotNull LongSparseArray<T> $this$getOrDefault, long key, T defaultValue) {
        k.e($this$getOrDefault, "<this>");
        T t = $this$getOrDefault.get(key);
        return t == null ? defaultValue : t;
    }

    @RequiresApi(16)
    public static final <T> T getOrElse(@NotNull LongSparseArray<T> $this$getOrElse, long key, @NotNull a<? extends T> defaultValue) {
        k.e($this$getOrElse, "<this>");
        k.e(defaultValue, "defaultValue");
        T t = $this$getOrElse.get(key);
        return t == null ? defaultValue.invoke() : t;
    }

    @RequiresApi(16)
    public static final <T> boolean isEmpty(@NotNull LongSparseArray<T> $this$isEmpty) {
        k.e($this$isEmpty, "<this>");
        return $this$isEmpty.size() == 0;
    }

    @RequiresApi(16)
    public static final <T> boolean isNotEmpty(@NotNull LongSparseArray<T> $this$isNotEmpty) {
        k.e($this$isNotEmpty, "<this>");
        return $this$isNotEmpty.size() != 0;
    }

    @RequiresApi(16)
    public static final <T> boolean remove(@NotNull LongSparseArray<T> $this$remove, long key, T value) {
        k.e($this$remove, "<this>");
        int index = $this$remove.indexOfKey(key);
        if (index < 0 || !k.a(value, $this$remove.valueAt(index))) {
            return false;
        }
        $this$remove.removeAt(index);
        return true;
    }

    @RequiresApi(16)
    public static final <T> void putAll(@NotNull LongSparseArray<T> $this$putAll, @NotNull LongSparseArray<T> other) {
        k.e($this$putAll, "<this>");
        k.e(other, "other");
        LongSparseArray $this$forEach$iv = other;
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

    @RequiresApi(16)
    public static final <T> void forEach(@NotNull LongSparseArray<T> $this$forEach, @NotNull p<? super Long, ? super T, x> action) {
        k.e($this$forEach, "<this>");
        k.e(action, "action");
        int size = $this$forEach.size();
        if (size > 0) {
            int i = 0;
            do {
                int index = i;
                i++;
                action.invoke(Long.valueOf($this$forEach.keyAt(index)), $this$forEach.valueAt(index));
            } while (i < size);
        }
    }

    @RequiresApi(16)
    @NotNull
    public static final <T> h0 keyIterator(@NotNull LongSparseArray<T> $this$keyIterator) {
        k.e($this$keyIterator, "<this>");
        return new LongSparseArrayKt$keyIterator$1($this$keyIterator);
    }

    @RequiresApi(16)
    @NotNull
    public static final <T> Iterator<T> valueIterator(@NotNull LongSparseArray<T> $this$valueIterator) {
        k.e($this$valueIterator, "<this>");
        return new LongSparseArrayKt$valueIterator$1($this$valueIterator);
    }
}
