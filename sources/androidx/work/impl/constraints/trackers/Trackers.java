package androidx.work.impl.constraints.trackers;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class Trackers {
    private static Trackers sInstance;
    private BatteryChargingTracker mBatteryChargingTracker;
    private BatteryNotLowTracker mBatteryNotLowTracker;
    private NetworkStateTracker mNetworkStateTracker;
    private StorageNotLowTracker mStorageNotLowTracker;

    @NonNull
    public static synchronized Trackers getInstance(Context context, TaskExecutor taskExecutor) {
        Trackers trackers;
        synchronized (Trackers.class) {
            if (sInstance == null) {
                sInstance = new Trackers(context, taskExecutor);
            }
            trackers = sInstance;
        }
        return trackers;
    }

    @VisibleForTesting
    public static synchronized void setInstance(@NonNull Trackers trackers) {
        synchronized (Trackers.class) {
            sInstance = trackers;
        }
    }

    private Trackers(@NonNull Context context, @NonNull TaskExecutor taskExecutor) {
        Context appContext = context.getApplicationContext();
        this.mBatteryChargingTracker = new BatteryChargingTracker(appContext, taskExecutor);
        this.mBatteryNotLowTracker = new BatteryNotLowTracker(appContext, taskExecutor);
        this.mNetworkStateTracker = new NetworkStateTracker(appContext, taskExecutor);
        this.mStorageNotLowTracker = new StorageNotLowTracker(appContext, taskExecutor);
    }

    @NonNull
    public BatteryChargingTracker getBatteryChargingTracker() {
        return this.mBatteryChargingTracker;
    }

    @NonNull
    public BatteryNotLowTracker getBatteryNotLowTracker() {
        return this.mBatteryNotLowTracker;
    }

    @NonNull
    public NetworkStateTracker getNetworkStateTracker() {
        return this.mNetworkStateTracker;
    }

    @NonNull
    public StorageNotLowTracker getStorageNotLowTracker() {
        return this.mStorageNotLowTracker;
    }
}
