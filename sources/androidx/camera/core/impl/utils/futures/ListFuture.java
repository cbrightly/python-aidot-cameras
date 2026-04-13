package androidx.camera.core.impl.utils.futures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ListFuture<V> implements ListenableFuture<List<V>> {
    private final boolean mAllMustSucceed;
    @Nullable
    List<? extends ListenableFuture<? extends V>> mFutures;
    @NonNull
    private final AtomicInteger mRemaining;
    @NonNull
    private final ListenableFuture<List<V>> mResult = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver<List<V>>() {
        public Object attachCompleter(@NonNull CallbackToFutureAdapter.Completer<List<V>> completer) {
            Preconditions.checkState(ListFuture.this.mResultNotifier == null, "The result can only set once!");
            ListFuture.this.mResultNotifier = completer;
            return "ListFuture[" + this + "]";
        }
    });
    CallbackToFutureAdapter.Completer<List<V>> mResultNotifier;
    @Nullable
    List<V> mValues;

    ListFuture(@NonNull List<? extends ListenableFuture<? extends V>> futures, boolean allMustSucceed, @NonNull Executor listenerExecutor) {
        this.mFutures = (List) Preconditions.checkNotNull(futures);
        this.mValues = new ArrayList(futures.size());
        this.mAllMustSucceed = allMustSucceed;
        this.mRemaining = new AtomicInteger(futures.size());
        init(listenerExecutor);
    }

    private void init(@NonNull Executor listenerExecutor) {
        addListener(new Runnable() {
            public void run() {
                ListFuture listFuture = ListFuture.this;
                listFuture.mValues = null;
                listFuture.mFutures = null;
            }
        }, CameraXExecutors.directExecutor());
        if (this.mFutures.isEmpty()) {
            this.mResultNotifier.set(new ArrayList(this.mValues));
            return;
        }
        for (int i = 0; i < this.mFutures.size(); i++) {
            this.mValues.add((Object) null);
        }
        List<? extends ListenableFuture<? extends V>> localFutures = this.mFutures;
        for (int i2 = 0; i2 < localFutures.size(); i2++) {
            final ListenableFuture<? extends V> listenable = (ListenableFuture) localFutures.get(i2);
            final int index = i2;
            listenable.addListener(new Runnable() {
                public void run() {
                    ListFuture.this.setOneValue(index, listenable);
                }
            }, listenerExecutor);
        }
    }

    /* access modifiers changed from: package-private */
    public void setOneValue(int index, @NonNull Future<? extends V> future) {
        ArrayList arrayList;
        CallbackToFutureAdapter.Completer<List<V>> completer;
        List<V> localValues = this.mValues;
        if (isDone() || localValues == null) {
            Preconditions.checkState(this.mAllMustSucceed, "Future was done before all dependencies completed");
            return;
        }
        boolean z = true;
        try {
            Preconditions.checkState(future.isDone(), "Tried to set value from future which is not done");
            localValues.set(index, Futures.getUninterruptibly(future));
            int newRemaining = this.mRemaining.decrementAndGet();
            if (newRemaining < 0) {
                z = false;
            }
            Preconditions.checkState(z, "Less than 0 remaining futures");
            if (newRemaining == 0) {
                List<V> localValues2 = this.mValues;
                if (localValues2 != null) {
                    completer = this.mResultNotifier;
                    arrayList = new ArrayList(localValues2);
                    completer.set(arrayList);
                    return;
                }
                Preconditions.checkState(isDone());
            }
        } catch (CancellationException e) {
            if (this.mAllMustSucceed) {
                cancel(false);
            }
            int newRemaining2 = this.mRemaining.decrementAndGet();
            if (newRemaining2 < 0) {
                z = false;
            }
            Preconditions.checkState(z, "Less than 0 remaining futures");
            if (newRemaining2 == 0) {
                List<V> localValues3 = this.mValues;
                if (localValues3 != null) {
                    completer = this.mResultNotifier;
                    arrayList = new ArrayList(localValues3);
                }
            }
        } catch (ExecutionException e2) {
            if (this.mAllMustSucceed) {
                this.mResultNotifier.setException(e2.getCause());
            }
            int newRemaining3 = this.mRemaining.decrementAndGet();
            if (newRemaining3 < 0) {
                z = false;
            }
            Preconditions.checkState(z, "Less than 0 remaining futures");
            if (newRemaining3 == 0) {
                List<V> localValues4 = this.mValues;
                if (localValues4 != null) {
                    completer = this.mResultNotifier;
                    arrayList = new ArrayList(localValues4);
                }
            }
        } catch (RuntimeException e3) {
            if (this.mAllMustSucceed) {
                this.mResultNotifier.setException(e3);
            }
            int newRemaining4 = this.mRemaining.decrementAndGet();
            if (newRemaining4 < 0) {
                z = false;
            }
            Preconditions.checkState(z, "Less than 0 remaining futures");
            if (newRemaining4 == 0) {
                List<V> localValues5 = this.mValues;
                if (localValues5 != null) {
                    completer = this.mResultNotifier;
                    arrayList = new ArrayList(localValues5);
                }
            }
        } catch (Error e4) {
            this.mResultNotifier.setException(e4);
            int newRemaining5 = this.mRemaining.decrementAndGet();
            if (newRemaining5 < 0) {
                z = false;
            }
            Preconditions.checkState(z, "Less than 0 remaining futures");
            if (newRemaining5 == 0) {
                List<V> localValues6 = this.mValues;
                if (localValues6 != null) {
                    completer = this.mResultNotifier;
                    arrayList = new ArrayList(localValues6);
                }
            }
        } catch (Throwable th) {
            int newRemaining6 = this.mRemaining.decrementAndGet();
            if (newRemaining6 < 0) {
                z = false;
            }
            Preconditions.checkState(z, "Less than 0 remaining futures");
            if (newRemaining6 == 0) {
                List<V> localValues7 = this.mValues;
                if (localValues7 != null) {
                    this.mResultNotifier.set(new ArrayList(localValues7));
                } else {
                    Preconditions.checkState(isDone());
                }
            }
            throw th;
        }
    }

    public void addListener(@NonNull Runnable listener, @NonNull Executor executor) {
        this.mResult.addListener(listener, executor);
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        List<? extends ListenableFuture<? extends V>> list = this.mFutures;
        if (list != null) {
            for (ListenableFuture<? extends V> f : list) {
                f.cancel(mayInterruptIfRunning);
            }
        }
        return this.mResult.cancel(mayInterruptIfRunning);
    }

    public boolean isCancelled() {
        return this.mResult.isCancelled();
    }

    public boolean isDone() {
        return this.mResult.isDone();
    }

    @Nullable
    public List<V> get() {
        callAllGets();
        return this.mResult.get();
    }

    public List<V> get(long timeout, @NonNull TimeUnit unit) {
        return this.mResult.get(timeout, unit);
    }

    private void callAllGets() {
        List<? extends ListenableFuture<? extends V>> oldFutures = this.mFutures;
        if (oldFutures != null && !isDone()) {
            for (ListenableFuture<? extends V> future : oldFutures) {
                while (true) {
                    if (!future.isDone()) {
                        try {
                            future.get();
                        } catch (Error e) {
                            throw e;
                        } catch (InterruptedException e2) {
                            throw e2;
                        } catch (Throwable th) {
                            if (this.mAllMustSucceed) {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
