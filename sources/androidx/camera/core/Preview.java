package androidx.camera.core;

import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Pair;
import android.util.Size;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;
import androidx.annotation.VisibleForTesting;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.CaptureProcessor;
import androidx.camera.core.impl.CaptureStage;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ConfigProvider;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageInfoProcessor;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.PreviewConfig;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.x;
import androidx.camera.core.internal.CameraCaptureResultImageInfo;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.ThreadConfig;
import androidx.camera.core.internal.UseCaseEventConfig;
import androidx.core.util.Consumer;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;

public final class Preview extends UseCase {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final Defaults DEFAULT_CONFIG = new Defaults();
    private static final Executor DEFAULT_SURFACE_PROVIDER_EXECUTOR = CameraXExecutors.mainThreadExecutor();
    private static final String TAG = "Preview";
    @VisibleForTesting
    @Nullable
    SurfaceRequest mCurrentSurfaceRequest;
    private boolean mHasUnsentSurfaceRequest = false;
    private DeferrableSurface mSessionDeferrableSurface;
    @Nullable
    private SurfaceProvider mSurfaceProvider;
    @NonNull
    private Executor mSurfaceProviderExecutor = DEFAULT_SURFACE_PROVIDER_EXECUTOR;
    @Nullable
    private Size mSurfaceSize;

    public interface SurfaceProvider {
        void onSurfaceRequested(@NonNull SurfaceRequest surfaceRequest);
    }

    @MainThread
    Preview(@NonNull PreviewConfig config) {
        super(config);
    }

