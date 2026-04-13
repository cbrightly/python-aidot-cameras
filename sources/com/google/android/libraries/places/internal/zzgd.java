package com.google.android.libraries.places.internal;

import android.content.Context;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzgd implements zzgh {
    private Context zza;
    private zzgk zzb;
    private zzgr zzc;

    private zzgd() {
    }

    /* synthetic */ zzgd(zzgc zzgc) {
    }

    public final /* synthetic */ zzgh zza(zzgk zzgk) {
        this.zzb = zzgk;
        return this;
    }

    public final /* synthetic */ zzgh zzb(zzgr zzgr) {
        this.zzc = zzgr;
        return this;
    }

    public final /* synthetic */ zzgh zzc(Context context) {
        if (context != null) {
            this.zza = context;
            return this;
        }
        throw null;
    }

    public final zzgi zzd() {
        zzaje.zzb(this.zza, Context.class);
        zzaje.zzb(this.zzb, zzgk.class);
        zzaje.zzb(this.zzc, zzgr.class);
        return new zzgf(this.zza, this.zzb, this.zzc, (zzge) null);
    }
}
