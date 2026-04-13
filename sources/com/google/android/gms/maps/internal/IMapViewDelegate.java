package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IInterface;
import androidx.annotation.NonNull;
import com.google.android.gms.dynamic.IObjectWrapper;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public interface IMapViewDelegate extends IInterface {
    @NonNull
    IGoogleMapDelegate getMap();

    void getMapAsync(zzas zzas);

    @NonNull
    IObjectWrapper getView();

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
}
