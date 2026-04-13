package androidx.work.impl.constraints;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.work.Logger;
import androidx.work.impl.constraints.controllers.BatteryChargingController;
import androidx.work.impl.constraints.controllers.BatteryNotLowController;
import androidx.work.impl.constraints.controllers.ConstraintController;
import androidx.work.impl.constraints.controllers.NetworkConnectedController;
import androidx.work.impl.constraints.controllers.NetworkMeteredController;
import androidx.work.impl.constraints.controllers.NetworkNotRoamingController;
import androidx.work.impl.constraints.controllers.NetworkUnmeteredController;
import androidx.work.impl.constraints.controllers.StorageNotLowController;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import java.util.ArrayList;
import java.util.List;

public class WorkConstraintsTracker implements ConstraintController.OnConstraintUpdatedCallback {
    private static final String TAG = Logger.tagWithPrefix("WorkConstraintsTracker");
    @Nullable
    private final WorkConstraintsCallback mCallback;
    private final ConstraintController<?>[] mConstraintControllers;
    private final Object mLock = new Object();

    public WorkConstraintsTracker(@NonNull Context context, @NonNull TaskExecutor taskExecutor, @Nullable WorkConstraintsCallback callback) {
        Context appContext = context.getApplicationContext();
        this.mCallback = callback;
        this.mConstraintControllers = new ConstraintController[]{new BatteryChargingController(appContext, taskExecutor), new BatteryNotLowController(appContext, taskExecutor), new StorageNotLowController(appContext, taskExecutor), new NetworkConnectedController(appContext, taskExecutor), new NetworkUnmeteredController(appContext, taskExecutor), new NetworkNotRoamingController(appContext, taskExecutor), new NetworkMeteredController(appContext, taskExecutor)};
    }

    @VisibleForTesting
    WorkConstraintsTracker(@Nullable WorkConstraintsCallback callback, ConstraintController<?>[] controllers) {
        this.mCallback = callback;
        this.mConstraintControllers = controllers;
    }

    public void replace(@NonNull Iterable<WorkSpec> workSpecs) {
        synchronized (this.mLock) {
            for (ConstraintController<?> controller : this.mConstraintControllers) {
                controller.setCallback((ConstraintController.OnConstraintUpdatedCallback) null);
            }
            for (ConstraintController<?> controller2 : this.mConstraintControllers) {
                controller2.replace(workSpecs);
            }
            for (ConstraintController<?> controller3 : this.mConstraintControllers) {
                controller3.setCallback(this);
            }
        }
    }

    public void reset() {
        synchronized (this.mLock) {
            for (ConstraintController<?> controller : this.mConstraintControllers) {
                controller.reset();
            }
        }
    }

    public boolean areAllConstraintsMet(@NonNull String workSpecId) {
        synchronized (this.mLock) {
            for (ConstraintController<?> constraintController : this.mConstraintControllers) {
                if (constraintController.isWorkSpecConstrained(workSpecId)) {
                    Logger.get().debug(TAG, String.format("Work %s constrained by %s", new Object[]{workSpecId, constraintController.getClass().getSimpleName()}), new Throwable[0]);
                    return false;
                }
            }
            return true;
        }
    }

    public void onConstraintMet(@NonNull List<String> workSpecIds) {
        synchronized (this.mLock) {
            List<String> unconstrainedWorkSpecIds = new ArrayList<>();
            for (String workSpecId : workSpecIds) {
                if (areAllConstraintsMet(workSpecId)) {
                    Logger.get().debug(TAG, String.format("Constraints met for %s", new Object[]{workSpecId}), new Throwable[0]);
                    unconstrainedWorkSpecIds.add(workSpecId);
                }
            }
            WorkConstraintsCallback workConstraintsCallback = this.mCallback;
            if (workConstraintsCallback != null) {
                workConstraintsCallback.onAllConstraintsMet(unconstrainedWorkSpecIds);
            }
        }
    }

    public void onConstraintNotMet(@NonNull List<String> workSpecIds) {
        synchronized (this.mLock) {
            WorkConstraintsCallback workConstraintsCallback = this.mCallback;
            if (workConstraintsCallback != null) {
                workConstraintsCallback.onAllConstraintsNotMet(workSpecIds);
            }
        }
    }
}
