package androidx.work.impl.background.systemjob;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.PersistableBundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.Logger;
import androidx.work.OutOfQuotaPolicy;
import androidx.work.WorkInfo;
import androidx.work.impl.Scheduler;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.SystemIdInfo;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.utils.IdGenerator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@RequiresApi(23)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class SystemJobScheduler implements Scheduler {
    private static final String TAG = Logger.tagWithPrefix("SystemJobScheduler");
    private final Context mContext;
    private final JobScheduler mJobScheduler;
    private final SystemJobInfoConverter mSystemJobInfoConverter;
    private final WorkManagerImpl mWorkManager;

    public SystemJobScheduler(@NonNull Context context, @NonNull WorkManagerImpl workManager) {
        this(context, workManager, (JobScheduler) context.getSystemService("jobscheduler"), new SystemJobInfoConverter(context));
    }

    @VisibleForTesting
    public SystemJobScheduler(Context context, WorkManagerImpl workManager, JobScheduler jobScheduler, SystemJobInfoConverter systemJobInfoConverter) {
        this.mContext = context;
        this.mWorkManager = workManager;
        this.mJobScheduler = jobScheduler;
        this.mSystemJobInfoConverter = systemJobInfoConverter;
    }

    public void schedule(@NonNull WorkSpec... workSpecs) {
        List<Integer> jobIds;
        int nextJobId;
        WorkDatabase workDatabase = this.mWorkManager.getWorkDatabase();
        IdGenerator idGenerator = new IdGenerator(workDatabase);
        int length = workSpecs.length;
        int i = 0;
        while (i < length) {
            WorkSpec workSpec = workSpecs[i];
            workDatabase.beginTransaction();
            try {
                WorkSpec currentDbWorkSpec = workDatabase.workSpecDao().getWorkSpec(workSpec.id);
                if (currentDbWorkSpec == null) {
                    Logger.get().warning(TAG, "Skipping scheduling " + workSpec.id + " because it's no longer in the DB", new Throwable[0]);
                    workDatabase.setTransactionSuccessful();
                } else if (currentDbWorkSpec.state != WorkInfo.State.ENQUEUED) {
                    Logger.get().warning(TAG, "Skipping scheduling " + workSpec.id + " because it is no longer enqueued", new Throwable[0]);
                    workDatabase.setTransactionSuccessful();
                    workDatabase.endTransaction();
                } else {
                    SystemIdInfo info = workDatabase.systemIdInfoDao().getSystemIdInfo(workSpec.id);
                    int jobId = info != null ? info.systemId : idGenerator.nextJobSchedulerIdWithRange(this.mWorkManager.getConfiguration().getMinJobSchedulerId(), this.mWorkManager.getConfiguration().getMaxJobSchedulerId());
                    if (info == null) {
                        this.mWorkManager.getWorkDatabase().systemIdInfoDao().insertSystemIdInfo(new SystemIdInfo(workSpec.id, jobId));
                    }
                    scheduleInternal(workSpec, jobId);
                    if (Build.VERSION.SDK_INT == 23 && (jobIds = getPendingJobIds(this.mContext, this.mJobScheduler, workSpec.id)) != null) {
                        int index = jobIds.indexOf(Integer.valueOf(jobId));
                        if (index >= 0) {
                            jobIds.remove(index);
                        }
                        if (!jobIds.isEmpty()) {
                            nextJobId = jobIds.get(0).intValue();
                        } else {
                            nextJobId = idGenerator.nextJobSchedulerIdWithRange(this.mWorkManager.getConfiguration().getMinJobSchedulerId(), this.mWorkManager.getConfiguration().getMaxJobSchedulerId());
                        }
                        scheduleInternal(workSpec, nextJobId);
                    }
                    workDatabase.setTransactionSuccessful();
                    workDatabase.endTransaction();
                }
                i++;
            } finally {
                workDatabase.endTransaction();
            }
        }
    }

    @VisibleForTesting
    public void scheduleInternal(WorkSpec workSpec, int jobId) {
        JobInfo jobInfo = this.mSystemJobInfoConverter.convert(workSpec, jobId);
        Logger logger = Logger.get();
        String str = TAG;
        logger.debug(str, String.format("Scheduling work ID %s Job ID %s", new Object[]{workSpec.id, Integer.valueOf(jobId)}), new Throwable[0]);
        try {
            if (this.mJobScheduler.schedule(jobInfo) == 0) {
                Logger.get().warning(str, String.format("Unable to schedule work ID %s", new Object[]{workSpec.id}), new Throwable[0]);
                if (workSpec.expedited && workSpec.outOfQuotaPolicy == OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST) {
                    workSpec.expedited = false;
                    Logger.get().debug(str, String.format("Scheduling a non-expedited job (work ID %s)", new Object[]{workSpec.id}), new Throwable[0]);
                    scheduleInternal(workSpec, jobId);
                }
            }
        } catch (IllegalStateException e) {
            List<JobInfo> jobs = getPendingJobs(this.mContext, this.mJobScheduler);
            String message = String.format(Locale.getDefault(), "JobScheduler 100 job limit exceeded.  We count %d WorkManager jobs in JobScheduler; we have %d tracked jobs in our DB; our Configuration limit is %d.", new Object[]{Integer.valueOf(jobs != null ? jobs.size() : 0), Integer.valueOf(this.mWorkManager.getWorkDatabase().workSpecDao().getScheduledWork().size()), Integer.valueOf(this.mWorkManager.getConfiguration().getMaxSchedulerLimit())});
            Logger.get().error(TAG, message, new Throwable[0]);
            throw new IllegalStateException(message, e);
        } catch (Throwable throwable) {
            Logger.get().error(TAG, String.format("Unable to schedule %s", new Object[]{workSpec}), throwable);
        }
    }

    public void cancel(@NonNull String workSpecId) {
        List<Integer> jobIds = getPendingJobIds(this.mContext, this.mJobScheduler, workSpecId);
        if (jobIds != null && !jobIds.isEmpty()) {
            for (Integer intValue : jobIds) {
                cancelJobById(this.mJobScheduler, intValue.intValue());
            }
            this.mWorkManager.getWorkDatabase().systemIdInfoDao().removeSystemIdInfo(workSpecId);
        }
    }

    public boolean hasLimitedSchedulingSlots() {
        return true;
    }

    private static void cancelJobById(@NonNull JobScheduler jobScheduler, int id) {
        try {
            jobScheduler.cancel(id);
        } catch (Throwable throwable) {
            Logger.get().error(TAG, String.format(Locale.getDefault(), "Exception while trying to cancel job (%d)", new Object[]{Integer.valueOf(id)}), throwable);
        }
    }

    public static void cancelAll(@NonNull Context context) {
        List<JobInfo> jobs;
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        if (jobScheduler != null && (jobs = getPendingJobs(context, jobScheduler)) != null && !jobs.isEmpty()) {
            for (JobInfo jobInfo : jobs) {
                cancelJobById(jobScheduler, jobInfo.getId());
            }
        }
    }

    public static boolean reconcileJobs(@NonNull Context context, @NonNull WorkManagerImpl workManager) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        List<JobInfo> jobs = getPendingJobs(context, jobScheduler);
        List<String> workManagerWorkSpecs = workManager.getWorkDatabase().systemIdInfoDao().getWorkSpecIds();
        Set<String> jobSchedulerWorkSpecs = new HashSet<>(jobs != null ? jobs.size() : 0);
        if (jobs != null && !jobs.isEmpty()) {
            for (JobInfo jobInfo : jobs) {
                String workSpecId = getWorkSpecIdFromJobInfo(jobInfo);
                if (!TextUtils.isEmpty(workSpecId)) {
                    jobSchedulerWorkSpecs.add(workSpecId);
                } else {
                    cancelJobById(jobScheduler, jobInfo.getId());
                }
            }
        }
        boolean needsReconciling = false;
        Iterator<String> it = workManagerWorkSpecs.iterator();
        while (true) {
            if (it.hasNext()) {
                if (!jobSchedulerWorkSpecs.contains(it.next())) {
                    Logger.get().debug(TAG, "Reconciling jobs", new Throwable[0]);
                    needsReconciling = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (needsReconciling) {
            WorkDatabase workDatabase = workManager.getWorkDatabase();
            workDatabase.beginTransaction();
            try {
                WorkSpecDao workSpecDao = workDatabase.workSpecDao();
                for (String workSpecId2 : workManagerWorkSpecs) {
                    workSpecDao.markWorkSpecScheduled(workSpecId2, -1);
                }
                workDatabase.setTransactionSuccessful();
            } finally {
                workDatabase.endTransaction();
            }
        }
        return needsReconciling;
    }

    @Nullable
    private static List<JobInfo> getPendingJobs(@NonNull Context context, @NonNull JobScheduler jobScheduler) {
        List<JobInfo> pendingJobs = null;
        try {
            pendingJobs = jobScheduler.getAllPendingJobs();
        } catch (Throwable exception) {
            Logger.get().error(TAG, "getAllPendingJobs() is not reliable on this device.", exception);
        }
        if (pendingJobs == null) {
            return null;
        }
        List<JobInfo> filtered = new ArrayList<>(pendingJobs.size());
        ComponentName jobServiceComponent = new ComponentName(context, SystemJobService.class);
        for (JobInfo jobInfo : pendingJobs) {
            if (jobServiceComponent.equals(jobInfo.getService())) {
                filtered.add(jobInfo);
            }
        }
        return filtered;
    }

    @Nullable
    private static List<Integer> getPendingJobIds(@NonNull Context context, @NonNull JobScheduler jobScheduler, @NonNull String workSpecId) {
        List<JobInfo> jobs = getPendingJobs(context, jobScheduler);
        if (jobs == null) {
            return null;
        }
        List<Integer> jobIds = new ArrayList<>(2);
        for (JobInfo jobInfo : jobs) {
            if (workSpecId.equals(getWorkSpecIdFromJobInfo(jobInfo))) {
                jobIds.add(Integer.valueOf(jobInfo.getId()));
            }
        }
        return jobIds;
    }

    @Nullable
    private static String getWorkSpecIdFromJobInfo(@NonNull JobInfo jobInfo) {
        PersistableBundle extras = jobInfo.getExtras();
        if (extras == null) {
            return null;
        }
        try {
            if (extras.containsKey("EXTRA_WORK_SPEC_ID")) {
                return extras.getString("EXTRA_WORK_SPEC_ID");
            }
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }
}
