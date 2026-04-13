package androidx.camera.core;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import androidx.annotation.GuardedBy;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;
import androidx.annotation.VisibleForTesting;
import androidx.camera.core.ForwardingImageProxy;
import androidx.camera.core.ImageSaver;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.camera.core.impl.CameraCaptureMetaData;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CaptureBundle;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.CaptureProcessor;
import androidx.camera.core.impl.CaptureStage;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ConfigProvider;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageCaptureConfig;
import androidx.camera.core.impl.ImageInputConfig;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.utils.CameraOrientationUtil;
import androidx.camera.core.impl.utils.Exif;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.impl.x;
import androidx.camera.core.internal.IoConfig;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.UseCaseEventConfig;
import androidx.camera.core.internal.YuvToJpegProcessor;
import androidx.camera.core.internal.compat.quirk.DeviceQuirks;
import androidx.camera.core.internal.compat.quirk.ImageCaptureWashedOutImageQuirk;
import androidx.camera.core.internal.compat.workaround.ExifRotationAvailability;
import androidx.camera.core.internal.utils.ImageUtil;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ImageCapture extends UseCase {
    public static final int CAPTURE_MODE_MAXIMIZE_QUALITY = 0;
    public static final int CAPTURE_MODE_MINIMIZE_LATENCY = 1;
    private static final long CHECK_3A_TIMEOUT_IN_MS = 1000;
    private static final int DEFAULT_CAPTURE_MODE = 1;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final Defaults DEFAULT_CONFIG = new Defaults();
    private static final int DEFAULT_FLASH_MODE = 2;
    public static final int ERROR_CAMERA_CLOSED = 3;
    public static final int ERROR_CAPTURE_FAILED = 2;
    public static final int ERROR_FILE_IO = 1;
    public static final int ERROR_INVALID_CAMERA = 4;
    public static final int ERROR_UNKNOWN = 0;
    public static final int FLASH_MODE_AUTO = 0;
    public static final int FLASH_MODE_OFF = 2;
    public static final int FLASH_MODE_ON = 1;
    private static final int FLASH_MODE_UNKNOWN = -1;
    private static final byte JPEG_QUALITY_MAXIMIZE_QUALITY_MODE = 100;
    private static final byte JPEG_QUALITY_MINIMIZE_LATENCY_MODE = 95;
    private static final int MAX_IMAGES = 2;
    private static final String TAG = "ImageCapture";
    private CaptureBundle mCaptureBundle;
    private CaptureConfig mCaptureConfig;
    private final int mCaptureMode;
    private CaptureProcessor mCaptureProcessor;
    private final ImageReaderProxy.OnImageAvailableListener mClosingListener = n0.a;
    private Rational mCropAspectRatio = null;
    private DeferrableSurface mDeferrableSurface;
    private final boolean mEnableCheck3AConverged;
    private ExecutorService mExecutor;
    @GuardedBy("mLockedFlashMode")
    private int mFlashMode = -1;
    private ImageCaptureRequestProcessor mImageCaptureRequestProcessor;
    SafeCloseImageReaderProxy mImageReader;
    @NonNull
    final Executor mIoExecutor;
    @GuardedBy("mLockedFlashMode")
    private final AtomicReference<Integer> mLockedFlashMode = new AtomicReference<>((Object) null);
    private int mMaxCaptureStages;
    private CameraCaptureCallback mMetadataMatchingCaptureCallback;
    ProcessingImageReader mProcessingImageReader;
    final Executor mSequentialIoExecutor;
    private final CaptureCallbackChecker mSessionCallbackChecker = new CaptureCallbackChecker();
    SessionConfig.Builder mSessionConfigBuilder;
    private boolean mUseSoftwareJpeg;
    private final boolean mUseTorchFlash;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CaptureMode {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FlashMode {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ImageCaptureError {
    }

    public interface OnImageSavedCallback {
        void onError(@NonNull ImageCaptureException imageCaptureException);

        void onImageSaved(@NonNull OutputFileResults outputFileResults);
    }

    static /* synthetic */ void lambda$new$0(ImageReaderProxy imageReader) {
        ImageProxy image;
        try {
            image = imageReader.acquireLatestImage();
            Log.d(TAG, "Discarding ImageProxy which was inadvertently acquired: " + image);
            if (image != null) {
                image.close();
                return;
            }
            return;
        } catch (IllegalStateException e) {
            Log.e(TAG, "Failed to acquire latest image.", e);
            return;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    ImageCapture(@NonNull ImageCaptureConfig userConfig) {
        super(userConfig);
        boolean z = false;
        this.mUseSoftwareJpeg = false;
        ImageCaptureConfig useCaseConfig = (ImageCaptureConfig) getCurrentConfig();
        if (useCaseConfig.containsOption(ImageCaptureConfig.OPTION_IMAGE_CAPTURE_MODE)) {
            this.mCaptureMode = useCaseConfig.getCaptureMode();
        } else {
            this.mCaptureMode = 1;
        }
        Executor executor = (Executor) Preconditions.checkNotNull(useCaseConfig.getIoExecutor(CameraXExecutors.ioExecutor()));
        this.mIoExecutor = executor;
        this.mSequentialIoExecutor = CameraXExecutors.newSequentialExecutor(executor);
        if (this.mCaptureMode == 0) {
            this.mEnableCheck3AConverged = true;
        } else {
            this.mEnableCheck3AConverged = false;
        }
        z = DeviceQuirks.get(ImageCaptureWashedOutImageQuirk.class) != null ? true : z;
        this.mUseTorchFlash = z;
        if (z) {
            Logger.d(TAG, "Open and close torch to replace the flash fired by flash mode.");
        }
    }

    /* access modifiers changed from: package-private */
    @UiThread
    public SessionConfig.Builder createPipeline(@NonNull String cameraId, @NonNull ImageCaptureConfig config, @NonNull Size resolution) {
        int outputFormat;
        Threads.checkMainThread();
        SessionConfig.Builder sessionConfigBuilder = SessionConfig.Builder.createFrom(config);
        sessionConfigBuilder.addRepeatingCameraCaptureCallback(this.mSessionCallbackChecker);
        if (config.getImageReaderProxyProvider() != null) {
            this.mImageReader = new SafeCloseImageReaderProxy(config.getImageReaderProxyProvider().newInstance(resolution.getWidth(), resolution.getHeight(), getImageFormat(), 2, 0));
            this.mMetadataMatchingCaptureCallback = new CameraCaptureCallback() {
            };
        } else if (this.mCaptureProcessor != null || this.mUseSoftwareJpeg) {
            YuvToJpegProcessor softwareJpegProcessor = null;
            CaptureProcessorPipeline captureProcessorPipeline = null;
            CaptureProcessor captureProcessor = this.mCaptureProcessor;
            int inputFormat = getImageFormat();
            int outputFormat2 = getImageFormat();
            if (!this.mUseSoftwareJpeg) {
                outputFormat = outputFormat2;
            } else if (Build.VERSION.SDK_INT >= 26) {
                Logger.i(TAG, "Using software JPEG encoder.");
                if (this.mCaptureProcessor != null) {
                    softwareJpegProcessor = new YuvToJpegProcessor(getJpegQuality(), this.mMaxCaptureStages);
                    CaptureProcessorPipeline captureProcessorPipeline2 = new CaptureProcessorPipeline(this.mCaptureProcessor, this.mMaxCaptureStages, softwareJpegProcessor, this.mExecutor);
                    captureProcessorPipeline = captureProcessorPipeline2;
                    captureProcessor = captureProcessorPipeline2;
                } else {
                    YuvToJpegProcessor yuvToJpegProcessor = new YuvToJpegProcessor(getJpegQuality(), this.mMaxCaptureStages);
                    softwareJpegProcessor = yuvToJpegProcessor;
                    captureProcessor = yuvToJpegProcessor;
                }
                outputFormat = 256;
            } else {
                throw new IllegalStateException("Software JPEG only supported on API 26+");
            }
            ProcessingImageReader processingImageReader = r6;
            ProcessingImageReader processingImageReader2 = new ProcessingImageReader(resolution.getWidth(), resolution.getHeight(), inputFormat, this.mMaxCaptureStages, this.mExecutor, getCaptureBundle(CaptureBundles.singleDefaultCaptureBundle()), captureProcessor, outputFormat);
            this.mProcessingImageReader = processingImageReader;
            this.mMetadataMatchingCaptureCallback = processingImageReader.getCameraCaptureCallback();
            this.mImageReader = new SafeCloseImageReaderProxy(this.mProcessingImageReader);
            if (softwareJpegProcessor != null) {
                this.mProcessingImageReader.getCloseFuture().addListener(new p0(softwareJpegProcessor, captureProcessorPipeline), CameraXExecutors.directExecutor());
            }
        } else {
            MetadataImageReader metadataImageReader = new MetadataImageReader(resolution.getWidth(), resolution.getHeight(), getImageFormat(), 2);
            this.mMetadataMatchingCaptureCallback = metadataImageReader.getCameraCaptureCallback();
            this.mImageReader = new SafeCloseImageReaderProxy(metadataImageReader);
        }
        this.mImageCaptureRequestProcessor = new ImageCaptureRequestProcessor(2, new w(this));
        this.mImageReader.setOnImageAvailableListener(this.mClosingListener, CameraXExecutors.mainThreadExecutor());
        SafeCloseImageReaderProxy imageReaderProxy = this.mImageReader;
        DeferrableSurface deferrableSurface = this.mDeferrableSurface;
        if (deferrableSurface != null) {
            deferrableSurface.close();
        }
        ImmediateSurface immediateSurface = new ImmediateSurface(this.mImageReader.getSurface());
        this.mDeferrableSurface = immediateSurface;
        ListenableFuture<Void> terminationFuture = immediateSurface.getTerminationFuture();
        Objects.requireNonNull(imageReaderProxy);
        terminationFuture.addListener(new z1(imageReaderProxy), CameraXExecutors.mainThreadExecutor());
        sessionConfigBuilder.addNonRepeatingSurface(this.mDeferrableSurface);
        sessionConfigBuilder.addErrorListener(new e0(this, cameraId, config, resolution));
        return sessionConfigBuilder;
    }

    static /* synthetic */ void lambda$createPipeline$1(YuvToJpegProcessor processorToClose, CaptureProcessorPipeline captureProcessorPipelineToClose) {
        if (Build.VERSION.SDK_INT >= 26) {
            processorToClose.close();
            captureProcessorPipelineToClose.close();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$createPipeline$3 */
    public /* synthetic */ void b(String cameraId, ImageCaptureConfig config, Size resolution, SessionConfig sessionConfig, SessionConfig.SessionError error) {
        clearPipeline();
        if (isCurrentCamera(cameraId)) {
            SessionConfig.Builder createPipeline = createPipeline(cameraId, config, resolution);
            this.mSessionConfigBuilder = createPipeline;
            updateSessionConfig(createPipeline.build());
            notifyReset();
        }
    }

    /* access modifiers changed from: package-private */
    @UiThread
    public void clearPipeline() {
        Threads.checkMainThread();
        DeferrableSurface deferrableSurface = this.mDeferrableSurface;
        this.mDeferrableSurface = null;
        this.mImageReader = null;
        this.mProcessingImageReader = null;
        if (deferrableSurface != null) {
            deferrableSurface.close();
        }
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfig<?> getDefaultConfig(boolean applyDefaultConfig, @NonNull UseCaseConfigFactory factory) {
        Config captureConfig = factory.getConfig(UseCaseConfigFactory.CaptureType.IMAGE_CAPTURE);
        if (applyDefaultConfig) {
            captureConfig = x.b(captureConfig, DEFAULT_CONFIG.getConfig());
        }
        if (captureConfig == null) {
            return null;
        }
        return getUseCaseConfigBuilder(captureConfig).getUseCaseConfig();
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfig.Builder<?, ?, ?> getUseCaseConfigBuilder(@NonNull Config config) {
        return Builder.fromConfig(config);
    }

    /* JADX WARNING: type inference failed for: r10v0, types: [androidx.camera.core.impl.UseCaseConfig$Builder, androidx.camera.core.ExtendableBuilder, androidx.camera.core.impl.UseCaseConfig$Builder<?, ?, ?>] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Unknown variable types count: 1 */
    @androidx.annotation.NonNull
    @androidx.annotation.RestrictTo({androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.camera.core.impl.UseCaseConfig<?> onMergeConfig(@androidx.annotation.NonNull androidx.camera.core.impl.CameraInfoInternal r9, @androidx.annotation.NonNull androidx.camera.core.impl.UseCaseConfig.Builder<?, ?, ?> r10) {
        /*
            r8 = this;
            androidx.camera.core.impl.UseCaseConfig r0 = r10.getUseCaseConfig()
            androidx.camera.core.impl.Config$Option<androidx.camera.core.impl.CaptureProcessor> r1 = androidx.camera.core.impl.ImageCaptureConfig.OPTION_CAPTURE_PROCESSOR
            r2 = 0
            java.lang.Object r0 = r0.retrieveOption(r1, r2)
            java.lang.String r3 = "ImageCapture"
            r4 = 1
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r4)
            if (r0 == 0) goto L_0x0029
            int r0 = android.os.Build.VERSION.SDK_INT
            r6 = 29
            if (r0 < r6) goto L_0x0029
            java.lang.String r0 = "Requesting software JPEG due to a CaptureProcessor is set."
            androidx.camera.core.Logger.i(r3, r0)
            androidx.camera.core.impl.MutableConfig r0 = r10.getMutableConfig()
            androidx.camera.core.impl.Config$Option<java.lang.Boolean> r3 = androidx.camera.core.impl.ImageCaptureConfig.OPTION_USE_SOFTWARE_JPEG_ENCODER
            r0.insertOption(r3, r5)
            goto L_0x005a
        L_0x0029:
            androidx.camera.core.impl.Quirks r0 = r9.getCameraQuirks()
            java.lang.Class<androidx.camera.core.internal.compat.quirk.SoftwareJpegEncodingPreferredQuirk> r6 = androidx.camera.core.internal.compat.quirk.SoftwareJpegEncodingPreferredQuirk.class
            boolean r0 = r0.contains(r6)
            if (r0 == 0) goto L_0x005a
            androidx.camera.core.impl.MutableConfig r0 = r10.getMutableConfig()
            androidx.camera.core.impl.Config$Option<java.lang.Boolean> r6 = androidx.camera.core.impl.ImageCaptureConfig.OPTION_USE_SOFTWARE_JPEG_ENCODER
            java.lang.Object r0 = r0.retrieveOption(r6, r5)
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 != 0) goto L_0x004e
            java.lang.String r0 = "Device quirk suggests software JPEG encoder, but it has been explicitly disabled."
            androidx.camera.core.Logger.w(r3, r0)
            goto L_0x005a
        L_0x004e:
            java.lang.String r0 = "Requesting software JPEG due to device quirk."
            androidx.camera.core.Logger.i(r3, r0)
            androidx.camera.core.impl.MutableConfig r0 = r10.getMutableConfig()
            r0.insertOption(r6, r5)
        L_0x005a:
            androidx.camera.core.impl.MutableConfig r0 = r10.getMutableConfig()
            boolean r0 = enforceSoftwareJpegConstraints(r0)
            androidx.camera.core.impl.MutableConfig r3 = r10.getMutableConfig()
            androidx.camera.core.impl.Config$Option<java.lang.Integer> r5 = androidx.camera.core.impl.ImageCaptureConfig.OPTION_BUFFER_FORMAT
            java.lang.Object r3 = r3.retrieveOption(r5, r2)
            java.lang.Integer r3 = (java.lang.Integer) r3
            r5 = 0
            r6 = 35
            if (r3 == 0) goto L_0x009b
            androidx.camera.core.impl.MutableConfig r7 = r10.getMutableConfig()
            java.lang.Object r1 = r7.retrieveOption(r1, r2)
            if (r1 != 0) goto L_0x0080
            r1 = r4
            goto L_0x0081
        L_0x0080:
            r1 = r5
        L_0x0081:
            java.lang.String r2 = "Cannot set buffer format with CaptureProcessor defined."
            androidx.core.util.Preconditions.checkArgument(r1, r2)
            androidx.camera.core.impl.MutableConfig r1 = r10.getMutableConfig()
            androidx.camera.core.impl.Config$Option<java.lang.Integer> r2 = androidx.camera.core.impl.ImageInputConfig.OPTION_INPUT_FORMAT
            if (r0 == 0) goto L_0x008f
            goto L_0x0093
        L_0x008f:
            int r6 = r3.intValue()
        L_0x0093:
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r1.insertOption(r2, r6)
            goto L_0x00c5
        L_0x009b:
            androidx.camera.core.impl.MutableConfig r7 = r10.getMutableConfig()
            java.lang.Object r1 = r7.retrieveOption(r1, r2)
            if (r1 != 0) goto L_0x00b8
            if (r0 == 0) goto L_0x00a8
            goto L_0x00b8
        L_0x00a8:
            androidx.camera.core.impl.MutableConfig r1 = r10.getMutableConfig()
            androidx.camera.core.impl.Config$Option<java.lang.Integer> r2 = androidx.camera.core.impl.ImageInputConfig.OPTION_INPUT_FORMAT
            r6 = 256(0x100, float:3.59E-43)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r1.insertOption(r2, r6)
            goto L_0x00c5
        L_0x00b8:
            androidx.camera.core.impl.MutableConfig r1 = r10.getMutableConfig()
            androidx.camera.core.impl.Config$Option<java.lang.Integer> r2 = androidx.camera.core.impl.ImageInputConfig.OPTION_INPUT_FORMAT
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r1.insertOption(r2, r6)
        L_0x00c5:
            androidx.camera.core.impl.MutableConfig r1 = r10.getMutableConfig()
            androidx.camera.core.impl.Config$Option<java.lang.Integer> r2 = androidx.camera.core.impl.ImageCaptureConfig.OPTION_MAX_CAPTURE_STAGES
            r6 = 2
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            java.lang.Object r1 = r1.retrieveOption(r2, r6)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            if (r1 < r4) goto L_0x00de
            goto L_0x00df
        L_0x00de:
            r4 = r5
        L_0x00df:
            java.lang.String r1 = "Maximum outstanding image count must be at least 1"
            androidx.core.util.Preconditions.checkArgument(r4, r1)
            androidx.camera.core.impl.UseCaseConfig r1 = r10.getUseCaseConfig()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.ImageCapture.onMergeConfig(androidx.camera.core.impl.CameraInfoInternal, androidx.camera.core.impl.UseCaseConfig$Builder):androidx.camera.core.impl.UseCaseConfig");
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onCameraControlReady() {
        trySetFlashModeToCameraControl();
    }

    public int getFlashMode() {
        int i;
        synchronized (this.mLockedFlashMode) {
            i = this.mFlashMode;
            if (i == -1) {
                i = ((ImageCaptureConfig) getCurrentConfig()).getFlashMode(2);
            }
        }
        return i;
    }

    public void setFlashMode(int flashMode) {
        if (flashMode == 0 || flashMode == 1 || flashMode == 2) {
            synchronized (this.mLockedFlashMode) {
                this.mFlashMode = flashMode;
                trySetFlashModeToCameraControl();
            }
            return;
        }
        throw new IllegalArgumentException("Invalid flash mode: " + flashMode);
    }

    public void setCropAspectRatio(@NonNull Rational aspectRatio) {
        this.mCropAspectRatio = aspectRatio;
    }

    public int getTargetRotation() {
        return getTargetRotationInternal();
    }

    public void setTargetRotation(int rotation) {
        int oldRotation = getTargetRotation();
        if (setTargetRotationInternal(rotation) && this.mCropAspectRatio != null) {
            this.mCropAspectRatio = ImageUtil.getRotatedAspectRatio(Math.abs(CameraOrientationUtil.surfaceRotationToDegrees(rotation) - CameraOrientationUtil.surfaceRotationToDegrees(oldRotation)), this.mCropAspectRatio);
        }
    }

    public int getCaptureMode() {
        return this.mCaptureMode;
    }

    /* renamed from: takePicture */
    public void lambda$takePicture$4(@NonNull Executor executor, @NonNull OnImageCapturedCallback callback) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            CameraXExecutors.mainThreadExecutor().execute(new l0(this, executor, callback));
        } else {
            sendImageCaptureRequest(executor, callback);
        }
    }

    /* renamed from: takePicture */
    public void lambda$takePicture$5(@NonNull OutputFileOptions outputFileOptions, @NonNull Executor executor, @NonNull final OnImageSavedCallback imageSavedCallback) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            CameraXExecutors.mainThreadExecutor().execute(new g0(this, outputFileOptions, executor, imageSavedCallback));
            return;
        }
        final ImageSaver.OnImageSavedCallback imageSavedCallbackWrapper = new ImageSaver.OnImageSavedCallback() {
            public void onImageSaved(@NonNull OutputFileResults outputFileResults) {
                imageSavedCallback.onImageSaved(outputFileResults);
            }

            public void onError(@NonNull ImageSaver.SaveError error, @NonNull String message, @Nullable Throwable cause) {
                int imageCaptureError = 0;
                switch (AnonymousClass9.$SwitchMap$androidx$camera$core$ImageSaver$SaveError[error.ordinal()]) {
                    case 1:
                        imageCaptureError = 1;
                        break;
                }
                imageSavedCallback.onError(new ImageCaptureException(imageCaptureError, message, cause));
            }
        };
        final OutputFileOptions outputFileOptions2 = outputFileOptions;
        final Executor executor2 = executor;
        final OnImageSavedCallback onImageSavedCallback = imageSavedCallback;
        sendImageCaptureRequest(CameraXExecutors.mainThreadExecutor(), new OnImageCapturedCallback() {
            public void onCaptureSuccess(@NonNull ImageProxy image) {
                ImageCapture.this.mIoExecutor.execute(new ImageSaver(image, outputFileOptions2, image.getImageInfo().getRotationDegrees(), executor2, ImageCapture.this.mSequentialIoExecutor, imageSavedCallbackWrapper));
            }

            public void onError(@NonNull ImageCaptureException exception) {
                onImageSavedCallback.onError(exception);
            }
        });
    }

    /* renamed from: androidx.camera.core.ImageCapture$9  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$core$ImageSaver$SaveError;

        static {
            int[] iArr = new int[ImageSaver.SaveError.values().length];
            $SwitchMap$androidx$camera$core$ImageSaver$SaveError = iArr;
            try {
                iArr[ImageSaver.SaveError.FILE_IO_FAILED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    @UiThread
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onStateDetached() {
        abortImageCaptureRequests();
    }

    private void abortImageCaptureRequests() {
        this.mImageCaptureRequestProcessor.cancelRequests(new CameraClosedException("Camera is closed."));
    }

    @UiThread
    private void sendImageCaptureRequest(@NonNull Executor callbackExecutor, @NonNull OnImageCapturedCallback callback) {
        CameraInternal attachedCamera = getCamera();
        if (attachedCamera == null) {
            callbackExecutor.execute(new c0(this, callback));
        } else {
            this.mImageCaptureRequestProcessor.sendRequest(new ImageCaptureRequest(getRelativeRotation(attachedCamera), getJpegQuality(), this.mCropAspectRatio, getViewPortCropRect(), callbackExecutor, callback));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$sendImageCaptureRequest$6 */
    public /* synthetic */ void g(OnImageCapturedCallback callback) {
        callback.onError(new ImageCaptureException(4, "Not bound to a valid Camera [" + this + "]", (Throwable) null));
    }

    private void lockFlashMode() {
        synchronized (this.mLockedFlashMode) {
            if (this.mLockedFlashMode.get() == null) {
                this.mLockedFlashMode.set(Integer.valueOf(getFlashMode()));
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void unlockFlashMode() {
        /*
            r4 = this;
            java.util.concurrent.atomic.AtomicReference<java.lang.Integer> r0 = r4.mLockedFlashMode
            monitor-enter(r0)
            java.util.concurrent.atomic.AtomicReference<java.lang.Integer> r1 = r4.mLockedFlashMode     // Catch:{ all -> 0x001f }
            r2 = 0
            java.lang.Object r1 = r1.getAndSet(r2)     // Catch:{ all -> 0x001f }
            java.lang.Integer r1 = (java.lang.Integer) r1     // Catch:{ all -> 0x001f }
            if (r1 != 0) goto L_0x0010
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            return
        L_0x0010:
            int r2 = r1.intValue()     // Catch:{ all -> 0x001f }
            int r3 = r4.getFlashMode()     // Catch:{ all -> 0x001f }
            if (r2 == r3) goto L_0x001d
            r4.trySetFlashModeToCameraControl()     // Catch:{ all -> 0x001f }
        L_0x001d:
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            return
        L_0x001f:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.ImageCapture.unlockFlashMode():void");
    }

    private void trySetFlashModeToCameraControl() {
        synchronized (this.mLockedFlashMode) {
            if (this.mLockedFlashMode.get() == null) {
                getCameraControl().setFlashMode(getFlashMode());
            }
        }
    }

    @IntRange(from = 1, to = 100)
    private int getJpegQuality() {
        switch (this.mCaptureMode) {
            case 0:
                return 100;
            case 1:
                return 95;
            default:
                throw new IllegalStateException("CaptureMode " + this.mCaptureMode + " is invalid");
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: takePictureInternal */
    public ListenableFuture<ImageProxy> lambda$createPipeline$2(@NonNull ImageCaptureRequest imageCaptureRequest) {
        return CallbackToFutureAdapter.getFuture(new k0(this, imageCaptureRequest));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$takePictureInternal$10 */
    public /* synthetic */ Object j(ImageCaptureRequest imageCaptureRequest, final CallbackToFutureAdapter.Completer completer) {
        this.mImageReader.setOnImageAvailableListener(new j0(completer), CameraXExecutors.mainThreadExecutor());
        final TakePictureState state = new TakePictureState();
        ListenableFuture<Void> future = FutureChain.from(preTakePicture(state)).transformAsync(new z(this, imageCaptureRequest), this.mExecutor);
        Futures.addCallback(future, new FutureCallback<Void>() {
            public void onSuccess(Void result) {
                ImageCapture.this.postTakePicture(state);
            }

            public void onFailure(Throwable throwable) {
                ImageCapture.this.postTakePicture(state);
                completer.setException(throwable);
            }
        }, this.mExecutor);
        completer.addCancellationListener(new d0(future), CameraXExecutors.directExecutor());
        return "takePictureInternal";
    }

    static /* synthetic */ void lambda$takePictureInternal$7(CallbackToFutureAdapter.Completer completer, ImageReaderProxy imageReader) {
        try {
            ImageProxy image = imageReader.acquireLatestImage();
            if (image == null) {
                completer.setException(new IllegalStateException("Unable to acquire image"));
            } else if (!completer.set(image)) {
                image.close();
            }
        } catch (IllegalStateException e) {
            completer.setException(e);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$takePictureInternal$8 */
    public /* synthetic */ ListenableFuture k(ImageCaptureRequest imageCaptureRequest, Void v) {
        return issueTakePicture(imageCaptureRequest);
    }

    @VisibleForTesting
    public static class ImageCaptureRequestProcessor implements ForwardingImageProxy.OnImageCloseListener {
        @GuardedBy("mLock")
        ImageCaptureRequest mCurrentRequest = null;
        @GuardedBy("mLock")
        ListenableFuture<ImageProxy> mCurrentRequestFuture = null;
        @GuardedBy("mLock")
        private final ImageCaptor mImageCaptor;
        final Object mLock = new Object();
        private final int mMaxImages;
        @GuardedBy("mLock")
        int mOutstandingImages = 0;
        @GuardedBy("mLock")
        private final Deque<ImageCaptureRequest> mPendingRequests = new ArrayDeque();

        public interface ImageCaptor {
            @NonNull
            ListenableFuture<ImageProxy> capture(@NonNull ImageCaptureRequest imageCaptureRequest);
        }

        ImageCaptureRequestProcessor(int maxImages, @NonNull ImageCaptor imageCaptor) {
            this.mMaxImages = maxImages;
            this.mImageCaptor = imageCaptor;
        }

        public void sendRequest(@NonNull ImageCaptureRequest imageCaptureRequest) {
            synchronized (this.mLock) {
                this.mPendingRequests.offer(imageCaptureRequest);
                Locale locale = Locale.US;
                Object[] objArr = new Object[2];
                objArr[0] = Integer.valueOf(this.mCurrentRequest != null ? 1 : 0);
                objArr[1] = Integer.valueOf(this.mPendingRequests.size());
                Logger.d(ImageCapture.TAG, String.format(locale, "Send image capture request [current, pending] = [%d, %d]", objArr));
                processNextRequest();
            }
        }

        public void cancelRequests(@NonNull Throwable throwable) {
            ImageCaptureRequest currentRequest;
            ListenableFuture<ImageProxy> currentRequestFuture;
            List<ImageCaptureRequest> pendingRequests;
            synchronized (this.mLock) {
                currentRequest = this.mCurrentRequest;
                this.mCurrentRequest = null;
                currentRequestFuture = this.mCurrentRequestFuture;
                this.mCurrentRequestFuture = null;
                pendingRequests = new ArrayList<>(this.mPendingRequests);
                this.mPendingRequests.clear();
            }
            if (!(currentRequest == null || currentRequestFuture == null)) {
                currentRequest.notifyCallbackError(ImageCapture.getError(throwable), throwable.getMessage(), throwable);
                currentRequestFuture.cancel(true);
            }
            for (ImageCaptureRequest request : pendingRequests) {
                request.notifyCallbackError(ImageCapture.getError(throwable), throwable.getMessage(), throwable);
            }
        }

        public void onImageClose(ImageProxy image) {
            synchronized (this.mLock) {
                this.mOutstandingImages--;
                processNextRequest();
            }
        }

        /* access modifiers changed from: package-private */
        public void processNextRequest() {
            synchronized (this.mLock) {
                if (this.mCurrentRequest == null) {
                    if (this.mOutstandingImages >= this.mMaxImages) {
                        Logger.w(ImageCapture.TAG, "Too many acquire images. Close image to be able to process next.");
                        return;
                    }
                    final ImageCaptureRequest imageCaptureRequest = this.mPendingRequests.poll();
                    if (imageCaptureRequest != null) {
                        this.mCurrentRequest = imageCaptureRequest;
                        ListenableFuture<ImageProxy> capture = this.mImageCaptor.capture(imageCaptureRequest);
                        this.mCurrentRequestFuture = capture;
                        Futures.addCallback(capture, new FutureCallback<ImageProxy>() {
                            public void onSuccess(@Nullable ImageProxy image) {
                                synchronized (ImageCaptureRequestProcessor.this.mLock) {
                                    Preconditions.checkNotNull(image);
                                    SingleCloseImageProxy wrappedImage = new SingleCloseImageProxy(image);
                                    wrappedImage.addOnImageCloseListener(ImageCaptureRequestProcessor.this);
                                    ImageCaptureRequestProcessor.this.mOutstandingImages++;
                                    imageCaptureRequest.dispatchImage(wrappedImage);
                                    ImageCaptureRequestProcessor imageCaptureRequestProcessor = ImageCaptureRequestProcessor.this;
                                    imageCaptureRequestProcessor.mCurrentRequest = null;
                                    imageCaptureRequestProcessor.mCurrentRequestFuture = null;
                                    imageCaptureRequestProcessor.processNextRequest();
                                }
                            }

                            public void onFailure(Throwable t) {
                                synchronized (ImageCaptureRequestProcessor.this.mLock) {
                                    if (!(t instanceof CancellationException)) {
                                        imageCaptureRequest.notifyCallbackError(ImageCapture.getError(t), t != null ? t.getMessage() : "Unknown error", t);
                                    }
                                    ImageCaptureRequestProcessor imageCaptureRequestProcessor = ImageCaptureRequestProcessor.this;
                                    imageCaptureRequestProcessor.mCurrentRequest = null;
                                    imageCaptureRequestProcessor.mCurrentRequestFuture = null;
                                    imageCaptureRequestProcessor.processNextRequest();
                                }
                            }
                        }, CameraXExecutors.directExecutor());
                    }
                }
            }
        }
    }

    @NonNull
    public String toString() {
        return "ImageCapture:" + getName();
    }

    static int getError(Throwable throwable) {
        if (throwable instanceof CameraClosedException) {
            return 3;
        }
        if (throwable instanceof CaptureFailedException) {
            return 2;
        }
        return 0;
    }

    static boolean enforceSoftwareJpegConstraints(@NonNull MutableConfig mutableConfig) {
        Config.Option<Boolean> option = ImageCaptureConfig.OPTION_USE_SOFTWARE_JPEG_ENCODER;
        if (!((Boolean) mutableConfig.retrieveOption(option, false)).booleanValue()) {
            return false;
        }
        boolean supported = true;
        int i = Build.VERSION.SDK_INT;
        if (i < 26) {
            Logger.w(TAG, "Software JPEG only supported on API 26+, but current API level is " + i);
            supported = false;
        }
        Integer bufferFormat = (Integer) mutableConfig.retrieveOption(ImageCaptureConfig.OPTION_BUFFER_FORMAT, null);
        if (!(bufferFormat == null || bufferFormat.intValue() == 256)) {
            Logger.w(TAG, "Software JPEG cannot be used with non-JPEG output buffer format.");
            supported = false;
        }
        if (!supported) {
            Logger.w(TAG, "Unable to support software JPEG. Disabling.");
            mutableConfig.insertOption(option, false);
        }
        return supported;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onDetached() {
        abortImageCaptureRequests();
        clearPipeline();
        this.mUseSoftwareJpeg = false;
        this.mExecutor.shutdown();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onAttached() {
        ImageCaptureConfig useCaseConfig = (ImageCaptureConfig) getCurrentConfig();
        this.mCaptureConfig = CaptureConfig.Builder.createFrom(useCaseConfig).build();
        this.mCaptureProcessor = useCaseConfig.getCaptureProcessor((CaptureProcessor) null);
        this.mMaxCaptureStages = useCaseConfig.getMaxCaptureStages(2);
        this.mCaptureBundle = useCaseConfig.getCaptureBundle(CaptureBundles.singleDefaultCaptureBundle());
        this.mUseSoftwareJpeg = useCaseConfig.isSoftwareJpegEncoderRequested();
        this.mExecutor = Executors.newFixedThreadPool(1, new ThreadFactory() {
            private final AtomicInteger mId = new AtomicInteger(0);

            public Thread newThread(@NonNull Runnable r) {
                return new Thread(r, "CameraX-image_capture_" + this.mId.getAndIncrement());
            }
        });
    }

    /* access modifiers changed from: protected */
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Size onSuggestedResolutionUpdated(@NonNull Size suggestedResolution) {
        SessionConfig.Builder createPipeline = createPipeline(getCameraId(), (ImageCaptureConfig) getCurrentConfig(), suggestedResolution);
        this.mSessionConfigBuilder = createPipeline;
        updateSessionConfig(createPipeline.build());
        notifyActive();
        return suggestedResolution;
    }

    private ListenableFuture<Void> preTakePicture(TakePictureState state) {
        lockFlashMode();
        return FutureChain.from(getPreCaptureStateIfNeeded()).transformAsync(new m0(this, state), this.mExecutor).transformAsync(new r0(this, state), this.mExecutor).transform(i0.a, this.mExecutor);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$preTakePicture$11 */
    public /* synthetic */ ListenableFuture e(TakePictureState state, CameraCaptureResult captureResult) {
        state.mPreCaptureState = captureResult;
        triggerAfIfNeeded(state);
        if (!isFlashRequired(state)) {
            return Futures.immediateFuture(null);
        }
        if (this.mUseTorchFlash) {
            return openTorch(state);
        }
        return triggerAePrecapture(state);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$preTakePicture$12 */
    public /* synthetic */ ListenableFuture f(TakePictureState state, Void v) {
        return check3AConverged(state);
    }

    static /* synthetic */ Void lambda$preTakePicture$13(Boolean is3AConverged) {
        return null;
    }

    /* access modifiers changed from: package-private */
    public void postTakePicture(TakePictureState state) {
        closeTorch(state);
        cancelAfAeTrigger(state);
        unlockFlashMode();
    }

    @NonNull
    private ListenableFuture<Void> openTorch(@NonNull TakePictureState state) {
        CameraInternal camera = getCamera();
        if (camera != null && camera.getCameraInfo().getTorchState().getValue().intValue() == 1) {
            return Futures.immediateFuture(null);
        }
        Logger.d(TAG, "openTorch");
        return CallbackToFutureAdapter.getFuture(new v(this, state));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$openTorch$15 */
    public /* synthetic */ Object d(TakePictureState state, CallbackToFutureAdapter.Completer completer) {
        CameraControlInternal cameraControl = getCameraControl();
        state.mIsTorchOpened = true;
        cameraControl.enableTorch(true).addListener(new f0(completer), CameraXExecutors.directExecutor());
        return "openTorch";
    }

    private void closeTorch(@NonNull TakePictureState state) {
        if (state.mIsTorchOpened) {
            CameraControlInternal cameraControl = getCameraControl();
            state.mIsTorchOpened = false;
            cameraControl.enableTorch(false).addListener(y.c, CameraXExecutors.directExecutor());
        }
    }

    static /* synthetic */ void lambda$closeTorch$16() {
    }

    private ListenableFuture<CameraCaptureResult> getPreCaptureStateIfNeeded() {
        return (this.mEnableCheck3AConverged || getFlashMode() == 0) ? this.mSessionCallbackChecker.checkCaptureResult(new CaptureCallbackChecker.CaptureResultChecker<CameraCaptureResult>() {
            public CameraCaptureResult check(@NonNull CameraCaptureResult captureResult) {
                if (Logger.isDebugEnabled(ImageCapture.TAG)) {
                    Logger.d(ImageCapture.TAG, "preCaptureState, AE=" + captureResult.getAeState() + " AF =" + captureResult.getAfState() + " AWB=" + captureResult.getAwbState());
                }
                return captureResult;
            }
        }) : Futures.immediateFuture(null);
    }

    /* access modifiers changed from: package-private */
    public boolean isFlashRequired(@NonNull TakePictureState state) {
        switch (getFlashMode()) {
            case 0:
                if (state.mPreCaptureState.getAeState() == CameraCaptureMetaData.AeState.FLASH_REQUIRED) {
                    return true;
                }
                return false;
            case 1:
                return true;
            case 2:
                return false;
            default:
                throw new AssertionError(getFlashMode());
        }
    }

    /* access modifiers changed from: package-private */
    public ListenableFuture<Boolean> check3AConverged(TakePictureState state) {
        if (this.mEnableCheck3AConverged || state.mIsAePrecaptureTriggered || state.mIsTorchOpened) {
            return this.mSessionCallbackChecker.checkCaptureResult(new CaptureCallbackChecker.CaptureResultChecker<Boolean>() {
                public Boolean check(@NonNull CameraCaptureResult captureResult) {
                    if (Logger.isDebugEnabled(ImageCapture.TAG)) {
                        Logger.d(ImageCapture.TAG, "checkCaptureResult, AE=" + captureResult.getAeState() + " AF =" + captureResult.getAfState() + " AWB=" + captureResult.getAwbState());
                    }
                    if (ImageCapture.this.is3AConverged(captureResult)) {
                        return true;
                    }
                    return null;
                }
            }, 1000, false);
        }
        return Futures.immediateFuture(false);
    }

    /* access modifiers changed from: package-private */
    public boolean is3AConverged(CameraCaptureResult captureResult) {
        if (captureResult == null) {
            return false;
        }
        boolean isAfReady = captureResult.getAfMode() == CameraCaptureMetaData.AfMode.ON_CONTINUOUS_AUTO || captureResult.getAfMode() == CameraCaptureMetaData.AfMode.OFF || captureResult.getAfMode() == CameraCaptureMetaData.AfMode.UNKNOWN || captureResult.getAfState() == CameraCaptureMetaData.AfState.FOCUSED || captureResult.getAfState() == CameraCaptureMetaData.AfState.LOCKED_FOCUSED || captureResult.getAfState() == CameraCaptureMetaData.AfState.LOCKED_NOT_FOCUSED;
        boolean isAeReady = captureResult.getAeState() == CameraCaptureMetaData.AeState.CONVERGED || captureResult.getAeState() == CameraCaptureMetaData.AeState.FLASH_REQUIRED || captureResult.getAeState() == CameraCaptureMetaData.AeState.UNKNOWN;
        boolean isAwbReady = captureResult.getAwbState() == CameraCaptureMetaData.AwbState.CONVERGED || captureResult.getAwbState() == CameraCaptureMetaData.AwbState.UNKNOWN;
        if (!isAfReady || !isAeReady || !isAwbReady) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void triggerAfIfNeeded(TakePictureState state) {
        if (this.mEnableCheck3AConverged && state.mPreCaptureState.getAfMode() == CameraCaptureMetaData.AfMode.ON_MANUAL_AUTO && state.mPreCaptureState.getAfState() == CameraCaptureMetaData.AfState.INACTIVE) {
            triggerAf(state);
        }
    }

    private void triggerAf(TakePictureState state) {
        Logger.d(TAG, "triggerAf");
        state.mIsAfTriggered = true;
        getCameraControl().triggerAf().addListener(o0.c, CameraXExecutors.directExecutor());
    }

    static /* synthetic */ void lambda$triggerAf$17() {
    }

    /* access modifiers changed from: package-private */
    public ListenableFuture<Void> triggerAePrecapture(TakePictureState state) {
        Logger.d(TAG, "triggerAePrecapture");
        state.mIsAePrecaptureTriggered = true;
        return Futures.transform(getCameraControl().triggerAePrecapture(), s0.a, CameraXExecutors.directExecutor());
    }

    static /* synthetic */ Void lambda$triggerAePrecapture$18(CameraCaptureResult captureResult) {
        return null;
    }

    /* access modifiers changed from: package-private */
    public void cancelAfAeTrigger(TakePictureState state) {
        if (state.mIsAfTriggered || state.mIsAePrecaptureTriggered) {
            getCameraControl().cancelAfAeTrigger(state.mIsAfTriggered, state.mIsAePrecaptureTriggered);
            state.mIsAfTriggered = false;
            state.mIsAePrecaptureTriggered = false;
        }
    }

    /* access modifiers changed from: package-private */
    public ListenableFuture<Void> issueTakePicture(@NonNull ImageCaptureRequest imageCaptureRequest) {
        CaptureBundle captureBundle;
        Logger.d(TAG, "issueTakePicture");
        List<ListenableFuture<Void>> futureList = new ArrayList<>();
        List<CaptureConfig> captureConfigs = new ArrayList<>();
        String tagBundleKey = null;
        if (this.mProcessingImageReader != null) {
            captureBundle = getCaptureBundle(CaptureBundles.singleDefaultCaptureBundle());
            if (captureBundle == null) {
                return Futures.immediateFailedFuture(new IllegalArgumentException("ImageCapture cannot set empty CaptureBundle."));
            }
            if (this.mCaptureProcessor == null && captureBundle.getCaptureStages().size() > 1) {
                return Futures.immediateFailedFuture(new IllegalArgumentException("No CaptureProcessor can be found to process the images captured for multiple CaptureStages."));
            }
            if (captureBundle.getCaptureStages().size() > this.mMaxCaptureStages) {
                return Futures.immediateFailedFuture(new IllegalArgumentException("ImageCapture has CaptureStages > Max CaptureStage size"));
            }
            this.mProcessingImageReader.setCaptureBundle(captureBundle);
            tagBundleKey = this.mProcessingImageReader.getTagBundleKey();
        } else {
            captureBundle = getCaptureBundle(CaptureBundles.singleDefaultCaptureBundle());
            if (captureBundle.getCaptureStages().size() > 1) {
                return Futures.immediateFailedFuture(new IllegalArgumentException("ImageCapture have no CaptureProcess set with CaptureBundle size > 1."));
            }
        }
        for (CaptureStage captureStage : captureBundle.getCaptureStages()) {
            CaptureConfig.Builder builder = new CaptureConfig.Builder();
            builder.setTemplateType(this.mCaptureConfig.getTemplateType());
            builder.addImplementationOptions(this.mCaptureConfig.getImplementationOptions());
            builder.addAllCameraCaptureCallbacks(this.mSessionConfigBuilder.getSingleCameraCaptureCallbacks());
            builder.addSurface(this.mDeferrableSurface);
            if (new ExifRotationAvailability().isRotationOptionSupported()) {
                builder.addImplementationOption(CaptureConfig.OPTION_ROTATION, Integer.valueOf(imageCaptureRequest.mRotationDegrees));
            }
            builder.addImplementationOption(CaptureConfig.OPTION_JPEG_QUALITY, Integer.valueOf(imageCaptureRequest.mJpegQuality));
            builder.addImplementationOptions(captureStage.getCaptureConfig().getImplementationOptions());
            if (tagBundleKey != null) {
                builder.addTag(tagBundleKey, Integer.valueOf(captureStage.getId()));
            }
            builder.addCameraCaptureCallback(this.mMetadataMatchingCaptureCallback);
            futureList.add(CallbackToFutureAdapter.getFuture(new h0(this, builder, captureConfigs, captureStage)));
        }
        getCameraControl().submitCaptureRequests(captureConfigs);
        return Futures.transform(Futures.allAsList(futureList), q0.a, CameraXExecutors.directExecutor());
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$issueTakePicture$19 */
    public /* synthetic */ Object c(CaptureConfig.Builder builder, List captureConfigs, CaptureStage captureStage, final CallbackToFutureAdapter.Completer completer) {
        builder.addCameraCaptureCallback(new CameraCaptureCallback() {
            public void onCaptureCompleted(@NonNull CameraCaptureResult result) {
                completer.set(null);
            }

            public void onCaptureFailed(@NonNull CameraCaptureFailure failure) {
                completer.setException(new CaptureFailedException("Capture request failed with reason " + failure.getReason()));
            }

            public void onCaptureCancelled() {
                completer.setException(new CameraClosedException("Capture request is cancelled because camera is closed"));
            }
        });
        captureConfigs.add(builder.build());
        return "issueTakePicture[stage=" + captureStage.getId() + "]";
    }

    static /* synthetic */ Void lambda$issueTakePicture$20(List input) {
        return null;
    }

    public static final class CaptureFailedException extends RuntimeException {
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        CaptureFailedException(String s, Throwable e) {
            super(s, e);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        CaptureFailedException(String s) {
            super(s);
        }
    }

    private CaptureBundle getCaptureBundle(CaptureBundle defaultCaptureBundle) {
        List<CaptureStage> captureStages = this.mCaptureBundle.getCaptureStages();
        if (captureStages == null || captureStages.isEmpty()) {
            return defaultCaptureBundle;
        }
        return CaptureBundles.createCaptureBundle(captureStages);
    }

    public static abstract class OnImageCapturedCallback {
        public void onCaptureSuccess(@NonNull ImageProxy image) {
        }

        public void onError(@NonNull ImageCaptureException exception) {
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final class Defaults implements ConfigProvider<ImageCaptureConfig> {
        private static final int DEFAULT_ASPECT_RATIO = 0;
        private static final ImageCaptureConfig DEFAULT_CONFIG = new Builder().setSurfaceOccupancyPriority(4).setTargetAspectRatio(0).getUseCaseConfig();
        private static final int DEFAULT_SURFACE_OCCUPANCY_PRIORITY = 4;

        @NonNull
        public ImageCaptureConfig getConfig() {
            return DEFAULT_CONFIG;
        }
    }

    public static final class OutputFileOptions {
        @Nullable
        private final ContentResolver mContentResolver;
        @Nullable
        private final ContentValues mContentValues;
        @Nullable
        private final File mFile;
        @NonNull
        private final Metadata mMetadata;
        @Nullable
        private final OutputStream mOutputStream;
        @Nullable
        private final Uri mSaveCollection;

        OutputFileOptions(@Nullable File file, @Nullable ContentResolver contentResolver, @Nullable Uri saveCollection, @Nullable ContentValues contentValues, @Nullable OutputStream outputStream, @Nullable Metadata metadata) {
            this.mFile = file;
            this.mContentResolver = contentResolver;
            this.mSaveCollection = saveCollection;
            this.mContentValues = contentValues;
            this.mOutputStream = outputStream;
            this.mMetadata = metadata == null ? new Metadata() : metadata;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public File getFile() {
            return this.mFile;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public ContentResolver getContentResolver() {
            return this.mContentResolver;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public Uri getSaveCollection() {
            return this.mSaveCollection;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public ContentValues getContentValues() {
            return this.mContentValues;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public OutputStream getOutputStream() {
            return this.mOutputStream;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Metadata getMetadata() {
            return this.mMetadata;
        }

        public static final class Builder {
            @Nullable
            private ContentResolver mContentResolver;
            @Nullable
            private ContentValues mContentValues;
            @Nullable
            private File mFile;
            @Nullable
            private Metadata mMetadata;
            @Nullable
            private OutputStream mOutputStream;
            @Nullable
            private Uri mSaveCollection;

            public Builder(@NonNull File file) {
                this.mFile = file;
            }

            public Builder(@NonNull ContentResolver contentResolver, @NonNull Uri saveCollection, @NonNull ContentValues contentValues) {
                this.mContentResolver = contentResolver;
                this.mSaveCollection = saveCollection;
                this.mContentValues = contentValues;
            }

            public Builder(@NonNull OutputStream outputStream) {
                this.mOutputStream = outputStream;
            }

            @NonNull
            public Builder setMetadata(@NonNull Metadata metadata) {
                this.mMetadata = metadata;
                return this;
            }

            @NonNull
            public OutputFileOptions build() {
                return new OutputFileOptions(this.mFile, this.mContentResolver, this.mSaveCollection, this.mContentValues, this.mOutputStream, this.mMetadata);
            }
        }
    }

    public static class OutputFileResults {
        @Nullable
        private Uri mSavedUri;

        OutputFileResults(@Nullable Uri savedUri) {
            this.mSavedUri = savedUri;
        }

        @Nullable
        public Uri getSavedUri() {
            return this.mSavedUri;
        }
    }

    public static final class Metadata {
        private boolean mIsReversedHorizontal;
        private boolean mIsReversedHorizontalSet = false;
        private boolean mIsReversedVertical;
        @Nullable
        private Location mLocation;

        public boolean isReversedHorizontal() {
            return this.mIsReversedHorizontal;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public boolean isReversedHorizontalSet() {
            return this.mIsReversedHorizontalSet;
        }

        public void setReversedHorizontal(boolean isReversedHorizontal) {
            this.mIsReversedHorizontal = isReversedHorizontal;
            this.mIsReversedHorizontalSet = true;
        }

        public boolean isReversedVertical() {
            return this.mIsReversedVertical;
        }

        public void setReversedVertical(boolean isReversedVertical) {
            this.mIsReversedVertical = isReversedVertical;
        }

        @Nullable
        public Location getLocation() {
            return this.mLocation;
        }

        public void setLocation(@Nullable Location location) {
            this.mLocation = location;
        }
    }

    public static final class TakePictureState {
        boolean mIsAePrecaptureTriggered = false;
        boolean mIsAfTriggered = false;
        boolean mIsTorchOpened = false;
        CameraCaptureResult mPreCaptureState = CameraCaptureResult.EmptyCameraCaptureResult.create();

        TakePictureState() {
        }
    }

    public static final class CaptureCallbackChecker extends CameraCaptureCallback {
        private static final long NO_TIMEOUT = 0;
        private final Set<CaptureResultListener> mCaptureResultListeners = new HashSet();

        public interface CaptureResultChecker<T> {
            @Nullable
            T check(@NonNull CameraCaptureResult cameraCaptureResult);
        }

        public interface CaptureResultListener {
            boolean onCaptureResult(@NonNull CameraCaptureResult cameraCaptureResult);
        }

        CaptureCallbackChecker() {
        }

        public void onCaptureCompleted(@NonNull CameraCaptureResult cameraCaptureResult) {
            deliverCaptureResultToListeners(cameraCaptureResult);
        }

        /* access modifiers changed from: package-private */
        public <T> ListenableFuture<T> checkCaptureResult(CaptureResultChecker<T> checker) {
            return checkCaptureResult(checker, 0, (Object) null);
        }

        /* access modifiers changed from: package-private */
        public <T> ListenableFuture<T> checkCaptureResult(CaptureResultChecker<T> checker, long timeoutInMs, T defValue) {
            long startTimeInMs = 0;
            if (timeoutInMs >= 0) {
                if (timeoutInMs != 0) {
                    startTimeInMs = SystemClock.elapsedRealtime();
                }
                return CallbackToFutureAdapter.getFuture(new x(this, checker, startTimeInMs, timeoutInMs, defValue));
            }
            throw new IllegalArgumentException("Invalid timeout value: " + timeoutInMs);
        }

        /* access modifiers changed from: private */
        /* renamed from: lambda$checkCaptureResult$0 */
        public /* synthetic */ Object a(CaptureResultChecker checker, long startTimeInMs, long timeoutInMs, Object defValue, CallbackToFutureAdapter.Completer completer) {
            final CaptureResultChecker captureResultChecker = checker;
            final CallbackToFutureAdapter.Completer completer2 = completer;
            final long j = startTimeInMs;
            final long j2 = timeoutInMs;
            final Object obj = defValue;
            addListener(new CaptureResultListener() {
                public boolean onCaptureResult(@NonNull CameraCaptureResult captureResult) {
                    Object result = captureResultChecker.check(captureResult);
                    if (result != null) {
                        completer2.set(result);
                        return true;
                    } else if (j <= 0 || SystemClock.elapsedRealtime() - j <= j2) {
                        return false;
                    } else {
                        completer2.set(obj);
                        return true;
                    }
                }
            });
            return "checkCaptureResult";
        }

        private void deliverCaptureResultToListeners(@NonNull CameraCaptureResult captureResult) {
            synchronized (this.mCaptureResultListeners) {
                Set<CaptureResultListener> removeSet = null;
                Iterator it = new HashSet(this.mCaptureResultListeners).iterator();
                while (it.hasNext()) {
                    CaptureResultListener listener = (CaptureResultListener) it.next();
                    if (listener.onCaptureResult(captureResult)) {
                        if (removeSet == null) {
                            removeSet = new HashSet<>();
                        }
                        removeSet.add(listener);
                    }
                }
                if (removeSet != null) {
                    this.mCaptureResultListeners.removeAll(removeSet);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void addListener(CaptureResultListener listener) {
            synchronized (this.mCaptureResultListeners) {
                this.mCaptureResultListeners.add(listener);
            }
        }
    }

    @VisibleForTesting
    public static class ImageCaptureRequest {
        @NonNull
        private final OnImageCapturedCallback mCallback;
        AtomicBoolean mDispatched = new AtomicBoolean(false);
        @IntRange(from = 1, to = 100)
        final int mJpegQuality;
        @NonNull
        private final Executor mListenerExecutor;
        final int mRotationDegrees;
        private final Rational mTargetRatio;
        private final Rect mViewPortCropRect;

        ImageCaptureRequest(int rotationDegrees, @IntRange(from = 1, to = 100) int jpegQuality, Rational targetRatio, @Nullable Rect viewPortCropRect, @NonNull Executor executor, @NonNull OnImageCapturedCallback callback) {
            boolean z = false;
            this.mRotationDegrees = rotationDegrees;
            this.mJpegQuality = jpegQuality;
            if (targetRatio != null) {
                Preconditions.checkArgument(!targetRatio.isZero(), "Target ratio cannot be zero");
                Preconditions.checkArgument(targetRatio.floatValue() > 0.0f ? true : z, "Target ratio must be positive");
            }
            this.mTargetRatio = targetRatio;
            this.mViewPortCropRect = viewPortCropRect;
            this.mListenerExecutor = executor;
            this.mCallback = callback;
        }

        /* access modifiers changed from: package-private */
        public void dispatchImage(ImageProxy image) {
            Size dispatchResolution;
            int dispatchRotationDegrees;
            if (!this.mDispatched.compareAndSet(false, true)) {
                image.close();
                return;
            }
            if (new ExifRotationAvailability().shouldUseExifOrientation(image)) {
                try {
                    ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                    buffer.rewind();
                    byte[] data = new byte[buffer.capacity()];
                    buffer.get(data);
                    Exif exif = Exif.createFromInputStream(new ByteArrayInputStream(data));
                    buffer.rewind();
                    dispatchResolution = new Size(exif.getWidth(), exif.getHeight());
                    dispatchRotationDegrees = exif.getRotation();
                } catch (IOException e) {
                    notifyCallbackError(1, "Unable to parse JPEG exif", e);
                    image.close();
                    return;
                }
            } else {
                dispatchResolution = new Size(image.getWidth(), image.getHeight());
                dispatchRotationDegrees = this.mRotationDegrees;
            }
            ImageProxy dispatchedImageProxy = new SettableImageProxy(image, dispatchResolution, ImmutableImageInfo.create(image.getImageInfo().getTagBundle(), image.getImageInfo().getTimestamp(), dispatchRotationDegrees));
            Rect rect = this.mViewPortCropRect;
            if (rect != null) {
                dispatchedImageProxy.setCropRect(getDispatchCropRect(rect, this.mRotationDegrees, dispatchResolution, dispatchRotationDegrees));
            } else if (this.mTargetRatio != null) {
                Rational dispatchRatio = this.mTargetRatio;
                if (dispatchRotationDegrees % 180 != 0) {
                    dispatchRatio = new Rational(this.mTargetRatio.getDenominator(), this.mTargetRatio.getNumerator());
                }
                Size sourceSize = new Size(dispatchedImageProxy.getWidth(), dispatchedImageProxy.getHeight());
                if (ImageUtil.isAspectRatioValid(sourceSize, dispatchRatio)) {
                    dispatchedImageProxy.setCropRect(ImageUtil.computeCropRectFromAspectRatio(sourceSize, dispatchRatio));
                }
            }
            try {
                this.mListenerExecutor.execute(new b0(this, dispatchedImageProxy));
            } catch (RejectedExecutionException e2) {
                Logger.e(ImageCapture.TAG, "Unable to post to the supplied executor.");
                image.close();
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: lambda$dispatchImage$0 */
        public /* synthetic */ void a(ImageProxy dispatchedImageProxy) {
            this.mCallback.onCaptureSuccess(dispatchedImageProxy);
        }

        @NonNull
        static Rect getDispatchCropRect(@NonNull Rect surfaceCropRect, int surfaceToOutputDegrees, @NonNull Size dispatchResolution, int dispatchToOutputDegrees) {
            Matrix matrix = new Matrix();
            matrix.setRotate((float) (dispatchToOutputDegrees - surfaceToOutputDegrees));
            float[] vertexes = ImageUtil.sizeToVertexes(dispatchResolution);
            matrix.mapPoints(vertexes);
            matrix.postTranslate(-ImageUtil.min(vertexes[0], vertexes[2], vertexes[4], vertexes[6]), -ImageUtil.min(vertexes[1], vertexes[3], vertexes[5], vertexes[7]));
            matrix.invert(matrix);
            RectF dispatchCropRectF = new RectF();
            matrix.mapRect(dispatchCropRectF, new RectF(surfaceCropRect));
            dispatchCropRectF.sort();
            Rect dispatchCropRect = new Rect();
            dispatchCropRectF.round(dispatchCropRect);
            return dispatchCropRect;
        }

        /* access modifiers changed from: package-private */
        public void notifyCallbackError(int imageCaptureError, String message, Throwable cause) {
            if (this.mDispatched.compareAndSet(false, true)) {
                try {
                    this.mListenerExecutor.execute(new a0(this, imageCaptureError, message, cause));
                } catch (RejectedExecutionException e) {
                    Logger.e(ImageCapture.TAG, "Unable to post to the supplied executor.");
                }
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: lambda$notifyCallbackError$1 */
        public /* synthetic */ void b(int imageCaptureError, String message, Throwable cause) {
            this.mCallback.onError(new ImageCaptureException(imageCaptureError, message, cause));
        }
    }

    public static final class Builder implements UseCaseConfig.Builder<ImageCapture, ImageCaptureConfig, Builder>, ImageOutputConfig.Builder<Builder>, IoConfig.Builder<Builder> {
        private final MutableOptionsBundle mMutableConfig;

        public Builder() {
            this(MutableOptionsBundle.create());
        }

        private Builder(MutableOptionsBundle mutableConfig) {
            Class<ImageCapture> cls = ImageCapture.class;
            this.mMutableConfig = mutableConfig;
            Class<?> oldConfigClass = (Class) mutableConfig.retrieveOption(TargetConfig.OPTION_TARGET_CLASS, null);
            if (oldConfigClass == null || oldConfigClass.equals(cls)) {
                setTargetClass(cls);
                return;
            }
            throw new IllegalArgumentException("Invalid target class configuration for " + this + ": " + oldConfigClass);
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static Builder fromConfig(@NonNull Config configuration) {
            return new Builder(MutableOptionsBundle.from(configuration));
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        static Builder fromConfig(@NonNull ImageCaptureConfig configuration) {
            return new Builder(MutableOptionsBundle.from(configuration));
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public MutableConfig getMutableConfig() {
            return this.mMutableConfig;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public ImageCaptureConfig getUseCaseConfig() {
            return new ImageCaptureConfig(OptionsBundle.from(this.mMutableConfig));
        }

        @NonNull
        public ImageCapture build() {
            int flashMode;
            if (getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO, null) == null || getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, null) == null) {
                Integer bufferFormat = (Integer) getMutableConfig().retrieveOption(ImageCaptureConfig.OPTION_BUFFER_FORMAT, null);
                boolean z = false;
                if (bufferFormat != null) {
                    Preconditions.checkArgument(getMutableConfig().retrieveOption(ImageCaptureConfig.OPTION_CAPTURE_PROCESSOR, null) == null, "Cannot set buffer format with CaptureProcessor defined.");
                    getMutableConfig().insertOption(ImageInputConfig.OPTION_INPUT_FORMAT, bufferFormat);
                } else if (getMutableConfig().retrieveOption(ImageCaptureConfig.OPTION_CAPTURE_PROCESSOR, null) != null) {
                    getMutableConfig().insertOption(ImageInputConfig.OPTION_INPUT_FORMAT, 35);
                } else {
                    getMutableConfig().insertOption(ImageInputConfig.OPTION_INPUT_FORMAT, 256);
                }
                ImageCapture imageCapture = new ImageCapture(getUseCaseConfig());
                Size targetResolution = (Size) getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, null);
                if (targetResolution != null) {
                    imageCapture.setCropAspectRatio(new Rational(targetResolution.getWidth(), targetResolution.getHeight()));
                }
                if (((Integer) getMutableConfig().retrieveOption(ImageCaptureConfig.OPTION_MAX_CAPTURE_STAGES, 2)).intValue() >= 1) {
                    z = true;
                }
                Preconditions.checkArgument(z, "Maximum outstanding image count must be at least 1");
                Preconditions.checkNotNull((Executor) getMutableConfig().retrieveOption(IoConfig.OPTION_IO_EXECUTOR, CameraXExecutors.ioExecutor()), "The IO executor can't be null");
                MutableConfig mutableConfig = getMutableConfig();
                Config.Option<Integer> option = ImageCaptureConfig.OPTION_FLASH_MODE;
                if (!mutableConfig.containsOption(option) || (flashMode = ((Integer) getMutableConfig().retrieveOption(option)).intValue()) == 0 || flashMode == 1 || flashMode == 2) {
                    return imageCapture;
                }
                throw new IllegalArgumentException("The flash mode is not allowed to set: " + flashMode);
            }
            throw new IllegalArgumentException("Cannot use both setTargetResolution and setTargetAspectRatio on the same config.");
        }

        @NonNull
        public Builder setCaptureMode(int captureMode) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_IMAGE_CAPTURE_MODE, Integer.valueOf(captureMode));
            return this;
        }

        @NonNull
        public Builder setFlashMode(int flashMode) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_FLASH_MODE, Integer.valueOf(flashMode));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setCaptureBundle(@NonNull CaptureBundle captureBundle) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_CAPTURE_BUNDLE, captureBundle);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setCaptureProcessor(@NonNull CaptureProcessor captureProcessor) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_CAPTURE_PROCESSOR, captureProcessor);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setBufferFormat(int bufferImageFormat) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_BUFFER_FORMAT, Integer.valueOf(bufferImageFormat));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setMaxCaptureStages(int maxCaptureStages) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_MAX_CAPTURE_STAGES, Integer.valueOf(maxCaptureStages));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setSupportedResolutions(@NonNull List<Pair<Integer, Size[]>> resolutions) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_SUPPORTED_RESOLUTIONS, resolutions);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setTargetClass(@NonNull Class<ImageCapture> targetClass) {
            getMutableConfig().insertOption(TargetConfig.OPTION_TARGET_CLASS, targetClass);
            if (getMutableConfig().retrieveOption(TargetConfig.OPTION_TARGET_NAME, null) == null) {
                setTargetName(targetClass.getCanonicalName() + "-" + UUID.randomUUID());
            }
            return this;
        }

        @NonNull
        public Builder setTargetName(@NonNull String targetName) {
            getMutableConfig().insertOption(TargetConfig.OPTION_TARGET_NAME, targetName);
            return this;
        }

        @NonNull
        public Builder setTargetAspectRatio(int aspectRatio) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO, Integer.valueOf(aspectRatio));
            return this;
        }

        @NonNull
        public Builder setTargetRotation(int rotation) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ROTATION, Integer.valueOf(rotation));
            return this;
        }

        @NonNull
        public Builder setTargetResolution(@NonNull Size resolution) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, resolution);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setDefaultResolution(@NonNull Size resolution) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_DEFAULT_RESOLUTION, resolution);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setMaxResolution(@NonNull Size resolution) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_MAX_RESOLUTION, resolution);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setImageReaderProxyProvider(@NonNull ImageReaderProxyProvider imageReaderProxyProvider) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_IMAGE_READER_PROXY_PROVIDER, imageReaderProxyProvider);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setSoftwareJpegEncoderRequested(boolean requestSoftwareJpeg) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_USE_SOFTWARE_JPEG_ENCODER, Boolean.valueOf(requestSoftwareJpeg));
            return this;
        }

        @NonNull
        public Builder setIoExecutor(@NonNull Executor executor) {
            getMutableConfig().insertOption(IoConfig.OPTION_IO_EXECUTOR, executor);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setDefaultSessionConfig(@NonNull SessionConfig sessionConfig) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_DEFAULT_SESSION_CONFIG, sessionConfig);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setDefaultCaptureConfig(@NonNull CaptureConfig captureConfig) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_DEFAULT_CAPTURE_CONFIG, captureConfig);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setSessionOptionUnpacker(@NonNull SessionConfig.OptionUnpacker optionUnpacker) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_SESSION_CONFIG_UNPACKER, optionUnpacker);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setCaptureOptionUnpacker(@NonNull CaptureConfig.OptionUnpacker optionUnpacker) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_CAPTURE_CONFIG_UNPACKER, optionUnpacker);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setCameraSelector(@NonNull CameraSelector cameraSelector) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_CAMERA_SELECTOR, cameraSelector);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setSurfaceOccupancyPriority(int priority) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_SURFACE_OCCUPANCY_PRIORITY, Integer.valueOf(priority));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setUseCaseEventCallback(@NonNull UseCase.EventCallback useCaseEventCallback) {
            getMutableConfig().insertOption(UseCaseEventConfig.OPTION_USE_CASE_EVENT_CALLBACK, useCaseEventCallback);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setAttachedUseCasesUpdateListener(@NonNull Consumer<Collection<UseCase>> attachedUseCasesUpdateListener) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_ATTACHED_USE_CASES_UPDATE_LISTENER, attachedUseCasesUpdateListener);
            return this;
        }
    }
}
