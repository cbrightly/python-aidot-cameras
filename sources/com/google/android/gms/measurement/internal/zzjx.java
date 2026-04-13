package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Looper;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.VisibleForTesting;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;

@VisibleForTesting
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzjx implements ServiceConnection, BaseGmsClient.BaseConnectionCallbacks, BaseGmsClient.BaseOnConnectionFailedListener {
    final /* synthetic */ zzjy zza;
    /* access modifiers changed from: private */
    public volatile boolean zzb;
    private volatile zzeq zzc;

    protected zzjx(zzjy zzjy) {
        this.zza = zzjy;
    }

    @MainThread
    public final void onConnected(Bundle bundle) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnected");
        synchronized (this) {
            try {
                Preconditions.checkNotNull(this.zzc);
                this.zza.zzt.zzaB().zzp(new zzju(this, (zzek) this.zzc.getService()));
            } catch (DeadObjectException | IllegalStateException e) {
                this.zzc = null;
                this.zzb = false;
            }
        }
    }

    @MainThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnectionFailed");
        zzeu zzl = this.zza.zzt.zzl();
        if (zzl != null) {
            zzl.zzk().zzb("Service connection failed", connectionResult);
        }
        synchronized (this) {
            this.zzb = false;
            this.zzc = null;
        }
        this.zza.zzt.zzaB().zzp(new zzjw(this));
    }

    @MainThread
    public final void onConnectionSuspended(int i) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnectionSuspended");
        this.zza.zzt.zzaA().zzc().zza("Service connection suspended");
        this.zza.zzt.zzaB().zzp(new zzjv(this));
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0095 A[SYNTHETIC, Splitter:B:31:0x0095] */
    @androidx.annotation.MainThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onServiceConnected(android.content.ComponentName r4, android.os.IBinder r5) {
        /*
            r3 = this;
            java.lang.String r4 = "MeasurementServiceConnection.onServiceConnected"
            com.google.android.gms.common.internal.Preconditions.checkMainThread(r4)
            monitor-enter(r3)
            r4 = 0
            if (r5 != 0) goto L_0x001e
            r3.zzb = r4     // Catch:{ all -> 0x0065 }
            com.google.android.gms.measurement.internal.zzjy r4 = r3.zza     // Catch:{ all -> 0x0065 }
            com.google.android.gms.measurement.internal.zzge r4 = r4.zzt     // Catch:{ all -> 0x0065 }
            com.google.android.gms.measurement.internal.zzeu r4 = r4.zzaA()     // Catch:{ all -> 0x0065 }
            com.google.android.gms.measurement.internal.zzes r4 = r4.zzd()     // Catch:{ all -> 0x0065 }
            java.lang.String r5 = "Service connected with null binder"
            r4.zza(r5)     // Catch:{ all -> 0x0065 }
            monitor-exit(r3)     // Catch:{ all -> 0x0065 }
            return
        L_0x001e:
            r0 = 0
            java.lang.String r1 = r5.getInterfaceDescriptor()     // Catch:{ RemoteException -> 0x0067 }
            java.lang.String r2 = "com.google.android.gms.measurement.internal.IMeasurementService"
            boolean r2 = r2.equals(r1)     // Catch:{ RemoteException -> 0x0067 }
            if (r2 == 0) goto L_0x0053
            java.lang.String r1 = "com.google.android.gms.measurement.internal.IMeasurementService"
            android.os.IInterface r1 = r5.queryLocalInterface(r1)     // Catch:{ RemoteException -> 0x0067 }
            boolean r2 = r1 instanceof com.google.android.gms.measurement.internal.zzek     // Catch:{ RemoteException -> 0x0067 }
            if (r2 == 0) goto L_0x0039
            com.google.android.gms.measurement.internal.zzek r1 = (com.google.android.gms.measurement.internal.zzek) r1     // Catch:{ RemoteException -> 0x0067 }
            r0 = r1
            goto L_0x003f
        L_0x0039:
            com.google.android.gms.measurement.internal.zzei r1 = new com.google.android.gms.measurement.internal.zzei     // Catch:{ RemoteException -> 0x0067 }
            r1.<init>(r5)     // Catch:{ RemoteException -> 0x0067 }
            r0 = r1
        L_0x003f:
            com.google.android.gms.measurement.internal.zzjy r5 = r3.zza     // Catch:{ RemoteException -> 0x0051 }
            com.google.android.gms.measurement.internal.zzge r5 = r5.zzt     // Catch:{ RemoteException -> 0x0051 }
            com.google.android.gms.measurement.internal.zzeu r5 = r5.zzaA()     // Catch:{ RemoteException -> 0x0051 }
            com.google.android.gms.measurement.internal.zzes r5 = r5.zzj()     // Catch:{ RemoteException -> 0x0051 }
            java.lang.String r1 = "Bound to IMeasurementService interface"
            r5.zza(r1)     // Catch:{ RemoteException -> 0x0051 }
            goto L_0x0079
        L_0x0051:
            r5 = move-exception
            goto L_0x0068
        L_0x0053:
            com.google.android.gms.measurement.internal.zzjy r5 = r3.zza     // Catch:{ RemoteException -> 0x0067 }
            com.google.android.gms.measurement.internal.zzge r5 = r5.zzt     // Catch:{ RemoteException -> 0x0067 }
            com.google.android.gms.measurement.internal.zzeu r5 = r5.zzaA()     // Catch:{ RemoteException -> 0x0067 }
            com.google.android.gms.measurement.internal.zzes r5 = r5.zzd()     // Catch:{ RemoteException -> 0x0067 }
            java.lang.String r2 = "Got binder with a wrong descriptor"
            r5.zzb(r2, r1)     // Catch:{ RemoteException -> 0x0067 }
            goto L_0x0079
        L_0x0065:
            r4 = move-exception
            goto L_0x00a7
        L_0x0067:
            r5 = move-exception
        L_0x0068:
            com.google.android.gms.measurement.internal.zzjy r5 = r3.zza     // Catch:{ all -> 0x0065 }
            com.google.android.gms.measurement.internal.zzge r5 = r5.zzt     // Catch:{ all -> 0x0065 }
            com.google.android.gms.measurement.internal.zzeu r5 = r5.zzaA()     // Catch:{ all -> 0x0065 }
            com.google.android.gms.measurement.internal.zzes r5 = r5.zzd()     // Catch:{ all -> 0x0065 }
            java.lang.String r1 = "Service connect failed to get IMeasurementService"
            r5.zza(r1)     // Catch:{ all -> 0x0065 }
        L_0x0079:
            if (r0 != 0) goto L_0x0095
            r3.zzb = r4     // Catch:{ all -> 0x0065 }
            com.google.android.gms.common.stats.ConnectionTracker r4 = com.google.android.gms.common.stats.ConnectionTracker.getInstance()     // Catch:{ IllegalArgumentException -> 0x0093 }
            com.google.android.gms.measurement.internal.zzjy r5 = r3.zza     // Catch:{ IllegalArgumentException -> 0x0093 }
            com.google.android.gms.measurement.internal.zzge r5 = r5.zzt     // Catch:{ IllegalArgumentException -> 0x0093 }
            android.content.Context r5 = r5.zzaw()     // Catch:{ IllegalArgumentException -> 0x0093 }
            com.google.android.gms.measurement.internal.zzjy r0 = r3.zza     // Catch:{ IllegalArgumentException -> 0x0093 }
            com.google.android.gms.measurement.internal.zzjx r0 = r0.zza     // Catch:{ IllegalArgumentException -> 0x0093 }
            r4.unbindService(r5, r0)     // Catch:{ IllegalArgumentException -> 0x0093 }
            goto L_0x00a5
        L_0x0093:
            r4 = move-exception
            goto L_0x00a5
        L_0x0095:
            com.google.android.gms.measurement.internal.zzjy r4 = r3.zza     // Catch:{ all -> 0x0065 }
            com.google.android.gms.measurement.internal.zzge r4 = r4.zzt     // Catch:{ all -> 0x0065 }
            com.google.android.gms.measurement.internal.zzgb r4 = r4.zzaB()     // Catch:{ all -> 0x0065 }
            com.google.android.gms.measurement.internal.zzjs r5 = new com.google.android.gms.measurement.internal.zzjs     // Catch:{ all -> 0x0065 }
            r5.<init>(r3, r0)     // Catch:{ all -> 0x0065 }
            r4.zzp(r5)     // Catch:{ all -> 0x0065 }
        L_0x00a5:
            monitor-exit(r3)     // Catch:{ all -> 0x0065 }
            return
        L_0x00a7:
            monitor-exit(r3)     // Catch:{ all -> 0x0065 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzjx.onServiceConnected(android.content.ComponentName, android.os.IBinder):void");
    }

    @MainThread
    public final void onServiceDisconnected(ComponentName componentName) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onServiceDisconnected");
        this.zza.zzt.zzaA().zzc().zza("Service disconnected");
        this.zza.zzt.zzaB().zzp(new zzjt(this, componentName));
    }

    @WorkerThread
    public final void zzb(Intent intent) {
        this.zza.zzg();
        Context zzaw = this.zza.zzt.zzaw();
        ConnectionTracker instance = ConnectionTracker.getInstance();
        synchronized (this) {
            if (this.zzb) {
                this.zza.zzt.zzaA().zzj().zza("Connection attempt already in progress");
                return;
            }
            this.zza.zzt.zzaA().zzj().zza("Using local app measurement service");
            this.zzb = true;
            instance.bindService(zzaw, intent, this.zza.zza, NeedPermissionEvent.PER_IPC_GET_SPEAK_PERM);
        }
    }

    @WorkerThread
    public final void zzc() {
        this.zza.zzg();
        Context zzaw = this.zza.zzt.zzaw();
        synchronized (this) {
            if (this.zzb) {
                this.zza.zzt.zzaA().zzj().zza("Connection attempt already in progress");
            } else if (this.zzc == null || (!this.zzc.isConnecting() && !this.zzc.isConnected())) {
                this.zzc = new zzeq(zzaw, Looper.getMainLooper(), this, this);
                this.zza.zzt.zzaA().zzj().zza("Connecting to remote service");
                this.zzb = true;
                Preconditions.checkNotNull(this.zzc);
                this.zzc.checkAvailabilityAndConnect();
            } else {
                this.zza.zzt.zzaA().zzj().zza("Already awaiting connection attempt");
            }
        }
    }

    @WorkerThread
    public final void zzd() {
        if (this.zzc != null && (this.zzc.isConnected() || this.zzc.isConnecting())) {
            this.zzc.disconnect();
        }
        this.zzc = null;
    }
}
