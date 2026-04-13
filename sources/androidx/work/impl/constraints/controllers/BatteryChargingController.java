package androidx.work.impl.constraints.controllers;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.impl.constraints.trackers.Trackers;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;

public class BatteryChargingController extends ConstraintController<Boolean> {
    public BatteryChargingController(Context context, TaskExecutor taskExecutor) {
        super(Trackers.getInstance(context, taskExecutor).getBatteryChargingTracker());
    }

    /* access modifiers changed from: package-private */
    public boolean hasConstraint(@NonNull WorkSpec workSpec) {
        return workSpec.constraints.requiresCharging();
    }

    /* access modifiers changed from: package-private */
    public boolean isConstrained(@NonNull Boolean isBatteryCharging) {
        return !isBatteryCharging.booleanValue();
    }
}
