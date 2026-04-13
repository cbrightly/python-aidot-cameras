package androidx.work;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.work.ListenableWorker;
import androidx.work.impl.utils.futures.SettableFuture;
import com.google.common.util.concurrent.ListenableFuture;

public abstract class Worker extends ListenableWorker {
    SettableFuture<ListenableWorker.Result> mFuture;

    @WorkerThread
    @NonNull
    public abstract ListenableWorker.Result doWork();

    @SuppressLint({"BanKeepAnnotation"})
    @Keep
    public Worker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    public final ListenableFuture<ListenableWorker.Result> startWork() {
        this.mFuture = SettableFuture.create();
        getBackgroundExecutor().execute(new Runnable() {
            public void run() {
                try {
                    Worker.this.mFuture.set(Worker.this.doWork());
                } catch (Throwable throwable) {
                    Worker.this.mFuture.setException(throwable);
                }
            }
        });
        return this.mFuture;
    }
}
