package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "StrokeStyleCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class StrokeStyle extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<StrokeStyle> CREATOR = new zzv();
    @SafeParcelable.Field(getter = "getWidth", id = 2)
    private final float zza;
    @SafeParcelable.Field(getter = "getColor", id = 3)
    private final int zzb;
    @SafeParcelable.Field(getter = "getToColor", id = 4)
    private final int zzc;
    @SafeParcelable.Field(getter = "isVisible", id = 5)
    private final boolean zzd;
    @SafeParcelable.Field(getter = "getStamp", id = 6)
    @Nullable
    private final StampStyle zze;

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public static final class Builder {
        private float zza;
        private int zzb;
        private int zzc;
        private boolean zzd;
        @Nullable
        private StampStyle zze;

        private Builder() {
        }

        public Builder(@NonNull StrokeStyle strokeStyle) {
            this.zza = strokeStyle.zza();
            Pair zzb2 = strokeStyle.zzb();
            this.zzb = ((Integer) zzb2.first).intValue();
            this.zzc = ((Integer) zzb2.second).intValue();
            this.zzd = strokeStyle.isVisible();
            this.zze = strokeStyle.getStamp();
        }

        /* synthetic */ Builder(zzu zzu) {
        }

        @NonNull
        public StrokeStyle build() {
            return new StrokeStyle(this.zza, this.zzb, this.zzc, this.zzd, this.zze);
        }

        @NonNull
        public Builder stamp(@NonNull StampStyle stampStyle) {
            this.zze = stampStyle;
            return this;
        }

        @NonNull
        public final Builder zza(int i) {
            this.zzb = i;
            this.zzc = i;
            return this;
        }

        @NonNull
        public final Builder zzb(int i, int i2) {
            this.zzb = i;
            this.zzc = i2;
            return this;
        }

        @NonNull
        public final Builder zzc(boolean z) {
            this.zzd = z;
            return this;
        }

        @NonNull
        public final Builder zzd(float f) {
            this.zza = f;
            return this;
        }
    }

    @SafeParcelable.Constructor
    StrokeStyle(@SafeParcelable.Param(id = 2) float f, @SafeParcelable.Param(id = 3) int i, @SafeParcelable.Param(id = 4) int i2, @SafeParcelable.Param(id = 5) boolean z, @SafeParcelable.Param(id = 6) @Nullable StampStyle stampStyle) {
        this.zza = f;
        this.zzb = i;
        this.zzc = i2;
        this.zzd = z;
        this.zze = stampStyle;
    }

    @NonNull
    public static Builder colorBuilder(int color) {
        Builder builder = new Builder((zzu) null);
        builder.zza(color);
        return builder;
    }

    @NonNull
    public static Builder gradientBuilder(int fromColor, int toColor) {
        Builder builder = new Builder((zzu) null);
        builder.zzb(fromColor, toColor);
        return builder;
    }

    @NonNull
    public static Builder transparentColorBuilder() {
        Builder builder = new Builder((zzu) null);
        builder.zza(0);
        return builder;
    }

    @Nullable
    public StampStyle getStamp() {
        return this.zze;
    }

    public boolean isVisible() {
        return this.zzd;
    }

    public final void writeToParcel(@NonNull Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeFloat(parcel, 2, this.zza);
        SafeParcelWriter.writeInt(parcel, 3, this.zzb);
        SafeParcelWriter.writeInt(parcel, 4, this.zzc);
        SafeParcelWriter.writeBoolean(parcel, 5, isVisible());
        SafeParcelWriter.writeParcelable(parcel, 6, getStamp(), i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final float zza() {
        return this.zza;
    }

    @NonNull
    public final Pair zzb() {
        return new Pair(Integer.valueOf(this.zzb), Integer.valueOf(this.zzc));
    }
}
