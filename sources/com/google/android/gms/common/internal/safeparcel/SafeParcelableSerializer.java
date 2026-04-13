package com.google.android.gms.common.internal.safeparcel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.internal.common.zzag;
import java.util.ArrayList;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class SafeParcelableSerializer {
    private SafeParcelableSerializer() {
    }

    @NonNull
    @KeepForSdk
    public static <T extends SafeParcelable> T deserializeFromBytes(@NonNull byte[] serializedBytes, @NonNull Parcelable.Creator<T> safeParcelableCreator) {
        Preconditions.checkNotNull(safeParcelableCreator);
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(serializedBytes, 0, serializedBytes.length);
        obtain.setDataPosition(0);
        T t = (SafeParcelable) safeParcelableCreator.createFromParcel(obtain);
        obtain.recycle();
        return t;
    }

    @KeepForSdk
    @Nullable
    public static <T extends SafeParcelable> T deserializeFromIntentExtra(@NonNull Intent intent, @NonNull String extra, @NonNull Parcelable.Creator<T> safeParcelableCreator) {
        byte[] byteArrayExtra = intent.getByteArrayExtra(extra);
        if (byteArrayExtra == null) {
            return null;
        }
        return deserializeFromBytes(byteArrayExtra, safeParcelableCreator);
    }

    @NonNull
    @KeepForSdk
    public static <T extends SafeParcelable> T deserializeFromString(@NonNull String serializedString, @NonNull Parcelable.Creator<T> safeParcelableCreator) {
        return deserializeFromBytes(Base64Utils.decodeUrlSafe(serializedString), safeParcelableCreator);
    }

    @Deprecated
    @Nullable
    public static <T extends SafeParcelable> ArrayList<T> deserializeIterableFromBundle(@NonNull Bundle bundle, @NonNull String key, @NonNull Parcelable.Creator<T> safeParcelableCreator) {
        ArrayList arrayList = (ArrayList) bundle.getSerializable(key);
        if (arrayList == null) {
            return null;
        }
        ArrayList<T> arrayList2 = new ArrayList<>(arrayList.size());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList2.add(deserializeFromBytes((byte[]) arrayList.get(i), safeParcelableCreator));
        }
        return arrayList2;
    }

    @KeepForSdk
    @Nullable
    public static <T extends SafeParcelable> ArrayList<T> deserializeIterableFromBundleSafe(@NonNull Bundle bundle, @NonNull String key, @NonNull Parcelable.Creator<T> safeParcelableCreator) {
        return deserializeIterableFromBytes(bundle.getByteArray(key), safeParcelableCreator);
    }

    @Nullable
    public static <T extends SafeParcelable> ArrayList<T> deserializeIterableFromBytes(@Nullable byte[] serializedBytes, @NonNull Parcelable.Creator<T> safeParcelableCreator) {
        if (serializedBytes == null) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(serializedBytes, 0, serializedBytes.length);
        obtain.setDataPosition(0);
        try {
            ArrayList<T> arrayList = new ArrayList<>();
            obtain.readTypedList(arrayList, safeParcelableCreator);
            return arrayList;
        } finally {
            obtain.recycle();
        }
    }

    @Nullable
    @KeepForSdk
    @Deprecated
    public static <T extends SafeParcelable> ArrayList<T> deserializeIterableFromIntentExtra(@NonNull Intent intent, @NonNull String extra, @NonNull Parcelable.Creator<T> safeParcelableCreator) {
        ArrayList arrayList = (ArrayList) intent.getSerializableExtra(extra);
        if (arrayList == null) {
            return null;
        }
        ArrayList<T> arrayList2 = new ArrayList<>(arrayList.size());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList2.add(deserializeFromBytes((byte[]) arrayList.get(i), safeParcelableCreator));
        }
        return arrayList2;
    }

    @KeepForSdk
    @Nullable
    public static <T extends SafeParcelable> ArrayList<T> deserializeIterableFromIntentExtraSafe(@NonNull Intent intent, @NonNull String extra, @NonNull Parcelable.Creator<T> safeParcelableCreator) {
        return deserializeIterableFromBytes(intent.getByteArrayExtra(extra), safeParcelableCreator);
    }

    @Deprecated
    public static <T extends SafeParcelable> void serializeIterableToBundle(@NonNull Iterable<T> safeParcelables, @NonNull Bundle bundle, @NonNull String key) {
        ArrayList arrayList = new ArrayList();
        for (T serializeToBytes : safeParcelables) {
            arrayList.add(serializeToBytes(serializeToBytes));
        }
        bundle.putSerializable(key, arrayList);
    }

    public static <T extends SafeParcelable> void serializeIterableToBundleSafe(@NonNull Iterable<T> safeParcelables, @NonNull Bundle bundle, @NonNull String key) {
        bundle.putByteArray(key, zza(safeParcelables));
    }

    @KeepForSdk
    @Deprecated
    public static <T extends SafeParcelable> void serializeIterableToIntentExtra(@NonNull Iterable<T> safeParcelables, @NonNull Intent intent, @NonNull String extra) {
        ArrayList arrayList = new ArrayList();
        for (T serializeToBytes : safeParcelables) {
            arrayList.add(serializeToBytes(serializeToBytes));
        }
        intent.putExtra(extra, arrayList);
    }

    @KeepForSdk
    public static <T extends SafeParcelable> void serializeIterableToIntentExtraSafe(@NonNull Iterable<T> safeParcelables, @NonNull Intent intent, @NonNull String extra) {
        intent.putExtra(extra, zza(safeParcelables));
    }

    @NonNull
    @KeepForSdk
    public static <T extends SafeParcelable> byte[] serializeToBytes(@NonNull T safeParcelable) {
        Parcel obtain = Parcel.obtain();
        safeParcelable.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        return marshall;
    }

    @KeepForSdk
    public static <T extends SafeParcelable> void serializeToIntentExtra(@NonNull T safeParcelable, @NonNull Intent intent, @NonNull String extra) {
        intent.putExtra(extra, serializeToBytes(safeParcelable));
    }

    @NonNull
    @KeepForSdk
    public static <T extends SafeParcelable> String serializeToString(@NonNull T safeParcelable) {
        return Base64Utils.encodeUrlSafe(serializeToBytes(safeParcelable));
    }

    private static byte[] zza(Iterable iterable) {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeTypedList(zzag.zzj(iterable));
            return obtain.marshall();
        } finally {
            obtain.recycle();
        }
    }
}
