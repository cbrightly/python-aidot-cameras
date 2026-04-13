package com.google.android.gms.internal.vision;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzkq<T> implements zzlc<T> {
    private final zzkk zza;
    private final zzlu<?, ?> zzb;
    private final boolean zzc;
    private final zziq<?> zzd;

    private zzkq(zzlu<?, ?> zzlu, zziq<?> zziq, zzkk zzkk) {
        this.zzb = zzlu;
        this.zzc = zziq.zza(zzkk);
        this.zzd = zziq;
        this.zza = zzkk;
    }

    static <T> zzkq<T> zza(zzlu<?, ?> zzlu, zziq<?> zziq, zzkk zzkk) {
        return new zzkq<>(zzlu, zziq, zzkk);
    }

    public final T zza() {
        return this.zza.zzq().zze();
    }

    public final boolean zza(T t, T t2) {
        if (!this.zzb.zzb(t).equals(this.zzb.zzb(t2))) {
            return false;
        }
        if (this.zzc) {
            return this.zzd.zza((Object) t).equals(this.zzd.zza((Object) t2));
        }
        return true;
    }

    public final int zza(T t) {
        int hashCode = this.zzb.zzb(t).hashCode();
        if (this.zzc) {
            return (hashCode * 53) + this.zzd.zza((Object) t).hashCode();
        }
        return hashCode;
    }

    public final void zzb(T t, T t2) {
        zzle.zza(this.zzb, t, t2);
        if (this.zzc) {
            zzle.zza(this.zzd, t, t2);
        }
    }

    public final void zza(T t, zzmr zzmr) {
        Iterator<Map.Entry<?, Object>> zzd2 = this.zzd.zza((Object) t).zzd();
        while (zzd2.hasNext()) {
            Map.Entry next = zzd2.next();
            zziw zziw = (zziw) next.getKey();
            if (zziw.zzc() != zzmo.MESSAGE || zziw.zzd() || zziw.zze()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            } else if (next instanceof zzjr) {
                zzmr.zza(zziw.zza(), (Object) ((zzjr) next).zza().zzc());
            } else {
                zzmr.zza(zziw.zza(), next.getValue());
            }
        }
        zzlu<?, ?> zzlu = this.zzb;
        zzlu.zzb(zzlu.zzb(t), zzmr);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v1, resolved type: com.google.android.gms.internal.vision.zzjb$zze} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(T r10, byte[] r11, int r12, int r13, com.google.android.gms.internal.vision.zzhn r14) {
        /*
            r9 = this;
            r0 = r10
            com.google.android.gms.internal.vision.zzjb r0 = (com.google.android.gms.internal.vision.zzjb) r0
            com.google.android.gms.internal.vision.zzlx r1 = r0.zzb
            com.google.android.gms.internal.vision.zzlx r2 = com.google.android.gms.internal.vision.zzlx.zza()
            if (r1 != r2) goto L_0x0011
            com.google.android.gms.internal.vision.zzlx r1 = com.google.android.gms.internal.vision.zzlx.zzb()
            r0.zzb = r1
        L_0x0011:
            com.google.android.gms.internal.vision.zzjb$zzc r10 = (com.google.android.gms.internal.vision.zzjb.zzc) r10
            com.google.android.gms.internal.vision.zziu r10 = r10.zza()
            r0 = 0
            r2 = r0
        L_0x0019:
            if (r12 >= r13) goto L_0x00d0
            int r4 = com.google.android.gms.internal.vision.zzhl.zza(r11, r12, r14)
            int r12 = r14.zza
            r3 = 11
            r5 = 2
            if (r12 == r3) goto L_0x0068
            r3 = r12 & 7
            if (r3 != r5) goto L_0x0063
            com.google.android.gms.internal.vision.zziq<?> r2 = r9.zzd
            com.google.android.gms.internal.vision.zzio r3 = r14.zzd
            com.google.android.gms.internal.vision.zzkk r5 = r9.zza
            int r6 = r12 >>> 3
            java.lang.Object r2 = r2.zza(r3, r5, r6)
            r8 = r2
            com.google.android.gms.internal.vision.zzjb$zze r8 = (com.google.android.gms.internal.vision.zzjb.zze) r8
            if (r8 == 0) goto L_0x0057
            com.google.android.gms.internal.vision.zzky r12 = com.google.android.gms.internal.vision.zzky.zza()
            com.google.android.gms.internal.vision.zzkk r2 = r8.zzc
            java.lang.Class r2 = r2.getClass()
            com.google.android.gms.internal.vision.zzlc r12 = r12.zza(r2)
            int r12 = com.google.android.gms.internal.vision.zzhl.zza((com.google.android.gms.internal.vision.zzlc) r12, (byte[]) r11, (int) r4, (int) r13, (com.google.android.gms.internal.vision.zzhn) r14)
            com.google.android.gms.internal.vision.zzjb$zzf r2 = r8.zzd
            java.lang.Object r3 = r14.zzc
            r10.zza(r2, (java.lang.Object) r3)
            r2 = r8
            goto L_0x0019
        L_0x0057:
            r2 = r12
            r3 = r11
            r5 = r13
            r6 = r1
            r7 = r14
            int r12 = com.google.android.gms.internal.vision.zzhl.zza((int) r2, (byte[]) r3, (int) r4, (int) r5, (com.google.android.gms.internal.vision.zzlx) r6, (com.google.android.gms.internal.vision.zzhn) r7)
            r2 = r8
            goto L_0x0019
        L_0x0063:
            int r12 = com.google.android.gms.internal.vision.zzhl.zza((int) r12, (byte[]) r11, (int) r4, (int) r13, (com.google.android.gms.internal.vision.zzhn) r14)
            goto L_0x0019
        L_0x0068:
            r12 = 0
            r3 = r0
        L_0x006a:
            if (r4 >= r13) goto L_0x00c3
            int r4 = com.google.android.gms.internal.vision.zzhl.zza(r11, r4, r14)
            int r6 = r14.zza
            int r7 = r6 >>> 3
            r8 = r6 & 7
            switch(r7) {
                case 2: goto L_0x00a5;
                case 3: goto L_0x007e;
                default: goto L_0x007d;
            }
        L_0x007d:
            goto L_0x00ba
        L_0x007e:
            if (r2 == 0) goto L_0x009a
            com.google.android.gms.internal.vision.zzky r6 = com.google.android.gms.internal.vision.zzky.zza()
            com.google.android.gms.internal.vision.zzkk r7 = r2.zzc
            java.lang.Class r7 = r7.getClass()
            com.google.android.gms.internal.vision.zzlc r6 = r6.zza(r7)
            int r4 = com.google.android.gms.internal.vision.zzhl.zza((com.google.android.gms.internal.vision.zzlc) r6, (byte[]) r11, (int) r4, (int) r13, (com.google.android.gms.internal.vision.zzhn) r14)
            com.google.android.gms.internal.vision.zzjb$zzf r6 = r2.zzd
            java.lang.Object r7 = r14.zzc
            r10.zza(r6, (java.lang.Object) r7)
            goto L_0x006a
        L_0x009a:
            if (r8 != r5) goto L_0x00ba
            int r4 = com.google.android.gms.internal.vision.zzhl.zze(r11, r4, r14)
            java.lang.Object r3 = r14.zzc
            com.google.android.gms.internal.vision.zzht r3 = (com.google.android.gms.internal.vision.zzht) r3
            goto L_0x006a
        L_0x00a5:
            if (r8 != 0) goto L_0x00ba
            int r4 = com.google.android.gms.internal.vision.zzhl.zza(r11, r4, r14)
            int r12 = r14.zza
            com.google.android.gms.internal.vision.zziq<?> r2 = r9.zzd
            com.google.android.gms.internal.vision.zzio r6 = r14.zzd
            com.google.android.gms.internal.vision.zzkk r7 = r9.zza
            java.lang.Object r2 = r2.zza(r6, r7, r12)
            com.google.android.gms.internal.vision.zzjb$zze r2 = (com.google.android.gms.internal.vision.zzjb.zze) r2
            goto L_0x006a
        L_0x00ba:
            r7 = 12
            if (r6 == r7) goto L_0x00c3
            int r4 = com.google.android.gms.internal.vision.zzhl.zza((int) r6, (byte[]) r11, (int) r4, (int) r13, (com.google.android.gms.internal.vision.zzhn) r14)
            goto L_0x006a
        L_0x00c3:
            if (r3 == 0) goto L_0x00cd
            int r12 = r12 << 3
            r12 = r12 | r5
            r1.zza((int) r12, (java.lang.Object) r3)
        L_0x00cd:
            r12 = r4
            goto L_0x0019
        L_0x00d0:
            if (r12 != r13) goto L_0x00d3
            return
        L_0x00d3:
            com.google.android.gms.internal.vision.zzjk r10 = com.google.android.gms.internal.vision.zzjk.zzg()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzkq.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.vision.zzhn):void");
    }

    public final void zza(T t, zzld zzld, zzio zzio) {
        boolean z;
        zzlu<?, ?> zzlu = this.zzb;
        zziq<?> zziq = this.zzd;
        Object zzc2 = zzlu.zzc(t);
        zziu<?> zzb2 = zziq.zzb(t);
        do {
            try {
                if (zzld.zza() == Integer.MAX_VALUE) {
                    zzlu.zzb((Object) t, zzc2);
                    return;
                }
                int zzb3 = zzld.zzb();
                if (zzb3 == 11) {
                    int i = 0;
                    Object obj = null;
                    zzht zzht = null;
                    while (zzld.zza() != Integer.MAX_VALUE) {
                        int zzb4 = zzld.zzb();
                        if (zzb4 == 16) {
                            i = zzld.zzo();
                            obj = zziq.zza(zzio, this.zza, i);
                        } else if (zzb4 == 26) {
                            if (obj != null) {
                                zziq.zza(zzld, obj, zzio, zzb2);
                            } else {
                                zzht = zzld.zzn();
                            }
                        } else if (!zzld.zzc()) {
                            break;
                        }
                    }
                    if (zzld.zzb() != 12) {
                        throw zzjk.zze();
                    } else if (zzht != null) {
                        if (obj != null) {
                            zziq.zza(zzht, obj, zzio, zzb2);
                        } else {
                            zzlu.zza(zzc2, i, zzht);
                        }
                    }
                } else if ((zzb3 & 7) == 2) {
                    Object zza2 = zziq.zza(zzio, this.zza, zzb3 >>> 3);
                    if (zza2 != null) {
                        zziq.zza(zzld, zza2, zzio, zzb2);
                    } else {
                        z = zzlu.zza(zzc2, zzld);
                        continue;
                    }
                } else {
                    z = zzld.zzc();
                    continue;
                }
                z = true;
                continue;
            } finally {
                zzlu.zzb((Object) t, zzc2);
            }
        } while (z);
    }

    public final void zzc(T t) {
        this.zzb.zzd(t);
        this.zzd.zzc(t);
    }

    public final boolean zzd(T t) {
        return this.zzd.zza((Object) t).zzf();
    }

    public final int zzb(T t) {
        zzlu<?, ?> zzlu = this.zzb;
        int zze = zzlu.zze(zzlu.zzb(t)) + 0;
        if (this.zzc) {
            return zze + this.zzd.zza((Object) t).zzg();
        }
        return zze;
    }
}
