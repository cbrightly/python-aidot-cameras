package com.google.android.gms.internal.measurement;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzek extends zzlb implements zzmj {
    /* access modifiers changed from: private */
    public static final zzek zza;
    private int zzd;
    private int zze;
    private String zzf = "";
    private zzli zzg = zzlb.zzbH();
    private boolean zzh;
    private zzer zzi;
    private boolean zzj;
    private boolean zzk;
    private boolean zzl;

    static {
        zzek zzek = new zzek();
        zza = zzek;
        zzlb.zzbO(zzek.class, zzek);
    }

    private zzek() {
    }

    public static zzej zzc() {
        return (zzej) zza.zzbA();
    }

    static /* synthetic */ void zzi(zzek zzek, String str) {
        zzek.zzd |= 2;
        zzek.zzf = str;
    }

    static /* synthetic */ void zzj(zzek zzek, int i, zzem zzem) {
        zzem.getClass();
        zzli zzli = zzek.zzg;
        if (!zzli.zzc()) {
            zzek.zzg = zzlb.zzbI(zzli);
        }
        zzek.zzg.set(i, zzem);
    }

    public final int zza() {
        return this.zzg.size();
    }

    public final int zzb() {
        return this.zze;
    }

    public final zzem zze(int i) {
        return (zzem) this.zzg.get(i);
    }

    public final zzer zzf() {
        zzer zzer = this.zzi;
        return zzer == null ? zzer.zzb() : zzer;
    }

    public final String zzg() {
        return this.zzf;
    }

    public final List zzh() {
        return this.zzg;
    }

    public final boolean zzk() {
        return this.zzj;
    }

    public final boolean zzm() {
        return this.zzk;
    }

    public final boolean zzn() {
        return this.zzl;
    }

    public final boolean zzo() {
        return (this.zzd & 8) != 0;
    }

    public final boolean zzp() {
        return (this.zzd & 1) != 0;
    }

    public final boolean zzq() {
        return (this.zzd & 64) != 0;
    }

    /* access modifiers changed from: protected */
    public final Object zzl(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzlb.zzbL(zza, "\u0001\b\u0000\u0001\u0001\b\b\u0000\u0001\u0000\u0001င\u0000\u0002ဈ\u0001\u0003\u001b\u0004ဇ\u0002\u0005ဉ\u0003\u0006ဇ\u0004\u0007ဇ\u0005\bဇ\u0006", new Object[]{"zzd", "zze", "zzf", "zzg", zzem.class, "zzh", "zzi", "zzj", "zzk", "zzl"});
            case 3:
                return new zzek();
            case 4:
                return new zzej((zzeg) null);
            case 5:
                return zza;
            default:
                return null;
        }
    }
}
