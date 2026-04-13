package androidx.work.impl.background.systemalarm;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.text.TextUtils;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.Logger;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.Processor;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.utils.SerialExecutor;
import androidx.work.impl.utils.WakeLocks;
import androidx.work.impl.utils.WorkTimer;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import java.util.ArrayList;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class SystemAlarmDispatcher implements ExecutionListener {
    private static final int DEFAULT_START_ID = 0;
    private static final String KEY_START_ID = "KEY_START_ID";
    private static final String PROCESS_COMMAND_TAG = "ProcessCommand";
    static final String TAG = Logger.tagWithPrefix("SystemAlarmDispatcher");
    final CommandHandler mCommandHandler;
    @Nullable
    private CommandsCompletedListener mCompletedListener;
    final Context mContext;
    Intent mCurrentIntent;
    final List<Intent> mIntents;
    private final Handler mMainHandler;
    private final Processor mProcessor;
    private final TaskExecutor mTaskExecutor;
    private final WorkManagerImpl mWorkManager;
    private final WorkTimer mWorkTimer;

    public interface CommandsCompletedListener {
        void onAllCommandsCompleted();
    }

    SystemAlarmDispatcher(@NonNull Context context) {
        this(context, (Processor) null, (WorkManagerImpl) null);
    }

    @VisibleForTesting
    SystemAlarmDispatcher(@NonNull Context context, @Nullable Processor processor, @Nullable WorkManagerImpl workManager) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mCommandHandler = new CommandHandler(applicationContext);
        this.mWorkTimer = new WorkTimer();
        WorkManagerImpl instance = workManager != null ? workManager : WorkManagerImpl.getInstance(context);
        this.mWorkManager = instance;
        Processor processor2 = processor != null ? processor : instance.getProcessor();
        this.mProcessor = processor2;
        this.mTaskExecutor = instance.getWorkTaskExecutor();
        processor2.addExecutionListener(this);
        this.mIntents = new ArrayList();
        this.mCurrentIntent = null;
        this.mMainHandler = new Handler(Looper.getMainLooper());
    }

    /* access modifiers changed from: package-private */
    public void onDestroy() {
        Logger.get().debug(TAG, "Destroying SystemAlarmDispatcher", new Throwable[0]);
        this.mProcessor.removeExecutionListener(this);
        this.mWorkTimer.onDestroy();
        this.mCompletedListener = null;
    }

    public void onExecuted(@NonNull String workSpecId, boolean needsReschedule) {
        postOnMainThread(new AddRunnable(this, CommandHandler.createExecutionCompletedIntent(this.mContext, workSpecId, needsReschedule), 0));
    }

    @MainThread
    public boolean add(@NonNull Intent intent, int startId) {
        Logger logger = Logger.get();
        String str = TAG;
        boolean z = false;
        logger.debug(str, String.format("Adding command %s (%s)", new Object[]{intent, Integer.valueOf(startId)}), new Throwable[0]);
        assertMainThread();
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            Logger.get().warning(str, "Unknown command. Ignoring", new Throwable[0]);
            return false;
        } else if ("ACTION_CONSTRAINTS_CHANGED".equals(action) && hasIntentWithAction("ACTION_CONSTRAINTS_CHANGED")) {
            return false;
        } else {
            intent.putExtra(KEY_START_ID, startId);
            synchronized (this.mIntents) {
                if (!this.mIntents.isEmpty()) {
                    z = true;
                }
                boolean hasCommands = z;
                this.mIntents.add(intent);
                if (!hasCommands) {
                    processCommand();
                }
            }
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public void setCompletedListener(@NonNull CommandsCompletedListener listener) {
        if (this.mCompletedListener != null) {
            Logger.get().error(TAG, "A completion listener for SystemAlarmDispatcher already exists.", new Throwable[0]);
        } else {
            this.mCompletedListener = listener;
        }
    }

    /* access modifiers changed from: package-private */
    public Processor getProcessor() {
        return this.mProcessor;
    }

    /* access modifiers changed from: package-private */
    public WorkTimer getWorkTimer() {
        return this.mWorkTimer;
    }

    /* access modifiers changed from: package-private */
    public WorkManagerImpl getWorkManager() {
        return this.mWorkManager;
    }

    /* access modifiers changed from: package-private */
    public TaskExecutor getTaskExecutor() {
        return this.mTaskExecutor;
    }

    /* access modifiers changed from: package-private */
    public void postOnMainThread(@NonNull Runnable runnable) {
        this.mMainHandler.post(runnable);
    }

    /* access modifiers changed from: package-private */
    @MainThread
    public void dequeueAndCheckForCompletion() {
        Logger logger = Logger.get();
        String str = TAG;
        logger.debug(str, "Checking if commands are complete.", new Throwable[0]);
        assertMainThread();
        synchronized (this.mIntents) {
            if (this.mCurrentIntent != null) {
                Logger.get().debug(str, String.format("Removing command %s", new Object[]{this.mCurrentIntent}), new Throwable[0]);
                if (this.mIntents.remove(0).equals(this.mCurrentIntent)) {
                    this.mCurrentIntent = null;
                } else {
                    throw new IllegalStateException("Dequeue-d command is not the first.");
                }
            }
            SerialExecutor serialExecutor = this.mTaskExecutor.getBackgroundExecutor();
            if (!this.mCommandHandler.hasPendingCommands() && this.mIntents.isEmpty() && !serialExecutor.hasPendingTasks()) {
                Logger.get().debug(str, "No more commands & intents.", new Throwable[0]);
                CommandsCompletedListener commandsCompletedListener = this.mCompletedListener;
                if (commandsCompletedListener != null) {
                    commandsCompletedListener.onAllCommandsCompleted();
                }
            } else if (!this.mIntents.isEmpty()) {
                processCommand();
            }
        }
    }

    @MainThread
    private void processCommand() {
        assertMainThread();
        PowerManager.WakeLock processCommandLock = WakeLocks.newWakeLock(this.mContext, PROCESS_COMMAND_TAG);
        try {
            processCommandLock.acquire();
            this.mWorkManager.getWorkTaskExecutor().executeOnBackgroundThread(new Runnable() {
                public void run() {
                    DequeueAndCheckForCompletion dequeueAndCheckForCompletion;
                    SystemAlarmDispatcher systemAlarmDispatcher;
                    synchronized (SystemAlarmDispatcher.this.mIntents) {
                        SystemAlarmDispatcher systemAlarmDispatcher2 = SystemAlarmDispatcher.this;
                        systemAlarmDispatcher2.mCurrentIntent = systemAlarmDispatcher2.mIntents.get(0);
                    }
                    Intent intent = SystemAlarmDispatcher.this.mCurrentIntent;
                    if (intent != null) {
                        String action = intent.getAction();
                        int startId = SystemAlarmDispatcher.this.mCurrentIntent.getIntExtra(SystemAlarmDispatcher.KEY_START_ID, 0);
                        Logger logger = Logger.get();
                        String str = SystemAlarmDispatcher.TAG;
                        logger.debug(str, String.format("Processing command %s, %s", new Object[]{SystemAlarmDispatcher.this.mCurrentIntent, Integer.valueOf(startId)}), new Throwable[0]);
                        PowerManager.WakeLock wakeLock = WakeLocks.newWakeLock(SystemAlarmDispatcher.this.mContext, String.format("%s (%s)", new Object[]{action, Integer.valueOf(startId)}));
                        try {
                            Logger.get().debug(str, String.format("Acquiring operation wake lock (%s) %s", new Object[]{action, wakeLock}), new Throwable[0]);
                            wakeLock.acquire();
                            SystemAlarmDispatcher systemAlarmDispatcher3 = SystemAlarmDispatcher.this;
                            systemAlarmDispatcher3.mCommandHandler.onHandleIntent(systemAlarmDispatcher3.mCurrentIntent, startId, systemAlarmDispatcher3);
                            Logger.get().debug(str, String.format("Releasing operation wake lock (%s) %s", new Object[]{action, wakeLock}), new Throwable[0]);
                            wakeLock.release();
                            systemAlarmDispatcher = SystemAlarmDispatcher.this;
                            dequeueAndCheckForCompletion = new DequeueAndCheckForCompletion(systemAlarmDispatcher);
                        } catch (Throwable th) {
                            Logger.get().debug(SystemAlarmDispatcher.TAG, String.format("Releasing operation wake lock (%s) %s", new Object[]{action, wakeLock}), new Throwable[0]);
                            wakeLock.release();
                            SystemAlarmDispatcher systemAlarmDispatcher4 = SystemAlarmDispatcher.this;
                            systemAlarmDispatcher4.postOnMainThread(new DequeueAndCheckForCompletion(systemAlarmDispatcher4));
                            throw th;
                        }
                        systemAlarmDispatcher.postOnMainThread(dequeueAndCheckForCompletion);
                    }
                }
            });
        } finally {
            processCommandLock.release();
        }
    }

    @MainThread
    private boolean hasIntentWithAction(@NonNull String action) {
        assertMainThread();
        synchronized (this.mIntents) {
            for (Intent intent : this.mIntents) {
                if (action.equals(intent.getAction())) {
                    return true;
                }
            }
            return false;
        }
    }

    private void assertMainThread() {
        if (this.mMainHandler.getLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException("Needs to be invoked on the main thread.");
        }
    }

    public static class DequeueAndCheckForCompletion implements Runnable {
        private final SystemAlarmDispatcher mDispatcher;

        DequeueAndCheckForCompletion(@NonNull SystemAlarmDispatcher dispatcher) {
            this.mDispatcher = dispatcher;
        }

        public void run() {
            this.mDispatcher.dequeueAndCheckForCompletion();
        }
    }

    public static class AddRunnable implements Runnable {
        private final SystemAlarmDispatcher mDispatcher;
        private final Intent mIntent;
        private final int mStartId;

        AddRunnable(@NonNull SystemAlarmDispatcher dispatcher, @NonNull Intent intent, int startId) {
            this.mDispatcher = dispatcher;
            this.mIntent = intent;
            this.mStartId = startId;
        }

        public void run() {
            this.mDispatcher.add(this.mIntent, this.mStartId);
        }
    }
}
