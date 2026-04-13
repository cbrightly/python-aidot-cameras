package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.impl.Config;
import java.util.Set;

public interface ReadableConfig extends Config {
    boolean containsOption(@NonNull Config.Option<?> option);

    void findOptions(@NonNull String str, @NonNull Config.OptionMatcher optionMatcher);

    @NonNull
    Config getConfig();

    @NonNull
    Config.OptionPriority getOptionPriority(@NonNull Config.Option<?> option);

    @NonNull
    Set<Config.OptionPriority> getPriorities(@NonNull Config.Option<?> option);

    @NonNull
    Set<Config.Option<?>> listOptions();

    @Nullable
    <ValueT> ValueT retrieveOption(@NonNull Config.Option<ValueT> option);

    @Nullable
    <ValueT> ValueT retrieveOption(@NonNull Config.Option<ValueT> option, @Nullable ValueT valuet);

    @Nullable
    <ValueT> ValueT retrieveOptionWithPriority(@NonNull Config.Option<ValueT> option, @NonNull Config.OptionPriority optionPriority);
}
