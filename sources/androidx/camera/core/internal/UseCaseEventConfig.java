package androidx.camera.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ReadableConfig;

public interface UseCaseEventConfig extends ReadableConfig {
    public static final Config.Option<UseCase.EventCallback> OPTION_USE_CASE_EVENT_CALLBACK = Config.Option.create("camerax.core.useCaseEventCallback", UseCase.EventCallback.class);

    public interface Builder<B> {
        @NonNull
        B setUseCaseEventCallback(@NonNull UseCase.EventCallback eventCallback);
    }

    @NonNull
    UseCase.EventCallback getUseCaseEventCallback();

    @Nullable
    UseCase.EventCallback getUseCaseEventCallback(@Nullable UseCase.EventCallback eventCallback);
}
