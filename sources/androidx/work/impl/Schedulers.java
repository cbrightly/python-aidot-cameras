package androidx.work.impl;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.work.Configuration;
import androidx.work.Logger;
import androidx.work.impl.background.systemalarm.SystemAlarmScheduler;
import androidx.work.impl.background.systemalarm.SystemAlarmService;
import androidx.work.impl.background.systemjob.SystemJobScheduler;
import androidx.work.impl.background.systemjob.SystemJobService;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.utils.PackageManagerHelper;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class Schedulers {
    public static final String GCM_SCHEDULER = "androidx.work.impl.background.gcm.GcmScheduler";
    private static final String TAG = Logger.tagWithPrefix("Schedulers");

    public static void schedule(@NonNull Configuration configuration, @NonNull WorkDatabase workDatabase, List<Scheduler> schedulers) {
        if (schedulers != null && schedulers.size() != 0) {
            WorkSpecDao workSpecDao = workDatabase.workSpecDao();
            workDatabase.beginTransaction();
            try {
                List<WorkSpec> eligibleWorkSpecsForLimitedSlots = workSpecDao.getEligibleWorkForScheduling(configuration.getMaxSchedulerLimit());
                List<WorkSpec> allEligibleWorkSpecs = workSpecDao.getAllEligibleWorkSpecsForScheduling(200);
                if (eligibleWorkSpecsForLimitedSlots != null && eligibleWorkSpecsForLimitedSlots.size() > 0) {
                    long now = System.currentTimeMillis();
                    for (WorkSpec workSpec : eligibleWorkSpecsForLimitedSlots) {
                        workSpecDao.markWorkSpecScheduled(workSpec.id, now);
                    }
                }
                workDatabase.setTransactionSuccessful();
                if (eligibleWorkSpecsForLimitedSlots != null && eligibleWorkSpecsForLimitedSlots.size() > 0) {
                    WorkSpec[] eligibleWorkSpecsArray = (WorkSpec[]) eligibleWorkSpecsForLimitedSlots.toArray(new WorkSpec[eligibleWorkSpecsForLimitedSlots.size()]);
                    for (Scheduler scheduler : schedulers) {
                        if (scheduler.hasLimitedSchedulingSlots()) {
                            scheduler.schedule(eligibleWorkSpecsArray);
                        }
                    }
                }
                if (allEligibleWorkSpecs != null && allEligibleWorkSpecs.size() > 0) {
                    WorkSpec[] enqueuedWorkSpecsArray = (WorkSpec[]) allEligibleWorkSpecs.toArray(new WorkSpec[allEligibleWorkSpecs.size()]);
                    for (Scheduler scheduler2 : schedulers) {
                        if (!scheduler2.hasLimitedSchedulingSlots()) {
                            scheduler2.schedule(enqueuedWorkSpecsArray);
                        }
                    }
                }
            } finally {
                workDatabase.endTransaction();
            }
        }
    }

    @NonNull
    static Scheduler createBestAvailableBackgroundScheduler(@NonNull Context context, @NonNull WorkManagerImpl workManager) {
        if (Build.VERSION.SDK_INT >= 23) {
            Scheduler scheduler = new SystemJobScheduler(context, workManager);
            PackageManagerHelper.setComponentEnabled(context, SystemJobService.class, true);
            Logger.get().debug(TAG, "Created SystemJobScheduler and enabled SystemJobService", new Throwable[0]);
            return scheduler;
        }
        Scheduler scheduler2 = tryCreateGcmBasedScheduler(context);
        if (scheduler2 != null) {
            return scheduler2;
        }
        Scheduler scheduler3 = new SystemAlarmScheduler(context);
        PackageManagerHelper.setComponentEnabled(context, SystemAlarmService.class, true);
        Logger.get().debug(TAG, "Created SystemAlarmScheduler", new Throwable[0]);
        return scheduler3;
    }

    @Nullable
    private static Scheduler tryCreateGcmBasedScheduler(@NonNull Context context) {
        try {
            Scheduler scheduler = (Scheduler) Class.forName(GCM_SCHEDULER).getConstructor(new Class[]{Context.class}).newInstance(new Object[]{context});
            Logger.get().debug(TAG, String.format("Created %s", new Object[]{GCM_SCHEDULER}), new Throwable[0]);
            return scheduler;
        } catch (Throwable throwable) {
            Logger.get().debug(TAG, "Unable to create GCM Scheduler", throwable);
            return null;
        }
    }

    private Schedulers() {
    }
}
