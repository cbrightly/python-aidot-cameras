package com.google.android.gms.common.api.internal;

import android.app.Dialog;
import android.app.PendingIntent;
import androidx.annotation.MainThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zao implements Runnable {
    final /* synthetic */ zap zaa;
    private final zam zab;

    zao(zap zap, zam zam) {
        this.zaa = zap;
        this.zab = zam;
    }

    @MainThread
    public final void run() {
        if (this.zaa.zaa) {
            ConnectionResult zab2 = this.zab.zab();
            if (zab2.hasResolution()) {
                zap zap = this.zaa;
                zap.mLifecycleFragment.startActivityForResult(GoogleApiActivity.zaa(zap.getActivity(), (PendingIntent) Preconditions.checkNotNull(zab2.getResolution()), this.zab.zaa(), false), 1);
                return;
            }
            zap zap2 = this.zaa;
            if (zap2.zac.getErrorResolutionIntent(zap2.getActivity(), zab2.getErrorCode(), (String) null) != null) {
                zap zap3 = this.zaa;
                zap3.zac.zag(zap3.getActivity(), this.zaa.mLifecycleFragment, zab2.getErrorCode(), 2, this.zaa);
            } else if (zab2.getErrorCode() == 18) {
                zap zap4 = this.zaa;
                Dialog zab3 = zap4.zac.zab(zap4.getActivity(), this.zaa);
                zap zap5 = this.zaa;
                zap5.zac.zac(zap5.getActivity().getApplicationContext(), new zan(this, zab3));
            } else {
                this.zaa.zaa(zab2, this.zab.zaa());
            }
        }
    }
}
