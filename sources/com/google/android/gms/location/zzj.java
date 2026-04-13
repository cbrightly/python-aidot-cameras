package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.WorkSource;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.location.zzd;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzj implements Parcelable.Creator {
    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        WorkSource workSource = new WorkSource();
        String str = null;
        zzd zzd = null;
        long j = Long.MAX_VALUE;
        long j2 = Long.MAX_VALUE;
        int i = 0;
        boolean z = false;
        int i2 = 0;
        int i3 = 102;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    j = SafeParcelReader.readLong(parcel2, readHeader);
                    break;
                case 2:
                    i = SafeParcelReader.readInt(parcel2, readHeader);
                    break;
                case 3:
                    i3 = SafeParcelReader.readInt(parcel2, readHeader);
                    break;
                case 4:
                    j2 = SafeParcelReader.readLong(parcel2, readHeader);
                    break;
                case 5:
                    z = SafeParcelReader.readBoolean(parcel2, readHeader);
                    break;
                case 6:
                    workSource = (WorkSource) SafeParcelReader.createParcelable(parcel2, readHeader, WorkSource.CREATOR);
                    break;
                case 7:
                    i2 = SafeParcelReader.readInt(parcel2, readHeader);
                    break;
                case 8:
                    str = SafeParcelReader.createString(parcel2, readHeader);
                    break;
                case 9:
                    zzd = (zzd) SafeParcelReader.createParcelable(parcel2, readHeader, zzd.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel2, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel2, validateObjectHeader);
        return new CurrentLocationRequest(j, i, i3, j2, z, i2, str, workSource, zzd);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new CurrentLocationRequest[i];
    }
}
