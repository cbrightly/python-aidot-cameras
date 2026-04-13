package junit.framework;

/* compiled from: TestFailure */
public class e {
    protected Test a;
    protected Throwable b;

    public e(Test failedTest, Throwable thrownException) {
        this.a = failedTest;
        this.b = thrownException;
    }

    public String toString() {
        return this.a + ": " + this.b.getMessage();
    }
}
