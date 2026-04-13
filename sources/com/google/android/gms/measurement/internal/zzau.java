package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Iterator;

@SafeParcelable.Class(creator = "EventParamsCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzau extends AbstractSafeParcelable implements Iterable<String> {
    public static final Parcelable.Creator<zzau> CREATOR = new zzav();
    /* access modifiers changed from: private */
    @SafeParcelable.Field(getter = "z", id = 2)
    public final Bundle zza;

    @SafeParcelable.Constructor
    zzau(@SafeParcelable.Param(id = 2) Bundle bundle) {
        this.zza = bundle;
    }

    public final Iterator iterator() {
        return new zzat(this);
    }

    public final String toString() {
        return this.zza.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 2, zzc(), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final int zza() {
        return this.zza.size();
    }

    public final Bundle zzc() {
        return new Bundle(this.zza);
    }

    /* access modifiers changed from: package-private */
    public final Double zzd(String str) {
        return Double.valueOf(this.zza.getDouble("value"));
    }

    /* access modifiers changed from: package-private */
    public final Long zze(String str) {
        return Long.valueOf(this.zza.getLong("value"));
    }

    /* access modifiers changed from: package-private */
    public final Object zzf(String str) {
        return this.zza.get(str);
    }

    /* access modifiers changed from: package-private */
    public final String zzg(String str) {
        return this.zza.getString(str);
    }
}
