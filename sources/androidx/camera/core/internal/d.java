package androidx.camera.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.concurrent.Executor;

/* compiled from: ThreadConfig */
public final /* synthetic */ class d {
    @Nullable
    public static Executor b(@Nullable ThreadConfig _this, Executor valueIfMissing) {
        return (Executor) _this.retrieveOption(ThreadConfig.OPTION_BACKGROUND_EXECUTOR, valueIfMissing);
    }

    @NonNull
    public static Executor a(ThreadConfig _this) {
        return (Executor) _this.retrieveOption(ThreadConfig.OPTION_BACKGROUND_EXECUTOR);
    }
}
