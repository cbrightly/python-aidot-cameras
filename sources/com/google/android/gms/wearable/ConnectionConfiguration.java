package com.google.android.gms.wearable;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "ConnectionConfigurationCreator")
@SafeParcelable.Reserved({1})
public class ConnectionConfiguration extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<ConnectionConfiguration> CREATOR = new zzg();
    @SafeParcelable.Field(getter = "getName", id = 2)
    private final String name;
    @SafeParcelable.Field(getter = "getType", id = 4)
    private final int type;
    @SafeParcelable.Field(getter = "getAddress", id = 3)
    private final String zzi;
    @SafeParcelable.Field(getter = "getRole", id = 5)
    private final int zzj;
    @SafeParcelable.Field(getter = "isEnabled", id = 6)
    private final boolean zzk;
    @SafeParcelable.Field(getter = "isConnected", id = 7)
    private volatile boolean zzl;
    @SafeParcelable.Field(getter = "getPeerNodeId", id = 8)
    private volatile String zzm;
    @SafeParcelable.Field(getter = "getBtlePriority", id = 9)
    private boolean zzn;
    @SafeParcelable.Field(getter = "getNodeId", id = 10)
    private String zzo;

    @SafeParcelable.Constructor
    ConnectionConfiguration(@SafeParcelable.Param(id = 2) String str, @SafeParcelable.Param(id = 3) String str2, @SafeParcelable.Param(id = 4) int i, @SafeParcelable.Param(id = 5) int i2, @SafeParcelable.Param(id = 6) boolean z, @SafeParcelable.Param(id = 7) boolean z2, @SafeParcelable.Param(id = 8) String str3, @SafeParcelable.Param(id = 9) boolean z3, @SafeParcelable.Param(id = 10) String str4) {
        this.name = str;
        this.zzi = str2;
        this.type = i;
        this.zzj = i2;
        this.zzk = z;
        this.zzl = z2;
        this.zzm = str3;
        this.zzn = z3;
        this.zzo = str4;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.name, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzi, false);
        SafeParcelWriter.writeInt(parcel, 4, this.type);
        SafeParcelWriter.writeInt(parcel, 5, this.zzj);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zzk);
        SafeParcelWriter.writeBoolean(parcel, 7, this.zzl);
        SafeParcelWriter.writeString(parcel, 8, this.zzm, false);
        SafeParcelWriter.writeBoolean(parcel, 9, this.zzn);
        SafeParcelWriter.writeString(parcel, 10, this.zzo, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ConnectionConfiguration[ ");
        String valueOf = String.valueOf(this.name);
        sb.append(valueOf.length() != 0 ? "mName=".concat(valueOf) : new String("mName="));
        String valueOf2 = String.valueOf(this.zzi);
        sb.append(valueOf2.length() != 0 ? ", mAddress=".concat(valueOf2) : new String(", mAddress="));
        int i = this.type;
        StringBuilder sb2 = new StringBuilder(19);
        sb2.append(", mType=");
        sb2.append(i);
        sb.append(sb2.toString());
        int i2 = this.zzj;
        StringBuilder sb3 = new StringBuilder(19);
        sb3.append(", mRole=");
        sb3.append(i2);
        sb.append(sb3.toString());
        boolean z = this.zzk;
        StringBuilder sb4 = new StringBuilder(16);
        sb4.append(", mEnabled=");
        sb4.append(z);
        sb.append(sb4.toString());
        boolean z2 = this.zzl;
        StringBuilder sb5 = new StringBuilder(20);
        sb5.append(", mIsConnected=");
        sb5.append(z2);
        sb.append(sb5.toString());
        String valueOf3 = String.valueOf(this.zzm);
        sb.append(valueOf3.length() != 0 ? ", mPeerNodeId=".concat(valueOf3) : new String(", mPeerNodeId="));
        boolean z3 = this.zzn;
        StringBuilder sb6 = new StringBuilder(21);
        sb6.append(", mBtlePriority=");
        sb6.append(z3);
        sb.append(sb6.toString());
        String valueOf4 = String.valueOf(this.zzo);
        sb.append(valueOf4.length() != 0 ? ", mNodeId=".concat(valueOf4) : new String(", mNodeId="));
        sb.append("]");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ConnectionConfiguration)) {
            return false;
        }
        ConnectionConfiguration connectionConfiguration = (ConnectionConfiguration) obj;
        if (!Objects.equal(this.name, connectionConfiguration.name) || !Objects.equal(this.zzi, connectionConfiguration.zzi) || !Objects.equal(Integer.valueOf(this.type), Integer.valueOf(connectionConfiguration.type)) || !Objects.equal(Integer.valueOf(this.zzj), Integer.valueOf(connectionConfiguration.zzj)) || !Objects.equal(Boolean.valueOf(this.zzk), Boolean.valueOf(connectionConfiguration.zzk)) || !Objects.equal(Boolean.valueOf(this.zzn), Boolean.valueOf(connectionConfiguration.zzn))) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.name, this.zzi, Integer.valueOf(this.type), Integer.valueOf(this.zzj), Boolean.valueOf(this.zzk), Boolean.valueOf(this.zzn));
    }
}
