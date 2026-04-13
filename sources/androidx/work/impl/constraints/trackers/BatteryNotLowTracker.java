package androidx.work.impl.constraints.trackers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.Logger;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.firebase.analytics.FirebaseAnalytics;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class BatteryNotLowTracker extends BroadcastReceiverConstraintTracker<Boolean> {
    static final float BATTERY_LOW_THRESHOLD = 0.15f;
    private static final String TAG = Logger.tagWithPrefix("BatteryNotLowTracker");

    public BatteryNotLowTracker(@NonNull Context context, @NonNull TaskExecutor taskExecutor) {
        super(context, taskExecutor);
    }

    public Boolean getInitialState() {
        Intent intent = this.mAppContext.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        boolean z = false;
        if (intent == null) {
            Logger.get().error(TAG, "getInitialState - null intent received", new Throwable[0]);
            return null;
        }
        int status = intent.getIntExtra("status", -1);
        float batteryPercentage = ((float) intent.getIntExtra(FirebaseAnalytics.Param.LEVEL, -1)) / ((float) intent.getIntExtra("scale", -1));
        if (status == 1 || batteryPercentage > BATTERY_LOW_THRESHOLD) {
            z = true;
        }
        return Boolean.valueOf(z);
    }

    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_OKAY");
        intentFilter.addAction("android.intent.action.BATTERY_LOW");
        return intentFilter;
    }

    public void onBroadcastReceive(Context context, @NonNull Intent intent) {
        if (intent.getAction() != null) {
            Logger.get().debug(TAG, String.format("Received %s", new Object[]{intent.getAction()}), new Throwable[0]);
            String action = intent.getAction();
            char c = 65535;
            switch (action.hashCode()) {
                case -1980154005:
                    if (action.equals("android.intent.action.BATTERY_OKAY")) {
                        c = 0;
                        break;
                    }
                    break;
                case 490310653:
                    if (action.equals("android.intent.action.BATTERY_LOW")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    setState(true);
                    return;
                case 1:
                    setState(false);
                    return;
                default:
                    return;
            }
        }
    }
}
