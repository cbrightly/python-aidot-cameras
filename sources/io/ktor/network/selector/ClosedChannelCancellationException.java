package io.ktor.network.selector;

import java.util.concurrent.CancellationException;

/* compiled from: Selectable.kt */
public final class ClosedChannelCancellationException extends CancellationException {
    public ClosedChannelCancellationException() {
        super("Closed channel.");
    }
}
