package com.google.firebase.messaging;

import androidx.annotation.GuardedBy;
import androidx.collection.ArrayMap;
import com.google.android.gms.tasks.Task;
import java.util.Map;
import java.util.concurrent.Executor;

public class RequestDeduplicator {
    private final Executor executor;
    @GuardedBy("this")
    private final Map<String, Task<String>> getTokenRequests = new ArrayMap();

    public interface GetTokenRequest {
        Task<String> start();
    }

    public /* synthetic */ Task a(String str, Task task) {
        lambda$getOrStartGetTokenRequest$0(str, task);
        return task;
    }

    RequestDeduplicator(Executor executor2) {
        this.executor = executor2;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x002b, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.google.android.gms.tasks.Task<java.lang.String> getOrStartGetTokenRequest(java.lang.String r5, com.google.firebase.messaging.RequestDeduplicator.GetTokenRequest r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.util.Map<java.lang.String, com.google.android.gms.tasks.Task<java.lang.String>> r0 = r4.getTokenRequests     // Catch:{ all -> 0x0061 }
            java.lang.Object r0 = r0.get(r5)     // Catch:{ all -> 0x0061 }
            com.google.android.gms.tasks.Task r0 = (com.google.android.gms.tasks.Task) r0     // Catch:{ all -> 0x0061 }
            r1 = 3
            if (r0 == 0) goto L_0x002c
            java.lang.String r2 = "FirebaseMessaging"
            boolean r1 = android.util.Log.isLoggable(r2, r1)     // Catch:{ all -> 0x0061 }
            if (r1 == 0) goto L_0x002a
            java.lang.String r1 = "FirebaseMessaging"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0061 }
            r2.<init>()     // Catch:{ all -> 0x0061 }
            java.lang.String r3 = "Joining ongoing request for: "
            r2.append(r3)     // Catch:{ all -> 0x0061 }
            r2.append(r5)     // Catch:{ all -> 0x0061 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0061 }
            android.util.Log.d(r1, r2)     // Catch:{ all -> 0x0061 }
        L_0x002a:
            monitor-exit(r4)
            return r0
        L_0x002c:
            java.lang.String r2 = "FirebaseMessaging"
            boolean r1 = android.util.Log.isLoggable(r2, r1)     // Catch:{ all -> 0x0061 }
            if (r1 == 0) goto L_0x004a
            java.lang.String r1 = "FirebaseMessaging"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0061 }
            r2.<init>()     // Catch:{ all -> 0x0061 }
            java.lang.String r3 = "Making new request for: "
            r2.append(r3)     // Catch:{ all -> 0x0061 }
            r2.append(r5)     // Catch:{ all -> 0x0061 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0061 }
            android.util.Log.d(r1, r2)     // Catch:{ all -> 0x0061 }
        L_0x004a:
            com.google.android.gms.tasks.Task r1 = r6.start()     // Catch:{ all -> 0x0061 }
            java.util.concurrent.Executor r2 = r4.executor     // Catch:{ all -> 0x0061 }
            com.google.firebase.messaging.z r3 = new com.google.firebase.messaging.z     // Catch:{ all -> 0x0061 }
            r3.<init>(r4, r5)     // Catch:{ all -> 0x0061 }
            com.google.android.gms.tasks.Task r1 = r1.continueWithTask(r2, r3)     // Catch:{ all -> 0x0061 }
            java.util.Map<java.lang.String, com.google.android.gms.tasks.Task<java.lang.String>> r2 = r4.getTokenRequests     // Catch:{ all -> 0x0061 }
            r2.put(r5, r1)     // Catch:{ all -> 0x0061 }
            monitor-exit(r4)
            return r1
        L_0x0061:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.RequestDeduplicator.getOrStartGetTokenRequest(java.lang.String, com.google.firebase.messaging.RequestDeduplicator$GetTokenRequest):com.google.android.gms.tasks.Task");
    }

    private /* synthetic */ Task lambda$getOrStartGetTokenRequest$0(String authorizedEntity, Task task) {
        synchronized (this) {
            this.getTokenRequests.remove(authorizedEntity);
        }
        return task;
    }
}
