package com.google.android.gms.internal.vision;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import org.glassfish.grizzly.http.server.util.MappingData;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzho extends zzhm {
    private final boolean zza = true;
    private final byte[] zzb;
    private int zzc;
    private final int zzd;
    private int zze;
    private int zzf;
    private int zzg;

    public zzho(ByteBuffer byteBuffer, boolean z) {
        super((zzhp) null);
        this.zzb = byteBuffer.array();
        int arrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
        this.zzc = arrayOffset;
        this.zzd = arrayOffset;
        this.zze = byteBuffer.arrayOffset() + byteBuffer.limit();
    }

    private final boolean zzu() {
        return this.zzc == this.zze;
    }

    public final int zza() {
        if (zzu()) {
            return Integer.MAX_VALUE;
        }
        int zzv = zzv();
        this.zzf = zzv;
        if (zzv == this.zzg) {
            return Integer.MAX_VALUE;
        }
        return zzv >>> 3;
    }

    public final int zzb() {
        return this.zzf;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x002a A[LOOP:0: B:11:0x002a->B:14:0x0037, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zzc() {
        /*
            r7 = this;
            boolean r0 = r7.zzu()
            r1 = 0
            if (r0 != 0) goto L_0x0085
            int r0 = r7.zzf
            int r2 = r7.zzg
            if (r0 != r2) goto L_0x000f
            goto L_0x0085
        L_0x000f:
            r3 = r0 & 7
            r4 = 4
            r5 = 1
            switch(r3) {
                case 0: goto L_0x0055;
                case 1: goto L_0x004f;
                case 2: goto L_0x0047;
                case 3: goto L_0x0020;
                case 4: goto L_0x0017;
                case 5: goto L_0x001c;
                default: goto L_0x0017;
            }
        L_0x0017:
            com.google.android.gms.internal.vision.zzjn r0 = com.google.android.gms.internal.vision.zzjk.zzf()
            throw r0
        L_0x001c:
            r7.zza((int) r4)
            return r5
        L_0x0020:
            int r0 = r0 >>> 3
            int r0 = r0 << 3
            r0 = r0 | r4
            r7.zzg = r0
        L_0x002a:
            int r0 = r7.zza()
            r1 = 2147483647(0x7fffffff, float:NaN)
            if (r0 == r1) goto L_0x0039
            boolean r0 = r7.zzc()
            if (r0 != 0) goto L_0x002a
        L_0x0039:
            int r0 = r7.zzf
            int r1 = r7.zzg
            if (r0 != r1) goto L_0x0042
            r7.zzg = r2
            return r5
        L_0x0042:
            com.google.android.gms.internal.vision.zzjk r0 = com.google.android.gms.internal.vision.zzjk.zzg()
            throw r0
        L_0x0047:
            int r0 = r7.zzv()
            r7.zza((int) r0)
            return r5
        L_0x004f:
            r0 = 8
            r7.zza((int) r0)
            return r5
        L_0x0055:
            int r0 = r7.zze
            int r2 = r7.zzc
            int r0 = r0 - r2
            r3 = 10
            if (r0 < r3) goto L_0x0072
            byte[] r0 = r7.zzb
            r4 = r1
        L_0x0063:
            if (r4 >= r3) goto L_0x0072
            int r6 = r2 + 1
            byte r2 = r0[r2]
            if (r2 < 0) goto L_0x006e
            r7.zzc = r6
            goto L_0x007f
        L_0x006e:
            int r4 = r4 + 1
            r2 = r6
            goto L_0x0063
        L_0x0072:
        L_0x0074:
            if (r1 >= r3) goto L_0x0080
            byte r0 = r7.zzy()
            if (r0 >= 0) goto L_0x007f
            int r1 = r1 + 1
            goto L_0x0074
        L_0x007f:
            return r5
        L_0x0080:
            com.google.android.gms.internal.vision.zzjk r0 = com.google.android.gms.internal.vision.zzjk.zzc()
            throw r0
        L_0x0085:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzho.zzc():boolean");
    }

    public final double zzd() {
        zzc(1);
        return Double.longBitsToDouble(zzaa());
    }

    public final float zze() {
        zzc(5);
        return Float.intBitsToFloat(zzz());
    }

    public final long zzf() {
        zzc(0);
        return zzw();
    }

    public final long zzg() {
        zzc(0);
        return zzw();
    }

    public final int zzh() {
        zzc(0);
        return zzv();
    }

    public final long zzi() {
        zzc(1);
        return zzaa();
    }

    public final int zzj() {
        zzc(5);
        return zzz();
    }

    public final boolean zzk() {
        zzc(0);
        if (zzv() != 0) {
            return true;
        }
        return false;
    }

    public final String zzl() {
        return zza(false);
    }

    public final String zzm() {
        return zza(true);
    }

    private final String zza(boolean z) {
        zzc(2);
        int zzv = zzv();
        if (zzv == 0) {
            return "";
        }
        zzb(zzv);
        if (z) {
            byte[] bArr = this.zzb;
            int i = this.zzc;
            if (!zzmd.zza(bArr, i, i + zzv)) {
                throw zzjk.zzh();
            }
        }
        String str = new String(this.zzb, this.zzc, zzv, zzjf.zza);
        this.zzc += zzv;
        return str;
    }

    public final <T> T zza(Class<T> cls, zzio zzio) {
        zzc(2);
        return zzc(zzky.zza().zza(cls), zzio);
    }

    public final <T> T zza(zzlc<T> zzlc, zzio zzio) {
        zzc(2);
        return zzc(zzlc, zzio);
    }

    private final <T> T zzc(zzlc<T> zzlc, zzio zzio) {
        int zzv = zzv();
        zzb(zzv);
        int i = this.zze;
        int i2 = this.zzc + zzv;
        this.zze = i2;
        try {
            T zza2 = zzlc.zza();
            zzlc.zza(zza2, this, zzio);
            zzlc.zzc(zza2);
            if (this.zzc == i2) {
                return zza2;
            }
            throw zzjk.zzg();
        } finally {
            this.zze = i;
        }
    }

    public final <T> T zzb(Class<T> cls, zzio zzio) {
        zzc(3);
        return zzd(zzky.zza().zza(cls), zzio);
    }

    public final <T> T zzb(zzlc<T> zzlc, zzio zzio) {
        zzc(3);
        return zzd(zzlc, zzio);
    }

    private final <T> T zzd(zzlc<T> zzlc, zzio zzio) {
        int i = this.zzg;
        this.zzg = ((this.zzf >>> 3) << 3) | 4;
        try {
            T zza2 = zzlc.zza();
            zzlc.zza(zza2, this, zzio);
            zzlc.zzc(zza2);
            if (this.zzf == this.zzg) {
                return zza2;
            }
            throw zzjk.zzg();
        } finally {
            this.zzg = i;
        }
    }

    public final zzht zzn() {
        zzht zzht;
        zzc(2);
        int zzv = zzv();
        if (zzv == 0) {
            return zzht.zza;
        }
        zzb(zzv);
        if (this.zza) {
            zzht = zzht.zzb(this.zzb, this.zzc, zzv);
        } else {
            zzht = zzht.zza(this.zzb, this.zzc, zzv);
        }
        this.zzc += zzv;
        return zzht;
    }

    public final int zzo() {
        zzc(0);
        return zzv();
    }

    public final int zzp() {
        zzc(0);
        return zzv();
    }

    public final int zzq() {
        zzc(5);
        return zzz();
    }

    public final long zzr() {
        zzc(1);
        return zzaa();
    }

    public final int zzs() {
        zzc(0);
        return zzif.zze(zzv());
    }

    public final long zzt() {
        zzc(0);
        return zzif.zza(zzw());
    }

    public final void zza(List<Double> list) {
        int i;
        int i2;
        if (list instanceof zzin) {
            zzin zzin = (zzin) list;
            switch (this.zzf & 7) {
                case 1:
                    break;
                case 2:
                    int zzv = zzv();
                    zzd(zzv);
                    int i3 = this.zzc + zzv;
                    while (this.zzc < i3) {
                        zzin.zza(Double.longBitsToDouble(zzac()));
                    }
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzin.zza(zzd());
                if (!zzu()) {
                    i2 = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i2;
            return;
        }
        switch (this.zzf & 7) {
            case 1:
                break;
            case 2:
                int zzv2 = zzv();
                zzd(zzv2);
                int i4 = this.zzc + zzv2;
                while (this.zzc < i4) {
                    list.add(Double.valueOf(Double.longBitsToDouble(zzac())));
                }
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Double.valueOf(zzd()));
            if (!zzu()) {
                i = this.zzc;
            } else {
                return;
            }
        } while (zzv() == this.zzf);
        this.zzc = i;
    }

    public final void zzb(List<Float> list) {
        int i;
        int i2;
        if (list instanceof zzja) {
            zzja zzja = (zzja) list;
            switch (this.zzf & 7) {
                case 2:
                    int zzv = zzv();
                    zze(zzv);
                    int i3 = this.zzc + zzv;
                    while (this.zzc < i3) {
                        zzja.zza(Float.intBitsToFloat(zzab()));
                    }
                    return;
                case 5:
                    break;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzja.zza(zze());
                if (!zzu()) {
                    i2 = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i2;
            return;
        }
        switch (this.zzf & 7) {
            case 2:
                int zzv2 = zzv();
                zze(zzv2);
                int i4 = this.zzc + zzv2;
                while (this.zzc < i4) {
                    list.add(Float.valueOf(Float.intBitsToFloat(zzab())));
                }
                return;
            case 5:
                break;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Float.valueOf(zze()));
            if (!zzu()) {
                i = this.zzc;
            } else {
                return;
            }
        } while (zzv() == this.zzf);
        this.zzc = i;
    }

    public final void zzc(List<Long> list) {
        int i;
        int i2;
        if (list instanceof zzjy) {
            zzjy zzjy = (zzjy) list;
            switch (this.zzf & 7) {
                case 0:
                    break;
                case 2:
                    int zzv = this.zzc + zzv();
                    while (this.zzc < zzv) {
                        zzjy.zza(zzw());
                    }
                    zzf(zzv);
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjy.zza(zzf());
                if (!zzu()) {
                    i2 = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i2;
            return;
        }
        switch (this.zzf & 7) {
            case 0:
                break;
            case 2:
                int zzv2 = this.zzc + zzv();
                while (this.zzc < zzv2) {
                    list.add(Long.valueOf(zzw()));
                }
                zzf(zzv2);
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Long.valueOf(zzf()));
            if (!zzu()) {
                i = this.zzc;
            } else {
                return;
            }
        } while (zzv() == this.zzf);
        this.zzc = i;
    }

    public final void zzd(List<Long> list) {
        int i;
        int i2;
        if (list instanceof zzjy) {
            zzjy zzjy = (zzjy) list;
            switch (this.zzf & 7) {
                case 0:
                    break;
                case 2:
                    int zzv = this.zzc + zzv();
                    while (this.zzc < zzv) {
                        zzjy.zza(zzw());
                    }
                    zzf(zzv);
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjy.zza(zzg());
                if (!zzu()) {
                    i2 = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i2;
            return;
        }
        switch (this.zzf & 7) {
            case 0:
                break;
            case 2:
                int zzv2 = this.zzc + zzv();
                while (this.zzc < zzv2) {
                    list.add(Long.valueOf(zzw()));
                }
                zzf(zzv2);
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Long.valueOf(zzg()));
            if (!zzu()) {
                i = this.zzc;
            } else {
                return;
            }
        } while (zzv() == this.zzf);
        this.zzc = i;
    }

    public final void zze(List<Integer> list) {
        int i;
        int i2;
        if (list instanceof zzjd) {
            zzjd zzjd = (zzjd) list;
            switch (this.zzf & 7) {
                case 0:
                    break;
                case 2:
                    int zzv = this.zzc + zzv();
                    while (this.zzc < zzv) {
                        zzjd.zzc(zzv());
                    }
                    zzf(zzv);
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjd.zzc(zzh());
                if (!zzu()) {
                    i2 = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i2;
            return;
        }
        switch (this.zzf & 7) {
            case 0:
                break;
            case 2:
                int zzv2 = this.zzc + zzv();
                while (this.zzc < zzv2) {
                    list.add(Integer.valueOf(zzv()));
                }
                zzf(zzv2);
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Integer.valueOf(zzh()));
            if (!zzu()) {
                i = this.zzc;
            } else {
                return;
            }
        } while (zzv() == this.zzf);
        this.zzc = i;
    }

    public final void zzf(List<Long> list) {
        int i;
        int i2;
        if (list instanceof zzjy) {
            zzjy zzjy = (zzjy) list;
            switch (this.zzf & 7) {
                case 1:
                    break;
                case 2:
                    int zzv = zzv();
                    zzd(zzv);
                    int i3 = this.zzc + zzv;
                    while (this.zzc < i3) {
                        zzjy.zza(zzac());
                    }
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjy.zza(zzi());
                if (!zzu()) {
                    i2 = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i2;
            return;
        }
        switch (this.zzf & 7) {
            case 1:
                break;
            case 2:
                int zzv2 = zzv();
                zzd(zzv2);
                int i4 = this.zzc + zzv2;
                while (this.zzc < i4) {
                    list.add(Long.valueOf(zzac()));
                }
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Long.valueOf(zzi()));
            if (!zzu()) {
                i = this.zzc;
            } else {
                return;
            }
        } while (zzv() == this.zzf);
        this.zzc = i;
    }

    public final void zzg(List<Integer> list) {
        int i;
        int i2;
        if (list instanceof zzjd) {
            zzjd zzjd = (zzjd) list;
            switch (this.zzf & 7) {
                case 2:
                    int zzv = zzv();
                    zze(zzv);
                    int i3 = this.zzc + zzv;
                    while (this.zzc < i3) {
                        zzjd.zzc(zzab());
                    }
                    return;
                case 5:
                    break;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjd.zzc(zzj());
                if (!zzu()) {
                    i2 = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i2;
            return;
        }
        switch (this.zzf & 7) {
            case 2:
                int zzv2 = zzv();
                zze(zzv2);
                int i4 = this.zzc + zzv2;
                while (this.zzc < i4) {
                    list.add(Integer.valueOf(zzab()));
                }
                return;
            case 5:
                break;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Integer.valueOf(zzj()));
            if (!zzu()) {
                i = this.zzc;
            } else {
                return;
            }
        } while (zzv() == this.zzf);
        this.zzc = i;
    }

    public final void zzh(List<Boolean> list) {
        int i;
        int i2;
        if (list instanceof zzhr) {
            zzhr zzhr = (zzhr) list;
            switch (this.zzf & 7) {
                case 0:
                    break;
                case 2:
                    int zzv = this.zzc + zzv();
                    while (this.zzc < zzv) {
                        zzhr.zza(zzv() != 0);
                    }
                    zzf(zzv);
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzhr.zza(zzk());
                if (!zzu()) {
                    i2 = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i2;
            return;
        }
        switch (this.zzf & 7) {
            case 0:
                break;
            case 2:
                int zzv2 = this.zzc + zzv();
                while (this.zzc < zzv2) {
                    list.add(Boolean.valueOf(zzv() != 0));
                }
                zzf(zzv2);
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Boolean.valueOf(zzk()));
            if (!zzu()) {
                i = this.zzc;
            } else {
                return;
            }
        } while (zzv() == this.zzf);
        this.zzc = i;
    }

    public final void zzi(List<String> list) {
        zza(list, false);
    }

    public final void zzj(List<String> list) {
        zza(list, true);
    }

    private final void zza(List<String> list, boolean z) {
        int i;
        int i2;
        if ((this.zzf & 7) != 2) {
            throw zzjk.zzf();
        } else if (!(list instanceof zzjv) || z) {
            do {
                list.add(zza(z));
                if (!zzu()) {
                    i = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i;
        } else {
            zzjv zzjv = (zzjv) list;
            do {
                zzjv.zza(zzn());
                if (!zzu()) {
                    i2 = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i2;
        }
    }

    public final <T> void zza(List<T> list, zzlc<T> zzlc, zzio zzio) {
        int i;
        int i2 = this.zzf;
        if ((i2 & 7) == 2) {
            do {
                list.add(zzc(zzlc, zzio));
                if (!zzu()) {
                    i = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == i2);
            this.zzc = i;
            return;
        }
        throw zzjk.zzf();
    }

    public final <T> void zzb(List<T> list, zzlc<T> zzlc, zzio zzio) {
        int i;
        int i2 = this.zzf;
        if ((i2 & 7) == 3) {
            do {
                list.add(zzd(zzlc, zzio));
                if (!zzu()) {
                    i = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == i2);
            this.zzc = i;
            return;
        }
        throw zzjk.zzf();
    }

    public final void zzk(List<zzht> list) {
        int i;
        if ((this.zzf & 7) == 2) {
            do {
                list.add(zzn());
                if (!zzu()) {
                    i = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i;
            return;
        }
        throw zzjk.zzf();
    }

    public final void zzl(List<Integer> list) {
        int i;
        int i2;
        if (list instanceof zzjd) {
            zzjd zzjd = (zzjd) list;
            switch (this.zzf & 7) {
                case 0:
                    break;
                case 2:
                    int zzv = this.zzc + zzv();
                    while (this.zzc < zzv) {
                        zzjd.zzc(zzv());
                    }
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjd.zzc(zzo());
                if (!zzu()) {
                    i2 = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i2;
            return;
        }
        switch (this.zzf & 7) {
            case 0:
                break;
            case 2:
                int zzv2 = this.zzc + zzv();
                while (this.zzc < zzv2) {
                    list.add(Integer.valueOf(zzv()));
                }
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Integer.valueOf(zzo()));
            if (!zzu()) {
                i = this.zzc;
            } else {
                return;
            }
        } while (zzv() == this.zzf);
        this.zzc = i;
    }

    public final void zzm(List<Integer> list) {
        int i;
        int i2;
        if (list instanceof zzjd) {
            zzjd zzjd = (zzjd) list;
            switch (this.zzf & 7) {
                case 0:
                    break;
                case 2:
                    int zzv = this.zzc + zzv();
                    while (this.zzc < zzv) {
                        zzjd.zzc(zzv());
                    }
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjd.zzc(zzp());
                if (!zzu()) {
                    i2 = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i2;
            return;
        }
        switch (this.zzf & 7) {
            case 0:
                break;
            case 2:
                int zzv2 = this.zzc + zzv();
                while (this.zzc < zzv2) {
                    list.add(Integer.valueOf(zzv()));
                }
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Integer.valueOf(zzp()));
            if (!zzu()) {
                i = this.zzc;
            } else {
                return;
            }
        } while (zzv() == this.zzf);
        this.zzc = i;
    }

    public final void zzn(List<Integer> list) {
        int i;
        int i2;
        if (list instanceof zzjd) {
            zzjd zzjd = (zzjd) list;
            switch (this.zzf & 7) {
                case 2:
                    int zzv = zzv();
                    zze(zzv);
                    int i3 = this.zzc + zzv;
                    while (this.zzc < i3) {
                        zzjd.zzc(zzab());
                    }
                    return;
                case 5:
                    break;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjd.zzc(zzq());
                if (!zzu()) {
                    i2 = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i2;
            return;
        }
        switch (this.zzf & 7) {
            case 2:
                int zzv2 = zzv();
                zze(zzv2);
                int i4 = this.zzc + zzv2;
                while (this.zzc < i4) {
                    list.add(Integer.valueOf(zzab()));
                }
                return;
            case 5:
                break;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Integer.valueOf(zzq()));
            if (!zzu()) {
                i = this.zzc;
            } else {
                return;
            }
        } while (zzv() == this.zzf);
        this.zzc = i;
    }

    public final void zzo(List<Long> list) {
        int i;
        int i2;
        if (list instanceof zzjy) {
            zzjy zzjy = (zzjy) list;
            switch (this.zzf & 7) {
                case 1:
                    break;
                case 2:
                    int zzv = zzv();
                    zzd(zzv);
                    int i3 = this.zzc + zzv;
                    while (this.zzc < i3) {
                        zzjy.zza(zzac());
                    }
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjy.zza(zzr());
                if (!zzu()) {
                    i2 = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i2;
            return;
        }
        switch (this.zzf & 7) {
            case 1:
                break;
            case 2:
                int zzv2 = zzv();
                zzd(zzv2);
                int i4 = this.zzc + zzv2;
                while (this.zzc < i4) {
                    list.add(Long.valueOf(zzac()));
                }
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Long.valueOf(zzr()));
            if (!zzu()) {
                i = this.zzc;
            } else {
                return;
            }
        } while (zzv() == this.zzf);
        this.zzc = i;
    }

    public final void zzp(List<Integer> list) {
        int i;
        int i2;
        if (list instanceof zzjd) {
            zzjd zzjd = (zzjd) list;
            switch (this.zzf & 7) {
                case 0:
                    break;
                case 2:
                    int zzv = this.zzc + zzv();
                    while (this.zzc < zzv) {
                        zzjd.zzc(zzif.zze(zzv()));
                    }
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjd.zzc(zzs());
                if (!zzu()) {
                    i2 = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i2;
            return;
        }
        switch (this.zzf & 7) {
            case 0:
                break;
            case 2:
                int zzv2 = this.zzc + zzv();
                while (this.zzc < zzv2) {
                    list.add(Integer.valueOf(zzif.zze(zzv())));
                }
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Integer.valueOf(zzs()));
            if (!zzu()) {
                i = this.zzc;
            } else {
                return;
            }
        } while (zzv() == this.zzf);
        this.zzc = i;
    }

    public final void zzq(List<Long> list) {
        int i;
        int i2;
        if (list instanceof zzjy) {
            zzjy zzjy = (zzjy) list;
            switch (this.zzf & 7) {
                case 0:
                    break;
                case 2:
                    int zzv = this.zzc + zzv();
                    while (this.zzc < zzv) {
                        zzjy.zza(zzif.zza(zzw()));
                    }
                    return;
                default:
                    throw zzjk.zzf();
            }
            do {
                zzjy.zza(zzt());
                if (!zzu()) {
                    i2 = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i2;
            return;
        }
        switch (this.zzf & 7) {
            case 0:
                break;
            case 2:
                int zzv2 = this.zzc + zzv();
                while (this.zzc < zzv2) {
                    list.add(Long.valueOf(zzif.zza(zzw())));
                }
                return;
            default:
                throw zzjk.zzf();
        }
        do {
            list.add(Long.valueOf(zzt()));
            if (!zzu()) {
                i = this.zzc;
            } else {
                return;
            }
        } while (zzv() == this.zzf);
        this.zzc = i;
    }

    public final <K, V> void zza(Map<K, V> map, zzkf<K, V> zzkf, zzio zzio) {
        zzc(2);
        int zzv = zzv();
        zzb(zzv);
        int i = this.zze;
        this.zze = this.zzc + zzv;
        try {
            K k = zzkf.zzb;
            V v = zzkf.zzd;
            while (true) {
                int zza2 = zza();
                if (zza2 != Integer.MAX_VALUE) {
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
                } else {
                    map.put(k, v);
                    this.zze = i;
                    return;
                }
            }
        } catch (zzjn e) {
            if (!zzc()) {
                throw new zzjk("Unable to parse map entry.");
            }
        } catch (Throwable th) {
            this.zze = i;
            throw th;
        }
    }

    private final Object zza(zzml zzml, Class<?> cls, zzio zzio) {
        switch (zzhp.zza[zzml.ordinal()]) {
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
                return zza(true);
            case 16:
                return Integer.valueOf(zzo());
            case 17:
                return Long.valueOf(zzf());
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private final int zzv() {
        byte b;
        int i = this.zzc;
        int i2 = this.zze;
        if (i2 != i) {
            byte[] bArr = this.zzb;
            int i3 = i + 1;
            byte b2 = bArr[i];
            if (b2 >= 0) {
                this.zzc = i3;
                return b2;
            } else if (i2 - i3 < 9) {
                return (int) zzx();
            } else {
                int i4 = i3 + 1;
                byte b3 = b2 ^ (bArr[i3] << 7);
                if (b3 < 0) {
                    b = b3 ^ OTACommand.RESP_ID_VERSION;
                } else {
                    int i5 = i4 + 1;
                    byte b4 = b3 ^ (bArr[i4] << 14);
                    if (b4 >= 0) {
                        b = b4 ^ OTACommand.RESP_ID_VERSION;
                        i4 = i5;
                    } else {
                        i4 = i5 + 1;
                        byte b5 = b4 ^ (bArr[i5] << 21);
                        if (b5 < 0) {
                            b = b5 ^ OTACommand.RESP_ID_VERSION;
                        } else {
                            int i6 = i4 + 1;
                            byte b6 = bArr[i4];
                            b = (b5 ^ (b6 << 28)) ^ OTACommand.RESP_ID_VERSION;
                            if (b6 < 0) {
                                i4 = i6 + 1;
                                if (bArr[i6] < 0) {
                                    i6 = i4 + 1;
                                    if (bArr[i4] < 0) {
                                        i4 = i6 + 1;
                                        if (bArr[i6] < 0) {
                                            i6 = i4 + 1;
                                            if (bArr[i4] < 0) {
                                                i4 = i6 + 1;
                                                if (bArr[i6] < 0) {
                                                    throw zzjk.zzc();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            i4 = i6;
                        }
                    }
                }
                this.zzc = i4;
                return b;
            }
        } else {
            throw zzjk.zza();
        }
    }

    private final long zzw() {
        long j;
        int i = this.zzc;
        int i2 = this.zze;
        if (i2 != i) {
            byte[] bArr = this.zzb;
            int i3 = i + 1;
            byte b = bArr[i];
            if (b >= 0) {
                this.zzc = i3;
                return (long) b;
            } else if (i2 - i3 < 9) {
                return zzx();
            } else {
                int i4 = i3 + 1;
                byte b2 = b ^ (bArr[i3] << 7);
                if (b2 < 0) {
                    j = (long) (b2 ^ OTACommand.RESP_ID_VERSION);
                } else {
                    int i5 = i4 + 1;
                    byte b3 = b2 ^ (bArr[i4] << 14);
                    if (b3 >= 0) {
                        i4 = i5;
                        j = (long) (b3 ^ OTACommand.RESP_ID_VERSION);
                    } else {
                        i4 = i5 + 1;
                        byte b4 = b3 ^ (bArr[i5] << 21);
                        if (b4 < 0) {
                            j = (long) (b4 ^ OTACommand.RESP_ID_VERSION);
                        } else {
                            long j2 = (long) b4;
                            int i6 = i4 + 1;
                            long j3 = j2 ^ (((long) bArr[i4]) << 28);
                            if (j3 >= 0) {
                                j = j3 ^ 266354560;
                                i4 = i6;
                            } else {
                                i4 = i6 + 1;
                                long j4 = j3 ^ (((long) bArr[i6]) << 35);
                                if (j4 < 0) {
                                    j = j4 ^ -34093383808L;
                                } else {
                                    int i7 = i4 + 1;
                                    long j5 = j4 ^ (((long) bArr[i4]) << 42);
                                    if (j5 >= 0) {
                                        j = j5 ^ 4363953127296L;
                                        i4 = i7;
                                    } else {
                                        i4 = i7 + 1;
                                        long j6 = j5 ^ (((long) bArr[i7]) << 49);
                                        if (j6 < 0) {
                                            j = j6 ^ -558586000294016L;
                                        } else {
                                            int i8 = i4 + 1;
                                            long j7 = (j6 ^ (((long) bArr[i4]) << 56)) ^ 71499008037633920L;
                                            if (j7 < 0) {
                                                i4 = i8 + 1;
                                                if (((long) bArr[i8]) >= 0) {
                                                    j = j7;
                                                } else {
                                                    throw zzjk.zzc();
                                                }
                                            } else {
                                                i4 = i8;
                                                j = j7;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                this.zzc = i4;
                return j;
            }
        } else {
            throw zzjk.zza();
        }
    }

    private final long zzx() {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzy = zzy();
            j |= ((long) (zzy & Byte.MAX_VALUE)) << i;
            if ((zzy & OTACommand.RESP_ID_VERSION) == 0) {
                return j;
            }
        }
        throw zzjk.zzc();
    }

    private final byte zzy() {
        int i = this.zzc;
        if (i != this.zze) {
            byte[] bArr = this.zzb;
            this.zzc = i + 1;
            return bArr[i];
        }
        throw zzjk.zza();
    }

    private final int zzz() {
        zzb(4);
        return zzab();
    }

    private final long zzaa() {
        zzb(8);
        return zzac();
    }

    private final int zzab() {
        int i = this.zzc;
        byte[] bArr = this.zzb;
        this.zzc = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << MappingData.PATH);
    }

    private final long zzac() {
        int i = this.zzc;
        byte[] bArr = this.zzb;
        this.zzc = i + 8;
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    private final void zza(int i) {
        zzb(i);
        this.zzc += i;
    }

    private final void zzb(int i) {
        if (i < 0 || i > this.zze - this.zzc) {
            throw zzjk.zza();
        }
    }

    private final void zzc(int i) {
        if ((this.zzf & 7) != i) {
            throw zzjk.zzf();
        }
    }

    private final void zzd(int i) {
        zzb(i);
        if ((i & 7) != 0) {
            throw zzjk.zzg();
        }
    }

    private final void zze(int i) {
        zzb(i);
        if ((i & 3) != 0) {
            throw zzjk.zzg();
        }
    }

    private final void zzf(int i) {
        if (this.zzc != i) {
            throw zzjk.zza();
        }
    }
}
