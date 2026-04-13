package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzfe extends zzkx implements zzmj {
    private zzfe() {
        super(zzff.zza);
    }

    public final int zza() {
        return ((zzff) this.zza).zzb();
    }

    public final zzfd zzb(int i) {
        return ((zzff) this.zza).zzd(i);
    }

    public final zzfe zzc() {
        zzaH();
        ((zzff) this.zza).zzj = zzlb.zzbH();
        return this;
    }

    public final zzfe zzd(int i, zzfc zzfc) {
        zzaH();
        zzff.zzq((zzff) this.zza, i, (zzfd) zzfc.zzaD());
        return this;
    }

    public final String zze() {
        return ((zzff) this.zza).zzk();
    }

    public final List zzf() {
        return Collections.unmodifiableList(((zzff) this.zza).zzm());
    }

    public final List zzg() {
        return Collections.unmodifiableList(((zzff) this.zza).zzn());
    }

    /* synthetic */ zzfe(zzez zzez) {
        super(zzff.zza);
    }
}
