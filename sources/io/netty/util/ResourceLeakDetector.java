package io.netty.util;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class ResourceLeakDetector<T> {
    /* access modifiers changed from: private */
    public static final Level DEFAULT_LEVEL;
    static final int DEFAULT_SAMPLING_INTERVAL = 128;
    private static final int DEFAULT_TARGET_RECORDS = 4;
    private static final String PROP_LEVEL = "io.netty.leakDetection.level";
    private static final String PROP_LEVEL_OLD = "io.netty.leakDetectionLevel";
    private static final String PROP_TARGET_RECORDS = "io.netty.leakDetection.targetRecords";
    /* access modifiers changed from: private */
    public static final int TARGET_RECORDS;
    /* access modifiers changed from: private */
    public static final AtomicReference<String[]> excludedMethods = new AtomicReference<>(EmptyArrays.EMPTY_STRINGS);
    private static Level level;
    private static final InternalLogger logger;
    private final ConcurrentMap<DefaultResourceLeak<?>, LeakEntry> allLeaks;
    private final ReferenceQueue<Object> refQueue;
    private final ConcurrentMap<String, Boolean> reportedLeaks;
    private final String resourceType;
    private final int samplingInterval;

    static {
        boolean disabled;
        Level defaultLevel = Level.SIMPLE;
        DEFAULT_LEVEL = defaultLevel;
        InternalLogger instance = InternalLoggerFactory.getInstance((Class<?>) ResourceLeakDetector.class);
        logger = instance;
        if (SystemPropertyUtil.get("io.netty.noResourceLeakDetection") != null) {
            disabled = SystemPropertyUtil.getBoolean("io.netty.noResourceLeakDetection", false);
            instance.debug("-Dio.netty.noResourceLeakDetection: {}", (Object) Boolean.valueOf(disabled));
            instance.warn("-Dio.netty.noResourceLeakDetection is deprecated. Use '-D{}={}' instead.", PROP_LEVEL, defaultLevel.name().toLowerCase());
        } else {
            disabled = false;
        }
        if (disabled) {
            defaultLevel = Level.DISABLED;
        }
        Level level2 = Level.parseLevel(SystemPropertyUtil.get(PROP_LEVEL, SystemPropertyUtil.get(PROP_LEVEL_OLD, defaultLevel.name())));
        int i = SystemPropertyUtil.getInt(PROP_TARGET_RECORDS, 4);
        TARGET_RECORDS = i;
        level = level2;
        if (instance.isDebugEnabled()) {
            instance.debug("-D{}: {}", PROP_LEVEL, level2.name().toLowerCase());
            instance.debug("-D{}: {}", PROP_TARGET_RECORDS, Integer.valueOf(i));
        }
    }

    public enum Level {
        DISABLED,
        SIMPLE,
        ADVANCED,
        PARANOID;

        static Level parseLevel(String levelStr) {
            String trimmedLevelStr = levelStr.trim();
            for (Level l : values()) {
                if (trimmedLevelStr.equalsIgnoreCase(l.name()) || trimmedLevelStr.equals(String.valueOf(l.ordinal()))) {
                    return l;
                }
            }
            return ResourceLeakDetector.DEFAULT_LEVEL;
        }
    }

    @Deprecated
    public static void setEnabled(boolean enabled) {
        setLevel(enabled ? Level.SIMPLE : Level.DISABLED);
    }

    public static boolean isEnabled() {
        return getLevel().ordinal() > Level.DISABLED.ordinal();
    }

    public static void setLevel(Level level2) {
        if (level2 != null) {
            level = level2;
            return;
        }
        throw new NullPointerException(FirebaseAnalytics.Param.LEVEL);
    }

    public static Level getLevel() {
        return level;
    }

    @Deprecated
    public ResourceLeakDetector(Class<?> resourceType2) {
        this(StringUtil.simpleClassName(resourceType2));
    }

    @Deprecated
    public ResourceLeakDetector(String resourceType2) {
        this(resourceType2, 128, (long) DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
    }

    @Deprecated
    public ResourceLeakDetector(Class<?> resourceType2, int samplingInterval2, long maxActive) {
        this(resourceType2, samplingInterval2);
    }

    public ResourceLeakDetector(Class<?> resourceType2, int samplingInterval2) {
        this(StringUtil.simpleClassName(resourceType2), samplingInterval2, (long) DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
    }

    @Deprecated
    public ResourceLeakDetector(String resourceType2, int samplingInterval2, long maxActive) {
        this.allLeaks = PlatformDependent.newConcurrentHashMap();
        this.refQueue = new ReferenceQueue<>();
        this.reportedLeaks = PlatformDependent.newConcurrentHashMap();
        if (resourceType2 != null) {
            this.resourceType = resourceType2;
            this.samplingInterval = samplingInterval2;
            return;
        }
        throw new NullPointerException("resourceType");
    }

    @Deprecated
    public final ResourceLeak open(T obj) {
        return track0(obj);
    }

    public final ResourceLeakTracker<T> track(T obj) {
        return track0(obj);
    }

    private DefaultResourceLeak track0(T obj) {
        Level level2 = level;
        if (level2 == Level.DISABLED) {
            return null;
        }
        if (level2.ordinal() >= Level.PARANOID.ordinal()) {
            reportLeak();
            return new DefaultResourceLeak(obj, this.refQueue, this.allLeaks);
        } else if (PlatformDependent.threadLocalRandom().nextInt(this.samplingInterval) != 0) {
            return null;
        } else {
            reportLeak();
            return new DefaultResourceLeak(obj, this.refQueue, this.allLeaks);
        }
    }

    private void clearRefQueue() {
        while (true) {
            DefaultResourceLeak ref = (DefaultResourceLeak) this.refQueue.poll();
            if (ref != null) {
                ref.dispose();
            } else {
                return;
            }
        }
    }

    private void reportLeak() {
        if (!logger.isErrorEnabled()) {
            clearRefQueue();
            return;
        }
        while (true) {
            DefaultResourceLeak ref = (DefaultResourceLeak) this.refQueue.poll();
            if (ref != null) {
                if (ref.dispose()) {
                    String records = ref.toString();
                    if (this.reportedLeaks.putIfAbsent(records, Boolean.TRUE) == null) {
                        if (records.isEmpty()) {
                            reportUntracedLeak(this.resourceType);
                        } else {
                            reportTracedLeak(this.resourceType, records);
                        }
                    }
                }
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void reportTracedLeak(String resourceType2, String records) {
        logger.error("LEAK: {}.release() was not called before it's garbage-collected. See http://netty.io/wiki/reference-counted-objects.html for more information.{}", resourceType2, records);
    }

    /* access modifiers changed from: protected */
    public void reportUntracedLeak(String resourceType2) {
        logger.error("LEAK: {}.release() was not called before it's garbage-collected. Enable advanced leak reporting to find out where the leak occurred. To enable advanced leak reporting, specify the JVM option '-D{}={}' or call {}.setLevel() See http://netty.io/wiki/reference-counted-objects.html for more information.", resourceType2, PROP_LEVEL, Level.ADVANCED.name().toLowerCase(), StringUtil.simpleClassName((Object) this));
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void reportInstancesLeak(String resourceType2) {
    }

    public static final class DefaultResourceLeak<T> extends WeakReference<Object> implements ResourceLeakTracker<T>, ResourceLeak {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final AtomicIntegerFieldUpdater<DefaultResourceLeak<?>> droppedRecordsUpdater = AtomicIntegerFieldUpdater.newUpdater(DefaultResourceLeak.class, "droppedRecords");
        private static final AtomicReferenceFieldUpdater<DefaultResourceLeak<?>, Record> headUpdater = AtomicReferenceFieldUpdater.newUpdater(DefaultResourceLeak.class, Record.class, CacheEntity.HEAD);
        private final ConcurrentMap<DefaultResourceLeak<?>, LeakEntry> allLeaks;
        private volatile int droppedRecords;
        private volatile Record head;
        private final int trackedHash;

        DefaultResourceLeak(Object referent, ReferenceQueue<Object> refQueue, ConcurrentMap<DefaultResourceLeak<?>, LeakEntry> allLeaks2) {
            super(referent, refQueue);
            if (referent != null) {
                this.trackedHash = System.identityHashCode(referent);
                allLeaks2.put(this, LeakEntry.INSTANCE);
                headUpdater.set(this, Record.BOTTOM);
                this.allLeaks = allLeaks2;
                return;
            }
            throw new AssertionError();
        }

        public void record() {
            record0((Object) null);
        }

        public void record(Object hint) {
            record0(hint);
        }

        private void record0(Object hint) {
            AtomicReferenceFieldUpdater<DefaultResourceLeak<?>, Record> atomicReferenceFieldUpdater;
            Record oldHead;
            boolean dropped;
            Record newHead;
            if (ResourceLeakDetector.TARGET_RECORDS > 0) {
                do {
                    atomicReferenceFieldUpdater = headUpdater;
                    Record record = atomicReferenceFieldUpdater.get(this);
                    oldHead = record;
                    Record prevHead = record;
                    if (record != null) {
                        boolean z = true;
                        int numElements = oldHead.pos + 1;
                        if (numElements >= ResourceLeakDetector.TARGET_RECORDS) {
                            if (PlatformDependent.threadLocalRandom().nextInt(1 << Math.min(numElements - ResourceLeakDetector.TARGET_RECORDS, 30)) == 0) {
                                z = false;
                            }
                            dropped = z;
                            if (z) {
                                prevHead = oldHead.next;
                            }
                        } else {
                            dropped = false;
                        }
                        if (hint == null) {
                            newHead = new Record(prevHead);
                        }
                    } else {
                        return;
                    }
                } while (!atomicReferenceFieldUpdater.compareAndSet(this, oldHead, newHead));
                if (dropped) {
                    droppedRecordsUpdater.incrementAndGet(this);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public boolean dispose() {
            clear();
            return this.allLeaks.remove(this, LeakEntry.INSTANCE);
        }

        public boolean close() {
            if (!this.allLeaks.remove(this, LeakEntry.INSTANCE)) {
                return false;
            }
            clear();
            headUpdater.set(this, (Object) null);
            return true;
        }

        public boolean close(T trackedObject) {
            if (this.trackedHash == System.identityHashCode(trackedObject)) {
                return close() && trackedObject != null;
            }
            throw new AssertionError();
        }

        public String toString() {
            Record oldHead = headUpdater.getAndSet(this, (Object) null);
            if (oldHead == null) {
                return "";
            }
            int dropped = droppedRecordsUpdater.get(this);
            int duped = 0;
            int present = oldHead.pos + 1;
            StringBuilder sb = new StringBuilder(present * 2048);
            String str = StringUtil.NEWLINE;
            StringBuilder buf = sb.append(str);
            buf.append("Recent access records: ");
            buf.append(str);
            int i = 1;
            Set<String> seen = new HashSet<>(present);
            while (oldHead != Record.BOTTOM) {
                String s = oldHead.toString();
                if (!seen.add(s)) {
                    duped++;
                } else if (oldHead.next == Record.BOTTOM) {
                    buf.append("Created at:");
                    buf.append(StringUtil.NEWLINE);
                    buf.append(s);
                } else {
                    buf.append('#');
                    buf.append(i);
                    buf.append(':');
                    buf.append(StringUtil.NEWLINE);
                    buf.append(s);
                    i++;
                }
                oldHead = oldHead.next;
            }
            if (duped > 0) {
                buf.append(": ");
                buf.append(dropped);
                buf.append(" leak records were discarded because they were duplicates");
                buf.append(StringUtil.NEWLINE);
            }
            if (dropped > 0) {
                buf.append(": ");
                buf.append(dropped);
                buf.append(" leak records were discarded because the leak record count is targeted to ");
                buf.append(ResourceLeakDetector.TARGET_RECORDS);
                buf.append(". Use system property ");
                buf.append(ResourceLeakDetector.PROP_TARGET_RECORDS);
                buf.append(" to increase the limit.");
                buf.append(StringUtil.NEWLINE);
            }
            buf.setLength(buf.length() - StringUtil.NEWLINE.length());
            return buf.toString();
        }
    }

    public static void addExclusions(Class clz, String... methodNames) {
        String[] oldMethods;
        String[] newMethods;
        Set<String> nameSet = new HashSet<>(Arrays.asList(methodNames));
        Method[] declaredMethods = clz.getDeclaredMethods();
        int length = declaredMethods.length;
        int i = 0;
        while (i < length && (!nameSet.remove(declaredMethods[i].getName()) || !nameSet.isEmpty())) {
            i++;
        }
        if (nameSet.isEmpty()) {
            do {
                oldMethods = excludedMethods.get();
                newMethods = (String[]) Arrays.copyOf(oldMethods, oldMethods.length + (methodNames.length * 2));
                for (int i2 = 0; i2 < methodNames.length; i2++) {
                    newMethods[oldMethods.length + (i2 * 2)] = clz.getName();
                    newMethods[oldMethods.length + (i2 * 2) + 1] = methodNames[i2];
                }
            } while (!excludedMethods.compareAndSet(oldMethods, newMethods));
            return;
        }
        throw new IllegalArgumentException("Can't find '" + nameSet + "' in " + clz.getName());
    }

    public static final class Record extends Throwable {
        /* access modifiers changed from: private */
        public static final Record BOTTOM = new Record();
        private static final long serialVersionUID = 6065153674892850720L;
        private final String hintString;
        /* access modifiers changed from: private */
        public final Record next;
        /* access modifiers changed from: private */
        public final int pos;

        Record(Record next2, Object hint) {
            this.hintString = hint instanceof ResourceLeakHint ? ((ResourceLeakHint) hint).toHintString() : hint.toString();
            this.next = next2;
            this.pos = next2.pos + 1;
        }

        Record(Record next2) {
            this.hintString = null;
            this.next = next2;
            this.pos = next2.pos + 1;
        }

        private Record() {
            this.hintString = null;
            this.next = null;
            this.pos = -1;
        }

        public String toString() {
            StringBuilder buf = new StringBuilder(2048);
            if (this.hintString != null) {
                buf.append("\tHint: ");
                buf.append(this.hintString);
                buf.append(StringUtil.NEWLINE);
            }
            StackTraceElement[] array = getStackTrace();
            for (int i = 3; i < array.length; i++) {
                StackTraceElement element = array[i];
                String[] exclusions = (String[]) ResourceLeakDetector.excludedMethods.get();
                int k = 0;
                while (true) {
                    if (k < exclusions.length) {
                        if (exclusions[k].equals(element.getClassName()) && exclusions[k + 1].equals(element.getMethodName())) {
                            break;
                        }
                        k += 2;
                    } else {
                        buf.append(9);
                        buf.append(element.toString());
                        buf.append(StringUtil.NEWLINE);
                        break;
                    }
                }
            }
            return buf.toString();
        }
    }

    public static final class LeakEntry {
        private static final int HASH;
        static final LeakEntry INSTANCE;

        static {
            LeakEntry leakEntry = new LeakEntry();
            INSTANCE = leakEntry;
            HASH = System.identityHashCode(leakEntry);
        }

        private LeakEntry() {
        }

        public int hashCode() {
            return HASH;
        }

        public boolean equals(Object obj) {
            return obj == this;
        }
    }
}
