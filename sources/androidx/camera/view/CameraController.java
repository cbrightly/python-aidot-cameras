package androidx.camera.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import androidx.annotation.FloatRange;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraInfoUnavailableException;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalUseCaseGroup;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Logger;
import androidx.camera.core.MeteringPoint;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.core.UseCaseGroup;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.ViewPort;
import androidx.camera.core.ZoomState;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.video.ExperimentalVideo;
import androidx.camera.view.video.OnVideoSavedCallback;
import androidx.camera.view.video.OutputFileOptions;
import androidx.camera.view.video.OutputFileResults;
import androidx.core.util.Preconditions;
import androidx.lifecycle.LiveData;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.messaging.Constants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CameraController {
    private static final float AE_SIZE = 0.25f;
    private static final float AF_SIZE = 0.16666667f;
    private static final String CAMERA_NOT_ATTACHED = "Use cases not attached to camera.";
    private static final String CAMERA_NOT_INITIALIZED = "Camera not initialized.";
    public static final int IMAGE_ANALYSIS = 2;
    public static final int IMAGE_CAPTURE = 1;
    private static final String IMAGE_CAPTURE_DISABLED = "ImageCapture disabled.";
    private static final String PREVIEW_VIEW_NOT_ATTACHED = "PreviewView not attached.";
    private static final String TAG = "CameraController";
    @ExperimentalVideo
    public static final int VIDEO_CAPTURE = 4;
    private static final String VIDEO_CAPTURE_DISABLED = "VideoCapture disabled.";
    @Nullable
    private ImageAnalysis.Analyzer mAnalysisAnalyzer;
    @Nullable
    private Executor mAnalysisExecutor;
    private final Context mAppContext;
    @Nullable
    Camera mCamera;
    @Nullable
    ProcessCameraProvider mCameraProvider;
    CameraSelector mCameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
    @Nullable
    private final DisplayRotationListener mDisplayRotationListener;
    private int mEnabledUseCases = 3;
    @NonNull
    private ImageAnalysis mImageAnalysis;
    @NonNull
    final ImageCapture mImageCapture;
    @NonNull
    private final ListenableFuture<Void> mInitializationFuture;
    private boolean mPinchToZoomEnabled = true;
    @NonNull
    final Preview mPreview;
    @Nullable
    Display mPreviewDisplay;
    @NonNull
    final SensorRotationListener mSensorRotationListener;
    @Nullable
    Preview.SurfaceProvider mSurfaceProvider;
    private boolean mTapToFocusEnabled = true;
    private final ForwardingLiveData<Integer> mTorchState = new ForwardingLiveData<>();
    @NonNull
    final VideoCapture mVideoCapture;
    @NonNull
    final AtomicBoolean mVideoIsRecording = new AtomicBoolean(false);
    @Nullable
    ViewPort mViewPort;
    private final ForwardingLiveData<ZoomState> mZoomState = new ForwardingLiveData<>();

    @OptIn(markerClass = {ExperimentalVideo.class})
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UseCases {
    }

    public /* synthetic */ Void a(ProcessCameraProvider processCameraProvider) {
        lambda$new$0(processCameraProvider);
        return null;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public abstract Camera startCamera();

    CameraController(@NonNull Context context) {
        Context applicationContext = context.getApplicationContext();
        this.mAppContext = applicationContext;
        this.mPreview = new Preview.Builder().build();
        this.mImageCapture = new ImageCapture.Builder().build();
        this.mImageAnalysis = new ImageAnalysis.Builder().build();
        this.mVideoCapture = new VideoCapture.Builder().build();
        this.mInitializationFuture = Futures.transform(ProcessCameraProvider.getInstance(applicationContext), new c(this), CameraXExecutors.mainThreadExecutor());
        this.mDisplayRotationListener = new DisplayRotationListener();
        this.mSensorRotationListener = new SensorRotationListener(applicationContext) {
            public void onRotationChanged(int rotation) {
                CameraController.this.mImageCapture.setTargetRotation(rotation);
                CameraController.this.mVideoCapture.setTargetRotation(rotation);
            }
        };
    }

    private /* synthetic */ Void lambda$new$0(ProcessCameraProvider provider) {
        this.mCameraProvider = provider;
        startCameraAndTrackStates();
        return null;
    }

    @NonNull
    public ListenableFuture<Void> getInitializationFuture() {
        return this.mInitializationFuture;
    }

    private boolean isCameraInitialized() {
        return this.mCameraProvider != null;
    }

    private boolean isPreviewViewAttached() {
        return (this.mSurfaceProvider == null || this.mViewPort == null || this.mPreviewDisplay == null) ? false : true;
    }

    private boolean isCameraAttached() {
        return this.mCamera != null;
    }

    @MainThread
    @OptIn(markerClass = {ExperimentalVideo.class})
    public void setEnabledUseCases(int enabledUseCases) {
        Threads.checkMainThread();
        if (enabledUseCases != this.mEnabledUseCases) {
            int oldEnabledUseCases = this.mEnabledUseCases;
            this.mEnabledUseCases = enabledUseCases;
            if (!isVideoCaptureEnabled()) {
                stopRecording();
            }
            startCameraAndTrackStates(new b(this, oldEnabledUseCases));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$setEnabledUseCases$1 */
    public /* synthetic */ void c(int oldEnabledUseCases) {
        this.mEnabledUseCases = oldEnabledUseCases;
    }

    private boolean isUseCaseEnabled(int useCaseMask) {
        return (this.mEnabledUseCases & useCaseMask) != 0;
    }

    @OptIn(markerClass = {ExperimentalVideo.class})
    private boolean isVideoCaptureEnabledInternal() {
        return isVideoCaptureEnabled();
    }

    /* access modifiers changed from: package-private */
    @SuppressLint({"MissingPermission", "WrongConstant"})
    @MainThread
    @OptIn(markerClass = {ExperimentalUseCaseGroup.class})
    public void attachPreviewSurface(@NonNull Preview.SurfaceProvider surfaceProvider, @NonNull ViewPort viewPort, @NonNull Display display) {
        Threads.checkMainThread();
        if (this.mSurfaceProvider != surfaceProvider) {
            this.mSurfaceProvider = surfaceProvider;
            this.mPreview.setSurfaceProvider(surfaceProvider);
        }
        this.mViewPort = viewPort;
        this.mPreviewDisplay = display;
        startListeningToRotationEvents();
        startCameraAndTrackStates();
    }

    /* access modifiers changed from: package-private */
    @MainThread
    public void clearPreviewSurface() {
        Threads.checkMainThread();
        ProcessCameraProvider processCameraProvider = this.mCameraProvider;
        if (processCameraProvider != null) {
            processCameraProvider.unbindAll();
        }
        this.mPreview.setSurfaceProvider((Preview.SurfaceProvider) null);
        this.mCamera = null;
        this.mSurfaceProvider = null;
        this.mViewPort = null;
        this.mPreviewDisplay = null;
        stopListeningToRotationEvents();
    }

    private void startListeningToRotationEvents() {
        getDisplayManager().registerDisplayListener(this.mDisplayRotationListener, new Handler(Looper.getMainLooper()));
        if (this.mSensorRotationListener.canDetectOrientation()) {
            this.mSensorRotationListener.enable();
        }
    }

    private void stopListeningToRotationEvents() {
        getDisplayManager().unregisterDisplayListener(this.mDisplayRotationListener);
        this.mSensorRotationListener.disable();
    }

    private DisplayManager getDisplayManager() {
        return (DisplayManager) this.mAppContext.getSystemService(Constants.ScionAnalytics.MessageType.DISPLAY_NOTIFICATION);
    }

    @MainThread
    public boolean isImageCaptureEnabled() {
        Threads.checkMainThread();
        return isUseCaseEnabled(1);
    }

    @MainThread
    public int getImageCaptureFlashMode() {
        Threads.checkMainThread();
        return this.mImageCapture.getFlashMode();
    }

    @MainThread
    public void setImageCaptureFlashMode(int flashMode) {
        Threads.checkMainThread();
        this.mImageCapture.setFlashMode(flashMode);
    }

    @MainThread
    public void takePicture(@NonNull ImageCapture.OutputFileOptions outputFileOptions, @NonNull Executor executor, @NonNull ImageCapture.OnImageSavedCallback imageSavedCallback) {
        Threads.checkMainThread();
        Preconditions.checkState(isCameraInitialized(), CAMERA_NOT_INITIALIZED);
        Preconditions.checkState(isImageCaptureEnabled(), IMAGE_CAPTURE_DISABLED);
        updateMirroringFlagInOutputFileOptions(outputFileOptions);
        this.mImageCapture.lambda$takePicture$5(outputFileOptions, executor, imageSavedCallback);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void updateMirroringFlagInOutputFileOptions(@NonNull ImageCapture.OutputFileOptions outputFileOptions) {
        if (this.mCameraSelector.getLensFacing() != null && !outputFileOptions.getMetadata().isReversedHorizontalSet()) {
            outputFileOptions.getMetadata().setReversedHorizontal(this.mCameraSelector.getLensFacing().intValue() == 0);
        }
    }

    @MainThread
    public void takePicture(@NonNull Executor executor, @NonNull ImageCapture.OnImageCapturedCallback callback) {
        Threads.checkMainThread();
        Preconditions.checkState(isCameraInitialized(), CAMERA_NOT_INITIALIZED);
        Preconditions.checkState(isImageCaptureEnabled(), IMAGE_CAPTURE_DISABLED);
        this.mImageCapture.lambda$takePicture$4(executor, callback);
    }

    @MainThread
    public boolean isImageAnalysisEnabled() {
        Threads.checkMainThread();
        return isUseCaseEnabled(2);
    }

    @MainThread
    public void setImageAnalysisAnalyzer(@NonNull Executor executor, @NonNull ImageAnalysis.Analyzer analyzer) {
        Threads.checkMainThread();
        if (this.mAnalysisAnalyzer != analyzer || this.mAnalysisExecutor != executor) {
            this.mAnalysisExecutor = executor;
            this.mAnalysisAnalyzer = analyzer;
            this.mImageAnalysis.setAnalyzer(executor, analyzer);
        }
    }

    @MainThread
    public void clearImageAnalysisAnalyzer() {
        Threads.checkMainThread();
        this.mAnalysisExecutor = null;
        this.mAnalysisAnalyzer = null;
        this.mImageAnalysis.clearAnalyzer();
    }

    @MainThread
    public int getImageAnalysisBackpressureStrategy() {
        Threads.checkMainThread();
        return this.mImageAnalysis.getBackpressureStrategy();
    }

    @MainThread
    public void setImageAnalysisBackpressureStrategy(int strategy) {
        Threads.checkMainThread();
        if (this.mImageAnalysis.getBackpressureStrategy() != strategy) {
            unbindImageAnalysisAndRecreate(strategy, this.mImageAnalysis.getImageQueueDepth());
            startCameraAndTrackStates();
        }
    }

    @MainThread
    public void setImageAnalysisImageQueueDepth(int depth) {
        Threads.checkMainThread();
        if (this.mImageAnalysis.getImageQueueDepth() != depth) {
            unbindImageAnalysisAndRecreate(this.mImageAnalysis.getBackpressureStrategy(), depth);
            startCameraAndTrackStates();
        }
    }

    @MainThread
    public int getImageAnalysisImageQueueDepth() {
        Threads.checkMainThread();
        return this.mImageAnalysis.getImageQueueDepth();
    }

    private void unbindImageAnalysisAndRecreate(int strategy, int imageQueueDepth) {
        ImageAnalysis.Analyzer analyzer;
        if (isCameraInitialized()) {
            this.mCameraProvider.unbind(this.mImageAnalysis);
        }
        ImageAnalysis build = new ImageAnalysis.Builder().setBackpressureStrategy(strategy).setImageQueueDepth(imageQueueDepth).build();
        this.mImageAnalysis = build;
        Executor executor = this.mAnalysisExecutor;
        if (executor != null && (analyzer = this.mAnalysisAnalyzer) != null) {
            build.setAnalyzer(executor, analyzer);
        }
    }

    @MainThread
    @ExperimentalVideo
    public boolean isVideoCaptureEnabled() {
        Threads.checkMainThread();
        return isUseCaseEnabled(4);
    }

    @MainThread
    @ExperimentalVideo
    public void startRecording(@NonNull OutputFileOptions outputFileOptions, @NonNull Executor executor, @NonNull final OnVideoSavedCallback callback) {
        Threads.checkMainThread();
        Preconditions.checkState(isCameraInitialized(), CAMERA_NOT_INITIALIZED);
        Preconditions.checkState(isVideoCaptureEnabled(), VIDEO_CAPTURE_DISABLED);
        this.mVideoCapture.lambda$startRecording$0(outputFileOptions.toVideoCaptureOutputFileOptions(), executor, new VideoCapture.OnVideoSavedCallback() {
            public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
                CameraController.this.mVideoIsRecording.set(false);
                callback.onVideoSaved(OutputFileResults.create(outputFileResults.getSavedUri()));
            }

            public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                CameraController.this.mVideoIsRecording.set(false);
                callback.onError(videoCaptureError, message, cause);
            }
        });
        this.mVideoIsRecording.set(true);
    }

    @MainThread
    @ExperimentalVideo
    public void stopRecording() {
        Threads.checkMainThread();
        if (this.mVideoIsRecording.get()) {
            this.mVideoCapture.lambda$stopRecording$5();
        }
    }

    @MainThread
    @ExperimentalVideo
    public boolean isRecording() {
        Threads.checkMainThread();
        return this.mVideoIsRecording.get();
    }

    @MainThread
    public void setCameraSelector(@NonNull CameraSelector cameraSelector) {
        Threads.checkMainThread();
        if (this.mCameraSelector != cameraSelector) {
            CameraSelector oldCameraSelector = this.mCameraSelector;
            this.mCameraSelector = cameraSelector;
            ProcessCameraProvider processCameraProvider = this.mCameraProvider;
            if (processCameraProvider != null) {
                processCameraProvider.unbindAll();
                startCameraAndTrackStates(new d(this, oldCameraSelector));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$setCameraSelector$2 */
    public /* synthetic */ void b(CameraSelector oldCameraSelector) {
        this.mCameraSelector = oldCameraSelector;
    }

    @MainThread
    public boolean hasCamera(@NonNull CameraSelector cameraSelector) {
        Threads.checkMainThread();
        Preconditions.checkNotNull(cameraSelector);
        ProcessCameraProvider processCameraProvider = this.mCameraProvider;
        if (processCameraProvider != null) {
            try {
                return processCameraProvider.hasCamera(cameraSelector);
            } catch (CameraInfoUnavailableException e) {
                Logger.w(TAG, "Failed to check camera availability", e);
                return false;
            }
        } else {
            throw new IllegalStateException("Camera not initialized. Please wait for the initialization future to finish. See #getInitializationFuture().");
        }
    }

    @MainThread
    @NonNull
    public CameraSelector getCameraSelector() {
        Threads.checkMainThread();
        return this.mCameraSelector;
    }

    @MainThread
    public boolean isPinchToZoomEnabled() {
        Threads.checkMainThread();
        return this.mPinchToZoomEnabled;
    }

    @MainThread
    public void setPinchToZoomEnabled(boolean enabled) {
        Threads.checkMainThread();
        this.mPinchToZoomEnabled = enabled;
    }

    /* access modifiers changed from: package-private */
    public void onPinchToZoom(float pinchToZoomScale) {
        if (!isCameraAttached()) {
            Logger.w(TAG, CAMERA_NOT_ATTACHED);
        } else if (!this.mPinchToZoomEnabled) {
            Logger.d(TAG, "Pinch to zoom disabled.");
        } else {
            Logger.d(TAG, "Pinch to zoom with scale: " + pinchToZoomScale);
            ZoomState zoomState = getZoomState().getValue();
            if (zoomState != null) {
                setZoomRatio(Math.min(Math.max(zoomState.getZoomRatio() * speedUpZoomBy2X(pinchToZoomScale), zoomState.getMinZoomRatio()), zoomState.getMaxZoomRatio()));
            }
        }
    }

    private float speedUpZoomBy2X(float scaleFactor) {
        if (scaleFactor > 1.0f) {
            return ((scaleFactor - 1.0f) * 2.0f) + 1.0f;
        }
        return 1.0f - ((1.0f - scaleFactor) * 2.0f);
    }

    /* access modifiers changed from: package-private */
    public void onTapToFocus(MeteringPointFactory meteringPointFactory, float x, float y) {
        if (!isCameraAttached()) {
            Logger.w(TAG, CAMERA_NOT_ATTACHED);
        } else if (!this.mTapToFocusEnabled) {
            Logger.d(TAG, "Tap to focus disabled. ");
        } else {
            Logger.d(TAG, "Tap to focus: " + x + ", " + y);
            MeteringPoint afPoint = meteringPointFactory.createPoint(x, y, AF_SIZE);
            this.mCamera.getCameraControl().startFocusAndMetering(new FocusMeteringAction.Builder(afPoint, 1).addPoint(meteringPointFactory.createPoint(x, y, AE_SIZE), 2).build());
        }
    }

    @MainThread
    public boolean isTapToFocusEnabled() {
        Threads.checkMainThread();
        return this.mTapToFocusEnabled;
    }

    @MainThread
    public void setTapToFocusEnabled(boolean enabled) {
        Threads.checkMainThread();
        this.mTapToFocusEnabled = enabled;
    }

    @MainThread
    @NonNull
    public LiveData<ZoomState> getZoomState() {
        Threads.checkMainThread();
        return this.mZoomState;
    }

    @MainThread
    @Nullable
    public CameraInfo getCameraInfo() {
        Threads.checkMainThread();
        Camera camera = this.mCamera;
        if (camera == null) {
            return null;
        }
        return camera.getCameraInfo();
    }

    @MainThread
    @NonNull
    public ListenableFuture<Void> setZoomRatio(float zoomRatio) {
        Threads.checkMainThread();
        if (isCameraAttached()) {
            return this.mCamera.getCameraControl().setZoomRatio(zoomRatio);
        }
        Logger.w(TAG, CAMERA_NOT_ATTACHED);
        return Futures.immediateFuture(null);
    }

    @MainThread
    @NonNull
    public ListenableFuture<Void> setLinearZoom(@FloatRange(from = 0.0d, to = 1.0d) float linearZoom) {
        Threads.checkMainThread();
        if (isCameraAttached()) {
            return this.mCamera.getCameraControl().setLinearZoom(linearZoom);
        }
        Logger.w(TAG, CAMERA_NOT_ATTACHED);
        return Futures.immediateFuture(null);
    }

    @MainThread
    @NonNull
    public LiveData<Integer> getTorchState() {
        Threads.checkMainThread();
        return this.mTorchState;
    }

    @MainThread
    @NonNull
    public ListenableFuture<Void> enableTorch(boolean torchEnabled) {
        Threads.checkMainThread();
        if (isCameraAttached()) {
            return this.mCamera.getCameraControl().enableTorch(torchEnabled);
        }
        Logger.w(TAG, CAMERA_NOT_ATTACHED);
        return Futures.immediateFuture(null);
    }

    /* access modifiers changed from: package-private */
    public void startCameraAndTrackStates() {
        startCameraAndTrackStates((Runnable) null);
    }

    /* access modifiers changed from: package-private */
    public void startCameraAndTrackStates(@Nullable Runnable restoreStateRunnable) {
        try {
            this.mCamera = startCamera();
            if (!isCameraAttached()) {
                Logger.d(TAG, CAMERA_NOT_ATTACHED);
                return;
            }
            this.mZoomState.setSource(this.mCamera.getCameraInfo().getZoomState());
            this.mTorchState.setSource(this.mCamera.getCameraInfo().getTorchState());
        } catch (IllegalArgumentException exception) {
            if (restoreStateRunnable != null) {
                restoreStateRunnable.run();
            }
            throw new IllegalStateException("The selected camera does not support the enabled use cases. Please disable use case and/or select a different camera. e.g. #setVideoCaptureEnabled(false)", exception);
        }
    }

    /* access modifiers changed from: protected */
    @OptIn(markerClass = {ExperimentalUseCaseGroup.class})
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseGroup createUseCaseGroup() {
        if (!isCameraInitialized()) {
            Logger.d(TAG, CAMERA_NOT_INITIALIZED);
            return null;
        } else if (!isPreviewViewAttached()) {
            Logger.d(TAG, PREVIEW_VIEW_NOT_ATTACHED);
            return null;
        } else {
            UseCaseGroup.Builder builder = new UseCaseGroup.Builder().addUseCase(this.mPreview);
            if (isImageCaptureEnabled()) {
                builder.addUseCase(this.mImageCapture);
            } else {
                this.mCameraProvider.unbind(this.mImageCapture);
            }
            if (isImageAnalysisEnabled()) {
                builder.addUseCase(this.mImageAnalysis);
            } else {
                this.mCameraProvider.unbind(this.mImageAnalysis);
            }
            if (isVideoCaptureEnabledInternal()) {
                builder.addUseCase(this.mVideoCapture);
            } else {
                this.mCameraProvider.unbind(this.mVideoCapture);
            }
            builder.setViewPort(this.mViewPort);
            return builder.build();
        }
    }

    public class DisplayRotationListener implements DisplayManager.DisplayListener {
        DisplayRotationListener() {
        }

        public void onDisplayAdded(int displayId) {
        }

        public void onDisplayRemoved(int displayId) {
        }

        @SuppressLint({"WrongConstant"})
        @OptIn(markerClass = {ExperimentalUseCaseGroup.class})
        public void onDisplayChanged(int displayId) {
            Display display = CameraController.this.mPreviewDisplay;
            if (display != null && display.getDisplayId() == displayId) {
                CameraController cameraController = CameraController.this;
                cameraController.mPreview.setTargetRotation(cameraController.mPreviewDisplay.getRotation());
            }
        }
    }
}
