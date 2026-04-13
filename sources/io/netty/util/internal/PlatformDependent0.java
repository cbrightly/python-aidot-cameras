package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLogger;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import sun.misc.Unsafe;

public final class PlatformDependent0 {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long ADDRESS_FIELD_OFFSET;
    private static final Method ALLOCATE_ARRAY_METHOD;
    private static final long BYTE_ARRAY_BASE_OFFSET;
    private static final Constructor<?> DIRECT_BUFFER_CONSTRUCTOR;
    private static final Object INTERNAL_UNSAFE;
    private static final boolean IS_ANDROID = isAndroid0();
    private static final boolean IS_EXPLICIT_NO_UNSAFE = explicitNoUnsafe0();
    private static final boolean IS_EXPLICIT_TRY_REFLECTION_SET_ACCESSIBLE = explicitTryReflectionSetAccessible0();
    private static final int JAVA_VERSION = javaVersion0();
    private static final boolean UNALIGNED;
    static final Unsafe UNSAFE;
    private static final long UNSAFE_COPY_THRESHOLD = 1048576;
    private static final Throwable UNSAFE_UNAVAILABILITY_CAUSE;
    private static final InternalLogger logger;

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: java.lang.reflect.Method} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.UnsupportedOperationException} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v7, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v20, resolved type: java.lang.reflect.Field} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v16, resolved type: sun.misc.Unsafe} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v16, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v12, resolved type: java.lang.UnsupportedOperationException} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x01f0  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x01fb  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0216  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0219  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x022b  */
    static {
        /*
            java.lang.Class<io.netty.util.internal.PlatformDependent0> r0 = io.netty.util.internal.PlatformDependent0.class
            io.netty.util.internal.logging.InternalLogger r0 = io.netty.util.internal.logging.InternalLoggerFactory.getInstance((java.lang.Class<?>) r0)
            logger = r0
            boolean r1 = explicitNoUnsafe0()
            IS_EXPLICIT_NO_UNSAFE = r1
            int r1 = javaVersion0()
            JAVA_VERSION = r1
            boolean r1 = isAndroid0()
            IS_ANDROID = r1
            boolean r1 = explicitTryReflectionSetAccessible0()
            IS_EXPLICIT_TRY_REFLECTION_SET_ACCESSIBLE = r1
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            boolean r5 = isExplicitNoUnsafe()
            r6 = 1
            r8 = 1
            if (r5 == 0) goto L_0x003c
            r5 = 0
            r1 = 0
            java.lang.UnsupportedOperationException r9 = new java.lang.UnsupportedOperationException
            java.lang.String r10 = "Unsafe explicitly disabled"
            r9.<init>(r10)
            r3 = r9
            r9 = 0
            r4 = 0
            goto L_0x00ce
        L_0x003c:
            java.nio.ByteBuffer r5 = java.nio.ByteBuffer.allocateDirect(r8)
            io.netty.util.internal.PlatformDependent0$1 r9 = new io.netty.util.internal.PlatformDependent0$1
            r9.<init>()
            java.lang.Object r9 = java.security.AccessController.doPrivileged(r9)
            boolean r10 = r9 instanceof java.lang.Throwable
            if (r10 == 0) goto L_0x005b
            r10 = 0
            r3 = r9
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            r11 = r9
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            java.lang.String r12 = "sun.misc.Unsafe.theUnsafe: unavailable"
            r0.debug((java.lang.String) r12, (java.lang.Throwable) r11)
            goto L_0x0064
        L_0x005b:
            r10 = r9
            sun.misc.Unsafe r10 = (sun.misc.Unsafe) r10
            java.lang.String r11 = "sun.misc.Unsafe.theUnsafe: available"
            r0.debug(r11)
        L_0x0064:
            if (r10 == 0) goto L_0x0086
            r11 = r10
            io.netty.util.internal.PlatformDependent0$2 r12 = new io.netty.util.internal.PlatformDependent0$2
            r12.<init>(r11)
            java.lang.Object r12 = java.security.AccessController.doPrivileged(r12)
            if (r12 != 0) goto L_0x0079
            java.lang.String r13 = "sun.misc.Unsafe.copyMemory: available"
            r0.debug(r13)
            goto L_0x0086
        L_0x0079:
            r10 = 0
            r3 = r12
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            r13 = r12
            java.lang.Throwable r13 = (java.lang.Throwable) r13
            java.lang.String r14 = "sun.misc.Unsafe.copyMemory: unavailable"
            r0.debug((java.lang.String) r14, (java.lang.Throwable) r13)
        L_0x0086:
            if (r10 == 0) goto L_0x00ab
            r11 = r10
            io.netty.util.internal.PlatformDependent0$3 r12 = new io.netty.util.internal.PlatformDependent0$3
            r12.<init>(r11, r5)
            java.lang.Object r12 = java.security.AccessController.doPrivileged(r12)
            boolean r13 = r12 instanceof java.lang.reflect.Field
            if (r13 == 0) goto L_0x009f
            r1 = r12
            java.lang.reflect.Field r1 = (java.lang.reflect.Field) r1
            java.lang.String r13 = "java.nio.Buffer.address: available"
            r0.debug(r13)
            goto L_0x00ab
        L_0x009f:
            r3 = r12
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            r13 = r12
            java.lang.Throwable r13 = (java.lang.Throwable) r13
            java.lang.String r14 = "java.nio.Buffer.address: unavailable"
            r0.debug((java.lang.String) r14, (java.lang.Throwable) r13)
            r10 = 0
        L_0x00ab:
            if (r10 == 0) goto L_0x00cd
            java.lang.Class<byte[]> r11 = byte[].class
            int r11 = r10.arrayIndexScale(r11)
            long r11 = (long) r11
            int r13 = (r11 > r6 ? 1 : (r11 == r6 ? 0 : -1))
            if (r13 == 0) goto L_0x00cd
            java.lang.Long r13 = java.lang.Long.valueOf(r11)
            java.lang.String r14 = "unsafe.arrayIndexScale is {} (expected: 1). Not using unsafe."
            r0.debug((java.lang.String) r14, (java.lang.Object) r13)
            java.lang.UnsupportedOperationException r13 = new java.lang.UnsupportedOperationException
            java.lang.String r14 = "Unexpected unsafe.arrayIndexScale"
            r13.<init>(r14)
            r3 = r13
            r10 = 0
            r9 = r10
            goto L_0x00ce
        L_0x00cd:
            r9 = r10
        L_0x00ce:
            UNSAFE_UNAVAILABILITY_CAUSE = r3
            UNSAFE = r9
            r10 = 0
            r11 = -1
            if (r9 != 0) goto L_0x00e6
            BYTE_ARRAY_BASE_OFFSET = r11
            ADDRESS_FIELD_OFFSET = r11
            UNALIGNED = r10
            r0 = 0
            DIRECT_BUFFER_CONSTRUCTOR = r0
            ALLOCATE_ARRAY_METHOD = r0
            r18 = r1
            goto L_0x020e
        L_0x00e6:
            r13 = -1
            io.netty.util.internal.PlatformDependent0$4 r15 = new io.netty.util.internal.PlatformDependent0$4     // Catch:{ all -> 0x0222 }
            r15.<init>(r5)     // Catch:{ all -> 0x0222 }
            java.lang.Object r15 = java.security.AccessController.doPrivileged(r15)     // Catch:{ all -> 0x0222 }
            boolean r11 = r15 instanceof java.lang.reflect.Constructor     // Catch:{ all -> 0x0222 }
            r12 = 2
            if (r11 == 0) goto L_0x0126
            long r6 = r9.allocateMemory(r6)     // Catch:{ all -> 0x0121 }
            r13 = r6
            r6 = r15
            java.lang.reflect.Constructor r6 = (java.lang.reflect.Constructor) r6     // Catch:{ InstantiationException -> 0x011e, IllegalAccessException -> 0x011b, InvocationTargetException -> 0x0118 }
            java.lang.Object[] r7 = new java.lang.Object[r12]     // Catch:{ InstantiationException -> 0x011e, IllegalAccessException -> 0x011b, InvocationTargetException -> 0x0118 }
            java.lang.Long r11 = java.lang.Long.valueOf(r13)     // Catch:{ InstantiationException -> 0x011e, IllegalAccessException -> 0x011b, InvocationTargetException -> 0x0118 }
            r7[r10] = r11     // Catch:{ InstantiationException -> 0x011e, IllegalAccessException -> 0x011b, InvocationTargetException -> 0x0118 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r8)     // Catch:{ InstantiationException -> 0x011e, IllegalAccessException -> 0x011b, InvocationTargetException -> 0x0118 }
            r7[r8] = r11     // Catch:{ InstantiationException -> 0x011e, IllegalAccessException -> 0x011b, InvocationTargetException -> 0x0118 }
            r6.newInstance(r7)     // Catch:{ InstantiationException -> 0x011e, IllegalAccessException -> 0x011b, InvocationTargetException -> 0x0118 }
            r6 = r15
            java.lang.reflect.Constructor r6 = (java.lang.reflect.Constructor) r6     // Catch:{ InstantiationException -> 0x011e, IllegalAccessException -> 0x011b, InvocationTargetException -> 0x0118 }
            java.lang.String r7 = "direct buffer constructor: available"
            r0.debug(r7)     // Catch:{ InstantiationException -> 0x011e, IllegalAccessException -> 0x011b, InvocationTargetException -> 0x0118 }
            goto L_0x0120
        L_0x0118:
            r0 = move-exception
            r6 = 0
            goto L_0x0120
        L_0x011b:
            r0 = move-exception
            r6 = 0
            goto L_0x0120
        L_0x011e:
            r0 = move-exception
            r6 = 0
        L_0x0120:
            goto L_0x012f
        L_0x0121:
            r0 = move-exception
            r18 = r1
            goto L_0x0225
        L_0x0126:
            java.lang.String r6 = "direct buffer constructor: unavailable"
            r7 = r15
            java.lang.Throwable r7 = (java.lang.Throwable) r7     // Catch:{ all -> 0x0222 }
            r0.debug((java.lang.String) r6, (java.lang.Throwable) r7)     // Catch:{ all -> 0x0222 }
            r6 = 0
        L_0x012f:
            r15 = -1
            int r0 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r0 == 0) goto L_0x013a
            sun.misc.Unsafe r0 = UNSAFE
            r0.freeMemory(r13)
        L_0x013a:
            DIRECT_BUFFER_CONSTRUCTOR = r6
            long r15 = objectFieldOffset(r1)
            ADDRESS_FIELD_OFFSET = r15
            io.netty.util.internal.PlatformDependent0$5 r0 = new io.netty.util.internal.PlatformDependent0$5
            r0.<init>()
            java.lang.Object r7 = java.security.AccessController.doPrivileged(r0)
            boolean r0 = r7 instanceof java.lang.Boolean
            if (r0 == 0) goto L_0x0163
            r0 = r7
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            io.netty.util.internal.logging.InternalLogger r11 = logger
            java.lang.Boolean r15 = java.lang.Boolean.valueOf(r0)
            java.lang.String r8 = "java.nio.Bits.unaligned: available, {}"
            r11.debug((java.lang.String) r8, (java.lang.Object) r15)
            r8 = r0
            goto L_0x0180
        L_0x0163:
            java.lang.String r0 = "os.arch"
            java.lang.String r8 = ""
            java.lang.String r0 = io.netty.util.internal.SystemPropertyUtil.get(r0, r8)
            java.lang.String r8 = "^(i[3-6]86|x86(_64)?|x64|amd64)$"
            boolean r8 = r0.matches(r8)
            r11 = r7
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            io.netty.util.internal.logging.InternalLogger r15 = logger
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r8)
            java.lang.String r12 = "java.nio.Bits.unaligned: unavailable {}"
            r15.debug(r12, r10, r11)
        L_0x0180:
            UNALIGNED = r8
            long r10 = arrayBaseOffset()
            BYTE_ARRAY_BASE_OFFSET = r10
            int r0 = javaVersion()
            r10 = 9
            if (r0 < r10) goto L_0x0203
            io.netty.util.internal.PlatformDependent0$6 r0 = new io.netty.util.internal.PlatformDependent0$6
            r0.<init>()
            java.lang.Object r0 = java.security.AccessController.doPrivileged(r0)
            boolean r10 = r0 instanceof java.lang.Throwable
            if (r10 != 0) goto L_0x01ea
            r4 = r0
            r10 = r4
            io.netty.util.internal.PlatformDependent0$7 r11 = new io.netty.util.internal.PlatformDependent0$7
            r11.<init>(r10)
            java.lang.Object r11 = java.security.AccessController.doPrivileged(r11)
            boolean r0 = r11 instanceof java.lang.reflect.Method
            if (r0 == 0) goto L_0x01e6
            r0 = r11
            java.lang.reflect.Method r0 = (java.lang.reflect.Method) r0     // Catch:{ IllegalAccessException -> 0x01e1, InvocationTargetException -> 0x01dc }
            r12 = 2
            java.lang.Object[] r12 = new java.lang.Object[r12]     // Catch:{ IllegalAccessException -> 0x01e1, InvocationTargetException -> 0x01dc }
            java.lang.Class r15 = java.lang.Byte.TYPE     // Catch:{ IllegalAccessException -> 0x01e1, InvocationTargetException -> 0x01dc }
            r17 = 0
            r12[r17] = r15     // Catch:{ IllegalAccessException -> 0x01e1, InvocationTargetException -> 0x01dc }
            r15 = 8
            java.lang.Integer r17 = java.lang.Integer.valueOf(r15)     // Catch:{ IllegalAccessException -> 0x01e1, InvocationTargetException -> 0x01dc }
            r16 = 1
            r12[r16] = r17     // Catch:{ IllegalAccessException -> 0x01e1, InvocationTargetException -> 0x01dc }
            java.lang.Object r12 = r0.invoke(r10, r12)     // Catch:{ IllegalAccessException -> 0x01e1, InvocationTargetException -> 0x01dc }
            byte[] r12 = (byte[]) r12     // Catch:{ IllegalAccessException -> 0x01e1, InvocationTargetException -> 0x01dc }
            int r15 = r12.length     // Catch:{ IllegalAccessException -> 0x01e1, InvocationTargetException -> 0x01dc }
            r18 = r1
            r1 = 8
            if (r15 != r1) goto L_0x01d2
            r2 = r0
            r0 = r11
            goto L_0x01ec
        L_0x01d2:
            java.lang.AssertionError r1 = new java.lang.AssertionError     // Catch:{ IllegalAccessException -> 0x01da, InvocationTargetException -> 0x01d8 }
            r1.<init>()     // Catch:{ IllegalAccessException -> 0x01da, InvocationTargetException -> 0x01d8 }
            throw r1     // Catch:{ IllegalAccessException -> 0x01da, InvocationTargetException -> 0x01d8 }
        L_0x01d8:
            r0 = move-exception
            goto L_0x01df
        L_0x01da:
            r0 = move-exception
            goto L_0x01e4
        L_0x01dc:
            r0 = move-exception
            r18 = r1
        L_0x01df:
            r1 = r0
            goto L_0x01ec
        L_0x01e1:
            r0 = move-exception
            r18 = r1
        L_0x01e4:
            goto L_0x01ec
        L_0x01e6:
            r18 = r1
            r0 = r11
            goto L_0x01ec
        L_0x01ea:
            r18 = r1
        L_0x01ec:
            boolean r1 = r0 instanceof java.lang.Throwable
            if (r1 == 0) goto L_0x01fb
            io.netty.util.internal.logging.InternalLogger r1 = logger
            r10 = r0
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.String r11 = "jdk.internal.misc.Unsafe.allocateUninitializedArray(int): unavailable"
            r1.debug((java.lang.String) r11, (java.lang.Throwable) r10)
            goto L_0x0202
        L_0x01fb:
            io.netty.util.internal.logging.InternalLogger r1 = logger
            java.lang.String r10 = "jdk.internal.misc.Unsafe.allocateUninitializedArray(int): available"
            r1.debug(r10)
        L_0x0202:
            goto L_0x020c
        L_0x0203:
            r18 = r1
            io.netty.util.internal.logging.InternalLogger r0 = logger
            java.lang.String r1 = "jdk.internal.misc.Unsafe.allocateUninitializedArray(int): unavailable prior to Java9"
            r0.debug(r1)
        L_0x020c:
            ALLOCATE_ARRAY_METHOD = r2
        L_0x020e:
            INTERNAL_UNSAFE = r4
            io.netty.util.internal.logging.InternalLogger r0 = logger
            java.lang.reflect.Constructor<?> r1 = DIRECT_BUFFER_CONSTRUCTOR
            if (r1 == 0) goto L_0x0219
            java.lang.String r1 = "available"
            goto L_0x021c
        L_0x0219:
            java.lang.String r1 = "unavailable"
        L_0x021c:
            java.lang.String r6 = "java.nio.DirectByteBuffer.<init>(long, int): {}"
            r0.debug((java.lang.String) r6, (java.lang.Object) r1)
            return
        L_0x0222:
            r0 = move-exception
            r18 = r1
        L_0x0225:
            r6 = -1
            int r1 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1))
            if (r1 == 0) goto L_0x0230
            sun.misc.Unsafe r1 = UNSAFE
            r1.freeMemory(r13)
        L_0x0230:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.PlatformDependent0.<clinit>():void");
    }

    static boolean isExplicitNoUnsafe() {
        return IS_EXPLICIT_NO_UNSAFE;
    }

    private static boolean explicitNoUnsafe0() {
        boolean tryUnsafe;
        boolean noUnsafe = SystemPropertyUtil.getBoolean("io.netty.noUnsafe", false);
        InternalLogger internalLogger = logger;
        internalLogger.debug("-Dio.netty.noUnsafe: {}", (Object) Boolean.valueOf(noUnsafe));
        if (noUnsafe) {
            internalLogger.debug("sun.misc.Unsafe: unavailable (io.netty.noUnsafe)");
            return true;
        }
        if (SystemPropertyUtil.contains("io.netty.tryUnsafe")) {
            tryUnsafe = SystemPropertyUtil.getBoolean("io.netty.tryUnsafe", true);
        } else {
            tryUnsafe = SystemPropertyUtil.getBoolean("org.jboss.netty.tryUnsafe", true);
        }
        if (tryUnsafe) {
            return false;
        }
        internalLogger.debug("sun.misc.Unsafe: unavailable (io.netty.tryUnsafe/org.jboss.netty.tryUnsafe)");
        return true;
    }

    static boolean isUnaligned() {
        return UNALIGNED;
    }

    static boolean hasUnsafe() {
        return UNSAFE != null;
    }

    static Throwable getUnsafeUnavailabilityCause() {
        return UNSAFE_UNAVAILABILITY_CAUSE;
    }

    static void throwException(Throwable cause) {
        UNSAFE.throwException((Throwable) ObjectUtil.checkNotNull(cause, "cause"));
    }

    static boolean hasDirectBufferNoCleanerConstructor() {
        return DIRECT_BUFFER_CONSTRUCTOR != null;
    }

    static ByteBuffer reallocateDirectNoCleaner(ByteBuffer buffer, int capacity) {
        return newDirectBuffer(UNSAFE.reallocateMemory(directBufferAddress(buffer), (long) capacity), capacity);
    }

    static ByteBuffer allocateDirectNoCleaner(int capacity) {
        return newDirectBuffer(UNSAFE.allocateMemory((long) capacity), capacity);
    }

    static boolean hasAllocateArrayMethod() {
        return ALLOCATE_ARRAY_METHOD != null;
    }

    static byte[] allocateUninitializedArray(int size) {
        try {
            return (byte[]) ALLOCATE_ARRAY_METHOD.invoke(INTERNAL_UNSAFE, new Object[]{Byte.TYPE, Integer.valueOf(size)});
        } catch (IllegalAccessException e) {
            throw new Error(e);
        } catch (InvocationTargetException e2) {
            throw new Error(e2);
        }
    }

    static ByteBuffer newDirectBuffer(long address, int capacity) {
        ObjectUtil.checkPositiveOrZero(capacity, "capacity");
        try {
            return (ByteBuffer) DIRECT_BUFFER_CONSTRUCTOR.newInstance(new Object[]{Long.valueOf(address), Integer.valueOf(capacity)});
        } catch (Throwable th) {
            if (th instanceof Error) {
                throw th;
            }
            throw new Error(th);
        }
    }

    static long directBufferAddress(ByteBuffer buffer) {
        return getLong((Object) buffer, ADDRESS_FIELD_OFFSET);
    }

    static long arrayBaseOffset() {
        return (long) UNSAFE.arrayBaseOffset(byte[].class);
    }

    static Object getObject(Object object, long fieldOffset) {
        return UNSAFE.getObject(object, fieldOffset);
    }

    static int getInt(Object object, long fieldOffset) {
        return UNSAFE.getInt(object, fieldOffset);
    }

    private static long getLong(Object object, long fieldOffset) {
        return UNSAFE.getLong(object, fieldOffset);
    }

    static long objectFieldOffset(Field field) {
        return UNSAFE.objectFieldOffset(field);
    }

    static byte getByte(long address) {
        return UNSAFE.getByte(address);
    }

    static short getShort(long address) {
        return UNSAFE.getShort(address);
    }

    static int getInt(long address) {
        return UNSAFE.getInt(address);
    }

    static long getLong(long address) {
        return UNSAFE.getLong(address);
    }

    static byte getByte(byte[] data, int index) {
        return UNSAFE.getByte(data, BYTE_ARRAY_BASE_OFFSET + ((long) index));
    }

    static short getShort(byte[] data, int index) {
        return UNSAFE.getShort(data, BYTE_ARRAY_BASE_OFFSET + ((long) index));
    }

    static int getInt(byte[] data, int index) {
        return UNSAFE.getInt(data, BYTE_ARRAY_BASE_OFFSET + ((long) index));
    }

    static long getLong(byte[] data, int index) {
        return UNSAFE.getLong(data, BYTE_ARRAY_BASE_OFFSET + ((long) index));
    }

    static void putByte(long address, byte value) {
        UNSAFE.putByte(address, value);
    }

    static void putShort(long address, short value) {
        UNSAFE.putShort(address, value);
    }

    static void putInt(long address, int value) {
        UNSAFE.putInt(address, value);
    }

    static void putLong(long address, long value) {
        UNSAFE.putLong(address, value);
    }

    static void putByte(byte[] data, int index, byte value) {
        UNSAFE.putByte(data, BYTE_ARRAY_BASE_OFFSET + ((long) index), value);
    }

    static void putShort(byte[] data, int index, short value) {
        UNSAFE.putShort(data, BYTE_ARRAY_BASE_OFFSET + ((long) index), value);
    }

    static void putInt(byte[] data, int index, int value) {
        UNSAFE.putInt(data, BYTE_ARRAY_BASE_OFFSET + ((long) index), value);
    }

    static void putLong(byte[] data, int index, long value) {
        UNSAFE.putLong(data, BYTE_ARRAY_BASE_OFFSET + ((long) index), value);
    }

    static void copyMemory(long srcAddr, long dstAddr, long length) {
        while (length > 0) {
            long size = Math.min(length, 1048576);
            UNSAFE.copyMemory(srcAddr, dstAddr, size);
            length -= size;
            srcAddr += size;
            dstAddr += size;
        }
    }

    static void copyMemory(Object src, long srcOffset, Object dst, long dstOffset, long length) {
        long srcOffset2 = srcOffset;
        long dstOffset2 = dstOffset;
        long length2 = length;
        while (length2 > 0) {
            long size = Math.min(length2, 1048576);
            UNSAFE.copyMemory(src, srcOffset2, dst, dstOffset2, size);
            length2 -= size;
            srcOffset2 += size;
            dstOffset2 += size;
        }
    }

    static void setMemory(long address, long bytes, byte value) {
        UNSAFE.setMemory(address, bytes, value);
    }

    static void setMemory(Object o, long offset, long bytes, byte value) {
        UNSAFE.setMemory(o, offset, bytes, value);
    }

    static boolean isZero(byte[] bytes, int startPos, int length) {
        byte[] bArr = bytes;
        int i = startPos;
        int i2 = length;
        if (i2 <= 0) {
            return true;
        }
        long baseOffset = BYTE_ARRAY_BASE_OFFSET + ((long) i);
        int remainingBytes = i2 & 7;
        long end = ((long) remainingBytes) + baseOffset;
        for (long i3 = (baseOffset - 8) + ((long) i2); i3 >= end; i3 -= 8) {
            if (UNSAFE.getLong(bArr, i3) != 0) {
                return false;
            }
        }
        if (remainingBytes >= 4) {
            remainingBytes -= 4;
            if (UNSAFE.getInt(bArr, ((long) remainingBytes) + baseOffset) != 0) {
                return false;
            }
        }
        if (remainingBytes >= 2) {
            if (UNSAFE.getChar(bArr, baseOffset) == 0 && (remainingBytes == 2 || bArr[i + 2] == 0)) {
                return true;
            }
            return false;
        } else if (bArr[i] == 0) {
            return true;
        } else {
            return false;
        }
    }

    static ClassLoader getClassLoader(final Class<?> clazz) {
        if (System.getSecurityManager() == null) {
            return clazz.getClassLoader();
        }
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            public ClassLoader run() {
                return clazz.getClassLoader();
            }
        });
    }

    static ClassLoader getContextClassLoader() {
        if (System.getSecurityManager() == null) {
            return Thread.currentThread().getContextClassLoader();
        }
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            public ClassLoader run() {
                return Thread.currentThread().getContextClassLoader();
            }
        });
    }

    static ClassLoader getSystemClassLoader() {
        if (System.getSecurityManager() == null) {
            return ClassLoader.getSystemClassLoader();
        }
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            public ClassLoader run() {
                return ClassLoader.getSystemClassLoader();
            }
        });
    }

    static int addressSize() {
        return UNSAFE.addressSize();
    }

    static long allocateMemory(long size) {
        return UNSAFE.allocateMemory(size);
    }

    static void freeMemory(long address) {
        UNSAFE.freeMemory(address);
    }

    static long reallocateMemory(long address, long newSize) {
        return UNSAFE.reallocateMemory(address, newSize);
    }

    static boolean isAndroid() {
        return IS_ANDROID;
    }

    private static boolean isAndroid0() {
        boolean android2;
        try {
            Class.forName("android.app.Application", false, getSystemClassLoader());
            android2 = true;
        } catch (Throwable th) {
            android2 = false;
        }
        if (android2) {
            logger.debug("Platform: Android");
        }
        return android2;
    }

    private static boolean explicitTryReflectionSetAccessible0() {
        return SystemPropertyUtil.getBoolean("io.netty.tryReflectionSetAccessible", javaVersion() < 9);
    }

    static boolean isExplicitTryReflectionSetAccessible() {
        return IS_EXPLICIT_TRY_REFLECTION_SET_ACCESSIBLE;
    }

    static int javaVersion() {
        return JAVA_VERSION;
    }

    private static int javaVersion0() {
        int majorVersion;
        if (isAndroid0()) {
            majorVersion = 6;
        } else {
            majorVersion = majorVersionFromJavaSpecificationVersion();
        }
        logger.debug("Java version: {}", (Object) Integer.valueOf(majorVersion));
        return majorVersion;
    }

    static int majorVersionFromJavaSpecificationVersion() {
        return majorVersion(SystemPropertyUtil.get("java.specification.version", "1.6"));
    }

    static int majorVersion(String javaSpecVersion) {
        String[] components = javaSpecVersion.split("\\.");
        int[] version = new int[components.length];
        for (int i = 0; i < components.length; i++) {
            version[i] = Integer.parseInt(components[i]);
        }
        if (version[0] != 1) {
            return version[0];
        }
        if (version[1] >= 6) {
            return version[1];
        }
        throw new AssertionError();
    }

    private PlatformDependent0() {
    }
}
