package okhttp3.internal.http2;

import java.io.IOException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: StreamResetException.kt */
public final class StreamResetException extends IOException {
    @NotNull
    public final a errorCode;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public StreamResetException(@NotNull a errorCode2) {
        super("stream was reset: " + errorCode2);
        k.f(errorCode2, "errorCode");
        this.errorCode = errorCode2;
    }
}
