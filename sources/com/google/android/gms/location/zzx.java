package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.WorkSource;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.location.zzd;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzx implements Parcelable.Creator {
    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        WorkSource workSource = new WorkSource();
        String str = null;
        zzd zzd = null;
        long j = Long.MAX_VALUE;
        long j2 = Long.MAX_VALUE;
        boolean z = false;
        int i = 0;
        int i2 = 0;
        boolean z2 = false;
        int i3 = 102;
        long j3 = 3600000;
        long j4 = 600000;
        long j5 = 0;
        int i4 = Integer.MAX_VALUE;
        float f = 0.0f;
        long j6 = -1;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i3 = SafeParcelReader.readInt(parcel2, readHeader);
                    break;
                case 2:
                    j3 = SafeParcelReader.readLong(parcel2, readHeader);
                    break;
                case 3:
                    j4 = SafeParcelReader.readLong(parcel2, readHeader);
                    break;
                case 5:
                    j = SafeParcelReader.readLong(parcel2, readHeader);
                    break;
                case 6:
                    i4 = SafeParcelReader.readInt(parcel2, readHeader);
                    break;
                case 7:
                    f = SafeParcelReader.readFloat(parcel2, readHeader);
                    break;
                case 8:
                    j5 = SafeParcelReader.readLong(parcel2, readHeader);
                    break;
                case 9:
                    z = SafeParcelReader.readBoolean(parcel2, readHeader);
                    break;
                case 10:
                    j2 = SafeParcelReader.readLong(parcel2, readHeader);
                    break;
                case 11:
                    j6 = SafeParcelReader.readLong(parcel2, readHeader);
                    break;
                case 12:
                    i = SafeParcelReader.readInt(parcel2, readHeader);
                    break;
                case 13:
                    i2 = SafeParcelReader.readInt(parcel2, readHeader);
                    break;
                case 14:
                    str = SafeParcelReader.createString(parcel2, readHeader);
                    break;
                case 15:
                    z2 = SafeParcelReader.readBoolean(parcel2, readHeader);
                    break;
                case 16:
                    workSource = (WorkSource) SafeParcelReader.createParcelable(parcel2, readHeader, WorkSource.CREATOR);
                    break;
                case 17:
                    zzd = (zzd) SafeParcelReader.createParcelable(parcel2, readHeader, zzd.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel2, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel2, validateObjectHeader);
        return new LocationRequest(i3, j3, j4, j5, j, j2, i4, f, z, j6, i, i2, str, z2, workSource, zzd);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new LocationRequest[i];
    }
}
