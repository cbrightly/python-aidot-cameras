package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "StreetViewSourceCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class StreetViewSource extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<StreetViewSource> CREATOR = new zzt();
    @NonNull
    public static final StreetViewSource DEFAULT = new StreetViewSource(0);
    @NonNull
    public static final StreetViewSource OUTDOOR = new StreetViewSource(1);
    private static final String zza = StreetViewSource.class.getSimpleName();
    @SafeParcelable.Field(getter = "getType", id = 2)
    private final int zzb;

    @SafeParcelable.Constructor
    public StreetViewSource(@SafeParcelable.Param(id = 2) int i) {
        this.zzb = i;
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        return (o instanceof StreetViewSource) && this.zzb == ((StreetViewSource) o).zzb;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzb));
    }

    @NonNull
    public String toString() {
        String str;
        int i = this.zzb;
        switch (i) {
            case 0:
                str = "DEFAULT";
                break;
            case 1:
                str = "OUTDOOR";
                break;
            default:
                str = String.format("UNKNOWN(%s)", new Object[]{Integer.valueOf(i)});
                break;
        }
        return String.format("StreetViewSource:%s", new Object[]{str});
    }

    public void writeToParcel(@NonNull Parcel out, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeInt(out, 2, this.zzb);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }
}
