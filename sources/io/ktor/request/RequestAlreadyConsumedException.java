package io.ktor.request;

/* compiled from: ApplicationReceiveFunctions.kt */
public final class RequestAlreadyConsumedException extends IllegalStateException {
    public RequestAlreadyConsumedException() {
        super("Request body has been already consumed (received).");
    }
}
