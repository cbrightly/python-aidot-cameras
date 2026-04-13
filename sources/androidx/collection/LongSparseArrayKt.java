package androidx.collection;

import java.util.Iterator;
import kotlin.collections.h0;
import kotlin.jvm.functions.a;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: LongSparseArray.kt */
public final class LongSparseArrayKt {
    public static final <T> int getSize(@NotNull LongSparseArray<T> $receiver) {
        k.f($receiver, "receiver$0");
        return $receiver.size();
    }

    public static final <T> boolean contains(@NotNull LongSparseArray<T> $receiver, long key) {
        k.f($receiver, "receiver$0");
        return $receiver.containsKey(key);
    }

    public static final <T> void set(@NotNull LongSparseArray<T> $receiver, long key, T value) {
        k.f($receiver, "receiver$0");
        $receiver.put(key, value);
    }

    @NotNull
    public static final <T> LongSparseArray<T> plus(@NotNull LongSparseArray<T> $receiver, @NotNull LongSparseArray<T> other) {
        k.f($receiver, "receiver$0");
        k.f(other, "other");
        LongSparseArray longSparseArray = new LongSparseArray($receiver.size() + other.size());
        longSparseArray.putAll($receiver);
        longSparseArray.putAll(other);
        return longSparseArray;
    }

    public static final <T> T getOrDefault(@NotNull LongSparseArray<T> $receiver, long key, T defaultValue) {
        k.f($receiver, "receiver$0");
        return $receiver.get(key, defaultValue);
    }

    public static final <T> T getOrElse(@NotNull LongSparseArray<T> $receiver, long key, @NotNull a<? extends T> defaultValue) {
        k.f($receiver, "receiver$0");
        k.f(defaultValue, "defaultValue");
        T t = $receiver.get(key);
        return t != null ? t : defaultValue.invoke();
    }

    public static final <T> boolean isNotEmpty(@NotNull LongSparseArray<T> $receiver) {
        k.f($receiver, "receiver$0");
        return !$receiver.isEmpty();
    }

    public static final <T> boolean remove(@NotNull LongSparseArray<T> $receiver, long key, T value) {
        k.f($receiver, "receiver$0");
        return $receiver.remove(key, value);
    }

    public static final <T> void forEach(@NotNull LongSparseArray<T> $receiver, @NotNull p<? super Long, ? super T, x> action) {
        k.f($receiver, "receiver$0");
        k.f(action, "action");
        int size = $receiver.size();
        for (int index = 0; index < size; index++) {
            action.invoke(Long.valueOf($receiver.keyAt(index)), $receiver.valueAt(index));
        }
    }

    @NotNull
    public static final <T> h0 keyIterator(@NotNull LongSparseArray<T> $receiver) {
        k.f($receiver, "receiver$0");
        return new LongSparseArrayKt$keyIterator$1($receiver);
    }

    @NotNull
    public static final <T> Iterator<T> valueIterator(@NotNull LongSparseArray<T> $receiver) {
        k.f($receiver, "receiver$0");
        return new LongSparseArrayKt$valueIterator$1($receiver);
    }
}
