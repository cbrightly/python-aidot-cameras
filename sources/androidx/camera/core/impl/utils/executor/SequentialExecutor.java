package androidx.camera.core.impl.utils.executor;

import androidx.annotation.GuardedBy;
import androidx.core.util.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;

public final class SequentialExecutor implements Executor {
    private static final String TAG = "SequentialExecutor";
    private final Executor mExecutor;
    @GuardedBy("mQueue")
    final Deque<Runnable> mQueue = new ArrayDeque();
    private final QueueWorker mWorker = new QueueWorker();
    @GuardedBy("mQueue")
    long mWorkerRunCount = 0;
    @GuardedBy("mQueue")
    WorkerRunningState mWorkerRunningState = WorkerRunningState.IDLE;

    public enum WorkerRunningState {
        IDLE,
        QUEUING,
        QUEUED,
        RUNNING
    }

    SequentialExecutor(Executor executor) {
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0022, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r11.mExecutor.execute(r11.mWorker);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002e, code lost:
        if (r11.mWorkerRunningState == r5) goto L_0x0031;
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
        r8 = r11.mQueue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0038, code lost:
        monitor-enter(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003d, code lost:
        if (r11.mWorkerRunCount != r3) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0041, code lost:
        if (r11.mWorkerRunningState != r5) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0043, code lost:
        r11.mWorkerRunningState = r2;
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
        monitor-enter(r11.mQueue);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        r7 = r11.mWorkerRunningState;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0054, code lost:
        if (r7 == androidx.camera.core.impl.utils.executor.SequentialExecutor.WorkerRunningState.IDLE) goto L_0x005a;
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
            androidx.core.util.Preconditions.checkNotNull(r12)
            java.util.Deque<java.lang.Runnable> r0 = r11.mQueue
            monitor-enter(r0)
            androidx.camera.core.impl.utils.executor.SequentialExecutor$WorkerRunningState r1 = r11.mWorkerRunningState     // Catch:{ all -> 0x0078 }
            androidx.camera.core.impl.utils.executor.SequentialExecutor$WorkerRunningState r2 = androidx.camera.core.impl.utils.executor.SequentialExecutor.WorkerRunningState.RUNNING     // Catch:{ all -> 0x0078 }
            if (r1 == r2) goto L_0x0071
            androidx.camera.core.impl.utils.executor.SequentialExecutor$WorkerRunningState r2 = androidx.camera.core.impl.utils.executor.SequentialExecutor.WorkerRunningState.QUEUED     // Catch:{ all -> 0x0078 }
            if (r1 != r2) goto L_0x0011
            goto L_0x0071
        L_0x0011:
            long r3 = r11.mWorkerRunCount     // Catch:{ all -> 0x0078 }
            androidx.camera.core.impl.utils.executor.SequentialExecutor$1 r1 = new androidx.camera.core.impl.utils.executor.SequentialExecutor$1     // Catch:{ all -> 0x0078 }
            r1.<init>(r12)     // Catch:{ all -> 0x0078 }
            java.util.Deque<java.lang.Runnable> r5 = r11.mQueue     // Catch:{ all -> 0x0078 }
            r5.add(r1)     // Catch:{ all -> 0x0078 }
            androidx.camera.core.impl.utils.executor.SequentialExecutor$WorkerRunningState r5 = androidx.camera.core.impl.utils.executor.SequentialExecutor.WorkerRunningState.QUEUING     // Catch:{ all -> 0x0078 }
            r11.mWorkerRunningState = r5     // Catch:{ all -> 0x0078 }
            monitor-exit(r0)     // Catch:{ all -> 0x0078 }
            r0 = 1
            r6 = 0
            java.util.concurrent.Executor r7 = r11.mExecutor     // Catch:{ RuntimeException -> 0x004c, Error -> 0x004a }
            androidx.camera.core.impl.utils.executor.SequentialExecutor$QueueWorker r8 = r11.mWorker     // Catch:{ RuntimeException -> 0x004c, Error -> 0x004a }
            r7.execute(r8)     // Catch:{ RuntimeException -> 0x004c, Error -> 0x004a }
            androidx.camera.core.impl.utils.executor.SequentialExecutor$WorkerRunningState r7 = r11.mWorkerRunningState
            if (r7 == r5) goto L_0x0031
            goto L_0x0032
        L_0x0031:
            r0 = r6
        L_0x0032:
            r7 = r0
            if (r7 == 0) goto L_0x0036
            return
        L_0x0036:
            java.util.Deque<java.lang.Runnable> r8 = r11.mQueue
            monitor-enter(r8)
            long r9 = r11.mWorkerRunCount     // Catch:{ all -> 0x0047 }
            int r0 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r0 != 0) goto L_0x0045
            androidx.camera.core.impl.utils.executor.SequentialExecutor$WorkerRunningState r0 = r11.mWorkerRunningState     // Catch:{ all -> 0x0047 }
            if (r0 != r5) goto L_0x0045
            r11.mWorkerRunningState = r2     // Catch:{ all -> 0x0047 }
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
            java.util.Deque<java.lang.Runnable> r5 = r11.mQueue
            monitor-enter(r5)
            androidx.camera.core.impl.utils.executor.SequentialExecutor$WorkerRunningState r7 = r11.mWorkerRunningState     // Catch:{ all -> 0x006e }
            androidx.camera.core.impl.utils.executor.SequentialExecutor$WorkerRunningState r8 = androidx.camera.core.impl.utils.executor.SequentialExecutor.WorkerRunningState.IDLE     // Catch:{ all -> 0x006e }
            if (r7 == r8) goto L_0x005a
            androidx.camera.core.impl.utils.executor.SequentialExecutor$WorkerRunningState r8 = androidx.camera.core.impl.utils.executor.SequentialExecutor.WorkerRunningState.QUEUING     // Catch:{ all -> 0x006e }
            if (r7 != r8) goto L_0x0063
        L_0x005a:
            java.util.Deque<java.lang.Runnable> r7 = r11.mQueue     // Catch:{ all -> 0x006e }
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
            java.util.Deque<java.lang.Runnable> r1 = r11.mQueue     // Catch:{ all -> 0x0078 }
            r1.add(r12)     // Catch:{ all -> 0x0078 }
            monitor-exit(r0)     // Catch:{ all -> 0x0078 }
            return
        L_0x0078:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0078 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.impl.utils.executor.SequentialExecutor.execute(java.lang.Runnable):void");
    }

