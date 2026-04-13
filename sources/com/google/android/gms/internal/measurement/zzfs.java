package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzfs extends zzkx implements zzmj {
    private zzfs() {
        super(zzft.zza);
    }

    public final int zza() {
        return ((zzft) this.zza).zzb();
    }

    public final long zzb() {
        return ((zzft) this.zza).zzc();
    }

    public final long zzc() {
        return ((zzft) this.zza).zzd();
    }

    public final zzfs zzd(Iterable iterable) {
        zzaH();
        zzft.zzm((zzft) this.zza, iterable);
        return this;
    }

    public final zzfs zze(zzfw zzfw) {
        zzaH();
        zzft.zzk((zzft) this.zza, (zzfx) zzfw.zzaD());
        return this;
    }

    public final zzfs zzf(zzfx zzfx) {
        zzaH();
        zzft.zzk((zzft) this.zza, zzfx);
        return this;
    }

    public final zzfs zzg() {
        zzaH();
        ((zzft) this.zza).zze = zzlb.zzbH();
        return this;
    }

    public final zzfs zzh(int i) {
        zzaH();
        zzft.zzo((zzft) this.zza, i);
        return this;
    }

    public final zzfs zzi(String str) {
        zzaH();
        zzft.zzp((zzft) this.zza, str);
        return this;
    }

    public final zzfs zzj(int i, zzfw zzfw) {
        zzaH();
        zzft.zzj((zzft) this.zza, i, (zzfx) zzfw.zzaD());
        return this;
    }

    public final zzfs zzk(int i, zzfx zzfx) {
        zzaH();
        zzft.zzj((zzft) this.zza, i, zzfx);
        return this;
    }

    public final zzfs zzl(long j) {
        zzaH();
        zzft.zzr((zzft) this.zza, j);
        return this;
    }

    public final zzfs zzm(long j) {
        zzaH();
        zzft.zzq((zzft) this.zza, j);
        return this;
    }

    public final zzfx zzn(int i) {
        return ((zzft) this.zza).zzg(i);
    }

    public final String zzo() {
        return ((zzft) this.zza).zzh();
    }

    public final List zzp() {
        return Collections.unmodifiableList(((zzft) this.zza).zzi());
    }

    public final boolean zzq() {
        return ((zzft) this.zza).zzu();
    }

    /* synthetic */ zzfs(zzfk zzfk) {
        super(zzft.zza);
    }
}
