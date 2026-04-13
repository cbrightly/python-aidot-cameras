package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.wearable.Node;

@SafeParcelable.Class(creator = "NodeParcelableCreator")
@SafeParcelable.Reserved({1})
public final class zzfo extends AbstractSafeParcelable implements Node {
    public static final Parcelable.Creator<zzfo> CREATOR = new zzfp();
    @SafeParcelable.Field(getter = "getDisplayName", id = 3)
    private final String zzbk;
    @SafeParcelable.Field(getter = "getId", id = 2)
    private final String zzdm;
    @SafeParcelable.Field(getter = "getHopCount", id = 4)
    private final int zzen;
    @SafeParcelable.Field(getter = "isNearby", id = 5)
    private final boolean zzeo;

    @SafeParcelable.Constructor
    public zzfo(@SafeParcelable.Param(id = 2) String str, @SafeParcelable.Param(id = 3) String str2, @SafeParcelable.Param(id = 4) int i, @SafeParcelable.Param(id = 5) boolean z) {
        this.zzdm = str;
        this.zzbk = str2;
        this.zzen = i;
        this.zzeo = z;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, getId(), false);
        SafeParcelWriter.writeString(parcel, 3, getDisplayName(), false);
        SafeParcelWriter.writeInt(parcel, 4, this.zzen);
        SafeParcelWriter.writeBoolean(parcel, 5, isNearby());
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzfo)) {
            return false;
        }
        return ((zzfo) obj).zzdm.equals(this.zzdm);
    }

    public final int hashCode() {
        return this.zzdm.hashCode();
    }

    public final String toString() {
        String str = this.zzbk;
        String str2 = this.zzdm;
        int i = this.zzen;
        boolean z = this.zzeo;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 45 + String.valueOf(str2).length());
        sb.append("Node{");
        sb.append(str);
        sb.append(", id=");
        sb.append(str2);
        sb.append(", hops=");
        sb.append(i);
        sb.append(", isNearby=");
        sb.append(z);
        sb.append("}");
        return sb.toString();
    }

    public final String getId() {
        return this.zzdm;
    }

    public final String getDisplayName() {
        return this.zzbk;
    }

    public final boolean isNearby() {
        return this.zzeo;
    }
}
