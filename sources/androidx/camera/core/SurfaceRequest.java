package androidx.camera.core;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import com.google.auto.value.AutoValue;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

public final class SurfaceRequest {
    private final CameraInternal mCamera;
    private final DeferrableSurface mInternalDeferrableSurface;
    private final boolean mRGBA8888Required;
    private final CallbackToFutureAdapter.Completer<Void> mRequestCancellationCompleter;
    private final Size mResolution;
    private final ListenableFuture<Void> mSessionStatusFuture;
    private final CallbackToFutureAdapter.Completer<Surface> mSurfaceCompleter;
    final ListenableFuture<Surface> mSurfaceFuture;
    @Nullable
    private TransformationInfo mTransformationInfo;
    @Nullable
    private Executor mTransformationInfoExecutor;
    @Nullable
    private TransformationInfoListener mTransformationInfoListener;

    @ExperimentalUseCaseGroup
    public interface TransformationInfoListener {
        void onTransformationInfoUpdate(@NonNull TransformationInfo transformationInfo);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public SurfaceRequest(@NonNull Size resolution, @NonNull CameraInternal camera, boolean isRGBA8888Required) {
        this.mResolution = resolution;
        this.mCamera = camera;
        this.mRGBA8888Required = isRGBA8888Required;
        final String surfaceRequestString = "SurfaceRequest[size: " + resolution + ", id: " + hashCode() + "]";
        AtomicReference<CallbackToFutureAdapter.Completer<Void>> cancellationCompleterRef = new AtomicReference<>((Object) null);
        final ListenableFuture<Void> requestCancellationFuture = CallbackToFutureAdapter.getFuture(new n1(cancellationCompleterRef, surfaceRequestString));
        final CallbackToFutureAdapter.Completer<Void> requestCancellationCompleter = (CallbackToFutureAdapter.Completer) Preconditions.checkNotNull(cancellationCompleterRef.get());
        this.mRequestCancellationCompleter = requestCancellationCompleter;
        AtomicReference<CallbackToFutureAdapter.Completer<Void>> sessionStatusCompleterRef = new AtomicReference<>((Object) null);
        ListenableFuture<Void> future = CallbackToFutureAdapter.getFuture(new o1(sessionStatusCompleterRef, surfaceRequestString));
        this.mSessionStatusFuture = future;
        Futures.addCallback(future, new FutureCallback<Void>() {
            public void onSuccess(@Nullable Void result) {
                Preconditions.checkState(requestCancellationCompleter.set(null));
            }

            public void onFailure(Throwable t) {
                if (t instanceof RequestCancelledException) {
                    Preconditions.checkState(requestCancellationFuture.cancel(false));
                } else {
                    Preconditions.checkState(requestCancellationCompleter.set(null));
                }
            }
        }, CameraXExecutors.directExecutor());
        final CallbackToFutureAdapter.Completer<Void> sessionStatusCompleter = (CallbackToFutureAdapter.Completer) Preconditions.checkNotNull(sessionStatusCompleterRef.get());
        AtomicReference<CallbackToFutureAdapter.Completer<Surface>> surfaceCompleterRef = new AtomicReference<>((Object) null);
        ListenableFuture<Surface> future2 = CallbackToFutureAdapter.getFuture(new m1(surfaceCompleterRef, surfaceRequestString));
        this.mSurfaceFuture = future2;
        this.mSurfaceCompleter = (CallbackToFutureAdapter.Completer) Preconditions.checkNotNull(surfaceCompleterRef.get());
        AnonymousClass2 r8 = new DeferrableSurface() {
            /* access modifiers changed from: protected */
            @NonNull
            public ListenableFuture<Surface> provideSurface() {
                return SurfaceRequest.this.mSurfaceFuture;
            }
        };
        this.mInternalDeferrableSurface = r8;
        final ListenableFuture<Void> terminationFuture = r8.getTerminationFuture();
        Futures.addCallback(future2, new FutureCallback<Surface>() {
            public void onSuccess(@Nullable Surface result) {
                Futures.propagate(terminationFuture, sessionStatusCompleter);
            }

            public void onFailure(Throwable t) {
                if (t instanceof CancellationException) {
                    CallbackToFutureAdapter.Completer completer = sessionStatusCompleter;
                    Preconditions.checkState(completer.setException(new RequestCancelledException(surfaceRequestString + " cancelled.", t)));
                    return;
                }
                sessionStatusCompleter.set(null);
            }
        }, CameraXExecutors.directExecutor());
        terminationFuture.addListener(new l1(this), CameraXExecutors.directExecutor());
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$new$3 */
    public /* synthetic */ void a() {
        this.mSurfaceFuture.cancel(true);
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public DeferrableSurface getDeferrableSurface() {
        return this.mInternalDeferrableSurface;
    }

    @NonNull
    public Size getResolution() {
        return this.mResolution;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CameraInternal getCamera() {
        return this.mCamera;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isRGBA8888Required() {
        return this.mRGBA8888Required;
    }

    public void provideSurface(@NonNull final Surface surface, @NonNull Executor executor, @NonNull final Consumer<Result> resultListener) {
        if (this.mSurfaceCompleter.set(surface) || this.mSurfaceFuture.isCancelled()) {
            Futures.addCallback(this.mSessionStatusFuture, new FutureCallback<Void>() {
                public void onSuccess(@Nullable Void result) {
                    resultListener.accept(Result.of(0, surface));
                }

                public void onFailure(Throwable t) {
                    Preconditions.checkState(t instanceof RequestCancelledException, "Camera surface session should only fail with request cancellation. Instead failed due to:\n" + t);
                    resultListener.accept(Result.of(1, surface));
                }
            }, executor);
            return;
        }
        Preconditions.checkState(this.mSurfaceFuture.isDone());
        try {
            this.mSurfaceFuture.get();
            executor.execute(new h1(resultListener, surface));
        } catch (InterruptedException | ExecutionException e) {
            executor.execute(new i1(resultListener, surface));
        }
    }

    public boolean willNotProvideSurface() {
        return this.mSurfaceCompleter.setException(new DeferrableSurface.SurfaceUnavailableException("Surface request will not complete."));
    }

    @SuppressLint({"PairedRegistration"})
    public void addRequestCancellationListener(@NonNull Executor executor, @NonNull Runnable listener) {
        this.mRequestCancellationCompleter.addCancellationListener(listener, executor);
    }

    @ExperimentalUseCaseGroup
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void updateTransformationInfo(@NonNull TransformationInfo transformationInfo) {
        this.mTransformationInfo = transformationInfo;
        TransformationInfoListener listener = this.mTransformationInfoListener;
        if (listener != null) {
            this.mTransformationInfoExecutor.execute(new k1(listener, transformationInfo));
        }
    }

    @ExperimentalUseCaseGroup
    public void setTransformationInfoListener(@NonNull Executor executor, @NonNull TransformationInfoListener listener) {
        this.mTransformationInfoListener = listener;
        this.mTransformationInfoExecutor = executor;
        TransformationInfo transformationInfo = this.mTransformationInfo;
        if (transformationInfo != null) {
            executor.execute(new j1(listener, transformationInfo));
        }
    }

    @ExperimentalUseCaseGroup
    public void clearTransformationInfoListener() {
        this.mTransformationInfoListener = null;
        this.mTransformationInfoExecutor = null;
    }

    public static final class RequestCancelledException extends RuntimeException {
        RequestCancelledException(@NonNull String message, @NonNull Throwable cause) {
            super(message, cause);
        }
    }

    @AutoValue
    public static abstract class Result {
        public static final int RESULT_INVALID_SURFACE = 2;
        public static final int RESULT_REQUEST_CANCELLED = 1;
        public static final int RESULT_SURFACE_ALREADY_PROVIDED = 3;
        public static final int RESULT_SURFACE_USED_SUCCESSFULLY = 0;
        public static final int RESULT_WILL_NOT_PROVIDE_SURFACE = 4;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        @Retention(RetentionPolicy.SOURCE)
        public @interface ResultCode {
        }

        public abstract int getResultCode();

        @NonNull
        public abstract Surface getSurface();

        @NonNull
        static Result of(int code, @NonNull Surface surface) {
            return new AutoValue_SurfaceRequest_Result(code, surface);
        }

        Result() {
        }
    }

    @AutoValue
    @ExperimentalUseCaseGroup
    public static abstract class TransformationInfo {
        @NonNull
        public abstract Rect getCropRect();

        public abstract int getRotationDegrees();

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public abstract int getTargetRotation();

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static TransformationInfo of(@NonNull Rect cropRect, int rotationDegrees, int targetRotation) {
            return new AutoValue_SurfaceRequest_TransformationInfo(cropRect, rotationDegrees, targetRotation);
        }

        TransformationInfo() {
        }
    }
}
