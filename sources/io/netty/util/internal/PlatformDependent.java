package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueue;
import io.netty.util.internal.shaded.org.jctools.queues.MpscChunkedArrayQueue;
import io.netty.util.internal.shaded.org.jctools.queues.MpscUnboundedArrayQueue;
import io.netty.util.internal.shaded.org.jctools.queues.SpscLinkedQueue;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscAtomicArrayQueue;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscGrowableAtomicArrayQueue;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscUnboundedAtomicArrayQueue;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.SpscLinkedAtomicQueue;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public final class PlatformDependent {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ADDRESS_SIZE = addressSize0();
    private static final long ARRAY_BASE_OFFSET = arrayBaseOffset0();
    public static final boolean BIG_ENDIAN_NATIVE_ORDER = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
    private static final int BIT_MODE = bitMode0();
    private static final boolean CAN_ENABLE_TCP_NODELAY_BY_DEFAULT = (!isAndroid());
    private static final Cleaner CLEANER;
    private static final boolean DIRECT_BUFFER_PREFERRED;
    private static final AtomicLong DIRECT_MEMORY_COUNTER;
    private static final long DIRECT_MEMORY_LIMIT;
    private static final boolean HAS_UNSAFE;
    private static final boolean IS_OSX = isOsx0();
    private static final boolean IS_WINDOWS = isWindows0();
    private static final int MAX_ALLOWED_MPSC_CAPACITY = 1073741824;
    private static final long MAX_DIRECT_MEMORY = maxDirectMemory0();
    private static final Pattern MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN = Pattern.compile("\\s*-XX:MaxDirectMemorySize\\s*=\\s*([0-9]+)\\s*([kKmMgG]?)\\s*$");
    private static final boolean MAYBE_SUPER_USER = maybeSuperUser0();
    private static final int MIN_MAX_MPSC_CAPACITY = 2048;
    private static final int MPSC_CHUNK_SIZE = 1024;
    private static final Cleaner NOOP;
    private static final String NORMALIZED_ARCH = normalizeArch(SystemPropertyUtil.get("os.arch", ""));
    private static final String NORMALIZED_OS = normalizeOs(SystemPropertyUtil.get("os.name", ""));
    private static final ThreadLocalRandomProvider RANDOM_PROVIDER;
    private static final File TMPDIR = tmpdir0();
    private static final int UNINITIALIZED_ARRAY_ALLOCATION_THRESHOLD;
    private static final boolean USE_DIRECT_BUFFER_NO_CLEANER;
    /* access modifiers changed from: private */
    public static final InternalLogger logger;

    public interface ThreadLocalRandomProvider {
        Random current();
    }

    static {
        InternalLogger instance = InternalLoggerFactory.getInstance((Class<?>) PlatformDependent.class);
        logger = instance;
        boolean hasUnsafe0 = hasUnsafe0();
        HAS_UNSAFE = hasUnsafe0;
        boolean z = hasUnsafe0 && !SystemPropertyUtil.getBoolean("io.netty.noPreferDirect", false);
        DIRECT_BUFFER_PREFERRED = z;
        Cleaner r4 = new Cleaner() {
            public void freeDirectBuffer(ByteBuffer buffer) {
            }
        };
        NOOP = r4;
        if (javaVersion() >= 7) {
            RANDOM_PROVIDER = new ThreadLocalRandomProvider() {
                public Random current() {
                    return ThreadLocalRandom.current();
                }
            };
        } else {
            RANDOM_PROVIDER = new ThreadLocalRandomProvider() {
                public Random current() {
                    return ThreadLocalRandom.current();
                }
            };
        }
        if (instance.isDebugEnabled()) {
            instance.debug("-Dio.netty.noPreferDirect: {}", (Object) Boolean.valueOf(!z));
        }
        if (!hasUnsafe() && !isAndroid() && !PlatformDependent0.isExplicitNoUnsafe()) {
            instance.info("Your platform does not provide complete low-level API for accessing direct buffers reliably. Unless explicitly requested, heap buffer will always be preferred to avoid potential system instability.");
        }
        long maxDirectMemory = SystemPropertyUtil.getLong("io.netty.maxDirectMemory", -1);
        if (maxDirectMemory == 0 || !hasUnsafe() || !PlatformDependent0.hasDirectBufferNoCleanerConstructor()) {
            USE_DIRECT_BUFFER_NO_CLEANER = false;
            DIRECT_MEMORY_COUNTER = null;
        } else {
            USE_DIRECT_BUFFER_NO_CLEANER = true;
            if (maxDirectMemory < 0) {
                maxDirectMemory = maxDirectMemory0();
                if (maxDirectMemory <= 0) {
                    DIRECT_MEMORY_COUNTER = null;
                } else {
                    DIRECT_MEMORY_COUNTER = new AtomicLong();
                }
            } else {
                DIRECT_MEMORY_COUNTER = new AtomicLong();
            }
        }
        DIRECT_MEMORY_LIMIT = maxDirectMemory;
        instance.debug("-Dio.netty.maxDirectMemory: {} bytes", (Object) Long.valueOf(maxDirectMemory));
        int i = (javaVersion() < 9 || !PlatformDependent0.hasAllocateArrayMethod()) ? -1 : SystemPropertyUtil.getInt("io.netty.uninitializedArrayAllocationThreshold", 1024);
        UNINITIALIZED_ARRAY_ALLOCATION_THRESHOLD = i;
        instance.debug("-Dio.netty.uninitializedArrayAllocationThreshold: {}", (Object) Integer.valueOf(i));
        if (isAndroid() || !hasUnsafe()) {
            CLEANER = r4;
        } else if (javaVersion() >= 9) {
            if (CleanerJava9.isSupported()) {
                r4 = new CleanerJava9();
            }
            CLEANER = r4;
        } else {
            if (CleanerJava6.isSupported()) {
                r4 = new CleanerJava6();
            }
            CLEANER = r4;
        }
    }

    public static boolean hasDirectBufferNoCleanerConstructor() {
        return PlatformDependent0.hasDirectBufferNoCleanerConstructor();
    }

    public static byte[] allocateUninitializedArray(int size) {
        int i = UNINITIALIZED_ARRAY_ALLOCATION_THRESHOLD;
        if (i < 0 || i > size) {
            return new byte[size];
        }
        return PlatformDependent0.allocateUninitializedArray(size);
    }

    public static boolean isAndroid() {
        return PlatformDependent0.isAndroid();
    }

    public static boolean isWindows() {
        return IS_WINDOWS;
    }

    public static boolean isOsx() {
        return IS_OSX;
    }

    public static boolean maybeSuperUser() {
        return MAYBE_SUPER_USER;
    }

    public static int javaVersion() {
        return PlatformDependent0.javaVersion();
    }

    public static boolean canEnableTcpNoDelayByDefault() {
        return CAN_ENABLE_TCP_NODELAY_BY_DEFAULT;
    }

    public static boolean hasUnsafe() {
        return HAS_UNSAFE;
    }

    public static Throwable getUnsafeUnavailabilityCause() {
        return PlatformDependent0.getUnsafeUnavailabilityCause();
    }

    public static boolean isUnaligned() {
        return PlatformDependent0.isUnaligned();
    }

    public static boolean directBufferPreferred() {
        return DIRECT_BUFFER_PREFERRED;
    }

    public static long maxDirectMemory() {
        return MAX_DIRECT_MEMORY;
    }

    public static File tmpdir() {
        return TMPDIR;
    }

    public static int bitMode() {
        return BIT_MODE;
    }

    public static int addressSize() {
        return ADDRESS_SIZE;
    }

    public static long allocateMemory(long size) {
        return PlatformDependent0.allocateMemory(size);
    }

    public static void freeMemory(long address) {
        PlatformDependent0.freeMemory(address);
    }

    public static void throwException(Throwable t) {
        if (hasUnsafe()) {
            PlatformDependent0.throwException(t);
        } else {
            throwException0(t);
        }
    }

    private static <E extends Throwable> void throwException0(Throwable t) {
        throw t;
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap();
    }

    public static LongCounter newLongCounter() {
        if (javaVersion() >= 8) {
            return new LongAdderCounter();
        }
        return new AtomicLongCounter();
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int initialCapacity) {
        return new ConcurrentHashMap(initialCapacity);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int initialCapacity, float loadFactor) {
        return new ConcurrentHashMap(initialCapacity, loadFactor);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
        return new ConcurrentHashMap(initialCapacity, loadFactor, concurrencyLevel);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(Map<? extends K, ? extends V> map) {
        return new ConcurrentHashMap(map);
    }

    public static void freeDirectBuffer(ByteBuffer buffer) {
        CLEANER.freeDirectBuffer(buffer);
    }

    public static long directBufferAddress(ByteBuffer buffer) {
        return PlatformDependent0.directBufferAddress(buffer);
    }

    public static ByteBuffer directBuffer(long memoryAddress, int size) {
        if (PlatformDependent0.hasDirectBufferNoCleanerConstructor()) {
            return PlatformDependent0.newDirectBuffer(memoryAddress, size);
        }
        throw new UnsupportedOperationException("sun.misc.Unsafe or java.nio.DirectByteBuffer.<init>(long, int) not available");
    }

    public static int getInt(Object object, long fieldOffset) {
        return PlatformDependent0.getInt(object, fieldOffset);
    }

    public static byte getByte(long address) {
        return PlatformDependent0.getByte(address);
    }

    public static short getShort(long address) {
        return PlatformDependent0.getShort(address);
    }

    public static int getInt(long address) {
        return PlatformDependent0.getInt(address);
    }

    public static long getLong(long address) {
        return PlatformDependent0.getLong(address);
    }

    public static byte getByte(byte[] data, int index) {
        return PlatformDependent0.getByte(data, index);
    }

    public static short getShort(byte[] data, int index) {
        return PlatformDependent0.getShort(data, index);
    }

    public static int getInt(byte[] data, int index) {
        return PlatformDependent0.getInt(data, index);
    }

    public static long getLong(byte[] data, int index) {
        return PlatformDependent0.getLong(data, index);
    }

    public static void putByte(long address, byte value) {
        PlatformDependent0.putByte(address, value);
    }

    public static void putShort(long address, short value) {
        PlatformDependent0.putShort(address, value);
    }

    public static void putInt(long address, int value) {
        PlatformDependent0.putInt(address, value);
    }

    public static void putLong(long address, long value) {
        PlatformDependent0.putLong(address, value);
    }

    public static void putByte(byte[] data, int index, byte value) {
        PlatformDependent0.putByte(data, index, value);
    }

    public static void putShort(byte[] data, int index, short value) {
        PlatformDependent0.putShort(data, index, value);
    }

    public static void putInt(byte[] data, int index, int value) {
        PlatformDependent0.putInt(data, index, value);
    }

    public static void putLong(byte[] data, int index, long value) {
        PlatformDependent0.putLong(data, index, value);
    }

    public static void copyMemory(long srcAddr, long dstAddr, long length) {
        PlatformDependent0.copyMemory(srcAddr, dstAddr, length);
    }

    public static void copyMemory(byte[] src, int srcIndex, long dstAddr, long length) {
        PlatformDependent0.copyMemory(src, ARRAY_BASE_OFFSET + ((long) srcIndex), (Object) null, dstAddr, length);
    }

    public static void copyMemory(long srcAddr, byte[] dst, int dstIndex, long length) {
        PlatformDependent0.copyMemory((Object) null, srcAddr, dst, ARRAY_BASE_OFFSET + ((long) dstIndex), length);
    }

    public static void setMemory(byte[] dst, int dstIndex, long bytes, byte value) {
        PlatformDependent0.setMemory(dst, ARRAY_BASE_OFFSET + ((long) dstIndex), bytes, value);
    }

    public static void setMemory(long address, long bytes, byte value) {
        PlatformDependent0.setMemory(address, bytes, value);
    }

    public static ByteBuffer allocateDirectNoCleaner(int capacity) {
        if (USE_DIRECT_BUFFER_NO_CLEANER) {
            incrementMemoryCounter(capacity);
            try {
                return PlatformDependent0.allocateDirectNoCleaner(capacity);
            } catch (Throwable e) {
                decrementMemoryCounter(capacity);
                throwException(e);
                return null;
            }
        } else {
            throw new AssertionError();
        }
    }

    public static ByteBuffer reallocateDirectNoCleaner(ByteBuffer buffer, int capacity) {
        if (USE_DIRECT_BUFFER_NO_CLEANER) {
            int len = capacity - buffer.capacity();
            incrementMemoryCounter(len);
            try {
                return PlatformDependent0.reallocateDirectNoCleaner(buffer, capacity);
            } catch (Throwable e) {
                decrementMemoryCounter(len);
                throwException(e);
                return null;
            }
        } else {
            throw new AssertionError();
        }
    }

    public static void freeDirectNoCleaner(ByteBuffer buffer) {
        if (USE_DIRECT_BUFFER_NO_CLEANER) {
            int capacity = buffer.capacity();
            PlatformDependent0.freeMemory(PlatformDependent0.directBufferAddress(buffer));
            decrementMemoryCounter(capacity);
            return;
        }
        throw new AssertionError();
    }

    private static void incrementMemoryCounter(int capacity) {
        AtomicLong atomicLong;
        long usedMemory;
        long newUsedMemory;
        if (DIRECT_MEMORY_COUNTER != null) {
            do {
                atomicLong = DIRECT_MEMORY_COUNTER;
                usedMemory = atomicLong.get();
                newUsedMemory = ((long) capacity) + usedMemory;
                long j = DIRECT_MEMORY_LIMIT;
                if (newUsedMemory > j) {
                    throw new OutOfDirectMemoryError("failed to allocate " + capacity + " byte(s) of direct memory (used: " + usedMemory + ", max: " + j + ')');
                }
            } while (!atomicLong.compareAndSet(usedMemory, newUsedMemory));
        }
    }

    private static void decrementMemoryCounter(int capacity) {
        AtomicLong atomicLong = DIRECT_MEMORY_COUNTER;
        if (atomicLong != null && atomicLong.addAndGet((long) (-capacity)) < 0) {
            throw new AssertionError();
        }
    }

    public static boolean useDirectBufferNoCleaner() {
        return USE_DIRECT_BUFFER_NO_CLEANER;
    }

    public static boolean isZero(byte[] bytes, int startPos, int length) {
        if (!hasUnsafe() || !isUnaligned()) {
            return isZeroSafe(bytes, startPos, length);
        }
        return PlatformDependent0.isZero(bytes, startPos, length);
    }

    public static final class Mpsc {
        private static final boolean USE_MPSC_CHUNKED_ARRAY_QUEUE;

        private Mpsc() {
        }

        static {
            Object unsafe = null;
            if (PlatformDependent.hasUnsafe()) {
                unsafe = AccessController.doPrivileged(new PrivilegedAction<Object>() {
                    public Object run() {
                        return UnsafeAccess.UNSAFE;
                    }
                });
            }
            if (unsafe == null) {
                PlatformDependent.logger.debug("org.jctools-core.MpscChunkedArrayQueue: unavailable");
                USE_MPSC_CHUNKED_ARRAY_QUEUE = false;
                return;
            }
            PlatformDependent.logger.debug("org.jctools-core.MpscChunkedArrayQueue: available");
            USE_MPSC_CHUNKED_ARRAY_QUEUE = true;
        }

        static <T> Queue<T> newMpscQueue(int maxCapacity) {
            int capacity = Math.max(Math.min(maxCapacity, 1073741824), 2048);
            return USE_MPSC_CHUNKED_ARRAY_QUEUE ? new MpscChunkedArrayQueue(1024, capacity) : new MpscGrowableAtomicArrayQueue(1024, capacity);
        }

        static <T> Queue<T> newMpscQueue() {
            return USE_MPSC_CHUNKED_ARRAY_QUEUE ? new MpscUnboundedArrayQueue(1024) : new MpscUnboundedAtomicArrayQueue(1024);
        }
    }

    public static <T> Queue<T> newMpscQueue() {
        return Mpsc.newMpscQueue();
    }

    public static <T> Queue<T> newMpscQueue(int maxCapacity) {
        return Mpsc.newMpscQueue(maxCapacity);
    }

    public static <T> Queue<T> newSpscQueue() {
        return hasUnsafe() ? new SpscLinkedQueue() : new SpscLinkedAtomicQueue();
    }

    public static <T> Queue<T> newFixedMpscQueue(int capacity) {
        return hasUnsafe() ? new MpscArrayQueue(capacity) : new MpscAtomicArrayQueue(capacity);
    }

    public static ClassLoader getClassLoader(Class<?> clazz) {
        return PlatformDependent0.getClassLoader(clazz);
    }

    public static ClassLoader getContextClassLoader() {
        return PlatformDependent0.getContextClassLoader();
    }

    public static ClassLoader getSystemClassLoader() {
        return PlatformDependent0.getSystemClassLoader();
    }

    public static <C> Deque<C> newConcurrentDeque() {
        if (javaVersion() < 7) {
            return new LinkedBlockingDeque();
        }
        return new ConcurrentLinkedDeque();
    }

    public static Random threadLocalRandom() {
        return RANDOM_PROVIDER.current();
    }

    private static boolean isWindows0() {
        boolean windows = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).contains("win");
        if (windows) {
            logger.debug("Platform: Windows");
        }
        return windows;
    }

    private static boolean isOsx0() {
        String osname = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
        boolean osx = osname.startsWith("macosx") || osname.startsWith("osx");
        if (osx) {
            logger.debug("Platform: MacOS");
        }
        return osx;
    }

    private static boolean maybeSuperUser0() {
        String username = SystemPropertyUtil.get("user.name");
        if (isWindows()) {
            return "Administrator".equals(username);
        }
        return "root".equals(username) || "toor".equals(username);
    }

    private static boolean hasUnsafe0() {
        if (isAndroid()) {
            logger.debug("sun.misc.Unsafe: unavailable (Android)");
            return false;
        } else if (PlatformDependent0.isExplicitNoUnsafe()) {
            return false;
        } else {
            try {
                boolean hasUnsafe = PlatformDependent0.hasUnsafe();
                logger.debug("sun.misc.Unsafe: {}", (Object) hasUnsafe ? "available" : "unavailable");
                return hasUnsafe;
            } catch (Throwable t) {
                logger.trace("Could not determine if Unsafe is available", t);
                return false;
            }
        }
    }

    private static long arrayBaseOffset0() {
        if (!hasUnsafe()) {
            return -1;
        }
        return PlatformDependent0.arrayBaseOffset();
    }

    private static long maxDirectMemory0() {
        long maxDirectMemory = 0;
        ClassLoader systemClassLoader = null;
        try {
            systemClassLoader = getSystemClassLoader();
            maxDirectMemory = ((Number) Class.forName("sun.misc.VM", true, systemClassLoader).getDeclaredMethod("maxDirectMemory", new Class[0]).invoke((Object) null, new Object[0])).longValue();
        } catch (Throwable th) {
        }
        if (maxDirectMemory > 0) {
            return maxDirectMemory;
        }
        try {
            Class<?> mgmtFactoryClass = Class.forName("java.lang.management.ManagementFactory", true, systemClassLoader);
            List<String> vmArgs = (List) Class.forName("java.lang.management.RuntimeMXBean", true, systemClassLoader).getDeclaredMethod("getInputArguments", new Class[0]).invoke(mgmtFactoryClass.getDeclaredMethod("getRuntimeMXBean", new Class[0]).invoke((Object) null, new Object[0]), new Object[0]);
            int i = vmArgs.size() - 1;
            while (true) {
                if (i < 0) {
                    break;
                }
                Matcher m = MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN.matcher(vmArgs.get(i));
                if (m.matches()) {
                    maxDirectMemory = Long.parseLong(m.group(1));
                    switch (m.group(2).charAt(0)) {
                        case 'G':
                        case 'g':
                            maxDirectMemory *= IjkMediaMeta.AV_CH_STEREO_RIGHT;
                            break;
                        case 'K':
                        case 'k':
                            maxDirectMemory *= 1024;
                            break;
                        case 'M':
                        case 'm':
                            maxDirectMemory *= 1048576;
                            break;
                    }
                } else {
                    i--;
                }
            }
        } catch (Throwable th2) {
        }
        if (maxDirectMemory <= 0) {
            long maxDirectMemory2 = Runtime.getRuntime().maxMemory();
            logger.debug("maxDirectMemory: {} bytes (maybe)", (Object) Long.valueOf(maxDirectMemory2));
            return maxDirectMemory2;
        }
        logger.debug("maxDirectMemory: {} bytes", (Object) Long.valueOf(maxDirectMemory));
        return maxDirectMemory;
    }

    private static File tmpdir0() {
        File f;
        try {
            File f2 = toDirectory(SystemPropertyUtil.get("io.netty.tmpdir"));
            if (f2 != null) {
                logger.debug("-Dio.netty.tmpdir: {}", (Object) f2);
                return f2;
            }
            File f3 = toDirectory(SystemPropertyUtil.get("java.io.tmpdir"));
            if (f3 != null) {
                logger.debug("-Dio.netty.tmpdir: {} (java.io.tmpdir)", (Object) f3);
                return f3;
            }
            if (isWindows()) {
                File f4 = toDirectory(System.getenv("TEMP"));
                if (f4 != null) {
                    logger.debug("-Dio.netty.tmpdir: {} (%TEMP%)", (Object) f4);
                    return f4;
                }
                String userprofile = System.getenv("USERPROFILE");
                if (userprofile != null) {
                    File f5 = toDirectory(userprofile + "\\AppData\\Local\\Temp");
                    if (f5 != null) {
                        logger.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\AppData\\Local\\Temp)", (Object) f5);
                        return f5;
                    }
                    File f6 = toDirectory(userprofile + "\\Local Settings\\Temp");
                    if (f6 != null) {
                        logger.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\Local Settings\\Temp)", (Object) f6);
                        return f6;
                    }
                }
            } else {
                File f7 = toDirectory(System.getenv("TMPDIR"));
                if (f7 != null) {
                    logger.debug("-Dio.netty.tmpdir: {} ($TMPDIR)", (Object) f7);
                    return f7;
                }
            }
            if (isWindows()) {
                f = new File("C:\\Windows\\Temp");
            } else {
                f = new File("/tmp");
            }
            logger.warn("Failed to get the temporary directory; falling back to: {}", (Object) f);
            return f;
        } catch (Throwable th) {
        }
    }

    private static File toDirectory(String path) {
        if (path == null) {
            return null;
        }
        File f = new File(path);
        f.mkdirs();
        if (!f.isDirectory()) {
            return null;
        }
        try {
            return f.getAbsoluteFile();
        } catch (Exception e) {
            return f;
        }
    }

    private static int bitMode0() {
        int bitMode = SystemPropertyUtil.getInt("io.netty.bitMode", 0);
        if (bitMode > 0) {
            logger.debug("-Dio.netty.bitMode: {}", (Object) Integer.valueOf(bitMode));
            return bitMode;
        }
        int bitMode2 = SystemPropertyUtil.getInt("sun.arch.data.model", 0);
        if (bitMode2 > 0) {
            logger.debug("-Dio.netty.bitMode: {} (sun.arch.data.model)", (Object) Integer.valueOf(bitMode2));
            return bitMode2;
        }
        int bitMode3 = SystemPropertyUtil.getInt("com.ibm.vm.bitmode", 0);
        if (bitMode3 > 0) {
            logger.debug("-Dio.netty.bitMode: {} (com.ibm.vm.bitmode)", (Object) Integer.valueOf(bitMode3));
            return bitMode3;
        }
        String str = SystemPropertyUtil.get("os.arch", "");
        Locale locale = Locale.US;
        String arch = str.toLowerCase(locale).trim();
        if ("amd64".equals(arch) || "x86_64".equals(arch)) {
            bitMode3 = 64;
        } else if ("i386".equals(arch) || "i486".equals(arch) || "i586".equals(arch) || "i686".equals(arch)) {
            bitMode3 = 32;
        }
        if (bitMode3 > 0) {
            logger.debug("-Dio.netty.bitMode: {} (os.arch: {})", Integer.valueOf(bitMode3), arch);
        }
        Matcher m = Pattern.compile("([1-9][0-9]+)-?bit").matcher(SystemPropertyUtil.get("java.vm.name", "").toLowerCase(locale));
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return 64;
    }

    private static int addressSize0() {
        if (!hasUnsafe()) {
            return -1;
        }
        return PlatformDependent0.addressSize();
    }

    private static boolean isZeroSafe(byte[] bytes, int startPos, int length) {
        int end = startPos + length;
        while (startPos < end) {
            if (bytes[startPos] != 0) {
                return false;
            }
            startPos++;
        }
        return true;
    }

    public static String normalizedArch() {
        return NORMALIZED_ARCH;
    }

    public static String normalizedOs() {
        return NORMALIZED_OS;
    }

    private static String normalize(String value) {
        return value.toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
    }

    private static String normalizeArch(String value) {
        String value2 = normalize(value);
        if (value2.matches("^(x8664|amd64|ia32e|em64t|x64)$")) {
            return "x86_64";
        }
        if (value2.matches("^(x8632|x86|i[3-6]86|ia32|x32)$")) {
            return "x86_32";
        }
        if (value2.matches("^(ia64|itanium64)$")) {
            return "itanium_64";
        }
        if (value2.matches("^(sparc|sparc32)$")) {
            return "sparc_32";
        }
        if (value2.matches("^(sparcv9|sparc64)$")) {
            return "sparc_64";
        }
        if (value2.matches("^(arm|arm32)$")) {
            return "arm_32";
        }
        if ("aarch64".equals(value2)) {
            return "aarch_64";
        }
        if (value2.matches("^(ppc|ppc32)$")) {
            return "ppc_32";
        }
        if ("ppc64".equals(value2)) {
            return "ppc_64";
        }
        if ("ppc64le".equals(value2)) {
            return "ppcle_64";
        }
        if ("s390".equals(value2)) {
            return "s390_32";
        }
        if ("s390x".equals(value2)) {
            return "s390_64";
        }
        return "unknown";
    }

    private static String normalizeOs(String value) {
        String value2 = normalize(value);
        if (value2.startsWith("aix")) {
            return "aix";
        }
        if (value2.startsWith("hpux")) {
            return "hpux";
        }
        if (value2.startsWith("os400") && (value2.length() <= 5 || !Character.isDigit(value2.charAt(5)))) {
            return "os400";
        }
        if (value2.startsWith("linux")) {
            return "linux";
        }
        if (value2.startsWith("macosx") || value2.startsWith("osx")) {
            return "osx";
        }
        if (value2.startsWith("freebsd")) {
            return "freebsd";
        }
        if (value2.startsWith("openbsd")) {
            return "openbsd";
        }
        if (value2.startsWith("netbsd")) {
            return "netbsd";
        }
        if (value2.startsWith("solaris") || value2.startsWith("sunos")) {
            return "sunos";
        }
        if (value2.startsWith("windows")) {
            return "windows";
        }
        return "unknown";
    }

    public static final class AtomicLongCounter extends AtomicLong implements LongCounter {
        private AtomicLongCounter() {
        }

        public void add(long delta) {
            addAndGet(delta);
        }

        public void increment() {
            incrementAndGet();
        }

        public void decrement() {
            decrementAndGet();
        }

        public long value() {
            return get();
        }
    }

    private PlatformDependent() {
    }
}
