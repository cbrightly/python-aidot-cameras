package androidx.collection;

import java.util.Iterator;
import kotlin.collections.g0;
import kotlin.jvm.functions.a;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: SparseArray.kt */
public final class SparseArrayKt {
    public static final <T> int getSize(@NotNull SparseArrayCompat<T> $receiver) {
        k.f($receiver, "receiver$0");
        return $receiver.size();
    }

    public static final <T> boolean contains(@NotNull SparseArrayCompat<T> $receiver, int key) {
        k.f($receiver, "receiver$0");
        return $receiver.containsKey(key);
    }

    public static final <T> void set(@NotNull SparseArrayCompat<T> $receiver, int key, T value) {
        k.f($receiver, "receiver$0");
        $receiver.put(key, value);
    }

    @NotNull
    public static final <T> SparseArrayCompat<T> plus(@NotNull SparseArrayCompat<T> $receiver, @NotNull SparseArrayCompat<T> other) {
        k.f($receiver, "receiver$0");
        k.f(other, "other");
        SparseArrayCompat sparseArrayCompat = new SparseArrayCompat($receiver.size() + other.size());
        sparseArrayCompat.putAll($receiver);
        sparseArrayCompat.putAll(other);
        return sparseArrayCompat;
    }

    public static final <T> T getOrDefault(@NotNull SparseArrayCompat<T> $receiver, int key, T defaultValue) {
        k.f($receiver, "receiver$0");
        return $receiver.get(key, defaultValue);
    }

    public static final <T> T getOrElse(@NotNull SparseArrayCompat<T> $receiver, int key, @NotNull a<? extends T> defaultValue) {
        k.f($receiver, "receiver$0");
        k.f(defaultValue, "defaultValue");
        T t = $receiver.get(key);
        return t != null ? t : defaultValue.invoke();
    }

    public static final <T> boolean isNotEmpty(@NotNull SparseArrayCompat<T> $receiver) {
        k.f($receiver, "receiver$0");
        return !$receiver.isEmpty();
    }

    public static final <T> boolean remove(@NotNull SparseArrayCompat<T> $receiver, int key, T value) {
        k.f($receiver, "receiver$0");
        return $receiver.remove(key, value);
    }

    public static final <T> void forEach(@NotNull SparseArrayCompat<T> $receiver, @NotNull p<? super Integer, ? super T, x> action) {
        k.f($receiver, "receiver$0");
        k.f(action, "action");
        int size = $receiver.size();
        for (int index = 0; index < size; index++) {
            action.invoke(Integer.valueOf($receiver.keyAt(index)), $receiver.valueAt(index));
        }
    }

    @NotNull
    public static final <T> g0 keyIterator(@NotNull SparseArrayCompat<T> $receiver) {
        k.f($receiver, "receiver$0");
        return new SparseArrayKt$keyIterator$1($receiver);
    }

    @NotNull
    public static final <T> Iterator<T> valueIterator(@NotNull SparseArrayCompat<T> $receiver) {
        k.f($receiver, "receiver$0");
        return new SparseArrayKt$valueIterator$1($receiver);
    }
}
