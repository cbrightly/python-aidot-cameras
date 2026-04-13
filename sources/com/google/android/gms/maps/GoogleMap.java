package com.google.android.gms.maps;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.RemoteException;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.maps.zzaa;
import com.google.android.gms.internal.maps.zzaj;
import com.google.android.gms.internal.maps.zzo;
import com.google.android.gms.internal.maps.zzr;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.ILocationSourceDelegate;
import com.google.android.gms.maps.internal.zzab;
import com.google.android.gms.maps.internal.zzad;
import com.google.android.gms.maps.internal.zzaf;
import com.google.android.gms.maps.internal.zzah;
import com.google.android.gms.maps.internal.zzam;
import com.google.android.gms.maps.internal.zzao;
import com.google.android.gms.maps.internal.zzaq;
import com.google.android.gms.maps.internal.zzau;
import com.google.android.gms.maps.internal.zzaw;
import com.google.android.gms.maps.internal.zzay;
import com.google.android.gms.maps.internal.zzba;
import com.google.android.gms.maps.internal.zzbc;
import com.google.android.gms.maps.internal.zzbe;
import com.google.android.gms.maps.internal.zzbg;
import com.google.android.gms.maps.internal.zzbi;
import com.google.android.gms.maps.internal.zzi;
import com.google.android.gms.maps.internal.zzn;
import com.google.android.gms.maps.internal.zzp;
import com.google.android.gms.maps.internal.zzt;
import com.google.android.gms.maps.internal.zzv;
import com.google.android.gms.maps.internal.zzx;
import com.google.android.gms.maps.internal.zzz;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import java.util.HashMap;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public class GoogleMap {
    public static final int MAP_TYPE_HYBRID = 4;
    public static final int MAP_TYPE_NONE = 0;
    public static final int MAP_TYPE_NORMAL = 1;
    public static final int MAP_TYPE_SATELLITE = 2;
    public static final int MAP_TYPE_TERRAIN = 3;
    private final IGoogleMapDelegate zza;
    private final HashMap zzb = new HashMap();
    private UiSettings zzc;

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface CancelableCallback {
        void onCancel();

        void onFinish();
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface InfoWindowAdapter {
        @Nullable
        View getInfoContents(@NonNull Marker marker);

        @Nullable
        View getInfoWindow(@NonNull Marker marker);
    }

    @Deprecated
    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnCameraChangeListener {
        void onCameraChange(@NonNull CameraPosition cameraPosition);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnCameraIdleListener {
        void onCameraIdle();
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnCameraMoveCanceledListener {
        void onCameraMoveCanceled();
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnCameraMoveListener {
        void onCameraMove();
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnCameraMoveStartedListener {
        public static final int REASON_API_ANIMATION = 2;
        public static final int REASON_DEVELOPER_ANIMATION = 3;
        public static final int REASON_GESTURE = 1;

        void onCameraMoveStarted(int i);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnCircleClickListener {
        void onCircleClick(@NonNull Circle circle);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnGroundOverlayClickListener {
        void onGroundOverlayClick(@NonNull GroundOverlay groundOverlay);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnIndoorStateChangeListener {
        void onIndoorBuildingFocused();

        void onIndoorLevelActivated(@NonNull IndoorBuilding indoorBuilding);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnInfoWindowClickListener {
        void onInfoWindowClick(@NonNull Marker marker);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnInfoWindowCloseListener {
        void onInfoWindowClose(@NonNull Marker marker);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnInfoWindowLongClickListener {
        void onInfoWindowLongClick(@NonNull Marker marker);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnMapClickListener {
        void onMapClick(@NonNull LatLng latLng);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnMapLoadedCallback {
        void onMapLoaded();
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnMapLongClickListener {
        void onMapLongClick(@NonNull LatLng latLng);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnMarkerClickListener {
        boolean onMarkerClick(@NonNull Marker marker);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnMarkerDragListener {
        void onMarkerDrag(@NonNull Marker marker);

        void onMarkerDragEnd(@NonNull Marker marker);

        void onMarkerDragStart(@NonNull Marker marker);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnMyLocationButtonClickListener {
        boolean onMyLocationButtonClick();
    }

    @Deprecated
    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnMyLocationChangeListener {
        void onMyLocationChange(@NonNull Location location);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnMyLocationClickListener {
        void onMyLocationClick(@NonNull Location location);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnPoiClickListener {
        void onPoiClick(@NonNull PointOfInterest pointOfInterest);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnPolygonClickListener {
        void onPolygonClick(@NonNull Polygon polygon);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnPolylineClickListener {
        void onPolylineClick(@NonNull Polyline polyline);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface SnapshotReadyCallback {
        void onSnapshotReady(@Nullable Bitmap bitmap);
    }

    public GoogleMap(@NonNull IGoogleMapDelegate iGoogleMapDelegate) {
        this.zza = (IGoogleMapDelegate) Preconditions.checkNotNull(iGoogleMapDelegate);
    }

    @NonNull
    public final Circle addCircle(@NonNull CircleOptions options) {
        try {
            Preconditions.checkNotNull(options, "CircleOptions must not be null.");
            return new Circle(this.zza.addCircle(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Nullable
    public final GroundOverlay addGroundOverlay(@NonNull GroundOverlayOptions options) {
        try {
            Preconditions.checkNotNull(options, "GroundOverlayOptions must not be null.");
            zzo addGroundOverlay = this.zza.addGroundOverlay(options);
            if (addGroundOverlay != null) {
                return new GroundOverlay(addGroundOverlay);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Nullable
    public final Marker addMarker(@NonNull MarkerOptions options) {
        try {
            Preconditions.checkNotNull(options, "MarkerOptions must not be null.");
            zzaa addMarker = this.zza.addMarker(options);
            if (addMarker != null) {
                return new Marker(addMarker);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public final Polygon addPolygon(@NonNull PolygonOptions options) {
        try {
            Preconditions.checkNotNull(options, "PolygonOptions must not be null");
            return new Polygon(this.zza.addPolygon(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public final Polyline addPolyline(@NonNull PolylineOptions options) {
        try {
            Preconditions.checkNotNull(options, "PolylineOptions must not be null");
            return new Polyline(this.zza.addPolyline(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Nullable
    public final TileOverlay addTileOverlay(@NonNull TileOverlayOptions options) {
        try {
            Preconditions.checkNotNull(options, "TileOverlayOptions must not be null.");
            zzaj addTileOverlay = this.zza.addTileOverlay(options);
            if (addTileOverlay != null) {
                return new TileOverlay(addTileOverlay);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public final CameraPosition getCameraPosition() {
        try {
            return this.zza.getCameraPosition();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Nullable
    public IndoorBuilding getFocusedBuilding() {
        try {
            zzr focusedBuilding = this.zza.getFocusedBuilding();
            if (focusedBuilding != null) {
                return new IndoorBuilding(focusedBuilding);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final int getMapType() {
        try {
            return this.zza.getMapType();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final float getMaxZoomLevel() {
        try {
            return this.zza.getMaxZoomLevel();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final float getMinZoomLevel() {
        try {
            return this.zza.getMinZoomLevel();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    @Deprecated
    public final Location getMyLocation() {
        try {
            return this.zza.getMyLocation();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public final Projection getProjection() {
        try {
            return new Projection(this.zza.getProjection());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public final UiSettings getUiSettings() {
        try {
            if (this.zzc == null) {
                this.zzc = new UiSettings(this.zza.getUiSettings());
            }
            return this.zzc;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isBuildingsEnabled() {
        try {
            return this.zza.isBuildingsEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isIndoorEnabled() {
        try {
            return this.zza.isIndoorEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isMyLocationEnabled() {
        try {
            return this.zza.isMyLocationEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isTrafficEnabled() {
        try {
            return this.zza.isTrafficEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean setIndoorEnabled(boolean enabled) {
        try {
            return this.zza.setIndoorEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setInfoWindowAdapter(@Nullable InfoWindowAdapter adapter) {
        if (adapter == null) {
            try {
                this.zza.setInfoWindowAdapter((zzi) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setInfoWindowAdapter(new zzf(this, adapter));
        }
    }

    public final void setLocationSource(@Nullable LocationSource source) {
        if (source == null) {
            try {
                this.zza.setLocationSource((ILocationSourceDelegate) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setLocationSource(new zzs(this, source));
        }
    }

    public boolean setMapStyle(@Nullable MapStyleOptions style) {
        try {
            return this.zza.setMapStyle(style);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Deprecated
    public final void setOnCameraChangeListener(@Nullable OnCameraChangeListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnCameraChangeListener((zzn) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnCameraChangeListener(new zzt(this, listener));
        }
    }

    public final void setOnCameraIdleListener(@Nullable OnCameraIdleListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnCameraIdleListener((zzp) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnCameraIdleListener(new zzx(this, listener));
        }
    }

    public final void setOnCameraMoveCanceledListener(@Nullable OnCameraMoveCanceledListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnCameraMoveCanceledListener((com.google.android.gms.maps.internal.zzr) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnCameraMoveCanceledListener(new zzw(this, listener));
        }
    }

    public final void setOnCameraMoveListener(@Nullable OnCameraMoveListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnCameraMoveListener((zzt) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnCameraMoveListener(new zzv(this, listener));
        }
    }

    public final void setOnCameraMoveStartedListener(@Nullable OnCameraMoveStartedListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnCameraMoveStartedListener((zzv) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnCameraMoveStartedListener(new zzu(this, listener));
        }
    }

    public final void setOnCircleClickListener(@Nullable OnCircleClickListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnCircleClickListener((zzx) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnCircleClickListener(new zzn(this, listener));
        }
    }

    public final void setOnGroundOverlayClickListener(@Nullable OnGroundOverlayClickListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnGroundOverlayClickListener((zzz) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnGroundOverlayClickListener(new zzm(this, listener));
        }
    }

    public final void setOnIndoorStateChangeListener(@Nullable OnIndoorStateChangeListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnIndoorStateChangeListener((zzab) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnIndoorStateChangeListener(new zzk(this, listener));
        }
    }

    public final void setOnInfoWindowClickListener(@Nullable OnInfoWindowClickListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnInfoWindowClickListener((zzad) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnInfoWindowClickListener(new zzc(this, listener));
        }
    }

    public final void setOnInfoWindowCloseListener(@Nullable OnInfoWindowCloseListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnInfoWindowCloseListener((zzaf) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnInfoWindowCloseListener(new zze(this, listener));
        }
    }

    public final void setOnInfoWindowLongClickListener(@Nullable OnInfoWindowLongClickListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnInfoWindowLongClickListener((zzah) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnInfoWindowLongClickListener(new zzd(this, listener));
        }
    }

    public final void setOnMapClickListener(@Nullable OnMapClickListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnMapClickListener((zzam) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnMapClickListener(new zzy(this, listener));
        }
    }

    public void setOnMapLoadedCallback(@Nullable OnMapLoadedCallback callback) {
        if (callback == null) {
            try {
                this.zza.setOnMapLoadedCallback((zzao) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnMapLoadedCallback(new zzj(this, callback));
        }
    }

    public final void setOnMapLongClickListener(@Nullable OnMapLongClickListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnMapLongClickListener((zzaq) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnMapLongClickListener(new zzz(this, listener));
        }
    }

    public final void setOnMarkerClickListener(@Nullable OnMarkerClickListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnMarkerClickListener((zzau) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnMarkerClickListener(new zza(this, listener));
        }
    }

    public final void setOnMarkerDragListener(@Nullable OnMarkerDragListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnMarkerDragListener((zzaw) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnMarkerDragListener(new zzb(this, listener));
        }
    }

    public final void setOnMyLocationButtonClickListener(@Nullable OnMyLocationButtonClickListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnMyLocationButtonClickListener((zzay) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnMyLocationButtonClickListener(new zzh(this, listener));
        }
    }

    @Deprecated
    public final void setOnMyLocationChangeListener(@Nullable OnMyLocationChangeListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnMyLocationChangeListener((zzba) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnMyLocationChangeListener(new zzg(this, listener));
        }
    }

    public final void setOnMyLocationClickListener(@Nullable OnMyLocationClickListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnMyLocationClickListener((zzbc) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnMyLocationClickListener(new zzi(this, listener));
        }
    }

    public final void setOnPoiClickListener(@Nullable OnPoiClickListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnPoiClickListener((zzbe) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnPoiClickListener(new zzr(this, listener));
        }
    }

    public final void setOnPolygonClickListener(@Nullable OnPolygonClickListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnPolygonClickListener((zzbg) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnPolygonClickListener(new zzo(this, listener));
        }
    }

    public final void setOnPolylineClickListener(@Nullable OnPolylineClickListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnPolylineClickListener((zzbi) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnPolylineClickListener(new zzp(this, listener));
        }
    }

    public final void snapshot(@NonNull SnapshotReadyCallback callback) {
        Preconditions.checkNotNull(callback, "Callback must not be null.");
        snapshot(callback, (Bitmap) null);
    }

    public final void clear() {
        try {
            this.zza.clear();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void resetMinMaxZoomPreference() {
        try {
            this.zza.resetMinMaxZoomPreference();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setBuildingsEnabled(boolean enabled) {
        try {
            this.zza.setBuildingsEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setContentDescription(@Nullable String description) {
        try {
            this.zza.setContentDescription(description);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setLatLngBoundsForCameraTarget(@Nullable LatLngBounds bounds) {
        try {
            this.zza.setLatLngBoundsForCameraTarget(bounds);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setMapType(int type) {
        try {
            this.zza.setMapType(type);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setMaxZoomPreference(float maxZoomPreference) {
        try {
            this.zza.setMaxZoomPreference(maxZoomPreference);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setMinZoomPreference(float minZoomPreference) {
        try {
            this.zza.setMinZoomPreference(minZoomPreference);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public final void setMyLocationEnabled(boolean enabled) {
        try {
            this.zza.setMyLocationEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setPadding(int left, int top, int right, int bottom) {
        try {
            this.zza.setPadding(left, top, right, bottom);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setTrafficEnabled(boolean enabled) {
        try {
            this.zza.setTrafficEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void stopAnimation() {
        try {
            this.zza.stopAnimation();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(@NonNull CameraUpdate update) {
        try {
            Preconditions.checkNotNull(update, "CameraUpdate must not be null.");
            this.zza.animateCamera(update.zza());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void moveCamera(@NonNull CameraUpdate update) {
        try {
            Preconditions.checkNotNull(update, "CameraUpdate must not be null.");
            this.zza.moveCamera(update.zza());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void snapshot(@NonNull SnapshotReadyCallback callback, @Nullable Bitmap bitmap) {
        IObjectWrapper iObjectWrapper;
        Preconditions.checkNotNull(callback, "Callback must not be null.");
        if (bitmap != null) {
            iObjectWrapper = ObjectWrapper.wrap(bitmap);
        } else {
            iObjectWrapper = null;
        }
        try {
            this.zza.snapshot(new zzq(this, callback), (ObjectWrapper) iObjectWrapper);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(@NonNull CameraUpdate update, int durationMs, @Nullable CancelableCallback callback) {
        zzaa zzaa;
        try {
            Preconditions.checkNotNull(update, "CameraUpdate must not be null.");
            IGoogleMapDelegate iGoogleMapDelegate = this.zza;
            IObjectWrapper zza2 = update.zza();
            if (callback == null) {
                zzaa = null;
            } else {
                zzaa = new zzaa(callback);
            }
            iGoogleMapDelegate.animateCameraWithDurationAndCallback(zza2, durationMs, zzaa);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(@NonNull CameraUpdate update, @Nullable CancelableCallback callback) {
        zzaa zzaa;
        try {
            Preconditions.checkNotNull(update, "CameraUpdate must not be null.");
            IGoogleMapDelegate iGoogleMapDelegate = this.zza;
            IObjectWrapper zza2 = update.zza();
            if (callback == null) {
                zzaa = null;
            } else {
                zzaa = new zzaa(callback);
            }
            iGoogleMapDelegate.animateCameraWithCallback(zza2, zzaa);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
