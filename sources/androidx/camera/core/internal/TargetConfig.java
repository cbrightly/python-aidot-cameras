package androidx.camera.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ReadableConfig;

public interface TargetConfig<T> extends ReadableConfig {
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final Config.Option<Class<?>> OPTION_TARGET_CLASS = Config.Option.create("camerax.core.target.class", Class.class);
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final Config.Option<String> OPTION_TARGET_NAME = Config.Option.create("camerax.core.target.name", String.class);

    public interface Builder<T, B> {
        @NonNull
        B setTargetClass(@NonNull Class<T> cls);

        @NonNull
        B setTargetName(@NonNull String str);
    }

    @NonNull
    Class<T> getTargetClass();

    @Nullable
    Class<T> getTargetClass(@Nullable Class<T> cls);

    @NonNull
    String getTargetName();

    @Nullable
    String getTargetName(@Nullable String str);
}
