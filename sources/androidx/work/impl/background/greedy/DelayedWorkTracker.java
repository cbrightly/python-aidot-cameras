package androidx.work.impl.background.greedy;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.Logger;
import androidx.work.RunnableScheduler;
import androidx.work.impl.model.WorkSpec;
import java.util.HashMap;
import java.util.Map;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class DelayedWorkTracker {
    static final String TAG = Logger.tagWithPrefix("DelayedWorkTracker");
    final GreedyScheduler mGreedyScheduler;
    private final RunnableScheduler mRunnableScheduler;
    private final Map<String, Runnable> mRunnables = new HashMap();

    public DelayedWorkTracker(@NonNull GreedyScheduler scheduler, @NonNull RunnableScheduler runnableScheduler) {
        this.mGreedyScheduler = scheduler;
        this.mRunnableScheduler = runnableScheduler;
    }

    public void schedule(@NonNull final WorkSpec workSpec) {
        Runnable existing = this.mRunnables.remove(workSpec.id);
        if (existing != null) {
            this.mRunnableScheduler.cancel(existing);
        }
        Runnable runnable = new Runnable() {
            public void run() {
                Logger.get().debug(DelayedWorkTracker.TAG, String.format("Scheduling work %s", new Object[]{workSpec.id}), new Throwable[0]);
                DelayedWorkTracker.this.mGreedyScheduler.schedule(workSpec);
            }
        };
        this.mRunnables.put(workSpec.id, runnable);
        long now = System.currentTimeMillis();
        this.mRunnableScheduler.scheduleWithDelay(workSpec.calculateNextRunTime() - now, runnable);
    }

    public void unschedule(@NonNull String workSpecId) {
        Runnable runnable = this.mRunnables.remove(workSpecId);
        if (runnable != null) {
            this.mRunnableScheduler.cancel(runnable);
        }
    }
}