    /* access modifiers changed from: package-private */
    @OptIn(markerClass = {ExperimentalUseCaseGroup.class})
    public SessionConfig.Builder createPipeline(@NonNull String cameraId, @NonNull PreviewConfig config, @NonNull Size resolution) {
        PreviewConfig previewConfig = config;
        Size size = resolution;
        Threads.checkMainThread();
        SessionConfig.Builder sessionConfigBuilder = SessionConfig.Builder.createFrom(config);
        CaptureProcessor captureProcessor = previewConfig.getCaptureProcessor((CaptureProcessor) null);
        DeferrableSurface deferrableSurface = this.mSessionDeferrableSurface;
        if (deferrableSurface != null) {
            deferrableSurface.close();
        }
        SurfaceRequest surfaceRequest = new SurfaceRequest(size, getCamera(), captureProcessor != null);
        this.mCurrentSurfaceRequest = surfaceRequest;
        if (sendSurfaceRequestIfReady()) {
            sendTransformationInfoIfReady();
        } else {
            this.mHasUnsentSurfaceRequest = true;
        }
        if (captureProcessor != null) {
            CaptureStage captureStage = new CaptureStage.DefaultCaptureStage();
            HandlerThread handlerThread = new HandlerThread("CameraX-preview_processing");
            handlerThread.start();
            String tagBundleKey = Integer.toString(captureStage.hashCode());
            ProcessingSurface processingSurface = new ProcessingSurface(resolution.getWidth(), resolution.getHeight(), config.getInputFormat(), new Handler(handlerThread.getLooper()), captureStage, captureProcessor, surfaceRequest.getDeferrableSurface(), tagBundleKey);
            sessionConfigBuilder.addCameraCaptureCallback(processingSurface.getCameraCaptureCallback());
            ListenableFuture<Void> terminationFuture = processingSurface.getTerminationFuture();
            Objects.requireNonNull(handlerThread);
            terminationFuture.addListener(new a(handlerThread), CameraXExecutors.directExecutor());
            this.mSessionDeferrableSurface = processingSurface;
            sessionConfigBuilder.addTag(tagBundleKey, Integer.valueOf(captureStage.getId()));
        } else {
            final ImageInfoProcessor processor = previewConfig.getImageInfoProcessor((ImageInfoProcessor) null);
            if (processor != null) {
                sessionConfigBuilder.addCameraCaptureCallback(new CameraCaptureCallback() {
                    public void onCaptureCompleted(@NonNull CameraCaptureResult cameraCaptureResult) {
                        super.onCaptureCompleted(cameraCaptureResult);
                        if (processor.process(new CameraCaptureResultImageInfo(cameraCaptureResult))) {
                            Preview.this.notifyUpdated();
                        }
                    }
                });
            }
            this.mSessionDeferrableSurface = surfaceRequest.getDeferrableSurface();
        }
        sessionConfigBuilder.addSurface(this.mSessionDeferrableSurface);
        sessionConfigBuilder.addErrorListener(new z0(this, cameraId, previewConfig, size));
        return sessionConfigBuilder;
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$createPipeline$0 */
    public /* synthetic */ void a(String cameraId, PreviewConfig config, Size resolution, SessionConfig sessionConfig, SessionConfig.SessionError error) {
        if (isCurrentCamera(cameraId)) {
            updateSessionConfig(createPipeline(cameraId, config, resolution).build());
            notifyReset();
        }
    }

    @ExperimentalUseCaseGroup
    public void setTargetRotation(int targetRotation) {
        if (setTargetRotationInternal(targetRotation)) {
            sendTransformationInfoIfReady();
        }
    }

    @ExperimentalUseCaseGroup
    private void sendTransformationInfoIfReady() {
        CameraInternal cameraInternal = getCamera();
        SurfaceProvider surfaceProvider = this.mSurfaceProvider;
        Rect cropRect = getCropRect(this.mSurfaceSize);
        SurfaceRequest surfaceRequest = this.mCurrentSurfaceRequest;
        if (cameraInternal != null && surfaceProvider != null && cropRect != null) {
            surfaceRequest.updateTransformationInfo(SurfaceRequest.TransformationInfo.of(cropRect, getRelativeRotation(cameraInternal), getTargetRotation()));
        }
    }

    @Nullable
    private Rect getCropRect(@Nullable Size surfaceResolution) {
        if (getViewPortCropRect() != null) {
            return getViewPortCropRect();
        }
        if (surfaceResolution != null) {
            return new Rect(0, 0, surfaceResolution.getWidth(), surfaceResolution.getHeight());
        }
        return null;
    }

    @UiThread
    @OptIn(markerClass = {ExperimentalUseCaseGroup.class})
    public void setSurfaceProvider(@NonNull Executor executor, @Nullable SurfaceProvider surfaceProvider) {
        Threads.checkMainThread();
        if (surfaceProvider == null) {
            this.mSurfaceProvider = null;
            notifyInactive();
            return;
        }
        this.mSurfaceProvider = surfaceProvider;
        this.mSurfaceProviderExecutor = executor;
        notifyActive();
        if (this.mHasUnsentSurfaceRequest) {
            if (sendSurfaceRequestIfReady()) {
                sendTransformationInfoIfReady();
                this.mHasUnsentSurfaceRequest = false;
            }
        } else if (getAttachedSurfaceResolution() != null) {
            updateConfigAndOutput(getCameraId(), (PreviewConfig) getCurrentConfig(), getAttachedSurfaceResolution());
            notifyReset();
        }
    }

    private boolean sendSurfaceRequestIfReady() {
        SurfaceRequest surfaceRequest = this.mCurrentSurfaceRequest;
        SurfaceProvider surfaceProvider = this.mSurfaceProvider;
        if (surfaceProvider == null || surfaceRequest == null) {
            return false;
        }
        this.mSurfaceProviderExecutor.execute(new a1(surfaceProvider, surfaceRequest));
        return true;
    }

    @UiThread
    public void setSurfaceProvider(@Nullable SurfaceProvider surfaceProvider) {
        setSurfaceProvider(DEFAULT_SURFACE_PROVIDER_EXECUTOR, surfaceProvider);
    }

    private void updateConfigAndOutput(@NonNull String cameraId, @NonNull PreviewConfig config, @NonNull Size resolution) {
        updateSessionConfig(createPipeline(cameraId, config, resolution).build());
    }

    public int getTargetRotation() {
        return getTargetRotationInternal();
    }

    @NonNull
    public String toString() {
        return "Preview:" + getName();
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfig<?> getDefaultConfig(boolean applyDefaultConfig, @NonNull UseCaseConfigFactory factory) {
        Config captureConfig = factory.getConfig(UseCaseConfigFactory.CaptureType.PREVIEW);
        if (applyDefaultConfig) {
            captureConfig = x.b(captureConfig, DEFAULT_CONFIG.getConfig());
        }
        if (captureConfig == null) {
            return null;
        }
        return getUseCaseConfigBuilder(captureConfig).getUseCaseConfig();
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [androidx.camera.core.impl.UseCaseConfig$Builder, androidx.camera.core.ExtendableBuilder, androidx.camera.core.impl.UseCaseConfig$Builder<?, ?, ?>] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Unknown variable types count: 1 */
    @androidx.annotation.NonNull
    @androidx.annotation.RestrictTo({androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.camera.core.impl.UseCaseConfig<?> onMergeConfig(@androidx.annotation.NonNull androidx.camera.core.impl.CameraInfoInternal r4, @androidx.annotation.NonNull androidx.camera.core.impl.UseCaseConfig.Builder<?, ?, ?> r5) {
        /*
            r3 = this;
            androidx.camera.core.impl.MutableConfig r0 = r5.getMutableConfig()
            androidx.camera.core.impl.Config$Option<androidx.camera.core.impl.CaptureProcessor> r1 = androidx.camera.core.impl.PreviewConfig.OPTION_PREVIEW_CAPTURE_PROCESSOR
            r2 = 0
            java.lang.Object r0 = r0.retrieveOption(r1, r2)
            if (r0 == 0) goto L_0x001d
            androidx.camera.core.impl.MutableConfig r0 = r5.getMutableConfig()
            androidx.camera.core.impl.Config$Option<java.lang.Integer> r1 = androidx.camera.core.impl.ImageInputConfig.OPTION_INPUT_FORMAT
            r2 = 35
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r0.insertOption(r1, r2)
            goto L_0x002c
        L_0x001d:
            androidx.camera.core.impl.MutableConfig r0 = r5.getMutableConfig()
            androidx.camera.core.impl.Config$Option<java.lang.Integer> r1 = androidx.camera.core.impl.ImageInputConfig.OPTION_INPUT_FORMAT
            r2 = 34
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r0.insertOption(r1, r2)
        L_0x002c:
            androidx.camera.core.impl.UseCaseConfig r0 = r5.getUseCaseConfig()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.Preview.onMergeConfig(androidx.camera.core.impl.CameraInfoInternal, androidx.camera.core.impl.UseCaseConfig$Builder):androidx.camera.core.impl.UseCaseConfig");
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfig.Builder<?, ?, ?> getUseCaseConfigBuilder(@NonNull Config config) {
        return Builder.fromConfig(config);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onDetached() {
        DeferrableSurface deferrableSurface = this.mSessionDeferrableSurface;
        if (deferrableSurface != null) {
            deferrableSurface.close();
        }
        this.mCurrentSurfaceRequest = null;
    }

    /* access modifiers changed from: protected */
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Size onSuggestedResolutionUpdated(@NonNull Size suggestedResolution) {
        this.mSurfaceSize = suggestedResolution;
        updateConfigAndOutput(getCameraId(), (PreviewConfig) getCurrentConfig(), this.mSurfaceSize);
        return suggestedResolution;
    }

    @OptIn(markerClass = {ExperimentalUseCaseGroup.class})
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setViewPortCropRect(@NonNull Rect viewPortCropRect) {
        super.setViewPortCropRect(viewPortCropRect);
        sendTransformationInfoIfReady();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final class Defaults implements ConfigProvider<PreviewConfig> {
        private static final int DEFAULT_ASPECT_RATIO = 0;
        private static final PreviewConfig DEFAULT_CONFIG = new Builder().setSurfaceOccupancyPriority(2).setTargetAspectRatio(0).getUseCaseConfig();
        private static final int DEFAULT_SURFACE_OCCUPANCY_PRIORITY = 2;

        @NonNull
        public PreviewConfig getConfig() {
            return DEFAULT_CONFIG;
        }
    }

    public static final class Builder implements UseCaseConfig.Builder<Preview, PreviewConfig, Builder>, ImageOutputConfig.Builder<Builder>, ThreadConfig.Builder<Builder> {
        private final MutableOptionsBundle mMutableConfig;

        public Builder() {
            this(MutableOptionsBundle.create());
        }

        private Builder(MutableOptionsBundle mutableConfig) {
            Class<Preview> cls = Preview.class;
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
        static Builder fromConfig(@NonNull Config configuration) {
            return new Builder(MutableOptionsBundle.from(configuration));
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static Builder fromConfig(@NonNull PreviewConfig configuration) {
            return new Builder(MutableOptionsBundle.from(configuration));
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public MutableConfig getMutableConfig() {
            return this.mMutableConfig;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public PreviewConfig getUseCaseConfig() {
            return new PreviewConfig(OptionsBundle.from(this.mMutableConfig));
        }

        @NonNull
        public Preview build() {
            if (getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO, null) == null || getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, null) == null) {
                return new Preview(getUseCaseConfig());
            }
            throw new IllegalArgumentException("Cannot use both setTargetResolution and setTargetAspectRatio on the same config.");
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setTargetClass(@NonNull Class<Preview> targetClass) {
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
        public Builder setSupportedResolutions(@NonNull List<Pair<Integer, Size[]>> resolutions) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_SUPPORTED_RESOLUTIONS, resolutions);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setBackgroundExecutor(@NonNull Executor executor) {
            getMutableConfig().insertOption(ThreadConfig.OPTION_BACKGROUND_EXECUTOR, executor);
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
        public Builder setSurfaceOccupancyPriority(int priority) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_SURFACE_OCCUPANCY_PRIORITY, Integer.valueOf(priority));
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
        public Builder setUseCaseEventCallback(@NonNull UseCase.EventCallback useCaseEventCallback) {
            getMutableConfig().insertOption(UseCaseEventConfig.OPTION_USE_CASE_EVENT_CALLBACK, useCaseEventCallback);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setImageInfoProcessor(@NonNull ImageInfoProcessor processor) {
            getMutableConfig().insertOption(PreviewConfig.IMAGE_INFO_PROCESSOR, processor);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setCaptureProcessor(@NonNull CaptureProcessor captureProcessor) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_PREVIEW_CAPTURE_PROCESSOR, captureProcessor);
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
