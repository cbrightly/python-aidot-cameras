package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public final class CleanerJava9 implements Cleaner {
    private static final Method INVOKE_CLEANER;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) CleanerJava9.class);

    CleanerJava9() {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: java.lang.reflect.Method} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: java.lang.Throwable} */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            java.lang.Class<io.netty.util.internal.CleanerJava9> r0 = io.netty.util.internal.CleanerJava9.class
            io.netty.util.internal.logging.InternalLogger r0 = io.netty.util.internal.logging.InternalLoggerFactory.getInstance((java.lang.Class<?>) r0)
            logger = r0
            boolean r0 = io.netty.util.internal.PlatformDependent0.hasUnsafe()
            if (r0 == 0) goto L_0x0046
            r0 = 1
            java.nio.ByteBuffer r1 = java.nio.ByteBuffer.allocateDirect(r0)
            sun.misc.Unsafe r2 = io.netty.util.internal.PlatformDependent0.UNSAFE     // Catch:{ NoSuchMethodException -> 0x0035, InvocationTargetException -> 0x0032, IllegalAccessException -> 0x002f }
            java.lang.Class r3 = r2.getClass()     // Catch:{ NoSuchMethodException -> 0x0035, InvocationTargetException -> 0x0032, IllegalAccessException -> 0x002f }
            java.lang.String r4 = "invokeCleaner"
            java.lang.Class[] r5 = new java.lang.Class[r0]     // Catch:{ NoSuchMethodException -> 0x0035, InvocationTargetException -> 0x0032, IllegalAccessException -> 0x002f }
            java.lang.Class<java.nio.ByteBuffer> r6 = java.nio.ByteBuffer.class
            r7 = 0
            r5[r7] = r6     // Catch:{ NoSuchMethodException -> 0x0035, InvocationTargetException -> 0x0032, IllegalAccessException -> 0x002f }
            java.lang.reflect.Method r3 = r3.getDeclaredMethod(r4, r5)     // Catch:{ NoSuchMethodException -> 0x0035, InvocationTargetException -> 0x0032, IllegalAccessException -> 0x002f }
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ NoSuchMethodException -> 0x0035, InvocationTargetException -> 0x0032, IllegalAccessException -> 0x002f }
            r0[r7] = r1     // Catch:{ NoSuchMethodException -> 0x0035, InvocationTargetException -> 0x0032, IllegalAccessException -> 0x002f }
            r3.invoke(r2, r0)     // Catch:{ NoSuchMethodException -> 0x0035, InvocationTargetException -> 0x0032, IllegalAccessException -> 0x002f }
            r0 = r3
            goto L_0x0037
        L_0x002f:
            r0 = move-exception
            r2 = r0
            goto L_0x0038
        L_0x0032:
            r0 = move-exception
            goto L_0x0037
        L_0x0035:
            r0 = move-exception
        L_0x0037:
        L_0x0038:
            boolean r2 = r0 instanceof java.lang.Throwable
            if (r2 == 0) goto L_0x0041
            r2 = 0
            r3 = r0
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            goto L_0x0045
        L_0x0041:
            r2 = r0
            java.lang.reflect.Method r2 = (java.lang.reflect.Method) r2
            r3 = 0
        L_0x0045:
            goto L_0x0050
        L_0x0046:
            r2 = 0
            java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
            java.lang.String r1 = "sun.misc.Unsafe unavailable"
            r0.<init>(r1)
            r3 = r0
        L_0x0050:
            if (r3 != 0) goto L_0x005a
            io.netty.util.internal.logging.InternalLogger r0 = logger
            java.lang.String r1 = "java.nio.ByteBuffer.cleaner(): available"
            r0.debug(r1)
            goto L_0x0061
        L_0x005a:
            io.netty.util.internal.logging.InternalLogger r0 = logger
            java.lang.String r1 = "java.nio.ByteBuffer.cleaner(): unavailable"
            r0.debug((java.lang.String) r1, (java.lang.Throwable) r3)
        L_0x0061:
            INVOKE_CLEANER = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.CleanerJava9.<clinit>():void");
    }

    static boolean isSupported() {
        return INVOKE_CLEANER != null;
    }

    public void freeDirectBuffer(ByteBuffer buffer) {
        try {
            INVOKE_CLEANER.invoke(PlatformDependent0.UNSAFE, new Object[]{buffer});
        } catch (Throwable cause) {
            PlatformDependent0.throwException(cause);
        }
    }
}
