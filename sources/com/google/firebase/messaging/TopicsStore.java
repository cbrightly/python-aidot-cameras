package com.google.firebase.messaging;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;

public final class TopicsStore {
    private static final String DIVIDER_QUEUE_OPERATIONS = ",";
    @VisibleForTesting
    static final String KEY_TOPIC_OPERATIONS_QUEUE = "topic_operation_queue";
    @VisibleForTesting
    static final String PREFERENCES = "com.google.android.gms.appid";
    @GuardedBy("TopicsStore.class")
    private static WeakReference<TopicsStore> topicsStoreWeakReference;
    private final SharedPreferences sharedPreferences;
    private final Executor syncExecutor;
    private SharedPreferencesQueue topicOperationsQueue;

    private TopicsStore(SharedPreferences sharedPrefs, Executor executor) {
        this.syncExecutor = executor;
        this.sharedPreferences = sharedPrefs;
    }

    @WorkerThread
    private synchronized void initStore() {
        this.topicOperationsQueue = SharedPreferencesQueue.createInstance(this.sharedPreferences, KEY_TOPIC_OPERATIONS_QUEUE, DIVIDER_QUEUE_OPERATIONS, this.syncExecutor);
    }

    @WorkerThread
    public static synchronized TopicsStore getInstance(Context context, Executor executor) {
        TopicsStore store;
        synchronized (TopicsStore.class) {
            store = null;
            WeakReference<TopicsStore> weakReference = topicsStoreWeakReference;
            if (weakReference != null) {
                store = (TopicsStore) weakReference.get();
            }
            if (store == null) {
                store = new TopicsStore(context.getSharedPreferences(PREFERENCES, 0), executor);
                store.initStore();
                topicsStoreWeakReference = new WeakReference<>(store);
            }
        }
        return store;
    }

    @VisibleForTesting
    static synchronized void clearCaches() {
        synchronized (TopicsStore.class) {
            WeakReference<TopicsStore> weakReference = topicsStoreWeakReference;
            if (weakReference != null) {
                weakReference.clear();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public synchronized TopicOperation getNextTopicOperation() {
        return TopicOperation.from(this.topicOperationsQueue.peek());
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean addTopicOperation(TopicOperation topicOperation) {
        return this.topicOperationsQueue.add(topicOperation.serialize());
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean removeTopicOperation(TopicOperation topicOperation) {
        return this.topicOperationsQueue.remove(topicOperation.serialize());
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public synchronized TopicOperation pollTopicOperation() {
        try {
        } catch (NoSuchElementException e) {
            Log.e(Constants.TAG, "Polling operation queue failed");
            return null;
        }
        return TopicOperation.from(this.topicOperationsQueue.remove());
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public synchronized List<TopicOperation> getOperations() {
        List<TopicOperation> operations;
        List<String> items = this.topicOperationsQueue.toList();
        operations = new ArrayList<>(items.size());
        for (String item : items) {
            operations.add(TopicOperation.from(item));
        }
        return operations;
    }

    /* access modifiers changed from: package-private */
    public synchronized void clearTopicOperations() {
        this.topicOperationsQueue.clear();
    }
}
