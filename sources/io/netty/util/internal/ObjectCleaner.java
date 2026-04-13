package io.netty.util.internal;

import io.netty.util.concurrent.FastThreadLocalThread;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ObjectCleaner {
    /* access modifiers changed from: private */
    public static final AtomicBoolean CLEANER_RUNNING = new AtomicBoolean(false);
    private static final Runnable CLEANER_TASK = new Runnable() {
        /* JADX WARNING: Removed duplicated region for block: B:17:0x004c  */
        /* JADX WARNING: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r4 = this;
                r0 = 0
            L_0x0001:
                java.util.Set r1 = io.netty.util.internal.ObjectCleaner.LIVE_SET
                boolean r1 = r1.isEmpty()
                if (r1 != 0) goto L_0x002d
                java.lang.ref.ReferenceQueue r1 = io.netty.util.internal.ObjectCleaner.REFERENCE_QUEUE     // Catch:{ InterruptedException -> 0x002a }
                int r2 = io.netty.util.internal.ObjectCleaner.REFERENCE_QUEUE_POLL_TIMEOUT_MS     // Catch:{ InterruptedException -> 0x002a }
                long r2 = (long) r2     // Catch:{ InterruptedException -> 0x002a }
                java.lang.ref.Reference r1 = r1.remove(r2)     // Catch:{ InterruptedException -> 0x002a }
                io.netty.util.internal.ObjectCleaner$AutomaticCleanerReference r1 = (io.netty.util.internal.ObjectCleaner.AutomaticCleanerReference) r1     // Catch:{ InterruptedException -> 0x002a }
                if (r1 == 0) goto L_0x0029
                r1.cleanup()     // Catch:{ all -> 0x0021 }
                goto L_0x0022
            L_0x0021:
                r2 = move-exception
            L_0x0022:
                java.util.Set r2 = io.netty.util.internal.ObjectCleaner.LIVE_SET
                r2.remove(r1)
            L_0x0029:
                goto L_0x0001
            L_0x002a:
                r1 = move-exception
                r0 = 1
                goto L_0x0001
            L_0x002d:
                java.util.concurrent.atomic.AtomicBoolean r1 = io.netty.util.internal.ObjectCleaner.CLEANER_RUNNING
                r2 = 0
                r1.set(r2)
                java.util.Set r1 = io.netty.util.internal.ObjectCleaner.LIVE_SET
                boolean r1 = r1.isEmpty()
                if (r1 != 0) goto L_0x004a
                java.util.concurrent.atomic.AtomicBoolean r1 = io.netty.util.internal.ObjectCleaner.CLEANER_RUNNING
                r3 = 1
                boolean r1 = r1.compareAndSet(r2, r3)
                if (r1 != 0) goto L_0x0001
            L_0x004a:
                if (r0 == 0) goto L_0x0053
                java.lang.Thread r1 = java.lang.Thread.currentThread()
                r1.interrupt()
            L_0x0053:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.ObjectCleaner.AnonymousClass1.run():void");
        }
    };
    static final String CLEANER_THREAD_NAME = (ObjectCleaner.class.getSimpleName() + "Thread");
    /* access modifiers changed from: private */
    public static final Set<AutomaticCleanerReference> LIVE_SET = new ConcurrentSet();
    /* access modifiers changed from: private */
    public static final ReferenceQueue<Object> REFERENCE_QUEUE = new ReferenceQueue<>();
    /* access modifiers changed from: private */
    public static final int REFERENCE_QUEUE_POLL_TIMEOUT_MS = Math.max(500, SystemPropertyUtil.getInt("io.netty.util.internal.ObjectCleaner.refQueuePollTimeout", 10000));

    public static void register(Object object, Runnable cleanupTask) {
        LIVE_SET.add(new AutomaticCleanerReference(object, (Runnable) ObjectUtil.checkNotNull(cleanupTask, "cleanupTask")));
        if (CLEANER_RUNNING.compareAndSet(false, true)) {
            final Thread cleanupThread = new FastThreadLocalThread(CLEANER_TASK);
            cleanupThread.setPriority(1);
            AccessController.doPrivileged(new PrivilegedAction<Void>() {
                public Void run() {
                    cleanupThread.setContextClassLoader((ClassLoader) null);
                    return null;
                }
            });
            cleanupThread.setName(CLEANER_THREAD_NAME);
            cleanupThread.setDaemon(true);
            cleanupThread.start();
        }
    }

    public static int getLiveSetCount() {
        return LIVE_SET.size();
    }

    private ObjectCleaner() {
    }

    public static final class AutomaticCleanerReference extends WeakReference<Object> {
        private final Runnable cleanupTask;

        AutomaticCleanerReference(Object referent, Runnable cleanupTask2) {
            super(referent, ObjectCleaner.REFERENCE_QUEUE);
            this.cleanupTask = cleanupTask2;
        }

        /* access modifiers changed from: package-private */
        public void cleanup() {
            this.cleanupTask.run();
        }

        public Thread get() {
            return null;
        }

        public void clear() {
            ObjectCleaner.LIVE_SET.remove(this);
            super.clear();
        }
    }
}
