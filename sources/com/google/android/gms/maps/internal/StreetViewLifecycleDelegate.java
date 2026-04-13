package com.google.android.gms.maps.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public interface StreetViewLifecycleDelegate extends LifecycleDelegate {
    void getStreetViewPanoramaAsync(@NonNull OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback);
}
