package androidx.camera.core.impl;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.impl.Observable;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.core.util.Preconditions;
import com.google.auto.value.AutoValue;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public abstract class StateObservable<T> implements Observable<T> {
    private static final int INITIAL_VERSION = 0;
    private final Object mLock = new Object();
    @GuardedBy("mLock")
    private final CopyOnWriteArraySet<ObserverWrapper<T>> mNotifySet = new CopyOnWriteArraySet<>();
    private final AtomicReference<Object> mState;
    @GuardedBy("mLock")
    private boolean mUpdating = false;
    @GuardedBy("mLock")
    private int mVersion = 0;
    @GuardedBy("mLock")
    private final Map<Observable.Observer<? super T>, ObserverWrapper<T>> mWrapperMap = new HashMap();

    StateObservable(@Nullable Object initialState, boolean isError) {
        if (isError) {
            Preconditions.checkArgument(initialState instanceof Throwable, "Initial errors must be Throwable");
            this.mState = new AtomicReference<>(ErrorWrapper.wrap((Throwable) initialState));
            return;
        }
        this.mState = new AtomicReference<>(initialState);
    }

    /* access modifiers changed from: package-private */
    public void updateState(@Nullable T state) {
        updateStateInternal(state);
    }

    /* access modifiers changed from: package-private */
    public void updateStateAsError(@NonNull Throwable error) {
        updateStateInternal(ErrorWrapper.wrap(error));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002b, code lost:
        if (r1.hasNext() == false) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002d, code lost:
        r1.next().update(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0037, code lost:
        r3 = r5.mLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0039, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003c, code lost:
        if (r5.mVersion != r2) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003e, code lost:
        r5.mUpdating = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0041, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0042, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0043, code lost:
        r1 = r5.mNotifySet.iterator();
        r2 = r5.mVersion;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004d, code lost:
        monitor-exit(r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateStateInternal(@androidx.annotation.Nullable java.lang.Object r6) {
        /*
            r5 = this;
            java.lang.Object r0 = r5.mLock
            monitor-enter(r0)
            java.util.concurrent.atomic.AtomicReference<java.lang.Object> r1 = r5.mState     // Catch:{ all -> 0x0052 }
            java.lang.Object r1 = r1.getAndSet(r6)     // Catch:{ all -> 0x0052 }
            boolean r2 = java.util.Objects.equals(r1, r6)     // Catch:{ all -> 0x0052 }
            if (r2 == 0) goto L_0x0011
            monitor-exit(r0)     // Catch:{ all -> 0x0052 }
            return
        L_0x0011:
            int r2 = r5.mVersion     // Catch:{ all -> 0x0052 }
            r3 = 1
            int r2 = r2 + r3
            r5.mVersion = r2     // Catch:{ all -> 0x0052 }
            boolean r4 = r5.mUpdating     // Catch:{ all -> 0x0052 }
            if (r4 == 0) goto L_0x001d
            monitor-exit(r0)     // Catch:{ all -> 0x0052 }
            return
        L_0x001d:
            r5.mUpdating = r3     // Catch:{ all -> 0x0052 }
            java.util.concurrent.CopyOnWriteArraySet<androidx.camera.core.impl.StateObservable$ObserverWrapper<T>> r3 = r5.mNotifySet     // Catch:{ all -> 0x0052 }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ all -> 0x0052 }
            r1 = r3
            monitor-exit(r0)     // Catch:{ all -> 0x0052 }
        L_0x0027:
            boolean r0 = r1.hasNext()
            if (r0 == 0) goto L_0x0037
            java.lang.Object r0 = r1.next()
            androidx.camera.core.impl.StateObservable$ObserverWrapper r0 = (androidx.camera.core.impl.StateObservable.ObserverWrapper) r0
            r0.update(r2)
            goto L_0x0027
        L_0x0037:
            java.lang.Object r3 = r5.mLock
            monitor-enter(r3)
            int r0 = r5.mVersion     // Catch:{ all -> 0x004f }
            if (r0 != r2) goto L_0x0043
            r0 = 0
            r5.mUpdating = r0     // Catch:{ all -> 0x004f }
            monitor-exit(r3)     // Catch:{ all -> 0x004f }
            return
        L_0x0043:
            java.util.concurrent.CopyOnWriteArraySet<androidx.camera.core.impl.StateObservable$ObserverWrapper<T>> r0 = r5.mNotifySet     // Catch:{ all -> 0x004f }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x004f }
            r1 = r0
            int r0 = r5.mVersion     // Catch:{ all -> 0x004f }
            r2 = r0
            monitor-exit(r3)     // Catch:{ all -> 0x004f }
            goto L_0x0027
        L_0x004f:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x004f }
            throw r0
        L_0x0052:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0052 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.impl.StateObservable.updateStateInternal(java.lang.Object):void");
    }

    @NonNull
    public ListenableFuture<T> fetchData() {
        Object state = this.mState.get();
        if (state instanceof ErrorWrapper) {
            return Futures.immediateFailedFuture(((ErrorWrapper) state).getError());
        }
        return Futures.immediateFuture(state);
    }

    public void addObserver(@NonNull Executor executor, @NonNull Observable.Observer<? super T> observer) {
        ObserverWrapper<T> wrapper;
        synchronized (this.mLock) {
            removeObserverLocked(observer);
            wrapper = new ObserverWrapper<>(this.mState, executor, observer);
            this.mWrapperMap.put(observer, wrapper);
            this.mNotifySet.add(wrapper);
        }
        wrapper.update(0);
    }

    public void removeObserver(@NonNull Observable.Observer<? super T> observer) {
        synchronized (this.mLock) {
            removeObserverLocked(observer);
        }
    }

    @GuardedBy("mLock")
    private void removeObserverLocked(@NonNull Observable.Observer<? super T> observer) {
        ObserverWrapper<T> wrapper = this.mWrapperMap.remove(observer);
        if (wrapper != null) {
            wrapper.close();
            this.mNotifySet.remove(wrapper);
        }
    }

    public static final class ObserverWrapper<T> implements Runnable {
        private static final Object NOT_SET = new Object();
        private static final int NO_VERSION = -1;
        private final AtomicBoolean mActive = new AtomicBoolean(true);
        private final Executor mExecutor;
        private Object mLastState = NOT_SET;
        @GuardedBy("this")
        private int mLatestSignalledVersion = -1;
        private final Observable.Observer<? super T> mObserver;
        private final AtomicReference<Object> mStateRef;
        @GuardedBy("this")
        private boolean mWrapperUpdating = false;

        ObserverWrapper(@NonNull AtomicReference<Object> stateRef, @NonNull Executor executor, @NonNull Observable.Observer<? super T> observer) {
            this.mStateRef = stateRef;
            this.mExecutor = executor;
            this.mObserver = observer;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x001d, code lost:
            if (java.util.Objects.equals(r5.mLastState, r0) != false) goto L_0x0037;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x001f, code lost:
            r5.mLastState = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0023, code lost:
            if ((r0 instanceof androidx.camera.core.impl.StateObservable.ErrorWrapper) == false) goto L_0x0032;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0025, code lost:
            r5.mObserver.onError(((androidx.camera.core.impl.StateObservable.ErrorWrapper) r0).getError());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0032, code lost:
            r5.mObserver.onNewData(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0037, code lost:
            monitor-enter(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x003a, code lost:
            if (r2 == r5.mLatestSignalledVersion) goto L_0x0051;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0042, code lost:
            if (r5.mActive.get() != false) goto L_0x0045;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0045, code lost:
            r0 = r5.mStateRef.get();
            r2 = r5.mLatestSignalledVersion;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x004f, code lost:
            monitor-exit(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0051, code lost:
            r5.mWrapperUpdating = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0053, code lost:
            monitor-exit(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0054, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r5 = this;
                monitor-enter(r5)
                java.util.concurrent.atomic.AtomicBoolean r0 = r5.mActive     // Catch:{ all -> 0x0058 }
                boolean r0 = r0.get()     // Catch:{ all -> 0x0058 }
                r1 = 0
                if (r0 != 0) goto L_0x000e
                r5.mWrapperUpdating = r1     // Catch:{ all -> 0x0058 }
                monitor-exit(r5)     // Catch:{ all -> 0x0058 }
                return
            L_0x000e:
                java.util.concurrent.atomic.AtomicReference<java.lang.Object> r0 = r5.mStateRef     // Catch:{ all -> 0x0058 }
                java.lang.Object r0 = r0.get()     // Catch:{ all -> 0x0058 }
                int r2 = r5.mLatestSignalledVersion     // Catch:{ all -> 0x0058 }
                monitor-exit(r5)     // Catch:{ all -> 0x0058 }
            L_0x0017:
                java.lang.Object r3 = r5.mLastState
                boolean r3 = java.util.Objects.equals(r3, r0)
                if (r3 != 0) goto L_0x0037
                r5.mLastState = r0
                boolean r3 = r0 instanceof androidx.camera.core.impl.StateObservable.ErrorWrapper
                if (r3 == 0) goto L_0x0032
                androidx.camera.core.impl.Observable$Observer<? super T> r3 = r5.mObserver
                r4 = r0
                androidx.camera.core.impl.StateObservable$ErrorWrapper r4 = (androidx.camera.core.impl.StateObservable.ErrorWrapper) r4
                java.lang.Throwable r4 = r4.getError()
                r3.onError(r4)
                goto L_0x0037
            L_0x0032:
                androidx.camera.core.impl.Observable$Observer<? super T> r3 = r5.mObserver
                r3.onNewData(r0)
            L_0x0037:
                monitor-enter(r5)
                int r3 = r5.mLatestSignalledVersion     // Catch:{ all -> 0x0055 }
                if (r2 == r3) goto L_0x0051
                java.util.concurrent.atomic.AtomicBoolean r3 = r5.mActive     // Catch:{ all -> 0x0055 }
                boolean r3 = r3.get()     // Catch:{ all -> 0x0055 }
                if (r3 != 0) goto L_0x0045
                goto L_0x0051
            L_0x0045:
                java.util.concurrent.atomic.AtomicReference<java.lang.Object> r3 = r5.mStateRef     // Catch:{ all -> 0x0055 }
                java.lang.Object r3 = r3.get()     // Catch:{ all -> 0x0055 }
                r0 = r3
                int r3 = r5.mLatestSignalledVersion     // Catch:{ all -> 0x0055 }
                r2 = r3
                monitor-exit(r5)     // Catch:{ all -> 0x0055 }
                goto L_0x0017
            L_0x0051:
                r5.mWrapperUpdating = r1     // Catch:{ all -> 0x0055 }
                monitor-exit(r5)     // Catch:{ all -> 0x0055 }
                return
            L_0x0055:
                r1 = move-exception
                monitor-exit(r5)     // Catch:{ all -> 0x0055 }
                throw r1
            L_0x0058:
                r0 = move-exception
                monitor-exit(r5)     // Catch:{ all -> 0x0058 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.impl.StateObservable.ObserverWrapper.run():void");
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void update(int r3) {
            /*
                r2 = this;
                monitor-enter(r2)
                java.util.concurrent.atomic.AtomicBoolean r0 = r2.mActive     // Catch:{ all -> 0x002d }
                boolean r0 = r0.get()     // Catch:{ all -> 0x002d }
                if (r0 != 0) goto L_0x000b
                monitor-exit(r2)     // Catch:{ all -> 0x002d }
                return
            L_0x000b:
                int r0 = r2.mLatestSignalledVersion     // Catch:{ all -> 0x002d }
                if (r3 > r0) goto L_0x0011
                monitor-exit(r2)     // Catch:{ all -> 0x002d }
                return
            L_0x0011:
                r2.mLatestSignalledVersion = r3     // Catch:{ all -> 0x002d }
                boolean r0 = r2.mWrapperUpdating     // Catch:{ all -> 0x002d }
                if (r0 == 0) goto L_0x0019
                monitor-exit(r2)     // Catch:{ all -> 0x002d }
                return
            L_0x0019:
                r0 = 1
                r2.mWrapperUpdating = r0     // Catch:{ all -> 0x002d }
                monitor-exit(r2)     // Catch:{ all -> 0x002d }
                java.util.concurrent.Executor r0 = r2.mExecutor     // Catch:{ all -> 0x0023 }
                r0.execute(r2)     // Catch:{ all -> 0x0023 }
                goto L_0x0029
            L_0x0023:
                r0 = move-exception
                monitor-enter(r2)
                r1 = 0
                r2.mWrapperUpdating = r1     // Catch:{ all -> 0x002a }
                monitor-exit(r2)     // Catch:{ all -> 0x002a }
            L_0x0029:
                return
            L_0x002a:
                r1 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x002a }
                throw r1
            L_0x002d:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x002d }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.impl.StateObservable.ObserverWrapper.update(int):void");
        }

        /* access modifiers changed from: package-private */
        public void close() {
            this.mActive.set(false);
        }
    }

    @AutoValue
    public static abstract class ErrorWrapper {
        @NonNull
        public abstract Throwable getError();

        ErrorWrapper() {
        }

        @NonNull
        static ErrorWrapper wrap(@NonNull Throwable error) {
            return new AutoValue_StateObservable_ErrorWrapper(error);
        }
    }
}
