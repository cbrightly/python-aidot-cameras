package com.google.firebase.messaging;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public class RemoteMessageCreator implements Parcelable.Creator<RemoteMessage> {
    public static final int CONTENT_DESCRIPTION = 0;

    @Nullable
    public RemoteMessage createFromParcel(Parcel parcel) {
        int end = SafeParcelReader.validateObjectHeader(parcel);
        Bundle _local_safe_0a1b_bundle = null;
        while (parcel.dataPosition() < end) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 2:
                    _local_safe_0a1b_bundle = SafeParcelReader.createBundle(parcel, header);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, end);
        return new RemoteMessage(_local_safe_0a1b_bundle);
    }

    @Nullable
    public RemoteMessage[] newArray(int size) {
        return new RemoteMessage[size];
    }

    static void writeToParcel(RemoteMessage obj, Parcel parcel, int flags) {
        int myStart = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 2, obj.bundle, false);
        SafeParcelWriter.finishObjectHeader(parcel, myStart);
    }
}
