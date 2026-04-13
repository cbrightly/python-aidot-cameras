package com.google.android.gms.common.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.errorprone.annotations.InlineMe;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class CollectionUtils {
    private CollectionUtils() {
    }

    @KeepForSdk
    public static boolean isEmpty(@Nullable Collection<?> collection) {
        if (collection == null) {
            return true;
        }
        return collection.isEmpty();
    }

    @InlineMe(imports = {"java.util.Collections"}, replacement = "Collections.emptyList()")
    @NonNull
    @KeepForSdk
    @Deprecated
    public static <T> List<T> listOf() {
        return Collections.emptyList();
    }

    @NonNull
    @KeepForSdk
    public static <K, V> Map<K, V> mapOf(@NonNull K key1, @NonNull V value1, @NonNull K key2, @NonNull V value2, @NonNull K key3, @NonNull V value3) {
        Map zza = zza(3, false);
        zza.put(key1, value1);
        zza.put(key2, value2);
        zza.put(key3, value3);
        return Collections.unmodifiableMap(zza);
    }

    @NonNull
    @KeepForSdk
    public static <K, V> Map<K, V> mapOfKeyValueArrays(@NonNull K[] keys, @NonNull V[] values) {
        int length = keys.length;
        int length2 = values.length;
        if (length == length2) {
            switch (length) {
                case 0:
                    return Collections.emptyMap();
                case 1:
                    return Collections.singletonMap(keys[0], values[0]);
                default:
                    Map zza = zza(length, false);
                    for (int i = 0; i < keys.length; i++) {
                        zza.put(keys[i], values[i]);
                    }
                    return Collections.unmodifiableMap(zza);
            }
        } else {
            throw new IllegalArgumentException("Key and values array lengths not equal: " + length + " != " + length2);
        }
    }

    @NonNull
    @KeepForSdk
    public static <T> Set<T> mutableSetOfWithSize(int sizeEstimate) {
        if (sizeEstimate == 0) {
            return new ArraySet();
        }
        return zzb(sizeEstimate, true);
    }

    @NonNull
    @KeepForSdk
    @Deprecated
    public static <T> Set<T> setOf(@NonNull T item1, @NonNull T item2, @NonNull T item3) {
        Set zzb = zzb(3, false);
        zzb.add(item1);
        zzb.add(item2);
        zzb.add(item3);
        return Collections.unmodifiableSet(zzb);
    }

    private static Map zza(int i, boolean z) {
        if (i <= 256) {
            return new ArrayMap(i);
        }
        return new HashMap(i, 1.0f);
    }

    @InlineMe(imports = {"java.util.Collections"}, replacement = "Collections.singletonList(item)")
    @NonNull
    @KeepForSdk
    @Deprecated
    public static <T> List<T> listOf(@NonNull T item) {
        return Collections.singletonList(item);
    }

    private static Set zzb(int i, boolean z) {
        int i2;
        float f;
        if (true != z) {
            i2 = 256;
        } else {
            i2 = 128;
        }
        if (i <= i2) {
            return new ArraySet(i);
        }
        if (true != z) {
            f = 1.0f;
        } else {
            f = 0.75f;
        }
        return new HashSet(i, f);
    }

    @NonNull
    @KeepForSdk
    @Deprecated
    public static <T> List<T> listOf(@NonNull T... items) {
        switch (items.length) {
            case 0:
                return Collections.emptyList();
            case 1:
                return Collections.singletonList(items[0]);
            default:
                return Collections.unmodifiableList(Arrays.asList(items));
        }
    }

    @NonNull
    @KeepForSdk
    public static <K, V> Map<K, V> mapOf(@NonNull K key1, @NonNull V value1, @NonNull K key2, @NonNull V value2, @NonNull K key3, @NonNull V value3, @NonNull K key4, @NonNull V value4, @NonNull K key5, @NonNull V value5, @NonNull K key6, @NonNull V value6) {
        Map zza = zza(6, false);
        zza.put(key1, value1);
        zza.put(key2, value2);
        zza.put(key3, value3);
        zza.put(key4, value4);
        zza.put(key5, value5);
        zza.put(key6, value6);
        return Collections.unmodifiableMap(zza);
    }

    @NonNull
    @KeepForSdk
    @Deprecated
    public static <T> Set<T> setOf(@NonNull T... items) {
        int length = items.length;
        switch (length) {
            case 0:
                return Collections.emptySet();
            case 1:
                return Collections.singleton(items[0]);
            case 2:
                T t = items[0];
                T t2 = items[1];
                Set zzb = zzb(2, false);
                zzb.add(t);
                zzb.add(t2);
                return Collections.unmodifiableSet(zzb);
            case 3:
                return setOf(items[0], items[1], items[2]);
            case 4:
                T t3 = items[0];
                T t4 = items[1];
                T t5 = items[2];
                T t6 = items[3];
                Set zzb2 = zzb(4, false);
                zzb2.add(t3);
                zzb2.add(t4);
                zzb2.add(t5);
                zzb2.add(t6);
                return Collections.unmodifiableSet(zzb2);
            default:
                Set zzb3 = zzb(length, false);
                Collections.addAll(zzb3, items);
                return Collections.unmodifiableSet(zzb3);
        }
    }
}
