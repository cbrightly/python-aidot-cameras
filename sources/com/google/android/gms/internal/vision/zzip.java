package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzjb;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzip extends zziq<zzjb.zzf> {
    zzip() {
    }

    /* access modifiers changed from: package-private */
    public final boolean zza(zzkk zzkk) {
        return zzkk instanceof zzjb.zzc;
    }

    /* access modifiers changed from: package-private */
    public final zziu<zzjb.zzf> zza(Object obj) {
        return ((zzjb.zzc) obj).zzc;
    }

    /* access modifiers changed from: package-private */
    public final zziu<zzjb.zzf> zzb(Object obj) {
        return ((zzjb.zzc) obj).zza();
    }

    /* access modifiers changed from: package-private */
    public final void zzc(Object obj) {
        zza(obj).zzb();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.lang.Object} */
    /* JADX WARNING: type inference failed for: r1v0, types: [java.lang.Throwable, com.google.android.gms.internal.vision.zzjh] */
    /* JADX WARNING: type inference failed for: r1v30 */
    /* JADX WARNING: type inference failed for: r1v31 */
    /* JADX WARNING: type inference failed for: r1v32 */
    /* JADX WARNING: type inference failed for: r1v33 */
    /* JADX WARNING: type inference failed for: r1v34 */
    /* JADX WARNING: type inference failed for: r1v35 */
    /* JADX WARNING: type inference failed for: r1v36 */
    /* JADX WARNING: type inference failed for: r1v37 */
    /* JADX WARNING: type inference failed for: r1v38 */
    /* JADX WARNING: type inference failed for: r1v39 */
    /* JADX WARNING: type inference failed for: r1v40 */
    /* JADX WARNING: type inference failed for: r1v41 */
    /* JADX WARNING: type inference failed for: r1v42 */
    /* JADX WARNING: type inference failed for: r1v43 */
    /* JADX WARNING: type inference failed for: r1v44 */
    /* JADX WARNING: type inference failed for: r1v45 */
    /* JADX WARNING: type inference failed for: r1v46 */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <UT, UB> UB zza(com.google.android.gms.internal.vision.zzld r4, java.lang.Object r5, com.google.android.gms.internal.vision.zzio r6, com.google.android.gms.internal.vision.zziu<com.google.android.gms.internal.vision.zzjb.zzf> r7, UB r8, com.google.android.gms.internal.vision.zzlu<UT, UB> r9) {
        /*
            r3 = this;
            com.google.android.gms.internal.vision.zzjb$zze r5 = (com.google.android.gms.internal.vision.zzjb.zze) r5
            com.google.android.gms.internal.vision.zzjb$zzf r9 = r5.zzd
            int r0 = r9.zzb
            boolean r0 = r9.zzd
            com.google.android.gms.internal.vision.zzml r9 = r9.zzc
            com.google.android.gms.internal.vision.zzml r0 = com.google.android.gms.internal.vision.zzml.ENUM
            r1 = 0
            if (r9 == r0) goto L_0x00f4
            int[] r0 = com.google.android.gms.internal.vision.zzis.zza
            int r9 = r9.ordinal()
            r9 = r0[r9]
            switch(r9) {
                case 1: goto L_0x00c0;
                case 2: goto L_0x00b7;
                case 3: goto L_0x00ae;
                case 4: goto L_0x00a5;
                case 5: goto L_0x009c;
                case 6: goto L_0x0093;
                case 7: goto L_0x008a;
                case 8: goto L_0x0081;
                case 9: goto L_0x0078;
                case 10: goto L_0x006f;
                case 11: goto L_0x0066;
                case 12: goto L_0x005d;
                case 13: goto L_0x0053;
                case 14: goto L_0x004b;
                case 15: goto L_0x0045;
                case 16: goto L_0x003f;
                case 17: goto L_0x0032;
                case 18: goto L_0x0025;
                default: goto L_0x0023;
            }
        L_0x0023:
            goto L_0x00c9
        L_0x0025:
            com.google.android.gms.internal.vision.zzkk r9 = r5.zzc
            java.lang.Class r9 = r9.getClass()
            java.lang.Object r1 = r4.zza(r9, (com.google.android.gms.internal.vision.zzio) r6)
            goto L_0x00c9
        L_0x0032:
            com.google.android.gms.internal.vision.zzkk r9 = r5.zzc
            java.lang.Class r9 = r9.getClass()
            java.lang.Object r1 = r4.zzb(r9, (com.google.android.gms.internal.vision.zzio) r6)
            goto L_0x00c9
        L_0x003f:
            java.lang.String r1 = r4.zzl()
            goto L_0x00c9
        L_0x0045:
            com.google.android.gms.internal.vision.zzht r1 = r4.zzn()
            goto L_0x00c9
        L_0x004b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "Shouldn't reach here."
            r4.<init>(r5)
            throw r4
        L_0x0053:
            long r1 = r4.zzt()
            java.lang.Long r1 = java.lang.Long.valueOf(r1)
            goto L_0x00c9
        L_0x005d:
            int r4 = r4.zzs()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)
            goto L_0x00c9
        L_0x0066:
            long r1 = r4.zzr()
            java.lang.Long r1 = java.lang.Long.valueOf(r1)
            goto L_0x00c9
        L_0x006f:
            int r4 = r4.zzq()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)
            goto L_0x00c9
        L_0x0078:
            int r4 = r4.zzo()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)
            goto L_0x00c9
        L_0x0081:
            boolean r4 = r4.zzk()
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)
            goto L_0x00c9
        L_0x008a:
            int r4 = r4.zzj()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)
            goto L_0x00c9
        L_0x0093:
            long r1 = r4.zzi()
            java.lang.Long r1 = java.lang.Long.valueOf(r1)
            goto L_0x00c9
        L_0x009c:
            int r4 = r4.zzh()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)
            goto L_0x00c9
        L_0x00a5:
            long r1 = r4.zzf()
            java.lang.Long r1 = java.lang.Long.valueOf(r1)
            goto L_0x00c9
        L_0x00ae:
            long r1 = r4.zzg()
            java.lang.Long r1 = java.lang.Long.valueOf(r1)
            goto L_0x00c9
        L_0x00b7:
            float r4 = r4.zze()
            java.lang.Float r1 = java.lang.Float.valueOf(r4)
            goto L_0x00c9
        L_0x00c0:
            double r1 = r4.zzd()
            java.lang.Double r1 = java.lang.Double.valueOf(r1)
        L_0x00c9:
            com.google.android.gms.internal.vision.zzjb$zzf r4 = r5.zzd
            boolean r6 = r4.zzd
            if (r6 == 0) goto L_0x00d4
            r7.zzb(r4, r1)
            goto L_0x00f3
        L_0x00d4:
            com.google.android.gms.internal.vision.zzml r4 = r4.zzc
            int r4 = r4.ordinal()
            r4 = r0[r4]
            switch(r4) {
                case 17: goto L_0x00e2;
                case 18: goto L_0x00e2;
                default: goto L_0x00e1;
            }
        L_0x00e1:
            goto L_0x00ee
        L_0x00e2:
            com.google.android.gms.internal.vision.zzjb$zzf r4 = r5.zzd
            java.lang.Object r4 = r7.zza(r4)
            if (r4 == 0) goto L_0x00ee
            java.lang.Object r1 = com.google.android.gms.internal.vision.zzjf.zza((java.lang.Object) r4, (java.lang.Object) r1)
        L_0x00ee:
            com.google.android.gms.internal.vision.zzjb$zzf r4 = r5.zzd
            r7.zza(r4, (java.lang.Object) r1)
        L_0x00f3:
            return r8
        L_0x00f4:
            int r4 = r4.zzh()
            r1.zza(r4)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzip.zza(com.google.android.gms.internal.vision.zzld, java.lang.Object, com.google.android.gms.internal.vision.zzio, com.google.android.gms.internal.vision.zziu, java.lang.Object, com.google.android.gms.internal.vision.zzlu):java.lang.Object");
    }

    /* access modifiers changed from: package-private */
    public final int zza(Map.Entry<?, ?> entry) {
        return ((zzjb.zzf) entry.getKey()).zzb;
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzmr zzmr, Map.Entry<?, ?> entry) {
        zzjb.zzf zzf = (zzjb.zzf) entry.getKey();
        if (zzf.zzd) {
            switch (zzis.zza[zzf.zzc.ordinal()]) {
                case 1:
                    zzle.zza(zzf.zzb, (List<Double>) (List) entry.getValue(), zzmr, false);
                    return;
                case 2:
                    zzle.zzb(zzf.zzb, (List<Float>) (List) entry.getValue(), zzmr, false);
                    return;
                case 3:
                    zzle.zzc(zzf.zzb, (List) entry.getValue(), zzmr, false);
                    return;
                case 4:
                    zzle.zzd(zzf.zzb, (List) entry.getValue(), zzmr, false);
                    return;
                case 5:
                    zzle.zzh(zzf.zzb, (List) entry.getValue(), zzmr, false);
                    return;
                case 6:
                    zzle.zzf(zzf.zzb, (List) entry.getValue(), zzmr, false);
                    return;
                case 7:
                    zzle.zzk(zzf.zzb, (List) entry.getValue(), zzmr, false);
                    return;
                case 8:
                    zzle.zzn(zzf.zzb, (List) entry.getValue(), zzmr, false);
                    return;
                case 9:
                    zzle.zzi(zzf.zzb, (List) entry.getValue(), zzmr, false);
                    return;
                case 10:
                    zzle.zzl(zzf.zzb, (List) entry.getValue(), zzmr, false);
                    return;
                case 11:
                    zzle.zzg(zzf.zzb, (List) entry.getValue(), zzmr, false);
                    return;
                case 12:
                    zzle.zzj(zzf.zzb, (List) entry.getValue(), zzmr, false);
                    return;
                case 13:
                    zzle.zze(zzf.zzb, (List) entry.getValue(), zzmr, false);
                    return;
                case 14:
                    zzle.zzh(zzf.zzb, (List) entry.getValue(), zzmr, false);
                    return;
                case 15:
                    zzle.zzb(zzf.zzb, (List<zzht>) (List) entry.getValue(), zzmr);
                    return;
                case 16:
                    zzle.zza(zzf.zzb, (List<String>) (List) entry.getValue(), zzmr);
                    return;
                case 17:
                    List list = (List) entry.getValue();
                    if (list != null && !list.isEmpty()) {
                        zzle.zzb(zzf.zzb, (List<?>) (List) entry.getValue(), zzmr, (zzlc) zzky.zza().zza(list.get(0).getClass()));
                        return;
                    }
                    return;
                case 18:
                    List list2 = (List) entry.getValue();
                    if (list2 != null && !list2.isEmpty()) {
                        zzle.zza(zzf.zzb, (List<?>) (List) entry.getValue(), zzmr, (zzlc) zzky.zza().zza(list2.get(0).getClass()));
                        return;
                    }
                    return;
                default:
                    return;
            }
        } else {
            switch (zzis.zza[zzf.zzc.ordinal()]) {
                case 1:
                    zzmr.zza(zzf.zzb, ((Double) entry.getValue()).doubleValue());
                    return;
                case 2:
                    zzmr.zza(zzf.zzb, ((Float) entry.getValue()).floatValue());
                    return;
                case 3:
                    zzmr.zza(zzf.zzb, ((Long) entry.getValue()).longValue());
                    return;
                case 4:
                    zzmr.zzc(zzf.zzb, ((Long) entry.getValue()).longValue());
                    return;
                case 5:
                    zzmr.zzc(zzf.zzb, ((Integer) entry.getValue()).intValue());
                    return;
                case 6:
                    zzmr.zzd(zzf.zzb, ((Long) entry.getValue()).longValue());
                    return;
                case 7:
                    zzmr.zzd(zzf.zzb, ((Integer) entry.getValue()).intValue());
                    return;
                case 8:
                    zzmr.zza(zzf.zzb, ((Boolean) entry.getValue()).booleanValue());
                    return;
                case 9:
                    zzmr.zze(zzf.zzb, ((Integer) entry.getValue()).intValue());
                    return;
                case 10:
                    zzmr.zza(zzf.zzb, ((Integer) entry.getValue()).intValue());
                    return;
                case 11:
                    zzmr.zzb(zzf.zzb, ((Long) entry.getValue()).longValue());
                    return;
                case 12:
                    zzmr.zzf(zzf.zzb, ((Integer) entry.getValue()).intValue());
                    return;
                case 13:
                    zzmr.zze(zzf.zzb, ((Long) entry.getValue()).longValue());
                    return;
                case 14:
                    zzmr.zzc(zzf.zzb, ((Integer) entry.getValue()).intValue());
                    return;
                case 15:
                    zzmr.zza(zzf.zzb, (zzht) entry.getValue());
                    return;
                case 16:
                    zzmr.zza(zzf.zzb, (String) entry.getValue());
                    return;
                case 17:
                    zzmr.zzb(zzf.zzb, (Object) entry.getValue(), (zzlc) zzky.zza().zza(entry.getValue().getClass()));
                    return;
                case 18:
                    zzmr.zza(zzf.zzb, (Object) entry.getValue(), (zzlc) zzky.zza().zza(entry.getValue().getClass()));
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final Object zza(zzio zzio, zzkk zzkk, int i) {
        return zzio.zza(zzkk, i);
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzld zzld, Object obj, zzio zzio, zziu<zzjb.zzf> zziu) {
        zzjb.zze zze = (zzjb.zze) obj;
        zziu.zza(zze.zzd, (Object) zzld.zza(zze.zzc.getClass(), zzio));
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzht zzht, Object obj, zzio zzio, zziu<zzjb.zzf> zziu) {
        byte[] bArr;
        zzjb.zze zze = (zzjb.zze) obj;
        zzkk zze2 = zze.zzc.zzq().zze();
        int zza = zzht.zza();
        if (zza == 0) {
            bArr = zzjf.zzb;
        } else {
            byte[] bArr2 = new byte[zza];
            zzht.zza(bArr2, 0, 0, zza);
            bArr = bArr2;
        }
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        if (wrap.hasArray()) {
            zzho zzho = new zzho(wrap, true);
            zzky.zza().zza(zze2).zza(zze2, zzho, zzio);
            zziu.zza(zze.zzd, (Object) zze2);
            if (zzho.zza() != Integer.MAX_VALUE) {
                throw zzjk.zze();
            }
            return;
        }
        throw new IllegalArgumentException("Direct buffers not yet supported");
    }
}
