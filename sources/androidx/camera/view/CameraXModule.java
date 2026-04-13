package androidx.camera.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Rational;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.RequiresPermission;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraInfoUnavailableException;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Logger;
import androidx.camera.core.Preview;
import androidx.camera.core.UseCase;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.impl.LensFacingConverter;
import androidx.camera.core.impl.utils.CameraOrientationUtil;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.CameraView;
import androidx.camera.view.video.ExperimentalVideo;
import androidx.core.util.Preconditions;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

@Deprecated
public final class CameraXModule {
    private static final Rational ASPECT_RATIO_16_9 = new Rational(16, 9);
    private static final Rational ASPECT_RATIO_3_4 = new Rational(3, 4);
    private static final Rational ASPECT_RATIO_4_3 = new Rational(4, 3);
    private static final Rational ASPECT_RATIO_9_16 = new Rational(9, 16);
    public static final String TAG = "CameraXModule";
    private static final float UNITY_ZOOM_SCALE = 1.0f;
    private static final float ZOOM_NOT_SUPPORTED = 1.0f;
    @Nullable
    Camera mCamera;
    @Nullable
    Integer mCameraLensFacing = 1;
    @Nullable
    ProcessCameraProvider mCameraProvider;
    private final CameraView mCameraView;
    private CameraView.CaptureMode mCaptureMode = CameraView.CaptureMode.IMAGE;
    @Nullable
    LifecycleOwner mCurrentLifecycle;
    private final LifecycleObserver mCurrentLifecycleObserver = new LifecycleObserver() {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy(LifecycleOwner owner) {
            CameraXModule cameraXModule = CameraXModule.this;
            if (owner == cameraXModule.mCurrentLifecycle) {
                cameraXModule.clearCurrentLifecycle();
            }
        }
    };
    private int mFlash = 2;
    @Nullable
    private ImageCapture mImageCapture;
    private final ImageCapture.Builder mImageCaptureBuilder;
    private long mMaxVideoDuration = -1;
    private long mMaxVideoSize = -1;
    @Nullable
    private LifecycleOwner mNewLifecycle;
    @Nullable
    Preview mPreview;
    private final Preview.Builder mPreviewBuilder;
    @Nullable
    private VideoCapture mVideoCapture;
    private final VideoCapture.Builder mVideoCaptureBuilder;
    final AtomicBoolean mVideoIsRecording = new AtomicBoolean(false);

    CameraXModule(CameraView view) {
        this.mCameraView = view;
        Futures.addCallback(ProcessCameraProvider.getInstance(view.getContext()), new FutureCallback<ProcessCameraProvider>() {
            @SuppressLint({"MissingPermission"})
            public void onSuccess(@Nullable ProcessCameraProvider provider) {
                Preconditions.checkNotNull(provider);
                CameraXModule cameraXModule = CameraXModule.this;
                cameraXModule.mCameraProvider = provider;
                LifecycleOwner lifecycleOwner = cameraXModule.mCurrentLifecycle;
                if (lifecycleOwner != null) {
                    cameraXModule.bindToLifecycle(lifecycleOwner);
                }
            }

            public void onFailure(Throwable t) {
                throw new RuntimeException("CameraX failed to initialize.", t);
            }
        }, CameraXExecutors.mainThreadExecutor());
        this.mPreviewBuilder = new Preview.Builder().setTargetName("Preview");
        this.mImageCaptureBuilder = new ImageCapture.Builder().setTargetName("ImageCapture");
        this.mVideoCaptureBuilder = new VideoCapture.Builder().setTargetName("VideoCapture");
    }

    /* access modifiers changed from: package-private */
    @RequiresPermission("android.permission.CAMERA")
    public void bindToLifecycle(LifecycleOwner lifecycleOwner) {
        this.mNewLifecycle = lifecycleOwner;
        if (getMeasuredWidth() > 0 && getMeasuredHeight() > 0) {
            bindToLifecycleAfterViewMeasured();
        }
    }

