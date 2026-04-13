package com.google.firebase.concurrent;

import androidx.annotation.GuardedBy;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;

public final class SequentialExecutor implements Executor {
    /* access modifiers changed from: private */
    public static final Logger log = Logger.getLogger(SequentialExecutor.class.getName());
    private final Executor executor;
    /* access modifiers changed from: private */
    @GuardedBy("queue")
    public final Deque<Runnable> queue = new ArrayDeque();
    private final QueueWorker worker = new QueueWorker();
    @GuardedBy("queue")
    private long workerRunCount = 0;
    /* access modifiers changed from: private */
    @GuardedBy("queue")
    public WorkerRunningState workerRunningState = WorkerRunningState.IDLE;

    public enum WorkerRunningState {
        IDLE,
        QUEUING,
        QUEUED,
        RUNNING
    }

    static /* synthetic */ long access$308(SequentialExecutor x0) {
        long j = x0.workerRunCount;
        x0.workerRunCount = 1 + j;
        return j;
    }

    SequentialExecutor(Executor executor2) {
        this.executor = (Executor) Preconditions.checkNotNull(executor2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0022, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r11.executor.execute(r11.worker);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002e, code lost:
        if (r11.workerRunningState == r5) goto L_0x0031;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0031, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0033, code lost:
        if (r0 == false) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0035, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0036, code lost:
        r8 = r11.queue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0038, code lost:
        monitor-enter(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003d, code lost:
        if (r11.workerRunCount != r3) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0041, code lost:
        if (r11.workerRunningState != r5) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0043, code lost:
        r11.workerRunningState = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0045, code lost:
        monitor-exit(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0046, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x004a, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x004f, code lost:
        monitor-enter(r11.queue);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        r7 = r11.workerRunningState;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0054, code lost:
        if (r7 == com.google.firebase.concurrent.SequentialExecutor.WorkerRunningState.IDLE) goto L_0x005a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0063, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0066, code lost:
        if ((r2 instanceof java.util.concurrent.RejectedExecutionException) == false) goto L_0x006d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x006b, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x006d, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void execute(final java.lang.Runnable r12) {
        /*
            r11 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r12)
            java.util.Deque<java.lang.Runnable> r0 = r11.queue
            monitor-enter(r0)
            com.google.firebase.concurrent.SequentialExecutor$WorkerRunningState r1 = r11.workerRunningState     // Catch:{ all -> 0x0078 }
            com.google.firebase.concurrent.SequentialExecutor$WorkerRunningState r2 = com.google.firebase.concurrent.SequentialExecutor.WorkerRunningState.RUNNING     // Catch:{ all -> 0x0078 }
            if (r1 == r2) goto L_0x0071
            com.google.firebase.concurrent.SequentialExecutor$WorkerRunningState r2 = com.google.firebase.concurrent.SequentialExecutor.WorkerRunningState.QUEUED     // Catch:{ all -> 0x0078 }
            if (r1 != r2) goto L_0x0011
            goto L_0x0071
        L_0x0011:
            long r3 = r11.workerRunCount     // Catch:{ all -> 0x0078 }
            com.google.firebase.concurrent.SequentialExecutor$1 r1 = new com.google.firebase.concurrent.SequentialExecutor$1     // Catch:{ all -> 0x0078 }
            r1.<init>(r12)     // Catch:{ all -> 0x0078 }
            java.util.Deque<java.lang.Runnable> r5 = r11.queue     // Catch:{ all -> 0x0078 }
            r5.add(r1)     // Catch:{ all -> 0x0078 }
            com.google.firebase.concurrent.SequentialExecutor$WorkerRunningState r5 = com.google.firebase.concurrent.SequentialExecutor.WorkerRunningState.QUEUING     // Catch:{ all -> 0x0078 }
            r11.workerRunningState = r5     // Catch:{ all -> 0x0078 }
            monitor-exit(r0)     // Catch:{ all -> 0x0078 }
            r0 = 1
            r6 = 0
            java.util.concurrent.Executor r7 = r11.executor     // Catch:{ RuntimeException -> 0x004c, Error -> 0x004a }
            com.google.firebase.concurrent.SequentialExecutor$QueueWorker r8 = r11.worker     // Catch:{ RuntimeException -> 0x004c, Error -> 0x004a }
            r7.execute(r8)     // Catch:{ RuntimeException -> 0x004c, Error -> 0x004a }
            com.google.firebase.concurrent.SequentialExecutor$WorkerRunningState r7 = r11.workerRunningState
            if (r7 == r5) goto L_0x0031
            goto L_0x0032
        L_0x0031:
            r0 = r6
        L_0x0032:
            r7 = r0
            if (r7 == 0) goto L_0x0036
            return
        L_0x0036:
            java.util.Deque<java.lang.Runnable> r8 = r11.queue
            monitor-enter(r8)
            long r9 = r11.workerRunCount     // Catch:{ all -> 0x0047 }
            int r0 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r0 != 0) goto L_0x0045
            com.google.firebase.concurrent.SequentialExecutor$WorkerRunningState r0 = r11.workerRunningState     // Catch:{ all -> 0x0047 }
            if (r0 != r5) goto L_0x0045
            r11.workerRunningState = r2     // Catch:{ all -> 0x0047 }
        L_0x0045:
            monitor-exit(r8)     // Catch:{ all -> 0x0047 }
            return
        L_0x0047:
            r0 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x0047 }
            throw r0
        L_0x004a:
            r2 = move-exception
            goto L_0x004d
        L_0x004c:
            r2 = move-exception
        L_0x004d:
            java.util.Deque<java.lang.Runnable> r5 = r11.queue
            monitor-enter(r5)
            com.google.firebase.concurrent.SequentialExecutor$WorkerRunningState r7 = r11.workerRunningState     // Catch:{ all -> 0x006e }
            com.google.firebase.concurrent.SequentialExecutor$WorkerRunningState r8 = com.google.firebase.concurrent.SequentialExecutor.WorkerRunningState.IDLE     // Catch:{ all -> 0x006e }
            if (r7 == r8) goto L_0x005a
            com.google.firebase.concurrent.SequentialExecutor$WorkerRunningState r8 = com.google.firebase.concurrent.SequentialExecutor.WorkerRunningState.QUEUING     // Catch:{ all -> 0x006e }
            if (r7 != r8) goto L_0x0063
        L_0x005a:
            java.util.Deque<java.lang.Runnable> r7 = r11.queue     // Catch:{ all -> 0x006e }
            boolean r7 = r7.removeLastOccurrence(r1)     // Catch:{ all -> 0x006e }
            if (r7 == 0) goto L_0x0063
            goto L_0x0064
        L_0x0063:
            r0 = r6
        L_0x0064:
            boolean r6 = r2 instanceof java.util.concurrent.RejectedExecutionException     // Catch:{ all -> 0x006e }
            if (r6 == 0) goto L_0x006c
            if (r0 != 0) goto L_0x006c
            monitor-exit(r5)     // Catch:{ all -> 0x006e }
            return
        L_0x006c:
            throw r2     // Catch:{ all -> 0x006e }
        L_0x006e:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x006e }
            throw r0
        L_0x0071:
            java.util.Deque<java.lang.Runnable> r1 = r11.queue     // Catch:{ all -> 0x0078 }
            r1.add(r12)     // Catch:{ all -> 0x0078 }
            monitor-exit(r0)     // Catch:{ all -> 0x0078 }
            return
        L_0x0078:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0078 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.concurrent.SequentialExecutor.execute(java.lang.Runnable):void");
    }

    public final class QueueWorker implements Runnable {
        @CheckForNull
        Runnable task;

        private QueueWorker() {
        }

        public void run() {
            try {
                workOnQueue();
            } catch (Error e) {
                synchronized (SequentialExecutor.this.queue) {
                    WorkerRunningState unused = SequentialExecutor.this.workerRunningState = WorkerRunningState.IDLE;
                    throw e;
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0018, code lost:
            java.lang.Thread.currentThread().interrupt();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0043, code lost:
            if (r0 == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0045, code lost:
            java.lang.Thread.currentThread().interrupt();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0052, code lost:
            r0 = r0 | java.lang.Thread.interrupted();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            r8.task.run();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
            r8.task = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x005e, code lost:
            r3 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
            r4 = com.google.firebase.concurrent.SequentialExecutor.access$400();
            r5 = java.util.logging.Level.SEVERE;
            r4.log(r5, "Exception while executing runnable " + r8.task, r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
            r8.task = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0016, code lost:
            if (r0 == false) goto L_?;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void workOnQueue() {
            /*
                r8 = this;
                r0 = 0
                r1 = 0
            L_0x0002:
                com.google.firebase.concurrent.SequentialExecutor r2 = com.google.firebase.concurrent.SequentialExecutor.this     // Catch:{ all -> 0x0085 }
                java.util.Deque r2 = r2.queue     // Catch:{ all -> 0x0085 }
                monitor-enter(r2)     // Catch:{ all -> 0x0085 }
                if (r1 != 0) goto L_0x002b
                com.google.firebase.concurrent.SequentialExecutor r3 = com.google.firebase.concurrent.SequentialExecutor.this     // Catch:{ all -> 0x0082 }
                com.google.firebase.concurrent.SequentialExecutor$WorkerRunningState r3 = r3.workerRunningState     // Catch:{ all -> 0x0082 }
                com.google.firebase.concurrent.SequentialExecutor$WorkerRunningState r4 = com.google.firebase.concurrent.SequentialExecutor.WorkerRunningState.RUNNING     // Catch:{ all -> 0x0082 }
                if (r3 != r4) goto L_0x0020
                monitor-exit(r2)     // Catch:{ all -> 0x0082 }
                if (r0 == 0) goto L_0x001f
                java.lang.Thread r2 = java.lang.Thread.currentThread()
                r2.interrupt()
            L_0x001f:
                return
            L_0x0020:
                com.google.firebase.concurrent.SequentialExecutor r3 = com.google.firebase.concurrent.SequentialExecutor.this     // Catch:{ all -> 0x0082 }
                com.google.firebase.concurrent.SequentialExecutor.access$308(r3)     // Catch:{ all -> 0x0082 }
                com.google.firebase.concurrent.SequentialExecutor r3 = com.google.firebase.concurrent.SequentialExecutor.this     // Catch:{ all -> 0x0082 }
                com.google.firebase.concurrent.SequentialExecutor.WorkerRunningState unused = r3.workerRunningState = r4     // Catch:{ all -> 0x0082 }
                r1 = 1
            L_0x002b:
                com.google.firebase.concurrent.SequentialExecutor r3 = com.google.firebase.concurrent.SequentialExecutor.this     // Catch:{ all -> 0x0082 }
                java.util.Deque r3 = r3.queue     // Catch:{ all -> 0x0082 }
                java.lang.Object r3 = r3.poll()     // Catch:{ all -> 0x0082 }
                java.lang.Runnable r3 = (java.lang.Runnable) r3     // Catch:{ all -> 0x0082 }
                r8.task = r3     // Catch:{ all -> 0x0082 }
                if (r3 != 0) goto L_0x004d
                com.google.firebase.concurrent.SequentialExecutor r3 = com.google.firebase.concurrent.SequentialExecutor.this     // Catch:{ all -> 0x0082 }
                com.google.firebase.concurrent.SequentialExecutor$WorkerRunningState r4 = com.google.firebase.concurrent.SequentialExecutor.WorkerRunningState.IDLE     // Catch:{ all -> 0x0082 }
                com.google.firebase.concurrent.SequentialExecutor.WorkerRunningState unused = r3.workerRunningState = r4     // Catch:{ all -> 0x0082 }
                monitor-exit(r2)     // Catch:{ all -> 0x0082 }
                if (r0 == 0) goto L_0x004c
                java.lang.Thread r2 = java.lang.Thread.currentThread()
                r2.interrupt()
            L_0x004c:
                return
            L_0x004d:
                monitor-exit(r2)     // Catch:{ all -> 0x0082 }
                boolean r2 = java.lang.Thread.interrupted()     // Catch:{ all -> 0x0085 }
                r0 = r0 | r2
                r2 = 0
                java.lang.Runnable r3 = r8.task     // Catch:{ RuntimeException -> 0x005e }
                r3.run()     // Catch:{ RuntimeException -> 0x005e }
                r8.task = r2     // Catch:{ all -> 0x0085 }
            L_0x005b:
                goto L_0x0002
            L_0x005c:
                r3 = move-exception
                goto L_0x007e
            L_0x005e:
                r3 = move-exception
                java.util.logging.Logger r4 = com.google.firebase.concurrent.SequentialExecutor.log     // Catch:{ all -> 0x005c }
                java.util.logging.Level r5 = java.util.logging.Level.SEVERE     // Catch:{ all -> 0x005c }
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x005c }
                r6.<init>()     // Catch:{ all -> 0x005c }
                java.lang.String r7 = "Exception while executing runnable "
                r6.append(r7)     // Catch:{ all -> 0x005c }
                java.lang.Runnable r7 = r8.task     // Catch:{ all -> 0x005c }
                r6.append(r7)     // Catch:{ all -> 0x005c }
                java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x005c }
                r4.log(r5, r6, r3)     // Catch:{ all -> 0x005c }
                r8.task = r2     // Catch:{ all -> 0x0085 }
                goto L_0x005b
            L_0x007e:
                r8.task = r2     // Catch:{ all -> 0x0085 }
                throw r3     // Catch:{ all -> 0x0085 }
            L_0x0082:
                r3 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0082 }
                throw r3     // Catch:{ all -> 0x0085 }
            L_0x0085:
                r2 = move-exception
                if (r0 == 0) goto L_0x008f
                java.lang.Thread r3 = java.lang.Thread.currentThread()
                r3.interrupt()
            L_0x008f:
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.concurrent.SequentialExecutor.QueueWorker.workOnQueue():void");
        }

        public String toString() {
            Runnable currentlyRunning = this.task;
            if (currentlyRunning != null) {
                return "SequentialExecutorWorker{running=" + currentlyRunning + "}";
            }
            return "SequentialExecutorWorker{state=" + SequentialExecutor.this.workerRunningState + "}";
        }
    }

    public String toString() {
        return "SequentialExecutor@" + System.identityHashCode(this) + "{" + this.executor + "}";
    }
}
