package com.google.firebase.messaging;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.concurrent.NamedThreadFactory;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.sqlcipher.database.SQLiteDatabase;

public class WithinAppServiceConnection implements ServiceConnection {
    private static final int REQUEST_TIMEOUT_MS = 9000;
    @Nullable
    private WithinAppServiceBinder binder;
    @GuardedBy("this")
    private boolean connectionInProgress;
    private final Intent connectionIntent;
    private final Context context;
    private final Queue<BindRequest> intentQueue;
    private final ScheduledExecutorService scheduledExecutorService;

    public static class BindRequest {
        final Intent intent;
        private final TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<>();

        BindRequest(Intent intent2) {
            this.intent = intent2;
        }

        /* access modifiers changed from: package-private */
        public void arrangeTimeout(ScheduledExecutorService executor) {
            getTask().addOnCompleteListener((Executor) executor, new f0(executor.schedule(new e0(this), (this.intent.getFlags() & SQLiteDatabase.CREATE_IF_NECESSARY) != 0 ? WakeLockHolder.WAKE_LOCK_ACQUIRE_TIMEOUT_MILLIS : 9000, TimeUnit.MILLISECONDS)));
        }

        /* access modifiers changed from: private */
        /* renamed from: lambda$arrangeTimeout$0 */
        public /* synthetic */ void a() {
            Log.w(Constants.TAG, "Service took too long to process intent: " + this.intent.getAction() + " Releasing WakeLock.");
            finish();
        }

        /* access modifiers changed from: package-private */
        public Task<Void> getTask() {
            return this.taskCompletionSource.getTask();
        }

        /* access modifiers changed from: package-private */
        public void finish() {
            this.taskCompletionSource.trySetResult(null);
        }
    }

    @SuppressLint({"ThreadPoolCreation"})
    WithinAppServiceConnection(Context context2, String action) {
        this(context2, action, new ScheduledThreadPoolExecutor(0, new NamedThreadFactory("Firebase-FirebaseInstanceIdServiceConnection")));
    }

    @VisibleForTesting
    WithinAppServiceConnection(Context context2, String action, ScheduledExecutorService scheduledExecutorService2) {
        this.intentQueue = new ArrayDeque();
        this.connectionInProgress = false;
        Context applicationContext = context2.getApplicationContext();
        this.context = applicationContext;
        this.connectionIntent = new Intent(action).setPackage(applicationContext.getPackageName());
        this.scheduledExecutorService = scheduledExecutorService2;
    }

    /* access modifiers changed from: package-private */
    public synchronized Task<Void> sendIntent(Intent intent) {
        BindRequest req;
        if (Log.isLoggable(Constants.TAG, 3)) {
            Log.d(Constants.TAG, "new intent queued in the bind-strategy delivery");
        }
        req = new BindRequest(intent);
        req.arrangeTimeout(this.scheduledExecutorService);
        this.intentQueue.add(req);
        flushQueue();
        return req.getTask();
    }

    private synchronized void flushQueue() {
        if (Log.isLoggable(Constants.TAG, 3)) {
            Log.d(Constants.TAG, "flush queue called");
        }
        while (!this.intentQueue.isEmpty()) {
            if (Log.isLoggable(Constants.TAG, 3)) {
                Log.d(Constants.TAG, "found intent to be delivered");
            }
            WithinAppServiceBinder withinAppServiceBinder = this.binder;
            if (withinAppServiceBinder == null || !withinAppServiceBinder.isBinderAlive()) {
                startConnectionIfNeeded();
                return;
            }
            if (Log.isLoggable(Constants.TAG, 3)) {
                Log.d(Constants.TAG, "binder is alive, sending the intent.");
            }
            this.binder.send(this.intentQueue.poll());
        }
    }

    @GuardedBy("this")
    private void startConnectionIfNeeded() {
        if (Log.isLoggable(Constants.TAG, 3)) {
            StringBuilder sb = new StringBuilder();
            sb.append("binder is dead. start connection? ");
            sb.append(!this.connectionInProgress);
            Log.d(Constants.TAG, sb.toString());
        }
        if (!this.connectionInProgress) {
            this.connectionInProgress = true;
            try {
                if (!ConnectionTracker.getInstance().bindService(this.context, this.connectionIntent, this, 65)) {
                    Log.e(Constants.TAG, "binding to the service failed");
                    this.connectionInProgress = false;
                    finishAllInQueue();
                }
            } catch (SecurityException e) {
                Log.e(Constants.TAG, "Exception while binding the service", e);
            }
        }
    }

    @GuardedBy("this")
    private void finishAllInQueue() {
        while (!this.intentQueue.isEmpty()) {
            this.intentQueue.poll().finish();
        }
    }

    public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (Log.isLoggable(Constants.TAG, 3)) {
            Log.d(Constants.TAG, "onServiceConnected: " + componentName);
        }
        this.connectionInProgress = false;
        if (!(iBinder instanceof WithinAppServiceBinder)) {
            Log.e(Constants.TAG, "Invalid service connection: " + iBinder);
            finishAllInQueue();
            return;
        }
        this.binder = (WithinAppServiceBinder) iBinder;
        flushQueue();
    }

    public void onServiceDisconnected(ComponentName componentName) {
        if (Log.isLoggable(Constants.TAG, 3)) {
            Log.d(Constants.TAG, "onServiceDisconnected: " + componentName);
        }
        flushQueue();
    }
}
