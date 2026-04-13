package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzia implements Runnable {
    public final /* synthetic */ zzid zza;
    public final /* synthetic */ String zzb;

    public /* synthetic */ zzia(zzid zzid, String str) {
        this.zza = zzid;
        this.zzb = str;
    }

    public final void run() {
        this.zza.zzd(this.zzb);
    }
}
