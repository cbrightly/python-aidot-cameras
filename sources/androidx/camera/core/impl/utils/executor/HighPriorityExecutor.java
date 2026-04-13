package androidx.camera.core.impl.utils.executor;

import androidx.annotation.NonNull;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public final class HighPriorityExecutor implements Executor {
    private static volatile Executor sExecutor;
    private final ExecutorService mHighPriorityService = Executors.newSingleThreadExecutor(new ThreadFactory() {
        private static final String THREAD_NAME = "CameraX-camerax_high_priority";

        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setPriority(10);
            t.setName(THREAD_NAME);
            return t;
        }
    });

    HighPriorityExecutor() {
    }

    static Executor getInstance() {
        if (sExecutor != null) {
            return sExecutor;
        }
        synchronized (HighPriorityExecutor.class) {
            if (sExecutor == null) {
                sExecutor = new HighPriorityExecutor();
            }
        }
        return sExecutor;
    }

    public void execute(@NonNull Runnable command) {
        this.mHighPriorityService.execute(command);
    }
}
