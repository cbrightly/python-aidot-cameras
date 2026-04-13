package junit.framework;

import java.util.ArrayList;
import java.util.List;

/* compiled from: TestResult */
public class g {
    protected List<e> a = new ArrayList();
    protected List<e> b = new ArrayList();
    protected List<f> c = new ArrayList();
    protected int d = 0;
    private boolean e = false;

    public synchronized void a(Test test, Throwable e2) {
        this.b.add(new e(test, e2));
        for (f each : c()) {
            each.a(test, e2);
        }
    }

    public synchronized void b(Test test, a e2) {
        this.a.add(new e(test, e2));
        for (f each : c()) {
            each.b(test, e2);
        }
    }

    private synchronized List<f> c() {
        List<TestListener> result;
        result = new ArrayList<>();
        result.addAll(this.c);
        return result;
    }

    public void d(Test test) {
        for (f each : c()) {
            each.c(test);
        }
    }

    /* access modifiers changed from: protected */
    public void e(TestCase test) {
        g(test);
        f(test, new a(test));
        d(test);
    }

    /* compiled from: TestResult */
    public class a implements d {
        final /* synthetic */ TestCase a;

        a(TestCase testCase) {
            this.a = testCase;
        }

        public void a() {
            this.a.runBare();
        }
    }

    public void f(Test test, d p) {
        try {
            p.a();
        } catch (a e2) {
            b(test, e2);
        } catch (ThreadDeath e3) {
            throw e3;
        } catch (Throwable e4) {
            a(test, e4);
        }
    }

    public void g(Test test) {
        int count = test.countTestCases();
        synchronized (this) {
            this.d += count;
        }
        for (f each : c()) {
            each.d(test);
        }
    }
}
