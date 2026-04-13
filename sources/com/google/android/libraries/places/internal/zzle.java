package com.google.android.libraries.places.internal;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzle {
    private static final zzli zza = new zzlb();
    private static final zzlh zzb = new zzlc();
    /* access modifiers changed from: private */
    public final Map zzc = new HashMap();
    /* access modifiers changed from: private */
    public final Map zzd = new HashMap();
    /* access modifiers changed from: private */
    public final zzli zze;
    /* access modifiers changed from: private */
    public zzlh zzf = null;

    /* synthetic */ zzle(zzli zzli, zzld zzld) {
        this.zze = zzli;
    }

    public final zzle zza(zzlh zzlh) {
        this.zzf = zzlh;
        return this;
    }

    public final zzlj zzd() {
        return new zzlg(this, (zzlf) null);
    }

    /* access modifiers changed from: package-private */
    public final void zzg(zzkv zzkv) {
        zzms.zza(zzkv, CacheEntity.KEY);
        if (zzkv.zzb()) {
            zzlh zzlh = zzb;
            zzms.zza(zzkv, CacheEntity.KEY);
            if (zzkv.zzb()) {
                this.zzc.remove(zzkv);
                this.zzd.put(zzkv, zzlh);
                return;
            }
            throw new IllegalArgumentException("key must be repeating");
        }
        zzli zzli = zza;
        zzms.zza(zzkv, CacheEntity.KEY);
        this.zzd.remove(zzkv);
        this.zzc.put(zzkv, zzli);
    }
}
