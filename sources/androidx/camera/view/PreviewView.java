package androidx.camera.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Rational;
import android.util.Size;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.annotation.AnyThread;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;
import androidx.annotation.VisibleForTesting;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.ExperimentalUseCaseGroup;
import androidx.camera.core.Logger;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.ViewPort;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.view.internal.compat.quirk.DeviceQuirks;
import androidx.camera.view.internal.compat.quirk.SurfaceViewStretchedQuirk;
import androidx.camera.view.transform.OutputTransform;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.concurrent.atomic.AtomicReference;

public final class PreviewView extends FrameLayout {
    @ColorRes
    static final int DEFAULT_BACKGROUND_COLOR = 17170444;
    private static final ImplementationMode DEFAULT_IMPL_MODE = ImplementationMode.PERFORMANCE;
    private static final String TAG = "PreviewView";
    @Nullable
    final AtomicReference<PreviewStreamStateObserver> mActiveStreamStateObserver;
    CameraController mCameraController;
    @VisibleForTesting
    @Nullable
    PreviewViewImplementation mImplementation;
    @NonNull
    ImplementationMode mImplementationMode;
    private final View.OnLayoutChangeListener mOnLayoutChangeListener;
    @NonNull
    final MutableLiveData<StreamState> mPreviewStreamStateLiveData;
    @NonNull
    final PreviewTransformation mPreviewTransform;
    @NonNull
    PreviewViewMeteringPointFactory mPreviewViewMeteringPointFactory;
    @NonNull
    private final ScaleGestureDetector mScaleGestureDetector;
    final Preview.SurfaceProvider mSurfaceProvider;
    @Nullable
    private MotionEvent mTouchUpEvent;

    public enum StreamState {
        IDLE,
        STREAMING
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$new$0 */
    public /* synthetic */ void a(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if ((right - left == oldRight - oldLeft && bottom - top == oldBottom - oldTop) ? false : true) {
            redrawPreview();
            attachToControllerIfReady(true);
        }
    }

