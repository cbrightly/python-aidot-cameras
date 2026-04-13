package com.google.android.gms.internal.measurement;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzff extends zzlb implements zzmj {
    /* access modifiers changed from: private */
    public static final zzff zza;
    private int zzd;
    private long zze;
    private String zzf = "";
    private int zzg;
    private zzli zzh = zzlb.zzbH();
    private zzli zzi = zzlb.zzbH();
    /* access modifiers changed from: private */
    public zzli zzj = zzlb.zzbH();
    private String zzk = "";
    private boolean zzl;
    private zzli zzm = zzlb.zzbH();
    private zzli zzn = zzlb.zzbH();
    private String zzo = "";
    private String zzp = "";
    private String zzq = "";

    static {
        zzff zzff = new zzff();
        zza = zzff;
        zzlb.zzbO(zzff.class, zzff);
    }

    private zzff() {
    }

    public static zzfe zze() {
        return (zzfe) zza.zzbA();
    }

    public static zzff zzg() {
        return zza;
    }

    static /* synthetic */ void zzq(zzff zzff, int i, zzfd zzfd) {
        zzfd.getClass();
        zzli zzli = zzff.zzi;
        if (!zzli.zzc()) {
            zzff.zzi = zzlb.zzbI(zzli);
        }
        zzff.zzi.set(i, zzfd);
    }

    public final int zza() {
        return this.zzm.size();
    }

    public final int zzb() {
        return this.zzi.size();
    }

    public final long zzc() {
        return this.zze;
    }

    public final zzfd zzd(int i) {
        return (zzfd) this.zzi.get(i);
    }

    public final String zzh() {
        return this.zzf;
    }

    public final String zzi() {
        return this.zzq;
    }

    public final String zzj() {
        return this.zzp;
    }

    public final String zzk() {
        return this.zzo;
    }

    public final List zzm() {
        return this.zzj;
    }

    public final List zzn() {
        return this.zzn;
    }

    public final List zzo() {
        return this.zzm;
    }

    public final List zzp() {
        return this.zzh;
    }

    public final boolean zzs() {
        return this.zzl;
    }

    public final boolean zzt() {
        return (this.zzd & 2) != 0;
    }

    public final boolean zzu() {
        return (this.zzd & 1) != 0;
    }

    /* access modifiers changed from: protected */
    public final Object zzl(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzlb.zzbL(zza, "\u0001\r\u0000\u0001\u0001\r\r\u0000\u0005\u0000\u0001ဂ\u0000\u0002ဈ\u0001\u0003င\u0002\u0004\u001b\u0005\u001b\u0006\u001b\u0007ဈ\u0003\bဇ\u0004\t\u001b\n\u001b\u000bဈ\u0005\fဈ\u0006\rဈ\u0007", new Object[]{"zzd", "zze", "zzf", "zzg", "zzh", zzfj.class, "zzi", zzfd.class, "zzj", zzei.class, "zzk", "zzl", "zzm", zzgt.class, "zzn", zzfb.class, "zzo", "zzp", "zzq"});
            case 3:
                return new zzff();
            case 4:
                return new zzfe((zzez) null);
            case 5:
                return zza;
            default:
                return null;
        }
    }
}
