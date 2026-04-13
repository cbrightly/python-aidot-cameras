package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.concurrent.GuardedBy;

@SafeParcelable.Class(creator = "CapabilityInfoParcelableCreator")
@SafeParcelable.Reserved({1})
public final class zzah extends AbstractSafeParcelable implements CapabilityInfo {
    public static final Parcelable.Creator<zzah> CREATOR = new zzai();
    private final Object lock = new Object();
    @SafeParcelable.Field(getter = "getName", id = 2)
    private final String name;
    @GuardedBy("lock")
    private Set<Node> zzbt;
    @SafeParcelable.Field(getter = "getNodeParcelables", id = 3)
    private final List<zzfo> zzca;

    @SafeParcelable.Constructor
    public zzah(@SafeParcelable.Param(id = 2) String str, @SafeParcelable.Param(id = 3) List<zzfo> list) {
        this.name = str;
        this.zzca = list;
        this.zzbt = null;
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(list);
    }

    public final String getName() {
        return this.name;
    }

    public final Set<Node> getNodes() {
        Set<Node> set;
        synchronized (this.lock) {
            if (this.zzbt == null) {
                this.zzbt = new HashSet(this.zzca);
            }
            set = this.zzbt;
        }
        return set;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, getName(), false);
        SafeParcelWriter.writeTypedList(parcel, 3, this.zzca, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final String toString() {
        String str = this.name;
        String valueOf = String.valueOf(this.zzca);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 18 + String.valueOf(valueOf).length());
        sb.append("CapabilityInfo{");
        sb.append(str);
        sb.append(", ");
        sb.append(valueOf);
        sb.append("}");
        return sb.toString();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || zzah.class != obj.getClass()) {
            return false;
        }
        zzah zzah = (zzah) obj;
        String str = this.name;
        if (str == null ? zzah.name != null : !str.equals(zzah.name)) {
            return false;
        }
        List<zzfo> list = this.zzca;
        List<zzfo> list2 = zzah.zzca;
        if (list == null ? list2 == null : list.equals(list2)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        String str = this.name;
        int i = 0;
        int hashCode = ((str != null ? str.hashCode() : 0) + 31) * 31;
        List<zzfo> list = this.zzca;
        if (list != null) {
            i = list.hashCode();
        }
        return hashCode + i;
    }
}
