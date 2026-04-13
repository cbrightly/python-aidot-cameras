package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.impl.Config;

/* compiled from: Config */
public final /* synthetic */ class x {
    public static boolean a(@NonNull Config.OptionPriority priority1, @NonNull Config.OptionPriority priority2) {
        Config.OptionPriority optionPriority = Config.OptionPriority.ALWAYS_OVERRIDE;
        if (priority1 == optionPriority && priority2 == optionPriority) {
            return true;
        }
        Config.OptionPriority optionPriority2 = Config.OptionPriority.REQUIRED;
        if (priority1 == optionPriority2 && priority2 == optionPriority2) {
            return true;
        }
        return false;
    }

    @NonNull
    public static Config b(@Nullable Config extendedConfig, @Nullable Config baseConfig) {
        MutableOptionsBundle mergedConfig;
        if (extendedConfig == null && baseConfig == null) {
            return OptionsBundle.emptyBundle();
        }
        if (baseConfig != null) {
            mergedConfig = MutableOptionsBundle.from(baseConfig);
        } else {
            mergedConfig = MutableOptionsBundle.create();
        }
        if (extendedConfig != null) {
            for (Config.Option<?> opt : extendedConfig.listOptions()) {
                Config.Option<?> option = opt;
                mergedConfig.insertOption(option, extendedConfig.getOptionPriority(opt), extendedConfig.retrieveOption(option));
            }
        }
        return OptionsBundle.from(mergedConfig);
    }
}
