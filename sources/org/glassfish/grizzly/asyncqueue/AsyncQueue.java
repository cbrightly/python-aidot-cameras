package org.glassfish.grizzly.asyncqueue;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.ProcessorResult;

public interface AsyncQueue {
    public static final String EXPECTING_MORE_OPTION = (AsyncQueue.class.getName() + ".expectingMore");

    void close();

    boolean isReady(Connection connection);

    void onClose(Connection connection);

    AsyncResult processAsync(Context context);

    public enum AsyncResult {
        COMPLETE(ProcessorResult.createLeave()),
        INCOMPLETE(ProcessorResult.createComplete()),
        EXPECTING_MORE(ProcessorResult.createComplete(AsyncQueue.EXPECTING_MORE_OPTION)),
        TERMINATE(ProcessorResult.createTerminate());
        
        private final ProcessorResult result;

        private AsyncResult(ProcessorResult result2) {
            this.result = result2;
        }

        public ProcessorResult toProcessorResult() {
            return this.result;
        }
    }
}
