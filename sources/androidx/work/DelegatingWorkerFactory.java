package androidx.work;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DelegatingWorkerFactory extends WorkerFactory {
    private static final String TAG = Logger.tagWithPrefix("DelegatingWkrFctry");
    private final List<WorkerFactory> mFactories = new CopyOnWriteArrayList();

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    @NonNull
    public List<WorkerFactory> getFactories() {
        return this.mFactories;
    }

    public final void addFactory(@NonNull WorkerFactory workerFactory) {
        this.mFactories.add(workerFactory);
    }

    @Nullable
    public final ListenableWorker createWorker(@NonNull Context appContext, @NonNull String workerClassName, @NonNull WorkerParameters workerParameters) {
        for (WorkerFactory factory : this.mFactories) {
            try {
                ListenableWorker worker = factory.createWorker(appContext, workerClassName, workerParameters);
                if (worker != null) {
                    return worker;
                }
            } catch (Throwable throwable) {
                String message = String.format("Unable to instantiate a ListenableWorker (%s)", new Object[]{workerClassName});
                Logger.get().error(TAG, message, throwable);
                throw throwable;
            }
        }
        return null;
    }
}
