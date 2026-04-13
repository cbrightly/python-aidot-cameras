package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzaab extends zzafz implements zzahi {
    /* access modifiers changed from: private */
    public static final zzaab zzb;
    private int zzd;
    private int zze;
    private int zzf = 1;
    private boolean zzg;
    private boolean zzh;
    private boolean zzi;
    private int zzj;
    private int zzk;
    private int zzl;
    private int zzm;
    private int zzn;
    private int zzo;
    private int zzp;
    private boolean zzq;
    private int zzr;
    private int zzs;
    private int zzt;

    static {
        zzaab zzaab = new zzaab();
        zzb = zzaab;
        zzafz.zzI(zzaab.class, zzaab);
    }

    private zzaab() {
    }

    public static zzzy zza() {
        return (zzzy) zzb.zzw();
    }

    static /* synthetic */ void zzd(zzaab zzaab, boolean z) {
        zzaab.zzd |= 4;
        zzaab.zzg = z;
    }

    static /* synthetic */ void zze(zzaab zzaab, boolean z) {
        zzaab.zzd |= 8;
        zzaab.zzh = z;
    }

    static /* synthetic */ void zzf(zzaab zzaab, boolean z) {
        zzaab.zzd |= 16;
        zzaab.zzi = z;
    }

    static /* synthetic */ void zzg(zzaab zzaab, int i) {
        zzaab.zzd |= 32;
        zzaab.zzj = i;
    }

    static /* synthetic */ void zzh(zzaab zzaab, int i) {
        zzaab.zzd |= 64;
        zzaab.zzk = i;
    }

    static /* synthetic */ void zzi(zzaab zzaab, int i) {
        zzaab.zzd |= 128;
        zzaab.zzl = i;
    }

    static /* synthetic */ void zzj(zzaab zzaab, int i) {
        zzaab.zzd |= 256;
        zzaab.zzm = i;
    }

    static /* synthetic */ void zzk(zzaab zzaab, int i) {
        zzaab.zzd |= 512;
        zzaab.zzn = i;
    }

    static /* synthetic */ void zzl(zzaab zzaab, int i) {
        zzaab.zzd |= 1024;
        zzaab.zzo = i;
    }

    static /* synthetic */ void zzm(zzaab zzaab, int i) {
        zzaab.zzd |= 2048;
        zzaab.zzp = i;
    }

    static /* synthetic */ void zzn(zzaab zzaab, boolean z) {
        zzaab.zzd |= 4096;
        zzaab.zzq = z;
    }

    static /* synthetic */ void zzo(zzaab zzaab, int i) {
        zzaab.zzd |= 8192;
        zzaab.zzr = i;
    }

    static /* synthetic */ void zzp(zzaab zzaab, int i) {
        zzaab.zze = i - 1;
        zzaab.zzd |= 1;
    }

    static /* synthetic */ void zzq(zzaab zzaab, int i) {
        zzaab.zzf = i;
        zzaab.zzd |= 2;
    }

    /* access modifiers changed from: protected */
    public final Object zzb(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzafz.zzF(zzb, "\u0001\u0010\u0000\u0001\u0001\u0011\u0010\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဌ\u0001\u0003ဇ\u0002\u0004ဇ\u0003\u0005ဇ\u0004\u0006ဋ\u0005\u0007ဋ\u0006\bဋ\u0007\nဋ\t\u000bဋ\n\fဋ\u000b\rဇ\f\u000eဋ\r\u000fဋ\b\u0010ဋ\u000e\u0011ဌ\u000f", new Object[]{"zzd", "zze", zzaaa.zza, "zzf", zzzx.zza, "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzn", "zzo", "zzp", "zzq", "zzr", "zzm", "zzs", "zzt", zzzz.zza});
            case 3:
                return new zzaab();
            case 4:
                return new zzzy((zzya) null);
            case 5:
                return zzb;
            default:
                return null;
        }
    }
}
