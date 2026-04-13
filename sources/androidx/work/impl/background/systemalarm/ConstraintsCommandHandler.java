package androidx.work.impl.background.systemalarm;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.WorkerThread;
import androidx.work.Logger;
import androidx.work.impl.background.systemalarm.SystemAlarmDispatcher;
import androidx.work.impl.constraints.WorkConstraintsCallback;
import androidx.work.impl.constraints.WorkConstraintsTracker;
import androidx.work.impl.model.WorkSpec;
import java.util.ArrayList;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class ConstraintsCommandHandler {
    private static final String TAG = Logger.tagWithPrefix("ConstraintsCmdHandler");
    private final Context mContext;
    private final SystemAlarmDispatcher mDispatcher;
    private final int mStartId;
    private final WorkConstraintsTracker mWorkConstraintsTracker;

    ConstraintsCommandHandler(@NonNull Context context, int startId, @NonNull SystemAlarmDispatcher dispatcher) {
        this.mContext = context;
        this.mStartId = startId;
        this.mDispatcher = dispatcher;
        this.mWorkConstraintsTracker = new WorkConstraintsTracker(context, dispatcher.getTaskExecutor(), (WorkConstraintsCallback) null);
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public void handleConstraintsChanged() {
        List<WorkSpec> candidates = this.mDispatcher.getWorkManager().getWorkDatabase().workSpecDao().getScheduledWork();
        ConstraintProxy.updateAll(this.mContext, candidates);
        this.mWorkConstraintsTracker.replace(candidates);
        List<WorkSpec> eligibleWorkSpecs = new ArrayList<>(candidates.size());
        long now = System.currentTimeMillis();
        for (WorkSpec workSpec : candidates) {
            String workSpecId = workSpec.id;
            if (now >= workSpec.calculateNextRunTime() && (!workSpec.hasConstraints() || this.mWorkConstraintsTracker.areAllConstraintsMet(workSpecId))) {
                eligibleWorkSpecs.add(workSpec);
            }
        }
        for (WorkSpec workSpec2 : eligibleWorkSpecs) {
            String workSpecId2 = workSpec2.id;
            Intent intent = CommandHandler.createDelayMetIntent(this.mContext, workSpecId2);
            Logger.get().debug(TAG, String.format("Creating a delay_met command for workSpec with id (%s)", new Object[]{workSpecId2}), new Throwable[0]);
            SystemAlarmDispatcher systemAlarmDispatcher = this.mDispatcher;
            systemAlarmDispatcher.postOnMainThread(new SystemAlarmDispatcher.AddRunnable(systemAlarmDispatcher, intent, this.mStartId));
        }
        this.mWorkConstraintsTracker.reset();
    }
}
