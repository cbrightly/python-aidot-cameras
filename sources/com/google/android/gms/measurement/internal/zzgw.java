package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.os.Binder;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.BinderThread;
import com.amazonaws.kinesisvideo.producer.Time;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.GoogleSignatureVerifier;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.measurement.zzaa;
import com.google.android.gms.internal.measurement.zzc;
import com.google.android.gms.internal.measurement.zzd;
import com.google.firebase.messaging.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzgw extends zzej {
    /* access modifiers changed from: private */
    public final zzlg zza;
    private Boolean zzb;
    private String zzc = null;

    public zzgw(zzlg zzlg, String str) {
        Preconditions.checkNotNull(zzlg);
        this.zza = zzlg;
    }

    private final void zzA(zzaw zzaw, zzq zzq) {
        this.zza.zzA();
        this.zza.zzE(zzaw, zzq);
    }

    @BinderThread
    private final void zzy(zzq zzq, boolean z) {
        Preconditions.checkNotNull(zzq);
        Preconditions.checkNotEmpty(zzq.zza);
        zzz(zzq.zza, false);
        this.zza.zzv().zzY(zzq.zzb, zzq.zzq);
    }

    @BinderThread
    private final void zzz(String str, boolean z) {
        boolean z2;
        if (!TextUtils.isEmpty(str)) {
            if (z) {
                try {
                    if (this.zzb == null) {
                        if ("com.google.android.gms".equals(this.zzc)) {
                            z2 = true;
                        } else if (!UidVerifier.isGooglePlayServicesUid(this.zza.zzaw(), Binder.getCallingUid())) {
                            z2 = GoogleSignatureVerifier.getInstance(this.zza.zzaw()).isUidGoogleSigned(Binder.getCallingUid());
                        } else {
                            z2 = true;
                        }
                        this.zzb = Boolean.valueOf(z2);
                    }
                    if (this.zzb.booleanValue()) {
                        return;
                    }
                } catch (SecurityException e) {
                    this.zza.zzaA().zzd().zzb("Measurement Service called with invalid calling package. appId", zzeu.zzn(str));
                    throw e;
                }
            }
            if (this.zzc == null && GooglePlayServicesUtilLight.uidHasPackageName(this.zza.zzaw(), Binder.getCallingUid(), str)) {
                this.zzc = str;
            }
            if (!str.equals(this.zzc)) {
                throw new SecurityException(String.format("Unknown calling package name '%s'.", new Object[]{str}));
            }
            return;
        }
        this.zza.zzaA().zzd().zza("Measurement Service called without app package");
        throw new SecurityException("Measurement Service called without app package");
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final zzaw zzb(zzaw zzaw, zzq zzq) {
        zzau zzau;
        if (!(!Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN.equals(zzaw.zza) || (zzau = zzaw.zzb) == null || zzau.zza() == 0)) {
            String zzg = zzaw.zzb.zzg("_cis");
            if ("referrer broadcast".equals(zzg) || "referrer API".equals(zzg)) {
                this.zza.zzaA().zzi().zzb("Event has been filtered ", zzaw.toString());
                return new zzaw("_cmpx", zzaw.zzb, zzaw.zzc, zzaw.zzd);
            }
        }
        return zzaw;
    }

    @BinderThread
    public final String zzd(zzq zzq) {
        zzy(zzq, false);
        return this.zza.zzx(zzq);
    }

    @BinderThread
    public final List zze(zzq zzq, boolean z) {
        zzy(zzq, false);
        String str = zzq.zza;
        Preconditions.checkNotNull(str);
        try {
            List<zzll> list = (List) this.zza.zzaB().zzh(new zzgt(this, str)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzll zzll : list) {
                if (z || !zzlo.zzaj(zzll.zzc)) {
                    arrayList.add(new zzlj(zzll));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzaA().zzd().zzc("Failed to get user properties. appId", zzeu.zzn(zzq.zza), e);
            return null;
        }
    }

    @BinderThread
    public final List zzf(String str, String str2, zzq zzq) {
        zzy(zzq, false);
        String str3 = zzq.zza;
        Preconditions.checkNotNull(str3);
        try {
            return (List) this.zza.zzaB().zzh(new zzgk(this, str3, str, str2)).get();
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzaA().zzd().zzb("Failed to get conditional user properties", e);
            return Collections.emptyList();
        }
    }

    @BinderThread
    public final List zzg(String str, String str2, String str3) {
        zzz(str, true);
        try {
            return (List) this.zza.zzaB().zzh(new zzgl(this, str, str2, str3)).get();
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzaA().zzd().zzb("Failed to get conditional user properties as", e);
            return Collections.emptyList();
        }
    }

    @BinderThread
    public final List zzh(String str, String str2, boolean z, zzq zzq) {
        zzy(zzq, false);
        String str3 = zzq.zza;
        Preconditions.checkNotNull(str3);
        try {
            List<zzll> list = (List) this.zza.zzaB().zzh(new zzgi(this, str3, str, str2)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzll zzll : list) {
                if (z || !zzlo.zzaj(zzll.zzc)) {
                    arrayList.add(new zzlj(zzll));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzaA().zzd().zzc("Failed to query user properties. appId", zzeu.zzn(zzq.zza), e);
            return Collections.emptyList();
        }
    }

    @BinderThread
    public final List zzi(String str, String str2, String str3, boolean z) {
        zzz(str, true);
        try {
            List<zzll> list = (List) this.zza.zzaB().zzh(new zzgj(this, str, str2, str3)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzll zzll : list) {
                if (z || !zzlo.zzaj(zzll.zzc)) {
                    arrayList.add(new zzlj(zzll));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzaA().zzd().zzc("Failed to get user properties as. appId", zzeu.zzn(str), e);
            return Collections.emptyList();
        }
    }

    @BinderThread
    public final void zzj(zzq zzq) {
        zzy(zzq, false);
        zzx(new zzgu(this, zzq));
    }

    @BinderThread
    public final void zzk(zzaw zzaw, zzq zzq) {
        Preconditions.checkNotNull(zzaw);
        zzy(zzq, false);
        zzx(new zzgp(this, zzaw, zzq));
    }

    @BinderThread
    public final void zzl(zzaw zzaw, String str, String str2) {
        Preconditions.checkNotNull(zzaw);
        Preconditions.checkNotEmpty(str);
        zzz(str, true);
        zzx(new zzgq(this, zzaw, str));
    }

    @BinderThread
    public final void zzm(zzq zzq) {
        Preconditions.checkNotEmpty(zzq.zza);
        zzz(zzq.zza, false);
        zzx(new zzgm(this, zzq));
    }

    @BinderThread
    public final void zzn(zzac zzac, zzq zzq) {
        Preconditions.checkNotNull(zzac);
        Preconditions.checkNotNull(zzac.zzc);
        zzy(zzq, false);
        zzac zzac2 = new zzac(zzac);
        zzac2.zza = zzq.zza;
        zzx(new zzgg(this, zzac2, zzq));
    }

    @BinderThread
    public final void zzo(zzac zzac) {
        Preconditions.checkNotNull(zzac);
        Preconditions.checkNotNull(zzac.zzc);
        Preconditions.checkNotEmpty(zzac.zza);
        zzz(zzac.zza, true);
        zzx(new zzgh(this, new zzac(zzac)));
    }

    @BinderThread
    public final void zzp(zzq zzq) {
        Preconditions.checkNotEmpty(zzq.zza);
        Preconditions.checkNotNull(zzq.zzv);
        zzgo zzgo = new zzgo(this, zzq);
        Preconditions.checkNotNull(zzgo);
        if (this.zza.zzaB().zzs()) {
            zzgo.run();
        } else {
            this.zza.zzaB().zzq(zzgo);
        }
    }

    @BinderThread
    public final void zzq(long j, String str, String str2, String str3) {
        zzx(new zzgv(this, str2, str3, str, j));
    }

    @BinderThread
    public final void zzr(Bundle bundle, zzq zzq) {
        zzy(zzq, false);
        String str = zzq.zza;
        Preconditions.checkNotNull(str);
        zzx(new zzgf(this, str, bundle));
    }

    @BinderThread
    public final void zzs(zzq zzq) {
        zzy(zzq, false);
        zzx(new zzgn(this, zzq));
    }

    @BinderThread
    public final void zzt(zzlj zzlj, zzq zzq) {
        Preconditions.checkNotNull(zzlj);
        zzy(zzq, false);
        zzx(new zzgs(this, zzlj, zzq));
    }

    @BinderThread
    public final byte[] zzu(zzaw zzaw, String str) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzaw);
        zzz(str, true);
        this.zza.zzaA().zzc().zzb("Log and bundle. event", this.zza.zzj().zzd(zzaw.zza));
        long nanoTime = this.zza.zzax().nanoTime() / Time.NANOS_IN_A_MILLISECOND;
        try {
            byte[] bArr = (byte[]) this.zza.zzaB().zzi(new zzgr(this, zzaw, str)).get();
            if (bArr == null) {
                this.zza.zzaA().zzd().zzb("Log and bundle returned null. appId", zzeu.zzn(str));
                bArr = new byte[0];
            }
            this.zza.zzaA().zzc().zzd("Log and bundle processed. event, size, time_ms", this.zza.zzj().zzd(zzaw.zza), Integer.valueOf(bArr.length), Long.valueOf((this.zza.zzax().nanoTime() / Time.NANOS_IN_A_MILLISECOND) - nanoTime));
            return bArr;
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzaA().zzd().zzd("Failed to log and bundle. appId, event, error", zzeu.zzn(str), this.zza.zzj().zzd(zzaw.zza), e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzv(zzaw zzaw, zzq zzq) {
        zzc zzc2;
        if (!this.zza.zzo().zzo(zzq.zza)) {
            zzA(zzaw, zzq);
            return;
        }
        this.zza.zzaA().zzj().zzb("EES config found for", zzq.zza);
        zzfv zzo = this.zza.zzo();
        String str = zzq.zza;
        if (TextUtils.isEmpty(str)) {
            zzc2 = null;
        } else {
            zzc2 = (zzc) zzo.zzd.get(str);
        }
        if (zzc2 != null) {
            try {
                Map zzs = this.zza.zzu().zzs(zzaw.zzb.zzc(), true);
                String zza2 = zzhb.zza(zzaw.zza);
                if (zza2 == null) {
                    zza2 = zzaw.zza;
                }
                if (zzc2.zze(new zzaa(zza2, zzaw.zzd, zzs))) {
                    if (zzc2.zzg()) {
                        this.zza.zzaA().zzj().zzb("EES edited event", zzaw.zza);
                        zzA(this.zza.zzu().zzi(zzc2.zza().zzb()), zzq);
                    } else {
                        zzA(zzaw, zzq);
                    }
                    if (zzc2.zzf()) {
                        for (zzaa zzaa : zzc2.zza().zzc()) {
                            this.zza.zzaA().zzj().zzb("EES logging created event", zzaa.zzd());
                            zzA(this.zza.zzu().zzi(zzaa), zzq);
                        }
                        return;
                    }
                    return;
                }
            } catch (zzd e) {
                this.zza.zzaA().zzd().zzc("EES error. appId, eventName", zzq.zzb, zzaw.zza);
            }
            this.zza.zzaA().zzj().zzb("EES was not applied to event", zzaw.zza);
            zzA(zzaw, zzq);
            return;
        }
        this.zza.zzaA().zzj().zzb("EES not loaded for", zzq.zza);
        zzA(zzaw, zzq);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzw(String str, Bundle bundle) {
        zzam zzi = this.zza.zzi();
        zzi.zzg();
        zzi.zzW();
        byte[] zzbx = zzi.zzf.zzu().zzj(new zzar(zzi.zzt, "", str, "dep", 0, 0, bundle)).zzbx();
        zzi.zzt.zzaA().zzj().zzc("Saving default event parameters, appId, data size", zzi.zzt.zzj().zzd(str), Integer.valueOf(zzbx.length));
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("parameters", zzbx);
        try {
            if (zzi.zzh().insertWithOnConflict("default_event_params", (String) null, contentValues, 5) == -1) {
                zzi.zzt.zzaA().zzd().zzb("Failed to insert default event parameters (got -1). appId", zzeu.zzn(str));
            }
        } catch (SQLiteException e) {
            zzi.zzt.zzaA().zzd().zzc("Error storing default event parameters. appId", zzeu.zzn(str), e);
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final void zzx(Runnable runnable) {
        Preconditions.checkNotNull(runnable);
        if (this.zza.zzaB().zzs()) {
            runnable.run();
        } else {
            this.zza.zzaB().zzp(runnable);
        }
    }
}
