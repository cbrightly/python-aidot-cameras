package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SafeParcelable.Class(creator = "PatternItemCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public class PatternItem extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<PatternItem> CREATOR = new zzj();
    private static final String zza = PatternItem.class.getSimpleName();
    @SafeParcelable.Field(getter = "getType", id = 2)
    private final int zzb;
    @SafeParcelable.Field(getter = "getLength", id = 3)
    @Nullable
    private final Float zzc;

    @SafeParcelable.Constructor
    public PatternItem(@SafeParcelable.Param(id = 2) int i, @SafeParcelable.Param(id = 3) @Nullable Float f) {
        boolean z = false;
        if (i == 1) {
            z = true;
        } else if (f != null && f.floatValue() >= 0.0f) {
            z = true;
        }
        Preconditions.checkArgument(z, "Invalid PatternItem: type=" + i + " length=" + f);
        this.zzb = i;
        this.zzc = f;
    }

    @Nullable
    static List zza(@Nullable List list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PatternItem patternItem = (PatternItem) it.next();
            if (patternItem != null) {
                int i = patternItem.zzb;
                boolean z = true;
                switch (i) {
                    case 0:
                        if (patternItem.zzc == null) {
                            z = false;
                        }
                        Preconditions.checkState(z, "length must not be null.");
                        patternItem = new Dash(patternItem.zzc.floatValue());
                        break;
                    case 1:
                        patternItem = new Dot();
                        break;
                    case 2:
                        if (patternItem.zzc == null) {
                            z = false;
                        }
                        Preconditions.checkState(z, "length must not be null.");
                        patternItem = new Gap(patternItem.zzc.floatValue());
                        break;
                    default:
                        Log.w(zza, "Unknown PatternItem type: " + i);
                        break;
                }
            } else {
                patternItem = null;
            }
            arrayList.add(patternItem);
        }
        return arrayList;
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatternItem)) {
            return false;
        }
        PatternItem patternItem = (PatternItem) o;
        return this.zzb == patternItem.zzb && Objects.equal(this.zzc, patternItem.zzc);
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzb), this.zzc);
    }

    @NonNull
    public String toString() {
        int i = this.zzb;
        Float f = this.zzc;
        return "[PatternItem: type=" + i + " length=" + f + "]";
    }

    public void writeToParcel(@NonNull Parcel out, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeInt(out, 2, this.zzb);
        SafeParcelWriter.writeFloatObject(out, 3, this.zzc, false);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }
}