    @UiThread
    public PreviewView(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    @UiThread
    public PreviewView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @UiThread
    public PreviewView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    /* JADX INFO: finally extract failed */
    @UiThread
    public PreviewView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        ImplementationMode implementationMode = DEFAULT_IMPL_MODE;
        this.mImplementationMode = implementationMode;
        PreviewTransformation previewTransformation = new PreviewTransformation();
        this.mPreviewTransform = previewTransformation;
        this.mPreviewStreamStateLiveData = new MutableLiveData<>(StreamState.IDLE);
        this.mActiveStreamStateObserver = new AtomicReference<>();
        this.mPreviewViewMeteringPointFactory = new PreviewViewMeteringPointFactory(previewTransformation);
        this.mOnLayoutChangeListener = new k(this);
        this.mSurfaceProvider = new Preview.SurfaceProvider() {
            @OptIn(markerClass = {ExperimentalUseCaseGroup.class})
            @AnyThread
            public void onSurfaceRequested(@NonNull SurfaceRequest surfaceRequest) {
                PreviewViewImplementation previewViewImplementation;
                if (!Threads.isMainThread()) {
                    ContextCompat.getMainExecutor(PreviewView.this.getContext()).execute(new j(this, surfaceRequest));
                    return;
                }
                Logger.d(PreviewView.TAG, "Surface requested by Preview.");
                CameraInternal camera = surfaceRequest.getCamera();
                surfaceRequest.setTransformationInfoListener(ContextCompat.getMainExecutor(PreviewView.this.getContext()), new i(this, camera, surfaceRequest));
                PreviewView previewView = PreviewView.this;
                if (PreviewView.shouldUseTextureView(surfaceRequest, previewView.mImplementationMode)) {
                    PreviewView previewView2 = PreviewView.this;
                    previewViewImplementation = new TextureViewImplementation(previewView2, previewView2.mPreviewTransform);
                } else {
                    PreviewView previewView3 = PreviewView.this;
                    previewViewImplementation = new SurfaceViewImplementation(previewView3, previewView3.mPreviewTransform);
                }
                previewView.mImplementation = previewViewImplementation;
                CameraInfoInternal cameraInfoInternal = camera.getCameraInfoInternal();
                PreviewView previewView4 = PreviewView.this;
                PreviewStreamStateObserver streamStateObserver = new PreviewStreamStateObserver(cameraInfoInternal, previewView4.mPreviewStreamStateLiveData, previewView4.mImplementation);
                PreviewView.this.mActiveStreamStateObserver.set(streamStateObserver);
                camera.getCameraState().addObserver(ContextCompat.getMainExecutor(PreviewView.this.getContext()), streamStateObserver);
                PreviewView.this.mImplementation.onSurfaceRequested(surfaceRequest, new h(this, streamStateObserver, camera));
            }

            /* access modifiers changed from: private */
            /* renamed from: lambda$onSurfaceRequested$0 */
            public /* synthetic */ void a(SurfaceRequest surfaceRequest) {
                PreviewView.this.mSurfaceProvider.onSurfaceRequested(surfaceRequest);
            }

            /* access modifiers changed from: private */
            /* renamed from: lambda$onSurfaceRequested$1 */
            public /* synthetic */ void b(CameraInternal camera, SurfaceRequest surfaceRequest, SurfaceRequest.TransformationInfo transformationInfo) {
                Logger.d(PreviewView.TAG, "Preview transformation info updated. " + transformationInfo);
                PreviewView.this.mPreviewTransform.setTransformationInfo(transformationInfo, surfaceRequest.getResolution(), camera.getCameraInfoInternal().getLensFacing().intValue() == 0);
                PreviewView.this.redrawPreview();
            }

            /* access modifiers changed from: private */
            /* renamed from: lambda$onSurfaceRequested$2 */
            public /* synthetic */ void c(PreviewStreamStateObserver streamStateObserver, CameraInternal camera) {
                if (PreviewView.this.mActiveStreamStateObserver.compareAndSet(streamStateObserver, (Object) null)) {
                    streamStateObserver.updatePreviewStreamState(StreamState.IDLE);
                }
                streamStateObserver.clear();
                camera.getCameraState().removeObserver(streamStateObserver);
            }
        };
        Threads.checkMainThread();
        Resources.Theme theme = context.getTheme();
        int[] iArr = R.styleable.PreviewView;
        TypedArray attributes = theme.obtainStyledAttributes(attrs, iArr, defStyleAttr, defStyleRes);
        if (Build.VERSION.SDK_INT >= 29) {
            saveAttributeDataForStyleable(context, iArr, attrs, attributes, defStyleAttr, defStyleRes);
        }
        try {
            setScaleType(ScaleType.fromId(attributes.getInteger(R.styleable.PreviewView_scaleType, previewTransformation.getScaleType().getId())));
            setImplementationMode(ImplementationMode.fromId(attributes.getInteger(R.styleable.PreviewView_implementationMode, implementationMode.getId())));
            attributes.recycle();
            this.mScaleGestureDetector = new ScaleGestureDetector(context, new PinchToZoomOnScaleGestureListener());
            if (getBackground() == null) {
                setBackgroundColor(ContextCompat.getColor(getContext(), DEFAULT_BACKGROUND_COLOR));
            }
        } catch (Throwable th) {
            attributes.recycle();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        PreviewViewImplementation previewViewImplementation = this.mImplementation;
        if (previewViewImplementation != null) {
            previewViewImplementation.onAttachedToWindow();
        }
        attachToControllerIfReady(true);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
        PreviewViewImplementation previewViewImplementation = this.mImplementation;
        if (previewViewImplementation != null) {
            previewViewImplementation.onDetachedFromWindow();
        }
        CameraController cameraController = this.mCameraController;
        if (cameraController != null) {
            cameraController.clearPreviewSurface();
        }
    }

    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (this.mCameraController == null) {
            return super.onTouchEvent(event);
        }
        boolean isSingleTouch = event.getPointerCount() == 1;
        boolean isUpEvent = event.getAction() == 1;
        boolean notALongPress = event.getEventTime() - event.getDownTime() < ((long) ViewConfiguration.getLongPressTimeout());
        if (isSingleTouch && isUpEvent && notALongPress) {
            this.mTouchUpEvent = event;
            performClick();
            return true;
        } else if (this.mScaleGestureDetector.onTouchEvent(event) || super.onTouchEvent(event)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean performClick() {
        if (this.mCameraController != null) {
            MotionEvent motionEvent = this.mTouchUpEvent;
            float x = motionEvent != null ? motionEvent.getX() : ((float) getWidth()) / 2.0f;
            MotionEvent motionEvent2 = this.mTouchUpEvent;
            this.mCameraController.onTapToFocus(this.mPreviewViewMeteringPointFactory, x, motionEvent2 != null ? motionEvent2.getY() : ((float) getHeight()) / 2.0f);
        }
        this.mTouchUpEvent = null;
        return super.performClick();
    }

    @UiThread
    public void setImplementationMode(@NonNull ImplementationMode implementationMode) {
        Threads.checkMainThread();
        this.mImplementationMode = implementationMode;
    }

    @UiThread
    @NonNull
    public ImplementationMode getImplementationMode() {
        Threads.checkMainThread();
        return this.mImplementationMode;
    }

    @UiThread
    @NonNull
    @OptIn(markerClass = {ExperimentalUseCaseGroup.class})
    public Preview.SurfaceProvider getSurfaceProvider() {
        Threads.checkMainThread();
        return this.mSurfaceProvider;
    }

    @UiThread
    public void setScaleType(@NonNull ScaleType scaleType) {
        Threads.checkMainThread();
        this.mPreviewTransform.setScaleType(scaleType);
        redrawPreview();
        attachToControllerIfReady(false);
    }

    @UiThread
    @NonNull
    public ScaleType getScaleType() {
        Threads.checkMainThread();
        return this.mPreviewTransform.getScaleType();
    }

    @UiThread
    @NonNull
    public MeteringPointFactory getMeteringPointFactory() {
        Threads.checkMainThread();
        return this.mPreviewViewMeteringPointFactory;
    }

    @NonNull
    public LiveData<StreamState> getPreviewStreamState() {
        return this.mPreviewStreamStateLiveData;
    }

    @UiThread
    @Nullable
    public Bitmap getBitmap() {
        Threads.checkMainThread();
        PreviewViewImplementation previewViewImplementation = this.mImplementation;
        if (previewViewImplementation == null) {
            return null;
        }
        return previewViewImplementation.getBitmap();
    }

    @UiThread
    @ExperimentalUseCaseGroup
    @Nullable
    public ViewPort getViewPort() {
        Threads.checkMainThread();
        if (getDisplay() == null) {
            return null;
        }
        return getViewPort(getDisplay().getRotation());
    }

    @UiThread
    @SuppressLint({"WrongConstant"})
    @Nullable
    @ExperimentalUseCaseGroup
    public ViewPort getViewPort(int targetRotation) {
        Threads.checkMainThread();
        if (getWidth() == 0 || getHeight() == 0) {
            return null;
        }
        return new ViewPort.Builder(new Rational(getWidth(), getHeight()), targetRotation).setScaleType(getViewPortScaleType()).setLayoutDirection(getLayoutDirection()).build();
    }

    private int getViewPortScaleType() {
        switch (AnonymousClass2.$SwitchMap$androidx$camera$view$PreviewView$ScaleType[getScaleType().ordinal()]) {
            case 1:
                return 2;
            case 2:
                return 1;
            case 3:
                return 0;
            case 4:
            case 5:
            case 6:
                return 3;
            default:
                throw new IllegalStateException("Unexpected scale type: " + getScaleType());
        }
    }

    /* access modifiers changed from: package-private */
    public void redrawPreview() {
        PreviewViewImplementation previewViewImplementation = this.mImplementation;
        if (previewViewImplementation != null) {
            previewViewImplementation.redrawPreview();
        }
        this.mPreviewViewMeteringPointFactory.recalculate(new Size(getWidth(), getHeight()), getLayoutDirection());
    }

    static boolean shouldUseTextureView(@NonNull SurfaceRequest surfaceRequest, @NonNull ImplementationMode implementationMode) {
        boolean isLegacyDevice = surfaceRequest.getCamera().getCameraInfoInternal().getImplementationType().equals(CameraInfo.IMPLEMENTATION_TYPE_CAMERA2_LEGACY);
        boolean hasSurfaceViewQuirk = DeviceQuirks.get(SurfaceViewStretchedQuirk.class) != null;
        if (surfaceRequest.isRGBA8888Required() || Build.VERSION.SDK_INT <= 24 || isLegacyDevice || hasSurfaceViewQuirk) {
            return true;
        }
        switch (AnonymousClass2.$SwitchMap$androidx$camera$view$PreviewView$ImplementationMode[implementationMode.ordinal()]) {
            case 1:
                return true;
            case 2:
                return false;
            default:
                throw new IllegalArgumentException("Invalid implementation mode: " + implementationMode);
        }
    }

    /* renamed from: androidx.camera.view.PreviewView$2  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$view$PreviewView$ImplementationMode;
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$view$PreviewView$ScaleType;

        static {
            int[] iArr = new int[ImplementationMode.values().length];
            $SwitchMap$androidx$camera$view$PreviewView$ImplementationMode = iArr;
            try {
                iArr[ImplementationMode.COMPATIBLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$camera$view$PreviewView$ImplementationMode[ImplementationMode.PERFORMANCE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            int[] iArr2 = new int[ScaleType.values().length];
            $SwitchMap$androidx$camera$view$PreviewView$ScaleType = iArr2;
            try {
                iArr2[ScaleType.FILL_END.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$androidx$camera$view$PreviewView$ScaleType[ScaleType.FILL_CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$androidx$camera$view$PreviewView$ScaleType[ScaleType.FILL_START.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$androidx$camera$view$PreviewView$ScaleType[ScaleType.FIT_END.ordinal()] = 4;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$androidx$camera$view$PreviewView$ScaleType[ScaleType.FIT_CENTER.ordinal()] = 5;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$androidx$camera$view$PreviewView$ScaleType[ScaleType.FIT_START.ordinal()] = 6;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    public enum ImplementationMode {
        PERFORMANCE(0),
        COMPATIBLE(1);
        
        private final int mId;

        private ImplementationMode(int id) {
            this.mId = id;
        }

        /* access modifiers changed from: package-private */
        public int getId() {
            return this.mId;
        }

        static ImplementationMode fromId(int id) {
            for (ImplementationMode implementationMode : values()) {
                if (implementationMode.mId == id) {
                    return implementationMode;
                }
            }
            throw new IllegalArgumentException("Unknown implementation mode id " + id);
        }
    }

    public enum ScaleType {
        FILL_START(0),
        FILL_CENTER(1),
        FILL_END(2),
        FIT_START(3),
        FIT_CENTER(4),
        FIT_END(5);
        
        private final int mId;

        private ScaleType(int id) {
            this.mId = id;
        }

        /* access modifiers changed from: package-private */
        public int getId() {
            return this.mId;
        }

        static ScaleType fromId(int id) {
            for (ScaleType scaleType : values()) {
                if (scaleType.mId == id) {
                    return scaleType;
                }
            }
            throw new IllegalArgumentException("Unknown scale type id " + id);
        }
    }

    public class PinchToZoomOnScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        PinchToZoomOnScaleGestureListener() {
        }

        public boolean onScale(ScaleGestureDetector detector) {
            CameraController cameraController = PreviewView.this.mCameraController;
            if (cameraController == null) {
                return true;
            }
            cameraController.onPinchToZoom(detector.getScaleFactor());
            return true;
        }
    }

