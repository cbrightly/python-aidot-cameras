package androidx.camera.view;

import androidx.annotation.GuardedBy;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.Observable;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.view.PreviewView;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.lifecycle.MutableLiveData;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.List;

public final class PreviewStreamStateObserver implements Observable.Observer<CameraInternal.State> {
    private static final String TAG = "StreamStateObserver";
    private final CameraInfoInternal mCameraInfoInternal;
    ListenableFuture<Void> mFlowFuture;
    private boolean mHasStartedPreviewStreamFlow = false;
    @GuardedBy("this")
    private PreviewView.StreamState mPreviewStreamState;
    private final MutableLiveData<PreviewView.StreamState> mPreviewStreamStateLiveData;
    private final PreviewViewImplementation mPreviewViewImplementation;

    public /* synthetic */ Void b(Void voidR) {
        lambda$startPreviewStreamStateFlow$1(voidR);
        return null;
    }

    PreviewStreamStateObserver(CameraInfoInternal cameraInfoInternal, MutableLiveData<PreviewView.StreamState> previewStreamLiveData, PreviewViewImplementation implementation) {
        this.mCameraInfoInternal = cameraInfoInternal;
        this.mPreviewStreamStateLiveData = previewStreamLiveData;
        this.mPreviewViewImplementation = implementation;
        synchronized (this) {
            this.mPreviewStreamState = previewStreamLiveData.getValue();
        }
    }

    @MainThread
    public void onNewData(@Nullable CameraInternal.State value) {
        if (value == CameraInternal.State.CLOSING || value == CameraInternal.State.CLOSED || value == CameraInternal.State.RELEASING || value == CameraInternal.State.RELEASED) {
            updatePreviewStreamState(PreviewView.StreamState.IDLE);
            if (this.mHasStartedPreviewStreamFlow) {
                this.mHasStartedPreviewStreamFlow = false;
                cancelFlow();
            }
        } else if ((value == CameraInternal.State.OPENING || value == CameraInternal.State.OPEN || value == CameraInternal.State.PENDING_OPEN) && !this.mHasStartedPreviewStreamFlow) {
            startPreviewStreamStateFlow(this.mCameraInfoInternal);
            this.mHasStartedPreviewStreamFlow = true;
        }
    }

    @MainThread
    public void onError(@NonNull Throwable t) {
        clear();
        updatePreviewStreamState(PreviewView.StreamState.IDLE);
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        cancelFlow();
    }

    private void cancelFlow() {
        ListenableFuture<Void> listenableFuture = this.mFlowFuture;
        if (listenableFuture != null) {
            listenableFuture.cancel(false);
            this.mFlowFuture = null;
        }
    }

    @MainThread
    private void startPreviewStreamStateFlow(final CameraInfo cameraInfo) {
        updatePreviewStreamState(PreviewView.StreamState.IDLE);
        final List<CameraCaptureCallback> callbacksToClear = new ArrayList<>();
        FutureChain<T> transform = FutureChain.from(waitForCaptureResult(cameraInfo, callbacksToClear)).transformAsync(new g(this), CameraXExecutors.directExecutor()).transform(new e(this), CameraXExecutors.directExecutor());
        this.mFlowFuture = transform;
        Futures.addCallback(transform, new FutureCallback<Void>() {
            public void onSuccess(@Nullable Void result) {
                PreviewStreamStateObserver.this.mFlowFuture = null;
            }

            public void onFailure(Throwable t) {
                PreviewStreamStateObserver.this.mFlowFuture = null;
                if (!callbacksToClear.isEmpty()) {
                    for (CameraCaptureCallback callback : callbacksToClear) {
                        ((CameraInfoInternal) cameraInfo).removeSessionCaptureCallback(callback);
                    }
                    callbacksToClear.clear();
                }
            }
        }, CameraXExecutors.directExecutor());
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$startPreviewStreamStateFlow$0 */
    public /* synthetic */ ListenableFuture a(Void v) {
        return this.mPreviewViewImplementation.waitForNextFrame();
    }

    private /* synthetic */ Void lambda$startPreviewStreamStateFlow$1(Void v) {
        updatePreviewStreamState(PreviewView.StreamState.STREAMING);
        return null;
    }

    /* access modifiers changed from: package-private */
    public void updatePreviewStreamState(PreviewView.StreamState streamState) {
        synchronized (this) {
            if (!this.mPreviewStreamState.equals(streamState)) {
                this.mPreviewStreamState = streamState;
                Logger.d(TAG, "Update Preview stream state to " + streamState);
                this.mPreviewStreamStateLiveData.postValue(streamState);
            }
        }
    }

    private ListenableFuture<Void> waitForCaptureResult(CameraInfo cameraInfo, List<CameraCaptureCallback> callbacksToClear) {
        return CallbackToFutureAdapter.getFuture(new f(this, cameraInfo, callbacksToClear));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$waitForCaptureResult$2 */
    public /* synthetic */ Object c(final CameraInfo cameraInfo, List callbacksToClear, final CallbackToFutureAdapter.Completer completer) {
        CameraCaptureCallback callback = new CameraCaptureCallback() {
            public void onCaptureCompleted(@NonNull CameraCaptureResult result) {
                completer.set(null);
                ((CameraInfoInternal) cameraInfo).removeSessionCaptureCallback(this);
            }
        };
        callbacksToClear.add(callback);
        ((CameraInfoInternal) cameraInfo).addSessionCaptureCallback(CameraXExecutors.directExecutor(), callback);
        return "waitForCaptureResult";
    }
}
