package com.google.android.gms.internal.measurement;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzfx extends zzlb implements zzmj {
    /* access modifiers changed from: private */
    public static final zzfx zza;
    private int zzd;
    private String zze = "";
    private String zzf = "";
    private long zzg;
    private float zzh;
    private double zzi;
    /* access modifiers changed from: private */
    public zzli zzj = zzlb.zzbH();

    static {
        zzfx zzfx = new zzfx();
        zza = zzfx;
        zzlb.zzbO(zzfx.class, zzfx);
    }

    private zzfx() {
    }

    public static zzfw zze() {
        return (zzfw) zza.zzbA();
    }

    static /* synthetic */ void zzj(zzfx zzfx, String str) {
        str.getClass();
        zzfx.zzd |= 1;
        zzfx.zze = str;
    }

    static /* synthetic */ void zzk(zzfx zzfx, String str) {
        str.getClass();
        zzfx.zzd |= 2;
        zzfx.zzf = str;
    }

    static /* synthetic */ void zzm(zzfx zzfx) {
        zzfx.zzd &= -3;
        zzfx.zzf = zza.zzf;
    }

    static /* synthetic */ void zzn(zzfx zzfx, long j) {
        zzfx.zzd |= 4;
        zzfx.zzg = j;
    }

    static /* synthetic */ void zzo(zzfx zzfx) {
        zzfx.zzd &= -5;
        zzfx.zzg = 0;
    }

    static /* synthetic */ void zzp(zzfx zzfx, double d) {
        zzfx.zzd |= 16;
        zzfx.zzi = d;
    }

    static /* synthetic */ void zzq(zzfx zzfx) {
        zzfx.zzd &= -17;
        zzfx.zzi = 0.0d;
    }

    static /* synthetic */ void zzr(zzfx zzfx, zzfx zzfx2) {
        zzfx2.getClass();
        zzfx.zzz();
        zzfx.zzj.add(zzfx2);
    }

    static /* synthetic */ void zzs(zzfx zzfx, Iterable iterable) {
        zzfx.zzz();
        zzjk.zzbw(iterable, zzfx.zzj);
    }

    private final void zzz() {
        zzli zzli = this.zzj;
        if (!zzli.zzc()) {
            this.zzj = zzlb.zzbI(zzli);
        }
    }

    public final double zza() {
        return this.zzi;
    }

    public final float zzb() {
        return this.zzh;
    }

    public final int zzc() {
        return this.zzj.size();
    }

    public final long zzd() {
        return this.zzg;
    }

    public final String zzg() {
        return this.zze;
    }

    public final String zzh() {
        return this.zzf;
    }

    public final List zzi() {
        return this.zzj;
    }

    public final boolean zzu() {
        return (this.zzd & 16) != 0;
    }

    public final boolean zzv() {
        return (this.zzd & 8) != 0;
    }

    public final boolean zzw() {
        return (this.zzd & 4) != 0;
    }

    public final boolean zzx() {
        return (this.zzd & 1) != 0;
    }

    public final boolean zzy() {
        return (this.zzd & 2) != 0;
    }

    /* access modifiers changed from: protected */
    public final Object zzl(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzlb.zzbL(zza, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0001\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဂ\u0002\u0004ခ\u0003\u0005က\u0004\u0006\u001b", new Object[]{"zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", zzfx.class});
            case 3:
                return new zzfx();
            case 4:
                return new zzfw((zzfk) null);
            case 5:
                return zza;
            default:
                return null;
        }
    }
}
