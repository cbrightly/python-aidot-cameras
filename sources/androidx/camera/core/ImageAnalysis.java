package androidx.camera.core;

import android.util.Pair;
import android.util.Size;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ConfigProvider;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageAnalysisConfig;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.x;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.ThreadConfig;
import androidx.camera.core.internal.UseCaseEventConfig;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;

public final class ImageAnalysis extends UseCase {
    private static final int DEFAULT_BACKPRESSURE_STRATEGY = 0;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final Defaults DEFAULT_CONFIG = new Defaults();
    private static final int DEFAULT_IMAGE_QUEUE_DEPTH = 6;
    private static final int NON_BLOCKING_IMAGE_DEPTH = 4;
    public static final int STRATEGY_BLOCK_PRODUCER = 1;
    public static final int STRATEGY_KEEP_ONLY_LATEST = 0;
    private static final String TAG = "ImageAnalysis";
    private final Object mAnalysisLock = new Object();
    @Nullable
    private DeferrableSurface mDeferrableSurface;
    final ImageAnalysisAbstractAnalyzer mImageAnalysisAbstractAnalyzer;
    @GuardedBy("mAnalysisLock")
    private Analyzer mSubscribedAnalyzer;

    public interface Analyzer {
        void analyze(@NonNull ImageProxy imageProxy);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BackpressureStrategy {
    }

    ImageAnalysis(@NonNull ImageAnalysisConfig config) {
        super(config);
        if (((ImageAnalysisConfig) getCurrentConfig()).getBackpressureStrategy(0) == 1) {
            this.mImageAnalysisAbstractAnalyzer = new ImageAnalysisBlockingAnalyzer();
        } else {
            this.mImageAnalysisAbstractAnalyzer = new ImageAnalysisNonBlockingAnalyzer(config.getBackgroundExecutor(CameraXExecutors.highPriorityExecutor()));
        }
    }

