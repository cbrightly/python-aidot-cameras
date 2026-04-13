package io.netty.util.concurrent;

import io.netty.util.internal.StringUtil;
import java.util.Locale;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolId = new AtomicInteger();
    private final boolean daemon;
    private final AtomicInteger nextId;
    private final String prefix;
    private final int priority;
    protected final ThreadGroup threadGroup;

    public DefaultThreadFactory(Class<?> poolType) {
        this(poolType, false, 5);
    }

    public DefaultThreadFactory(String poolName) {
        this(poolName, false, 5);
    }

    public DefaultThreadFactory(Class<?> poolType, boolean daemon2) {
        this(poolType, daemon2, 5);
    }

    public DefaultThreadFactory(String poolName, boolean daemon2) {
        this(poolName, daemon2, 5);
    }

    public DefaultThreadFactory(Class<?> poolType, int priority2) {
        this(poolType, false, priority2);
    }

    public DefaultThreadFactory(String poolName, int priority2) {
        this(poolName, false, priority2);
    }

    public DefaultThreadFactory(Class<?> poolType, boolean daemon2, int priority2) {
        this(toPoolName(poolType), daemon2, priority2);
    }

    public static String toPoolName(Class<?> poolType) {
        if (poolType != null) {
            String poolName = StringUtil.simpleClassName(poolType);
            switch (poolName.length()) {
                case 0:
                    return "unknown";
                case 1:
                    return poolName.toLowerCase(Locale.US);
                default:
                    if (!Character.isUpperCase(poolName.charAt(0)) || !Character.isLowerCase(poolName.charAt(1))) {
                        return poolName;
                    }
                    return Character.toLowerCase(poolName.charAt(0)) + poolName.substring(1);
            }
        } else {
            throw new NullPointerException("poolType");
        }
    }

    public DefaultThreadFactory(String poolName, boolean daemon2, int priority2, ThreadGroup threadGroup2) {
        this.nextId = new AtomicInteger();
        if (poolName == null) {
            throw new NullPointerException("poolName");
        } else if (priority2 < 1 || priority2 > 10) {
            throw new IllegalArgumentException("priority: " + priority2 + " (expected: Thread.MIN_PRIORITY <= priority <= Thread.MAX_PRIORITY)");
        } else {
            this.prefix = poolName + '-' + poolId.incrementAndGet() + '-';
            this.daemon = daemon2;
            this.priority = priority2;
            this.threadGroup = threadGroup2;
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public DefaultThreadFactory(String poolName, boolean daemon2, int priority2) {
        this(poolName, daemon2, priority2, System.getSecurityManager() == null ? Thread.currentThread().getThreadGroup() : System.getSecurityManager().getThreadGroup());
    }

    public Thread newThread(Runnable r) {
        Runnable wrap = FastThreadLocalRunnable.wrap(r);
        Thread t = newThread(wrap, this.prefix + this.nextId.incrementAndGet());
        try {
            boolean isDaemon = t.isDaemon();
            boolean z = this.daemon;
            if (isDaemon != z) {
                t.setDaemon(z);
            }
            int priority2 = t.getPriority();
            int i = this.priority;
            if (priority2 != i) {
                t.setPriority(i);
            }
        } catch (Exception e) {
        }
        return t;
    }

    /* access modifiers changed from: protected */
    public Thread newThread(Runnable r, String name) {
        return new FastThreadLocalThread(this.threadGroup, r, name);
    }
}
