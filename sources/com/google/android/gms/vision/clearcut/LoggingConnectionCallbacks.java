package com.google.android.gms.vision.clearcut;

import android.os.Bundle;
import androidx.annotation.Keep;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

@Keep
/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public class LoggingConnectionCallbacks implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public void onConnected(@RecentlyNonNull Bundle bundle) {
        throw new NoSuchMethodError();
    }

    public void onConnectionSuspended(int i) {
        throw new NoSuchMethodError();
    }

    public void onConnectionFailed(@RecentlyNonNull ConnectionResult connectionResult) {
        throw new NoSuchMethodError();
    }
}
