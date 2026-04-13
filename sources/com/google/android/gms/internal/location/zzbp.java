package com.google.android.gms.internal.location;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.api.internal.RegistrationMethods;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LastLocationRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.zzm;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.Executor;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzbp extends GoogleApi implements FusedLocationProviderClient {
    static final Api.ClientKey zza;
    public static final Api zzb;

    static {
        Api.ClientKey clientKey = new Api.ClientKey();
        zza = clientKey;
        zzb = new Api("LocationServices.API", new zzbm(), clientKey);
    }

    public zzbp(Activity activity) {
        super(activity, zzb, Api.ApiOptions.NO_OPTIONS, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    private final Task zza(LocationRequest locationRequest, ListenerHolder listenerHolder) {
        zzbo zzbo = new zzbo(this, listenerHolder, zzax.zza);
        return doRegisterEventListener(RegistrationMethods.builder().register(new zzay(zzbo, locationRequest)).unregister(zzbo).withHolder(listenerHolder).setMethodKey(2436).build());
    }

    private final Task zzb(LocationRequest locationRequest, ListenerHolder listenerHolder) {
        zzbo zzbo = new zzbo(this, listenerHolder, zzbd.zza);
        return doRegisterEventListener(RegistrationMethods.builder().register(new zzbf(zzbo, locationRequest)).unregister(zzbo).withHolder(listenerHolder).setMethodKey(2435).build());
    }

    public final Task<Void> flushLocations() {
        return doWrite(TaskApiCall.builder().run(zzav.zza).setMethodKey(2422).build());
    }

    public final Task<Location> getCurrentLocation(int i, @Nullable CancellationToken cancellationToken) {
        CurrentLocationRequest.Builder builder = new CurrentLocationRequest.Builder();
        builder.setPriority(i);
        CurrentLocationRequest build = builder.build();
        if (cancellationToken != null) {
            Preconditions.checkArgument(!cancellationToken.isCancellationRequested(), "cancellationToken may not be already canceled");
        }
        Task<Location> doRead = doRead(TaskApiCall.builder().run(new zzbh(build, cancellationToken)).setMethodKey(2415).build());
        if (cancellationToken == null) {
            return doRead;
        }
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource(cancellationToken);
        doRead.continueWith(new zzbi(taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    public final Task<Location> getLastLocation() {
        return doRead(TaskApiCall.builder().run(zzbe.zza).setMethodKey(2414).build());
    }

    public final Task<LocationAvailability> getLocationAvailability() {
        return doRead(TaskApiCall.builder().run(zzba.zza).setMethodKey(2416).build());
    }

    public final Task<Void> removeLocationUpdates(PendingIntent pendingIntent) {
        return doWrite(TaskApiCall.builder().run(new zzbg(pendingIntent)).setMethodKey(2418).build());
    }

    public final Task<Void> requestLocationUpdates(LocationRequest locationRequest, PendingIntent pendingIntent) {
        return doWrite(TaskApiCall.builder().run(new zzaz(pendingIntent, locationRequest)).setMethodKey(2417).build());
    }

    public final Task<Void> setMockLocation(Location location) {
        boolean z;
        if (location != null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        return doWrite(TaskApiCall.builder().run(new zzaw(location)).setMethodKey(2421).build());
    }

    public final Task<Void> setMockMode(boolean z) {
        return doWrite(TaskApiCall.builder().run(new zzbb(z)).setMethodKey(2420).build());
    }

    public zzbp(Context context) {
        super(context, zzb, Api.ApiOptions.NO_OPTIONS, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    public final Task<Location> getLastLocation(LastLocationRequest lastLocationRequest) {
        return doRead(TaskApiCall.builder().run(new zzbj(lastLocationRequest)).setMethodKey(2414).setFeatures(zzm.zzf).build());
    }

    public final Task<Void> removeLocationUpdates(LocationCallback locationCallback) {
        return doUnregisterEventListener(ListenerHolders.createListenerKey(locationCallback, LocationCallback.class.getSimpleName()), 2418).continueWith(zzbk.zza, zzbc.zza);
    }

    public final Task<Void> requestLocationUpdates(LocationRequest locationRequest, LocationCallback locationCallback, @Nullable Looper looper) {
        if (looper == null) {
            looper = Looper.myLooper();
            Preconditions.checkNotNull(looper, "invalid null looper");
        }
        return zza(locationRequest, ListenerHolders.createListenerHolder(locationCallback, looper, LocationCallback.class.getSimpleName()));
    }

    public final Task<Void> removeLocationUpdates(LocationListener locationListener) {
        return doUnregisterEventListener(ListenerHolders.createListenerKey(locationListener, LocationListener.class.getSimpleName()), 2418).continueWith(zzbk.zza, zzbl.zza);
    }

    public final Task<Void> requestLocationUpdates(LocationRequest locationRequest, LocationListener locationListener, @Nullable Looper looper) {
        if (looper == null) {
            looper = Looper.myLooper();
            Preconditions.checkNotNull(looper, "invalid null looper");
        }
        return zzb(locationRequest, ListenerHolders.createListenerHolder(locationListener, looper, LocationListener.class.getSimpleName()));
    }

    public final Task<Location> getCurrentLocation(CurrentLocationRequest currentLocationRequest, @Nullable CancellationToken cancellationToken) {
        if (cancellationToken != null) {
            Preconditions.checkArgument(!cancellationToken.isCancellationRequested(), "cancellationToken may not be already canceled");
        }
        Task<Location> doRead = doRead(TaskApiCall.builder().run(new zzbh(currentLocationRequest, cancellationToken)).setMethodKey(2415).build());
        if (cancellationToken == null) {
            return doRead;
        }
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource(cancellationToken);
        doRead.continueWith(new zzbi(taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    public final Task<Void> requestLocationUpdates(LocationRequest locationRequest, Executor executor, LocationCallback locationCallback) {
        return zza(locationRequest, ListenerHolders.createListenerHolder(locationCallback, executor, LocationCallback.class.getSimpleName()));
    }

    public final Task<Void> requestLocationUpdates(LocationRequest locationRequest, Executor executor, LocationListener locationListener) {
        return zzb(locationRequest, ListenerHolders.createListenerHolder(locationListener, executor, LocationListener.class.getSimpleName()));
    }
}
