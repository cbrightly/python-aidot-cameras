package com.google.android.gms.maps;

import android.location.Location;
import androidx.annotation.NonNull;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public interface LocationSource {

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnLocationChangedListener {
        void onLocationChanged(@NonNull Location location);
    }

    void activate(@NonNull OnLocationChangedListener onLocationChangedListener);

    void deactivate();
}
