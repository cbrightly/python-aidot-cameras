package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IInterface;
import androidx.annotation.NonNull;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public interface IStreetViewPanoramaFragmentDelegate extends IInterface {
    @NonNull
    IStreetViewPanoramaDelegate getStreetViewPanorama();

    void getStreetViewPanoramaAsync(zzbs zzbs);

    boolean isReady();

    void onCreate(@NonNull Bundle bundle);

    @NonNull
    IObjectWrapper onCreateView(@NonNull IObjectWrapper iObjectWrapper, @NonNull IObjectWrapper iObjectWrapper2, @NonNull Bundle bundle);

    void onDestroy();

    void onDestroyView();

    void onInflate(@NonNull IObjectWrapper iObjectWrapper, @Nullable StreetViewPanoramaOptions streetViewPanoramaOptions, @Nullable Bundle bundle);

    void onLowMemory();

    void onPause();

    void onResume();

    void onSaveInstanceState(@NonNull Bundle bundle);

    void onStart();

    void onStop();
}
