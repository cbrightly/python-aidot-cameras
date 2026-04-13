package com.google.android.gms.common.data;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.ArrayList;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class DataBufferUtils {
    @NonNull
    @KeepForSdk
    public static final String KEY_NEXT_PAGE_TOKEN = "next_page_token";
    @NonNull
    @KeepForSdk
    public static final String KEY_PREV_PAGE_TOKEN = "prev_page_token";

    private DataBufferUtils() {
    }

    @NonNull
    public static <T, E extends Freezable<T>> ArrayList<T> freezeAndClose(@NonNull DataBuffer<E> buffer) {
        ArrayList<T> arrayList = new ArrayList<>(buffer.getCount());
        try {
            for (E freeze : buffer) {
                arrayList.add(freeze.freeze());
            }
            return arrayList;
        } finally {
            buffer.close();
        }
    }

    public static boolean hasData(@NonNull DataBuffer<?> buffer) {
        return buffer != null && buffer.getCount() > 0;
    }

    public static boolean hasNextPage(@NonNull DataBuffer<?> buffer) {
        Bundle metadata = buffer.getMetadata();
        return (metadata == null || metadata.getString(KEY_NEXT_PAGE_TOKEN) == null) ? false : true;
    }

    public static boolean hasPrevPage(@NonNull DataBuffer<?> buffer) {
        Bundle metadata = buffer.getMetadata();
        return (metadata == null || metadata.getString(KEY_PREV_PAGE_TOKEN) == null) ? false : true;
    }
}
