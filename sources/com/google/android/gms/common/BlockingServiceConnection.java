package com.google.android.gms.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class BlockingServiceConnection implements ServiceConnection {
    boolean zza = false;
    private final BlockingQueue zzb = new LinkedBlockingQueue();

    @ResultIgnorabilityUnspecified
    @NonNull
    @KeepForSdk
    public IBinder getService() {
        Preconditions.checkNotMainThread("BlockingServiceConnection.getService() called on main thread");
        if (!this.zza) {
            this.zza = true;
            return (IBinder) this.zzb.take();
        }
        throw new IllegalStateException("Cannot call get on this connection more than once");
    }

    @ResultIgnorabilityUnspecified
    @NonNull
    @KeepForSdk
    public IBinder getServiceWithTimeout(long timeout, @NonNull TimeUnit timeUnit) {
        Preconditions.checkNotMainThread("BlockingServiceConnection.getServiceWithTimeout() called on main thread");
        if (!this.zza) {
            this.zza = true;
            IBinder iBinder = (IBinder) this.zzb.poll(timeout, timeUnit);
            if (iBinder != null) {
                return iBinder;
            }
            throw new TimeoutException("Timed out waiting for the service connection");
        }
        throw new IllegalStateException("Cannot call get on this connection more than once");
    }

    public final void onServiceConnected(@NonNull ComponentName componentName, @NonNull IBinder iBinder) {
        this.zzb.add(iBinder);
    }

    public final void onServiceDisconnected(@NonNull ComponentName componentName) {
    }
}
