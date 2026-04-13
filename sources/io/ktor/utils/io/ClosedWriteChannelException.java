package io.ktor.utils.io;

import java.util.concurrent.CancellationException;
import org.jetbrains.annotations.Nullable;

/* compiled from: ByteWriteChannel.kt */
public final class ClosedWriteChannelException extends CancellationException {
    public ClosedWriteChannelException(@Nullable String message) {
        super(message);
    }
}
