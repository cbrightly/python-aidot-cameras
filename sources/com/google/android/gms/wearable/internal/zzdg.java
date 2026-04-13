package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "DeleteDataItemsResponseCreator")
@SafeParcelable.Reserved({1})
public final class zzdg extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzdg> CREATOR = new zzdh();
    @SafeParcelable.Field(id = 2)
    public final int statusCode;
    @SafeParcelable.Field(id = 3)
    public final int zzdh;

    @SafeParcelable.Constructor
    public zzdg(@SafeParcelable.Param(id = 2) int i, @SafeParcelable.Param(id = 3) int i2) {
        this.statusCode = i;
        this.zzdh = i2;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.statusCode);
        SafeParcelWriter.writeInt(parcel, 3, this.zzdh);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
