package androidx.camera.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.annotation.RestrictTo;
import androidx.camera.core.Camera;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.FocusMeteringResult;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Logger;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.impl.LensFacingConverter;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.view.PreviewView;
import androidx.camera.view.video.ExperimentalVideo;
import androidx.camera.view.video.OnVideoSavedCallback;
import androidx.camera.view.video.OutputFileOptions;
import androidx.camera.view.video.OutputFileResults;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import com.google.firebase.messaging.Constants;
import java.io.File;
import java.util.concurrent.Executor;

@Deprecated
public final class CameraView extends FrameLayout {
    private static final String EXTRA_CAMERA_DIRECTION = "camera_direction";
    private static final String EXTRA_CAPTURE_MODE = "captureMode";
    private static final String EXTRA_FLASH = "flash";
    private static final String EXTRA_MAX_VIDEO_DURATION = "max_video_duration";
    private static final String EXTRA_MAX_VIDEO_SIZE = "max_video_size";
    private static final String EXTRA_PINCH_TO_ZOOM_ENABLED = "pinch_to_zoom_enabled";
    private static final String EXTRA_SCALE_TYPE = "scale_type";
    private static final String EXTRA_SUPER = "super";
    private static final String EXTRA_ZOOM_RATIO = "zoom_ratio";
    private static final int FLASH_MODE_AUTO = 1;
    private static final int FLASH_MODE_OFF = 4;
    private static final int FLASH_MODE_ON = 2;
    static final int INDEFINITE_VIDEO_DURATION = -1;
    static final int INDEFINITE_VIDEO_SIZE = -1;
    private static final int LENS_FACING_BACK = 2;
    private static final int LENS_FACING_FRONT = 1;
    private static final int LENS_FACING_NONE = 0;
    static final String TAG = CameraView.class.getSimpleName();
    CameraXModule mCameraModule;
    private final DisplayManager.DisplayListener mDisplayListener;
    private long mDownEventTimestamp;
    private boolean mIsPinchToZoomEnabled;
    private PinchToZoomGestureDetector mPinchToZoomGestureDetector;
    private PreviewView mPreviewView;
    private MotionEvent mUpEvent;

    public CameraView(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public CameraView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mIsPinchToZoomEnabled = true;
        this.mDisplayListener = new DisplayManager.DisplayListener() {
            public void onDisplayAdded(int displayId) {
            }

            public void onDisplayRemoved(int displayId) {
            }

            public void onDisplayChanged(int displayId) {
                CameraView.this.mCameraModule.invalidateView();
            }
        };
        init(context, attrs);
    }