    /* access modifiers changed from: package-private */
    public SessionConfig.Builder createPipeline(@NonNull String cameraId, @NonNull ImageAnalysisConfig config, @NonNull Size resolution) {
        int imageQueueDepth;
        SafeCloseImageReaderProxy imageReaderProxy;
        Threads.checkMainThread();
        Executor backgroundExecutor = (Executor) Preconditions.checkNotNull(config.getBackgroundExecutor(CameraXExecutors.highPriorityExecutor()));
        if (getBackpressureStrategy() == 1) {
            imageQueueDepth = getImageQueueDepth();
        } else {
            imageQueueDepth = 4;
        }
        if (config.getImageReaderProxyProvider() != null) {
            imageReaderProxy = new SafeCloseImageReaderProxy(config.getImageReaderProxyProvider().newInstance(resolution.getWidth(), resolution.getHeight(), getImageFormat(), imageQueueDepth, 0));
        } else {
            imageReaderProxy = new SafeCloseImageReaderProxy(ImageReaderProxys.createIsolatedReader(resolution.getWidth(), resolution.getHeight(), getImageFormat(), imageQueueDepth));
        }
        tryUpdateRelativeRotation();
        imageReaderProxy.setOnImageAvailableListener(this.mImageAnalysisAbstractAnalyzer, backgroundExecutor);
        SessionConfig.Builder sessionConfigBuilder = SessionConfig.Builder.createFrom(config);
        DeferrableSurface deferrableSurface = this.mDeferrableSurface;
        if (deferrableSurface != null) {
            deferrableSurface.close();
        }
        ImmediateSurface immediateSurface = new ImmediateSurface(imageReaderProxy.getSurface());
        this.mDeferrableSurface = immediateSurface;
        ListenableFuture<Void> terminationFuture = immediateSurface.getTerminationFuture();
        Objects.requireNonNull(imageReaderProxy);
        terminationFuture.addListener(new z1(imageReaderProxy), CameraXExecutors.mainThreadExecutor());
        sessionConfigBuilder.addSurface(this.mDeferrableSurface);
        sessionConfigBuilder.addErrorListener(new p(this, cameraId, config, resolution));
        return sessionConfigBuilder;
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$createPipeline$0 */
    public /* synthetic */ void a(String cameraId, ImageAnalysisConfig config, Size resolution, SessionConfig sessionConfig, SessionConfig.SessionError error) {
        clearPipeline();
        this.mImageAnalysisAbstractAnalyzer.clearCache();
        if (isCurrentCamera(cameraId)) {
            updateSessionConfig(createPipeline(cameraId, config, resolution).build());
            notifyReset();
        }
    }

    /* access modifiers changed from: package-private */
    public void clearPipeline() {
        Threads.checkMainThread();
        DeferrableSurface deferrableSurface = this.mDeferrableSurface;
        if (deferrableSurface != null) {
            deferrableSurface.close();
            this.mDeferrableSurface = null;
        }
    }

    public void clearAnalyzer() {
        synchronized (this.mAnalysisLock) {
            this.mImageAnalysisAbstractAnalyzer.setAnalyzer((Executor) null, (Analyzer) null);
            if (this.mSubscribedAnalyzer != null) {
                notifyInactive();
            }
            this.mSubscribedAnalyzer = null;
        }
    }

    public int getTargetRotation() {
        return getTargetRotationInternal();
    }

    public void setTargetRotation(int rotation) {
        if (setTargetRotationInternal(rotation)) {
            tryUpdateRelativeRotation();
        }
    }

    public void setAnalyzer(@NonNull Executor executor, @NonNull Analyzer analyzer) {
        synchronized (this.mAnalysisLock) {
            this.mImageAnalysisAbstractAnalyzer.setAnalyzer(executor, new q(this, analyzer));
            if (this.mSubscribedAnalyzer == null) {
                notifyActive();
            }
            this.mSubscribedAnalyzer = analyzer;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$setAnalyzer$1 */
    public /* synthetic */ void b(Analyzer analyzer, ImageProxy image) {
        if (getViewPortCropRect() != null) {
            image.setCropRect(getViewPortCropRect());
        }
        analyzer.analyze(image);
    }

    public int getBackpressureStrategy() {
        return ((ImageAnalysisConfig) getCurrentConfig()).getBackpressureStrategy(0);
    }

    public int getImageQueueDepth() {
        return ((ImageAnalysisConfig) getCurrentConfig()).getImageQueueDepth(6);
    }

    @NonNull
    public String toString() {
        return "ImageAnalysis:" + getName();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onDetached() {
        clearPipeline();
        this.mImageAnalysisAbstractAnalyzer.detach();
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfig<?> getDefaultConfig(boolean applyDefaultConfig, @NonNull UseCaseConfigFactory factory) {
        Config captureConfig = factory.getConfig(UseCaseConfigFactory.CaptureType.IMAGE_ANALYSIS);
        if (applyDefaultConfig) {
            captureConfig = x.b(captureConfig, DEFAULT_CONFIG.getConfig());
        }
        if (captureConfig == null) {
            return null;
        }
        return getUseCaseConfigBuilder(captureConfig).getUseCaseConfig();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onAttached() {
        this.mImageAnalysisAbstractAnalyzer.attach();
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfig.Builder<?, ?, ?> getUseCaseConfigBuilder(@NonNull Config config) {
        return Builder.fromConfig(config);
    }

    /* access modifiers changed from: protected */
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Size onSuggestedResolutionUpdated(@NonNull Size suggestedResolution) {
        updateSessionConfig(createPipeline(getCameraId(), (ImageAnalysisConfig) getCurrentConfig(), suggestedResolution).build());
        return suggestedResolution;
    }

    private void tryUpdateRelativeRotation() {
        CameraInternal cameraInternal = getCamera();
        if (cameraInternal != null) {
            this.mImageAnalysisAbstractAnalyzer.setRelativeRotation(getRelativeRotation(cameraInternal));
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final class Defaults implements ConfigProvider<ImageAnalysisConfig> {
        private static final int DEFAULT_ASPECT_RATIO = 0;
        private static final ImageAnalysisConfig DEFAULT_CONFIG;
        private static final Size DEFAULT_MAX_RESOLUTION;
        private static final int DEFAULT_SURFACE_OCCUPANCY_PRIORITY = 1;
        private static final Size DEFAULT_TARGET_RESOLUTION;

        static {
            Size size = new Size(640, 480);
            DEFAULT_TARGET_RESOLUTION = size;
            Size size2 = new Size(1920, 1080);
            DEFAULT_MAX_RESOLUTION = size2;
            DEFAULT_CONFIG = new Builder().setDefaultResolution(size).setMaxResolution(size2).setSurfaceOccupancyPriority(1).setTargetAspectRatio(0).getUseCaseConfig();
        }

        @NonNull
        public ImageAnalysisConfig getConfig() {
            return DEFAULT_CONFIG;
        }
    }

    public static final class Builder implements ImageOutputConfig.Builder<Builder>, ThreadConfig.Builder<Builder>, UseCaseConfig.Builder<ImageAnalysis, ImageAnalysisConfig, Builder> {
        private final MutableOptionsBundle mMutableConfig;

        public Builder() {
            this(MutableOptionsBundle.create());
        }

        private Builder(MutableOptionsBundle mutableConfig) {
            Class<ImageAnalysis> cls = ImageAnalysis.class;
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
        public static Builder fromConfig(@NonNull ImageAnalysisConfig configuration) {
            return new Builder(MutableOptionsBundle.from(configuration));
        }

        @NonNull
        public Builder setBackpressureStrategy(int strategy) {
            getMutableConfig().insertOption(ImageAnalysisConfig.OPTION_BACKPRESSURE_STRATEGY, Integer.valueOf(strategy));
            return this;
        }

        @NonNull
        public Builder setImageQueueDepth(int depth) {
            getMutableConfig().insertOption(ImageAnalysisConfig.OPTION_IMAGE_QUEUE_DEPTH, Integer.valueOf(depth));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public MutableConfig getMutableConfig() {
            return this.mMutableConfig;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public ImageAnalysisConfig getUseCaseConfig() {
            return new ImageAnalysisConfig(OptionsBundle.from(this.mMutableConfig));
        }

        @NonNull
        public ImageAnalysis build() {
            if (getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO, null) == null || getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, null) == null) {
                return new ImageAnalysis(getUseCaseConfig());
            }
            throw new IllegalArgumentException("Cannot use both setTargetResolution and setTargetAspectRatio on the same config.");
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setTargetClass(@NonNull Class<ImageAnalysis> targetClass) {
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
        @RestrictTo({RestrictTo.Scope.LIBRARY})
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
        public Builder setImageReaderProxyProvider(@NonNull ImageReaderProxyProvider imageReaderProxyProvider) {
            getMutableConfig().insertOption(ImageAnalysisConfig.OPTION_IMAGE_READER_PROXY_PROVIDER, imageReaderProxyProvider);
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
