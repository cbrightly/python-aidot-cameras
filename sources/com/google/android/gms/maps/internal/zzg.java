package com.google.android.gms.maps.internal;

import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.internal.maps.zzaa;
import com.google.android.gms.internal.maps.zzac;
import com.google.android.gms.internal.maps.zzad;
import com.google.android.gms.internal.maps.zzaf;
import com.google.android.gms.internal.maps.zzag;
import com.google.android.gms.internal.maps.zzai;
import com.google.android.gms.internal.maps.zzaj;
import com.google.android.gms.internal.maps.zzc;
import com.google.android.gms.internal.maps.zzk;
import com.google.android.gms.internal.maps.zzl;
import com.google.android.gms.internal.maps.zzn;
import com.google.android.gms.internal.maps.zzo;
import com.google.android.gms.internal.maps.zzq;
import com.google.android.gms.internal.maps.zzr;
import com.google.android.gms.internal.maps.zzw;
import com.google.android.gms.internal.maps.zzx;
import com.google.android.gms.internal.maps.zzz;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzg extends zza implements IGoogleMapDelegate {
    zzg(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IGoogleMapDelegate");
    }

    public final zzl addCircle(CircleOptions circleOptions) {
        Parcel zza = zza();
        zzc.zze(zza, circleOptions);
        Parcel zzH = zzH(35, zza);
        zzl zzb = zzk.zzb(zzH.readStrongBinder());
        zzH.recycle();
        return zzb;
    }

    public final zzo addGroundOverlay(GroundOverlayOptions groundOverlayOptions) {
        Parcel zza = zza();
        zzc.zze(zza, groundOverlayOptions);
        Parcel zzH = zzH(12, zza);
        zzo zzb = zzn.zzb(zzH.readStrongBinder());
        zzH.recycle();
        return zzb;
    }

    public final zzaa addMarker(MarkerOptions markerOptions) {
        Parcel zza = zza();
        zzc.zze(zza, markerOptions);
        Parcel zzH = zzH(11, zza);
        zzaa zzb = zzz.zzb(zzH.readStrongBinder());
        zzH.recycle();
        return zzb;
    }

    public final void addOnMapCapabilitiesChangedListener(zzak zzak) {
        Parcel zza = zza();
        zzc.zzg(zza, zzak);
        zzc(110, zza);
    }

    public final zzad addPolygon(PolygonOptions polygonOptions) {
        Parcel zza = zza();
        zzc.zze(zza, polygonOptions);
        Parcel zzH = zzH(10, zza);
        zzad zzb = zzac.zzb(zzH.readStrongBinder());
        zzH.recycle();
        return zzb;
    }

    public final zzag addPolyline(PolylineOptions polylineOptions) {
        Parcel zza = zza();
        zzc.zze(zza, polylineOptions);
        Parcel zzH = zzH(9, zza);
        zzag zzb = zzaf.zzb(zzH.readStrongBinder());
        zzH.recycle();
        return zzb;
    }

    public final zzaj addTileOverlay(TileOverlayOptions tileOverlayOptions) {
        Parcel zza = zza();
        zzc.zze(zza, tileOverlayOptions);
        Parcel zzH = zzH(13, zza);
        zzaj zzb = zzai.zzb(zzH.readStrongBinder());
        zzH.recycle();
        return zzb;
    }

    public final void animateCamera(IObjectWrapper iObjectWrapper) {
        Parcel zza = zza();
        zzc.zzg(zza, iObjectWrapper);
        zzc(5, zza);
    }

    public final void animateCameraWithCallback(IObjectWrapper iObjectWrapper, zzd zzd) {
        Parcel zza = zza();
        zzc.zzg(zza, iObjectWrapper);
        zzc.zzg(zza, zzd);
        zzc(6, zza);
    }

    public final void animateCameraWithDurationAndCallback(IObjectWrapper iObjectWrapper, int i, zzd zzd) {
        Parcel zza = zza();
        zzc.zzg(zza, iObjectWrapper);
        zza.writeInt(i);
        zzc.zzg(zza, zzd);
        zzc(7, zza);
    }

    public final void clear() {
        zzc(14, zza());
    }

    public final CameraPosition getCameraPosition() {
        Parcel zzH = zzH(1, zza());
        CameraPosition cameraPosition = (CameraPosition) zzc.zza(zzH, CameraPosition.CREATOR);
        zzH.recycle();
        return cameraPosition;
    }

    public final zzr getFocusedBuilding() {
        Parcel zzH = zzH(44, zza());
        zzr zzb = zzq.zzb(zzH.readStrongBinder());
        zzH.recycle();
        return zzb;
    }

    public final void getMapAsync(zzas zzas) {
        Parcel zza = zza();
        zzc.zzg(zza, zzas);
        zzc(53, zza);
    }

    public final zzx getMapCapabilities() {
        Parcel zzH = zzH(109, zza());
        zzx zzb = zzw.zzb(zzH.readStrongBinder());
        zzH.recycle();
        return zzb;
    }

    public final int getMapType() {
        Parcel zzH = zzH(15, zza());
        int readInt = zzH.readInt();
        zzH.recycle();
        return readInt;
    }

    public final float getMaxZoomLevel() {
        Parcel zzH = zzH(2, zza());
        float readFloat = zzH.readFloat();
        zzH.recycle();
        return readFloat;
    }

    public final float getMinZoomLevel() {
        Parcel zzH = zzH(3, zza());
        float readFloat = zzH.readFloat();
        zzH.recycle();
        return readFloat;
    }

    public final Location getMyLocation() {
        Parcel zzH = zzH(23, zza());
        Location location = (Location) zzc.zza(zzH, Location.CREATOR);
        zzH.recycle();
        return location;
    }

    /* JADX WARNING: type inference failed for: r2v1, types: [android.os.IInterface] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.maps.internal.IProjectionDelegate getProjection() {
        /*
            r4 = this;
            android.os.Parcel r0 = r4.zza()
            r1 = 26
            android.os.Parcel r0 = r4.zzH(r1, r0)
            android.os.IBinder r1 = r0.readStrongBinder()
            if (r1 != 0) goto L_0x0012
            r1 = 0
            goto L_0x0027
        L_0x0012:
            java.lang.String r2 = "com.google.android.gms.maps.internal.IProjectionDelegate"
            android.os.IInterface r2 = r1.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.maps.internal.IProjectionDelegate
            if (r3 == 0) goto L_0x0021
            r1 = r2
            com.google.android.gms.maps.internal.IProjectionDelegate r1 = (com.google.android.gms.maps.internal.IProjectionDelegate) r1
            goto L_0x0027
        L_0x0021:
            com.google.android.gms.maps.internal.zzbt r2 = new com.google.android.gms.maps.internal.zzbt
            r2.<init>(r1)
            r1 = r2
        L_0x0027:
            r0.recycle()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.maps.internal.zzg.getProjection():com.google.android.gms.maps.internal.IProjectionDelegate");
    }

    /* JADX WARNING: type inference failed for: r2v1, types: [android.os.IInterface] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.maps.internal.IUiSettingsDelegate getUiSettings() {
        /*
            r4 = this;
            android.os.Parcel r0 = r4.zza()
            r1 = 25
            android.os.Parcel r0 = r4.zzH(r1, r0)
            android.os.IBinder r1 = r0.readStrongBinder()
            if (r1 != 0) goto L_0x0012
            r1 = 0
            goto L_0x0027
        L_0x0012:
            java.lang.String r2 = "com.google.android.gms.maps.internal.IUiSettingsDelegate"
            android.os.IInterface r2 = r1.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.maps.internal.IUiSettingsDelegate
            if (r3 == 0) goto L_0x0021
            r1 = r2
            com.google.android.gms.maps.internal.IUiSettingsDelegate r1 = (com.google.android.gms.maps.internal.IUiSettingsDelegate) r1
            goto L_0x0027
        L_0x0021:
            com.google.android.gms.maps.internal.zzbz r2 = new com.google.android.gms.maps.internal.zzbz
            r2.<init>(r1)
            r1 = r2
        L_0x0027:
            r0.recycle()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.maps.internal.zzg.getUiSettings():com.google.android.gms.maps.internal.IUiSettingsDelegate");
    }

    public final boolean isBuildingsEnabled() {
        Parcel zzH = zzH(40, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean isIndoorEnabled() {
        Parcel zzH = zzH(19, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean isMyLocationEnabled() {
        Parcel zzH = zzH(21, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean isTrafficEnabled() {
        Parcel zzH = zzH(17, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final void moveCamera(IObjectWrapper iObjectWrapper) {
        Parcel zza = zza();
        zzc.zzg(zza, iObjectWrapper);
        zzc(4, zza);
    }

    public final void onCreate(Bundle bundle) {
        Parcel zza = zza();
        zzc.zze(zza, bundle);
        zzc(54, zza);
    }

    public final void onDestroy() {
        zzc(57, zza());
    }

    public final void onEnterAmbient(Bundle bundle) {
        Parcel zza = zza();
        zzc.zze(zza, bundle);
        zzc(81, zza);
    }

    public final void onExitAmbient() {
        zzc(82, zza());
    }

    public final void onLowMemory() {
        zzc(58, zza());
    }

    public final void onPause() {
        zzc(56, zza());
    }

    public final void onResume() {
        zzc(55, zza());
    }

    public final void onSaveInstanceState(Bundle bundle) {
        Parcel zza = zza();
        zzc.zze(zza, bundle);
        Parcel zzH = zzH(60, zza);
        if (zzH.readInt() != 0) {
            bundle.readFromParcel(zzH);
        }
        zzH.recycle();
    }

    public final void onStart() {
        zzc(101, zza());
    }

    public final void onStop() {
        zzc(102, zza());
    }

    public final void removeOnMapCapabilitiesChangedListener(zzak zzak) {
        Parcel zza = zza();
        zzc.zzg(zza, zzak);
        zzc(111, zza);
    }

    public final void resetMinMaxZoomPreference() {
        zzc(94, zza());
    }

    public final void setBuildingsEnabled(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(41, zza);
    }

    public final void setContentDescription(String str) {
        Parcel zza = zza();
        zza.writeString(str);
        zzc(61, zza);
    }

    public final boolean setIndoorEnabled(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        Parcel zzH = zzH(20, zza);
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final void setInfoWindowAdapter(zzi zzi) {
        Parcel zza = zza();
        zzc.zzg(zza, zzi);
        zzc(33, zza);
    }

    public final void setLatLngBoundsForCameraTarget(LatLngBounds latLngBounds) {
        Parcel zza = zza();
        zzc.zze(zza, latLngBounds);
        zzc(95, zza);
    }

    public final void setLocationSource(ILocationSourceDelegate iLocationSourceDelegate) {
        Parcel zza = zza();
        zzc.zzg(zza, iLocationSourceDelegate);
        zzc(24, zza);
    }

    public final boolean setMapStyle(MapStyleOptions mapStyleOptions) {
        Parcel zza = zza();
        zzc.zze(zza, mapStyleOptions);
        Parcel zzH = zzH(91, zza);
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final void setMapType(int i) {
        Parcel zza = zza();
        zza.writeInt(i);
        zzc(16, zza);
    }

    public final void setMaxZoomPreference(float f) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zzc(93, zza);
    }

    public final void setMinZoomPreference(float f) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zzc(92, zza);
    }

    public final void setMyLocationEnabled(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(22, zza);
    }

    public final void setOnCameraChangeListener(zzn zzn) {
        Parcel zza = zza();
        zzc.zzg(zza, zzn);
        zzc(27, zza);
    }

    public final void setOnCameraIdleListener(zzp zzp) {
        Parcel zza = zza();
        zzc.zzg(zza, zzp);
        zzc(99, zza);
    }

    public final void setOnCameraMoveCanceledListener(zzr zzr) {
        Parcel zza = zza();
        zzc.zzg(zza, zzr);
        zzc(98, zza);
    }

    public final void setOnCameraMoveListener(zzt zzt) {
        Parcel zza = zza();
        zzc.zzg(zza, zzt);
        zzc(97, zza);
    }

    public final void setOnCameraMoveStartedListener(zzv zzv) {
        Parcel zza = zza();
        zzc.zzg(zza, zzv);
        zzc(96, zza);
    }

    public final void setOnCircleClickListener(zzx zzx) {
        Parcel zza = zza();
        zzc.zzg(zza, zzx);
        zzc(89, zza);
    }

    public final void setOnGroundOverlayClickListener(zzz zzz) {
        Parcel zza = zza();
        zzc.zzg(zza, zzz);
        zzc(83, zza);
    }

    public final void setOnIndoorStateChangeListener(zzab zzab) {
        Parcel zza = zza();
        zzc.zzg(zza, zzab);
        zzc(45, zza);
    }

    public final void setOnInfoWindowClickListener(zzad zzad) {
        Parcel zza = zza();
        zzc.zzg(zza, zzad);
        zzc(32, zza);
    }

    public final void setOnInfoWindowCloseListener(zzaf zzaf) {
        Parcel zza = zza();
        zzc.zzg(zza, zzaf);
        zzc(86, zza);
    }

    public final void setOnInfoWindowLongClickListener(zzah zzah) {
        Parcel zza = zza();
        zzc.zzg(zza, zzah);
        zzc(84, zza);
    }

    public final void setOnMapClickListener(zzam zzam) {
        Parcel zza = zza();
        zzc.zzg(zza, zzam);
        zzc(28, zza);
    }

    public final void setOnMapLoadedCallback(zzao zzao) {
        Parcel zza = zza();
        zzc.zzg(zza, zzao);
        zzc(42, zza);
    }

    public final void setOnMapLongClickListener(zzaq zzaq) {
        Parcel zza = zza();
        zzc.zzg(zza, zzaq);
        zzc(29, zza);
    }

    public final void setOnMarkerClickListener(zzau zzau) {
        Parcel zza = zza();
        zzc.zzg(zza, zzau);
        zzc(30, zza);
    }

    public final void setOnMarkerDragListener(zzaw zzaw) {
        Parcel zza = zza();
        zzc.zzg(zza, zzaw);
        zzc(31, zza);
    }

    public final void setOnMyLocationButtonClickListener(zzay zzay) {
        Parcel zza = zza();
        zzc.zzg(zza, zzay);
        zzc(37, zza);
    }

    public final void setOnMyLocationChangeListener(zzba zzba) {
        Parcel zza = zza();
        zzc.zzg(zza, zzba);
        zzc(36, zza);
    }

    public final void setOnMyLocationClickListener(zzbc zzbc) {
        Parcel zza = zza();
        zzc.zzg(zza, zzbc);
        zzc(107, zza);
    }

    public final void setOnPoiClickListener(zzbe zzbe) {
        Parcel zza = zza();
        zzc.zzg(zza, zzbe);
        zzc(80, zza);
    }

    public final void setOnPolygonClickListener(zzbg zzbg) {
        Parcel zza = zza();
        zzc.zzg(zza, zzbg);
        zzc(85, zza);
    }

    public final void setOnPolylineClickListener(zzbi zzbi) {
        Parcel zza = zza();
        zzc.zzg(zza, zzbi);
        zzc(87, zza);
    }

    public final void setPadding(int i, int i2, int i3, int i4) {
        Parcel zza = zza();
        zza.writeInt(i);
        zza.writeInt(i2);
        zza.writeInt(i3);
        zza.writeInt(i4);
        zzc(39, zza);
    }

    public final void setTrafficEnabled(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(18, zza);
    }

    public final void setWatermarkEnabled(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(51, zza);
    }

    public final void snapshot(zzbv zzbv, IObjectWrapper iObjectWrapper) {
        Parcel zza = zza();
        zzc.zzg(zza, zzbv);
        zzc.zzg(zza, iObjectWrapper);
        zzc(38, zza);
    }

    public final void snapshotForTest(zzbv zzbv) {
        Parcel zza = zza();
        zzc.zzg(zza, zzbv);
        zzc(71, zza);
    }

    public final void stopAnimation() {
        zzc(8, zza());
    }

    public final boolean useViewLifecycleWhenInFragment() {
        Parcel zzH = zzH(59, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }
}
