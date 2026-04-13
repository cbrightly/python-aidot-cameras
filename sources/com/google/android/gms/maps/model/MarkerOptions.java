package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.IObjectWrapper;

@SafeParcelable.Class(creator = "MarkerOptionsCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class MarkerOptions extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<MarkerOptions> CREATOR = new zzi();
    @SafeParcelable.Field(getter = "getPosition", id = 2)
    private LatLng zza;
    @SafeParcelable.Field(getter = "getTitle", id = 3)
    @Nullable
    private String zzb;
    @SafeParcelable.Field(getter = "getSnippet", id = 4)
    @Nullable
    private String zzc;
    @SafeParcelable.Field(getter = "getWrappedIconDescriptorImplBinder", id = 5, type = "android.os.IBinder")
    @Nullable
    private BitmapDescriptor zzd;
    @SafeParcelable.Field(getter = "getAnchorU", id = 6)
    private float zze = 0.5f;
    @SafeParcelable.Field(getter = "getAnchorV", id = 7)
    private float zzf = 1.0f;
    @SafeParcelable.Field(getter = "isDraggable", id = 8)
    private boolean zzg;
    @SafeParcelable.Field(getter = "isVisible", id = 9)
    private boolean zzh = true;
    @SafeParcelable.Field(getter = "isFlat", id = 10)
    private boolean zzi = false;
    @SafeParcelable.Field(getter = "getRotation", id = 11)
    private float zzj = 0.0f;
    @SafeParcelable.Field(defaultValue = "0.5f", getter = "getInfoWindowAnchorU", id = 12)
    private float zzk = 0.5f;
    @SafeParcelable.Field(getter = "getInfoWindowAnchorV", id = 13)
    private float zzl = 0.0f;
    @SafeParcelable.Field(defaultValue = "1.0f", getter = "getAlpha", id = 14)
    private float zzm = 1.0f;
    @SafeParcelable.Field(getter = "getZIndex", id = 15)
    private float zzn;

    public MarkerOptions() {
    }

    @NonNull
    public MarkerOptions alpha(float f) {
        this.zzm = f;
        return this;
    }

    @NonNull
    public MarkerOptions anchor(float f, float f2) {
        this.zze = f;
        this.zzf = f2;
        return this;
    }

    @NonNull
    public MarkerOptions draggable(boolean z) {
        this.zzg = z;
        return this;
    }

    @NonNull
    public MarkerOptions flat(boolean z) {
        this.zzi = z;
        return this;
    }

    public float getAlpha() {
        return this.zzm;
    }

    public float getAnchorU() {
        return this.zze;
    }

    public float getAnchorV() {
        return this.zzf;
    }

    @Nullable
    public BitmapDescriptor getIcon() {
        return this.zzd;
    }

    public float getInfoWindowAnchorU() {
        return this.zzk;
    }

    public float getInfoWindowAnchorV() {
        return this.zzl;
    }

    @NonNull
    public LatLng getPosition() {
        return this.zza;
    }

    public float getRotation() {
        return this.zzj;
    }

    @Nullable
    public String getSnippet() {
        return this.zzc;
    }

    @Nullable
    public String getTitle() {
        return this.zzb;
    }

    public float getZIndex() {
        return this.zzn;
    }

    @NonNull
    public MarkerOptions icon(@Nullable BitmapDescriptor bitmapDescriptor) {
        this.zzd = bitmapDescriptor;
        return this;
    }

    @NonNull
    public MarkerOptions infoWindowAnchor(float f, float f2) {
        this.zzk = f;
        this.zzl = f2;
        return this;
    }

    public boolean isDraggable() {
        return this.zzg;
    }

    public boolean isFlat() {
        return this.zzi;
    }

    public boolean isVisible() {
        return this.zzh;
    }

    @NonNull
    public MarkerOptions position(@NonNull LatLng latlng) {
        if (latlng != null) {
            this.zza = latlng;
            return this;
        }
        throw new IllegalArgumentException("latlng cannot be null - a position is required.");
    }

    @NonNull
    public MarkerOptions rotation(float f) {
        this.zzj = f;
        return this;
    }

    @NonNull
    public MarkerOptions snippet(@Nullable String str) {
        this.zzc = str;
        return this;
    }

    @NonNull
    public MarkerOptions title(@Nullable String str) {
        this.zzb = str;
        return this;
    }

    @NonNull
    public MarkerOptions visible(boolean z) {
        this.zzh = z;
        return this;
    }

    public void writeToParcel(@NonNull Parcel out, int flags) {
        IBinder iBinder;
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeParcelable(out, 2, getPosition(), flags, false);
        SafeParcelWriter.writeString(out, 3, getTitle(), false);
        SafeParcelWriter.writeString(out, 4, getSnippet(), false);
        BitmapDescriptor bitmapDescriptor = this.zzd;
        if (bitmapDescriptor == null) {
            iBinder = null;
        } else {
            iBinder = bitmapDescriptor.zza().asBinder();
        }
        SafeParcelWriter.writeIBinder(out, 5, iBinder, false);
        SafeParcelWriter.writeFloat(out, 6, getAnchorU());
        SafeParcelWriter.writeFloat(out, 7, getAnchorV());
        SafeParcelWriter.writeBoolean(out, 8, isDraggable());
        SafeParcelWriter.writeBoolean(out, 9, isVisible());
        SafeParcelWriter.writeBoolean(out, 10, isFlat());
        SafeParcelWriter.writeFloat(out, 11, getRotation());
        SafeParcelWriter.writeFloat(out, 12, getInfoWindowAnchorU());
        SafeParcelWriter.writeFloat(out, 13, getInfoWindowAnchorV());
        SafeParcelWriter.writeFloat(out, 14, getAlpha());
        SafeParcelWriter.writeFloat(out, 15, getZIndex());
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }

    @NonNull
    public MarkerOptions zIndex(float f) {
        this.zzn = f;
        return this;
    }

    @SafeParcelable.Constructor
    MarkerOptions(@SafeParcelable.Param(id = 2) LatLng latLng, @SafeParcelable.Param(id = 3) String str, @SafeParcelable.Param(id = 4) String str2, @SafeParcelable.Param(id = 5) @Nullable IBinder iBinder, @SafeParcelable.Param(id = 6) float f, @SafeParcelable.Param(id = 7) float f2, @SafeParcelable.Param(id = 8) boolean z, @SafeParcelable.Param(id = 9) boolean z2, @SafeParcelable.Param(id = 10) boolean z3, @SafeParcelable.Param(id = 11) float f3, @SafeParcelable.Param(id = 12) float f4, @SafeParcelable.Param(id = 13) float f5, @SafeParcelable.Param(id = 14) float f6, @SafeParcelable.Param(id = 15) float f7) {
        this.zza = latLng;
        this.zzb = str;
        this.zzc = str2;
        if (iBinder == null) {
            this.zzd = null;
        } else {
            this.zzd = new BitmapDescriptor(IObjectWrapper.Stub.asInterface(iBinder));
        }
        this.zze = f;
        this.zzf = f2;
        this.zzg = z;
        this.zzh = z2;
        this.zzi = z3;
        this.zzj = f3;
        this.zzk = f4;
        this.zzl = f5;
        this.zzm = f6;
        this.zzn = f7;
    }
}
