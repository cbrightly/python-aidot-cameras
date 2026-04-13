package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.signin.zae;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zaat implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    final /* synthetic */ zaaw zaa;

    /* synthetic */ zaat(zaaw zaaw, zaas zaas) {
        this.zaa = zaaw;
    }

    public final void onConnected(@Nullable Bundle bundle) {
        ClientSettings clientSettings = (ClientSettings) Preconditions.checkNotNull(this.zaa.zar);
        ((zae) Preconditions.checkNotNull(this.zaa.zak)).zad(new zaar(this.zaa));
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zaa.zab.lock();
        try {
            if (this.zaa.zaI(connectionResult)) {
                this.zaa.zaA();
                this.zaa.zaF();
            } else {
                this.zaa.zaD(connectionResult);
            }
        } finally {
            this.zaa.zab.unlock();
        }
    }

    public final void onConnectionSuspended(int i) {
    }
}
