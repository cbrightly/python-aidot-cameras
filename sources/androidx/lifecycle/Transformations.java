package androidx.lifecycle;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;

public class Transformations {
    private Transformations() {
    }

    @MainThread
    @NonNull
    public static <X, Y> LiveData<Y> map(@NonNull LiveData<X> source, @NonNull final Function<X, Y> mapFunction) {
        final MediatorLiveData<Y> result = new MediatorLiveData<>();
        result.addSource(source, new Observer<X>() {
            public void onChanged(@Nullable X x) {
                result.setValue(mapFunction.apply(x));
            }
        });
        return result;
    }

    @MainThread
    @NonNull
    public static <X, Y> LiveData<Y> switchMap(@NonNull LiveData<X> source, @NonNull final Function<X, LiveData<Y>> switchMapFunction) {
        final MediatorLiveData<Y> result = new MediatorLiveData<>();
        result.addSource(source, new Observer<X>() {
            LiveData<Y> mSource;

            public void onChanged(@Nullable X x) {
                LiveData<Y> newLiveData = (LiveData) switchMapFunction.apply(x);
                LiveData<Y> liveData = this.mSource;
                if (liveData != newLiveData) {
                    if (liveData != null) {
                        result.removeSource(liveData);
                    }
                    this.mSource = newLiveData;
                    if (newLiveData != null) {
                        result.addSource(newLiveData, new Observer<Y>() {
                            public void onChanged(@Nullable Y y) {
                                result.setValue(y);
                            }
                        });
                    }
                }
            }
        });
        return result;
    }

    @MainThread
    @NonNull
    public static <X> LiveData<X> distinctUntilChanged(@NonNull LiveData<X> source) {
        final MediatorLiveData<X> outputLiveData = new MediatorLiveData<>();
        outputLiveData.addSource(source, new Observer<X>() {
            boolean mFirstTime = true;

            public void onChanged(X currentValue) {
                X previousValue = outputLiveData.getValue();
                if (this.mFirstTime || ((previousValue == null && currentValue != null) || (previousValue != null && !previousValue.equals(currentValue)))) {
                    this.mFirstTime = false;
                    outputLiveData.setValue(currentValue);
                }
            }
        });
        return outputLiveData;
    }
}
