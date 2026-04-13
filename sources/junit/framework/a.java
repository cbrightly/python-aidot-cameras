package junit.framework;

/* compiled from: AssertionFailedError */
public class a extends AssertionError {
    private static final long serialVersionUID = 1;

    public a() {
    }

    public a(String message) {
        super(a(message));
    }

    private static String a(String message) {
        return message == null ? "" : message;
    }
}
