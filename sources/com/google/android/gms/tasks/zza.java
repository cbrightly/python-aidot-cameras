package com.google.android.gms.tasks;

/* compiled from: com.google.android.gms:play-services-tasks@@18.0.2 */
public final class zza implements OnSuccessListener {
    final /* synthetic */ OnTokenCanceledListener zza;

    zza(zzb zzb, OnTokenCanceledListener onTokenCanceledListener) {
        this.zza = onTokenCanceledListener;
    }

    public final /* bridge */ /* synthetic */ void onSuccess(Object obj) {
        Void voidR = (Void) obj;
        this.zza.onCanceled();
    }
}
