package androidx.camera.core.impl;

import android.os.SystemClock;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.impl.Observable;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public final class LiveDataObservable<T> implements Observable<T> {
    final MutableLiveData<Result<T>> mLiveData = new MutableLiveData<>();
    @GuardedBy("mObservers")
    private final Map<Observable.Observer<? super T>, LiveDataObserverAdapter<T>> mObservers = new HashMap();

    public void postValue(@Nullable T value) {
        this.mLiveData.postValue(Result.fromValue(value));
    }

    public void postError(@NonNull Throwable error) {
        this.mLiveData.postValue(Result.fromError(error));
    }

    @NonNull
    public LiveData<Result<T>> getLiveData() {
        return this.mLiveData;
    }

    @NonNull
    public ListenableFuture<T> fetchData() {
        return CallbackToFutureAdapter.getFuture(new p(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$fetchData$1 */
    public /* synthetic */ Object c(CallbackToFutureAdapter.Completer completer) {
        CameraXExecutors.mainThreadExecutor().execute(new o(this, completer));
        return this + " [fetch@" + SystemClock.uptimeMillis() + "]";
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$fetchData$0 */
    public /* synthetic */ void b(CallbackToFutureAdapter.Completer completer) {
        Result<T> result = this.mLiveData.getValue();
        if (result == null) {
            completer.setException(new IllegalStateException("Observable has not yet been initialized with a value."));
        } else if (result.completedSuccessfully()) {
            completer.set(result.getValue());
        } else {
            Preconditions.checkNotNull(result.getError());
            completer.setException(result.getError());
        }
    }

    public void addObserver(@NonNull Executor executor, @NonNull Observable.Observer<? super T> observer) {
        synchronized (this.mObservers) {
            LiveDataObserverAdapter<T> oldAdapter = this.mObservers.get(observer);
            if (oldAdapter != null) {
                oldAdapter.disable();
            }
            LiveDataObserverAdapter<T> newAdapter = new LiveDataObserverAdapter<>(executor, observer);
            this.mObservers.put(observer, newAdapter);
            CameraXExecutors.mainThreadExecutor().execute(new n(this, oldAdapter, newAdapter));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$addObserver$2 */
    public /* synthetic */ void a(LiveDataObserverAdapter oldAdapter, LiveDataObserverAdapter newAdapter) {
        if (oldAdapter != null) {
            this.mLiveData.removeObserver(oldAdapter);
        }
        this.mLiveData.observeForever(newAdapter);
    }

    public void removeObserver(@NonNull Observable.Observer<? super T> observer) {
        synchronized (this.mObservers) {
            LiveDataObserverAdapter<T> adapter = this.mObservers.remove(observer);
            if (adapter != null) {
                adapter.disable();
                CameraXExecutors.mainThreadExecutor().execute(new l(this, adapter));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$removeObserver$3 */
    public /* synthetic */ void d(LiveDataObserverAdapter adapter) {
        this.mLiveData.removeObserver(adapter);
    }

    public static final class Result<T> {
        @Nullable
        private final Throwable mError;
        @Nullable
        private final T mValue;

        private Result(@Nullable T value, @Nullable Throwable error) {
            this.mValue = value;
            this.mError = error;
        }

        static <T> Result<T> fromValue(@Nullable T value) {
            return new Result<>(value, (Throwable) null);
        }

        static <T> Result<T> fromError(@NonNull Throwable error) {
            return new Result<>((Object) null, (Throwable) Preconditions.checkNotNull(error));
        }

        public boolean completedSuccessfully() {
            return this.mError == null;
        }

        @Nullable
        public T getValue() {
            if (completedSuccessfully()) {
                return this.mValue;
            }
            throw new IllegalStateException("Result contains an error. Does not contain a value.");
        }

        @Nullable
        public Throwable getError() {
            return this.mError;
        }

        @NonNull
        public String toString() {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append("[Result: <");
            if (completedSuccessfully()) {
                str = "Value: " + this.mValue;
            } else {
                str = "Error: " + this.mError;
            }
            sb.append(str);
            sb.append(">]");
            return sb.toString();
        }
    }

    public static final class LiveDataObserverAdapter<T> implements Observer<Result<T>> {
        final AtomicBoolean mActive = new AtomicBoolean(true);
        final Executor mExecutor;
        final Observable.Observer<? super T> mObserver;

        LiveDataObserverAdapter(@NonNull Executor executor, @NonNull Observable.Observer<? super T> observer) {
            this.mExecutor = executor;
            this.mObserver = observer;
        }

        /* access modifiers changed from: package-private */
        public void disable() {
            this.mActive.set(false);
        }

        public void onChanged(@NonNull Result<T> result) {
            this.mExecutor.execute(new m(this, result));
        }

        /* access modifiers changed from: private */
        /* renamed from: lambda$onChanged$0 */
        public /* synthetic */ void a(Result result) {
            if (this.mActive.get()) {
                if (result.completedSuccessfully()) {
                    this.mObserver.onNewData(result.getValue());
                    return;
                }
                Preconditions.checkNotNull(result.getError());
                this.mObserver.onError(result.getError());
            }
        }
    }
}
