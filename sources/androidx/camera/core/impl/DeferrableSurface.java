package androidx.camera.core.impl;

import android.util.Log;
import android.view.Surface;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class DeferrableSurface {
    private static final boolean DEBUG = Logger.isDebugEnabled(TAG);
    private static final String TAG = "DeferrableSurface";
    private static final AtomicInteger TOTAL_COUNT = new AtomicInteger(0);
    private static final AtomicInteger USED_COUNT = new AtomicInteger(0);
    @GuardedBy("mLock")
    private boolean mClosed = false;
    private final Object mLock = new Object();
    @GuardedBy("mLock")
    private CallbackToFutureAdapter.Completer<Void> mTerminationCompleter;
    private final ListenableFuture<Void> mTerminationFuture;
    @GuardedBy("mLock")
    private int mUseCount = 0;

    /* access modifiers changed from: protected */
    @NonNull
    public abstract ListenableFuture<Surface> provideSurface();

    public static final class SurfaceUnavailableException extends Exception {
        public SurfaceUnavailableException(@NonNull String message) {
            super(message);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final class SurfaceClosedException extends Exception {
        DeferrableSurface mDeferrableSurface;

        public SurfaceClosedException(@NonNull String s, @NonNull DeferrableSurface surface) {
            super(s);
            this.mDeferrableSurface = surface;
        }

        @NonNull
        public DeferrableSurface getDeferrableSurface() {
            return this.mDeferrableSurface;
        }
    }

    public DeferrableSurface() {
        ListenableFuture<Void> future = CallbackToFutureAdapter.getFuture(new g(this));
        this.mTerminationFuture = future;
        if (Logger.isDebugEnabled(TAG)) {
            printGlobalDebugCounts("Surface created", TOTAL_COUNT.incrementAndGet(), USED_COUNT.get());
            future.addListener(new f(this, Log.getStackTraceString(new Exception())), CameraXExecutors.directExecutor());
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$new$0 */
    public /* synthetic */ Object a(CallbackToFutureAdapter.Completer completer) {
        synchronized (this.mLock) {
            this.mTerminationCompleter = completer;
        }
        return "DeferrableSurface-termination(" + this + ")";
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$new$1 */
    public /* synthetic */ void b(String creationStackTrace) {
        try {
            this.mTerminationFuture.get();
            printGlobalDebugCounts("Surface terminated", TOTAL_COUNT.decrementAndGet(), USED_COUNT.get());
        } catch (Exception e) {
            Logger.e(TAG, "Unexpected surface termination for " + this + "\nStack Trace:\n" + creationStackTrace);
            synchronized (this.mLock) {
                throw new IllegalArgumentException(String.format("DeferrableSurface %s [closed: %b, use_count: %s] terminated with unexpected exception.", new Object[]{this, Boolean.valueOf(this.mClosed), Integer.valueOf(this.mUseCount)}), e);
            }
        }
    }

    private void printGlobalDebugCounts(@NonNull String prefix, int totalCount, int useCount) {
        if (!DEBUG && Logger.isDebugEnabled(TAG)) {
            Logger.d(TAG, "DeferrableSurface usage statistics may be inaccurate since debug logging was not enabled at static initialization time. App restart may be required to enable accurate usage statistics.");
        }
        Logger.d(TAG, prefix + "[total_surfaces=" + totalCount + ", used_surfaces=" + useCount + "](" + this + "}");
    }

    @NonNull
    public final ListenableFuture<Surface> getSurface() {
        synchronized (this.mLock) {
            if (this.mClosed) {
                ListenableFuture<Surface> immediateFailedFuture = Futures.immediateFailedFuture(new SurfaceClosedException("DeferrableSurface already closed.", this));
                return immediateFailedFuture;
            }
            ListenableFuture<Surface> provideSurface = provideSurface();
            return provideSurface;
        }
    }

    @NonNull
    public ListenableFuture<Void> getTerminationFuture() {
        return Futures.nonCancellationPropagating(this.mTerminationFuture);
    }

    public void incrementUseCount() {
        synchronized (this.mLock) {
            int i = this.mUseCount;
            if (i == 0) {
                if (this.mClosed) {
                    throw new SurfaceClosedException("Cannot begin use on a closed surface.", this);
                }
            }
            this.mUseCount = i + 1;
            if (Logger.isDebugEnabled(TAG)) {
                if (this.mUseCount == 1) {
                    printGlobalDebugCounts("New surface in use", TOTAL_COUNT.get(), USED_COUNT.incrementAndGet());
                }
                Logger.d(TAG, "use count+1, useCount=" + this.mUseCount + " " + this);
            }
        }
    }

    public final void close() {
        CallbackToFutureAdapter.Completer<Void> terminationCompleter = null;
        synchronized (this.mLock) {
            if (!this.mClosed) {
                this.mClosed = true;
                if (this.mUseCount == 0) {
                    terminationCompleter = this.mTerminationCompleter;
                    this.mTerminationCompleter = null;
                }
                if (Logger.isDebugEnabled(TAG)) {
                    Logger.d(TAG, "surface closed,  useCount=" + this.mUseCount + " closed=true " + this);
                }
            }
        }
        if (terminationCompleter != null) {
            terminationCompleter.set(null);
        }
    }

    public void decrementUseCount() {
        CallbackToFutureAdapter.Completer<Void> terminationCompleter = null;
        synchronized (this.mLock) {
            int i = this.mUseCount;
            if (i != 0) {
                int i2 = i - 1;
                this.mUseCount = i2;
                if (i2 == 0 && this.mClosed) {
                    terminationCompleter = this.mTerminationCompleter;
                    this.mTerminationCompleter = null;
                }
                if (Logger.isDebugEnabled(TAG)) {
                    Logger.d(TAG, "use count-1,  useCount=" + this.mUseCount + " closed=" + this.mClosed + " " + this);
                    if (this.mUseCount == 0) {
                        printGlobalDebugCounts("Surface no longer in use", TOTAL_COUNT.get(), USED_COUNT.decrementAndGet());
                    }
                }
            } else {
                throw new IllegalStateException("Decrementing use count occurs more times than incrementing");
            }
        }
        if (terminationCompleter != null) {
            terminationCompleter.set(null);
        }
    }

    @RestrictTo({RestrictTo.Scope.TESTS})
    public int getUseCount() {
        int i;
        synchronized (this.mLock) {
            i = this.mUseCount;
        }
        return i;
    }
}
