package com.google.firebase.messaging;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TopicsSubscriber {
    static final String ERROR_INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    private static final long MAX_DELAY_SEC = TimeUnit.HOURS.toSeconds(8);
    private static final long MIN_DELAY_SEC = 30;
    private static final long RPC_TIMEOUT_SEC = 30;
    private final Context context;
    private final FirebaseMessaging firebaseMessaging;
    private final Metadata metadata;
    @GuardedBy("pendingOperations")
    private final Map<String, ArrayDeque<TaskCompletionSource<Void>>> pendingOperations = new ArrayMap();
    private final GmsRpc rpc;
    private final TopicsStore store;
    private final ScheduledExecutorService syncExecutor;
    @GuardedBy("this")
    private boolean syncScheduledOrRunning = false;

    @VisibleForTesting
    static Task<TopicsSubscriber> createInstance(FirebaseMessaging firebaseMessaging2, Metadata metadata2, GmsRpc rpc2, Context context2, @NonNull ScheduledExecutorService syncExecutor2) {
        return Tasks.call(syncExecutor2, new b0(context2, syncExecutor2, firebaseMessaging2, metadata2, rpc2));
    }

    static /* synthetic */ TopicsSubscriber lambda$createInstance$0(Context context2, ScheduledExecutorService syncExecutor2, FirebaseMessaging firebaseMessaging2, Metadata metadata2, GmsRpc rpc2) {
        return new TopicsSubscriber(firebaseMessaging2, metadata2, TopicsStore.getInstance(context2, syncExecutor2), rpc2, context2, syncExecutor2);
    }

    private TopicsSubscriber(FirebaseMessaging firebaseMessaging2, Metadata metadata2, TopicsStore store2, GmsRpc rpc2, Context context2, @NonNull ScheduledExecutorService syncExecutor2) {
        this.firebaseMessaging = firebaseMessaging2;
        this.metadata = metadata2;
        this.store = store2;
        this.rpc = rpc2;
        this.context = context2;
        this.syncExecutor = syncExecutor2;
    }

    /* access modifiers changed from: package-private */
    public Task<Void> subscribeToTopic(String topic) {
        Task<Void> task = scheduleTopicOperation(TopicOperation.subscribe(topic));
        startTopicsSyncIfNecessary();
        return task;
    }

    /* access modifiers changed from: package-private */
    public Task<Void> unsubscribeFromTopic(String topic) {
        Task<Void> task = scheduleTopicOperation(TopicOperation.unsubscribe(topic));
        startTopicsSyncIfNecessary();
        return task;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public Task<Void> scheduleTopicOperation(TopicOperation topicOperation) {
        this.store.addTopicOperation(topicOperation);
        TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<>();
        addToPendingOperations(topicOperation, taskCompletionSource);
        return taskCompletionSource.getTask();
    }

    private void addToPendingOperations(TopicOperation topicOperation, TaskCompletionSource<Void> taskCompletionSource) {
        ArrayDeque<TaskCompletionSource<Void>> list;
        synchronized (this.pendingOperations) {
            String key = topicOperation.serialize();
            if (this.pendingOperations.containsKey(key)) {
                list = this.pendingOperations.get(key);
            } else {
                list = new ArrayDeque<>();
                this.pendingOperations.put(key, list);
            }
            list.add(taskCompletionSource);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean hasPendingOperation() {
        return this.store.getNextTopicOperation() != null;
    }

    /* access modifiers changed from: package-private */
    public void startTopicsSyncIfNecessary() {
        if (hasPendingOperation()) {
            startSync();
        }
    }

    private void startSync() {
        if (!isSyncScheduledOrRunning()) {
            syncWithDelaySecondsInternal(0);
        }
    }

    /* access modifiers changed from: package-private */
    public void syncWithDelaySecondsInternal(long delaySeconds) {
        scheduleSyncTaskWithDelaySeconds(new TopicsSyncTask(this, this.context, this.metadata, Math.min(Math.max(30, 2 * delaySeconds), MAX_DELAY_SEC)), delaySeconds);
        setSyncScheduledOrRunning(true);
    }

    /* access modifiers changed from: package-private */
    public void scheduleSyncTaskWithDelaySeconds(Runnable task, long delaySeconds) {
        this.syncExecutor.schedule(task, delaySeconds, TimeUnit.SECONDS);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
        if (performTopicOperation(r0) != false) goto L_0x0023;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0021, code lost:
        return false;
     */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean syncTopics() {
        /*
            r3 = this;
        L_0x0000:
            monitor-enter(r3)
            com.google.firebase.messaging.TopicsStore r0 = r3.store     // Catch:{ all -> 0x002c }
            com.google.firebase.messaging.TopicOperation r0 = r0.getNextTopicOperation()     // Catch:{ all -> 0x002c }
            if (r0 != 0) goto L_0x001a
            boolean r1 = isDebugLogEnabled()     // Catch:{ all -> 0x002c }
            if (r1 == 0) goto L_0x0017
            java.lang.String r1 = "FirebaseMessaging"
            java.lang.String r2 = "topic sync succeeded"
            android.util.Log.d(r1, r2)     // Catch:{ all -> 0x002c }
        L_0x0017:
            r1 = 1
            monitor-exit(r3)     // Catch:{ all -> 0x002c }
            return r1
        L_0x001a:
            monitor-exit(r3)     // Catch:{ all -> 0x002c }
            boolean r1 = r3.performTopicOperation(r0)
            if (r1 != 0) goto L_0x0023
            r1 = 0
            return r1
        L_0x0023:
            com.google.firebase.messaging.TopicsStore r1 = r3.store
            r1.removeTopicOperation(r0)
            r3.markCompletePendingOperation(r0)
            goto L_0x0000
        L_0x002c:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x002c }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.TopicsSubscriber.syncTopics():boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0031, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void markCompletePendingOperation(com.google.firebase.messaging.TopicOperation r6) {
        /*
            r5 = this;
            java.util.Map<java.lang.String, java.util.ArrayDeque<com.google.android.gms.tasks.TaskCompletionSource<java.lang.Void>>> r0 = r5.pendingOperations
            monitor-enter(r0)
            java.lang.String r1 = r6.serialize()     // Catch:{ all -> 0x0032 }
            java.util.Map<java.lang.String, java.util.ArrayDeque<com.google.android.gms.tasks.TaskCompletionSource<java.lang.Void>>> r2 = r5.pendingOperations     // Catch:{ all -> 0x0032 }
            boolean r2 = r2.containsKey(r1)     // Catch:{ all -> 0x0032 }
            if (r2 != 0) goto L_0x0011
            monitor-exit(r0)     // Catch:{ all -> 0x0032 }
            return
        L_0x0011:
            java.util.Map<java.lang.String, java.util.ArrayDeque<com.google.android.gms.tasks.TaskCompletionSource<java.lang.Void>>> r2 = r5.pendingOperations     // Catch:{ all -> 0x0032 }
            java.lang.Object r2 = r2.get(r1)     // Catch:{ all -> 0x0032 }
            java.util.ArrayDeque r2 = (java.util.ArrayDeque) r2     // Catch:{ all -> 0x0032 }
            java.lang.Object r3 = r2.poll()     // Catch:{ all -> 0x0032 }
            com.google.android.gms.tasks.TaskCompletionSource r3 = (com.google.android.gms.tasks.TaskCompletionSource) r3     // Catch:{ all -> 0x0032 }
            if (r3 == 0) goto L_0x0025
            r4 = 0
            r3.setResult(r4)     // Catch:{ all -> 0x0032 }
        L_0x0025:
            boolean r4 = r2.isEmpty()     // Catch:{ all -> 0x0032 }
            if (r4 == 0) goto L_0x0030
            java.util.Map<java.lang.String, java.util.ArrayDeque<com.google.android.gms.tasks.TaskCompletionSource<java.lang.Void>>> r4 = r5.pendingOperations     // Catch:{ all -> 0x0032 }
            r4.remove(r1)     // Catch:{ all -> 0x0032 }
        L_0x0030:
            monitor-exit(r0)     // Catch:{ all -> 0x0032 }
            return
        L_0x0032:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0032 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.TopicsSubscriber.markCompletePendingOperation(com.google.firebase.messaging.TopicOperation):void");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean performTopicOperation(com.google.firebase.messaging.TopicOperation r7) {
        /*
            r6 = this;
            java.lang.String r0 = "FirebaseMessaging"
            r1 = 0
            java.lang.String r2 = r7.getOperation()     // Catch:{ IOException -> 0x009d }
            r3 = -1
            int r4 = r2.hashCode()     // Catch:{ IOException -> 0x009d }
            r5 = 1
            switch(r4) {
                case 83: goto L_0x001b;
                case 84: goto L_0x0010;
                case 85: goto L_0x0011;
                default: goto L_0x0010;
            }     // Catch:{ IOException -> 0x009d }
        L_0x0010:
            goto L_0x0024
        L_0x0011:
            java.lang.String r4 = "U"
            boolean r2 = r2.equals(r4)     // Catch:{ IOException -> 0x009d }
            if (r2 == 0) goto L_0x0010
            r3 = r5
            goto L_0x0024
        L_0x001b:
            java.lang.String r4 = "S"
            boolean r2 = r2.equals(r4)     // Catch:{ IOException -> 0x009d }
            if (r2 == 0) goto L_0x0010
            r3 = r1
        L_0x0024:
            java.lang.String r2 = " succeeded."
            switch(r3) {
                case 0: goto L_0x0057;
                case 1: goto L_0x002e;
                default: goto L_0x0029;
            }
        L_0x0029:
            boolean r2 = isDebugLogEnabled()     // Catch:{ IOException -> 0x009d }
            goto L_0x0080
        L_0x002e:
            java.lang.String r3 = r7.getTopic()     // Catch:{ IOException -> 0x009d }
            r6.blockingUnsubscribeFromTopic(r3)     // Catch:{ IOException -> 0x009d }
            boolean r3 = isDebugLogEnabled()     // Catch:{ IOException -> 0x009d }
            if (r3 == 0) goto L_0x009b
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x009d }
            r3.<init>()     // Catch:{ IOException -> 0x009d }
            java.lang.String r4 = "Unsubscribe from topic: "
            r3.append(r4)     // Catch:{ IOException -> 0x009d }
            java.lang.String r4 = r7.getTopic()     // Catch:{ IOException -> 0x009d }
            r3.append(r4)     // Catch:{ IOException -> 0x009d }
            r3.append(r2)     // Catch:{ IOException -> 0x009d }
            java.lang.String r2 = r3.toString()     // Catch:{ IOException -> 0x009d }
            android.util.Log.d(r0, r2)     // Catch:{ IOException -> 0x009d }
            goto L_0x009b
        L_0x0057:
            java.lang.String r3 = r7.getTopic()     // Catch:{ IOException -> 0x009d }
            r6.blockingSubscribeToTopic(r3)     // Catch:{ IOException -> 0x009d }
            boolean r3 = isDebugLogEnabled()     // Catch:{ IOException -> 0x009d }
            if (r3 == 0) goto L_0x009b
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x009d }
            r3.<init>()     // Catch:{ IOException -> 0x009d }
            java.lang.String r4 = "Subscribe to topic: "
            r3.append(r4)     // Catch:{ IOException -> 0x009d }
            java.lang.String r4 = r7.getTopic()     // Catch:{ IOException -> 0x009d }
            r3.append(r4)     // Catch:{ IOException -> 0x009d }
            r3.append(r2)     // Catch:{ IOException -> 0x009d }
            java.lang.String r2 = r3.toString()     // Catch:{ IOException -> 0x009d }
            android.util.Log.d(r0, r2)     // Catch:{ IOException -> 0x009d }
            goto L_0x009b
        L_0x0080:
            if (r2 == 0) goto L_0x009b
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x009d }
            r2.<init>()     // Catch:{ IOException -> 0x009d }
            java.lang.String r3 = "Unknown topic operation"
            r2.append(r3)     // Catch:{ IOException -> 0x009d }
            r2.append(r7)     // Catch:{ IOException -> 0x009d }
            java.lang.String r3 = "."
            r2.append(r3)     // Catch:{ IOException -> 0x009d }
            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x009d }
            android.util.Log.d(r0, r2)     // Catch:{ IOException -> 0x009d }
        L_0x009b:
            return r5
        L_0x009d:
            r2 = move-exception
            java.lang.String r3 = r2.getMessage()
            java.lang.String r4 = "SERVICE_NOT_AVAILABLE"
            boolean r3 = r4.equals(r3)
            if (r3 != 0) goto L_0x00c4
            java.lang.String r3 = r2.getMessage()
            java.lang.String r4 = "INTERNAL_SERVER_ERROR"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x00b7
            goto L_0x00c4
        L_0x00b7:
            java.lang.String r3 = r2.getMessage()
            if (r3 != 0) goto L_0x00c3
            java.lang.String r3 = "Topic operation failed without exception message. Will retry Topic operation."
            android.util.Log.e(r0, r3)
            return r1
        L_0x00c3:
            throw r2
        L_0x00c4:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Topic operation failed: "
            r3.append(r4)
            java.lang.String r4 = r2.getMessage()
            r3.append(r4)
            java.lang.String r4 = ". Will retry Topic operation."
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r0, r3)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.TopicsSubscriber.performTopicOperation(com.google.firebase.messaging.TopicOperation):boolean");
    }

    @WorkerThread
    private void blockingSubscribeToTopic(String topic) {
        awaitTask(this.rpc.subscribeToTopic(this.firebaseMessaging.blockingGetToken(), topic));
    }

    @WorkerThread
    private void blockingUnsubscribeFromTopic(String topic) {
        awaitTask(this.rpc.unsubscribeFromTopic(this.firebaseMessaging.blockingGetToken(), topic));
    }

    @WorkerThread
    private static <T> void awaitTask(Task<T> task) {
        try {
            Tasks.await(task, 30, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IOException) {
                throw ((IOException) cause);
            } else if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else {
                throw new IOException(e);
            }
        } catch (InterruptedException | TimeoutException e2) {
            throw new IOException(ERROR_SERVICE_NOT_AVAILABLE, e2);
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean isSyncScheduledOrRunning() {
        return this.syncScheduledOrRunning;
    }

    /* access modifiers changed from: package-private */
    public synchronized void setSyncScheduledOrRunning(boolean value) {
        this.syncScheduledOrRunning = value;
    }

    static boolean isDebugLogEnabled() {
        return Log.isLoggable(Constants.TAG, 3) || (Build.VERSION.SDK_INT == 23 && Log.isLoggable(Constants.TAG, 3));
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public TopicsStore getStore() {
        return this.store;
    }
}