    @UiThread
    public void setController(@Nullable CameraController cameraController) {
        Threads.checkMainThread();
        CameraController cameraController2 = this.mCameraController;
        if (!(cameraController2 == null || cameraController2 == cameraController)) {
            cameraController2.clearPreviewSurface();
        }
        this.mCameraController = cameraController;
        attachToControllerIfReady(false);
    }

    @UiThread
    @Nullable
    public CameraController getController() {
        Threads.checkMainThread();
        return this.mCameraController;
    }

    @TransformExperimental
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public OutputTransform getOutputTransform() {
        Threads.checkMainThread();
        Matrix matrix = null;
        try {
            matrix = this.mPreviewTransform.getSurfaceToPreviewViewMatrix(new Size(getWidth(), getHeight()), getLayoutDirection());
        } catch (IllegalStateException e) {
        }
        Rect surfaceCropRect = this.mPreviewTransform.getSurfaceCropRect();
        if (matrix == null || surfaceCropRect == null) {
            Logger.d(TAG, "Transform info is not ready");
            return null;
        }
        matrix.preConcat(TransformUtils.getNormalizedToBuffer(surfaceCropRect));
        if (this.mImplementation instanceof TextureViewImplementation) {
            matrix.postConcat(getMatrix());
        } else {
            Logger.w(TAG, "PreviewView needs to be in COMPATIBLE mode for the transform to work correctly.");
        }
        return new OutputTransform(matrix, new Size(surfaceCropRect.width(), surfaceCropRect.height()));
    }

    @OptIn(markerClass = {ExperimentalUseCaseGroup.class})
    private void attachToControllerIfReady(boolean shouldFailSilently) {
        Display display = getDisplay();
        ViewPort viewPort = getViewPort();
        if (this.mCameraController != null && viewPort != null && isAttachedToWindow() && display != null) {
            try {
                this.mCameraController.attachPreviewSurface(getSurfaceProvider(), viewPort, display);
            } catch (IllegalStateException ex) {
                if (shouldFailSilently) {
                    Logger.e(TAG, ex.getMessage(), ex);
                    return;
                }
                throw ex;
            }
        }
    }
}
