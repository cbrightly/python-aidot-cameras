package com.google.android.gms.maps.internal;

import android.os.IInterface;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zzi;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public interface zzf extends IInterface {
    int zzd();

    ICameraUpdateFactoryDelegate zze();

    IMapFragmentDelegate zzf(IObjectWrapper iObjectWrapper);

    IMapViewDelegate zzg(IObjectWrapper iObjectWrapper, @Nullable GoogleMapOptions googleMapOptions);

    IStreetViewPanoramaFragmentDelegate zzh(IObjectWrapper iObjectWrapper);

    IStreetViewPanoramaViewDelegate zzi(IObjectWrapper iObjectWrapper, @Nullable StreetViewPanoramaOptions streetViewPanoramaOptions);

    zzi zzj();

    void zzk(IObjectWrapper iObjectWrapper, int i);

    void zzl(IObjectWrapper iObjectWrapper, int i);
}
