package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraFilter;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.UseCaseConfigFactory;
import java.util.Set;

public class CameraConfigs {
    private static final CameraConfig EMPTY_CONFIG = new EmptyCameraConfig();

    @NonNull
    public static CameraConfig emptyConfig() {
        return EMPTY_CONFIG;
    }

    public static final class EmptyCameraConfig implements CameraConfig {
        private final UseCaseConfigFactory mUseCaseConfigFactory = new UseCaseConfigFactory() {
            @Nullable
            public Config getConfig(@NonNull UseCaseConfigFactory.CaptureType captureType) {
                return null;
            }
        };

        public /* synthetic */ boolean containsOption(Config.Option option) {
            return a0.a(this, option);
        }

        public /* synthetic */ void findOptions(String str, Config.OptionMatcher optionMatcher) {
            a0.b(this, str, optionMatcher);
        }

        public /* synthetic */ CameraFilter getCameraFilter() {
            return v.a(this);
        }

        public /* synthetic */ Config.OptionPriority getOptionPriority(Config.Option option) {
            return a0.c(this, option);
        }

        public /* synthetic */ Set getPriorities(Config.Option option) {
            return a0.d(this, option);
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

        EmptyCameraConfig() {
        }

        @NonNull
        public UseCaseConfigFactory getUseCaseConfigFactory() {
            return this.mUseCaseConfigFactory;
        }

        @NonNull
        public Config getConfig() {
            return OptionsBundle.emptyBundle();
        }
    }

    private CameraConfigs() {
    }
}
