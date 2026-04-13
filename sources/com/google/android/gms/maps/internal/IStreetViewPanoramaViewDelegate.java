package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IInterface;
import androidx.annotation.NonNull;
import com.google.android.gms.dynamic.IObjectWrapper;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public interface IStreetViewPanoramaViewDelegate extends IInterface {
    @NonNull
    IStreetViewPanoramaDelegate getStreetViewPanorama();

    void getStreetViewPanoramaAsync(zzbs zzbs);

    @NonNull
    IObjectWrapper getView();

    void onCreate(@NonNull Bundle bundle);

    void onDestroy();

    void onLowMemory();

    void onPause();

    void onResume();

    void onSaveInstanceState(@NonNull Bundle bundle);

    void onStart();

    void onStop();
}
