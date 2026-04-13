package com.google.android.gms.internal.measurement;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzgi extends zzlb implements zzmj {
    /* access modifiers changed from: private */
    public static final zzgi zza;
    /* access modifiers changed from: private */
    public zzlh zzd = zzlb.zzbF();
    /* access modifiers changed from: private */
    public zzlh zze = zzlb.zzbF();
    /* access modifiers changed from: private */
    public zzli zzf = zzlb.zzbH();
    /* access modifiers changed from: private */
    public zzli zzg = zzlb.zzbH();

    static {
        zzgi zzgi = new zzgi();
        zza = zzgi;
        zzlb.zzbO(zzgi.class, zzgi);
    }

    private zzgi() {
    }

    public static zzgh zze() {
        return (zzgh) zza.zzbA();
    }

    public static zzgi zzg() {
        return zza;
    }

    static /* synthetic */ void zzm(zzgi zzgi, Iterable iterable) {
        zzlh zzlh = zzgi.zzd;
        if (!zzlh.zzc()) {
            zzgi.zzd = zzlb.zzbG(zzlh);
        }
        zzjk.zzbw(iterable, zzgi.zzd);
    }

    static /* synthetic */ void zzo(zzgi zzgi, Iterable iterable) {
        zzlh zzlh = zzgi.zze;
        if (!zzlh.zzc()) {
            zzgi.zze = zzlb.zzbG(zzlh);
        }
        zzjk.zzbw(iterable, zzgi.zze);
    }

    static /* synthetic */ void zzq(zzgi zzgi, Iterable iterable) {
        zzli zzli = zzgi.zzf;
        if (!zzli.zzc()) {
            zzgi.zzf = zzlb.zzbI(zzli);
        }
        zzjk.zzbw(iterable, zzgi.zzf);
    }

    static /* synthetic */ void zzs(zzgi zzgi, Iterable iterable) {
        zzli zzli = zzgi.zzg;
        if (!zzli.zzc()) {
            zzgi.zzg = zzlb.zzbI(zzli);
        }
        zzjk.zzbw(iterable, zzgi.zzg);
    }

    public final int zza() {
        return this.zzf.size();
    }

    public final int zzb() {
        return this.zze.size();
    }

    public final int zzc() {
        return this.zzg.size();
    }

    public final int zzd() {
        return this.zzd.size();
    }

    public final List zzh() {
        return this.zzf;
    }

    public final List zzi() {
        return this.zze;
    }

    public final List zzj() {
        return this.zzg;
    }

    public final List zzk() {
        return this.zzd;
    }

    /* access modifiers changed from: protected */
    public final Object zzl(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzlb.zzbL(zza, "\u0001\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0004\u0000\u0001\u0015\u0002\u0015\u0003\u001b\u0004\u001b", new Object[]{"zzd", "zze", "zzf", zzfr.class, "zzg", zzgk.class});
            case 3:
                return new zzgi();
            case 4:
                return new zzgh((zzfk) null);
            case 5:
                return zza;
            default:
                return null;
        }
    }
}
