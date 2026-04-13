package com.google.firebase.messaging;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.messaging.WithinAppServiceBinder;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

@SuppressLint({"UnwrappedWakefulBroadcastReceiver"})
public abstract class EnhancedIntentService extends Service {
    private static final String TAG = "EnhancedIntentService";
    private Binder binder;
    @VisibleForTesting
    final ExecutorService executor = FcmExecutors.newIntentHandleExecutor();
    private int lastStartId;
    private final Object lock = new Object();
    private int runningTasks = 0;

    public abstract void handleIntent(Intent intent);

    public final synchronized IBinder onBind(Intent intent) {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Service received bind request");
        }
        if (this.binder == null) {
            this.binder = new WithinAppServiceBinder(new WithinAppServiceBinder.IntentHandler() {
                @KeepForSdk
                public Task<Void> handle(Intent intent) {
                    return EnhancedIntentService.this.processIntent(intent);
                }
            });
        }
        return this.binder;
    }

    /* access modifiers changed from: private */
    @MainThread
    public Task<Void> processIntent(Intent intent) {
        if (handleIntentOnMainThread(intent)) {
            return Tasks.forResult(null);
        }
        TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<>();
        this.executor.execute(new c(this, intent, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$processIntent$0 */
    public /* synthetic */ void b(Intent intent, TaskCompletionSource taskCompletionSource) {
        try {
            handleIntent(intent);
        } finally {
            taskCompletionSource.setResult(null);
        }
    }

    public final int onStartCommand(Intent originalIntent, int i, int startId) {
        PushAutoTrackHelper.onServiceStartCommand(this, originalIntent, i, startId);
        synchronized (this.lock) {
            this.lastStartId = startId;
            this.runningTasks++;
        }
        Intent intent = getStartCommandIntent(originalIntent);
        if (intent == null) {
            finishTask(originalIntent);
            return 2;
        }
        Task<Void> task = processIntent(intent);
        if (task.isComplete()) {
            finishTask(originalIntent);
            return 2;
        }
        task.addOnCompleteListener((Executor) x.c, (OnCompleteListener<Void>) new b(this, originalIntent));
        return 3;
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$onStartCommand$1 */
    public /* synthetic */ void a(Intent originalIntent, Task unusedTask) {
        finishTask(originalIntent);
    }

    @CallSuper
    public void onDestroy() {
        this.executor.shutdown();
        super.onDestroy();
    }

    private void finishTask(Intent originalIntent) {
        if (originalIntent != null) {
            WakeLockHolder.completeWakefulIntent(originalIntent);
        }
        synchronized (this.lock) {
            int i = this.runningTasks - 1;
            this.runningTasks = i;
            if (i == 0) {
                stopSelfResultHook(this.lastStartId);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean stopSelfResultHook(int startId) {
        return stopSelfResult(startId);
    }

    /* access modifiers changed from: protected */
    public Intent getStartCommandIntent(Intent originalIntent) {
        return originalIntent;
    }

    public boolean handleIntentOnMainThread(Intent intent) {
        return false;
    }
}
