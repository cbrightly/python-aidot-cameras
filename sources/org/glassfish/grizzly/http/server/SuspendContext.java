package org.glassfish.grizzly.http.server;

import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.CompletionHandler;

public interface SuspendContext {
    CompletionHandler<Response> getCompletionHandler();

    long getTimeout(TimeUnit timeUnit);

    TimeoutHandler getTimeoutHandler();

    boolean isSuspended();

    void setTimeout(long j, TimeUnit timeUnit);
}