    @RequiresApi(21)
    public CameraView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mIsPinchToZoomEnabled = true;
        this.mDisplayListener = new DisplayManager.DisplayListener() {
            public void onDisplayAdded(int displayId) {
            }

            public void onDisplayRemoved(int displayId) {
            }

            public void onDisplayChanged(int displayId) {
                CameraView.this.mCameraModule.invalidateView();
            }
        };
        init(context, attrs);
    }

    @RequiresPermission("android.permission.CAMERA")
    public void bindToLifecycle(@NonNull LifecycleOwner lifecycleOwner) {
        this.mCameraModule.bindToLifecycle(lifecycleOwner);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        PreviewView previewView = new PreviewView(getContext());
        this.mPreviewView = previewView;
        addView(previewView, 0);
        this.mCameraModule = new CameraXModule(this);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CameraView);
            setScaleType(PreviewView.ScaleType.fromId(a.getInteger(R.styleable.CameraView_scaleType, getScaleType().getId())));
            setPinchToZoomEnabled(a.getBoolean(R.styleable.CameraView_pinchToZoomEnabled, isPinchToZoomEnabled()));
            setCaptureMode(CaptureMode.fromId(a.getInteger(R.styleable.CameraView_captureMode, getCaptureMode().getId())));
            switch (a.getInt(R.styleable.CameraView_lensFacing, 2)) {
                case 0:
                    setCameraLensFacing((Integer) null);
                    break;
                case 1:
                    setCameraLensFacing(0);
                    break;
                case 2:
                    setCameraLensFacing(1);
                    break;
            }
            switch (a.getInt(R.styleable.CameraView_flash, 0)) {
                case 1:
                    setFlash(0);
                    break;
                case 2:
                    setFlash(1);
                    break;
                case 4:
                    setFlash(2);
                    break;
            }
            a.recycle();
        }
        if (getBackground() == null) {
            setBackgroundColor(-15658735);
        }
        this.mPinchToZoomGestureDetector = new PinchToZoomGestureDetector(this, context);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public FrameLayout.LayoutParams generateDefaultLayoutParams() {
        return new FrameLayout.LayoutParams(-1, -1);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Parcelable onSaveInstanceState() {
        Bundle state = new Bundle();
        state.putParcelable(EXTRA_SUPER, super.onSaveInstanceState());
        state.putInt(EXTRA_SCALE_TYPE, getScaleType().getId());
        state.putFloat(EXTRA_ZOOM_RATIO, getZoomRatio());
        state.putBoolean(EXTRA_PINCH_TO_ZOOM_ENABLED, isPinchToZoomEnabled());
        state.putString(EXTRA_FLASH, FlashModeConverter.nameOf(getFlash()));
        state.putLong(EXTRA_MAX_VIDEO_DURATION, getMaxVideoDuration());
        state.putLong(EXTRA_MAX_VIDEO_SIZE, getMaxVideoSize());
        if (getCameraLensFacing() != null) {
            state.putString(EXTRA_CAMERA_DIRECTION, LensFacingConverter.nameOf(getCameraLensFacing().intValue()));
        }
        state.putInt(EXTRA_CAPTURE_MODE, getCaptureMode().getId());
        return state;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(@Nullable Parcelable savedState) {
        Integer num;
        if (savedState instanceof Bundle) {
            Bundle state = (Bundle) savedState;
            super.onRestoreInstanceState(state.getParcelable(EXTRA_SUPER));
            setScaleType(PreviewView.ScaleType.fromId(state.getInt(EXTRA_SCALE_TYPE)));
            setZoomRatio(state.getFloat(EXTRA_ZOOM_RATIO));
            setPinchToZoomEnabled(state.getBoolean(EXTRA_PINCH_TO_ZOOM_ENABLED));
            setFlash(FlashModeConverter.valueOf(state.getString(EXTRA_FLASH)));
            setMaxVideoDuration(state.getLong(EXTRA_MAX_VIDEO_DURATION));
            setMaxVideoSize(state.getLong(EXTRA_MAX_VIDEO_SIZE));
            String lensFacingString = state.getString(EXTRA_CAMERA_DIRECTION);
            if (TextUtils.isEmpty(lensFacingString)) {
                num = null;
            } else {
                num = Integer.valueOf(LensFacingConverter.valueOf(lensFacingString));
            }
            setCameraLensFacing(num);
            setCaptureMode(CaptureMode.fromId(state.getInt(EXTRA_CAPTURE_MODE)));
            return;
        }
        super.onRestoreInstanceState(savedState);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((DisplayManager) getContext().getSystemService(Constants.ScionAnalytics.MessageType.DISPLAY_NOTIFICATION)).registerDisplayListener(this.mDisplayListener, new Handler(Looper.getMainLooper()));
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((DisplayManager) getContext().getSystemService(Constants.ScionAnalytics.MessageType.DISPLAY_NOTIFICATION)).unregisterDisplayListener(this.mDisplayListener);
    }

    @NonNull
    public LiveData<PreviewView.StreamState> getPreviewStreamState() {
        return this.mPreviewView.getPreviewStreamState();
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public PreviewView getPreviewView() {
        return this.mPreviewView;
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"MissingPermission"})
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getMeasuredWidth() > 0 && getMeasuredHeight() > 0) {
            this.mCameraModule.bindToLifecycleAfterViewMeasured();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"MissingPermission"})
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.mCameraModule.bindToLifecycleAfterViewMeasured();
        this.mCameraModule.invalidateView();
        super.onLayout(changed, left, top, right, bottom);
    }

    /* access modifiers changed from: package-private */
    public int getDisplaySurfaceRotation() {
        Display display = getDisplay();
        if (display == null) {
            return 0;
        }
        return display.getRotation();
    }

    @NonNull
    public PreviewView.ScaleType getScaleType() {
        return this.mPreviewView.getScaleType();
    }

    public void setScaleType(@NonNull PreviewView.ScaleType scaleType) {
        this.mPreviewView.setScaleType(scaleType);
    }

    @NonNull
    public CaptureMode getCaptureMode() {
        return this.mCameraModule.getCaptureMode();
    }

    public void setCaptureMode(@NonNull CaptureMode captureMode) {
        this.mCameraModule.setCaptureMode(captureMode);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public long getMaxVideoDuration() {
        return this.mCameraModule.getMaxVideoDuration();
    }

    private void setMaxVideoDuration(long duration) {
        this.mCameraModule.setMaxVideoDuration(duration);
    }

    private long getMaxVideoSize() {
        return this.mCameraModule.getMaxVideoSize();
    }

    private void setMaxVideoSize(long size) {
        this.mCameraModule.setMaxVideoSize(size);
    }

    public void takePicture(@NonNull Executor executor, @NonNull ImageCapture.OnImageCapturedCallback callback) {
        this.mCameraModule.takePicture(executor, callback);
    }

    public void takePicture(@NonNull ImageCapture.OutputFileOptions outputFileOptions, @NonNull Executor executor, @NonNull ImageCapture.OnImageSavedCallback callback) {
        this.mCameraModule.takePicture(outputFileOptions, executor, callback);
    }

    @ExperimentalVideo
    public void startRecording(@NonNull File file, @NonNull Executor executor, @NonNull OnVideoSavedCallback callback) {
        startRecording(OutputFileOptions.builder(file).build(), executor, callback);
    }

    @ExperimentalVideo
    public void startRecording(@NonNull ParcelFileDescriptor fd, @NonNull Executor executor, @NonNull OnVideoSavedCallback callback) {
        startRecording(OutputFileOptions.builder(fd).build(), executor, callback);
    }

    @ExperimentalVideo
    public void startRecording(@NonNull OutputFileOptions outputFileOptions, @NonNull Executor executor, @NonNull final OnVideoSavedCallback callback) {
        this.mCameraModule.startRecording(outputFileOptions.toVideoCaptureOutputFileOptions(), executor, new VideoCapture.OnVideoSavedCallback() {
            public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
                callback.onVideoSaved(OutputFileResults.create(outputFileResults.getSavedUri()));
            }

            public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                callback.onError(videoCaptureError, message, cause);
            }
        });
    }

    @ExperimentalVideo
    public void stopRecording() {
        this.mCameraModule.stopRecording();
    }

    @ExperimentalVideo
    public boolean isRecording() {
        return this.mCameraModule.isRecording();
    }

    @RequiresPermission("android.permission.CAMERA")
    public boolean hasCameraWithLensFacing(int lensFacing) {
        return this.mCameraModule.hasCameraWithLensFacing(lensFacing);
    }

    public void toggleCamera() {
        this.mCameraModule.toggleCamera();
    }

    public void setCameraLensFacing(@Nullable Integer lensFacing) {
        this.mCameraModule.setCameraLensFacing(lensFacing);
    }

    @Nullable
    public Integer getCameraLensFacing() {
        return this.mCameraModule.getLensFacing();
    }

    public int getFlash() {
        return this.mCameraModule.getFlash();
    }

    public void setFlash(int flashMode) {
        this.mCameraModule.setFlash(flashMode);
    }

    private long delta() {
        return System.currentTimeMillis() - this.mDownEventTimestamp;
    }

    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (this.mCameraModule.isPaused()) {
            return false;
        }
        if (isPinchToZoomEnabled()) {
            this.mPinchToZoomGestureDetector.onTouchEvent(event);
        }
        if (event.getPointerCount() == 2 && isPinchToZoomEnabled() && isZoomSupported()) {
            return true;
        }
        switch (event.getAction()) {
            case 0:
                this.mDownEventTimestamp = System.currentTimeMillis();
                break;
            case 1:
                if (delta() < ((long) ViewConfiguration.getLongPressTimeout()) && this.mCameraModule.isBoundToLifecycle()) {
                    this.mUpEvent = event;
                    performClick();
                    break;
                }
            default:
                return false;
        }
        return true;
    }

    public boolean performClick() {
        super.performClick();
        MotionEvent motionEvent = this.mUpEvent;
        float x = motionEvent != null ? motionEvent.getX() : getX() + (((float) getWidth()) / 2.0f);
        MotionEvent motionEvent2 = this.mUpEvent;
        float y = motionEvent2 != null ? motionEvent2.getY() : getY() + (((float) getHeight()) / 2.0f);
        this.mUpEvent = null;
        Camera camera = this.mCameraModule.getCamera();
        if (camera != null) {
            MeteringPointFactory pointFactory = this.mPreviewView.getMeteringPointFactory();
            Futures.addCallback(camera.getCameraControl().startFocusAndMetering(new FocusMeteringAction.Builder(pointFactory.createPoint(x, y, 0.16666667f), 1).addPoint(pointFactory.createPoint(x, y, 1.5f * 0.16666667f), 2).build()), new FutureCallback<FocusMeteringResult>() {
                public void onSuccess(@Nullable FocusMeteringResult result) {
                }

                public void onFailure(Throwable t) {
                    throw new RuntimeException(t);
                }
            }, CameraXExecutors.directExecutor());
        } else {
            Logger.d(TAG, "cannot access camera");
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public float rangeLimit(float val, float max, float min) {
        return Math.min(Math.max(val, min), max);
    }

    public boolean isPinchToZoomEnabled() {
        return this.mIsPinchToZoomEnabled;
    }

    public void setPinchToZoomEnabled(boolean enabled) {
        this.mIsPinchToZoomEnabled = enabled;
    }

    public float getZoomRatio() {
        return this.mCameraModule.getZoomRatio();
    }

    public void setZoomRatio(float zoomRatio) {
        this.mCameraModule.setZoomRatio(zoomRatio);
    }

    public float getMinZoomRatio() {
        return this.mCameraModule.getMinZoomRatio();
    }

    public float getMaxZoomRatio() {
        return this.mCameraModule.getMaxZoomRatio();
    }

    public boolean isZoomSupported() {
        return this.mCameraModule.isZoomSupported();
    }

    public void enableTorch(boolean torch) {
        this.mCameraModule.enableTorch(torch);
    }

    public boolean isTorchOn() {
        return this.mCameraModule.isTorchOn();
    }

    public enum CaptureMode {
        IMAGE(0),
        VIDEO(1),
        MIXED(2);
        
        private final int mId;

        /* access modifiers changed from: package-private */
        public int getId() {
            return this.mId;
        }

        private CaptureMode(int id) {
            this.mId = id;
        }

        static CaptureMode fromId(int id) {
            for (CaptureMode f : values()) {
                if (f.mId == id) {
                    return f;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    public static class S extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleGestureDetector.OnScaleGestureListener mListener;

        S() {
        }

        /* access modifiers changed from: package-private */
        public void setRealGestureDetector(ScaleGestureDetector.OnScaleGestureListener l) {
            this.mListener = l;
        }

        public boolean onScale(ScaleGestureDetector detector) {
            return this.mListener.onScale(detector);
        }
    }

    public class PinchToZoomGestureDetector extends ScaleGestureDetector implements ScaleGestureDetector.OnScaleGestureListener {
        PinchToZoomGestureDetector(CameraView cameraView, Context context) {
            this(context, new S());
        }

        PinchToZoomGestureDetector(Context context, S s) {
            super(context, s);
            s.setRealGestureDetector(this);
        }

        public boolean onScale(ScaleGestureDetector detector) {
            float scale;
            float scale2 = detector.getScaleFactor();
            if (scale2 > 1.0f) {
                scale = ((scale2 - 1.0f) * 2.0f) + 1.0f;
            } else {
                scale = 1.0f - ((1.0f - scale2) * 2.0f);
            }
            CameraView cameraView = CameraView.this;
            CameraView.this.setZoomRatio(cameraView.rangeLimit(CameraView.this.getZoomRatio() * scale, cameraView.getMaxZoomRatio(), CameraView.this.getMinZoomRatio()));
            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    }
}