    /* access modifiers changed from: package-private */
    @RequiresPermission("android.permission.CAMERA")
    @OptIn(markerClass = {ExperimentalVideo.class})
    public void bindToLifecycleAfterViewMeasured() {
        Rational targetAspectRatio;
        if (this.mNewLifecycle != null) {
            clearCurrentLifecycle();
            if (this.mNewLifecycle.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
                this.mNewLifecycle = null;
                return;
            }
            this.mCurrentLifecycle = this.mNewLifecycle;
            this.mNewLifecycle = null;
            if (this.mCameraProvider != null) {
                Set<Integer> available = getAvailableCameraLensFacing();
                if (available.isEmpty()) {
                    Logger.w(TAG, "Unable to bindToLifeCycle since no cameras available");
                    this.mCameraLensFacing = null;
                }
                Integer num = this.mCameraLensFacing;
                if (num != null && !available.contains(num)) {
                    Logger.w(TAG, "Camera does not exist with direction " + this.mCameraLensFacing);
                    this.mCameraLensFacing = available.iterator().next();
                    Logger.w(TAG, "Defaulting to primary camera with direction " + this.mCameraLensFacing);
                }
                if (this.mCameraLensFacing != null) {
                    boolean isDisplayPortrait = getDisplayRotationDegrees() == 0 || getDisplayRotationDegrees() == 180;
                    CameraView.CaptureMode captureMode = getCaptureMode();
                    CameraView.CaptureMode captureMode2 = CameraView.CaptureMode.IMAGE;
                    if (captureMode == captureMode2) {
                        targetAspectRatio = isDisplayPortrait ? ASPECT_RATIO_3_4 : ASPECT_RATIO_4_3;
                    } else {
                        this.mImageCaptureBuilder.setTargetAspectRatio(1);
                        this.mVideoCaptureBuilder.setTargetAspectRatio(1);
                        targetAspectRatio = isDisplayPortrait ? ASPECT_RATIO_9_16 : ASPECT_RATIO_16_9;
                    }
                    this.mImageCaptureBuilder.setTargetRotation(getDisplaySurfaceRotation());
                    this.mImageCapture = this.mImageCaptureBuilder.build();
                    this.mVideoCaptureBuilder.setTargetRotation(getDisplaySurfaceRotation());
                    this.mVideoCapture = this.mVideoCaptureBuilder.build();
                    this.mPreviewBuilder.setTargetResolution(new Size(getMeasuredWidth(), (int) (((float) getMeasuredWidth()) / targetAspectRatio.floatValue())));
                    Preview build = this.mPreviewBuilder.build();
                    this.mPreview = build;
                    build.setSurfaceProvider(this.mCameraView.getPreviewView().getSurfaceProvider());
                    CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(this.mCameraLensFacing.intValue()).build();
                    if (getCaptureMode() == captureMode2) {
                        this.mCamera = this.mCameraProvider.bindToLifecycle(this.mCurrentLifecycle, cameraSelector, this.mImageCapture, this.mPreview);
                    } else if (getCaptureMode() == CameraView.CaptureMode.VIDEO) {
                        this.mCamera = this.mCameraProvider.bindToLifecycle(this.mCurrentLifecycle, cameraSelector, this.mVideoCapture, this.mPreview);
                    } else {
                        this.mCamera = this.mCameraProvider.bindToLifecycle(this.mCurrentLifecycle, cameraSelector, this.mImageCapture, this.mVideoCapture, this.mPreview);
                    }
                    setZoomRatio(1.0f);
                    this.mCurrentLifecycle.getLifecycle().addObserver(this.mCurrentLifecycleObserver);
                    setFlash(getFlash());
                }
            }
        }
    }

    public void open() {
        throw new UnsupportedOperationException("Explicit open/close of camera not yet supported. Use bindtoLifecycle() instead.");
    }

    public void close() {
        throw new UnsupportedOperationException("Explicit open/close of camera not yet supported. Use bindtoLifecycle() instead.");
    }

    @OptIn(markerClass = {ExperimentalVideo.class})
    public void takePicture(Executor executor, ImageCapture.OnImageCapturedCallback callback) {
        if (this.mImageCapture != null) {
            if (getCaptureMode() == CameraView.CaptureMode.VIDEO) {
                throw new IllegalStateException("Can not take picture under VIDEO capture mode.");
            } else if (callback != null) {
                this.mImageCapture.lambda$takePicture$4(executor, callback);
            } else {
                throw new IllegalArgumentException("OnImageCapturedCallback should not be empty");
            }
        }
    }

    @OptIn(markerClass = {ExperimentalVideo.class})
    public void takePicture(@NonNull ImageCapture.OutputFileOptions outputFileOptions, @NonNull Executor executor, ImageCapture.OnImageSavedCallback callback) {
        if (this.mImageCapture != null) {
            if (getCaptureMode() == CameraView.CaptureMode.VIDEO) {
                throw new IllegalStateException("Can not take picture under VIDEO capture mode.");
            } else if (callback != null) {
                ImageCapture.Metadata metadata = outputFileOptions.getMetadata();
                Integer num = this.mCameraLensFacing;
                metadata.setReversedHorizontal(num != null && num.intValue() == 0);
                this.mImageCapture.lambda$takePicture$5(outputFileOptions, executor, callback);
            } else {
                throw new IllegalArgumentException("OnImageSavedCallback should not be empty");
            }
        }
    }

