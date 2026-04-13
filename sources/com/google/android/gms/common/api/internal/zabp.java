package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.internal.BaseGmsClient;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zabp implements BaseGmsClient.SignOutCallbacks {
    final /* synthetic */ zabq zaa;

    zabp(zabq zabq) {
        this.zaa = zabq;
    }

    public final void onSignOutComplete() {
        this.zaa.zaa.zar.post(new zabo(this));
    }
}
