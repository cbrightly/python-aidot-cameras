package org.glassfish.grizzly;

import java.util.concurrent.Future;

public interface GrizzlyFuture<R> extends Future<R>, Cacheable {
    void addCompletionHandler(CompletionHandler<R> completionHandler);

    @Deprecated
    void markForRecycle(boolean z);

    void recycle(boolean z);
}
