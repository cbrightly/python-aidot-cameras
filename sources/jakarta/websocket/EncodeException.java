package jakarta.websocket;

public class EncodeException extends Exception {
    private static final long serialVersionUID = 6;
    private final Object object;

    public EncodeException(Object object2, String message) {
        super(message);
        this.object = object2;
    }

    public EncodeException(Object object2, String message, Throwable cause) {
        super(message, cause);
        this.object = object2;
    }

    public Object getObject() {
        return this.object;
    }
}
