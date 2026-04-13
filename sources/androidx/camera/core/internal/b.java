package androidx.camera.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.concurrent.Executor;

/* compiled from: IoConfig */
public final /* synthetic */ class b {
    @Nullable
    public static Executor b(@Nullable IoConfig _this, Executor valueIfMissing) {
        return (Executor) _this.retrieveOption(IoConfig.OPTION_IO_EXECUTOR, valueIfMissing);
    }

    @NonNull
    public static Executor a(IoConfig _this) {
        return (Executor) _this.retrieveOption(IoConfig.OPTION_IO_EXECUTOR);
    }
}
