package io.netty.util.concurrent;

import io.netty.util.concurrent.Future;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PromiseNotificationUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class PromiseNotifier<V, F extends Future<V>> implements GenericFutureListener<F> {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) PromiseNotifier.class);
    private final boolean logNotifyFailure;
    private final Promise<? super V>[] promises;

    @SafeVarargs
    public PromiseNotifier(Promise<? super V>... promises2) {
        this(true, promises2);
    }

    @SafeVarargs
    public PromiseNotifier(boolean logNotifyFailure2, Promise<? super V>... promises2) {
        ObjectUtil.checkNotNull(promises2, "promises");
        int length = promises2.length;
        int i = 0;
        while (i < length) {
            if (promises2[i] != null) {
                i++;
            } else {
                throw new IllegalArgumentException("promises contains null Promise");
            }
        }
        this.promises = (Promise[]) promises2.clone();
        this.logNotifyFailure = logNotifyFailure2;
    }

    public void operationComplete(F future) {
        InternalLogger internalLogger = this.logNotifyFailure ? logger : null;
        int i = 0;
        if (future.isSuccess()) {
            V result = future.get();
            Promise<? super V>[] promiseArr = this.promises;
            int length = promiseArr.length;
            while (i < length) {
                PromiseNotificationUtil.trySuccess(promiseArr[i], result, internalLogger);
                i++;
            }
        } else if (future.isCancelled()) {
            Promise<? super V>[] promiseArr2 = this.promises;
            int length2 = promiseArr2.length;
            while (i < length2) {
                PromiseNotificationUtil.tryCancel(promiseArr2[i], internalLogger);
                i++;
            }
        } else {
            Throwable cause = future.cause();
            Promise<? super V>[] promiseArr3 = this.promises;
            int length3 = promiseArr3.length;
            while (i < length3) {
                PromiseNotificationUtil.tryFailure(promiseArr3[i], cause, internalLogger);
                i++;
            }
        }
    }
}
