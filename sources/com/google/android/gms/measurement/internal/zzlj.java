package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "UserAttributeParcelCreator")
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzlj extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzlj> CREATOR = new zzlk();
    @SafeParcelable.Field(id = 1)
    public final int zza;
    @SafeParcelable.Field(id = 2)
    public final String zzb;
    @SafeParcelable.Field(id = 3)
    public final long zzc;
    @SafeParcelable.Field(id = 4)
    @Nullable
    public final Long zzd;
    @SafeParcelable.Field(id = 6)
    @Nullable
    public final String zze;
    @SafeParcelable.Field(id = 7)
    public final String zzf;
    @SafeParcelable.Field(id = 8)
    @Nullable
    public final Double zzg;

    @SafeParcelable.Constructor
    zzlj(@SafeParcelable.Param(id = 1) int i, @SafeParcelable.Param(id = 2) String str, @SafeParcelable.Param(id = 3) long j, @SafeParcelable.Param(id = 4) @Nullable Long l, @SafeParcelable.Param(id = 5) Float f, @SafeParcelable.Param(id = 6) @Nullable String str2, @SafeParcelable.Param(id = 7) String str3, @SafeParcelable.Param(id = 8) @Nullable Double d) {
        this.zza = i;
        this.zzb = str;
        this.zzc = j;
        this.zzd = l;
        if (i == 1) {
            this.zzg = f != null ? Double.valueOf(f.doubleValue()) : null;
        } else {
            this.zzg = d;
        }
        this.zze = str2;
        this.zzf = str3;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zzlk.zza(this, parcel, i);
    }

    @Nullable
    public final Object zza() {
        Long l = this.zzd;
        if (l != null) {
            return l;
        }
        Double d = this.zzg;
        if (d != null) {
            return d;
        }
        String str = this.zze;
        if (str != null) {
            return str;
        }
        return null;
    }

    zzlj(zzll zzll) {
        this(zzll.zzc, zzll.zzd, zzll.zze, zzll.zzb);
    }

    zzlj(String str, long j, @Nullable Object obj, String str2) {
        Preconditions.checkNotEmpty(str);
        this.zza = 2;
        this.zzb = str;
        this.zzc = j;
        this.zzf = str2;
        if (obj == null) {
            this.zzd = null;
            this.zzg = null;
            this.zze = null;
        } else if (obj instanceof Long) {
            this.zzd = (Long) obj;
            this.zzg = null;
            this.zze = null;
        } else if (obj instanceof String) {
            this.zzd = null;
            this.zzg = null;
            this.zze = (String) obj;
        } else if (obj instanceof Double) {
            this.zzd = null;
            this.zzg = (Double) obj;
            this.zze = null;
        } else {
            throw new IllegalArgumentException("User attribute given of un-supported type");
        }
    }
}
