package com.google.android.material.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class ParcelableSparseIntArray extends SparseIntArray implements Parcelable {
    public static final Parcelable.Creator<ParcelableSparseIntArray> CREATOR = new Parcelable.Creator<ParcelableSparseIntArray>() {
        @NonNull
        public ParcelableSparseIntArray createFromParcel(@NonNull Parcel source) {
            int size = source.readInt();
            ParcelableSparseIntArray read = new ParcelableSparseIntArray(size);
            int[] keys = new int[size];
            int[] values = new int[size];
            source.readIntArray(keys);
            source.readIntArray(values);
            for (int i = 0; i < size; i++) {
                read.put(keys[i], values[i]);
            }
            return read;
        }

        @NonNull
        public ParcelableSparseIntArray[] newArray(int size) {
            return new ParcelableSparseIntArray[size];
        }
    };

    public ParcelableSparseIntArray() {
    }

    public ParcelableSparseIntArray(int initialCapacity) {
        super(initialCapacity);
    }

    public ParcelableSparseIntArray(@NonNull SparseIntArray sparseIntArray) {
        for (int i = 0; i < sparseIntArray.size(); i++) {
            put(sparseIntArray.keyAt(i), sparseIntArray.valueAt(i));
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel dest, int flags) {
        int[] keys = new int[size()];
        int[] values = new int[size()];
        for (int i = 0; i < size(); i++) {
            keys[i] = keyAt(i);
            values[i] = valueAt(i);
        }
        dest.writeInt(size());
        dest.writeIntArray(keys);
        dest.writeIntArray(values);
    }
}
