package androidx.camera.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ReadableConfig;
import java.util.concurrent.Executor;

public interface IoConfig extends ReadableConfig {
    public static final Config.Option<Executor> OPTION_IO_EXECUTOR = Config.Option.create("camerax.core.io.ioExecutor", Executor.class);

    public interface Builder<B> {
        @NonNull
        B setIoExecutor(@NonNull Executor executor);
    }

    @NonNull
    Executor getIoExecutor();

    @Nullable
    Executor getIoExecutor(@Nullable Executor executor);
}
