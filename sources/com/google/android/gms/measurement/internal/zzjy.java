package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Pair;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.measurement.zzcf;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@VisibleForTesting
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzjy extends zzf {
    /* access modifiers changed from: private */
    public final zzjx zza;
    /* access modifiers changed from: private */
    public zzek zzb;
    private volatile Boolean zzc;
    private final zzap zzd;
    private final zzkp zze;
    private final List zzf = new ArrayList();
    private final zzap zzg;

    protected zzjy(zzge zzge) {
        super(zzge);
        this.zze = new zzkp(zzge.zzax());
        this.zza = new zzjx(this);
        this.zzd = new zzji(this, zzge);
        this.zzg = new zzjk(this, zzge);
    }

    @WorkerThread
    private final zzq zzO(boolean z) {
        Pair zza2;
        this.zzt.zzay();
        zzel zzh = this.zzt.zzh();
        String str = null;
        if (z) {
            zzeu zzaA = this.zzt.zzaA();
            if (!(zzaA.zzt.zzm().zzb == null || (zza2 = zzaA.zzt.zzm().zzb.zza()) == null || zza2 == zzfj.zza)) {
                str = String.valueOf(zza2.second) + ":" + ((String) zza2.first);
            }
        }
        return zzh.zzj(str);
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzP() {
        zzg();
        this.zzt.zzaA().zzj().zzb("Processing queued up service tasks", Integer.valueOf(this.zzf.size()));
        for (Runnable run : this.zzf) {
            try {
                run.run();
            } catch (RuntimeException e) {
                this.zzt.zzaA().zzd().zzb("Task exception while flushing queue", e);
            }
        }
        this.zzf.clear();
        this.zzg.zzb();
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzQ() {
        zzg();
        this.zze.zzb();
        zzap zzap = this.zzd;
        this.zzt.zzf();
        zzap.zzd(((Long) zzeh.zzJ.zza((Object) null)).longValue());
    }

    @WorkerThread
    private final void zzR(Runnable runnable) {
        zzg();
        if (zzL()) {
            runnable.run();
            return;
        }
        this.zzt.zzf();
        if (((long) this.zzf.size()) >= 1000) {
            this.zzt.zzaA().zzd().zza("Discarding data. Max runnable queue size reached");
            return;
        }
        this.zzf.add(runnable);
        this.zzg.zzd(60000);
        zzr();
    }

    private final boolean zzS() {
        this.zzt.zzay();
        return true;
    }

    static /* bridge */ /* synthetic */ void zzo(zzjy zzjy, ComponentName componentName) {
        zzjy.zzg();
        if (zzjy.zzb != null) {
            zzjy.zzb = null;
            zzjy.zzt.zzaA().zzj().zzb("Disconnected from device MeasurementService", componentName);
            zzjy.zzg();
            zzjy.zzr();
        }
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzA(zzaw zzaw, String str) {
        Preconditions.checkNotNull(zzaw);
        zzg();
        zza();
        zzS();
        zzR(new zzjn(this, true, zzO(true), this.zzt.zzi().zzo(zzaw), zzaw, str));
    }

    @WorkerThread
    public final void zzB(zzcf zzcf, zzaw zzaw, String str) {
        zzg();
        zza();
        if (this.zzt.zzv().zzo(12451000) != 0) {
            this.zzt.zzaA().zzk().zza("Not bundling data. Service unavailable or out of date");
            this.zzt.zzv().zzT(zzcf, new byte[0]);
            return;
        }
        zzR(new zzjj(this, zzaw, str, zzcf));
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzC() {
        zzg();
        zza();
        zzq zzO = zzO(false);
        zzS();
        this.zzt.zzi().zzj();
        zzR(new zzjc(this, zzO));
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    @VisibleForTesting
    public final void zzD(zzek zzek, AbstractSafeParcelable abstractSafeParcelable, zzq zzq) {
        int i;
        zzg();
        zza();
        zzS();
        this.zzt.zzf();
        int i2 = 0;
        int i3 = 100;
        while (i2 < 1001 && i3 == 100) {
            ArrayList arrayList = new ArrayList();
            List zzi = this.zzt.zzi().zzi(100);
            if (zzi != null) {
                arrayList.addAll(zzi);
                i = zzi.size();
            } else {
                i = 0;
            }
            if (abstractSafeParcelable != null && i < 100) {
                arrayList.add(abstractSafeParcelable);
            }
            int size = arrayList.size();
            for (int i4 = 0; i4 < size; i4++) {
                AbstractSafeParcelable abstractSafeParcelable2 = (AbstractSafeParcelable) arrayList.get(i4);
                if (abstractSafeParcelable2 instanceof zzaw) {
                    try {
                        zzek.zzk((zzaw) abstractSafeParcelable2, zzq);
                    } catch (RemoteException e) {
                        this.zzt.zzaA().zzd().zzb("Failed to send event to the service", e);
                    }
                } else if (abstractSafeParcelable2 instanceof zzlj) {
                    try {
                        zzek.zzt((zzlj) abstractSafeParcelable2, zzq);
                    } catch (RemoteException e2) {
                        this.zzt.zzaA().zzd().zzb("Failed to send user property to the service", e2);
                    }
                } else if (abstractSafeParcelable2 instanceof zzac) {
                    try {
                        zzek.zzn((zzac) abstractSafeParcelable2, zzq);
                    } catch (RemoteException e3) {
                        this.zzt.zzaA().zzd().zzb("Failed to send conditional user property to the service", e3);
                    }
                } else {
                    this.zzt.zzaA().zzd().zza("Discarding data. Unrecognized parcel type.");
                }
            }
            i2++;
            i3 = i;
        }
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzE(zzac zzac) {
        Preconditions.checkNotNull(zzac);
        zzg();
        zza();
        this.zzt.zzay();
        zzR(new zzjo(this, true, zzO(true), this.zzt.zzi().zzn(zzac), new zzac(zzac), zzac));
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzF(boolean z) {
        zzg();
        zza();
        if (z) {
            zzS();
            this.zzt.zzi().zzj();
        }
        if (zzM()) {
            zzR(new zzjm(this, zzO(false)));
        }
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzG(zziq zziq) {
        zzg();
        zza();
        zzR(new zzjg(this, zziq));
    }

    @WorkerThread
    public final void zzH(Bundle bundle) {
        zzg();
        zza();
        zzR(new zzjh(this, zzO(false), bundle));
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzI() {
        zzg();
        zza();
        zzR(new zzjl(this, zzO(true)));
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    @VisibleForTesting
    public final void zzJ(zzek zzek) {
        zzg();
        Preconditions.checkNotNull(zzek);
        this.zzb = zzek;
        zzQ();
        zzP();
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzK(zzlj zzlj) {
        zzg();
        zza();
        zzS();
        zzR(new zzjb(this, zzO(true), this.zzt.zzi().zzp(zzlj), zzlj));
    }

    @WorkerThread
    public final boolean zzL() {
        zzg();
        zza();
        return this.zzb != null;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final boolean zzM() {
        zzg();
        zza();
        if (!zzN() || this.zzt.zzv().zzm() >= ((Integer) zzeh.zzah.zza((Object) null)).intValue()) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final boolean zzN() {
        Boolean bool;
        zzg();
        zza();
        if (this.zzc == null) {
            zzg();
            zza();
            zzfj zzm = this.zzt.zzm();
            zzm.zzg();
            boolean z = false;
            if (!zzm.zza().contains("use_service")) {
                bool = null;
            } else {
                bool = Boolean.valueOf(zzm.zza().getBoolean("use_service", false));
            }
            boolean z2 = true;
            if (bool == null || !bool.booleanValue()) {
                this.zzt.zzay();
                if (this.zzt.zzh().zzh() != 1) {
                    this.zzt.zzaA().zzj().zza("Checking service availability");
                    int zzo = this.zzt.zzv().zzo(12451000);
                    switch (zzo) {
                        case 0:
                            this.zzt.zzaA().zzj().zza("Service available");
                            z = true;
                            break;
                        case 1:
                            this.zzt.zzaA().zzj().zza("Service missing");
                            break;
                        case 2:
                            this.zzt.zzaA().zzc().zza("Service container out of date");
                            if (this.zzt.zzv().zzm() >= 17443) {
                                if (bool != null) {
                                    z2 = false;
                                }
                                z = z2;
                                z2 = false;
                                break;
                            }
                            break;
                        case 3:
                            this.zzt.zzaA().zzk().zza("Service disabled");
                            z2 = false;
                            break;
                        case 9:
                            this.zzt.zzaA().zzk().zza("Service invalid");
                            z2 = false;
                            break;
                        case 18:
                            this.zzt.zzaA().zzk().zza("Service updating");
                            z = true;
                            break;
                        default:
                            this.zzt.zzaA().zzk().zzb("Unexpected service status", Integer.valueOf(zzo));
                            z2 = false;
                            break;
                    }
                } else {
                    z = true;
                }
                if (!z && this.zzt.zzf().zzx()) {
                    this.zzt.zzaA().zzd().zza("No way to upload. Consider using the full version of Analytics");
                } else if (z2) {
                    zzfj zzm2 = this.zzt.zzm();
                    zzm2.zzg();
                    SharedPreferences.Editor edit = zzm2.zza().edit();
                    edit.putBoolean("use_service", z);
                    edit.apply();
                }
                z2 = z;
            }
            this.zzc = Boolean.valueOf(z2);
        }
        return this.zzc.booleanValue();
    }

    /* access modifiers changed from: protected */
    public final boolean zzf() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public final Boolean zzj() {
        return this.zzc;
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzq() {
        zzg();
        zza();
        zzq zzO = zzO(true);
        this.zzt.zzi().zzk();
        zzR(new zzjf(this, zzO));
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzr() {
        zzg();
        zza();
        if (!zzL()) {
            if (zzN()) {
                this.zza.zzc();
            } else if (!this.zzt.zzf().zzx()) {
                this.zzt.zzay();
                List<ResolveInfo> queryIntentServices = this.zzt.zzaw().getPackageManager().queryIntentServices(new Intent().setClassName(this.zzt.zzaw(), "com.google.android.gms.measurement.AppMeasurementService"), 65536);
                if (queryIntentServices == null || queryIntentServices.isEmpty()) {
                    this.zzt.zzaA().zzd().zza("Unable to use remote or local measurement implementation. Please register the AppMeasurementService service in the app manifest");
                    return;
                }
                Intent intent = new Intent("com.google.android.gms.measurement.START");
                Context zzaw = this.zzt.zzaw();
                this.zzt.zzay();
                intent.setComponent(new ComponentName(zzaw, "com.google.android.gms.measurement.AppMeasurementService"));
                this.zza.zzb(intent);
            }
        }
    }

    @WorkerThread
    public final void zzs() {
        zzg();
        zza();
        this.zza.zzd();
        try {
            ConnectionTracker.getInstance().unbindService(this.zzt.zzaw(), this.zza);
        } catch (IllegalArgumentException | IllegalStateException e) {
        }
        this.zzb = null;
    }

    @WorkerThread
    public final void zzt(zzcf zzcf) {
        zzg();
        zza();
        zzR(new zzje(this, zzO(false), zzcf));
    }

    @WorkerThread
    public final void zzu(AtomicReference atomicReference) {
        zzg();
        zza();
        zzR(new zzjd(this, atomicReference, zzO(false)));
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzv(zzcf zzcf, String str, String str2) {
        zzg();
        zza();
        zzR(new zzjq(this, str, str2, zzO(false), zzcf));
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzw(AtomicReference atomicReference, String str, String str2, String str3) {
        zzg();
        zza();
        zzR(new zzjp(this, atomicReference, (String) null, str2, str3, zzO(false)));
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzx(AtomicReference atomicReference, boolean z) {
        zzg();
        zza();
        zzR(new zzja(this, atomicReference, zzO(false), z));
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzy(zzcf zzcf, String str, String str2, boolean z) {
        zzg();
        zza();
        zzR(new zziz(this, str, str2, zzO(false), z, zzcf));
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzz(AtomicReference atomicReference, String str, String str2, String str3, boolean z) {
        zzg();
        zza();
        zzR(new zzjr(this, atomicReference, (String) null, str2, str3, zzO(false), z));
    }
}