    public final class QueueWorker implements Runnable {
        QueueWorker() {
        }

        public void run() {
            try {
                workOnQueue();
            } catch (Error e) {
                synchronized (SequentialExecutor.this.mQueue) {
                    SequentialExecutor.this.mWorkerRunningState = WorkerRunningState.IDLE;
                    throw e;
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0014, code lost:
            java.lang.Thread.currentThread().interrupt();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0039, code lost:
            if (r0 == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x003b, code lost:
            java.lang.Thread.currentThread().interrupt();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0048, code lost:
            r0 = r0 | java.lang.Thread.interrupted();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            r3.run();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
            if (r0 == false) goto L_?;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void workOnQueue() {
            /*
                r10 = this;
                r0 = 0
                r1 = 0
            L_0x0002:
                androidx.camera.core.impl.utils.executor.SequentialExecutor r2 = androidx.camera.core.impl.utils.executor.SequentialExecutor.this     // Catch:{ all -> 0x0068 }
                java.util.Deque<java.lang.Runnable> r2 = r2.mQueue     // Catch:{ all -> 0x0068 }
                monitor-enter(r2)     // Catch:{ all -> 0x0068 }
                if (r1 != 0) goto L_0x0026
                androidx.camera.core.impl.utils.executor.SequentialExecutor r3 = androidx.camera.core.impl.utils.executor.SequentialExecutor.this     // Catch:{ all -> 0x0065 }
                androidx.camera.core.impl.utils.executor.SequentialExecutor$WorkerRunningState r4 = r3.mWorkerRunningState     // Catch:{ all -> 0x0065 }
                androidx.camera.core.impl.utils.executor.SequentialExecutor$WorkerRunningState r5 = androidx.camera.core.impl.utils.executor.SequentialExecutor.WorkerRunningState.RUNNING     // Catch:{ all -> 0x0065 }
                if (r4 != r5) goto L_0x001c
                monitor-exit(r2)     // Catch:{ all -> 0x0065 }
                if (r0 == 0) goto L_0x001b
                java.lang.Thread r2 = java.lang.Thread.currentThread()
                r2.interrupt()
            L_0x001b:
                return
            L_0x001c:
                long r6 = r3.mWorkerRunCount     // Catch:{ all -> 0x0065 }
                r8 = 1
                long r6 = r6 + r8
                r3.mWorkerRunCount = r6     // Catch:{ all -> 0x0065 }
                r3.mWorkerRunningState = r5     // Catch:{ all -> 0x0065 }
                r1 = 1
            L_0x0026:
                androidx.camera.core.impl.utils.executor.SequentialExecutor r3 = androidx.camera.core.impl.utils.executor.SequentialExecutor.this     // Catch:{ all -> 0x0065 }
                java.util.Deque<java.lang.Runnable> r3 = r3.mQueue     // Catch:{ all -> 0x0065 }
                java.lang.Object r3 = r3.poll()     // Catch:{ all -> 0x0065 }
                java.lang.Runnable r3 = (java.lang.Runnable) r3     // Catch:{ all -> 0x0065 }
                if (r3 != 0) goto L_0x0043
                androidx.camera.core.impl.utils.executor.SequentialExecutor r4 = androidx.camera.core.impl.utils.executor.SequentialExecutor.this     // Catch:{ all -> 0x0065 }
                androidx.camera.core.impl.utils.executor.SequentialExecutor$WorkerRunningState r5 = androidx.camera.core.impl.utils.executor.SequentialExecutor.WorkerRunningState.IDLE     // Catch:{ all -> 0x0065 }
                r4.mWorkerRunningState = r5     // Catch:{ all -> 0x0065 }
                monitor-exit(r2)     // Catch:{ all -> 0x0065 }
                if (r0 == 0) goto L_0x0042
                java.lang.Thread r2 = java.lang.Thread.currentThread()
                r2.interrupt()
            L_0x0042:
                return
            L_0x0043:
                monitor-exit(r2)     // Catch:{ all -> 0x0065 }
                boolean r2 = java.lang.Thread.interrupted()     // Catch:{ all -> 0x0068 }
                r0 = r0 | r2
                r3.run()     // Catch:{ RuntimeException -> 0x004d }
                goto L_0x0064
            L_0x004d:
                r2 = move-exception
                java.lang.String r4 = "SequentialExecutor"
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0068 }
                r5.<init>()     // Catch:{ all -> 0x0068 }
                java.lang.String r6 = "Exception while executing runnable "
                r5.append(r6)     // Catch:{ all -> 0x0068 }
                r5.append(r3)     // Catch:{ all -> 0x0068 }
                java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0068 }
                androidx.camera.core.Logger.e(r4, r5, r2)     // Catch:{ all -> 0x0068 }
            L_0x0064:
                goto L_0x0002
            L_0x0065:
                r3 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0065 }
                throw r3     // Catch:{ all -> 0x0068 }
            L_0x0068:
                r2 = move-exception
                if (r0 == 0) goto L_0x0072
                java.lang.Thread r3 = java.lang.Thread.currentThread()
                r3.interrupt()
            L_0x0072:
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.impl.utils.executor.SequentialExecutor.QueueWorker.workOnQueue():void");
        }
    }
}
