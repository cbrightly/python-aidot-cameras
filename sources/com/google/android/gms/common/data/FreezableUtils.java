package com.google.android.gms.common.data;

import androidx.annotation.NonNull;
import java.util.ArrayList;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class FreezableUtils {
    @NonNull
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(@NonNull ArrayList<E> list) {
        ArrayList<T> arrayList = new ArrayList<>(list.size());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(((Freezable) list.get(i)).freeze());
        }
        return arrayList;
    }

    @NonNull
    public static <T, E extends Freezable<T>> ArrayList<T> freezeIterable(@NonNull Iterable<E> iterable) {
        ArrayList<T> arrayList = new ArrayList<>();
        for (E freeze : iterable) {
            arrayList.add(freeze.freeze());
        }
        return arrayList;
    }

    @NonNull
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(@NonNull E[] array) {
        ArrayList<T> arrayList = new ArrayList<>(array.length);
        for (E freeze : array) {
            arrayList.add(freeze.freeze());
        }
        return arrayList;
    }
}
