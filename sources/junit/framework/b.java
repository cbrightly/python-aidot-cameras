package junit.framework;

import com.meituan.robust.Constants;

/* compiled from: ComparisonCompactor */
public class b {
    private int a;
    private String b;
    private String c;
    private int d;
    private int e;

    public b(int contextLength, String expected, String actual) {
        this.a = contextLength;
        this.b = expected;
        this.c = actual;
    }

    public String b(String message) {
        if (this.b == null || this.c == null || a()) {
            return Assert.format(message, this.b, this.c);
        }
        f();
        g();
        return Assert.format(message, c(this.b), c(this.c));
    }

    private String c(String source) {
        String result = Constants.ARRAY_TYPE + source.substring(this.d, (source.length() - this.e) + 1) + "]";
        if (this.d > 0) {
            result = d() + result;
        }
        if (this.e <= 0) {
            return result;
        }
        return result + e();
    }

    private void f() {
        this.d = 0;
        int end = Math.min(this.b.length(), this.c.length());
        while (true) {
            int i = this.d;
            if (i < end && this.b.charAt(i) == this.c.charAt(this.d)) {
                this.d++;
            } else {
                return;
            }
        }
    }

    private void g() {
        int expectedSuffix = this.b.length() - 1;
        int actualSuffix = this.c.length() - 1;
        while (true) {
            int i = this.d;
            if (actualSuffix < i || expectedSuffix < i || this.b.charAt(expectedSuffix) != this.c.charAt(actualSuffix)) {
                this.e = this.b.length() - expectedSuffix;
            } else {
                actualSuffix--;
                expectedSuffix--;
            }
        }
        this.e = this.b.length() - expectedSuffix;
    }

    private String d() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.d > this.a ? "..." : "");
        sb.append(this.b.substring(Math.max(0, this.d - this.a), this.d));
        return sb.toString();
    }

    private String e() {
        int end = Math.min((this.b.length() - this.e) + 1 + this.a, this.b.length());
        StringBuilder sb = new StringBuilder();
        String str = this.b;
        sb.append(str.substring((str.length() - this.e) + 1, end));
        sb.append((this.b.length() - this.e) + 1 < this.b.length() - this.a ? "..." : "");
        return sb.toString();
    }

    private boolean a() {
        return this.b.equals(this.c);
    }
}
