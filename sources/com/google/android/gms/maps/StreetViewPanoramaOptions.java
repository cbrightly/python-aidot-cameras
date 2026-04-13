package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.zza;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewSource;

@SafeParcelable.Class(creator = "StreetViewPanoramaOptionsCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class StreetViewPanoramaOptions extends AbstractSafeParcelable implements ReflectedParcelable {
    @NonNull
    public static final Parcelable.Creator<StreetViewPanoramaOptions> CREATOR = new zzap();
    @SafeParcelable.Field(getter = "getStreetViewPanoramaCamera", id = 2)
    @Nullable
    private StreetViewPanoramaCamera zza;
    @SafeParcelable.Field(getter = "getPanoramaId", id = 3)
    @Nullable
    private String zzb;
    @SafeParcelable.Field(getter = "getPosition", id = 4)
    @Nullable
    private LatLng zzc;
    @SafeParcelable.Field(getter = "getRadius", id = 5)
    @Nullable
    private Integer zzd;
    @SafeParcelable.Field(getter = "getUserNavigationEnabledForParcel", id = 6, type = "byte")
    @Nullable
    private Boolean zze = true;
    @SafeParcelable.Field(getter = "getZoomGesturesEnabledForParcel", id = 7, type = "byte")
    @Nullable
    private Boolean zzf = true;
    @SafeParcelable.Field(getter = "getPanningGesturesEnabledForParcel", id = 8, type = "byte")
    @Nullable
    private Boolean zzg = true;
    @SafeParcelable.Field(getter = "getStreetNamesEnabledForParcel", id = 9, type = "byte")
    @Nullable
    private Boolean zzh = true;
    @SafeParcelable.Field(getter = "getUseViewLifecycleInFragmentForParcel", id = 10, type = "byte")
    @Nullable
    private Boolean zzi;
    @SafeParcelable.Field(getter = "getSource", id = 11)
    private StreetViewSource zzj = StreetViewSource.DEFAULT;

    public StreetViewPanoramaOptions() {
    }

    @Nullable
    public Boolean getPanningGesturesEnabled() {
        return this.zzg;
    }

    @Nullable
    public String getPanoramaId() {
        return this.zzb;
    }

    @Nullable
    public LatLng getPosition() {
        return this.zzc;
    }

    @Nullable
    public Integer getRadius() {
        return this.zzd;
    }

    @NonNull
    public StreetViewSource getSource() {
        return this.zzj;
    }

    @Nullable
    public Boolean getStreetNamesEnabled() {
        return this.zzh;
    }

    @Nullable
    public StreetViewPanoramaCamera getStreetViewPanoramaCamera() {
        return this.zza;
    }

    @Nullable
    public Boolean getUseViewLifecycleInFragment() {
        return this.zzi;
    }

    @Nullable
    public Boolean getUserNavigationEnabled() {
        return this.zze;
    }

    @Nullable
    public Boolean getZoomGesturesEnabled() {
        return this.zzf;
    }

    @NonNull
    public StreetViewPanoramaOptions panningGesturesEnabled(boolean enabled) {
        this.zzg = Boolean.valueOf(enabled);
        return this;
    }

    @NonNull
    public StreetViewPanoramaOptions panoramaCamera(@Nullable StreetViewPanoramaCamera streetViewPanoramaCamera) {
        this.zza = streetViewPanoramaCamera;
        return this;
    }

    @NonNull
    public StreetViewPanoramaOptions panoramaId(@Nullable String str) {
        this.zzb = str;
        return this;
    }

    @NonNull
    public StreetViewPanoramaOptions position(@Nullable LatLng latLng) {
        this.zzc = latLng;
        return this;
    }

    @NonNull
    public StreetViewPanoramaOptions position(@Nullable LatLng latLng, @NonNull StreetViewSource streetViewSource) {
        this.zzc = latLng;
        this.zzj = streetViewSource;
        return this;
    }

    @NonNull
    public StreetViewPanoramaOptions position(@Nullable LatLng latLng, @Nullable Integer num) {
        this.zzc = latLng;
        this.zzd = num;
        return this;
    }

    @NonNull
    public StreetViewPanoramaOptions position(@Nullable LatLng latLng, @Nullable Integer num, @NonNull StreetViewSource streetViewSource) {
        this.zzc = latLng;
        this.zzd = num;
        this.zzj = streetViewSource;
        return this;
    }

    @NonNull
    public StreetViewPanoramaOptions streetNamesEnabled(boolean enabled) {
        this.zzh = Boolean.valueOf(enabled);
        return this;
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add("PanoramaId", this.zzb).add("Position", this.zzc).add("Radius", this.zzd).add("Source", this.zzj).add("StreetViewPanoramaCamera", this.zza).add("UserNavigationEnabled", this.zze).add("ZoomGesturesEnabled", this.zzf).add("PanningGesturesEnabled", this.zzg).add("StreetNamesEnabled", this.zzh).add("UseViewLifecycleInFragment", this.zzi).toString();
    }

    @NonNull
    public StreetViewPanoramaOptions useViewLifecycleInFragment(boolean useViewLifecycleInFragment) {
        this.zzi = Boolean.valueOf(useViewLifecycleInFragment);
        return this;
    }

    @NonNull
    public StreetViewPanoramaOptions userNavigationEnabled(boolean enabled) {
        this.zze = Boolean.valueOf(enabled);
        return this;
    }

    public void writeToParcel(@NonNull Parcel out, int flags) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeParcelable(out, 2, getStreetViewPanoramaCamera(), flags, false);
        SafeParcelWriter.writeString(out, 3, getPanoramaId(), false);
        SafeParcelWriter.writeParcelable(out, 4, getPosition(), flags, false);
        SafeParcelWriter.writeIntegerObject(out, 5, getRadius(), false);
        SafeParcelWriter.writeByte(out, 6, zza.zza(this.zze));
        SafeParcelWriter.writeByte(out, 7, zza.zza(this.zzf));
        SafeParcelWriter.writeByte(out, 8, zza.zza(this.zzg));
        SafeParcelWriter.writeByte(out, 9, zza.zza(this.zzh));
        SafeParcelWriter.writeByte(out, 10, zza.zza(this.zzi));
        SafeParcelWriter.writeParcelable(out, 11, getSource(), flags, false);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }

    @NonNull
    public StreetViewPanoramaOptions zoomGesturesEnabled(boolean enabled) {
        this.zzf = Boolean.valueOf(enabled);
        return this;
    }

    @SafeParcelable.Constructor
    StreetViewPanoramaOptions(@SafeParcelable.Param(id = 2) @Nullable StreetViewPanoramaCamera streetViewPanoramaCamera, @SafeParcelable.Param(id = 3) @Nullable String str, @SafeParcelable.Param(id = 4) @Nullable LatLng latLng, @SafeParcelable.Param(id = 5) @Nullable Integer num, @SafeParcelable.Param(id = 6) byte b, @SafeParcelable.Param(id = 7) byte b2, @SafeParcelable.Param(id = 8) byte b3, @SafeParcelable.Param(id = 9) byte b4, @SafeParcelable.Param(id = 10) byte b5, @SafeParcelable.Param(id = 11) StreetViewSource streetViewSource) {
        this.zza = streetViewPanoramaCamera;
        this.zzc = latLng;
        this.zzd = num;
        this.zzb = str;
        this.zze = zza.zzb(b);
        this.zzf = zza.zzb(b2);
        this.zzg = zza.zzb(b3);
        this.zzh = zza.zzb(b4);
        this.zzi = zza.zzb(b5);
        this.zzj = streetViewSource;
    }
}
