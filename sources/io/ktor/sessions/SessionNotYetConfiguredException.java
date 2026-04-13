package io.ktor.sessions;

/* compiled from: Sessions.kt */
public final class SessionNotYetConfiguredException extends IllegalStateException {
    public SessionNotYetConfiguredException() {
        super("Sessions are not yet ready: you are asking it to early before the Sessions feature.");
    }
}
