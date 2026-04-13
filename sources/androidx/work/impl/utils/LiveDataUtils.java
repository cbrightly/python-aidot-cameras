package androidx.work.impl.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class LiveDataUtils {
    public static <In, Out> LiveData<Out> dedupedMappedLiveDataFor(@NonNull LiveData<In> inputLiveData, @NonNull final Function<In, Out> mappingMethod, @NonNull final TaskExecutor workTaskExecutor) {
        final Object lock = new Object();
        final MediatorLiveData<Out> outputLiveData = new MediatorLiveData<>();
        outputLiveData.addSource(inputLiveData, new Observer<In>() {
            Out mCurrentOutput = null;

            public void onChanged(@Nullable final In input) {
                TaskExecutor.this.executeOnBackgroundThread(new Runnable() {
                    public void run() {
                        synchronized (lock) {
                            Out newOutput = mappingMethod.apply(input);
                            AnonymousClass1 r2 = AnonymousClass1.this;
                            Out out = r2.mCurrentOutput;
                            if (out == null && newOutput != null) {
                                r2.mCurrentOutput = newOutput;
                                outputLiveData.postValue(newOutput);
                            } else if (out != null && !out.equals(newOutput)) {
                                AnonymousClass1 r22 = AnonymousClass1.this;
                                r22.mCurrentOutput = newOutput;
                                outputLiveData.postValue(newOutput);
                            }
                        }
                    }
                });
            }
        });
        return outputLiveData;
    }

    private LiveDataUtils() {
    }
}
