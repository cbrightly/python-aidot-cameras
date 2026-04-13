package com.google.android.material.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class ParcelableSparseArray extends SparseArray<Parcelable> implements Parcelable {
    public static final Parcelable.Creator<ParcelableSparseArray> CREATOR = new Parcelable.ClassLoaderCreator<ParcelableSparseArray>() {
        @NonNull
        public ParcelableSparseArray createFromParcel(@NonNull Parcel source, ClassLoader loader) {
            return new ParcelableSparseArray(source, loader);
        }

        @Nullable
        public ParcelableSparseArray createFromParcel(@NonNull Parcel source) {
            return new ParcelableSparseArray(source, (ClassLoader) null);
        }

        @NonNull
        public ParcelableSparseArray[] newArray(int size) {
            return new ParcelableSparseArray[size];
        }
    };

    public ParcelableSparseArray() {
    }

    public ParcelableSparseArray(@NonNull Parcel source, @Nullable ClassLoader loader) {
        int size = source.readInt();
        int[] keys = new int[size];
        source.readIntArray(keys);
        Parcelable[] values = source.readParcelableArray(loader);
        for (int i = 0; i < size; i++) {
            put(keys[i], values[i]);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        int size = size();
        int[] keys = new int[size];
        Parcelable[] values = new Parcelable[size];
        for (int i = 0; i < size; i++) {
            keys[i] = keyAt(i);
            values[i] = (Parcelable) valueAt(i);
        }
        parcel.writeInt(size);
        parcel.writeIntArray(keys);
        parcel.writeParcelableArray(values, flags);
    }
}
