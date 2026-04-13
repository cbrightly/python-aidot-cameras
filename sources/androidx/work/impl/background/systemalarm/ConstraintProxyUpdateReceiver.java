package androidx.work.impl.background.systemalarm;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Logger;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.background.systemalarm.ConstraintProxy;
import androidx.work.impl.utils.PackageManagerHelper;

public class ConstraintProxyUpdateReceiver extends BroadcastReceiver {
    static final String ACTION = "androidx.work.impl.background.systemalarm.UpdateProxies";
    static final String KEY_BATTERY_CHARGING_PROXY_ENABLED = "KEY_BATTERY_CHARGING_PROXY_ENABLED";
    static final String KEY_BATTERY_NOT_LOW_PROXY_ENABLED = "KEY_BATTERY_NOT_LOW_PROXY_ENABLED";
    static final String KEY_NETWORK_STATE_PROXY_ENABLED = "KEY_NETWORK_STATE_PROXY_ENABLED";
    static final String KEY_STORAGE_NOT_LOW_PROXY_ENABLED = "KEY_STORAGE_NOT_LOW_PROXY_ENABLED";
    static final String TAG = Logger.tagWithPrefix("ConstrntProxyUpdtRecvr");

    public static Intent newConstraintProxyUpdateIntent(Context context, boolean batteryNotLowProxyEnabled, boolean batteryChargingProxyEnabled, boolean storageNotLowProxyEnabled, boolean networkStateProxyEnabled) {
        Intent intent = new Intent(ACTION);
        intent.setComponent(new ComponentName(context, ConstraintProxyUpdateReceiver.class));
        intent.putExtra(KEY_BATTERY_NOT_LOW_PROXY_ENABLED, batteryNotLowProxyEnabled).putExtra(KEY_BATTERY_CHARGING_PROXY_ENABLED, batteryChargingProxyEnabled).putExtra(KEY_STORAGE_NOT_LOW_PROXY_ENABLED, storageNotLowProxyEnabled).putExtra(KEY_NETWORK_STATE_PROXY_ENABLED, networkStateProxyEnabled);
        return intent;
    }

    public void onReceive(@NonNull final Context context, @Nullable final Intent intent) {
        String action = intent != null ? intent.getAction() : null;
        if (!ACTION.equals(action)) {
            Logger.get().debug(TAG, String.format("Ignoring unknown action %s", new Object[]{action}), new Throwable[0]);
            return;
        }
        final BroadcastReceiver.PendingResult pendingResult = goAsync();
        WorkManagerImpl.getInstance(context).getWorkTaskExecutor().executeOnBackgroundThread(new Runnable() {
            public void run() {
                try {
                    boolean batteryNotLowProxyEnabled = intent.getBooleanExtra(ConstraintProxyUpdateReceiver.KEY_BATTERY_NOT_LOW_PROXY_ENABLED, false);
                    boolean batteryChargingProxyEnabled = intent.getBooleanExtra(ConstraintProxyUpdateReceiver.KEY_BATTERY_CHARGING_PROXY_ENABLED, false);
                    boolean storageNotLowProxyEnabled = intent.getBooleanExtra(ConstraintProxyUpdateReceiver.KEY_STORAGE_NOT_LOW_PROXY_ENABLED, false);
                    boolean networkStateProxyEnabled = intent.getBooleanExtra(ConstraintProxyUpdateReceiver.KEY_NETWORK_STATE_PROXY_ENABLED, false);
                    Logger.get().debug(ConstraintProxyUpdateReceiver.TAG, String.format("Updating proxies: BatteryNotLowProxy enabled (%s), BatteryChargingProxy enabled (%s), StorageNotLowProxy (%s), NetworkStateProxy enabled (%s)", new Object[]{Boolean.valueOf(batteryNotLowProxyEnabled), Boolean.valueOf(batteryChargingProxyEnabled), Boolean.valueOf(storageNotLowProxyEnabled), Boolean.valueOf(networkStateProxyEnabled)}), new Throwable[0]);
                    PackageManagerHelper.setComponentEnabled(context, ConstraintProxy.BatteryNotLowProxy.class, batteryNotLowProxyEnabled);
                    PackageManagerHelper.setComponentEnabled(context, ConstraintProxy.BatteryChargingProxy.class, batteryChargingProxyEnabled);
                    PackageManagerHelper.setComponentEnabled(context, ConstraintProxy.StorageNotLowProxy.class, storageNotLowProxyEnabled);
                    PackageManagerHelper.setComponentEnabled(context, ConstraintProxy.NetworkStateProxy.class, networkStateProxyEnabled);
                } finally {
                    pendingResult.finish();
                }
            }
        });
    }
}
