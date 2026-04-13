package androidx.work.impl.foreground;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.ForegroundInfo;
import androidx.work.Logger;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.constraints.WorkConstraintsCallback;
import androidx.work.impl.constraints.WorkConstraintsTracker;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class SystemForegroundDispatcher implements WorkConstraintsCallback, ExecutionListener {
    private static final String ACTION_CANCEL_WORK = "ACTION_CANCEL_WORK";
    private static final String ACTION_NOTIFY = "ACTION_NOTIFY";
    private static final String ACTION_START_FOREGROUND = "ACTION_START_FOREGROUND";
    private static final String ACTION_STOP_FOREGROUND = "ACTION_STOP_FOREGROUND";
    private static final String KEY_FOREGROUND_SERVICE_TYPE = "KEY_FOREGROUND_SERVICE_TYPE";
    private static final String KEY_NOTIFICATION = "KEY_NOTIFICATION";
    private static final String KEY_NOTIFICATION_ID = "KEY_NOTIFICATION_ID";
    private static final String KEY_WORKSPEC_ID = "KEY_WORKSPEC_ID";
    static final String TAG = Logger.tagWithPrefix("SystemFgDispatcher");
    @Nullable
    private Callback mCallback;
    final WorkConstraintsTracker mConstraintsTracker;
    private Context mContext;
    String mCurrentForegroundWorkSpecId;
    final Map<String, ForegroundInfo> mForegroundInfoById;
    final Object mLock = new Object();
    private final TaskExecutor mTaskExecutor;
    final Set<WorkSpec> mTrackedWorkSpecs;
    private WorkManagerImpl mWorkManagerImpl;
    final Map<String, WorkSpec> mWorkSpecById;

    public interface Callback {
        void cancelNotification(int i);

        void notify(int i, @NonNull Notification notification);

        void startForeground(int i, int i2, @NonNull Notification notification);

        void stop();
    }

    SystemForegroundDispatcher(@NonNull Context context) {
        this.mContext = context;
        WorkManagerImpl instance = WorkManagerImpl.getInstance(this.mContext);
        this.mWorkManagerImpl = instance;
        TaskExecutor workTaskExecutor = instance.getWorkTaskExecutor();
        this.mTaskExecutor = workTaskExecutor;
        this.mCurrentForegroundWorkSpecId = null;
        this.mForegroundInfoById = new LinkedHashMap();
        this.mTrackedWorkSpecs = new HashSet();
        this.mWorkSpecById = new HashMap();
        this.mConstraintsTracker = new WorkConstraintsTracker(this.mContext, workTaskExecutor, this);
        this.mWorkManagerImpl.getProcessor().addExecutionListener(this);
    }

    @VisibleForTesting
    SystemForegroundDispatcher(@NonNull Context context, @NonNull WorkManagerImpl workManagerImpl, @NonNull WorkConstraintsTracker tracker) {
        this.mContext = context;
        this.mWorkManagerImpl = workManagerImpl;
        this.mTaskExecutor = workManagerImpl.getWorkTaskExecutor();
        this.mCurrentForegroundWorkSpecId = null;
        this.mForegroundInfoById = new LinkedHashMap();
        this.mTrackedWorkSpecs = new HashSet();
        this.mWorkSpecById = new HashMap();
        this.mConstraintsTracker = tracker;
        this.mWorkManagerImpl.getProcessor().addExecutionListener(this);
    }

    @MainThread
    public void onExecuted(@NonNull String workSpecId, boolean needsReschedule) {
        Map.Entry<String, ForegroundInfo> entry;
        boolean removed = false;
        synchronized (this.mLock) {
            WorkSpec workSpec = this.mWorkSpecById.remove(workSpecId);
            if (workSpec != null) {
                removed = this.mTrackedWorkSpecs.remove(workSpec);
            }
            if (removed) {
                this.mConstraintsTracker.replace(this.mTrackedWorkSpecs);
            }
        }
        ForegroundInfo removedInfo = this.mForegroundInfoById.remove(workSpecId);
        if (workSpecId.equals(this.mCurrentForegroundWorkSpecId) && this.mForegroundInfoById.size() > 0) {
            Iterator<Map.Entry<String, ForegroundInfo>> iterator = this.mForegroundInfoById.entrySet().iterator();
            Map.Entry<String, ForegroundInfo> entry2 = iterator.next();
            while (true) {
                entry = entry2;
                if (!iterator.hasNext()) {
                    break;
                }
                entry2 = iterator.next();
            }
            this.mCurrentForegroundWorkSpecId = entry.getKey();
            if (this.mCallback != null) {
                ForegroundInfo info = entry.getValue();
                this.mCallback.startForeground(info.getNotificationId(), info.getForegroundServiceType(), info.getNotification());
                this.mCallback.cancelNotification(info.getNotificationId());
            }
        }
        Callback callback = this.mCallback;
        if (removedInfo != null && callback != null) {
            Logger.get().debug(TAG, String.format("Removing Notification (id: %s, workSpecId: %s ,notificationType: %s)", new Object[]{Integer.valueOf(removedInfo.getNotificationId()), workSpecId, Integer.valueOf(removedInfo.getForegroundServiceType())}), new Throwable[0]);
            callback.cancelNotification(removedInfo.getNotificationId());
        }
    }

    /* access modifiers changed from: package-private */
    @MainThread
    public void setCallback(@NonNull Callback callback) {
        if (this.mCallback != null) {
            Logger.get().error(TAG, "A callback already exists.", new Throwable[0]);
        } else {
            this.mCallback = callback;
        }
    }

    /* access modifiers changed from: package-private */
    public WorkManagerImpl getWorkManager() {
        return this.mWorkManagerImpl;
    }

    /* access modifiers changed from: package-private */
    public void onStartCommand(@NonNull Intent intent) {
        String action = intent.getAction();
        if (ACTION_START_FOREGROUND.equals(action)) {
            handleStartForeground(intent);
            handleNotify(intent);
        } else if (ACTION_NOTIFY.equals(action)) {
            handleNotify(intent);
        } else if (ACTION_CANCEL_WORK.equals(action)) {
            handleCancelWork(intent);
        } else if (ACTION_STOP_FOREGROUND.equals(action)) {
            handleStop(intent);
        }
    }

    /* access modifiers changed from: package-private */
    @MainThread
    public void onDestroy() {
        this.mCallback = null;
        synchronized (this.mLock) {
            this.mConstraintsTracker.reset();
        }
        this.mWorkManagerImpl.getProcessor().removeExecutionListener(this);
    }

    @MainThread
    private void handleStartForeground(@NonNull Intent intent) {
        Logger.get().info(TAG, String.format("Started foreground service %s", new Object[]{intent}), new Throwable[0]);
        final String workSpecId = intent.getStringExtra(KEY_WORKSPEC_ID);
        final WorkDatabase database = this.mWorkManagerImpl.getWorkDatabase();
        this.mTaskExecutor.executeOnBackgroundThread(new Runnable() {
            public void run() {
                WorkSpec workSpec = database.workSpecDao().getWorkSpec(workSpecId);
                if (workSpec != null && workSpec.hasConstraints()) {
                    synchronized (SystemForegroundDispatcher.this.mLock) {
                        SystemForegroundDispatcher.this.mWorkSpecById.put(workSpecId, workSpec);
                        SystemForegroundDispatcher.this.mTrackedWorkSpecs.add(workSpec);
                        SystemForegroundDispatcher systemForegroundDispatcher = SystemForegroundDispatcher.this;
                        systemForegroundDispatcher.mConstraintsTracker.replace(systemForegroundDispatcher.mTrackedWorkSpecs);
                    }
                }
            }
        });
    }

    @MainThread
    private void handleNotify(@NonNull Intent intent) {
        int notificationId = intent.getIntExtra(KEY_NOTIFICATION_ID, 0);
        int notificationType = intent.getIntExtra(KEY_FOREGROUND_SERVICE_TYPE, 0);
        String workSpecId = intent.getStringExtra(KEY_WORKSPEC_ID);
        Notification notification = (Notification) intent.getParcelableExtra(KEY_NOTIFICATION);
        Logger.get().debug(TAG, String.format("Notifying with (id: %s, workSpecId: %s, notificationType: %s)", new Object[]{Integer.valueOf(notificationId), workSpecId, Integer.valueOf(notificationType)}), new Throwable[0]);
        if (notification != null && this.mCallback != null) {
            this.mForegroundInfoById.put(workSpecId, new ForegroundInfo(notificationId, notification, notificationType));
            if (TextUtils.isEmpty(this.mCurrentForegroundWorkSpecId)) {
                this.mCurrentForegroundWorkSpecId = workSpecId;
                this.mCallback.startForeground(notificationId, notificationType, notification);
                return;
            }
            this.mCallback.notify(notificationId, notification);
            if (notificationType != 0 && Build.VERSION.SDK_INT >= 29) {
                int foregroundServiceType = 0;
                for (Map.Entry<String, ForegroundInfo> entry : this.mForegroundInfoById.entrySet()) {
                    foregroundServiceType |= entry.getValue().getForegroundServiceType();
                }
                ForegroundInfo currentInfo = this.mForegroundInfoById.get(this.mCurrentForegroundWorkSpecId);
                if (currentInfo != null) {
                    this.mCallback.startForeground(currentInfo.getNotificationId(), foregroundServiceType, currentInfo.getNotification());
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    @MainThread
    public void handleStop(@NonNull Intent intent) {
        Logger.get().info(TAG, "Stopping foreground service", new Throwable[0]);
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.stop();
        }
    }

    @MainThread
    private void handleCancelWork(@NonNull Intent intent) {
        Logger.get().info(TAG, String.format("Stopping foreground work for %s", new Object[]{intent}), new Throwable[0]);
        String workSpecId = intent.getStringExtra(KEY_WORKSPEC_ID);
        if (workSpecId != null && !TextUtils.isEmpty(workSpecId)) {
            this.mWorkManagerImpl.cancelWorkById(UUID.fromString(workSpecId));
        }
    }

    public void onAllConstraintsMet(@NonNull List<String> list) {
    }

    public void onAllConstraintsNotMet(@NonNull List<String> workSpecIds) {
        if (!workSpecIds.isEmpty()) {
            for (String workSpecId : workSpecIds) {
                Logger.get().debug(TAG, String.format("Constraints unmet for WorkSpec %s", new Object[]{workSpecId}), new Throwable[0]);
                this.mWorkManagerImpl.stopForegroundWork(workSpecId);
            }
        }
    }

    @NonNull
    public static Intent createStartForegroundIntent(@NonNull Context context, @NonNull String workSpecId, @NonNull ForegroundInfo info) {
        Intent intent = new Intent(context, SystemForegroundService.class);
        intent.setAction(ACTION_START_FOREGROUND);
        intent.putExtra(KEY_WORKSPEC_ID, workSpecId);
        intent.putExtra(KEY_NOTIFICATION_ID, info.getNotificationId());
        intent.putExtra(KEY_FOREGROUND_SERVICE_TYPE, info.getForegroundServiceType());
        intent.putExtra(KEY_NOTIFICATION, info.getNotification());
        intent.putExtra(KEY_WORKSPEC_ID, workSpecId);
        return intent;
    }

    @NonNull
    public static Intent createCancelWorkIntent(@NonNull Context context, @NonNull String workSpecId) {
        Intent intent = new Intent(context, SystemForegroundService.class);
        intent.setAction(ACTION_CANCEL_WORK);
        intent.setData(Uri.parse(String.format("workspec://%s", new Object[]{workSpecId})));
        intent.putExtra(KEY_WORKSPEC_ID, workSpecId);
        return intent;
    }

    @NonNull
    public static Intent createNotifyIntent(@NonNull Context context, @NonNull String workSpecId, @NonNull ForegroundInfo info) {
        Intent intent = new Intent(context, SystemForegroundService.class);
        intent.setAction(ACTION_NOTIFY);
        intent.putExtra(KEY_NOTIFICATION_ID, info.getNotificationId());
        intent.putExtra(KEY_FOREGROUND_SERVICE_TYPE, info.getForegroundServiceType());
        intent.putExtra(KEY_NOTIFICATION, info.getNotification());
        intent.putExtra(KEY_WORKSPEC_ID, workSpecId);
        return intent;
    }

    @NonNull
    public static Intent createStopForegroundIntent(@NonNull Context context) {
        Intent intent = new Intent(context, SystemForegroundService.class);
        intent.setAction(ACTION_STOP_FOREGROUND);
        return intent;
    }
}
