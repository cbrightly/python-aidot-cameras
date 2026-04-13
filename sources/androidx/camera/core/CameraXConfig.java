package androidx.camera.core;

import android.os.Handler;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.CameraDeviceSurfaceManager;
import androidx.camera.core.impl.CameraFactory;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.a0;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.c;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;

public final class CameraXConfig implements TargetConfig<CameraX> {
    static final Config.Option<CameraSelector> OPTION_AVAILABLE_CAMERAS_LIMITER = Config.Option.create("camerax.core.appConfig.availableCamerasLimiter", CameraSelector.class);
    static final Config.Option<Executor> OPTION_CAMERA_EXECUTOR = Config.Option.create("camerax.core.appConfig.cameraExecutor", Executor.class);
    static final Config.Option<CameraFactory.Provider> OPTION_CAMERA_FACTORY_PROVIDER = Config.Option.create("camerax.core.appConfig.cameraFactoryProvider", CameraFactory.Provider.class);
    static final Config.Option<CameraDeviceSurfaceManager.Provider> OPTION_DEVICE_SURFACE_MANAGER_PROVIDER = Config.Option.create("camerax.core.appConfig.deviceSurfaceManagerProvider", CameraDeviceSurfaceManager.Provider.class);
    static final Config.Option<Integer> OPTION_MIN_LOGGING_LEVEL = Config.Option.create("camerax.core.appConfig.minimumLoggingLevel", Integer.TYPE);
    static final Config.Option<Handler> OPTION_SCHEDULER_HANDLER = Config.Option.create("camerax.core.appConfig.schedulerHandler", Handler.class);
    static final Config.Option<UseCaseConfigFactory.Provider> OPTION_USECASE_CONFIG_FACTORY_PROVIDER = Config.Option.create("camerax.core.appConfig.useCaseConfigFactoryProvider", UseCaseConfigFactory.Provider.class);
    private final OptionsBundle mConfig;

    public interface Provider {
        @NonNull
        CameraXConfig getCameraXConfig();
    }

    public /* synthetic */ boolean containsOption(Config.Option option) {
        return a0.a(this, option);
    }

    public /* synthetic */ void findOptions(String str, Config.OptionMatcher optionMatcher) {
        a0.b(this, str, optionMatcher);
    }

    public /* synthetic */ Config.OptionPriority getOptionPriority(Config.Option option) {
        return a0.c(this, option);
    }

    public /* synthetic */ Set getPriorities(Config.Option option) {
        return a0.d(this, option);
    }

    public /* synthetic */ Class getTargetClass() {
        return c.a(this);
    }

    public /* synthetic */ Class getTargetClass(Class cls) {
        return c.b(this, cls);
    }

    public /* synthetic */ String getTargetName() {
        return c.c(this);
    }

    public /* synthetic */ String getTargetName(String str) {
        return c.d(this, str);
    }

    public /* synthetic */ Set listOptions() {
        return a0.e(this);
    }

    public /* synthetic */ Object retrieveOption(Config.Option option) {
        return a0.f(this, option);
    }

    public /* synthetic */ Object retrieveOption(Config.Option option, Object obj) {
        return a0.g(this, option, obj);
    }

    public /* synthetic */ Object retrieveOptionWithPriority(Config.Option option, Config.OptionPriority optionPriority) {
        return a0.h(this, option, optionPriority);
    }

