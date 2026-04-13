package androidx.work.impl.background.systemalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.work.Constraints;
import androidx.work.Logger;
import androidx.work.NetworkType;
import androidx.work.impl.model.WorkSpec;
import java.util.List;

public abstract class ConstraintProxy extends BroadcastReceiver {
    private static final String TAG = Logger.tagWithPrefix("ConstraintProxy");

    ConstraintProxy() {
    }

    public void onReceive(Context context, Intent intent) {
        Logger.get().debug(TAG, String.format("onReceive : %s", new Object[]{intent}), new Throwable[0]);
        context.startService(CommandHandler.createConstraintsChangedIntent(context));
    }

    public static class BatteryNotLowProxy extends ConstraintProxy {
        public /* bridge */ /* synthetic */ void onReceive(Context context, Intent intent) {
            ConstraintProxy.super.onReceive(context, intent);
        }
    }

    public static class BatteryChargingProxy extends ConstraintProxy {
        public /* bridge */ /* synthetic */ void onReceive(Context context, Intent intent) {
            ConstraintProxy.super.onReceive(context, intent);
        }
    }

    public static class StorageNotLowProxy extends ConstraintProxy {
        public /* bridge */ /* synthetic */ void onReceive(Context context, Intent intent) {
            ConstraintProxy.super.onReceive(context, intent);
        }
    }

    public static class NetworkStateProxy extends ConstraintProxy {
        public /* bridge */ /* synthetic */ void onReceive(Context context, Intent intent) {
            ConstraintProxy.super.onReceive(context, intent);
        }
    }

    static void updateAll(Context context, List<WorkSpec> workSpecs) {
        boolean batteryNotLowProxyEnabled = false;
        boolean batteryChargingProxyEnabled = false;
        boolean storageNotLowProxyEnabled = false;
        boolean networkStateProxyEnabled = false;
        for (WorkSpec workSpec : workSpecs) {
            Constraints constraints = workSpec.constraints;
            batteryNotLowProxyEnabled |= constraints.requiresBatteryNotLow();
            batteryChargingProxyEnabled |= constraints.requiresCharging();
            storageNotLowProxyEnabled |= constraints.requiresStorageNotLow();
            networkStateProxyEnabled |= constraints.getRequiredNetworkType() != NetworkType.NOT_REQUIRED;
            if (batteryNotLowProxyEnabled && batteryChargingProxyEnabled && storageNotLowProxyEnabled && networkStateProxyEnabled) {
                break;
            }
        }
        context.sendBroadcast(ConstraintProxyUpdateReceiver.newConstraintProxyUpdateIntent(context, batteryNotLowProxyEnabled, batteryChargingProxyEnabled, storageNotLowProxyEnabled, networkStateProxyEnabled));
    }
}
