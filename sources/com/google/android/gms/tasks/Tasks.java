package com.google.android.gms.tasks;

import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.tasks.zza;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: com.google.android.gms:play-services-tasks@@18.0.2 */
public final class Tasks {
    private Tasks() {
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task) {
        Preconditions.checkNotMainThread();
        Preconditions.checkNotNull(task, "Task must not be null");
        if (task.isComplete()) {
            return zza(task);
        }
        zzad zzad = new zzad((zzac) null);
        zzb(task, zzad);
        zzad.zza();
        return zza(task);
    }

    @NonNull
    @Deprecated
    public static <TResult> Task<TResult> call(@NonNull Callable<TResult> callable) {
        return call(TaskExecutors.MAIN_THREAD, callable);
    }

    @NonNull
    public static <TResult> Task<TResult> forCanceled() {
        zzw zzw = new zzw();
        zzw.zzc();
        return zzw;
    }

    @NonNull
    public static <TResult> Task<TResult> forException(@NonNull Exception e) {
        zzw zzw = new zzw();
        zzw.zza(e);
        return zzw;
    }

    @NonNull
    public static <TResult> Task<TResult> forResult(TResult result) {
        zzw zzw = new zzw();
        zzw.zzb(result);
        return zzw;
    }

    @NonNull
    public static Task<Void> whenAll(@Nullable Collection<? extends Task<?>> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return forResult((Object) null);
        }
        for (Task task : tasks) {
            if (task == null) {
                throw new NullPointerException("null tasks are not accepted");
            }
        }
        zzw zzw = new zzw();
        zzaf zzaf = new zzaf(tasks.size(), zzw);
        for (Task zzb : tasks) {
            zzb(zzb, zzaf);
        }
        return zzw;
    }

    @NonNull
    public static Task<List<Task<?>>> whenAllComplete(@Nullable Collection<? extends Task<?>> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return forResult(Collections.emptyList());
        }
        return whenAll(tasks).continueWithTask(TaskExecutors.MAIN_THREAD, new zzab(tasks));
    }

    @NonNull
    public static <TResult> Task<List<TResult>> whenAllSuccess(@Nullable Collection<? extends Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return forResult(Collections.emptyList());
        }
        return whenAll((Collection<? extends Task<?>>) tasks).continueWith(TaskExecutors.MAIN_THREAD, new zzaa(tasks));
    }

    @NonNull
    public static <T> Task<T> withTimeout(@NonNull Task<T> task, long timeout, @NonNull TimeUnit unit) {
        boolean z;
        Preconditions.checkNotNull(task, "Task must not be null");
        if (timeout > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Timeout must be positive");
        Preconditions.checkNotNull(unit, "TimeUnit must not be null");
        zzb zzb = new zzb();
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource(zzb);
        zza zza = new zza(Looper.getMainLooper());
        zza.postDelayed(new zzx(taskCompletionSource), unit.toMillis(timeout));
        task.addOnCompleteListener(new zzy(zza, taskCompletionSource, zzb));
        return taskCompletionSource.getTask();
    }

    private static Object zza(@NonNull Task task) {
        if (task.isSuccessful()) {
            return task.getResult();
        }
        if (task.isCanceled()) {
            throw new CancellationException("Task is already canceled");
        }
        throw new ExecutionException(task.getException());
    }

    private static void zzb(Task task, zzae zzae) {
        Executor executor = TaskExecutors.zza;
        task.addOnSuccessListener(executor, zzae);
        task.addOnFailureListener(executor, (OnFailureListener) zzae);
        task.addOnCanceledListener(executor, (OnCanceledListener) zzae);
    }

    @NonNull
    @Deprecated
    public static <TResult> Task<TResult> call(@NonNull Executor executor, @NonNull Callable<TResult> callable) {
        Preconditions.checkNotNull(executor, "Executor must not be null");
        Preconditions.checkNotNull(callable, "Callback must not be null");
        zzw zzw = new zzw();
        executor.execute(new zzz(zzw, callable));
        return zzw;
    }

    @NonNull
    public static Task<List<Task<?>>> whenAllComplete(@Nullable Task<?>... tasks) {
        if (tasks == null || tasks.length == 0) {
            return forResult(Collections.emptyList());
        }
        return whenAllComplete((Collection<? extends Task<?>>) Arrays.asList(tasks));
    }

    @NonNull
    public static <TResult> Task<List<TResult>> whenAllSuccess(@Nullable Task... tasks) {
        if (tasks == null || tasks.length == 0) {
            return forResult(Collections.emptyList());
        }
        return whenAllSuccess((Collection<? extends Task>) Arrays.asList(tasks));
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task, long timeout, @NonNull TimeUnit unit) {
        Preconditions.checkNotMainThread();
        Preconditions.checkNotNull(task, "Task must not be null");
        Preconditions.checkNotNull(unit, "TimeUnit must not be null");
        if (task.isComplete()) {
            return zza(task);
        }
        zzad zzad = new zzad((zzac) null);
        zzb(task, zzad);
        if (zzad.zzb(timeout, unit)) {
            return zza(task);
        }
        throw new TimeoutException("Timed out waiting for Task");
    }

    @NonNull
    public static Task<Void> whenAll(@Nullable Task<?>... tasks) {
        if (tasks == null || tasks.length == 0) {
            return forResult((Object) null);
        }
        return whenAll((Collection<? extends Task<?>>) Arrays.asList(tasks));
    }
}
