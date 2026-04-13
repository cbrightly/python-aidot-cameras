package junit.framework;

/* compiled from: ComparisonFailure */
public class c extends a {
    private static final long serialVersionUID = 1;
    private String fActual;
    private String fExpected;

    public c(String message, String expected, String actual) {
        super(message);
        this.fExpected = expected;
        this.fActual = actual;
    }

    public String getMessage() {
        return new b(20, this.fExpected, this.fActual).b(super.getMessage());
    }

    public String getActual() {
        return this.fActual;
    }

    public String getExpected() {
        return this.fExpected;
    }
}
