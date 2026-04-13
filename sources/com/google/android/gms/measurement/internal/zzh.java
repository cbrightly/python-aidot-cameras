package com.google.android.gms.measurement.internal;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzh {
    private long zzA;
    private long zzB;
    private long zzC;
    @Nullable
    private String zzD;
    private boolean zzE;
    private long zzF;
    private long zzG;
    private final zzge zza;
    private final String zzb;
    @Nullable
    private String zzc;
    @Nullable
    private String zzd;
    @Nullable
    private String zze;
    @Nullable
    private String zzf;
    private long zzg;
    private long zzh;
    private long zzi;
    @Nullable
    private String zzj;
    private long zzk;
    @Nullable
    private String zzl;
    private long zzm;
    private long zzn;
    private boolean zzo;
    private boolean zzp;
    @Nullable
    private String zzq;
    @Nullable
    private Boolean zzr;
    private long zzs;
    @Nullable
    private List zzt;
    @Nullable
    private String zzu;
    private boolean zzv;
    private long zzw;
    private long zzx;
    private long zzy;
    private long zzz;

    @WorkerThread
    zzh(zzge zzge, String str) {
        Preconditions.checkNotNull(zzge);
        Preconditions.checkNotEmpty(str);
        this.zza = zzge;
        this.zzb = str;
        zzge.zzaB().zzg();
    }

    @WorkerThread
    @Nullable
    public final String zzA() {
        this.zza.zzaB().zzg();
        return this.zzD;
    }

    @WorkerThread
    @Nullable
    public final String zzB() {
        this.zza.zzaB().zzg();
        return this.zze;
    }

    @WorkerThread
    @Nullable
    public final String zzC() {
        this.zza.zzaB().zzg();
        return this.zzu;
    }

    @WorkerThread
    @Nullable
    public final List zzD() {
        this.zza.zzaB().zzg();
        return this.zzt;
    }

    @WorkerThread
    public final void zzE() {
        this.zza.zzaB().zzg();
        this.zzE = false;
    }

    @WorkerThread
    public final void zzF() {
        this.zza.zzaB().zzg();
        long j = this.zzg + 1;
        if (j > 2147483647L) {
            this.zza.zzaA().zzk().zzb("Bundle index overflow. appId", zzeu.zzn(this.zzb));
            j = 0;
        }
        this.zzE = true;
        this.zzg = j;
    }

    @WorkerThread
    public final void zzG(@Nullable String str) {
        this.zza.zzaB().zzg();
        if (true == TextUtils.isEmpty(str)) {
            str = null;
        }
        this.zzE |= true ^ zzg.zza(this.zzq, str);
        this.zzq = str;
    }

    @WorkerThread
    public final void zzH(boolean z) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzp != z;
        this.zzp = z;
    }

    @WorkerThread
    public final void zzI(@Nullable String str) {
        this.zza.zzaB().zzg();
        this.zzE |= !zzg.zza(this.zzc, str);
        this.zzc = str;
    }

    @WorkerThread
    public final void zzJ(@Nullable String str) {
        this.zza.zzaB().zzg();
        this.zzE |= !zzg.zza(this.zzl, str);
        this.zzl = str;
    }

    @WorkerThread
    public final void zzK(@Nullable String str) {
        this.zza.zzaB().zzg();
        this.zzE |= !zzg.zza(this.zzj, str);
        this.zzj = str;
    }

    @WorkerThread
    public final void zzL(long j) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzk != j;
        this.zzk = j;
    }

    @WorkerThread
    public final void zzM(long j) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzF != j;
        this.zzF = j;
    }

    @WorkerThread
    public final void zzN(long j) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzA != j;
        this.zzA = j;
    }

    @WorkerThread
    public final void zzO(long j) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzB != j;
        this.zzB = j;
    }

    @WorkerThread
    public final void zzP(long j) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzz != j;
        this.zzz = j;
    }

    @WorkerThread
    public final void zzQ(long j) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzy != j;
        this.zzy = j;
    }

    @WorkerThread
    public final void zzR(long j) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzC != j;
        this.zzC = j;
    }

    @WorkerThread
    public final void zzS(long j) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzx != j;
        this.zzx = j;
    }

    @WorkerThread
    public final void zzT(long j) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzn != j;
        this.zzn = j;
    }

    @WorkerThread
    public final void zzU(long j) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzs != j;
        this.zzs = j;
    }

    @WorkerThread
    public final void zzV(long j) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzG != j;
        this.zzG = j;
    }

    @WorkerThread
    public final void zzW(@Nullable String str) {
        this.zza.zzaB().zzg();
        this.zzE |= !zzg.zza(this.zzf, str);
        this.zzf = str;
    }

    @WorkerThread
    public final void zzX(@Nullable String str) {
        this.zza.zzaB().zzg();
        if (true == TextUtils.isEmpty(str)) {
            str = null;
        }
        this.zzE |= true ^ zzg.zza(this.zzd, str);
        this.zzd = str;
    }

    @WorkerThread
    public final void zzY(long j) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzm != j;
        this.zzm = j;
    }

    @WorkerThread
    public final void zzZ(@Nullable String str) {
        this.zza.zzaB().zzg();
        this.zzE |= !zzg.zza(this.zzD, str);
        this.zzD = str;
    }

    @WorkerThread
    public final long zza() {
        this.zza.zzaB().zzg();
        return 0;
    }

    @WorkerThread
    public final void zzaa(long j) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzi != j;
        this.zzi = j;
    }

    @WorkerThread
    public final void zzab(long j) {
        boolean z;
        boolean z2 = true;
        if (j >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        this.zza.zzaB().zzg();
        boolean z3 = this.zzE;
        if (this.zzg == j) {
            z2 = false;
        }
        this.zzE = z3 | z2;
        this.zzg = j;
    }

    @WorkerThread
    public final void zzac(long j) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzh != j;
        this.zzh = j;
    }

    @WorkerThread
    public final void zzad(boolean z) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzo != z;
        this.zzo = z;
    }

    @WorkerThread
    public final void zzae(@Nullable Boolean bool) {
        this.zza.zzaB().zzg();
        this.zzE |= !zzg.zza(this.zzr, bool);
        this.zzr = bool;
    }

    @WorkerThread
    public final void zzaf(@Nullable String str) {
        this.zza.zzaB().zzg();
        this.zzE |= !zzg.zza(this.zze, str);
        this.zze = str;
    }

    @WorkerThread
    public final void zzag(@Nullable List list) {
        this.zza.zzaB().zzg();
        if (!zzg.zza(this.zzt, list)) {
            this.zzE = true;
            this.zzt = list != null ? new ArrayList(list) : null;
        }
    }

    @WorkerThread
    public final void zzah(@Nullable String str) {
        this.zza.zzaB().zzg();
        this.zzE |= !zzg.zza(this.zzu, str);
        this.zzu = str;
    }

    @WorkerThread
    public final void zzai(boolean z) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzv != z;
        this.zzv = z;
    }

    @WorkerThread
    public final void zzaj(long j) {
        this.zza.zzaB().zzg();
        this.zzE |= this.zzw != j;
        this.zzw = j;
    }

    @WorkerThread
    public final boolean zzak() {
        this.zza.zzaB().zzg();
        return this.zzp;
    }

    @WorkerThread
    public final boolean zzal() {
        this.zza.zzaB().zzg();
        return this.zzo;
    }

    @WorkerThread
    public final boolean zzam() {
        this.zza.zzaB().zzg();
        return this.zzE;
    }

    @WorkerThread
    public final boolean zzan() {
        this.zza.zzaB().zzg();
        return this.zzv;
    }

    @WorkerThread
    public final long zzb() {
        this.zza.zzaB().zzg();
        return this.zzk;
    }

    @WorkerThread
    public final long zzc() {
        this.zza.zzaB().zzg();
        return this.zzF;
    }

    @WorkerThread
    public final long zzd() {
        this.zza.zzaB().zzg();
        return this.zzA;
    }

    @WorkerThread
    public final long zze() {
        this.zza.zzaB().zzg();
        return this.zzB;
    }

    @WorkerThread
    public final long zzf() {
        this.zza.zzaB().zzg();
        return this.zzz;
    }

    @WorkerThread
    public final long zzg() {
        this.zza.zzaB().zzg();
        return this.zzy;
    }

    @WorkerThread
    public final long zzh() {
        this.zza.zzaB().zzg();
        return this.zzC;
    }

    @WorkerThread
    public final long zzi() {
        this.zza.zzaB().zzg();
        return this.zzx;
    }

    @WorkerThread
    public final long zzj() {
        this.zza.zzaB().zzg();
        return this.zzn;
    }

    @WorkerThread
    public final long zzk() {
        this.zza.zzaB().zzg();
        return this.zzs;
    }

    @WorkerThread
    public final long zzl() {
        this.zza.zzaB().zzg();
        return this.zzG;
    }

    @WorkerThread
    public final long zzm() {
        this.zza.zzaB().zzg();
        return this.zzm;
    }

    @WorkerThread
    public final long zzn() {
        this.zza.zzaB().zzg();
        return this.zzi;
    }

    @WorkerThread
    public final long zzo() {
        this.zza.zzaB().zzg();
        return this.zzg;
    }

    @WorkerThread
    public final long zzp() {
        this.zza.zzaB().zzg();
        return this.zzh;
    }

    @WorkerThread
    public final long zzq() {
        this.zza.zzaB().zzg();
        return this.zzw;
    }

    @WorkerThread
    @Nullable
    public final Boolean zzr() {
        this.zza.zzaB().zzg();
        return this.zzr;
    }

    @WorkerThread
    @Nullable
    public final String zzs() {
        this.zza.zzaB().zzg();
        return this.zzq;
    }

    @WorkerThread
    @Nullable
    public final String zzt() {
        this.zza.zzaB().zzg();
        String str = this.zzD;
        zzZ((String) null);
        return str;
    }

    @WorkerThread
    public final String zzu() {
        this.zza.zzaB().zzg();
        return this.zzb;
    }

    @WorkerThread
    @Nullable
    public final String zzv() {
        this.zza.zzaB().zzg();
        return this.zzc;
    }

    @WorkerThread
    @Nullable
    public final String zzw() {
        this.zza.zzaB().zzg();
        return this.zzl;
    }

    @WorkerThread
    @Nullable
    public final String zzx() {
        this.zza.zzaB().zzg();
        return this.zzj;
    }

    @WorkerThread
    @Nullable
    public final String zzy() {
        this.zza.zzaB().zzg();
        return this.zzf;
    }

    @WorkerThread
    @Nullable
    public final String zzz() {
        this.zza.zzaB().zzg();
        return this.zzd;
    }
}
