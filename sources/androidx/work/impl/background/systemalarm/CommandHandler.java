package androidx.work.impl.background.systemalarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.WorkerThread;
import androidx.work.Logger;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.background.systemalarm.SystemAlarmDispatcher;
import androidx.work.impl.model.WorkSpec;
import java.util.HashMap;
import java.util.Map;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class CommandHandler implements ExecutionListener {
    static final String ACTION_CONSTRAINTS_CHANGED = "ACTION_CONSTRAINTS_CHANGED";
    static final String ACTION_DELAY_MET = "ACTION_DELAY_MET";
    static final String ACTION_EXECUTION_COMPLETED = "ACTION_EXECUTION_COMPLETED";
    static final String ACTION_RESCHEDULE = "ACTION_RESCHEDULE";
    static final String ACTION_SCHEDULE_WORK = "ACTION_SCHEDULE_WORK";
    static final String ACTION_STOP_WORK = "ACTION_STOP_WORK";
    private static final String KEY_NEEDS_RESCHEDULE = "KEY_NEEDS_RESCHEDULE";
    private static final String KEY_WORKSPEC_ID = "KEY_WORKSPEC_ID";
    private static final String TAG = Logger.tagWithPrefix("CommandHandler");
    static final long WORK_PROCESSING_TIME_IN_MS = 600000;
    private final Context mContext;
    private final Object mLock = new Object();
    private final Map<String, ExecutionListener> mPendingDelayMet = new HashMap();

    static Intent createScheduleWorkIntent(@NonNull Context context, @NonNull String workSpecId) {
        Intent intent = new Intent(context, SystemAlarmService.class);
        intent.setAction(ACTION_SCHEDULE_WORK);
        intent.putExtra(KEY_WORKSPEC_ID, workSpecId);
        return intent;
    }

    static Intent createDelayMetIntent(@NonNull Context context, @NonNull String workSpecId) {
        Intent intent = new Intent(context, SystemAlarmService.class);
        intent.setAction(ACTION_DELAY_MET);
        intent.putExtra(KEY_WORKSPEC_ID, workSpecId);
        return intent;
    }

    static Intent createStopWorkIntent(@NonNull Context context, @NonNull String workSpecId) {
        Intent intent = new Intent(context, SystemAlarmService.class);
        intent.setAction(ACTION_STOP_WORK);
        intent.putExtra(KEY_WORKSPEC_ID, workSpecId);
        return intent;
    }

    static Intent createConstraintsChangedIntent(@NonNull Context context) {
        Intent intent = new Intent(context, SystemAlarmService.class);
        intent.setAction(ACTION_CONSTRAINTS_CHANGED);
        return intent;
    }

    static Intent createRescheduleIntent(@NonNull Context context) {
        Intent intent = new Intent(context, SystemAlarmService.class);
        intent.setAction(ACTION_RESCHEDULE);
        return intent;
    }

    static Intent createExecutionCompletedIntent(@NonNull Context context, @NonNull String workSpecId, boolean needsReschedule) {
        Intent intent = new Intent(context, SystemAlarmService.class);
        intent.setAction(ACTION_EXECUTION_COMPLETED);
        intent.putExtra(KEY_WORKSPEC_ID, workSpecId);
        intent.putExtra(KEY_NEEDS_RESCHEDULE, needsReschedule);
        return intent;
    }

    CommandHandler(@NonNull Context context) {
        this.mContext = context;
    }

    public void onExecuted(@NonNull String workSpecId, boolean needsReschedule) {
        synchronized (this.mLock) {
            ExecutionListener listener = this.mPendingDelayMet.remove(workSpecId);
            if (listener != null) {
                listener.onExecuted(workSpecId, needsReschedule);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean hasPendingCommands() {
        boolean z;
        synchronized (this.mLock) {
            z = !this.mPendingDelayMet.isEmpty();
        }
        return z;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public void onHandleIntent(@NonNull Intent intent, int startId, @NonNull SystemAlarmDispatcher dispatcher) {
        String action = intent.getAction();
        if (ACTION_CONSTRAINTS_CHANGED.equals(action)) {
            handleConstraintsChanged(intent, startId, dispatcher);
        } else if (ACTION_RESCHEDULE.equals(action)) {
            handleReschedule(intent, startId, dispatcher);
        } else if (!hasKeys(intent.getExtras(), KEY_WORKSPEC_ID)) {
            Logger.get().error(TAG, String.format("Invalid request for %s, requires %s.", new Object[]{action, KEY_WORKSPEC_ID}), new Throwable[0]);
        } else if (ACTION_SCHEDULE_WORK.equals(action)) {
            handleScheduleWorkIntent(intent, startId, dispatcher);
        } else if (ACTION_DELAY_MET.equals(action)) {
            handleDelayMet(intent, startId, dispatcher);
        } else if (ACTION_STOP_WORK.equals(action)) {
            handleStopWork(intent, dispatcher);
        } else if (ACTION_EXECUTION_COMPLETED.equals(action)) {
            handleExecutionCompleted(intent, startId);
        } else {
            Logger.get().warning(TAG, String.format("Ignoring intent %s", new Object[]{intent}), new Throwable[0]);
        }
    }

    private void handleScheduleWorkIntent(@NonNull Intent intent, int startId, @NonNull SystemAlarmDispatcher dispatcher) {
        SystemAlarmDispatcher systemAlarmDispatcher = dispatcher;
        String workSpecId = intent.getExtras().getString(KEY_WORKSPEC_ID);
        Logger logger = Logger.get();
        String str = TAG;
        logger.debug(str, String.format("Handling schedule work for %s", new Object[]{workSpecId}), new Throwable[0]);
        WorkDatabase workDatabase = dispatcher.getWorkManager().getWorkDatabase();
        workDatabase.beginTransaction();
        try {
            WorkSpec workSpec = workDatabase.workSpecDao().getWorkSpec(workSpecId);
            if (workSpec == null) {
                Logger logger2 = Logger.get();
                logger2.warning(str, "Skipping scheduling " + workSpecId + " because it's no longer in the DB", new Throwable[0]);
                workDatabase.endTransaction();
            } else if (workSpec.state.isFinished()) {
                Logger logger3 = Logger.get();
                logger3.warning(str, "Skipping scheduling " + workSpecId + "because it is finished.", new Throwable[0]);
                workDatabase.endTransaction();
            } else {
                long triggerAt = workSpec.calculateNextRunTime();
                if (!workSpec.hasConstraints()) {
                    Logger.get().debug(str, String.format("Setting up Alarms for %s at %s", new Object[]{workSpecId, Long.valueOf(triggerAt)}), new Throwable[0]);
                    Alarms.setAlarm(this.mContext, dispatcher.getWorkManager(), workSpecId, triggerAt);
                    int i = startId;
                } else {
                    Logger.get().debug(str, String.format("Opportunistically setting an alarm for %s at %s", new Object[]{workSpecId, Long.valueOf(triggerAt)}), new Throwable[0]);
                    Alarms.setAlarm(this.mContext, dispatcher.getWorkManager(), workSpecId, triggerAt);
                    try {
                        systemAlarmDispatcher.postOnMainThread(new SystemAlarmDispatcher.AddRunnable(systemAlarmDispatcher, createConstraintsChangedIntent(this.mContext), startId));
                    } catch (Throwable th) {
                        th = th;
                        workDatabase.endTransaction();
                        throw th;
                    }
                }
                workDatabase.setTransactionSuccessful();
                workDatabase.endTransaction();
            }
        } catch (Throwable th2) {
            th = th2;
            int i2 = startId;
            workDatabase.endTransaction();
            throw th;
        }
    }

    private void handleDelayMet(@NonNull Intent intent, int startId, @NonNull SystemAlarmDispatcher dispatcher) {
        Bundle extras = intent.getExtras();
        synchronized (this.mLock) {
            String workSpecId = extras.getString(KEY_WORKSPEC_ID);
            Logger logger = Logger.get();
            String str = TAG;
            logger.debug(str, String.format("Handing delay met for %s", new Object[]{workSpecId}), new Throwable[0]);
            if (!this.mPendingDelayMet.containsKey(workSpecId)) {
                DelayMetCommandHandler delayMetCommandHandler = new DelayMetCommandHandler(this.mContext, startId, workSpecId, dispatcher);
                this.mPendingDelayMet.put(workSpecId, delayMetCommandHandler);
                delayMetCommandHandler.handleProcessWork();
            } else {
                Logger.get().debug(str, String.format("WorkSpec %s is already being handled for ACTION_DELAY_MET", new Object[]{workSpecId}), new Throwable[0]);
            }
        }
    }

    private void handleStopWork(@NonNull Intent intent, @NonNull SystemAlarmDispatcher dispatcher) {
        String workSpecId = intent.getExtras().getString(KEY_WORKSPEC_ID);
        Logger.get().debug(TAG, String.format("Handing stopWork work for %s", new Object[]{workSpecId}), new Throwable[0]);
        dispatcher.getWorkManager().stopWork(workSpecId);
        Alarms.cancelAlarm(this.mContext, dispatcher.getWorkManager(), workSpecId);
        dispatcher.onExecuted(workSpecId, false);
    }

    private void handleConstraintsChanged(@NonNull Intent intent, int startId, @NonNull SystemAlarmDispatcher dispatcher) {
        Logger.get().debug(TAG, String.format("Handling constraints changed %s", new Object[]{intent}), new Throwable[0]);
        new ConstraintsCommandHandler(this.mContext, startId, dispatcher).handleConstraintsChanged();
    }

    private void handleReschedule(@NonNull Intent intent, int startId, @NonNull SystemAlarmDispatcher dispatcher) {
        Logger.get().debug(TAG, String.format("Handling reschedule %s, %s", new Object[]{intent, Integer.valueOf(startId)}), new Throwable[0]);
        dispatcher.getWorkManager().rescheduleEligibleWork();
    }

    private void handleExecutionCompleted(@NonNull Intent intent, int startId) {
        Bundle extras = intent.getExtras();
        String workSpecId = extras.getString(KEY_WORKSPEC_ID);
        boolean needsReschedule = extras.getBoolean(KEY_NEEDS_RESCHEDULE);
        Logger.get().debug(TAG, String.format("Handling onExecutionCompleted %s, %s", new Object[]{intent, Integer.valueOf(startId)}), new Throwable[0]);
        onExecuted(workSpecId, needsReschedule);
    }

    private static boolean hasKeys(@Nullable Bundle bundle, @NonNull String... keys) {
        if (bundle == null || bundle.isEmpty()) {
            return false;
        }
        for (String key : keys) {
            if (bundle.get(key) == null) {
                return false;
            }
        }
        return true;
    }
}
