package jakarta.websocket;

/* compiled from: SendResult */
public final class i {
    private final Throwable a;
    private final boolean b;

    public i(Throwable exception) {
        this.a = exception;
        this.b = false;
    }

    public i() {
        this.a = null;
        this.b = true;
    }
}
