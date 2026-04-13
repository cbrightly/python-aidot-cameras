package com.google.firebase.messaging;

import android.content.SharedPreferences;
import android.text.TextUtils;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

public final class SharedPreferencesQueue {
    @GuardedBy("internalQueue")
    private boolean bulkOperation = false;
    @VisibleForTesting
    @GuardedBy("internalQueue")
    final ArrayDeque<String> internalQueue = new ArrayDeque<>();
    private final String itemSeparator;
    private final String queueName;
    private final SharedPreferences sharedPreferences;
    private final Executor syncExecutor;

    private SharedPreferencesQueue(SharedPreferences sharedPreferences2, String queueName2, String itemSeparator2, Executor syncExecutor2) {
        this.sharedPreferences = sharedPreferences2;
        this.queueName = queueName2;
        this.itemSeparator = itemSeparator2;
        this.syncExecutor = syncExecutor2;
    }

    @WorkerThread
    static SharedPreferencesQueue createInstance(SharedPreferences sharedPreferences2, String queueName2, String itemSeparator2, Executor syncExecutor2) {
        SharedPreferencesQueue queue = new SharedPreferencesQueue(sharedPreferences2, queueName2, itemSeparator2, syncExecutor2);
        queue.initQueue();
        return queue;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0049, code lost:
        return;
     */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initQueue() {
        /*
            r7 = this;
            java.util.ArrayDeque<java.lang.String> r0 = r7.internalQueue
            monitor-enter(r0)
            java.util.ArrayDeque<java.lang.String> r1 = r7.internalQueue     // Catch:{ all -> 0x004a }
            r1.clear()     // Catch:{ all -> 0x004a }
            android.content.SharedPreferences r1 = r7.sharedPreferences     // Catch:{ all -> 0x004a }
            java.lang.String r2 = r7.queueName     // Catch:{ all -> 0x004a }
            java.lang.String r3 = ""
            java.lang.String r1 = r1.getString(r2, r3)     // Catch:{ all -> 0x004a }
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x004a }
            if (r2 != 0) goto L_0x0048
            java.lang.String r2 = r7.itemSeparator     // Catch:{ all -> 0x004a }
            boolean r2 = r1.contains(r2)     // Catch:{ all -> 0x004a }
            if (r2 != 0) goto L_0x0021
            goto L_0x0048
        L_0x0021:
            java.lang.String r2 = r7.itemSeparator     // Catch:{ all -> 0x004a }
            r3 = -1
            java.lang.String[] r2 = r1.split(r2, r3)     // Catch:{ all -> 0x004a }
            int r3 = r2.length     // Catch:{ all -> 0x004a }
            if (r3 != 0) goto L_0x0032
            java.lang.String r3 = "FirebaseMessaging"
            java.lang.String r4 = "Corrupted queue. Please check the queue contents and item separator provided"
            android.util.Log.e(r3, r4)     // Catch:{ all -> 0x004a }
        L_0x0032:
            int r3 = r2.length     // Catch:{ all -> 0x004a }
            r4 = 0
        L_0x0034:
            if (r4 >= r3) goto L_0x0046
            r5 = r2[r4]     // Catch:{ all -> 0x004a }
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x004a }
            if (r6 != 0) goto L_0x0043
            java.util.ArrayDeque<java.lang.String> r6 = r7.internalQueue     // Catch:{ all -> 0x004a }
            r6.add(r5)     // Catch:{ all -> 0x004a }
        L_0x0043:
            int r4 = r4 + 1
            goto L_0x0034
        L_0x0046:
            monitor-exit(r0)     // Catch:{ all -> 0x004a }
            return
        L_0x0048:
            monitor-exit(r0)     // Catch:{ all -> 0x004a }
            return
        L_0x004a:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.SharedPreferencesQueue.initQueue():void");
    }

    @NonNull
    public List<String> toList() {
        ArrayList arrayList;
        synchronized (this.internalQueue) {
            arrayList = new ArrayList(this.internalQueue);
        }
        return arrayList;
    }

    public boolean add(@NonNull String item) {
        boolean checkAndSyncState;
        if (TextUtils.isEmpty(item) || item.contains(this.itemSeparator)) {
            return false;
        }
        synchronized (this.internalQueue) {
            checkAndSyncState = checkAndSyncState(this.internalQueue.add(item));
        }
        return checkAndSyncState;
    }

    @GuardedBy("internalQueue")
    private String checkAndSyncState(String transactionValue) {
        checkAndSyncState(transactionValue != null);
        return transactionValue;
    }

    @GuardedBy("internalQueue")
    private boolean checkAndSyncState(boolean transactionState) {
        if (transactionState && !this.bulkOperation) {
            syncStateAsync();
        }
        return transactionState;
    }

    private void syncStateAsync() {
        this.syncExecutor.execute(new a0(this));
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public void syncState() {
        synchronized (this.internalQueue) {
            this.sharedPreferences.edit().putString(this.queueName, serialize()).commit();
        }
    }

    @GuardedBy("internalQueue")
    @NonNull
    public String serialize() {
        StringBuilder builder = new StringBuilder();
        Iterator<String> it = this.internalQueue.iterator();
        while (it.hasNext()) {
            builder.append(it.next());
            builder.append(this.itemSeparator);
        }
        return builder.toString();
    }

    @GuardedBy("internalQueue")
    public void beginTransaction() {
        this.bulkOperation = true;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void beginTransactionSync() {
        synchronized (this.internalQueue) {
            beginTransaction();
        }
    }

    @GuardedBy("internalQueue")
    public void commitTransaction() {
        this.bulkOperation = false;
        syncStateAsync();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void commitTransactionSync() {
        synchronized (this.internalQueue) {
            commitTransaction();
        }
    }

    @VisibleForTesting
    public String serializeSync() {
        String serialize;
        synchronized (this.internalQueue) {
            serialize = serialize();
        }
        return serialize;
    }

    public boolean remove(@Nullable Object o) {
        boolean checkAndSyncState;
        synchronized (this.internalQueue) {
            checkAndSyncState = checkAndSyncState(this.internalQueue.remove(o));
        }
        return checkAndSyncState;
    }

    public String remove() {
        String checkAndSyncState;
        synchronized (this.internalQueue) {
            checkAndSyncState = checkAndSyncState(this.internalQueue.remove());
        }
        return checkAndSyncState;
    }

    public void clear() {
        synchronized (this.internalQueue) {
            this.internalQueue.clear();
            checkAndSyncState(true);
        }
    }

    @Nullable
    public String peek() {
        String peek;
        synchronized (this.internalQueue) {
            peek = this.internalQueue.peek();
        }
        return peek;
    }

    public int size() {
        int size;
        synchronized (this.internalQueue) {
            size = this.internalQueue.size();
        }
        return size;
    }
}
