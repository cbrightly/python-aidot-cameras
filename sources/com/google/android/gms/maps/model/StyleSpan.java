package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "StyleSpanCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class StyleSpan extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<StyleSpan> CREATOR = new zzw();
    @SafeParcelable.Field(getter = "getStyle", id = 2)
    private final StrokeStyle zza;
    @SafeParcelable.Field(getter = "getSegments", id = 3)
    private final double zzb;

    public StyleSpan(int color) {
        this.zza = StrokeStyle.colorBuilder(color).build();
        this.zzb = 1.0d;
    }

    public double getSegments() {
        return this.zzb;
    }

    @NonNull
    public StrokeStyle getStyle() {
        return this.zza;
    }

    public final void writeToParcel(@NonNull Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, getStyle(), i, false);
        SafeParcelWriter.writeDouble(parcel, 3, getSegments());
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public StyleSpan(int color, double segments) {
        if (segments > 0.0d) {
            this.zza = StrokeStyle.colorBuilder(color).build();
            this.zzb = segments;
            return;
        }
        throw new IllegalArgumentException("A style must be applied to some segments on a polyline.");
    }

    public StyleSpan(@NonNull StrokeStyle style) {
        this.zza = style;
        this.zzb = 1.0d;
    }

    @SafeParcelable.Constructor
    public StyleSpan(@SafeParcelable.Param(id = 2) @NonNull StrokeStyle style, @SafeParcelable.Param(id = 3) double segments) {
        if (segments > 0.0d) {
            this.zza = style;
            this.zzb = segments;
            return;
        }
        throw new IllegalArgumentException("A style must be applied to some segments on a polyline.");
    }
}
