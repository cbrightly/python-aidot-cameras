package com.google.android.gms.internal.vision;

import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzig implements zzld {
    private final zzif zza;
    private int zzb;
    private int zzc;
    private int zzd = 0;

    public static zzig zza(zzif zzif) {
        zzig zzig = zzif.zzc;
        if (zzig != null) {
            return zzig;
        }
        return new zzig(zzif);
    }

    private zzig(zzif zzif) {
        zzif zzif2 = (zzif) zzjf.zza(zzif, "input");
        this.zza = zzif2;
        zzif2.zzc = this;
    }

    public final int zza() {
        int i = this.zzd;
        if (i != 0) {
            this.zzb = i;
            this.zzd = 0;
        } else {
            this.zzb = this.zza.zza();
        }
        int i2 = this.zzb;
        if (i2 == 0 || i2 == this.zzc) {
            return Integer.MAX_VALUE;
        }
        return i2 >>> 3;
    }

    public final int zzb() {
        return this.zzb;
    }

    public final boolean zzc() {
        int i;
        if (this.zza.zzt() || (i = this.zzb) == this.zzc) {
            return false;
        }
        return this.zza.zzb(i);
    }

    private final void zza(int i) {
        if ((this.zzb & 7) != i) {
            throw zzjk.zzf();
        }
    }

    public final double zzd() {
        zza(1);
        return this.zza.zzb();
    }

    public final float zze() {
        zza(5);
        return this.zza.zzc();
    }

    public final long zzf() {
        zza(0);
        return this.zza.zzd();
    }

    public final long zzg() {
        zza(0);
        return this.zza.zze();
    }

    public final int zzh() {
        zza(0);
        return this.zza.zzf();
    }

    public final long zzi() {
        zza(1);
        return this.zza.zzg();
    }

    public final int zzj() {
        zza(5);
        return this.zza.zzh();
    }

    public final boolean zzk() {
        zza(0);
        return this.zza.zzi();
    }

    public final String zzl() {
        zza(2);
        return this.zza.zzj();
    }

    public final String zzm() {
        zza(2);
        return this.zza.zzk();
    }

    public final <T> T zza(Class<T> cls, zzio zzio) {
        zza(2);
        return zzc(zzky.zza().zza(cls), zzio);
    }

    public final <T> T zza(zzlc<T> zzlc, zzio zzio) {
        zza(2);
        return zzc(zzlc, zzio);
    }

    public final <T> T zzb(Class<T> cls, zzio zzio) {
        zza(3);
        return zzd(zzky.zza().zza(cls), zzio);
    }

    public final <T> T zzb(zzlc<T> zzlc, zzio zzio) {
        zza(3);
        return zzd(zzlc, zzio);
    }

    private final <T> T zzc(zzlc<T> zzlc, zzio zzio) {
        int zzm = this.zza.zzm();
        zzif zzif = this.zza;
        if (zzif.zza < zzif.zzb) {
            int zzc2 = zzif.zzc(zzm);
            T zza2 = zzlc.zza();
            this.zza.zza++;
            zzlc.zza(zza2, this, zzio);
            zzlc.zzc(zza2);
            this.zza.zza(0);
            zzif zzif2 = this.zza;
            zzif2.zza--;
            zzif2.zzd(zzc2);
            return zza2;
        }
        throw new zzjk("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }

    private final <T> T zzd(zzlc<T> zzlc, zzio zzio) {
        int i = this.zzc;
        this.zzc = ((this.zzb >>> 3) << 3) | 4;
        try {
            T zza2 = zzlc.zza();
            zzlc.zza(zza2, this, zzio);
            zzlc.zzc(zza2);
            if (this.zzb == this.zzc) {
                return zza2;
            }
            throw zzjk.zzg();
        } finally {
            this.zzc = i;
        }
    }

    public final zzht zzn() {
        zza(2);
        return this.zza.zzl();
    }

    public final int zzo() {
        zza(0);
        return this.zza.zzm();
    }

    public final int zzp() {
        zza(0);
        return this.zza.zzn();
    }

    public final int zzq() {
        zza(5);
        return this.zza.zzo();
    }

    public final long zzr() {
        zza(1);
        return this.zza.zzp();
    }

    public final int zzs() {
        zza(0);
        return this.zza.zzq();
    }

    public final long zzt() {
        zza(0);
        return this.zza.zzr();
    }

    public final void zza(List<Double> list) {
        int zza2;
        int zza3;
        if (list instanceof zzin) {
            zzin zzin = (zzin) list;
            switch (this.zzb & 7) {
                case 1:
                    break;
                case 2:
                    int zzm = this.zza.zzm();
                    zzb(zzm);
                    int zzu = this.zza.zzu() + zzm;
                    do {
                        zzin.zza(this.zza.zzb());
                    } while (this.zza.zzu() < zzu);
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzin.zza(this.zza.zzb());
                if (!this.zza.zzt()) {
                    zza3 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza3 == this.zzb);
            this.zzd = zza3;
            return;
        }
        switch (this.zzb & 7) {
            case 1:
                break;
            case 2:
                int zzm2 = this.zza.zzm();
                zzb(zzm2);
                int zzu2 = this.zza.zzu() + zzm2;
                do {
                    list.add(Double.valueOf(this.zza.zzb()));
                } while (this.zza.zzu() < zzu2);
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Double.valueOf(this.zza.zzb()));
            if (!this.zza.zzt()) {
                zza2 = this.zza.zza();
            } else {
                return;
            }
        } while (zza2 == this.zzb);
        this.zzd = zza2;
    }

    public final void zzb(List<Float> list) {
        int zza2;
        int zza3;
        if (list instanceof zzja) {
            zzja zzja = (zzja) list;
            switch (this.zzb & 7) {
                case 2:
                    int zzm = this.zza.zzm();
                    zzc(zzm);
                    int zzu = this.zza.zzu() + zzm;
                    do {
                        zzja.zza(this.zza.zzc());
                    } while (this.zza.zzu() < zzu);
                    return;
                case 5:
                    break;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzja.zza(this.zza.zzc());
                if (!this.zza.zzt()) {
                    zza3 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza3 == this.zzb);
            this.zzd = zza3;
            return;
        }
        switch (this.zzb & 7) {
            case 2:
                int zzm2 = this.zza.zzm();
                zzc(zzm2);
                int zzu2 = this.zza.zzu() + zzm2;
                do {
                    list.add(Float.valueOf(this.zza.zzc()));
                } while (this.zza.zzu() < zzu2);
                return;
            case 5:
                break;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Float.valueOf(this.zza.zzc()));
            if (!this.zza.zzt()) {
                zza2 = this.zza.zza();
            } else {
                return;
            }
        } while (zza2 == this.zzb);
        this.zzd = zza2;
    }

    public final void zzc(List<Long> list) {
        int zza2;
        int zza3;
        if (list instanceof zzjy) {
            zzjy zzjy = (zzjy) list;
            switch (this.zzb & 7) {
                case 0:
                    break;
                case 2:
                    int zzu = this.zza.zzu() + this.zza.zzm();
                    do {
                        zzjy.zza(this.zza.zzd());
                    } while (this.zza.zzu() < zzu);
                    zzd(zzu);
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjy.zza(this.zza.zzd());
                if (!this.zza.zzt()) {
                    zza3 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza3 == this.zzb);
            this.zzd = zza3;
            return;
        }
        switch (this.zzb & 7) {
            case 0:
                break;
            case 2:
                int zzu2 = this.zza.zzu() + this.zza.zzm();
                do {
                    list.add(Long.valueOf(this.zza.zzd()));
                } while (this.zza.zzu() < zzu2);
                zzd(zzu2);
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Long.valueOf(this.zza.zzd()));
            if (!this.zza.zzt()) {
                zza2 = this.zza.zza();
            } else {
                return;
            }
        } while (zza2 == this.zzb);
        this.zzd = zza2;
    }

    public final void zzd(List<Long> list) {
        int zza2;
        int zza3;
        if (list instanceof zzjy) {
            zzjy zzjy = (zzjy) list;
            switch (this.zzb & 7) {
                case 0:
                    break;
                case 2:
                    int zzu = this.zza.zzu() + this.zza.zzm();
                    do {
                        zzjy.zza(this.zza.zze());
                    } while (this.zza.zzu() < zzu);
                    zzd(zzu);
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjy.zza(this.zza.zze());
                if (!this.zza.zzt()) {
                    zza3 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza3 == this.zzb);
            this.zzd = zza3;
            return;
        }
        switch (this.zzb & 7) {
            case 0:
                break;
            case 2:
                int zzu2 = this.zza.zzu() + this.zza.zzm();
                do {
                    list.add(Long.valueOf(this.zza.zze()));
                } while (this.zza.zzu() < zzu2);
                zzd(zzu2);
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Long.valueOf(this.zza.zze()));
            if (!this.zza.zzt()) {
                zza2 = this.zza.zza();
            } else {
                return;
            }
        } while (zza2 == this.zzb);
        this.zzd = zza2;
    }

    public final void zze(List<Integer> list) {
        int zza2;
        int zza3;
        if (list instanceof zzjd) {
            zzjd zzjd = (zzjd) list;
            switch (this.zzb & 7) {
                case 0:
                    break;
                case 2:
                    int zzu = this.zza.zzu() + this.zza.zzm();
                    do {
                        zzjd.zzc(this.zza.zzf());
                    } while (this.zza.zzu() < zzu);
                    zzd(zzu);
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjd.zzc(this.zza.zzf());
                if (!this.zza.zzt()) {
                    zza3 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza3 == this.zzb);
            this.zzd = zza3;
            return;
        }
        switch (this.zzb & 7) {
            case 0:
                break;
            case 2:
                int zzu2 = this.zza.zzu() + this.zza.zzm();
                do {
                    list.add(Integer.valueOf(this.zza.zzf()));
                } while (this.zza.zzu() < zzu2);
                zzd(zzu2);
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Integer.valueOf(this.zza.zzf()));
            if (!this.zza.zzt()) {
                zza2 = this.zza.zza();
            } else {
                return;
            }
        } while (zza2 == this.zzb);
        this.zzd = zza2;
    }

    public final void zzf(List<Long> list) {
        int zza2;
        int zza3;
        if (list instanceof zzjy) {
            zzjy zzjy = (zzjy) list;
            switch (this.zzb & 7) {
                case 1:
                    break;
                case 2:
                    int zzm = this.zza.zzm();
                    zzb(zzm);
                    int zzu = this.zza.zzu() + zzm;
                    do {
                        zzjy.zza(this.zza.zzg());
                    } while (this.zza.zzu() < zzu);
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjy.zza(this.zza.zzg());
                if (!this.zza.zzt()) {
                    zza3 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza3 == this.zzb);
            this.zzd = zza3;
            return;
        }
        switch (this.zzb & 7) {
            case 1:
                break;
            case 2:
                int zzm2 = this.zza.zzm();
                zzb(zzm2);
                int zzu2 = this.zza.zzu() + zzm2;
                do {
                    list.add(Long.valueOf(this.zza.zzg()));
                } while (this.zza.zzu() < zzu2);
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Long.valueOf(this.zza.zzg()));
            if (!this.zza.zzt()) {
                zza2 = this.zza.zza();
            } else {
                return;
            }
        } while (zza2 == this.zzb);
        this.zzd = zza2;
    }

    public final void zzg(List<Integer> list) {
        int zza2;
        int zza3;
        if (list instanceof zzjd) {
            zzjd zzjd = (zzjd) list;
            switch (this.zzb & 7) {
                case 2:
                    int zzm = this.zza.zzm();
                    zzc(zzm);
                    int zzu = this.zza.zzu() + zzm;
                    do {
                        zzjd.zzc(this.zza.zzh());
                    } while (this.zza.zzu() < zzu);
                    return;
                case 5:
                    break;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjd.zzc(this.zza.zzh());
                if (!this.zza.zzt()) {
                    zza3 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza3 == this.zzb);
            this.zzd = zza3;
            return;
        }
        switch (this.zzb & 7) {
            case 2:
                int zzm2 = this.zza.zzm();
                zzc(zzm2);
                int zzu2 = this.zza.zzu() + zzm2;
                do {
                    list.add(Integer.valueOf(this.zza.zzh()));
                } while (this.zza.zzu() < zzu2);
                return;
            case 5:
                break;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Integer.valueOf(this.zza.zzh()));
            if (!this.zza.zzt()) {
                zza2 = this.zza.zza();
            } else {
                return;
            }
        } while (zza2 == this.zzb);
        this.zzd = zza2;
    }

    public final void zzh(List<Boolean> list) {
        int zza2;
        int zza3;
        if (list instanceof zzhr) {
            zzhr zzhr = (zzhr) list;
            switch (this.zzb & 7) {
                case 0:
                    break;
                case 2:
                    int zzu = this.zza.zzu() + this.zza.zzm();
                    do {
                        zzhr.zza(this.zza.zzi());
                    } while (this.zza.zzu() < zzu);
                    zzd(zzu);
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzhr.zza(this.zza.zzi());
                if (!this.zza.zzt()) {
                    zza3 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza3 == this.zzb);
            this.zzd = zza3;
            return;
        }
        switch (this.zzb & 7) {
            case 0:
                break;
            case 2:
                int zzu2 = this.zza.zzu() + this.zza.zzm();
                do {
                    list.add(Boolean.valueOf(this.zza.zzi()));
                } while (this.zza.zzu() < zzu2);
                zzd(zzu2);
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Boolean.valueOf(this.zza.zzi()));
            if (!this.zza.zzt()) {
                zza2 = this.zza.zza();
            } else {
                return;
            }
        } while (zza2 == this.zzb);
        this.zzd = zza2;
    }

    public final void zzi(List<String> list) {
        zza(list, false);
    }

    public final void zzj(List<String> list) {
        zza(list, true);
    }

    private final void zza(List<String> list, boolean z) {
        int zza2;
        int zza3;
        if ((this.zzb & 7) != 2) {
            throw zzjk.zzf();
        } else if (!(list instanceof zzjv) || z) {
            do {
                list.add(z ? zzm() : zzl());
                if (!this.zza.zzt()) {
                    zza2 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza2 == this.zzb);
            this.zzd = zza2;
        } else {
            zzjv zzjv = (zzjv) list;
            do {
                zzjv.zza(zzn());
                if (!this.zza.zzt()) {
                    zza3 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza3 == this.zzb);
            this.zzd = zza3;
        }
    }

    public final <T> void zza(List<T> list, zzlc<T> zzlc, zzio zzio) {
        int zza2;
        int i = this.zzb;
        if ((i & 7) == 2) {
            do {
                list.add(zzc(zzlc, zzio));
                if (!this.zza.zzt() && this.zzd == 0) {
                    zza2 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza2 == i);
            this.zzd = zza2;
            return;
        }
        throw zzjk.zzf();
    }

    public final <T> void zzb(List<T> list, zzlc<T> zzlc, zzio zzio) {
        int zza2;
        int i = this.zzb;
        if ((i & 7) == 3) {
            do {
                list.add(zzd(zzlc, zzio));
                if (!this.zza.zzt() && this.zzd == 0) {
                    zza2 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza2 == i);
            this.zzd = zza2;
            return;
        }
        throw zzjk.zzf();
    }

    public final void zzk(List<zzht> list) {
        int zza2;
        if ((this.zzb & 7) == 2) {
            do {
                list.add(zzn());
                if (!this.zza.zzt()) {
                    zza2 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza2 == this.zzb);
            this.zzd = zza2;
            return;
        }
        throw zzjk.zzf();
    }

    public final void zzl(List<Integer> list) {
        int zza2;
        int zza3;
        if (list instanceof zzjd) {
            zzjd zzjd = (zzjd) list;
            switch (this.zzb & 7) {
                case 0:
                    break;
                case 2:
                    int zzu = this.zza.zzu() + this.zza.zzm();
                    do {
                        zzjd.zzc(this.zza.zzm());
                    } while (this.zza.zzu() < zzu);
                    zzd(zzu);
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjd.zzc(this.zza.zzm());
                if (!this.zza.zzt()) {
                    zza3 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza3 == this.zzb);
            this.zzd = zza3;
            return;
        }
        switch (this.zzb & 7) {
            case 0:
                break;
            case 2:
                int zzu2 = this.zza.zzu() + this.zza.zzm();
                do {
                    list.add(Integer.valueOf(this.zza.zzm()));
                } while (this.zza.zzu() < zzu2);
                zzd(zzu2);
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Integer.valueOf(this.zza.zzm()));
            if (!this.zza.zzt()) {
                zza2 = this.zza.zza();
            } else {
                return;
            }
        } while (zza2 == this.zzb);
        this.zzd = zza2;
    }

    public final void zzm(List<Integer> list) {
        int zza2;
        int zza3;
        if (list instanceof zzjd) {
            zzjd zzjd = (zzjd) list;
            switch (this.zzb & 7) {
                case 0:
                    break;
                case 2:
                    int zzu = this.zza.zzu() + this.zza.zzm();
                    do {
                        zzjd.zzc(this.zza.zzn());
                    } while (this.zza.zzu() < zzu);
                    zzd(zzu);
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjd.zzc(this.zza.zzn());
                if (!this.zza.zzt()) {
                    zza3 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza3 == this.zzb);
            this.zzd = zza3;
            return;
        }
        switch (this.zzb & 7) {
            case 0:
                break;
            case 2:
                int zzu2 = this.zza.zzu() + this.zza.zzm();
                do {
                    list.add(Integer.valueOf(this.zza.zzn()));
                } while (this.zza.zzu() < zzu2);
                zzd(zzu2);
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Integer.valueOf(this.zza.zzn()));
            if (!this.zza.zzt()) {
                zza2 = this.zza.zza();
            } else {
                return;
            }
        } while (zza2 == this.zzb);
        this.zzd = zza2;
    }

    public final void zzn(List<Integer> list) {
        int zza2;
        int zza3;
        if (list instanceof zzjd) {
            zzjd zzjd = (zzjd) list;
            switch (this.zzb & 7) {
                case 2:
                    int zzm = this.zza.zzm();
                    zzc(zzm);
                    int zzu = this.zza.zzu() + zzm;
                    do {
                        zzjd.zzc(this.zza.zzo());
                    } while (this.zza.zzu() < zzu);
                    return;
                case 5:
                    break;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjd.zzc(this.zza.zzo());
                if (!this.zza.zzt()) {
                    zza3 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza3 == this.zzb);
            this.zzd = zza3;
            return;
        }
        switch (this.zzb & 7) {
            case 2:
                int zzm2 = this.zza.zzm();
                zzc(zzm2);
                int zzu2 = this.zza.zzu() + zzm2;
                do {
                    list.add(Integer.valueOf(this.zza.zzo()));
                } while (this.zza.zzu() < zzu2);
                return;
            case 5:
                break;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Integer.valueOf(this.zza.zzo()));
            if (!this.zza.zzt()) {
                zza2 = this.zza.zza();
            } else {
                return;
            }
        } while (zza2 == this.zzb);
        this.zzd = zza2;
    }

    public final void zzo(List<Long> list) {
        int zza2;
        int zza3;
        if (list instanceof zzjy) {
            zzjy zzjy = (zzjy) list;
            switch (this.zzb & 7) {
                case 1:
                    break;
                case 2:
                    int zzm = this.zza.zzm();
                    zzb(zzm);
                    int zzu = this.zza.zzu() + zzm;
                    do {
                        zzjy.zza(this.zza.zzp());
                    } while (this.zza.zzu() < zzu);
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjy.zza(this.zza.zzp());
                if (!this.zza.zzt()) {
                    zza3 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza3 == this.zzb);
            this.zzd = zza3;
            return;
        }
        switch (this.zzb & 7) {
            case 1:
                break;
            case 2:
                int zzm2 = this.zza.zzm();
                zzb(zzm2);
                int zzu2 = this.zza.zzu() + zzm2;
                do {
                    list.add(Long.valueOf(this.zza.zzp()));
                } while (this.zza.zzu() < zzu2);
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Long.valueOf(this.zza.zzp()));
            if (!this.zza.zzt()) {
                zza2 = this.zza.zza();
            } else {
                return;
            }
        } while (zza2 == this.zzb);
        this.zzd = zza2;
    }

    public final void zzp(List<Integer> list) {
        int zza2;
        int zza3;
        if (list instanceof zzjd) {
            zzjd zzjd = (zzjd) list;
            switch (this.zzb & 7) {
                case 0:
                    break;
                case 2:
                    int zzu = this.zza.zzu() + this.zza.zzm();
                    do {
                        zzjd.zzc(this.zza.zzq());
                    } while (this.zza.zzu() < zzu);
                    zzd(zzu);
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjd.zzc(this.zza.zzq());
                if (!this.zza.zzt()) {
                    zza3 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza3 == this.zzb);
            this.zzd = zza3;
            return;
        }
        switch (this.zzb & 7) {
            case 0:
                break;
            case 2:
                int zzu2 = this.zza.zzu() + this.zza.zzm();
                do {
                    list.add(Integer.valueOf(this.zza.zzq()));
                } while (this.zza.zzu() < zzu2);
                zzd(zzu2);
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Integer.valueOf(this.zza.zzq()));
            if (!this.zza.zzt()) {
                zza2 = this.zza.zza();
            } else {
                return;
            }
        } while (zza2 == this.zzb);
        this.zzd = zza2;
    }

    public final void zzq(List<Long> list) {
        int zza2;
        int zza3;
        if (list instanceof zzjy) {
            zzjy zzjy = (zzjy) list;
            switch (this.zzb & 7) {
                case 0:
                    break;
                case 2:
                    int zzu = this.zza.zzu() + this.zza.zzm();
                    do {
                        zzjy.zza(this.zza.zzr());
                    } while (this.zza.zzu() < zzu);
                    zzd(zzu);
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjy.zza(this.zza.zzr());
                if (!this.zza.zzt()) {
                    zza3 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza3 == this.zzb);
            this.zzd = zza3;
            return;
        }
        switch (this.zzb & 7) {
            case 0:
                break;
            case 2:
                int zzu2 = this.zza.zzu() + this.zza.zzm();
                do {
                    list.add(Long.valueOf(this.zza.zzr()));
                } while (this.zza.zzu() < zzu2);
                zzd(zzu2);
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Long.valueOf(this.zza.zzr()));
            if (!this.zza.zzt()) {
                zza2 = this.zza.zza();
            } else {
                return;
            }
        } while (zza2 == this.zzb);
        this.zzd = zza2;
    }

    private static void zzb(int i) {
        if ((i & 7) != 0) {
            throw zzjk.zzg();
        }
    }

    public final <K, V> void zza(Map<K, V> map, zzkf<K, V> zzkf, zzio zzio) {
        zza(2);
        int zzc2 = this.zza.zzc(this.zza.zzm());
        K k = zzkf.zzb;
        V v = zzkf.zzd;
        while (true) {
            try {
                int zza2 = zza();
                if (zza2 == Integer.MAX_VALUE || this.zza.zzt()) {
                    map.put(k, v);
                } else {
                    switch (zza2) {
                        case 1:
                            k = zza(zzkf.zza, (Class<?>) null, (zzio) null);
                            break;
                        case 2:
                            v = zza(zzkf.zzc, zzkf.zzd.getClass(), zzio);
                            break;
                        default:
                            if (zzc()) {
                                break;
                            } else {
                                throw new zzjk("Unable to parse map entry.");
                            }
                    }
                }
            } catch (zzjn e) {
                if (!zzc()) {
                    throw new zzjk("Unable to parse map entry.");
                }
            } catch (Throwable th) {
                this.zza.zzd(zzc2);
                throw th;
            }
        }
        map.put(k, v);
        this.zza.zzd(zzc2);
    }

    private final Object zza(zzml zzml, Class<?> cls, zzio zzio) {
        switch (zzij.zza[zzml.ordinal()]) {
            case 1:
                return Boolean.valueOf(zzk());
            case 2:
                return zzn();
            case 3:
                return Double.valueOf(zzd());
            case 4:
                return Integer.valueOf(zzp());
            case 5:
                return Integer.valueOf(zzj());
            case 6:
                return Long.valueOf(zzi());
            case 7:
                return Float.valueOf(zze());
            case 8:
                return Integer.valueOf(zzh());
            case 9:
                return Long.valueOf(zzg());
            case 10:
                return zza(cls, zzio);
            case 11:
                return Integer.valueOf(zzq());
            case 12:
                return Long.valueOf(zzr());
            case 13:
                return Integer.valueOf(zzs());
            case 14:
                return Long.valueOf(zzt());
            case 15:
                return zzm();
            case 16:
                return Integer.valueOf(zzo());
            case 17:
                return Long.valueOf(zzf());
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private static void zzc(int i) {
        if ((i & 3) != 0) {
            throw zzjk.zzg();
        }
    }

    private final void zzd(int i) {
        if (this.zza.zzu() != i) {
            throw zzjk.zza();
        }
    }
}
