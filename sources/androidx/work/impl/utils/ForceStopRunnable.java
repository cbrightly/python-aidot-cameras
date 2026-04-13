package androidx.work.impl.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.ApplicationExitInfo;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteTableLockedException;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.app.NotificationCompat;
import androidx.core.os.BuildCompat;
import androidx.work.Configuration;
import androidx.work.InitializationExceptionHandler;
import androidx.work.Logger;
import androidx.work.WorkInfo;
import androidx.work.impl.Schedulers;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkDatabasePathHelper;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.background.systemjob.SystemJobScheduler;
import androidx.work.impl.model.WorkProgressDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class ForceStopRunnable implements Runnable {
    @VisibleForTesting
    static final String ACTION_FORCE_STOP_RESCHEDULE = "ACTION_FORCE_STOP_RESCHEDULE";
    private static final int ALARM_ID = -1;
    private static final long BACKOFF_DURATION_MS = 300;
    @VisibleForTesting
    static final int MAX_ATTEMPTS = 3;
    private static final String TAG = Logger.tagWithPrefix("ForceStopRunnable");
    private static final long TEN_YEARS = TimeUnit.DAYS.toMillis(3650);
    private final Context mContext;
    private int mRetryCount = 0;
    private final WorkManagerImpl mWorkManager;

    public ForceStopRunnable(@NonNull Context context, @NonNull WorkManagerImpl workManager) {
        this.mContext = context.getApplicationContext();
        this.mWorkManager = workManager;
    }

    public void run() {
        try {
            if (!multiProcessChecks()) {
                this.mWorkManager.onForceStopRunnableCompleted();
                return;
            }
            while (true) {
                WorkDatabasePathHelper.migrateDatabase(this.mContext);
                Logger.get().debug(TAG, "Performing cleanup operations.", new Throwable[0]);
                forceStopRunnable();
                break;
            }
            this.mWorkManager.onForceStopRunnableCompleted();
        } catch (SQLiteAccessPermException | SQLiteCantOpenDatabaseException | SQLiteConstraintException | SQLiteDatabaseCorruptException | SQLiteDatabaseLockedException | SQLiteTableLockedException exception) {
            int i = this.mRetryCount + 1;
            this.mRetryCount = i;
            if (i >= 3) {
                Logger logger = Logger.get();
                String str = TAG;
                logger.error(str, "The file system on the device is in a bad state. WorkManager cannot access the app's internal data store.", exception);
                IllegalStateException throwable = new IllegalStateException("The file system on the device is in a bad state. WorkManager cannot access the app's internal data store.", exception);
                InitializationExceptionHandler exceptionHandler = this.mWorkManager.getConfiguration().getExceptionHandler();
                if (exceptionHandler != null) {
                    Logger.get().debug(str, "Routing exception to the specified exception handler", throwable);
                    exceptionHandler.handleException(throwable);
                } else {
                    throw throwable;
                }
            } else {
                long duration = ((long) i) * BACKOFF_DURATION_MS;
                Logger.get().debug(TAG, String.format("Retrying after %s", new Object[]{Long.valueOf(duration)}), exception);
                sleep(((long) this.mRetryCount) * BACKOFF_DURATION_MS);
            }
        } catch (Throwable th) {
            this.mWorkManager.onForceStopRunnableCompleted();
            throw th;
        }
    }

    @VisibleForTesting
    @SuppressLint({"ClassVerificationFailure"})
    public boolean isForceStopped() {
        int flags = 536870912;
        try {
            if (BuildCompat.isAtLeastS()) {
                flags = 536870912 | 33554432;
            }
            PendingIntent pendingIntent = getPendingIntent(this.mContext, flags);
            if (Build.VERSION.SDK_INT >= 30) {
                if (pendingIntent != null) {
                    pendingIntent.cancel();
                }
                List<ApplicationExitInfo> exitInfoList = ((ActivityManager) this.mContext.getSystemService("activity")).getHistoricalProcessExitReasons((String) null, 0, 0);
                if (exitInfoList != null && !exitInfoList.isEmpty()) {
                    for (int i = 0; i < exitInfoList.size(); i++) {
                        if (exitInfoList.get(i).getReason() == 10) {
                            return true;
                        }
                    }
                }
            } else if (pendingIntent == null) {
                setAlarm(this.mContext);
                return true;
            }
            return false;
        } catch (IllegalArgumentException | SecurityException exception) {
            Logger.get().warning(TAG, "Ignoring exception", exception);
            return true;
        }
    }

    @VisibleForTesting
    public void forceStopRunnable() {
        boolean needsScheduling = cleanUp();
        if (shouldRescheduleWorkers()) {
            Logger.get().debug(TAG, "Rescheduling Workers.", new Throwable[0]);
            this.mWorkManager.rescheduleEligibleWork();
            this.mWorkManager.getPreferenceUtils().setNeedsReschedule(false);
        } else if (isForceStopped()) {
            Logger.get().debug(TAG, "Application was force-stopped, rescheduling.", new Throwable[0]);
            this.mWorkManager.rescheduleEligibleWork();
        } else if (needsScheduling) {
            Logger.get().debug(TAG, "Found unfinished work, scheduling it.", new Throwable[0]);
            Schedulers.schedule(this.mWorkManager.getConfiguration(), this.mWorkManager.getWorkDatabase(), this.mWorkManager.getSchedulers());
        }
    }

    @VisibleForTesting
    public boolean cleanUp() {
        boolean needsReconciling = false;
        if (Build.VERSION.SDK_INT >= 23) {
            needsReconciling = SystemJobScheduler.reconcileJobs(this.mContext, this.mWorkManager);
        }
        WorkDatabase workDatabase = this.mWorkManager.getWorkDatabase();
        WorkSpecDao workSpecDao = workDatabase.workSpecDao();
        WorkProgressDao workProgressDao = workDatabase.workProgressDao();
        workDatabase.beginTransaction();
        try {
            List<WorkSpec> workSpecs = workSpecDao.getRunningWork();
            boolean needsScheduling = workSpecs != null && !workSpecs.isEmpty();
            if (needsScheduling) {
                for (WorkSpec workSpec : workSpecs) {
                    workSpecDao.setState(WorkInfo.State.ENQUEUED, workSpec.id);
                    workSpecDao.markWorkSpecScheduled(workSpec.id, -1);
                }
            }
            workProgressDao.deleteAll();
            workDatabase.setTransactionSuccessful();
            if (needsScheduling || needsReconciling) {
                return true;
            }
            return false;
        } finally {
            workDatabase.endTransaction();
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean shouldRescheduleWorkers() {
        return this.mWorkManager.getPreferenceUtils().getNeedsReschedule();
    }

    @VisibleForTesting
    public boolean multiProcessChecks() {
        Configuration configuration = this.mWorkManager.getConfiguration();
        if (TextUtils.isEmpty(configuration.getDefaultProcessName())) {
            Logger.get().debug(TAG, "The default process name was not specified.", new Throwable[0]);
            return true;
        }
        boolean isDefaultProcess = ProcessUtils.isDefaultProcess(this.mContext, configuration);
        Logger.get().debug(TAG, String.format("Is default app process = %s", new Object[]{Boolean.valueOf(isDefaultProcess)}), new Throwable[0]);
        return isDefaultProcess;
    }

    @VisibleForTesting
    public void sleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
        }
    }

    private static PendingIntent getPendingIntent(Context context, int flags) {
        return PendingIntent.getBroadcast(context, -1, getIntent(context), flags);
    }

    @VisibleForTesting
    static Intent getIntent(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(context, BroadcastReceiver.class));
        intent.setAction(ACTION_FORCE_STOP_RESCHEDULE);
        return intent;
    }

    @SuppressLint({"ClassVerificationFailure"})
    static void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        int flags = 134217728;
        if (BuildCompat.isAtLeastS()) {
            flags = 134217728 | 33554432;
        }
        PendingIntent pendingIntent = getPendingIntent(context, flags);
        long triggerAt = System.currentTimeMillis() + TEN_YEARS;
        if (alarmManager == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(0, triggerAt, pendingIntent);
        } else {
            alarmManager.set(0, triggerAt, pendingIntent);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static class BroadcastReceiver extends android.content.BroadcastReceiver {
        private static final String TAG = Logger.tagWithPrefix("ForceStopRunnable$Rcvr");

        public void onReceive(@NonNull Context context, @Nullable Intent intent) {
            if (intent != null && ForceStopRunnable.ACTION_FORCE_STOP_RESCHEDULE.equals(intent.getAction())) {
                Logger.get().verbose(TAG, "Rescheduling alarm that keeps track of force-stops.", new Throwable[0]);
                ForceStopRunnable.setAlarm(context);
            }
        }
    }
}
