package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;
import android.os.RemoteException;
import androidx.annotation.RequiresPermission;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzau implements FusedLocationProviderApi {
    static /* bridge */ /* synthetic */ TaskCompletionSource zza(BaseImplementation.ResultHolder resultHolder) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        taskCompletionSource.getTask().addOnCompleteListener(new zzah(resultHolder));
        return taskCompletionSource;
    }

    public final PendingResult<Status> flushLocations(GoogleApiClient googleApiClient) {
        return googleApiClient.execute(new zzaj(this, googleApiClient));
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0073  */
    @androidx.annotation.RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.location.Location getLastLocation(com.google.android.gms.common.api.GoogleApiClient r11) {
        /*
            r10 = this;
            r0 = 0
            r1 = 1
            if (r11 == 0) goto L_0x0006
            r2 = r1
            goto L_0x0007
        L_0x0006:
            r2 = r0
        L_0x0007:
            java.lang.String r3 = "GoogleApiClient parameter is required."
            com.google.android.gms.common.internal.Preconditions.checkArgument(r2, r3)
            com.google.android.gms.common.api.Api$ClientKey r2 = com.google.android.gms.internal.location.zzbp.zza
            com.google.android.gms.common.api.Api$Client r11 = r11.getClient(r2)
            com.google.android.gms.internal.location.zzda r11 = (com.google.android.gms.internal.location.zzda) r11
            java.util.concurrent.atomic.AtomicReference r2 = new java.util.concurrent.atomic.AtomicReference
            r2.<init>()
            java.util.concurrent.CountDownLatch r3 = new java.util.concurrent.CountDownLatch
            r3.<init>(r1)
            com.google.android.gms.tasks.TaskCompletionSource r4 = new com.google.android.gms.tasks.TaskCompletionSource
            r4.<init>()
            r5 = 0
            com.google.android.gms.location.LastLocationRequest$Builder r6 = new com.google.android.gms.location.LastLocationRequest$Builder     // Catch:{ Exception -> 0x007b }
            r6.<init>()     // Catch:{ Exception -> 0x007b }
            com.google.android.gms.location.LastLocationRequest r6 = r6.build()     // Catch:{ Exception -> 0x007b }
            r11.zzt(r6, r4)     // Catch:{ Exception -> 0x007b }
            com.google.android.gms.tasks.Task r11 = r4.getTask()
            com.google.android.gms.internal.location.zzai r4 = new com.google.android.gms.internal.location.zzai
            r4.<init>(r2, r3)
            r11.addOnCompleteListener(r4)
            java.util.concurrent.TimeUnit r11 = java.util.concurrent.TimeUnit.SECONDS
            r6 = 30
            long r6 = r11.toNanos(r6)     // Catch:{ all -> 0x0070 }
            long r8 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0070 }
            long r8 = r8 + r6
        L_0x0049:
            java.util.concurrent.TimeUnit r11 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ InterruptedException -> 0x0064, all -> 0x0062 }
            boolean r11 = r3.await(r6, r11)     // Catch:{ InterruptedException -> 0x0064, all -> 0x0062 }
            if (r0 == 0) goto L_0x0058
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            r0.interrupt()
        L_0x0058:
            if (r11 == 0) goto L_0x0061
            java.lang.Object r11 = r2.get()
            android.location.Location r11 = (android.location.Location) r11
            return r11
        L_0x0061:
            return r5
        L_0x0062:
            r11 = move-exception
            goto L_0x0071
        L_0x0064:
            r11 = move-exception
            long r6 = java.lang.System.nanoTime()     // Catch:{ all -> 0x006d }
            long r6 = r8 - r6
            r0 = r1
            goto L_0x0049
        L_0x006d:
            r11 = move-exception
            r0 = r1
            goto L_0x0071
        L_0x0070:
            r11 = move-exception
        L_0x0071:
            if (r0 == 0) goto L_0x007a
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            r0.interrupt()
        L_0x007a:
            throw r11
        L_0x007b:
            r11 = move-exception
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.location.zzau.getLastLocation(com.google.android.gms.common.api.GoogleApiClient):android.location.Location");
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public final LocationAvailability getLocationAvailability(GoogleApiClient googleApiClient) {
        boolean z;
        if (googleApiClient != null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "GoogleApiClient parameter is required.");
        try {
            return ((zzda) googleApiClient.getClient(zzbp.zza)).zzp();
        } catch (RemoteException e) {
            return null;
        }
    }

    public final PendingResult<Status> removeLocationUpdates(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        return googleApiClient.execute(new zzao(this, googleApiClient, pendingIntent));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public final PendingResult<Status> requestLocationUpdates(GoogleApiClient googleApiClient, LocationRequest locationRequest, PendingIntent pendingIntent) {
        return googleApiClient.execute(new zzam(this, googleApiClient, pendingIntent, locationRequest));
    }

    public final PendingResult<Status> setMockLocation(GoogleApiClient googleApiClient, Location location) {
        return googleApiClient.execute(new zzar(this, googleApiClient, location));
    }

    public final PendingResult<Status> setMockMode(GoogleApiClient googleApiClient, boolean z) {
        return googleApiClient.execute(new zzaq(this, googleApiClient, z));
    }

    public final PendingResult<Status> removeLocationUpdates(GoogleApiClient googleApiClient, LocationCallback locationCallback) {
        return googleApiClient.execute(new zzap(this, googleApiClient, locationCallback));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public final PendingResult<Status> requestLocationUpdates(GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationCallback locationCallback, Looper looper) {
        if (looper == null) {
            looper = Looper.myLooper();
            Preconditions.checkNotNull(looper, "invalid null looper");
        }
        return googleApiClient.execute(new zzal(this, googleApiClient, ListenerHolders.createListenerHolder(locationCallback, looper, LocationCallback.class.getSimpleName()), locationRequest));
    }

    public final PendingResult<Status> removeLocationUpdates(GoogleApiClient googleApiClient, LocationListener locationListener) {
        return googleApiClient.execute(new zzan(this, googleApiClient, locationListener));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public final PendingResult<Status> requestLocationUpdates(GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener) {
        Looper myLooper = Looper.myLooper();
        Preconditions.checkNotNull(myLooper, "invalid null looper");
        return googleApiClient.execute(new zzak(this, googleApiClient, ListenerHolders.createListenerHolder(locationListener, myLooper, LocationListener.class.getSimpleName()), locationRequest));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public final PendingResult<Status> requestLocationUpdates(GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener, Looper looper) {
        if (looper == null) {
            looper = Looper.myLooper();
            Preconditions.checkNotNull(looper, "invalid null looper");
        }
        return googleApiClient.execute(new zzak(this, googleApiClient, ListenerHolders.createListenerHolder(locationListener, looper, LocationListener.class.getSimpleName()), locationRequest));
    }
}
