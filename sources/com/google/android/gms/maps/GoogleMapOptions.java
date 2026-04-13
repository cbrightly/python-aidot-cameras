package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.zza;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

@SafeParcelable.Class(creator = "GoogleMapOptionsCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class GoogleMapOptions extends AbstractSafeParcelable implements ReflectedParcelable {
    @NonNull
    public static final Parcelable.Creator<GoogleMapOptions> CREATOR = new zzab();
    private static final Integer zza = Integer.valueOf(Color.argb(255, 236, 233, 225));
    @SafeParcelable.Field(defaultValue = "-1", getter = "getZOrderOnTopForParcel", id = 2, type = "byte")
    @Nullable
    private Boolean zzb;
    @SafeParcelable.Field(defaultValue = "-1", getter = "getUseViewLifecycleInFragmentForParcel", id = 3, type = "byte")
    @Nullable
    private Boolean zzc;
    @SafeParcelable.Field(getter = "getMapType", id = 4)
    private int zzd = -1;
    @SafeParcelable.Field(getter = "getCamera", id = 5)
    @Nullable
    private CameraPosition zze;
    @SafeParcelable.Field(defaultValue = "-1", getter = "getZoomControlsEnabledForParcel", id = 6, type = "byte")
    @Nullable
    private Boolean zzf;
    @SafeParcelable.Field(defaultValue = "-1", getter = "getCompassEnabledForParcel", id = 7, type = "byte")
    @Nullable
    private Boolean zzg;
    @SafeParcelable.Field(defaultValue = "-1", getter = "getScrollGesturesEnabledForParcel", id = 8, type = "byte")
    @Nullable
    private Boolean zzh;
    @SafeParcelable.Field(defaultValue = "-1", getter = "getZoomGesturesEnabledForParcel", id = 9, type = "byte")
    @Nullable
    private Boolean zzi;
    @SafeParcelable.Field(defaultValue = "-1", getter = "getTiltGesturesEnabledForParcel", id = 10, type = "byte")
    @Nullable
    private Boolean zzj;
    @SafeParcelable.Field(defaultValue = "-1", getter = "getRotateGesturesEnabledForParcel", id = 11, type = "byte")
    @Nullable
    private Boolean zzk;
    @SafeParcelable.Field(defaultValue = "-1", getter = "getLiteModeForParcel", id = 12, type = "byte")
    @Nullable
    private Boolean zzl;
    @SafeParcelable.Field(defaultValue = "-1", getter = "getMapToolbarEnabledForParcel", id = 14, type = "byte")
    @Nullable
    private Boolean zzm;
    @SafeParcelable.Field(defaultValue = "-1", getter = "getAmbientEnabledForParcel", id = 15, type = "byte")
    @Nullable
    private Boolean zzn;
    @SafeParcelable.Field(getter = "getMinZoomPreference", id = 16)
    @Nullable
    private Float zzo = null;
    @SafeParcelable.Field(getter = "getMaxZoomPreference", id = 17)
    @Nullable
    private Float zzp = null;
    @SafeParcelable.Field(getter = "getLatLngBoundsForCameraTarget", id = 18)
    @Nullable
    private LatLngBounds zzq = null;
    @SafeParcelable.Field(defaultValue = "-1", getter = "getScrollGesturesEnabledDuringRotateOrZoomForParcel", id = 19, type = "byte")
    @Nullable
    private Boolean zzr;
    @SafeParcelable.Field(getter = "getBackgroundColor", id = 20)
    @ColorInt
    @Nullable
    private Integer zzs = null;
    @SafeParcelable.Field(getter = "getMapId", id = 21)
    @Nullable
    private String zzt = null;

    public GoogleMapOptions() {
    }

    @Nullable
    public static GoogleMapOptions createFromAttributes(@Nullable Context context, @Nullable AttributeSet attrs) {
        String string;
        if (context == null || attrs == null) {
            return null;
        }
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attrs, R.styleable.MapAttrs);
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        int i = R.styleable.MapAttrs_mapType;
        if (obtainAttributes.hasValue(i)) {
            googleMapOptions.mapType(obtainAttributes.getInt(i, -1));
        }
        int i2 = R.styleable.MapAttrs_zOrderOnTop;
        if (obtainAttributes.hasValue(i2)) {
            googleMapOptions.zOrderOnTop(obtainAttributes.getBoolean(i2, false));
        }
        int i3 = R.styleable.MapAttrs_useViewLifecycle;
        if (obtainAttributes.hasValue(i3)) {
            googleMapOptions.useViewLifecycleInFragment(obtainAttributes.getBoolean(i3, false));
        }
        int i4 = R.styleable.MapAttrs_uiCompass;
        if (obtainAttributes.hasValue(i4)) {
            googleMapOptions.compassEnabled(obtainAttributes.getBoolean(i4, true));
        }
        int i5 = R.styleable.MapAttrs_uiRotateGestures;
        if (obtainAttributes.hasValue(i5)) {
            googleMapOptions.rotateGesturesEnabled(obtainAttributes.getBoolean(i5, true));
        }
        int i6 = R.styleable.MapAttrs_uiScrollGesturesDuringRotateOrZoom;
        if (obtainAttributes.hasValue(i6)) {
            googleMapOptions.scrollGesturesEnabledDuringRotateOrZoom(obtainAttributes.getBoolean(i6, true));
        }
        int i7 = R.styleable.MapAttrs_uiScrollGestures;
        if (obtainAttributes.hasValue(i7)) {
            googleMapOptions.scrollGesturesEnabled(obtainAttributes.getBoolean(i7, true));
        }
        int i8 = R.styleable.MapAttrs_uiTiltGestures;
        if (obtainAttributes.hasValue(i8)) {
            googleMapOptions.tiltGesturesEnabled(obtainAttributes.getBoolean(i8, true));
        }
        int i9 = R.styleable.MapAttrs_uiZoomGestures;
        if (obtainAttributes.hasValue(i9)) {
            googleMapOptions.zoomGesturesEnabled(obtainAttributes.getBoolean(i9, true));
        }
        int i10 = R.styleable.MapAttrs_uiZoomControls;
        if (obtainAttributes.hasValue(i10)) {
            googleMapOptions.zoomControlsEnabled(obtainAttributes.getBoolean(i10, true));
        }
        int i11 = R.styleable.MapAttrs_liteMode;
        if (obtainAttributes.hasValue(i11)) {
            googleMapOptions.liteMode(obtainAttributes.getBoolean(i11, false));
        }
        int i12 = R.styleable.MapAttrs_uiMapToolbar;
        if (obtainAttributes.hasValue(i12)) {
            googleMapOptions.mapToolbarEnabled(obtainAttributes.getBoolean(i12, true));
        }
        int i13 = R.styleable.MapAttrs_ambientEnabled;
        if (obtainAttributes.hasValue(i13)) {
            googleMapOptions.ambientEnabled(obtainAttributes.getBoolean(i13, false));
        }
        int i14 = R.styleable.MapAttrs_cameraMinZoomPreference;
        if (obtainAttributes.hasValue(i14)) {
            googleMapOptions.minZoomPreference(obtainAttributes.getFloat(i14, Float.NEGATIVE_INFINITY));
        }
        if (obtainAttributes.hasValue(i14)) {
            googleMapOptions.maxZoomPreference(obtainAttributes.getFloat(R.styleable.MapAttrs_cameraMaxZoomPreference, Float.POSITIVE_INFINITY));
        }
        int i15 = R.styleable.MapAttrs_backgroundColor;
        if (obtainAttributes.hasValue(i15)) {
            googleMapOptions.backgroundColor(Integer.valueOf(obtainAttributes.getColor(i15, zza.intValue())));
        }
        int i16 = R.styleable.MapAttrs_mapId;
        if (obtainAttributes.hasValue(i16) && (string = obtainAttributes.getString(i16)) != null && !string.isEmpty()) {
            googleMapOptions.mapId(string);
        }
        googleMapOptions.latLngBoundsForCameraTarget(zzb(context, attrs));
        googleMapOptions.camera(zza(context, attrs));
        obtainAttributes.recycle();
        return googleMapOptions;
    }

    @Nullable
    public static CameraPosition zza(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        float f;
        float f2;
        if (context == null || attributeSet == null) {
            return null;
        }
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attributeSet, R.styleable.MapAttrs);
        int i = R.styleable.MapAttrs_cameraTargetLat;
        if (obtainAttributes.hasValue(i)) {
            f = obtainAttributes.getFloat(i, 0.0f);
        } else {
            f = 0.0f;
        }
        int i2 = R.styleable.MapAttrs_cameraTargetLng;
        if (obtainAttributes.hasValue(i2)) {
            f2 = obtainAttributes.getFloat(i2, 0.0f);
        } else {
            f2 = 0.0f;
        }
        LatLng latLng = new LatLng((double) f, (double) f2);
        CameraPosition.Builder builder = CameraPosition.builder();
        builder.target(latLng);
        int i3 = R.styleable.MapAttrs_cameraZoom;
        if (obtainAttributes.hasValue(i3)) {
            builder.zoom(obtainAttributes.getFloat(i3, 0.0f));
        }
        int i4 = R.styleable.MapAttrs_cameraBearing;
        if (obtainAttributes.hasValue(i4)) {
            builder.bearing(obtainAttributes.getFloat(i4, 0.0f));
        }
        int i5 = R.styleable.MapAttrs_cameraTilt;
        if (obtainAttributes.hasValue(i5)) {
            builder.tilt(obtainAttributes.getFloat(i5, 0.0f));
        }
        obtainAttributes.recycle();
        return builder.build();
    }

    @Nullable
    public static LatLngBounds zzb(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        Float f;
        Float f2;
        Float f3;
        Float f4;
        if (context == null || attributeSet == null) {
            return null;
        }
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attributeSet, R.styleable.MapAttrs);
        int i = R.styleable.MapAttrs_latLngBoundsSouthWestLatitude;
        if (obtainAttributes.hasValue(i)) {
            f = Float.valueOf(obtainAttributes.getFloat(i, 0.0f));
        } else {
            f = null;
        }
        int i2 = R.styleable.MapAttrs_latLngBoundsSouthWestLongitude;
        if (obtainAttributes.hasValue(i2)) {
            f2 = Float.valueOf(obtainAttributes.getFloat(i2, 0.0f));
        } else {
            f2 = null;
        }
        int i3 = R.styleable.MapAttrs_latLngBoundsNorthEastLatitude;
        if (obtainAttributes.hasValue(i3)) {
            f3 = Float.valueOf(obtainAttributes.getFloat(i3, 0.0f));
        } else {
            f3 = null;
        }
        int i4 = R.styleable.MapAttrs_latLngBoundsNorthEastLongitude;
        if (obtainAttributes.hasValue(i4)) {
            f4 = Float.valueOf(obtainAttributes.getFloat(i4, 0.0f));
        } else {
            f4 = null;
        }
        obtainAttributes.recycle();
        if (f == null || f2 == null || f3 == null || f4 == null) {
            return null;
        }
        return new LatLngBounds(new LatLng((double) f.floatValue(), (double) f2.floatValue()), new LatLng((double) f3.floatValue(), (double) f4.floatValue()));
    }

    @NonNull
    public GoogleMapOptions ambientEnabled(boolean enabled) {
        this.zzn = Boolean.valueOf(enabled);
        return this;
    }

    @NonNull
    public GoogleMapOptions backgroundColor(@ColorInt @Nullable Integer num) {
        this.zzs = num;
        return this;
    }

    @NonNull
    public GoogleMapOptions camera(@Nullable CameraPosition cameraPosition) {
        this.zze = cameraPosition;
        return this;
    }

    @NonNull
    public GoogleMapOptions compassEnabled(boolean enabled) {
        this.zzg = Boolean.valueOf(enabled);
        return this;
    }

    @Nullable
    public Boolean getAmbientEnabled() {
        return this.zzn;
    }

    @ColorInt
    @Nullable
    public Integer getBackgroundColor() {
        return this.zzs;
    }

    @Nullable
    public CameraPosition getCamera() {
        return this.zze;
    }

    @Nullable
    public Boolean getCompassEnabled() {
        return this.zzg;
    }

    @Nullable
    public LatLngBounds getLatLngBoundsForCameraTarget() {
        return this.zzq;
    }

    @Nullable
    public Boolean getLiteMode() {
        return this.zzl;
    }

    @Nullable
    public String getMapId() {
        return this.zzt;
    }

    @Nullable
    public Boolean getMapToolbarEnabled() {
        return this.zzm;
    }

    public int getMapType() {
        return this.zzd;
    }

    @Nullable
    public Float getMaxZoomPreference() {
        return this.zzp;
    }

    @Nullable
    public Float getMinZoomPreference() {
        return this.zzo;
    }

    @Nullable
    public Boolean getRotateGesturesEnabled() {
        return this.zzk;
    }

    @Nullable
    public Boolean getScrollGesturesEnabled() {
        return this.zzh;
    }

    @Nullable
    public Boolean getScrollGesturesEnabledDuringRotateOrZoom() {
        return this.zzr;
    }

    @Nullable
    public Boolean getTiltGesturesEnabled() {
        return this.zzj;
    }

    @Nullable
    public Boolean getUseViewLifecycleInFragment() {
        return this.zzc;
    }

    @Nullable
    public Boolean getZOrderOnTop() {
        return this.zzb;
    }

    @Nullable
    public Boolean getZoomControlsEnabled() {
        return this.zzf;
    }

    @Nullable
    public Boolean getZoomGesturesEnabled() {
        return this.zzi;
    }

    @NonNull
    public GoogleMapOptions latLngBoundsForCameraTarget(@Nullable LatLngBounds latLngBounds) {
        this.zzq = latLngBounds;
        return this;
    }

    @NonNull
    public GoogleMapOptions liteMode(boolean enabled) {
        this.zzl = Boolean.valueOf(enabled);
        return this;
    }

    @NonNull
    public GoogleMapOptions mapId(@NonNull String str) {
        this.zzt = str;
        return this;
    }

    @NonNull
    public GoogleMapOptions mapToolbarEnabled(boolean enabled) {
        this.zzm = Boolean.valueOf(enabled);
        return this;
    }

    @NonNull
    public GoogleMapOptions mapType(int i) {
        this.zzd = i;
        return this;
    }

    @NonNull
    public GoogleMapOptions maxZoomPreference(float maxZoomPreference) {
        this.zzp = Float.valueOf(maxZoomPreference);
        return this;
    }

    @NonNull
    public GoogleMapOptions minZoomPreference(float minZoomPreference) {
        this.zzo = Float.valueOf(minZoomPreference);
        return this;
    }

    @NonNull
    public GoogleMapOptions rotateGesturesEnabled(boolean enabled) {
        this.zzk = Boolean.valueOf(enabled);
        return this;
    }

    @NonNull
    public GoogleMapOptions scrollGesturesEnabled(boolean enabled) {
        this.zzh = Boolean.valueOf(enabled);
        return this;
    }

    @NonNull
    public GoogleMapOptions scrollGesturesEnabledDuringRotateOrZoom(boolean enabled) {
        this.zzr = Boolean.valueOf(enabled);
        return this;
    }

    @NonNull
    public GoogleMapOptions tiltGesturesEnabled(boolean enabled) {
        this.zzj = Boolean.valueOf(enabled);
        return this;
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add("MapType", Integer.valueOf(this.zzd)).add("LiteMode", this.zzl).add("Camera", this.zze).add("CompassEnabled", this.zzg).add("ZoomControlsEnabled", this.zzf).add("ScrollGesturesEnabled", this.zzh).add("ZoomGesturesEnabled", this.zzi).add("TiltGesturesEnabled", this.zzj).add("RotateGesturesEnabled", this.zzk).add("ScrollGesturesEnabledDuringRotateOrZoom", this.zzr).add("MapToolbarEnabled", this.zzm).add("AmbientEnabled", this.zzn).add("MinZoomPreference", this.zzo).add("MaxZoomPreference", this.zzp).add("BackgroundColor", this.zzs).add("LatLngBoundsForCameraTarget", this.zzq).add("ZOrderOnTop", this.zzb).add("UseViewLifecycleInFragment", this.zzc).toString();
    }

    @NonNull
    public GoogleMapOptions useViewLifecycleInFragment(boolean useViewLifecycleInFragment) {
        this.zzc = Boolean.valueOf(useViewLifecycleInFragment);
        return this;
    }

    public void writeToParcel(@NonNull Parcel out, int flags) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeByte(out, 2, zza.zza(this.zzb));
        SafeParcelWriter.writeByte(out, 3, zza.zza(this.zzc));
        SafeParcelWriter.writeInt(out, 4, getMapType());
        SafeParcelWriter.writeParcelable(out, 5, getCamera(), flags, false);
        SafeParcelWriter.writeByte(out, 6, zza.zza(this.zzf));
        SafeParcelWriter.writeByte(out, 7, zza.zza(this.zzg));
        SafeParcelWriter.writeByte(out, 8, zza.zza(this.zzh));
        SafeParcelWriter.writeByte(out, 9, zza.zza(this.zzi));
        SafeParcelWriter.writeByte(out, 10, zza.zza(this.zzj));
        SafeParcelWriter.writeByte(out, 11, zza.zza(this.zzk));
        SafeParcelWriter.writeByte(out, 12, zza.zza(this.zzl));
        SafeParcelWriter.writeByte(out, 14, zza.zza(this.zzm));
        SafeParcelWriter.writeByte(out, 15, zza.zza(this.zzn));
        SafeParcelWriter.writeFloatObject(out, 16, getMinZoomPreference(), false);
        SafeParcelWriter.writeFloatObject(out, 17, getMaxZoomPreference(), false);
        SafeParcelWriter.writeParcelable(out, 18, getLatLngBoundsForCameraTarget(), flags, false);
        SafeParcelWriter.writeByte(out, 19, zza.zza(this.zzr));
        SafeParcelWriter.writeIntegerObject(out, 20, getBackgroundColor(), false);
        SafeParcelWriter.writeString(out, 21, getMapId(), false);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }

    @NonNull
    public GoogleMapOptions zOrderOnTop(boolean zOrderOnTop) {
        this.zzb = Boolean.valueOf(zOrderOnTop);
        return this;
    }

    @NonNull
    public GoogleMapOptions zoomControlsEnabled(boolean enabled) {
        this.zzf = Boolean.valueOf(enabled);
        return this;
    }

    @NonNull
    public GoogleMapOptions zoomGesturesEnabled(boolean enabled) {
        this.zzi = Boolean.valueOf(enabled);
        return this;
    }

    @SafeParcelable.Constructor
    GoogleMapOptions(@SafeParcelable.Param(id = 2) byte b, @SafeParcelable.Param(id = 3) byte b2, @SafeParcelable.Param(id = 4) int i, @SafeParcelable.Param(id = 5) @Nullable CameraPosition cameraPosition, @SafeParcelable.Param(id = 6) byte b3, @SafeParcelable.Param(id = 7) byte b4, @SafeParcelable.Param(id = 8) byte b5, @SafeParcelable.Param(id = 9) byte b6, @SafeParcelable.Param(id = 10) byte b7, @SafeParcelable.Param(id = 11) byte b8, @SafeParcelable.Param(id = 12) byte b9, @SafeParcelable.Param(id = 14) byte b10, @SafeParcelable.Param(id = 15) byte b11, @SafeParcelable.Param(id = 16) @Nullable Float f, @SafeParcelable.Param(id = 17) @Nullable Float f2, @SafeParcelable.Param(id = 18) @Nullable LatLngBounds latLngBounds, @SafeParcelable.Param(id = 19) byte b12, @SafeParcelable.Param(id = 20) @ColorInt @Nullable Integer num, @SafeParcelable.Param(id = 21) @Nullable String str) {
        this.zzb = zza.zzb(b);
        this.zzc = zza.zzb(b2);
        this.zzd = i;
        this.zze = cameraPosition;
        this.zzf = zza.zzb(b3);
        this.zzg = zza.zzb(b4);
        this.zzh = zza.zzb(b5);
        this.zzi = zza.zzb(b6);
        this.zzj = zza.zzb(b7);
        this.zzk = zza.zzb(b8);
        this.zzl = zza.zzb(b9);
        this.zzm = zza.zzb(b10);
        this.zzn = zza.zzb(b11);
        this.zzo = f;
        this.zzp = f2;
        this.zzq = latLngBounds;
        this.zzr = zza.zzb(b12);
        this.zzs = num;
        this.zzt = str;
    }
}
