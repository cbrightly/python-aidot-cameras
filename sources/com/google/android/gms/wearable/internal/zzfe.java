package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.wearable.MessageEvent;
import com.google.maps.android.BuildConfig;

@SafeParcelable.Class(creator = "MessageEventParcelableCreator")
@SafeParcelable.Reserved({1})
public final class zzfe extends AbstractSafeParcelable implements MessageEvent {
    public static final Parcelable.Creator<zzfe> CREATOR = new zzff();
    @SafeParcelable.Field(getter = "getData", id = 4)
    private final byte[] data;
    @SafeParcelable.Field(getter = "getPath", id = 3)
    private final String zzcl;
    @SafeParcelable.Field(getter = "getRequestId", id = 2)
    private final int zzeh;
    @SafeParcelable.Field(getter = "getSourceNodeId", id = 5)
    private final String zzek;

    @SafeParcelable.Constructor
    public zzfe(@SafeParcelable.Param(id = 2) int i, @SafeParcelable.Param(id = 3) String str, @SafeParcelable.Param(id = 4) byte[] bArr, @SafeParcelable.Param(id = 5) String str2) {
        this.zzeh = i;
        this.zzcl = str;
        this.data = bArr;
        this.zzek = str2;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, getRequestId());
        SafeParcelWriter.writeString(parcel, 3, getPath(), false);
        SafeParcelWriter.writeByteArray(parcel, 4, getData(), false);
        SafeParcelWriter.writeString(parcel, 5, getSourceNodeId(), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final String toString() {
        int i = this.zzeh;
        String str = this.zzcl;
        byte[] bArr = this.data;
        String valueOf = String.valueOf(bArr == null ? BuildConfig.TRAVIS : Integer.valueOf(bArr.length));
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 43 + String.valueOf(valueOf).length());
        sb.append("MessageEventParcelable[");
        sb.append(i);
        sb.append(",");
        sb.append(str);
        sb.append(", size=");
        sb.append(valueOf);
        sb.append("]");
        return sb.toString();
    }

    public final int getRequestId() {
        return this.zzeh;
    }

    public final String getPath() {
        return this.zzcl;
    }

    public final byte[] getData() {
        return this.data;
    }

    public final String getSourceNodeId() {
        return this.zzek;
    }
}
