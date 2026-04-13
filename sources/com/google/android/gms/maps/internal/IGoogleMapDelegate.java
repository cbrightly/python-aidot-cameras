package com.google.android.gms.maps.internal;

import android.location.Location;
import android.os.Bundle;
import android.os.IInterface;
import androidx.annotation.NonNull;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zzaa;
import com.google.android.gms.internal.maps.zzad;
import com.google.android.gms.internal.maps.zzag;
import com.google.android.gms.internal.maps.zzaj;
import com.google.android.gms.internal.maps.zzl;
import com.google.android.gms.internal.maps.zzo;
import com.google.android.gms.internal.maps.zzr;
import com.google.android.gms.internal.maps.zzx;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public interface IGoogleMapDelegate extends IInterface {
    zzl addCircle(CircleOptions circleOptions);

    zzo addGroundOverlay(GroundOverlayOptions groundOverlayOptions);

    zzaa addMarker(MarkerOptions markerOptions);

    void addOnMapCapabilitiesChangedListener(zzak zzak);

    zzad addPolygon(PolygonOptions polygonOptions);

    zzag addPolyline(PolylineOptions polylineOptions);

    zzaj addTileOverlay(TileOverlayOptions tileOverlayOptions);

    void animateCamera(@NonNull IObjectWrapper iObjectWrapper);

    void animateCameraWithCallback(IObjectWrapper iObjectWrapper, @Nullable zzd zzd);

    void animateCameraWithDurationAndCallback(IObjectWrapper iObjectWrapper, int i, @Nullable zzd zzd);

    void clear();

    @NonNull
    CameraPosition getCameraPosition();

    zzr getFocusedBuilding();

    void getMapAsync(zzas zzas);

    zzx getMapCapabilities();

    int getMapType();

    float getMaxZoomLevel();

    float getMinZoomLevel();

    @NonNull
    Location getMyLocation();

    @NonNull
    IProjectionDelegate getProjection();

    @NonNull
    IUiSettingsDelegate getUiSettings();

    boolean isBuildingsEnabled();

    boolean isIndoorEnabled();

    boolean isMyLocationEnabled();

    boolean isTrafficEnabled();

    void moveCamera(@NonNull IObjectWrapper iObjectWrapper);

    void onCreate(@NonNull Bundle bundle);

    void onDestroy();

    void onEnterAmbient(@NonNull Bundle bundle);

    void onExitAmbient();

    void onLowMemory();

    void onPause();

    void onResume();

    void onSaveInstanceState(@NonNull Bundle bundle);

    void onStart();

    void onStop();

    void removeOnMapCapabilitiesChangedListener(zzak zzak);

    void resetMinMaxZoomPreference();

    void setBuildingsEnabled(boolean z);

    void setContentDescription(@Nullable String str);

    boolean setIndoorEnabled(boolean z);

    void setInfoWindowAdapter(@Nullable zzi zzi);

    void setLatLngBoundsForCameraTarget(@Nullable LatLngBounds latLngBounds);

    void setLocationSource(@Nullable ILocationSourceDelegate iLocationSourceDelegate);

    boolean setMapStyle(@Nullable MapStyleOptions mapStyleOptions);

    void setMapType(int i);

    void setMaxZoomPreference(float f);

    void setMinZoomPreference(float f);

    void setMyLocationEnabled(boolean z);

    void setOnCameraChangeListener(@Nullable zzn zzn);

    void setOnCameraIdleListener(@Nullable zzp zzp);

    void setOnCameraMoveCanceledListener(@Nullable zzr zzr);

    void setOnCameraMoveListener(@Nullable zzt zzt);

    void setOnCameraMoveStartedListener(@Nullable zzv zzv);

    void setOnCircleClickListener(@Nullable zzx zzx);

    void setOnGroundOverlayClickListener(@Nullable zzz zzz);

    void setOnIndoorStateChangeListener(@Nullable zzab zzab);

    void setOnInfoWindowClickListener(@Nullable zzad zzad);

    void setOnInfoWindowCloseListener(@Nullable zzaf zzaf);

    void setOnInfoWindowLongClickListener(@Nullable zzah zzah);

    void setOnMapClickListener(@Nullable zzam zzam);

    void setOnMapLoadedCallback(@Nullable zzao zzao);

    void setOnMapLongClickListener(@Nullable zzaq zzaq);

    void setOnMarkerClickListener(@Nullable zzau zzau);

    void setOnMarkerDragListener(@Nullable zzaw zzaw);

    void setOnMyLocationButtonClickListener(@Nullable zzay zzay);

    void setOnMyLocationChangeListener(@Nullable zzba zzba);

    void setOnMyLocationClickListener(@Nullable zzbc zzbc);

    void setOnPoiClickListener(@Nullable zzbe zzbe);

    void setOnPolygonClickListener(@Nullable zzbg zzbg);

    void setOnPolylineClickListener(@Nullable zzbi zzbi);

    void setPadding(int i, int i2, int i3, int i4);

    void setTrafficEnabled(boolean z);

    void setWatermarkEnabled(boolean z);

    void snapshot(zzbv zzbv, @Nullable IObjectWrapper iObjectWrapper);

    void snapshotForTest(zzbv zzbv);

    void stopAnimation();

    boolean useViewLifecycleWhenInFragment();
}
