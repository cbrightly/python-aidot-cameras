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
import com.google.android.gms.dynamic.IObjectWrapper;

@SafeParcelable.Class(creator = "GroundOverlayOptionsCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class GroundOverlayOptions extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<GroundOverlayOptions> CREATOR = new zzd();
    public static final float NO_DIMENSION = -1.0f;
    @SafeParcelable.Field(getter = "getWrappedImageDescriptorImplBinder", id = 2, type = "android.os.IBinder")
    private BitmapDescriptor zza;
    @SafeParcelable.Field(getter = "getLocation", id = 3)
    @Nullable
    private LatLng zzb;
    @SafeParcelable.Field(getter = "getWidth", id = 4)
    private float zzc;
    @SafeParcelable.Field(getter = "getHeight", id = 5)
    private float zzd;
    @SafeParcelable.Field(getter = "getBounds", id = 6)
    @Nullable
    private LatLngBounds zze;
    @SafeParcelable.Field(getter = "getBearing", id = 7)
    private float zzf;
    @SafeParcelable.Field(getter = "getZIndex", id = 8)
    private float zzg;
    @SafeParcelable.Field(getter = "isVisible", id = 9)
    private boolean zzh = true;
    @SafeParcelable.Field(getter = "getTransparency", id = 10)
    private float zzi = 0.0f;
    @SafeParcelable.Field(getter = "getAnchorU", id = 11)
    private float zzj = 0.5f;
    @SafeParcelable.Field(getter = "getAnchorV", id = 12)
    private float zzk = 0.5f;
    @SafeParcelable.Field(getter = "isClickable", id = 13)
    private boolean zzl = false;

    public GroundOverlayOptions() {
    }

    private final GroundOverlayOptions zza(LatLng latLng, float f, float f2) {
        this.zzb = latLng;
        this.zzc = f;
        this.zzd = f2;
        return this;
    }

    @NonNull
    public GroundOverlayOptions anchor(float f, float f2) {
        this.zzj = f;
        this.zzk = f2;
        return this;
    }

    @NonNull
    public GroundOverlayOptions bearing(float f) {
        this.zzf = ((f % 360.0f) + 360.0f) % 360.0f;
        return this;
    }

    @NonNull
    public GroundOverlayOptions clickable(boolean z) {
        this.zzl = z;
        return this;
    }

    public float getAnchorU() {
        return this.zzj;
    }

    public float getAnchorV() {
        return this.zzk;
    }

    public float getBearing() {
        return this.zzf;
    }

    @Nullable
    public LatLngBounds getBounds() {
        return this.zze;
    }

    public float getHeight() {
        return this.zzd;
    }

    @NonNull
    public BitmapDescriptor getImage() {
        return this.zza;
    }

    @Nullable
    public LatLng getLocation() {
        return this.zzb;
    }

    public float getTransparency() {
        return this.zzi;
    }

    public float getWidth() {
        return this.zzc;
    }

    public float getZIndex() {
        return this.zzg;
    }

    @NonNull
    public GroundOverlayOptions image(@NonNull BitmapDescriptor imageDescriptor) {
        Preconditions.checkNotNull(imageDescriptor, "imageDescriptor must not be null");
        this.zza = imageDescriptor;
        return this;
    }

    public boolean isClickable() {
        return this.zzl;
    }

    public boolean isVisible() {
        return this.zzh;
    }

    @NonNull
    public GroundOverlayOptions position(@NonNull LatLng location, float width) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        if (this.zze == null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z, "Position has already been set using positionFromBounds");
        if (location != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "Location must be specified");
        if (width < 0.0f) {
            z3 = false;
        }
        Preconditions.checkArgument(z3, "Width must be non-negative");
        zza(location, width, -1.0f);
        return this;
    }

    @NonNull
    public GroundOverlayOptions positionFromBounds(@NonNull LatLngBounds bounds) {
        LatLng latLng = this.zzb;
        Preconditions.checkState(latLng == null, "Position has already been set using position: ".concat(String.valueOf(String.valueOf(latLng))));
        this.zze = bounds;
        return this;
    }

    @NonNull
    public GroundOverlayOptions transparency(float transparency) {
        boolean z = false;
        if (transparency >= 0.0f && transparency <= 1.0f) {
            z = true;
        }
        Preconditions.checkArgument(z, "Transparency must be in the range [0..1]");
        this.zzi = transparency;
        return this;
    }

    @NonNull
    public GroundOverlayOptions visible(boolean z) {
        this.zzh = z;
        return this;
    }

    public void writeToParcel(@NonNull Parcel out, int flags) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeIBinder(out, 2, this.zza.zza().asBinder(), false);
        SafeParcelWriter.writeParcelable(out, 3, getLocation(), flags, false);
        SafeParcelWriter.writeFloat(out, 4, getWidth());
        SafeParcelWriter.writeFloat(out, 5, getHeight());
        SafeParcelWriter.writeParcelable(out, 6, getBounds(), flags, false);
        SafeParcelWriter.writeFloat(out, 7, getBearing());
        SafeParcelWriter.writeFloat(out, 8, getZIndex());
        SafeParcelWriter.writeBoolean(out, 9, isVisible());
        SafeParcelWriter.writeFloat(out, 10, getTransparency());
        SafeParcelWriter.writeFloat(out, 11, getAnchorU());
        SafeParcelWriter.writeFloat(out, 12, getAnchorV());
        SafeParcelWriter.writeBoolean(out, 13, isClickable());
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }

    @NonNull
    public GroundOverlayOptions zIndex(float f) {
        this.zzg = f;
        return this;
    }

    @SafeParcelable.Constructor
    GroundOverlayOptions(@SafeParcelable.Param(id = 2) IBinder iBinder, @SafeParcelable.Param(id = 3) LatLng latLng, @SafeParcelable.Param(id = 4) float f, @SafeParcelable.Param(id = 5) float f2, @SafeParcelable.Param(id = 6) LatLngBounds latLngBounds, @SafeParcelable.Param(id = 7) float f3, @SafeParcelable.Param(id = 8) float f4, @SafeParcelable.Param(id = 9) boolean z, @SafeParcelable.Param(id = 10) float f5, @SafeParcelable.Param(id = 11) float f6, @SafeParcelable.Param(id = 12) float f7, @SafeParcelable.Param(id = 13) boolean z2) {
        this.zza = new BitmapDescriptor(IObjectWrapper.Stub.asInterface(iBinder));
        this.zzb = latLng;
        this.zzc = f;
        this.zzd = f2;
        this.zze = latLngBounds;
        this.zzf = f3;
        this.zzg = f4;
        this.zzh = z;
        this.zzi = f5;
        this.zzj = f6;
        this.zzk = f7;
        this.zzl = z2;
    }

    @NonNull
    public GroundOverlayOptions position(@NonNull LatLng location, float width, float height) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = true;
        if (this.zze == null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z, "Position has already been set using positionFromBounds");
        if (location != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "Location must be specified");
        if (width >= 0.0f) {
            z3 = true;
        } else {
            z3 = false;
        }
        Preconditions.checkArgument(z3, "Width must be non-negative");
        if (height < 0.0f) {
            z4 = false;
        }
        Preconditions.checkArgument(z4, "Height must be non-negative");
        zza(location, width, height);
        return this;
    }
}
