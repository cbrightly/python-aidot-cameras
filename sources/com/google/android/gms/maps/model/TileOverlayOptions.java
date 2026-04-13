package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.maps.zzal;
import com.google.android.gms.internal.maps.zzam;

@SafeParcelable.Class(creator = "TileOverlayOptionsCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class TileOverlayOptions extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<TileOverlayOptions> CREATOR = new zzac();
    /* access modifiers changed from: private */
    @SafeParcelable.Field(getter = "getTileProviderDelegate", id = 2, type = "android.os.IBinder")
    @Nullable
    public zzam zza;
    @Nullable
    private TileProvider zzb;
    @SafeParcelable.Field(getter = "isVisible", id = 3)
    private boolean zzc = true;
    @SafeParcelable.Field(getter = "getZIndex", id = 4)
    private float zzd;
    @SafeParcelable.Field(defaultValue = "true", getter = "getFadeIn", id = 5)
    private boolean zze = true;
    @SafeParcelable.Field(getter = "getTransparency", id = 6)
    private float zzf = 0.0f;

    public TileOverlayOptions() {
    }

    @NonNull
    public TileOverlayOptions fadeIn(boolean z) {
        this.zze = z;
        return this;
    }

    public boolean getFadeIn() {
        return this.zze;
    }

    @Nullable
    public TileProvider getTileProvider() {
        return this.zzb;
    }

    public float getTransparency() {
        return this.zzf;
    }

    public float getZIndex() {
        return this.zzd;
    }

    public boolean isVisible() {
        return this.zzc;
    }

    @NonNull
    public TileOverlayOptions tileProvider(@NonNull TileProvider tileProvider) {
        this.zzb = (TileProvider) Preconditions.checkNotNull(tileProvider, "tileProvider must not be null.");
        this.zza = new zzab(this, tileProvider);
        return this;
    }

    @NonNull
    public TileOverlayOptions transparency(float transparency) {
        boolean z = false;
        if (transparency >= 0.0f && transparency <= 1.0f) {
            z = true;
        }
        Preconditions.checkArgument(z, "Transparency must be in the range [0..1]");
        this.zzf = transparency;
        return this;
    }

    @NonNull
    public TileOverlayOptions visible(boolean z) {
        this.zzc = z;
        return this;
    }

    public void writeToParcel(@NonNull Parcel out, int i) {
        IBinder iBinder;
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        zzam zzam = this.zza;
        if (zzam == null) {
            iBinder = null;
        } else {
            iBinder = zzam.asBinder();
        }
        SafeParcelWriter.writeIBinder(out, 2, iBinder, false);
        SafeParcelWriter.writeBoolean(out, 3, isVisible());
        SafeParcelWriter.writeFloat(out, 4, getZIndex());
        SafeParcelWriter.writeBoolean(out, 5, getFadeIn());
        SafeParcelWriter.writeFloat(out, 6, getTransparency());
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }

    @NonNull
    public TileOverlayOptions zIndex(float f) {
        this.zzd = f;
        return this;
    }

    @SafeParcelable.Constructor
    TileOverlayOptions(@SafeParcelable.Param(id = 2) IBinder iBinder, @SafeParcelable.Param(id = 3) boolean z, @SafeParcelable.Param(id = 4) float f, @SafeParcelable.Param(id = 5) boolean z2, @SafeParcelable.Param(id = 6) float f2) {
        zzaa zzaa;
        zzam zzc2 = zzal.zzc(iBinder);
        this.zza = zzc2;
        if (zzc2 == null) {
            zzaa = null;
        } else {
            zzaa = new zzaa(this);
        }
        this.zzb = zzaa;
        this.zzc = z;
        this.zzd = f;
        this.zze = z2;
        this.zzf = f2;
    }
}
