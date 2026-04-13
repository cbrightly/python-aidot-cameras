package org.glassfish.grizzly.http.server;

import java.util.concurrent.Executor;
import org.glassfish.grizzly.threadpool.Threads;

public interface RequestExecutorProvider {
    Executor getExecutor(Request request);

    public static class SameThreadProvider implements RequestExecutorProvider {
        public Executor getExecutor(Request request) {
            return null;
        }
    }

    public static class WorkerThreadProvider implements RequestExecutorProvider {
        public Executor getExecutor(Request request) {
            if (!Threads.isService()) {
                return null;
            }
            return request.getContext().getConnection().getTransport().getWorkerThreadPool();
        }
    }
}
