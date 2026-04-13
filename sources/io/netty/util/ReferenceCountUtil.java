package io.netty.util;

import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public final class ReferenceCountUtil {
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) ReferenceCountUtil.class);

    public static <T> T retain(T msg) {
        if (msg instanceof ReferenceCounted) {
            return ((ReferenceCounted) msg).retain();
        }
        return msg;
    }

    public static <T> T retain(T msg, int increment) {
        if (msg instanceof ReferenceCounted) {
            return ((ReferenceCounted) msg).retain(increment);
        }
        return msg;
    }

    public static boolean release(Object msg) {
        if (msg instanceof ReferenceCounted) {
            return ((ReferenceCounted) msg).release();
        }
        return false;
    }

    public static boolean release(Object msg, int decrement) {
        if (msg instanceof ReferenceCounted) {
            return ((ReferenceCounted) msg).release(decrement);
        }
        return false;
    }

    public static void safeRelease(Object msg) {
        try {
            release(msg);
        } catch (Throwable t) {
            logger.warn("Failed to release a message: {}", msg, t);
        }
    }

    public static void safeRelease(Object msg, int decrement) {
        try {
            release(msg, decrement);
        } catch (Throwable t) {
            if (logger.isWarnEnabled()) {
                logger.warn("Failed to release a message: {} (decrement: {})", msg, Integer.valueOf(decrement), t);
            }
        }
    }

    @Deprecated
    public static <T> T releaseLater(T msg) {
        return releaseLater(msg, 1);
    }

    @Deprecated
    public static <T> T releaseLater(T msg, int decrement) {
        if (msg instanceof ReferenceCounted) {
            ThreadDeathWatcher.watch(Thread.currentThread(), new ReleasingTask((ReferenceCounted) msg, decrement));
        }
        return msg;
    }

    public static int refCnt(Object msg) {
        if (msg instanceof ReferenceCounted) {
            return ((ReferenceCounted) msg).refCnt();
        }
        return -1;
    }

    public static final class ReleasingTask implements Runnable {
        private final int decrement;
        private final ReferenceCounted obj;

        ReleasingTask(ReferenceCounted obj2, int decrement2) {
            this.obj = obj2;
            this.decrement = decrement2;
        }

        public void run() {
            try {
                if (!this.obj.release(this.decrement)) {
                    ReferenceCountUtil.logger.warn("Non-zero refCnt: {}", (Object) this);
                } else {
                    ReferenceCountUtil.logger.debug("Released: {}", (Object) this);
                }
            } catch (Exception ex) {
                ReferenceCountUtil.logger.warn("Failed to release an object: {}", this.obj, ex);
            }
        }

        public String toString() {
            return StringUtil.simpleClassName((Object) this.obj) + ".release(" + this.decrement + ") refCnt: " + this.obj.refCnt();
        }
    }

    private ReferenceCountUtil() {
    }
}
