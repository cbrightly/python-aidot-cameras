package com.google.android.gms.measurement.internal;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzfs;
import com.google.android.gms.internal.measurement.zzft;
import com.google.android.gms.internal.measurement.zzfu;
import com.google.android.gms.internal.measurement.zzfv;
import com.google.android.gms.internal.measurement.zzfw;
import com.google.android.gms.internal.measurement.zzfx;
import com.google.android.gms.internal.measurement.zzga;
import com.google.android.gms.internal.measurement.zzgb;
import com.google.android.gms.internal.measurement.zzgc;
import com.google.android.gms.internal.measurement.zzgd;
import com.google.android.gms.internal.measurement.zzge;
import com.google.android.gms.internal.measurement.zzgg;
import com.google.android.gms.internal.measurement.zzgl;
import com.google.android.gms.internal.measurement.zzgm;
import com.google.android.gms.internal.measurement.zzpw;
import com.google.android.gms.internal.measurement.zzqr;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzgr implements Callable {
    final /* synthetic */ zzaw zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ zzgw zzc;

    zzgr(zzgw zzgw, zzaw zzaw, String str) {
        this.zzc = zzgw;
        this.zza = zzaw;
        this.zzb = str;
    }

    public final /* bridge */ /* synthetic */ Object call() {
        byte[] bArr;
        zzlg zzlg;
        zzll zzll;
        zzga zzga;
        Bundle bundle;
        zzgc zzgc;
        zzh zzh;
        String str;
        long j;
        String str2;
        zzas zzas;
        this.zzc.zza.zzA();
        zzio zzr = this.zzc.zza.zzr();
        zzaw zzaw = this.zza;
        String str3 = this.zzb;
        zzr.zzg();
        zzge.zzO();
        Preconditions.checkNotNull(zzaw);
        Preconditions.checkNotEmpty(str3);
        if (!zzr.zzt.zzf().zzs(str3, zzeh.zzU)) {
            zzr.zzt.zzaA().zzc().zzb("Generating ScionPayload disabled. packageName", str3);
            return new byte[0];
        } else if ("_iap".equals(zzaw.zza) || "_iapx".equals(zzaw.zza)) {
            zzga zza2 = zzgb.zza();
            zzr.zzf.zzi().zzw();
            zzh zzj = zzr.zzf.zzi().zzj(str3);
            if (zzj == null) {
                zzr.zzt.zzaA().zzc().zzb("Log and bundle not available. package_name", str3);
                bArr = new byte[0];
                zzlg = zzr.zzf;
            } else if (!zzj.zzal()) {
                zzr.zzt.zzaA().zzc().zzb("Log and bundle disabled. package_name", str3);
                bArr = new byte[0];
                zzlg = zzr.zzf;
            } else {
                zzgc zzu = zzgd.zzu();
                zzu.zzad(1);
                zzu.zzZ("android");
                if (!TextUtils.isEmpty(zzj.zzu())) {
                    zzu.zzD(zzj.zzu());
                }
                if (!TextUtils.isEmpty(zzj.zzw())) {
                    zzu.zzF((String) Preconditions.checkNotNull(zzj.zzw()));
                }
                if (!TextUtils.isEmpty(zzj.zzx())) {
                    zzu.zzG((String) Preconditions.checkNotNull(zzj.zzx()));
                }
                if (zzj.zzb() != -2147483648L) {
                    zzu.zzH((int) zzj.zzb());
                }
                zzu.zzV(zzj.zzm());
                zzu.zzP(zzj.zzk());
                String zzz = zzj.zzz();
                String zzs = zzj.zzs();
                if (!TextUtils.isEmpty(zzz)) {
                    zzu.zzU(zzz);
                } else if (!TextUtils.isEmpty(zzs)) {
                    zzu.zzC(zzs);
                }
                zzpw.zzc();
                if (zzr.zzt.zzf().zzs((String) null, zzeh.zzaE)) {
                    zzu.zzaj(zzj.zzq());
                }
                zzai zzh2 = zzr.zzf.zzh(str3);
                zzu.zzM(zzj.zzj());
                if (zzr.zzt.zzJ() && zzr.zzt.zzf().zzt(zzu.zzaq()) && zzh2.zzi(zzah.AD_STORAGE) && !TextUtils.isEmpty((CharSequence) null)) {
                    zzu.zzO((String) null);
                }
                zzu.zzL(zzh2.zzh());
                if (zzh2.zzi(zzah.AD_STORAGE) && zzj.zzak()) {
                    Pair zzd = zzr.zzf.zzs().zzd(zzj.zzu(), zzh2);
                    if (zzj.zzak() && !TextUtils.isEmpty((CharSequence) zzd.first)) {
                        try {
                            zzu.zzae(zzio.zza((String) zzd.first, Long.toString(zzaw.zzd)));
                            Object obj = zzd.second;
                            if (obj != null) {
                                zzu.zzX(((Boolean) obj).booleanValue());
                            }
                        } catch (SecurityException e) {
                            zzr.zzt.zzaA().zzc().zzb("Resettable device id encryption failed", e.getMessage());
                            bArr = new byte[0];
                            zzlg = zzr.zzf;
                        } catch (Throwable th) {
                            zzr.zzf.zzi().zzx();
                            throw th;
                        }
                    }
                }
                zzr.zzt.zzg().zzv();
                zzu.zzN(Build.MODEL);
                zzr.zzt.zzg().zzv();
                zzu.zzY(Build.VERSION.RELEASE);
                zzu.zzak((int) zzr.zzt.zzg().zzb());
                zzu.zzao(zzr.zzt.zzg().zzc());
                try {
                    if (zzh2.zzi(zzah.ANALYTICS_STORAGE) && zzj.zzv() != null) {
                        zzu.zzE(zzio.zza((String) Preconditions.checkNotNull(zzj.zzv()), Long.toString(zzaw.zzd)));
                    }
                    if (!TextUtils.isEmpty(zzj.zzy())) {
                        zzu.zzT((String) Preconditions.checkNotNull(zzj.zzy()));
                    }
                    String zzu2 = zzj.zzu();
                    List zzu3 = zzr.zzf.zzi().zzu(zzu2);
                    Iterator it = zzu3.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            zzll = null;
                            break;
                        }
                        zzll = (zzll) it.next();
                        if ("_lte".equals(zzll.zzc)) {
                            break;
                        }
                    }
                    if (zzll == null || zzll.zze == null) {
                        zzll zzll2 = new zzll(zzu2, "auto", "_lte", zzr.zzt.zzax().currentTimeMillis(), 0L);
                        zzu3.add(zzll2);
                        zzr.zzf.zzi().zzL(zzll2);
                    }
                    zzli zzu4 = zzr.zzf.zzu();
                    zzu4.zzt.zzaA().zzj().zza("Checking account type status for ad personalization signals");
                    if (zzu4.zzt.zzg().zze()) {
                        String zzu5 = zzj.zzu();
                        Preconditions.checkNotNull(zzu5);
                        if (zzj.zzak() && zzu4.zzf.zzo().zzn(zzu5)) {
                            zzu4.zzt.zzaA().zzc().zza("Turning off ad personalization due to account type");
                            Iterator it2 = zzu3.iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    break;
                                } else if ("_npa".equals(((zzll) it2.next()).zzc)) {
                                    it2.remove();
                                    break;
                                }
                            }
                            zzu3.add(new zzll(zzu5, "auto", "_npa", zzu4.zzt.zzax().currentTimeMillis(), 1L));
                        }
                    }
                    zzgm[] zzgmArr = new zzgm[zzu3.size()];
                    for (int i = 0; i < zzu3.size(); i++) {
                        zzgl zzd2 = zzgm.zzd();
                        zzd2.zzf(((zzll) zzu3.get(i)).zzc);
                        zzd2.zzg(((zzll) zzu3.get(i)).zzd);
                        zzr.zzf.zzu().zzu(zzd2, ((zzll) zzu3.get(i)).zze);
                        zzgmArr[i] = (zzgm) zzd2.zzaD();
                    }
                    zzu.zzj(Arrays.asList(zzgmArr));
                    zzev zzb2 = zzev.zzb(zzaw);
                    zzr.zzt.zzv().zzL(zzb2.zzd, zzr.zzf.zzi().zzi(str3));
                    zzr.zzt.zzv().zzN(zzb2, zzr.zzt.zzf().zzd(str3));
                    Bundle bundle2 = zzb2.zzd;
                    bundle2.putLong("_c", 1);
                    zzr.zzt.zzaA().zzc().zza("Marking in-app purchase as real-time");
                    bundle2.putLong("_r", 1);
                    bundle2.putString("_o", zzaw.zzc);
                    if (zzr.zzt.zzv().zzaf(zzu.zzaq())) {
                        zzr.zzt.zzv().zzP(bundle2, "_dbg", 1L);
                        zzr.zzt.zzv().zzP(bundle2, "_r", 1L);
                    }
                    zzas zzn = zzr.zzf.zzi().zzn(str3, zzaw.zza);
                    if (zzn == null) {
                        zzgc = zzu;
                        zzh = zzj;
                        zzga = zza2;
                        str = str3;
                        bundle = bundle2;
                        str2 = null;
                        zzas = new zzas(str3, zzaw.zza, 0, 0, 0, zzaw.zzd, 0, (Long) null, (Long) null, (Long) null, (Boolean) null);
                        j = 0;
                    } else {
                        zzh = zzj;
                        zzga = zza2;
                        str = str3;
                        bundle = bundle2;
                        zzgc = zzu;
                        str2 = null;
                        long j2 = zzn.zzf;
                        zzas = zzn.zzc(zzaw.zzd);
                        j = j2;
                    }
                    zzr.zzf.zzi().zzE(zzas);
                    zzar zzar = new zzar(zzr.zzt, zzaw.zzc, str, zzaw.zza, zzaw.zzd, j, bundle);
                    zzfs zze = zzft.zze();
                    zze.zzm(zzar.zzd);
                    zze.zzi(zzar.zzb);
                    zze.zzl(zzar.zze);
                    zzat zzat = new zzat(zzar.zzf);
                    while (zzat.hasNext()) {
                        String zza3 = zzat.next();
                        zzfw zze2 = zzfx.zze();
                        zze2.zzj(zza3);
                        Object zzf = zzar.zzf.zzf(zza3);
                        if (zzf != null) {
                            zzr.zzf.zzu().zzt(zze2, zzf);
                            zze.zze(zze2);
                        }
                    }
                    zzgc zzgc2 = zzgc;
                    zzgc2.zzk(zze);
                    zzge zza4 = zzgg.zza();
                    zzfu zza5 = zzfv.zza();
                    zza5.zza(zzas.zzc);
                    zza5.zzb(zzaw.zza);
                    zza4.zza(zza5);
                    zzgc2.zzaa(zza4);
                    zzgc2.zzf(zzr.zzf.zzf().zza(zzh.zzu(), Collections.emptyList(), zzgc2.zzau(), Long.valueOf(zze.zzc()), Long.valueOf(zze.zzc())));
                    if (zze.zzq()) {
                        zzgc2.zzai(zze.zzc());
                        zzgc2.zzQ(zze.zzc());
                    }
                    long zzn2 = zzh.zzn();
                    int i2 = (zzn2 > 0 ? 1 : (zzn2 == 0 ? 0 : -1));
                    if (i2 != 0) {
                        zzgc2.zzab(zzn2);
                    }
                    long zzp = zzh.zzp();
                    if (zzp != 0) {
                        zzgc2.zzac(zzp);
                    } else if (i2 != 0) {
                        zzgc2.zzac(zzn2);
                    }
                    String zzC = zzh.zzC();
                    zzqr.zzc();
                    String str4 = str;
                    if (zzr.zzt.zzf().zzs(str4, zzeh.zzao) && zzC != null) {
                        zzgc2.zzah(zzC);
                    }
                    zzh.zzF();
                    zzgc2.zzI((int) zzh.zzo());
                    zzr.zzt.zzf().zzh();
                    zzgc2.zzam(77000);
                    zzgc2.zzal(zzr.zzt.zzax().currentTimeMillis());
                    zzgc2.zzag(Boolean.TRUE.booleanValue());
                    if (zzr.zzt.zzf().zzs(str2, zzeh.zzas)) {
                        zzr.zzf.zzC(zzgc2.zzaq(), zzgc2);
                    }
                    zzga zzga2 = zzga;
                    zzga2.zza(zzgc2);
                    zzh zzh3 = zzh;
                    zzh3.zzac(zzgc2.zzd());
                    zzh3.zzaa(zzgc2.zzc());
                    zzr.zzf.zzi().zzD(zzh3);
                    zzr.zzf.zzi().zzC();
                    zzr.zzf.zzi().zzx();
                    try {
                        return zzr.zzf.zzu().zzy(((zzgb) zzga2.zzaD()).zzbx());
                    } catch (IOException e2) {
                        zzr.zzt.zzaA().zzd().zzc("Data loss. Failed to bundle and serialize. appId", zzeu.zzn(str4), e2);
                        return str2;
                    }
                } catch (SecurityException e3) {
                    zzr.zzt.zzaA().zzc().zzb("app instance id encryption failed", e3.getMessage());
                    bArr = new byte[0];
                    zzlg = zzr.zzf;
                }
            }
            zzlg.zzi().zzx();
            return bArr;
        } else {
            zzr.zzt.zzaA().zzc().zzc("Generating a payload for this event is not available. package_name, event_name", str3, zzaw.zza);
            return null;
        }
    }
}
