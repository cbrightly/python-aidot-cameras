package io.ktor.sessions;

/* compiled from: Sessions.kt */
public final class TooLateSessionSetException extends IllegalStateException {
    public TooLateSessionSetException() {
        super("It's too late to set session: response most likely already has been sent");
    }
}
