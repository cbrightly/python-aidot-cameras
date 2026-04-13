package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExtendableBuilder;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.UseCaseEventConfig;
import androidx.core.util.Consumer;
import java.util.Collection;

public interface UseCaseConfig<T extends UseCase> extends TargetConfig<T>, UseCaseEventConfig, ImageInputConfig {
    public static final Config.Option<Consumer<Collection<UseCase>>> OPTION_ATTACHED_USE_CASES_UPDATE_LISTENER = Config.Option.create("camerax.core.useCase.attachedUseCasesUpdateListener", Consumer.class);
    public static final Config.Option<CameraSelector> OPTION_CAMERA_SELECTOR = Config.Option.create("camerax.core.useCase.cameraSelector", CameraSelector.class);
    public static final Config.Option<CaptureConfig.OptionUnpacker> OPTION_CAPTURE_CONFIG_UNPACKER = Config.Option.create("camerax.core.useCase.captureConfigUnpacker", CaptureConfig.OptionUnpacker.class);
    public static final Config.Option<CaptureConfig> OPTION_DEFAULT_CAPTURE_CONFIG = Config.Option.create("camerax.core.useCase.defaultCaptureConfig", CaptureConfig.class);
    public static final Config.Option<SessionConfig> OPTION_DEFAULT_SESSION_CONFIG = Config.Option.create("camerax.core.useCase.defaultSessionConfig", SessionConfig.class);
    public static final Config.Option<SessionConfig.OptionUnpacker> OPTION_SESSION_CONFIG_UNPACKER = Config.Option.create("camerax.core.useCase.sessionConfigUnpacker", SessionConfig.OptionUnpacker.class);
    public static final Config.Option<Integer> OPTION_SURFACE_OCCUPANCY_PRIORITY = Config.Option.create("camerax.core.useCase.surfaceOccupancyPriority", Integer.TYPE);

    public interface Builder<T extends UseCase, C extends UseCaseConfig<T>, B> extends TargetConfig.Builder<T, B>, ExtendableBuilder<T>, UseCaseEventConfig.Builder<B> {
        @NonNull
        C getUseCaseConfig();

        @NonNull
        B setAttachedUseCasesUpdateListener(@NonNull Consumer<Collection<UseCase>> consumer);

        @NonNull
        B setCameraSelector(@NonNull CameraSelector cameraSelector);

        @NonNull
        B setCaptureOptionUnpacker(@NonNull CaptureConfig.OptionUnpacker optionUnpacker);

        @NonNull
        B setDefaultCaptureConfig(@NonNull CaptureConfig captureConfig);

        @NonNull
        B setDefaultSessionConfig(@NonNull SessionConfig sessionConfig);

        @NonNull
        B setSessionOptionUnpacker(@NonNull SessionConfig.OptionUnpacker optionUnpacker);

        @NonNull
        B setSurfaceOccupancyPriority(int i);
    }

    @NonNull
    Consumer<Collection<UseCase>> getAttachedUseCasesUpdateListener();

    @Nullable
    Consumer<Collection<UseCase>> getAttachedUseCasesUpdateListener(@Nullable Consumer<Collection<UseCase>> consumer);

    @NonNull
    CameraSelector getCameraSelector();

    @Nullable
    CameraSelector getCameraSelector(@Nullable CameraSelector cameraSelector);

    @NonNull
    CaptureConfig.OptionUnpacker getCaptureOptionUnpacker();

    @Nullable
    CaptureConfig.OptionUnpacker getCaptureOptionUnpacker(@Nullable CaptureConfig.OptionUnpacker optionUnpacker);

    @NonNull
    CaptureConfig getDefaultCaptureConfig();

    @Nullable
    CaptureConfig getDefaultCaptureConfig(@Nullable CaptureConfig captureConfig);

    @NonNull
    SessionConfig getDefaultSessionConfig();

    @Nullable
    SessionConfig getDefaultSessionConfig(@Nullable SessionConfig sessionConfig);

    @NonNull
    SessionConfig.OptionUnpacker getSessionOptionUnpacker();

    @Nullable
    SessionConfig.OptionUnpacker getSessionOptionUnpacker(@Nullable SessionConfig.OptionUnpacker optionUnpacker);

    int getSurfaceOccupancyPriority();

    int getSurfaceOccupancyPriority(int i);
}
