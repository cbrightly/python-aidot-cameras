package io.ktor.util.cio;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Channels.kt */
public final class ChannelWriteException extends ChannelIOException {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ChannelWriteException(@NotNull String message, @NotNull Throwable exception) {
        super(message, exception);
        k.f(message, "message");
        k.f(exception, "exception");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ChannelWriteException(String str, Throwable th, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "Cannot write to a channel" : str, th);
    }
}
