package com.google.android.gms.common.internal;

import android.os.Bundle;
import androidx.annotation.BinderThread;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class zzg extends zza {
    final /* synthetic */ BaseGmsClient zze;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    @BinderThread
    public zzg(BaseGmsClient baseGmsClient, @Nullable int i, Bundle bundle) {
        super(baseGmsClient, i, (Bundle) null);
        this.zze = baseGmsClient;
    }

    /* access modifiers changed from: protected */
    public final void zzb(ConnectionResult connectionResult) {
        if (!this.zze.enableLocalFallback() || !BaseGmsClient.zzo(this.zze)) {
            this.zze.zzc.onReportServiceBinding(connectionResult);
            this.zze.onConnectionFailed(connectionResult);
            return;
        }
        BaseGmsClient.zzk(this.zze, 16);
    }

    /* access modifiers changed from: protected */
    public final boolean zzd() {
        this.zze.zzc.onReportServiceBinding(ConnectionResult.RESULT_SUCCESS);
        return true;
    }
}
