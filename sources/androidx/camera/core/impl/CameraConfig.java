package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraFilter;
import androidx.camera.core.impl.Config;

public interface CameraConfig extends ReadableConfig {
    public static final Config.Option<CameraFilter> OPTION_CAMERA_FILTER = Config.Option.create("camerax.core.camera.cameraFilter", CameraFilter.class);
    public static final Config.Option<UseCaseConfigFactory> OPTION_USECASE_CONFIG_FACTORY = Config.Option.create("camerax.core.camera.useCaseConfigFactory", UseCaseConfigFactory.class);

    public interface Builder<B> {
        @NonNull
        B setCameraFilter(@NonNull CameraFilter cameraFilter);

        @NonNull
        B setUseCaseConfigFactory(@NonNull UseCaseConfigFactory useCaseConfigFactory);
    }

    @NonNull
    CameraFilter getCameraFilter();

    @NonNull
    UseCaseConfigFactory getUseCaseConfigFactory();
}
