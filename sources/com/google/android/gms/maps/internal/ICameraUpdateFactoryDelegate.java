package com.google.android.gms.maps.internal;

import android.os.IInterface;
import androidx.annotation.NonNull;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public interface ICameraUpdateFactoryDelegate extends IInterface {
    @NonNull
    IObjectWrapper newCameraPosition(@NonNull CameraPosition cameraPosition);

    @NonNull
    IObjectWrapper newLatLng(@NonNull LatLng latLng);

    @NonNull
    IObjectWrapper newLatLngBounds(@NonNull LatLngBounds latLngBounds, int i);

    @NonNull
    IObjectWrapper newLatLngBoundsWithSize(@NonNull LatLngBounds latLngBounds, int i, int i2, int i3);

    @NonNull
    IObjectWrapper newLatLngZoom(@NonNull LatLng latLng, float f);

    @NonNull
    IObjectWrapper scrollBy(float f, float f2);

    @NonNull
    IObjectWrapper zoomBy(float f);

    @NonNull
    IObjectWrapper zoomByWithFocus(float f, int i, int i2);

    @NonNull
    IObjectWrapper zoomIn();

    @NonNull
    IObjectWrapper zoomOut();

    @NonNull
    IObjectWrapper zoomTo(float f);
}
