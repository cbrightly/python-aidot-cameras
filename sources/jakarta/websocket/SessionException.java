package jakarta.websocket;

public class SessionException extends Exception {
    private static final long serialVersionUID = 12;
    private final Session session;

    public SessionException(String message, Throwable cause, Session session2) {
        super(message, cause);
        this.session = session2;
    }

    public Session getSession() {
        return this.session;
    }
}
