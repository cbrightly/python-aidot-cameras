package io.ktor.util.cio;

import java.io.IOException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Channels.kt */
public class ChannelIOException extends IOException {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ChannelIOException(@NotNull String message, @NotNull Throwable exception) {
        super(message, exception);
        k.f(message, "message");
        k.f(exception, "exception");
    }
}
