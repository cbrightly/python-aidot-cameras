package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import androidx.collection.SimpleArrayMap;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LastLocationRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.zzm;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzda extends GmsClient {
    public static final /* synthetic */ int zze = 0;
    private final SimpleArrayMap zzf = new SimpleArrayMap();
    private final SimpleArrayMap zzg = new SimpleArrayMap();
    private final SimpleArrayMap zzh = new SimpleArrayMap();

    public zzda(Context context, Looper looper, ClientSettings clientSettings, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 23, clientSettings, connectionCallbacks, onConnectionFailedListener);
    }

    private final boolean zzE(Feature feature) {
        Feature feature2;
        Feature[] availableFeatures = getAvailableFeatures();
        if (availableFeatures == null) {
            return false;
        }
        int length = availableFeatures.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                feature2 = null;
                break;
            }
            feature2 = availableFeatures[i];
            if (feature.getName().equals(feature2.getName())) {
                break;
            }
            i++;
        }
        if (feature2 != null && feature2.getVersion() >= feature.getVersion()) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
        return queryLocalInterface instanceof zzo ? (zzo) queryLocalInterface : new zzn(iBinder);
    }

    public final Feature[] getApiFeatures() {
        return zzm.zzl;
    }

    public final int getMinApkVersion() {
        return 11717000;
    }

    /* access modifiers changed from: protected */
    public final String getServiceDescriptor() {
        return "com.google.android.gms.location.internal.IGoogleLocationManagerService";
    }

    /* access modifiers changed from: protected */
    public final String getStartServiceAction() {
        return "com.google.android.location.internal.GoogleLocationManagerService.START";
    }

    public final void onConnectionSuspended(int i) {
        super.onConnectionSuspended(i);
        synchronized (this.zzf) {
            this.zzf.clear();
        }
        synchronized (this.zzg) {
            this.zzg.clear();
        }
        synchronized (this.zzh) {
            this.zzh.clear();
        }
    }

    public final boolean usesClientTelemetry() {
        return true;
    }

    public final void zzA(boolean z, TaskCompletionSource taskCompletionSource) {
        if (zzE(zzm.zzg)) {
            ((zzo) getService()).zzx(z, new zzcl(this, (Object) null, taskCompletionSource));
            return;
        }
        ((zzo) getService()).zzw(z);
        taskCompletionSource.setResult(null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x005a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzB(com.google.android.gms.common.api.internal.ListenerHolder.ListenerKey r10, boolean r11, com.google.android.gms.tasks.TaskCompletionSource r12) {
        /*
            r9 = this;
            androidx.collection.SimpleArrayMap r0 = r9.zzg
            monitor-enter(r0)
            androidx.collection.SimpleArrayMap r1 = r9.zzg     // Catch:{ all -> 0x005b }
            java.lang.Object r10 = r1.remove(r10)     // Catch:{ all -> 0x005b }
            r5 = r10
            com.google.android.gms.internal.location.zzcw r5 = (com.google.android.gms.internal.location.zzcw) r5     // Catch:{ all -> 0x005b }
            if (r5 != 0) goto L_0x0015
            java.lang.Boolean r10 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x005b }
            r12.setResult(r10)     // Catch:{ all -> 0x005b }
            monitor-exit(r0)     // Catch:{ all -> 0x005b }
            return
        L_0x0015:
            r5.zzh()     // Catch:{ all -> 0x005b }
            if (r11 == 0) goto L_0x0054
            com.google.android.gms.common.Feature r10 = com.google.android.gms.location.zzm.zzj     // Catch:{ all -> 0x005b }
            boolean r10 = r9.zzE(r10)     // Catch:{ all -> 0x005b }
            if (r10 == 0) goto L_0x0038
            android.os.IInterface r10 = r9.getService()     // Catch:{ all -> 0x005b }
            com.google.android.gms.internal.location.zzo r10 = (com.google.android.gms.internal.location.zzo) r10     // Catch:{ all -> 0x005b }
            r11 = 0
            com.google.android.gms.internal.location.zzdb r11 = com.google.android.gms.internal.location.zzdb.zzb(r11, r5, r11, r11)     // Catch:{ all -> 0x005b }
            java.lang.Boolean r1 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x005b }
            com.google.android.gms.internal.location.zzcl r2 = new com.google.android.gms.internal.location.zzcl     // Catch:{ all -> 0x005b }
            r2.<init>(r9, r1, r12)     // Catch:{ all -> 0x005b }
            r10.zzy(r11, r2)     // Catch:{ all -> 0x005b }
            goto L_0x0059
        L_0x0038:
            android.os.IInterface r10 = r9.getService()     // Catch:{ all -> 0x005b }
            com.google.android.gms.internal.location.zzo r10 = (com.google.android.gms.internal.location.zzo) r10     // Catch:{ all -> 0x005b }
            java.lang.Boolean r11 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x005b }
            com.google.android.gms.internal.location.zzcn r7 = new com.google.android.gms.internal.location.zzcn     // Catch:{ all -> 0x005b }
            r7.<init>(r11, r12)     // Catch:{ all -> 0x005b }
            com.google.android.gms.internal.location.zzdf r11 = new com.google.android.gms.internal.location.zzdf     // Catch:{ all -> 0x005b }
            r2 = 2
            r3 = 0
            r4 = 0
            r6 = 0
            r8 = 0
            r1 = r11
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x005b }
            r10.zzz(r11)     // Catch:{ all -> 0x005b }
            goto L_0x0059
        L_0x0054:
            java.lang.Boolean r10 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x005b }
            r12.setResult(r10)     // Catch:{ all -> 0x005b }
        L_0x0059:
            monitor-exit(r0)     // Catch:{ all -> 0x005b }
            return
        L_0x005b:
            r10 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x005b }
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.location.zzda.zzB(com.google.android.gms.common.api.internal.ListenerHolder$ListenerKey, boolean, com.google.android.gms.tasks.TaskCompletionSource):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x005a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzC(com.google.android.gms.common.api.internal.ListenerHolder.ListenerKey r10, boolean r11, com.google.android.gms.tasks.TaskCompletionSource r12) {
        /*
            r9 = this;
            androidx.collection.SimpleArrayMap r0 = r9.zzf
            monitor-enter(r0)
            androidx.collection.SimpleArrayMap r1 = r9.zzf     // Catch:{ all -> 0x005b }
            java.lang.Object r10 = r1.remove(r10)     // Catch:{ all -> 0x005b }
            r4 = r10
            com.google.android.gms.internal.location.zzcz r4 = (com.google.android.gms.internal.location.zzcz) r4     // Catch:{ all -> 0x005b }
            if (r4 != 0) goto L_0x0015
            java.lang.Boolean r10 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x005b }
            r12.setResult(r10)     // Catch:{ all -> 0x005b }
            monitor-exit(r0)     // Catch:{ all -> 0x005b }
            return
        L_0x0015:
            r4.zzg()     // Catch:{ all -> 0x005b }
            if (r11 == 0) goto L_0x0054
            com.google.android.gms.common.Feature r10 = com.google.android.gms.location.zzm.zzj     // Catch:{ all -> 0x005b }
            boolean r10 = r9.zzE(r10)     // Catch:{ all -> 0x005b }
            if (r10 == 0) goto L_0x0038
            android.os.IInterface r10 = r9.getService()     // Catch:{ all -> 0x005b }
            com.google.android.gms.internal.location.zzo r10 = (com.google.android.gms.internal.location.zzo) r10     // Catch:{ all -> 0x005b }
            r11 = 0
            com.google.android.gms.internal.location.zzdb r11 = com.google.android.gms.internal.location.zzdb.zzc(r11, r4, r11, r11)     // Catch:{ all -> 0x005b }
            java.lang.Boolean r1 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x005b }
            com.google.android.gms.internal.location.zzcl r2 = new com.google.android.gms.internal.location.zzcl     // Catch:{ all -> 0x005b }
            r2.<init>(r9, r1, r12)     // Catch:{ all -> 0x005b }
            r10.zzy(r11, r2)     // Catch:{ all -> 0x005b }
            goto L_0x0059
        L_0x0038:
            android.os.IInterface r10 = r9.getService()     // Catch:{ all -> 0x005b }
            com.google.android.gms.internal.location.zzo r10 = (com.google.android.gms.internal.location.zzo) r10     // Catch:{ all -> 0x005b }
            java.lang.Boolean r11 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x005b }
            com.google.android.gms.internal.location.zzcn r7 = new com.google.android.gms.internal.location.zzcn     // Catch:{ all -> 0x005b }
            r7.<init>(r11, r12)     // Catch:{ all -> 0x005b }
            com.google.android.gms.internal.location.zzdf r11 = new com.google.android.gms.internal.location.zzdf     // Catch:{ all -> 0x005b }
            r2 = 2
            r3 = 0
            r5 = 0
            r6 = 0
            r8 = 0
            r1 = r11
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x005b }
            r10.zzz(r11)     // Catch:{ all -> 0x005b }
            goto L_0x0059
        L_0x0054:
            java.lang.Boolean r10 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x005b }
            r12.setResult(r10)     // Catch:{ all -> 0x005b }
        L_0x0059:
            monitor-exit(r0)     // Catch:{ all -> 0x005b }
            return
        L_0x005b:
            r10 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x005b }
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.location.zzda.zzC(com.google.android.gms.common.api.internal.ListenerHolder$ListenerKey, boolean, com.google.android.gms.tasks.TaskCompletionSource):void");
    }

    public final void zzD(PendingIntent pendingIntent, TaskCompletionSource taskCompletionSource, Object obj) {
        if (zzE(zzm.zzj)) {
            ((zzo) getService()).zzy(zzdb.zza(pendingIntent, (String) null, (String) null), new zzcl(this, (Object) null, taskCompletionSource));
            return;
        }
        ((zzo) getService()).zzz(new zzdf(2, (zzdd) null, (IBinder) null, (IBinder) null, pendingIntent, new zzcn((Object) null, taskCompletionSource), (String) null));
    }

    public final LocationAvailability zzp() {
        return ((zzo) getService()).zzf(getContext().getPackageName());
    }

    public final void zzq(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, TaskCompletionSource taskCompletionSource) {
        Preconditions.checkNotNull(geofencingRequest, "geofencingRequest can't be null.");
        Preconditions.checkNotNull(pendingIntent, "PendingIntent must be specified.");
        ((zzo) getService()).zzg(geofencingRequest, pendingIntent, new zzci(taskCompletionSource));
    }

    public final void zzr(TaskCompletionSource taskCompletionSource) {
        ((zzo) getService()).zzi(new zzcn((Object) null, taskCompletionSource));
    }

    public final void zzs(CurrentLocationRequest currentLocationRequest, CancellationToken cancellationToken, TaskCompletionSource taskCompletionSource) {
        getContext();
        if (zzE(zzm.zze)) {
            ICancelToken zze2 = ((zzo) getService()).zze(currentLocationRequest, new zzcm(this, taskCompletionSource));
            if (cancellationToken != null) {
                cancellationToken.onCanceledRequested(new zzcf(zze2));
                return;
            }
            return;
        }
        ListenerHolder createListenerHolder = ListenerHolders.createListenerHolder(new zzcj(this, taskCompletionSource), zzdx.zza(), "GetCurrentLocation");
        ListenerHolder.ListenerKey listenerKey = createListenerHolder.getListenerKey();
        listenerKey.getClass();
        zzck zzck = new zzck(this, createListenerHolder, taskCompletionSource);
        TaskCompletionSource taskCompletionSource2 = new TaskCompletionSource();
        LocationRequest.Builder builder = new LocationRequest.Builder(currentLocationRequest.getPriority(), 0);
        builder.setMinUpdateIntervalMillis(0);
        builder.setDurationMillis(currentLocationRequest.getDurationMillis());
        builder.setGranularity(currentLocationRequest.getGranularity());
        builder.setMaxUpdateAgeMillis(currentLocationRequest.getMaxUpdateAgeMillis());
        builder.zza(currentLocationRequest.zze());
        builder.zzc(currentLocationRequest.zza());
        builder.setWaitForAccurateLocation(true);
        builder.zzb(currentLocationRequest.zzd());
        builder.zzd(currentLocationRequest.zzb());
        zzu(zzck, builder.build(), taskCompletionSource2);
        taskCompletionSource2.getTask().addOnCompleteListener(new zzcg(taskCompletionSource));
        if (cancellationToken != null) {
            cancellationToken.onCanceledRequested(new zzch(this, listenerKey));
        }
    }

    public final void zzt(LastLocationRequest lastLocationRequest, TaskCompletionSource taskCompletionSource) {
        getContext();
        if (zzE(zzm.zzf)) {
            ((zzo) getService()).zzj(lastLocationRequest, new zzcm(this, taskCompletionSource));
        } else {
            taskCompletionSource.setResult(((zzo) getService()).zzd());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0057  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzu(com.google.android.gms.internal.location.zzcs r18, com.google.android.gms.location.LocationRequest r19, com.google.android.gms.tasks.TaskCompletionSource r20) {
        /*
            r17 = this;
            r1 = r17
            r0 = r19
            r2 = r20
            com.google.android.gms.common.api.internal.ListenerHolder r3 = r18.zza()
            com.google.android.gms.common.api.internal.ListenerHolder$ListenerKey r4 = r3.getListenerKey()
            r4.getClass()
            com.google.android.gms.common.Feature r5 = com.google.android.gms.location.zzm.zzj
            boolean r5 = r1.zzE(r5)
            androidx.collection.SimpleArrayMap r6 = r1.zzg
            monitor-enter(r6)
            androidx.collection.SimpleArrayMap r7 = r1.zzg     // Catch:{ all -> 0x0082 }
            java.lang.Object r7 = r7.get(r4)     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.location.zzcw r7 = (com.google.android.gms.internal.location.zzcw) r7     // Catch:{ all -> 0x0082 }
            r8 = 0
            if (r7 == 0) goto L_0x002e
            if (r5 == 0) goto L_0x0028
            goto L_0x002e
        L_0x0028:
            r7.zzg(r3)     // Catch:{ all -> 0x0082 }
            r13 = r7
            r7 = r8
            goto L_0x003b
        L_0x002e:
            com.google.android.gms.internal.location.zzcw r3 = new com.google.android.gms.internal.location.zzcw     // Catch:{ all -> 0x0082 }
            r9 = r18
            r3.<init>(r9)     // Catch:{ all -> 0x0082 }
            androidx.collection.SimpleArrayMap r9 = r1.zzg     // Catch:{ all -> 0x0082 }
            r9.put(r4, r3)     // Catch:{ all -> 0x0082 }
            r13 = r3
        L_0x003b:
            r17.getContext()     // Catch:{ all -> 0x0082 }
            java.lang.String r3 = r4.toIdString()     // Catch:{ all -> 0x0082 }
            if (r5 == 0) goto L_0x0057
            android.os.IInterface r4 = r17.getService()     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.location.zzo r4 = (com.google.android.gms.internal.location.zzo) r4     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.location.zzdb r3 = com.google.android.gms.internal.location.zzdb.zzb(r7, r13, r8, r3)     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.location.zzcl r5 = new com.google.android.gms.internal.location.zzcl     // Catch:{ all -> 0x0082 }
            r5.<init>(r1, r8, r2)     // Catch:{ all -> 0x0082 }
            r4.zzk(r3, r0, r5)     // Catch:{ all -> 0x0082 }
            goto L_0x0080
        L_0x0057:
            android.os.IInterface r4 = r17.getService()     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.location.zzo r4 = (com.google.android.gms.internal.location.zzo) r4     // Catch:{ all -> 0x0082 }
            com.google.android.gms.location.LocationRequest$Builder r5 = new com.google.android.gms.location.LocationRequest$Builder     // Catch:{ all -> 0x0082 }
            r5.<init>((com.google.android.gms.location.LocationRequest) r0)     // Catch:{ all -> 0x0082 }
            r5.zzb(r8)     // Catch:{ all -> 0x0082 }
            com.google.android.gms.location.LocationRequest r0 = r5.build()     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.location.zzdd r11 = com.google.android.gms.internal.location.zzdd.zza(r8, r0)     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.location.zzcp r15 = new com.google.android.gms.internal.location.zzcp     // Catch:{ all -> 0x0082 }
            r15.<init>(r2, r13)     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.location.zzdf r0 = new com.google.android.gms.internal.location.zzdf     // Catch:{ all -> 0x0082 }
            r10 = 1
            r12 = 0
            r14 = 0
            r9 = r0
            r16 = r3
            r9.<init>(r10, r11, r12, r13, r14, r15, r16)     // Catch:{ all -> 0x0082 }
            r4.zzz(r0)     // Catch:{ all -> 0x0082 }
        L_0x0080:
            monitor-exit(r6)     // Catch:{ all -> 0x0082 }
            return
        L_0x0082:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0082 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.location.zzda.zzu(com.google.android.gms.internal.location.zzcs, com.google.android.gms.location.LocationRequest, com.google.android.gms.tasks.TaskCompletionSource):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0057  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzv(com.google.android.gms.internal.location.zzcs r18, com.google.android.gms.location.LocationRequest r19, com.google.android.gms.tasks.TaskCompletionSource r20) {
        /*
            r17 = this;
            r1 = r17
            r0 = r19
            r2 = r20
            com.google.android.gms.common.api.internal.ListenerHolder r3 = r18.zza()
            com.google.android.gms.common.api.internal.ListenerHolder$ListenerKey r4 = r3.getListenerKey()
            r4.getClass()
            com.google.android.gms.common.Feature r5 = com.google.android.gms.location.zzm.zzj
            boolean r5 = r1.zzE(r5)
            androidx.collection.SimpleArrayMap r6 = r1.zzf
            monitor-enter(r6)
            androidx.collection.SimpleArrayMap r7 = r1.zzf     // Catch:{ all -> 0x0082 }
            java.lang.Object r7 = r7.get(r4)     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.location.zzcz r7 = (com.google.android.gms.internal.location.zzcz) r7     // Catch:{ all -> 0x0082 }
            r8 = 0
            if (r7 == 0) goto L_0x002e
            if (r5 == 0) goto L_0x0028
            goto L_0x002e
        L_0x0028:
            r7.zzf(r3)     // Catch:{ all -> 0x0082 }
            r12 = r7
            r7 = r8
            goto L_0x003b
        L_0x002e:
            com.google.android.gms.internal.location.zzcz r3 = new com.google.android.gms.internal.location.zzcz     // Catch:{ all -> 0x0082 }
            r9 = r18
            r3.<init>(r9)     // Catch:{ all -> 0x0082 }
            androidx.collection.SimpleArrayMap r9 = r1.zzf     // Catch:{ all -> 0x0082 }
            r9.put(r4, r3)     // Catch:{ all -> 0x0082 }
            r12 = r3
        L_0x003b:
            r17.getContext()     // Catch:{ all -> 0x0082 }
            java.lang.String r3 = r4.toIdString()     // Catch:{ all -> 0x0082 }
            if (r5 == 0) goto L_0x0057
            android.os.IInterface r4 = r17.getService()     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.location.zzo r4 = (com.google.android.gms.internal.location.zzo) r4     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.location.zzdb r3 = com.google.android.gms.internal.location.zzdb.zzc(r7, r12, r8, r3)     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.location.zzcl r5 = new com.google.android.gms.internal.location.zzcl     // Catch:{ all -> 0x0082 }
            r5.<init>(r1, r8, r2)     // Catch:{ all -> 0x0082 }
            r4.zzk(r3, r0, r5)     // Catch:{ all -> 0x0082 }
            goto L_0x0080
        L_0x0057:
            android.os.IInterface r4 = r17.getService()     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.location.zzo r4 = (com.google.android.gms.internal.location.zzo) r4     // Catch:{ all -> 0x0082 }
            com.google.android.gms.location.LocationRequest$Builder r5 = new com.google.android.gms.location.LocationRequest$Builder     // Catch:{ all -> 0x0082 }
            r5.<init>((com.google.android.gms.location.LocationRequest) r0)     // Catch:{ all -> 0x0082 }
            r5.zzb(r8)     // Catch:{ all -> 0x0082 }
            com.google.android.gms.location.LocationRequest r0 = r5.build()     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.location.zzdd r11 = com.google.android.gms.internal.location.zzdd.zza(r8, r0)     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.location.zzco r15 = new com.google.android.gms.internal.location.zzco     // Catch:{ all -> 0x0082 }
            r15.<init>(r2, r12)     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.location.zzdf r0 = new com.google.android.gms.internal.location.zzdf     // Catch:{ all -> 0x0082 }
            r10 = 1
            r13 = 0
            r14 = 0
            r9 = r0
            r16 = r3
            r9.<init>(r10, r11, r12, r13, r14, r15, r16)     // Catch:{ all -> 0x0082 }
            r4.zzz(r0)     // Catch:{ all -> 0x0082 }
        L_0x0080:
            monitor-exit(r6)     // Catch:{ all -> 0x0082 }
            return
        L_0x0082:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0082 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.location.zzda.zzv(com.google.android.gms.internal.location.zzcs, com.google.android.gms.location.LocationRequest, com.google.android.gms.tasks.TaskCompletionSource):void");
    }

    public final void zzw(PendingIntent pendingIntent, LocationRequest locationRequest, TaskCompletionSource taskCompletionSource) {
        getContext();
        if (zzE(zzm.zzj)) {
            ((zzo) getService()).zzk(zzdb.zza(pendingIntent, (String) null, (String) null), locationRequest, new zzcl(this, (Object) null, taskCompletionSource));
            return;
        }
        LocationRequest.Builder builder = new LocationRequest.Builder(locationRequest);
        builder.zzb((String) null);
        zzdd zza = zzdd.zza((String) null, builder.build());
        zzcn zzcn = new zzcn((Object) null, taskCompletionSource);
        int hashCode = pendingIntent.hashCode();
        ((zzo) getService()).zzz(new zzdf(1, zza, (IBinder) null, (IBinder) null, pendingIntent, zzcn, "PendingIntent@" + hashCode));
    }

    public final void zzx(PendingIntent pendingIntent, TaskCompletionSource taskCompletionSource) {
        Preconditions.checkNotNull(pendingIntent, "PendingIntent must be specified.");
        ((zzo) getService()).zzn(pendingIntent, new zzci(taskCompletionSource), getContext().getPackageName());
    }

    public final void zzy(List list, TaskCompletionSource taskCompletionSource) {
        boolean z;
        if (list == null || list.isEmpty()) {
            z = false;
        } else {
            z = true;
        }
        Preconditions.checkArgument(z, "geofenceRequestIds can't be null nor empty.");
        ((zzo) getService()).zzo((String[]) list.toArray(new String[0]), new zzci(taskCompletionSource), getContext().getPackageName());
    }

    public final void zzz(Location location, TaskCompletionSource taskCompletionSource) {
        if (zzE(zzm.zzh)) {
            ((zzo) getService()).zzv(location, new zzcl(this, (Object) null, taskCompletionSource));
            return;
        }
        ((zzo) getService()).zzu(location);
        taskCompletionSource.setResult(null);
    }
}
