package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public final class CleanerJava6 implements Cleaner {
    private static final long CLEANER_FIELD_OFFSET;
    private static final Method CLEAN_METHOD;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) CleanerJava6.class);

    CleanerJava6() {
    }

    static {
        long fieldOffset = -1;
        Method clean = null;
        Throwable error = null;
        if (PlatformDependent0.hasUnsafe()) {
            ByteBuffer direct = ByteBuffer.allocateDirect(1);
            try {
                fieldOffset = PlatformDependent0.objectFieldOffset(direct.getClass().getDeclaredField("cleaner"));
                Object cleaner = PlatformDependent0.getObject(direct, fieldOffset);
                clean = cleaner.getClass().getDeclaredMethod("clean", new Class[0]);
                clean.invoke(cleaner, new Object[0]);
            } catch (Throwable t) {
                fieldOffset = -1;
                clean = null;
                error = t;
            }
        } else {
            error = new UnsupportedOperationException("sun.misc.Unsafe unavailable");
        }
        if (error == null) {
            logger.debug("java.nio.ByteBuffer.cleaner(): available");
        } else {
            logger.debug("java.nio.ByteBuffer.cleaner(): unavailable", error);
        }
        CLEANER_FIELD_OFFSET = fieldOffset;
        CLEAN_METHOD = clean;
    }

    static boolean isSupported() {
        return CLEANER_FIELD_OFFSET != -1;
    }

    public void freeDirectBuffer(ByteBuffer buffer) {
        if (buffer.isDirect()) {
            try {
                Object cleaner = PlatformDependent0.getObject(buffer, CLEANER_FIELD_OFFSET);
                if (cleaner != null) {
                    CLEAN_METHOD.invoke(cleaner, new Object[0]);
                }
            } catch (Throwable cause) {
                PlatformDependent0.throwException(cause);
            }
        }
    }
}