    CameraXConfig(OptionsBundle options) {
        this.mConfig = options;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CameraFactory.Provider getCameraFactoryProvider(@Nullable CameraFactory.Provider valueIfMissing) {
        return (CameraFactory.Provider) this.mConfig.retrieveOption(OPTION_CAMERA_FACTORY_PROVIDER, valueIfMissing);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CameraDeviceSurfaceManager.Provider getDeviceSurfaceManagerProvider(@Nullable CameraDeviceSurfaceManager.Provider valueIfMissing) {
        return (CameraDeviceSurfaceManager.Provider) this.mConfig.retrieveOption(OPTION_DEVICE_SURFACE_MANAGER_PROVIDER, valueIfMissing);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfigFactory.Provider getUseCaseConfigFactoryProvider(@Nullable UseCaseConfigFactory.Provider valueIfMissing) {
        return (UseCaseConfigFactory.Provider) this.mConfig.retrieveOption(OPTION_USECASE_CONFIG_FACTORY_PROVIDER, valueIfMissing);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Executor getCameraExecutor(@Nullable Executor valueIfMissing) {
        return (Executor) this.mConfig.retrieveOption(OPTION_CAMERA_EXECUTOR, valueIfMissing);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Handler getSchedulerHandler(@Nullable Handler valueIfMissing) {
        return (Handler) this.mConfig.retrieveOption(OPTION_SCHEDULER_HANDLER, valueIfMissing);
    }

    @ExperimentalLogging
    public int getMinimumLoggingLevel() {
        return ((Integer) this.mConfig.retrieveOption(OPTION_MIN_LOGGING_LEVEL, 3)).intValue();
    }

    @Nullable
    @ExperimentalAvailableCamerasLimiter
    public CameraSelector getAvailableCamerasLimiter(@Nullable CameraSelector valueIfMissing) {
        return (CameraSelector) this.mConfig.retrieveOption(OPTION_AVAILABLE_CAMERAS_LIMITER, valueIfMissing);
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Config getConfig() {
        return this.mConfig;
    }

    public static final class Builder implements TargetConfig.Builder<CameraX, Builder> {
        private final MutableOptionsBundle mMutableConfig;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder() {
            this(MutableOptionsBundle.create());
        }

        private Builder(MutableOptionsBundle mutableConfig) {
            Class<CameraX> cls = CameraX.class;
            this.mMutableConfig = mutableConfig;
            Class<?> oldConfigClass = (Class) mutableConfig.retrieveOption(TargetConfig.OPTION_TARGET_CLASS, null);
            if (oldConfigClass == null || oldConfigClass.equals(cls)) {
                setTargetClass(cls);
                return;
            }
            throw new IllegalArgumentException("Invalid target class configuration for " + this + ": " + oldConfigClass);
        }

        @NonNull
        public static Builder fromConfig(@NonNull CameraXConfig configuration) {
            return new Builder(MutableOptionsBundle.from(configuration));
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setCameraFactoryProvider(@NonNull CameraFactory.Provider cameraFactory) {
            getMutableConfig().insertOption(CameraXConfig.OPTION_CAMERA_FACTORY_PROVIDER, cameraFactory);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setDeviceSurfaceManagerProvider(@NonNull CameraDeviceSurfaceManager.Provider surfaceManagerProvider) {
            getMutableConfig().insertOption(CameraXConfig.OPTION_DEVICE_SURFACE_MANAGER_PROVIDER, surfaceManagerProvider);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setUseCaseConfigFactoryProvider(@NonNull UseCaseConfigFactory.Provider configFactoryProvider) {
            getMutableConfig().insertOption(CameraXConfig.OPTION_USECASE_CONFIG_FACTORY_PROVIDER, configFactoryProvider);
            return this;
        }

        @NonNull
        public Builder setCameraExecutor(@NonNull Executor executor) {
            getMutableConfig().insertOption(CameraXConfig.OPTION_CAMERA_EXECUTOR, executor);
            return this;
        }

        @NonNull
        @ExperimentalCustomizableThreads
        public Builder setSchedulerHandler(@NonNull Handler handler) {
            getMutableConfig().insertOption(CameraXConfig.OPTION_SCHEDULER_HANDLER, handler);
            return this;
        }

        @NonNull
        @ExperimentalLogging
        public Builder setMinimumLoggingLevel(@IntRange(from = 3, to = 6) int logLevel) {
            getMutableConfig().insertOption(CameraXConfig.OPTION_MIN_LOGGING_LEVEL, Integer.valueOf(logLevel));
            return this;
        }

        @NonNull
        @ExperimentalAvailableCamerasLimiter
        public Builder setAvailableCamerasLimiter(@NonNull CameraSelector availableCameraSelector) {
            getMutableConfig().insertOption(CameraXConfig.OPTION_AVAILABLE_CAMERAS_LIMITER, availableCameraSelector);
            return this;
        }

        @NonNull
        private MutableConfig getMutableConfig() {
            return this.mMutableConfig;
        }

        @NonNull
        public CameraXConfig build() {
            return new CameraXConfig(OptionsBundle.from(this.mMutableConfig));
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setTargetClass(@NonNull Class<CameraX> targetClass) {
            getMutableConfig().insertOption(TargetConfig.OPTION_TARGET_CLASS, targetClass);
            if (getMutableConfig().retrieveOption(TargetConfig.OPTION_TARGET_NAME, null) == null) {
                setTargetName(targetClass.getCanonicalName() + "-" + UUID.randomUUID());
            }
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setTargetName(@NonNull String targetName) {
            getMutableConfig().insertOption(TargetConfig.OPTION_TARGET_NAME, targetName);
            return this;
        }
    }
}
