package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "AmsEntityUpdateParcelableCreator")
@SafeParcelable.Reserved({1})
public final class zzi extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzi> CREATOR = new zzj();
    @SafeParcelable.Field(getter = "getValue", id = 4)
    private final String value;
    @SafeParcelable.Field(getter = "getEntityId", id = 2)
    private byte zzbd;
    @SafeParcelable.Field(getter = "getAttributeId", id = 3)
    private final byte zzbe;

    @SafeParcelable.Constructor
    public zzi(@SafeParcelable.Param(id = 2) byte b, @SafeParcelable.Param(id = 3) byte b2, @SafeParcelable.Param(id = 4) String str) {
        this.zzbd = b;
        this.zzbe = b2;
        this.value = str;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeByte(parcel, 2, this.zzbd);
        SafeParcelWriter.writeByte(parcel, 3, this.zzbe);
        SafeParcelWriter.writeString(parcel, 4, this.value, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final String toString() {
        byte b = this.zzbd;
        byte b2 = this.zzbe;
        String str = this.value;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 73);
        sb.append("AmsEntityUpdateParcelable{, mEntityId=");
        sb.append(b);
        sb.append(", mAttributeId=");
        sb.append(b2);
        sb.append(", mValue='");
        sb.append(str);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || zzi.class != obj.getClass()) {
            return false;
        }
        zzi zzi = (zzi) obj;
        if (this.zzbd == zzi.zzbd && this.zzbe == zzi.zzbe && this.value.equals(zzi.value)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return ((((this.zzbd + 31) * 31) + this.zzbe) * 31) + this.value.hashCode();
    }
}
