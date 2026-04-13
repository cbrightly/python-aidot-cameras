package androidx.work;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

public abstract class WorkerFactory {
    private static final String TAG = Logger.tagWithPrefix("WorkerFactory");

    @Nullable
    public abstract ListenableWorker createWorker(@NonNull Context context, @NonNull String str, @NonNull WorkerParameters workerParameters);

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final ListenableWorker createWorkerWithDefaultFallback(@NonNull Context appContext, @NonNull String workerClassName, @NonNull WorkerParameters workerParameters) {
        ListenableWorker worker = createWorker(appContext, workerClassName, workerParameters);
        if (worker == null) {
            Class<? extends U> cls = null;
            try {
                cls = Class.forName(workerClassName).asSubclass(ListenableWorker.class);
            } catch (Throwable throwable) {
                Logger logger = Logger.get();
                String str = TAG;
                logger.error(str, "Invalid class: " + workerClassName, throwable);
            }
            if (cls != null) {
                try {
                    worker = (ListenableWorker) cls.getDeclaredConstructor(new Class[]{Context.class, WorkerParameters.class}).newInstance(new Object[]{appContext, workerParameters});
                } catch (Throwable e) {
                    Logger logger2 = Logger.get();
                    String str2 = TAG;
                    logger2.error(str2, "Could not instantiate " + workerClassName, e);
                }
            }
        }
        if (worker == null || !worker.isUsed()) {
            return worker;
        }
        throw new IllegalStateException(String.format("WorkerFactory (%s) returned an instance of a ListenableWorker (%s) which has already been invoked. createWorker() must always return a new instance of a ListenableWorker.", new Object[]{getClass().getName(), workerClassName}));
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static WorkerFactory getDefaultWorkerFactory() {
        return new WorkerFactory() {
            @Nullable
            public ListenableWorker createWorker(@NonNull Context appContext, @NonNull String workerClassName, @NonNull WorkerParameters workerParameters) {
                return null;
            }
        };
    }
}