    public void startRecording(VideoCapture.OutputFileOptions outputFileOptions, Executor executor, final VideoCapture.OnVideoSavedCallback callback) {
        if (this.mVideoCapture != null) {
            if (getCaptureMode() == CameraView.CaptureMode.IMAGE) {
                throw new IllegalStateException("Can not record video under IMAGE capture mode.");
            } else if (callback != null) {
                this.mVideoIsRecording.set(true);
                this.mVideoCapture.lambda$startRecording$0(outputFileOptions, executor, new VideoCapture.OnVideoSavedCallback() {
                    public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
                        CameraXModule.this.mVideoIsRecording.set(false);
                        callback.onVideoSaved(outputFileResults);
                    }

                    public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                        CameraXModule.this.mVideoIsRecording.set(false);
                        Logger.e(CameraXModule.TAG, message, cause);
                        callback.onError(videoCaptureError, message, cause);
                    }
                });
            } else {
                throw new IllegalArgumentException("OnVideoSavedCallback should not be empty");
            }
        }
    }

    public void stopRecording() {
        VideoCapture videoCapture = this.mVideoCapture;
        if (videoCapture != null) {
            videoCapture.lambda$stopRecording$5();
        }
    }

    public boolean isRecording() {
        return this.mVideoIsRecording.get();
    }

    @SuppressLint({"MissingPermission"})
    public void setCameraLensFacing(@Nullable Integer lensFacing) {
        if (!Objects.equals(this.mCameraLensFacing, lensFacing)) {
            this.mCameraLensFacing = lensFacing;
            LifecycleOwner lifecycleOwner = this.mCurrentLifecycle;
            if (lifecycleOwner != null) {
                bindToLifecycle(lifecycleOwner);
            }
        }
    }

    @RequiresPermission("android.permission.CAMERA")
    public boolean hasCameraWithLensFacing(int lensFacing) {
        ProcessCameraProvider processCameraProvider = this.mCameraProvider;
        if (processCameraProvider == null) {
            return false;
        }
        try {
            return processCameraProvider.hasCamera(new CameraSelector.Builder().requireLensFacing(lensFacing).build());
        } catch (CameraInfoUnavailableException e) {
            return false;
        }
    }

    @Nullable
    public Integer getLensFacing() {
        return this.mCameraLensFacing;
    }

    public void toggleCamera() {
        Set<Integer> availableCameraLensFacing = getAvailableCameraLensFacing();
        if (!availableCameraLensFacing.isEmpty()) {
            Integer num = this.mCameraLensFacing;
            if (num == null) {
                setCameraLensFacing(availableCameraLensFacing.iterator().next());
            } else if (num.intValue() == 1 && availableCameraLensFacing.contains(0)) {
                setCameraLensFacing(0);
            } else if (this.mCameraLensFacing.intValue() == 0 && availableCameraLensFacing.contains(1)) {
                setCameraLensFacing(1);
            }
        }
    }

    public float getZoomRatio() {
        Camera camera = this.mCamera;
        if (camera != null) {
            return camera.getCameraInfo().getZoomState().getValue().getZoomRatio();
        }
        return 1.0f;
    }

    public void setZoomRatio(float zoomRatio) {
        Camera camera = this.mCamera;
        if (camera != null) {
            Futures.addCallback(camera.getCameraControl().setZoomRatio(zoomRatio), new FutureCallback<Void>() {
                public void onSuccess(@Nullable Void result) {
                }

                public void onFailure(Throwable t) {
                    throw new RuntimeException(t);
                }
            }, CameraXExecutors.directExecutor());
        } else {
            Logger.e(TAG, "Failed to set zoom ratio");
        }
    }

    public float getMinZoomRatio() {
        Camera camera = this.mCamera;
        if (camera != null) {
            return camera.getCameraInfo().getZoomState().getValue().getMinZoomRatio();
        }
        return 1.0f;
    }

    public float getMaxZoomRatio() {
        Camera camera = this.mCamera;
        if (camera != null) {
            return camera.getCameraInfo().getZoomState().getValue().getMaxZoomRatio();
        }
        return 1.0f;
    }

    public boolean isZoomSupported() {
        return getMaxZoomRatio() != 1.0f;
    }

    @SuppressLint({"MissingPermission"})
    private void rebindToLifecycle() {
        LifecycleOwner lifecycleOwner = this.mCurrentLifecycle;
        if (lifecycleOwner != null) {
            bindToLifecycle(lifecycleOwner);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isBoundToLifecycle() {
        return this.mCamera != null;
    }

    /* access modifiers changed from: package-private */
    public int getRelativeCameraOrientation(boolean compensateForMirroring) {
        Camera camera = this.mCamera;
        if (camera == null) {
            return 0;
        }
        int rotationDegrees = camera.getCameraInfo().getSensorRotationDegrees(getDisplaySurfaceRotation());
        if (compensateForMirroring) {
            return (360 - rotationDegrees) % 360;
        }
        return rotationDegrees;
    }

    public void invalidateView() {
        updateViewInfo();
    }

    /* access modifiers changed from: package-private */
    public void clearCurrentLifecycle() {
        if (!(this.mCurrentLifecycle == null || this.mCameraProvider == null)) {
            List<UseCase> toUnbind = new ArrayList<>();
            ImageCapture imageCapture = this.mImageCapture;
            if (imageCapture != null && this.mCameraProvider.isBound(imageCapture)) {
                toUnbind.add(this.mImageCapture);
            }
            VideoCapture videoCapture = this.mVideoCapture;
            if (videoCapture != null && this.mCameraProvider.isBound(videoCapture)) {
                toUnbind.add(this.mVideoCapture);
            }
            Preview preview = this.mPreview;
            if (preview != null && this.mCameraProvider.isBound(preview)) {
                toUnbind.add(this.mPreview);
            }
            if (!toUnbind.isEmpty()) {
                this.mCameraProvider.unbind((UseCase[]) toUnbind.toArray(new UseCase[0]));
            }
            Preview preview2 = this.mPreview;
            if (preview2 != null) {
                preview2.setSurfaceProvider((Preview.SurfaceProvider) null);
            }
        }
        this.mCamera = null;
        this.mCurrentLifecycle = null;
    }

    private void updateViewInfo() {
        ImageCapture imageCapture = this.mImageCapture;
        if (imageCapture != null) {
            imageCapture.setCropAspectRatio(new Rational(getWidth(), getHeight()));
            this.mImageCapture.setTargetRotation(getDisplaySurfaceRotation());
        }
        VideoCapture videoCapture = this.mVideoCapture;
        if (videoCapture != null) {
            videoCapture.setTargetRotation(getDisplaySurfaceRotation());
        }
    }

    @RequiresPermission("android.permission.CAMERA")
    private Set<Integer> getAvailableCameraLensFacing() {
        Set<Integer> available = new LinkedHashSet<>(Arrays.asList(LensFacingConverter.values()));
        if (this.mCurrentLifecycle != null) {
            if (!hasCameraWithLensFacing(1)) {
                available.remove(1);
            }
            if (!hasCameraWithLensFacing(0)) {
                available.remove(0);
            }
        }
        return available;
    }

    public int getFlash() {
        return this.mFlash;
    }

    public void setFlash(int flash) {
        this.mFlash = flash;
        ImageCapture imageCapture = this.mImageCapture;
        if (imageCapture != null) {
            imageCapture.setFlashMode(flash);
        }
    }

    public void enableTorch(boolean torch) {
        Camera camera = this.mCamera;
        if (camera != null) {
            Futures.addCallback(camera.getCameraControl().enableTorch(torch), new FutureCallback<Void>() {
                public void onSuccess(@Nullable Void result) {
                }

                public void onFailure(Throwable t) {
                    throw new RuntimeException(t);
                }
            }, CameraXExecutors.directExecutor());
        }
    }

    public boolean isTorchOn() {
        Camera camera = this.mCamera;
        if (camera != null && camera.getCameraInfo().getTorchState().getValue().intValue() == 1) {
            return true;
        }
        return false;
    }

    public Context getContext() {
        return this.mCameraView.getContext();
    }

    public int getWidth() {
        return this.mCameraView.getWidth();
    }

    public int getHeight() {
        return this.mCameraView.getHeight();
    }

    public int getDisplayRotationDegrees() {
        return CameraOrientationUtil.surfaceRotationToDegrees(getDisplaySurfaceRotation());
    }

    /* access modifiers changed from: protected */
    public int getDisplaySurfaceRotation() {
        return this.mCameraView.getDisplaySurfaceRotation();
    }

    private int getMeasuredWidth() {
        return this.mCameraView.getMeasuredWidth();
    }

    private int getMeasuredHeight() {
        return this.mCameraView.getMeasuredHeight();
    }

    @Nullable
    public Camera getCamera() {
        return this.mCamera;
    }

    @NonNull
    public CameraView.CaptureMode getCaptureMode() {
        return this.mCaptureMode;
    }

    public void setCaptureMode(@NonNull CameraView.CaptureMode captureMode) {
        this.mCaptureMode = captureMode;
        rebindToLifecycle();
    }

    public long getMaxVideoDuration() {
        return this.mMaxVideoDuration;
    }

    public void setMaxVideoDuration(long duration) {
        this.mMaxVideoDuration = duration;
    }

    public long getMaxVideoSize() {
        return this.mMaxVideoSize;
    }

    public void setMaxVideoSize(long size) {
        this.mMaxVideoSize = size;
    }

    public boolean isPaused() {
        return false;
    }
}
